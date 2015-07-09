package com.temenos.t24.tools.eclipse.basic.wizards.rtc.install;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.ArrayContentProvider;
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
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;

import com.ibm.team.repository.client.ITeamRepository;

/**
 * 
 * @author ssethupathi
 * 
 */
public class InstallChangeSetPage extends WizardPage implements Listener {

    private Text workitemReferenceText;
    private Button scanButton;
    private Button includeChildrenButton;
    private Label reportLabel;
    private Composite container;
    private TableViewer repositoryTableViewer;
    private String repositoryURISelected;
    private InstallChangeSetWizard wizard;
    private boolean proceedToNextPage = false;
    private ChangesetInstallerViewController viewController = null;
    private ChangeSetInstallManager csInstallManager = null;

    /**
     * Constructs this page object
     * 
     * @param selection
     */
    public InstallChangeSetPage(InstallChangeSetWizard wizard) {
        super("InstallChangeSetPage");
        this.wizard = wizard;
        super.setDescription("Searches for Change Sets");
    }

    /**
     * {@inheritDoc}
     */
    public void createControl(Composite parent) {
        container = new Composite(parent, SWT.NULL);
        drawLayouts(container);
        setControl(container);
    }

    /**
     * Builds up page by layouts and components
     * 
     * @param container
     */
    private void drawLayouts(Composite container) {
        GridLayout layout = new GridLayout();
        GridData gridData0 = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
        layout.makeColumnsEqualWidth = true;
        gridData0.grabExcessHorizontalSpace = true;
        container.setLayout(layout);
        container.setLayoutData(gridData0);
        //
        Composite one = new Composite(container, SWT.NORMAL);
        GridLayout gridLayout_one = new GridLayout();
        gridLayout_one.numColumns = 1;
        one.setLayout(gridLayout_one);
        Label repositoryLabel = new Label(one, SWT.NORMAL);
        repositoryLabel.setText("Select repository");
        repositoryTableViewer = new TableViewer(one, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
        Table table = repositoryTableViewer.getTable();
        table.setEnabled(true);
        table.setHeaderVisible(false);
        table.setLinesVisible(false);
        int columnAlignment = SWT.CENTER | SWT.BOLD;
        TableColumn column = new TableColumn(table, columnAlignment, 0);
        column.setWidth(400);
        repositoryTableViewer.setContentProvider(new ArrayContentProvider());
        repositoryTableViewer.setLabelProvider(new RepositoryLabelProvider());
        ITeamRepository[] repositories = ChangeSetInstallManager.getRepositories();
        repositoryTableViewer.setInput(repositories);
        if (repositories != null && repositories.length > 0) {
            repositoryTableViewer.getTable().select(0);
            repositoryURISelected = repositories[0].getRepositoryURI();
        }
        GridData gridData_one = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
        layout.makeColumnsEqualWidth = true;
        gridData_one.grabExcessHorizontalSpace = true;
        one.setLayoutData(gridData_one);
        Composite child = new Composite(container, SWT.NORMAL);
        GridLayout gridLayout = new GridLayout();
        gridLayout.numColumns = 3;
        child.setLayout(gridLayout);
        //
        Composite three = new Composite(container, SWT.NORMAL);
        GridLayout layoutThree = new GridLayout();
        layoutThree.numColumns = 2;
        includeChildrenButton = new Button(three, SWT.CHECK | SWT.LEFT);
        includeChildrenButton.setSelection(false);
        three.setLayout(layoutThree);
        Label includeChildrenLable = new Label(three, SWT.NORMAL);
        includeChildrenLable.setText("Include Change Sets of child Work Items");
        //
        GridData gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
        gridData.grabExcessHorizontalSpace = true;
        gridData.minimumWidth = 250;
        gridData.horizontalIndent = 15;
        Label workitemReferenceLabel = new Label(child, SWT.NORMAL);
        workitemReferenceLabel.setText("Workitem reference");
        workitemReferenceText = new Text(child, SWT.BORDER);
        workitemReferenceText.setLayoutData(gridData);
        scanButton = new Button(child, SWT.PUSH);
        scanButton.setText("Search");
        scanButton.setEnabled(false);
        //
        Composite child1 = new Composite(container, SWT.NORMAL);
        GridLayout gridLayout1 = new GridLayout();
        gridLayout1.numColumns = 1;
        child1.setLayout(gridLayout1);
        //
        GridData gridData1 = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
        gridData1.grabExcessHorizontalSpace = true;
        gridData1.minimumWidth = 500;
        gridData1.horizontalIndent = 15;
        reportLabel = new Label(child1, SWT.NORMAL);
        reportLabel.setLayoutData(gridData1);
        addListeners();
    }

    /**
     * Adds listeners to the components
     */
    private void addListeners() {
        workitemReferenceText.addListener(SWT.KeyUp, this);
        scanButton.addListener(SWT.MouseUp, this);
        repositoryTableViewer.addSelectionChangedListener(new ISelectionChangedListener() {

            public void selectionChanged(SelectionChangedEvent event) {
                IStructuredSelection selection = (IStructuredSelection) event.getSelection();
                ITeamRepository iTeamRepository = (ITeamRepository) selection.getFirstElement();
                repositoryURISelected = iTeamRepository.getRepositoryURI();
                refreshScanButton();
            }
        });
    }

    /**
     * Handles the event of the components
     */
    public void handleEvent(Event event) {
        setErrorMessage(null);
        if (event.widget == workitemReferenceText) {
            if (!isWorkItemRefValid()) {
                setErrorMessage("Invalid Work Item number");
            }
            refreshScanButton();
        }
        if (event.widget == scanButton) {
            int workItemReference = Integer.parseInt(workitemReferenceText.getText());
            scanButton.setEnabled(false);
            wizard.setWorkItemRef(workItemReference);
            wizard.setRepositoryURI(repositoryURISelected);
            reportLabel.setText("Searching for Change sets...");
            csInstallManager = new ChangeSetInstallManager(wizard.getRepositoryURI(), wizard.getWorkItemRef());
            List<T24ChangeSet> changeSets = new ArrayList<T24ChangeSet>();
            if (includeChildrenButton.getSelection()) {
                changeSets = csInstallManager.getAllChangeSets();
            } else {
                changeSets = csInstallManager.getDirectChangeSets();
            }
            int changeSetCount = changeSets.size();
            if (changeSetCount > 0) {
                reportLabel.setText(changeSetCount + " Change sets found. Click Next to proceed.");
                viewController = new ChangesetInstallerViewController(changeSets);
                ((ChangeSetTreePage) getNextPage()).setInput(viewController.getInput());
                proceedToNextPage = true;
            } else {
                reportLabel.setText("No Change set found for the workitem reference " + workItemReference);
                proceedToNextPage = false;
            }
            scanButton.setEnabled(true);
            setPageComplete(isPageComplete());
        }
    }

    private void refreshScanButton() {
        if (!isWorkItemRefValid() || repositoryURISelected == null) {
            scanButton.setEnabled(false);
        } else {
            scanButton.setEnabled(true);
        }
    }

    private boolean isWorkItemRefValid() {
        String text = workitemReferenceText.getText();
        if (text != null && text.length() > 0 && text.matches("[0-9]*")) {
            return true;
        }
        return false;
    }

    public boolean isPageComplete() {
        if (proceedToNextPage && getErrorMessage() == null) {
            return true;
        }
        return false;
    }

    public boolean canFlipToNextPage() {
        return isPageComplete();
    }

    private class RepositoryLabelProvider extends LabelProvider implements ITableLabelProvider {

        public Image getColumnImage(Object element, int columnIndex) {
            return null;
        }

        public String getColumnText(Object element, int columnIndex) {
            if (element instanceof ITeamRepository) {
                ITeamRepository iTeamRepository = (ITeamRepository) element;
                return iTeamRepository.getName();
            }
            return "No repositories";
        }
    }

    public ChangeSetInstallManager getInstallManager() {
        return csInstallManager;
    }

    public ChangesetInstallerViewController getViewController() {
        return viewController;
    }
}
