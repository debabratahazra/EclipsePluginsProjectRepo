package com.odcgroup.page.model.symbols.impl;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.ecore.EObject;

import com.odcgroup.page.model.corporate.CorporateDesign;
import com.odcgroup.page.model.corporate.CorporateDesignUtils;
import com.odcgroup.page.model.symbols.SymbolResolver;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.core.repository.OfsResourceHelper;

/**
 * @author atr
 */
public abstract class AbstractSymbolResolver implements SymbolResolver {

	/** The name of this resolver */
	private String name;
	
	/** A short description */
	private String description;
	
	/**
	 * @param eObj
	 * @return IOfsProject given an EObject instance
	 */
	protected IOfsProject getOfsProject(EObject eObj) {
		return OfsResourceHelper.getOfsProject(eObj.eResource());
	}
	
	protected IProject getProject(EObject eObj) {
		return OfsResourceHelper.getProject(eObj.eResource());
	}

	/**
	 * @param ofsProject
	 * @return CorporateDesign given an IOfsProject instance
	 */
	protected final CorporateDesign getCorporateDesign(IOfsProject ofsProject) {
		return CorporateDesignUtils.getCorporateDesign(ofsProject);
	}
	
	/**
	 * @param eObj
	 * @return CorporateDesign given an EObject instance
	 */
	protected final CorporateDesign getCorporateDesign(EObject eObj) {
		return getCorporateDesign(getOfsProject(eObj));
	}
	
	/**
	 * @see com.odcgroup.page.model.symbols.SymbolResolver#getDescription()
	 */
	public final String getDescription() {
		return this.description;
	}

	/**
	 * @param description
	 */
	public final void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @see com.odcgroup.page.model.symbols.SymbolResolver#getName()
	 */
	public final String getName() {
		return this.name;
	}

	/**
	 * @param name
	 */
	public final void setName(String name) {
		this.name = name;
	}

	/**
	 * @param name
	 * @param description
	 */
	protected AbstractSymbolResolver(String name, String description) {
		this.name = name;
		this.description = description;
	}

	/**
	 * 
	 */
	protected AbstractSymbolResolver() {
	}

}
