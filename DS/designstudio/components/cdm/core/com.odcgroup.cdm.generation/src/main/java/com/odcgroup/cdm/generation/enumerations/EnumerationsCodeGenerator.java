package com.odcgroup.cdm.generation.enumerations;

import com.odcgroup.cdm.generation.AbstractCdmCodeGenerator;
import com.odcgroup.mdf.metamodel.MdfDomain;

/**
 * Generates the custom fields for CDM.
 * 
 * @author Gary Hayes
 */
public class EnumerationsCodeGenerator extends AbstractCdmCodeGenerator {

	/**
	 * Generates the files.
	 * 
	 * @param domain The Mdf Domain
	 * @param outputFilePath The output file path
	 */
	protected void generateFiles(MdfDomain domain, String outputFilePath) {
		EnumerationsWriter2 writer = new EnumerationsWriter2(domain, outputFilePath);
		writer.write();
	}
}