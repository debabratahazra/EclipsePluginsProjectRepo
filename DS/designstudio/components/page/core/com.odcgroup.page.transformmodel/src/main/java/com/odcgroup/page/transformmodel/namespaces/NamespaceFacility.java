package com.odcgroup.page.transformmodel.namespaces;

import java.util.List;

import org.eclipse.core.runtime.preferences.IEclipsePreferences.IPreferenceChangeListener;

import com.odcgroup.page.transformmodel.Namespace;
import com.odcgroup.page.transformmodel.TransformModel;
import com.odcgroup.workbench.core.preferences.ProjectPreferences;

/**
 * This is the corporate design interface.
 * 
 * @author atr
 * @version 1.1
 */
public interface NamespaceFacility {
	
	/**
	 * @param listener
	 */
	void addPreferenceChangeListener(IPreferenceChangeListener listener);

	/**
	 * @param listener
	 */
	void removePreferenceChangeListener(IPreferenceChangeListener listener);	
	
	/**
	 * @return IEclipsePreferences
	 */
	ProjectPreferences getPropertyStore();
	
	/**
	 * @return a list of user defined Namespaces
	 */
	List<Namespace> getUserDefinedNamespaces();

	/**
	 * @param model
	 */
	void removeUserDefinedNamespaces(TransformModel model);

	/**
	 * @param model
	 */
	void addUserDefinedNamespaces(TransformModel model);
	
	
	/**
	 * @param namespaces
	 */
	void setUserDefinedNamespaces(List<Namespace> namespaces);

}
