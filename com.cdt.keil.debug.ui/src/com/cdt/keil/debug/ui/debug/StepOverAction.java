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
import com.cdt.keil.debug.ui.launch.run.LibDsmFileLocation;
import com.cdt.keil.debug.ui.memory.views.RegisterView;
import com.cdt.keil.debug.ui.serialPortComm.IdebugInterface;
import com.cdt.keil.debug.ui.simulator.sdk.SimulatorSDK;

public class StepOverAction implements IWorkbenchWindowActionDelegate {
		
	SimulatorSDK SDK=new SimulatorSDK();
	LibDsmFileLocation dsm = null;
	String filePath = null;
	
	public static int[] stackValue;
	public static String spRecentValue = new String("0x07");

	@Override
	public void dispose() {}

	@Override
	public void init(IWorkbenchWindow window) {		
		dsm = new LibDsmFileLocation(true);
		filePath = dsm.dsmDetailFileLocation();
	}

	@Override
	public void run(IAction action) {
		
		switch(DebugAction.debugMode){
		
		case 1:
			//Assembly Mode
			assemblySetpOver();
			break;
			
		case 2:
			//High level Mode
			highlevelStepOver();
			break;
			
		case 3:
			//Mixed Mode
			mixedlevelStepOver();
			break;
			
		default:
			new DownloadImageAction();
			ConsoleDisplayMgr.getDefault().clear();
			ConsoleDisplayMgr.getDefault().println("PLEASE SELECT THE MODE OF DEBUGGING OPTION", 2);
			break;		
		}		
	}	

		
		

	private void highlevelStepOver() {	
		
		if(StepIntoAction.firstStepInto){		
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
			StepIntoAction.firstStepInto=false;
			
			ConsoleDisplayMgr.getDefault().println("STEP-OVER INSTRUCTION EXECUTED.", 1);
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
					RegisterView.updateRegister(false, true);
					new UpdatePC();
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
				}						
			}
			ConsoleDisplayMgr.getDefault().println("STEP-OVER INSTRUCTION EXECUTED.", 1);		
		}		
		
		//Highlight next instruction (HL Code).
		new HighlightHighlevelMode().highlightLine(
				RegisterView.regAddressValue[0].getText(1));		
}
	
	
	
	

	private void assemblySetpOver() {
		
		if(EditorLineInfo.text.contains("$ END") && EditorLineInfo.text.contains("$ End")){
			return;		//Program End.
		}
		
		if(!EditorLineInfo.text.contains("ACALL") && !EditorLineInfo.text.contains("acall") &&
				!EditorLineInfo.text.contains("LCALL") && !EditorLineInfo.text.contains("lcall")){
			stepIntoLowLevel();
		}
		else
			stepOverLowLevel();
		
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
	
	
	
	private void mixedlevelStepOver() {
		
		if(!EditorLineInfo.text.contains("ACALL") && !EditorLineInfo.text.contains("acall") &&
				!EditorLineInfo.text.contains("LCALL") && !EditorLineInfo.text.contains("lcall")){
			stepIntoMixedLevel();
		}
		else
			stepOverMixedLevel();
		
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
	
	

	private void stepOverMixedLevel() {		
		
		try{
		String currentPC = RegisterView.regAddressValue[0].getText(1);
		int index=currentPC.indexOf("x");
		if(index!= -1){
			String address = currentPC.substring(index+1, index+5);			    
			short[] Packet = new short[3];
			Packet[0]=100;  		// temporary breakpoint
			Packet[1] = Short.parseShort(address.substring(0, 2),16);
			Packet[2] = Short.parseShort(address.substring
						(2, address.length()),16);
			
			Packet[2] =(short)( Packet[2] + 4);
			if(Packet[2]>255 ){
					Packet[2]=(short)(Packet[2] - 256); 
					Packet[1]++; 
			}	
			    	
			int[] value=IdebugInterface.addBreakpoint(Packet,false);	if(value==null) return;
			value=IdebugInterface.runInstruction2();					if(value==null) return;
			    	
			if(value[1]==148){
				ConsoleDisplayMgr.getDefault().println("TARGET BOARD IS RUNNING...", 3);
			}
			    				    	
			IdebugInterface.removeBreakpoint(new short[]{100}, false);		    	
			ConsoleDisplayMgr.getDefault().println("STEP-OVER INSTRUCTION EXECUTED.", 1);
			    	
			RegisterView.updateRegister(false, true);				
			new UpdatePC();
				    
			new HighlightMixedMode().highlightLine(
					RegisterView.regAddressValue[0].getText(1).substring(2, 6));	
			}
			else{
			   	//Run upto last code memory.			    	
			   	IdebugInterface.addBreakpoint(new short[]{100,255,255},false);
			   	int[] value=IdebugInterface.runInstruction2();
			   	if(value[1]==148){
			   		ConsoleDisplayMgr.getDefault().println("TARGET BOARD IS RUNNING...", 3);
			   	}
			   				    	
			   	RegisterView.updateRegister(false, true);				
				new UpdatePC();					    
				new HighlightMixedMode().highlightLine(
						RegisterView.regAddressValue[0].getText(1).substring(2, 6));
									
			   	IdebugInterface.removeBreakpoint(new short[]{100}, false);			    	
			}    
		}		
		catch(Exception e){}	
	}

	private void stepIntoMixedLevel() {
		
		int[] val=IdebugInterface.stepOver();
		if(val==null) return;
		
		RegisterView.updateRegister(false, true);
		new UpdatePC();		
		new HighlightMixedMode().highlightLine(RegisterView.regAddressValue[0].getText(1).substring(2, 6));
		
	}

	private void stepIntoLowLevel() {
		
		int[] val=IdebugInterface.stepOver();
		if(val==null) return;
		
		RegisterView.updateRegister(false, true);
		new UpdatePC();		
		new HighlightAssemblyMode().highlightLine
					(RegisterView.regAddressValue[0].getText(1).substring(2, 6));		
	}

	
	private void stepOverLowLevel() {
		
		/*		
		
		String text = new String();
		int line=0, index=0;
		String address=new String("FFFE");
		try {
			BufferedReader reader=null;
			if(DynamicFileLocationAL.absoluteFilePath.equalsIgnoreCase("")){
				reader=new BufferedReader(new FileReader(filePath));
			}else{
				reader=new BufferedReader(new FileReader(DynamicFileLocationAL.absoluteFilePath));
			}
						
			 try {
				while ((text = reader.readLine()) != null ){
					if(EditorLineInfo.lineNo==line) break;
					line++;
				}				
			    index=text.indexOf("x");
			    if(index!= -1){
			    	address = text.substring(index+1, index+5);			    
			    	short[] Packet = new short[3];
			    	Packet[0]=100;  		// temporary breakpoint
			    	Packet[1] = Short.parseShort(address.substring(0, 2),16);
			    	Packet[2] = Short.parseShort(address.substring
			    			(2, address.length()),16); Packet[2]++;
			    	if(Packet[2]>255 ){ Packet[2]=0; Packet[1]++; }
			    	if(Packet[1]>255) Packet[1]=0;
			    	IdebugInterface.addBreakpoint(Packet,false);
			    	int[] value=IdebugInterface.runInstruction2();
			    	if(value==null) return;
			    	
			    	if(value[1]==148){
			    		ConsoleDisplayMgr.getDefault().println("TARGET BOARD IS RUNNING...", 3);
			    	}
			    				    	
			    	IdebugInterface.removeBreakpoint(new short[]{100}, false);		    	
			    	ConsoleDisplayMgr.getDefault().println("STEP-OVER INSTRUCTION EXECUTED.", 1);
			    	
			    	RegisterView.updateRegister(false, true);				
					new UpdatePC();
				    
					new HighlightAssemblyMode().highlightLine(
							RegisterView.regAddressValue[0].getText(1).substring(2, 6));	
			    }
			    else{
			    	//Run upto last code memory.			    	
			    	IdebugInterface.addBreakpoint(new short[]{100,255,255},false);
			    	int[] value=IdebugInterface.runInstruction2();
			    	if(value[1]==148){
			    		ConsoleDisplayMgr.getDefault().println("TARGET BOARD IS RUNNING...", 3);
			    	}
			    				    	
			    	RegisterView.updateRegister(false, true);				
					new UpdatePC();					    
					new HighlightAssemblyMode().highlightLine
						(RegisterView.regAddressValue[0].getText(1).substring(2, 6));
										
			    	IdebugInterface.removeBreakpoint(new short[]{100}, false);			    	
			    }
			    
			   		    
			    
			} catch (IOException e) {}
		} catch (FileNotFoundException e) {}
		catch(Exception e){}
	*/
		
		
		
		try{
		String currentPC = RegisterView.regAddressValue[0].getText(1);
		int index=currentPC.indexOf("x");
		if(index!= -1){
			String address = currentPC.substring(index+1, index+5);			    
			short[] Packet = new short[3];
			Packet[0]=100;  		// temporary breakpoint
			Packet[1] = Short.parseShort(address.substring(0, 2),16);
			Packet[2] = Short.parseShort(address.substring
						(2, address.length()),16);
			
			Packet[2] =(short)( Packet[2] + 4);
			if(Packet[2]>255 ){
					Packet[2]=(short)(Packet[2] - 256); 
					Packet[1]++; 
			}	
			    	
			int[] value=IdebugInterface.addBreakpoint(Packet,false);	if(value==null) return;
			value=IdebugInterface.runInstruction2();					if(value==null) return;
			    	
			if(value[1]==148){
				ConsoleDisplayMgr.getDefault().println("TARGET BOARD IS RUNNING...", 3);
			}
			    				    	
			IdebugInterface.removeBreakpoint(new short[]{100}, false);		    	
			ConsoleDisplayMgr.getDefault().println("STEP-OVER INSTRUCTION EXECUTED.", 1);
			    	
			RegisterView.updateRegister(false, true);				
			new UpdatePC();
				    
			new HighlightAssemblyMode().highlightLine(
					RegisterView.regAddressValue[0].getText(1).substring(2, 6));	
			}
			else{
			   	//Run upto last code memory.			    	
			   	IdebugInterface.addBreakpoint(new short[]{100,255,255},false);
			   	int[] value=IdebugInterface.runInstruction2();
			   	if(value[1]==148){
			   		ConsoleDisplayMgr.getDefault().println("TARGET BOARD IS RUNNING...", 3);
			   	}
			   				    	
			   	RegisterView.updateRegister(false, true);				
				new UpdatePC();					    
				new HighlightAssemblyMode().highlightLine(
						RegisterView.regAddressValue[0].getText(1).substring(2, 6));
									
			   	IdebugInterface.removeBreakpoint(new short[]{100}, false);			    	
			}    
		}		
		catch(Exception e){}	
	
	
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
	
	
	@Override
	public void selectionChanged(IAction action, ISelection selection) {}
		
}
