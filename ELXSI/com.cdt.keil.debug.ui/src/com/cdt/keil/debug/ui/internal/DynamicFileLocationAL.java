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

public class DynamicFileLocationAL {
	
	static final String C_PROJECTS_VIEW = "org.eclipse.cdt.ui.CView";
	public static String absoluteFilePath = new String();
	public static String relativeFilePath = new String();
	IResource resource = null;
	IContainer container = null;
	FileInfoName fName = null;	
	
	public DynamicFileLocationAL() {
		fName = new FileInfoName();
	}
		
	public String dynFileLocation(int address, int value) {		
		
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
            	ConsoleDisplayMgr.getDefault().println("PROJECT IS CLOSED.", 2);
            	return null;
            }
            //Project is Open.........
            else {              		            	
            		IPath  iPath=  container.getLocation();       
            		String projectPath=iPath.toOSString();     
            		String projectName=container.getName(); 
            		String fileName = fName.getFileInfo(address);
            		
            		String dsmFileLocationDebug = new String(projectPath + "\\Debug" + 
            				"\\" + fileName);            		      
            		String dsmFileLocationRelease = new String(projectPath + "\\Release" + 
            				"\\" + fileName);             	      
            		File fileDebug= new File(dsmFileLocationDebug);
            		File fileRelease=new File(dsmFileLocationRelease);      
            		
            		
            		//Only Debug Mode......
            		//relative path value.
            		if(fileDebug.exists() && !fileRelease.exists() && value==0){
            			relativeFilePath=projectName + "\\Debug" + "\\" + fileName;
            			return (projectName + "\\Debug" + "\\" + fileName);
            		}
            		//absolute path value.
            		else if(fileDebug.exists() && !fileRelease.exists() && value==1){
            			absoluteFilePath=dsmFileLocationDebug;
            			return (dsmFileLocationDebug);
            		}
            		
            		//Only Release Mode......
            		//relative path value.
            		else if(fileRelease.exists() && !fileDebug.exists() && value==0){
            			relativeFilePath=projectName + "\\Release" + "\\" + fileName;
            			return (projectName + "\\Release" + "\\" + fileName);            			
            		}
            		//absolute path value.
            		else if(fileRelease.exists() && !fileDebug.exists() && value==1){
            			absoluteFilePath=dsmFileLocationRelease;
            			return (dsmFileLocationRelease);            			
            		}
            		
            		
            		else if(fileDebug.exists() && fileRelease.exists() && value==1){
            			absoluteFilePath=dsmFileLocationDebug;
            			return (dsmFileLocationDebug);
            		}            		
            		
            		else if(fileDebug.exists() && fileRelease.exists() && value==0){
            			relativeFilePath=projectName + "\\Debug" + "\\" + fileName;
            			return (projectName + "\\Debug" + "\\" + fileName);
            		}
            }
        }		
		return null;
	}	
	
	
	
	public String dynFileLoc(int address, int value) {

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
            	return null;
            }
            //Project is Open.........
            else {              		            	
            		IPath  iPath=  container.getLocation();       
            		String projectPath=iPath.toOSString();     
            		String projectName=container.getName(); 
            		String fileName = fName.getFileInfo(address);
            		
            		String dsmFileLocationDebug = new String(projectPath + "\\Debug" + 
            				"\\" + fileName);            		      
            		String dsmFileLocationRelease = new String(projectPath + "\\Release" + 
            				"\\" + fileName);             	      
            		File fileDebug= new File(dsmFileLocationDebug);
            		File fileRelease=new File(dsmFileLocationRelease);      
            		
            		
            		//Only Debug Mode......
            		//relative path value.
            		if(fileDebug.exists() && !fileRelease.exists() && value==0){
            			relativeFilePath=projectName + "\\Debug" + "\\" + fileName;
            			return (projectName + "\\Debug" + "\\" + fileName);
            		}
            		//absolute path value.
            		else if(fileDebug.exists() && !fileRelease.exists() && value==1){
            			absoluteFilePath=dsmFileLocationDebug;
            			return (dsmFileLocationDebug);
            		}
            		
            		//Only Release Mode......
            		//relative path value.
            		else if(fileRelease.exists() && !fileDebug.exists() && value==0){
            			relativeFilePath=projectName + "\\Release" + "\\" + fileName;
            			return (projectName + "\\Release" + "\\" + fileName);            			
            		}
            		//absolute path value.
            		else if(fileRelease.exists() && !fileDebug.exists() && value==1){
            			absoluteFilePath=dsmFileLocationRelease;
            			return (dsmFileLocationRelease);            			
            		}
            		
            		
            		else if(fileDebug.exists() && fileRelease.exists() && value==1){
            			absoluteFilePath=dsmFileLocationDebug;
            			return (dsmFileLocationDebug);
            		}            		
            		
            		else if(fileDebug.exists() && fileRelease.exists() && value==0){
            			relativeFilePath=projectName + "\\Debug" + "\\" + fileName;
            			return (projectName + "\\Debug" + "\\" + fileName);
            		}
            }
        }		
		absoluteFilePath="";	relativeFilePath="";
		return null;
	}	
	
	
	public String dynFileLocInfo(String filename, int value) {
		//Return Full or absolute file location.

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
            	return null;
            }
            //Project is Open.........
            else {              		            	
            		IPath  iPath=  container.getLocation();       
            		String projectPath=iPath.toOSString();     
            		String projectName=container.getName(); 
            		String fileName = filename;
            		
            		String dsmFileLocationDebug = new String(projectPath + "\\Debug" + 
            				"\\" + fileName);            		      
            		String dsmFileLocationRelease = new String(projectPath + "\\Release" + 
            				"\\" + fileName);             	      
            		File fileDebug= new File(dsmFileLocationDebug);
            		File fileRelease=new File(dsmFileLocationRelease);      
            		
            		
            		//Only Debug Mode......
            		//relative path value.
            		if(fileDebug.exists() && !fileRelease.exists() && value==0){
            			relativeFilePath=projectName + "\\Debug" + "\\" + fileName;
            			return (projectName + "\\Debug" + "\\" + fileName);
            		}
            		//absolute path value.
            		else if(fileDebug.exists() && !fileRelease.exists() && value==1){
            			absoluteFilePath=dsmFileLocationDebug;
            			return (dsmFileLocationDebug);
            		}
            		
            		//Only Release Mode......
            		//relative path value.
            		else if(fileRelease.exists() && !fileDebug.exists() && value==0){
            			relativeFilePath=projectName + "\\Release" + "\\" + fileName;
            			return (projectName + "\\Release" + "\\" + fileName);            			
            		}
            		//absolute path value.
            		else if(fileRelease.exists() && !fileDebug.exists() && value==1){
            			absoluteFilePath=dsmFileLocationRelease;
            			return (dsmFileLocationRelease);            			
            		}
            		
            		
            		else if(fileDebug.exists() && fileRelease.exists() && value==1){
            			absoluteFilePath=dsmFileLocationDebug;
            			return (dsmFileLocationDebug);
            		}            		
            		
            		else if(fileDebug.exists() && fileRelease.exists() && value==0){
            			relativeFilePath=projectName + "\\Debug" + "\\" + fileName;
            			return (projectName + "\\Debug" + "\\" + fileName);
            		}
            }
        }		
		absoluteFilePath="";	relativeFilePath="";
		return null;
	}
	

}
