package com.cdt.keil.debug.ui.debug.mode;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.PlatformUI;

import com.cdt.keil.debug.ui.breakpoints.SDKRemoveAllBreakpointsAction;
import com.cdt.keil.debug.ui.console.ConsoleDisplayMgr;
import com.cdt.keil.debug.ui.debug.DebugAction;
import com.cdt.keil.debug.ui.debug.DownloadImageAction;
import com.cdt.keil.debug.ui.editor.info.HighlightMixedMode;
import com.cdt.keil.debug.ui.internal.BreakpointMap;
import com.cdt.keil.debug.ui.internal.FileInfoName;
import com.cdt.keil.debug.ui.launch.run.FileOpenOperation;
import com.cdt.keil.debug.ui.serialPortComm.IdebugInterface;

public class MixedLevelModeAction implements IWorkbenchWindowActionDelegate {
	
	public static IAction mAction;

	@Override
	public void dispose() {}

	@Override
	public void init(IWorkbenchWindow window) {}

	@Override
	public void run(IAction action) {		
		mAction = action;
		
		if(action.isChecked()){
			
			try{
				//Remove all Breakpoints.
				new SDKRemoveAllBreakpointsAction().run(action);
				IdebugInterface.removeVirtualBreakpoint((short)0);
			}catch(Exception e){}
			
			try{				
				BreakpointMap.clearMap();
			}catch(Exception e){}
			
			try{
				//Unchecked other mode
				AssemblyLevelModeAction.aAction.setChecked(false);				
				HighLevelModeAction.hAction.setChecked(false);
			}catch(Exception e){
				try{
					HighLevelModeAction.hAction.setChecked(false);
				}catch(Exception ee){}
			}		
			
			//Target reset.
			int[] val=IdebugInterface.resetInstruction();
			if(val==null) return;
				
			
			//Save & Close All Editor Tabs.
			try{			
				PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().saveAllEditors(false);
				PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().closeAllEditors(false);
			}catch(Exception e){}
			
			try{
				//Open & activate the LIBRARY.DSM File.
				int valid = new FileOpenOperation().openLibDsmFile();		
				if(valid==0) return;
				
				//Open All .MXM file in editor.
				String[] filename = new FileInfoName().getAllFileName();
				new FileOpenOperation().openAllMxmFile(filename);
				
				new DownloadImageAction();
				ConsoleDisplayMgr.getDefault().clear();
				ConsoleDisplayMgr.getDefault().println("SWITCH TO MIXED LEVEL DEBUGGING MODE", 1);
				
			}catch(Exception e){
				ConsoleDisplayMgr.getDefault().println("PROJECT NOT SELECTED", 3);
				action.setChecked(false);
				return;
			}
			
			ConsoleDisplayMgr.getDefault().println("ALL BREAKPOINTS REMOVED", 1);
			
			new HighlightMixedMode().highlightLine("0000");
			
			DebugAction.debugMode=3;
			
		}else{
			DebugAction.debugMode=0;
		}		
	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {}

}
