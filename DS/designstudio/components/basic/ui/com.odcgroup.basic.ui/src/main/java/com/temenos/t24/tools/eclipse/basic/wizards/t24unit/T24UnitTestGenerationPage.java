package com.temenos.t24.tools.eclipse.basic.wizards.t24unit;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Widget;

import com.temenos.t24.tools.eclipse.basic.t24unit.T24UnitTestConstants;
import com.temenos.t24.tools.eclipse.basic.utils.EclipseUtil;
import com.temenos.t24.tools.eclipse.basic.utils.StringUtil;

/**
 * T24 Unit test code generation wizard page
 * 
 * @author ssethupathi
 * 
 */
public class T24UnitTestGenerationPage extends WizardPage implements Listener {

    private IStructuredSelection selection;
    private Text testFileNameText;
    private Text testDirNameText;
    // private Text subroutineNameText;
    private Button browseButton;
    private Composite container;
    /** status map */
    private Map<Widget, IStatus> statusMap = new HashMap<Widget, IStatus>();

    /**
     * Constructs this page object
     * 
     * @param selection
     */
    public T24UnitTestGenerationPage(IStructuredSelection selection) {
        super("TUnitTestGenerationWizard");
        super.setDescription("Generates test code");
        this.selection = selection;
    }

    /**
     * {@inheritDoc}
     */
    public void createControl(Composite parent) {
        container = parent;
        drawLayouts(parent);
    }

    /**
     * Builds up page by layouts and components
     * 
     * @param container
     */
    private void drawLayouts(Composite container) {
        final int TEXT_MIN_WIDTH = 450;
        final String TEST_DIR_TOOLTIP = "Folder where T24 Unit test will be stored";
        final String TEST_FILE_TOOLTIP = "Name of the T24 Unit test file";
        GridLayout gridLayout0 = new GridLayout();
        gridLayout0.makeColumnsEqualWidth = true;
        GridData gridData0 = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
        gridData0.grabExcessHorizontalSpace = true;
        container.setLayout(gridLayout0);
        container.setLayoutData(gridData0);
        //
        Composite child = new Composite(container, SWT.NORMAL);
        GridLayout gridLayout = new GridLayout();
        gridLayout.numColumns = 3;
        child.setLayout(gridLayout);
        //
        GridData gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
        gridData.grabExcessHorizontalSpace = true;
        gridData.minimumWidth = 390;
        gridData.horizontalIndent = 15;
        //
        Label testDirNameLabel = new Label(child, SWT.NORMAL);
        testDirNameLabel.setText("T24 Unit test folder");
        testDirNameLabel.setToolTipText(TEST_DIR_TOOLTIP);
        testDirNameText = new Text(child, SWT.BORDER);
        testDirNameText.setLayoutData(gridData);
        testDirNameText.setEditable(false);
        browseButton = new Button(child, SWT.PUSH);
        browseButton.setText("Browse..");
        //
        Composite child0 = new Composite(container, SWT.NORMAL);
        GridLayout gridLayout1 = new GridLayout();
        gridLayout1.numColumns = 2;
        child0.setLayout(gridLayout1);
        //
        GridData gridData1 = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
        gridData1.grabExcessHorizontalSpace = true;
        gridData1.minimumWidth = TEXT_MIN_WIDTH;
        //
        Label testFileNameLabel = new Label(child0, SWT.NORMAL);
        testFileNameLabel.setText("T24 Unit test file name");
        testFileNameLabel.setToolTipText(TEST_FILE_TOOLTIP);
        testFileNameText = new Text(child0, SWT.BORDER);
        testFileNameText.setLayoutData(gridData1);
        // Subroutine under test field - here for future use
        // Label componentNameLabel = new Label(child0, SWT.NORMAL);
        // componentNameLabel.setText("Subroutine name");
        // subroutineNameText = new Text(child0, SWT.BORDER);
        // subroutineNameText.setLayoutData(gridData1);
        container.pack();
        setControl(container);
        addListeners();
        initWizard();
    }

    /**
     * Adds listeners to the components
     */
    private void addListeners() {
        testDirNameText.addListener(SWT.KeyUp, this);
        testFileNameText.addListener(SWT.KeyUp, this);
        // subroutineNameText.addListener(SWT.KeyUp, this);
        browseButton.addListener(SWT.MouseUp, this);
    }

    /**
     * Initializes the wizard
     */
    private void initWizard() {
        if (selection == null || !(selection instanceof IStructuredSelection)) {
            return;
        }
        Object itemSelected = selection.getFirstElement();
        String subroutineName = null;
        String testDirName = null;
        if (itemSelected instanceof IProject) {
            testDirName = ((IProject) itemSelected).getLocation().toOSString();
            if (testDirName != null && testDirName.length() > 0) {
                testDirNameText.setText(testDirName);
            }
            return;
        }
        if (itemSelected instanceof IFolder) {
            testDirName = ((IFolder) itemSelected).getLocation().toOSString();
            if (testDirName != null && testDirName.length() > 0) {
                testDirNameText.setText(testDirName);
            }
            return;
        }
        if (itemSelected instanceof IFile) {
            subroutineName = ((IFile) itemSelected).getName();
            if (subroutineName != null && subroutineName.length() > 0) {
                testFileNameText.setText(StringUtil.removeBasicExtension(subroutineName) + "."
                        + T24UnitTestConstants.T24UNIT_TEST_PART);
            }
            return;
        }
    }

    /**
     * Handles the event of the components
     */
    public void handleEvent(Event event) {
        final String INVALID_FILE_ERR = "Invalid file name. File name must be alphanumeric starting with a capital letter";
        statusMap.put(event.widget, new Status(Status.OK, "not_used", 0, "", null));
        //
        if (event.widget == browseButton) {
            DirectoryDialog dialog = new DirectoryDialog(container.getShell(), SWT.OPEN | SWT.MULTI);
            String cd = EclipseUtil.getLocalCurDirectory(true);
            dialog.setFilterPath(cd);
            String file = dialog.open();
            testDirNameText.setText(file);
        }
        if (event.widget == testDirNameText) {
            // do nothing
        }
        if (event.widget == testFileNameText) {
            String name = testFileNameText.getText();
            if (!validSubroutineName(name)) {
                statusMap.put(event.widget, new Status(Status.ERROR, "not_used", 0, INVALID_FILE_ERR, null));
            }
            if (!obeyTestRules(name) || (name.length() < 4)) {
                statusMap.put(event.widget, new Status(Status.ERROR, "not_used", 0, "Test file name should contain "
                        + T24UnitTestConstants.T24UNIT_TEST_PART, null));
            }
        }
        // if (event.widget == subroutineNameText) {
        // if (!validSubroutineName(subroutineNameText.getText())) {
        // statusMap.put(event.widget, new Status(Status.ERROR, "not_used", 0,
        // INVALID_FILE_ERR, null));
        // }
        // }
        updateStatusLine(event.widget);
        getWizard().getContainer().updateButtons();
    }

    private boolean validSubroutineName(String name) {
        if (name == null) {
            return false;
        }
        if (!name.matches("[A-Z]+[a-zA-Z0-9.]*")) {
            return false;
        }
        return true;
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
     * If there is no error, set page complete as true
     */
    public boolean isPageComplete() {
        if (getErrorMessage() == null && inputComplete()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Checks whether the page has completed with data
     * 
     * @return true if the page is completed false otherwise
     */
    private boolean inputComplete() {
        String testFileName = testFileNameText.getText();
        String testDirName = testDirNameText.getText();
        // String subroutineName = subroutineNameText.getText();
        if (testFileName != null && testFileName.length() > 0 && testDirName != null && testDirName.length() > 0) {
            // && subroutineName != null && subroutineName.length() > 0) {
            return true;
        }
        return false;
    }

    /**
     * Checks to see if the test file name obeys to the rules
     * 
     * @param fileName
     * @return true if the name follows the rules false otherwise
     */
    private boolean obeyTestRules(String fileName) {
        if (fileName == null || fileName.length() <= 0) {
            return false;
        }
        if (!fileName.contains(T24UnitTestConstants.T24UNIT_TEST_PART)) {
            return false;
        }
        return true;
    }

    /**
     * Returns the test file name
     * 
     * @return test file name
     */
    public String getTestFileName() {
        return testFileNameText.getText();
    }

    /**
     * Returns the test file directory
     * 
     * @return test directory name
     */
    public String getTestDirName() {
        return testDirNameText.getText();
    }
    /**
     * Returns the subroutine under test
     * 
     * @return name of the subroutine under test
     */
    // public String getSubroutineName() {
    // return subroutineNameText.getText();
    // }
}
