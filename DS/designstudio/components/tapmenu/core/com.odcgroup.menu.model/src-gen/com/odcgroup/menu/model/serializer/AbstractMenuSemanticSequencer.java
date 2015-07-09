package com.odcgroup.menu.model.serializer;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.serializer.acceptor.SequenceFeeder;
import org.eclipse.xtext.serializer.sequencer.AbstractDelegatingSemanticSequencer;
import org.eclipse.xtext.serializer.sequencer.ISemanticNodeProvider.INodesForEObjectProvider;
import org.eclipse.xtext.serializer.sequencer.ITransientValueService.ValueTransient;

import com.google.inject.Inject;
import com.odcgroup.menu.model.MenuItem;
import com.odcgroup.menu.model.MenuPackage;
import com.odcgroup.menu.model.MenuRoot;
import com.odcgroup.menu.model.Translation;
import com.odcgroup.menu.model.services.MenuGrammarAccess;

@SuppressWarnings("all")
public abstract class AbstractMenuSemanticSequencer extends AbstractDelegatingSemanticSequencer {

	@Inject
	private MenuGrammarAccess grammarAccess;
	
	public void createSequence(EObject context, EObject semanticObject) {
		if(semanticObject.eClass().getEPackage() == MenuPackage.eINSTANCE) switch(semanticObject.eClass().getClassifierID()) {
			case MenuPackage.MENU_ITEM:
				if(context == grammarAccess.getMenuItemRule()) {
					sequence_MenuItem(context, (MenuItem) semanticObject); 
					return; 
				}
				else break;
			case MenuPackage.MENU_ROOT:
				if(context == grammarAccess.getMenuRootRule()) {
					sequence_MenuRoot(context, (MenuRoot) semanticObject); 
					return; 
				}
				else break;
			case MenuPackage.TRANSLATION:
				if(context == grammarAccess.getTranslationRule()) {
					sequence_Translation(context, (Translation) semanticObject); 
					return; 
				}
				else break;
			}
		if (errorAcceptor != null) errorAcceptor.accept(diagnosticProvider.createInvalidContextOrTypeDiagnostic(semanticObject, context));
	}
	
	/**
	 * Constraint:
	 *     (
	 *         name=String_Value 
	 *         pageflow=String_Value? 
	 *         enabled=Enabled? 
	 *         displayTabs?='true'? 
	 *         securityRole=String_Value? 
	 *         (labels+=Translation labels+=Translation*)? 
	 *         (submenus+=MenuItem submenus+=MenuItem*)? 
	 *         shortcut=String_Value?
	 *     )
	 */
	protected void sequence_MenuItem(EObject context, MenuItem semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (metamodelVersion=String_Value rootItem=MenuItem)
	 */
	protected void sequence_MenuRoot(EObject context, MenuRoot semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, MenuPackage.Literals.MENU_ROOT__METAMODEL_VERSION) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, MenuPackage.Literals.MENU_ROOT__METAMODEL_VERSION));
			if(transientValues.isValueTransient(semanticObject, MenuPackage.Literals.MENU_ROOT__ROOT_ITEM) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, MenuPackage.Literals.MENU_ROOT__ROOT_ITEM));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getMenuRootAccess().getMetamodelVersionString_ValueParserRuleCall_2_0(), semanticObject.getMetamodelVersion());
		feeder.accept(grammarAccess.getMenuRootAccess().getRootItemMenuItemParserRuleCall_3_0(), semanticObject.getRootItem());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     (language=ID message=String_Value)
	 */
	protected void sequence_Translation(EObject context, Translation semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, MenuPackage.Literals.TRANSLATION__LANGUAGE) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, MenuPackage.Literals.TRANSLATION__LANGUAGE));
			if(transientValues.isValueTransient(semanticObject, MenuPackage.Literals.TRANSLATION__MESSAGE) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, MenuPackage.Literals.TRANSLATION__MESSAGE));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getTranslationAccess().getLanguageIDTerminalRuleCall_0_0(), semanticObject.getLanguage());
		feeder.accept(grammarAccess.getTranslationAccess().getMessageString_ValueParserRuleCall_2_0(), semanticObject.getMessage());
		feeder.finish();
	}
}
