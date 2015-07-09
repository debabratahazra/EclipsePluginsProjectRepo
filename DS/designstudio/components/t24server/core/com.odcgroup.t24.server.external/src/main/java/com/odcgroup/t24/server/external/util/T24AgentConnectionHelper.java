package com.odcgroup.t24.server.external.util;

import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jbase.jremote.DefaultJConnectionFactory;
import com.jbase.jremote.JConnection;
import com.jbase.jremote.JDynArray;
import com.jbase.jremote.JRemoteException;
import com.jbase.jremote.JSubroutineParameters;
import com.odcgroup.t24.server.external.model.internal.T24ServerException;

public class T24AgentConnectionHelper {

	static private final Logger logger = LoggerFactory.getLogger(T24AgentConnectionHelper.class);

    public static T24AgentConnection getT24AgentConnection(String host, String port , String uid, String pwd) throws T24ServerException {
        DefaultJConnectionFactory jConnectionFactory = new DefaultJConnectionFactory();
        if (isPropertyNotEmpty(host)) {
			jConnectionFactory.setHost(host);
		}
        else {
			throw new T24ServerException("Unable to connect - host ip missing");
		}
		try {
			if (isPropertyNotEmpty(port)) {
				jConnectionFactory.setPort(Integer.valueOf(port));
			}
			else {
				throw new T24ServerException("Unable to connect - agent port number missing");
			}
        } catch (NumberFormatException e) {
			throw new T24ServerException("Unable to connect to " + host + ":" + port + " for the user \"" + uid + "\" (invalid port number)", e);
        }
        
        Properties properties = new Properties();
        // NOTE: DS.TELNET is the "official" 'OFS source ID for Design Studio ("like SEAT is for SEAT, STP.OFS is for our STP")
        // This is usually created & correctly configured in T24 by the some IN.RTN during the Design Studio "Feature Pack" installation.
        // @see http://rd.oams.com/browse/DS-6023 for more background
        properties.put("env.OFS_SOURCE", "DS.TELNET"); 
        JConnection jConnection;
		try {
			jConnection = jConnectionFactory.getConnection(uid, pwd, properties);
		} catch (Exception e) {
			String message = "Unable to connect to " + host + ":" + port + " for the user \"" + uid + "\"";
			logger.error(message, e);
			throw new T24ServerException(message, e);
		}
        
        // for initialize the connection the below hard-coded subroutine
        // needs to be call, meanwhile we may not require any subroutine
        // parameters, hence the second arg passed as null.
        try {
			jConnection.call("JF.INITIALISE.CONNECTION", null);
		} catch (JRemoteException e) {
			final String msg = "Unable to initialize the connection (it connected, so host/port/uid/pwd is fine; but then JF.INITIALISE.CONNECTION failed; may be the Design Studio Feature Pack is not correctly installed in T24, and so the DS.TELNET OFS_SOURCE is not configured?)";
			logger.error(msg, e);
			throw new T24ServerException(msg, e);
		}
        
        return new T24AgentConnection(jConnection);
    }
    
    private static boolean isPropertyNotEmpty(String propValue){
		String propertyValue  = StringUtils.trimToEmpty(propValue);
		return (propertyValue!=null && !propertyValue.isEmpty());
	}
    
	public static void checkForValidLicense(JSubroutineParameters parameters) throws T24ServerException {
		if(parameters !=null && parameters.size()>0) {
	       	int size = parameters.size();
	        JDynArray jDyarry = parameters.get(size-1);
	        String statusString = jDyarry.toString();
	        LicenseValidator.checkValidLicense(statusString);
		}   
	}
	
}
