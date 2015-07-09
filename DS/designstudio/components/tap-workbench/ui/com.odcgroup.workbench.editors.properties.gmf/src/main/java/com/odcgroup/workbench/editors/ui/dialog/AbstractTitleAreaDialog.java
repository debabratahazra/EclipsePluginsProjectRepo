package com.odcgroup.workbench.editors.ui.dialog;

import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.widgets.Shell;

public abstract class AbstractTitleAreaDialog extends TitleAreaDialog {
	
	protected AddCommand command = null;
	protected boolean update = false;
	protected EditingDomain domain;
	protected boolean readOnly = false;
	
	/**
	 * @param parentShell
	 * @param addCommand
	 */
	public AbstractTitleAreaDialog(Shell parentShell) {
		super(parentShell);
	}
	
	/**
	 * @param command
	 */
	public void setCommand(AddCommand command) {
		this.command = command;
		this.domain = command.getDomain();
	}
	
	/**
	 * @param update
	 */
	public void setUpdate(boolean update){
		this.update = update;
	}
	
	/**
	 * @param readOnly
	 */
	public void setReadOnly(boolean readOnly) {
		this.readOnly = readOnly;
	}


}
