package com.odcgroup.workbench.core.tests;

import org.eclipse.core.resources.ICommand;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.odcgroup.workbench.core.OfsCore;
import com.odcgroup.workbench.core.init.OfsProjectInitializer;


public class OfsCoreCreateTest {

    IProject project;

    @Before
	public void setUp() throws Exception {
        IWorkspace workspace = ResourcesPlugin.getWorkspace();
        project = workspace.getRoot().getProject(this.getClass().getName());
        project.create(null);
        project.open(null);
        new OfsProjectInitializer().updateConfiguration(project, null);
    }

    @After
	public void tearDown() throws Exception {
        project.delete(true, null);
    }

    @Test
    public void testCreateFolder() throws CoreException {
        IFolder folder = project.getFolder("testFolder/subfolder/subsubfolder");
        OfsCore.createFolder(folder);
        Assert.assertTrue(project.getFolder("testFolder").getFolder("subfolder").getFolder(
                "subsubfolder").exists());
    }

    @Test
    public void testAddProjectBuilder() throws CoreException {
        project.delete(true, null);
        IWorkspace workspace = ResourcesPlugin.getWorkspace();
        IProject project = workspace.getRoot().getProject(
                this.getClass().getName());
        project.create(null);
        project.open(null);
        OfsCore.addProjectBuilder(project, OfsCore.BUILDER_ID);
        ICommand[] cmds = project.getDescription().getBuildSpec();
        int hits = 0;
        for (ICommand cmd : cmds) {
            if (cmd.getBuilderName().equals(OfsCore.BUILDER_ID)) hits++;
        }
        Assert.assertEquals(1, hits);
    }

    @Test
    public void testRemoveProjectBuilder() throws CoreException {
        OfsCore.removeProjectBuilder(project, OfsCore.BUILDER_ID);
        ICommand[] cmds = project.getDescription().getBuildSpec();
        int hits = 0;
        for (ICommand cmd : cmds) {
            if (cmd.getBuilderName().equals(OfsCore.BUILDER_ID)) hits++;
        }
        Assert.assertEquals(0, hits);
    }
}
