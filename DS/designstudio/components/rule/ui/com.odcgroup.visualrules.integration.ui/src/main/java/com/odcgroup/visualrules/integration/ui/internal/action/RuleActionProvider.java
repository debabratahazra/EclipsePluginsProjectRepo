package com.odcgroup.visualrules.integration.ui.internal.action;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.ui.navigator.CommonActionProvider;
import org.eclipse.ui.navigator.ICommonActionExtensionSite;

import com.odcgroup.visualrules.integration.RulesIntegrationPlugin;
import com.odcgroup.visualrules.integration.template.Template;

public class RuleActionProvider extends CommonActionProvider {

	private List<NewRuleFromTemplateWizardAction> newRuleFromTemplateWizardActions = new ArrayList<NewRuleFromTemplateWizardAction>();

	public RuleActionProvider() {
	}

	public void init(ICommonActionExtensionSite aSite) {
		for(Template template : RulesIntegrationPlugin.getRegisteredTemplates()) {		
			newRuleFromTemplateWizardActions.add(
					new NewRuleFromTemplateWizardAction(template));
		}
	}

	public void fillContextMenu(IMenuManager menu) {
		IMenuManager submenu = new MenuManager("Rules");
		for(NewRuleFromTemplateWizardAction action : newRuleFromTemplateWizardActions) {
			if(action.isEnabled()) submenu.add(action);
		}
		if(!submenu.isEmpty()) {
			menu.appendToGroup("group.new", submenu);
		}
	}

}
