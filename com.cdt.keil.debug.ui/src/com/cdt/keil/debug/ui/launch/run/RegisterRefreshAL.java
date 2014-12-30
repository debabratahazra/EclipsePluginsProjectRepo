package com.cdt.keil.debug.ui.launch.run;

import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;

import com.cdt.keil.debug.ui.debug.StepReturnAction;
import com.cdt.keil.debug.ui.editor.info.EditorLineInfo;
import com.cdt.keil.debug.ui.editor.info.HighlightAssemblyMode;
import com.cdt.keil.debug.ui.internal.UpdatePC;
import com.cdt.keil.debug.ui.memory.views.RegisterView;
import com.cdt.keil.debug.ui.serialPortComm.IdebugInterface;
import com.cdt.keil.debug.ui.simulator.sdk.SimulatorSDK;

public class RegisterRefreshAL extends Thread{

	private Display display = Display.getDefault();
	SimulatorSDK SDK=new SimulatorSDK();	
	
	public RegisterRefreshAL(Display display) {
		this.display=display;		
	}
	
	public void run(){
		display.asyncExec(new Runnable(){
			@Override
			public void run() {
				
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {}
				catch(Exception e1){}
				
				RegisterView.updateRegister(false, true);				
				new UpdatePC();
								
				EditorLineInfo.lineNo=0;
				new HighlightAssemblyMode().highlightLine(RegisterView.regAddressValue[0].getText(1).substring(2, 6));
				
				try{
					//Focus Debug View.
		    		PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().
		    		showView("org.eclipse.debug.ui.DebugView", null, org.eclipse.ui.IWorkbenchPage.VIEW_ACTIVATE);
		    		
		    	}catch(Exception e){}
				
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
		});
		
	}
}
