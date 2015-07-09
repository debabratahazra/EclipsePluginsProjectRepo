package com.odcgroup.cdm.generation.dataset;

import com.odcgroup.cdm.generation.CdmGenerationCore;
import com.odcgroup.mdf.generation.OAWGenerator;

/**
 * Code generator for CDM dataset implementations.
 * 
 * @author flm
 */
public class CdmDatasetBeanGenerator extends OAWGenerator {

    /**
     * Creates a new CdmDatasetBeanGenerator.
     */
    public CdmDatasetBeanGenerator() {
	super("com/odcgroup/cdm/generation/dataset/bean/dataset_bean.oaw");
    }

    @Override
    protected String getPluginId() {
    	return CdmGenerationCore.PLUGIN_ID;
    }
}
