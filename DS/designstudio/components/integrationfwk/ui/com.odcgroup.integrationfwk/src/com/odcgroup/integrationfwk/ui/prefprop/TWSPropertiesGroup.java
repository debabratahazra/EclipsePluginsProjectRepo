package com.odcgroup.integrationfwk.ui.prefprop;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;

import com.odcgroup.integrationfwk.ui.utils.StringConstants;

public class TWSPropertiesGroup {
	// this method creates a visual area on the screen that
	// related to connectivity to T24
	public static Group createTwsPropertiesGroup(Composite parent) {

		Group twsPropertiesGroup = new Group(parent, SWT.SHADOW_IN);
		GridData twsPropertiesGridData = new GridData();
		twsPropertiesGridData.horizontalAlignment = GridData.FILL;
		twsPropertiesGridData.grabExcessHorizontalSpace = true;
		twsPropertiesGridData.horizontalSpan = 3;
		twsPropertiesGroup.setLayoutData(twsPropertiesGridData);

		twsPropertiesGroup
				.setText(StringConstants._UI_WIZARD_CONNECTION_PROPERTIES);
		twsPropertiesGroup.setLayout(new GridLayout(1, false));

		return twsPropertiesGroup;
		/*
		 * twsPropertiesView = new TWSPropertiesView(twsPropertiesGroup);
		 * twsPropertiesView.setUseProjectSpecificSettings(false);
		 * 
		 * TWSProjectPreferences twsProjectPreferences = new
		 * TWSProjectPreferences();
		 * 
		 * twsPropertiesView.setHost(twsProjectPreferences.getHostPreference());
		 * twsPropertiesView.setOfsSource(twsProjectPreferences
		 * .getOfsSourcePreference());
		 * twsPropertiesView.setPort(Integer.toString(twsProjectPreferences
		 * .getPortPreference()));
		 */
	}

}
