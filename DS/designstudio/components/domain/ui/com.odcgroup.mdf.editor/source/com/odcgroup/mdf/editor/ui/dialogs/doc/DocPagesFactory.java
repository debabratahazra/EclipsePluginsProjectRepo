package com.odcgroup.mdf.editor.ui.dialogs.doc;


import java.util.List;

import com.odcgroup.mdf.editor.ui.dialogs.DialogPagesFactory;
import com.odcgroup.mdf.metamodel.MdfModelElement;

/**
 * TODO: DOCUMENT ME!
 * 
 * @author <a href="mailto:cvedovini@odyssey-group.com">Claude Vedovini</a>
 * @version 1.0
 */
public class DocPagesFactory implements DialogPagesFactory {
	
	public void addPages(MdfModelElement model, List pages) {
		pages.add(new MdfDocPage(model));			
	}
}
