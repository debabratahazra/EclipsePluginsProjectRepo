package com.odcgroup.iris.metadata.generation

import com.odcgroup.t24.enquiry.util.EMEntity
import com.odcgroup.t24.enquiry.util.EMEntityModel
import com.odcgroup.t24.enquiry.util.EMProperty
import java.util.HashMap
import org.eclipse.xtend.expression.Variable
import org.eclipse.xtext.generator.IFileSystemAccess
import com.odcgroup.iris.generator.IIRISGenerator

class IRISMetadataConfig implements IIRISGenerator {

	override doGenerate(EMEntityModel entityModel, IFileSystemAccess fsa, HashMap<String,Variable> globalVars) {
		var filename =  globalVars.get("fileName").value.toString
		fsa.generateFile(filename, renderMetadata(entityModel, globalVars))
	}

	def renderMetadata(EMEntityModel entityModel, HashMap<String, Variable> globalVars) '''
		<?xml version="1.0" encoding="UTF-8"?>
		<Metadata ModelName="«entityModel.name»" Version="1.0"
				xmlns="http://iris.temenos.com/metadata.xsd"
				xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
				xsi:schemaLocation="http://iris.temenos.com metadata.xsd">
			«FOR entity: entityModel.entities»
				«entity.renderEntity»
			«ENDFOR»
		</Metadata>
	'''

	def renderEntity(EMEntity entity) '''
		<Entity Name="«entity.name»">
			«IF entity.vocabularyTerms.size > 0»
				«entity.renderTerm»
			«ENDIF»
			«FOR property: entity.properties»
				«property.renderProperty»
			«ENDFOR»
		</Entity>
	'''

	def CharSequence renderProperty(EMProperty property) '''
		«IF property.vocabularyTerms.size > 0 || property.subProperties.size > 0»
			<Property Name="«property.name»">
				«IF property.vocabularyTerms.size > 0»
					«property.renderTerm»
				«ENDIF»
				«IF property.subProperties.size > 0»
					«property.renderComplexProperty»
				«ENDIF»
			</Property>
		«ELSE»
			<Property Name="«property.name»" />
		«ENDIF»
	'''

	def renderComplexProperty(EMProperty property) '''
			«FOR prop: property.subProperties»
				«prop.renderProperty»
			«ENDFOR»
	'''

	def renderTerm(EMProperty property) '''
		«FOR term: property.vocabularyTerms»
			<Term Name="«term.name»">«term.value»</Term>
		«ENDFOR»
	'''

	def renderTerm(EMEntity entity) '''
		«FOR term: entity.vocabularyTerms»
			<Term Name="«term.name»">«term.value»</Term>
		«ENDFOR»
	'''

}