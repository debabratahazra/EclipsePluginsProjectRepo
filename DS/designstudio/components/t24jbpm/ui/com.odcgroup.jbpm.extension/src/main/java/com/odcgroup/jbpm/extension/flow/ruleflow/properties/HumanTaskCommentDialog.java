package com.odcgroup.jbpm.extension.flow.ruleflow.properties;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.drools.eclipse.flow.ruleflow.core.HumanTaskNodeWrapper;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.fieldassist.AutoCompleteField;
import org.eclipse.jface.fieldassist.ComboContentAdapter;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.xtext.resource.XtextResourceSet;

import com.odcgroup.jbpm.extension.Activator;
import com.odcgroup.t24.version.versionDSL.Field;
import com.odcgroup.t24.version.versionDSL.Version;
import com.odcgroup.workbench.core.xtext.XtextResourceSetProviderDS;

/**
 * @author phanikumark
 * @author gasampong DS-8037 & DS-8035, added all code relating to enqVarsTable
 * 
 */
public class HumanTaskCommentDialog extends Dialog implements MouseListener, FocusListener {

	private String title;
	private String value;
	private String enqVarsValue;
	private Combo versionCbx;
	private Combo modeCbx;
	private Combo recordCbx;
	//private Text enqVarsTbx;
	private String[] values;
	private Table table;
    private Table enqVarsTable;	
	private Button removeButton;
	private Button addButton;
	private Button enqVarsRemoveButton;
	private Button enqVarsAddButton;			
	private Combo valueCbx;
	private Text enqVarsKeyText;	
	private Combo enqVarsValueCombo;	
	private CCombo combo;
	private TableEditor editor;
	private TableEditor ceditor;
	private TableEditor enqVarsKeyEditor;	
	private TableEditor enqVarsValueEditor;	
	private HumanTaskNodeWrapper wrapper;	
	
	private String currentVersion;

	private static Map<String, String> MODES = new LinkedHashMap<String, String>();
	
	static {
		MODES.put("I", "INPUT");
		MODES.put("A", "AUTHORISE");
		//MODES.put("V", "VALIDATE");		
	}
	
	private static Map<String, IResource> VERSION_MAP = new LinkedHashMap<String, IResource>();

	/**
	 * @param parentShell
	 * @param title
	 */
	protected HumanTaskCommentDialog(Shell parentShell, String title,HumanTaskNodeWrapper wrapper) {
		super(parentShell);
		this.title = title;
		setShellStyle(getShellStyle() | SWT.RESIZE);
		VERSION_MAP.putAll(VersionModelUtil.loadVersionsFromActiveProjects());
		this.wrapper = wrapper; 
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.window.Window#configureShell(org.eclipse.swt.widgets
	 * .Shell)
	 */
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText(title);
	}

	@Override
	protected Control createDialogArea(Composite parent) {

		Composite comp = (Composite) super.createDialogArea(parent);
		GridLayout gridLayout = new GridLayout(1, false);
		comp.setLayout(gridLayout);
		GridData gridData = new GridData(GridData.FILL_BOTH);
		comp.setLayoutData(gridData);

		Group body = new Group(comp, SWT.SHADOW_ETCHED_IN);
		gridLayout = new GridLayout(1, false);
		body.setLayout(gridLayout);
		gridData = new GridData(GridData.FILL_BOTH);
		body.setLayoutData(gridData);

		Composite eBody = new Composite(body, SWT.FILL);
		gridLayout = new GridLayout(2, false);
		eBody.setLayout(gridLayout);
		gridData = new GridData(GridData.FILL_VERTICAL);
		gridData.verticalAlignment = SWT.TOP;
		eBody.setLayoutData(gridData);

		Label vlabel = new Label(eBody, SWT.LEFT);
		vlabel.setText("Version:");
		versionCbx = new Combo(eBody, SWT.None);

		Set<String> keys = VERSION_MAP.keySet();
		List<String> keylist = new ArrayList<String>();
		keylist.addAll(keys);
		Collections.sort(keylist);
		versionCbx.setItems(keylist.toArray(new String[] {}));
		new AutoCompleteField(versionCbx, new ComboContentAdapter(), versionCbx.getItems());		
		versionCbx.addModifyListener(new ModifyListener() {
			
			@Override
			public void modifyText(ModifyEvent e) {
				String version = versionCbx.getText();
				if (!VERSION_MAP.containsKey(version)) {
					return;
				}
				if (currentVersion != null && !currentVersion.equals(version)) {
					boolean confirm = MessageDialog
							.openConfirm(
									getShell(),
									"New T24 Version selected",
									"A new T24 version is selected, " +
									"which invalidates all the current field mappings. " +
									"\nAre you sure you want to continue with the new version?");
					if (confirm) {
						currentVersion = version;
						//table.clearAll();
					} else {
						select(currentVersion, versionCbx);
					}
				} else {
					currentVersion = version;
				}
				
			}
		});

		Label modeLbl = new Label(eBody, SWT.LEFT);
		modeLbl.setText("Mode:");
		modeCbx = new Combo(eBody, SWT.READ_ONLY);
		gridData = new GridData();
		gridData.widthHint = 150;
		modeCbx.setLayoutData(gridData);
		modeCbx.setItems(MODES.values().toArray(new String[] {}));

		Label recordLbl = new Label(eBody, SWT.LEFT);
		recordLbl.setText("Record ID:");
		recordCbx = new Combo(eBody, SWT.NONE);
		gridData = new GridData();
		gridData.widthHint = 150;
		recordCbx.setLayoutData(gridData);
		List<String> rItems = new ArrayList<String>();
		rItems.add("F3");
		rItems.addAll(JbpmDialogHelper.getProcessVariables(wrapper,true));
		recordCbx.setItems(rItems.toArray(new String[]{}));

		createFieldMappingsTable(body);
		
		createEnqVarsFieldMappingsTable(body);

		createEnqVarsTextBox(body);

		initializeControls();
		
		updateEnqVarsFieldMappingsToTable();		

		return comp;
	}

	/**
	 * @param body
	 */
	private void createFieldMappingsTable(Composite body) {
		Group composite = createFieldMappingGroup(body);
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;		
		composite.setLayout(layout);

		table = new Table(composite, SWT.SINGLE | SWT.BORDER);
		
		GridData gd = new GridData();
		gd.verticalSpan = 3;
		gd.grabExcessHorizontalSpace = true;
		gd.grabExcessVerticalSpace = true;
		gd.verticalAlignment = GridData.FILL;
		gd.horizontalAlignment = GridData.FILL;
		table.setLayoutData(gd);
		table.addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent e) {
				removeButton.setEnabled(table.getSelectionIndex() != -1);
			}

			public void widgetSelected(SelectionEvent e) {
				removeButton.setEnabled(table.getSelectionIndex() != -1);
			}
		});
		table.addMouseListener(this);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		TableColumn variableNameColumn = new TableColumn(table, SWT.LEFT);
		variableNameColumn.setText("Name");
		variableNameColumn.setWidth(150);
		TableColumn parameterNameColumn = new TableColumn(table, SWT.LEFT);
		parameterNameColumn.setText("Value");
		parameterNameColumn.setWidth(225);

		ceditor = new TableEditor(table);
		combo = new CCombo(table, SWT.NONE);
		combo.setVisible(false);
		ceditor.minimumWidth = combo.getSize().x;
		ceditor.horizontalAlignment = SWT.LEFT;
		ceditor.grabHorizontal = true;
		
		editor = new TableEditor(table);
		valueCbx = new Combo(table,SWT.NONE);
		valueCbx.setVisible(false);
		valueCbx.setText("");
		editor.minimumWidth = valueCbx.getSize().x;
		editor.horizontalAlignment = SWT.LEFT;
		editor.grabHorizontal = true;

		addButton = new Button(composite, SWT.PUSH);
		addButton.setText("Add");
		addButton.setFont(JFaceResources.getDialogFont());
		addButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				addButtonPressed();
			}
		});
		
		gd = new GridData();
		gd.horizontalAlignment = SWT.FILL;
		addButton.setLayoutData(gd);		
		
		removeButton = new Button(composite, SWT.PUSH);
		removeButton.setText("Remove");
		removeButton.setFont(JFaceResources.getDialogFont());
		removeButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				removeButtonPressed();
			}
		});
		gd = new GridData();
		removeButton.setLayoutData(gd);
		removeButton.setEnabled(false);
	}
	
	/**
	 * @param body
	 */
	private void createEnqVarsFieldMappingsTable(Composite body) {
		Group composite = createEnqVarsFieldMappingGroup(body);
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		composite.setLayout(layout);

		enqVarsTable = new Table(composite, SWT.SINGLE | SWT.BORDER);
		GridData gd = new GridData();
		gd.verticalSpan = 3;
		gd.grabExcessHorizontalSpace = true;
		gd.grabExcessVerticalSpace = true;
		gd.verticalAlignment = GridData.FILL;
		gd.horizontalAlignment = GridData.FILL;
		enqVarsTable.setLayoutData(gd);
		enqVarsTable.addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent e) {
				enqVarsRemoveButton.setEnabled(enqVarsTable.getSelectionIndex() != -1);
			}

			public void widgetSelected(SelectionEvent e) {
				enqVarsRemoveButton.setEnabled(enqVarsTable.getSelectionIndex() != -1);
			}
		});
		enqVarsTable.addMouseListener(this);				
		enqVarsTable.setHeaderVisible(true);
		enqVarsTable.setLinesVisible(true);
		TableColumn variableNameColumn = new TableColumn(enqVarsTable, SWT.LEFT);
		variableNameColumn.setText("Key");
		variableNameColumn.setWidth(150);
		TableColumn parameterNameColumn = new TableColumn(enqVarsTable, SWT.LEFT);
		parameterNameColumn.setText("Value");
		parameterNameColumn.setWidth(225);

		enqVarsKeyEditor = new TableEditor(enqVarsTable);
		enqVarsKeyText = new Text(enqVarsTable, SWT.NORMAL | SWT.BORDER);
		enqVarsKeyText.setVisible(false);
		enqVarsKeyText.setText("");
		enqVarsKeyEditor.minimumWidth = enqVarsKeyText.getSize().x;
		enqVarsKeyEditor.horizontalAlignment = SWT.LEFT;
		enqVarsKeyEditor.grabHorizontal = true;
		
		enqVarsValueEditor = new TableEditor(enqVarsTable);
		enqVarsValueCombo = new Combo(enqVarsTable, SWT.NONE);
		enqVarsValueCombo.setVisible(false);
		enqVarsValueCombo.setText("");
		enqVarsValueEditor.minimumWidth = enqVarsValueCombo.getSize().x;
		enqVarsValueEditor.horizontalAlignment = SWT.LEFT;
		enqVarsValueEditor.grabHorizontal = true;

		enqVarsAddButton = new Button(composite, SWT.PUSH);
		enqVarsAddButton.setText("Add");
		enqVarsAddButton.setFont(JFaceResources.getDialogFont());
		enqVarsAddButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				enqVarsAddButtonPressed();
			}
		});
		
		gd = new GridData();
		gd.horizontalAlignment = SWT.FILL;		
		enqVarsAddButton.setLayoutData(gd);		
						
		enqVarsRemoveButton = new Button(composite, SWT.PUSH);
		enqVarsRemoveButton.setText("Remove");
		enqVarsRemoveButton.setFont(JFaceResources.getDialogFont());
		enqVarsRemoveButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				enqVarsRemoveButtonPressed();
			}
		});
		gd = new GridData();
		gd.horizontalAlignment = SWT.FILL;		
		enqVarsRemoveButton.setLayoutData(gd);
		enqVarsRemoveButton.setEnabled(false);
	}	
	
	private void createEnqVarsTextBox(Composite body){
		
		Composite eBody = new Composite(body, SWT.FILL);
		GridLayout gridLayout = new GridLayout(1, false);
		eBody.setLayout(gridLayout);
		GridData gridData = new GridData(GridData.FILL_VERTICAL);
		gridData.verticalAlignment = SWT.TOP;
		eBody.setLayoutData(gridData);		
		
		/*Label enqVarsLbl = new Label(eBody, SWT.LEFT);
		enqVarsLbl.setText("Enquiry Variables Field Mapping:");
		enqVarsTbx = new Text(eBody, SWT.READ_ONLY);
		gridData = new GridData();
		gridData.widthHint = 650;
		enqVarsTbx.setLayoutData(gridData);
		enqVarsTbx.setText(enqVarsValue);*/
	}
	
	private void updateFieldMappingsToTable() {
		Map<String, String> fieldmappings = new LinkedHashMap<String, String>();
		if (values.length > 3) {
			fieldmappings = JbpmDialogHelper.getFieldMappings(values);
            for (Map.Entry<String, String> entry: fieldmappings.entrySet()) {
                TableItem item = new TableItem(table, SWT.NONE);
                item.setText(new String[] { entry.getKey(),
                    entry.getValue() == null ? "" : entry.getValue()} );
            }
		}
    }
	
	private void updateEnqVarsFieldMappingsToTable() {	
		LinkedHashMap<String,String> enqVarsFieldMappigns = JbpmDialogHelper.getEnqVarsFieldMappings(enqVarsValue);
        for (Map.Entry<String, String> entry: enqVarsFieldMappigns.entrySet()) {
            TableItem item = new TableItem(enqVarsTable, SWT.NONE);
            item.setText(0, entry.getKey());
            item.setText(1, entry.getValue());            	
        }	
    }	
		
	/**
	 * @return
	 */
	private String getMappingStringFromTable() {
		StringBuilder sb = new StringBuilder();
		TableItem[] items = table.getItems();
		int i = 0;
		for (TableItem tableItem : items) {
			i++;
			sb.append(tableItem.getText(0));
			sb.append("=");
			sb.append(tableItem.getText(1));
			if (i < items.length) {
				sb.append(" ");
			}
		}
		return sb.toString();
	}
	
	/**
	 * @return
	 */
	protected String getMappingsStringFromEnqVarsTable() {										
		StringBuffer enqVars = new StringBuffer();

		TableItem[] items = enqVarsTable.getItems();
		int length = items.length;
		
		if(items.length>0){
			enqVars.append("ENQ_VARS={");				
					
			for (int i = 0; i < length; i++) {
				enqVars.append(items[i].getText(0));
				enqVars.append("=");
				enqVars.append(items[i].getText(1));
				if(i<length-1){
					enqVars.append("^");
				}
			}
		
			enqVars.append("}");			
			return enqVars.toString();
		}	
	return "";
    }		

    private void removeButtonPressed() {
        int i = table.getSelectionIndex();
        if (i == -1) {
            return;
        }
        table.remove(i);
        removeButton.setEnabled(table.getItemCount() == 0);
    }
    
    /**
     * Make this method 'public static' for JUnit test case
     */
    public static List<String> getSelectedVersionFields(String version) {
		List<String> fnames = new ArrayList<String>();
		if (VERSION_MAP.containsKey(version)) {
			IResource mresource = VERSION_MAP.get(version);
			URI uri = URI.createPlatformResourceURI(mresource.getFullPath().toString(), true);
			XtextResourceSet rs = XtextResourceSetProviderDS.newXtextResourceSet(XtextResourceSet.class, mresource.getProject());
			Resource resource = rs.getResource(uri, true);
			EObject eobj = resource.getContents().get(0);
			if (eobj instanceof Version) {
				Version ver = (Version) eobj;
				fnames.addAll(getFieldsFromVersion(ver));
				
				// Add Associated versions Field. DS-8034
				EList<Version> associatedVersions = ver.getAssociatedVersions();
				for (Iterator<Version> iterator = associatedVersions.iterator(); iterator.hasNext();) {
					Version associatedVersion = (Version) iterator.next();
					fnames.addAll(getFieldsFromVersion(associatedVersion));
				}
			}
		}
		return fnames;
    }
    
    /**
	 * Used for Junit test case
	 */
	public static void setVERSION_MAP(Map<String, IResource> vERSION_MAP) {
		VERSION_MAP = vERSION_MAP;
	}

	private static List<String> getFieldsFromVersion(Version version) {
    	List<String> fieldNames = new ArrayList<String>();
    	List<Field> fields = version.getFields();
		for (Field field : fields) {
			fieldNames.add(field.getName().replace("_", "."));
		}
		return fieldNames;
    }

	private void addButtonPressed() {
		List<String> fnames = getSelectedVersionFields(versionCbx.getText());
		VersionFieldMappingDialog dialog = new VersionFieldMappingDialog(
				getShell(), "Field Mapping", fnames, JbpmDialogHelper.getProcessVariables(wrapper,true),
				table,enqVarsTable);
		int result = dialog.open();
		if (result == Dialog.OK) {
			TableItem item = dialog.getValue();
			table.setSelection(item);
		}
	}
	
	private void enqVarsAddButtonPressed() {
		VersionEnqVarsFieldMappingDialog dialog = new VersionEnqVarsFieldMappingDialog(
				getShell(), "Enquiry Variables Field Mapping",
				JbpmDialogHelper.getProcessVariables(wrapper,true),
				JbpmDialogHelper.getTaskAssignedGroupId(wrapper),enqVarsTable);
		int result = dialog.open();
		if (result == Dialog.OK) {
			TableItem item = dialog.getValue();
			enqVarsTable.setSelection(item);
		}
	}	
		
    private void enqVarsRemoveButtonPressed() {
        int i = enqVarsTable.getSelectionIndex();
        if (i == -1) {
            return;
        }
        enqVarsTable.remove(i);
        enqVarsRemoveButton.setEnabled(enqVarsTable.getItemCount() == 0);
    }

	private void initializeControls() {
		if (values.length >= 3) {
			// version name
			currentVersion = getDSVersionName(values[0]);
			select(currentVersion, versionCbx);
			
			// mode
			select(getModeValue(values[1]), modeCbx);
			// record
			String record = values[2];
			if (record.equals("F3")) {
				select(record, recordCbx);
			} else {
				recordCbx.setText(record);
			}	
			// field mappings
			updateFieldMappingsToTable();
		} else {
			modeCbx.select(0);
			recordCbx.select(0);
		}
	}

	/**
	 * @param key
	 * @return
	 */
	private String getModeValue(String key) {
		return MODES.get(key);
	}

	/**
	 * @param value
	 * @return
	 */
	private String getModeKey(String value) {
		Set<Entry<String, String>> set = MODES.entrySet();
		for (Entry<String, String> entry : set) {
			if (value.equals(entry.getValue())) {
				return entry.getKey();
			}
		}
		return null;
	}

	/**
	 * @param value
	 * @return
	 */
	private String getDSVersionName(String value) {
		return value.replace(".", "_");
	}

	/**
	 * @param body
	 * @return
	 */
	private Group createFieldMappingGroup(Composite body) {
		// create a composite with standard margins and spacing
		Group composite = new Group(body, SWT.SHADOW_ETCHED_IN);
		GridLayout layout = new GridLayout();
		layout.marginHeight = convertVerticalDLUsToPixels(IDialogConstants.VERTICAL_MARGIN);
		layout.marginWidth = convertHorizontalDLUsToPixels(IDialogConstants.HORIZONTAL_MARGIN);
		layout.verticalSpacing = convertVerticalDLUsToPixels(IDialogConstants.VERTICAL_SPACING);
		layout.horizontalSpacing = convertHorizontalDLUsToPixels(IDialogConstants.HORIZONTAL_SPACING);
		composite.setLayout(layout);
		composite.setLayoutData(new GridData(GridData.FILL_BOTH));
		composite.setText("Field Mappings");
		applyDialogFont(composite);
		return composite;
	}
	
	private Group createEnqVarsFieldMappingGroup(Composite body) {
		// create a composite with standard margins and spacing
		Group composite = new Group(body, SWT.SHADOW_ETCHED_IN);
		GridLayout layout = new GridLayout();
		layout.marginHeight = convertVerticalDLUsToPixels(IDialogConstants.VERTICAL_MARGIN);
		layout.marginWidth = convertHorizontalDLUsToPixels(IDialogConstants.HORIZONTAL_MARGIN);
		layout.verticalSpacing = convertVerticalDLUsToPixels(IDialogConstants.VERTICAL_SPACING);
		layout.horizontalSpacing = convertHorizontalDLUsToPixels(IDialogConstants.HORIZONTAL_SPACING);
		composite.setLayout(layout);
		composite.setLayoutData(new GridData(GridData.FILL_BOTH));
		composite.setText("Enquiry Variables Field Mapping");
		applyDialogFont(composite);
		return composite;
	}	

	/**
	 * @param item
	 * @param combo
	 */
	public void select(String item, Combo combo) {
		int index = -1;
		String[] items = combo.getItems();
		for (int i = 0; i < items.length; i++) {
			if (items[i].equals(item)) {
				index = i;
			}
		}
		combo.select(index);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.Dialog#getInitialSize()
	 */
	protected Point getInitialSize() {
		return new Point(700, 550);
	}

	/**
	 * @return
	 */
	public String getValue() {
		return value;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.Dialog#okPressed()
	 */
	protected void okPressed() {
		try {
			if (validateControls() && isEnqVarsDataValid()) {
				String versionValue = versionCbx.getText().replace("_", ".");
				value = updateValue(versionValue + " "
						+ getModeKey(modeCbx.getText()) + " "
						+ recordCbx.getText() + " "
						+ getMappingStringFromTable() + " "
						+ getMappingsStringFromEnqVarsTable());
				super.okPressed();
			}
		} catch (IllegalArgumentException e) {
			showError(e.getLocalizedMessage(), e.getMessage());
		}
	}

	private boolean validateControls() {
		String version = versionCbx.getText();
		if (!VERSION_MAP.containsKey(version)) {
			showError(
					"Invalid Version",
					"Version \'"
							+ version
							+ "\' is not a valid version. " +
							"Select a valid version from the list proposed.");
			return false;
		}
		
		//gasampong - added to ensure that value is empty or has special characters in it
		for(int i = 0; i < table.getItemCount(); i++){
			TableItem tableItem = table.getItem(i);			
			String key = tableItem.getText(0);
			String value = tableItem.getText(1);			
			if(key.isEmpty()){
				showError("Key cannot be empty",
						"Key is empty for the value "+value);				
				return false;
			} else if(value.isEmpty()){
				showError("Value cannot be empty",
						"Value is empty for the field "+key);				
				return false;				
			}else if (JbpmDialogHelper.containsInValidCharacter(key)){
				throw new IllegalArgumentException(JbpmDialogHelper.INVALID_CHAR_ERROR_MSG);
			} else if (JbpmDialogHelper.containsInValidCharacter(value)){
				throw new IllegalArgumentException(JbpmDialogHelper.INVALID_CHAR_ERROR_MSG);
			}
		}
		
		return true;
	}
	
	private boolean isEnqVarsDataValid(){
		ArrayList<String> keys = new ArrayList<String>();
		for(int i = 0; i < enqVarsTable.getItemCount(); i++){
			TableItem tableItem = enqVarsTable.getItem(i);
			String key = tableItem.getText(0);			
			if(key.isEmpty()){
				throw new IllegalArgumentException("Key cannot be empty");				
			} else if(JbpmDialogHelper.containsInValidCharacter(key)){
				throw new IllegalArgumentException(JbpmDialogHelper.INVALID_CHAR_ERROR_MSG);			
			} else {
				if(keys.contains(key)){
					throw new IllegalArgumentException("Duplicate key: "+key);						
				} else {
					if(key.equals("DURATION")){	
						if(!JbpmDialogHelper.isDurationValid(tableItem.getText(1)==null?"":tableItem.getText(1))){
							throw new IllegalArgumentException(JbpmDialogHelper.INVALID_DURATION_ERROR_MSG);					
						}						
					}
					keys.add(key);
				}				
			}
			
			if(tableItem.getText(1).isEmpty()){
				throw new IllegalArgumentException("Value cannot be empty");				
			}else if (JbpmDialogHelper.containsInValidCharacter(tableItem.getText(1))){
				throw new IllegalArgumentException(JbpmDialogHelper.INVALID_CHAR_ERROR_MSG);				
			}			
		}
		return true;
	}	

	/**
	 * @param value
	 * @return
	 */
	protected String updateValue(String value) {
		return value;
	}

	/**
	 * @param value
	 */
	public void setValue(String value) {
		//Remove ENQ_VARS from value string
		String[]temp = JbpmDialogHelper.parseValue(value);
		String parsedValue = temp[0];	
		
		//George Asampong - This logic causes issues when dealing with comma versions
		//New logic doesn't do replace if dealing with a comma version 
		//if (parsedValue.contains(", ")) {
		Pattern pattern = Pattern.compile(", [AI] ");
		Matcher m = pattern.matcher(value);
		if(m.find()==false){		
			parsedValue = parsedValue.replace(", ", ",");
		}
		
		this.value = parsedValue;	
		values = parsedValue.split(JbpmDialogHelper.delims);
		this.enqVarsValue = temp[1];
	}
	
	/**
	 * @param error
	 * @param message
	 */
	protected void showError(String error, String message) {
		ErrorDialog.openError(getShell(), "Error", error, new Status(
				IStatus.ERROR, Activator.getDefault().getBundle()
						.getSymbolicName(), IStatus.ERROR, message, null));
	}

	@Override
	public void mouseDoubleClick(MouseEvent e) {
	}
	
    private int selectedColumn = -1;
    private int selectedEnqVarsTableColumn = -1;

	@Override
	public void mouseDown(MouseEvent e) {
		if(e.widget == table) {
			selectedColumn = getSelectedColumn(e.x, e.y,table);		
			if (selectedColumn == -1) return;
			doEdit();
		} else if (e.widget == enqVarsTable)
			selectedEnqVarsTableColumn = getSelectedColumn(e.x, e.y,enqVarsTable);
        	if (selectedEnqVarsTableColumn == -1) return;
        	doEditEnqVarsTable();                
	}

	@Override
	public void mouseUp(MouseEvent e) {
	}
    
    /* (non-Javadoc)
     * @see org.eclipse.swt.events.FocusListener#focusGained(org.eclipse.swt.events.FocusEvent)
     */
    public void focusGained(FocusEvent e) {
    }

    /* (non-Javadoc)
     * @see org.eclipse.swt.events.FocusListener#focusLost(org.eclipse.swt.events.FocusEvent)
     */
    public void focusLost(FocusEvent e) {
        if (e.widget == valueCbx || e.widget == combo) {
            applyValue(e.widget);
            endEdit();
        } else if (e.widget == enqVarsKeyText || e.widget == enqVarsValueCombo){
        	applyValueEnqVarsTable(e.widget);
        	endEnqVarsTableEndEdit();
        }
    }

    /**
     * @param widget
     */
    private void applyValue(Widget widget) {
        int i = table.getSelectionIndex();
        if (i == -1) {
            return;
        }
        TableItem item = table.getItem(i);
        if (widget == combo) {
        	item.setText(selectedColumn, combo.getText());
        } else {
        	item.setText(selectedColumn, valueCbx.getText());
        }
    }
    
    /**
     * @param widget
     */
    private void applyValueEnqVarsTable(Widget widget) {
        int i = enqVarsTable.getSelectionIndex();
        if (i == -1) {
            return;
        }
        TableItem item = enqVarsTable.getItem(i);
        if (widget == enqVarsKeyText) {
        	item.setText(selectedEnqVarsTableColumn, enqVarsKeyText.getText());
        } else if(widget == enqVarsValueCombo){
        	item.setText(selectedEnqVarsTableColumn, enqVarsValueCombo.getText());
        }
    }    
      
    private void doEdit() {
        if (valueCbx.isVisible()) {
            endEdit();
        }
        if (table.getSelectionIndex() == -1 || selectedColumn == -1) return;
        TableItem selection = table.getItem(table.getSelectionIndex());
        String value = selection.getText(selectedColumn);
        if (selectedColumn == 0) {
        	List<String> fieldNames = getSelectedVersionFields(versionCbx.getText());
        	combo.setItems(fieldNames.toArray(new String[]{}));
        	combo.setText(value == null ? "" : value);
            ceditor.setEditor(combo, selection, selectedColumn);
            combo.setVisible(true);
            combo.setFocus();
            combo.addFocusListener(this);         
        } else {
        	List<String> processVariables = JbpmDialogHelper.getProcessVariables(wrapper,true);
        	valueCbx.setItems(processVariables.toArray(new String[]{}));
            valueCbx.setText(value == null ? "" : value);
            editor.setEditor(valueCbx, selection, selectedColumn);
            valueCbx.setVisible(true);
            valueCbx.setFocus();
            valueCbx.addFocusListener(this);          
        }
    }
    
    private void doEditEnqVarsTable() {
        if (enqVarsValueCombo.isVisible()) {
            endEnqVarsTableEndEdit();
        }
        if (enqVarsTable.getSelectionIndex() == -1) return;
        TableItem selection = enqVarsTable.getItem(enqVarsTable.getSelectionIndex());
        String value = selection.getText(selectedEnqVarsTableColumn);
        if (selectedEnqVarsTableColumn == 0) {
            enqVarsKeyText.setText(value == null ? "" : value);
            enqVarsKeyEditor.setEditor(enqVarsKeyText, selection, selectedEnqVarsTableColumn);
            enqVarsKeyText.setVisible(true);
            enqVarsKeyText.selectAll();
            enqVarsKeyText.setFocus();
            enqVarsKeyText.addFocusListener(this);
        } else if (selectedEnqVarsTableColumn == 1){
        	List<String> processVariables = JbpmDialogHelper.getProcessVariables(wrapper,true);
        	enqVarsValueCombo.setItems(processVariables.toArray(new String[]{}));        	
            enqVarsValueCombo.setText(value == null ? "" : value);
            enqVarsValueEditor.setEditor(enqVarsValueCombo, selection, selectedEnqVarsTableColumn);
            enqVarsValueCombo.setVisible(true);
            enqVarsValueCombo.setFocus();            
            enqVarsValueCombo.addFocusListener(this);
        }
    }    
    
    private void endEdit() {
        if (selectedColumn == 0) {
        	combo.setVisible(false);
        	combo.setText("");
        	combo.removeFocusListener(this);
        } else {
	        valueCbx.setVisible(false);
	        valueCbx.setText("");
	        valueCbx.removeFocusListener(this);
        }
    }
    
    private void endEnqVarsTableEndEdit() {
        if (selectedEnqVarsTableColumn == 0) {
        	enqVarsKeyText.setVisible(false);
        	enqVarsKeyText.setText("");
        	enqVarsKeyText.removeFocusListener(this);      	
        } else if (selectedEnqVarsTableColumn == 1){
        	enqVarsValueCombo.setVisible(false);
        	enqVarsValueCombo.setText("");
        	enqVarsValueCombo.removeFocusListener(this);        	
        }
    }    
    
    /**
     * @param x
     * @param y
     * @return
     */
    private int getSelectedColumn(int x, int y,Table table) {	
        int columnToEdit = -1;
        int columns = table.getColumnCount();
        if (table.getSelection().length == 0) {
            return -1;
        }
        for (int i = 0; i < columns; i++) {
            Rectangle bounds = table.getSelection()[0].getBounds(i);
            if (bounds.contains(x, y)) {
                columnToEdit = i;
                break;
            }
        }
        return columnToEdit;
    }
    
}
