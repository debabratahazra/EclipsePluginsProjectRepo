package com.cdt.keil.debug.ui.debug;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.WorkbenchException;
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
import com.cdt.keil.debug.ui.internal.SourceFileLocation;
import com.cdt.keil.debug.ui.launch.run.FileOpenOperation;
import com.cdt.keil.debug.ui.memory.views.RegisterView;
import com.cdt.keil.debug.ui.serialPortComm.IdebugInterface;


public class DebugAction implements IWorkbenchWindowActionDelegate {
	
	IWorkbenchWindow window;
	DownloadImageAction download;
	
	//1 -> aDebug,	2-> hDebug,	3-> mDebug
	public static int debugMode;
	
	public void dispose() {
	}

	public void init(IWorkbenchWindow window) {		
		this.window=window;
		download=new DownloadImageAction();		
	}

	
	public void run(IAction action) {
			
		//Create new Console Manager [Communication Status]
		new DownloadImageAction();
		
		//Set Default Console "SDK Communication"
		try{
			ConsolePlugin.getDefault().getConsoleManager().showConsoleView(ConsoleDisplayMgr.console[0]);
		}catch(Exception e1){}
		
		//Checking build error or not.
		String srcFile = new SourceFileLocation(true).sourceFileLocation();
		if(srcFile==null) return;
		
		//Reset DS for LL Debugging.
		try{
			FileInfoName.clearMap();
			BreakpointMap.clearMap();
		}catch(Exception e){}
			
			
		try{
			//Target Reset.
			int[] packet=IdebugInterface.resetInstruction();	
			if(packet==null) return;	//Board isn't connected properly.
		}catch(Exception e){}
			
		ConsoleDisplayMgr.getDefault().clear();
		ConsoleDisplayMgr.getDefault().println("DEBUG MODE: ASSEMBLY LEVEL (DEFAULT)", 1)	;
			//Change to Debug perspective.
			try {	
				
				//Change to Debug Perspective.
				PlatformUI.getWorkbench().showPerspective("org.eclipse.debug.ui.DebugPerspective", window);		
								
				//Open Register View, Memory View Menu, Mode Action set, Reset Action & CView. 
				PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().
					showView("com.cdt.keil.debug.ui.RegisterView");
				PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().
					showActionSet("com.cdt.keil.debug.ui.simulatorView");
				PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().
				showActionSet("com.cdt.keil.debug.ui.resetAction");
				PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().
					showView("org.eclipse.cdt.ui.CView");
				PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().
					showActionSet("com.cdt.keil.debug.ui.debugActionSet");
				PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().
					showActionSet("com.cdt.keil.debug.ui.modeActionSet");
				
				//Hide Unnecessary Views
				PlatformUI.getWorkbench().getActiveWorkbenchWindow().
					getActivePage().hideView(PlatformUI.getWorkbench().
					getActiveWorkbenchWindow().getActivePage().findView("org.eclipse.debug.ui.BreakpointView"));
				PlatformUI.getWorkbench().getActiveWorkbenchWindow().
					getActivePage().hideView(PlatformUI.getWorkbench().
					getActiveWorkbenchWindow().getActivePage().findView("org.eclipse.debug.ui.VariableView"));
				PlatformUI.getWorkbench().getActiveWorkbenchWindow().
					getActivePage().hideView(PlatformUI.getWorkbench().
					getActiveWorkbenchWindow().getActivePage().findView("org.eclipse.ui.views.ContentOutline"));
					
						
				try{
					//Save & Close All Editor Tabs.
					PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().saveAllEditors(false);
					PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().closeAllEditors(false);
				}catch(Exception e){}
				
				try{
					//Remove all Breakpoints.
					new SDKRemoveAllBreakpointsAction().run(action);	
					IdebugInterface.removeVirtualBreakpoint((short)0);
				}catch(Exception e){}
				
				try{
					//Unchecked all debug mode action button except AL Mode.
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
								
				
				//Down-load Hex Image File.
				int retValue=downloadHexFile();
				if(retValue==0) return;		//Board not connected properly.
			
				//Open & activate the LIBRARY.DSM File.
				int valid = new FileOpenOperation().openLibDsmFile();		
				if(valid==0) return;
								
				//Open All .DSM file in editor.
				String[] filename = new FileInfoName().getAllFileName();
				new FileOpenOperation().openAllDsmFile(filename);	
								
				//Update Register View.
				RegisterView.updateRegister(true, false);								
				
			} catch (WorkbenchException e) {}catch(Exception e){}
						
		ConsoleDisplayMgr.getDefault().println("ALL BREAKPOINTS REMOVED", 1);
			
		StepReturnAction.spRecentValue="0x07";
		EditorLineInfo.lineNo=0;
		DynamicFileLocationAL.absoluteFilePath="";
		DynamicFileLocationAL.relativeFilePath="";
		EditorLineInfo.text="";
		RegisterView.spChanged=false;
		debugMode=1;		//Default Mode is Assembly Mode.
		new HighlightAssemblyMode().highlightLine("0000");   	
		
		IdebugInterface.terminateProcess();
	}
	
	private int downloadHexFile(){		
		
		//Down load Hex File..........		
		MessageBox message=new MessageBox(window.getShell(),SWT.ICON_INFORMATION|SWT.YES|SWT.NO);		
		message.setMessage("Do you want to download Hex file to the Target ?");
		message.setText("Confirm Download HEX File");		
		if(message.open()==SWT.YES){
		try{
		
			int suc=IdebugInterface.transferHexFile();	//Load Hex file to the target.
			if(suc==0) return 0;
			
							
		}catch(Exception e){
			return 0;
		}	
		}
	    return 1;
	}

	public void selectionChanged(IAction action, ISelection selection) {	
	}

}
