package com.odcgroup.ocs.server.external.ui.builder.internal.mapping;

import java.io.File;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import com.odcgroup.ocs.server.external.builder.internal.mapping.TargetMapper;
import com.odcgroup.ocs.server.external.model.IExternalServer;
import com.odcgroup.ocs.server.external.ui.OCSServerUIExternalCore;
import com.odcgroup.ocs.server.ui.OcsServerUICore;

public abstract class AbstractTargetMapper implements TargetMapper {

	// Server 
	private IExternalServer classicalServer;
	
	private boolean configured;
	
	public AbstractTargetMapper(IExternalServer classicalServer) {
		this.classicalServer = classicalServer;
		this.configured = false;
	}
	
	/**
	 * Configure the mapper according to the builder configuration.
	 * 
	 * Call super when this method when overridden. It is used to check the target mapper
	 * is properly configured before using it. Failure to do so will
	 * @param builderConfig
	 */
	@Override
	public void configure(Map<String, String> builderConfig) {
		this.configured = true;
	}
	
	/**
	 * @return
	 * @throws CoreException 
	 */
	protected String getDestination() throws CoreException {
		return safeGetServerDirectory(classicalServer.getInstallationDirectory());
	}

	private String safeGetServerDirectory(String serverDirectory) throws CoreException {
		if (!configured) {
			String errorMessage = "Target Mapper used before it is properly configured";
			OCSServerUIExternalCore.getDefault().getDeployBuilderConsole().printError(errorMessage);
			throw new CoreException(new Status(IStatus.ERROR, OcsServerUICore.PLUGIN_ID, 0, 
					errorMessage, null));
		}//DS-5480
		if (serverDirectory !=null && (!new File(serverDirectory).exists() || 
				serverDirectory.length() <= 3)) { // Avoid path like "C:\", "\" or ""
			throw new CoreException(new Status(IStatus.ERROR,
					OcsServerUICore.PLUGIN_ID, 0, "The server location is not properly configured (\"" + serverDirectory + "\")", null));
		}
		return serverDirectory;
	}

}
