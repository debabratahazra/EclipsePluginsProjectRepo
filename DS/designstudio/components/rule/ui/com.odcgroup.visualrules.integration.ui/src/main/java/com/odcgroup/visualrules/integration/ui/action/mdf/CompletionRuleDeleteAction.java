package com.odcgroup.visualrules.integration.ui.action.mdf;

import com.odcgroup.mdf.generation.rules.RuleCategory;
import com.odcgroup.visualrules.integration.RulesIntegrationPlugin;
import com.odcgroup.visualrules.integration.template.Template;


public class CompletionRuleDeleteAction extends AbstractRuleDeleteAction {

	private static Template template = RulesIntegrationPlugin.getTemplate(RuleCategory.COMPLETION);

	public CompletionRuleDeleteAction() {
		super(template);
	}
}
