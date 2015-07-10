package samplecode.source.action;

import org.eclipse.core.internal.resources.File;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.ui.IViewActionDelegate;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;

@SuppressWarnings("restriction")
public class OpenFileViewActionDelegate implements IViewActionDelegate {
	
	private TreeSelection treeSelection ;

	@Override
	public void run(IAction action) {
		
		if(treeSelection == null) return;
		Object object = treeSelection.getFirstElement();
		if(object instanceof File){
			IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
			
			IWorkspace workspace= ResourcesPlugin.getWorkspace();    
			IPath location= Path.fromOSString(((File) object).getRawLocation().toOSString()); 
			IFile input= workspace.getRoot().getFileForLocation(location);
			
			try {
				IDE.openEditor(page, input);
			} catch (PartInitException e) {
				e.printStackTrace();
			}
			
		}
	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		if(!selection.isEmpty()){
			if(selection instanceof TreeSelection){
				treeSelection = (TreeSelection) selection;
			}
		}
	}

	@Override
	public void init(IViewPart view) {
	}

}
