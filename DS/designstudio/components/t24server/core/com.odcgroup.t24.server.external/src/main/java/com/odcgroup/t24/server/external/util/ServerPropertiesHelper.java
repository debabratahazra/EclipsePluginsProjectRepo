package com.odcgroup.t24.server.external.util;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;


public class ServerPropertiesHelper {
	
	private static final String HOST_KEY          = "host";
	public static final String PROTOCOL_KEY      = "protocol";
	private static final String AGENT_PORT_KEY    = "agent.port";
	private static final String WS_PORT_KEY       = "ws.port";
	private static final String USERNAME_KEY      = "username";
	private static final String PASSWORD_KEY      = "password";
	private static final String T24USERNAME_KEY   = "t24username";
	private static final String T24PASSWORD_KEY   = "t24password";
	private static final String BRANCH_KEY        = "branch";
	private static final String DEPLOYED_PROJECTS_KEY = "deployed.projects";
	private static final String OFS_ID = "ofsid";
	private static final String STRICT_O_DATA = "strictodata";
	public static final String OSTYPE = "ostype";
	private static final String TAFC_HOME = "tafchome";
	public static final String DEFAULT_PROTOCOL = "sftp";
	public static final String DEFAULT_OSTYPE = "unix";
	public static final String T24_CATALOGSERVICE_VERSION   = "t24catalogserviceversion";
	public static final String DEFAULT_T24_CATALOGSERVICE_VERSION   = "2";
	public static final String DEFAULT_WS_PROTOCOL = "ws";
	
	public enum T24ConnectionProtocol {
		WS, AGENT
	}

	public static String getHost(Properties properties) {
		return getProperty(properties, HOST_KEY);
	}
	
	public static T24ConnectionProtocol getProtocol(Properties properties) {
		String protocol = getProperty(properties, PROTOCOL_KEY);
		if ("ws".equals(protocol)) {
			return T24ConnectionProtocol.WS;
		} else {
			return T24ConnectionProtocol.AGENT;
		}
	}

	public static String getAgentPort(Properties properties) {
		return getProperty(properties, AGENT_PORT_KEY);
	}

	public static String getWebServicePort(Properties properties) {
		return getProperty(properties, WS_PORT_KEY);
	}
	
	public static String getUsername(Properties properties) {
		return getProperty(properties, USERNAME_KEY);
	}

	public static String getPassword(Properties properties) {
		String password = getProperty(properties, PASSWORD_KEY);
		String pwd = new ServerPropertiesPasswordScrambler().decode(password);
		return pwd;
	}
	
	public static String getT24User(Properties properties) {
		return getProperty(properties, T24USERNAME_KEY);
	}

	public static String getT24Password(Properties properties) {
		String password = getProperty(properties, T24PASSWORD_KEY);
		String pwd = new ServerPropertiesPasswordScrambler().decode(password);
		return pwd;
	}
	
	public static String getBranch(Properties properties) {
		return getProperty(properties, BRANCH_KEY);
	}
	
	public static String getDeployedProjects(Properties properties) {
		return getProperty(properties, DEPLOYED_PROJECTS_KEY);
	}
	
	public static void setDeployedProjects(Properties properties, String deployedProjects) {
		properties.put(DEPLOYED_PROJECTS_KEY, deployedProjects);
	}
	
	public static String getOfsId(Properties properties) {
		return getProperty(properties, OFS_ID);
	}
	
	public static String getTafcHome(Properties properties) {
		return getProperty(properties, TAFC_HOME);
	}
	
	private static String getProperty(Properties properties, String key) {
		String value = properties.getProperty(key.toLowerCase());
		if (value != null) {
			return StringUtils.trimToEmpty(value);
		}
		value = properties.getProperty(key.toUpperCase());
		if (value != null) {
			return StringUtils.trimToEmpty(properties.getProperty(key.toUpperCase()));
		}
		return StringUtils.trimToEmpty(properties.getProperty(key));
	}
	
	public static String getPropertyOSType(Properties properties) {
		return getProperty(properties, OSTYPE);
	}
	
	public static List<String> getAllKeys() {
		return Arrays.asList(
				HOST_KEY,
				AGENT_PORT_KEY,
				USERNAME_KEY,
				PASSWORD_KEY,
				BRANCH_KEY,
				T24USERNAME_KEY,
				T24PASSWORD_KEY,
				DEPLOYED_PROJECTS_KEY,
				OFS_ID,
				TAFC_HOME,
				OSTYPE, 
				STRICT_O_DATA,
				PROTOCOL_KEY ,
				T24_CATALOGSERVICE_VERSION);
	}

	public static String getStrictOData(Properties properties) {
		return getProperty(properties, STRICT_O_DATA);
	}
	
	public static Integer getT24CatalogServiceVersion(Properties properties) {
		Integer versionValue = null;
		try {
			String catlogSeriveVerisonProperty = properties.getProperty(T24_CATALOGSERVICE_VERSION);
			if (catlogSeriveVerisonProperty == null) {
				return new Integer(1);
			}
			String catalogversion = getProperty(properties, T24_CATALOGSERVICE_VERSION);
			if (catalogversion != null) {
				versionValue = Integer.valueOf(catalogversion);
			}
		} catch (NumberFormatException e) {
			return null;
		}
		return versionValue;
	} 

	

}
