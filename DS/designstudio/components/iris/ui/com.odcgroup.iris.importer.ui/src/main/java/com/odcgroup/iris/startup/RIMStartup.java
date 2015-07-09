package com.odcgroup.iris.startup;

import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IPerspectiveDescriptor;
import org.eclipse.ui.IStartup;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PerspectiveAdapter;
import org.eclipse.ui.PlatformUI;
public class RIMStartup implements IStartup {
	@Override
	public void earlyStartup() {
		Display.getDefault().syncExec(new Runnable() {
			public void run() {
				final IWorkbenchWindow workbenchWindow = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
				if (workbenchWindow != null) {
					workbenchWindow.addPerspectiveListener(new PerspectiveAdapter() {
						public void perspectiveActivated(IWorkbenchPage page,
								IPerspectiveDescriptor perspectiveDescriptor) {
							super.perspectiveActivated(page, perspectiveDescriptor);
							IViewPart part = workbenchWindow.getActivePage().findView("com.temenos.interaction.rimdsl.visualisation.views.RIMDSLVisualisationView");
							workbenchWindow.getActivePage().hideView(part);
						}
					});
					
				}
			}
		});
	}

}
