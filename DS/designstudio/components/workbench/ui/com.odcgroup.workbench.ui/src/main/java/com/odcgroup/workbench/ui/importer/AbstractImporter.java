package com.odcgroup.workbench.ui.importer;

import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;

import com.odcgroup.workbench.core.OfsCore;

abstract public class AbstractImporter<ModelType> {
	
	
	String importedFileName = null;
	
	/**
	 * @param content
	 * @param folder
	 * @param importedFileName
	 * @return
	 */
	public boolean runImport(final InputStream content, final IFolder folder, String importedFileName) {
		setImportedFileName(importedFileName);
		return runImport(content, folder);
	}
		
	public boolean runImport(final InputStream content, final IFolder folder) {
	    // Run the actual operation for creating the project.
	    IRunnableWithProgress op = new IRunnableWithProgress() {
	      public void run( IProgressMonitor monitor ) throws InvocationTargetException {
	        try {
	            execute(content,
	            		folder.getProject().getName(), 
	            		folder.getFullPath(),
	            		monitor );
	        } finally {
	          monitor.done();
	        }
	      }
	    };

	    try {
	    	IWorkbench workbench = PlatformUI.getWorkbench();
	    	workbench.getProgressService().run(false, true, op );
	    } catch ( InterruptedException e ) {
	    	// return without a message
	    	return true;
	    } catch ( InvocationTargetException e ) {
	      Throwable realException = e.getTargetException();
	      IStatus status = new Status(Status.ERROR, OfsCore.PLUGIN_ID, Status.OK, e.getMessage(), e.getTargetException());
	      Shell shell = new Shell();
	      ErrorDialog.openError(shell, "Error", "File import failed.", status);
	      shell.dispose();
	      return false;
	    }
	    return true;
	}

	/**
	 * This interface defines a method for direct execution of an import from a stream to a path
	 * 
	 * @param content An input stream of the content to import
	 * @param projectName The name of the project for which these files are imported
	 * @param path A file system path where resulting files will be stored
	 * @param monitor a progress monitor, if needed (should allow null values)
	 * @return true if execution was successful, otherwise false
	 * @throws InvocationTargetException thrown if an error occurs during execution
	 */
	abstract public boolean execute(InputStream content, String projectName, IPath path, IProgressMonitor monitor) 
	throws InvocationTargetException;
	
    /**
     * Validates dropping on the given object. This method is called whenever the importer
     * must decide whether it want to react on a given dropped object
     * 
     * @param target the object that the mouse is currently hovering over, or
     *   <code>null</code> if the mouse is hovering over empty space
     * @param files an array of filenames to be dropped into the target
     * @return <code>true</code> if the importer reacts on this drop, and <code>false</code>
     *   otherwise
     */
	abstract public boolean validateDrop(Object target, String[] files);

	/**
	 * Returns an array of all models that have been created by the latest import execution.
	 * 
	 * @return the imported models
	 */
	abstract public List getModels();

	public String getImportedFileName() {
		return importedFileName;
	}

	public void setImportedFileName(String importedFileName) {
		this.importedFileName = importedFileName;
	}

}
