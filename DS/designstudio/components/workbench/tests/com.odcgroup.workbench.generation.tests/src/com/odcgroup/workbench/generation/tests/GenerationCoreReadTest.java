package com.odcgroup.workbench.generation.tests;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.JavaCore;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.odcgroup.workbench.core.OfsCore;
import com.odcgroup.workbench.core.init.OfsProjectInitializer;
import com.odcgroup.workbench.generation.GenerationCore;

public class GenerationCoreReadTest {

    IProject project;

    @Before
    public void setUp() throws Exception {

        // create workspace with an OFS and a Java project
        IWorkspace workspace = ResourcesPlugin.getWorkspace();
        project = workspace.getRoot().getProject(this.getClass().getName());
        project.create(null);
        project.open(null);
        new OfsProjectInitializer().updateConfiguration(project, null);
        OfsCore.addNature(project, JavaCore.NATURE_ID);
    }

    @After
    public void tearDown() throws Exception {
        project.delete(true, null);
    }

    @Test
    public void testIsJavaSourceFolder() throws CoreException {
        IFolder otherFolder = project.getFolder("other");
        otherFolder.create(true, true, null);

        Assert.assertFalse(GenerationCore.isJavaSourceFolder(otherFolder));
    }

}
