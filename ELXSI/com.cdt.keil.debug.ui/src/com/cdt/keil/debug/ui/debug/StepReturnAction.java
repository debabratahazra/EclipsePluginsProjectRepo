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
import com.cdt.keil.debug.ui.internal.UpdatePC;
import com.cdt.keil.debug.ui.memory.views.RegisterView;
import com.cdt.keil.debug.ui.serialPortComm.IdebugInterface;
import com.cdt.keil.debug.ui.simulator.sdk.SimulatorSDK;

public class StepReturnAction implements IWorkbenchWindowActionDelegate {
	
	public static String spRecentValue = new String("0x07");
	public static int[] stackValue;
	
	HighlightAssemblyMode highlight;
	SimulatorSDK SDK=new SimulatorSDK();

	@Override
	public void dispose() {		
	}

	@Override
	public void init(IWorkbenchWindow window) {
		highlight=new HighlightAssemblyMode();
	}

	@Override
	public void run(IAction action) {
		
		switch(DebugAction.debugMode){
		
		case 1:
			//Assembly Mode
			assemblySetpReturn();
			break;
			
		case 2:
			//High level Mode
			highlevelStepReturn();
			break;
			
		case 3:
			//Mixed Mode
			mixedlevelStepReturn();
			break;
			
		default:
			new DownloadImageAction();
			ConsoleDisplayMgr.getDefault().clear();
			ConsoleDisplayMgr.getDefault().println("PLEASE SELECT THE MODE OF DEBUGGING OPTION", 2);
			break;		
		}	
}

	private void mixedlevelStepReturn() {
		

		short[] Packet = new short[3];
		Packet[0]=100;		//for temporary breakpoint.
		Packet[1] = (short)stackValue[6];
		Packet[2] = (short)(stackValue[5]+1);
		IdebugInterface.addBreakpoint(Packet,false);
		int[] value=IdebugInterface.runInstruction2();
		if(value==null){
			ConsoleDisplayMgr.getDefault().println("TARGET COMMUNICATION FAILED.", 2);
			return;
		}
		
		if(value[1]==148){
			ConsoleDisplayMgr.getDefault().println("TARGET BOARD IS RUNNING...", 3);
		}else{
			ConsoleDisplayMgr.getDefault().println("STEP RETURN EXECUTED.", 1);
		}
		
    	RegisterView.updateRegister(false, true);				
		new UpdatePC();		   
		
		new HighlightMixedMode().highlightLine(RegisterView.regAddressValue[0].getText(1).substring(2, 6));
					
    	IdebugInterface.removeBreakpoint(new short[] {100}, false);	    		    	
		IdebugInterface.terminateProcess();	    	
	
	//Update stackValue for Step Return Action
	if(RegisterView.spChanged){
		spRecentValue=RegisterView.regAddressValue[3].getText(1);			
		int sAddress = Short.parseShort(spRecentValue.substring(2, spRecentValue.length()), 16) - 1;
		short[] Packet1 = new short[3];
		Packet1[0]=0;
		Packet1[1]= (short) sAddress;
		Packet1[2] = 0x02;
		stackValue = IdebugInterface.readScratchpadRAM(Packet1);		
		}
	}  

	private void highlevelStepReturn() {
		
		String SP_Value=RegisterView.regAddressValue[3].getText(1);			
		int sAddress = Short.parseShort(SP_Value.substring(2, SP_Value.length()), 16)-1 ;
		short[] Packet = new short[3];
		Packet[0]=0;
		Packet[1]= (short) sAddress;
		Packet[2] = 0x02;
		stackValue = IdebugInterface.readScratchpadRAM(Packet);		
		
		Packet = new short[3];
		Packet[0]=100;		//for temporary breakpoint.
		Packet[1] = (short)stackValue[6];
		Packet[2] = (short)(stackValue[5]+1);
		IdebugInterface.addBreakpoint(Packet,false);
		int[] val=IdebugInterface.runInstruction2();						if(val==null) return;
		val=IdebugInterface.removeVirtualBreakpoint((short)100);			if(val==null) return;
		
		StepIntoAction.firstStepInto=false;
		
		RegisterView.updateRegister(false, true);
		new UpdatePC();
		
		//Highlight next instruction (HL Code).
		new HighlightHighlevelMode().highlightLine(
				RegisterView.regAddressValue[0].getText(1));
	
		ConsoleDisplayMgr.getDefault().println("STEP-RETRUN INSTRUCTION EXECUTED.", 1);
	}

	private void assemblySetpReturn() {
		
		//for RET option only.
		if((spRecentValue.compareToIgnoreCase("0x07")!=0)&&
				(!EditorLineInfo.text.contains("$ END") && !EditorLineInfo.text.contains("$ End"))){
			short[] Packet = new short[3];
			Packet[0]=100;		//for temporary breakpoint.
			Packet[1] = (short)stackValue[6];
			Packet[2] = (short)(stackValue[5]+1);
			IdebugInterface.addBreakpoint(Packet,false);
			int[] value=IdebugInterface.runInstruction2();
			if(value==null){
				ConsoleDisplayMgr.getDefault().println("TARGET COMMUNICATION FAILED.", 2);
				return;
			}
			
			if(value[1]==148){
				ConsoleDisplayMgr.getDefault().println("TARGET BOARD IS RUNNING...", 3);
			}else{
				ConsoleDisplayMgr.getDefault().println("STEP RETURN EXECUTED.", 1);
			}
			
	    	RegisterView.updateRegister(false, true);				
			new UpdatePC();		   
			
			highlight.highlightLine(RegisterView.regAddressValue[0].getText(1).substring(2, 6));
						
	    	IdebugInterface.removeBreakpoint(new short[] {100}, false);	    		    	
			IdebugInterface.terminateProcess();	    	
		}
		//Update stackValue for Step Return Action
		if(RegisterView.spChanged){
			spRecentValue=RegisterView.regAddressValue[3].getText(1);			
			int sAddress = Short.parseShort(spRecentValue.substring(2, spRecentValue.length()), 16) - 1;
			short[] Packet = new short[3];
			Packet[0]=0;
			Packet[1]= (short) sAddress;
			Packet[2] = 0x02;
			stackValue = IdebugInterface.readScratchpadRAM(Packet);			
		}
		
	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {		
	}

}
