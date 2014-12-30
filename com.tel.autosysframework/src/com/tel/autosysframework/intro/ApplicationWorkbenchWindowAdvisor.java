
package com.tel.autosysframework.intro;

import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;
import org.eclipse.ui.application.IWorkbenchWindowConfigurer;
import org.eclipse.ui.application.WorkbenchWindowAdvisor;

public class ApplicationWorkbenchWindowAdvisor extends WorkbenchWindowAdvisor {

    public ApplicationWorkbenchWindowAdvisor(
            IWorkbenchWindowConfigurer configurer) {
        super(configurer);
    }

    public ActionBarAdvisor createActionBarAdvisor(
            IActionBarConfigurer configurer) {
        return new ApplicationActionBarAdvisor(configurer);
    }

    public void preWindowOpen() {
        IWorkbenchWindowConfigurer configurer = getWindowConfigurer();
//        configurer.setInitialSize(new Point(800, 600));
        configurer.setShowPerspectiveBar(true);
        configurer.setShowFastViewBars(true);
        configurer.setShowProgressIndicator(true);
        configurer.setShowCoolBar(true);
        configurer.setShowStatusLine(true);
        configurer.setTitle("Debu_RCP");
    }
}
