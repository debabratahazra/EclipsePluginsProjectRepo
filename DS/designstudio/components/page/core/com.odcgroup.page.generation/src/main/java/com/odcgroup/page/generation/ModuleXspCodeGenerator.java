package com.odcgroup.page.generation;

import com.odcgroup.page.common.PageConstants;
import com.odcgroup.workbench.core.IOfsModelResource;

/**
 * Code generator for modules and page fragments.
 * 
 * @author Gary Hayes
 */
public class ModuleXspCodeGenerator extends AbstractXspCodeGenerator {

	/**
	 * Gets the files extension handled by this Code Generator.
	 * 
	 * @return String The file extension handled by this Code Generator
	 */
	protected String getHandledFileExtension() {
		return PageConstants.MODULE_FILE_EXTENSION;
	}	
	
    /**
     * Gets the output file path relative to the project.
     * 
     * @param resource The input resource
     * @return String The output file path
     */
    protected String getOutputFilePath(IOfsModelResource resource) {
        // The ModuleProjectInitializers automatically create the directory and "module".
        // This needs to be removed from the output path.
        // The resource path also contains the name of the file. This also needs to be removed.
    	String path = resource.getURI().trimSegments(1).path();
    	if(path.startsWith("/resource")) {
    		path = path.substring(9,path.length());
    	}
    	
        return path;
    }
    
}
