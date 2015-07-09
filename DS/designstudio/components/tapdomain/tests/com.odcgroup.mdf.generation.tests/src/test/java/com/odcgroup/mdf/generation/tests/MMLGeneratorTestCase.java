package com.odcgroup.mdf.generation.tests;

import java.io.File;
import java.io.FileNotFoundException;
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
import org.junit.Ignore;
import org.junit.Test;

import com.odcgroup.mdf.generation.ModelCopyGenerator;
import com.odcgroup.workbench.core.IOfsModelResource;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.core.OfsCore;
import com.odcgroup.workbench.core.repository.IDependencyManager;
import com.odcgroup.workbench.core.repository.ModelNotFoundException;
import com.odcgroup.workbench.core.repository.ModelURIConverter;
import com.odcgroup.workbench.core.tests.util.AbstractDSUnitTest;

public class MMLGeneratorTestCase extends AbstractDSUnitTest {
	
	private static final String OUTPUT_FOLDER = "output";
	private static final String DS_4860_MODEL_FILE = "ds4860/DS4860.domain";
	private static final String DS_5741_MODEL_FILE = "ds5741/DS5741.domain";
	private static final String DS_5741B_MODEL_FILE = "ds5741/DS5741B.domain";

	@Before
	public void setUp() throws Exception {
		createModelsProject();
		OfsCore.addNature(getProject(), "de.visualrules.core.visualrulesNature");
		OfsCore.getDependencyManager().disableAutoResolution();
		copyResourceInModelsProject(DOMAIN_LOCATION+"/aaa/entities/BusinessTypes.domain");
	}
	
	@After
	public void tearDown() throws Exception {
		deleteModelsProjects();
	}
	
	@SuppressWarnings("unchecked")
	@Test @Ignore // this will be fix this part of DS-7435
	public void testMMLDatasetDerivedAssociation_DS5741() throws IOException, ModelNotFoundException, CoreException {

		copyResourceInModelsProject(DOMAIN_LOCATION+"/"+DS_5741_MODEL_FILE);
		copyResourceInModelsProject(DOMAIN_LOCATION+"/"+DS_5741B_MODEL_FILE);

		IOfsProject ofsProject = getOfsProject();
        IDependencyManager depMgr = OfsCore.getDependencyManager(getProject());
        depMgr.resolveDependencies();
		ModelCopyGenerator generator = new ModelCopyGenerator();
		URI modeluri = ModelURIConverter.createModelURI(DS_5741_MODEL_FILE);
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

		File generatedFile = findGeneratedFile(generatedFiles, "META-INF\\models\\ds5741.mml");
		Assert.assertNotNull(generatedFile);
		
		String contents = getFileContents(generatedFile);
		System.out.println(contents);
		
		contents = contents.trim().replaceAll("\\s+", " ");
				
		Assert.assertTrue(contents.contains(  
				"<mml:derived-property name=\"refB\" type=\"DatasetB\" multiplicity=\"one\"/>"));
		
		Assert.assertTrue(contents.contains(  
				"<mml:derived-property name=\"refC\" type=\"DS5741B:DatasetC\" multiplicity=\"one\"/>"));

	}
	
	@SuppressWarnings("unchecked")
	@Test @Ignore // this will be fix this part of DS-7435
	public void testClassAttributeWithMultiplicityMany_DS4860() throws ModelNotFoundException, CoreException, IOException {
		
		copyResourceInModelsProject(DOMAIN_LOCATION+"/"+DS_4860_MODEL_FILE);

		IOfsProject ofsProject = getOfsProject();
        IDependencyManager depMgr = OfsCore.getDependencyManager(getProject());
        depMgr.resolveDependencies();
		ModelCopyGenerator generator = new ModelCopyGenerator();
		URI modeluri = ModelURIConverter.createModelURI(DS_4860_MODEL_FILE);
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

		File generatedFile = findGeneratedFile(generatedFiles, "META-INF\\models\\ds4860.mml");
		Assert.assertNotNull(generatedFile);
		
		String contents = getFileContents(generatedFile);
		System.out.println(contents);
		
		contents = contents.trim().replaceAll("\\s+", " ");
				
		Assert.assertTrue(contents.contains(    
				"<mml:class name=\"Class4860\" abstract=\"false\"> "+
				"<mml:attribute name=\"atr\" type=\"Enum4860\" business-key=\"false\" " +
				"primary-key=\"false\" required=\"true\" default=\"Value1\" multiplicity=\"many\"/> " +
				"<mml:attribute name=\"id\" type=\"AAABusinessTypes:Id\" "+
				"business-key=\"false\" primary-key=\"true\" required=\"false\"/> "+
				"</mml:class>"));
		
		Assert.assertTrue(contents.contains(    
				"<mml:dataset name=\"DS4860Dataset\" base-class=\"Class4860\" "+
				"linked=\"false\" sync=\"false\"> "+
				"<mml:mapped-property name=\"atr\" unique=\"true\" "+
	            "single-valued=\"false\" path=\"atr\"/> "+
	            "<mml:mapped-property name=\"id\" unique=\"true\" "+
	            "single-valued=\"false\" path=\"id\"/> "+
				"</mml:dataset>"));
		
	}
	
	/**
	 * @param generatedFiles
	 * @param fileSearched
	 * @return
	 */
	private File findGeneratedFile(Collection<File> generatedFiles, String fileSearched) {
		for (File file : generatedFiles) {
			if (file.getAbsolutePath().endsWith(OUTPUT_FOLDER+"\\" + fileSearched.toString())) {
				return file;
			}
		}
		return null;
	}
	
	/**
	 * @param generatedFile
	 * @return the (raw) file contents
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
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
