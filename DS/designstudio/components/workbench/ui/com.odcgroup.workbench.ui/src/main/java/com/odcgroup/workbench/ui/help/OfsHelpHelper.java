package com.odcgroup.workbench.ui.help;

import org.eclipse.jface.action.Action;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

/**
 * @author yan
 */
public class OfsHelpHelper {
	
	public static void displayHelp(String contextId) {
		PlatformUI.getWorkbench().getHelpSystem().displayHelp(contextId);
	}

	public static Action createHelpAction(final String contextId) {
    	Action helpAction = new Action("Help") {
    		public void run() {
    			displayHelp(contextId);
    		}
    	};
    	helpAction.setImageDescriptor(
    			PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(ISharedImages.IMG_LCL_LINKTO_HELP));
    	return helpAction;
	}
}
