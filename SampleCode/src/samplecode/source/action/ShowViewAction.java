package samplecode.source.action;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

public class ShowViewAction implements IWorkbenchWindowActionDelegate {
	
	String sampleViewID = null;

	@Override
	public void run(IAction action) {
		
		/**
		 * Show the Sample View
		 */
		if (action.isChecked()) {
			try {
				PlatformUI.getWorkbench().getActiveWorkbenchWindow()
						.getActivePage().showView(sampleViewID);
			} catch (PartInitException e) {
				e.printStackTrace();
			}
		} else {

			/**
			 * Hide the Sample View
			 */
			IViewPart viewPart = PlatformUI.getWorkbench()
					.getActiveWorkbenchWindow().getActivePage()
					.findView(sampleViewID);
			PlatformUI.getWorkbench().getActiveWorkbenchWindow()
					.getActivePage().hideView(viewPart);
		}
	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
	}

	@Override
	public void dispose() {
	}

	@Override
	public void init(IWorkbenchWindow window) {
		sampleViewID = "SampleCode.view" ;
	}
}
