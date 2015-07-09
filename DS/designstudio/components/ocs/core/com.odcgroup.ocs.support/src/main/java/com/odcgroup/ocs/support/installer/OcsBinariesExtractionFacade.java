package com.odcgroup.ocs.support.installer;

import java.io.File;
import java.io.FilenameFilter;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.eclipse.core.runtime.Platform;

/**
 * @author yan
 */
public class OcsBinariesExtractionFacade {

	/** Extraction folder prefix */
	protected static final String OCS_EXTRACT_PREFIX = "ocs.extract.";
	
	/** Subfolder where the repo is extracted be the OCS installation tool */
	protected static final String OCS_EXTRACT_REPO_FOLDER = "repo";
	
	public static final String ORIGINAL_WUI_BLOCKS = "originalWUIBlocks";

	/** Date formatter for the extraction location */
	protected static final SimpleDateFormat EXTRACT_FORMAT = new SimpleDateFormat("yyyyMMdd.HHmmss");

	/** Singleton */
	public static OcsBinariesExtractionFacade INSTANCE;
	public static OcsBinariesExtractionFacade instance() {
		if (INSTANCE == null) {
			INSTANCE = new OcsBinariesExtractionFacade();
		}
		return INSTANCE;
	}
	
	/** Filter used to find all the extractions */
	public static final FilenameFilter FILTER = new FilenameFilter() {
		public boolean accept(File dir, String name) {
			return new File(dir, name).isDirectory() &&
				name.startsWith(OCS_EXTRACT_PREFIX);
		}
	};	

	/**
	 * @return the folder where the OCS binaries are extracted
	 */
	public File getOcsBinariesFolder() {
		return new File(Platform.getInstallLocation().getURL().getPath() + "ocs-binaries");
	}
	
	/**
	 * @return the last extraction folder, 
	 * or <code>null</code> if not found. 
	 */
	public File getOcsBinariesExtractFolder() {
		if (getOcsBinariesFolder().exists()) {
			File lastRepo = null;
			for (File repo : getOcsBinariesExtractions()) {
				if (lastRepo == null) {
					lastRepo = repo;
				} else {
					if (lastRepo.getName().compareTo(repo.getName()) < 0) {
						lastRepo = repo;
					}
				}
			}
			return lastRepo;
		}
		return null;
	}

	/**
	 * @return the files (directories) that are in the extraction 
	 * folder that are repositories extraction.
	 */
	public File[] getOcsBinariesExtractions() {
		return getOcsBinariesFolder().listFiles(FILTER);
	}
	
	/**
	 * @return the repository folder in the last extraction folder, 
	 * or <code>null</code> if not found. 
	 */
	public File getOcsBinariesRepositoryFolder() {
		File extract = getOcsBinariesExtractFolder();
		if (extract != null) {
			return new File(extract, OCS_EXTRACT_REPO_FOLDER);
		}
		return null;
	}

	/**
	 * @return the original WUI blocks folder in the last extraction folder, 
	 * or <code>null</code> if not found. 
	 */
	public File getOcsBinariesOriginalWuiBlocksFolder() {
		File extract = getOcsBinariesExtractFolder();
		if (extract != null) {
			return new File(extract, ORIGINAL_WUI_BLOCKS);
		}
		return null;
	}

	/**
	 * @return the extraction folder for a new extraction
	 */
	public File generateNewBinariesDestination() {
		String extractDir = OCS_EXTRACT_PREFIX + EXTRACT_FORMAT.format(new Date());
		return new File(getOcsBinariesFolder(), extractDir);
	}
	
}
