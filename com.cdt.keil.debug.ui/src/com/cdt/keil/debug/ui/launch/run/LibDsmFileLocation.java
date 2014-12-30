package com.cdt.keil.debug.ui.launch.run;


import java.io.File;
import org.eclipse.cdt.core.model.IBinaryContainer;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import com.cdt.keil.debug.ui.console.ConsoleDisplayMgr;
import com.cdt.keil.debug.ui.serialPortComm.RefreshFile;


public class LibDsmFileLocation  {
	
	static final String C_PROJECTS_VIEW = "org.eclipse.cdt.ui.CView";
	IResource resource = null;
	IContainer container = null;
	boolean error=true;
	
	public LibDsmFileLocation(boolean error) {
		this.error=error;
	}

	public String LibDsmFileLocInfo() {

		//Refresh Workspace
		 RefreshFile refresh=new RefreshFile();
		 refresh.start();
		
		IStructuredSelection iStructSelection = null;
		IWorkbenchWindow activeWindow = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		
		if (activeWindow != null) {
			ISelection iSelection = activeWindow.getSelectionService().getSelection(C_PROJECTS_VIEW);
			
			if (iSelection instanceof IStructuredSelection) {
				iStructSelection = (IStructuredSelection) iSelection;
			}
			
			Object obj = iStructSelection.getFirstElement();
			
			if (obj instanceof IResource)
				resource = (IResource) obj;
			else if (obj instanceof IBinaryContainer) {
				IBinaryContainer iBinContainer = (IBinaryContainer) obj;
				resource = iBinContainer.getUnderlyingResource();
			} else if (obj instanceof IAdaptable) {
				IAdaptable iAdaptable = (IAdaptable) obj;
				resource = (IResource) iAdaptable.getAdapter(IResource.class);
			}
		}
		
        //Select the project name.....
		if (resource != null) 
        {
            int type = resource.getType();
            switch (type) {
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
            	//ConsoleDisplayMgr.getDefault().print("", 2);
            	ConsoleDisplayMgr.getDefault().println("Project is Closed.", 2);
            	return null;
            }
            //Project is Open.........
            else {              		            	
            		IPath  iPath=  container.getLocation();       
            		String projectPath=iPath.toOSString();     
            		String projectName=container.getName(); 
            		
            		String dsmFileLocationDebug = new String(projectPath + "\\Debug" + 
            				"\\" + "LIBRARY.DSM");            		      
            		String dsmFileLocationRelease = new String(projectPath + "\\Release" + 
            				"\\" + "LIBRARY.DSM");             	      
            		File fileDebug= new File(dsmFileLocationDebug);
            		File fileRelease=new File(dsmFileLocationRelease);      
            		
            		
            		//Only Debug Mode......
            		if(fileDebug.exists() && !fileRelease.exists()){
            			return (projectName + "\\Debug\\LIBRARY.DSM");
            		}
            		
            		//Only Release Mode......
            		else if(fileRelease.exists() && !fileDebug.exists()){
            			return (projectName + "\\Release\\LIBRARY.DSM");            			
            		}
            		else if(fileDebug.exists() && fileRelease.exists()){
            			return (projectName + "\\Debug\\LIBRARY.DSM");
            		}            		
            }
        }
		if(error){
			//ConsoleDisplayMgr.getDefault().print("", 2);
			ConsoleDisplayMgr.getDefault().println("LIBRARY.DSM FILE NOT FOUND FROM SELECTED PROJECT.",2);			
		}
		return null;
	}
	
	public String dsmDetailFileLocation() {

		
		//Refresh Workspace
		 RefreshFile refresh=new RefreshFile();
		 refresh.start();
		
		IStructuredSelection iStructSelection = null;
		IWorkbenchWindow activeWindow = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		
		if (activeWindow != null) {
			ISelection iSelection = activeWindow.getSelectionService().getSelection(C_PROJECTS_VIEW);
			
			if (iSelection instanceof IStructuredSelection) {
				iStructSelection = (IStructuredSelection) iSelection;
			}
			
			Object obj = iStructSelection.getFirstElement();
			
			if (obj instanceof IResource)
				resource = (IResource) obj;
			else if (obj instanceof IBinaryContainer) {
				IBinaryContainer iBinContainer = (IBinaryContainer) obj;
				resource = iBinContainer.getUnderlyingResource();
			} else if (obj instanceof IAdaptable) {
				IAdaptable iAdaptable = (IAdaptable) obj;
				resource = (IResource) iAdaptable.getAdapter(IResource.class);
			}
		}
		
        //Select the project name.....
		if (resource != null) 
        {
            int type = resource.getType();
            switch (type) {
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
            	//ConsoleDisplayMgr.getDefault().print("", 2);
            	ConsoleDisplayMgr.getDefault().println("Project is Closed.", 2);
            	return null;
            }
            //Project is Open.........
            else {              		            	
            		IPath  iPath=  container.getLocation();       
            		String projectPath=iPath.toOSString();            		 
            		
            		String dsmFileLocationDebug = new String(projectPath + "\\Debug" + 
            				"\\" + "LIBRARY.DSM");            		      
            		String dsmFileLocationRelease = new String(projectPath + "\\Release" + 
            				"\\" + "LIBRARY.DSM");             	      
            		File fileDebug= new File(dsmFileLocationDebug);
            		File fileRelease=new File(dsmFileLocationRelease);      
            		
            		
            		//Only Debug Mode......
            		if(fileDebug.exists() && !fileRelease.exists()){
            			return (dsmFileLocationDebug);
            		}
            		
            		//Only Release Mode......
            		else if(fileRelease.exists() && !fileDebug.exists()){
            			return (dsmFileLocationRelease);            			
            		}
            		else if(fileDebug.exists() && fileRelease.exists()){
            			return (dsmFileLocationDebug);
            		}            		
            }
        }
		if(error){
			//ConsoleDisplayMgr.getDefault().print("", 2);
			ConsoleDisplayMgr.getDefault().println("LIBRARY.DSM FILE NOT FOUND FROM SELECTED PROJECT.",2);			
		}
		return null;
	}
}
