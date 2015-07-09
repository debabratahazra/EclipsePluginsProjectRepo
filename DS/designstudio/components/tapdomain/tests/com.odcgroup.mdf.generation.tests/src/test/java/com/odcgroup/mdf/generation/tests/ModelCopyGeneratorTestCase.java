package com.odcgroup.mdf.generation.tests;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.common.util.URI;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.Ignore;

import com.odcgroup.mdf.generation.ModelCopyGenerator;
import com.odcgroup.mdf.model.translation.MdfTranslation;
import com.odcgroup.workbench.core.IOfsModelResource;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.core.OfsCore;
import com.odcgroup.workbench.core.repository.IDependencyManager;
import com.odcgroup.workbench.core.repository.ModelNotFoundException;
import com.odcgroup.workbench.core.repository.ModelURIConverter;
import com.odcgroup.workbench.core.tests.util.AbstractDSUnitTest;

/**
 *
 * @author pkk
 *
 */
public class ModelCopyGeneratorTestCase extends AbstractDSUnitTest {
	
	private static final String OUTPUT_FOLDER = "output";
	private static final String MODEL_FILE = "ds3410/new-domain.domain";

	@Before
	public void setUp() throws Exception {
		createModelsProject();
		OfsCore.addNature(getProject(), "de.visualrules.core.visualrulesNature");
		OfsCore.getDependencyManager().disableAutoResolution();
		copyResourceInModelsProject(DOMAIN_LOCATION+"/"+MODEL_FILE);
	}

	@After
	public void tearDown() throws Exception {
		deleteModelsProjects();
	}
	
	
	/**
	 * DS-3410
	 * @throws ModelNotFoundException
	 * @throws CoreException
	 */
	@Test @Ignore // this will be fix this part of DS-7435
	public void testModelCopyGenerator() throws ModelNotFoundException, CoreException, IOException {
		
		IOfsProject ofsProject = getOfsProject();
        IDependencyManager depMgr = OfsCore.getDependencyManager(getProject());
        depMgr.resolveDependencies();
		ModelCopyGenerator generator = new ModelCopyGenerator();
		URI modeluri = ModelURIConverter.createModelURI(MODEL_FILE);
		Set<IOfsModelResource> resources = Collections.singleton(ofsProject.getOfsModelResource(modeluri));
		File rootProject = ofsProject.getProject().getLocation().toFile();
		
		Collection<File> filesBefore = FileUtils.listFiles(rootProject, null, true);
		IFolder outputFolder = ofsProject.getProject().getFolder(OUTPUT_FOLDER);
		outputFolder.create(true, true, null);
		List<IStatus> nonOkStatuses = new ArrayList<IStatus>();
		generator.doRun(getProject(), outputFolder, resources, nonOkStatuses);
		getProject().refreshLocal(IResource.DEPTH_INFINITE, null);
		
		Collection<File> generatedFiles = FileUtils.listFiles(rootProject, null, true);
		generatedFiles.removeAll(filesBefore);

		File generatedFile = findGeneratedFile(generatedFiles, "META-INF\\models\\new-domain.mml");
		Assert.assertNotNull(generatedFile);
		
		String content = getFileContents(generatedFile);
		Assert.assertEquals(false, content.contains(MdfTranslation.NAMESPACE_URI));
	}
	
	private File findGeneratedFile(Collection<File> generatedFiles, String fileSearched) {
		for (File file : generatedFiles) {
			if (file.getAbsolutePath().endsWith(OUTPUT_FOLDER+"\\" + fileSearched.toString())) {
				return file;
			}
		}
		return null;
	}
	
	private String getFileContents(File generatedFile) {
		FileReader fileReader = null;
		try {
			fileReader = new FileReader(generatedFile);
			return IOUtils.toString(fileReader);
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			IOUtils.closeQuietly(fileReader);
		}
	}
	
}
