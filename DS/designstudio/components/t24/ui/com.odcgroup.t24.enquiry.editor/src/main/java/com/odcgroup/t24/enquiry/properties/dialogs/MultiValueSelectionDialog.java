package com.odcgroup.t24.enquiry.properties.dialogs;

import java.util.ArrayList;
import java.util.Arrays;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;

/**
 * @author satishnangi
 *
 */
public class MultiValueSelectionDialog extends Dialog {

	private ArrayList<String> selectedOptions;
	private java.util.List<String> selectedValues =null;
	protected Button removeButton = null;
	private Button addButton = null;
	protected List valueList = null;
	private boolean isSequential = false;
	private Button upButton = null;
	private Button downButton = null;
	private String dialogText = "Mutli Value Selection Dialog";
	private String label = "Values:";

	public MultiValueSelectionDialog(Shell parentShell, String dialogText, java.util.List<String> slectedValues, boolean isSequential) {
		super(parentShell);
		this.selectedValues = slectedValues;
		this.isSequential = isSequential;
		if(dialogText != null){
			this.dialogText = dialogText;
		}
	}

	public MultiValueSelectionDialog(Shell parentShell, String dialogText, java.util.List<String> slectedValues, boolean isSequential,String label) {
		super(parentShell);
		this.selectedValues = slectedValues;
		this.isSequential = isSequential;
		if(dialogText != null){
			this.dialogText = dialogText;
		}
		this.label = label;
	}


	@Override
	protected Control createDialogArea(Composite parent) {
		Composite container = (Composite) super.createDialogArea(parent);
		GridLayout gridLayout = new GridLayout(1, false);
		container.setLayout(gridLayout);
		GridData containerdata = new GridData(GridData.FILL_BOTH);
		container.setLayoutData(containerdata);
		container.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));

		
		Group valueGroup = new Group(container, SWT.None);
		valueGroup.setText("Features");
		GridLayout companiesgridLayout = new GridLayout(2, false);
		valueGroup.setLayout(companiesgridLayout);
		GridData companiesGroupdata = new GridData(GridData.FILL_BOTH);
		valueGroup.setLayoutData(companiesGroupdata);
		valueGroup.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));

		Label codeLable = new Label(valueGroup, SWT.NONE);
		GridData codeLabelData = new GridData();
		codeLabelData.horizontalSpan = 2;
		codeLable.setLayoutData(codeLabelData);
		codeLable.setText(label);
		codeLable.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));

		valueList = new List(valueGroup, SWT.BORDER |SWT.SINGLE);
		GridData codeListData = new GridData();
		codeListData.horizontalSpan = 1;
		codeListData.heightHint = 200;
		codeListData.widthHint = 250;
		valueList.setLayoutData(codeListData);
		if(selectedValues != null && !selectedValues.isEmpty()){
			valueList.setItems( (String[]) selectedValues.toArray(new String[0]));
		}
		valueList.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				List list = (List)e.getSource();
				String selection = list.getSelection()[0];
				TextValueDialog codeDialog = new TextValueDialog (getShell(),selection);
				int i = codeDialog.open();
				if (i == Dialog.OK) {
					if (codeDialog.getResult() != null && (!codeDialog.getResult().isEmpty())) {
						valueList.remove(list.getSelectionIndex());
						valueList.add(codeDialog.getResult());
					}
				}

			
			}
			
		});
		
		Composite buttonBar = new Composite(valueGroup, SWT.NONE);
		buttonBar.setLayout(new GridLayout(1, true));
		buttonBar.setLayoutData(new GridData());
		buttonBar.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
		
		addButton = new Button(buttonBar, SWT.NONE);
		GridData addButtonData = new GridData(GridData.FILL_HORIZONTAL);
		addButton.setLayoutData(addButtonData);
		addButton.setText("Add");
		addButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				addButtonSelection();
			}
		});

		removeButton = new Button(buttonBar, SWT.NONE);
		GridData removeButtonData = new GridData();
		removeButton.setLayoutData(removeButtonData);
		removeButton.setText("Remove");
		removeButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String[] selectedList = valueList.getSelection();
				for (String selected : selectedList) {
					valueList.remove(selected);
				}
				if (valueList.getItemCount() == 0) {
					removeButton.setEnabled(false);
				}
			}
		});
		
		if (isSequential) {
			upButton = new Button(buttonBar, SWT.NONE);
			GridData upButtonData = new GridData(GridData.FILL_HORIZONTAL);
			upButton.setLayoutData(upButtonData);
			upButton.setText("Up");
			upButton.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					int currentIndex = valueList.getSelectionIndex();
					if (valueList.getItemCount() != 0 && currentIndex != 0) {
						String item = valueList.getItem(currentIndex);
						valueList.remove(currentIndex);
						valueList.add(item, currentIndex - 1);
					}
				}
			});

			downButton = new Button(buttonBar, SWT.NONE);
			GridData downButtonData = new GridData(GridData.FILL_HORIZONTAL);
			downButton.setLayoutData(downButtonData);
			downButton.setText("Down");
			downButton.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					int currentIndex = valueList.getSelectionIndex();
					if (valueList.getItemCount() != 0 && currentIndex != valueList.getItemCount() - 1) {
						String item = valueList.getItem(currentIndex);
						valueList.remove(currentIndex);
						valueList.add(item, currentIndex + 1);
					}
				}
			});

		}
		enableButtons();
		return container;
	}

	/**
	 * 
	 */
	protected void enableSequenceButton() {
		if(isSequential && valueList.getItemCount() > 1){
			upButton.setEnabled(true);
			downButton.setEnabled(true);
		}
	}

	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText(dialogText);
	}

	@Override
	protected Point getInitialSize() {
		return new Point(450, 400);
	}

	@Override
	protected void okPressed() {
		selectedOptions = new ArrayList<String>();
		selectedOptions.addAll(Arrays.asList(valueList.getItems()));
		super.okPressed();
	}

	public ArrayList<String> getSelectedOptions() {
		return selectedOptions;
	}

	private void enableButtons() {
		if (valueList.getItemCount() == 0) {
			removeButton.setEnabled(false);
			if(isSequential){
				upButton.setEnabled(false);
				downButton.setEnabled(false);
			}
		}
	}


	/**
	 * 
	 */
	protected void addButtonSelection() {
		TextValueDialog codeDialog = new TextValueDialog(getShell());
		int i = codeDialog.open();
		if (i == Dialog.OK) {
			if (codeDialog.getResult() != null && (!codeDialog.getResult().isEmpty())) {
				valueList.add(codeDialog.getResult());
				if (!removeButton.isEnabled()) {
					removeButton.setEnabled(true);
				}
				enableSequenceButton();
			}
		}
	}
}