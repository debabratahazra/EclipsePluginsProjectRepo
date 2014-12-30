package com.cdt.keil.debug.ui.launch;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.model.IProcess;
import org.eclipse.debug.core.model.IStreamsProxy;

public class SDKProcess implements IProcess {
	
	private ILaunch launch=null;
	
	public SDKProcess(ILaunch launch) {
		this.launch=launch;
	}

	@Override
	public String getAttribute(String key) {		
		return null;
	}

	@Override
	public int getExitValue() throws DebugException {
		return 0;
	}

	@Override
	public String getLabel() {		
		return "SDK Process";
	}

	@Override
	public ILaunch getLaunch() {		
		return launch;
	}

	@Override
	public IStreamsProxy getStreamsProxy() {		
		return null;
	}

	@Override
	public void setAttribute(String key, String value) {
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object getAdapter(Class adapter) {
		return null;
	}

	@Override
	public boolean canTerminate() {		
		return true;
	}

	@Override
	public boolean isTerminated() {		
		return false;
	}

	@Override
	public void terminate() throws DebugException {		
		System.out.println("terminated.......");
	}

}
