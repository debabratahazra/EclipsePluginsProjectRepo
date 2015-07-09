package com.odcgroup.workbench.core.tests;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.core.OfsCore;
import com.odcgroup.workbench.core.init.OfsProjectInitializer;
import com.odcgroup.workbench.core.init.ProjectInitializer;
import com.odcgroup.workbench.core.tests.util.TestInitializer;


public class OfsCoreReadTest {

    IProject project;
    IFolder modelFolder;

    @Before
	public void setUp() throws Exception {
        // create workspace with an OFS and a Java project
        IWorkspace workspace = ResourcesPlugin.getWorkspace();
        project = workspace.getRoot().getProject(this.getClass().getName());
        project.create(null);
        project.open(null);
        new OfsProjectInitializer().updateConfiguration(project, null);
        project.refreshLocal(IResource.DEPTH_INFINITE, null);

        modelFolder = project.getFolder("domain");
        if(!modelFolder.exists()) modelFolder.create(false, true, null);
    }

    @After
	public void tearDown() throws Exception {
        project.delete(true, null);
    }

    @Test
    public void testGetExtensions() {
        IConfigurationElement[] extensions = OfsCore.getExtensions(OfsCore.MODEL_EXTENSION_ID);
        Assert.assertTrue(extensions.length > 0);
    }

    @Test
    public void testIsOfsProject() throws CoreException {
        IWorkspace workspace = ResourcesPlugin.getWorkspace();
        IProject noOfsProject = workspace.getRoot().getProject(
                this.getClass().getName() + "2");
        noOfsProject.create(null);

        Assert.assertTrue(OfsCore.isOfsProject(project));
        project.close(null);
        Assert.assertFalse(OfsCore.isOfsProject(project));

        Assert.assertFalse(OfsCore.isOfsProject(noOfsProject));
        noOfsProject.open(null);
        Assert.assertFalse(OfsCore.isOfsProject(noOfsProject));

        noOfsProject.delete(true, null);
    }

    @Test
    public void testIsOfsModelFolder() throws CoreException {
        IFolder otherFolder = project.getFolder("other");
        otherFolder.create(true, true, null);
        IFolder subModelFolder = modelFolder.getFolder("sub");
        subModelFolder.create(true, true, null);

        Assert.assertTrue(OfsCore.isOfsModelPackage(modelFolder));
        Assert.assertFalse(OfsCore.isOfsModelPackage(otherFolder));
        Assert.assertTrue(OfsCore.isOfsModelPackage(subModelFolder));
    }

    @Test
   public void testGetFolderModelName() throws CoreException {
        IFolder subModelFolder = modelFolder.getFolder("sub");
        subModelFolder.create(true, true, null);

        Assert.assertEquals("domain", OfsCore.getFolderModelName(subModelFolder));
    }

    @Test
    public void testGetModelRepository() {
        IOfsProject modelRepository = OfsCore.getOfsProjectManager().getOfsProject(project);
        Assert.assertNotNull(modelRepository);
    }

    @Test
    public void testGetModelProjectInitializers() {
        ProjectInitializer[] modelInitializers = OfsCore.getProjectInitializers(project, true);
        boolean found = false;
        for (ProjectInitializer initializer : modelInitializers) {
            if (initializer instanceof TestInitializer) found = true;
        }
        Assert.assertTrue(found);
    }

}
