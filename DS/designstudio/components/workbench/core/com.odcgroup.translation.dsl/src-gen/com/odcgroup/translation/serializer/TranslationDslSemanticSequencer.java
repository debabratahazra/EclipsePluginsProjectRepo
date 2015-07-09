package com.odcgroup.translation.serializer;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.odcgroup.translation.services.TranslationDslGrammarAccess;
import com.odcgroup.translation.translationDsl.LocalTranslation;
import com.odcgroup.translation.translationDsl.LocalTranslations;
import com.odcgroup.translation.translationDsl.TranslationDslPackage;
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
public class TranslationDslSemanticSequencer extends AbstractDelegatingSemanticSequencer {

	@Inject
	private TranslationDslGrammarAccess grammarAccess;
	
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
		if (errorAcceptor != null) errorAcceptor.accept(diagnosticProvider.createInvalidContextOrTypeDiagnostic(semanticObject, context));
	}
	
	/**
	 * Constraint:
	 *     (locale=ID text=STRING)
	 */
	protected void sequence_LocalTranslation(EObject context, LocalTranslation semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, TranslationDslPackage.Literals.LOCAL_TRANSLATION__LOCALE) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, TranslationDslPackage.Literals.LOCAL_TRANSLATION__LOCALE));
			if(transientValues.isValueTransient(semanticObject, TranslationDslPackage.Literals.LOCAL_TRANSLATION__TEXT) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, TranslationDslPackage.Literals.LOCAL_TRANSLATION__TEXT));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getLocalTranslationAccess().getLocaleIDTerminalRuleCall_0_0(), semanticObject.getLocale());
		feeder.accept(grammarAccess.getLocalTranslationAccess().getTextSTRINGTerminalRuleCall_2_0(), semanticObject.getText());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     (translations+=LocalTranslation translations+=LocalTranslation*)
	 */
	protected void sequence_LocalTranslations(EObject context, LocalTranslations semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
}
