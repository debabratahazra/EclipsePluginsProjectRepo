package com.tel.autosysframework.internal;


import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

public class Refresh extends Thread{
	public static final String PROJECTS_VIEW = "org.eclipse.ui.views.ResourceNavigator";
	IResource resource = null;
	IContainer container = null;

	public void run()
	{
		IStructuredSelection ssel = null;
		IWorkbenchWindow activeWindow = PlatformUI.getWorkbench()
		.getActiveWorkbenchWindow();
		if (activeWindow != null) {
			ISelection sel = activeWindow.getSelectionService()
			.getSelection(PROJECTS_VIEW);
			if (sel instanceof IStructuredSelection) {
				ssel = (IStructuredSelection) sel;
			}

			Object obj = ssel.getFirstElement();
			if (obj instanceof IResource)
				resource = (IResource) obj;
			else if (obj instanceof IAdaptable) {
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


		try {
			Thread.sleep(300);
		} catch (InterruptedException e2) {}
		catch(Exception e){}

		try {
			ResourcesPlugin.getWorkspace().getRoot().refreshLocal(IResource.DEPTH_INFINITE, null);
		} catch (CoreException e1) {}
		catch(Exception e){}  

		try {
			ResourcesPlugin.getWorkspace().getRoot().refreshLocal(IResource.DEPTH_INFINITE, null);
		} catch (CoreException e) {	}
		catch(Exception e){}
	}
}
