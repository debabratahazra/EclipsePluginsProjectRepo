package com.odcgroup.visualrules.integration.ui.action.mdf;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.navigator.CommonActionProvider;
import org.eclipse.ui.navigator.ICommonActionExtensionSite;
import org.eclipse.ui.navigator.ICommonMenuConstants;

import com.odcgroup.mdf.generation.rules.RuleCategory;
import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.mdf.metamodel.MdfDataset;


public class MdfRuleActionProvider extends CommonActionProvider {

	/**
	 * 
	 */
	private static final String RULE_EDIT_MENUENTRY = "Edit";
	private RuleFromTemplateAction validationAction;
	private RuleFromTemplateAction completionAction;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.navigator.CommonActionProvider#init(org.eclipse.ui.navigator.ICommonActionExtensionSite)
	 */
	public void init(ICommonActionExtensionSite anActionSite) {
		validationAction = new ValidationRuleAction();
		validationAction.setText(RULE_EDIT_MENUENTRY);
		completionAction = new CompletionRuleAction();
		completionAction.setText(RULE_EDIT_MENUENTRY);
	}

	public void fillContextMenu(IMenuManager menu) {
		IStructuredSelection selection = (IStructuredSelection) getContext()
			.getSelection();
		Object element = selection.getFirstElement();
		if(selection.size()>0 && (element instanceof MdfClass || element instanceof MdfDataset)) {
			IMenuManager submenu = new MenuManager("Rules");
			IMenuManager submenuValidation = new MenuManager(RuleCategory.VALIDATION.toString());
			IMenuManager submenuCompletion = new MenuManager(RuleCategory.COMPLETION.toString());
			submenuValidation.add(validationAction);
			submenuCompletion.add(completionAction);
			submenu.add(submenuValidation);
			submenu.add(submenuCompletion);
			menu.appendToGroup(ICommonMenuConstants.GROUP_EDIT, submenu);
		}
	}

	public void fillActionBars(IActionBars actionBars) {
 
		updateActionBars(); 
		
		IStructuredSelection selection = (IStructuredSelection) getContext()
		.getSelection();

		validationAction.selectionChanged(validationAction, selection);
		completionAction.selectionChanged(completionAction, selection);
	}

}
