package com.odcgroup.visualrules.integration.ui.datasync;

import org.eclipse.core.resources.IMarker;
import org.eclipse.ui.IMarkerResolution;

import com.odcgroup.visualrules.integration.RulesIntegrationPlugin;

import de.visualrules.integration.IRuleIntegration;
import de.visualrules.integration.IntegrationException;
import de.visualrules.ui.integration.VisualRulesIntegration;

public class DeleteRuleMarkerResolution implements IMarkerResolution {

	public String getDescription() {
		return "Run this quick fix to delete all outdated rules";
	}

	public String getLabel() {
		return "Delete all outdated rules";
	}

	public void run(IMarker marker) {
		String[] rules = RulesIntegrationPlugin.getOutdatedRules(marker.getResource().getProject());
		for(String rule : rules) {
			try {
				IRuleIntegration ruleIntegration = VisualRulesIntegration.getRuleIntegration(marker.getResource().getProject());
				ruleIntegration.deleteRule(rule);
			} catch (IntegrationException e) {
				RulesIntegrationPlugin.getDefault().logError("Cannot delete rule " + rule, e);
			}
		}
	}
}
