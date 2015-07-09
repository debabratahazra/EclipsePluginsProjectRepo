package com.odcgroup.translation.ui.navigator;

import org.eclipse.core.runtime.IAdaptable;

import com.odcgroup.workbench.core.IOfsProject;

/**
 * This class is used to present the Translation node in the navigator view
 * 
 * @author pkk, kkr
 *
 */
public class TranslationNode implements IAdaptable {
	
	protected IOfsProject ofsProject;
	
	/**
	 * @param ofsProject
	 */
	public TranslationNode(IOfsProject ofsProject) {
		this.ofsProject = ofsProject;
	}

	public IOfsProject getOfsProject() {
		return ofsProject;
	}

	public String getName() {
		return "Translations";
	}

	@SuppressWarnings({ "rawtypes" })
	@Override
	public Object getAdapter(Class adapter) {
		if(adapter.equals(IOfsProject.class)) return ofsProject;
		return null;
	}

}
