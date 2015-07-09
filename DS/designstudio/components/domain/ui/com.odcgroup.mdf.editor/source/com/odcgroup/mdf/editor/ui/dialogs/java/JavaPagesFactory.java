package com.odcgroup.mdf.editor.ui.dialogs.java;


import java.util.List;

import com.odcgroup.mdf.editor.ui.dialogs.DialogPagesFactory;
import com.odcgroup.mdf.metamodel.MdfDomain;
import com.odcgroup.mdf.metamodel.MdfModelElement;

/**
 * @author <a href="mailto:cvedovini@odyssey-group.com">Claude Vedovini</a>
 * @version 1.0
 */
public class JavaPagesFactory implements DialogPagesFactory {
	
	public void addPages(MdfModelElement model, List pages) {
		if (model instanceof MdfDomain) {
			pages.add(new JavaExtensionsPage((MdfDomain) model));
//		} else if (model instanceof MdfAttribute) {
//			pages.add(new JavaTypesPage((MdfModelElement) model));
		}
	}
}
