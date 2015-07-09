package com.odcgroup.workbench.ui.internal.wizards;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.dialogs.DialogPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;

import com.odcgroup.template.TemplateDescriptor;
import com.odcgroup.template.TemplateParameterDescriptor;
import com.odcgroup.workbench.core.template.DSTemplateCreator;
import com.odcgroup.workbench.core.template.SeparatorTemplateDescriptor;
import com.odcgroup.workbench.ui.help.IOfsHelpContextIds;

/**
 * @author yan
 */
public class TemplateSelectionPage extends WizardPage {
	
	private static final String SEPARATOR = ", ";
	public static final String COMBOBOX_ITEM_INDENT = "   ";
	private static final int NAME_COLUMN = 0;
	private static final int VALUE_COLUMN = 1;
	private static final int DESCRIPTION_COLUMN = 2;
	
	private static final int VALUE_COLUMN_MIN_WIDTH = 150;
	private static final int DESCRIPTION_COLUMN_MIN_WIDTH = 150;

	private List<TemplateDescriptor> templateDescriptors;
	
	private TemplateDescriptor selectedTemplateDescriptor;
	
	private Table parametersTable;
	
	/**
	 * @param pageName
	 */
	protected TemplateSelectionPage() {
		super("newTemplateSelectionPage");
		setTitle("Create Template Projects");
		setDescription("Creates new projects based on template");
		setPageComplete(false);
		
		templateDescriptors = DSTemplateCreator.getAvailableTemplates();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	public void createControl(Composite parent) {
		Composite topLevel = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.numColumns = 1;
		layout.verticalSpacing = 12;
		topLevel.setLayout(layout);
		topLevel.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_FILL
				| GridData.HORIZONTAL_ALIGN_FILL));
		topLevel.setFont(parent.getFont());
		String helpConextId = StringUtils.EMPTY ;
		if (Platform.getBundle(IOfsHelpContextIds.TAP_DOC_PLUGIN_ID) != null) {
			helpConextId = "com.odcgroup.workbench.doc.userguide." + IOfsHelpContextIds.NEW_TEMPLATE_WIZARD;
		} else {
			helpConextId = "com.odcgroup.workbench.doc.t24dsuserguide."+ IOfsHelpContextIds.NEW_TEMPLATE_WIZARD;
		}
		PlatformUI.getWorkbench().getHelpSystem().setHelp(parent,helpConextId);

		Label label = new Label(topLevel, SWT.NONE);
		label.setText("Choose a Template :");
		
		final Combo templateCombo = new Combo(topLevel, SWT.DROP_DOWN | SWT.READ_ONLY);
		templateCombo.setLayoutData(new GridData (SWT.FILL, SWT.BEGINNING, true, false));
		List<String> items = new ArrayList<String>();
		for (TemplateDescriptor templateDescriptor : templateDescriptors) {
			if (templateDescriptor instanceof SeparatorTemplateDescriptor) {
				items.add(templateDescriptor.getName());
			} else {
				items.add(COMBOBOX_ITEM_INDENT + templateDescriptor.getName());
			}
		}
		templateCombo.setItems(items.toArray(new String[items.size()]));
		
		final Label descriptionLabel = new Label(topLevel, SWT.WRAP);
		GridData gridData = new GridData (
		        GridData.HORIZONTAL_ALIGN_FILL | GridData.VERTICAL_ALIGN_FILL);
		gridData.grabExcessHorizontalSpace = true;
		descriptionLabel.setLayoutData (gridData);
		
		Label parametersLabel = new Label(topLevel, SWT.NONE);
		parametersLabel.setText("Parameters :");
		
		parametersTable = new Table (topLevel, SWT.MULTI | SWT.BORDER | SWT.FULL_SELECTION);
		parametersTable.setLinesVisible (true);
		parametersTable.setHeaderVisible (true);
		GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
		data.heightHint = 100;
		parametersTable.setLayoutData(data);
		String[] titles = {"Name", "Value", "Description"};
		for (int i=0; i<titles.length; i++) {
			TableColumn column = new TableColumn (parametersTable, SWT.NONE);
			column.setText (titles [i]);
			column.pack ();
		}
		
		SelectionListener selectionListener = new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				selectedTemplateDescriptor = templateDescriptors.get(templateCombo.getSelectionIndex());
				
				// Fill the details text
				String details = selectedTemplateDescriptor.getDescription();
				descriptionLabel.setText(details);

				// Fill the parameters table
				parametersTable.setItemCount(0);
				for (TemplateParameterDescriptor desc : selectedTemplateDescriptor.getParameters()) {
					TableItem item = new TableItem (parametersTable, SWT.NONE);
					item.setText (NAME_COLUMN, desc.getName());
					item.setText (VALUE_COLUMN, desc.getDefaultValue());
					item.setText (DESCRIPTION_COLUMN, desc.getDescription());
					item.setData(desc);
				}

				// Resize the columns according to the contents
				parametersTable.setVisible(false);
				for (int i=0; i<parametersTable.getColumnCount(); i++) {
					parametersTable.getColumn (i).pack ();
				}
				if (parametersTable.getColumn(VALUE_COLUMN).getWidth() < VALUE_COLUMN_MIN_WIDTH) {
					parametersTable.getColumn(VALUE_COLUMN).setWidth(VALUE_COLUMN_MIN_WIDTH);
				}
				if (parametersTable.getColumn(DESCRIPTION_COLUMN).getWidth() < DESCRIPTION_COLUMN_MIN_WIDTH) {
					parametersTable.getColumn(DESCRIPTION_COLUMN).setWidth(DESCRIPTION_COLUMN_MIN_WIDTH);
				}
				parametersTable.setVisible(true);
				
				validateParameters();
			}
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		};
		templateCombo.addSelectionListener(selectionListener);
		
		//The editor must have the same size as the cell and must
		//not be any smaller than 50 pixels.
		final TableEditor editor = new TableEditor(parametersTable);
		editor.horizontalAlignment = SWT.LEFT;
		editor.grabHorizontal = true;
		parametersTable.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				// Clean up any previous editor control
				Control oldEditor = editor.getEditor();
				if (oldEditor != null) oldEditor.dispose();
		
				// Identify the selected row
				TableItem item = (TableItem)e.item;
				if (item == null) return;
		
				// The control that will be the editor must be a child of the Table
				Text newEditor = new Text(parametersTable, SWT.NONE);
				newEditor.setData("id", "cellText");
				newEditor.setText(item.getText(VALUE_COLUMN));
				newEditor.addModifyListener(new ModifyListener() {
					public void modifyText(ModifyEvent me) {
						Text text = (Text)editor.getEditor();
						editor.getItem().setText(VALUE_COLUMN, text.getText());
						validateParameters();
					}
				});
				newEditor.addFocusListener(new FocusListener() {
					public void focusGained(FocusEvent e) {
					}
					public void focusLost(FocusEvent e) {
						// Clean up any previous editor control
						Control oldEditor = editor.getEditor();
						if (oldEditor != null) oldEditor.dispose();
					}
				});
				newEditor.selectAll();
				newEditor.setFocus();
				editor.setEditor(newEditor, item, VALUE_COLUMN);
			}
		});
		
		// Select by default the first template
		if (templateCombo.getItemCount()>0) {
			templateCombo.select(0);
			selectionListener.widgetSelected(null);
		}
		templateCombo.setFocus();

		setControl(topLevel);
	}
	
	public TemplateDescriptor getSelectedTemplateDescriptor() {
		return selectedTemplateDescriptor;
	}
	
	public Map<String, String> getParameters() {
		Map<String, String> params = new HashMap<String, String>();
		for (TableItem item : parametersTable.getItems()) {
			if (StringUtils.isNotEmpty(item.getText(VALUE_COLUMN))) {
				params.put(item.getText(NAME_COLUMN), item.getText(VALUE_COLUMN));
			}
		}
		return params;
	}

	/**
	 * Validate the parameters (i.e. all mandatory parameters are filled in)
	 */
	private void validateParameters() {
		String fieldsFormatError = validateFieldsFormat();
		String mandatoryFieldsMissing = validateMandatoryFields();
		
		String message;
		if (fieldsFormatError.length() != 0) {
			message = "The following field(s) are not valid: " + fieldsFormatError + "\n" +
					"Allowed characters: a-z, A-Z, 0-9, - (forbidden at the start and the end of the value)";
		} else if (mandatoryFieldsMissing.length() != 0) {
			message = "The following field(s) are mandatory: " + mandatoryFieldsMissing;
		} else {
			message = null;
		}
		
		setMessage(message, DialogPage.ERROR);
		setPageComplete(message == null);
	}

	private String validateFieldsFormat() {
		StringBuffer invalidFields = new StringBuffer();
//		Temporarily disabling this
//		for (TableItem item : parametersTable.getItems()) {
//			String value = item.getText(VALUE_COLUMN);
//			if (!StringUtils.containsOnly(value, "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890-") ||
//					value.startsWith("-") || 
//					value.endsWith("-")) {
//				invalidFields.append(item.getText(NAME_COLUMN)); 
//				invalidFields.append(SEPARATOR); 
//			}
//		}
		return StringUtils.removeEnd(invalidFields.toString(), SEPARATOR);
	}

	private String validateMandatoryFields() {
		StringBuffer mandatoryFieldsMissing = new StringBuffer();
		for (TableItem item : parametersTable.getItems()) {
			if (StringUtils.isEmpty(item.getText(VALUE_COLUMN))) {
				mandatoryFieldsMissing.append(item.getText(NAME_COLUMN)); 
				mandatoryFieldsMissing.append(SEPARATOR); 
			}
		}
		return StringUtils.removeEnd(mandatoryFieldsMissing.toString(), SEPARATOR);
	}
	
	@Override
	public boolean isPageComplete() {
		return !(selectedTemplateDescriptor instanceof SeparatorTemplateDescriptor) && 
				super.isPageComplete();
	}

}