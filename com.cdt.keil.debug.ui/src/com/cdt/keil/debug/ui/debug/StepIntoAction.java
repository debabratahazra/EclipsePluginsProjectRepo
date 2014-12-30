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
import com.cdt.keil.debug.ui.internal.HighLevelStructure;
import com.cdt.keil.debug.ui.internal.LineInfoContentDescriptor;
import com.cdt.keil.debug.ui.internal.UpdatePC;
import com.cdt.keil.debug.ui.memory.views.RegisterView;
import com.cdt.keil.debug.ui.serialPortComm.IdebugInterface;
import com.cdt.keil.debug.ui.simulator.sdk.SimulatorSDK;

public class StepIntoAction implements IWorkbenchWindowActionDelegate {
	
	public static boolean firstStepInto=true;
	public static int[] stackValue;
	public static String spRecentValue = new String("0x07");
	
	SimulatorSDK SDK=new SimulatorSDK();
			
	@Override
	public void dispose() {}

	@Override
	public void init(IWorkbenchWindow window) {		
	}

	@Override
	public void run(IAction action) {
		
		switch(DebugAction.debugMode){
		
		case 1:
			//Assembly Mode
			assemblySetpInto();
			break;
			
		case 2:
			//High level Mode
			highlevelStepInto();
			break;
			
		case 3:
			//Mixed Mode
			mixedlevelStepInto();
			break;
			
		default:
			new DownloadImageAction();
			ConsoleDisplayMgr.getDefault().clear();
			ConsoleDisplayMgr.getDefault().println("PLEASE SELECT THE MODE OF DEBUGGING OPTION", 2);
			break;		
		}
		
	}
	
	
	
	
	private void assemblySetpInto() {
		
		if(!EditorLineInfo.text.contains("$ END") && !EditorLineInfo.text.contains("$ End")){		
			
			int[] val=IdebugInterface.stepInto();
			if(val==null) return;
			
			RegisterView.updateRegister(false, true);			
			new UpdatePC();			
			new HighlightAssemblyMode().highlightLine(RegisterView.regAddressValue[0].getText(1).substring(2, 6));					
		}
		
		//For Step Return Action
		if(RegisterView.spChanged){
			StepReturnAction.spRecentValue=RegisterView.regAddressValue[3].getText(1);
			String str =StepReturnAction.spRecentValue;
			int sAddress = Short.parseShort(str.substring(2, str.length()), 16) - 1;
			short[] Packet = new short[3];
			Packet[0]=0;
			Packet[1]= (short) sAddress;
			Packet[2] = 0x02;
			StepReturnAction.stackValue = IdebugInterface.readScratchpadRAM(Packet);			
		}		
		IdebugInterface.terminateProcess();		
	}
	
	
	private void mixedlevelStepInto() {
		
		int[] val=IdebugInterface.stepInto();
		if(val==null) return;
		
		RegisterView.updateRegister(false, true);			
		new UpdatePC();	
		
		new HighlightMixedMode().highlightLine(RegisterView.regAddressValue[0].getText(1).substring(2, 6));
		
		
		//For Step Return Action
		if(RegisterView.spChanged){
			StepReturnAction.spRecentValue=RegisterView.regAddressValue[3].getText(1);
			String str =StepReturnAction.spRecentValue;
			int sAddress = Short.parseShort(str.substring(2, str.length()), 16) - 1;
			short[] Packet = new short[3];
			Packet[0]=0;
			Packet[1]= (short) sAddress;
			Packet[2] = 0x02;
			StepReturnAction.stackValue = IdebugInterface.readScratchpadRAM(Packet);			
		}	
		
	}

	private void highlevelStepInto() {	
		//High level Step into.
		
		if(firstStepInto){		
			String nextSAddress = new LineInfoContentDescriptor().getNextSAddress
				(HighlightHighlevelMode.currentFile, HighlightHighlevelMode.lastLine);						
			if(nextSAddress==null) return;
			
			short[] Packet = packetValue(nextSAddress,(short)100);			
			int[] val=IdebugInterface.addBreakpoint(Packet, false);
			if(val==null) return;
			val=IdebugInterface.runInstruction2();
			if(val==null) return;
			val=IdebugInterface.removeVirtualBreakpoint((short)100);			
			if(val==null) return;
			RegisterView.updateRegister(false, true);			
			new UpdatePC();
			firstStepInto=false;
			ConsoleDisplayMgr.getDefault().println("STEP-INTO INSTRUCTION EXECUTED.", 1);
		}
		else{
			if(HighlightHighlevelMode.currentHighlightText!=null){
				//Return Last Address of current highlighting HLCode.
				String lAddress = getLastAddress(HighlightHighlevelMode.currentFile,
						HighlightHighlevelMode.currentHighlightText);
				String sNextAddress = getNextFirstAddress(HighlightHighlevelMode.currentFile,
						HighlightHighlevelMode.currentHighlightText);
				if(lAddress==null) return;
												
				if(lAddress.equalsIgnoreCase("")){
					//Single line LL Instruction.
					int[] val=IdebugInterface.virtualStepInto();				     	if(val==null) return;
					
					new UpdatePC();
					RegisterView.updateRegister(false, true);
					
				}else{
					//Multiple line LL instruction.
					short[] Packet = packetValue(lAddress,(short)99);					
					int[] val=IdebugInterface.addBreakpoint(Packet, false);		if(val==null) return;
					Packet = packetValue(sNextAddress,(short)100);
					val=IdebugInterface.addBreakpoint(Packet, false);			if(val==null) return;
					val=IdebugInterface.runInstruction2();						if(val==null) return;
					val=IdebugInterface.removeVirtualBreakpoint((short)100);	if(val==null) return;
					val=IdebugInterface.removeVirtualBreakpoint((short)99);		if(val==null) return;
					
					new UpdatePC();					
					if(RegisterView.regAddressValue[0].getText(1).equalsIgnoreCase(lAddress)){						
						val=IdebugInterface.virtualStepInto();							if(val==null) return;	
												
						
						new UpdatePC();
						boolean jump = boolJumpHLCode(RegisterView.regAddressValue[0].getText(1));
						
						if(jump){
							//Jump to immediate next HL Code.
							RegisterView.updateRegSP();
							if(RegisterView.spChanged){
								
								spRecentValue=RegisterView.regAddressValue[3].getText(1);			
								int sAddress = Short.parseShort(spRecentValue.substring(2, spRecentValue.length()), 16)-1 ;
								Packet = new short[3];
								Packet[0]=0;
								Packet[1]= (short) sAddress;
								Packet[2] = 0x02;
								stackValue = IdebugInterface.readScratchpadRAM(Packet);		
								
								Packet = new short[3];
								Packet[0]=100;		//for temporary breakpoint.
								Packet[1] = (short)stackValue[6];
								Packet[2] = (short)(stackValue[5]+1);
								IdebugInterface.addBreakpoint(Packet,false);
								val=IdebugInterface.runInstruction2();						if(val==null) return;
								val=IdebugInterface.removeVirtualBreakpoint((short)100);	if(val==null) return;
								
								RegisterView.updateRegister(false, true);
								new UpdatePC();
							}
						}else{
							RegisterView.updateRegister(false, true);
						}
					}else{
						RegisterView.updateRegister(false, true);
					}								
				}						
			}	
			ConsoleDisplayMgr.getDefault().println("STEP-INTO INSTRUCTION EXECUTED.", 1);
		}	
		//Highlight next instruction (HL Code).
		new HighlightHighlevelMode().highlightLine(
				RegisterView.regAddressValue[0].getText(1));		
	}
	

	private boolean boolJumpHLCode(String currentPC) {
		// Return TRUE if needs jump to immediate next HL Code.  
		// FALSE if needs jump to other HL Code.
		
		HighLevelStructure hls = new HighLevelStructure();
		for(int i=0;i<HighLevelStructure.totalLineCount;i++){
			if(hls.getSAddress(i).trim().equalsIgnoreCase(currentPC.trim())){
				return false;
			}
		}		
		return true;
	}

	private String getLastAddress(String currentFile,
			String currentHighlightText) {		
		//Return last address of current highlight HL Code.
		
		HighLevelStructure hls = new HighLevelStructure();
		for(int i=0;i<HighLevelStructure.totalLineCount;i++){
			
			//Filename and HL Code are same.
			if(hls.getFilename(i).trim().equalsIgnoreCase(currentFile.trim()) && 
					hls.getHighLevelCode(i).trim().equalsIgnoreCase(currentHighlightText.trim())){
				
				//Current line's last address
				if(hls.getLineNumber(i)==HighlightHighlevelMode.lastLine){
					
					if(hls.getSAddress(i).trim().equalsIgnoreCase(hls.getLAddress(i).trim())){
						//For only one line instruction.
						return "";
					}else{
						//For multiple line instruction.
						return (hls.getLAddress(i));
					}	
				}							
			}
		}
		
		return null;
	}
	
	private String getNextFirstAddress(String currentFile,
			String currentHighlightText) {
		//Return next starting address of same Source file.
		
		HighLevelStructure hls = new HighLevelStructure();
		for(int i=0;i<HighLevelStructure.totalLineCount-1;i++){
			if(hls.getFilename(i+1).trim().equalsIgnoreCase(currentFile.trim()) && 
					hls.getHighLevelCode(i).trim().equalsIgnoreCase(currentHighlightText.trim())){					
						if((i+1)<HighLevelStructure.totalLineCount)
							return (hls.getSAddress(i+1));								
			}
		}
		
		return null;
	}


	private short[] packetValue(String nextSAddress, short ID) {
		//Return short[]  Packet for adding Breakpoint.
		
		short[] Packet = new short[3];
		Packet[0]=ID;
		String str = nextSAddress.substring(2, 4);
		Packet[1] = Short.parseShort(str, 16);
		str = nextSAddress.substring(4, nextSAddress.length());
		Packet[2] = Short.parseShort(str, 16);
		Packet[2]++;
		if(Packet[2]>255){
			Packet[2]=0;	Packet[1]++;
		}
		return Packet;
	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {}

}
