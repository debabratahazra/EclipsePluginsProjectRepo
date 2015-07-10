package com.cdt.keil.debug.ui.breakpoints;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.IBreakpointManager;
import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.text.source.IAnnotationModel;
import org.eclipse.jface.text.source.IVerticalRulerInfo;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IActionDelegate;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.dialogs.PropertyDialogAction;
import org.eclipse.ui.texteditor.AbstractMarkerAnnotationModel;
import org.eclipse.ui.texteditor.IDocumentProvider;
import org.eclipse.ui.texteditor.ITextEditor;
import org.eclipse.ui.texteditor.IUpdate;

import com.cdt.keil.debug.ui.breakpoints.SDKLineBreakpoint;

public class SDKBreakpointPropertiesAction extends Action implements IUpdate {
	
	private IBreakpoint fBreakpoint;
	private IVerticalRulerInfo ruler;
	private ITextEditor textEditor;
	private String menuLabel;

	public SDKBreakpointPropertiesAction(ITextEditor editor, IVerticalRulerInfo r){
		textEditor = editor;
		ruler = r;
		menuLabel = "Breakpoint Properties"; 
	}
	
	/**
	 * @see IActionDelegate#selectionChanged(IAction, ISelection)
	 */
	public void selectionChanged(IAction action, ISelection selection) {
		if (selection instanceof IStructuredSelection) {
			IStructuredSelection ss= (IStructuredSelection)selection;
			if (ss.isEmpty() || ss.size() > 1) {
				return;
			}
			Object element= ss.getFirstElement();
			if (element instanceof IBreakpoint) {
				setBreakpoint((IBreakpoint)element);
			}
		}
	}
	
	protected IBreakpoint getBreakpoint() {
		try {
			int rulerLine = getVerticalRulerInfo().getLineOfLastMouseButtonActivity();
			int lineNumber = rulerLine + 1;
			if (lineNumber < 0)
				return null;
			
			fBreakpoint = this.getBreakPointAtLine(lineNumber);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fBreakpoint;
	}

	public void setBreakpoint(IBreakpoint breakpoint) {
		fBreakpoint = breakpoint;
	}
	
	@SuppressWarnings("deprecation")
	public void run(){

		PropertyDialogAction propertyAction= 
			new PropertyDialogAction(new Shell(Display.getCurrent()), new ISelectionProvider() {
				public void addSelectionChangedListener(ISelectionChangedListener listener) {
				}
				public ISelection getSelection() {
					return new StructuredSelection(getBreakpoint());
				}
				public void removeSelectionChangedListener(ISelectionChangedListener listener) {
				}
				public void setSelection(ISelection selection) {
				}
			});
		propertyAction.run();
	}
	
	/**
	 * @return this action's vertical ruler
	 */
	protected IVerticalRulerInfo getVerticalRulerInfo() {
		return ruler;
	}

	/** 
	 * @return the resource for which to create the marker or <code>null</code>
	 */
	protected IResource getResource() {
		IEditorInput input= textEditor.getEditorInput();
		IResource resource= (IResource) input.getAdapter(IFile.class);
		if (resource == null) {
			resource= (IResource) input.getAdapter(IResource.class);
		}		
		return resource;
	}

	/**
	 * @return the marker annotation model of the editor's input.
	 */
	protected AbstractMarkerAnnotationModel getAnnotationModel() {
		IDocumentProvider provider= textEditor.getDocumentProvider();
		IAnnotationModel model= provider.getAnnotationModel(textEditor.getEditorInput());
		if (model instanceof AbstractMarkerAnnotationModel) {
			return (AbstractMarkerAnnotationModel) model;
		}
		return null;
	}
	
	private IBreakpoint getBreakPointAtLine(int line) {
		
		IResource resource= getResource();
		AbstractMarkerAnnotationModel model= getAnnotationModel();
		
		if (model != null) {
			try {
				
				IMarker[] markers= null;
				if (resource instanceof IFile)
					markers= resource.findMarkers(SDKLineBreakpoint.SDK_BREAKPOINT_MARKER, true, IResource.DEPTH_INFINITE);
				else {
					IWorkspaceRoot root= ResourcesPlugin.getWorkspace().getRoot();
					markers= root.findMarkers(SDKLineBreakpoint.SDK_BREAKPOINT_MARKER, true, IResource.DEPTH_INFINITE);
				}
				
				if (markers != null) {
					IBreakpointManager breakpointManager= DebugPlugin.getDefault().getBreakpointManager();
					for (int i= 0; i < markers.length; i++) {
						IBreakpoint breakpoint = breakpointManager.getBreakpoint(markers[i]);
						if (breakpoint != null && breakpointManager.isRegistered(breakpoint)) {
							if(line == markers[i].getAttribute(
									IMarker.LINE_NUMBER, -1)) {
								return breakpoint;
							}
						}
					}
				}
			} catch (CoreException x) {}
			catch(Exception e){}
		}
		
		return null;
	}

	/**
	 * @see IUpdate#update()
	 */
	public void update() {
		if(!hasBreakpoints()) {
			setEnabled(false);
			setText(menuLabel);
		} else if(hasBreakpoints()){
			setEnabled((getBreakPointAtLine(
					ruler.getLineOfLastMouseButtonActivity() + 1) == null)
					? Boolean.FALSE : Boolean.TRUE);
			setText(menuLabel);
		} else {
			setEnabled(true);
			setText(menuLabel);
		}
	}
	
	private boolean hasBreakpoints() {
		IResource resource= getResource();
		AbstractMarkerAnnotationModel model= getAnnotationModel();
		if (model != null && resource != null) {
			IMarker[] markers= null;
			try {
				if (resource instanceof IFile)
					markers= resource.findMarkers(SDKLineBreakpoint.SDK_BREAKPOINT_MARKER, true, IResource.DEPTH_INFINITE);
				else {
					IWorkspaceRoot root= ResourcesPlugin.getWorkspace().getRoot();
					markers= root.findMarkers(SDKLineBreakpoint.SDK_BREAKPOINT_MARKER, true, IResource.DEPTH_INFINITE);
				}
			} catch(Exception e) {
				e.printStackTrace();
			}
			if(markers != null && markers.length > 0) {
				return true;
			}
		}
		return false;
	}	
}
