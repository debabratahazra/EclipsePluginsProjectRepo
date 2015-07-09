package com.odcgroup.page.external.mdf;

import java.util.List;

import com.odcgroup.mdf.editor.ui.dialogs.DialogPagesFactory;
import com.odcgroup.mdf.metamodel.MdfBusinessType;
import com.odcgroup.mdf.metamodel.MdfModelElement;

/**
 * @author pkk
 *
 */
public class MdfBusinessTypePagesFactory implements DialogPagesFactory {
	
	/**
	 * 
	 */
	public MdfBusinessTypePagesFactory() {		
	}

	/**
	 * @see com.odcgroup.mdf.editor.ui.dialogs.DialogPagesFactory#addPages(com.odcgroup.mdf.metamodel.MdfModelElement, java.util.List)
	 */
	@SuppressWarnings("unchecked")
	public void addPages(MdfModelElement model, List pages) {
		if (model instanceof MdfBusinessType) {
			pages.add(new MdfBusinessTypeUIPage((MdfBusinessType) model));
		}
	}

}
