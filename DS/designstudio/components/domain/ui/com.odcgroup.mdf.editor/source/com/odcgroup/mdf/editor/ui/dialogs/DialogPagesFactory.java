package com.odcgroup.mdf.editor.ui.dialogs;

import java.util.List;

import com.odcgroup.mdf.metamodel.MdfModelElement;

/**
 * TODO: DOCUMENT ME!
 * 
 * @author <a href="mailto:cvedovini@odyssey-group.com">Claude Vedovini</a>
 * @version 1.0
 */
public interface DialogPagesFactory {
	public static final String EXTENSION_POINT = "com.odcgroup.mdf.editor.dialogpage";

	public void addPages(MdfModelElement model, List pages);
}