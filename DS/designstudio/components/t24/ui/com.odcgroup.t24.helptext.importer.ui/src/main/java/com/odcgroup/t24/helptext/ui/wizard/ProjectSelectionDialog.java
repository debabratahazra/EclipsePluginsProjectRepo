package com.odcgroup.t24.helptext.ui.wizard;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.ElementListSelectionDialog;

public class ProjectSelectionDialog extends ElementListSelectionDialog{

	
	protected Control createContents(Composite parent) {
		return super.createContents(parent);
	}

	protected ProjectSelectionDialog(Shell parentShell,LabelProvider provider) {
		super(parentShell , provider);
		
	}

}
