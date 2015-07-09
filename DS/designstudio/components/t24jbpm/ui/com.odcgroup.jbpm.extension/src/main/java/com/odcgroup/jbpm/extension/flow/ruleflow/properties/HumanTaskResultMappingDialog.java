package com.odcgroup.jbpm.extension.flow.ruleflow.properties;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.drools.eclipse.flow.ruleflow.core.HumanTaskNodeWrapper;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
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
import org.eclipse.swt.widgets.Widget;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.odcgroup.jbpm.extension.Activator;

/**
 * @author phanikumark
 * 
 */
public class HumanTaskResultMappingDialog extends Dialog implements
		MouseListener, FocusListener {

	private String title;
	private Map<String, String> value;
	private Table table;
	private Button removeButton;
	private Button addButton;
	private Combo ifSchemasCbx;
	private CCombo paramCombo;
	private CCombo varCombo;
	private TableEditor editor;
	private TableEditor ceditor;
	private HumanTaskNodeWrapper wrapper;
	
	private String currentIFSchema;	
	//private String targetVersion;
	private Map<String, String> fnames;
	
	private Map<String, IFile> IF_SCHEMA_MAP = new LinkedHashMap<String, IFile>();	
	private Map<String, IFile> IF_COMMON_SCHEMA_MAP = new LinkedHashMap<String, IFile>();		

	/**
	 * @param parentShell
	 * @param title
	 */
	protected HumanTaskResultMappingDialog(Shell parentShell, String title, HumanTaskNodeWrapper wrapper) {
		super(parentShell);
		this.title = title;
		this.wrapper = wrapper;
		IFModelUtil ifModelUtil = new IFModelUtil(wrapper);
		IF_SCHEMA_MAP.putAll(ifModelUtil.getIFSchemaMap());		
		IF_COMMON_SCHEMA_MAP.putAll(ifModelUtil.getIFCommonSchemaMap());
		if(!ifModelUtil.getSuccessfullyLoadedIfSchemas()){
			showError("Not all IF Schemas were loaded from your active projects", 
					"There was an exception whiles loading IF Schemas from your active projects");
		}
		setShellStyle(getShellStyle() | SWT.RESIZE);
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
		
		Label slabel = new Label(eBody, SWT.LEFT);
		slabel.setText("Schemas:");
		ifSchemasCbx = new Combo(eBody, SWT.None);	
		
		Set<String> keys = IF_SCHEMA_MAP.keySet();
				
		List<String> keylist = new ArrayList<String>();
				
		keylist.addAll(keys);
		
		if(keylist.size()==0){
			showError("There are no schemas available for result mapping", 
					"You don't have any schemas defined for the version used by this task");
		}			
		
		Collections.sort(keylist);
		ifSchemasCbx.setItems(keylist.toArray(new String[] {}));
		new AutoCompleteField(ifSchemasCbx, new ComboContentAdapter(), ifSchemasCbx.getItems());
		ifSchemasCbx.addModifyListener(new ModifyListener() {
			
			@Override
			public void modifyText(ModifyEvent e) {
				String ifSchema = ifSchemasCbx.getText();
				if (!IF_SCHEMA_MAP.containsKey(ifSchema)) {
					return;
				}
				if (currentIFSchema != null && !currentIFSchema.equals(ifSchema)) {
					boolean confirm = MessageDialog
							.openConfirm(
									getShell(),
									"New Integration Framework Schema selected",
									"A new Integration Framework Schema is selected, " +
									"which invalidates all the current parameter mappings. " +
									"\nAre you sure you want to continue with the new schema?");
					if (confirm) {
						currentIFSchema = ifSchema;
					} else {
						select(currentIFSchema, ifSchemasCbx);
					}
				} else {
					currentIFSchema = ifSchema;
				}
				
			}
		});	
		
		Group composite = new Group(body, SWT.SHADOW_ETCHED_IN);
		GridLayout layout = new GridLayout();
		layout.marginHeight = convertVerticalDLUsToPixels(IDialogConstants.VERTICAL_MARGIN);
		layout.marginWidth = convertHorizontalDLUsToPixels(IDialogConstants.HORIZONTAL_MARGIN);
		layout.verticalSpacing = convertVerticalDLUsToPixels(IDialogConstants.VERTICAL_SPACING);
		layout.horizontalSpacing = convertHorizontalDLUsToPixels(IDialogConstants.HORIZONTAL_SPACING);
		composite.setLayout(layout);
		composite.setLayoutData(new GridData(GridData.FILL_BOTH));
		composite.setText("Result Mappings");
		applyDialogFont(composite);	
					
		layout = new GridLayout();
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
		TableColumn parameterNameColumn = new TableColumn(table, SWT.LEFT);
		parameterNameColumn.setText("Parameter");
		parameterNameColumn.setWidth(175);
		TableColumn variableNameColumn = new TableColumn(table, SWT.LEFT);
		variableNameColumn.setText("Variable");
		variableNameColumn.setWidth(200);

		ceditor = new TableEditor(table);
		paramCombo = new CCombo(table, SWT.READ_ONLY);
		paramCombo.setVisible(false);
		ceditor.minimumWidth = paramCombo.getSize().x;
		ceditor.horizontalAlignment = SWT.LEFT;
		ceditor.grabHorizontal = true;

		editor = new TableEditor(table);
		varCombo = new CCombo(table, SWT.NONE);
		varCombo.setVisible(false);
		editor.minimumWidth = varCombo.getSize().x;
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

		initializeControls();
		selectIfSchemasCbx();
		return comp;
	}

	private void selectIfSchemasCbx() {
		String[] items = ifSchemasCbx.getItems();
		for (int i = 0; i < items.length; i++) {
			String item = items[i];
			if(item.equals(currentIFSchema)){
				ifSchemasCbx.select(i);
				return;
			}
		}
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
	 * @return
	 */
	private void setSelectedIFSchemaFields() {
		fnames = new LinkedHashMap<String, String>();
		
		IFile file = (IFile)IF_SCHEMA_MAP.get(currentIFSchema);		
		try {			
			Element rootNode = IFModelUtil.getDocumentRootNodeFromXML(file);
			getChildren(rootNode);			    	 	
		} catch (Exception e){
			showError(e.getLocalizedMessage(), e.getMessage());
		}		
	}
		
	/**
	 * 
	 * @param element
	 */
	private void getChildren(Element element){		
		NodeList nodes = element.getChildNodes();
		if(nodes!=null && nodes.getLength()>0){
			for (int i = 0; i < nodes.getLength(); i++){
				Node node = nodes.item(i);
				if(node.getNodeType()==Node.ELEMENT_NODE){
					Element childNode = (Element) node;
					if(childNode.getNodeName().equals("xsd:element")){
						String field = childNode.getAttribute("name");
						String type = childNode.getAttribute("type");
						if(field!=null && !field.equals("") && !field.contains(".flow")){
							String t24Field = getT24Field(childNode);
							if(t24Field==null || t24Field.equals("")){
								if(type.startsWith("ns") && type.contains(":")){
									String[] ns = type.split(":");
									loadImportedElements(ns[1]);
								} else {
									fnames.put(field, field);
								}
							} else {
								//will re-enable once we find a way to store the selected schema on hold
								//String fieldName = field+" ("+t24Field+")";
								//fnames.put(fieldName,field);
								fnames.put(field, field);
							}
						}
					} else {
						getChildren(childNode);			
					}
				}
			}
		}
	}
	
	/**
	 * 
	 * @param element
	 * @return
	 */
	private String getT24Field(Element element){
		String t24Field = "";
		NodeList nodes = element.getElementsByTagName("t24source");
		if(nodes!=null && nodes.getLength()>0){
			for(int i = 0; i < nodes.getLength(); i++){
				t24Field = ((Element)nodes.item(i)).getAttribute("field");
				if(t24Field!=null && !t24Field.equals("")){
					break;
				}
			}
		}
		return t24Field;
	}
	
	/**
	 * 
	 * @param ns
	 */
	private void loadImportedElements(String ns){
		IFile file = (IFile)IF_COMMON_SCHEMA_MAP.get("Common-"+ns);		
		
		if(file != null && file.exists()){		
			try {			
				Element rootNode = IFModelUtil.getDocumentRootNodeFromXML(file);
				getChildren(rootNode);
			} catch (Exception e){
				showError(e.getLocalizedMessage(), e.getMessage());
			}	
		}
	}

	private void addButtonPressed() {
		TableItem item = new TableItem(table, SWT.NONE);
		table.setSelection(item);
	}

	private void initializeControls() {
		if (value != null && !value.isEmpty()) {
			updateTable();
		}
	}

	private void updateTable() {
		Map<String, String> mapping = getValue();
		for (Map.Entry<String, String> entry : mapping.entrySet()) {
			if(entry.getKey().equals("Schema")){
				currentIFSchema = entry.getValue();
				continue;
			}
			TableItem item = new TableItem(table, SWT.NONE);
			item.setText(new String[] { entry.getKey(), entry.getValue() });
		}
	}

	/**
	 * 
	 * @param item
	 * 
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.dialogs.Dialog#getInitialSize()
	 */
	protected Point getInitialSize() {
		return new Point(500, 400);
	}

	/**
	 * @return
	 */
	public Map<String, String> getValue() {
		return value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.dialogs.Dialog#okPressed()
	 */
	protected void okPressed() {
		try {
			if (ifSchemasCbx != null && !ifSchemasCbx.isDisposed() && ifSchemasCbx.getSelectionIndex()>=0) {
				currentIFSchema = ifSchemasCbx.getItem(ifSchemasCbx.getSelectionIndex());
			}else {
				currentIFSchema = "";
			}
			value = updateValue();

			super.okPressed();
		} catch (IllegalArgumentException e) {
			showError(e.getLocalizedMessage(), e.getMessage());
		}
	}

	/**
	 * @param value
	 * @return
	 */
	protected Map<String, String> updateValue() {
		Map<String, String> mapping = new LinkedHashMap<String, String>();
		if (!currentIFSchema.isEmpty()) {
			mapping.put("Schema", currentIFSchema);
		}
		for (TableItem item : table.getItems()) {
			if(StringUtils.isNotBlank(item.getText(0))) {
				mapping.put(item.getText(0), item.getText(1));
			}
		}
		return mapping;
	}

	/**
	 * @param value
	 */

	public void setValue(Map<String, String> value) {
		this.value = value;
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
		int sel = getSelectedColumn(e.x, e.y);
		if (sel == -1) {
			addButtonPressed();
			selectedColumn = 0;
		}
		doEdit();
	}

	private int selectedColumn = -1;

	@Override
	public void mouseDown(MouseEvent e) {
		selectedColumn = getSelectedColumn(e.x, e.y);
		if (selectedColumn == -1)
			return;
		doEdit();
	}

	@Override
	public void mouseUp(MouseEvent e) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swt.events.FocusListener#focusGained(org.eclipse.swt.events
	 * .FocusEvent)
	 */
	public void focusGained(FocusEvent e) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swt.events.FocusListener#focusLost(org.eclipse.swt.events
	 * .FocusEvent)
	 */
	public void focusLost(FocusEvent e) {
		if (e.widget == varCombo || e.widget == paramCombo) {
			applyValue(e.widget);
			endEdit();
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
		if (widget == paramCombo) {
			String key = paramCombo.getText();
			if(key !=null){				
				item.setText(selectedColumn, getSchemaFieldValue(key));
			}
		} else {
			item.setText(selectedColumn, varCombo.getText());
		}
	}
	
	/**
	 * 
	 * @param key
	 * @return
	 */
	private String getSchemaFieldValue(String key){
		String schemaFieldValue = (String)fnames.get(key);
		if(schemaFieldValue == null){
			return "";
		} else {
			return schemaFieldValue;
		}
	}

	private void doEdit() {
		if (varCombo.isVisible()) {
			endEdit();
		}
		if (table.getSelectionIndex() == -1 || selectedColumn == -1)
			return;
		TableItem selection = table.getItem(table.getSelectionIndex());
		String value = selection.getText(selectedColumn);
		if (selectedColumn == 0) {
			if(currentIFSchema!=null){
				setSelectedIFSchemaFields();
				paramCombo.setItems(fnames.keySet().toArray(new String[] {}));
				paramCombo.setText(value == null ? "" : value);
				ceditor.setEditor(paramCombo, selection, selectedColumn);
				paramCombo.setVisible(true);
				paramCombo.setFocus();
				paramCombo.addFocusListener(this);
			}
		} else {
			List<String> varNames = JbpmDialogHelper.getProcessVariables(wrapper,false);
			varCombo.setItems(varNames.toArray(new String[] {}));
			varCombo.setText(value == null ? "" : value);
			editor.setEditor(varCombo, selection, selectedColumn);
			varCombo.setVisible(true);
			varCombo.setFocus();
			varCombo.addFocusListener(this);
		}
	}

	private void endEdit() {
		if (selectedColumn == 0) {
			paramCombo.setVisible(false);
			paramCombo.setText("");
			paramCombo.removeFocusListener(this);
		} else {
			varCombo.setVisible(false);
			varCombo.setText("");
			varCombo.removeFocusListener(this);
		}
	}

	/**
	 * @param x
	 * @param y
	 * @return
	 */
	private int getSelectedColumn(int x, int y) {
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
