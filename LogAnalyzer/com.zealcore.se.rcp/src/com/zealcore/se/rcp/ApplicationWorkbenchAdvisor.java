package com.zealcore.se.rcp;

import org.eclipse.ui.application.IWorkbenchConfigurer;
import org.eclipse.ui.application.IWorkbenchWindowConfigurer;
import org.eclipse.ui.application.WorkbenchAdvisor;
import org.eclipse.ui.application.WorkbenchWindowAdvisor;
import org.eclipse.ui.internal.ide.model.WorkbenchAdapterBuilder;

public class ApplicationWorkbenchAdvisor extends WorkbenchAdvisor {

    private static final String PERSPECTIVE_ID = "com.zealcore.se.ui.PerspectiveFactory";

    @Override
    public WorkbenchWindowAdvisor createWorkbenchWindowAdvisor(
            final IWorkbenchWindowConfigurer configurer) {
        return new ApplicationWorkbenchWindowAdvisor(configurer);
    }

    @Override
    public String getInitialWindowPerspectiveId() {
        return ApplicationWorkbenchAdvisor.PERSPECTIVE_ID;
    }

    @Override
    public void initialize(final IWorkbenchConfigurer configurer) {
        WorkbenchAdapterBuilder.registerAdapters();
        configurer.setSaveAndRestore(true);
        super.initialize(configurer);
    }

    @Override
    public void eventLoopException(final Throwable exception) {
        super.eventLoopException(exception);
    }
}
