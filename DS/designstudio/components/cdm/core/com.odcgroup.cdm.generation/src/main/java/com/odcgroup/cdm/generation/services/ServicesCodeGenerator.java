package com.odcgroup.cdm.generation.services;

import com.odcgroup.cdm.generation.AbstractCdmCodeGenerator;
import com.odcgroup.mdf.metamodel.MdfDomain;

/**
 * Generates the cdmServices.xml file.
 * 
 * @author Frederic Le Maitre
 */
public class ServicesCodeGenerator extends AbstractCdmCodeGenerator {

    /**
     * {@inheritDoc}
     */
    protected void generateFiles(MdfDomain domain, String outputFilePath) {
	ServicesWriter writer = new ServicesWriter(domain, outputFilePath);
	writer.write();
    }

}
