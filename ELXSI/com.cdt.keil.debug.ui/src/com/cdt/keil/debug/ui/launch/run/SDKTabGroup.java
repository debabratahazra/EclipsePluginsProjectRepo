package com.cdt.keil.debug.ui.launch.run;

import org.eclipse.debug.ui.AbstractLaunchConfigurationTabGroup;
import org.eclipse.debug.ui.ILaunchConfigurationDialog;
import org.eclipse.debug.ui.ILaunchConfigurationTab;

public class SDKTabGroup extends AbstractLaunchConfigurationTabGroup {

	public SDKTabGroup() {		
	}

	@Override
	public void createTabs(ILaunchConfigurationDialog dialog, String mode) {	
		
		setTabs(new ILaunchConfigurationTab[]{				
				new SDKRunTab()				
		});
	}
}
