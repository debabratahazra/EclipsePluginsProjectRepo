package com.odcgroup.mdf.entity.generator;

import com.odcgroup.mdf.generation.OAWGenerator;

/**
 * Generate the enums for static interfaces of dyna beans
 * @author yan
 */
public class EnumCodeGenerator extends OAWGenerator {

	private static String WORKFLOW_NAME = "com/odcgroup/mdf/entity/generator/mdf-entity-enum.oaw";

	public EnumCodeGenerator() {
		super(WORKFLOW_NAME);
	}

    @Override
    protected String getPluginId() {
    	return MdfEntityGeneratorPlugin.PLUGIN_ID;
    }

}
