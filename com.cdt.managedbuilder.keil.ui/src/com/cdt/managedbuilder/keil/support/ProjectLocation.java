package com.cdt.managedbuilder.keil.support;

import org.eclipse.cdt.core.model.IBinaryContainer;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;




public class ProjectLocation  {
	
	static final String C_PROJECTS_VIEW = "org.eclipse.cdt.ui.CView";
	IResource resource = null;
	IContainer container = null;
	boolean error=true;
	private Display display=Display.getDefault();
	IWorkbenchWindow activeWindow=null;
	IStructuredSelection iStructSelection = null;
	String filename=null;
	
	public ProjectLocation(boolean error) {
		this.error=error;
	}

	public String projectLocation() {				
		
		display.asyncExec(new Runnable(){
			public void run() {				
				activeWindow = PlatformUI.getWorkbench().getActiveWorkbenchWindow();				
				
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
		            	return;
		            }
		            //Project is Open.........
		            else {              		            	
		            		IPath  iPath=  container.getLocation();       
		            		String projectPath=iPath.toOSString();          		
		            		
		            		filename=projectPath;
		            		return ;
		            }
		        }			
			}
		});	
		
		//MultiThread Synchronization.
		while(true){
			if(filename==null){
				try{
					Thread.sleep(5);
				}catch(Exception e){}
			}else{
				break;
			}
		}
    		
		return filename;
	}	
	
}
