package com.odcgroup.iris.rim.generation.mappers

import com.temenos.interaction.rimdsl.rim.Command
import com.temenos.interaction.rimdsl.rim.CommandProperty
import com.temenos.interaction.rimdsl.rim.DomainDeclaration
import com.temenos.interaction.rimdsl.rim.DomainModel
import com.temenos.interaction.rimdsl.rim.Event
import com.temenos.interaction.rimdsl.rim.ImplRef
import com.temenos.interaction.rimdsl.rim.Ref
import com.temenos.interaction.rimdsl.rim.ResourceInteractionModel
import com.temenos.interaction.rimdsl.rim.State
import com.temenos.interaction.rimdsl.rim.Use
import com.temenos.interaction.rimdsl.scoping.RIMDslQualifiedNameProvider
import org.eclipse.emf.common.util.EList
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.xtext.generator.IFileSystemAccess
import com.temenos.interaction.rimdsl.rim.TransitionRef
import com.temenos.interaction.rimdsl.rim.Transition
import com.temenos.interaction.rimdsl.rim.TransitionSpec
import com.temenos.interaction.rimdsl.rim.TransitionAuto
import com.temenos.interaction.rimdsl.rim.TransitionEmbedded
import com.temenos.interaction.rimdsl.rim.TransitionForEach
import com.temenos.interaction.rimdsl.rim.OKFunction
import com.temenos.interaction.rimdsl.rim.NotFoundFunction
import com.temenos.interaction.rimdsl.rim.RelationRef
import com.temenos.interaction.rimdsl.rim.Relation
import com.temenos.interaction.rimdsl.rim.RelationConstant

class RIMSerializer {

	extension RIMDslQualifiedNameProvider = new RIMDslQualifiedNameProvider();

	def void generate(Resource resource, IFileSystemAccess fsa, String foldersAndFileName) {
	    if (resource == null) {
            throw new RuntimeException("Generator called with null resource");	        
	    }
	    var models = resource.contents.toList.filter(typeof(DomainModel));
	    if (models.size != 1)
	    	throw new AssertionError("Expecting exactly one DomainModel");
	    fsa.generateFile(foldersAndFileName, generate(models.iterator.next));
	}

	def generate(DomainModel model) '''
		«FOR ref : model.rims.toList.filter(typeof(Ref))»
		«refDispatch(ref)»
		«ENDFOR»
	'''

	def refDispatch(Ref ref) '''
		«IF ref instanceof DomainDeclaration»
		«domain(ref as DomainDeclaration)»
		«ELSEIF ref instanceof ResourceInteractionModel»
		«rim(ref as ResourceInteractionModel)»
		«ELSEIF ref instanceof Use»
		«use(ref as Use)»
        «ELSE»
		// invalid object seen
		«ENDIF»
	'''

	def domain(DomainDeclaration domain) '''
	domain «domain.fullyQualifiedName» {
		«FOR ref : domain.rims»
		«refDispatch(ref)»
		«ENDFOR»
	}
	'''

	def rim(ResourceInteractionModel rim) '''
	rim «rim.name» {
		«FOR event : rim.events»
		«event(event)»
		«ENDFOR»
		«FOR command : rim.commands»
		«command(command)»
		«ENDFOR»
		«IF rim.basepath != null»
		basepath: "«rim.basepath.name»"
		«ENDIF»

		«FOR state : rim.states»
		«state(state)»

		«ENDFOR»
	}

	'''

	def use(Use use) '''
	use «use.importedNamespace»
	'''

	def event(Event event) '''
	event «event.name»
	'''

	def command(Command command) '''
	command «command.name»
	'''

	def state(State state) '''
	resource «state.name» {
		«IF state.type.isItem»
		type: item
		«ELSEIF state.type.isCollection»
		type: collection
		«ELSE»
		// invalid type object seen
		«ENDIF»
		entity: «state.entity.name»
		«impl(state.impl)»
		«IF state.relations != null && state.relations.size > 0»
		«relations(state.relations)»
		«ENDIF»
		«IF state.path != null»
		path: "«state.path.name»"
		«ENDIF»
		«FOR transition : state.transitions»
		«transitionRef(transition)»
		«ENDFOR»
		«IF state.errorState != null»
		onerror --> «state.errorState.fullyQualifiedName»
		«ENDIF»
	}
	'''
	
	def impl(ImplRef impl) '''
		«IF impl.view != null»
		view: «impl.view.command.name»«IF impl.view.properties != null && impl.view.properties.size > 0» {
			«commandProperties(impl.view.properties)»
		}«ENDIF»
		«ELSEIF impl.actions != null»
		commands [ «FOR method : impl.methods SEPARATOR ', '»«method.event.name» : «method.command.command.name»«IF method.command.properties != null && method.command.properties.size > 0» {
			«commandProperties(method.command.properties)»
			}«ENDIF»«ENDFOR» ]
        «ELSE»
		// invalid impl object seen
		«ENDIF»
	'''
	
	def commandProperties(EList<CommandProperty> properties) '''
		properties [ «FOR prop : properties SEPARATOR ','»«prop.name» = "«prop.value»«ENDFOR»" ]
	'''

	def transitionRef(TransitionRef ref) '''
		«IF ref instanceof Transition»
		«transition(ref as Transition)»
		«ELSEIF ref instanceof TransitionForEach»
		«transition(ref as TransitionForEach)»
		«ELSEIF ref instanceof TransitionAuto»
		«transition(ref as TransitionAuto)»
		«ELSEIF ref instanceof TransitionEmbedded»
		«transition(ref as TransitionEmbedded)»
        «ELSE»
		// invalid transition type seen
		«ENDIF»
	'''

	def transition(Transition transition) '''
		«transition.event.name» -> «if(transition.state != null && transition.state.name != null) {transition.state.name} else {transition.name}»«IF transition.spec != null» {
			«transitionSpec(transition.spec)»
		}«ENDIF»
	'''

	def transition(TransitionForEach transition) '''
		«transition.event.name» *-> «if(transition.state != null && transition.state.name != null) {transition.state.name} else {transition.name}»«IF transition.spec != null» {
			«transitionSpec(transition.spec)»
		}«ENDIF»
	'''

	def transition(TransitionAuto transition) '''
		«transition.event.name» --> «if(transition.state != null && transition.state.name != null) {transition.state.name} else {transition.name}»«IF transition.spec != null» {
			«transitionSpec(transition.spec)»
		}«ENDIF»
	'''

	def transition(TransitionEmbedded transition) '''
		«transition.event.name» +-> «if(transition.state != null && transition.state.name != null) {transition.state.name} else {transition.name}»«IF transition.spec != null» {
			«transitionSpec(transition.spec)»
		}«ENDIF»
	'''

	def transitionSpec(TransitionSpec spec) '''
		«IF spec.uriLinks != null && spec.uriLinks.size() > 0»
		parameters [ «FOR parameter : spec.uriLinks SEPARATOR ','»«parameter.templateProperty» = "«parameter.entityProperty.name»"«ENDFOR» ]
		«ENDIF»
		«IF spec.title != null»
		title: "«spec.title.name»"
		«ENDIF»
		«IF spec.id != null»
		id: "«spec.id.name»"
		«ENDIF»
		«IF spec.eval != null»
		condition: «FOR expression : spec.eval.expressions SEPARATOR '&&'»«IF expression instanceof OKFunction»OK(«(expression as OKFunction).state.name»)«ENDIF»«IF expression instanceof NotFoundFunction»NOT_FOUND(«(expression as NotFoundFunction).state.name»)«ENDIF»«ENDFOR»
		«ENDIF»
	'''
	
	def relations(EList<RelationRef> relations) '''
		relations [ «FOR relation : relations SEPARATOR ','»«IF relation instanceof Relation»«(relation as Relation).name»«ENDIF»«IF relation instanceof RelationConstant»"«(relation as RelationConstant).name»"«ENDIF»«ENDFOR» ]
	'''
	
}