package com.cdt.keil.debug.ui.debug;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

import com.cdt.keil.debug.ui.console.ConsoleDisplayMgr;
import com.cdt.keil.debug.ui.editor.info.EditorLineInfo;
import com.cdt.keil.debug.ui.editor.info.HighlightAssemblyMode;
import com.cdt.keil.debug.ui.editor.info.HighlightHighlevelMode;
import com.cdt.keil.debug.ui.editor.info.HighlightMixedMode;
import com.cdt.keil.debug.ui.internal.DynamicFileLocationAL;
import com.cdt.keil.debug.ui.internal.DynamicFileLocationML;
import com.cdt.keil.debug.ui.memory.views.RegisterView;
import com.cdt.keil.debug.ui.serialPortComm.IdebugInterface;
import com.cdt.keil.debug.ui.simulator.sdk.SimulatorSDK;

public class ResetAction implements IWorkbenchWindowActionDelegate {
	
	static SimulatorSDK SDK=new SimulatorSDK();

	@Override
	public void dispose() {}

	@Override
	public void init(IWorkbenchWindow window) {}

	@Override
	public void run(IAction action) {
		
		switch(DebugAction.debugMode){
		
		case 1:
			//Assembly Mode
			assemblyReset();
			break;
			
		case 2:
			//High level Mode
			highlevelReset();
			break;
			
		case 3:
			//Mixed Mode
			mixedlevelReset();
			break;
			
		default:
			new DownloadImageAction();
			ConsoleDisplayMgr.getDefault().clear();
			ConsoleDisplayMgr.getDefault().println("PLEASE SELECT THE MODE OF DEBUGGING OPTION", 2);
			break;		
		}		
	}
	

	private void mixedlevelReset() {
		
		int[] val=IdebugInterface.stopInstruction(false);
		if(val==null) return;
		IdebugInterface.resetInstruction();
		
		DebugAction.debugMode=3;			//Define Mode of Debug
		RegisterView.updateRegister(true, false);
		EditorLineInfo.lineNo=0;
		RegisterView.spChanged=false;
		StepReturnAction.spRecentValue="0x07";
		DynamicFileLocationML.absoluteFilePath="";
		DynamicFileLocationML.relativeFilePath="";
		EditorLineInfo.text="";
		new HighlightMixedMode().highlightLine("0000");
		
	}

	private void highlevelReset() {
		
		//Target reset.
		int[] val=IdebugInterface.resetInstruction();
		if(val==null) return;
		
		DebugAction.debugMode=2;			//Define Mode of Debug
		
		//Update Register View.
		RegisterView.updateRegister(true, false);
		StepIntoAction.firstStepInto=true;
		
		//Highlight main()
		new HighlightHighlevelMode().highlightLine("main");
		
	}

	private void assemblyReset() {

		int[] val=IdebugInterface.stopInstruction(false);
		if(val==null) return;
		IdebugInterface.resetInstruction();
		
		DebugAction.debugMode=1;			//Define Mode of Debug
		RegisterView.updateRegister(true, false);
		EditorLineInfo.lineNo=0;
		RegisterView.spChanged=false;
		StepReturnAction.spRecentValue="0x07";
		DynamicFileLocationAL.absoluteFilePath="";
		DynamicFileLocationAL.relativeFilePath="";
		EditorLineInfo.text="";
		new HighlightAssemblyMode().highlightLine("0000");
		
	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {}

}
