package com.odcgroup.ocs.server.embedded.ui;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.maven.artifact.Artifact;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationType;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.debug.core.IStreamListener;
import org.eclipse.debug.core.model.IStreamMonitor;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.launching.IJavaLaunchConfigurationConstants;
import org.eclipse.jdt.launching.IRuntimeClasspathEntry;
import org.eclipse.jdt.launching.JavaRuntime;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.m2e.core.MavenPlugin;
import org.eclipse.swt.widgets.Display;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.odcgroup.ocs.server.embedded.model.IEmbeddedServer;
import com.odcgroup.ocs.server.ui.OcsServerUICore;
import com.odcgroup.server.model.IDSProject;
import com.odcgroup.server.model.IDSServer;
import com.odcgroup.server.ui.UnableToStartServerException;
import com.odcgroup.server.ui.UnableToStopServerException;
import com.odcgroup.server.ui.views.ServerTreeLabelProvider;
import com.odcgroup.workbench.core.targetplatform.TargetPlatform;

public class EmbeddedServerLauncherHelper {

	private static final String STARTER_ARTIFACT_ID = "starter";

	private static final String STARTER_GROUP_ID = "com.odcgroup.ds.simpleserver";

	private static Logger logger = LoggerFactory.getLogger(EmbeddedServerLauncherHelper.class);

	private static EmbeddedServerLauncherHelper INSTANCE = new EmbeddedServerLauncherHelper();

	private Map<IEmbeddedServer, ILaunch> launchConfigurations = new HashMap<IEmbeddedServer, ILaunch>();

	public static EmbeddedServerLauncherHelper getInstance() {
		return INSTANCE;
	}

	public void start(final IEmbeddedServer embeddedServer, final boolean debug)  throws UnableToStartServerException {
		IJavaProject javaProject;
		try {
			javaProject = (IJavaProject)embeddedServer.getServerProject().getNature(JavaCore.NATURE_ID);
			if (javaProject == null) {
				logger.error("The server project ("+ embeddedServer.getName() +") is not a java project");
				throw new UnableToStartServerException("The server project ("+ embeddedServer.getName() +") is not a java project");
			}
		} catch (CoreException e) {
			logger.error("The server project ("+ embeddedServer.getName() +") is not a java project", e);
			throw new UnableToStartServerException("Unable to get the java project of the server ("+ embeddedServer.getName() +")", e);
		}

		// Create a temp file with the class path
		File classpathFile;
		try {
			classpathFile = File.createTempFile("ocs-server-", ".classpath");
		} catch (IOException e) {
			logger.error("Cannot launch the server: Unable to create the temporary file", e);
			throw new UnableToStartServerException("Cannot launch the server: Unable to create the temporary file", e);
		}

		classpathFile.deleteOnExit();

		// Gather the classpath
	    List<File> classpathEntries;
	    try {
	    	classpathEntries = getSortedClasspath(javaProject);
		} catch (JavaModelException e) {
			logger.error("Cannot launch the server: Unable to read the M2ECLIPSE classpath container", e);
			throw new UnableToStartServerException("Unable to read the M2ECLIPSE classpath container", e);
		} catch (DuplicateWuiBlockIdException e) {
			logger.error("Cannot launch the server because duplicated wui block were id found. (" + e.getMessage() + ")", e);
			throw new UnableToStartServerException("Cannot launch the server because duplicated wui block were id found. (" + e.getMessage() + ")", e);
		} catch (Exception e) {
			logger.error("Cannot launch the server: Unable to build the classpath", e);
			throw new UnableToStartServerException("Unable to build the classpath", e);
		}

		// Write the classpath to a temporary file
		try {
			writeClasspathToFile(classpathFile, classpathEntries);
		} catch (IOException e) {
			logger.error("Cannot launch the server: Unable to write the temporary file: " + classpathFile.getAbsolutePath(), e);
			throw new UnableToStartServerException("Unable to write the temporary file: " + classpathFile.getAbsolutePath(), e);
		}

		// Locate the starter jar
		File starterJar;
		String version = TargetPlatform.getTechnicalVersion();
		String snapshotVersion = version + "-SNAPSHOT";
		try {
			String starterPathWithoutSnapshot = MavenPlugin.getMaven().getArtifactPath(MavenPlugin.getMaven().getLocalRepository(),
					STARTER_GROUP_ID, STARTER_ARTIFACT_ID, version, "jar", "");
			starterJar = new File(MavenPlugin.getMaven().getLocalRepository().getBasedir(), starterPathWithoutSnapshot);

			if (!starterJar.exists()) {
				Artifact artifact = MavenPlugin.getMaven().resolve(
						STARTER_GROUP_ID, STARTER_ARTIFACT_ID, snapshotVersion, "jar", "",
						MavenPlugin.getMaven().getArtifactRepositories(), null);
				starterJar = artifact.getFile();
			}
		} catch (CoreException e) {
			logger.error("Unable to get the starter jar for the "+ embeddedServer.getName() +" server project.\n" +
					"(groupId=" + STARTER_GROUP_ID + ", artifactId=" + STARTER_ARTIFACT_ID + ", version=" + version + " or " + snapshotVersion + ")", e);
			throw new UnableToStartServerException("Unable to get the starter jar for the "+ embeddedServer.getName() +" server project.\n" +
					"(groupId=" + STARTER_GROUP_ID + ", artifactId=" + STARTER_ARTIFACT_ID + ", version=" + version + " or " + snapshotVersion + ")", e);
		}

		// Launch the server
		try {
			ILaunchManager manager = DebugPlugin.getDefault().getLaunchManager();
			ILaunchConfigurationType type = manager.getLaunchConfigurationType(IJavaLaunchConfigurationConstants.ID_JAVA_APPLICATION);
			ILaunchConfigurationWorkingCopy workingCopy = type.newInstance(
					null, ServerTreeLabelProvider.getDisplayedServerLabel(embeddedServer));

			// Set the JRE according the server project JRE
			IPath jrePath = getJrePath(javaProject);
			String jreName = JavaRuntime.getVMInstall(jrePath).getName();
			String jreType = JavaRuntime.getVMInstall(jrePath).getVMInstallType().getId();
			workingCopy.setAttribute(IJavaLaunchConfigurationConstants.ATTR_JRE_CONTAINER_PATH, jreName);
			workingCopy.setAttribute(IJavaLaunchConfigurationConstants.ATTR_PROGRAM_ARGUMENTS, jreType);

			workingCopy.setAttribute(
					IJavaLaunchConfigurationConstants.ATTR_MAIN_TYPE_NAME,
					"com.odcgroup.ds.starter.LongClasspathAppStarter");
			
			String launchConfigurationValues = "\"" + classpathFile.getAbsolutePath() + "\" " +
					embeddedServer.getStarterClass() +
					" " +
					embeddedServer.getListeningPort() + 
					" \"" + 
					Platform.getInstallLocation().getURL().getFile().substring(1) + "\"";
			
			logger.info("Setting launch configuration vales: " + launchConfigurationValues);
			
			workingCopy.setAttribute(IJavaLaunchConfigurationConstants.ATTR_PROGRAM_ARGUMENTS,launchConfigurationValues);
			
			workingCopy.setAttribute(IJavaLaunchConfigurationConstants.ATTR_WORKING_DIRECTORY,
					javaProject.getProject().getLocation().toFile().toString());

			workingCopy.setAttribute(IJavaLaunchConfigurationConstants.ATTR_VM_ARGUMENTS,
					embeddedServer.getVmArguments());

			IPath classpathPath = new Path(starterJar.getPath());
			IRuntimeClasspathEntry classpathEntry = JavaRuntime
					.newArchiveRuntimeClasspathEntry(classpathPath);
			classpathEntry
					.setClasspathProperty(IRuntimeClasspathEntry.USER_CLASSES);

			List<String> classpath = new ArrayList<String>();
			classpath.add(classpathEntry.getMemento());
			workingCopy.setAttribute(IJavaLaunchConfigurationConstants.ATTR_CLASSPATH, classpath);
			workingCopy.setAttribute(IJavaLaunchConfigurationConstants.ATTR_DEFAULT_CLASSPATH, false);

			final ILaunchConfiguration configuration = workingCopy.doSave();

			Job launchEmbeddedServerJob = new Job("Launch Embedded Server") {
				@Override
				public IStatus run(
						IProgressMonitor monitor) {
					final ILaunch launch;
					try {
						launch = configuration.launch(debug?ILaunchManager.DEBUG_MODE:ILaunchManager.RUN_MODE, null, true);
					} catch (CoreException e) {
						return new Status(Status.ERROR, OCSServerUIEmbeddedCore.PLUGIN_ID, "Couldn't launch the Embedded Server", e);
					}
					launchConfigurations.put(embeddedServer, launch);

					if (launch.getProcesses().length >= 1) {
						launch.getProcesses()[0].getStreamsProxy().getOutputStreamMonitor().addListener(new IStreamListener() {
							@Override
							public void streamAppended(String text, IStreamMonitor monitor) {
								if (text != null) {
									if (text.contains("Embedded Server is now up and running at http://localhost:")) {
										embeddedServer.setState(debug?IDSServer.STATE_STARTED_DEBUG:IDSServer.STATE_STARTED);
									} else if (text.contains("Embedded Server could not start at http://localhost:")) {
										try {
											launch.terminate();
										} catch (DebugException e) {
											logger.error("A problem occured while forcing the shutdown of the Embedded Server", e);
										}
									}
								}
							}
						});
					}

					new Thread() {
						public void run() {
							while (!launch.isTerminated() && embeddedServer.getState() != IDSServer.STATE_STOPPED) {
								try {
									Thread.sleep(500);
								} catch (InterruptedException e) {
									// Ignore
								}
							}
							embeddedServer.setState(IDSServer.STATE_STOPPED);
							try {
								getInstance().stop(embeddedServer);
							} catch (UnableToStopServerException e) {
								// Ignore
							}
							OcsServerUICore.getDefault().getLogWatcherManager().stopAllLogWatchers(false);

							// DS-4393: do not delete anymore the configuration as this lead to internal (NPE) error depending on the timing
						}
					}.start();
					return Status.OK_STATUS;
				}
			};
			launchEmbeddedServerJob.setPriority(Job.SHORT);
			launchEmbeddedServerJob.schedule();


		} catch (CoreException e) {
			MessageDialog.openError(Display.getCurrent().getActiveShell(), "Cannot launch the server", "Unable to execute the launch configuration: "+ e.getMessage());
			logger.error("Cannot launch the server: Unable to find the starter jar: Unable to execute the launch configuration", e);
			throw new UnableToStartServerException("Unable to execute the launch configuration: "+ e.getMessage(), e);
		}
	}

	public IPath getJrePath(IJavaProject javaProject) throws JavaModelException {
		IClasspathEntry[] rawClasspath = javaProject.getRawClasspath();

		// Find the CPE_CONTAINER of the JRE
		for (IClasspathEntry rawEntry: rawClasspath) {
			if (rawEntry.getEntryKind() == IClasspathEntry.CPE_CONTAINER) {
				String path = rawEntry.getPath().toString();
				if (path.startsWith(org.eclipse.jdt.launching.JavaRuntime.JRE_CONTAINER)) {
					return rawEntry.getPath();
				}
			}
		}
		return null;
	}

	/**
	 * Build the classpath of the java project
	 * @param embeddedServerJavaProject
	 * @return the classpath of the java project
	 * @throws JavaModelException
	 * @throws DuplicateWuiBlockIdException 
	 */
	public List<File> getSortedClasspath(IJavaProject embeddedServerJavaProject) throws JavaModelException, DuplicateWuiBlockIdException {
		// Get the classpath with folder project before jars
		List<File> preSortedClasspath = new ArrayList<File>();
		List<File> tail = new ArrayList<File>();
		for (File f: getClasspath(embeddedServerJavaProject, new HashSet<IJavaProject>())) {
			if (f.isDirectory()) {
				preSortedClasspath.add(f);
			} else {
				tail.add(f);
			}
		}
		preSortedClasspath.addAll(tail);
		
		// Sort wui blocks first in the class path according to they weight
		List<File> classpath = new WuiBlockHelper().sort(preSortedClasspath);
		logger.debug("the classpath contains " + classpath.size() + " disctinct entries");
		for (File file: classpath) {
			logger.debug(file.getAbsolutePath());
		}
		return classpath;
	}
	
	private Set<File> getClasspath(IJavaProject currentEmbeddedServerJavaProject, Set<IJavaProject> visitedProjects) throws JavaModelException {
		// Use linked hash set to avoid unpredictable order of the classpath
		Set<File> result = new LinkedHashSet<File>();
		Set<IJavaProject> referencedProjects = new LinkedHashSet<IJavaProject>();

		IClasspathEntry[] rawClasspath = currentEmbeddedServerJavaProject.getRawClasspath();
		List<IClasspathEntry> resolvedClasspath = new ArrayList<IClasspathEntry>();

		// Resolve the classpath (containers, variables, ...)
		for (IClasspathEntry rawEntry: rawClasspath) {
			if (rawEntry.getEntryKind() == IClasspathEntry.CPE_CONTAINER) {
				if (!rawEntry.getPath().toString().startsWith(org.eclipse.jdt.launching.JavaRuntime.JRE_CONTAINER)) {
					List<IClasspathEntry> asList = Arrays.asList(JavaCore.getClasspathContainer(rawEntry.getPath(), currentEmbeddedServerJavaProject).getClasspathEntries());
					resolvedClasspath.addAll(asList);
				}
			} else {
				resolvedClasspath.add(JavaCore.getResolvedClasspathEntry(rawEntry));
			}
		}

		// Convert each resolved classpath entry to a physical location
		for (IClasspathEntry entry: resolvedClasspath) {
			File entryFile;
			if (entry.getEntryKind() == IClasspathEntry.CPE_SOURCE) {
				if (entry.getOutputLocation() != null) {
					entryFile = entry.getOutputLocation().toFile();
				} else {
					entryFile = referencedResource(currentEmbeddedServerJavaProject, currentEmbeddedServerJavaProject.getOutputLocation().toFile());
				}
			} else {
				entryFile = entry.getPath().toFile();
			}
			if (entryFile == null) {
				logger.error("Classpath entry null for " + entry);
				continue;
			}

			if (entryFile.exists()) {
				result.add(entryFile);
			} else {
				IJavaProject refProject = referencedProject(entryFile);
				File refResource = referencedResource(refProject, entryFile);
				if (refResource != null) {
					// Reference to a resource in another project
					if (refResource.exists()) {
						result.add(refResource);
					} else {
						logger.warn("Embedded Server classpath problem. The classpath references a non existing file in project: " + refResource.toString());
					}
				} else if (refProject != null) {
					// Reference to another projects will be recurcively examined
					if (!refProject.equals(currentEmbeddedServerJavaProject) &&
							!visitedProjects.contains(refProject)) {
						visitedProjects.add(refProject);
						referencedProjects.add(refProject);
					}
				}
			}
		}

		// Add the output location of the java project
		// (This is done it last position because it is must be added after source folder specific output folder)
		File file = referencedResource(currentEmbeddedServerJavaProject, currentEmbeddedServerJavaProject.getOutputLocation().toFile());
		if (file != null && file.exists()) {
			result.add(file);
		}

		// Reference to another projects will be recurcively examined
		// but after adding the output location of the current project
		for (IJavaProject referencedProject: referencedProjects) {
			Set<File> referencedClasspath = getClasspath(referencedProject, visitedProjects);
			result.addAll(referencedClasspath);
		}
		
		return result;
	}

	/**
	 * Convert a file to a classpath entry string. The classpath entry must end with a \\ if
	 * it references a folder.
	 * @param file
	 * @return a string referencing the
	 */
	private String asClasspathEntry(File file) {
		if (file.isDirectory()) {
			return file.toString() + "\\";
		} else {
			return file.toString();
		}
	}

	/**
	 * Retrieve the java project referenced by this entry, <code>null</code> otherwise
	 * @param entryFile
	 * @return the java project referenced by this entry, <code>null</code> otherwise
	 */
	private IJavaProject referencedProject(File entryFile) {
		IJavaProject result = null;
		if (entryFile.getPath().startsWith("\\")) {
			String[] splittedEntries = StringUtils.split(entryFile.getPath(), "\\");
			if (splittedEntries.length > 0) {
				String projectName = splittedEntries[0];
				IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
				if (project.exists() && project.isOpen()) {
					try {
						// Is a java project ?
						result = (IJavaProject) project.getNature(JavaCore.NATURE_ID);
					} catch (CoreException e) {
						logger.error("Unable to get java project", e);
					}
				}
			}
		}
		return result;
	}

	/**
	 * Retrieve the resource associated to an entry
	 * @param refProject
	 * @param entryFile
	 * @return the resource associated to an entry, <code>null</code> if the resource path is "" or
	 * if the java project is null.
	 */
	private File referencedResource(IJavaProject refProject, File entryFile) {
		if (refProject == null || !refProject.getProject().exists() || !refProject.getProject().isOpen()) {
			return null;
		}

		File result = null;
		if (entryFile.getPath().startsWith("\\")) {
			String path = StringUtils.removeStart(entryFile.getPath(), "\\" + refProject.getProject().getName());
			if (!path.isEmpty()) {
				result = refProject.getProject().getFile(path).getLocation().toFile();
			}
		}
		return result;
	}

	/**
	 * @param out
	 * @param path
	 * @throws IOException
	 */
	private void writePath(BufferedWriter out, String path) throws IOException {
		out.write(path);
		out.append(System.getProperty("line.separator"));
	}

	/**
	 * @param classpathFile
	 * @param classpathEntries
	 * @throws IOException
	 */
	protected void writeClasspathToFile(File classpathFile,
			List<File> classpathEntries) throws IOException {
		BufferedWriter out = null;
		try {
			FileWriter fstream = new FileWriter(classpathFile);
			out = new BufferedWriter(fstream);
			for (File file: classpathEntries) {
				writePath(out, asClasspathEntry(file));
			}
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					// Close silently
				}
			}
		}
	}

	public void stop(IDSServer server) throws UnableToStopServerException {
		ILaunch launch = launchConfigurations.remove(server);
		if (launch != null) {
			try {
				launch.terminate();
			} catch (DebugException e) {
				throw new UnableToStopServerException("Stop request failed", e);
			}
		}

	}

	public boolean checkProjectsConfiguration(IEmbeddedServer embeddedServer) {
		List<String> projectsWithConfigProblem = new ArrayList<String>();
		
		IProject serverProject = embeddedServer.getServerProject();
		if (!projectConfigIsCorrect(serverProject)) {
			projectsWithConfigProblem.add(serverProject.getName());
		}
		
		for (IDSProject ocsProject: embeddedServer.getDsProjects()) {
			IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(ocsProject.getName());
			if (!projectConfigIsCorrect(project)) {
				projectsWithConfigProblem.add(project.getName());
			}
		}
		if (projectsWithConfigProblem.size() > 0) {
			// Allow the user to decide if he want to continue or cancel
			return displayWarningMessage(projectsWithConfigProblem);
		} else {
			return true;
		}
	}

	private boolean projectConfigIsCorrect(IProject project) {
		try {
			IJavaProject javaProject = JavaCore.create(project);
			for (IClasspathEntry cp: javaProject.getRawClasspath()) {
				if (cp.getEntryKind() == IClasspathEntry.CPE_SOURCE &&
						cp.getExclusionPatterns().length > 0) {
					return false;
				}
			}
		} catch (CoreException e) {
			return true;
		}
		return true;
	}
	
	/**
	 * @param projectsWithConfigProblem
	 */
	protected boolean displayWarningMessage(List<String> projectsWithConfigProblem) {
		StringBuffer message = new StringBuffer();
		message.append("The following project(s) have a configuration problem:\n\n");
		for (String projectName: projectsWithConfigProblem) {
			message.append(" - " + projectName + "\n");
		}
		message.append("\nThe project configuration excluded some resources and will probably not work properly with the Embedded Server.\n\n");
		message.append("To solve this problem, go to project preferences > Java Build Path, click on the Source tab and remove all Excluded: ** definition (use the \"Remove\" button)\n\n");
		message.append("Click OK to start the Embedded Server anyway or Cancel to cancel the startup");
		MessageDialog messageDialog = new MessageDialog(Display.getDefault().getActiveShell(), 
				"Configuration Problem",
				null,
				message.toString(),
				MessageDialog.WARNING,
				new String[] { IDialogConstants.OK_LABEL, IDialogConstants.CANCEL_LABEL },
				0);
		return messageDialog.open() == 0;
	}

}

