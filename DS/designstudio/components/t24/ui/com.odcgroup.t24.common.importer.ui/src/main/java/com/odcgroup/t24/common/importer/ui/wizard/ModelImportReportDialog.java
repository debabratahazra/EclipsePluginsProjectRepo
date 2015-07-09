package com.odcgroup.t24.common.importer.ui.wizard;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Text;

import com.odcgroup.t24.common.importer.IImportModelReport;
import com.odcgroup.t24.common.importer.ui.Messages;

public class ModelImportReportDialog extends TitleAreaDialog {
	
	private IImportModelReport report;
	private String modelName;
	
	private void updateButtons() {
		getButton(IDialogConstants.OK_ID).setEnabled(true);
	}
	
	@Override
	protected void configureShell(Shell shell) {
		super.configureShell(shell);
		shell.setText(NLS.bind(Messages.ModelImportReportDialog_title, this.modelName));
		shell.setSize(700, 550);
	}
	
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL, true);
		updateButtons();
	}
	
	protected Composite createContent(TabFolder folder, String description, String message) {
		Composite composite = new Composite(folder, SWT.NULL);
		final GridLayout layout = new GridLayout();
		layout.marginWidth = 15;
		layout.marginHeight = 10;
		layout.numColumns = 1;	
		composite.setLayout(layout);
		composite.setLayoutData(new GridData(GridData.FILL_BOTH));	

		// description
		Label lbl = new Label(composite, SWT.NULL);
		lbl.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		lbl.setText(description);
		
		// Filler
		new Label(composite, SWT.NULL);

		// message
		Text text = new Text(composite, SWT.MULTI | SWT.BORDER | SWT.WRAP | SWT.V_SCROLL);
		text.setLayoutData(new GridData(GridData.FILL_BOTH));
		text.setEditable(false);
		text.setText(message);
		return composite;
	}

	
	protected Control createDialogArea(Composite parent) {
		
		Composite body = (Composite) super.createDialogArea(parent);

		Composite composite = new Composite(body, SWT.NULL);
		final GridLayout layout = new GridLayout();
		layout.marginWidth = 15;
		layout.marginHeight = 10;
		layout.numColumns = 1;	
		composite.setLayout(layout);
		composite.setLayoutData(new GridData(GridData.FILL_BOTH));	
		
		// Report Summary
		Group summaryGroup = new Group(composite, SWT.SHADOW_ETCHED_IN);
		summaryGroup.setLayout(new GridLayout(1, false));
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		summaryGroup.setLayoutData(gd);
		summaryGroup.setText("Summary");
		
		Text text = new Text(summaryGroup, SWT.MULTI | SWT.WRAP);
		text.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		text.setEditable(false);
		text.setText(report.getSummaryMessage());

		// Tabbed Pane
		final TabFolder tabFolder = new TabFolder(composite, SWT.TOP);
		tabFolder.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		// Error Tab
		TabItem errorTab= new TabItem(tabFolder, SWT.NULL);
	    errorTab.setText("Errors");
	    errorTab.setControl(createContent(tabFolder, 
	    		"The following models have errors",
	    		report.getErrors()));

	    // Success Tab
	    TabItem successTab = new TabItem(tabFolder, SWT.NULL);
	    successTab.setText("Success");
	    successTab.setControl(createContent(tabFolder,
	    		"The following models has been successfully imported",
	    		report.getSuccess()));
	    
	    if (!report.hasErrors()) {
	    	tabFolder.setSelection(1);
	    }
	    
	    tabFolder.addSelectionListener(new SelectionListener() {
	        public void widgetSelected(SelectionEvent e) {
	          //System.out.println("Selected item index = " + tabFolder.getSelectionIndex());
	          //System.out.println("Selected item = " + (tabFolder.getSelection() == null ? "null" : tabFolder.getSelection()[0].toString()));
	        }
	        public void widgetDefaultSelected(SelectionEvent e) {
	          widgetSelected(e);
	        }
	      });

		return composite;
	}
	
	@Override
	protected Button getButton(int id) {
		if (IDialogConstants.CANCEL_ID == id) {
			return null;
		}
		return super.getButton(id);
	}

	@Override
	protected void okPressed() {
		super.okPressed();
	}

	@Override
	public void create() {
		super.create();
		setTitle(NLS.bind(Messages.ModelImportReportDialog_title, this.modelName));
	}
	
	public ModelImportReportDialog(Shell shell, IImportModelReport report, String modelName) {
		super(shell);
		setShellStyle(this.getShellStyle() | SWT.RESIZE);
		this.report = report;
		this.modelName = modelName;
	}
	
	
}
