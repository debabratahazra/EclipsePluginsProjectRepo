package com.odcgroup.cdm.generation.dataset;

import com.odcgroup.cdm.generation.CdmGenerationCore;
import com.odcgroup.mdf.generation.OAWGenerator;

/**
 * Code generator for CDM dataset implementations.
 * 
 * @author flm
 */
public class CdmDatasetActionGenerator extends OAWGenerator {

    /**
     * Creates a new CdmDatasetBeanGenerator.
     */
    public CdmDatasetActionGenerator() {
    	super("com/odcgroup/cdm/generation/dataset/actions/actions.oaw");
    }
    
    @Override
    protected String getPluginId() {
    	return CdmGenerationCore.PLUGIN_ID;
    }
}
