package com.odcgroup.mdf.entity.generator;

import com.odcgroup.mdf.generation.OAWGenerator;

/**
 * Generate the class static interfaces of dyna beans
 * @author yan
 */
public class ClassCodeGenerator extends OAWGenerator {

	private static String WORKFLOW_NAME = "com/odcgroup/mdf/entity/generator/mdf-entity-class.oaw";

	public ClassCodeGenerator() {
		super(WORKFLOW_NAME);
	}

    @Override
    protected String getPluginId() {
    	return MdfEntityGeneratorPlugin.PLUGIN_ID;
    }

}
