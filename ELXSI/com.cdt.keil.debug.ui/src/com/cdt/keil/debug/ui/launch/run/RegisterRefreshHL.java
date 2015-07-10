package com.cdt.keil.debug.ui.launch.run;

import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;

import com.cdt.keil.debug.ui.editor.info.HighlightHighlevelMode;
import com.cdt.keil.debug.ui.internal.UpdatePC;
import com.cdt.keil.debug.ui.memory.views.RegisterView;
import com.cdt.keil.debug.ui.simulator.sdk.SimulatorSDK;

public class RegisterRefreshHL extends Thread{
	
	private Display display = Display.getDefault();
	SimulatorSDK SDK=new SimulatorSDK();	
	
	public RegisterRefreshHL(Display display) {
		this.display=display;		
	}
	
	@Override
	public void run() {
		display.asyncExec(new Runnable(){
			@Override
			public void run() {
				
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {}
				catch(Exception e1){}
				
				RegisterView.updateRegister(false, true);				
				new UpdatePC();
				
				new HighlightHighlevelMode().highlightLine(
						RegisterView.regAddressValue[0].getText(1));
				
				try{
					//Focus Debug View.
		    		PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().
		    		showView("org.eclipse.debug.ui.DebugView", null, org.eclipse.ui.IWorkbenchPage.VIEW_ACTIVATE);
		    		
		    	}catch(Exception e){}
			}
		});
	}

}
