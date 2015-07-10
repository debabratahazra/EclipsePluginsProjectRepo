package com.cdt.keil.debug.ui.debug;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.console.ConsolePlugin;

import com.cdt.keil.debug.ui.breakpoints.SDKRemoveAllBreakpointsAction;
import com.cdt.keil.debug.ui.console.ConsoleDisplayMgr;
import com.cdt.keil.debug.ui.debug.mode.AssemblyLevelModeAction;
import com.cdt.keil.debug.ui.debug.mode.HighLevelModeAction;
import com.cdt.keil.debug.ui.debug.mode.MixedLevelModeAction;
import com.cdt.keil.debug.ui.editor.info.EditorLineInfo;
import com.cdt.keil.debug.ui.editor.info.HighlightAssemblyMode;
import com.cdt.keil.debug.ui.internal.BreakpointMap;
import com.cdt.keil.debug.ui.internal.DynamicFileLocationAL;
import com.cdt.keil.debug.ui.internal.FileInfoName;
import com.cdt.keil.debug.ui.launch.run.FileOpenOperation;
import com.cdt.keil.debug.ui.memory.views.RegisterView;
import com.cdt.keil.debug.ui.serialPortComm.IdebugInterface;
import com.cdt.keil.debug.ui.serialPortComm.RefreshFile;


public class DownloadImageAction implements IWorkbenchWindowActionDelegate {
	
	IWorkbenchWindow window;
	private static int Flag=0;
	ConsoleDisplayMgr display;
	
	public DownloadImageAction() {		
		if(Flag==0){
			new RefreshFile().start();
			display=new ConsoleDisplayMgr("SDK COMMUNICATION STATUS");
			Flag=1;
		}
	}

	@Override
	public void dispose() {
	}

	@Override
	public void init(IWorkbenchWindow window) {
		this.window=window;	
	}

	@Override
	public void run(IAction action){		
		
		//Create new Console Manager [Communication Status]
		new DownloadImageAction();		
		try{
			//Set default console.
			ConsolePlugin.getDefault().getConsoleManager().showConsoleView(ConsoleDisplayMgr.console[0]);
		}catch(Exception e1){}
							
		
		//Down load Hex File..........		
		MessageBox message=new MessageBox(window.getShell(),SWT.ICON_INFORMATION|SWT.YES|SWT.NO);		
		message.setMessage("Do you want to download Hex file to the Target ?");
		message.setText("Confirm Download HEX File");		
		if(message.open()==SWT.YES){
			
			try{
				//Remove all Breakpoints.
				new SDKRemoveAllBreakpointsAction().run(action);
				IdebugInterface.removeVirtualBreakpoint((short)0);
			}catch(Exception e){}
			
			
			try{
				//Unchecked all debug mode except AL Mode.
				AssemblyLevelModeAction.aAction.setChecked(true);				
				HighLevelModeAction.hAction.setChecked(false);
			}catch(Exception e){
				try{
					HighLevelModeAction.hAction.setChecked(false);
					MixedLevelModeAction.mAction.setChecked(false);
				}catch(Exception ee){
					try{
						MixedLevelModeAction.mAction.setChecked(false);
					}catch(Exception eee){}
				}
			}		
			
					
			try{
				FileInfoName.clearMap();
				BreakpointMap.clearMap();
			}catch(Exception e){}
			
			ConsoleDisplayMgr.getDefault().clear();			
			
			try{
				int [] packet=IdebugInterface.resetInstruction();	
				if(packet==null) return;	// Connection error.
				
				
				ConsoleDisplayMgr.getDefault().println("HEX IMAGE FILE DOWNLOADING.....PLEASE WAIT.", 1);
				
				int valid = new FileOpenOperation().openLibDsmFile();
				if(valid==0) return;		//File not found.
				
				int success=IdebugInterface.transferHexFile();	//Load Hex file to the target.
				if(success==0) return;
								
				try{
					//Save & Close All Editor Tabs.
					PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().saveAllEditors(false);
					PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().closeAllEditors(false);
				}catch(Exception e){}
				
				//Open All .DSM file in editor.
				String[] filename = new FileInfoName().getAllFileName();
				new FileOpenOperation().openAllDsmFile(filename);					
				
			}catch(Exception e){}			
			
			try {
				PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().
				showView("com.cdt.keil.debug.ui.RegisterView");
				
				RegisterView.updateRegister(true, false);
			} catch (PartInitException e) {}	
			catch(Exception e){}
			
			
		}		 	
				
		EditorLineInfo.lineNo=0;
		StepReturnAction.spRecentValue="0x07";
		DynamicFileLocationAL.absoluteFilePath="";
		DynamicFileLocationAL.relativeFilePath="";
		EditorLineInfo.text="";
		RegisterView.spChanged=false;
		DebugAction.debugMode=1;		//Default Mode is Assembly Mode.
		new HighlightAssemblyMode().highlightLine("0000");
		
		IdebugInterface.terminateProcess();
	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
	}

}
