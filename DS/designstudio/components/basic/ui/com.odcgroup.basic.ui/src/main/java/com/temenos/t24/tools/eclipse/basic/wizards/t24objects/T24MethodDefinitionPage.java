package com.temenos.t24.tools.eclipse.basic.wizards.t24objects;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Widget;

import com.temenos.t24.tools.eclipse.basic.PluginConstants;

public class T24MethodDefinitionPage extends WizardPage implements Listener {

    /** This page instance */
    private T24MethodDefinitionPage page = null;
    /** The Composite instance */
    private Composite container;
    /** Gridlayout - method details input */
    private Label t24MethodNameLabel;
    private static Text t24MethodNameText;
    private Label descriptionLable;
    private static Text descriptionText;
    private Label argumentsLable;
    private static Text argumentsText;
    private Label returnLable;
    private static Text returnText;
    private static Button addButton;
    private static Button updateButton;
    private static Button resetButton;
    /** Gridlayout - T24 method details display */
    private TableViewer tableViewer;
    /** Buttons to manipulate the methods */
    private static Button editButton;
    private static Button deleteButton;
    /** T24Object object */
    private T24Object t24Object;
    /** Method map */
    private Map<String, T24Method> t24Methods = new HashMap<String, T24Method>();
    /** current Method */
    private static T24Method t24Method = null;
    /** method selected by mouse to edit/delete */
    private static String t24MethodNameSelected = null;
    /** status map */
    private Map<Widget, IStatus> statusMap = new HashMap<Widget, IStatus>();

    public T24MethodDefinitionPage() {
        super("T24ObjectMethods");
        setDescription("Create methods");
        page = this;
    }

    public void setT24Object(T24Object t24Object) {
        this.t24Object = t24Object;
    }

    public T24Object getT24Object() {
        return this.t24Object;
    }

    public void createControl(Composite parent) {
        drawLayouts(parent);
        resetAllButtons();
        addListeners();
        setMessage(null);
        setErrorMessage(null);
    }

    private void drawLayouts(Composite parent) {
        // Size constants
        final int COLUMN_LEFT_WIDTH = 250;
        final int COLUMN_RIGHT_WIDTH = 400;
        final int TEXT_MINIMUM_WIDTH = 570;
        final int COLUMN_VERTICAL_SPACING = 20;
        final int SCROLL_FIELD_HEIGHT = 30;
        // Composite object
        container = new Composite(parent, SWT.NORMAL);
        setControl(container);
        // Composite layout
        GridLayout gridLayout = new GridLayout();
        gridLayout.numColumns = 2;
        container.setLayout(gridLayout);
        // Method input group - column 1, row 1
        Group t24MethodInputGroup = new Group(container, SWT.HORIZONTAL);
        GridLayout t24MethodInputLayout = new GridLayout();
        t24MethodInputLayout.numColumns = 2;
        t24MethodInputGroup.setLayout(t24MethodInputLayout);
        // Method Name:
        t24MethodNameLabel = new Label(t24MethodInputGroup, SWT.NORMAL);
        t24MethodNameLabel.setText("Method Name:");
        t24MethodNameLabel.setToolTipText("Name of the method. Ex., nextWorkingDay");
        GridData t24MethodNameData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
        t24MethodNameData.grabExcessHorizontalSpace = true;
        t24MethodNameData.minimumWidth = TEXT_MINIMUM_WIDTH;
        t24MethodNameText = new Text(t24MethodInputGroup, SWT.BORDER);
        // Description:
        descriptionLable = new Label(t24MethodInputGroup, SWT.NORMAL);
        t24MethodNameText.setLayoutData(t24MethodNameData);
        descriptionLable.setText("Description:");
        descriptionLable.setToolTipText("Description about the method");
        GridData descriptionData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
        descriptionText = new Text(t24MethodInputGroup, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
        descriptionText.setLayoutData(descriptionData);
        descriptionData.minimumWidth = TEXT_MINIMUM_WIDTH;
        descriptionData.heightHint = SCROLL_FIELD_HEIGHT;
        // Arguments:
        argumentsLable = new Label(t24MethodInputGroup, SWT.HORIZONTAL);
        argumentsLable.setText("Arguments:");
        argumentsLable.setToolTipText("Incoming arguments, seperated by comma. Ex., regionCode,today");
        GridData argumentsData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
        argumentsText = new Text(t24MethodInputGroup, SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
        argumentsText.setLayoutData(argumentsData);
        argumentsData.minimumWidth = TEXT_MINIMUM_WIDTH;
        argumentsData.heightHint = SCROLL_FIELD_HEIGHT;
        // Return:
        returnLable = new Label(t24MethodInputGroup, SWT.NORMAL);
        returnLable.setText("Return:");
        returnLable.setToolTipText("Return Argument");
        GridData returnData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
        returnText = new Text(t24MethodInputGroup, SWT.BORDER);
        returnText.setLayoutData(returnData);
        returnData.minimumWidth = TEXT_MINIMUM_WIDTH;
        // Add, Reset buttons group - column 2, row 1
        Group buttons = new Group(t24MethodInputGroup, SWT.VERTICAL);
        GridLayout buttonsLayout = new GridLayout();
        buttonsLayout.numColumns = 1;
        buttons.setParent(container);
        addButton = new Button(buttons, SWT.PUSH | SWT.FILL);
        addButton.setText("  Add   ");
        addButton.setToolTipText("Adds T24 methods to the T24Object");
        updateButton = new Button(buttons, SWT.PUSH | SWT.FILL);
        updateButton.setText("Update");
        updateButton.setToolTipText("Updates an existing T24 method");
        resetButton = new Button(buttons, SWT.PUSH | SWT.FILL);
        resetButton.setText(" Reset ");
        resetButton.setToolTipText("Resets the page");
        buttons.setLayout(buttonsLayout);
        buttonsLayout.makeColumnsEqualWidth = true;
        // Separator
        gridLayout.verticalSpacing = COLUMN_VERTICAL_SPACING;
        Label line = new Label(container, SWT.SEPARATOR | SWT.HORIZONTAL | SWT.BOLD);
        GridData lineData = new GridData(GridData.FILL_HORIZONTAL);
        lineData.horizontalSpan = 2;
        line.setLayoutData(lineData);
        // Table viewer - Column 1, row 2
        tableViewer = new TableViewer(container, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
        Table table = tableViewer.getTable();
        table.setEnabled(true);
        table.setHeaderVisible(true);
        table.setLinesVisible(true);
        GridData t24MethodData = new GridData(GridData.GRAB_HORIZONTAL);
        t24MethodData.minimumHeight = 150;
        t24MethodData.grabExcessVerticalSpace = true;
        t24MethodData.grabExcessHorizontalSpace = true;
        table.setLayoutData(t24MethodData);
        String rightColumnName = "Description";
        int rightColumnAlignment = SWT.CENTER | SWT.BOLD;
        TableColumn rightColumn = new TableColumn(table, rightColumnAlignment, 0);
        rightColumn.setText(rightColumnName);
        rightColumn.setWidth(COLUMN_RIGHT_WIDTH);
        String leftColumnName = "Method Name";
        int leftColumnAlignment = SWT.CENTER | SWT.BOLD;
        TableColumn leftColumn = new TableColumn(table, leftColumnAlignment, 0);
        leftColumn.setText(leftColumnName);
        leftColumn.setResizable(false);
        leftColumn.setWidth(COLUMN_LEFT_WIDTH);
        tableViewer.setContentProvider(new ArrayContentProvider());
        tableViewer.setLabelProvider(new T24MethodLabelProvider());
        // Edit, Delete buttons group - column 2, row 2
        buttons = new Group(container, SWT.VERTICAL);
        buttonsLayout = new GridLayout();
        buttonsLayout.numColumns = 1;
        buttons.setParent(container);
        editButton = new Button(buttons, SWT.PUSH | SWT.FILL);
        editButton.setText("  Edit  ");
        editButton.setToolTipText("Loads the selected method for amendments");
        deleteButton = new Button(buttons, SWT.PUSH | SWT.FILL);
        deleteButton.setText("Delete");
        deleteButton.setToolTipText("Removes the selected method from the T24Object");
        buttons.setLayout(buttonsLayout);
        buttonsLayout.makeColumnsEqualWidth = true;
    }

    private void addListeners() {
        page.getControl().addListener(SWT.Paint, this);
        t24MethodNameText.addListener(SWT.KeyUp, this);
        descriptionText.addListener(SWT.KeyUp, this);
        argumentsText.addListener(SWT.KeyUp, this);
        returnText.addListener(SWT.KeyUp, this);
        addButton.addListener(SWT.MouseUp, this);
        updateButton.addListener(SWT.MouseUp, this);
        resetButton.addListener(SWT.MouseUp, this);
        editButton.addListener(SWT.MouseUp, this);
        deleteButton.addListener(SWT.MouseUp, this);
        tableViewer.getTable().addListener(SWT.MouseDoubleClick, this);
        tableViewer.addSelectionChangedListener(new ISelectionChangedListener() {

            public void selectionChanged(SelectionChangedEvent event) {
                IStructuredSelection selection = (IStructuredSelection) event.getSelection();
                T24Method t24MethodSelected = (T24Method) selection.getFirstElement();
                t24MethodNameSelected = "";
                if (t24MethodSelected != null) {
                    t24MethodNameSelected = t24MethodSelected.getT24MethodName();
                }
            }
        });
        tableViewer.addDoubleClickListener(new IDoubleClickListener() {

            public void doubleClick(DoubleClickEvent event) {
                IStructuredSelection selection = (IStructuredSelection) event.getSelection();
                T24Method t24MethodSelected = (T24Method) selection.getFirstElement();
                t24MethodNameSelected = "";
                if (t24MethodSelected != null) {
                    t24MethodNameSelected = t24MethodSelected.getT24MethodName();
                }
            }
        });
    }

    public void handleEvent(Event event) {
        if (!t24Object.isFunctionSelected()) {
            returnText.setText("");
            returnText.setEnabled(false);
        } else {
            returnText.setEnabled(true);
        }
        if (event.widget.equals(container)) {
            return;
        }
        statusMap.put(event.widget, new Status(Status.OK, "not_used", 0, "", null));
        statusMap.put(addButton, new Status(Status.OK, "not_used", 0, "", null));
        statusMap.put(updateButton, new Status(Status.OK, "not_used", 0, "", null));
        if (event.widget instanceof Text) {
            if (event.widget == t24MethodNameText && t24MethodNameText.isEnabled()) {
                String t24MethodName = t24MethodNameText.getText();
                String errorMessage = null;
                if (t24MethodName.length() > 0) {
                    errorMessage = validateT24MethodName(t24MethodName);
                }
                if (errorMessage != null && errorMessage.length() > 0) {
                    statusMap.put(event.widget, new Status(Status.ERROR, "not_used", 0, errorMessage, null));
                }
            }
            if (event.widget == argumentsText) {
                String arguments = argumentsText.getText();
                String errorMessage = null;
                errorMessage = validateArguments(arguments);
                if (errorMessage != null && errorMessage.length() > 0) {
                    statusMap.put(event.widget, new Status(Status.ERROR, "not_used", 0, errorMessage, null));
                }
            }
            if (event.widget == returnText) {
                String returnVariable = returnText.getText();
                if (returnVariable.length() > 0 && !validateInput(returnVariable)) {
                    statusMap.put(event.widget, new Status(Status.ERROR, "not_used", 0, "Invalid return variable", null));
                }
            }
        }
        if (event.widget instanceof Button) {
            if (event.widget == addButton) {
                loadT24Method();
                if (!(t24Method.validate(t24Object.isFunctionSelected()))) {
                    statusMap.put(event.widget, new Status(Status.ERROR, "not_used", 0, t24Method.getErrorMessage(), null));
                } else if (getErrorMessage() == null) {
                    doAddT24Method();
                }
            }
            if (event.widget == updateButton) {
                loadT24Method();
                if (!(t24Method.validate(t24Object.isFunctionSelected()))) {
                    statusMap.put(event.widget, new Status(Status.ERROR, "not_used", 0, t24Method.getErrorMessage(), null));
                } else if (getErrorMessage() == null) {
                    doUpdateT24Method();
                }
            }
            if (event.widget == resetButton) {
                resetTextFields();
                resetAllButtons();
            }
            if (event.widget == editButton) {
                if (t24MethodNameSelected != null && t24Methods.containsKey(t24MethodNameSelected)) {
                    doEditT24Method();
                }
            }
            if (event.widget == deleteButton) {
                if (t24MethodNameSelected != null && t24Methods.containsKey(t24MethodNameSelected)) {
                    t24Object.removeT24Method(t24Methods.get(t24MethodNameSelected));
                    t24Methods.remove(t24MethodNameSelected);
                }
            }
        }
        if (event.widget == tableViewer.getTable()) {
            if (t24MethodNameSelected != null && t24MethodNameSelected.length() > 0) {
                doEditT24Method();
            }
        }
        updateStatusLine(event.widget);
        getWizard().getContainer().updateButtons();
        populateTableViewer();
    }

    /**
     * method resets all the active buttons
     */
    private void resetAllButtons() {
        addButton.setEnabled(true);
        updateButton.setEnabled(false);
        t24MethodNameText.setEnabled(true);
        tableViewer.getTable().setEnabled(true);
        if (t24Methods.isEmpty()) {
            editButton.setEnabled(false);
            deleteButton.setEnabled(false);
        } else {
            editButton.setEnabled(true);
            deleteButton.setEnabled(true);
        }
    }

    /**
     * execute sequence of activities when "Update" button is selected.
     */
    private void doUpdateT24Method() {
        doDeleteT24Method();
        doAddT24Method();
    }

    /**
     * execute sequence of activities when "Reset" button is selected.
     */
    private void doResetT24Method() {
        resetTextFields();
        initNewT24Method();
        resetAllButtons();
    }

    /**
     * execute sequence of activities when "Delete" button is selected.
     */
    private void doDeleteT24Method() {
        t24Object.removeT24Method(t24Methods.get(t24MethodNameSelected));
        t24Methods.remove(t24MethodNameSelected);
    }

    /**
     * execute sequence of activities when "Edit" button is selected.
     */
    private void doEditT24Method() {
        t24MethodNameText.setEnabled(false);
        addButton.setEnabled(false);
        updateButton.setEnabled(true);
        editButton.setEnabled(false);
        deleteButton.setEnabled(false);
        tableViewer.getTable().setEnabled(false);
        editT24Method();
    }

    /**
     * Method adds a new method to <code>T24Method</code> object
     */
    private void doAddT24Method() {
        t24Object.addT24Method(t24Method);
        t24Methods.put(t24Method.getT24MethodName(), t24Method);
        doResetT24Method();
    }

    /**
     * Method to loads text field values into a method object
     */
    private void loadT24Method() {
        initNewT24Method();
        t24Method.setT24MethodName(t24MethodNameText.getText());
        t24Method.setDescription(descriptionText.getText());
        t24Method.setReturnValue(returnText.getText());
        String[] arguments = argumentsText.getText().split(",");
        t24Method.setArguments(Collections.unmodifiableList(Arrays.asList(arguments)));
    }

    /**
     * Updates the status line with error message if any based on the state of
     * the widgets. It finds out the widget with error and throws the error.
     * 
     * @param currentWidget widget which is currently in use.
     */
    private void updateStatusLine(Widget currentWidget) {
        IStatus currentStatus = statusMap.get(currentWidget);
        if (currentStatus != null && currentStatus.getSeverity() == Status.ERROR) {
            setErrorMessage(currentStatus.getMessage());
            return;
        } else {
            setErrorMessage(null);
        }
        Widget severeWidget = findMostSevereWidget();
        if (severeWidget != null) {
            setErrorMessage(statusMap.get(severeWidget).getMessage());
        } else {
            setErrorMessage(null);
        }
        setPageComplete(isPageComplete());
    }

    /**
     * Finds the widget with most severe status.
     * 
     * @return Widget most severe widget
     */
    private Widget findMostSevereWidget() {
        Set<Widget> allWidgets = statusMap.keySet();
        IStatus widgetStatus = null;
        for (Widget severeWidget : allWidgets) {
            if (statusMap.containsKey(severeWidget)) {
                widgetStatus = statusMap.get(severeWidget);
                if (widgetStatus != null && widgetStatus.getSeverity() == Status.ERROR) {
                    return severeWidget;
                }
            }
        }
        return null;
    }

    /**
     * Validates the method name
     * 
     * @param t24MethodName
     * @return errorMessage based on the rules.
     */
    private String validateT24MethodName(String t24MethodName) {
        String firstChar = t24MethodName.substring(0, 1);
        if (firstChar.equals(firstChar.toUpperCase())) {
            return "T24 Method name must begin with lower case";
        }
        if (t24Methods.containsKey(t24MethodName)) {
            return "T24 Method " + t24MethodName + " already exists";
        }
        if (!validateInput(t24MethodName)) {
            return "T24 Method name must not contain invalid characters";
        }
        if (t24MethodName.length() < PluginConstants.T24METHOD_MIN_LENGTH) {
            return "T24 Method name must have minimum of " + PluginConstants.T24METHOD_MIN_LENGTH + " characters";
        }
        if (t24MethodName.length() > PluginConstants.T24METHOD_MAX_LENGTH) {
            return "T24 Method name must have maximum of " + PluginConstants.T24METHOD_MAX_LENGTH + " characters";
        }
        return null;
    }

    private String validateArguments(String arguments) {
        if (arguments.length() <= 0) {
            return null;
        }
        if (arguments.startsWith(",") || arguments.endsWith(",")) {
            return "Arguments must not start or end with comma";
        }
        String[] argArray = arguments.split(",");
        for (String arg : argArray) {
            if (arg != null && arg.length() <= 0) {
                return "Argument cannot be empty";
            }
            if (arg.substring(0, 1).matches("[A-Z0-9]")) {
                return "Argument must start with lower case";
            }
            if (!validateInput(arg)) {
                return "Argument contains invalid characters";
            }
        }
        return null;
    }

    private void initNewT24Method() {
        t24Method = new T24Method();
    }

    private void resetTextFields() {
        t24MethodNameText.setText("");
        descriptionText.setText("");
        argumentsText.setText("");
        returnText.setText("");
        statusMap.clear();
    }

    /**
     * Validates the given input such that input contains only alphanumeric
     * String starting with a lower case
     * 
     * @param element to be validated
     * @return true the element is valid. false otherwise.
     */
    private static boolean validateInput(String element) {
        if (!element.matches("[a-z]+[a-zA-Z0-9]*")) {
            return false;
        }
        return true;
    }

    /**
     * method to allow editing of an existing method
     */
    private void editT24Method() {
        t24Method = t24Methods.get(t24MethodNameSelected);
        if (t24Method == null) {
            // it should never happen, anyway
            return;
        }
        resetTextFields();
        t24MethodNameText.setText(t24Method.getT24MethodName());
        descriptionText.setText(t24Method.getDescription());
        returnText.setText(t24Method.getReturnValue());
        StringBuffer args = new StringBuffer();
        for (String arg : t24Method.getArguments()) {
            args.append(arg + ",");
        }
        argumentsText.setText(args.substring(0, args.length() - 1));
    }

    private void populateTableViewer() {
        tableViewer.setInput(t24Object.getT24Methods().toArray());
    }

    /**
     * If there is no error, set page complete as true
     */
    public boolean isPageComplete() {
        if (getErrorMessage() == null) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * populates label names (method name) and defines which columns are to be
     * displayed in table viewer
     */
    private class T24MethodLabelProvider extends LabelProvider implements ITableLabelProvider {

        /**
         * We return null, because we don't support images yet.
         * 
         * @see org.eclipse.jface.viewers.ITableLabelProvider#getColumnImage(java.lang.Object,
         *      int)
         */
        public Image getColumnImage(Object element, int columnIndex) {
            return null;
        }

        /**
         * @see org.eclipse.jface.viewers.ITableLabelProvider#getColumnText(java.lang.Object,
         *      int)
         */
        public String getColumnText(Object element, int columnIndex) {
            if (element instanceof T24Method) {
                T24Method t24Method = (T24Method) element;
                switch (columnIndex) {
                    case 0:
                        return t24Method.getT24MethodName();
                    case 1:
                        return t24Method.getDescription();
                }
            }
            return "";
        }
    }
}
