package com.odcgroup.iris.generator;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.ecore.EObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Charsets;
import com.odcgroup.workbench.core.repository.OfsResourceHelper;

/**
 * Utility class that helps decide whether it's safe to indicate data type information for a given field or application.
 * Implemented as an enum to give us singleton behaviour.
 * 
 * Maybe this can be captured in the enquiry model at some point, removing the need for a separate config.
 *
 * @author agoulding
 *
 */
public class FieldTypeChecker {
	
	private static Logger LOGGER = LoggerFactory.getLogger(FieldTypeChecker.class);
	
	private final String FILE_NAME = "typeSafeFields.txt";
    private String text;
        

    /**
	 * @return the text
	 */
	public String getText() {
		return text;
	}


	/**
     * Creates an empty FieldTypeChecker
     */
    public FieldTypeChecker() {
        this.text = "";
    }


	/**
	 * FieldTypeChecker constructor with name argument
	 * 
	 * @param enqApplnVerObject
	 */
	public FieldTypeChecker(EObject enqApplnVerObject) {
		this.text = setTextValue(enqApplnVerObject);
	}
	
	/**
	 * Reads the file at the given path and sets textValue = the values found there
	 * 
	 * @param enqApplnVerObject
	 */
	private String setTextValue(EObject enqApplnVerObject) {
		String textValue = "";
		try {
			String projectLocation = findProjectLocation(enqApplnVerObject);

			File file = new File(projectLocation + File.separatorChar + FILE_NAME);
			if (file.exists()) {
				// Add newlines so that isTypeSafe() works on first and last entries
				textValue = "\r\n" + FileUtils.readFileToString(file, Charsets.UTF_8.name()) + "\r\n";
			}
		} catch (IOException e) {
			textValue = "";
		} catch (CoreException e) {
			textValue = "";
		}
		return textValue;
	}

    /**
     * Finds the project location from the given name.
     * 
	 * @param enqApplnVerObject
	 * @return projectLocation
     * @throws CoreException 
	 */
	private String findProjectLocation(EObject enqApplnVerObject) throws CoreException {
		String projectLocation = null;
		if(enqApplnVerObject.eResource()==null){
			LOGGER.error("No resource attached for '" + enqApplnVerObject + "'" );
		} else {
			IProject iproject = OfsResourceHelper.getProject(enqApplnVerObject.eResource());
			if(iproject==null && enqApplnVerObject.eResource().getURI().toString().startsWith("bundleresource")) {
				//for unit test as protocol bundleresource:/324xxx/:/com/.... isn't resolved.
				projectLocation = "unitTestProject";
			} else if (iproject != null) {
				projectLocation = iproject.getLocation().toOSString();
			}
		}
		return projectLocation;
	}

	/**
     * This method provided so that it's possible to unit test the functionality.
     * @param text
     */
    public void setText(String text) {
        this.text = "\r\n" + text + "\r\n"; // Add newlines so that isTypeSafe() works on first and last entries
    }
	
    /**
     * Checks if an application / field combination is present in the typeSafeFields.
     * If an applicationName alone is found, all fields in the application are treated as safe.
     * @param applicationName
     * @param fieldname
     * @return
     */
    public boolean isTypeSafe(String applicationName, String fieldname) {
        if (this.text.indexOf("\r\n" + applicationName + "\r\n") > -1) {
            return true;
        }
        if (this.text.indexOf("\r\n" + applicationName + "/" + fieldname + "\r\n") > -1) {
            return true;
        }
        return false;
    }
}
