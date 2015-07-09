package com.odcgroup.page.pageflow.integration.ui.dialog.controls;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.commons.lang.StringUtils;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
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
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

import com.odcgroup.page.metamodel.DataType;
import com.odcgroup.page.metamodel.DataValue;
import com.odcgroup.page.metamodel.EventType;
import com.odcgroup.page.metamodel.EventTypeConstants;
import com.odcgroup.page.metamodel.FunctionType;
import com.odcgroup.page.metamodel.FunctionTypeConstants;
import com.odcgroup.page.metamodel.ParameterType;
import com.odcgroup.page.metamodel.ParameterTypeConstants;
import com.odcgroup.page.metamodel.PropertyType;
import com.odcgroup.page.metamodel.util.MetaModelRegistry;
import com.odcgroup.page.model.Event;
import com.odcgroup.page.model.ModelFactory;
import com.odcgroup.page.model.Parameter;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Widget;

/**
 * Advanced Event definition Control
 *
 * @author pkk
 *
 */
public class AdvancedEventDefinitionControl implements  IEventControl, SelectionListener, ModifyListener {

	/**	 */
	private Event event;
	/**	 */
	private Widget widget;

    /** The functions list. */
	private List fctList;

	/** */
	private String errorMessage;

    /** Event list. */
    private Combo eventCbx;

    /** The params table. */
    private Table paramsTbl;

    /** The editable column index. */
    private static final int EDITABLE_COLUMN = 1;

//	private java.util.List<Parameter> eventParameters = new ArrayList<Parameter>();
	
	// this is the cache for all values of non-user-defined parameters 
	// key = parameter_name+datatype_name, value=the entered value
	private Map<String, String> cachedParamValues = new HashMap<String, String>();
	
	private void initializeCachedParamValues(Event event) {
		FunctionType ft = event.getFunctionType();
		for (Parameter parameter : event.getParameters()) {
			if (!parameter.isUserDefined()) {
				String paramName = parameter.getName();
				ParameterType pt = ft.findParameterType(paramName);
				String value = parameter.getValue();
				if(paramName.equals("param") && value.equals("")){
				   Parameter tranParm= EventUtil.getTransitionParameter(event);
				  if(tranParm!=null && tranParm.getValue()!=null && StringUtils.isNotEmpty(tranParm.getValue())){
				    value=tranParm.getName()+"="+tranParm.getValue();
				   }
				}
				if (value == null) {
					value = "";
				}
				String key = paramName + "_" + pt.getType().getName();
				cachedParamValues.put(key, value);
			}
		}
		
	}

	/**
	 * @param parent
	 * @param event
	 */
	public AdvancedEventDefinitionControl(Composite parent, Event event, Widget widget) {
		this.event = event;
		this.widget = widget;
	        initializeCachedParamValues(event);
		createControls(parent);
		initializeControls();
	}

	/**
	 * @param parent
	 */
	private void createControls(Composite parent) {

	Composite eventBody = new Composite(parent, SWT.FILL | SWT.BORDER);
        GridLayout gridLayout = new GridLayout(2, false);
        eventBody.setLayout(gridLayout);
        GridData gridData = new GridData(GridData.FILL_BOTH);
        eventBody.setLayoutData(gridData);

        // event definition
		createEventDefinitionControl(eventBody);
        // event attributes editor control
		createEventAttributeControl(eventBody);

	}

	/**
	 * @param body
	 */
	private void createEventDefinitionControl(Composite body) {
		Composite eBody = new Composite(body, SWT.FILL);
		GridLayout gridLayout = new GridLayout(2, false);
		eBody.setLayout(gridLayout);
		GridData gridData = new GridData(GridData.FILL_VERTICAL);
		gridData.verticalAlignment = SWT.TOP;
		eBody.setLayoutData(gridData);

		// Events
		Label eventLbl = new Label(eBody, SWT.LEFT);
		eventLbl.setText("Event:");

		eventCbx = new Combo(eBody, SWT.SINGLE | SWT.READ_ONLY);
		eventCbx.addSelectionListener(new SelectionAdapter() {
		    public void widgetSelected(SelectionEvent e) {
		        String eventName = ((Combo) e.getSource()).getText();
		        event.setEventName(eventName);
		        createProperties();

		        fillFunctionsList();
		        fillParametersList();

		        if(fctList.getItemCount() > 0) {
		        	String functionName = event.getFunctionName();
					if (!StringUtils.isEmpty(functionName)) {
		        		fctList.setSelection(new String[] {functionName});
		        	}
		        	else {
		        		fctList.select(0);
		        	}
		        }
		    }
		});

        // Functions
		Label functionsLbl = new Label(eBody, SWT.LEFT);
		functionsLbl.setText("Functions:");
		gridData = new GridData();
		gridData.verticalAlignment = SWT.TOP;
		functionsLbl.setLayoutData(gridData);

		fctList = new List(eBody, SWT.BORDER | SWT.SINGLE | SWT.V_SCROLL);
		gridData = new GridData(GridData.FILL_VERTICAL);
		fctList.setLayoutData(gridData);
		fctList.addSelectionListener(new SelectionAdapter() {

		    public void widgetSelected(SelectionEvent e) {
		        String funcName = fctList.getSelection()[0];
		        event.setFunctionName(funcName);

		        // clear non-user-defined parameters
		        java.util.List<Parameter> paramToBeRemoved = new ArrayList<Parameter>();
		        for (Parameter parameter : event.getParameters()) {
		        	if (!parameter.isUserDefined()) {
		        		paramToBeRemoved.add(parameter);
		        	}
		        }
		        event.getParameters().removeAll(paramToBeRemoved);
		        
		        FunctionType ft = event.getFunctionType();
				for (ParameterType parameterType : ft.getParameters()) {
					Parameter parameter = ModelFactory.eINSTANCE.createParameter();
					parameter.setName(parameterType.getName());
					parameter.setValue(parameterType.getDefaultValue());
					event.getParameters().add(parameter);
				}
		            
		        fillParametersList();
		    }
		});


		if (!StringUtils.isEmpty(event.getEventName())) {
		    fctList.setSelection(new String[] {event.getEventName()});
		}
	}

	/**
	 * @param body
	 */
	private void createEventAttributeControl(Composite body) {
		// attributes group
        Group def = new Group(body, SWT.SHADOW_ETCHED_IN);
		def.setText(" Attributes ");
		GridLayout gridLayout = new GridLayout(1, false);
		def.setLayout(gridLayout);
		GridData gridData = new GridData(GridData.FILL_BOTH);
        def.setLayoutData(gridData);

        // Parameters table
        paramsTbl = new Table(def, SWT.SINGLE);
        gridData = new GridData(GridData.FILL_BOTH);
        gridData.heightHint = 100;
        paramsTbl.setLayoutData(gridData);
        paramsTbl.setHeaderVisible(true);
        paramsTbl.setLinesVisible(true);
        addColumn(paramsTbl, "Attribute", 100);
        addColumn(paramsTbl, "Value", 160);
        paramsTbl.addListener(SWT.MeasureItem, new Listener() {

			public void handleEvent(org.eclipse.swt.widgets.Event event) {
				Double dou = new Double(event.gc.getFontMetrics().getHeight()*1.12);
				event.height = dou.intValue();
			}
        });
        final TableEditor paramsTblEditor = new TableEditor(paramsTbl);
		// The editor must have the same size as the cell and must not be any
		// smaller than 50 pixels.
		paramsTblEditor.horizontalAlignment = SWT.LEFT;
		paramsTblEditor.grabHorizontal = true;
		paramsTblEditor.grabVertical = true;
		paramsTblEditor.minimumWidth = 50;
		paramsTbl.addListener(SWT.MouseDown, new Listener() {

		    public void handleEvent(org.eclipse.swt.widgets.Event event) {
		        int nbColumns = paramsTbl.getColumnCount();
		        Rectangle clientArea = paramsTbl.getClientArea();
		        Point pt = new Point(event.x, event.y);
		        int index = paramsTbl.getTopIndex();
		        while (index < paramsTbl.getItemCount()) {
		            boolean visible = false;
		            TableItem item = paramsTbl.getItem(index);
		            for (int cx = 0; cx < nbColumns; cx++) {
		                Rectangle rect = item.getBounds(cx);
		                if (rect.contains(pt)) {
		                    installCellEditor(paramsTblEditor, item, index, cx);
		                }
		                if (!visible && rect.intersects(clientArea)) {
		                    visible = true;
		                }
		            }
		            if (!visible) return;
		            index++;
		        }
		    }
		});

		createProperties();
	}


	/**
     * Add a column to the specified table.
     *
     * @param table The selected table
     * @param name The name of the column
     * @param width The width of the column
     */
    private void addColumn(Table table, String name, int width) {
        final TableColumn tc = new TableColumn(table, SWT.LEFT);
        tc.setText(name);
        tc.setWidth(width);
    }

    /**
     * Install a cell editor for the specified editor.
     *
     * @param editor The selected editor
     * @param row The selected row
     * @param rowIndex The row index
     * @param colIndex The column index
     */
    private void installCellEditor(final TableEditor editor, TableItem row, int rowIndex, int colIndex) {
        // Clean up any previous editor control
        Control oldEditor = editor.getEditor();
        if (oldEditor != null) {
            oldEditor.dispose();
        }

        final Control newEditor;
        String editName = "";
        String paramName = row.getText(0);
        java.util.List<String> values = new ArrayList<String>();
        ParameterType pt = event.getFunctionType().findParameterType(paramName);
        if (pt != null) {
            DataType dt = pt.getType();
            for (DataValue dataValue : dt.getValues()) {
				values.add(dataValue.getValue());
            }
            editName = dt.getEditorName();
        }

        // The combobox control that will be the editor must be a child of the Table
        // TODO GHA We should not be using the endsWith to determine the editor name!
		if (editName.endsWith("ComboBoxEditor")) {
        	int style = pt.getType().isEditable() ? SWT.SINGLE : SWT.READ_ONLY | SWT.SINGLE;
			CCombo cbx = new CCombo(paramsTbl, style);

			addDefaultEmptyValue(values, cbx);

			// ParameterType
			for (String v : values) {
			    cbx.add(v);
			}
			cbx.addSelectionListener(new SelectionAdapter() {
			    public void widgetSelected(SelectionEvent e) {
			        CCombo cbx = (CCombo) editor.getEditor();
					updateTableItemAndParameter(editor.getItem(), cbx.getText());
					cbx.setFocus();
			    }
			});

			if (pt.getType().isEditable()) {
			    cbx.addModifyListener(new ModifyListener() {
					@Override
					public void modifyText(ModifyEvent e) {
						CCombo cbx = (CCombo) editor.getEditor();
						updateTableItemAndParameter(editor.getItem(), cbx.getText());
						cbx.setFocus();
					}
			    });
			}
			newEditor = cbx;
        }

        // The checkbox control that will be the editor must be a child of the Table
        else if (editName.endsWith("CheckBoxEditor")) {
            Button btn = new Button(paramsTbl, SWT.CHECK);
			boolean selected = "true".equalsIgnoreCase(row.getText(EDITABLE_COLUMN));
			btn.setSelection(selected);
			btn.addSelectionListener(new SelectionAdapter() {

			    public void widgetSelected(SelectionEvent e) {
			        Button btn = (Button) editor.getEditor();
			        String selected = btn.getSelection() ? "true" : "false";
			        updateTableItemAndParameter(editor.getItem(), selected);
			    }
			});
			newEditor = btn;
        }

        // The text control that will be the editor must be a child of the Table
        else {
            Text txt = new Text(paramsTbl, SWT.NONE);
			txt.setText(row.getText(EDITABLE_COLUMN));
			txt.addModifyListener(new ModifyListener() {

			    public void modifyText(ModifyEvent e) {
			        Text text = (Text) editor.getEditor();
			        updateTableItemAndParameter(editor.getItem(), text.getText());
			    }
			});
			txt.selectAll();
			newEditor = txt;
        }

        newEditor.addFocusListener(new FocusAdapter() {
            public void focusLost(FocusEvent e) {
                editor.getEditor().dispose();
            }
        });

        newEditor.setFocus();
        editor.setEditor(newEditor, row, EDITABLE_COLUMN);
        
        // now force the selection of the combo.
        if (editName.endsWith("ComboBoxEditor")) {
        	setSelectedParameterValue(newEditor, paramName);
        }

        
    }

	/**
     * Updates the TableItem and the Parameter of the event.
     *
     * @param ti The TableItem
     * @param value The value to update
     */
    private void updateTableItemAndParameter(TableItem ti, String value) {
    	ti.setText(EDITABLE_COLUMN, value);
        String paramName = ti.getText(0);
		setParameterValue(value, paramName);
    }

	/**
     * Fills the parameters list.
	 */
    private void fillParametersList() {
        paramsTbl.removeAll();
        
        SortedSet<Parameter> ps = new TreeSet<Parameter>(new Comparator<Parameter>() {
            public int compare(Parameter p1, Parameter p2) {
                return p1.getName().compareTo(p2.getName());
            }
        });
        
        // rebuild parameters and reuse value already entered
        FunctionType ft = event.getFunctionType();
		for (ParameterType parameterType : ft.getParameters()) {
			String parameterName = parameterType.getName();
			Parameter parameter = event.findParameter(parameterName);
			String key = parameterName + "_" + parameterType.getType().getName();
			String value = cachedParamValues.get(key);
			//if the event is onmouseover  and function in submit set the method=pull and target =qtip 
			if(event.getEventName().equals(EventTypeConstants.ON_MouseOver_EVENT)&&ft.getName().equals(FunctionTypeConstants.SUBMIT_FUNCTION)){
			    if(parameterName.equals(ParameterTypeConstants.METHOD)){
				parameter.setValue("pull");
			    }
			    if(parameterName.equals(ParameterTypeConstants.TARGET)){
				parameter.setValue("qtip");
			    }
			    
			}else if (value == null) {
				// the value is not yet cached
				value = parameter.getValue();
				cachedParamValues.put(key, value);
			} else {
				// override the parameter's value with the cached one (reuse) 
				parameter.setValue(value);
			}
			ps.add(parameter);
		}
                
		for (Parameter parameter : ps) {
			String name = parameter.getName();
			String value = parameter.getValue();
			new TableItem(paramsTbl, SWT.NONE).setText(new String[] { name, value });
		}
		
       
    }

	/**
     * Fill the event types list.
     */
    private void fillEventTypesList() {
        // Build a sorted list of event types names
        ArrayList<String> names = new ArrayList<String>();
        Set<EventType> eventTypes = MetaModelRegistry.getEventsFor(widget.getType());

        for (EventType et : eventTypes) {
            names.add(et.getName());
        }

        Collections.sort(names);

        // Fill the combobox
        eventCbx.setItems(names.toArray(new String[] {}));
    }

    /**
     * Fills the function list.
     */
    private void fillFunctionsList() {
        fctList.removeAll();

        ArrayList<String> names = new ArrayList<String>();

        Set<FunctionType> functions = MetaModelRegistry.getFunctionsFor(event.getEventType());

        for (FunctionType ft : functions) {
            String funcName = ft.getName();
			names.add(funcName);
        }

        Collections.sort(names);
        fctList.setItems(names.toArray(new String[] {}));
    }

    /**
     * Creates the properties for the Event. Existing properties are reused so that their values are conserved.
     */
    private void createProperties() {
        Map<String, Property> existingProperties = new HashMap<String, Property>();
        for (Object p : event.getProperties()) {
        	existingProperties.put(((Property) p).getTypeName(), (Property) p);
        }

        // Clear the old properties
        event.getProperties().clear();

        for (Object pt : event.getEventType().getPropertyTypes()) {
            Property p = existingProperties.get(((PropertyType)pt).getName());
            if (p == null) {
                // Create a new property
                p = ModelFactory.eINSTANCE.createProperty();
                p.setTypeName(((PropertyType)pt).getName());
                p.setLibraryName(((PropertyType) pt).getLibrary().getName());
            }
            event.getProperties().add(p);
        }
    }

    /**
	 * Populate the lists and set the current selection
	 */
	private void initializeControls() {

	fillEventTypesList();
        fillFunctionsList();

        String eventName = event.getEventName();
        if (StringUtils.isNotEmpty(eventName)){
        	String[] eventItems = eventCbx.getItems();
        	for (int i = 0; i < eventItems.length; i++) {
        		if(eventItems[i].equalsIgnoreCase(eventName)) {
        			eventCbx.select(i);
        			break;
        		}
			}
        }

        String functionName = event.getFunctionName();
        if (StringUtils.isNotEmpty(functionName)) {
        	String[] functionalItems = fctList.getItems();
        	for (int i = 0; i < functionalItems.length; i++) {
				if (functionalItems[i].equalsIgnoreCase(functionName)) {
					fctList.select(i);
					break;
				}
			}
        }
        else {
        	fctList.select(0);
        	event.setFunctionName(fctList.getSelection()[0]);
        }
       
        fillParametersList();
	}

	/**
	 * @see com.odcgroup.page.pageflow.integration.ui.dialog.controls.IEventControl#getErrorMessage()
	 */
	public String getErrorMessage() {
		return this.errorMessage;
	}

	/**
	 * @return functionname
	 */
	public String getSelectedFunction() {
		String selection = null;
        if (fctList.getSelection().length>0)
        	selection = fctList.getSelection()[0];
        return selection;
	}

	/**
	 * @see com.odcgroup.page.pageflow.integration.ui.dialog.controls.IEventControl#isValid()
	 */
	public boolean isValid() {
		if (StringUtils.isEmpty(getSelectedFunction())) {
			errorMessage = "A Function needs to be selected";
			return false;
		}
		errorMessage = null;
		return true;
	}

	/**
	 * @see org.eclipse.swt.events.SelectionListener#widgetDefaultSelected(org.eclipse.swt.events.SelectionEvent)
	 */
	public void widgetDefaultSelected(SelectionEvent e) {
	}

	/**
	 * @see org.eclipse.swt.events.SelectionListener#widgetSelected(org.eclipse.swt.events.SelectionEvent)
	 */
	public void widgetSelected(SelectionEvent e) {
	}

	/**
	 * @see org.eclipse.swt.events.ModifyListener#modifyText(org.eclipse.swt.events.ModifyEvent)
	 */
	public void modifyText(ModifyEvent e) {
	}

	/**
	 * @see com.odcgroup.page.pageflow.integration.ui.dialog.controls.IEventControl#addEventModelChangeListener(com.odcgroup.page.pageflow.integration.ui.dialog.controls.EventModelChangeListener)
	 */
	public void addEventModelChangeListener(EventModelChangeListener listener) {
		// TODO Auto-generated method stub

	}

	/**
	 * @see com.odcgroup.page.pageflow.integration.ui.dialog.controls.IEventControl#notifyEventModelChange(com.odcgroup.page.model.Event)
	 */
	public void notifyEventModelChange(Event event) {
		// TODO Auto-generated method stub

	}

	/**
	 * @param values
	 * @param cbx
	 */
	private void addDefaultEmptyValue(java.util.List<String> values, CCombo cbx) {
		if (!values.contains("")) {
			cbx.add("");
		}
	}

    /**
     * @param newEditor
     * @param paramName
     */
    private void setSelectedParameterValue(final Control newEditor, String paramName) {
    	Parameter parameter = event.findParameter(paramName);
    	if (parameter != null) {
    		String value = parameter.getValue();
    		if (value != null) {
    			CCombo cbx = ((CCombo)newEditor);
    			int index = cbx.indexOf(value);
    			if (index != -1) {
    				cbx.setText(value);
    			} else {
    				cbx.setText("");
    			}
    		}
    	}
    }

    /**
	 * @param value
	 * @param paramName
	 */
	private void setParameterValue(String value, String paramName) {
		Parameter p = event.findParameter(paramName);

		if (p != null) {
			p.setValue(value);
			FunctionType ft = event.getFunctionType();
			ParameterType pt = ft.findParameterType(paramName);
			String key = paramName + "_" + pt.getType().getName();
			cachedParamValues.put(key, value);
		}
	}
}
