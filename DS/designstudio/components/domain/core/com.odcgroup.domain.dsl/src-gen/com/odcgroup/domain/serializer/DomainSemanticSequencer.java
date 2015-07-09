package com.odcgroup.domain.serializer;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.odcgroup.domain.services.DomainGrammarAccess;
import com.odcgroup.mdf.ecore.MdfPackage;
import com.odcgroup.mdf.metamodel.MdfAnnotation;
import com.odcgroup.mdf.metamodel.MdfAnnotationProperty;
import com.odcgroup.mdf.metamodel.MdfAssociation;
import com.odcgroup.mdf.metamodel.MdfAttribute;
import com.odcgroup.mdf.metamodel.MdfBusinessType;
import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.mdf.metamodel.MdfDataset;
import com.odcgroup.mdf.metamodel.MdfDatasetDerivedProperty;
import com.odcgroup.mdf.metamodel.MdfDatasetMappedProperty;
import com.odcgroup.mdf.metamodel.MdfDomain;
import com.odcgroup.mdf.metamodel.MdfEnumValue;
import com.odcgroup.mdf.metamodel.MdfEnumeration;
import com.odcgroup.mdf.metamodel.MdfPrimitive;
import com.odcgroup.mdf.metamodel.MdfReverseAssociation;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.serializer.acceptor.ISemanticSequenceAcceptor;
import org.eclipse.xtext.serializer.diagnostic.ISemanticSequencerDiagnosticProvider;
import org.eclipse.xtext.serializer.diagnostic.ISerializationDiagnostic.Acceptor;
import org.eclipse.xtext.serializer.sequencer.AbstractDelegatingSemanticSequencer;
import org.eclipse.xtext.serializer.sequencer.GenericSequencer;
import org.eclipse.xtext.serializer.sequencer.ISemanticSequencer;
import org.eclipse.xtext.serializer.sequencer.ITransientValueService;

@SuppressWarnings("all")
public class DomainSemanticSequencer extends AbstractDelegatingSemanticSequencer {

	@Inject
	private DomainGrammarAccess grammarAccess;
	
	public void createSequence(EObject context, EObject semanticObject) {
		if(semanticObject.eClass().getEPackage() == MdfPackage.eINSTANCE) switch(semanticObject.eClass().getClassifierID()) {
			case MdfPackage.MDF_ANNOTATION:
				if(context == grammarAccess.getMdfAnnotationRule()) {
					sequence_MdfAnnotation(context, (MdfAnnotation) semanticObject); 
					return; 
				}
				else break;
			case MdfPackage.MDF_ANNOTATION_PROPERTY:
				if(context == grammarAccess.getMdfAnnotationPropertyRule()) {
					sequence_MdfAnnotationProperty(context, (MdfAnnotationProperty) semanticObject); 
					return; 
				}
				else break;
			case MdfPackage.MDF_ASSOCIATION:
				if(context == grammarAccess.getMdfAssociationRule() ||
				   context == grammarAccess.getMdfPropertyRule()) {
					sequence_MdfAssociation(context, (MdfAssociation) semanticObject); 
					return; 
				}
				else break;
			case MdfPackage.MDF_ATTRIBUTE:
				if(context == grammarAccess.getMdfAttributeRule() ||
				   context == grammarAccess.getMdfPropertyRule()) {
					sequence_MdfAttribute(context, (MdfAttribute) semanticObject); 
					return; 
				}
				else break;
			case MdfPackage.MDF_BUSINESS_TYPE:
				if(context == grammarAccess.getMdfBusinessTypeRule() ||
				   context == grammarAccess.getMdfEntityRule() ||
				   context == grammarAccess.getMdfPrimitiveRule()) {
					sequence_MdfBusinessType(context, (MdfBusinessType) semanticObject); 
					return; 
				}
				else break;
			case MdfPackage.MDF_CLASS:
				if(context == grammarAccess.getMdfClassRule() ||
				   context == grammarAccess.getMdfEntityRule()) {
					sequence_MdfClass(context, (MdfClass) semanticObject); 
					return; 
				}
				else break;
			case MdfPackage.MDF_DATASET:
				if(context == grammarAccess.getMdfDatasetRule() ||
				   context == grammarAccess.getMdfEntityRule()) {
					sequence_MdfDataset(context, (MdfDataset) semanticObject); 
					return; 
				}
				else break;
			case MdfPackage.MDF_DATASET_DERIVED_PROPERTY:
				if(context == grammarAccess.getMdfDatasetDerivedPropertyRule() ||
				   context == grammarAccess.getMdfDatasetPropertyRule()) {
					sequence_MdfDatasetDerivedProperty(context, (MdfDatasetDerivedProperty) semanticObject); 
					return; 
				}
				else break;
			case MdfPackage.MDF_DATASET_MAPPED_PROPERTY:
				if(context == grammarAccess.getMdfDatasetMappedPropertyRule() ||
				   context == grammarAccess.getMdfDatasetPropertyRule()) {
					sequence_MdfDatasetMappedProperty(context, (MdfDatasetMappedProperty) semanticObject); 
					return; 
				}
				else break;
			case MdfPackage.MDF_DOMAIN:
				if(context == grammarAccess.getMdfDomainRule()) {
					sequence_MdfDomain(context, (MdfDomain) semanticObject); 
					return; 
				}
				else break;
			case MdfPackage.MDF_ENUM_VALUE:
				if(context == grammarAccess.getMdfEnumValueRule()) {
					sequence_MdfEnumValue(context, (MdfEnumValue) semanticObject); 
					return; 
				}
				else break;
			case MdfPackage.MDF_ENUMERATION:
				if(context == grammarAccess.getMdfEntityRule() ||
				   context == grammarAccess.getMdfEnumerationRule() ||
				   context == grammarAccess.getMdfPrimitiveRule()) {
					sequence_MdfEnumeration(context, (MdfEnumeration) semanticObject); 
					return; 
				}
				else break;
			case MdfPackage.MDF_PRIMITIVE:
				if(context == grammarAccess.getMdfEntityRule() ||
				   context == grammarAccess.getMdfPrimitiveRule() ||
				   context == grammarAccess.getMdfPrimitive_ImplRule()) {
					sequence_MdfPrimitive_Impl(context, (MdfPrimitive) semanticObject); 
					return; 
				}
				else break;
			case MdfPackage.MDF_REVERSE_ASSOCIATION:
				if(context == grammarAccess.getMdfPropertyRule() ||
				   context == grammarAccess.getMdfReverseAssociationRule()) {
					sequence_MdfReverseAssociation(context, (MdfReverseAssociation) semanticObject); 
					return; 
				}
				else break;
			}
		if (errorAcceptor != null) errorAcceptor.accept(diagnosticProvider.createInvalidContextOrTypeDiagnostic(semanticObject, context));
	}
	
	/**
	 * Constraint:
	 *     (cDATA?='cDATA'? name=String_Value value=String_Value?)
	 */
	protected void sequence_MdfAnnotationProperty(EObject context, MdfAnnotationProperty semanticObject) {
		genericSequencer.createSequence(context, (EObject)semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (namespace=Namespace name=ID (properties+=MdfAnnotationProperty properties+=MdfAnnotationProperty*)?)
	 */
	protected void sequence_MdfAnnotation(EObject context, MdfAnnotation semanticObject) {
		genericSequencer.createSequence(context, (EObject)semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (
	 *         documentation=Documentation? 
	 *         name=String_Value 
	 *         containment=MdfContainment? 
	 *         type=[MdfClass|Ref]? 
	 *         multiplicity=MdfMultiplicity? 
	 *         businessKey?='BK'? 
	 *         primaryKey?='PK'? 
	 *         required?='required'? 
	 *         reverse=MdfReverseAssociation? 
	 *         annotations+=MdfAnnotation*
	 *     )
	 */
	protected void sequence_MdfAssociation(EObject context, MdfAssociation semanticObject) {
		genericSequencer.createSequence(context, (EObject)semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (
	 *         documentation=Documentation? 
	 *         name=String_Value 
	 *         type=[MdfPrimitive|Ref]? 
	 *         multiplicity=MdfMultiplicity? 
	 *         businessKey?='BK'? 
	 *         primaryKey?='PK'? 
	 *         required?='required'? 
	 *         default=String_Value? 
	 *         annotations+=MdfAnnotation*
	 *     )
	 */
	protected void sequence_MdfAttribute(EObject context, MdfAttribute semanticObject) {
		genericSequencer.createSequence(context, (EObject)semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (documentation=Documentation? name=String_Value type=[MdfPrimitive|Ref]? annotations+=MdfAnnotation*)
	 */
	protected void sequence_MdfBusinessType(EObject context, MdfBusinessType semanticObject) {
		genericSequencer.createSequence(context, (EObject)semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (
	 *         documentation=Documentation? 
	 *         abstract?='abstract'? 
	 *         name=String_Value 
	 *         baseClass=[MdfClass|Ref]? 
	 *         annotations+=MdfAnnotation* 
	 *         properties+=MdfProperty*
	 *     )
	 */
	protected void sequence_MdfClass(EObject context, MdfClass semanticObject) {
		genericSequencer.createSequence(context, (EObject)semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (
	 *         documentation=Documentation? 
	 *         name=String_Value 
	 *         type=[MdfEntity|Ref]? 
	 *         default=String_Value? 
	 *         multiplicity=MdfMultiplicity? 
	 *         annotations+=MdfAnnotation*
	 *     )
	 */
	protected void sequence_MdfDatasetDerivedProperty(EObject context, MdfDatasetDerivedProperty semanticObject) {
		genericSequencer.createSequence(context, (EObject)semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (
	 *         documentation=Documentation? 
	 *         name=String_Value 
	 *         unique=UniqueBoolean? 
	 *         singleValued?='singleValued'? 
	 *         path=String_Value? 
	 *         linkDataset=[MdfDataset|Ref]? 
	 *         annotations+=MdfAnnotation*
	 *     )
	 */
	protected void sequence_MdfDatasetMappedProperty(EObject context, MdfDatasetMappedProperty semanticObject) {
		genericSequencer.createSequence(context, (EObject)semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (
	 *         documentation=Documentation? 
	 *         name=String_Value 
	 *         baseClass=[MdfClass|Ref]? 
	 *         linked?='default'? 
	 *         sync?='synchronized'? 
	 *         annotations+=MdfAnnotation* 
	 *         properties+=MdfDatasetProperty*
	 *     )
	 */
	protected void sequence_MdfDataset(EObject context, MdfDataset semanticObject) {
		genericSequencer.createSequence(context, (EObject)semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (
	 *         documentation=Documentation? 
	 *         name=ID 
	 *         annotations+=MdfAnnotation* 
	 *         namespace=String_Value 
	 *         metamodelVersion=String_Value 
	 *         classes+=MdfClass* 
	 *         datasets+=MdfDataset* 
	 *         businessTypes+=MdfBusinessType* 
	 *         enumerations+=MdfEnumeration*
	 *     )
	 */
	protected void sequence_MdfDomain(EObject context, MdfDomain semanticObject) {
		genericSequencer.createSequence(context, (EObject)semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (documentation=Documentation? name=String_Value value=String_Value annotations+=MdfAnnotation*)
	 */
	protected void sequence_MdfEnumValue(EObject context, MdfEnumValue semanticObject) {
		genericSequencer.createSequence(context, (EObject)semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (
	 *         documentation=Documentation? 
	 *         name=String_Value 
	 *         type=[MdfPrimitive|Ref]? 
	 *         acceptNullValue?='acceptNullValue'? 
	 *         annotations+=MdfAnnotation* 
	 *         values+=MdfEnumValue*
	 *     )
	 */
	protected void sequence_MdfEnumeration(EObject context, MdfEnumeration semanticObject) {
		genericSequencer.createSequence(context, (EObject)semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     name=ID
	 */
	protected void sequence_MdfPrimitive_Impl(EObject context, MdfPrimitive semanticObject) {
		genericSequencer.createSequence(context, (EObject)semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (
	 *         documentation=Documentation? 
	 *         name=String_Value 
	 *         reversedAssociationType=[MdfClass|Ref] 
	 *         reversedAssociation=[MdfAssociation|ID] 
	 *         multiplicity=MdfMultiplicity? 
	 *         businessKey?='BK'? 
	 *         primaryKey?='PK'? 
	 *         required?='required'? 
	 *         annotations+=MdfAnnotation*
	 *     )
	 */
	protected void sequence_MdfReverseAssociation(EObject context, MdfReverseAssociation semanticObject) {
		genericSequencer.createSequence(context, (EObject)semanticObject);
	}
}
