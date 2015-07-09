package com.odcgroup.translation.ui.internal.views;

import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.widgets.Shell;

public abstract class BaseTranslationDialog extends TitleAreaDialog {
	
	/**
	 * @return the text entered by the user.
	 */
	public abstract String getText();
	
	
	/**
	 * Constructor
	 */
	public BaseTranslationDialog(Shell parentShell) {
		super(parentShell);
	}


}
