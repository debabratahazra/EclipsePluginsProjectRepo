package com.odcgroup.cdm.generation.custo.generator;

import com.odcgroup.cdm.generation.AbstractCdmCodeGenerator;
import com.odcgroup.mdf.metamodel.MdfDomain;

/**
 * The code generator for Cdm Custom Fields.
 * 
 * @author Gary Hayes
 */
public class CustomFieldsCodeGenerator extends AbstractCdmCodeGenerator {

	/**
	 * Generates the files.
	 * 
	 * @param domain The Mdf Domain
	 * @param outputFilePath The output file path
	 */
	protected void generateFiles(MdfDomain domain, String outputFilePath) {
		CustomFieldsWriter writer = new CustomFieldsWriter(domain, outputFilePath);
		writer.write();
	}
}
