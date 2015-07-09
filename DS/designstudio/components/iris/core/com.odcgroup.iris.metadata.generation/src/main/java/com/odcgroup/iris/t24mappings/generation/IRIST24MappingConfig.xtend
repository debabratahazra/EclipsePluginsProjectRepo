package com.odcgroup.iris.t24mappings.generation

import com.odcgroup.t24.enquiry.util.EMEntity
import com.odcgroup.t24.enquiry.util.EMEntityModel
import com.odcgroup.t24.enquiry.util.EMProperty
import java.util.HashMap
import org.eclipse.xtend.expression.Variable
import org.eclipse.xtext.generator.IFileSystemAccess
import com.odcgroup.iris.generator.IIRISGenerator

class IRIST24MappingConfig implements IIRISGenerator {

	private var projectName = "";

	override doGenerate(EMEntityModel entityModel, IFileSystemAccess fsa, HashMap<String,Variable> globalVars) {
		var filename =  globalVars.get("fileName").value.toString
		projectName = entityModel.name
		fsa.generateFile(filename, renderT24Properties(entityModel, globalVars))
	}

	def renderT24Properties(EMEntityModel entityModel, HashMap<String,Variable> globalVars) '''
		# ----------------------------------------------------------------------------------------------------
		# T24 settings
		#
		# This file contains basic T24 descriptions such as mapping to
		# T24 entities and other field attributes specific to T24.
		# ----------------------------------------------------------------------------------------------------

		# NO T24 credentials for deployments with disabled web authentication
		# NOT t24Credentials = username: INPUTT|password: 123456|company:
		# Having env. specific information in a gen. T24.properties does not make sense from a DS perspective.
		# IRIS will be changed to just read this from another completely separate (not generated) connection properties file
		t24Credentials = username: INPUTT|password: 123456|company:

		«entityModel.renderEntities»
	'''

	def renderEntities(EMEntityModel entityModel) '''
		«FOR entity: entityModel.entities»
		«IF !entity.t24Name.nullOrEmpty»
		«entity.renderEntity»
		«ENDIF»
		«ENDFOR»
	'''

	def renderEntity(EMEntity entity) '''

	# ----------------------------------------------------------------------------------------------------
	# Resource : «entity.t24Name»
	# ----------------------------------------------------------------------------------------------------
	«projectName».«entity.name» = name: «entity.t24Name»
	«FOR property: entity.properties»
	«property.renderProperty(entity)»
	«ENDFOR»
	'''
	// support 2 levels of nested properties and additional entry for A Property Class
	def renderProperty(EMProperty property, EMEntity entity) '''
	«IF !property.t24Name.nullOrEmpty»
	«IF property.AAPropertyClass»
	«projectName».«entity.name».«entity.name»_«property.name» = name: «property.t24Name»|entity: «entity.t24Name»«renderSelectionTerms(property)»«renderJoinedTo(property)»«renderPropertyGroupTo(property)»
	«ELSE»
	«projectName».«entity.name».«property.name» = name: «property.t24Name»|entity: «entity.t24Name»«renderSelectionTerms(property)»«renderJoinedTo(property)»«renderPropertyGroupTo(property)»
	«ENDIF»
	«ENDIF»
	«FOR sproperty: property.subProperties»
	«IF !sproperty.t24Name.nullOrEmpty»
	«projectName».«entity.name».«entity.name»_«property.name».«sproperty.name» = name: «sproperty.t24Name»|entity: «entity.t24Name»«renderSelectionTerms(sproperty)»«renderJoinedTo(sproperty)»«renderPropertyGroupTo(sproperty)»
	«ENDIF»
	«FOR sprop: sproperty.subProperties»
	«IF !sprop.t24Name.nullOrEmpty»
	«projectName».«entity.name».«entity.name»_«property.name».«entity.name»_«sproperty.name».«sprop.name» = name: «sprop.t24Name»|entity: «entity.t24Name»«renderSelectionTerms(sprop)»«renderJoinedTo(sprop)»«renderPropertyGroupTo(sprop)»
	«ENDIF»
	«IF property.AAPropertyClass»
	«FOR aaSubprop: sprop.subProperties»
	«IF !aaSubprop.t24Name.nullOrEmpty»
	«projectName».«entity.name».«entity.name»_«property.name».«entity.name»_«sproperty.name».«entity.name»_«sprop.name».«aaSubprop.name» = name: «aaSubprop.t24Name»|entity: «entity.t24Name»«renderSelectionTerms(aaSubprop)»«renderJoinedTo(aaSubprop)»«renderPropertyGroupTo(aaSubprop)»
	«ENDIF»
	«ENDFOR»
	«ENDIF»
	«ENDFOR»
	«ENDFOR»
	'''
	def renderSelectionTerms(EMProperty property)
	'''«IF !property.selectionInfo.empty»|«property.selectionInfo»«ENDIF»'''

	def renderJoinedTo(EMProperty property)
	'''«IF !property.joinedTo.empty»|«property.joinedTo»«ENDIF»'''

	def renderPropertyGroupTo(EMProperty property)
	'''«IF !property.propertyGroup.empty»|«property.propertyGroup»«ENDIF»'''
}