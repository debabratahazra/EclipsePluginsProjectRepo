package com.zealcore.se.ui.wizards;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.model.WorkbenchContentProvider;

import com.zealcore.se.ui.ICaseFile;
import com.zealcore.se.ui.IconManager;
import com.zealcore.se.ui.core.CaseFileManager;
import com.zealcore.se.ui.editors.ILogSessionWrapper;
import com.zealcore.se.ui.views.NavigatorLabelProvider;

public class LogsetCreationPage extends WizardPage {

    private Tree tree;

    private static final String E_NO_PROJECT = "No Project specified.";

	private static final String E_NOT_A_PROJECT = "Selection is not a Project";

	private static final String E_CONFLICTING_NAME = "Logset with this name already exists.";

    private static final String TITLE = "Logset";

    private static final String DESCRIPTION = "Create a new Logset.";

    private static final String NAME = "LogsetCreationPage";

    private Text txtName;

    private TreeViewer treeProjects;

    private final Map<String, Set<String>> logSetsByProject = new HashMap<String, Set<String>>();

    private String selectedProject;

    private IStructuredSelection selection;

    private boolean hasValidated;

    public LogsetCreationPage(final IStructuredSelection selection) {
        super(LogsetCreationPage.NAME, LogsetCreationPage.TITLE, IconManager
                .getImageDescriptor(IconManager.LOGFILE_MEDIUM_IMAGE_ID));
        setDescription(LogsetCreationPage.DESCRIPTION);
        populateProjectMap();
        this.selection = selection;
        hasValidated = false;
    }

    private void initializeSelectedProject() {
        Object object = selection.getFirstElement();
        if (object instanceof IResource) {
            final IResource resource = (IResource) object;
            object = resource.getProject();

        }
        if (!CaseFileManager.isCaseFile(object)) {
            return;
        }
        if (!(object instanceof IAdaptable)) {
            throw new IllegalArgumentException(
                    LogsetCreationPage.E_NOT_A_PROJECT);
        }
        final IAdaptable adaptable = (IAdaptable) object;
        final ICaseFile caseFile = (ICaseFile) adaptable
                .getAdapter(ICaseFile.class);
        if (caseFile == null) {
            throw new IllegalArgumentException(
                    LogsetCreationPage.E_NOT_A_PROJECT);
        }
        for (final TreeItem treeItem : treeProjects.getTree().getItems()) {
            if (treeItem.getText().equalsIgnoreCase(
                    caseFile.getResource().getName())) {
                treeProjects.getTree().setSelection(treeItem);
                treeProjects.setSelection(treeProjects.getSelection());
                getContainer().updateButtons();
                return;
            }
        }
    }

    private void populateProjectMap() {
        logSetsByProject.clear();
        final IWorkspaceRoot workspaceRoot = ResourcesPlugin.getWorkspace()
                .getRoot();
        for (final IProject project : workspaceRoot.getProjects()) {
            if (!CaseFileManager.isCaseFile(project)) {
                continue;
            }
            final ICaseFile caseFile = (ICaseFile) project
                    .getAdapter(ICaseFile.class);
            final String parentName = caseFile.getResource().getName()
                    .toLowerCase();
            for (final ILogSessionWrapper logSet : caseFile.getLogs()) {
                final String logSetName = logSet.getLabel(null);
                if (!logSetsByProject.containsKey(parentName)) {
                    logSetsByProject.put(parentName, new HashSet<String>());
                }
                logSetsByProject.get(parentName).add(logSetName);
            }
        }
    }

    public void createControl(final Composite parent) {
        final Composite container = new Composite(parent, SWT.NULL);
        container.setLayout(new FormLayout());
        setControl(container);

        final Label lblSelectProject = new Label(container, SWT.NONE);
        final FormData fdLblSelectProject = new FormData();
        fdLblSelectProject.right = new FormAttachment(100, -5);
        fdLblSelectProject.bottom = new FormAttachment(0, 25);
        fdLblSelectProject.top = new FormAttachment(0, 5);
        fdLblSelectProject.left = new FormAttachment(0, 5);
        lblSelectProject.setLayoutData(fdLblSelectProject);
        lblSelectProject.setText("Select the parent Project:");

        treeProjects = new TreeViewer(container, SWT.BORDER);
        tree = treeProjects.getTree();
        final FormData fdtree = new FormData();
        fdtree.top = new FormAttachment(lblSelectProject, 5, SWT.BOTTOM);
        fdtree.right = new FormAttachment(100, -5);
        fdtree.left = new FormAttachment(0, 0);
        tree.setLayoutData(fdtree);
        final FormData fdTreeProjects = new FormData();
        fdTreeProjects.bottom = new FormAttachment(100, -44);
        fdTreeProjects.top = new FormAttachment(lblSelectProject, 5, SWT.BOTTOM);
        fdTreeProjects.right = new FormAttachment(100, -5);
        fdTreeProjects.left = new FormAttachment(0, 0);
        treeProjects.getTree().setLayoutData(fdTreeProjects);
        treeProjects.setLabelProvider(new NavigatorLabelProvider());
        treeProjects.setContentProvider(new WorkbenchContentProvider());
        treeProjects.setInput(ResourcesPlugin.getWorkspace().getRoot());
        treeProjects.addFilter(new ViewerFilter() {
            @Override
            public boolean select(final Viewer viewer,
                    final Object parentElement, final Object element) {
                if (CaseFileManager.isCaseFile(element)) {
                    return true;
                }
                if (!(element instanceof IAdaptable)) {
                    return false;
                }
                final IAdaptable adaptable = (IAdaptable) element;
                final ILogSessionWrapper logSession = (ILogSessionWrapper) adaptable
                        .getAdapter(ILogSessionWrapper.class);
                if (logSession == null) {
                    return false;
                }
                return true;
            }
        });
        treeProjects
                .addSelectionChangedListener(new ISelectionChangedListener() {
                    public void selectionChanged(final SelectionChangedEvent e) {
                        final ISelection sel = treeProjects.getSelection();
                        final Object element = ((IStructuredSelection) sel)
                                .getFirstElement();
                        if (!CaseFileManager.isCaseFile(element)) {
                            selectedProject = null;
                        } else {
                            selectedProject = ((IResource) element).getName();
                            selection = (IStructuredSelection) sel;
                        }
                        getContainer().updateButtons();
                    }
                });

        final Label logsetNameLabel = new Label(container, SWT.NONE);
        final FormData fdLogsetNameLabel = new FormData();
        fdLogsetNameLabel.bottom = new FormAttachment(100, -7);
        fdLogsetNameLabel.right = new FormAttachment(0, 95);
        fdLogsetNameLabel.left = new FormAttachment(0, 5);
        logsetNameLabel.setLayoutData(fdLogsetNameLabel);
        logsetNameLabel.setText("Logset name:");

        txtName = new Text(container, SWT.BORDER);
        fdtree.bottom = new FormAttachment(txtName, -5, SWT.TOP);
        final FormData fdTxtName = new FormData();
        fdTxtName.right = new FormAttachment(tree, 0, SWT.RIGHT);
        fdTxtName.bottom = new FormAttachment(100, -4);
        fdTxtName.left = new FormAttachment(0, 100);
        txtName.setLayoutData(fdTxtName);

        txtName.addModifyListener(new ModifyListener() {
            public void modifyText(final ModifyEvent e) {
                hasValidated = true;
                getContainer().updateButtons();
            }
        });
        initializeSelectedProject();
        setFocus();
    }

    public void setFocus() {
        txtName.setFocus();
    }

    @Override
    public boolean isPageComplete() {
        if (!hasValidated) {
            setErrorMessage(null);
            setMessage(null);
            return false;
        }
        if (selectedProject == null) {
            setErrorMessage(LogsetCreationPage.E_NO_PROJECT);
            return false;
        }
        final String input = txtName.getText().trim();
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IStatus result = workspace.validateName(input,
				IResource.FOLDER);
		if (!result.isOK()) {
			setErrorMessage(result.getMessage());
			return false;
		}
        final Set<String> logSets = logSetsByProject.get(selectedProject
                .toLowerCase());
        if (logSets != null && logSets.contains(input.toLowerCase())) {
            setErrorMessage(LogsetCreationPage.E_CONFLICTING_NAME);
            return false;
        }
        setErrorMessage(null);
        setMessage(null);
        return true;
    }

    public String getLogsetName() {
        return txtName.getText();
    }

    public ICaseFile getProject() {
        final Object object = selection.getFirstElement();
        if (!CaseFileManager.isCaseFile(object)) {
            throw new IllegalStateException(LogsetCreationPage.E_NOT_A_PROJECT);
        }
        if (!(object instanceof IAdaptable)) {
            throw new IllegalStateException(LogsetCreationPage.E_NOT_A_PROJECT);
        }
        final IAdaptable adaptable = (IAdaptable) object;
        final ICaseFile caseFile = (ICaseFile) adaptable
                .getAdapter(ICaseFile.class);
        return caseFile;
    }
}
