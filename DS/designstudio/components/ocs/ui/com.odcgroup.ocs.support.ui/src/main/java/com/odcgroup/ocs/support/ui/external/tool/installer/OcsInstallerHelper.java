package com.odcgroup.ocs.support.ui.external.tool.installer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;

import org.apache.commons.io.FilenameUtils;
import org.apache.maven.execution.MavenExecutionRequest;
import org.apache.maven.execution.MavenExecutionResult;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.m2e.core.MavenPlugin;
import org.eclipse.m2e.core.embedder.IMaven;
import org.eclipse.swt.widgets.Shell;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;

import com.odcgroup.ocs.support.external.tool.ExternalToolException;
import com.odcgroup.ocs.support.installer.OcsBinariesExtractionFacade;
import com.odcgroup.ocs.support.ui.OCSSupportUICore;
import com.odcgroup.ocs.support.ui.external.tool.ExternalToolUI;
import com.odcgroup.ocs.support.ui.external.tool.LogKeeper;
import com.odcgroup.ocs.support.ui.preferences.pages.EmbeddedServerPreferencePage;
import com.odcgroup.workbench.ui.maven.M2EclipseIntegrationFacade;

/**
 * Helper used to launch the OCS installer
 * @author yan
 */
public class OcsInstallerHelper {

	private static final String ORIGINAL_WUIBLOCKS_PROGRESS_TITLE = "Orignial Wuiblocks Extraction Progress Information";
	private static final String DOUBLE_QUOTES = "\"";
	private static final String ERROR_MESSAGE = "An error occured while extracting the Triple'A Plus binaires";
	private static final String ANT_INSTALL_PROPERTIES = "ant.install.properties";

	protected static final Logger logger = LoggerFactory.getLogger(OcsInstallerHelper.class);

	/**
	 * Execute the OCS binaries extraction.
	 * @param shell
	 * @param binariesDestination
	 * @param ocsDistributionFilepath
	 * @param hotfixesDir
	 * @param custoDir
	 * @return <code>true</code> if the return code of the script is 0,
	 * <code>false</code> otherwise.
	 */
	public static boolean extractOcsBinaries(Shell shell,
			String binariesDestination,
			String ocsDistributionFilepath,
			String hotfixesDir,
			String custoDir) {

		logger.debug("Extracting Triple'A Plus Binaries");
		removeExitingAntInstallProperties();

		StringBuilder javaArgsStringBuilder = new StringBuilder();
		javaArgsStringBuilder.append(addDefaultJavaArgs(binariesDestination, ocsDistributionFilepath));
		javaArgsStringBuilder.append(addHotfixesDirectoryJavaArg(hotfixesDir));
		javaArgsStringBuilder.append(addCustoDirectoryJavaArg(custoDir));
		javaArgsStringBuilder.append(addInstallerJavaArgs(ocsDistributionFilepath));
		javaArgsStringBuilder.append(" text-auto");
		final String javaArgs = javaArgsStringBuilder.toString();

		try {
			logger.debug("Executing:\n" + javaArgs);

			String ocsDistributionFileName = FilenameUtils.getName(ocsDistributionFilepath);
			LogKeeper logKeeper = new LogKeeper();
			int returnCode = ExternalToolUI.executeJavaWithProgressBar(
					shell,
					"Extracting Triple'A Plus binaries from " + ocsDistributionFileName,
					javaArgs,
					logKeeper);
			if (returnCode != 0) {
				OCSSupportUICore.getDefault().logError("Could not extract the ocs ditribution from " + ocsDistributionFileName + "\n" +
						"Last log entries:\n" + logKeeper.getLastLogEntries(), null);
				displayError(shell, "Failed to execute extraction.",
						"Unable to extract Triple'A Plus binaries from the selected distribution. ",
						"Could not extract the ocs ditribution from " + ocsDistributionFileName,
						"Command line called:\n" + javaArgs + "\n\n" +
						"Last log entries:\n" + logKeeper.getLastLogEntries());
				return false;
			}

			logger.debug("Return code:" + returnCode +", trying to run " + javaArgs);

			copySettingsXml(new File(binariesDestination));

			extractOriginalWuiblocks(shell);
			
			return true;

		} catch (ExternalToolException e) {
			logger.error("Error during Triple'A Plus binaries extraction", e);
			displayError(shell, "Unable to extract Triple'A Plus binaries from "+ ocsDistributionFilepath, ERROR_MESSAGE, "", e);
			return false;
		} catch (InterruptedException e) {
			logger.error("Triple'A Plus binaries extraction interrupted", e);
			return false;
		} catch (IOException e) {
			logger.error("Unable to copy the settings.xml file from the extraction", e);
			displayError(shell, "Unable to extract Triple'A Plus binaries", ERROR_MESSAGE, "Could not copy the settings.xml file from " + binariesDestination, e);
			return false;
		} finally {
			removeExitingAntInstallProperties();
		}
	}

	private static void extractOriginalWuiblocks(Shell shell) throws ExternalToolException, InterruptedException {
		
		final ProgressMonitorDialog progressDialog = new ProgressMonitorDialog(shell){
			@Override
			protected void configureShell(Shell shell) {
				super.configureShell(shell);
				shell.setText(ORIGINAL_WUIBLOCKS_PROGRESS_TITLE);
			}
			
		};
		IRunnableWithProgress runnable = new IRunnableWithProgress() {
			public void run(final IProgressMonitor progressMonitor) throws InvocationTargetException,InterruptedException {
				progressMonitor.beginTask("Extracting " + OcsBinariesExtractionFacade.ORIGINAL_WUI_BLOCKS, 100);
				
				AppenderBase<ILoggingEvent> listener = M2EclipseLoggingHelper.addListenerToMavenExecution(progressMonitor);
				try {
					IMaven maven = MavenPlugin.getMaven();
					MavenExecutionRequest request = maven.createExecutionRequest(progressMonitor);

					List<String> paramList = new ArrayList<String>();
					
					paramList.add("repository:extract-wuiblocks");
					request.setGoals(paramList);

					Properties props = new Properties();
					props.put("original.wuiblocks.extraction.show.progress", "true");
					props.put("original.wuiblocks.extraction.folder", OcsBinariesExtractionFacade.instance().getOcsBinariesOriginalWuiBlocksFolder().toString());
					request.setSystemProperties(props);

					request.setProjectPresent(false);
					
					request.setLocalRepository(null);
					request.setLocalRepositoryPath(OcsBinariesExtractionFacade.instance().getOcsBinariesRepositoryFolder());
					request.setGlobalSettingsFile(new File(OcsBinariesExtractionFacade.instance().getOcsBinariesRepositoryFolder(), "..\\opt\\maven\\conf\\settings.xml"));

					MavenExecutionResult result = maven.execute(request, progressMonitor);
					
					List<Throwable> exceptions = result.getExceptions(); 
					if (exceptions.size() > 0) {
						for (Throwable e:exceptions) {
							logger.error(e.getMessage(), e);
						}
						throw new InvocationTargetException(exceptions.get(0));		
					}
				} catch (CoreException e) {
					throw new InvocationTargetException(e);
				} finally {
					M2EclipseLoggingHelper.removeListenerToMavenExecution(listener);
				}
				progressMonitor.done();
			}

		};
		
		try {
			progressDialog.run(true, false, runnable);
		} catch (InvocationTargetException e) {
			if (e.getCause() instanceof ExternalToolException) {
				throw (ExternalToolException)e.getCause();
			} else {
				throw new ExternalToolException("Unexpected execution problem", e);
			}
		} catch (InterruptedException e) {
			throw e;
		} catch (Exception e) {
			throw new ExternalToolException("Unexpected execution problem", e);
		}
	}

	/**
	 *
	 */
	private static void removeExitingAntInstallProperties() {
		logger.debug("Remove (if existing):" + new File(ANT_INSTALL_PROPERTIES).getAbsolutePath());
		if (!new File(ANT_INSTALL_PROPERTIES).delete()) {
			logger.error("Unable to delete " + ANT_INSTALL_PROPERTIES);
		}
	}

	private static String addInstallerJavaArgs(String ocsDistributionFilepath) {
		String ocsDistributionParentFilepath = FilenameUtils.getFullPathNoEndSeparator(ocsDistributionFilepath);
		return " -jar \"" + ocsDistributionParentFilepath + "\\" + EmbeddedServerPreferencePage.OCS_INSTALLER + DOUBLE_QUOTES;
	}

	private static String addDefaultJavaArgs(String binariesDestination,
			String ocsDistributionFilepath) {
		return DOUBLE_QUOTES +
			   "-Dinstall.oams.home=" + binariesDestination +
			   DOUBLE_QUOTES +
			   " -Dinstaller.launch.db-dpi=false" +
			   " -Dinstaller.launch.as-dpi=false" +
			   " -Dinstaller.repo.show.progress=true" +
			   " " +
			   DOUBLE_QUOTES +
			   "-Dinstaller.release.file=" + ocsDistributionFilepath +
			   DOUBLE_QUOTES;
	}

	private static String addCustoDirectoryJavaArg(String custoDir) {
		String custoDirectoryString = "";
		if (!custoDir.trim().isEmpty()) {
			custoDirectoryString = " " + DOUBLE_QUOTES + "-Dinstaller.custo.folder=" + custoDir + DOUBLE_QUOTES;
		}
		return custoDirectoryString;
	}

	private static String addHotfixesDirectoryJavaArg(String hotfixesDir) {
		String hotFixesString = "";
		if (!hotfixesDir.trim().isEmpty()) {
			hotFixesString = " " + DOUBLE_QUOTES + "-Dinstaller.hotfixes.folder=" + hotfixesDir + DOUBLE_QUOTES;
		}
		return hotFixesString;
	}

	/**
	 * Copy the settings.xml file produced during the extraction
	 * @param binariesDestination
	 * @return
	 * @throws IOException
	 */
	private static void copySettingsXml(File binariesDestination) throws IOException {
		logger.debug("copy the settings.xml from:" + binariesDestination);
		if (!binariesDestination.exists()) {
			throw new IOException("Source settings doesn't exist:" + binariesDestination.getAbsolutePath().toString());
		}
		logger.debug(" to: " + M2EclipseIntegrationFacade.instance().getSettingsXmlFile());
		File source = new File(binariesDestination, "opt/maven/conf/settings.xml");
		copyFileNIO(source, M2EclipseIntegrationFacade.instance().getSettingsXmlFile());
	}

	/**
	 * Copy a file using java.nio
	 */
	private static void copyFileNIO(File sourceFile, File targetFile)
			throws IOException {
		String fileName = targetFile.getPath();
		String path = fileName.substring(0, fileName.lastIndexOf(File.separatorChar));
		if (path.length() != 0 && new File(path).mkdirs()) {
			throw new IOException("The path couldn't be created: " + path);
		}

		if (!targetFile.exists() && targetFile.createNewFile()) {
			throw new IOException("Unable to create " + targetFile.getAbsolutePath());
		}

		FileChannel source = null;
		FileChannel destination = null;
		try {
			source = new FileInputStream(sourceFile).getChannel();
			destination = new FileOutputStream(targetFile).getChannel();
			destination.transferFrom(source, 0, source.size());
		} finally {
			if (source != null) {
				source.close();
			}
			if (destination != null) {
				destination.close();
			}
		}
	}

	/**
	 * Display an error with the appropriate title and error message.
	 * @param shell
	 * @param dialogTitle
	 * @param errorMessage
	 * @param statusMessage
	 * @param e
	 */
	private static void displayError(final Shell shell, String dialogTitle, String errorMessage, String statusMessage, Exception e) {
		ErrorDialog.openError(
				shell,
				dialogTitle,
				errorMessage,
				new Status(IStatus.ERROR, OCSSupportUICore.PLUGIN_ID, statusMessage, e));
	}

	/**
	 * Display an error with the appropriate title and error message.
	 * @param shell
	 * @param dialogTitle
	 * @param errorMessage
	 * @param statusMessage
	 */
	private static void displayError(final Shell shell, String dialogTitle, String errorMessage, String statusMessage, String details) {
		MultiStatus multiStatus = new MultiStatus(OCSSupportUICore.PLUGIN_ID, 0, statusMessage, null);
		StringTokenizer st = new StringTokenizer(details, "\n");
		while (st.hasMoreTokens()) {
			String line = st.nextToken();
			multiStatus.add(new Status(IStatus.ERROR, OCSSupportUICore.PLUGIN_ID, line));
		}

		ErrorDialog.openError(shell, dialogTitle, errorMessage, multiStatus);
	}

}
