package com.cdt.keil.debug.ui.internal;

import com.cdt.keil.debug.ui.memory.views.RegisterView;
import com.cdt.keil.debug.ui.serialPortComm.IdebugInterface;
import com.cdt.keil.debug.ui.simulator.sdk.SimulatorSDK;

public class UpdatePC {
	
	static SimulatorSDK SDK=new SimulatorSDK();
	
	public UpdatePC() {
		
		//Update Program Counter value in SDK Register View.
		int[] PC = IdebugInterface.readProgramCounter();
		PC[1]++;   	
		if ((PC[3]== 0 && PC[2]==0) || (PC[3]==255 && PC[2]==255)) SDK.DataTransfer(PC);		
		else{
			PC[3]--;
			if(PC[3]<0){
				PC[3]=255;	PC[2]--;
			}
			SDK.DataTransfer(PC);
		}
		RegisterView.table.redraw();
	}

}
