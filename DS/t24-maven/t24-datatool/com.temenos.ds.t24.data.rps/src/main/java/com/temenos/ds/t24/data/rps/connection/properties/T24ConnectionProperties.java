package com.temenos.ds.t24.data.rps.connection.properties;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.io.IOUtils;

import com.temenos.ds.t24.data.rps.RemoteServiceException;

/**
 * Wrapper around java.util.Properties with strongly typed getters.
 *
 * @author Michael Vorburger
 */
public class T24ConnectionProperties {

	// These property name intentionally match (a subset of) those
	// in designstudio/components/t24server/core/com.odcgroup.t24.server.external/src/main/java/com/odcgroup/t24/server/external/util/ServerPropertiesHelper.java
	// This is intentional, so that server.properties files
	// can be shared between DS and this tool.
	static final String HOST_KEY         = "host";
	static final String PROTOCOL_KEY     = "protocol";
	static final String AGENT_PORT_KEY   = "agent.port";
	static final String WS_PORT_KEY      = "ws.port";
	static final String USERNAME_KEY     = "username";
	static final String PASSWORD_KEY     = "password";
	static final String T24USERNAME_KEY  = "t24username";
	static final String T24PASSWORD_KEY  = "t24password";
	static final String BRANCH_KEY       = "branch";
	// These properties are not (currently) in DS:
	private static final String OFS_SOURCE_DEFAULT = "IRISPA";
	static final String OFS_SOURCE       = "ofs.source";
	static final String WS_URL           = "ws.url";

	public static enum T24ConnectionProtocol {
		ws, jAgent;
	}

	public static T24ConnectionProperties from(Properties p) {
		return new T24ConnectionProperties(p);
	}

	public static T24ConnectionProperties fromClasspath(String classpathResource) throws RemoteServiceException {
		InputStream is = T24ConnectionProperties.class.getResourceAsStream(classpathResource);
		if (is == null)
			throw new RemoteServiceException("Classpath resource not found: " + classpathResource);
		try {
			Properties properties = new Properties();
			properties.load(is);
			return from(properties);
		} catch (IOException e) {
			throw new RemoteServiceException("Unable to read properties from " + classpathResource, e);
		} finally {
			IOUtils.closeQuietly(is);
		}
	}

	public final T24ConnectionProtocol protocol;
	public final String hostname;
	public final Integer jAgentPort;
	public final Integer wsPort;
	public final String username;
	public final String password;
	public final String t24username;
	public final String t24password;
	public final String branch;
	public final String ofsSource;
	public final String wsURL;

	protected T24ConnectionProperties(Properties p) {
		// Note the intentional use of AGENT as a default OFS_SOURCE here here (so that existing DS server.properties are compatible)
		this.protocol = safeEnumValueOf(T24ConnectionProtocol.class, p.getProperty(PROTOCOL_KEY, T24ConnectionProtocol.jAgent.name()));
		this.hostname = p.getProperty(HOST_KEY);
		this.jAgentPort = safeParseInt(p.getProperty(AGENT_PORT_KEY));
		this.wsPort = safeParseInt(p.getProperty(WS_PORT_KEY));
		this.username = p.getProperty(USERNAME_KEY);
		this.password = p.getProperty(PASSWORD_KEY);
		this.t24username = p.getProperty(T24USERNAME_KEY);
		this.t24password = p.getProperty(T24PASSWORD_KEY);
		this.branch = p.getProperty(BRANCH_KEY);
		// Note the intentional use of a default OFS_SOURCE here here (so that existing DS server.properties are compatible)
		this.ofsSource = p.getProperty(OFS_SOURCE, OFS_SOURCE_DEFAULT);
		this.wsURL = p.getProperty(WS_URL);
	}

	private Integer safeParseInt(String value) {
		if (value == null)
			return null;
		return Integer.parseInt(value);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected <T extends Enum> T safeEnumValueOf(Class<T> enumClass, String value) {
		if (value == null)
			return null;
		else
			return (T) Enum.valueOf(enumClass, value);
	}

}
