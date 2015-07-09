package com.odcgroup.integrationfwk.ui.common;

import integrationflowservicews.IntegrationFlowServiceWS;
import integrationlandscapeservicews.IntegrationLandscapeServiceWS;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

import javax.xml.namespace.QName;
import javax.xml.ws.WebServiceException;

import catalogservicews.CatalogServiceWS;

import com.jbase.jremote.DefaultJConnectionFactory;
import com.jbase.jremote.JRemoteException;
import com.odcgroup.integrationfwk.ui.TWSConsumerLog;
import com.odcgroup.integrationfwk.ui.projects.TWSConsumerProject;
import com.odcgroup.integrationfwk.ui.utils.TWSConsumerProjectUtil;

/**
 * Class which helps to validate the connection details given by user while
 * integration project creation/ preference change.
 * 
 * <p>
 * Note : Available methods in this class is just for verifying the connection.
 * 
 * @author sbharathraja
 * 
 */
public class ConnectionValidator {

	private boolean checkCredentials(String password, String userName) {
		if (password.equalsIgnoreCase("") || userName.equalsIgnoreCase("")) {
			return false;
		}
		return true;
	}

	/**
	 * Method which check the availability of agent connection using given
	 * values.
	 * 
	 * @param hostName
	 *            - host name for the agent connection
	 * @param portNumber
	 *            - port number for the agent connection
	 * @param ofsSource
	 *            - ofs source id for the agent connection
	 * @return true if valid agent connection, false otherwise.
	 */
	public boolean isValidAgentConnection(final TWSConsumerProject twsProject) {
		DefaultJConnectionFactory jConnectionFactory = new DefaultJConnectionFactory();
		jConnectionFactory.setHost(twsProject.getHost());
		jConnectionFactory.setPort(twsProject.getPort());
		Properties properties = new Properties();
		properties.put("env.OFS_SOURCE", twsProject.getOfsSource());
		try {
			jConnectionFactory.getConnection("", "", properties);
			return true;
		} catch (JRemoteException e) {
			return false;
		}
	}

	/**
	 * Method which helps to check the availability of catalog service web
	 * connection in the given url.
	 * 
	 * @param componentServiceURL
	 *            - url for the component service.
	 * @return true if valid url, false otherwise.
	 */
	public boolean isValidCatalogServiceURL(String componentServiceURL) {
		try {
			URL wsdlUrl = new URL(TWSConsumerProjectUtil.getCatalogServiceURL(componentServiceURL));
			new CatalogServiceWS(wsdlUrl, new QName("http://CatalogServiceWS", "CatalogServiceWS"));
			return true;
		} catch (MalformedURLException e) {
			TWSConsumerLog.logError("URL is not well formatted", e);
			return false;
		} catch (WebServiceException we) {
			TWSConsumerLog.logError("Invalid URL", we);
			return false;
		} catch (Exception e) {
			TWSConsumerLog.logError(e);
			return false;
		}
	}

	/**
	 * Method which checks the availability of flowservice web connection in the
	 * given url.
	 * 
	 * @param componentServiceURL
	 *            - url for component service web connection
	 * @return true if valid url, false otherwise.
	 */
	public boolean isValidFlowServiceURL(String componentServiceURL) {
		try {
			URL wsdlUrl = new URL(
					TWSConsumerProjectUtil
							.getFlowServiceURL(componentServiceURL));
			new IntegrationFlowServiceWS(wsdlUrl, new QName(
					"http://IntegrationFlowServiceWS",
					"IntegrationFlowServiceWS"));
			return true;
		} catch (MalformedURLException e) {
			TWSConsumerLog.logError("URL is not well formatted", e);
			return false;
		} catch (WebServiceException we) {
			TWSConsumerLog.logError("Invalid URL", we);
			return false;
		} catch (Exception e) {
			TWSConsumerLog.logError(e);
			return false;
		}
	}

	/**
	 * Method which checks the availability of landscape web service connection
	 * in the given url.
	 * 
	 * @param componentServiceURL
	 *            - url for component service web connection.
	 * @param password
	 *            - T24 sign-on password.
	 * @param userName
	 *            - T24 sign-on name.
	 * @param consumerProject
	 *            - project instance.
	 * @return true if valid url, false otherwise.
	 */
	public boolean isValidLandScapeURL(String componentServiceURL,
			final String password, final String userName,
			final TWSConsumerProject consumerProject) {
		try {
			URL wsdlUrl = new URL(
					TWSConsumerProjectUtil
							.getLandscapeServiceURL(componentServiceURL));
			new IntegrationLandscapeServiceWS(wsdlUrl, new QName(
					"http://IntegrationLandscapeServiceWS",
					"IntegrationLandscapeServiceWS"));
			if (!checkCredentials(password, userName)) {
				return false;
			}
			return true;
		} catch (MalformedURLException e) {
			TWSConsumerLog.logError("URL is not well formatted", e);
			return false;
		} catch (WebServiceException we) {
			TWSConsumerLog.logError("Invalid URL", we);
			return false;
		} catch (Exception e) {
			TWSConsumerLog.logError(e);
			return false;
		}
	}

}
