package com.odcgroup.visualrules.depmgr.tests;

import java.io.File;
import java.net.URL;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.odcgroup.workbench.core.IContainerIdentifier;
import com.odcgroup.workbench.core.IOfsModelContainer;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.core.OfsCore;
import com.odcgroup.workbench.core.repository.IDependencyManager;
import com.odcgroup.workbench.core.repository.IOfsProjectManager;
import com.odcgroup.workbench.core.tests.util.AbstractDSUnitTest;
import com.odcgroup.workbench.core.tests.util.ZipUtils;

/**
 * Tests the model project dependency mechanisms
 *
 * @author Kai Kreuzer
 *
 */
public class DependencyTest extends AbstractDSUnitTest {
	
	@Before
	public void setUp() throws Exception {
        final URL url = FileLocator.find(OfsCore.getDefault().getBundle(),
                new Path("resources/dependencytest.zip"), null);
        final File zipFile = new File(FileLocator.toFileURL(url).getPath());
        File tmpFolder = OfsCore.getDefault().getStateLocation().toFile();
        ZipUtils.unzipArchive(zipFile, tmpFolder);
        
		File[] projectDirs = tmpFolder.listFiles();
		for (File projectDir : projectDirs) {
			IPath projectPath = new Path(projectDir.getAbsolutePath());
			if (!projectPath.lastSegment().startsWith(".")) {
				importExistingProject(projectPath);
			}
		}
		
		ResourcesPlugin.getWorkspace().getRoot().refreshLocal(IResource.DEPTH_INFINITE, null);
		OfsCore.getDependencyManager().disableAutoResolution();
		OfsCore.getDependencyManager().resolveDependencies();
    }

    @After
	public void tearDown() throws Exception {
    	for(IProject project : ResourcesPlugin.getWorkspace().getRoot().getProjects()) {
    		project.delete(true, true, null);
    	}
    }

    @Test
	public void testDependencyResolution() {
    	IOfsProjectManager projectManager = OfsCore.getOfsProjectManager();
    	IDependencyManager dependencyManager = OfsCore.getDependencyManager();

    	IWorkspaceRoot wsRoot = ResourcesPlugin.getWorkspace().getRoot();

    	IOfsProject projectB = projectManager.getOfsProject(wsRoot.getProject("b-models"));
    	Set<IOfsModelContainer> dependenciesB = dependencyManager.getDependencies(projectB);   
    	Assert.assertEquals(1, dependenciesB.size());
    	Assert.assertFalse(dependencyManager.hasUnresolvedDependencies(projectB.getProject()));

    	IOfsProject projectC = projectManager.getOfsProject(wsRoot.getProject("c-models"));    	
    	Set<IOfsModelContainer> dependenciesC = dependencyManager.getDependencies(projectC);    	
    	Assert.assertEquals(1, dependenciesC.size());
    	IContainerIdentifier[] unresolvedDependenciesC = dependencyManager.getUnresolvedDependencies(projectC.getProject());    	
    	Assert.assertEquals(1, unresolvedDependenciesC.length);
    	Assert.assertTrue(dependencyManager.hasUnresolvedDependencies(projectC.getProject()));
    }
}
