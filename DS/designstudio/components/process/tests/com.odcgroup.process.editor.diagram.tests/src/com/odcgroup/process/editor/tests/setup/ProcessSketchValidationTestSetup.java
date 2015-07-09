package com.odcgroup.process.editor.tests.setup;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Platform;
import org.junit.Assert;

import com.odcgroup.workbench.core.OfsCore;
import com.odcgroup.workbench.core.init.OfsProjectInitializer;
import com.odcgroup.workbench.generation.GenerationCore;

public class ProcessSketchValidationTestSetup extends
		AbstractProcessEditorTestSetup {

	@Override
	protected IProject createProject() throws Exception {
    	IWorkspace workspace = null;
        String aProjectName = "projProcess"; //$NON-NLS-1$
        workspace = ResourcesPlugin.getWorkspace();
        IWorkspaceRoot wsroot = workspace.getRoot();
        IProject project = wsroot.getProject(aProjectName);
        project.create(null);
        project.open(null);
        IProjectDescription desc = workspace.newProjectDescription(project.getName());
        
        new OfsProjectInitializer().updateConfiguration(project, null);
        Assert.assertTrue(OfsCore.isOfsProject(project));
        
        //GenerationCore.toggleNature(project);
        Assert.assertFalse(GenerationCore.isTechnical(project));
        
        IPath locationPath = Platform.getLocation();
        locationPath = null;
        desc.setLocation(locationPath);
        if (!project.exists()) project.create(desc, null);
        if (!project.isOpen()) project.open(null);

        return project;
	}

}
