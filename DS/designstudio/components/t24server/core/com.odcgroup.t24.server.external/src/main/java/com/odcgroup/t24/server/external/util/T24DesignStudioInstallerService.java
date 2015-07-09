package com.odcgroup.t24.server.external.util;

import java.net.URL;
import java.text.MessageFormat;

import javax.xml.namespace.QName;

import com.odcgroup.t24.server.external.model.internal.T24ServerException;
import com.temenos.soa.services.data.xsd.ObjectFactory;
import com.temenos.soa.services.data.xsd.T24UserDetails;

import designstudioinstallerservicews.DesignStudioInstallerServiceWS;
import designstudioinstallerservicews.DesignStudioInstallerServiceWSPortType;

public class T24DesignStudioInstallerService implements IT24Connection {

	private final String URL_PATTERN = "http://{0}:{1}/axis2/services/DesignStudioInstallerServiceWS?wsdl";
	private final QName QNAME = new QName("http://DesignStudioInstallerServiceWS", "DesignStudioInstallerServiceWS");
	private DesignStudioInstallerServiceWSPortType designStudioConnPort;
	private T24UserDetails userDetails;
	
	public T24DesignStudioInstallerService(String host, String port, String user,
			String password) throws T24ServerException {
		try {
			// Instantiate the web service client
			String url = MessageFormat.format(URL_PATTERN, host, port);
			DesignStudioInstallerServiceWS designStudioConnService = new DesignStudioInstallerServiceWS(new URL(url), QNAME);
			designStudioConnPort = designStudioConnService.getDesignStudioInstallerServiceWSHttpSoap11Endpoint();
		
			// Create an instance of soa common and create a user details object for accessing T24
			ObjectFactory objCommon = new ObjectFactory();
			userDetails = objCommon.createT24UserDetails();
			userDetails.setUser(objCommon.createT24UserDetailsUser(user));
			userDetails.setPassword(objCommon.createT24UserDetailsPassword(password));
		} catch (Exception e) {
			throw new T24ServerException("Failed to instantiate web service, see exception for more details. Error Message: " + e.getMessage(), e);
		}
	}

	public DesignStudioInstallerServiceWSPortType getDesignStudioConnPort() {
		return designStudioConnPort;
	}

	public T24UserDetails getUserDetails() {
		return userDetails;
	}

	@Override
	public void sendOfsMessage(String message) throws T24ServerException {
		String result = T24ServerDeployProtocolFacade.sendOfsMessage(this, message);
		if (!result.isEmpty() && result.length()>0 && !result.equals("SUCCESS")) {
			throw new T24ServerException(result);
		}
	}

	@Override
	public void close() throws T24ServerException {
		// Nothing to do with web services
	}

}
