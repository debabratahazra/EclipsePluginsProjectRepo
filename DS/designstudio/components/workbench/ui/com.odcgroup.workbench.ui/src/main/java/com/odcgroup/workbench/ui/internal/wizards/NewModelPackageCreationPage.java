package com.odcgroup.workbench.ui.internal.wizards;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jdt.core.JavaConventions;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.WorkspaceModifyOperation;

import com.odcgroup.workbench.core.OfsCore;
import com.odcgroup.workbench.ui.help.IOfsHelpContextIds;

public class NewModelPackageCreationPage  extends WizardPage  {

	private IPath containerFullPath;
	private Text groupNameField;
	
	/**
	 * the diagram file for the newly created diagram
	 */
	protected IFolder folder;

	private static final String DEFAULT_GROUPNAME = "";

	/**
	 * @param pageName
	 * @param containerPath
	 */
	protected NewModelPackageCreationPage(String pageName, IWorkbench workbench,
			IPath containerFullPath) {
		super(pageName);
		this.containerFullPath = containerFullPath;
		setTitle("Create Model Package");
		setDescription("creates a new model package");
		setPageComplete(false);
		folder = null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
	 */
	public void createControl(Composite parent) {

		Composite topLevel = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		layout.verticalSpacing = 12;
		topLevel.setLayout(layout);
		topLevel.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_FILL
				| GridData.HORIZONTAL_ALIGN_FILL));
		topLevel.setFont(parent.getFont());
		PlatformUI.getWorkbench().getHelpSystem().setHelp(parent.getShell(), IOfsHelpContextIds.NEW_MODEL_PACKAGE);

		Label label = new Label(topLevel, SWT.WRAP);
		label.setText("Model Package Name :");
		label.setFont(this.getFont());

		groupNameField = new Text(topLevel, SWT.SINGLE | SWT.BORDER);
		groupNameField.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		groupNameField.addModifyListener(validator);
		groupNameField.setFont(this.getFont());

		if (null != containerFullPath) {
			String groupName = DEFAULT_GROUPNAME;
			groupNameField.setText(groupName);
		}
				
		setControl(parent);
		
		
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ModifyListener validator =
		new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				setPageComplete(validatePage());
			}
		};

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected boolean validatePage() {
		String packageName = getGroupName();
		
		if (packageName == null || packageName.equals("")) {
			setErrorMessage(null);
			setMessage(null);
			return false;
		}

		IPath path = getContainerFullPath().append(packageName.toLowerCase());
		if (ResourcesPlugin.getWorkspace().getRoot().exists(path)) {
			setErrorMessage("Model package already exists");
			return false;
		}
		/*
		URI fileURI = URI.createFileURI(path.toString());
		String ext = fileURI.fileExtension();
		if (ext != null) {
			setErrorMessage("Invalid model package name");
			return false;
		}		
		*/
		IStatus status = JavaConventions.validatePackageName(packageName, JavaCore.VERSION_1_5, JavaCore.VERSION_1_5); 
		if(!status.isOK() && status.getSeverity()==IStatus.ERROR) {
			setErrorMessage("Invalid model package name");
			return false;
		}		
		if(!status.isOK() && status.getSeverity()==IStatus.WARNING) {
			setMessage(status.getMessage(), IMessageProvider.WARNING);
			return true;
		}		
			
		setMessage(null);
		setErrorMessage(null);
		return true;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.dialogs.WizardNewFileCreationPage#getFileName()
	 */
	public String getGroupName() {
		String groupName = groupNameField.getText();
		return groupName;
	}
	
	/**
	 * Accessor for the diagram's file
	 * @return IFile the file owning the diagram
	 */
	public final IFolder getGroupFolder() {
		return folder;
	}

	/**
	 * @return
	 */
	public IPath getContainerFullPath() {
		return containerFullPath;
	}
	
	/**
	 * Performs the operations necessary to create and open the diagram
	 * @return boolean indicating whether the creation and opening the Diagram was successful 
	 */
	public boolean finish() {
		final boolean[] result = new boolean[1];
		WorkspaceModifyOperation op = new WorkspaceModifyOperation(null) {
			protected void execute(IProgressMonitor monitor) throws CoreException, InterruptedException {
				result[0] = doFinish(monitor);
			}
		};

		try {
			getContainer().run(false, true, op);
		} catch (InterruptedException e) {
			return false;
		} catch (InvocationTargetException e) {
			if (e.getTargetException() instanceof CoreException) {
				ErrorDialog.openError(
					getContainer().getShell(),
					"Error creating model package",
					null,	// no special message
					((CoreException) e.getTargetException()).getStatus());
			}
			else {
				// CoreExceptions are handled above, but unexpected runtime exceptions and errors may still occur.
				OfsCore.getDefault().logError(
						"Error creating model package", e.getTargetException());
			}
			return false;
		}
		return result[0];
	}
	
	/**
	 * @param monitor the <code>IProgressMonitor</code> to use to indicate progress and check for cancellation
	 * @return boolean indicating whether the creation and opening the Diagram was successful
	 * @throws CoreException 
	 */
	public boolean doFinish(IProgressMonitor monitor) throws CoreException {
		// create folder
        IPath newFolderPath = containerFullPath.append(getGroupName());
        folder = ResourcesPlugin.getWorkspace().getRoot().getFolder(newFolderPath);
        if (folder != null) {
        
	        try {
				folder.create(false, true, monitor);
			} catch (CoreException e) {
	            if(e.getStatus().getCode() == 374)
	            {
	                folder.refreshLocal(0, null);
	            } else {
	                OfsCore.getDefault().logError(e.getMessage(), e);
	                throw e;
	            }
			}
        }
		return folder != null;
	}
}
