package com.odcgroup.mdf.entity.generator;

import com.odcgroup.mdf.generation.OAWGenerator;

/**
 * Generate the dataset static interfaces of dyna beans
 * @author yan
 */
public class DatasetCodeGenerator extends OAWGenerator {

	private static String WORKFLOW_NAME = "com/odcgroup/mdf/entity/generator/mdf-entity-dataset.oaw";

	public DatasetCodeGenerator() {
		super(WORKFLOW_NAME);
	}

    @Override
    protected String getPluginId() {
    	return MdfEntityGeneratorPlugin.PLUGIN_ID;
    }

}
