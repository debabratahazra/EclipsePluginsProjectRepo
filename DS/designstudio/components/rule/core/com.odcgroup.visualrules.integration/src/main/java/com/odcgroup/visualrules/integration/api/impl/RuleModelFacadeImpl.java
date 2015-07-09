package com.odcgroup.visualrules.integration.api.impl;

import java.util.ArrayList;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.util.EList;

import com.odcgroup.mdf.generation.rules.RuleCategory;
import com.odcgroup.visualrules.integration.RulesIntegrationPlugin;
import com.odcgroup.visualrules.integration.RulesIntegrationUtils;
import com.odcgroup.visualrules.integration.api.DomainNotFoundException;
import com.odcgroup.visualrules.integration.api.EntityNotFoundException;
import com.odcgroup.visualrules.integration.api.RuleModelFacade;
import com.odcgroup.visualrules.integration.api.RuleModelNotFoundException;
import com.odcgroup.workbench.core.helper.StringHelper;

import de.visualrules.integration.ICoreRuleIntegration;
import de.visualrules.integration.IDataModelIntegration;
import de.visualrules.integration.IntegrationCore;
import de.visualrules.integration.IntegrationException;
import de.visualrules.integration.model.RulePackage;

public class RuleModelFacadeImpl implements RuleModelFacade {

	protected String ruleModelName;

	protected String ruleModelPath;

	protected RuleModelFacadeImpl() {
		// do nothing
	}
	
	public RuleModelFacadeImpl(IProject project) throws RuleModelNotFoundException {
		ruleModelPath = RulesIntegrationPlugin.getVRBasePath(project);
		if(ruleModelPath==null) throw new RuleModelNotFoundException();
		ruleModelName = StringHelper.withoutExtension(RulesIntegrationUtils.getRulesFile(project).getName());
	}

	public String[] getDomains(IProject project) {

		ArrayList<String> domains = new ArrayList<String>();
		try {
			IDataModelIntegration dataModelIntegration = IntegrationCore.getDataModelIntegration(project);
			RulePackage ruleModelPackage = dataModelIntegration
					.getPackage(ruleModelPath);
			for (RulePackage domainPackage : (EList<RulePackage>) ruleModelPackage.getRulePackages()) {
				String packageName = domainPackage.getName();
				if(!RulesIntegrationPlugin.DOMAIN_PKG_NAME.equals(packageName)) {
					domains.add(domainPackage.getName());
				}
			}
		} catch (IntegrationException e) {
			return new String[0];
		}
		return domains.toArray(new String[domains.size()]);
	}

	public String[] getEntitiesForDomain(IProject project, String domain)
			throws DomainNotFoundException {

		ArrayList<String> entities = new ArrayList<String>();
		try {
			IDataModelIntegration dataModelIntegration = IntegrationCore.getDataModelIntegration(project);
			RulePackage domainPackage = dataModelIntegration
					.getPackage(ruleModelPath + "/" + domain);
			for (RulePackage entityPackage : (EList<RulePackage>) domainPackage.getRulePackages()) {
				entities.add(entityPackage.getName());
			}
		} catch (IntegrationException e) {
			throw new DomainNotFoundException(e);
		}
		return entities.toArray(new String[entities.size()]);
	}

	public String[] getRulesForEntity(IProject project, String entity,
			String domain, RuleCategory category) throws EntityNotFoundException,
			DomainNotFoundException {

		ArrayList<String> rulesList = new ArrayList<String>();
		// Get all rules in entity package
		String[] rules;
		try {
			ICoreRuleIntegration ruleIntegration = IntegrationCore.getCoreRuleIntegration(project);
			rules = ruleIntegration.findRules(ruleModelPath + "/" + domain
					+ "/" + entity + "/" + category);
			if (rules.length > 0) {
				for (int i = 0; i < rules.length; i++) {
					String ruleName = getRuleName(rules[i]);
					if (ruleName != null) {
						rulesList.add(ruleName);
					}
				}
			}
		} catch (IntegrationException e) {
			throw new DomainNotFoundException(e);
		}
		return rulesList.toArray(new String[rulesList.size()]);
	}

	protected String getRuleName(String rulePath) {
		String ruleName = null;
		if (rulePath != null) {
			int lastSlashIndex = rulePath.lastIndexOf('/');
			if ((lastSlashIndex != -1)
					&& (lastSlashIndex < rulePath.length() - 1)) {
				ruleName = rulePath.substring(lastSlashIndex + 1);
			} // No correct rule name ?
		} // Rule path empty ?
		return ruleName;
	}

	public String getRuleModelName() {
		return ruleModelName;
	}

	public String[] getRulePathsForDomain(IProject project, String domain) throws DomainNotFoundException {
		try {
			ICoreRuleIntegration ruleIntegration = IntegrationCore.getCoreRuleIntegration(project);
			return ruleIntegration.findRules(ruleModelPath + "/" + domain);
		} catch (IntegrationException e) {
			throw new DomainNotFoundException(e);
		}
	}

	public String[] getRulePathsForEntity(IProject project, String entity, String domain) throws DomainNotFoundException,
			EntityNotFoundException {
		try {
			ICoreRuleIntegration ruleIntegration = IntegrationCore.getCoreRuleIntegration(project);
			String[] rules = ruleIntegration.findRules(ruleModelPath + "/" + domain
					+ "/" + entity);
			return rules;
		} catch (IntegrationException e) {
			throw new DomainNotFoundException(e);
		}
	}
}
