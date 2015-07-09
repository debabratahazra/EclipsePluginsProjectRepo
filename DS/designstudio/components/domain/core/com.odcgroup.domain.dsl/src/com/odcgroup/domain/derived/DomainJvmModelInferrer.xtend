package com.odcgroup.domain.derived

import com.google.inject.Inject
import com.odcgroup.domain.resource.DomainJvmLinkEncoder
import com.odcgroup.mdf.ecore.MdfBusinessTypeImpl
import com.odcgroup.mdf.ecore.MdfClassImpl
import com.odcgroup.mdf.ecore.MdfDatasetImpl
import com.odcgroup.mdf.ecore.MdfDatasetPropertyImpl
import com.odcgroup.mdf.ecore.MdfEnumValueImpl
import com.odcgroup.mdf.ecore.MdfEnumerationImpl
import com.odcgroup.mdf.ecore.MdfPropertyImpl
import org.eclipse.xtext.common.types.JvmVisibility
import org.eclipse.xtext.naming.IQualifiedNameProvider
import org.eclipse.xtext.xbase.jvmmodel.AbstractModelInferrer
import org.eclipse.xtext.xbase.jvmmodel.IJvmDeclaredTypeAcceptor
import org.eclipse.xtext.xbase.jvmmodel.JvmTypesBuilder
import org.eclipse.xtext.xtype.XtypeFactory

import com.odcgroup.workbench.core.helper.FeatureSwitches

@SuppressWarnings("restriction")
class DomainJvmModelInferrer extends AbstractModelInferrer {

	@Inject extension JvmTypesBuilder
	@Inject extension DomainJvmLinkEncoder domainJvmLinkEncoder
	@Inject extension IQualifiedNameProvider

	// Because ELang for DS EL XSP Java Gen. (which is what this is all about & for)
	// is actually always only based on MdfDataset in DS TAP, and never (directly) MdfClass
	// this may not be really needed? But as Dataset properties refer to MdfClass properties
	// it probably is?  Leaving it as-is for now..
	def dispatch infer(MdfClassImpl mdfClass, extension IJvmDeclaredTypeAcceptor acceptor, boolean prelinkingPhase) {
		if (!FeatureSwitches.domainInferJvmTypes.get)
			return;
		mdfClass.toClass(mdfClass.fullyQualifiedName).accept.initializeLater [
			documentation = mdfClass.documentation
			switch baseClass : mdfClass.baseClass {
				MdfClassImpl:
					superTypes += mdfClass.createTypeRef(baseClass)
			}
			for (property : mdfClass.properties.filter(MdfPropertyImpl)) {
				if (property.basicGetType != null && !property.name.empty) {
					members += property.toField(property.name, mdfClass.createTypeRef(property.basicGetType)) [
						visibility = JvmVisibility.PUBLIC
						documentation = property.documentation
					]
				}
			}
		]
	}

	def dispatch infer(MdfDatasetImpl mdfDataset, extension IJvmDeclaredTypeAcceptor acceptor, boolean prelinkingPhase) {
		if (!FeatureSwitches.domainInferJvmTypes.get)
			return;
		mdfDataset.toClass(mdfDataset.fullyQualifiedName).accept.initializeLater [
			documentation = mdfDataset.documentation
			for (property : mdfDataset.properties.filter(MdfDatasetPropertyImpl)) {
				val jvmTypeReference = XtypeFactory.eINSTANCE.createXComputedTypeReference
				jvmTypeReference.typeProvider = new DomainJvmDatasetPropertyTypeReferenceProvider(domainJvmLinkEncoder, property)
				if (!property.name.empty) {
					members += property.toField(property.name, jvmTypeReference) [
						visibility = JvmVisibility.PUBLIC
						documentation = property.documentation
					]
				}
			}
		]
	}

	def dispatch infer(MdfEnumerationImpl mdfEnumeration, extension IJvmDeclaredTypeAcceptor acceptor, boolean prelinkingPhase) {
		if (!FeatureSwitches.domainInferJvmTypes.get)
			return;
		mdfEnumeration.toEnumerationType(mdfEnumeration.fullyQualifiedName.toString, null).accept.initializeLater [
			static = true
			documentation = mdfEnumeration.documentation
			for (value : mdfEnumeration.values.filter(MdfEnumValueImpl)) {
				if (!value.name.empty) {
					members += value.toEnumerationLiteral(value.name) [
						static = true
						visibility = JvmVisibility.PUBLIC
						documentation = value.documentation
					]
				}
			}
		]
	}

	def dispatch infer(MdfBusinessTypeImpl mdfBusinessType, extension IJvmDeclaredTypeAcceptor acceptor, boolean prelinkingPhase) {
		if (!FeatureSwitches.domainInferJvmTypes.get)
			return;
		mdfBusinessType.toClass(mdfBusinessType.fullyQualifiedName).accept
	}

}
