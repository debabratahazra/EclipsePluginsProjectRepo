package com.odcgroup.mdf.editor.ui.actions;

import com.odcgroup.mdf.editor.MdfPlugin;
import com.odcgroup.mdf.editor.preferences.MdfPreferencePage;



public class DatasetFilterActionDelegate extends AbstractFilterActionDelegate {

    public DatasetFilterActionDelegate() {
        super(MdfPlugin.DATASET_FILTER, MdfPreferencePage.P_MDF_DATASET_FILTER);
    }

}
