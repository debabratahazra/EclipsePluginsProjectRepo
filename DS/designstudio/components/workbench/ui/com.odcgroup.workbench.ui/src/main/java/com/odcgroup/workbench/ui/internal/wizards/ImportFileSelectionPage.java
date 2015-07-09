package com.odcgroup.workbench.ui.internal.wizards;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.jface.preference.FileFieldEditor;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

import com.odcgroup.workbench.core.OfsCore;
import com.odcgroup.workbench.core.helper.StringHelper;

public class ImportFileSelectionPage extends WizardPage {
	
	protected FileFieldEditor editor;
	protected IFolder outputFolder;
	
	protected final String model;
	
    // qualified name to identify resource name
    public static final QualifiedName GENERATED_RESOURCE_NAME =
    	new QualifiedName("com.odcgroup.workbench", "resourceName");
    
	protected static String folderName;

	public ImportFileSelectionPage(String pageName, String model) {
		super(pageName);
		this.model = model;
		setTitle(pageName); //NON-NLS-1
		setDescription("Select the Triple'A Plus " + model + " file to import from the local file system"); //NON-NLS-1
	}
	
    /** (non-Javadoc)
     * Method declared on IDialogPage.
     */
	public void createControl(Composite parent) {
        initializeDialogUnits(parent);
		Composite fileSelectionArea = new Composite(parent, SWT.NONE);
		GridData fileSelectionData = new GridData(GridData.GRAB_HORIZONTAL
				| GridData.FILL_HORIZONTAL);
		fileSelectionArea.setLayoutData(fileSelectionData);

		GridLayout fileSelectionLayout = new GridLayout();
		fileSelectionLayout.numColumns = 1;
		fileSelectionLayout.makeColumnsEqualWidth = false;
		fileSelectionLayout.marginWidth = 0;
		fileSelectionLayout.marginHeight = 0;
		fileSelectionArea.setLayout(fileSelectionLayout);
		
		editor = new FileFieldEditor("fileSelect","Select File: ", fileSelectionArea); //NON-NLS-1 //NON-NLS-2
		String[] extensions = new String[] { "*.xml" }; //NON-NLS-1
		editor.setFileExtensions(extensions);
		editor.getTextControl(fileSelectionArea).addModifyListener(new ModifyListener(){
			public void modifyText(ModifyEvent e) {
				setPageComplete(validatePage());
			}
		});
		fileSelectionArea.moveAbove(null);

        // Show description on opening
        setErrorMessage(null);
        setMessage(null);
        setControl(parent);
        setPageComplete(validatePage());
	}
	
    /**
     * Returns whether this page's controls currently all contain valid 
     * values.
     *
     * @return <code>true</code> if all controls are valid, and
     *   <code>false</code> if at least one is invalid
     */
	protected boolean validatePage() {
		if(outputFolder==null || !outputFolder.exists()) {
			setErrorMessage("No output folder is selected.\n" +
					"Please select a folder before starting the import wizard!");
			return false;
		}
        if(editor.getStringValue().length()==0) {
        	// no error message as this is obvious to the user
            setErrorMessage(null);
        	return false;
        }
        File inputFile = new File(editor.getStringValue());
        if(!inputFile.exists()) {
        	setErrorMessage("The file '" + inputFile.getName() + "' does not exist!");
        	return false;
        }
        if(inputFile.isDirectory()) {
        	setErrorMessage("'" + inputFile.getName() + "' is a directory!");
        	return false;
        }
		if(getNewFolder().exists()) {
        	setErrorMessage("The folder '" + getNewFolder().getFullPath() + "' already exists!");
        	return false;
        }
        setErrorMessage(null);
        return true;
    }
	
	public String getSelectedFileName(){
		File file = new File(editor.getStringValue());
		String fileName = file.getName();
		return fileName.substring(0, fileName.lastIndexOf("."));
	}
    
	public InputStream getInitialContents() {
		try {
			return new FileInputStream(new File(editor.getStringValue()));
		} catch (FileNotFoundException e) {
			return null;
		}
	}
	
	public IFolder createNewFolder() {
		IFolder importFolder = getNewFolder();	
		try {
			final IProgressMonitor subProgressMonitor = new SubProgressMonitor(new NullProgressMonitor(), 1);
			OfsCore.createFolder(importFolder);			
			importFolder.setPersistentProperty(GENERATED_RESOURCE_NAME, folderName);
			return importFolder;
		} catch (CoreException e) {
			OfsCore.getDefault().logError("Error creating the import folder " + importFolder.getName(), e);
		}
		return null;
	}

	public void deleteNewFolder() {
		if(outputFolder!=null && outputFolder.exists()) {
			String[] pathSegments = StringHelper.withoutExtension(
					(new File(editor.getStringValue())).getName()).split("-");

			IFolder importFolder = outputFolder;
			for(String pathSegment : pathSegments) {
				importFolder = importFolder.getFolder(convertToJavaCompliantName(pathSegment));
			}
			try {
				while(importFolder!=outputFolder && importFolder.getParent() instanceof IFolder) {
					if(importFolder.members().length==0) {
						importFolder.delete(false, null);
					}
					importFolder = (IFolder) importFolder.getParent();
				}
			} catch (CoreException e) {
				OfsCore.getDefault().logWarning("Could not delete empty import folder '" + importFolder + "'", e);
				e.printStackTrace();
			}
		}
	}

	protected IFolder getNewFolder() {
		if(outputFolder!=null && outputFolder.exists()) {
			String[] pathSegments = StringHelper.withoutExtension(
					(new File(editor.getStringValue())).getName()).split("-");

			IFolder importFolder = outputFolder;
			for(String pathSegment : pathSegments) {
				importFolder = importFolder.getFolder(convertToJavaCompliantName(pathSegment));	

			}
			folderName = importFolder.getName();
			return importFolder;
		}
		return null;
	}

	/**
	 * Converts the folderName to comply with java naming convention
	 * @param importFolderName
	 * @return String
	 */
	private String convertToJavaCompliantName(String importFolderName) {
		// do not use upper case 
		importFolderName = importFolderName.toLowerCase();

		// remove blank space		
		importFolderName = importFolderName.replaceAll("\\s", "");		
		importFolderName = importFolderName.replaceAll("-", "_");
		importFolderName = importFolderName.replaceAll("^\\d", "");
		
		//remove special chars
		Set<Character> barredChars = new HashSet<Character>();
		for (int i = 0; i < importFolderName.length(); i++) {
			char c = importFolderName.charAt(i);
				if (!(Character.isJavaIdentifierPart(c))) {
					barredChars.add(c);
				}
		}
		for(char c : barredChars) {
			importFolderName = StringUtils.remove(importFolderName, c);
		}
		return StringUtils.uncapitalize(importFolderName);
	}

	public IFolder getOutputFolder() {
		return outputFolder;
	}

	public void setOutputFolder(IFolder outputFolder) {
		this.outputFolder = outputFolder;
	}
    
}
