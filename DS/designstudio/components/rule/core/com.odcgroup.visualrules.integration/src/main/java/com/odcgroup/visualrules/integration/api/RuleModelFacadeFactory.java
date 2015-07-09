package com.odcgroup.visualrules.integration.api;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IProject;

import com.odcgroup.visualrules.integration.api.impl.RuleModelFacadeImpl;

/**
 * This factory provides instances of RuleModelFacade for a specific project
 * 
 * @author Kai Kreuzer
 *
 */
public class RuleModelFacadeFactory {

	static private Map<IProject, RuleModelFacade> facades = new HashMap<IProject, RuleModelFacade>();
	
	/**
	 * gets a RuleModelFacade instance for a given project
	 * @param project the project for which the instance is requested
	 * @return the facade instance
	 */
	static public RuleModelFacade getInstance(IProject project) throws RuleModelNotFoundException {
		if(!facades.containsKey(project)) {
			RuleModelFacade facade = new RuleModelFacadeImpl(project);
			facades.put(project, facade);
		}
		return facades.get(project);
	}
}
