package com.odcgroup.page.ui.dialog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.commons.lang.StringUtils;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
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
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

import com.odcgroup.page.metamodel.DataType;
import com.odcgroup.page.metamodel.DataValue;
import com.odcgroup.page.metamodel.EventType;
import com.odcgroup.page.metamodel.FunctionType;
import com.odcgroup.page.metamodel.ParameterType;
import com.odcgroup.page.metamodel.PropertyType;
import com.odcgroup.page.metamodel.util.MetaModelRegistry;
import com.odcgroup.page.model.Event;
import com.odcgroup.page.model.ModelFactory;
import com.odcgroup.page.model.Parameter;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.util.WidgetCopier;
import com.odcgroup.workbench.core.IOfsModelResource;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.core.repository.ModelNotFoundException;
import com.odcgroup.workbench.core.repository.ModelURIConverter;
import com.odcgroup.workbench.core.repository.OfsResourceHelper;


/**
 * Dialog window that allow a user to specify event for a widget it's allow to select the appropriates function and set
 * the parameter values.
 * 
 * @author pkk
 */
public class EventDialog extends TitleAreaDialog implements IPageEventDefinitionDialog {

    /** The title of the dialog window. */
    private String title;
    
    /** message of the dialog */
    private String message;

    /** The event. */
    private Event event;

    /** The Widget. */
    private Widget widget;

    /** The functions list. */
    private List fctList;

    /** Event list. */
    private Combo eventCbx;

    /** The params table. */
    private Table paramsTbl;

    /** The editable column index. */
    private static final int EDITABLE_COLUMN = 1;

    /** Event Parameters Control */
    private UserParameterControl userParamControl;
    
// DS-3322 - Page Designer - Refactoring - begin
//    /**
//     * 
//     */
//    protected void addListeners() {
//		if (editor != null && widget != null) {
//			Resource resource = widget.eResource();
//			IOfsProject ofsProject = OfsResourceHelper.getOfsProject(resource);
//			if (ofsProject != null) {
//				MessageRepositoryHolder holder = MessageRepositoryHolder.getInstance(ofsProject);
//				if (holder != null) {
//					holder.addRepositoryStateListener(editor);
//				}
//			}
//		}
//    }
// DS-3322 - Page Designer - Refactoring - end
    
// DS-3322 - Page Designer - Refactoring - begin
//    /**
//     * 
//     */
//    protected void removeListeners() {
//		if (editor != null && widget != null) {
//			Resource resource = widget.eResource();
//			IOfsProject ofsProject = OfsResourceHelper.getOfsProject(resource);
//			if (ofsProject != null) {
//				MessageRepositoryHolder holder = MessageRepositoryHolder.getInstance(ofsProject);
//				if (holder != null) {
//					holder.removeRepositoryStateListener(editor);
//				}
//			}
//		}
//    }    
// DS-3322 - Page Designer - Refactoring - end

    /**
     * Creates a new EventDialog.
     * 
     * @param parent The parent window
     * @param widget The Widget
     * @param event The event
     * @param title The title of the dialog window
     * @param message The message of the dialog window
     */
    public EventDialog(Shell parent, Widget widget, Event event, String title, String message) {
        super(parent);
        setShellStyle(getShellStyle() | SWT.RESIZE);
        
        this.widget = widget;
        this.event = event;
        this.title = title;
        this.message = message;
    }

	/**
	 * (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.Dialog#create()
	 */
    @Override
	public void create() {
		super.create();
		setTitle(title);
		setMessage(message);
	}

	/**
     * Configures the shell.
     * 
     * @param shell The shell to configure
     */
    protected void configureShell(Shell shell) {
        super.configureShell(shell);
        shell.setText(title);
        shell.setMinimumSize(600, 300);
    }

    /**
     * Create the dialog area and place correctly the user interfaces widgets
     */
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
        
        Composite eventBody = new Composite(body, SWT.FILL);
        gridLayout = new GridLayout(2, false);
        eventBody.setLayout(gridLayout);
        gridData = new GridData(GridData.FILL_BOTH);
        eventBody.setLayoutData(gridData);
        
        Composite eBody = new Composite(eventBody, SWT.FILL);
        gridLayout = new GridLayout(2, false);
        eBody.setLayout(gridLayout);
        gridData = new GridData(GridData.FILL_VERTICAL);
        gridData.verticalAlignment = SWT.TOP;
        eBody.setLayoutData(gridData);
        
        // Events
        Label eventLbl = new Label(eBody, SWT.LEFT);
        eventLbl.setText("Event:");

        eventCbx = new Combo(eBody, SWT.NONE);
        fillEventTypesList();

        // Create a new Event
        if (event == null) {
            event = createEvent(eventCbx.getItem(0));
        } else {
            // We need to copy the original event in case the user cancels his changes
            event = WidgetCopier.copy(event);

        }
        eventCbx.setText(event.getEventName());

        eventCbx.addSelectionListener(new SelectionAdapter() {

            public void widgetSelected(SelectionEvent e) {
                String eventName = ((Combo) e.getSource()).getText();
                event.setEventName(eventName);
                createProperties();
// DS-3322 - Page Designer - Refactoring - begin
//                event.eAdapters().remove(localizable);
//                localizable = (Localizable) EventLocalizableAdapterFactory.INSTANCE.adapt(event, Localizable.class);
//                editor.setLocalizable(localizable);
//                editor.setReadOnly(isReadOnly(widget));
// DS-3322 - Page Designer - Refactoring - end
                fillFunctionsList();
                String selection = null;
                if (fctList.getSelection().length>0)
                	selection = fctList.getSelection()[0];
                refreshParamControl(selection);
                
            }
        });

        // Functions
        Label functionsLbl = new Label(eBody, SWT.LEFT);
        functionsLbl.setText("Functions:");
        gridData = new GridData();
        gridData.verticalAlignment = GridData.VERTICAL_ALIGN_BEGINNING;
        functionsLbl.setLayoutData(gridData);

        fctList = new List(eBody, SWT.BORDER | SWT.SINGLE | SWT.V_SCROLL);
        gridData = new GridData(GridData.FILL_VERTICAL);
        gridData.heightHint = 120;
        gridData.widthHint = 120;
        fctList.setLayoutData(gridData);

        fillFunctionsList();        

        fctList.addSelectionListener(new SelectionAdapter() {

            public void widgetSelected(SelectionEvent e) {
                String funcName = fctList.getSelection()[0];
                event.setFunctionName(funcName);
                createParameters();
                fillParametersList();
                refreshParamControl(funcName);
            }
        });
        
        // parameters group
        Group paramGroup = new Group(eventBody, SWT.SHADOW_ETCHED_IN | SWT.FILL);
        paramGroup.setText(" Parameters ");
		gridLayout = new GridLayout(1, false);
		paramGroup.setLayout(gridLayout);
        gridData = new GridData(GridData.FILL_BOTH);
        paramGroup.setLayoutData(gridData);        
        userParamControl = new UserParameterControl(paramGroup, SWT.FILL);
        userParamControl.setInput(event, null);
        userParamControl.setEnabled(false);
        
        if (!StringUtils.isEmpty(event.getFunctionName())) {
            fctList.setSelection(new String[] { event.getFunctionName() });
            refreshParamControl(event.getFunctionName());
        }

        // attributes group
        Group def = new Group(body, SWT.SHADOW_ETCHED_IN);
		def.setText(" Attributes ");
		gridLayout = new GridLayout(1, false);
		def.setLayout(gridLayout);
        gridData = new GridData(GridData.FILL_BOTH);
        def.setLayoutData(gridData);

        // Parameters table
        paramsTbl = new Table(def, SWT.SINGLE);
        gridData = new GridData(GridData.FILL_BOTH);
        gridData.heightHint = 100;
        paramsTbl.setLayoutData(gridData);
        paramsTbl.setHeaderVisible(true);
        paramsTbl.setLinesVisible(true);
        addColumn(paramsTbl, "Attribute", 100);
        addColumn(paramsTbl, "Value", 300);
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

        fillParametersList();

        def = new Group(body, SWT.SHADOW_ETCHED_IN);
		def.setText(" Confirmation Translations ");
		gridLayout = new GridLayout(1, false);
		def.setLayout(gridLayout);
        gridData = new GridData(GridData.FILL_BOTH);
        def.setLayoutData(gridData);

        createProperties();
        
// DS-3322 - Page Designer - Refactoring - begin
//        editor = LocalizableEditorSupportFactory.createLocalizableEditor(def, 2, false, true);
//        localizable = (Localizable) EventLocalizableAdapterFactory.INSTANCE.adapt(event, Localizable.class);
//        editor.setLocalizable(localizable);
//        editor.setReadOnly(isReadOnly(widget));
//        addListeners();
// DS-3322 - Page Designer - Refactoring - end

        return comp;
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

        MetaModelRegistry.getEventsFor(widget.getType());
        Set<FunctionType> functions = MetaModelRegistry.getFunctionsFor(event.getEventType());

        for (FunctionType ft : functions) {
            String funcName = ft.getName();
            names.add(funcName);
        }

        Collections.sort(names);
        fctList.setItems(names.toArray(new String[] {}));
    }
    
    /**
     * @param functionName
     */
    private void refreshParamControl(String functionName) {
    	if (!StringUtils.isEmpty(functionName) 
    			&& getFunctionTypeByName(functionName).isAllowUserParameters()) {
        	userParamControl.setEnabled(true);
        } else {
        	userParamControl.setEnabled(false);        	
        }
    }
    
    /**
     * @param functionName
     * @return FunctionType
     */
    private FunctionType getFunctionTypeByName(String functionName) {
    	 MetaModelRegistry.getEventsFor(widget.getType());
         Set<FunctionType> functions = MetaModelRegistry.getFunctionsFor(event.getEventType());
         for (FunctionType ft : functions) {
        	 if (ft.getName().equals(functionName)) {
        		 return ft;
        	 }
         }
         return null;
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

        ps.addAll(event.getParameters());

        for (Parameter p : ps) {
        	if (!p.isUserDefined())
        		new TableItem(paramsTbl, SWT.NONE).setText(new String[] { p.getName(), p.getValue() });
        }
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

        Control newEditor = null;
        String editName = "";
        String paramName = row.getText(0);
        java.util.List<String> values = new ArrayList<String>();
        ParameterType pt = event.getFunctionType().findParameterType(paramName);
        if (pt != null) {
            DataType dt = pt.getType();
            for (DataValue dv : dt.getValues()) {
                values.add(dv.getValue());
            }
            editName = dt.getEditorName();
        }

        // The combobox control that will be the editor must be a child of the Table
        // TODO GHA We should not be using the endsWith to determine the editor name!
        if (editName.endsWith("ComboBoxEditor")) {
            CCombo cbx = new CCombo(paramsTbl, SWT.READ_ONLY | SWT.SINGLE);
            newEditor = cbx;
            // ParameterType
            for (String v : values) {
                cbx.add(v);
            }
            cbx.setText(row.getText(EDITABLE_COLUMN));
            cbx.addSelectionListener(new SelectionAdapter() {

                public void widgetSelected(SelectionEvent e) {
                    CCombo cbx = (CCombo) editor.getEditor();
                    updateTableItemAndParameter(editor.getItem(), cbx.getText());
                }
            });
        }

        // The checkbox control that will be the editor must be a child of the Table
        else if (editName.endsWith("CheckBoxEditor")) {
            Button btn = new Button(paramsTbl, SWT.CHECK);
            newEditor = btn;
            boolean selected = "true".equalsIgnoreCase(row.getText(EDITABLE_COLUMN));
            btn.setSelection(selected);
            btn.addSelectionListener(new SelectionAdapter() {

                public void widgetSelected(SelectionEvent e) {
                    Button btn = (Button) editor.getEditor();
                    String selected = btn.getSelection() ? "true" : "false";
                    updateTableItemAndParameter(editor.getItem(), selected);
                }
            });
        }

        // The text control that will be the editor must be a child of the Table
        else {
            Text txt = new Text(paramsTbl, SWT.NONE);
            newEditor = txt;
            txt.setText(row.getText(EDITABLE_COLUMN));
            txt.addModifyListener(new ModifyListener() {

                public void modifyText(ModifyEvent e) {
                    Text text = (Text) editor.getEditor();
                    updateTableItemAndParameter(editor.getItem(), text.getText());
                }
            });
            txt.selectAll();
        }

        newEditor.addFocusListener(new FocusAdapter() {

            public void focusLost(FocusEvent e) {
                editor.getEditor().dispose();
            }
        });
        newEditor.setFocus();
        editor.setEditor(newEditor, row, EDITABLE_COLUMN);
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
        Parameter p = event.findParameter(paramName);
        p.setValue(value);
    }

  
    /**
     * (non-Javadoc)
     * @see com.odcgroup.page.ui.dialog.IPageEventDefinitionDialog#getDefinedEvent()
     */
    public Event getDefinedEvent() {
        return event;
    }

    /**
     * Creates a new event with the specified event name.
     * 
     * @param eventName The event name to creates
     * @return Event The created event
     */
    private Event createEvent(String eventName) {
        Event event = ModelFactory.eINSTANCE.createEvent();
        event.setEventName(eventName);
        return event;
    }

    /**
     * Creates the parameters for the Event. Existing parameters are reused so that their values are conserved.
     */
    private void createParameters() {
        Map<String, Parameter> existingParameters = new HashMap<String, Parameter>();
        for (Parameter p : event.getParameters()) {
            existingParameters.put(p.getName(), p);
        }

        // Clear the old parameters
        event.getParameters().clear();

        for (ParameterType pt : event.getFunctionType().getParameters()) {
            Parameter p = existingParameters.get(pt.getName());
            if (p == null) {
                // Create a new parameter
                p = ModelFactory.eINSTANCE.createParameter();
                p.setName(pt.getName());
            }
            event.getParameters().add(p);
        }
    }

    /**
     * Creates the properties for the Event. Existing properties are reused so that their values are conserved.
     */
    private void createProperties() {
        Map<String, Property> existingProperties = new HashMap<String, Property>();
        for (Property p : event.getProperties()) {
            existingProperties.put(p.getTypeName(), p);
        }

        // Clear the old properties
        event.getProperties().clear();

        for (PropertyType pt : event.getEventType().getPropertyTypes()) {
            Property p = existingProperties.get(pt.getName());
            if (p == null) {
                // Create a new property
                p = ModelFactory.eINSTANCE.createProperty();
                p.setTypeName(pt.getName());
                p.setLibraryName(pt.getLibrary().getName());
            }
            event.getProperties().add(p);
        }
    }
    
    /**
     * @see org.eclipse.jface.dialogs.Dialog#close()
     */
    public boolean close() {
// DS-3322 - Page Designer - Refactoring - begin
//    	removeListeners();
//        event.eAdapters().remove(localizable);
// DS-3322 - Page Designer - Refactoring - end
    	return super.close();
    }
    
	/**
	 * @param eObj
	 * @return boolean
	 */
	protected boolean isReadOnly(EObject eObj) {
		boolean isReadOnly = false;
		Resource resource = eObj.eResource();
		if (resource != null) {
			IOfsProject ofsProject = OfsResourceHelper.getOfsProject(resource);
			if (ofsProject != null) {
				try {
					URI uri = eObj.eResource().getURI();
					if (uri.isPlatform()) {
						String[] segments = resource.getURI().segments();
						StringBuffer path = new StringBuffer();
						for (int kx = 3; kx < segments.length; kx++) {
							path.append("/" + segments[kx]);
						}
						uri = ModelURIConverter.createModelURI(path.toString());
					}
					IOfsModelResource ofsResource = ofsProject.getOfsModelResource(uri);
					isReadOnly = ofsResource.isReadOnly();
				} catch (ModelNotFoundException ex) {
					;;// ignore 
				}
			}
		}
		return isReadOnly;
	}


}
