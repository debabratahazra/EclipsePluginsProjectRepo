package com.temenos.t24.tools.eclipse.basic.wizards.docgeneration;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Text;

import com.temenos.t24.tools.eclipse.basic.document.util.DocumentFileUtil;
import com.temenos.t24.tools.eclipse.basic.help.IT24FileHelper;
import com.temenos.t24.tools.eclipse.basic.wizards.docgeneration.file.GenerateDocConstants;

/**
 * Wizard page which holds the user interface components to help out strip out
 * comments from basic file
 * 
 * @author sbharathraja
 * 
 */
public class GenerateCommentsWizardPage extends WizardPage {

    private Text textTargetLocation;
    private List listAvailableProjects;
    private List listSelectedProjects;
    private Button buttonAdd;
    private Button buttonAddAll;
    private Button buttonRemove;
    private String inputPath;

    /**
     * Create the wizard.
     */
    public GenerateCommentsWizardPage() {
        super("wizardPage");
        setPageComplete(false);
        setTitle("Generate Comments Wizard");
        setDescription("Wizard which initiate the process of strip out the comments from basic file and store it in target folder.");
    }

    /**
     * Create contents of the wizard.
     * 
     * @param parent
     */
    public void createControl(Composite parent) {
        Composite container = new Composite(parent, SWT.NULL);
        buildComponents(container);
        setControl(container);
        generateAvailableProjects();
        generateTargetLocation();
        addListeners();
    }

    /**
     * setting layout for composite container and build the components
     * 
     * @param container - composite
     */
    private void buildComponents(Composite container) {
        // setting grid layout to container composite
        container.setLayout(new GridLayout(1, false));
        // composite which going to have list and buttons
        Composite compositeForActions = new Composite(container, SWT.NONE);
        // setting grid layout to actions composite
        compositeForActions.setLayout(new GridLayout(3, true));
        // grid data for action composite
        GridData compositeForActions_gridData = new GridData(SWT.LEFT, SWT.CENTER, false, true, 1, 1);
        compositeForActions_gridData.heightHint = 225;
        compositeForActions_gridData.widthHint = 573;
        // setting grid data for action composite
        compositeForActions.setLayoutData(compositeForActions_gridData);
        // group which going to have available list
        Group groupAvailList = new Group(compositeForActions, SWT.NONE);
        groupAvailList.setText("Available Project List");
        // setting fill layout for available list group
        groupAvailList.setLayout(new FillLayout(SWT.HORIZONTAL));
        // grid data for available list group
        GridData groupAvailList_gridData = new GridData(SWT.LEFT, SWT.CENTER, false, true, 1, 1);
        groupAvailList_gridData.heightHint = 194;
        groupAvailList_gridData.widthHint = 169;
        // setting grid data for available list group
        groupAvailList.setLayoutData(groupAvailList_gridData);
        // initialize the available list
        listAvailableProjects = new List(groupAvailList, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.MULTI);
        // group for collection of buttons
        Group groupActions = new Group(compositeForActions, SWT.NONE);
        groupActions.setText("Actions");
        // grid data for button group
        GridData groupActions_gridData = new GridData(SWT.LEFT, SWT.CENTER, false, true, 1, 1);
        groupActions_gridData.heightHint = 136;
        groupActions_gridData.widthHint = 172;
        // setting grid data for button group
        groupActions.setLayoutData(groupActions_gridData);
        // initialize the add button
        buttonAdd = new Button(groupActions, SWT.FLAT);
        buttonAdd.setBounds(45, 21, 86, 23);
        buttonAdd.setText("Add    -->");
        // initialize the add all button
        buttonAddAll = new Button(groupActions, SWT.NONE);
        buttonAddAll.setBounds(45, 64, 86, 23);
        buttonAddAll.setText("Add All  -->");
        // initialize the remove button
        buttonRemove = new Button(groupActions, SWT.NONE);
        buttonRemove.setBounds(45, 106, 86, 23);
        buttonRemove.setText("<--  Remove");
        // group for selected project list
        Group groupSelectedList = new Group(compositeForActions, SWT.NONE);
        // grid data for selected project list group
        GridData groupSelectedList_gridData = new GridData(SWT.LEFT, SWT.CENTER, false, true, 1, 1);
        groupSelectedList_gridData.heightHint = 194;
        groupSelectedList_gridData.widthHint = 169;
        // setting grid data for selected project list group
        groupSelectedList.setLayoutData(groupSelectedList_gridData);
        groupSelectedList.setText("Selected Project List");
        // setting fill layout for selected project list group
        groupSelectedList.setLayout(new FillLayout(SWT.HORIZONTAL));
        // initialize the selected project list
        listSelectedProjects = new List(groupSelectedList, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.MULTI);
        // composite for target location
        Composite compositeForTarget = new Composite(container, SWT.NONE);
        // row layout for target location composite
        RowLayout compositeForTargetLayout = new RowLayout(SWT.HORIZONTAL);
        compositeForTargetLayout.fill = true;
        // setting row layout for target location composite
        compositeForTarget.setLayout(compositeForTargetLayout);
        // grid data for target location composite
        GridData compositeForTarget_gridData = new GridData(SWT.LEFT, SWT.CENTER, false, true, 1, 1);
        compositeForTarget_gridData.heightHint = 35;
        compositeForTarget_gridData.widthHint = 574;
        // setting grid data for target location composite
        compositeForTarget.setLayoutData(compositeForTarget_gridData);
        // initialize the target location label
        Label labelTargetLocation = new Label(compositeForTarget, SWT.NONE);
        // setting layout data for target location label
        labelTargetLocation.setLayoutData(new RowData(91, SWT.DEFAULT));
        labelTargetLocation.setText("Target Location : ");
        // initialize the target location text
        textTargetLocation = new Text(compositeForTarget, SWT.BORDER);
        textTargetLocation.setEditable(false);
        // setting layout data for target location text
        textTargetLocation.setLayoutData(new RowData(438, SWT.DEFAULT));
    }

    /**
     * generate available project from current workspace and list out in
     * listAvailableProject list.
     */
    private void generateAvailableProjects() {
        IWorkspaceRoot workspaceRoot = ResourcesPlugin.getWorkspace().getRoot();
        inputPath = workspaceRoot.getLocation().toOSString();
        IProject[] projects = workspaceRoot.getProjects();
        for (IProject project : projects) {
            // ignore the t24 document directory and temporary directory
            if (!project.getName().equalsIgnoreCase(GenerateDocConstants.NAME_OF_T24DOC_DIR)
                    && !project.getName().equalsIgnoreCase(IT24FileHelper.NAME_OF_TEMP_FOLDER))
                listAvailableProjects.add(project.getName());
        }
    }

    /**
     * generate the target location path. The logic behind this is the path
     * should be an T24 Doc location provided by user in preferences page, is
     * the root folder under that the folder named as "inlinedoc" which is
     * stored as constants under {@link GenerateDocConstants}
     * 
     */
    private void generateTargetLocation() {
        /** t24 doc location given by user in preference page */
        String t24DocLocation = DocumentFileUtil.getRoot();
        if (!t24DocLocation.equalsIgnoreCase("")) {
            t24DocLocation += GenerateDocConstants.PATH_SEPARATOR + GenerateDocConstants.NAME_OF_STRIPPED_FOLDER;
            textTargetLocation.setText(t24DocLocation);
        }
    }

    /**
     * listen components
     */
    private void addListeners() {
        GCComponentListener compListener = new GCComponentListener(this, listAvailableProjects, listSelectedProjects);
        compListener.listenAddButton(buttonAdd);
        compListener.listenAddAllButton(buttonAddAll);
        compListener.listenSelectedList();
        compListener.listenRemoveButton(buttonRemove);
    }

    /**
     * get the selected projects
     * 
     * @return - array of selected projects
     */
    public String[] getProjects() {
        return listSelectedProjects.getItems();
    }

    /**
     * get the target path
     * 
     * @return
     */
    public String getTargetPath() {
        return textTargetLocation.getText();
    }

    /**
     * get the file input path
     * 
     * @return
     */
    public String getInputPath() {
        return inputPath;
    }

    @Override
    public boolean isPageComplete() {
        if (listSelectedProjects.getItemCount() > 0 && !textTargetLocation.getText().equalsIgnoreCase(""))
            return true;
        else
            return false;
    }
}
