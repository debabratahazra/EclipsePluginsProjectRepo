package com.odcgroup.mdf.editor.ui.actions;

import com.odcgroup.mdf.editor.MdfPlugin;
import com.odcgroup.mdf.editor.preferences.MdfPreferencePage;


public class MainClassFilterActionDelegate extends AbstractFilterActionDelegate {
	public MainClassFilterActionDelegate() {
        super(MdfPlugin.MAIN_CLASS_FILTER, MdfPreferencePage.P_MDF_MAIN_CLASS_FILTER);
    }
}
