package com.odcgroup.page.serializer;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.odcgroup.page.model.Event;
import com.odcgroup.page.model.Model;
import com.odcgroup.page.model.ModelPackage;
import com.odcgroup.page.model.Parameter;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Snippet;
import com.odcgroup.page.model.Translation;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.services.PageGrammarAccess;
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
public abstract class AbstractPageSemanticSequencer extends AbstractDelegatingSemanticSequencer {

	@Inject
	private PageGrammarAccess grammarAccess;

	public void createSequence(EObject context, EObject semanticObject) {
		if(semanticObject.eClass().getEPackage() == ModelPackage.eINSTANCE) switch(semanticObject.eClass().getClassifierID()) {
			case ModelPackage.EVENT:
				if(context == grammarAccess.getEventRule()) {
					sequence_Event(context, (Event) semanticObject);
					return;
				}
				else break;
			case ModelPackage.MODEL:
				if(context == grammarAccess.getModelRule()) {
					sequence_Model(context, (Model) semanticObject);
					return;
				}
				else break;
			case ModelPackage.PARAMETER:
				if(context == grammarAccess.getParameterRule()) {
					sequence_Parameter(context, (Parameter) semanticObject);
					return;
				}
				else break;
			case ModelPackage.PROPERTY:
				if(context == grammarAccess.getPropertyRule()) {
					sequence_Property(context, (Property) semanticObject);
					return;
				}
				else break;
			case ModelPackage.SNIPPET:
				if(context == grammarAccess.getSnippetRule()) {
					sequence_Snippet(context, (Snippet) semanticObject);
					return;
				}
				else break;
			case ModelPackage.TRANSLATION:
				if(context == grammarAccess.getTranslationRule()) {
					sequence_Translation(context, (Translation) semanticObject);
					return;
				}
				else break;
			case ModelPackage.WIDGET:
				if(context == grammarAccess.getWidgetRule()) {
					sequence_Widget(context, (Widget) semanticObject);
					return;
				}
				else break;
			}
		if (errorAcceptor != null) errorAcceptor.accept(diagnosticProvider.createInvalidContextOrTypeDiagnostic(semanticObject, context));
	}

	/**
	 * Constraint:
	 *     (
	 *         eventName=String_Value
	 *         functionName=String_Value
	 *         nature=EventNature?
	 *         description=String_Value?
	 *         properties+=Property*
	 *         (messages+=Translation messages+=Translation*)?
	 *         ((parameters+=Parameter parameters+=Parameter*)? (snippets+=Snippet snippets+=Snippet*)?)?
	 *     )
	 */
	protected void sequence_Event(EObject context, Event semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}


	/**
	 * Constraint:
	 *     (metamodelVersion=String_Value widget=Widget)
	 */
	protected void sequence_Model(EObject context, Model semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, ModelPackage.Literals.MODEL__WIDGET) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, ModelPackage.Literals.MODEL__WIDGET));
			if(transientValues.isValueTransient(semanticObject, ModelPackage.Literals.MODEL__METAMODEL_VERSION) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, ModelPackage.Literals.MODEL__METAMODEL_VERSION));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getModelAccess().getMetamodelVersionString_ValueParserRuleCall_3_0(), semanticObject.getMetamodelVersion());
		feeder.accept(grammarAccess.getModelAccess().getWidgetWidgetParserRuleCall_4_0(), semanticObject.getWidget());
		feeder.finish();
	}


	/**
	 * Constraint:
	 *     (name=ID value=String_Value userDefined?='ud'?)
	 */
	protected void sequence_Parameter(EObject context, Parameter semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}


	/**
	 * Constraint:
	 *     (libraryName=ID? typeName=ID value=String_Value readonly?='!'? model=[Model|URI]?)
	 */
	protected void sequence_Property(EObject context, Property semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}


	/**
	 * Constraint:
	 *     (typeName=String_Value? properties+=Property* (contents+=Snippet contents+=Snippet*)?)
	 */
	protected void sequence_Snippet(EObject context, Snippet semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}


	/**
	 * Constraint:
	 *     (language=ID message=String_Value)
	 */
	protected void sequence_Translation(EObject context, Translation semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, ModelPackage.Literals.TRANSLATION__LANGUAGE) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, ModelPackage.Literals.TRANSLATION__LANGUAGE));
			if(transientValues.isValueTransient(semanticObject, ModelPackage.Literals.TRANSLATION__MESSAGE) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, ModelPackage.Literals.TRANSLATION__MESSAGE));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getTranslationAccess().getLanguageIDTerminalRuleCall_0_0(), semanticObject.getLanguage());
		feeder.accept(grammarAccess.getTranslationAccess().getMessageString_ValueParserRuleCall_2_0(), semanticObject.getMessage());
		feeder.finish();
	}


	/**
	 * Constraint:
	 *     (
	 *         libraryName=LibraryName?
	 *         typeName=ID
	 *         (labels+=Translation labels+=Translation*)?
	 *         (toolTips+=Translation toolTips+=Translation*)?
	 *         properties+=Property*
	 *         ((events+=Event events+=Event*)? (snippets+=Snippet snippets+=Snippet*)? (contents+=Widget contents+=Widget*)?)?
	 *     )
	 */
	protected void sequence_Widget(EObject context, Widget semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
}
