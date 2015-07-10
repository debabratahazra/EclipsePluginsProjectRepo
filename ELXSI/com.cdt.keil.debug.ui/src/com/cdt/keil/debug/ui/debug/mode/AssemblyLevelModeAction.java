package com.cdt.keil.debug.ui.debug.mode;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.console.ConsolePlugin;

import com.cdt.keil.debug.ui.breakpoints.SDKRemoveAllBreakpointsAction;
import com.cdt.keil.debug.ui.console.ConsoleDisplayMgr;
import com.cdt.keil.debug.ui.debug.DebugAction;
import com.cdt.keil.debug.ui.debug.DownloadImageAction;
import com.cdt.keil.debug.ui.debug.StepReturnAction;
import com.cdt.keil.debug.ui.editor.info.EditorLineInfo;
import com.cdt.keil.debug.ui.editor.info.HighlightAssemblyMode;
import com.cdt.keil.debug.ui.internal.BreakpointMap;
import com.cdt.keil.debug.ui.internal.DynamicFileLocationAL;
import com.cdt.keil.debug.ui.internal.FileInfoName;
import com.cdt.keil.debug.ui.launch.run.FileOpenOperation;
import com.cdt.keil.debug.ui.memory.views.RegisterView;
import com.cdt.keil.debug.ui.serialPortComm.IdebugInterface;

public class AssemblyLevelModeAction implements IWorkbenchWindowActionDelegate {
	
	public static IAction aAction;

	@Override
	public void dispose() {}

	@Override
	public void init(IWorkbenchWindow window) {		
	}

	@Override
	public void run(IAction action) {		
		aAction = action;
		
		if(action.isChecked()){
			
			try{
				//Remove all Breakpoints.
				new SDKRemoveAllBreakpointsAction().run(action);
				IdebugInterface.removeVirtualBreakpoint((short)0);
			}catch(Exception e){}
			
			try{
				//Unchecked other Mode.
				HighLevelModeAction.hAction.setChecked(false);		
				MixedLevelModeAction.mAction.setChecked(false);
			}catch(Exception e){
				try{
					MixedLevelModeAction.mAction.setChecked(false);
				}catch(Exception ee){}
			}
		
			new DownloadImageAction();
			ConsoleDisplayMgr.getDefault().clear();
			
			try{
				int[] packet=IdebugInterface.resetInstruction();	
				if(packet==null) return;	//Board isn't connected properly.
			}catch(Exception e){}
			//ConsoleDisplayMgr.getDefault().print("target RESET", 1);
			
			try{
				ConsolePlugin.getDefault().getConsoleManager().showConsoleView(ConsoleDisplayMgr.console[0]);
			}catch(Exception e1){}
			

			try{
				FileInfoName.clearMap();
				BreakpointMap.clearMap();
			}catch(Exception e){}
			
			
			//Save & Close All Editor Tabs.
			try{			
				PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().saveAllEditors(false);
				PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().closeAllEditors(false);
			}catch(Exception e){}
			
			
			try{
				int valid = new FileOpenOperation().openLibDsmFile();		//Open & activate the LIBRARY.Dsm File.
				if(valid==0) return;
				
				//Open All .DSM file in editor.
				String[] filename = new FileInfoName().getAllFileName();
				new FileOpenOperation().openAllDsmFile(filename);	
						
				ConsoleDisplayMgr.getDefault().println("SWITCH TO ASSEMBLY LEVEL DEBUGGING MODE", 1);
			}	
			catch(Exception e){
				ConsoleDisplayMgr.getDefault().println("PROJECT NOT SELECTED", 3);
				action.setChecked(false);
				return;
			}	
			
			ConsoleDisplayMgr.getDefault().println("ALL BREAKPOINTS REMOVED", 1);
			
			DebugAction.debugMode=1;
			
			//Update Register View.
			RegisterView.updateRegister(true, false);	
			
			StepReturnAction.spRecentValue="0x07";
			EditorLineInfo.lineNo=0;
			DynamicFileLocationAL.absoluteFilePath="";
			DynamicFileLocationAL.relativeFilePath="";
			EditorLineInfo.text="";
			RegisterView.spChanged=false;
			new HighlightAssemblyMode().highlightLine("0000");   	
			
			IdebugInterface.terminateProcess();
		}
		else{
			//Mode toggle switch is unchecked.
			DebugAction.debugMode=0;
		}
	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {		
	}

	
}
