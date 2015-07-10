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
import com.cdt.keil.debug.ui.editor.info.HighlightHighlevelMode;
import com.cdt.keil.debug.ui.internal.HighLevelStructureDefine;
import com.cdt.keil.debug.ui.internal.SourceFileContentProvider;
import com.cdt.keil.debug.ui.launch.run.FileOpenOperation;
import com.cdt.keil.debug.ui.memory.views.RegisterView;
import com.cdt.keil.debug.ui.serialPortComm.IdebugInterface;

public class HighLevelModeAction implements IWorkbenchWindowActionDelegate {
	
	public static IAction hAction;

	@Override
	public void dispose() {}

	@Override
	public void init(IWorkbenchWindow window) {}

	@Override
	public void run(IAction action) {		
		hAction = action;
		
		if(action.isChecked()){
			
			try{
				//Remove all Breakpoints.
				new SDKRemoveAllBreakpointsAction().run(action);
				IdebugInterface.removeVirtualBreakpoint((short)0);
			}catch(Exception e){}
			
			
			try{
				//Unchecked other mode
				AssemblyLevelModeAction.aAction.setChecked(false);
				MixedLevelModeAction.mAction.setChecked(false);
			}catch(Exception e){
				try{
					MixedLevelModeAction.mAction.setChecked(false);
				}catch(Exception ee){}
			}
			
			//Target reset.
			int[] val=IdebugInterface.resetInstruction();
			if(val==null) return;
			//ConsoleDisplayMgr.getDefault().print("target RESET", 1);			
			
			//Save & Close All Editor Tabs.
			try{			
				PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().saveAllEditors(false);
				PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().closeAllEditors(false);
			}catch(Exception e){}		
		
			try{
				String[] srcFilename=SourceFileContentProvider.sourceFilePath();	//Return Absolute Source File path.
				if(srcFilename==null) return;
				new FileOpenOperation().openAllSouceFile(srcFilename);
				
				new DownloadImageAction();
				ConsoleDisplayMgr.getDefault().clear();
				ConsoleDisplayMgr.getDefault().println("SWITCH TO HIGH LEVEL DEBUGGING MODE", 1);
				
			}catch(Exception e){
				ConsoleDisplayMgr.getDefault().println("PROJECT NOT SELECTED", 3);
				action.setChecked(false);
				return;
			}		
			ConsoleDisplayMgr.getDefault().println("ALL BREAKPOINTS REMOVED", 1);
			
			new HighLevelStructureDefine();		//Generate DS for HL Debug.
			DebugAction.debugMode=2;			//Define Mode of Debug
						
			//Update Register View.
			RegisterView.updateRegister(true, false);		
			
			//Highlight main()
			new HighlightHighlevelMode().highlightLine("main");
		}else{
			DebugAction.debugMode=0;
		}
	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {}

}
