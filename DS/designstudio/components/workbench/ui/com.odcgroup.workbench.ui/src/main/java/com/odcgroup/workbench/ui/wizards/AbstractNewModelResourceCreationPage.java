package com.odcgroup.workbench.ui.wizards;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.common.util.URI;
import org.eclipse.jdt.core.JavaConventions;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jface.dialogs.ErrorDialog;
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
import org.eclipse.ui.actions.WorkspaceModifyOperation;

import com.odcgroup.workbench.core.helper.StringHelper;
import com.odcgroup.workbench.ui.OfsUICore;

abstract public class AbstractNewModelResourceCreationPage extends WizardPage  {

	protected IPath containerFullPath;
	protected IWorkbench workbench;
	protected Text fileNameField;
	protected Text description;
	protected String model;
	
	/**
	 * the diagram file for the newly created diagram
	 */
	protected IFile file;

	protected static final String DEFAULT_FILENAME = "MyName";

	/**
	 * @param pageName
	 * @param containerPath
	 */
	protected AbstractNewModelResourceCreationPage(String pageName, IWorkbench workbench,
			IPath containerFullPath, String model) {
		super(pageName);
		this.workbench = workbench;
		this.containerFullPath = containerFullPath;
		this.model = model;
		setTitle("Create " + StringHelper.toFirstUpper(model));
		setDescription("creates a new " + model);
		setPageComplete(false);
		file = null;
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

		Label label = new Label(topLevel, SWT.WRAP);
		label.setText(StringHelper.toFirstUpper(model) + " Name :");
		label.setFont(this.getFont());

		fileNameField = new Text(topLevel, SWT.SINGLE | SWT.BORDER);
		fileNameField.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		fileNameField.addModifyListener(validator);
		fileNameField.setFont(this.getFont());

		if (null != containerFullPath) {
			String fileName = DEFAULT_FILENAME;
			fileNameField.setText(fileName);
		}
		
		Label desc = new Label(topLevel, SWT.WRAP);
		desc.setText("Description :");
		desc.setFont(this.getFont());
		
		description = new Text(topLevel, SWT.SINGLE | SWT.BORDER);
		GridData gridData = new GridData();
		gridData.widthHint = 300;
		gridData.verticalSpan = 3;
		gridData.horizontalAlignment = GridData.FILL;
		gridData.verticalAlignment = GridData.BEGINNING;
		gridData.heightHint = 80;
		description.setLayoutData(gridData);
		description.setLayoutData(gridData);
		description.addModifyListener(validator);
		
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
		String fileName = getFileName();
		if (fileName == null)
			return false;
		if(!Character.isUpperCase(fileName.charAt(0))) {
			setErrorMessage(OfsUICore.getDefault().getString("wizard.modelresource.validator.nameMustStartWithUpperCase"));
			return false;
		}
		IPath path = getContainerFullPath().append(fileName);
		if (ResourcesPlugin.getWorkspace().getRoot().exists(path)) {
			setErrorMessage(OfsUICore.getDefault().getString("wizard.modelresource.validator.fileExists"));
			return false;
		}
		URI fileURI = URI.createFileURI(path.toString());
		String ext = fileURI.fileExtension();
		if (ext == null || !model.equals(ext)) {
			setErrorMessage(OfsUICore.getDefault().getString("wizard.modelresource.validator.fileNameInvalid"));
			return false;
		}

		IStatus status = JavaConventions.validateIdentifier(
				StringHelper.withoutExtension(fileName), JavaCore.VERSION_1_5, JavaCore.VERSION_1_5); 
		if(!status.isOK()) {
			setErrorMessage(OfsUICore.getDefault().getString("wizard.modelresource.validator.fileNameInvalid"));
			return false;
		}	
		
		setErrorMessage(null);
		return true;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.dialogs.WizardNewFileCreationPage#getFileName()
	 */
	public String getFileName() {
		String fileName = fileNameField.getText().trim() + "." + model;
		return fileName;
	}
	
	/**
	 * Accessor for the diagram's file
	 * @return IFile the file owning the diagram
	 */
	public final IFile getFile() {
		return file;
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
					OfsUICore.getDefault().getString("wizard.modelresource.errordialog"),
					null,	// no special message
					((CoreException) e.getTargetException()).getStatus());
			}
			else {
				// CoreExceptions are handled above, but unexpected runtime exceptions and errors may still occur.
				OfsUICore.getDefault().logError(OfsUICore.getDefault().getString("wizard.modelresource.errordialog"), e.getTargetException());
			}
			return false;
		}
		return result[0];
	}
	
	/**
	 * @param monitor the <code>IProgressMonitor</code> to use to indicate progress and check for cancellation
	 * @return boolean indicating whether the creation and opening the Diagram was successful
	 */
	abstract public boolean doFinish(IProgressMonitor monitor);
}
