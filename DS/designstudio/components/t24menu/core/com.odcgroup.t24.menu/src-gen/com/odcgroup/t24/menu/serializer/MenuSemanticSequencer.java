package com.odcgroup.t24.menu.serializer;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.odcgroup.t24.menu.menu.MenuItem;
import com.odcgroup.t24.menu.menu.MenuPackage;
import com.odcgroup.t24.menu.menu.MenuRoot;
import com.odcgroup.t24.menu.menu.Translation;
import com.odcgroup.t24.menu.services.MenuGrammarAccess;
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
public class MenuSemanticSequencer extends AbstractDelegatingSemanticSequencer {

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
	 *         name=VALUE 
	 *         enabled=Enabled? 
	 *         displayTabs?='true'? 
	 *         securityRole=STRING? 
	 *         shortcut=STRING? 
	 *         (labels+=Translation labels+=Translation*)? 
	 *         (
	 *             version=[Version|VALUE] | 
	 *             enquiry=[Enquiry|VALUE] | 
	 *             compositeScreen=[CompositeScreen|VALUE] | 
	 *             includedMenu=[MenuRoot|VALUE] | 
	 *             application=[MdfClass|VALUE]
	 *         )? 
	 *         parameters=STRING? 
	 *         (submenus+=MenuItem submenus+=MenuItem*)?
	 *     )
	 */
	protected void sequence_MenuItem(EObject context, MenuItem semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (metamodelVersion=STRING rootItem=MenuItem)
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
		feeder.accept(grammarAccess.getMenuRootAccess().getMetamodelVersionSTRINGTerminalRuleCall_2_0(), semanticObject.getMetamodelVersion());
		feeder.accept(grammarAccess.getMenuRootAccess().getRootItemMenuItemParserRuleCall_3_0(), semanticObject.getRootItem());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     (language=VALUE message=STRING)
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
		feeder.accept(grammarAccess.getTranslationAccess().getLanguageVALUETerminalRuleCall_0_0(), semanticObject.getLanguage());
		feeder.accept(grammarAccess.getTranslationAccess().getMessageSTRINGTerminalRuleCall_2_0(), semanticObject.getMessage());
		feeder.finish();
	}
}
