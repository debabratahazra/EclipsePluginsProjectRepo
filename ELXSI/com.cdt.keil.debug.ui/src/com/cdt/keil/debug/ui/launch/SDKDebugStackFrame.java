package com.cdt.keil.debug.ui.launch;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IRegisterGroup;
import org.eclipse.debug.core.model.IStackFrame;
import org.eclipse.debug.core.model.IThread;
import org.eclipse.debug.core.model.IVariable;

public class SDKDebugStackFrame extends SDKDebugElement implements IStackFrame {
	
	private SDKDebugThread SDKThread;
	private String SDKName;
	private IVariable[] SDKVariable;
	
	public SDKDebugStackFrame(SDKDebugThread thread) {
		super((SDKDebugTarget)thread.getDebugTarget());
		SDKThread = thread;
		SDKName = "SDK Stack";
	}

	@Override
	public int getCharEnd() throws DebugException {		
		return 0;
	}

	@Override
	public int getCharStart() throws DebugException {		
		return 0;
	}

	@Override
	public int getLineNumber() throws DebugException {		
		return 0;
	}

	@Override
	public String getName() throws DebugException {		
		return SDKName;
	}

	@Override
	public IRegisterGroup[] getRegisterGroups() throws DebugException {		
		return null;
	}

	@Override
	public IThread getThread() {		
		return SDKThread;
	}

	@Override
	public IVariable[] getVariables() throws DebugException {		
		return SDKVariable;
	}

	@Override
	public boolean hasRegisterGroups() throws DebugException {		
		return false;
	}

	@Override
	public boolean hasVariables() throws DebugException {		
		return SDKVariable.length > 0;
	}

	@Override
	public boolean canStepInto() {		
		return getThread().canStepInto();
	}

	@Override
	public boolean canStepOver() {		
		return getThread().canStepOver();
	}

	@Override
	public boolean canStepReturn() {		
		return getThread().canStepReturn();
	}

	@Override
	public boolean isStepping() {		
		return getThread().isStepping();
	}

	@Override
	public void stepInto() throws DebugException {
		getThread().stepInto();
	}

	@Override
	public void stepOver() throws DebugException {
		getThread().stepOver();
	}

	@Override
	public void stepReturn() throws DebugException {
		getThread().stepReturn();
	}

	@Override
	public boolean canResume() {		
		return getThread().canResume();
	}

	@Override
	public boolean canSuspend() {		
		return getThread().canSuspend();
	}

	@Override
	public boolean isSuspended() {
		return getThread().isSuspended();
	}

	@Override
	public void resume() throws DebugException {
		getThread().resume();
	}

	@Override
	public void suspend() throws DebugException {
		getThread().suspend();
	}

	@Override
	public boolean canTerminate() {		
		return getThread().canTerminate();
	}

	@Override
	public boolean isTerminated() {		
		return getThread().isTerminated();
	}

	@Override
	public void terminate() throws DebugException {
		getThread().terminate();
	}
}
