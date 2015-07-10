package com.zealcore.se.rcp;

import org.eclipse.swt.graphics.Point;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;
import org.eclipse.ui.application.IWorkbenchWindowConfigurer;
import org.eclipse.ui.application.WorkbenchWindowAdvisor;

public class ApplicationWorkbenchWindowAdvisor extends WorkbenchWindowAdvisor {

    private static final int WINDOW_HEIGHT = 900;

    private static final int WINDOW_WIDTH = 1200;

    public ApplicationWorkbenchWindowAdvisor(
            final IWorkbenchWindowConfigurer configurer) {
        super(configurer);
    }

    @Override
    public ActionBarAdvisor createActionBarAdvisor(
            final IActionBarConfigurer configurer) {
        return new ApplicationActionBarAdvisor(configurer);
    }

    @Override
    public void preWindowOpen() {
        final IWorkbenchWindowConfigurer configurer = getWindowConfigurer();
        configurer.setInitialSize(new Point(
                ApplicationWorkbenchWindowAdvisor.WINDOW_WIDTH,
                ApplicationWorkbenchWindowAdvisor.WINDOW_HEIGHT));
        configurer.setShowCoolBar(true);
        configurer.setShowMenuBar(true);
        configurer.setShowStatusLine(false);
        configurer.setShowPerspectiveBar(false);
        configurer.setShowFastViewBars(false);
        configurer.setShowProgressIndicator(false);
        configurer
                .setTitle("Enea Optima Log Analyzer 1.2.4 (c) Enea ZealCore AB");
    }
}
