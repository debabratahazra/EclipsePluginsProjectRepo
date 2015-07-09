package com.odcgroup.workbench.generation.tests;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.junit.Assert;

import com.odcgroup.workbench.core.IOfsModelResource;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.core.preferences.ProjectPreferences;
import com.odcgroup.workbench.core.tests.util.AbstractDSUnitTest;
import com.odcgroup.workbench.dsl.ui.quickfix.CommonXtextBuilderInitializer;
import com.odcgroup.workbench.generation.GenerationCommon;
import com.odcgroup.workbench.generation.GenerationCore;
import com.odcgroup.workbench.generation.cartridge.CodeCartridge;
import com.odcgroup.workbench.generation.init.CodeGenInitializer;

/**
 * @author yan
 */
public class AbstractDSGenerationTest extends AbstractDSUnitTest {

	protected Map<String, IProject> genProjects = new HashMap<String, IProject>();

	protected AbstractDSGenerationTest() {
		super();
	}

	protected IOfsProject createModelAndGenProject() {
		return createNamedModelAndGenProject(DEFAULT_MODELS_PROJECT);
	}
	
	protected IOfsProject createNamedModelAndGenProject(String modelProjectName) {
    	try {
    		// Cleanup before creating a project to ensure it doesn't 
    		// already exists
       		deleteModelsProject(modelProjectName);
	       		
    		// Create the Model project
       		IOfsProject modelsProject = createNamedModelsProject(modelProjectName);
       		
       		// enable xtext indexing for the model project
       		new CommonXtextBuilderInitializer().updateConfiguration(modelsProject.getProject(), null);
       		
       		// Create the gen projects
   	        new CodeGenInitializer().updateConfiguration(modelsProject.getProject(), null);

   			deactivateAllCartridges(modelsProject.getProject());
   	        
	        return modelsProject;
    	} catch (CoreException e) {
    		throw new RuntimeException("Unable to create " + modelProjectName + " models project (& gen projects)", e);
    	}
	}

	/**
	 * Delete silently a gen project
	 * @param projectName
	 * @throws CoreException 
	 * @throws CoreException 
	 */
	public void deleteModelsProject(String projectName) throws CoreException {
		IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
		for (String genProjectName : GenerationCore.getJavaProjectNames(project)) {
			IProject genProject = ResourcesPlugin.getWorkspace().getRoot().getProject(genProjectName);
			if (genProject != null && genProject.exists()) {
				genProject.delete(true, null);
			}
		}

		super.deleteModelsProject(projectName);
	}

	/**
	 * Delete all projects (including the gen projects)
	 */
	public void deleteModelsProjects() {
    	RuntimeException firstException = null;
		for (IOfsProject ofsProject : ofsProjects.values()) {
			for (String genProjectName : GenerationCore.getJavaProjectNames(ofsProject.getProject())) {
				IProject genProject = ResourcesPlugin.getWorkspace().getRoot().getProject(genProjectName);
				if (genProject != null && genProject.exists()) {
					try {
						genProject.delete(true, null);
					} catch (CoreException e) {
				    	firstException = new RuntimeException("Unable to delete " + genProject.getName(), e);
					}
				}
			}
		}
		if (firstException != null) {
			throw firstException;
		}
		
		super.deleteModelsProjects();
	}
	
    public IProject getGenProject() {
    	return getNamedGenProject(DEFAULT_MODELS_PROJECT);
    }
    
    public IProject getNamedGenProject(String modelProjectName) {
    	return ResourcesPlugin.getWorkspace().getRoot().getProject(modelProjectName + "-gen");
    }

	private void deactivateAllCartridges(IProject project) {
        ProjectPreferences preferences = new ProjectPreferences(project, GenerationCore.PLUGIN_ID);
		for (CodeCartridge registeredCartridge : GenerationCore.getRegisteredCodeCartridges()) {
			preferences.putBoolean(registeredCartridge.getId(), false);
		}
		preferences.flush();
	}
	
	protected void activateCartridge(IProject project, String id) {
        ProjectPreferences preferences = new ProjectPreferences(project, GenerationCore.PLUGIN_ID);
		preferences.putBoolean(id, true);
		preferences.flush();
	}

	protected Set<IFolder> generateAll() throws Throwable {
		return generateAll(getOfsProject());
	}
	
	protected Set<IFolder> generateAll(IOfsProject ofsProject) throws Throwable {
		 Collection<IOfsModelResource> resources = new ArrayList<IOfsModelResource>();
		 resources.addAll(ofsProject.getModelResourceSet().getAllOfsModelResources());
		 Set<IFolder> ouputs = new HashSet<IFolder>();
		 IStatus status = new GenerationCommon().doGeneration(ofsProject.getProject(), resources, ouputs, new NullProgressMonitor());
		 if (!status.isOK()) {
			 if ( status.getException() != null )
				 throw status.getException();
			 else
				 Assert.fail(status.getMessage());
		 }
		 return ouputs;
	}

}
