package com.odcgroup.process.editor.tests.setup;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceDescription;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Platform;
import org.junit.After;
import org.junit.Assert;

import com.odcgroup.workbench.core.OfsCore;
import com.odcgroup.workbench.core.init.OfsProjectInitializer;
import com.odcgroup.workbench.generation.GenerationCore;

public class ProcessTechnicalValidationTestSetup extends
		AbstractProcessEditorTestSetup {

	@After
	public void tearDown() throws Exception {
		super.tearDown();
		// delete the gen project
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
        String aProjectName = "processTech-gen"; //$NON-NLS-1$
        IWorkspaceRoot wsroot = workspace.getRoot();
        IProject project = wsroot.getProject(aProjectName);
        project.delete(true, null);
		
	}

	@Override
	protected IProject createProject() throws Exception {    	
    	IWorkspace workspace = null;
        String aProjectName = "processTech"; //$NON-NLS-1$
        workspace = ResourcesPlugin.getWorkspace();
        
        IWorkspaceDescription description = workspace.getDescription();
        description.setAutoBuilding(false);
        workspace.setDescription(description);
        
        IWorkspaceRoot wsroot = workspace.getRoot();
        IProject project = wsroot.getProject(aProjectName);
        project.create(null);
        project.open(null);
        
        IProjectDescription desc = workspace.newProjectDescription(project.getName());
	    
        new OfsProjectInitializer().updateConfiguration(project, null);
        Assert.assertTrue(OfsCore.isOfsProject(project));
	
        GenerationCore.toggleNature(project);
        Assert.assertTrue(GenerationCore.isTechnical(project));	
        
        IPath locationPath = Platform.getLocation();
        locationPath = null;
        desc.setLocation(locationPath);
        if (!project.exists()) {
        	project.create(desc, null);
        }   
      
        if (!project.isOpen()){
        	project.open(null);
        }
        return project;    
	}

}
