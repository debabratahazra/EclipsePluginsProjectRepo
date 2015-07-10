package com.cdt.keil.debug.ui.breakpoints;


import org.eclipse.core.resources.IMarkerDelta;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.IBreakpointManager;
import org.eclipse.debug.core.IBreakpointsListener;
import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.debug.internal.ui.DebugUIPlugin;
import org.eclipse.debug.internal.ui.actions.AbstractRemoveAllActionDelegate;
import org.eclipse.debug.internal.ui.actions.ActionMessages;
import org.eclipse.jface.action.IAction;
import org.eclipse.ui.IWorkbenchWindow;

@SuppressWarnings("restriction")
public class SDKRemoveAllBreakpointsAction extends
		AbstractRemoveAllActionDelegate implements IBreakpointsListener {

	@Override
	protected void initialize() {
		DebugPlugin.getDefault().getBreakpointManager().addBreakpointListener(this);
	}

	@Override
	protected boolean isEnabled() {		
		return DebugPlugin.getDefault().getBreakpointManager().hasBreakpoints();
	}

	

	@Override
	public void run(IAction action) {
		final IBreakpointManager breakpointManager= DebugPlugin.getDefault().getBreakpointManager();
		final IBreakpoint[] breakpoints = breakpointManager.getBreakpoints();
		if (breakpoints.length < 1) {
			return;
		}
		IWorkbenchWindow window= DebugUIPlugin.getActiveWorkbenchWindow();
		if (window == null) {
			return;
		}
		/*IPreferenceStore store = DebugUIPlugin.getDefault().getPreferenceStore();
		boolean prompt = store.getBoolean(IDebugPreferenceConstants.PREF_PROMPT_REMOVE_ALL_BREAKPOINTS);
		boolean proceed = true;
		if(prompt) {
			MessageDialogWithToggle mdwt = MessageDialogWithToggle.openYesNoQuestion(window.getShell(), ActionMessages.RemoveAllBreakpointsAction_0, 
					ActionMessages.RemoveAllBreakpointsAction_1, ActionMessages.RemoveAllBreakpointsAction_3, !prompt, null, null);
			if(mdwt.getReturnCode() !=  IDialogConstants.YES_ID){
				proceed = false;
			}
			else {
				//store.setValue(IDebugPreferenceConstants.PREF_PROMPT_REMOVE_ALL_BREAKPOINTS, !mdwt.getToggleState());
			//}
		}  */
		if (true) {
            new Job(ActionMessages.RemoveAllBreakpointsAction_2) { 
                protected IStatus run(IProgressMonitor monitor) {
                    try {
                        breakpointManager.removeBreakpoints(breakpoints, true);
                    } catch (CoreException e) {
                        DebugUIPlugin.log(e);
                        return Status.CANCEL_STATUS;
                    }
                    return Status.OK_STATUS;
                }
            }.schedule();
		}
	}

	@Override
	public void breakpointsChanged(IBreakpoint[] breakpoints,
			IMarkerDelta[] deltas) {		
	}

	@Override
	public void breakpointsRemoved(IBreakpoint[] breakpoints,
			IMarkerDelta[] deltas) {
		if (getAction() != null) {
			update();
		}		
	}

	@Override
	public void breakpointsAdded(IBreakpoint[] breakpoints) {
		update();
		
	}

}
