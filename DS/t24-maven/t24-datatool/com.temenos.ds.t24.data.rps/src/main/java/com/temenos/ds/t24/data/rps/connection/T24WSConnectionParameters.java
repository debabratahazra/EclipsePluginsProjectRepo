package com.temenos.ds.t24.data.rps.connection;

/**
 * Connection properties to T24.
 *
 * @author Michael Vorburger
 */
public class T24WSConnectionParameters extends T24ConnectionParameters {

	public final String wsURL;

	public T24WSConnectionParameters(String wsURL, /*String username, String password, String companyCode, */String ofsSource) {
		super(/* username, password, companyCode, */ ofsSource);
		this.wsURL = wsURL;
	}

	public T24WSConnectionParameters(String wsHostname, int wsPort, /* String username, String password, String companyCode, */ String ofsSource) {
		super(/*username, password, companyCode, */ ofsSource);
		this.wsURL = "http://" + wsHostname + ":" + wsPort + "/axis2/services/ResourceProviderServiceWS";
	}
}
