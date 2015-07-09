package com.odcgroup.mdf.editor.ui.dialogs;

import org.eclipse.emf.ecore.EObject;

import com.odcgroup.mdf.ecore.MdfAttributeImpl;

/**
 * TODO: Document me!
 *
 * @author vgirishkumar
 *
 */
public class MdfAttributeWithRef extends MdfAttributeImpl {
	
	private EObject refObj = null;

	/**
	 * @return the refObj
	 */
	public EObject getRefObj() {
		return refObj;
	}

	/**
	 * @param refObj the refObj to set
	 */
	public void setRefObj(EObject refObj) {
		this.refObj = refObj;
	}
	
	

}
