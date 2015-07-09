package com.odcgroup.workbench.generation.headless;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.Thread.UncaughtExceptionHandler;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.Set;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceDescription;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.jobs.IJobManager;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.odcgroup.translation.core.initializer.TranslationModelProjectInitializer;
import com.odcgroup.workbench.core.IContainerIdentifier;
import com.odcgroup.workbench.core.IOfsModelContainer;
import com.odcgroup.workbench.core.IOfsModelResource;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.core.InvalidMetamodelVersionException;
import com.odcgroup.workbench.core.OfsCore;
import com.odcgroup.workbench.core.helper.FeatureSwitches;
import com.odcgroup.workbench.core.helper.Timer;
import com.odcgroup.workbench.core.init.ProjectInitializer;
import com.odcgroup.workbench.core.logging.MemoryLogUtil;
import com.odcgroup.workbench.core.migration.IMigrationProcessor;
import com.odcgroup.workbench.core.migration.MigrationProcessorProvider;
import com.odcgroup.workbench.core.repository.IDependencyManager;
import com.odcgroup.workbench.core.repository.ModelResourceLookup;
import com.odcgroup.workbench.generation.GenerationCommon;
import com.odcgroup.workbench.generation.GenerationCore;
import com.odcgroup.workbench.generation.ModelsHelper;
import com.odcgroup.workbench.generation.init.CodeGenInitializer;

public class RunGeneration implements IApplication {
	
	private static final Logger logger = LoggerFactory.getLogger(RunGeneration.class);
	private static final String IGNORE_VALIDATION_ERRORS = "ignoreValidationErrors";
	
	private static final String PREPARE_CUSTO_PROJECT_EXTENSION_ID = "com.odcgroup.workbench.generation.prepare.custo.project";
	
	private void prepareWorkspace() throws CoreException, InterruptedException {
		// turn off auto-building
		IWorkspace ws = ResourcesPlugin.getWorkspace();
		IWorkspaceDescription desc = ws.getDescription();
		desc.setAutoBuilding(false);
		ws.setDescription(desc);
		
		OfsCore.getDependencyManager().disableAutoResolution();
		
		// import projects
		IPath workspacePath = ws.getRoot().getLocation();
		File[] projectDirs = workspacePath.toFile().listFiles();
		for (File projectDir : projectDirs) {
			IPath projectPath = new Path(projectDir.getAbsolutePath());
			if (!projectPath.lastSegment().startsWith(".")) {
				importExistingProject(projectPath);
			}
		}
		ResourcesPlugin.getWorkspace().getRoot().refreshLocal(IResource.DEPTH_INFINITE, null);
		OfsCore.getDependencyManager().resolveDependencies();
	}

	public Object start(IApplicationContext context) throws Exception {
		final Timer timer = new Timer().start();
		
		Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler() {
			public void uncaughtException(Thread t, Throwable e) {
				logger.error("DS Generator finished unsuccessfully due to errors in thread " + t, e);
				// make sure that the process return code is not ok
				throw new GenerationException("DS Generator finished unsuccessfully due to errors - please check the log entries above.", e);
			}
		});
	
		try {
			String version = OfsCore.getVersionNumber();
	
			logger.info("This is the DS Generator version " + version);
			MemoryLogUtil.logMemory(logger);
			
			if (FeatureSwitches.generationHeadlessFakeIt.get()) {
				logger.warn("STOP & EXIT Generator, due to explicitly enabled fake/simulation mode via: " + FeatureSwitches.generationHeadlessFakeIt.toString());
				return IApplication.EXIT_OK;
			}
			
			logJVMDetails();
			logger.info("Preparing workspace meta-data...");
			prepareWorkspace();

			stopBackgroundJobExecution();
			MemoryLogUtil.logMemory(logger);

			IProject[] projects = ResourcesPlugin.getWorkspace().getRoot().getProjects();
	
			logger.info("Updating project configurations...");
			updateProjects(projects);
			
			// we need background jobs to run for dependency resolution
			allowBackgroundJobExecution();

			MemoryLogUtil.logMemory(logger);
			logger.info("Validating model dependencies...");
			validateDependencies(projects);
			stopBackgroundJobExecution();
			
			logger.info("Migrating models...");
			migrateProjects(projects);
			
			logger.info("Temporarily migrating translations into models...");
			migrateTranslations(projects);

			logger.info("Building model projects...");
			buildProjects(projects);

			MemoryLogUtil.logMemory(logger);

			// DS-2806: Skip code generation if asked for
			if(!skipCodeGen()) {
				logger.info("Validating models of packaged dependencies...");
				validateDependencyModels(projects);
				MemoryLogUtil.logMemory(logger);

//				validateProjects(projects);
//				MemoryLogUtil.logMemory(logger);

				logger.info("Starting code generation...");
				executeCodeGeneration(projects);
			}

			logger.info("DS Generator finished successfully and terminates now (total took {}).", timer.stopAndText());
			
			Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler() {
				public void uncaughtException(Thread t, Throwable e) {
					// ignore whatever might now happen while closing the workspace
				}
			});
			allowBackgroundJobExecution();

			// stop any background jobs that might be scheduled
			Job currentJob = null;
			while((currentJob = Job.getJobManager().currentJob())!=null) currentJob.cancel();
		} catch (InterruptedException e) {
			logger.error(""+ e.getMessage(), e);
			// make sure that the process return code is not ok
			throw new GenerationException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("DS Generator finished unsuccessfully due to errors.", e);
			if (FeatureSwitches.generationHeadlessStopOnErrors.get()) {
				// make sure that the process return code is not ok
				throw new GenerationException("DS Generator finished unsuccessfully due to errors - please check the log entries above.", e);
			}
		}
		return IApplication.EXIT_OK;
	}

	private void logJVMDetails() {
		Properties p = System.getProperties();
		for (String pName : p.stringPropertyNames()) {
			if (!(pName.startsWith("java") || pName.startsWith("os")))
				continue;
			String pText = pName + " = " + p.getProperty(pName);
			logger.info(pText);
		}

		// inspired by org.apache.maven.cli.CLIReportingUtils
		StringBuilder localeAndEncodingInfo = new StringBuilder();
		localeAndEncodingInfo.append("JVM Default locale: ").append(Locale.getDefault())
				.append(", platform encoding: ").append(System.getProperty("file.encoding", "<unknown encoding>"));
		logger.info(localeAndEncodingInfo.toString());
	}

	
	private boolean skipCodeGen() {
		return "true".equalsIgnoreCase(System.getProperty("ds.skipCodeGen"));
	}

	private void updateProjects(IProject[] projects) throws InterruptedException, CoreException {
		// run the project configuration update
		for (final IProject project : projects) {
			if(OfsCore.isOfsProject(project)) {
				IWorkspaceRunnable runnable = new IWorkspaceRunnable() {
					public void run(IProgressMonitor monitor) throws CoreException {
						// DS-2097: make sure that project is correctly set up (i.e. add missing structures)
						for(ProjectInitializer initializer : OfsCore.getProjectInitializers(project, true)) {
							if (!initializer.getMarkerId().equals("com.odcgroup.translation.core.TranslationMigrationProblem")) {
								initializer.updateConfiguration(project, null);
							}
						}
					}
				};
				ResourcesPlugin.getWorkspace().run(runnable, ResourcesPlugin.getWorkspace().getRoot(), IWorkspace.AVOID_UPDATE, null);
			}
		}				
	}

	private void migrateProjects(IProject[] projects) throws InterruptedException {
		try {
			IMigrationProcessor mp = MigrationProcessorProvider.getMigrationProcessor();
			// mp is null for T24 products
			if (mp != null) {
				mp.migrateProjects(projects);
			}
		} catch (CoreException ex) {
			logger.error("A CoreException during the projects migration occurred", ex);
			throw new InterruptedException("DS Generator has stopped gracefully due to a missing migration processor.");
		}
		
	}

	private void migrateTranslations(IProject[] projects) throws InterruptedException {
		for (final IProject project : projects) {
			if(OfsCore.isOfsProject(project)) {
				try {
					final IOfsProject ofsProject = OfsCore.getOfsProjectManager().getOfsProject(project);
					IWorkspaceRunnable runnable = new IWorkspaceRunnable() {

						public void run(IProgressMonitor monitor) throws CoreException {
							// DS-3698: Migrate old translations
							TranslationModelProjectInitializer initializer = new TranslationModelProjectInitializer();
							if (!initializer.checkConfiguration(project).isOK()) {
								initializer.updateConfiguration(project, monitor);
							}
						}
					};
					ResourcesPlugin.getWorkspace().run(runnable, project, IWorkspace.AVOID_UPDATE, null);
						
				} catch (CoreException e) {
					logger.error("A CoreException during the translation migration occurred", e);
					throw new InterruptedException("DS Generator has stopped gracefully due to translation migration errors");
				}
			}
		}
	}

	private void buildProjects(IProject[] projects) throws InterruptedException, CoreException {
		// run the build on the projects (e.g. in order to execute VR data sync)
		for (IProject project : projects) {
			if(OfsCore.isOfsProject(project)) {
				project.build(IncrementalProjectBuilder.FULL_BUILD, null);
				OfsCore.getOfsProjectManager().getOfsProject(project).clearCache();
			}
		}
	}

	private void validateDependencies(IProject[] projects) throws InterruptedException {
		// check if all dependencies can be resolved
		IDependencyManager dependencyManager = OfsCore.getDependencyManager();

		Set<IProject> ofsProjects = new HashSet<IProject>();
		
		for (IProject project : projects) {
			if(OfsCore.isOfsProject(project)) {
				ofsProjects.add(project);
			}
		}
		dependencyManager.resolveDependencies(ofsProjects.toArray(new IProject[ofsProjects.size()]));
		
		for (IProject project : ofsProjects) {
			if(dependencyManager.hasUnresolvedDependencies(project)) {
				IContainerIdentifier[] identifiers = dependencyManager.getUnresolvedDependencies(project);
				logger.error("Project " + project.getName() + ": " + getErrorMessage(identifiers));
				throw new InterruptedException("DS Generator has stopped gracefully due to model dependency resolution errors");
			}
		}
	}
	
	private void validateDependencyModels(IProject[] projects) throws InterruptedException {
		IDependencyManager dependencyManager = OfsCore.getDependencyManager();
		for (IProject project : projects) {
			if(OfsCore.isOfsProject(project)) {
				IOfsProject ofsProject = OfsCore.getOfsProjectManager().getOfsProject(project);
				// check, if all dependencies are in the workspace or if we are using packaged models
				boolean dependsOnPackagedModels = false;
				for(IOfsModelContainer container : dependencyManager.getDependencies(ofsProject)) {
					if(!(container instanceof IOfsProject)) {
						dependsOnPackagedModels = true;
					}
				}

				if(dependsOnPackagedModels) {
					// in this case, we try to load the models and will notice, if any of them needs migration
					ModelResourceLookup lookup = new ModelResourceLookup(ofsProject, OfsCore.getRegisteredModelNames());
					for(IOfsModelResource modelResource : lookup.getAllOfsModelResources(IOfsProject.SCOPE_DEPENDENCY)) {
						try {
							modelResource.getEMFModel();
						} catch (IOException e) {
							logger.info(modelResource.getURI() + "cannot be read: " + e.getMessage());
						} catch (InvalidMetamodelVersionException e) {
							logger.error("" + e.getMessage());
							throw new InterruptedException("DS Generator has stopped gracefully due to problems in models of the dependencies.");
						}
					}
				}
			}
		}
	}

// DS-7127 has replaced this with com.odcgroup.workbench.generation.GenerationCommon.validate(Resource) ..
// Michael: I'm keeping this, which was already commented out before me (Alain?) just because I'm not sure if for DS TAP we may still want to re-activate this somehow one day, as its validators are not Xtext Validators (in DS T24 they all are - or should be, by now) 
//
//	private void validateProjects(IProject[] projects) throws InterruptedException {
//		boolean validationActive = true;
//		boolean terminate = false;
//		List<String> problems = new ArrayList<String>();		
//		for (IProject project : projects) {
//			if(OfsCore.isOfsProject(project)) {
//				try {
//					// just do a test if the validation plugin is installed, so that we do not do the
//					// lookup for nothing. If not available, a NoClassDefFoundException will be thrown here.
//					IBatchValidator validator = ValidationUtil.createBatchValidator();
//										
//					// DS-2248: If the validation plugin is active, run validation on all model files
//					IOfsProject ofsProject = OfsCore.getOfsProjectManager().getOfsProject(project);
//					ModelResourceLookup lookup = new ModelResourceLookup(ofsProject, OfsCore.getValidationModelNames());
//					final Set<IOfsModelResource> modelResources = lookup.getAllOfsModelResources(IOfsProject.SCOPE_ALL - IOfsProject.SCOPE_DEPENDENCY);
//					Timer validationTimer = new Timer().start();
//					logger.info("About to start validating {} models from project {}...", modelResources.size(), project.getName());
//					for(IOfsModelResource modelResource : modelResources) {
//						IStatus status = ValidationUtil.validate(modelResource, null, false);
//						if(status.getSeverity()==IStatus.ERROR) {
//							if(status.isMultiStatus()) {
//								for(IStatus child : status.getChildren()) {
//									if(child.getSeverity()==IStatus.ERROR) {
//										problems.add("Validation error: Project " + project.getName() + ", Resource " + modelResource.getURI().path() + ": " + child.getMessage() + exceptionDetails(child.getException()));
//									}
//								}
//							} else {
//								problems.add("Validation error: Project " + project.getName() + ", Resource " + modelResource.getURI().path() + ": " + status.getMessage() + exceptionDetails(status.getException()));
//							}
//							// DS-3842: if a flag is set on the project, we do not terminiate the generator execution on errors
//					        ProjectPreferences preferences = new ProjectPreferences(project, GenerationCore.PLUGIN_ID);
//				        	boolean ignoreErrors = preferences.getBoolean(IGNORE_VALIDATION_ERRORS, false);
//							if(!ignoreErrors) {
//								terminate = true;
//							}
//						}
//					}
//					// do NOT ofsProject.clearCache();
//					// This doesn't help for memory use (as it's needed anyways..)
//					// and would only cause everything to be loaded twice (once for Validation, and once for Generation)
//					logger.info("Done validating {} models in {}", modelResources.size(), validationTimer.stopAndText());
//				} catch(NoClassDefFoundError e) {
//					if(validationActive) {
//						logger.warn("Warning: Model validation is deactivated as validation plugin is not installed!");
//						validationActive = false;
//					}
//				}
//			}
//		}
//		if(problems.size()>0) {
//			for(String problem : problems) logger.error(problem);
//		}
//		if(terminate && !"true".equalsIgnoreCase(System.getProperty("ds.ignoreValidationErrors"))) {
//			throw new InterruptedException("DS Generator has stopped gracefully due to model validation errors");
//		}
//	}

	private String exceptionDetails(Throwable exception) {
		if (exception == null) {
			return "";
		}
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		exception.printStackTrace(pw);
		return "\nException details:\n" + sw.toString();
	}

	private void executeCodeGeneration(IProject[] projects) throws CoreException, InterruptedException {
		CodeGenInitializer initializer = new CodeGenInitializer();
		for (IProject project : projects) {
			if(OfsCore.isOfsProject(project)) {
				logger.info("Generating code for project '"	+ project.getName() + "' ...");
				if(!initializer.checkConfiguration(project).isOK()) {
					initializer.initialize(project, true, new NullProgressMonitor());
				}
				
				IStatus status = initializer.checkConfiguration(project);
				handleStatus(status);

				List<IResource> resources = ModelsHelper.collectModelResources(project);
				Set<IFolder> foldersToRefresh = new HashSet<IFolder>();
				IProgressMonitor monitor = new NullProgressMonitor();
				status = new GenerationCommon().doGenerationWithIResources(project, resources, foldersToRefresh, monitor);
				handleStatus(status);
				
				OfsCore.getOfsProjectManager().getOfsProject(project).clearCache();
			}
			logger.info("Code generation for project '" + project.getName() + "' done.");
		}
	}

	private void handleStatus(IStatus status) throws CoreException {
		if(!status.isOK()) {
			GenerationCore.getDefault().getLog().log(status);
			throw new CoreException(status);			
		}
	}

	private void stopBackgroundJobExecution() throws InterruptedException {
		IJobManager jobManager = WorkspaceJob.getJobManager();
		Job job = jobManager.currentJob();
		if(job!=null && job.getThread()!=Thread.currentThread()) {
			job.join();
		}
		jobManager.beginRule(ResourcesPlugin.getWorkspace().getRoot(), null);
		jobManager.suspend();
	}

	private void allowBackgroundJobExecution() throws InterruptedException {
		IJobManager jobManager = WorkspaceJob.getJobManager();
		jobManager.endRule(ResourcesPlugin.getWorkspace().getRoot());
		jobManager.resume();
	}

	public void stop() {
	}

	/**
	 * Imports the given path into the workspace as a project. Returns true if
	 * the operation succeeded, false if it failed to import due to an overlap.
	 * 
	 * @param projectPath
	 * @return true if project has been successfully imported
	 * @throws CoreException
	 *             if operation fails catastrophically
	 */
	private boolean importExistingProject(IPath projectPath)
			throws CoreException {

		IWorkspace workspace = ResourcesPlugin.getWorkspace();

		// Load the project description file
		final IProjectDescription description;
		try {
			description = workspace
					.loadProjectDescription(projectPath.append(IPath.SEPARATOR
							+ IProjectDescription.DESCRIPTION_FILE_NAME));
		} catch (Exception e) {
			logger.warn("No Eclipse .project, ignoring: {}", projectPath);
			return false;
		}
		final IProject project = workspace.getRoot().getProject(
				description.getName());

		// Only import the project if it doesn't appear to already exist. If it
		// looks like it exists, tell the user about it.
		if (project.exists()) {
			logger.warn("Project '" + project.getName()
					+ "' already exists in workspace!");
			return false;
		}
		IWorkspaceRunnable runnable = new IWorkspaceRunnable() {
			public void run(IProgressMonitor monitor) throws CoreException {
				project.create(description, monitor);
				project.open(IResource.NONE, monitor);
			}
		};
		workspace.run(runnable, workspace.getRuleFactory().modifyRule(
				workspace.getRoot()), IWorkspace.AVOID_UPDATE, null);
		return true;
	}

	private String getErrorMessage(IContainerIdentifier[] identifiers) {
		StringBuffer sb = new StringBuffer();
		if (identifiers.length==1) {
			sb.append("Could not resolve " + identifiers[0].toString());
		}
		if (identifiers.length>1) {
			sb.append("Unresolved dependencies ");
			for(IContainerIdentifier id : identifiers)
				sb.append(" " + id + " ");
			sb.append("found.");
		}
		return sb.toString();
	}
	
}
