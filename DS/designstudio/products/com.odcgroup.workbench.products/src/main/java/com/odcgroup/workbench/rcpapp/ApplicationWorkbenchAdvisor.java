package com.odcgroup.workbench.rcpapp;

import org.eclipse.ui.application.IWorkbenchWindowConfigurer;
import org.eclipse.ui.application.WorkbenchWindowAdvisor;
import org.eclipse.ui.internal.ide.application.IDEWorkbenchAdvisor;

@SuppressWarnings("restriction")
public class ApplicationWorkbenchAdvisor extends IDEWorkbenchAdvisor {

	@Override
    public WorkbenchWindowAdvisor createWorkbenchWindowAdvisor(IWorkbenchWindowConfigurer configurer) {

		// use this line if we want to do a pure RCP
		// return new ApplicationWorkbenchWindowAdvisor(configurer);

		return super.createWorkbenchWindowAdvisor(configurer);
    }

	@Override
	public String getInitialWindowPerspectiveId() {
		return super.getInitialWindowPerspectiveId();
	}
}
