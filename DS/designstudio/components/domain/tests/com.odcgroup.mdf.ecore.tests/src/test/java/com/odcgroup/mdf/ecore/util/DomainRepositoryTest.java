package com.odcgroup.mdf.ecore.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.mdf.metamodel.MdfDomain;
import com.odcgroup.mdf.metamodel.MdfEntity;
import com.odcgroup.mdf.metamodel.MdfEnumValue;
import com.odcgroup.mdf.metamodel.MdfEnumeration;
import com.odcgroup.mdf.metamodel.MdfProperty;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.core.OfsCore;
import com.odcgroup.workbench.core.repository.ModelNotFoundException;
import com.odcgroup.workbench.core.tests.util.AbstractDSUnitTest;
import com.odcgroup.workbench.core.tests.util.ZipUtils;
import com.odcgroup.workbench.testframework.headless.TestFrameworkHeadlessCore;

public class DomainRepositoryTest extends AbstractDSUnitTest {

	private IOfsProject projectA;
	private IOfsProject projectB;

	@Before
	public void setUp() throws Exception {
		// what we need to set up for this test is the following:
		// project a-models with a class A in Domain.domain having an enum attribute of type MyEnum in Enums.domain
		// project b-models which depends on a-models
		
        URL url = FileLocator.find(OfsCore.getDefault().getBundle(),
                new Path("resources/dependencytest.zip"), null);
        final File zipFile = new File(FileLocator.toFileURL(url).getPath());
        File tmpFolder = OfsCore.getDefault().getStateLocation().toFile();
        ZipUtils.unzipArchive(zipFile, tmpFolder);

		url = FileLocator.find(TestFrameworkHeadlessCore.getDefault().getBundle(), new Path(RESOURCES_FOLDER + "/domain/ds3652"), null);
        File domainFolder;
		try {
			domainFolder = new File(FileLocator.toFileURL(url).toURI());
		} catch (Exception e) {
			throw new RuntimeException("Unable to find the source folder: " + url, e);
		}
		File domainFile = new File(domainFolder, "Domain.domain");
		File outputFile = new File(tmpFolder.getAbsolutePath() + "/a-models/domain/ds3652/Domain.domain");        
		FileUtils.copyFile(domainFile, outputFile);

		File enumsFile = new File(domainFolder, "Enums.domain");
		outputFile = new File(tmpFolder.getAbsolutePath() + "/a-models/domain/ds3652/Enums.domain");        
		FileUtils.copyFile(enumsFile, outputFile);
		
		File dsFile = new File(domainFolder, "DS3962.domain");
		outputFile = new File(tmpFolder.getAbsolutePath() + "/a-models/domain/ds3652/DS3962.domain");        
		FileUtils.copyFile(dsFile, outputFile);

		File[] projectDirs = tmpFolder.listFiles();
		for (File projectDir : projectDirs) {
			IPath projectPath = new Path(projectDir.getAbsolutePath());
			if (!projectPath.lastSegment().startsWith(".")) {
				importExistingProject(projectPath);
			}
		}

		projectA = OfsCore.getOfsProject(ResourcesPlugin.getWorkspace().getRoot().getProject("a-models"));
		projectB = OfsCore.getOfsProject(ResourcesPlugin.getWorkspace().getRoot().getProject("b-models"));
		updateProjectConfigurations(projectA.getProject());
		updateProjectConfigurations(projectB.getProject());
		
		ResourcesPlugin.getWorkspace().getRoot().refreshLocal(IResource.DEPTH_INFINITE, null);
		OfsCore.getDependencyManager().disableAutoResolution();
		OfsCore.getDependencyManager().resolveDependencies();
		waitForAutoBuild();
	}

	@After
	public void tearDown() throws Exception {
    	for(IProject project : ResourcesPlugin.getWorkspace().getRoot().getProjects()) {
    		project.delete(true, true, null);
    	}
    }
	
	@Test
	@Ignore
	@SuppressWarnings("unchecked")
	public void testDs3652() throws ModelNotFoundException, URISyntaxException, IOException, CoreException {
		
		// we are first checking, that we can access the enum values of MyEnum through project b-models and
		// that these values have the expected order
		
		MdfDomain domain = DomainRepository.getInstance(projectB).getDomain("MyDomain");
		Assert.assertNotNull(domain);
		Assert.assertEquals(1, domain.getClasses().size());
		MdfClass classA = (MdfClass) domain.getClasses().get(0);
		MdfProperty attr = classA.getProperty("attribute");
		MdfEntity type = attr.getType();
		Assert.assertTrue(type instanceof MdfEnumeration);
		MdfEnumeration enumeration = (MdfEnumeration) type;
		List<MdfEnumValue> enumValues = enumeration.getValues();
		Assert.assertEquals(2, enumValues.size());
		Assert.assertEquals("1", enumValues.get(0).getValue());
		Assert.assertEquals("2", enumValues.get(1).getValue());
		
		// now we replace Enums.domain by a version where the two values of MyEnum are exchanged, i.e. 2 comes before 1.
		
		URL url = FileLocator.find(TestFrameworkHeadlessCore.getDefault().getBundle(), new Path(RESOURCES_FOLDER + "/domain/ds3652/Enums_modified.domain"), null);
        File modifiedFile = new File(FileLocator.toFileURL(url).toURI());
        IFile enumFile = (IFile) projectA.getProject().getFile(new Path("domain/ds3652/Enums.domain"));
        enumFile.setContents(new FileInputStream(modifiedFile), IFile.FORCE, null);

        // let's repeat the same test from above and check that we now see the updated order of the enum values.
        
		domain = DomainRepository.getInstance(projectB).getDomain("MyDomain");
		Assert.assertNotNull(domain);
		Assert.assertEquals(1, domain.getClasses().size());
		classA = (MdfClass) domain.getClasses().get(0);
		attr = classA.getProperty("attribute");
		type = attr.getType();
		Assert.assertTrue(type instanceof MdfEnumeration);
		enumeration = (MdfEnumeration) type;
		enumValues = enumeration.getValues();
		Assert.assertEquals(2, enumValues.size());
		Assert.assertEquals("1", enumValues.get(0).getValue());
		Assert.assertEquals("2", enumValues.get(1).getValue());
	}
	
}
