package com.odcgroup.mdf.editor.ui.actions;

import com.odcgroup.mdf.editor.MdfPlugin;
import com.odcgroup.mdf.editor.preferences.MdfPreferencePage;

/**
 * 
 *
 */
public class BusinessTypeFilterActionDelegate extends AbstractFilterActionDelegate {

    public BusinessTypeFilterActionDelegate() {
        super(MdfPlugin.BUSINESSTYPE_FILTER, MdfPreferencePage.P_MDF_BUSINESSTYPE_FILTER);
    }

}
