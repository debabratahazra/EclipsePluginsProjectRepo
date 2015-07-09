package com.temenos.ds.t24.data.rps.connection;

/**
 * Connection properties to T24.
 *
 * @author Michael Vorburger
 */
public class T24ConnectionParameters {

//	public final String username;
//	public final String password;
//	public final String companyCode;

	public final String ofsSource;

	public T24ConnectionParameters(/*String username, String password, String companyCode, */ String ofsSource) {
		super();
//		this.username = username;
//		this.password = password;
//		this.companyCode = companyCode;
		this.ofsSource = ofsSource;
	}

}
