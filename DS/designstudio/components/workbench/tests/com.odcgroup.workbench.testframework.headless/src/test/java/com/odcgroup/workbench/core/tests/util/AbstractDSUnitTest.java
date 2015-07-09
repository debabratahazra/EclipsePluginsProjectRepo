package com.odcgroup.workbench.core.tests.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.apache.commons.lang.StringUtils;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.EMFPlugin;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.xtext.junit4.ui.util.IResourcesSetupUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import com.odcgroup.workbench.core.IOfsModelResource;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.core.OfsCore;
import com.odcgroup.workbench.core.init.OfsProjectInitializer;
import com.odcgroup.workbench.core.init.ProjectInitializer;
import com.odcgroup.workbench.testframework.headless.TestFrameworkHeadlessCore;

/**
 * @author yan
 */
@SuppressWarnings("restriction")
abstract public class AbstractDSUnitTest {
	private static Logger logger = LoggerFactory.getLogger(AbstractDSUnitTest.class);

	public static final String DEFAULT_MODELS_PROJECT = "default-models";
	public static final String DEFAULT_CUSTO_MODELS_PROJECT = "default-custo-models";
	
	protected static final String RESOURCES_FOLDER = "resources/test-tank-models";
	protected static final String PMS_MODELS_RESOURCE_FOLDER = "resources/pms-models";
	
	protected static final String DOMAIN_LOCATION = "domain";
	protected static final String FRAGMENT_LOCATION = "fragment";
	protected static final String DEFAULT_MODULE_LOCATION = "module/Default/module";
	protected static final String MODULE_LOCATION = "module";
	protected static final String PAGEFLOWS_LOCATION = "pageflow";
	protected static final String PAGE_LOCATION = "page";
	protected static final String DEFAULT_PAGE_LOCATION = "page/Default/activity";
	protected static final String WORKFLOWS_LOCATION = "workflow";
	protected static final String RIM_LOCATION = "rim/rim";

	private static final int MAX_DELETE_PROJECT_RETRIES = 5;
	private static final int MAX_JAVA_NATURE_RETRIES = 5;
	private static final int MAX_CHECK_ABAILABILITY_RETRIES = 5;
	private static final long DELAY_BETWEEN_RETRIES = 2000;
	
	protected Map<String, IOfsProject> ofsProjects = new HashMap<String, IOfsProject>();
	
	protected AbstractDSUnitTest () {
		if (!EMFPlugin.IS_ECLIPSE_RUNNING)
			throw new IllegalStateException("You cannot run this as non-OSGi SE test.. (and, Michael, don't waste your time trying a 3rd time; the IOfsProject/IProject/IFile/Workbench are too tightly coupled to OSGi..)");
	}
	
	private IProject privateGetIProject(String projectName) {
		return ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
	}
	
    /**
     * Create a models project with a default name
     * @return the IOfsProject
     */
    public IOfsProject createModelsProject() {
    	return createNamedModelsProject(DEFAULT_MODELS_PROJECT);
    }
    
    /**
     * Create a model project with a specific name
     * @param modelProjectName
     * @return the IOfsProject 
     */
    public IOfsProject createNamedModelsProject(String modelProjectName) {
    	try {
    		// Cleanup before creating a project to ensure it doesn't 
    		// already exists
       		deleteModelsProject(modelProjectName);
       		
    		// Create the Project
	    	IProject project = privateGetIProject(modelProjectName);
	        project.create(null);
	        project.open(null);
	        updateProjectConfigurations(project);

	        // Create the OfsProject
	        IOfsProject ofsProject = OfsCore.getOfsProjectManager().getOfsProject(project);
	        ofsProjects.put(modelProjectName, ofsProject);
	        
	        // Create the models folders
	        for (String folderName : new String[] {
	        		DOMAIN_LOCATION, 
	        		FRAGMENT_LOCATION, 
	        		DEFAULT_MODULE_LOCATION, 
	        		MODULE_LOCATION, 
	        		PAGEFLOWS_LOCATION,
	        		PAGE_LOCATION, 
	        		DEFAULT_PAGE_LOCATION,
	        		WORKFLOWS_LOCATION,
	        		RIM_LOCATION}) {
		        IFolder folder = project.getFolder(folderName);
		        OfsCore.createFolder(folder);
	        }
	        
	        waitForJavaProjectNature(project);
	        
	        return ofsProject;
    	} catch (CoreException e) {
    		throw new RuntimeException("Unable to create " + modelProjectName + " models project", e);
    	}
    }
    
    protected void waitForJavaProjectNature(IProject project) {
		for (int i=0; i<MAX_JAVA_NATURE_RETRIES; i++) {
    		try {
    			IJavaProject javaProject = JavaCore.create(project);
    			if (javaProject != null ) {
					return;
				} 
    		} catch(Exception e) {
				logger.warn("Hasn't the JavaNature. Attempt #" + (i+1) + ".");
    		}
    		try {
				Thread.sleep(DELAY_BETWEEN_RETRIES);
			} catch (InterruptedException e) {
				break;
			}
    	}
    	throw new RuntimeException("The project " + project.getName() + " has no JavaNature after retrying " + MAX_DELETE_PROJECT_RETRIES + " times.");
    }
    
    protected void updateProjectConfigurations(final IProject project) throws CoreException {		
		IWorkspaceRunnable op = new IWorkspaceRunnable() {
			public void run(IProgressMonitor monitor) throws CoreException {
				for (ProjectInitializer initializer : OfsCore.getProjectInitializers(project, false)) {
					initializer.updateConfiguration(project, null);
				}
			}
		};
		ResourcesPlugin.getWorkspace().run(op, null);
    }
    
    /**
     * Create a custo models project with a default name
     * @return the IOfsProject
     */
    public IOfsProject createCustoModelsProject() {
    	return createNamedCustoModelsProject(DEFAULT_CUSTO_MODELS_PROJECT);
    }
    
    /**
     * Create a custo model project with a specific name
     * @param modelProjectName
     * @return the IOfsProject 
     */
    public IOfsProject createNamedCustoModelsProject(String custoModelProjectName) {
    	try {
    		// Cleanup before creating a project to ensure it doesn't 
    		// already exists
        	deleteModelsProject(custoModelProjectName);
        	
    		// Create the Project
	    	IProject project = privateGetIProject(custoModelProjectName);
	        project.create(null);
	        project.open(null);

	        // Create the CustoProject
	        IProjectDescription descr = project.getDescription();
			descr.setNatureIds(new String[] { OfsCore.NATURE_ID, "com.odcgroup.workbench.custo.core.OdysseyCustomNature"});
			project.setDescription(descr, null);
	        new OfsProjectInitializer().updateConfiguration(project, null);
	        IOfsProject ofsProject = OfsCore.getOfsProjectManager().getOfsProject(project);
	        ofsProjects.put(custoModelProjectName, ofsProject);
	        
	        // Create the models folders
	        for (String folderName : new String[] {
	        		DOMAIN_LOCATION, 
	        		FRAGMENT_LOCATION, 
	        		DEFAULT_MODULE_LOCATION, 
	        		MODULE_LOCATION, 
	        		PAGEFLOWS_LOCATION,
	        		PAGE_LOCATION, 
	        		DEFAULT_PAGE_LOCATION,
	        		WORKFLOWS_LOCATION,
	        		RIM_LOCATION}) {
		        IFolder folder = project.getFolder(folderName);
		        OfsCore.createFolder(folder);
	        }
	        return ofsProject;
    	} catch (CoreException e) {
    		throw new RuntimeException("Unable to create " + custoModelProjectName + " custo models project", e);
    	}
    }
    
    /**
     * Delete the models project (should be done in the tearDown
     * of the unit test if the creation was done in the setUp)
     */
    public void deleteModelsProjects() {
    	RuntimeException firstException = null;
    	
    	for (IOfsProject ofsProject : ofsProjects.values()) {
    		RuntimeException possibleFirstException = null;
    		for (int i=0; i<MAX_DELETE_PROJECT_RETRIES; i++) {
    			try {
    				ofsProject.getProject().delete(true, null);
    				waitForProjectDeletion(ofsProject.getProject());
    				possibleFirstException = null;
    				break;
    			} catch (Exception e) {
    				// try to remove as much projects as possible
    				if (possibleFirstException == null) {
    					possibleFirstException = new RuntimeException("Unable to delete the " + ofsProject.getName() + " project.", e);
    				}
    				logger.error("Unable to delete " + ofsProject.getName(), e);
    				try {
						Thread.sleep(DELAY_BETWEEN_RETRIES);
					} catch (InterruptedException e1) {
						// Ignore
					}
    			}
    		}
    		firstException = possibleFirstException;
    	}
    	ofsProjects.clear();
    	if (firstException != null) {
    		throw firstException;
    	}
    }
    
    /**
     * Delete silently a models project
     * @param projectName
     * @throws CoreException 
     * @throws CoreException 
     */
    public void deleteModelsProject(String projectName) throws CoreException {
    	CoreException thrownException = null;
		for (int i=0; i<MAX_DELETE_PROJECT_RETRIES; i++) {
    		try {
    			if (ofsProjects.containsKey(projectName)) {
    				ofsProjects.get(projectName).getProject().delete(true, null);
    				ofsProjects.remove(projectName);
    			} else {
    				IProject project = privateGetIProject(projectName);
    				if (project != null && project.exists()) {
    					project.delete(true, null);
        				waitForProjectDeletion(project);
    				}
    			}
    			thrownException = null;
    			break;
			} catch (CoreException e) {
				thrownException = e;
				try {
					Thread.sleep(DELAY_BETWEEN_RETRIES);
				} catch (InterruptedException e1) {
					// Ignore
				}
    		}
    	}
		
		if (thrownException != null) {
			throw thrownException;
		}
    }
    
    private void waitForProjectDeletion(IProject project) {
		for (int i=0; i<MAX_DELETE_PROJECT_RETRIES; i++) {
			if (!project.exists()) {
				return;
			}
			
			try {
				Thread.sleep(DELAY_BETWEEN_RETRIES);
			} catch (InterruptedException e1) {
				// Ignore
			}
		}
		throw new IllegalStateException("The project " + project.getName() + " still exists after a delete...");
    }

    /**
     * @return the IProject of the default models project
     */
    public IProject getProject() {
    	return getProject(DEFAULT_MODELS_PROJECT);
    }

    /**
     * @return the IProject of the default custo models project
     */
    public IProject getCustoProject() {
    	return getProject(DEFAULT_CUSTO_MODELS_PROJECT);
    }

    /**
     * Return the project named projectName
     * @param projectName
     * @return the project named projectName
     */
    public IProject getProject(String projectName) {
    	return getOfsProject(projectName).getProject();
    }

    /**
     * @return the IOfsProject of the default models project
     */
    public IOfsProject getOfsProject() {
    	return getOfsProject(DEFAULT_MODELS_PROJECT);
    }

    /**
     * Return the IOfsProject of the project named projectName
     * @param projectName
     * @return the IOfsProject of the project named projectName
     */
    public IOfsProject getOfsProject(String projectName) {
    	if (ofsProjects.containsKey(projectName)) {
    		return ofsProjects.get(projectName);
    	} else {
    		throw new RuntimeException("No " + projectName + " project found");
    	}
    }
    
    public final IFolder getModelFolder(IProject ofsProject, String name) {
    	return ofsProject.getProject().getFolder(name);
    }

    public final IFolder getModelFolder(IOfsProject ofsProject, String name) {
    	return getModelFolder(ofsProject.getProject(), name);
    }
    
    /**
     * @return the domain folder of the default models project
     */
    public IFolder getDomainFolder() {
    	return getDomainFolder(getOfsProject());
    }
    
    /**
     * @return the domain folder of the default models project
     */
    public IFolder getRimFolder() {
    	return getRimFolder(getOfsProject());
    }

    /**
     * @param ofsProject
     * @return the domain folder of the ofsProject
     */
    public final IFolder getDomainFolder(IOfsProject ofsProject) {
    	return getModelFolder(ofsProject, DOMAIN_LOCATION);
    }
    
    
    /**
     * @param ofsProject
     * @return the rim folder of the ofsProject
     */
    public final IFolder getRimFolder(IOfsProject ofsProject) {
    	return getModelFolder(ofsProject, RIM_LOCATION);
    }

    /**
     * @return the fragment folder of the default models project
     */
    public IFolder getFragmentFolder() {
    	return getFragmentFolder(getOfsProject());
    }
    
    /**
     * Return the fragment folder of the IOfsProject
     * @param ofsProject
     * @return the fragment folder of the IOfsProject
     */
    public IFolder getFragmentFolder(IOfsProject ofsProject) {
    	return getModelFolder(ofsProject, FRAGMENT_LOCATION);
    }

    /**
     * @return the default module folder of the default models project
     */
    public IFolder getDefaultModuleFolder() {
    	return getDefaultModuleFolder(getOfsProject());
    }
    
    /**
     * Return the default module project of the IOfsProject
     * @param ofsProject
     * @return the default module project of the IOfsProject
     */
    public IFolder getDefaultModuleFolder(IOfsProject ofsProject) {
    	return ofsProject.getProject().getFolder(DEFAULT_MODULE_LOCATION);
    }

    /**
     * @return the module folder of the default models project
     * @see getDefaultModuleFolder
     */
    public IFolder getModuleFolder() {
    	return getModuleFolder(getOfsProject());
    }
    
    /**
     * Return the module folder of the IOfsProject
     * @param ofsProject
     * @return the module folder of the IOfsProject
     */
    public IFolder getModuleFolder(IOfsProject ofsProject) {
    	return getModelFolder(ofsProject, MODULE_LOCATION);
    }
    
    /**
     * @return the pageflow folder of the default models project
     */
    public IFolder getPageflowFolder() {
    	return getPageflowFolder(getOfsProject());
    }

    /**
     * Return the pageflow folder of the IOfsProject
     * @param ofsProject
     * @return the pageflow folder of the IOfsProject
     */
    public IFolder getPageflowFolder(IOfsProject ofsProject) {
    	return getModelFolder(ofsProject, PAGEFLOWS_LOCATION);
    }
    
    /**
     * @return the page folder of the default models project
     */
    public IFolder getPageFolder() {
    	return getPageFolder(getOfsProject());
    }

    /**
     * Return the page folder of the IOfsProject
     * @param ofsProject
     * @return the page folder of the IOfsProject
     */
    public IFolder getPageFolder(IOfsProject ofsProject) {
    	return getModelFolder(ofsProject, PAGE_LOCATION);
    }
    
    /**
     * @return the default page folder of the default models project
     */
    public IFolder getDefaultPageFolder() {
    	return getDefaultPageFolder(getOfsProject());
    }

    /**
     * Return the default page folder of the IOfsProject
     * @param ofsProject
     * @return the default page folder of the IOfsProject
     */
    public IFolder getDefaultPageFolder(IOfsProject ofsProject) {
    	return ofsProject.getProject().getFolder(DEFAULT_PAGE_LOCATION);
    }
    
    /**
     * @return the workflow folder of the default models project
     */
    public IFolder getWorkflowFolder() {
    	return getWorkflowFolder(getOfsProject());
    }
    
    /**
     * Return the workflow folder of the IOfsProject
     * @param ofsProject
     * @return the workflow folder of the IOfsProject
     */
    public IFolder getWorkflowFolder(IOfsProject ofsProject) {
    	return getModelFolder(ofsProject, WORKFLOWS_LOCATION);
    }
    
    /**
     * Copy a list of resources in the default models project
     * @param resources
     */
	public void copyResourceInModelsProject(String... resources) {
		copyResourceInModelsProject(getOfsProject(), resources);
	}

	/**
	 * Copy the pms-models snapshot stored besides the test tank models
	 */
	public void copyPmsModelsResourceInModelsProject() {
		copyPmsModelsResourceInModelsProject(getOfsProject());
	}

	/**
	 * Copy the pms-models snapshot stored besides the test tank models
	 */
	public void copyPmsModelsResourceInModelsProject(IOfsProject ofsProject) {
		URL url = FileLocator.find(TestFrameworkHeadlessCore.getDefault().getBundle(), new Path(PMS_MODELS_RESOURCE_FOLDER), null);
        File srcDir;
		try {
			srcDir = new File(FileLocator.toFileURL(url).toURI());
		} catch (Exception e) {
			throw new RuntimeException("Unable to find the source folder: " + url, e);
		}
		
		// Skip file and folders prefixed by dot.
		IOFileFilter fileFilter = new IOFileFilter() {
			@Override
			public boolean accept(File file) { return accept(file.getParentFile(), file.getName()); }
			@Override
			public boolean accept(File dir, String name) {
				if (name.startsWith(".") || name.equals("pom.xml")) 
					return false;
				File currentDir = dir;
				while (currentDir != null) {
					if (currentDir.getName().startsWith("."))
						return false;
					currentDir = currentDir.getParentFile();
				}
				return true; 
			}
		};
		Collection<File> listFiles = FileUtils.listFiles(srcDir, fileFilter, TrueFileFilter.INSTANCE);
		String dirPrefix = srcDir.getAbsolutePath();
		List<String> files = new ArrayList<String>();
		for (File file : listFiles) {
			String fileString = file.getAbsolutePath();
			String relativeFile = StringUtils.removeStart(fileString, dirPrefix + File.separatorChar).replace('\\', '/');
			files.add(relativeFile);
		}
		copyResourceInModelsProject(ofsProject, srcDir, files.toArray(new String[files.size()]));
	}

	/**
	 * Copy a list of resources in the ofsProject.
	 * @param ofsProject
	 * @param resources
	 */
	public void copyResourceInModelsProject(IOfsProject ofsProject, String... resources) {
		URL url = FileLocator.find(TestFrameworkHeadlessCore.getDefault().getBundle(), new Path(RESOURCES_FOLDER), null);
        File resourceFolder;
		try {
			resourceFolder = new File(FileLocator.toFileURL(url).toURI());
		} catch (Exception e) {
			throw new RuntimeException("Unable to find the source folder: " + url, e);
		}
		copyResourceInModelsProject(ofsProject, resourceFolder, resources);
	}
	
	private void copyResourceInModelsProject(final IOfsProject ofsProject,
			final File resourceFolder, final String... resources) {
		class MyWorkspaceJob extends WorkspaceJob {
			public MyWorkspaceJob() {
				super("Copy Model Resource Job");
			}
			public IStatus runInWorkspace(IProgressMonitor monitor) throws CoreException {
				for (String sourceResource : resources) {
					final File sourceResourceFile = new File(resourceFolder, sourceResource);
					if (!sourceResourceFile.exists()) {
						throw new RuntimeException("Unable to create the test project because the " + sourceResourceFile + " resource doesn't exists");
					}
					if (!sourceResourceFile.isFile()) {
						throw new RuntimeException("Unable to create the test project because the " + sourceResourceFile + " resource is not a file");
					}
					
					final String targetFolderName = StringUtils.remove(sourceResource, sourceResourceFile.getName());
					IFolder targetFolder = ofsProject.getProject().getFolder(targetFolderName); 
					if (StringUtils.isNotEmpty(targetFolderName)) {
						try {
							mkdirs(targetFolder);
						} catch (CoreException e) {
							throw new RuntimeException("Unable to create the folders", e);
						}
					}
					
					IFile targetFile = targetFolder.getFile(sourceResourceFile.getName());
		            try {
						targetFile.create(new FileInputStream(sourceResourceFile), true, null);
					} catch (Exception e) {
						throw new RuntimeException("Unable to create the test project because a project occurs during the copy of the resource to the " + ofsProject + " models project", e);
					}
				}
				return Status.OK_STATUS;
			}
		};
		MyWorkspaceJob job = new MyWorkspaceJob();
		job.setRule(ofsProject.getProject());
		job.schedule();
		try {
			job.join();
		} catch (Exception e) {
			logger.warn("Unable to join the copy resource job", e);
		}
		
		for (String resource : resources) {
			checkResourceAvailability(resource);
		}
	}
	
	// Ensure the resource is available 
	private void checkResourceAvailability(String resource) {
		String resourceWithoutModelsFolder = StringUtils.substringAfter(resource, "/");
		org.eclipse.emf.common.util.URI uri = org.eclipse.emf.common.util.URI.createURI("resource:///" + resourceWithoutModelsFolder);
		checkResourceAvailability(uri, true);
	}

	/**
	 * @param uri
	 */
	protected void checkResourceAvailability(org.eclipse.emf.common.util.URI uri, boolean loadResource) {
		Exception lastException = null;
		for (int i=0; i<MAX_CHECK_ABAILABILITY_RETRIES; i++) {
			try {
				IOfsModelResource ofsResource = getOfsProject().getOfsModelResource(uri);
				if (loadResource) {
					ofsResource.getEMFModel().get(0);
				}
				break;
			} catch (Exception e) {
				lastException = e;
			}
			try {
				Thread.sleep(DELAY_BETWEEN_RETRIES);
			} catch (InterruptedException e) {
				break;
			}
		}
		if (lastException != null) {
			logger.warn("Unable to load the resource \"" + uri + "\" after " + MAX_CHECK_ABAILABILITY_RETRIES + " retries.", lastException);
		}
	}

	protected void resolveMavenDependenciesAndWaitForAutoBuild() {
		resolveAndWaitForMavenDependenciesResolution();
		waitForAutoBuild();
	}
	
	/** DS-7406 */
	protected void waitForAutoBuild() {
		// useful? getProject().build(IncrementalProjectBuilder.INCREMENTAL_BUILD, null);
		IResourcesSetupUtil.waitForAutoBuild();
	}
	
    protected void resolveAndWaitForMavenDependenciesResolution() {
		OfsCore.getDependencyManager().resolveDependencies(getProject());
		OfsCore.getDependencyManager().waitForResolutionJob();
    }

	/**
	 * Make dirs recursively for a folder 
	 * @param folder
	 * @throws CoreException
	 */
	protected void mkdirs(IFolder folder) throws CoreException {
		if (folder.exists()) {
			return;
		}
		IContainer container = folder.getParent();
		if (!container.exists()) {
			mkdirs((IFolder) container);
		}
		folder.create(true, true, null);
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
	protected boolean importExistingProject(IPath projectPath)
			throws CoreException {

		IWorkspace workspace = ResourcesPlugin.getWorkspace();

		// Load the project description file
		final IProjectDescription description = workspace
				.loadProjectDescription(projectPath.append(IPath.SEPARATOR
						+ IProjectDescription.DESCRIPTION_FILE_NAME));
		final IProject project = workspace.getRoot().getProject(
				description.getName());

		IWorkspaceRunnable runnable = new IWorkspaceRunnable() {
			public void run(IProgressMonitor monitor) throws CoreException {
				project.create(description, monitor);
				project.open(IResource.NONE, monitor);
			}
		};
		workspace.run(runnable, workspace.getRuleFactory().modifyRule(
				workspace.getRoot()), IResource.NONE, null);
		
		return true;
	}
	
	protected void assertXml(String message, String expectedXml, String generatedXml,
			final String... skippedXPaths) throws SAXException, IOException {
		XMLTestsUtils.assertXml(message, expectedXml, generatedXml, skippedXPaths);
	}
	
	/**
	 * Returns the file contents of the file designated by the filename.
	 * This file must be accessible from the classpath
	 * @param filename
	 * @return the file contents
	 * @throws IOException
	 */
	protected String readFileFromClasspath(String filename) throws IOException {
		InputStream is = this.getClass().getResourceAsStream(filename);
		StringWriter writer = new StringWriter();
		IOUtils.copy(is, writer);
		String expected = writer.toString();
		return expected;
	}

	protected String readFileContent(IProject genProject, String fullPath) throws CoreException, IOException {
		Path iPath = new Path(fullPath);
		IFile iFile = genProject.getFile(iPath);
		return IOUtils.toString(iFile.getContents(true));
	}
	
	@SuppressWarnings("unchecked")
	public <T> T invokeNonPublicMethod(Object instance, String methodName, Object... params) {
		try {
			List <Class<?>> paramsType = new ArrayList<Class<?>>();
			for (Object param : params) {
				paramsType.add(param.getClass());
			}
			Method declaredMethod = instance.getClass().getDeclaredMethod(methodName, paramsType.toArray(new Class<?>[paramsType.size()]));
			declaredMethod.setAccessible(true);
			return (T)declaredMethod.invoke(instance, params);
		} catch (Exception e) {
			throw new RuntimeException("Unable to invoke the non public method named " + methodName + " for " + instance!=null?instance.getClass().getName():"null", e);
		}
	}
	
    /**
     * Copy a list of resources in the default models project
     * @param resources
     */
	public void copyFromClasspathToModelsProject(String... resources) {
		copyFromClasspathToModelsProject(getOfsProject(), resources);
	}

	/**
	 * Copy a list of resources in the ofsProject.
	 * @param ofsProject
	 * @param resources
	 */
	public void copyFromClasspathToModelsProject(IOfsProject ofsProject,
			String... resources) {
		URL url = null;
		File resourceFolder;
		for (String resource : resources) {
			url = this.getClass().getClassLoader().getResource(resource);
			if (url == null)
				throw new IllegalArgumentException("Resource not found on classpath: " + resource);
			try {
				URI uri = FileLocator.toFileURL(url).toURI();
				resourceFolder = new File(uri);
			} catch (Exception e) {
				throw new IllegalArgumentException("Resource found on classpath, but couldn't convert to URI or File: " + url.toString());
			}
			copyFromClasspathToModelsProject(ofsProject, resourceFolder, resource);
		}
	}

	private void copyFromClasspathToModelsProject(final IOfsProject ofsProject,
			final File resourceFolder, final String sourceResource) {
		class MyWorkspaceJob extends WorkspaceJob {
			public MyWorkspaceJob() {
				super("Copy Model Resource from test bundle Job");
			}
			public IStatus runInWorkspace(IProgressMonitor monitor) throws CoreException {
					final File sourceResourceFile = new File(resourceFolder.getAbsolutePath());
					if (!sourceResourceFile.exists()) {
						throw new RuntimeException("Unable to create the test project because the " + sourceResourceFile + " resource doesn't exists");
					}
					if (!sourceResourceFile.isFile()) {
						throw new RuntimeException("Unable to create the test project because the " + sourceResourceFile + " resource is not a file");
					}
					
					final String targetFolderName = StringUtils.remove(sourceResource, sourceResourceFile.getName());
					IFolder targetFolder = ofsProject.getProject().getFolder(targetFolderName); 
					if (StringUtils.isNotEmpty(targetFolderName)) {
						try {
							mkdirs(targetFolder);
						} catch (CoreException e) {
							throw new RuntimeException("Unable to create the folders", e);
						}
					}
					
					IFile targetFile = targetFolder.getFile(sourceResourceFile.getName());
		            try {
						targetFile.create(new FileInputStream(sourceResourceFile), true, null);
					} catch (Exception e) {
						throw new RuntimeException("Unable to create the test project because a project occurs during the copy of the resource to the " + ofsProject + " models project", e);
					}
				
				return Status.OK_STATUS;
			}
		};
		MyWorkspaceJob job = new MyWorkspaceJob();
		job.setRule(ofsProject.getProject());
		job.schedule();
		try {
			job.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
			logger.error("InterruptedException", e);
		}
	}		
	
	protected ResourceSet getResourceSet() {
		return getOfsProject().getModelResourceSet();
	}
	
	/**
	 * utility method for testing purposes, fetches the resource from the given project
	 * 
	 * @param ofsProject
	 * @param modelFile
	 * @return
	 */
	protected IOfsModelResource getModelResource(IOfsProject ofsProject, String modelFile) {
		Set<IOfsModelResource> resources = ofsProject.getModelResourceSet().getAllOfsModelResources();
		for (IOfsModelResource mResource : resources) {
			if (mResource.getURI().toString().endsWith(modelFile)) {
				return mResource;
			}
 		}
		return null;
	}

	
	protected Resource getResource(String path) {
		org.eclipse.emf.common.util.URI uri = org.eclipse.emf.common.util.URI.createPlatformResourceURI("/"
				+ getProject().getName() + "/" + path, true);
		List<Resource> resources = getResourceSet().getResources();
		for (Resource resource : resources) {
			if (uri.equals(resource.getURI())) {
				return resource;
			}
		}
		return null;
	}

}
