package com.odcgroup.t24.version.serializer;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.odcgroup.t24.version.services.VersionDSLGrammarAccess;
import com.odcgroup.t24.version.versionDSL.DealSlip;
import com.odcgroup.t24.version.versionDSL.Default;
import com.odcgroup.t24.version.versionDSL.Field;
import com.odcgroup.t24.version.versionDSL.JBCRoutine;
import com.odcgroup.t24.version.versionDSL.JavaRoutine;
import com.odcgroup.t24.version.versionDSL.ValueOrAtRoutine;
import com.odcgroup.t24.version.versionDSL.Version;
import com.odcgroup.t24.version.versionDSL.VersionDSLPackage;
import com.odcgroup.translation.serializer.TranslationDslSemanticSequencer;
import com.odcgroup.translation.translationDsl.LocalTranslation;
import com.odcgroup.translation.translationDsl.LocalTranslations;
import com.odcgroup.translation.translationDsl.TranslationDslPackage;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.serializer.acceptor.ISemanticSequenceAcceptor;
import org.eclipse.xtext.serializer.acceptor.SequenceFeeder;
import org.eclipse.xtext.serializer.diagnostic.ISemanticSequencerDiagnosticProvider;
import org.eclipse.xtext.serializer.diagnostic.ISerializationDiagnostic.Acceptor;
import org.eclipse.xtext.serializer.sequencer.GenericSequencer;
import org.eclipse.xtext.serializer.sequencer.ISemanticNodeProvider.INodesForEObjectProvider;
import org.eclipse.xtext.serializer.sequencer.ISemanticSequencer;
import org.eclipse.xtext.serializer.sequencer.ITransientValueService;
import org.eclipse.xtext.serializer.sequencer.ITransientValueService.ValueTransient;

@SuppressWarnings("all")
public class VersionDSLSemanticSequencer extends TranslationDslSemanticSequencer {

	@Inject
	private VersionDSLGrammarAccess grammarAccess;
	
	public void createSequence(EObject context, EObject semanticObject) {
		if(semanticObject.eClass().getEPackage() == TranslationDslPackage.eINSTANCE) switch(semanticObject.eClass().getClassifierID()) {
			case TranslationDslPackage.LOCAL_TRANSLATION:
				if(context == grammarAccess.getLocalTranslationRule()) {
					sequence_LocalTranslation(context, (LocalTranslation) semanticObject); 
					return; 
				}
				else break;
			case TranslationDslPackage.LOCAL_TRANSLATIONS:
				if(context == grammarAccess.getLocalTranslationsRule() ||
				   context == grammarAccess.getTranslationsRule()) {
					sequence_LocalTranslations(context, (LocalTranslations) semanticObject); 
					return; 
				}
				else break;
			}
		else if(semanticObject.eClass().getEPackage() == VersionDSLPackage.eINSTANCE) switch(semanticObject.eClass().getClassifierID()) {
			case VersionDSLPackage.DEAL_SLIP:
				if(context == grammarAccess.getDealSlipRule()) {
					sequence_DealSlip(context, (DealSlip) semanticObject); 
					return; 
				}
				else break;
			case VersionDSLPackage.DEFAULT:
				if(context == grammarAccess.getDefaultRule()) {
					sequence_Default(context, (Default) semanticObject); 
					return; 
				}
				else break;
			case VersionDSLPackage.FIELD:
				if(context == grammarAccess.getFieldRule()) {
					sequence_Field(context, (Field) semanticObject); 
					return; 
				}
				else break;
			case VersionDSLPackage.JBC_ROUTINE:
				if(context == grammarAccess.getAtRoutineRule() ||
				   context == grammarAccess.getJBCRoutineRule() ||
				   context == grammarAccess.getRoutineRule()) {
					sequence_JBCRoutine(context, (JBCRoutine) semanticObject); 
					return; 
				}
				else break;
			case VersionDSLPackage.JAVA_ROUTINE:
				if(context == grammarAccess.getAtRoutineRule() ||
				   context == grammarAccess.getJavaRoutineRule() ||
				   context == grammarAccess.getRoutineRule()) {
					sequence_JavaRoutine(context, (JavaRoutine) semanticObject); 
					return; 
				}
				else break;
			case VersionDSLPackage.VALUE_OR_AT_ROUTINE:
				if(context == grammarAccess.getValueOrAtRoutineRule()) {
					sequence_ValueOrAtRoutine(context, (ValueOrAtRoutine) semanticObject); 
					return; 
				}
				else break;
			case VersionDSLPackage.VERSION:
				if(context == grammarAccess.getVersionRule()) {
					sequence_Version(context, (Version) semanticObject); 
					return; 
				}
				else break;
			}
		if (errorAcceptor != null) errorAcceptor.accept(diagnosticProvider.createInvalidContextOrTypeDiagnostic(semanticObject, context));
	}
	
	/**
	 * Constraint:
	 *     (format=STRING function=DealSlipFormatFunction)
	 */
	protected void sequence_DealSlip(EObject context, DealSlip semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, VersionDSLPackage.Literals.DEAL_SLIP__FORMAT) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, VersionDSLPackage.Literals.DEAL_SLIP__FORMAT));
			if(transientValues.isValueTransient(semanticObject, VersionDSLPackage.Literals.DEAL_SLIP__FUNCTION) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, VersionDSLPackage.Literals.DEAL_SLIP__FUNCTION));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getDealSlipAccess().getFormatSTRINGTerminalRuleCall_1_0(), semanticObject.getFormat());
		feeder.accept(grammarAccess.getDealSlipAccess().getFunctionDealSlipFormatFunctionEnumRuleCall_3_0(), semanticObject.getFunction());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     (mv=INTEGER_OBJECT? sv=INTEGER_OBJECT? defaultIfOldValueEquals=STRING defaultNewValueOrAtRoutine=ValueOrAtRoutine)
	 */
	protected void sequence_Default(EObject context, Default semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (
	 *         name=STRING 
	 *         (
	 *             displayType=DisplayType? 
	 *             inputBehaviour=InputBehaviour? 
	 *             caseConvention=CaseConvention? 
	 *             maxLength=INTEGER_OBJECT? 
	 *             enrichmentLength=INTEGER_OBJECT? 
	 *             expansion=Expansion? 
	 *             rightAdjust=YesNo? 
	 *             enrichment=YesNo? 
	 *             column=INTEGER_OBJECT? 
	 *             row=INTEGER_OBJECT? 
	 *             mandatory=YesNo? 
	 *             rekeyRequired=YesNo? 
	 *             hyperlink=STRING? 
	 *             hotValidate=YesNo? 
	 *             hotField=YesNo? 
	 *             webValidate=YesNo? 
	 *             selectionEnquiry=STRING? 
	 *             enquiryParameter=STRING? 
	 *             popupBehaviour=PopupBehaviour?
	 *         )? 
	 *         (defaults+=Default defaults+=Default*)? 
	 *         (label=Translations? toolTip=Translations?)? 
	 *         (validationRoutines+=Routine validationRoutines+=Routine*)? 
	 *         (attributes+=STRING attributes+=STRING*)? 
	 *         MV=INTEGER_OBJECT? 
	 *         SV=INTEGER_OBJECT?
	 *     )
	 */
	protected void sequence_Field(EObject context, Field semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     name=STRING
	 */
	protected void sequence_JBCRoutine(EObject context, JBCRoutine semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, VersionDSLPackage.Literals.ROUTINE__NAME) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, VersionDSLPackage.Literals.ROUTINE__NAME));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getJBCRoutineAccess().getNameSTRINGTerminalRuleCall_1_0(), semanticObject.getName());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     name=STRING
	 */
	protected void sequence_JavaRoutine(EObject context, JavaRoutine semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, VersionDSLPackage.Literals.ROUTINE__NAME) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, VersionDSLPackage.Literals.ROUTINE__NAME));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getJavaRoutineAccess().getNameSTRINGTerminalRuleCall_1_0(), semanticObject.getName());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     (value=STRING | atRoutine=AtRoutine)
	 */
	protected void sequence_ValueOrAtRoutine(EObject context, ValueOrAtRoutine semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (
	 *         forApplication=[MdfClass|EntityRef] 
	 *         shortName=NID? 
	 *         t24Name=STRING? 
	 *         metamodelVersion=STRING? 
	 *         group=STRING? 
	 *         numberOfAuthorisers=INTEGER_OBJECT? 
	 *         description=Translations? 
	 *         (
	 *             exceptionProcessing=INTEGER_OBJECT? 
	 *             interfaceException=INTEGER_OBJECT? 
	 *             businessDayControl=BusinessDayControl? 
	 *             (keySequence+=STRING keySequence+=STRING*)? 
	 *             otherCompanyAccess=YesNo? 
	 *             autoCompanyChange=YesNo? 
	 *             overrideApproval=YesNo? 
	 *             (dealSlipFormats+=DealSlip dealSlipFormats+=DealSlip*)? 
	 *             dealSlipTrigger=DealSlipTrigger? 
	 *             dealSlipStyleSheet=STRING?
	 *         )? 
	 *         (
	 *             recordsPerPage=STRING? 
	 *             fieldsPerLine=STRING? 
	 *             initialCursorPosition=STRING? 
	 *             browserToolbar=STRING? 
	 *             (languageLocale+=NID languageLocale+=NID*)? 
	 *             header1=Translations? 
	 *             header2=Translations? 
	 *             header=Translations? 
	 *             footer=Translations?
	 *         )? 
	 *         (
	 *             (nextVersion=[Version|VersionRef] nextVersionFunction=Function? (nextTransactionReference='AUTO' | nextTransactionReference=STRING)?)? 
	 *             (associatedVersions+=[Version|VersionRef] associatedVersions+=[Version|VersionRef]*)?
	 *         )? 
	 *         (
	 *             includeVersionControl=YesNo? 
	 *             (authorizationRoutines+=Routine authorizationRoutines+=Routine*)? 
	 *             (authorizationRoutinesAfterCommit+=Routine authorizationRoutinesAfterCommit+=Routine*)? 
	 *             (inputRoutines+=Routine inputRoutines+=Routine*)? 
	 *             (inputRoutinesAfterCommit+=Routine inputRoutinesAfterCommit+=Routine*)? 
	 *             (keyValidationRoutines+=Routine keyValidationRoutines+=Routine*)? 
	 *             (preProcessValidationRoutines+=Routine preProcessValidationRoutines+=Routine*)? 
	 *             (webValidationRoutines+=Routine webValidationRoutines+=Routine*)?
	 *         )? 
	 *         (confirmVersion=[Version|VersionRef]? previewVersion=[Version|VersionRef]? challengeResponse=STRING?)? 
	 *         (attributes+=STRING attributes+=STRING*)? 
	 *         (
	 *             publishWebService=YesNo? 
	 *             webServiceActivity=STRING? 
	 *             webServiceFunction=Function? 
	 *             webServiceDescription=STRING? 
	 *             (webServiceNames+=STRING webServiceNames+=STRING*)?
	 *         )? 
	 *         (generateIFP=YesNo? associatedVersionsPresentationPattern=AssociatedVersionsPresentationPattern? fieldsLayoutPattern=FieldsLayoutPattern?)? 
	 *         fields+=Field*
	 *     )
	 */
	protected void sequence_Version(EObject context, Version semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
}
