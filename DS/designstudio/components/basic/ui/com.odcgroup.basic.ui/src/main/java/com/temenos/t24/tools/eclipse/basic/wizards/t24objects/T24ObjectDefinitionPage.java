package com.temenos.t24.tools.eclipse.basic.wizards.t24objects;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Widget;

import com.temenos.t24.tools.eclipse.basic.PluginConstants;

public class T24ObjectDefinitionPage extends WizardPage implements Listener {

    Text t24ObjectNameText = null;
    Text packageNameText = null;
    Text authorNameText = null;
    Text commentText = null;
    private Label t24ObjectNameLabel = null;
    private Label packageNameLabel = null;
    private Label authorNameLabel = null;
    private Label commentNameLabel = null;
    private Label objectTypeLabel = null;
    private String t24ObjectName = "";
    private String packageName = "";
    private String authorName = "";
    private String comments = "";
    Button functionButton = null;
    Button subRoutineButton = null;
    private static final int LAYOUT_NUMBER_OF_COLUMNS = 2;
    public T24Object t24Object = new T24Object();
    private Map<Widget, IStatus> errorStatusMap = new HashMap<Widget, IStatus>();
    String defaultpackage = "com.temenos.t24.t24object";
    String defaultpackagePrefix = "com.temenos.t24.t24object.";

    public T24ObjectDefinitionPage() {
        super("T24ObjectDefinitionPage");
        setTitle("T24 Object Creation wizard");
        setDescription("Create a new T24 Object");
    }

    public void createControl(Composite parent) {
        Composite composite = new Composite(parent, SWT.NULL);
        GridLayout gridLayout = new GridLayout();
        gridLayout.numColumns = LAYOUT_NUMBER_OF_COLUMNS;
        composite.setLayout(gridLayout);
        GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
        t24ObjectNameLabel = new Label(composite, SWT.NONE);
        t24ObjectNameLabel.setText("T24Object Name:");
        t24ObjectNameLabel.setToolTipText("Name of the T24 Object. e.g. T24Date");
        t24ObjectNameText = new Text(composite, SWT.BORDER);
        t24ObjectNameText.setLayoutData(gridData);
        packageNameLabel = new Label(composite, SWT.NONE);
        packageNameLabel.setText("Package:");
        packageNameLabel.setToolTipText("Name of the Package. e.g. com.temenos.t24.t24object.date");
        packageNameText = new Text(composite, SWT.BORDER);
        packageNameText.setLayoutData(gridData);
        packageNameText.setText(defaultpackage);
        authorNameLabel = new Label(composite, SWT.NONE);
        authorNameLabel.setText("Author:");
        authorNameLabel.setToolTipText("Name of the Author. It can be your name or mail id.");
        authorNameText = new Text(composite, SWT.BORDER);
        authorNameText.setLayoutData(gridData);
        createLine(composite, LAYOUT_NUMBER_OF_COLUMNS);
        GridData gridData1 = new GridData(GridData.FILL_HORIZONTAL);
        gridData1.horizontalSpan = LAYOUT_NUMBER_OF_COLUMNS;
        objectTypeLabel = new Label(composite, SWT.NONE);
        objectTypeLabel.setText("Object Type:");
        objectTypeLabel.setToolTipText("Type of T24 Object");
        objectTypeLabel.setLayoutData(gridData1);
        functionButton = new Button(composite, SWT.RADIO);
        functionButton.setText("Function");
        functionButton.setToolTipText("Creates T24 Function");
        functionButton.setSelection(true);
        subRoutineButton = new Button(composite, SWT.RADIO);
        subRoutineButton.setText("Subroutine");
        subRoutineButton.setToolTipText("Creates T24 Subroutine");
        createLine(composite, LAYOUT_NUMBER_OF_COLUMNS);
        commentNameLabel = new Label(composite, SWT.TOP);
        commentNameLabel.setText("Comments:");
        commentNameLabel.setToolTipText("Description about the T24 Object");
        commentText = new Text(composite, SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
        GridData gridData3 = new GridData(GridData.FILL_HORIZONTAL);
        gridData3.verticalAlignment = SWT.FILL;
        gridData3.grabExcessVerticalSpace = true;
        commentText.setLayoutData(gridData3);
        setControl(composite);
        addListeners();
    }

    private void createLine(Composite parent, int ncol) {
        Label line = new Label(parent, SWT.SEPARATOR | SWT.HORIZONTAL | SWT.BOLD);
        GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
        gridData.horizontalSpan = ncol;
        line.setLayoutData(gridData);
    }

    private void addListeners() {
        t24ObjectNameText.addListener(SWT.KeyUp, this);
        packageNameText.addListener(SWT.TAB, this);
        packageNameText.addListener(SWT.KeyUp, this);
        authorNameText.addListener(SWT.KeyUp, this);
        commentText.addListener(SWT.KeyUp, this);
        functionButton.addListener(SWT.Selection, this);
        subRoutineButton.addListener(SWT.Selection, this);
    }

    public void handleEvent(Event event) {
        if (t24Object.getT24Methods().size() >= 1) {
            functionButton.setEnabled(false);
            subRoutineButton.setEnabled(false);
        } else {
            functionButton.setEnabled(true);
            subRoutineButton.setEnabled(true);
        }
        errorStatusMap.put(event.widget, new Status(Status.OK, "not_used", 0, "", null));
        if (event.widget == t24ObjectNameText) {
            t24ObjectName = t24ObjectNameText.getText();
            if (t24ObjectName.length() > 0) {
                if (isValidT24ObjectName(t24ObjectName)) {
                    char firstCharacter = t24ObjectName.charAt(0);
                    if (!isUpper(firstCharacter)) {
                        errorStatusMap.put(event.widget, new Status(Status.ERROR, "not_used", 0,
                                "T24Object name must begin with uppercase", null));
                    } else if (t24ObjectName.length() < PluginConstants.T24OBJECT_MIN_LENGTH) {
                        errorStatusMap.put(event.widget, new Status(Status.ERROR, "not_used", 0,
                                "T24Object name must have mininimum of " + PluginConstants.T24OBJECT_MIN_LENGTH + " characters",
                                null));
                    } else if (t24ObjectName.length() > PluginConstants.T24OBJECT_MAX_LENGTH) {
                        errorStatusMap.put(event.widget,
                                new Status(Status.ERROR, "not_used", 0, "T24Object name must have maximum of "
                                        + PluginConstants.T24OBJECT_MAX_LENGTH + " characters", null));
                    } else if (t24ObjectName.equals(t24ObjectName.toUpperCase())
                            || t24ObjectName.equals(t24ObjectName.toLowerCase())) {
                        errorStatusMap.put(event.widget, new Status(Status.ERROR, "not_used", 0,
                                "T24Object name must be mixed case", null));
                    }
                } else {
                    errorStatusMap.put(event.widget, new Status(Status.ERROR, "not_used", 0, "Invalid T24Object name", null));
                }
            }
            t24Object.setT24ObjectName(t24ObjectName);
            getNextPage().setTitle("T24 Method definition for " + t24ObjectName);
        }
        if (event.widget == packageNameText) {
            packageName = packageNameText.getText();
            if (packageName.length() > 0) {
                if (isValidPackageName(packageName)) {
                    if (packageName.equals(defaultpackage) || packageName.startsWith(defaultpackagePrefix)) {
                        if (packageName.endsWith(".")) {
                            errorStatusMap.put(event.widget, new Status(Status.ERROR, "not_used", 0,
                                    "Package name must not end with .", null));
                        }
                    } else {
                        errorStatusMap.put(event.widget, new Status(Status.ERROR, "not_used", 0, "Package name must begin "
                                + "with com.temenos.t24.t24object.", null));
                    }
                } else {
                    errorStatusMap.put(event.widget, new Status(Status.ERROR, "not_used", 0, "Invalid Package name", null));
                }
            }
            t24Object.setPackageName(packageName);
        }
        if (event.widget == authorNameText) {
            authorName = authorNameText.getText();
            if ((authorName.length() > 0 && !isValidAuthorName(authorName)) || authorName.startsWith(" ")
                    || authorName.startsWith("@") || authorName.startsWith(".") || authorName.endsWith(" ")
                    || authorName.endsWith("@") || authorName.endsWith(".")) {
                errorStatusMap.put(event.widget, new Status(Status.ERROR, "not_used", 0, "Invalid author name", null));
            }
            t24Object.setAuthorName(authorName);
        }
        if (event.widget == commentText) {
            comments = commentText.getText();
            t24Object.setComments(comments);
        }
        if (event.widget instanceof Button) {
            if (event.widget == functionButton) {
                t24Object.setFunctionSelected(true);
                commentText.setEnabled(true);
            }
            if (event.widget == subRoutineButton) {
                t24Object.setFunctionSelected(false);
                commentText.setText("");
                commentText.setEnabled(false);
            }
        }
        updateStatusLine(event.widget);
    }

    private void updateStatusLine(Widget currentWidget) {
        IStatus currentStatus = errorStatusMap.get(currentWidget);
        if (currentStatus.getSeverity() == Status.ERROR) {
            setErrorMessage(currentStatus.getMessage());
        } else {
            setErrorMessage(null);
        }
        Widget severeWidget = findMostSevereWidget();
        if (severeWidget != null) {
            setErrorMessage(errorStatusMap.get(severeWidget).getMessage());
        } else {
            setErrorMessage(null);
        }
        getWizard().getContainer().updateButtons();
    }

    private Widget findMostSevereWidget() {
        Set<Widget> allWidgets = errorStatusMap.keySet();
        for (Widget widgetName : allWidgets) {
            if (errorStatusMap.get(widgetName).getSeverity() == Status.ERROR) {
                return widgetName;
            }
        }
        return null;
    }

    public boolean canFlipToNextPage() {
        if (getErrorMessage() != null) {
            return false;
        }
        if (!t24Object.validate()) {
            return false;
        } else {
            return true;
        }
    }

    public T24Object getT24Object() {
        return t24Object;
    }

    public void init(IStructuredSelection selection) {
        return;
    }

    private boolean isUpper(char c) {
        if (c >= 65 && c <= 90)
            return true;
        else
            return false;
    }

    private boolean isValidT24ObjectName(String str) {
        boolean isValidT24ObjectName = false;
        char chr[] = null;
        if (str != null)
            chr = str.toCharArray();
        for (int i = 0; i < chr.length; i++) {
            if ((chr[i] >= 'A' && chr[i] <= 'Z') || (chr[i] >= 'a' && chr[i] <= 'z') || (chr[i] >= '0' && chr[i] <= '9')) {
                isValidT24ObjectName = true;
                continue;
            } else {
                isValidT24ObjectName = false;
                break;
            }
        }
        return isValidT24ObjectName;
    }

    private boolean isValidPackageName(String str) {
        boolean isValidPackageName = false;
        char chr[] = null;
        if (str != null)
            chr = str.toCharArray();
        for (int i = 0; i < chr.length; i++) {
            if ((chr[i] >= 'a' && chr[i] <= 'z') || chr[i] == '.' || (chr[i] >= '0' && chr[i] <= '9')) {
                isValidPackageName = true;
                continue;
            } else {
                isValidPackageName = false;
                break;
            }
        }
        return isValidPackageName;
    }

    private boolean isValidAuthorName(String str) {
        boolean isValidAuthorName = false;
        char chr[] = null;
        if (str != null)
            chr = str.toCharArray();
        for (int i = 0; i < chr.length; i++) {
            if ((chr[i] >= 'a' && chr[i] <= 'z') || (chr[i] >= 'A' && chr[i] <= 'Z') || chr[i] == ' ' || chr[i] == '.'
                    || chr[i] == '@') {
                isValidAuthorName = true;
                continue;
            } else {
                isValidAuthorName = false;
                break;
            }
        }
        return isValidAuthorName;
    }
}
