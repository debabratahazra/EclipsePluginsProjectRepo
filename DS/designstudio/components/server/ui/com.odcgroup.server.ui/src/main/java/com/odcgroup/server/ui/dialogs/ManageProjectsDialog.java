package com.odcgroup.server.ui.dialogs;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.CheckboxTreeViewer;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import com.odcgroup.server.model.IDSProject;
import com.odcgroup.server.model.IDSServer;
import com.odcgroup.server.ui.ServerUICore;
import com.odcgroup.server.util.DSProjectUtil;

/**
 *
 * @author pkk
 *
 */
public class ManageProjectsDialog extends TitleAreaDialog {
	
	private CheckboxTreeViewer projectsList = null;
	private IDSServer server = null;
	
	private IDSProject[] validProjects = null;
	private List<IDSProject> selectedProjects = new ArrayList<IDSProject>();
	
	/**
	 * @param parentShell
	 */
	public ManageProjectsDialog(Shell parentShell, IDSServer server) {
		super(parentShell);
		this.server = server;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.dialogs.Dialog#create()
	 */
	public void create() {
		super.create();
		this.setTitle("Add/Remove Projects");
		this.setMessage("Select the project(s) to deploy on the application server");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.window.Window#configureShell(org.eclipse.swt.widgets.Shell)
	 */
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("Add/Remove Projects");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.dialogs.Dialog#createDialogArea(org.eclipse.swt.widgets.Composite)
	 */
	protected Control createDialogArea(Composite parent) {

		initializeDialogUnits(parent);
		
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout());
		composite.setLayoutData(new GridData(GridData.FILL_BOTH
				| GridData.GRAB_HORIZONTAL | GridData.GRAB_VERTICAL));
		
		Label title = new Label(composite, SWT.NONE);
		title.setText("Select the projects to deploy to the server : ");
		
		Composite listComposite = new Composite(composite, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		layout.marginWidth = 0;
		layout.makeColumnsEqualWidth = false;
		listComposite.setLayout(layout);
		listComposite.setLayoutData(new GridData(GridData.GRAB_HORIZONTAL | GridData.GRAB_VERTICAL | GridData.FILL_BOTH));
		
		projectsList = new CheckboxTreeViewer(listComposite, SWT.BORDER);
		GridData listData = new GridData(GridData.GRAB_HORIZONTAL | GridData.GRAB_VERTICAL | GridData.FILL_BOTH);
		projectsList.getControl().setLayoutData(listData);
		
		configureCheckboxTreeViewer();
		createSelectionButtons(listComposite);
		projectsList.setInput(getValidProjects());
		
		updateProjectsList();
		return composite;
	}
	
	/**
	 * @param listComposite
	 */
	private void createSelectionButtons(Composite listComposite) {
		Composite buttonsComposite = new Composite(listComposite, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		buttonsComposite.setLayout(layout);

		buttonsComposite.setLayoutData(new GridData(
				GridData.VERTICAL_ALIGN_BEGINNING));

		Button selectAll = new Button(buttonsComposite, SWT.PUSH);
		selectAll.setText("Select All");
		selectAll.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				projectsList.setCheckedElements(getValidProjects());
				for (IDSProject project : getValidProjects()) {
					selectedProjects.add(project);
				}
			}
		});
		Dialog.applyDialogFont(selectAll);
		setButtonLayoutData(selectAll);

		Button deselectAll = new Button(buttonsComposite, SWT.PUSH);
		deselectAll.setText("Deselect All");
		deselectAll.addSelectionListener(new SelectionAdapter() {			
			public void widgetSelected(SelectionEvent e) {
				List<IDSProject> lockedProjects = new ArrayList<IDSProject>();
				for (IDSProject project: server.getDsProjects()) {
					if (project.isLocked()) {
						lockedProjects.add(project);
					}
				}
				projectsList.setCheckedElements(lockedProjects.toArray());
				selectedProjects.clear();
			}
		});
		Dialog.applyDialogFont(deselectAll);
		setButtonLayoutData(deselectAll);

		Button refresh = new Button(buttonsComposite, SWT.PUSH);
		refresh.setText("Refresh");
		refresh.addSelectionListener(new SelectionAdapter() {		
			public void widgetSelected(SelectionEvent e) {
				updateProjectsList();
			}
		});
		Dialog.applyDialogFont(refresh);
		setButtonLayoutData(refresh);
		
	}
	
	/**
	 * 
	 */
	private void configureCheckboxTreeViewer() {
		
		projectsList.setContentProvider(new ITreeContentProvider() {
			
			public Object[] getChildren(Object parentElement) {
				return null;
			}

			public Object getParent(Object element) {
				return null;
			}

			public boolean hasChildren(Object element) {
				return false;
			}

			public Object[] getElements(Object inputElement) {
				if (inputElement instanceof IDSProject[]) {
					return (IDSProject[])inputElement;
				}
				return null;
			}

			public void dispose() {				
			}

			public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {				
			}
			
		});
		
		projectsList.setLabelProvider(new LabelProvider() {			
			public String getText(Object element) {
				IDSProject project = (IDSProject)element;
				if (project.isLocked()) {
					return project.getName() + " (locked)";
				} else {
					return project.getName();
				}
			}
		});

		projectsList.addCheckStateListener(new ICheckStateListener() {			
			public void checkStateChanged(CheckStateChangedEvent event) {
				IDSProject project = (IDSProject)event.getElement();
				if (project.isLocked()) {
					event.getCheckable().setChecked(event.getElement(), true);
				} else {
					if (event.getChecked()) {
						selectedProjects.add(project);
					} else {
						selectedProjects.remove(project);
					}
				}
			}
		});
		
		projectsList.setComparator(new ViewerComparator());
	}
	
	/**
	 * 
	 */
	private void updateProjectsList() {
		List<IDSProject> selected = new ArrayList<IDSProject>();
		for (IDSProject project : getValidProjects()) {
			if (isAlreadySelected(project)) {
				selected.add(project);
				selectedProjects.add(project);
			}
		}
		if (!selected.isEmpty()) {
			projectsList.setCheckedElements(selected.toArray(new IDSProject[0]));
		} 
	}
	
	/**
	 * @param projectName
	 * @param sProjects
	 * @return
	 */
	private boolean isAlreadySelected(IDSProject givenProject) {
		return getDeployedProject(givenProject) != null;
	}
	
	/**
	 * @return
	 */
	protected IDSProject[] getValidProjects() {
		if (validProjects == null) {
			IWorkspace workspace = ResourcesPlugin.getWorkspace();
			List<IDSProject> deployableProjects = new ArrayList<IDSProject>();
			IProject[] projects = workspace.getRoot().getProjects();
			for (IProject project : projects) {
				if (project.exists() && 
						project.isOpen() && 
						server.canDeploy(project)) {
					IDSProject dsProject = DSProjectUtil.getDsProject(project);
					// Substitute the already deployed project if possible
					IDSProject deployedProject = getDeployedProject(dsProject);
					if (deployedProject == null) {
						deployableProjects.add(dsProject);
					} else {
						deployableProjects.add(deployedProject);
					}
				}
			}
			validProjects = deployableProjects.toArray(new IDSProject[0]);
		}
		return validProjects;
	}

	private IDSProject getDeployedProject(IDSProject givenProject) {
		for (IDSProject project : server.getDsProjects()) {
			if (project.getName().equals(givenProject.getName())
					/*&& project.getProjectLocation().equals(givenProject.getProjectLocation())*/
					) {
				return project;
			}
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.Dialog#okPressed()
	 */
	@Override
	protected void okPressed() {
		super.okPressed();
		// Sort the result
		List<IDSProject> sortedSelectedProjects = new ArrayList<IDSProject>();
		for (IDSProject validProject : validProjects) {
			if (selectedProjects.contains(validProject)) {
				sortedSelectedProjects.add(validProject);
			}
		}
		ServerUICore.getDefault().getContributions().updateDeployedProjects(server, sortedSelectedProjects.toArray(new IDSProject[0]));
	}

}
