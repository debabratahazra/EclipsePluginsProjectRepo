package com.odcgroup.workbench.core;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.eclipse.core.internal.runtime.InternalPlatform;
import org.eclipse.core.resources.ICommand;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.emf.common.EMFPlugin;
import org.osgi.framework.BundleContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;

import com.odcgroup.workbench.core.helper.FeatureSwitches;
import com.odcgroup.workbench.core.init.DefaultModelProjectInitializer;
import com.odcgroup.workbench.core.init.OfsProjectInitializer;
import com.odcgroup.workbench.core.init.ProjectInitializer;
import com.odcgroup.workbench.core.internal.logging.EclipseLogListener;
import com.odcgroup.workbench.core.internal.repository.DependencyManagerFactory;
import com.odcgroup.workbench.core.internal.repository.OfsProjectManager;
import com.odcgroup.workbench.core.logging.LoggingConstants;
import com.odcgroup.workbench.core.repository.IDependencyManager;
import com.odcgroup.workbench.core.repository.IOfsProjectManager;

/**
 * The activator class controls the plug-in life cycle
 */
public class OfsCore extends AbstractActivator {

	// logger name = class name
	private static final Logger logger = LoggerFactory.getLogger(OfsCore.class);	

	// the messages resource bundle
    private static final String BUNDLE_NAME = "com.odcgroup.workbench.core.messages"; //$NON-NLS-1$
	
    public static final String MODEL_EXTENSION_ID = "com.odcgroup.workbench.core.model";
	public static final String IMPORTER_EXTENSION_ID = "com.odcgroup.workbench.core.importer";
	public static final String NATUREEXT_EXTENSION_ID = "com.odcgroup.workbench.core.natureExtension";
	public static final String DEPENDENCY_MANAGER_ID = "com.odcgroup.workbench.core.dependencyManager";
	public static final String INITIALIZER_EXTENSION_ID = "com.odcgroup.workbench.core.initializer";
	
	// The plug-in ID
	public static final String PLUGIN_ID  = "com.odcgroup.workbench.core";
    public static final String NATURE_ID  = PLUGIN_ID + ".OfsNature"; //$NON-NLS-1$
    public static final String BUILDER_ID = PLUGIN_ID + ".OfsBuilder"; //$NON-NLS-1$
    public static final String MARKER_ID  = PLUGIN_ID + ".OfsProblem"; //$NON-NLS-1$

    // m2eclipse nature and builder ids
	public static final String M2ECLIPSE_NATURE = "org.eclipse.m2e.core.maven2Nature";
    public static final String M2ECLIPSE_BUILDER = "org.eclipse.m2e.core.maven2Builder";
    public static final String M2ECLIPSE_CLASSPATH_CONTAINER = "org.eclipse.m2e.MAVEN2_CLASSPATH_CONTAINER";
    public static final Path M2ECLIPSE_CLASSPATH_CONTAINER_PATH = new Path(M2ECLIPSE_CLASSPATH_CONTAINER);

	public static final String TRACE_DEPMGMT = PLUGIN_ID + "/debug/dependencymanagement"; //$NON-NLS-1$

    // qualified name to identify folders that contain model files
    public static final QualifiedName MODEL_NAME =
    	new QualifiedName("com.odcgroup.workbench", "modelName");
    		
	// marker used for SLF4J logging to identify the plugin
    public static final Marker LOG_MARKER = createBundleMarker();
    
	private EclipseLogListener eclipseLogListener = null;
    
    protected String getResourceBundleName() {
    	return BUNDLE_NAME;
    }
    
    public static OfsCore getDefault() {
    	return (OfsCore) getDefault(OfsCore.class);
    }

	/**
	 * Starts the Plugin.
	 * 
	 * @param context The BundleContext
	 * @throws Exception
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		SLF4JBridgeHandler.install();
		logger.debug("SLF4JBridgeHandler started"); //$NON-NLS-1$
		eclipseLogListener = new EclipseLogListener();
		InternalPlatform.getDefault().addLogListener(eclipseLogListener);
		logger.debug("Eclipse console log has been forwarded to SLF4J"); //$NON-NLS-1$
		
		// This line is just so that the static Feature fields in FeatureSwitches get initialized, and the respective log messages print out early on during startup..  
		FeatureSwitches.class.getName();
		
/* deactivating this as the logging takes too much time since xtext upgrade
		final Logger jobManagerLogger = LoggerFactory.getLogger("JobManagerMonitoring");
		jobManagerLogger.info("###################################################");
		jobManagerLogger.info("# New Job Manager Monitoring session starting ... #");
		jobManagerLogger.info("###################################################");
		Job.getJobManager().addJobChangeListener(new IJobChangeListener() {
			
			@Override
			public void scheduled(IJobChangeEvent event) {
				logDebugEvent("scheduled ", event);
				logCaller(event);
			}

			@Override
			public void sleeping(IJobChangeEvent event) {
				logDebugEvent("sleeping  ", event);
			}
			
			@Override
			public void running(IJobChangeEvent event) {
				logInfoEvent("running   ", event);
			}
			
			@Override
			public void done(IJobChangeEvent event) {
				logDebugEvent("done      ", event);
			}
			
			@Override
			public void awake(IJobChangeEvent event) {
				logDebugEvent("awake     ", event);
			}
			
			@Override
			public void aboutToRun(IJobChangeEvent event) {
				logDebugEvent("aboutToRun", event);
			}
			
			private void logDebugEvent(String eventType, IJobChangeEvent event) {
				Job job = event.getJob();
				if (job == null) {
					return;
				}

				if (jobManagerLogger.isDebugEnabled()) {
					jobManagerLogger.debug(logEvent(eventType, job));
				}
			}
			
			private void logInfoEvent(String eventType, IJobChangeEvent event) {
				Job job = event.getJob();
				if (job == null) {
					return;
				}

				if (jobManagerLogger.isInfoEnabled()) {
					jobManagerLogger.info(logEvent(eventType, job));
				}
			}

			private String logEvent(String eventType, Job job) {
				StringBuffer sb = new StringBuffer(eventType);
				sb.append("-> \"");
				sb.append(job.getName());
				
				sb.append("\" (");
				sb.append(job.getClass());
				sb.append(")");
				
				ISchedulingRule rule = job.getRule();
				if (rule==null) {
					sb.append(" with NO rule");
				} else {
					sb.append(" with rule \"");
					sb.append(rule.toString());
					sb.append("\" (");
					sb.append(rule.getClass());
					sb.append(")");
				}
				return sb.toString();
			}
			
			private void logCaller(IJobChangeEvent event) {
				if (jobManagerLogger.isTraceEnabled()) {
					StringWriter sw = new StringWriter();
					new Exception("Use this stacktrace to find out what has generated this event").printStackTrace(new PrintWriter(sw));
					jobManagerLogger.trace(sw.toString());
				}
			}
		});
		*/
	}

	/**
	 * Stops the Plugin.
	 * 
	 * @param context The BundleContext
	 * @throws Exception
	 */
	public void stop(BundleContext context) throws Exception {
		super.stop(context);
		InternalPlatform.getDefault().removeLogListener(eclipseLogListener);
	}

    private static final Marker createBundleMarker() {
    	Marker bundleMarker = MarkerFactory.getMarker(PLUGIN_ID);
    	bundleMarker.add(MarkerFactory.getMarker(LoggingConstants.IS_BUNDLE_MARKER));
    	return bundleMarker;
    }

	/**
	 * Retrieves a Boolean value indicating whether tracing is enabled for the
	 * specified debug option.
	 * 
	 * @return Whether tracing is enabled for the debug option of the plug-in.
	 * @param option The debug option for which to determine trace enablement.
	 * 
	 */
	static public boolean shouldTrace(String option) {
		boolean trace = false;
		if(getDefault().isDebugging()) {
			trace = isTraceOptionEnabled(option);
		}
		return trace;
	}


    /**
     * This method checks all extension for the extension point com.odcgroup.workbench.model
     * and returns an string array of their names.
     * @return the names of all registered models
     */
    public static String[] getRegisteredModelNames() {
    
		ArrayList<String> models = new ArrayList<String>();

		IExtensionRegistry registry = Platform.getExtensionRegistry();
		IExtensionPoint point = registry.getExtensionPoint(MODEL_EXTENSION_ID);
		if (point == null) return new String[0];
    	
		// iterate over all defined models
		for(IConfigurationElement configElement : point.getConfigurationElements()) {
			models.add(configElement.getAttribute("name"));
		}
		return models.toArray(new String[0]);
	}


    /**
     * This method checks all extension for the extension point com.odcgroup.workbench.model
     * and returns an string array of the model types for which the validation attribute is set to true.
     * 
     * @return the names of all registered models to include in validation
     */
    public static String[] getValidationModelNames() {
		ArrayList<String> models = new ArrayList<String>();

		IExtensionRegistry registry = Platform.getExtensionRegistry();
		IExtensionPoint point = registry.getExtensionPoint(MODEL_EXTENSION_ID);
		if (point == null) return new String[0];
    	
		// iterate over all defined models
		for(IConfigurationElement configElement : point.getConfigurationElements()) {
			String validation = configElement.getAttribute("validation");
			if(validation==null || validation.equals("true")) {
				String modelName = configElement.getAttribute("name");
				models.add(modelName);
				// FIXME: Workaround as long as we still treat mml files as domain models
				if(modelName.equals("domain")) {
					models.add("mml");
				}
			}
		}
		return models.toArray(new String[0]);
	}

    /**
     * This method checks all extension for the extension point com.odcgroup.workbench.model
     * and returns an string array of the model types for which the dependencies should be loaded eagerly.
     * 
     * @return the names of all registered models to be loaded eagerly.
     */
    public static String[] getEagerlyLoadedModelNames() {
		ArrayList<String> models = new ArrayList<String>();

		IExtensionRegistry registry = Platform.getExtensionRegistry();
		IExtensionPoint point = registry.getExtensionPoint(MODEL_EXTENSION_ID);
		if (point == null) return new String[0];
    	
		// iterate over all defined models
		for(IConfigurationElement configElement : point.getConfigurationElements()) {
			String proxyresolution = configElement.getAttribute("proxyresolution");
			if(proxyresolution!=null && proxyresolution.equals("eager")) {
				String modelName = configElement.getAttribute("name");
				models.add(modelName);
				// FIXME: Workaround as long as we still treat mml files as domain models
				if(modelName.equals("domain")) {
					models.add("mml");
				}
			}
		}
		return models.toArray(new String[0]);
	}

    /** 
     * This method returns all extensions for a given extension point
     * 
     * @param extensionId the extension point ID
     * @return an array of extensions 
     */
	public static IConfigurationElement[] getExtensions(String extensionId) {
		IExtensionRegistry registry = Platform.getExtensionRegistry();
		IExtensionPoint point = registry.getExtensionPoint(extensionId);
		if (point == null) return new IConfigurationElement[0];
		return point.getConfigurationElements();
	}
		
	public static boolean isOfsProject(IProject project) {
		if(project==null || !project.isOpen()) return false;
		try {
			if(project.getNature(OfsCore.NATURE_ID) != null) {
				return true;
			}
		} catch (CoreException e) {
			return false;
		}
		return false;
	}

	public static boolean isOfsModelPackage(IResource folder) {
		if(!(folder instanceof IFolder)) return false;
		if(!isOfsProject(folder.getProject())) return false;
		
		if(folder.getParent() instanceof IFolder) {
			IFolder parentFolder = (IFolder) folder.getParent();
			if(isOfsModelPackage(parentFolder)) {
				return true;
			}
		}
		return getFolderModelName((IFolder)folder)!=null;
	}
	
	public static String getFolderModelName(IResource folder) {
		if(!isOfsProject(folder.getProject())) return null;
		if(!(folder instanceof IFolder)) return null;
		if (!folder.exists()) return null;
		
		if(folder.getParent() instanceof IFolder) {
			IFolder parentFolder = (IFolder) folder.getParent();
			if(isOfsModelPackage(parentFolder)) {
				return getFolderModelName(parentFolder);
			} else {
				return null;
			}
		}
		if(Arrays.asList(getRegisteredModelNames()).contains(folder.getName())) {
			return folder.getName();
		} else {
			return null;
		}
	}
		
	/**
	 * Helper method which creates a folder and, recursively, all its parent
	 * folders.
	 * 
	 * @param folder
	 *            The folder to create.
	 * 
	 * @throws CoreException
	 *             if creating the given <code>folder</code> or any of its
	 *             parents fails.
	 */
	public static void createFolder(IFolder folder)
			throws CoreException {
		// Recurse until we find a parent folder which already exists.
		if (!folder.exists()) {
			IContainer parent = folder.getParent();
			// First, make sure that all parent folders exist.
			if (parent instanceof IFolder) {
				createFolder((IFolder) parent);
			}
			folder.create(true, true, null);
		}
	}
		
	public static void addProjectBuilder(IProject project, String builderId) throws CoreException {
		addProjectBuilder(project, builderId, null);
	}

	public static void addProjectBuilder(IProject project, String builderId, Map args) throws CoreException {
		   IProjectDescription desc;
				desc = project.getDescription();
		   ICommand[] commands = desc.getBuildSpec();
		   boolean found = false;
	
		   for (int i = 0; i < commands.length; ++i) {
		      if (commands[i].getBuilderName().equals(builderId)) {
		         found = true;
		         break;
		      }
		   }
		   if (!found) { 
			  // remove read-only flag from .project file if necessary
			  IFile projectFile = project.getFile(desc.DESCRIPTION_FILE_NAME);
			  if(projectFile.isReadOnly()) projectFile.setReadOnly(false);

			  //add builder to project
		      ICommand command = desc.newCommand();
		      command.setBuilderName(builderId);
		      if(args!=null) command.setArguments(args);
		      ICommand[] newCommands = new ICommand[commands.length + 1];
	
		      // Add it after other builders.
		      System.arraycopy(commands, 0, newCommands, 0, commands.length);
		      newCommands[commands.length] = command;
		      desc.setBuildSpec(newCommands);
		      project.setDescription(desc, null);
		   }
	}

	/**
	 * adds a project builder at the top of the builder list instead of the end as usually.
	 * 
	 * @param project the project to add the builder to
	 * @param builderId the builder id
	 * @throws CoreException
	 */
	public static void addProjectBuilderAtTop(IProject project, String builderId) throws CoreException {
		addProjectBuilderAtTop(project, builderId, null);
	}

	/**
	 * adds a project builder at the top of the builder list instead of the end as usually.
	 * 
	 * @param project the project to add the builder to
	 * @param builderId the builder id
	 * @param args builder arguments
	 * @throws CoreException
	 */
	public static void addProjectBuilderAtTop(IProject project, String builderId, Map args) throws CoreException {
		   IProjectDescription desc;
				desc = project.getDescription();
		   ICommand[] commands = desc.getBuildSpec();
		   boolean found = false;
	
		   for (int i = 0; i < commands.length; ++i) {
		      if (commands[i].getBuilderName().equals(builderId)) {
		         found = true;
		         break;
		      }
		   }
		   if (!found) { 
			  // remove read-only flag from .project file if necessary
			  IFile projectFile = project.getFile(desc.DESCRIPTION_FILE_NAME);
			  if(projectFile.isReadOnly()) projectFile.setReadOnly(false);

			  //add builder to project
		      ICommand command = desc.newCommand();
		      command.setBuilderName(builderId);
		      if(args!=null) command.setArguments(args);
		      ICommand[] newCommands = new ICommand[commands.length + 1];
	
		      // Add it after other builders.
		      System.arraycopy(commands, 0, newCommands, 1, commands.length);
		      newCommands[0] = command;
		      desc.setBuildSpec(newCommands);
		      project.setDescription(desc, null);
		   }
	}

	public static void removeProjectBuilder(IProject project, String builderId) throws CoreException {
		   IProjectDescription desc;
				desc = project.getDescription();
		   ICommand[] commands = desc.getBuildSpec();
		   int foundIndex = -1;
	
		   for (int i = 0; i < commands.length; ++i) {
		      if (commands[i].getBuilderName().equals(builderId)) {
		         foundIndex = i;
		         break;
		      }
		   }
		   if (foundIndex>=0) { 
		      ICommand[] newCommands = new ICommand[commands.length - 1];
	
		      for(int i=0; i<foundIndex; i++) {
		    	  newCommands[i] = commands[i];
		      }
		      for(int i=foundIndex; i<commands.length-1; i++) {
		    	  newCommands[i] = commands[i+1];
		      }
		      desc.setBuildSpec(newCommands);
		      project.setDescription(desc, null);
		   }
	}

	public static boolean hasProjectBuilder(IProject project, String builderId) {
		   IProjectDescription desc;
			try {
				desc = project.getDescription();
			} catch (CoreException e) {
				return false;
			}
		   ICommand[] commands = desc.getBuildSpec();
		   int foundIndex = -1;
	
		   for (int i = 0; i < commands.length; ++i) {
		      if (commands[i].getBuilderName().equals(builderId)) {
		         return true;
		      }
		   }
		   return false;
	}

	public static ProjectInitializer[] getProjectInitializers(IProject project, boolean onlyEnabled) {
		
		List<ProjectInitializer> initializers = new ArrayList<ProjectInitializer>();

		// first add the main initializer
		initializers.add(new OfsProjectInitializer());
		
		// add all registered model initializers
		IConfigurationElement[] modelExtensions = getExtensions(MODEL_EXTENSION_ID);
		for(IConfigurationElement ext : modelExtensions) {
			ProjectInitializer initializer = null;
			try {
				initializer = (ProjectInitializer) ext.createExecutableExtension("initializer");
				initializer.setMarkerId(OfsCore.MARKER_ID);
			} catch(Exception e) {
				// no initializer defined, therefore use default
				String folderName = ext.getAttribute("name");
				initializer = new DefaultModelProjectInitializer(folderName);
			}
			initializers.add(initializer);
		}

		// add all registered nature extensions for active natures of this project
		final IConfigurationElement[] natureExtensions = OfsCore.getExtensions(OfsCore.NATUREEXT_EXTENSION_ID);
		for(IConfigurationElement ext : natureExtensions) {
			String natureId = ext.getAttribute("natureId");
			try {
				if(project.hasNature(natureId)) {
					ProjectInitializer initializer = null;
					try {
						initializer = 
								(ProjectInitializer) ext.createExecutableExtension("initializer");
						String markerId = ext.getAttribute("markerId");
						initializer.setMarkerId(markerId==null ? OfsCore.MARKER_ID : markerId);
						initializers.add(initializer);
					} catch(Exception e) {
						// ignore if there is no initializer
					}
				}
			} catch (CoreException e) {
				// ignore if nature id cannot be checked on project
			}
		}

		// add all other registered project initializers
		final IConfigurationElement[] extensions = OfsCore.getExtensions(OfsCore.INITIALIZER_EXTENSION_ID);
		for(IConfigurationElement ext : extensions) {
			ProjectInitializer initializer = null;
			try {
				initializer = 
						(ProjectInitializer) ext.createExecutableExtension("class");
				String markerId = ext.getAttribute("markerId");
				initializer.setMarkerId(markerId==null ? OfsCore.MARKER_ID : markerId);
				initializers.add(initializer);
			} catch(Exception e) {
				OfsCore.getDefault().logWarning("Cannot instantiate project initializer " + ext.getAttribute("id"), e);
			}
		}

		return initializers.toArray(new ProjectInitializer[initializers.size()]);
	}

	public static String getVersionNumber() {
		String version = (String) getDefault().getBundle().getHeaders().get("Bundle-Version");
		if(version.endsWith("qualifier")) {
			DateFormat format = new SimpleDateFormat("yyyyMMdd");
			String qualifier = format.format(new Date()) + "9999"; // to be more recent than any model of today
			version = version.substring(0, version.length() - "qualifier".length()) + qualifier;
		}
		return version;
	}

	public static String getCurrentMetaModelVersion(String modelType) {
		// This makes it possible to quickly run some tests as non-OSGi JUnit..
		if (!EMFPlugin.IS_ECLIPSE_RUNNING) {
			final String msg = "OfsCore.getCurrentMetaModelVersion(\"" + modelType + "\") returns '0.0.0' because we're running as JavaSE standalone (non-OSGi)";
			logger.warn(msg);
			System.out.println("\n\n" + msg + "\n\n");
			return "0.0.0";
		}
		Object adapter = Platform.getAdapterManager().loadAdapter(modelType, IMetaModelVersioned.class.getName());
		if(adapter instanceof IMetaModelVersioned) {
			IMetaModelVersioned metamodelVersioned = (IMetaModelVersioned) adapter;
			return metamodelVersioned.getCurrentMetaModelVersion();
		} else {
			// fallback, if there is no metamodel version handler registered
			return "1.30.6";
		}
	}

	/**
	 * This method provides access to the IOfsProjectManager
	 * 
	 * @return the IOfsProjectManager singleton instance
	 */
	public static IOfsProjectManager getOfsProjectManager() {
		return OfsProjectManager.getInstance(); 
	}
	
	/**
	 * Returns the singleton ofs model project for a given project
	 * 
	 * @param project
	 *            the project for which the ofs model project is requested
	 * @return the singleton instance of the ofs model project
	 */
	public static IOfsProject getOfsProject(IProject project) {
		return OfsProjectManager.getInstance().getOfsProject(project);
	}

	/**
	 * This method provides a unique access for external bundles to the IDependencyManager.
	 * The default implementation is always returned by this method.
	 * 
	 * @return an IDependencyManager instance
	 */
	public static IDependencyManager getDependencyManager() {
		return DependencyManagerFactory.getDependencyManager(); 
	}

	/**
	 * This method provides a unique access for external bundles to the IDependencyManager.
	 * This implementation might be a project specific implementation (e.g. for custo projects)
	 * 
	 * @return an IDependencyManager instance for the IProject
	 */
	public static IDependencyManager getDependencyManager(IProject project) {
		return DependencyManagerFactory.getDependencyManager(project); 
	}

	/**
	 * This static method is used to add a nature to a project if it's not already defined
	 * @param project the project on which you want to add a nature if not already defined
	 * @param natureId the nature to add to the project
	 */
	public static void addNature(IProject project, String natureId) throws CoreException {
		
		IProjectDescription desc = project.getDescription();
		ArrayList<String> natureIds = new ArrayList<String>(Arrays.asList(desc.getNatureIds()));
		if(!natureIds.contains(natureId)) {
			natureIds.add(natureId);
			desc.setNatureIds(natureIds.toArray(new String[0]));
			project.setDescription(desc, new NullProgressMonitor());
		}	
	}

	/**
	 * This static method is used to remove a nature from a project if it exists
	 * @param project the project from which you want to remove the nature
	 * @param natureId the nature to remove from the project
	 */
	public static void removeNature(IProject project, String natureId) throws CoreException {
		
		IProjectDescription desc = project.getDescription();
		ArrayList<String> natureIds = new ArrayList<String>(Arrays.asList(desc.getNatureIds()));
		if(natureIds.contains(natureId)) {
			natureIds.remove(natureId);
			desc.setNatureIds(natureIds.toArray(new String[0]));
			project.setDescription(desc, new NullProgressMonitor());
		}	
	}
	
	public static String getForcedLocalRepository() {
		return System.getProperty("maven.repo.local");		
	}

}
