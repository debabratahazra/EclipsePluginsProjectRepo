package com.odcgroup.mdf.entity.generator;

import com.odcgroup.mdf.generation.OAWGenerator;

/**
 * Generate the bootstrap for mdf
 * @author yan
 */
public class BootstrapCodeGenerator extends OAWGenerator {

	private static String WORKFLOW_NAME = "com/odcgroup/mdf/entity/generator/mdf-entity-bootstrap.oaw";

	public BootstrapCodeGenerator() {
		super(WORKFLOW_NAME);
	}

    @Override
    protected String getPluginId() {
    	return MdfEntityGeneratorPlugin.PLUGIN_ID;
    }

}
