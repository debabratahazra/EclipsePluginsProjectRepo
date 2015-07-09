package com.odcgroup.mdf.editor.ui.dialogs;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.odcgroup.mdf.editor.ui.WidgetFactory;
import com.odcgroup.mdf.metamodel.MdfModelElement;

/**
 * @author <a href="mailto:cvedovini@odyssey-group.com">Claude Vedovini</a>
 * @version 1.0
 */
public abstract class DialogPage extends org.eclipse.jface.dialogs.DialogPage {
	private DialogPageContainer container = null;
	private boolean isPageComplete = true;
	
	// flag to let know the page is always enabled or not
	private boolean alwaysEnabled = false;
	
	public static int MODE_EDIT = 0;
	public static int MODE_CREATE = 1;
    public static int MODE_EDIT_SIMPLE = 2;
    public static int MODE_READ_ONLY = 3;
	
	private int editMode = MODE_CREATE; 
	private boolean dirty = false;

	/**
	 * Constructor for DialogPage
	 */
	public DialogPage() {
		super();
	}

	/**
	 * Constructor for DialogPage
	 */
	public DialogPage(String title) {
		super(title);
	}

	/**
	 * Constructor for DialogPage
	 */
	public DialogPage(String title, ImageDescriptor image) {
		super(title, image);
	}

	public DialogPageContainer getContainer() {
		return container;
	}
	
	public WidgetFactory getWidgetFactory() {
		return container.getWidgetFactory();
	}

    public void setContainer(DialogPageContainer container) {
		this.container = container;
	}

	public Shell getShell() {
		return (container == null) ? null : container.getShell();
	}
	
	protected boolean isCurrentPage() {
		return (container != null) && (this == container.getCurrentPage());
	}
	
	public boolean isPageComplete() {
		return isPageComplete;
	}
	
	public void setStatus(IStatus status) {
		String message = status.getMessage();
		switch (status.getSeverity()) {
			case IStatus.OK:
			case IStatus.INFO:
				setPageComplete(true);
				setMessage(getDescription(), INFORMATION);
				setErrorMessage(null);
				break;
				
			case IStatus.WARNING:
				setPageComplete(true);
				setMessage(message, WARNING);
				setErrorMessage(null);
				break;

			case IStatus.ERROR:
				setPageComplete(false);
				setMessage(message, ERROR);
				setErrorMessage(message);
				break;
				
			default:
				throw new IllegalArgumentException("Unknown status code");
		}
		
	}
	
	public void setDescription(String description) {
		super.setDescription(description);
		if (isCurrentPage()) {
			container.updateTitleBar();
		}
	}

	public void setErrorMessage(String newMessage) {
		super.setErrorMessage(newMessage);
		if (isCurrentPage()) {
			container.updateMessage();
		}
	}

	public void setImageDescriptor(ImageDescriptor image) {
		super.setImageDescriptor(image);
		if (isCurrentPage()) {
			container.updateTitleBar();
		}
	}

	public void setMessage(String newMessage, int newType) {
		super.setMessage(newMessage, newType);
		if (isCurrentPage()) {
			container.updateMessage();
		}
	}
	/**
	 * Sets whether this page is complete. 
	 * <p>
	 * This information is typically used by the wizard to decide
	 * when it is okay to move on to the next page or finish up.
	 * </p>
	 *
	 * @param complete <code>true</code> if this page is complete, and
	 *   and <code>false</code> otherwise
	 * @see #isPageComplete
	 */
	public void setPageComplete(boolean complete) {
		isPageComplete = complete;
		if (isCurrentPage()) {
			container.updateButtons();
		}
	}

	public abstract void doSave(MdfModelElement element); 
	
	protected void setText(Text field, Object value) {
		setText(field, value, "");
	}
	
	protected void setText(Combo field, Object value) {
		setText(field, value, "");
	}
	
	protected void setText(Text field, Object value, Object def) {
		field.setText(value == null ? def.toString() : value.toString());
	}
	
	protected void setText(Combo field, Object value, Object def) {
		field.setText(value == null ? def.toString() : value.toString());
	}
	
	public int getEditMode() {
		return editMode;
	}

	public void setEditMode(int editMode) {
		this.editMode = editMode;
		if (editMode == MODE_CREATE) {
			setDirty(true);
		}
		if (editMode == MODE_READ_ONLY) {
			getControl().setEnabled(false);
		}
	}

	/**
	 * TODO: DOCUMENT ME!
	 *
	 * @return
	 */
	public boolean isDirty() {
		return dirty;
	}

	/**
	 * TODO: DOCUMENT ME!
	 *
	 * @param dirty
	 */
	public void setDirty(boolean dirty) {
		this.dirty = dirty;

		// OCS-26284
		if (container != null) {
			container.setDirty(dirty);
		}
	}
	
	/** 
	 * @see org.eclipse.jface.dialogs.DialogPage#dispose()
	 * // OCS-26284
	 */
	public void dispose() {
		DialogPageContainer c = container;
		// OCS-27435 - to avoid stack overflow.
		container = null;
		if(c != null) {
			c.dispose();
		}
		super.dispose();
	}

	/**
	 * @return the alwaysEnabled
	 */
	public boolean isAlwaysEnabled() {
		return alwaysEnabled;
	}

	/**
	 * @param alwaysEnabled the alwaysEnabled to set
	 */
	public void setAlwaysEnabled(boolean alwaysEnabled) {
		this.alwaysEnabled = alwaysEnabled;
	}

}
