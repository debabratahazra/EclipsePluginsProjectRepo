package com.odcgroup.mdf.editor.ui.actions;

import com.odcgroup.mdf.editor.MdfPlugin;
import com.odcgroup.mdf.editor.preferences.MdfPreferencePage;


public class EnumFilterActionDelegate extends AbstractFilterActionDelegate {

    public EnumFilterActionDelegate() {
        super(MdfPlugin.ENUM_FILTER, MdfPreferencePage.P_MDF_ENUM_FILTER);
    }

}
