package com.odcgroup.ocs.server.external.builder.internal.mapping;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.odcgroup.ocs.server.ServerCore;
import com.odcgroup.ocs.server.external.OcsServerExternalCore;

/**
 * Helper to prepare deployment/undeployment (including jar backup, destination folder creation, ...)
 * @author yan
 */
public class PrepareDeploymentHelper {

	private static Logger logger = LoggerFactory.getLogger(PrepareDeploymentHelper.class);
	
	public static final String ORIGINAL_EXTENSION = ".original";

	/**
	 * @param destination
	 * @return <code>true</code> if the destination is not a file (i.e. a jar),
	 * <code>false</code> otherwise
	 */
	public static boolean needsPrepareDeployment(String destination) {
		File destinationFile = new File(destination);
		if (destinationFile.exists() && destinationFile.isFile()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Backup a jar destination with a suffix (ORIGINAL_EXTENSION).
	 * (If a file with the same suffix exists, delete it)
	 * @param destination
	 * @return a status about the success or failure of the operation.
	 */
	public static IStatus backupJar(String destination) {
		File jarFile = new File(destination);
		File originalJarFile = new File(destination + ORIGINAL_EXTENSION);
		
		if (jarFile.exists() && !jarFile.isDirectory()) {
			if (originalJarFile.exists()) {
				if (!originalJarFile.delete()) {
					return new Status(
							IStatus.ERROR,
							OcsServerExternalCore.PLUGIN_ID,
							"Unable to replace " + originalJarFile.toString());
				}
			}
			if (!jarFile.renameTo(originalJarFile)) {
				return new Status(
						IStatus.ERROR,
						OcsServerExternalCore.PLUGIN_ID,
						"Unable to save the original jar to " + originalJarFile.toString());
			}
		}

		logger.info("Deploying: " + destination + "to server");
		return Status.OK_STATUS;
	}

	/**
	 * Backup the jar and create the same location as a folder
	 * @param destination
	 * @return a status about the success or failure of the operation.
	 */
	public static IStatus prepare(String destination) {
		IStatus status = backupJar(destination);
		if (status != Status.OK_STATUS) {
			return status;
		}

		File jarFile = new File(destination);
		if (!jarFile.exists() && !jarFile.mkdirs()) {
			return new Status(
					IStatus.ERROR,
					OcsServerExternalCore.PLUGIN_ID,
					"Unable to create a folder for " + jarFile.toString());
		}

		return Status.OK_STATUS;
	}

	/**
	 * Rename backup jar with with a suffix ORIGINAL_EXTENSION
	 * to its original name deleting any exiting files with the same name
	 * @param backupFile
	 * @return
	 */
	public static IStatus restoreBackupJar(File backupFile) {
		if (backupFile == null) {
			return createErrorStatus("Cannot restore null file");
		}

		String backupFilePath = backupFile.getAbsolutePath();
		String destinationName = StringUtils.remove(backupFilePath, ORIGINAL_EXTENSION);
		File destinationFile = new File(destinationName);

		if (!backupFile.exists() ) {
			if(!destinationFile.exists()) {
				return Status.OK_STATUS;
			}
			else {
				return createErrorStatus(backupFilePath + " does not exist.");
			}
		}

		if (!backupFilePath.endsWith(ORIGINAL_EXTENSION)) {
			return createErrorStatus("Cannot restore " + backupFilePath + " as it is not a backed up jar with .orginal file extension");
		}

		if (destinationFile.exists()) {
			try {
				//Could be a directory so need to force the delete to ensure removal
				FileUtils.forceDelete(destinationFile);
			} catch (IOException e1) {
				return createErrorStatus("Cannot restore " + backupFilePath + " as cannot delete existing jar " + destinationName);
			}
		}


		if (backupFile.renameTo(destinationFile)) {
			return Status.OK_STATUS;
		} else {
			return createErrorStatus("Cannot restore " + backupFile.getAbsolutePath() + " as already exists at location.");
		}
	}

	private static IStatus createErrorStatus(String message) {
		// TODO: give the user the workaround procedure (add and remove again after releasing file(s) locked)
		return new Status(
				IStatus.ERROR,
				ServerCore.PLUGIN_ID,
				message);
	}

}
