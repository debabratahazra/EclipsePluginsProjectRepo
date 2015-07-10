package com.cdt.keil.debug.ui.breakpoints;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.debug.core.IBreakpointManagerListener;
import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.debug.core.model.LineBreakpoint;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.PlatformUI;

import com.cdt.keil.debug.ui.console.ConsoleDisplayMgr;
import com.cdt.keil.debug.ui.debug.DebugAction;
import com.cdt.keil.debug.ui.debug.DownloadImageAction;
import com.cdt.keil.debug.ui.editor.info.EditorAddressInfo;
import com.cdt.keil.debug.ui.editor.info.HighlevelAddressInfo;
import com.cdt.keil.debug.ui.serialPortComm.IdebugInterface;


public class SDKLineBreakpoint extends LineBreakpoint implements IBreakpointManagerListener {
	
	public static final String SDK_BREAKPOINT_MARKER = "com.cdt.keil.debug.ui.markerType.lineBreakpoint";	
	protected static final String CONDITION= "com.cdt.keil.debug.ui.condition";
	protected static final String CONDITION_ENABLED= "com.cdt.keil.debug.ui.conditionEnabled";	
	protected static final String CONDITION_SUSPEND= "com.cdt.keil.debug.ui.conditionSuspend";
	private static final int MAX_BRK_POINT = 99;
	
	public static int breakpointCount;
		
	static{
		breakpointCount=0;		
	}

	public SDKLineBreakpoint() throws CoreException{
	}	

	public SDKLineBreakpoint(final IResource resource, final int lineNumber)
			throws CoreException {
		IWorkspaceRunnable runnable = new IWorkspaceRunnable() {

			public void run(IProgressMonitor monitor) throws CoreException {
				
				if(breakpointCount>MAX_BRK_POINT)
				{
					MessageDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().
							getShell(),"Error !!!","BreakPoint Limit Exceed.\n"+
							"Maximum 99 Breakpoints Allowed.");					
				}	
				else
				{
					
					//Adding Breakpoint to the target board + editor.
					switch(DebugAction.debugMode){
					
					case 1:
						//AL Mode
						
						short[] address=new EditorAddressInfo().addressInfo(lineNumber);
						int[] pack=IdebugInterface.addBreakpoint(address,true);
						if(pack==null) return;
						
						IMarker marker = resource.createMarker(SDK_BREAKPOINT_MARKER);						
						setMarker(marker);			
						marker.setAttribute(IBreakpoint.ENABLED, Boolean.TRUE);
						marker.setAttribute(IMarker.LINE_NUMBER, lineNumber	);						
						breakpointCount=SDKBreakpointAction.existingMarkers.length + 1;								
						marker.setAttribute(IBreakpoint.ID, getModelIdentifier());				
						marker.setAttribute(IMarker.MESSAGE, "" + resource.getName() +":" +	lineNumber);		
						break;
						
					case 2:
						//HL Mode
						
						address=new HighlevelAddressInfo().addressInfo(lineNumber);
						pack=IdebugInterface.addBreakpointHL(address,true, HighlevelAddressInfo.lineInfo());
						if(pack==null) return;
						
						marker = resource.createMarker(SDK_BREAKPOINT_MARKER);						
						setMarker(marker);			
						marker.setAttribute(IBreakpoint.ENABLED, Boolean.TRUE);
						marker.setAttribute(IMarker.LINE_NUMBER,								
								HighlevelAddressInfo.lineInfo());		//Set the Breakpoint Marker.													
						breakpointCount=SDKBreakpointAction.existingMarkers.length + 1;								
						marker.setAttribute(IBreakpoint.ID, getModelIdentifier());				
						marker.setAttribute(IMarker.MESSAGE, "" + resource.getName() +":" +								
								HighlevelAddressInfo.lineInfo());		
						break;
						
					case 3:
						//ML Mode
						
						//Add Breakpoint on target + editor.						
						address= new EditorAddressInfo().addressInfoML(lineNumber,resource.getName());  //@param line, file name only.
						if(address==null) {
							ConsoleDisplayMgr.getDefault().println("ERROR! HEX ADDRESS NOT FOUND.", 2);
							return;
						}
						pack = IdebugInterface.addBreakpoint(address,true);
						if(pack==null) return;
						
						marker = resource.createMarker(SDK_BREAKPOINT_MARKER);						
						setMarker(marker);			
						marker.setAttribute(IBreakpoint.ENABLED, Boolean.TRUE);
						marker.setAttribute(IMarker.LINE_NUMBER, lineNumber);		//Set the Breakpoint Marker.												
						breakpointCount=SDKBreakpointAction.existingMarkers.length + 1;								
						marker.setAttribute(IBreakpoint.ID, getModelIdentifier());				
						marker.setAttribute(IMarker.MESSAGE, "" + resource.getName() +":" +	lineNumber);						
						break;
						
					default:
						new DownloadImageAction();
						ConsoleDisplayMgr.getDefault().clear();
						ConsoleDisplayMgr.getDefault().println("PLEASE SELECT THE MODE OF DEBUGGING OPTION", 2);
						//return;
						break;		
					}												
				}
			}						
		};
		run(getMarkerRule(resource), runnable);
	}
	
	public String getModelIdentifier() {
		return ISDKDebugModelConstants.ID_KEIL_DEBUG_MODEL;
	}	
	
	@Override
	public void breakpointManagerEnablementChanged(boolean enabled) {				
	}
}

