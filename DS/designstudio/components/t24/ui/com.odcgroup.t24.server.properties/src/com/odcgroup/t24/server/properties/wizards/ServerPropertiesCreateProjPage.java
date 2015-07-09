package com.odcgroup.t24.server.properties.wizards;

import org.eclipse.ui.dialogs.WizardNewProjectCreationPage;

public class ServerPropertiesCreateProjPage extends
		WizardNewProjectCreationPage {

	public ServerPropertiesCreateProjPage(String pageName) {
		super(pageName);
		setTitle("Create a Server Project");
		setDescription("Creates a Server Project with a server.properties file");
		setPageComplete(false);
	}
	
	@Override
	protected boolean validatePage() {
		boolean valid = super.validatePage();
		if(valid){
			if (this.getProjectName() != null) {
				if (this.getProjectName().endsWith("-server")) {
					valid = true;
				} else {
					setErrorMessage("Server Project name must comply with the naming convention '[component]-server'!");
					valid = false;
				}
			}
		}
		return valid;
	}
}
