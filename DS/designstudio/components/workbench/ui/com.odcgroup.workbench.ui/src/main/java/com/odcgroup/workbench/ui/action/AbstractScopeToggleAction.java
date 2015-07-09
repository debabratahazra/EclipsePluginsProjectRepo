package com.odcgroup.workbench.ui.action;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;

import com.odcgroup.workbench.ui.OfsUICore;

abstract public class AbstractScopeToggleAction extends Action 
	implements IWorkbenchAction, IPropertyChangeListener {

	final public static String PREFERENCE_KEY = "com.odcgroup.workbench.ui.navigator.scope"; 
    private IPreferenceStore store;

    public AbstractScopeToggleAction() {
        store = OfsUICore.getDefault().getPreferenceStore();
        //store.setDefault(PREFERENCE_KEY, 0xFFFF); 
        // by default, display models from all scopes
        // moved to a preferenceInitializer extension
        store.addPropertyChangeListener(this);
    }

    abstract public int getScopeMask();
    
    public final void run() {
    	int scope = store.getInt(PREFERENCE_KEY);
    	if((scope & getScopeMask()) > 0) {
    		scope -= getScopeMask(); // remove this bit
    	} else {
    		scope |= getScopeMask(); // set this bit
    	}
        store.setValue(PREFERENCE_KEY, scope);
    }

    protected void updateEnablement() {
        setChecked((store.getInt(PREFERENCE_KEY) & getScopeMask()) > 0);
    }

    public final void propertyChange(PropertyChangeEvent event) {
        if(event.getProperty().equals(PREFERENCE_KEY))
            updateEnablement();
    }

    public void dispose() {
        store.removePropertyChangeListener(this);
    }
}
