package com.odcgroup.t24.enquiry.properties.descriptors;

import java.util.ArrayList;
import java.util.Arrays;

import org.eclipse.emf.common.ui.celleditor.ExtendedDialogCellEditor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.SWT;
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
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.views.properties.PropertyDescriptor;

import com.odcgroup.t24.enquiry.enquiry.Companies;
import com.odcgroup.t24.enquiry.enquiry.Enquiry;
import com.odcgroup.t24.enquiry.enquiry.EnquiryPackage;

/**
 * TODO: Document me!
 * 
 * @author satishnangi
 * 
 */
public class EnquiryCompaniesPropertyDescriptor extends PropertyDescriptor {

	private Enquiry model;

	/**
	 * @param id
	 * @param displayName
	 */
	public EnquiryCompaniesPropertyDescriptor(EObject model) {
		super(EnquiryPackage.eINSTANCE.getCompanies().getName(), "Companies");
		this.model = (Enquiry) model;
	}

	public CellEditor createPropertyEditor(Composite parent) {
		ExtendedDialogCellEditor dilogCellEditor = new ExtendedDialogCellEditor(parent, new LabelProvider()) {
			@Override
			protected Object openDialogBox(Control cellEditorWindow) {
				CompaniesAllDialog dialog = new CompaniesAllDialog(cellEditorWindow.getShell());
				int i = dialog.open();
				if (i == Dialog.OK) {
					return dialog.getSelectedOptions();
				}
				return null;
			}
		};

		return dilogCellEditor;
	}

	private class CompaniesAllDialog extends Dialog {

		private ArrayList<String> selectedOptions;
		private Button selectAllMultiCheckBox = null;
		private Button removeButton = null;
		private Button addButton = null;
		private List codeList = null;
		Button selectAllBooksCheckBox=null;

		public CompaniesAllDialog(Shell parentShell) {
			super(parentShell);
		}

		@Override
		protected Control createDialogArea(Composite parent) {
			Composite container = (Composite) super.createDialogArea(parent);
			GridLayout gridLayout = new GridLayout(1, false);
			container.setLayout(gridLayout);
			GridData containerdata = new GridData(GridData.FILL_BOTH);
			container.setLayoutData(containerdata);

			Group companiesGroup = new Group(container, SWT.None);
			companiesGroup.setText("Companies");
			GridLayout companiesgridLayout = new GridLayout(2, false);
			companiesGroup.setLayout(companiesgridLayout);
			GridData companiesGroupdata = new GridData(GridData.FILL_BOTH);
			companiesGroup.setLayoutData(companiesGroupdata);
			companiesGroup.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));

			selectAllBooksCheckBox = new Button(companiesGroup, SWT.CHECK);
			GridData allBooksData = new GridData(GridData.FILL_HORIZONTAL);
			allBooksData.horizontalSpan = 2;
			selectAllBooksCheckBox.setLayoutData(allBooksData);
			selectAllBooksCheckBox.setText("Select All Books");
			selectAllBooksCheckBox.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
            Boolean  showAllbooks = model.getShowAllBooks();
            if(showAllbooks!=null){
            	selectAllBooksCheckBox.setSelection(showAllbooks.booleanValue());
            }
			selectAllMultiCheckBox = new Button(companiesGroup, SWT.CHECK);
			GridData multiCheckBoxData = new GridData(GridData.FILL_HORIZONTAL);
			multiCheckBoxData.horizontalSpan = 2;
			selectAllMultiCheckBox.setLayoutData(multiCheckBoxData);
			selectAllMultiCheckBox.setText("Select All Multi-Company");
			selectAllMultiCheckBox.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
            Companies company = model.getCompanies();
            if(company != null){
            	Boolean allboolen = company.getAll();
            	if(allboolen !=null){
            	   selectAllMultiCheckBox.setSelection(allboolen.booleanValue());
            	}
            }
			
			Label codeLable = new Label(companiesGroup, SWT.NONE);
			GridData codeLabelData = new GridData();
			codeLabelData.horizontalSpan = 2;
			codeLable.setLayoutData(codeLabelData);
			codeLable.setText("Code:");

			codeList = new List(companiesGroup, SWT.BORDER);
			GridData codeListData = new GridData();
			codeListData.horizontalSpan = 1;
			codeListData.heightHint = 200;
			codeListData.widthHint = 300;
			codeList.setLayoutData(codeListData);
            if(company !=null && !company.getCode().isEmpty()){
            	codeList.setItems(company.getCode().toArray(new String[0]));
            }
			Composite buttonBar = new Composite(companiesGroup, SWT.NONE);
			buttonBar.setLayout(new GridLayout(1, true));
			buttonBar.setLayoutData(new GridData());

			addButton = new Button(buttonBar, SWT.NONE);
			GridData addButtonData = new GridData(GridData.FILL_HORIZONTAL);
			addButton.setLayoutData(addButtonData);
			addButton.setText("Add");
			addButton.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					CodeDialog codeDialog = new CodeDialog(getShell());
					int i = codeDialog.open();
					if (i == Dialog.OK) {
						if (codeDialog.getResult() != null && (!codeDialog.getResult().isEmpty())) {
							codeList.add(codeDialog.getResult());
							if (!removeButton.isEnabled()) {
								removeButton.setEnabled(true);
							}
						}
					}

				}
			});

			removeButton = new Button(buttonBar, SWT.NONE);
			GridData removeButtonData = new GridData();
			removeButton.setLayoutData(removeButtonData);
			removeButton.setText("Remove");
			removeButton.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					String[] selectedList = codeList.getSelection();
					for (String selected : selectedList) {
						codeList.remove(selected);
					}
					if (codeList.getItemCount() == 0) {
						removeButton.setEnabled(false);
					}
				}
			});
			enableCodeButtons();
			selectAllMultiCheckBox.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					enableCodeButtons();
				}
			});
			return container;
		}

		@Override
		protected void configureShell(Shell newShell) {
			super.configureShell(newShell);
			newShell.setText("File VersionOption Selection Dialog");
		}

		@Override
		protected Point getInitialSize() {
			return new Point(500, 500);
		}

		@Override
		protected void okPressed() {
			selectedOptions = new ArrayList<String>();
			if(selectAllBooksCheckBox.getSelection()){
				selectedOptions.add("All Books");
			}
			if(selectAllMultiCheckBox.getSelection()){
				selectedOptions.add("All Companies");
			}else {
				selectedOptions.addAll(Arrays.asList(codeList.getItems()));
			}
			super.okPressed();
		}

		public ArrayList<String> getSelectedOptions() {
			return selectedOptions;
		}

		private void enableCodeButtons() {
			if (selectAllMultiCheckBox.getSelection()) {
				addButton.setEnabled(false);
				removeButton.setEnabled(false);
			} else {
				addButton.setEnabled(true);
				removeButton.setEnabled(true);
			}
			if (codeList.getItemCount() == 0) {
				removeButton.setEnabled(false);
			}
		}
	}

	private class CodeDialog extends Dialog {
		private String code = null;
		private Text codeText = null;

		/**
		 * @param parentShell
		 */
		protected CodeDialog(Shell parentShell) {
			super(parentShell);
		}

		@Override
		protected Control createDialogArea(Composite parent) {
			Composite container = (Composite) super.createDialogArea(parent);
			GridLayout gridLayout = new GridLayout(1, false);
			container.setLayout(gridLayout);
			GridData containerdata = new GridData(GridData.FILL_BOTH);
			container.setLayoutData(containerdata);
			codeText = new Text(container, SWT.BORDER);
			GridData codeTextData = new GridData(GridData.FILL_HORIZONTAL);
			codeText.setLayoutData(codeTextData);
			return container;
		}

		@Override
		protected Point getInitialSize() {
			return new Point(500, 150);
		}

		@Override
		protected void configureShell(Shell newShell) {
			super.configureShell(newShell);
			newShell.setText("Code Dialog");
		}

		@Override
		protected void okPressed() {
			code = codeText.getText();
			super.okPressed();
		}

		public String getResult() {
			return code;
		}
	}

}
