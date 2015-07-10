package com.cdt.keil.debug.ui.internal;


import org.eclipse.cdt.core.model.IBinaryContainer;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

public class RefreshFile extends Thread{
	public static final String S_C_PROJECTS_VIEW = "org.eclipse.cdt.ui.CView";
	IResource resource = null;
	IContainer container = null;

	public void run()
	{
		IStructuredSelection ssel = null;
		IWorkbenchWindow activeWindow = PlatformUI.getWorkbench()
							.getActiveWorkbenchWindow();
		if (activeWindow != null) {
			ISelection sel = activeWindow.getSelectionService()
						.getSelection(S_C_PROJECTS_VIEW);
			if (sel instanceof IStructuredSelection) {
				ssel = (IStructuredSelection) sel;
			}
			
			Object obj = ssel.getFirstElement();
			if (obj instanceof IResource)
				resource = (IResource) obj;
			else if (obj instanceof IBinaryContainer) {
				IBinaryContainer bincon = (IBinaryContainer) obj;
				resource = bincon.getUnderlyingResource();
			} else if (obj instanceof IAdaptable) {
				IAdaptable adaptable = (IAdaptable) obj;
				resource = (IResource) adaptable.getAdapter(IResource.class);
			}
		}
		
        if (resource != null) {
            int type = resource.getType();
            switch (type) {
                case IResource.FILE :
                    container = resource.getParent();
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
        }
        
       String debugName = new String("\\Debug");
       String releaseName = new String ("\\Release");
       
       try {
		Thread.sleep(400);
       } catch (InterruptedException e2) {}
       catch(Exception e){}
       
       try {
		ResourcesPlugin.getWorkspace().getRoot().refreshLocal(IResource.DEPTH_INFINITE, null);
	} catch (CoreException e1) {}
	catch(Exception e){}
	
	
       IFolder debugFolder;
       IFolder releaseFolder;
	try {
			IPath  debugPath =  container.getLocation().append(debugName);   
			IPath releasePath = container.getLocation().append(releaseName);
		
			debugFolder = ResourcesPlugin.getWorkspace().getRoot().getFolder(debugPath);
			releaseFolder = ResourcesPlugin.getWorkspace().getRoot().getFolder(releasePath);
		   
		   while(!debugFolder.exists() | !releaseFolder.exists())
	       {    	   
	       }
	} catch (Exception e1) {		
		
	}
       
       
       
       try {
		ResourcesPlugin.getWorkspace().getRoot().refreshLocal(IResource.DEPTH_INFINITE, null);
	} catch (CoreException e) {	}
	catch(Exception e){}
	}
}
	