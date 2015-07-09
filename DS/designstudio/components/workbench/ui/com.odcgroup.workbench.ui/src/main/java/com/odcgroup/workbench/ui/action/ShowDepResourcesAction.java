package com.odcgroup.workbench.ui.action;

import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.ui.OfsUICore;

public class ShowDepResourcesAction extends AbstractScopeToggleAction {
	
    public ShowDepResourcesAction() {
        setId("com.odcgroup.workbench.ui.action.showDepResources");
        setText("Show/Hide dependent projects");
        setToolTipText("Show/Hide dependent projects");
        setImageDescriptor(OfsUICore.imageDescriptorFromPlugin(OfsUICore.PLUGIN_ID, "icons/link.png"));
        updateEnablement();
    }

	public int getScopeMask() {
		return IOfsProject.SCOPE_DEPENDENCY;
	}
}
