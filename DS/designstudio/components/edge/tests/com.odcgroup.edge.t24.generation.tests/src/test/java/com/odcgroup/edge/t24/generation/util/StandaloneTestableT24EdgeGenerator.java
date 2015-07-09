package com.odcgroup.edge.t24.generation.util;

import com.odcgroup.edge.t24.generation.T24EdgeGenerator;

/**
 * T24EdgeGenerator which can work in a standalone (non-OSGi) test, and maybe
 * later standalone generator, environment.
 * 
 * @author Michael Vorburger
 */
public class StandaloneTestableT24EdgeGenerator extends T24EdgeGenerator {
	@Override
	protected String getProjectLocation(String projectName) {
		T24EdgeGenerator.s_forceGeneration = true;
		return "target/StandaloneTestableT24EdgeGenerator";
	}
}
