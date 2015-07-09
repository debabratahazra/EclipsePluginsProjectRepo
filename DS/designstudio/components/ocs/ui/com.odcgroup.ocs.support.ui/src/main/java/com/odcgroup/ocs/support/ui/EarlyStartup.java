package com.odcgroup.ocs.support.ui;

import org.eclipse.ui.IStartup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.odcgroup.ocs.support.ui.installer.OcsBinariesExtractionUIFacade;
import com.odcgroup.ocs.support.ui.internal.compiler.OCSSchemaResolver;

/**
 * During startup some clean up are made, as well as the synchronisation
 * of the m2eclipse configuration
 */
public class EarlyStartup implements IStartup {

	protected static final Logger logger = LoggerFactory.getLogger(EarlyStartup.class);

	public void earlyStartup() {
		// Clean up of old extraction(s)
		logger.info("Cleanup old extraction");
		OcsBinariesExtractionUIFacade.instance().cleanUpOldExtraction();
		
		if (OCSSchemaResolver.requiresOcsSchemasExtraction()) {
			logger.info("Extract Triple'A Plus schemas");
			OCSSchemaResolver.extractOcsSchemas();
		}
	}
}
