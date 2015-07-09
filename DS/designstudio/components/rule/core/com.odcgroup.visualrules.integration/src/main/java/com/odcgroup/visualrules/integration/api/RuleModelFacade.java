package com.odcgroup.visualrules.integration.api;

import org.eclipse.core.resources.IProject;

import com.odcgroup.mdf.generation.rules.RuleCategory;

/** 
 * This interface encapsulates the access to the Visual Rules model for a specific project. No plugins apart
 * the visualrules-integration plugin should make direct use of the API provided by Visual Rules.
 * 
 * @author Kai Kreuzer
 * @since 1.30.5
 */
public interface RuleModelFacade {

	/**
	 * returns the current RuleModel name
	 * 
	 * @return the RuleModel name
	 */
	public String getRuleModelName();
	
	/**
	 * returns a string array of domain names for which there are entities
	 * who have rules in the current project
	 * 
	 * @return array of domain names for which rules exist
	 */
	public String[] getDomains(IProject project);
	
	/**
	 * returns a string array of entity names for which there are rules
	 * of a given domain in the current project
	 * 
	 * @param domain the domain to look for entities in
	 * @return array of entity names for which rules exist in this domain
	 * @throws DomainNotFoundException
	 */
	public String[] getEntitiesForDomain(IProject project, String domain)
		throws DomainNotFoundException;
	
	/**
	 * returns a string array of rule names that exist for the given entity
	 * 
	 * @param entity the entity to list the rules for
	 * @param domain the domain that the entity belongs to
	 * @param category the rule category to return results for
	 * @return array of rule names that exist for the given entity
	 * @throws EntityNotFoundException, DomainNotFoundException
	 */
	public String[] getRulesForEntity(IProject project, String entity, String domain, RuleCategory category) 
		throws DomainNotFoundException, EntityNotFoundException;
	
	/**
	 * returns a string array of vrpaths for all rules that are found
	 * for a given domain in the current project
	 * 
	 * @param domain the domain to look for rules in
	 * @return array of vrpaths of rules for this domain
	 * @throws DomainNotFoundException
	 */
	public String[] getRulePathsForDomain(IProject project, String domain)
		throws DomainNotFoundException;

	/**
	 * returns a string array of vrpaths that exist for the given entity
	 * 
	 * @param entity the entity to list the rule paths for
	 * @param domain the domain that the entity belongs to
	 * @return array of vrpaths that exist for the given entity
	 * @throws EntityNotFoundException, DomainNotFoundException
	 */
	public String[] getRulePathsForEntity(IProject project, String entity, String domain) 
		throws DomainNotFoundException, EntityNotFoundException;
}
