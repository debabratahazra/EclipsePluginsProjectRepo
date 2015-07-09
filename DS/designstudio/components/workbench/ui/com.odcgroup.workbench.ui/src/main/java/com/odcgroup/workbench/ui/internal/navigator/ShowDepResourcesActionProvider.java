package com.odcgroup.workbench.ui.internal.navigator;

import org.eclipse.jface.action.Action;

import com.odcgroup.workbench.ui.action.AbstractScopeToggleActionProvider;
import com.odcgroup.workbench.ui.action.ShowDepResourcesAction;

public class ShowDepResourcesActionProvider extends AbstractScopeToggleActionProvider {

    private Action showDepResourcesAction;
	
	protected Action getAction() {
        if(showDepResourcesAction == null)
            showDepResourcesAction = new ShowDepResourcesAction();
        return showDepResourcesAction;
    }
}
