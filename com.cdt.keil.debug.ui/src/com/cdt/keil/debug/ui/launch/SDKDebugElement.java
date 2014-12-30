package com.cdt.keil.debug.ui.launch;

import org.eclipse.core.runtime.PlatformObject;
import org.eclipse.debug.core.DebugEvent;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.model.IDebugElement;
import org.eclipse.debug.core.model.IDebugTarget;

public class SDKDebugElement extends PlatformObject implements IDebugElement {

	protected SDKDebugTarget SdkTarget;
	
	public SDKDebugElement(SDKDebugTarget target) {
		SdkTarget=target;
	}
	
	public SDKDebugElement() {	
		SdkTarget=null;
	}
	
	@Override
	public IDebugTarget getDebugTarget() {		
		return SdkTarget;
	}

	@Override
	public ILaunch getLaunch() {		
		return getDebugTarget().getLaunch();
	}

	@Override
	public String getModelIdentifier() {		
		return SDKModifier.ID_SDK_DEBUG_MODEL;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object getAdapter(Class adapter) {
        if (adapter == IDebugElement.class) {
            return this;
        }
        return super.getAdapter(adapter);
    }
	
	public void setDebugtarget(SDKDebugTarget target){
		SdkTarget = target;
	}
			
	
    protected void fireEvent(DebugEvent event) {
    	
        DebugPlugin.getDefault().fireDebugEventSet(new DebugEvent[] {event});
    }    
   
    protected void fireCreationEvent() {
        fireEvent(new DebugEvent(this, DebugEvent.CREATE));
    }   
       
    public void fireResumeEvent(int detail) {
    	
        fireEvent(new DebugEvent(this, DebugEvent.RESUME, detail));
    }
   
    public void fireSuspendEvent(int detail) {
        fireEvent(new DebugEvent(this, DebugEvent.SUSPEND, detail));
    }    
    
    protected void fireTerminateEvent() {
        fireEvent(new DebugEvent(this, DebugEvent.TERMINATE));
    }

}
