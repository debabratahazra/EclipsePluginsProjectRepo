package com.odcgroup.workbench.core.internal.repository.maven;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.odcgroup.workbench.core.IContainerIdentifier;
import com.odcgroup.workbench.core.OfsCore;
import com.odcgroup.workbench.core.repository.IDependencyManager;
import com.odcgroup.workbench.core.tests.util.AbstractDSUnitTest;

import de.visualrules.core.nature.VisualRulesNature;

public class MavenSettingsCustomizerTest extends AbstractDSUnitTest {

	@Before
	public void setUp() throws Exception {
		createModelsProject();
		OfsCore.addNature(getProject(), VisualRulesNature.NATURE_ID);
		OfsCore.getDependencyManager(getProject()).disableAutoResolution();
	}
	
	@After
	public void tearDown() throws Exception {
		deleteModelsProjects();
	}
	
	@Test
	public void testNoRemoteUpdate_DS3544() throws IOException, CoreException {
        
		// replace pom.xml by our own, which defines an external repository
		final URL url = FileLocator.find(OfsCore.getDefault().getBundle(),
                new Path("resources/maven/test-ds3544_pom.xml"), null);
        final File srcPomFile = new File(FileLocator.toFileURL(url).getPath());
        IFile targetPomFile = getProject().getFile("pom.xml");
        targetPomFile.delete(true, null);
        targetPomFile.create(new FileInputStream(srcPomFile), true, null);

        IDependencyManager depMgr = OfsCore.getDependencyManager(getProject());
        depMgr.resolveDependencies();
        IContainerIdentifier[] deps = depMgr.getUnresolvedDependencies(getProject());
        Assert.assertEquals(1, deps.length);
        Assert.assertEquals("mdf-jpa-generation-integration-models", deps[0].getName());
	}
	
}
