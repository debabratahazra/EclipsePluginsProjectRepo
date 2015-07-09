package com.temenos.ds.t24.data.rps.connection.properties;

import java.util.Arrays;

import com.temenos.ds.t24.data.rps.connection.T24ConnectionParameters;
import com.temenos.ds.t24.data.rps.connection.T24JAgentConnectionParameters;
import com.temenos.ds.t24.data.rps.connection.T24WSConnectionParameters;
import com.temenos.ds.t24.data.rps.connection.properties.T24ConnectionProperties.T24ConnectionProtocol;


/**
 * Factory for T24ConnectionProperties, from *.properties files.
 *
 * @author Michael Vorburger
 */
public class T24ConnectionParametersFactory {

	public static T24ConnectionParameters from(T24ConnectionProperties props) {
		if (props.protocol == null)
			handleUnknownProtocol(props.protocol);

		switch (props.protocol) {
		case jAgent: {
			if (props.hostname == null)
				throw new IllegalArgumentException("Missing property: " + T24ConnectionProperties.HOST_KEY);
			if (props.jAgentPort == null)
				throw new IllegalArgumentException("Missing property: " + T24ConnectionProperties.AGENT_PORT_KEY);
			if (props.ofsSource == null)
				throw new IllegalArgumentException("Missing property: " + T24ConnectionProperties.OFS_SOURCE);
			return new T24JAgentConnectionParameters(props.hostname, props.jAgentPort, props.ofsSource);
		}

		case ws:
			if (props.wsURL != null) {
				if (props.ofsSource == null)
					throw new IllegalArgumentException("Missing property: " + T24ConnectionProperties.OFS_SOURCE);
				return new T24WSConnectionParameters(props.wsURL, props.ofsSource);
			} else {
				if (props.hostname == null)
					throw new IllegalArgumentException("Missing property: " + T24ConnectionProperties.HOST_KEY);
				if (props.wsPort == null)
					throw new IllegalArgumentException("Missing property: " + T24ConnectionProperties.WS_PORT_KEY);
				if (props.ofsSource == null)
					throw new IllegalArgumentException("Missing property: " + T24ConnectionProperties.OFS_SOURCE);
				return new T24WSConnectionParameters(props.hostname, props.wsPort, props.ofsSource);
			}

		default:
			handleUnknownProtocol(props.protocol);
			return null; // Compiler trick - this will (intentionally) never be reached
		}
	}

	private static void handleUnknownProtocol(T24ConnectionProtocol protocol) throws IllegalArgumentException {
		throw new IllegalArgumentException("Unknown T24 connection protocol: " + protocol + "; allowed: " + Arrays.toString(T24ConnectionProperties.T24ConnectionProtocol.values()));
	}

}
