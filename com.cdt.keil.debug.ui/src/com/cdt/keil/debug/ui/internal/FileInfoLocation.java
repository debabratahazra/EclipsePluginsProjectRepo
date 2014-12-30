package com.cdt.keil.debug.ui.internal;


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


public class FileInfoLocation  {
	
	static final String C_PROJECTS_VIEW = "org.eclipse.cdt.ui.CView";
	IResource resource = null;
	IContainer container = null;
	boolean error=true;
	
	public FileInfoLocation(boolean error) {
		this.error=error;
	}

	public String fileInfoLocation() {

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
            	ConsoleDisplayMgr.getDefault().print("", 2);
            	ConsoleDisplayMgr.getDefault().println("Project is Closed.", 2);
            	return null;
            }
            //Project is Open.........
            else {              		            	
            		IPath  iPath=  container.getLocation();       
            		String projectPath=iPath.toOSString();            		 
            		
            		String dsmFileLocationDebug = new String(projectPath + "\\Debug" + 
            				"\\" + ".FileInfo.txt");            		      
            		String dsmFileLocationRelease = new String(projectPath + "\\Release" + 
            				"\\" + ".FileInfo.txt");             	      
            		File fileDebug= new File(dsmFileLocationDebug);
            		File fileRelease=new File(dsmFileLocationRelease);      
            		
            		
            		//Only Debug Mode......
            		if(fileDebug.exists() && !fileRelease.exists()){
            			return (projectPath + "\\Debug\\.FileInfo.txt");
            		}
            		
            		//Only Release Mode......
            		else if(fileRelease.exists() && !fileDebug.exists()){
            			return (projectPath + "\\Release\\.FileInfo.txt");            			
            		}
            		else if(fileDebug.exists() && fileRelease.exists()){
            			return (projectPath + "\\Debug\\.FileInfo.txt");
            		}            		
            }
        }		
		return null;
	}	
	
}
