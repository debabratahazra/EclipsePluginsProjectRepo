package com.cdt.keil.debug.ui.launch;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.debug.core.model.IStackFrame;
import org.eclipse.debug.core.model.IThread;

public class SDKDebugThread extends SDKDebugElement implements IThread {
	
	private IBreakpoint[] SDKBreakpoints;
	private boolean SDKStepping = false;
	
	public SDKDebugThread(SDKDebugTarget target) {
		super(target);
	}	
	
	@Override
	public IBreakpoint[] getBreakpoints() {
		if(SDKBreakpoints == null)
			return new IBreakpoint[0];
		return SDKBreakpoints;
	}
	
	protected void setBreakpoints(IBreakpoint[] breakpoints){
		SDKBreakpoints = breakpoints;
	}

	@Override
	public String getName() throws DebugException {		
		return "SDK Thread";
	}

	@Override
	public int getPriority() throws DebugException {		
		return 0;
	}

	@Override
	public IStackFrame[] getStackFrames() throws DebugException {
		if(isSuspended()){
			return ((SDKDebugTarget)getDebugTarget()).getStackFrames();
		}
		else
			return new IStackFrame[0];		
	}

	@Override
	public IStackFrame getTopStackFrame() throws DebugException {
		IStackFrame[] frames = getStackFrames();
		if(frames.length > 0)
			return frames[0];
		return null;
	}

	@Override
	public boolean hasStackFrames() throws DebugException {
		return isSuspended();		
	}

	@Override
	public boolean canResume() {
		if(!isTerminated())
			return isSuspended();
		return false;
	}

	@Override
	public boolean canSuspend() {		
		return !isSuspended();
	}

	@Override
	public boolean isSuspended() {		
		return getDebugTarget().isSuspended();
	}

	@Override
	public void resume() throws DebugException {
		getDebugTarget().resume();
	}

	@Override
	public void suspend() throws DebugException {
		getDebugTarget().suspend();
	}

	@Override
	public boolean canStepInto() {
		if(!isTerminated())
			return isSuspended();
		return false;
	}

	@Override
	public boolean canStepOver() {
		if(!isTerminated())
			return isSuspended();
		return false;
	}

	@Override
	public boolean canStepReturn() {		
		return false;
	}

	@Override
	public boolean isStepping() {		
		return SDKStepping;
	}

	@Override
	public void stepInto() throws DebugException {
		((SDKDebugTarget)getDebugTarget()).stepInto();
	}

	@Override
	public void stepOver() throws DebugException {
		((SDKDebugTarget)getDebugTarget()).stepOver();
	}

	@Override
	public void stepReturn() throws DebugException {
		
	}

	@Override
	public boolean canTerminate() {		
		return !isTerminated();
	}

	@Override
	public boolean isTerminated() {		
		return getDebugTarget().isTerminated();
	}

	@Override
	public void terminate() throws DebugException {
		getDebugTarget().terminate();
	}
	
	protected void setStepping(boolean stepping){
		SDKStepping = stepping;
	}

}
