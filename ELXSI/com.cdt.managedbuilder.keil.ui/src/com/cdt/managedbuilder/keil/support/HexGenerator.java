package com.cdt.managedbuilder.keil.support;

import java.io.File;

import org.eclipse.cdt.core.model.IBinaryContainer;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.PlatformUI;

import com.cdt.managedbuilder.keil.support.RefreshFile;;

public class HexGenerator implements IWorkbenchWindowActionDelegate {
	
	public static final String C_PROJECTS_VIEW = "org.eclipse.cdt.ui.CView";
	IResource resource = null;
	IContainer container = null;
	private IWorkbenchWindow window;

	
	public void dispose() {	
	}

	public void init(IWorkbenchWindow window) {
		this.window=window;
	}
	
	

	public void run(IAction action) {
		
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
            	MessageDialog.openError(window.getShell(), "Hex Generator", "Project Not Open, Hex File Generation Failed."); 	
            }
            else {
        
            		String projectName=container.getName();
            	
            		IPath  iPath=  container.getLocation();
       
            		String projectPath=iPath.toOSString();
       
       
            		String commandDebug = new String("OH51.exe" + " \"" + projectPath + "\\Debug" + "\\" + projectName.toUpperCase() + "\"" + 
            				" " + "HEXFILE(" + " \"" + projectPath + "\\Debug" + "\\" + projectName.toUpperCase() + ".HEX" + "\")");
            		String debugFile = new String(projectPath + "\\Debug" + "\\" + projectName.toUpperCase());
      
            		String commandRelease = new String("OH51.exe" + " \"" + projectPath + "\\Release" + "\\" + projectName.toUpperCase() + "\"" +
            				" " + "HEXFILE(" + " \"" + projectPath + "\\Release" + "\\" + projectName.toUpperCase() + ".HEX" + "\")");
            		String releaseFile = new String(projectPath + "\\Release" + "\\" + projectName.toUpperCase());
      
            		File fileDebug= new File(debugFile);
            		File fileRelease=new File(releaseFile);
      
            		//Both Debug & Release Mode.........
            		if(fileDebug.exists() && fileRelease.exists()){
    	  
            			try {
            				Process process1 = Runtime.getRuntime().exec(commandDebug);
            				Process process2 = Runtime.getRuntime().exec(commandRelease);
            				MessageDialog.openInformation(window.getShell(), "Hex Generator", 
            						"Hex File Generated Successfully in both Debug and Release Mode.");
            				
            				try {
            					ResourcesPlugin.getWorkspace().getRoot().refreshLocal(IResource.DEPTH_INFINITE, null);
            				} catch (CoreException e) {}
            			} catch (Exception e) {	
            				try {
            					ResourcesPlugin.getWorkspace().getRoot().refreshLocal(IResource.DEPTH_INFINITE, null);
            				} catch (CoreException e1) {}  	  
            				
            			}	
    	  
            		}      
      
            		//Only Debug Mode......
            		else if(fileDebug.exists() && !fileRelease.exists()){     
            			try {
            				Process process = Runtime.getRuntime().exec(commandDebug);
            				MessageDialog.openInformation(window.getShell(), 
            						"Hex Generator", "Hex File Generated Successfully in Debug Mode.");
            				try {
            					ResourcesPlugin.getWorkspace().getRoot().refreshLocal(IResource.DEPTH_INFINITE, null);
            				} catch (CoreException e) {}
            			} catch (Exception e) {	
            				try {
            					ResourcesPlugin.getWorkspace().getRoot().refreshLocal(IResource.DEPTH_INFINITE, null);
            				} catch (CoreException e1) {}	  
            				
            			}	
            		}
            		
            		//Only Release Mode......
            		else if(fileRelease.exists() && !fileDebug.exists()){
    	  
            			try {
            				Process process = Runtime.getRuntime().exec(commandRelease);
            				MessageDialog.openInformation(window.getShell(), "Hex Generator", 
            						"Hex File Generated Successfully in Release Mode.");
            				try {
            					ResourcesPlugin.getWorkspace().getRoot().refreshLocal(IResource.DEPTH_INFINITE, null);
            				} catch (CoreException e) {}
            			} catch (Exception e) {	
            				try {
            					ResourcesPlugin.getWorkspace().getRoot().refreshLocal(IResource.DEPTH_INFINITE, null);
            				} catch (CoreException e1) {}            				
            			}	
    	  
            		}
            		
            		else{
            			//Build not successfully.......
            			MessageDialog.openError(window.getShell(), "Hex Generator", "Build Error, Hex File Generation Failed."); 	  
            		}
            }
        }
        else
        	//File or Project not found........
        	MessageDialog.openError(window.getShell(), "Hex Generator", "Project Name Not Found, Hex File Generation Failed."); 
        
     //Refreshing.....
      RefreshFile refresh2 = new RefreshFile();
      refresh2.start();
     
	}
	

	public void selectionChanged(IAction action, ISelection selection) {	

	}

}
