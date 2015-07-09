package com.odcgroup.page.generation;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;

import com.odcgroup.page.common.PageConstants;
import com.odcgroup.workbench.core.IOfsModelResource;

/**
 * Code generator for pages.
 * 
 * @author Gary Hayes
 */
public class PageXspCodeGenerator extends AbstractXspCodeGenerator {

	/**
	 * Gets the files extension handled by this Code Generator.
	 * 
	 * @return String The file extension handled by this Code Generator
	 */
	protected String getHandledFileExtension() {
		return PageConstants.PAGE_FILE_EXTENSION;
	}
	
    /**
     * Gets the output file path relative to the project.
     * 
     * @param resource The input resource
     * @return String The output file path
     */
    protected String getOutputFilePath(IOfsModelResource resource) {
        // The ModuleProjectInitializers automatically create the directory and "page".
        // This needs to be removed from the output path.
        // The resource path also contains the name of the file. This also needs to be removed.
        IPath path = new Path(resource.getURI().trimSegments(1).path());
        
        // The project path should be WuiProfile/activity/projectCode/myDirectory where
        // project code is something like cdm. We need to add a directory 'target' after this since
        // within OCS the page files are stored under this directory
        if (path.segmentCount() >= 3) {
            String p = "";
            for (int i = 0; i < path.segmentCount(); ++i) {
                p += path.segment(i).startsWith("resource")? "" : path.segment(i);
                if (i == 2) {
                    p += "/target";
                }
                if (i < path.segmentCount() - 1) {
                    p += "/";
                }
            }
            
            path  = new Path(p);
        }
        
        return path.toString();
    }	
}
