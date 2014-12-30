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
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.source.IAnnotationModel;
import org.eclipse.jface.text.source.IVerticalRulerInfo;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.texteditor.AbstractMarkerAnnotationModel;
import org.eclipse.ui.texteditor.IDocumentProvider;
import org.eclipse.ui.texteditor.ITextEditor;
import org.eclipse.ui.texteditor.IUpdate;

import com.cdt.keil.debug.ui.breakpoints.SDKLineBreakpoint;
import com.cdt.keil.debug.ui.console.ConsoleDisplayMgr;
import com.cdt.keil.debug.ui.debug.DebugAction;
import com.cdt.keil.debug.ui.editor.info.EditorAddressInfo;
import com.cdt.keil.debug.ui.editor.info.HighlevelAddressInfo;
import com.cdt.keil.debug.ui.serialPortComm.IdebugInterface;

public class SDKEnableBreakpointAction extends Action implements IUpdate {

	private IVerticalRulerInfo ruler;
	private ITextEditor textEditor;
	
	private String enableLabel;
	private String disableLabel;
	
	
	public SDKEnableBreakpointAction(ITextEditor editor, IVerticalRulerInfo r) {
		ruler = r;
		textEditor = editor;
		enableLabel = "Enable  Breakpoint"; 
		disableLabel = "Disable Breakpoint";
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
	 * @return this action's vertical ruler
	 */
	protected IVerticalRulerInfo getVerticalRulerInfo() {
		return ruler;
	}
	
	/**
	 * @return this action's editor
	 */
	protected ITextEditor getTextEditor() {
		return textEditor;
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
	
	/**
	 * @return the document of the editor's input
	 */
	protected IDocument getDocument() {
		IDocumentProvider provider= textEditor.getDocumentProvider();
		return provider.getDocument(textEditor.getEditorInput());
	}
	
	/**
	 * @see IUpdate#update()
	 */
	public void update() {
		if(!hasBreakpoints()) {
			setEnabled(false);
			setText(enableLabel);
		} else if(isBreakpointEnabled()) {
			setEnabled(true);
			setText(disableLabel);
		} else if(hasBreakpoints()){
			setEnabled((getBreakPointAtLine(
					ruler.getLineOfLastMouseButtonActivity() + 1) == null)
					? Boolean.FALSE : Boolean.TRUE);
			setText((getBreakPointAtLine(
					ruler.getLineOfLastMouseButtonActivity() + 1) == null)
					? disableLabel : enableLabel);
		} else {
			setEnabled(true);
			setText(enableLabel);
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
	
	private boolean isBreakpointEnabled() {
		int line= ruler.getLineOfLastMouseButtonActivity() + 1;
		
		IBreakpoint breakpoint =  getBreakPointAtLine(line);
		
		if(breakpoint == null) {
			return false;
		} else {
			try {
				if (!breakpoint.isEnabled() && breakpoint != null) {
					return false;
				}
			} catch (CoreException e) {}
			catch(Exception e){}
		}
		
		return true;
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
							
							
							//		IMarker.LINE_NUMBER, -1) );
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
	 * @see Action#run()
	 */
	public void run() {
		
		int line = ruler.getLineOfLastMouseButtonActivity() + 1;
		
		if (getText().equals(enableLabel)) {
			enableMarker(line);
		} else {
			disableMarkers(line);
		}
	}
	
	protected void enableMarker(int line) {
		//Breakpoint Enable at line.
		
		IBreakpoint breakpoint = getBreakPointAtLine(line);		
		try {
			//Breakpoint enabled on target + editor.
			if(DebugAction.debugMode==1){
				//AL Mode.
				
				//Add breakpoint again to the target board.
				short[] address=new EditorAddressInfo().addressInfo(line);
				int[] pack=IdebugInterface.addBreakpoint(address,false);
				if(pack==null)	return;
				
			}else if(DebugAction.debugMode==2){
				//HL Mode.
				
				//Add Breakpoint again on target + editor.
				short[] address=new HighlevelAddressInfo().addressInfo(line);
				int[] pack=IdebugInterface.addBreakpointHL(address,false,line);
				if(pack==null) 	return;
				
			}else if(DebugAction.debugMode==3){
				//ML Mode.
				//Add breakpoint again [Enable breakpoint] to the target board + editor.
				short[] address= new EditorAddressInfo().addressInfoML(line,textEditor.getTitle());
				if(address==null){
					ConsoleDisplayMgr.getDefault().println("ERROR! HEX ADDRESS NOT FOUND.", 2);
					return;
				}
				int[] pack=IdebugInterface.addBreakpoint(address,false);
				if(pack==null)	return;
				
			}else{
				//Debug Mode isn't selected.
				ConsoleDisplayMgr.getDefault().println("PLEASE SELECT THE MODE OF DEBUGGING OPTION", 2);
				return;						
			}
			
			breakpoint.setEnabled(true);
			IBreakpointManager breakpointManager = DebugPlugin.getDefault().getBreakpointManager();
			breakpointManager.fireBreakpointChanged(breakpoint);			
			
		} catch (CoreException e) {			
		}catch(Exception e){}
	}
	
	protected void disableMarkers(int line) {
		//Breakpoint Disable at line.
	
		IBreakpoint breakpoint = getBreakPointAtLine(line);
		try {
			
			if(DebugAction.debugMode==1){
				//AL Mode.
				
				//Remove breakpoint from the target board.
				short[] id=new EditorAddressInfo().idInfoAL(line);
				IdebugInterface.removeBreakpoint(id, false);
				
			}else if(DebugAction.debugMode==2){
				//HL Mode.
				short[] id = new HighlevelAddressInfo().idInfo(line);
				if(id==null) return;
				IdebugInterface.removeBreakpointHL(id, false,line);
				
			}else if(DebugAction.debugMode==3){
				//ML Mode.
				
				//Disable[/Remove] breakpoint from the target board.
				short[] id=new EditorAddressInfo().idInfoML(line,textEditor.getTitle());
				if(id==null) return;
				int[] pack = IdebugInterface.removeBreakpoint(id, false);
				if(pack==null) return;
				
			}else{
				//Debug Mode isn't selected.
				ConsoleDisplayMgr.getDefault().println("PLEASE SELECT THE MODE OF DEBUGGING OPTION", 2);
				return;						
			}
			
			breakpoint.setEnabled(false);
			IBreakpointManager breakpointManager = DebugPlugin.getDefault().getBreakpointManager();
			breakpointManager.fireBreakpointChanged(breakpoint);
			//DebugPlugin.getDefault().fireDebugEventSet(new DebugEvent[] { new DebugEvent(DebugEvent.BREAKPOINT) });
						
		} catch (CoreException e) {
		}catch(Exception e){}
	}
}