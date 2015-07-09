package com.odcgroup.page.model.corporate.internal;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ProjectScope;
import org.eclipse.core.runtime.preferences.IEclipsePreferences.IPreferenceChangeListener;

import com.odcgroup.page.model.corporate.CorporateImages;
import com.odcgroup.workbench.core.IOfsProject;

/**
 * This is the base class for all CorporateImages implementations
 *
 * @author atr
 */
abstract class AbstractCorporateImages implements CorporateImages {
	
	/** */
	private IProject project;
	
	/** */
	private String qualifier;

	/** */
	private ProjectScope scope;
	
	/**
	 * @return qualifier
	 */
	protected final String getQualifier() {
		return this.qualifier;
	}
	
	/**
	 * @param ofsProject
	 * @param qualifier
	 */
	protected AbstractCorporateImages(IOfsProject ofsProject, String qualifier) {
		this.project = ofsProject.getProject();
		this.qualifier = qualifier;
		this.scope = new ProjectScope(this.project);

	}
	
	/**
	 * @return ProjectScope
	 */
	public final ProjectScope getProjectScope() {
		return this.scope;
	}
	
	/**
	 * @see com.odcgroup.page.model.corporate.CorporateImages#addPreferenceChangeListener(org.eclipse.core.runtime.preferences.IEclipsePreferences.IPreferenceChangeListener)
	 */
	public void addPreferenceChangeListener(IPreferenceChangeListener listener) {
		getProjectScope().getNode(getQualifier()).addPreferenceChangeListener(listener);
	}

	/**
	 * @see com.odcgroup.page.model.corporate.CorporateImages#removePreferenceChangeListener(org.eclipse.core.runtime.preferences.IEclipsePreferences.IPreferenceChangeListener)
	 */
	public void removePreferenceChangeListener(IPreferenceChangeListener listener) {
		getProjectScope().getNode(getQualifier()).removePreferenceChangeListener(listener);
	}	
	
	/**
	 * Dispose this instance
	 */
	abstract void dispose();	

}
