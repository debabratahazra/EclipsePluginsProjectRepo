package com.odcgroup.t24.binaries.refresh;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.m2e.core.MavenPlugin;
import org.eclipse.osgi.service.datalocation.Location;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IStartup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class T24BinariesUpdate implements IStartup {

	private static final String T24_BINARIES_REFRESH_WINDOW_TITLE = "T24 Binaries Refresh";
	private static final String TMP = "tmp";
	private static final String T24_BINARIES = "t24-binaries";
	private static Logger logger = LoggerFactory.getLogger(T24BinariesUpdate.class);

	@Override
	public void earlyStartup() {
		File[] t24BinariesZips = null; 
		try {
			t24BinariesZips = getT24BinariesZips();
		} catch(URISyntaxException e) {
			logger.error("Unable to scan the eclipse platform installation location", e);
		} catch (UnsupportedEncodingException e) {
			logger.error("Unable to convert URL to URI", e);
		}
		
		if (t24BinariesZips == null || t24BinariesZips.length == 0) {
			return; // No t24-binaries zip, then nothing to do
		}
		
		if (t24BinariesZips.length == 1) {
			final File t24BinariesFile = t24BinariesZips[0];
			Display.getDefault().asyncExec(new Runnable() {
				@Override
				public void run() {
					if (MessageDialog.openQuestion(Display.getCurrent().getActiveShell(), T24_BINARIES_REFRESH_WINDOW_TITLE,
							"The following file has been found in the installation folder:\n" + 
							t24BinariesFile.getName() + "\n\n" +
							"Do you want to proceed with the update of the t24-binaries of Design Studio ?")) {
						try {
							String filepath = t24BinariesFile.getAbsolutePath();
							if(filepath.contains(" ")) {
								MessageDialog.openError(Display.getCurrent().getActiveShell(), T24_BINARIES_REFRESH_WINDOW_TITLE + " Error", 
										"Unable to extract " + t24BinariesFile.getName() + " file.\n" +
										"Please ensure the installation path has no space.\n" +
												"Current Installation path: " + t24BinariesFile.getParent());
								return;
							}
							ZipFile t24BinariesZipFile = new ZipFile(t24BinariesFile);
							extractT24BinariesWithProgressBar(t24BinariesFile, t24BinariesZipFile);
						} catch (Exception e) {
							logger.error("Unexpected error during t24-binaries extraction", e);
							MessageDialog.openError(Display.getCurrent().getActiveShell(), T24_BINARIES_REFRESH_WINDOW_TITLE, 
									"Unable to extract " + t24BinariesFile.getAbsolutePath() + ".\n" +
									"Please ensure the file is not corrupted or inaccessible." +
									"\n\nInternal error: " + e.getMessage());
						}
					}
				}

				/**
				 * @param t24BinariesFile
				 * @param t24BinariesZipFile
				 * @throws IOException
				 */
				private void extractT24BinariesWithProgressBar(final File t24BinariesFile,
						final ZipFile t24BinariesZipFile) throws IOException {

					Display.getDefault().asyncExec(new Runnable() {
						@Override
						public void run() {
							final ProgressMonitorDialog progressDialog = new ProgressMonitorDialog(Display.getDefault().getActiveShell());
							final IRunnableWithProgress runnable = new IRunnableWithProgress() {
								public void run(final IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
									monitor.beginTask("Extracting T24 binaries to a temporary folder...", 20 + t24BinariesZipFile.size());
									try {
										try {
											extracToTempFolder(monitor, t24BinariesZipFile);
										} catch (IOException e) {
											throw new InvocationTargetException(e);
										}
										
										monitor.subTask("Replacing the T24 binaries of Design Studio");
										updateT24Binaries();
										t24BinariesFile.renameTo(new File(t24BinariesFile.getAbsolutePath().concat(".backup")));
										monitor.worked(10);
										
										monitor.subTask("Refreshing maven configuration");
										updateMavenRepo();
										monitor.worked(10);
										monitor.done();
									} catch (RuntimeException e) {
										throw new InvocationTargetException(e);
									}
								}
							};
							
							try {
								progressDialog.run(true, true, runnable);
							} catch (InvocationTargetException e) {
								logger.error("Unable to extract " + t24BinariesFile.getAbsolutePath(), e);
								MessageDialog.openError(Display.getCurrent().getActiveShell(), T24_BINARIES_REFRESH_WINDOW_TITLE, 
										"Unable to extract " + t24BinariesFile.getAbsolutePath() + ".\n" +
												"Please ensure the file is not corrupted or inaccessible." +
												"\n\nInternal error: " + e.getMessage());
							} catch (InterruptedException e) {
								logger.info("T24 binaries extraction interrupted by the user", e);
								MessageDialog.openInformation(Display.getCurrent().getActiveShell(), T24_BINARIES_REFRESH_WINDOW_TITLE, "The T24 binaries extraction has been canceled.");
							} catch (Exception e) {
								logger.error("Internal error while displaying ProgressMonitor", e);
							}
						};
					});
				}
			});
		} else {
			// More that 1 file, not supported
			final File[] zipFiles = t24BinariesZips;
			logger.error("More that one t24-binaries zip found in the installation directory: " + StringUtils.join(zipFiles, ','));
			Display.getDefault().asyncExec(new Runnable() {
				@Override
				public void run() {
					MessageDialog.openError(Display.getCurrent().getActiveShell(), T24_BINARIES_REFRESH_WINDOW_TITLE, 
							"More than one t24-binaries zip file are available in the installation directory. Please only keep the one that should be installed and restart Design Studio.\n\n" +
							"T24 binaries zips found:\n" + StringUtils.join(zipFiles, '\n'));
				}
			});
		}
	}

	private void updateT24Binaries() {
		File t24Binaries = getT24BinariesFolder();
		if(t24Binaries.exists()){
			FileUtils.deleteQuietly(t24Binaries);
		}
		File temp = getTempFolder();
		temp.renameTo(t24Binaries);
	}

	private void updateMavenRepo() {
		try {
			MavenPlugin.getMaven().reloadSettings();
		} catch (CoreException e) {
			logger.error("A problem occured when trying to reload the maven settings.");
		}
	}

	public File[] getT24BinariesZips() throws URISyntaxException, UnsupportedEncodingException {
		File[] result = null;
		Location t24BinariesZipLocation = Platform.getInstallLocation();
		// The Platform.getInstallation().getURL() doesn't escape spaces properly
		// https://bugs.eclipse.org/bugs/show_bug.cgi?id=145096
		URI uri = new URI(t24BinariesZipLocation.getURL().toString().replace(" ", "%20"));
		result = new File(uri).listFiles(new FileFilter() {
			@Override
			public boolean accept(File pathname) {
				return pathname.getName().startsWith("t24-binaries") && pathname.getName().endsWith(".zip");
			}
		});

		return result == null ? new File[0] : result;
	}

	private void extracToTempFolder(IProgressMonitor monitor, ZipFile zipFile) throws IOException, InterruptedException {
		File target = getTempFolder();
		emptyTempFolder(target);
		try {
			extractFiles(monitor, zipFile, target);
		} catch (IOException e) {
			logger.error("Unable to extract t24-binaries. Attempt to cleang up the temp folder...");
			try {
				emptyTempFolder(target);
			} catch (IOException e1) {
				logger.error("Unable to do the clean up propertly", e1);
			}
			throw e;
		}
	}

	private void emptyTempFolder(File target) throws IOException {
		if (target.exists()) {
			if (target.list().length > 0) {
				FileUtils.deleteDirectory(target);
				target.mkdir();
			}
		} else {
			target.mkdir();
		}
	}

	private File getTempFolder() {
		return new File(Platform.getInstallLocation().getURL().getPath()+TMP);
	}

	private void extractFiles(final IProgressMonitor monitor, ZipFile zipFile, File target) throws IOException, InterruptedException {
		try {
			Enumeration<?> zipEntries;
			// Zip file to be unzipped
			zipEntries = zipFile.entries();
			String targetPath = target.getAbsolutePath();
			long currentTime = System.currentTimeMillis();
			int extractedFiles = 0;
			monitor.subTask("0 extracted file");
			while (zipEntries.hasMoreElements()) {
				ZipEntry zipEntry = (ZipEntry) zipEntries.nextElement();
				String path = targetPath.concat("/").concat(zipEntry.getName());
				monitor.worked(1);
				
				if (System.currentTimeMillis() - currentTime > 100) {
					monitor.subTask("" + extractedFiles + " extracted files");
					currentTime = System.currentTimeMillis();
				}
	
				// Check if the entry is a directory
				if (zipEntry.isDirectory()) {
					new File(path).mkdir();
					continue;
				}
				InputStream input = zipFile.getInputStream(zipEntry);
				OutputStream output = new BufferedOutputStream(new FileOutputStream(path));
				try {
					IOUtils.copy(input, output);
				} finally {
					IOUtils.closeQuietly(input);
					IOUtils.closeQuietly(output);
				}
				extractedFiles++;
				
				if (monitor.isCanceled()) {
					monitor.setTaskName("Cancelling extraction");
					Job cleaningJob = new Job("Cleaning canceled T24 binaries extraction") {
						@Override
						protected IStatus run(IProgressMonitor monitor) {
							File tempFolder = getTempFolder();
							try {
								FileUtils.deleteDirectory(tempFolder);
							} catch (IOException e) {
								logger.error("Error during cleaning of " + tempFolder);
							}
							return Status.OK_STATUS;
						}
					};
					cleaningJob.setSystem(true); // No UI
					cleaningJob.schedule(5000);
					throw new InterruptedException("The user canceled the T24 binaries extraction");
				}
			}
		} finally {
			zipFile.close();
		}
	}

	/**
	 * @return
	 */
	private File getT24BinariesFolder() {
		return new File(Platform.getInstallLocation().getURL().getPath() + T24_BINARIES);
	}

}
