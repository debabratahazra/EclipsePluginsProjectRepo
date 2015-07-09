package com.odcgroup.t24.server.external.util;

import com.odcgroup.t24.server.external.model.internal.T24ServerException;


public class LicenseValidator {

	public static void checkValidLicense(String statusString) throws T24ServerException {
		 if(statusString !=null && statusString.contains("##NOLIC")) { 
			 throw new T24ServerException("Design Studio (DS) license invalid in T24 ,please update license with a valid key obtained from distribution.");
		 }
		
	}

}
