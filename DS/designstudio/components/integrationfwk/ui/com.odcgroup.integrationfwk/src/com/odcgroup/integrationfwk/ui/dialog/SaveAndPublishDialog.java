package com.odcgroup.integrationfwk.ui.dialog;

import java.util.List;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorPart;

import com.odcgroup.integrationfwk.ui.editors.EventsEditor;
import com.odcgroup.integrationfwk.ui.editors.FlowsEditor;
import com.odcgroup.integrationfwk.ui.projects.TWSConsumerProject;
import com.odcgroup.integrationfwk.ui.utils.StringConstants;

/**
 * Responsible for providing the information about unsaved events/flows in
 * integration project and prompt the user for saving the resource before
 * publishing it. In addition to it, it will ask the user whether they always
 * wants to save the unsaved flows/events before publishing it. The project
 * properties will be change as like that henceforth.
 * 
 * @author sbharathraja
 * 
 */
public class SaveAndPublishDialog extends Dialog {

	/**
	 * Responsible for providing the labels to the table viewer which will shown
	 * the unsaved event/flow names.
	 * 
	 * @author sbharathraja
	 * 
	 */
	private class UnSavedEditorsTableLabelProvider extends LabelProvider {
		@Override
		public String getText(Object element) {
			if (element instanceof EventsEditor) {
				return ((EventsEditor) element).getEventName()
						+ StringConstants.EVENT;
			} else if (element instanceof FlowsEditor) {
				return ((FlowsEditor) element).getFlowName()
						+ StringConstants.FLOW;
			} else {
				return "";
			}
		}
	}

	/** collection of dirty integration editors */
	private final List<IEditorPart> dirtyEditors;
	/** select all button */
	private Button buttonSelectAll;
	/** de-select all button */
	private Button buttonDeselectAll;
	/** always save check box button */
	private Button buttonAlwaysSave;
	/** table viewer which will shown the unsaved event/flow names */
	private CheckboxTableViewer checkboxTableViewer;
	/**
	 * instance of {@link TWSConsumerProject} which is under publishing action
	 * currently
	 */
	private final TWSConsumerProject currentProject;

	/**
	 * Create the dialog.
	 * 
	 * @param parentShell
	 * @param dirtyEditors
	 *            - collection of dirty integration editors
	 * @param currentProject
	 *            - project which is under publishing action.
	 */
	public SaveAndPublishDialog(Shell parentShell,
			List<IEditorPart> dirtyEditors, TWSConsumerProject currentProject) {
		super(parentShell);
		setBlockOnOpen(true);
		this.dirtyEditors = dirtyEditors;
		this.currentProject = currentProject;
	}

	/**
	 * Helps to add and listen the ui components.
	 */
	private void addListener() {
		if (checkboxTableViewer == null) {
			return;
		}
		// listen select all button
		if (buttonSelectAll != null) {
			buttonSelectAll.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent event) {
					checkboxTableViewer.setAllChecked(true);
				}
			});
		}
		// listen de-select all button
		if (buttonDeselectAll != null) {
			buttonDeselectAll.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent event) {
					checkboxTableViewer.setAllChecked(false);
				}
			});
		}
	}

	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("Save and Publish");
	}

	/**
	 * Create contents of the button bar.
	 * 
	 * @param parent
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL,
				true);
		createButton(parent, IDialogConstants.CANCEL_ID,
				IDialogConstants.CANCEL_LABEL, false);
	}

	/**
	 * Create contents of the dialog.
	 * 
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		Composite container = (Composite) super.createDialogArea(parent);
		// top label
		Label labelSelectResources = new Label(container, SWT.NONE);
		labelSelectResources.setText("Select resources to save:");
		// composite which going to have the table viewer.
		Composite compositeSelectResources = new Composite(container, SWT.NONE);
		compositeSelectResources.setLayout(new FillLayout(SWT.HORIZONTAL));
		compositeSelectResources.setLayoutData(new GridData(SWT.FILL, SWT.FILL,
				true, true, 1, 1));
		// table viewer initialization
		checkboxTableViewer = CheckboxTableViewer.newCheckList(
				compositeSelectResources, SWT.BORDER);
		checkboxTableViewer.setContentProvider(new ArrayContentProvider());
		checkboxTableViewer
				.setLabelProvider(new UnSavedEditorsTableLabelProvider());
		checkboxTableViewer.setInput(dirtyEditors);
		// as per java project functionality, all available dirty editors is
		// selected by default.
		checkboxTableViewer.setAllChecked(true);
		// composite which going to have customized butttons.
		Composite compositeMiddle = new Composite(container, SWT.NONE);
		compositeMiddle.setLayout(new GridLayout(2, false));
		compositeMiddle.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false,
				false, 1, 1));
		// select all button initialization
		buttonSelectAll = new Button(compositeMiddle, SWT.NONE);
		buttonSelectAll.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, true,
				false, 1, 1));
		buttonSelectAll.setText("Select All");
		// de-select all button initialization
		buttonDeselectAll = new Button(compositeMiddle, SWT.NONE);
		buttonDeselectAll.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER,
				false, false, 1, 1));
		buttonDeselectAll.setText("Deselect All");
		// always save check box button initialization
		buttonAlwaysSave = new Button(compositeMiddle, SWT.CHECK);
		buttonAlwaysSave.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true,
				false, 1, 1));
		buttonAlwaysSave.setText("Always save resources before publishing");
		// dummy label for filling the grid.
		new Label(compositeMiddle, SWT.NONE);
		// going to listen ui components.
		addListener();
		return container;
	}

	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(350, 300);
	}

	/**
	 * Method which helps to perform the action when ok button is pressed.
	 */
	@Override
	protected void okPressed() {
		if (checkboxTableViewer == null || currentProject == null) {
			return;
		}
		if (buttonAlwaysSave != null) {
			currentProject
					.setAlwaySaveResourceBeforePublishing(buttonAlwaysSave
							.getSelection());
		}
		for (Object element : checkboxTableViewer.getCheckedElements()) {
			((IEditorPart) element).doSave(null);
		}
		super.okPressed();
	}

	@Override
	protected void setShellStyle(int newShellStyle) {
		super.setShellStyle(SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL | SWT.MAX
				| SWT.RESIZE);
	}
}
