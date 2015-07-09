package com.odcgroup.ocs.server.external.ui.builder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.eclipse.core.resources.ICommand;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;

import com.odcgroup.ocs.server.ServerCore;
import com.odcgroup.ocs.server.external.builder.internal.delta.AnalysedDelta;
import com.odcgroup.ocs.server.external.builder.internal.delta.SourceFolderEnum;
import com.odcgroup.ocs.server.external.builder.internal.mapping.NotDeployedFile;
import com.odcgroup.ocs.server.external.builder.internal.mapping.TargetMapper;
import com.odcgroup.ocs.server.external.model.IExternalServer;
import com.odcgroup.ocs.server.external.ui.OCSServerUIExternalCore;
import com.odcgroup.ocs.server.external.ui.builder.internal.mapping.GenTargetMapper;
import com.odcgroup.ocs.server.external.ui.builder.internal.mapping.NonGenTargetMapper;

public class DeployBuilder extends IncrementalProjectBuilder {

	public static final String MAVEN_TARGET = "target/";
	public static final String MAVEN_TARGET_TEST = "target/test-classes/";
	private static final String SVN_FOLDER = ".svn";

	public static final String BUILDER_ID = ServerCore.PLUGIN_ID + ".builder";  //$NON-NLS-1$
	private static final String DEBUG_KEY = "debug";

	private static final String GEN_SUFFIX = "-gen";

	private TargetMapper targetMapper;

	private IProject overrideProject;

	private static final Pattern TRANSLATION_FILE_PATTERN = Pattern.compile(".*message.*\\.xml");


	/**
	 * Used by eclipse
	 */
	public DeployBuilder() {
	}

	/**
	 * Used to invoke the builder initially
	 * @throws CoreException
	 */
	public DeployBuilder(IProject overrideProject) throws CoreException {
		this.overrideProject = overrideProject;
		initTargetMapper();
		configureBuilder(getBuilderArgs());
	}

	/**
	 * @param overrideProject
	 * @return
	 * @throws CoreException
	 */
	@SuppressWarnings("rawtypes")
	private Map getBuilderArgs() throws CoreException {
		// Retreive the builder
		Map args = null;
		for (ICommand cmd : getEffectiveProject().getDescription().getBuildSpec()) {
			if (cmd.getBuilderName().equals(DeployBuilder.BUILDER_ID)) {
				args = cmd.getArguments();
			}
		}
		return args;
	}

	/**
	 * Called once the builder is initialized
	 */
	protected void startupOnInitialize() {
		initTargetMapper();
	}

	/**
	 * Return the project (getProject() can be overridden if using the DeployBuilder(IProject) constructor
	 */
	private IProject getEffectiveProject() {
		if (overrideProject != null) {
			return overrideProject;
		} else {
			return getProject();
		}
	}

	private void initTargetMapper() {
		if (targetMapper == null) {
			IExternalServer externalServer = OCSServerUIExternalCore.getDefault().getExternalServer();
			if (getEffectiveProject().getName().endsWith(GEN_SUFFIX)) {
				targetMapper = new GenTargetMapper(externalServer, getEffectiveProject());
			} else {
				targetMapper = new NonGenTargetMapper(externalServer, getEffectiveProject());
			}
		}
	}

	public boolean needsPrepareDeploymentDestinations() throws CoreException {
		return targetMapper.needsPrepareDeploymentDestinations();
	}

	protected IStatus prepareDeploymentDestinations() throws CoreException {
		return targetMapper.prepareDeploymentDestinations();
	}

	/**
	 * Called when the builder in invoke by Eclipse (full and incremental build)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public IProject[] build(int kind, Map args, IProgressMonitor monitor)
			throws CoreException {
		DeployConsole deployConsole = OCSServerUIExternalCore.getDefault().getDeployBuilderConsole();
		IExternalServer externalServer = OCSServerUIExternalCore.getDefault().getExternalServer();
		deployConsole.setDisplayInfoDetail(externalServer.isDisplayAllDeployedFiles());
		deployConsole.printDebug("DeployBuilder invoked"); //$NON-NLS-1$

		if (externalServer.containsProject(getEffectiveProject().getName())) {
			deployConsole.printDebug("args: " + args); //$NON-NLS-1$
			if (args.containsKey(DEBUG_KEY)) {
				deployConsole.setDisplayDebug(Boolean.valueOf((String) args	.get(DEBUG_KEY)));
			}

			configureBuilder(args);

			// DS-2768 force full build if backup jar is necessary
			// (targetMapper.needsPrepareDeploymentDestinations())
			if (kind == IncrementalProjectBuilder.FULL_BUILD || targetMapper.needsPrepareDeploymentDestinations()) {
				fullBuild(monitor);
			} else {
				IResourceDelta delta = getDelta(getEffectiveProject());
				if (delta == null) {
					fullBuild(monitor);
				} else {
					incrementalBuild(delta, monitor);
				}
			}
		} else {
			deployConsole
					.printDebug("Project " + getEffectiveProject().getName() + " is not deployed on the server"); //$NON-NLS-1$
		}
		return null;
	}

	/**
	 * Initial builder (done when the builder is added)
	 * @throws CoreException
	 */
	public void initialBuild() throws CoreException {
		build(IncrementalProjectBuilder.FULL_BUILD, getBuilderArgs(), new NullProgressMonitor());
	}

	/**
	 * @param args
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void configureBuilder(Map args) {
		targetMapper.configure(args);
	}

	/**
	 * Called when a full build is requested (using the Clean action in Eclipse).
	 * @throws CoreException
	 */
	private void fullBuild(IProgressMonitor monitor) throws CoreException {
		DeployConsole deployConsole = OCSServerUIExternalCore.getDefault().getDeployBuilderConsole();
		deployConsole.printDebug("Full build requested");  //$NON-NLS-1$
		long start = System.currentTimeMillis();
		monitor.beginTask("Deployment of " + getEffectiveProject().getName(), 3);  //$NON-NLS-1$

		prepareDeploymentDestinations();

		// Analysis of the delta
		final AnalysedDelta delta = generateFullDelta();
		monitor.worked(1);

		if (delta.hasChanges()) {
			deployConsole.printInfo("Start deployment");
			deployConsole.printDebug("Analysed delta\n" + delta);

			monitor.setTaskName("Copying file for " + getEffectiveProject().getName());  //$NON-NLS-1$
			processDelta(delta);
			monitor.worked(1);
		}

		deployConsole.printInfo("Full deploy in " + (System.currentTimeMillis()-start) + " ms.");  //$NON-NLS-1$

		monitor.setTaskName("Deployment done for " + getEffectiveProject().getName());  //$NON-NLS-1$
		monitor.done();
	}

	/**
	 * Called when an incremental build is requested (invoked by Eclipse when building project in background).
	 */
	private void incrementalBuild(IResourceDelta resourceDelta, IProgressMonitor monitor) throws CoreException {
		DeployConsole deployConsole = OCSServerUIExternalCore.getDefault().getDeployBuilderConsole();
		deployConsole.printDebug("Incremental build requested");  //$NON-NLS-1$
		long start = System.currentTimeMillis();
		monitor.beginTask("Deployment of " + getEffectiveProject().getName(), 3);  //$NON-NLS-1$

		// Analysis of the delta
		final AnalysedDelta delta = analyseDelta(resourceDelta, monitor);
		monitor.worked(1);

		if (delta.hasChanges()) {
			deployConsole.printDebug("Analysed delta\n" + delta);  //$NON-NLS-1$

			monitor.setTaskName("Copying file for " + getEffectiveProject().getName());  //$NON-NLS-1$
			processDelta(delta);
			monitor.worked(1);
		}

		deployConsole.printInfo("Incremental deploy in " + (System.currentTimeMillis()-start) + " ms.");  //$NON-NLS-1$

		monitor.setTaskName("Deployment done for " + getEffectiveProject().getName());  //$NON-NLS-1$
		monitor.done();
	}

	/**
	 * Create an analyzed delta containing all deployable files and folders
	 */
	private AnalysedDelta generateFullDelta() {
		DeployConsole deployConsole = OCSServerUIExternalCore.getDefault().getDeployBuilderConsole();

		AnalysedDelta result = new AnalysedDelta(getProjectFullPath());
		// Note: Removing all existing target files may be risky
		// as many projects can produce file that goes in the same folder...
		deployConsole.printDebug("copy all files");  //$NON-NLS-1$
		File start = new File(getProjectFullPath());
		generateFullDelta(result, start);
		return result;
	}

	/**
	 * Recursive algorithm which scan all files and folders
	 */
	private void generateFullDelta(AnalysedDelta result, File file) {
		String fileName = file.getPath().replace(File.separatorChar, '/');

		if (fileName.contains(SVN_FOLDER)) {
			// Skip .svn folder
			return;
		}

		if (fileName.length() >= getProjectFullPath().length()+1) {
			fileName = fileName.substring(getProjectFullPath().length()+1);
			if (fileName.startsWith(MAVEN_TARGET) || fileName.startsWith(ProjectHelper.getOutputFolder(getEffectiveProject()))) {
				if (!fileName.startsWith(MAVEN_TARGET_TEST) &&
						!ProjectHelper.getOutputFolder(getEffectiveProject()).startsWith(fileName)) {
					// avoid target & target/class & target/test-classes folders
					if (file.isDirectory()) {
						result.addedFolder(fileName);
					} else {
						result.addedFile(fileName);
					}
				}
			}
		}

		// Recursively scan the sub folders
		if (file.isDirectory()) {
			for (File child : file.listFiles()) {
				generateFullDelta(result, child);
			}
		}
	}

	/**
	 * Analyze the delta generated by eclipse
	 */
	private AnalysedDelta analyseDelta(IResourceDelta delta,
			IProgressMonitor monitor) throws CoreException {
		final AnalysedDelta analysedDelta = new AnalysedDelta(getProjectFullPath());
		final String projectName = getEffectiveProject().getName();
		final int projectNameLength = ("" + File.separatorChar + getEffectiveProject().getName() + File.separatorChar).length();
		monitor.worked(1);
		final String outputFolder = ProjectHelper.getOutputFolder(getEffectiveProject());
		delta.accept(new IResourceDeltaVisitor() {
			public boolean visit(IResourceDelta delta) {
				if (delta == null || delta.getResource().getRawLocation() == null || delta.getFullPath().toPortableString().length() < projectNameLength) {
					return true;
				}
				String deltaResourceLocation = delta.getFullPath().toPortableString().substring(projectNameLength);
				if (deltaResourceLocation.contains(SVN_FOLDER)) {
					// Skip .svn folders
					return false;
				}

				if (deltaResourceLocation.startsWith(MAVEN_TARGET) || deltaResourceLocation.startsWith(outputFolder)) {
					processDelta(delta);
				} else {
					OCSServerUIExternalCore.getDefault().getDeployBuilderConsole()
						.printDebug("Ignored change: " + deltaResourceLocation);
				}
				return true; // visit children too
			}

			private void processDelta(IResourceDelta delta) {
				String file = delta.getResource().getFullPath().toPortableString().substring(projectName.length()+2);
				boolean isDirectory = delta.getResource().getType() == IResource.FOLDER;
				switch (delta.getKind()) {
				case IResourceDelta.ADDED:
					if (isDirectory) {
						analysedDelta.addedFolder(file);
					} else {
						analysedDelta.addedFile(file);
					}
					break;
				case IResourceDelta.CHANGED:
					if (!isDirectory) {
						analysedDelta.changedFile(file);
					}
					// Changed directories are ignored because they are
					// marked changed if a child file/folder is modified
					// (which is already tracked)
					break;
				case IResourceDelta.REMOVED:
					if (isDirectory) {
						analysedDelta.removedFolder(file);
					} else {
						analysedDelta.removedFile(file);
					}
					break;
				default:
				}
			}
		});
		return analysedDelta;
	}

	/**
	 * Process the analyzed delta (copy and remove target files and folder)
	 * @throws CoreException
	 */
	private void processDelta(AnalysedDelta delta) throws CoreException {
		DeployConsole deployConsole = OCSServerUIExternalCore.getDefault().getDeployBuilderConsole();

		long start = System.currentTimeMillis();

		deployConsole.printDebug("Removing removed files");  //$NON-NLS-1$
		deleteFiles(delta.getRemovedFiles());

		deployConsole.printDebug("Removing removed folder");  //$NON-NLS-1$
		deleteFolders(delta.getRemovedFolders());

		deployConsole.printDebug("Copying added files");  //$NON-NLS-1$
		copyFiles(delta.getAddedFiles());

		deployConsole.printDebug("Copying changed files");  //$NON-NLS-1$
		copyFiles(delta.getChangedFiles());

		deployConsole.printDebug("Add missing folder files");  //$NON-NLS-1$
		addFolders(delta.getAddedFolders());

		deployConsole.printInfo("Statistics: " + delta.getAddedFiles().size() + " added file(s), " + delta.getChangedFiles().size() + " modified file(s), " + delta.getRemovedFiles().size() + " removed file(s).");

		deployConsole.printInfo("Server update made in " + (System.currentTimeMillis()-start) + " ms.");  //$NON-NLS-1$

		if (hasTranslationChanged(delta)) {
			if (triggerTranslationReload()) {
				deployConsole.printInfo("Translation reload triggered.");
			} else {
				deployConsole.printWarning("Unable to trigger translation reload.");
			}
		}
	}

	/**
	 * @param delta
	 * @return true if a message.xml file is deployed.
	 */
	private boolean hasTranslationChanged(AnalysedDelta delta) {
		for (String file: delta.getChangedFiles()) {
			if (TRANSLATION_FILE_PATTERN.matcher(file).matches()) {
				return true;
			}
		}
		for (String file: delta.getAddedFiles()) {
			if (TRANSLATION_FILE_PATTERN.matcher(file).matches()) {
				return true;
			}
		}
		return false;
	}

	private boolean triggerTranslationReload() {
		File touchFile = new File(OCSServerUIExternalCore.getDefault().getExternalServer().getReloadMessagesTouchFile());
		if (touchFile.exists()) {
			touchFile.setLastModified(System.currentTimeMillis());
		} else {
			try {
				touchFile.createNewFile();
			} catch (IOException e) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Copy a list of files using a target mapper
	 * @throws CoreException
	 */
	private void copyFiles(List<String> files) throws CoreException {
		DeployConsole deployConsole = OCSServerUIExternalCore.getDefault().getDeployBuilderConsole();
		String projectFullPath = getProjectFullPath();
		File projectFolder = new File(projectFullPath);
		for (String sourceFileName: files) {
			File sourceFile = new File(projectFolder, sourceFileName);
			File targetFile = targetMapper.getTarget(sourceFileName);
			if (targetFile != null) {
				if (targetFile != NotDeployedFile.INSTANCE) {
					deployConsole.printInfoDetail("Copying file - Source: " + sourceFileName + ", target: " + targetFile);  //$NON-NLS-1$
					if (!sourceFile.exists()) {
						deployConsole.printWarning("source doesn't exists !!!");  //$NON-NLS-1$
					} else {
						try {
							if (isTargetFileUpdateOfSource(sourceFile, targetFile)) {
								copyFileNIO(sourceFile, targetFile);
								targetFile.setLastModified(sourceFile.lastModified());
							}
						} catch (IOException e) {
							deployConsole.printError("error occurs during file copy - Source : " + sourceFileName + ", target: " + targetFile); //$NON-NLS-1$
							e.printStackTrace();
						}
					}
				} else {
					deployConsole.printInfoDetail("Copying file - Not deployed: " + sourceFileName);
				}
			} else {
				deployConsole.printWarning("Non mappable file: " + sourceFileName); //$NON-NLS-1$
			}
		}
	}

	/**
	 * @param sourceFile
	 * @param targetFile
	 * @return
	 */
	protected boolean isTargetFileUpdateOfSource(File sourceFile, File targetFile) {
		return !targetFile.exists() || (sourceFile.lastModified() > targetFile.lastModified());
	}

	/**
	 * Copy a file using java.nio
	 */
	private void copyFileNIO(File sourceFile, File targetFile)
			throws IOException {
		String fileName = targetFile.getPath();
		String path = fileName.substring(0, fileName
				.lastIndexOf(File.separatorChar));
		if (path.length() != 0) {
			new File(path).mkdirs();
		}

		if (!targetFile.exists()) {
			targetFile.createNewFile();
		}

		FileInputStream inputStream = null;
		FileChannel source = null;
		FileOutputStream outputStream = null;
		FileChannel destination = null;
		try {
			inputStream = new FileInputStream(sourceFile);
			try {
				source = inputStream.getChannel();
				try {
					outputStream = new FileOutputStream(targetFile);
					try {
						destination = outputStream.getChannel();
						destination.transferFrom(source, 0, source.size());
					} finally {
						if (destination != null && destination.isOpen()) {
							destination.close();
						}
					}
				} finally {
					if (outputStream != null) {
						outputStream.close();
					}
				}
			} finally {
				if (source != null && source.isOpen()) {
					source.close();
				}
			}
		} finally {
			if (inputStream != null) {
				inputStream.close();
			}
		}
	}

	/**
	 * Remove a list of files
	 * @throws CoreException
	 */
	private void deleteFiles(List<String> removedFiles) throws CoreException {
		DeployConsole deployConsole = OCSServerUIExternalCore.getDefault().getDeployBuilderConsole();
		for (String sourceFileName: removedFiles) {
			File targetFile = targetMapper.getTarget(sourceFileName);
			if (targetFile != null) {
				if (targetFile != NotDeployedFile.INSTANCE) {
					deployConsole.printInfoDetail("Removing file - Source: " + sourceFileName + ", target: " + targetFile);  //$NON-NLS-1$
					if (!targetFile.delete()) {
						deployConsole.printWarning("unable to remove " + targetFile);  //$NON-NLS-1$
					}
				} else {
					deployConsole.printInfoDetail("Removing file - Not deployed: " + sourceFileName);
				}
			} else {
				deployConsole.printWarning("Non mappable file: " + sourceFileName);  //$NON-NLS-1$
			}
		}
	}

	/**
	 * Remove a list of folder
	 * @throws CoreException
	 */
	private void deleteFolders(List<String> removedFiles) throws CoreException {
		DeployConsole deployConsole = OCSServerUIExternalCore.getDefault().getDeployBuilderConsole();
		for (String sourceFileName: removedFiles) {
			File targetFile = targetMapper.getTarget(sourceFileName);
			if (targetFile != null) {
				if (targetFile != NotDeployedFile.INSTANCE) {
					deployConsole.printDebug("Removing folder - Source: " + sourceFileName + ", target: " + targetFile);  //$NON-NLS-1$
					if (!targetFile.delete()) {
						deployConsole.printWarning("unable to remove " + targetFile);  //$NON-NLS-1$
					}
				} else {
					deployConsole.printDebug("Removing folder - Not deployed: " + sourceFileName);
				}
			} else {
				if (!isIgnoredWarning(sourceFileName)) {
					deployConsole.printWarning("Non mappable file: " + sourceFileName);  //$NON-NLS-1$
				}
			}
		}
	}

	/**
	 * Create a list of folders (if not existing)
	 * @throws CoreException
	 */
	private void addFolders(List<String> addedFolders) throws CoreException {
		DeployConsole deployConsole = OCSServerUIExternalCore.getDefault().getDeployBuilderConsole();
		for (String sourceFolder: addedFolders) {
			File targetFile = targetMapper.getTarget(sourceFolder);
			if (targetFile != null) {
				if (targetFile != NotDeployedFile.INSTANCE) {
					deployConsole.printInfoDetail("Creating folder - Source: " + sourceFolder + ", target: " + targetFile);  //$NON-NLS-1$
					targetFile.mkdirs();
				} else {
					deployConsole.printInfoDetail("Creating folder - Not deployed: " + sourceFolder);
				}
			}
		}
	}

	/**
	 * Retrieve the project full path
	 */
	private String getProjectFullPath() {
		return getEffectiveProject().getLocation().toPortableString();
	}

	private boolean isIgnoredWarning(String fileName) {
		return fileName.contains(SourceFolderEnum.WUI_BLOCK.getWorkspaceFolder());
	}

}
