package com.odcgroup.iris.generation;

import java.io.File;

import org.eclipse.xtext.generator.IFileSystemAccess;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.odcgroup.workbench.core.helper.FeatureSwitches;

/**
 * This class provides utility functions for working with the Eclipse file system
 *
 * @author mlambert
 *
 */
public class FileSystemHelper {
	private static final Logger logger = LoggerFactory.getLogger(FileSystemHelper.class);
	/**
	 * Determines the Eclipse file system access abstraction to use for meta data config generation
	 */
	public IFileSystemAccess determineFsa(IFileSystemAccess fsa) {
		String irisConfigDirPath = FeatureSwitches.genIrisOutputDir.get();

		IFileSystemAccess result = fsa;

		if(irisConfigDirPath != null) {
			File irisConfigDir = new File(irisConfigDirPath);

			if(irisConfigDir.exists()) {
				result = new ExternalFileSystemAccess(irisConfigDir);
			} else {
				logger.warn("Override directory for IRIS config does not exist (" + irisConfigDirPath +
						") reverting to default location" );
			}
		}

		return result;
	}
}
