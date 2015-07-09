package com.temenos.ds.t24.data.rps.connection;

/**
 * Connection properties to T24.
 *
 * @author Michael Vorburger
 */
public class T24JAgentConnectionParameters extends T24ConnectionParameters {

	public final String jAgentHostname;
	public final int jAgentPort;

	public T24JAgentConnectionParameters(String jAgentHostname, int jAgentPort, /* String username, String password, String companyCode, */ String ofsSource) {
		super(/* username, password, companyCode, */ ofsSource);
		this.jAgentHostname = jAgentHostname;
		this.jAgentPort = jAgentPort;
	}

}
