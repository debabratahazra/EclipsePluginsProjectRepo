package com.tel.autosysframework.workspace;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;



public class ProjectInformation {
	
	static final String PROJECTS_VIEW = "org.eclipse.ui.views.ResourceNavigator";
	IResource resource = null;
	IContainer container = null;
		
	public ProjectInformation() {		
	}
	
		
	/**
	 * "type" define the return type
	 * type = 0 => Absolute project path with "Configfile.ini"
	 * type = 1 => Relative Project Path with "Configfile.ini"
	 * type = 2 => Project Name 
	 * type = other => Absolute Project path only
	 * @param type
	 * @return project path
	 */
	public String getProjectName(int type) {
		
				
		IStructuredSelection iStructSelection = null;
		IWorkbenchWindow activeWindow = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		
		if (activeWindow != null) {
			ISelection iSelection = activeWindow.getSelectionService().getSelection(PROJECTS_VIEW);
			
			if (iSelection instanceof IStructuredSelection) {
				iStructSelection = (IStructuredSelection) iSelection;
			}
			Object obj = iStructSelection.getFirstElement();
			
			if (obj instanceof IResource)
				resource = (IResource) obj;			
			else if (obj instanceof IAdaptable) {
				IAdaptable iAdaptable = (IAdaptable) obj;
				resource = (IResource) iAdaptable.getAdapter(IResource.class);
			}
		}
		
        //Select the project name.....
		if (resource != null) 
        {
            int type_2 = resource.getType();
            switch (type_2) {
                case IResource.FILE :
                    container = resource.getProject();
                    break;
                case IResource.FOLDER :
                    container = resource.getProject();
                    break;
                case IResource.PROJECT :
                    container = (IContainer) resource;
                    break;
                case IResource.ROOT :
                    break;
            }     
        
            //Project is Closed.....
            if(!container.isAccessible()){            	
            	return null;
            }
            
            //Project is Open.........
            else {              		            	
            		IPath  iPath=  container.getLocation();       
            		String projectPath=iPath.toOSString();     
            		String projectName=container.getName();
            		
            		switch(type){
            		case 0:
            			return (projectPath + "\\Configfile.ini");            			
            		case 1:
            			return (projectName + "\\Configfile.ini");   
            		case 2:
            			return projectName;           			
            		default:
            			return projectPath;            			
            		}            		            		
            }
        }		
		return null;
	}
}
