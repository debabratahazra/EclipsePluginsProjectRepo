package com.odcgroup.t24.server.external.ui.action;

import java.io.File;
import java.util.List;

import com.odcgroup.t24.server.external.ui.T24ServerUIExternalCore;
import com.odcgroup.t24.server.external.ui.builder.listener.IT24DeployListener;

public class PostDeployAction {

	public void postDeploy(File deployedXmlFile) {
		List<IT24DeployListener> listeners = T24ServerUIExternalCore.getDefault().getDeployListeners();
		if (listeners != null && !listeners.isEmpty()) {
			listeners.get(0).onSuccessfulDeploy(deployedXmlFile);
		}
	}
}
