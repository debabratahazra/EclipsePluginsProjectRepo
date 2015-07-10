package com.cdt.keil.debug.ui.launch.run;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.debug.core.model.ILaunchConfigurationDelegate;
import org.eclipse.ui.console.ConsolePlugin;

import com.cdt.keil.debug.ui.console.ConsoleDisplayMgr;
import com.cdt.keil.debug.ui.debug.DebugAction;
import com.cdt.keil.debug.ui.debug.DownloadImageAction;
import com.cdt.keil.debug.ui.editor.info.EditorLineInfo;
import com.cdt.keil.debug.ui.editor.info.HighlightHighlevelMode;
import com.cdt.keil.debug.ui.memory.views.RegisterView;
import com.cdt.keil.debug.ui.serialPortComm.IdebugInterface;

public class SDKLaunchActionDelegate implements ILaunchConfigurationDelegate {

	private static int flag;	
	
	public SDKLaunchActionDelegate() throws CoreException {		
		flag=0;		
	}
	
	@Override
	public void launch(ILaunchConfiguration configuration, String mode,
			ILaunch launch, IProgressMonitor monitor) throws CoreException {
		
		
		if(mode.equals(ILaunchManager.RUN_MODE)) {
			//If Run Mode is apply.
			
			new DownloadImageAction();		//Create Console Display.
			try{			
				//Set it default console.
				ConsolePlugin.getDefault().getConsoleManager().showConsoleView(ConsoleDisplayMgr.console[0]);
			}catch(Exception e){}
			
		
			if (monitor == null) {
                	monitor = new NullProgressMonitor();
            }
			
			monitor.beginTask("Launching SDK", 1);
			if (monitor.isCanceled()) {
               	return;
            }		
				
			if(flag==0){
				IdebugInterface.resetInstruction();			
			}
				
			int[] value;
			switch(DebugAction.debugMode){
			
			case 1:
				//AL Mode.
				int ret=assemblyLevelRun(launch);
				if(ret==0) {
					DebugPlugin.getDefault().getLaunchManager().removeLaunch(launch);
					return;
				}
				value=IdebugInterface.runInstruction();
				if(value==null) return;
				if(value[1]==148){
					ConsoleDisplayMgr.getDefault().println("TARGET BOARD IS RUNNING...", 3);
				}
				new RegisterRefreshAL(RegisterView.display).start();
								
				break;
			case 2:
				//HL Mode.
				ret=highLevelRun();
				if(ret==0) {
					DebugPlugin.getDefault().getLaunchManager().removeLaunch(launch);
					return;
				}
				break;
			case 3:
				//ML Mode.
				mixedLevelRun();			
								
				break;
			default:
				break;
			}
				
			if(flag>0)
				DebugPlugin.getDefault().getLaunchManager().removeLaunch(launch);
			flag++;	
        
			
			/*IProcess process = new SDKProcess(launch);
			IDebugTarget target=new SDKDebugTarget(launch,process);
			launch.addDebugTarget(target);	*/
					
			if (monitor.isCanceled()) {
				return;
			}	
			monitor.worked(1);
			monitor.done();
		}	
	}
	

	private void mixedLevelRun() {

		int[] value=IdebugInterface.runInstruction();
		if(value==null) return;
		if(value[1]==148){
			ConsoleDisplayMgr.getDefault().println("TARGET BOARD IS RUNNING...", 3);
		}
		new RegisterRefreshML(RegisterView.display).start();
		
	}

	private int highLevelRun() {
		
		if(HighlightHighlevelMode.currentHighlightText==null){
			return 0;
		}
		int[] value=IdebugInterface.runInstruction();		
		if(value==null) return 0;		
		if(value[1]==148){
			ConsoleDisplayMgr.getDefault().println("TARGET BOARD IS RUNNING...", 3);
		}	
		new RegisterRefreshHL(RegisterView.display).start();
		return 1;
	}

	private int assemblyLevelRun(ILaunch launch) {

		if(EditorLineInfo.text==null ||    			 
				EditorLineInfo.text.contains("$ End")){
			DebugPlugin.getDefault().getLaunchManager().removeLaunch(launch);
			return 0;	//Stop Running.
		}
		return 1;		//Successful.
	}
}
