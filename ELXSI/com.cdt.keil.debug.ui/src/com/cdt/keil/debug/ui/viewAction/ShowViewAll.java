package com.cdt.keil.debug.ui.viewAction;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import com.cdt.keil.debug.ui.simulator.sdk.SimulatorSDK;


public class ShowViewAll implements IWorkbenchWindowActionDelegate {
	
	SimulatorSDK SDK=new SimulatorSDK();

	@Override
	public void dispose() {		
	}

	@Override
	public void init(IWorkbenchWindow window) {		
	}

	@Override
	public void run(IAction action) {			
		try {
			PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().
			showView("com.cdt.keil.debug.ui.FlashCodeMemoryView");
			PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().
			showView("com.cdt.keil.debug.ui.FlashDataMemoryView");
			PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().
			showView("com.cdt.keil.debug.ui.SFRView");
			PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().
			showView("com.cdt.keil.debug.ui.RegisterView");
			PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().
			showView("com.cdt.keil.debug.ui.SRAMDataMemoryView");
			PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().
			showView("com.cdt.keil.debug.ui.ScratchpadRAMView");
			PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().
			showView("com.cdt.keil.debug.ui.FlashSRAMBufferCodeView");
			PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().
			showView("com.cdt.keil.debug.ui.FlashSRAMBufferDataView");
			PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().
			showView("com.cdt.keil.debug.ui.ProgramSecurityMemory");
			PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().
			showView("com.cdt.keil.debug.ui.DataSecurityMemory");
		} catch (PartInitException e) {					
		}catch(Exception e){}
		
	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
	}

}
