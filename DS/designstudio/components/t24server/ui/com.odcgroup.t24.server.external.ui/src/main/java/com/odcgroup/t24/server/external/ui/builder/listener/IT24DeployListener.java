package com.odcgroup.t24.server.external.ui.builder.listener;

import java.io.File;

public interface IT24DeployListener {

	void onSuccessfulDeploy(File deployedXmlFile);

}
