package com.cdt.keil.debug.ui.debug;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

import com.cdt.keil.debug.ui.internal.UpdatePC;
import com.cdt.keil.debug.ui.memory.views.RegisterView;
import com.cdt.keil.debug.ui.serialPortComm.IdebugInterface;

public class StopAction implements IWorkbenchWindowActionDelegate {

	@Override
	public void dispose() {
	}

	@Override
	public void init(IWorkbenchWindow window) {
	}

	@Override
	public void run(IAction action) {	
		
		int[] val=IdebugInterface.stopInstruction(true);
		if(val==null) return;
		
		RegisterView.updateRegister(false, true);			
		new UpdatePC();	
		
	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {

	}

}
