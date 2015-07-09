package com.odcgroup.page.ui.properties.table.dialog;

import org.eclipse.gef.commands.CommandStack;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

import com.odcgroup.page.ui.command.BaseCommand;

/**
 *
 * @author pkk & scn
 *
 */
public abstract class TableTransformDialog extends TitleAreaDialog implements SelectionListener, ModifyListener {
	
	/** command stack */
	private CommandStack commandStack;
	
	/** dialog in edit mode	 */
	private boolean editMode = false;
	
	/**
	 * @return the editMode
	 */
	public boolean isEditMode() {
		return editMode;
	}

	/**
	 * @param editMode the editMode to set
	 */
	public void setEditMode(boolean editMode) {
		this.editMode = editMode;
	}

	/**
	 * @param parentShell
	 * @param commandStack 
	 */
	public TableTransformDialog(Shell parentShell, CommandStack commandStack) {
		super(parentShell);
		this.commandStack = commandStack;
	}
	
	/**
	 * @return CommandStack
	 */
	protected CommandStack getCommandStack() {
		return this.commandStack;
	}
	
	/**
	 * (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.TitleAreaDialog#createContents(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected Control createContents(Composite parent) {
		Control control =  super.createContents(parent);
		if (!isEditMode())
			enableOK(false);
		return control;
	}
		
	/**
	 * @param enable
	 */
	protected void enableOK(boolean enable) {
		getButton(TitleAreaDialog.OK).setEnabled(enable);
	}
	
	protected void enableTemplate(boolean enable) {
	}
	
	
	/**
	 * (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.Dialog#okPressed()
	 */
	protected void okPressed() {
		if (validChanges()) {
			BaseCommand cmd = getCommand();
			if (cmd != null && cmd.canExecute()) {
				getCommandStack().execute(cmd);
			}
			super.okPressed();
		}
	}

	/**
	 * (non-Javadoc)
	 * @see org.eclipse.swt.events.SelectionListener#widgetDefaultSelected(org.eclipse.swt.events.SelectionEvent)
	 */
	@Override
	public void widgetDefaultSelected(SelectionEvent event) {
		
	}

	/**
	 * (non-Javadoc)
	 * @see org.eclipse.swt.events.SelectionListener#widgetSelected(org.eclipse.swt.events.SelectionEvent)
	 */
	@Override
	public void widgetSelected(SelectionEvent event) {
		if (validChanges()) {
			enableOK(true);
		} else {
			enableOK(false);
		}
	} 
	
	/**
	 * (non-Javadoc)
	 * @see org.eclipse.swt.events.ModifyListener#modifyText(org.eclipse.swt.events.ModifyEvent)
	 */
	public void modifyText(ModifyEvent event) {
		if (validChanges()) {
			enableOK(true);
		} else {
			enableOK(false);
		}		
	}
	
	/**
	 * @return boolean
	 */
	protected abstract boolean validChanges();
	
	/**
	 * @return BaseCommand
	 */
	protected abstract BaseCommand getCommand();
	
}
