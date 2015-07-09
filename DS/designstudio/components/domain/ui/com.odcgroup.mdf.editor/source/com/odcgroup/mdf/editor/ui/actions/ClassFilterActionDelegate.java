package com.odcgroup.mdf.editor.ui.actions;

import com.odcgroup.mdf.editor.MdfPlugin;
import com.odcgroup.mdf.editor.preferences.MdfPreferencePage;


public class ClassFilterActionDelegate extends AbstractFilterActionDelegate {
	public ClassFilterActionDelegate() {
        super(MdfPlugin.CLASS_FILTER, MdfPreferencePage.P_MDF_CLASS_FILTER);
    }
}
