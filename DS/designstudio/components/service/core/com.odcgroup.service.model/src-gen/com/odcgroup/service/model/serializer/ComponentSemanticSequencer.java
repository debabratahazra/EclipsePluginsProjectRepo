package com.odcgroup.service.model.serializer;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.odcgroup.service.model.component.Argument;
import com.odcgroup.service.model.component.Attribute;
import com.odcgroup.service.model.component.Component;
import com.odcgroup.service.model.component.ComponentPackage;
import com.odcgroup.service.model.component.Constant;
import com.odcgroup.service.model.component.Content;
import com.odcgroup.service.model.component.Interface;
import com.odcgroup.service.model.component.Method;
import com.odcgroup.service.model.component.MethodAnnotation;
import com.odcgroup.service.model.component.Property;
import com.odcgroup.service.model.component.Table;
import com.odcgroup.service.model.services.ComponentGrammarAccess;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.serializer.acceptor.ISemanticSequenceAcceptor;
import org.eclipse.xtext.serializer.acceptor.SequenceFeeder;
import org.eclipse.xtext.serializer.diagnostic.ISemanticSequencerDiagnosticProvider;
import org.eclipse.xtext.serializer.diagnostic.ISerializationDiagnostic.Acceptor;
import org.eclipse.xtext.serializer.sequencer.AbstractDelegatingSemanticSequencer;
import org.eclipse.xtext.serializer.sequencer.GenericSequencer;
import org.eclipse.xtext.serializer.sequencer.ISemanticNodeProvider.INodesForEObjectProvider;
import org.eclipse.xtext.serializer.sequencer.ISemanticSequencer;
import org.eclipse.xtext.serializer.sequencer.ITransientValueService;
import org.eclipse.xtext.serializer.sequencer.ITransientValueService.ValueTransient;

@SuppressWarnings("all")
public class ComponentSemanticSequencer extends AbstractDelegatingSemanticSequencer {

	@Inject
	private ComponentGrammarAccess grammarAccess;

	public void createSequence(EObject context, EObject semanticObject) {
		if(semanticObject.eClass().getEPackage() == ComponentPackage.eINSTANCE) switch(semanticObject.eClass().getClassifierID()) {
			case ComponentPackage.ARGUMENT:
				if(context == grammarAccess.getArgumentRule()) {
					sequence_Argument(context, (Argument) semanticObject);
					return;
				}
				else break;
			case ComponentPackage.ATTRIBUTE:
				if(context == grammarAccess.getAttributeRule()) {
					sequence_Attribute(context, (Attribute) semanticObject);
					return;
				}
				else break;
			case ComponentPackage.COMPONENT:
				if(context == grammarAccess.getComponentRule()) {
					sequence_Component(context, (Component) semanticObject);
					return;
				}
				else break;
			case ComponentPackage.CONSTANT:
				if(context == grammarAccess.getConstantRule()) {
					sequence_Constant(context, (Constant) semanticObject);
					return;
				}
				else break;
			case ComponentPackage.CONTENT:
				if(context == grammarAccess.getContentRule()) {
					sequence_Content(context, (Content) semanticObject);
					return;
				}
				else break;
			case ComponentPackage.INTERFACE:
				if(context == grammarAccess.getInterfaceRule()) {
					sequence_Interface(context, (Interface) semanticObject);
					return;
				}
				else break;
			case ComponentPackage.METHOD:
				if(context == grammarAccess.getMethodRule()) {
					sequence_Method(context, (Method) semanticObject);
					return;
				}
				else break;
			case ComponentPackage.METHOD_ANNOTATION:
				if(context == grammarAccess.getMethodAnnotationRule()) {
					sequence_MethodAnnotation(context, (MethodAnnotation) semanticObject);
					return;
				}
				else break;
			case ComponentPackage.PROPERTY:
				if(context == grammarAccess.getPropertyRule()) {
					sequence_Property(context, (Property) semanticObject);
					return;
				}
				else break;
			case ComponentPackage.TABLE:
				if(context == grammarAccess.getTableRule()) {
					sequence_Table(context, (Table) semanticObject);
					return;
				}
				else break;
			}
		if (errorAcceptor != null) errorAcceptor.accept(diagnosticProvider.createInvalidContextOrTypeDiagnostic(semanticObject, context));
	}

	/**
	 * Constraint:
	 *     (documentation=Documentation? inout=InOutType? name=ID type=[MdfEntity|EntityRef] multiplicity=Multiplicity?)
	 */
	protected void sequence_Argument(EObject context, Argument semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}


	/**
	 * Constraint:
	 *     (attrName=ID jBCName=ID? value=INT)
	 */
	protected void sequence_Attribute(EObject context, Attribute semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}


	/**
	 * Constraint:
	 *     (documentation=Documentation? name=ID metamodelVersion=String_Value content+=Content*)
	 */
	protected void sequence_Component(EObject context, Component semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}


	/**
	 * Constraint:
	 *     (documentation=Documentation? accessSpecifier=AccessSpecifier constantName=ID jBCName=ID? value=INT)
	 */
	protected void sequence_Constant(EObject context, Constant semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}


	/**
	 * Constraint:
	 *     (interface=Interface | method+=Method | property+=Property | constant+=Constant | table+=Table)
	 */
	protected void sequence_Content(EObject context, Content semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}


	/**
	 * Constraint:
	 *     (documentation=Documentation? accessSpecifier=AccessSpecifier name=ID (arguments+=Argument arguments+=Argument*)?)
	 */
	protected void sequence_Interface(EObject context, Interface semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}


	/**
	 * Constraint:
	 *     name=T24MethodStereotype
	 */
	protected void sequence_MethodAnnotation(EObject context, MethodAnnotation semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, ComponentPackage.Literals.METHOD_ANNOTATION__NAME) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, ComponentPackage.Literals.METHOD_ANNOTATION__NAME));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getMethodAnnotationAccess().getNameT24MethodStereotypeEnumRuleCall_3_0(), semanticObject.getName());
		feeder.finish();
	}


	/**
	 * Constraint:
	 *     (
	 *         documentation=Documentation?
	 *         accessSpecifier=MethodAccessSpecifier
	 *         name=ID
	 *         annotations+=MethodAnnotation*
	 *         (arguments+=Argument arguments+=Argument*)?
	 *         type=JBC_ID?
	 *     )
	 */
	protected void sequence_Method(EObject context, Method semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}


	/**
	 * Constraint:
	 *     (
	 *         documentation=Documentation?
	 *         accessSpecifier=AccessSpecifier
	 *         readOnly=ReadWrite
	 *         propertyName=ID
	 *         type1=JBC_ID
	 *         type2=JBC_ID
	 *         (array?='(' value=INT?)?
	 *     )
	 */
	protected void sequence_Property(EObject context, Property semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}


	/**
	 * Constraint:
	 *     (documentation=Documentation? accessSpecifier=AccessSpecifier tableName=ID type=ID attribute+=Attribute*)
	 */
	protected void sequence_Table(EObject context, Table semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
}
