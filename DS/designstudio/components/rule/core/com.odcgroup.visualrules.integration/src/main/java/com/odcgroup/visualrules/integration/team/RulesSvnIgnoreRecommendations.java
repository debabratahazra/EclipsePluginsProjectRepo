package com.odcgroup.visualrules.integration.team;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.team.svn.core.extension.options.IIgnoreRecommendations;

import com.odcgroup.visualrules.integration.RulesIntegrationPlugin;
import com.odcgroup.workbench.core.OfsCore;

public class RulesSvnIgnoreRecommendations implements IIgnoreRecommendations {

	public RulesSvnIgnoreRecommendations() {
	}

	/* (non-Javadoc)
	 * @see org.eclipse.team.svn.core.extension.options.IIgnoreRecommendations#isAcceptableNature(org.eclipse.core.resources.IResource)
	 */
	public boolean isAcceptableNature(IResource res) throws CoreException {
		return OfsCore.isOfsProject(res.getProject());
	}

	/* (non-Javadoc)
	 * @see org.eclipse.team.svn.core.extension.options.IIgnoreRecommendations#isIgnoreRecommended(org.eclipse.core.resources.IResource)
	 */
	public boolean isIgnoreRecommended(IResource res) throws CoreException {
		return isOutput(res);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.team.svn.core.extension.options.IIgnoreRecommendations#isOutput(org.eclipse.core.resources.IResource)
	 */
	public boolean isOutput(IResource res) throws CoreException {
		IPath path = res.getLocation();
		if(path.segmentCount()>3 &&
			path.toString().contains(RulesIntegrationPlugin.RULE_FOLDER) &&
			(res.getName().equals(RulesIntegrationPlugin.DOMAIN_PKG_NAME + ".vrpackage") ||
					res.getName().equals(RulesIntegrationPlugin.DOMAIN_PKG_NAME))) {
				return true;
		} else {
			return false;
		}
	}

}
