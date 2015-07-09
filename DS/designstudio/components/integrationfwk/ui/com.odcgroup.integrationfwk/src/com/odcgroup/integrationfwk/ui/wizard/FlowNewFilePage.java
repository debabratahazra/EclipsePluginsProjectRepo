package com.odcgroup.integrationfwk.ui.wizard;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.dialogs.WizardNewFileCreationPage;

import com.odcgroup.integrationfwk.ui.IntegrationToolingActivator;
import com.odcgroup.integrationfwk.ui.utils.StringConstants;
import com.odcgroup.integrationfwk.ui.utils.Utils;

/*
 * All the source is copied from XSDNewFilePage from XSD editor plugin
 */
public class FlowNewFilePage extends WizardNewFileCreationPage {

	private final String defaultName = "NewFlow"; //$NON-NLS-1$
	private final String defaultFileExtension = ".flow"; //$NON-NLS-1$

	//	private String[] filterExtensions = { "*.flow" }; //$NON-NLS-1$

	public FlowNewFilePage(IStructuredSelection selection) {
		super(IntegrationToolingActivator.getEventString("_UI_CREATE_FLOW"), selection);
		setTitle(IntegrationToolingActivator.getEventString("_UI_NEW_FLOW_TITLE"));
		setDescription(IntegrationToolingActivator
				.getEventString("_UI_CREATE_A_NEW_FLOW_DESC"));

	}

	protected String computeDefaultFileName() {
		int count = 0;
		String fileName = defaultName + defaultFileExtension;
		IPath containerFullPath = getContainerFullPath();
		if (containerFullPath != null) {
			while (true) {
				IPath path = containerFullPath.append(fileName);
				if (ResourcesPlugin.getWorkspace().getRoot().exists(path)) {
					count++;
					fileName = defaultName + count + defaultFileExtension;
				} else {
					break;
				}
			}
		}
		return fileName;
	}

	@Override
	public void createControl(Composite parent) {
		// inherit default container and name specification widgets
		super.createControl(parent);

		setFileName(computeDefaultFileName());

		setPageComplete(validatePage());
	}

	// returns true if file of specified name exists in any case for selected
	// container
	protected String existsFileAnyCase(String fileName) {
		if ((getContainerFullPath() != null)
				&& (getContainerFullPath().isEmpty() == false)
				&& (fileName.compareTo("") != 0)) {
			// look through all resources at the specified container - compare
			// in upper case
			IResource parent = ResourcesPlugin.getWorkspace().getRoot()
					.findMember(getContainerFullPath());
			if (parent instanceof IContainer) {
				IContainer container = (IContainer) parent;
				try {
					IResource[] members = container.members();
					String enteredFileUpper = fileName.toUpperCase();
					for (int i = 0; i < members.length; i++) {
						String resourceUpperName = members[i].getName()
								.toUpperCase();
						if (resourceUpperName.equals(enteredFileUpper)) {
							return members[i].getName();
						}
					}
				} catch (CoreException e) {
				}
			}
		}
		return null;
	}

	@Override
	protected boolean validatePage() {
		Path newName = new Path(getFileName());
		String fullFileName = getFileName();
		String extension = newName.getFileExtension();
		if (extension == null || !extension.equals("flow")) {
			setErrorMessage(IntegrationToolingActivator
					.getEventString("_ERROR_FILENAME_MUST_END_FLOW"));
			return false;
		} else if (Character.isDigit(fullFileName.charAt(0))) {
			setErrorMessage(IntegrationToolingActivator
					.getEventString("_ERROR_FILENAME_CANNOT_START_WITH_NUMBERS"));
			return false;
		} else {
			setErrorMessage(null);
		}

		// checking for invalid characters in file name
		String nameWithOutExtension = Utils.removeFileExtention(fullFileName,
				extension);
		if (!Utils.containsValidChar(nameWithOutExtension)) {
			setErrorMessage(StringConstants.ERROR_MSG_CONTAINS_INVALID_CHAR);
			return false;
		} else if (nameWithOutExtension.length() == 0
				|| nameWithOutExtension.length() < 3) {
			setErrorMessage(StringConstants.ERROR_MSG_FLOWNAME_RULES_NOT_MEET);
			return false;
		} else {
			setErrorMessage(null);
		}
		// check for file should be case insensitive
		String sameName = existsFileAnyCase(fullFileName);
		if (sameName != null) {
			setErrorMessage(IntegrationToolingActivator.getDefault().getString(
					"_ERROR_FILE_ALREADY_EXISTS", sameName)); //$NON-NLS-1$
			return false;
		}

		return super.validatePage();
	}
}
