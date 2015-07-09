package com.odcgroup.t24.server.external.util;

import com.odcgroup.t24.server.external.model.internal.T24ServerException;
import com.odcgroup.t24.server.external.util.T24AgentConnection.T24Response;
import com.temenos.services.designstudioinstaller.data.response.xsd.DeployDsXmlResponse;

public class T24ServerDeployProtocolFacade {

    // TODO Post POC, this code/class should be integrated with the IJremoteClient/AbstractJremoteClient/JremoteClient stuff from the basic-ui plug-in

    public static String sendOfsMessage(T24AgentConnection connection, String message) throws T24ServerException {
        T24Response response = connection.call("OFS.BULK.MANAGER", message, null, null);
        return response.getValue(1);
    }
    
    public static String sendOfsMessage(T24DesignStudioInstallerService connection, String message) throws T24ServerException {
    	if (connection.getDesignStudioConnPort() == null) {
			throw new T24ServerException("DesignStudioInstaller client is not instantiated, Aborting Request!");
		}
		try {
			DeployDsXmlResponse response = connection.getDesignStudioConnPort().deployDsXml(connection.getUserDetails(), message);
			String returnValue = response.getResponseDetails().getValue().getReturnCode().getValue();
			if (returnValue.equals("SUCCESS")) {
				if (response.getT24Response().getValue().getT24Response() != null && !response.getT24Response().getValue().getT24Response().isEmpty()) {
					return response.getT24Response().getValue().getT24Response().get(0);
				} else
					return returnValue;
			} else if (returnValue.equals("FAILURE")) {
				if (response.getT24Response().getValue().getT24Response() != null && !response.getT24Response().getValue().getT24Response().isEmpty()) {
					return response.getT24Response().getValue().getT24Response().get(0);
				} else {
					return returnValue;
				}
			} else {
				return "";
			}
		} catch (Exception e) {
			throw new T24ServerException(e);
		}
    }
}
