package com.cdt.keil.debug.ui.launch;

import org.eclipse.core.resources.IMarkerDelta;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchesListener;
import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.debug.core.model.IMemoryBlock;
import org.eclipse.debug.core.model.IProcess;
import org.eclipse.debug.core.model.IStackFrame;
import org.eclipse.debug.core.model.IThread;

public class SDKDebugTarget extends SDKDebugElement implements IDebugTarget, ILaunchesListener {
	
	private ILaunch SDKLaunch;	
	private IProcess SDKProcess;
	private SDKDebugTarget SDKTarget;
	private boolean SDKSuspended = false;
	public boolean SDKTerminated = false;
	private SDKDebugThread SDKThread;
	private IThread[] iThreads=null;
	
	public SDKDebugTarget(ILaunch launch, IProcess process) throws CoreException{
		SDKLaunch = launch;		
		SDKProcess=process;
		SDKTarget = this;
		SDKThread = new SDKDebugThread(SDKTarget);
		iThreads = new IThread[0];
		
		DebugPlugin.getDefault().getBreakpointManager().addBreakpointListener(this);
		
	}
	
	public SDKDebugTarget() {
		super(null);
	}

	@Override
	public String getName() throws DebugException {		
		return "SDK Target";
	}

	@Override
	public IProcess getProcess() {
		return SDKProcess;
	}

	@Override
	public IThread[] getThreads() throws DebugException {		
		return iThreads;
	}

	@Override
	public boolean hasThreads() throws DebugException {		
		return (iThreads.length > 0);
	}

	@Override
	public boolean supportsBreakpoint(IBreakpoint breakpoint) {
		if(breakpoint.getModelIdentifier().equals(SDKModifier.ID_SDK_DEBUG_MODEL))
			return true;
		return false;
	}

	@Override
	public IDebugTarget getDebugTarget() {		
		return this;
	}

	@Override
	public ILaunch getLaunch() {		
		return SDKLaunch;
	}

	@Override
	public String getModelIdentifier() {		
		return this.getModelIdentifier();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object getAdapter(Class adapter) {		
		return null;
	}

	@Override
	public boolean canTerminate() {
		if(getProcess() != null)
			return getProcess().canTerminate();
		return true;
	}

	@Override
	public boolean isTerminated() {		
		return getProcess().isTerminated();
	}

	@Override
	public void terminate() throws DebugException {
		System.out.println("terminate..........");
		
		SDKTerminated = true;
		fireTerminateEvent();		
		if(SDKThread != null);
			
	}

	@Override
	public boolean canResume() {
		return !isTerminated() && isSuspended();
	}

	@Override
	public boolean canSuspend() {		
		return !isTerminated() && !isSuspended();
	}

	@Override
	public boolean isSuspended() {		
		return SDKSuspended;
	}

	@Override
	public void resume() throws DebugException {
		System.out.println("resume..........");
		
		resumed(0);
	}
	
	private void resumed(int detail){
		SDKSuspended = false;
		SDKThread.fireResumeEvent(detail);
	}
		

	@Override
	public void suspend() throws DebugException {
		System.out.println("suspend....");
		
		suspended(0);
	}
	
	public void suspended(int detail){
		SDKSuspended = true;
		SDKThread.fireSuspendEvent(detail);
	}

	@Override
	public void breakpointAdded(IBreakpoint breakpoint) {
		System.out.println("breakpoint added.........");
	}

	@Override
	public void breakpointChanged(IBreakpoint breakpoint, IMarkerDelta delta) {
		System.out.println("breakpoint changed........");
		
		if(supportsBreakpoint(breakpoint)){
			try{
				if(breakpoint.isEnabled())
					breakpointAdded(breakpoint);
				else
					breakpointRemoved(breakpoint, null);
			}
			catch(CoreException e){}
			catch(Exception e){}
		}
	}

	@Override
	public void breakpointRemoved(IBreakpoint breakpoint, IMarkerDelta delta) {
		System.out.println("breakpoint remove...........");
	}

	@Override
	public boolean canDisconnect() {		
		return false;
	}

	@Override
	public void disconnect() throws DebugException {		
	}

	@Override
	public boolean isDisconnected() {		
		return false;
	}

	@Override
	public IMemoryBlock getMemoryBlock(long startAddress, long length)
			throws DebugException {		
		return null;
	}

	@Override
	public boolean supportsStorageRetrieval() {		
		return false;
	}

	public IStackFrame[] getStackFrames() {		
		return null;
	}

	public void stepInto() {
				
	}

	public void stepOver() {
		
		
	}

	@Override
	public void launchesAdded(ILaunch[] launches) {
		
		
	}

	@Override
	public void launchesChanged(ILaunch[] launches) {
		
		
	}

	@Override
	public void launchesRemoved(ILaunch[] launches) {
		
		
	}

}
