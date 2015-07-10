package com.zealcore.se.ui.wizards;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
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
import com.zealcore.se.ui.views.NavigatorLabelProvider;

public class ReportCreationPage extends WizardPage {

    private Tree tree;

    private static final String REPORTS_FOLDER = "reports";

	private static final String E_CONFLICTING_NAME = "Report with this name already exists.";

    private static final String E_NO_PROJECT = "No Project specified.";

    private static final String E_NOT_A_PROJECT = "Selection is not a Project";


    private static final String TITLE = "Report";

    private static final String DESCRIPTION = "Create a new Report.";

    private static final String NAME = "ReportCreationPage";

    private Text txtName;

    private TreeViewer treeProjects;

    private String selectedProject;

    private IStructuredSelection selection;

    private final String defaultReportName;

    public ReportCreationPage(final IStructuredSelection selection) {
        super(ReportCreationPage.NAME, ReportCreationPage.TITLE, IconManager
                .getImageDescriptor(IconManager.REPORT_MEDIUM));
        setDescription(ReportCreationPage.DESCRIPTION);
        this.selection = selection;
        defaultReportName = generateDefaultReportName();
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
                    ReportCreationPage.E_NOT_A_PROJECT);
        }
        final IAdaptable adaptable = (IAdaptable) object;
        final ICaseFile caseFile = (ICaseFile) adaptable
                .getAdapter(ICaseFile.class);
        if (caseFile == null) {
            throw new IllegalArgumentException(
                    ReportCreationPage.E_NOT_A_PROJECT);
        }
        for (final TreeItem treeItem : treeProjects.getTree().getItems()) {
            if (treeItem.getText().equalsIgnoreCase(
                    caseFile.getResource().getName())) {
                treeProjects.getTree().setSelection(treeItem);
                treeProjects.setSelection(treeProjects.getSelection());
                return;
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
        lblSelectProject.setText("Select the destination Project:");

        treeProjects = new TreeViewer(container, SWT.BORDER);
        tree = treeProjects.getTree();
        final FormData fdtree = new FormData();
        fdtree.top = new FormAttachment(lblSelectProject, 5, SWT.BOTTOM);
        fdtree.right = new FormAttachment(lblSelectProject, 0, SWT.RIGHT);
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
                return false;
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
                        if (ReportCreationPage.this.isCurrentPage()) {
                            ReportCreationPage.this.getContainer()
                                    .updateButtons();
                        }
                    }
                });

        final Label reportNameLabel = new Label(container, SWT.NONE);
        final FormData fdLogsetNameLabel = new FormData();
        fdLogsetNameLabel.bottom = new FormAttachment(100, -9);
        fdLogsetNameLabel.right = new FormAttachment(0, 95);
        fdLogsetNameLabel.left = new FormAttachment(0, 5);
        reportNameLabel.setLayoutData(fdLogsetNameLabel);
        reportNameLabel.setText("Report name:");

        txtName = new Text(container, SWT.BORDER);
        fdtree.bottom = new FormAttachment(txtName, -5, SWT.TOP);
        final FormData fdTxtName = new FormData();
        fdTxtName.bottom = new FormAttachment(100, -5);
        fdTxtName.right = new FormAttachment(tree, 0, SWT.RIGHT);
        fdTxtName.left = new FormAttachment(0, 100);
        txtName.setLayoutData(fdTxtName);
        txtName.addModifyListener(new ModifyListener() {
            public void modifyText(final ModifyEvent e) {
                if (!ReportCreationPage.this.isCurrentPage()) {
                    return;
                }
                ReportCreationPage.this.getContainer().updateButtons();
            }
        });
        initializeSelectedProject();
        setFocus();
        txtName.setText(defaultReportName);
    }

    public void setFocus() {
        txtName.setFocus();
    }

    @Override
    public boolean isPageComplete() {
        if (selectedProject == null) {
            setErrorMessage(ReportCreationPage.E_NO_PROJECT);
            return false;
        }
        final String input = txtName.getText();
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IStatus result = workspace.validateName(input,
				IResource.FOLDER);
		if (!result.isOK()) {
			setErrorMessage(result.getMessage());
			return false;
		}
        final File path = new File(getReportLocation(), input);
        if (path.exists()) {
            setErrorMessage(ReportCreationPage.E_CONFLICTING_NAME);
            return false;
        }
        setErrorMessage(null);
        setMessage(null);
        return true;
    }

    public File getReportLocation() {
        return getProject().getResource().getLocation().append(
                ReportCreationPage.REPORTS_FOLDER).toFile();
    }

    public String generateDefaultReportName() {
        final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd kk-mm-ss");
        return df.format(Calendar.getInstance().getTime());
    }

    public String getReportName() {
        return txtName.getText();
    }

    public ICaseFile getProject() {
        final Object object = selection.getFirstElement();
        if (!CaseFileManager.isCaseFile(object)) {
            throw new IllegalStateException(ReportCreationPage.E_NOT_A_PROJECT);
        }
        if (!(object instanceof IAdaptable)) {
            throw new IllegalStateException(ReportCreationPage.E_NOT_A_PROJECT);
        }
        final IAdaptable adaptable = (IAdaptable) object;
        final ICaseFile caseFile = (ICaseFile) adaptable
                .getAdapter(ICaseFile.class);
        return caseFile;
    }
}
