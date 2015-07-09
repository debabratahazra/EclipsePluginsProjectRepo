package com.odcgroup.page.ui.properties.table.controls;

import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

/**
 *
 * @author pkk
 * @param <E> 
 *
 */
public abstract class ReferenceDefinitionDialog<E> extends TitleAreaDialog implements SelectionListener, ModifyListener {
	
	/** */
	private E output = null;
		
	/**
	 * @return the output
	 */
	public E getOutput() {
		return output;
	}

	/**
	 * @param output the output to set
	 */
	public void setOutput(E output) {
		this.output = output;
	}

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
	 */
	public ReferenceDefinitionDialog(Shell parentShell) {
		super(parentShell);
	}
	
	/**
	 * (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.TitleAreaDialog#createContents(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected Control createContents(Composite parent) {
		Control control =  super.createContents(parent);
		enableOK(false);
		return control;
	}
		
	/**
	 * @param enable
	 */
	protected void enableOK(boolean enable) {
		getButton(TitleAreaDialog.OK).setEnabled(enable);
	}
	
	/**
	 * (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.Dialog#okPressed()
	 */
	protected final void okPressed() {
		if (validChanges()) {
			setOutput(updateDefinition());
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
	 * @return E
	 */
	protected abstract E updateDefinition();
	
	/**
	 * @return boolean
	 */
	protected abstract boolean validChanges();
	
	
}
