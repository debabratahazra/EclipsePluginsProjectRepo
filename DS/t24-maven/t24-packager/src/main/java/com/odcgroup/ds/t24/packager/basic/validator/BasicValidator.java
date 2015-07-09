package com.odcgroup.ds.t24.packager.basic.validator;

import org.apache.commons.lang3.StringUtils;

import com.odcgroup.ds.t24.packager.helper.T24EnvironmentConfiguration;


/**
 * Validate basic constraints
 */
public class BasicValidator {
	
	public void validateEnvironnment() throws BasicValidationException {
		if (StringUtils.isEmpty(T24EnvironmentConfiguration.getTafcHome())) {
			throw new BasicValidationException("No TAF/C environment available. (No TAFC_HOME environnement variable defined)");
		}
	}

}
