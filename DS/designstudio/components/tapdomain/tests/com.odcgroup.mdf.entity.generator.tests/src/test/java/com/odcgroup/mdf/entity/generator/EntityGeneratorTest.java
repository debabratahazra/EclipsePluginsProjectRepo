package com.odcgroup.mdf.entity.generator;

import static org.junit.Assert.assertNotNull;

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
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.odcgroup.mdf.generation.OAWGenerator;
import com.odcgroup.workbench.core.IOfsModelResource;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.core.repository.ModelNotFoundException;
import com.odcgroup.workbench.core.tests.util.AbstractDSUnitTest;

/**
 * @author yan
 */
public class EntityGeneratorTest extends AbstractDSUnitTest {

	private List<IStatus> nonOkStatuses;
	
	@Before
	public void setUp() throws Exception {
		createModelsProject();
		copyResourceInModelsProject("domain/ds1977/DS-1977.domain",
									"domain/ds3986/DS3986.domain",
									"domain/ds3986/DS3986_Enum.domain",
									"domain/ds3862/DS3862.domain",
									"domain/ds4248/DS4248.domain",
									"domain/aaa/entities/BusinessTypes.domain",
									"domain/ds4860/DS4860.domain"
									);
	}

	@After
	public void tearDown() throws Exception {
		deleteModelsProjects();
	}

	/**
	 * Test the Bootstrap Code Generator
	 * @throws ModelNotFoundException
	 * @throws CoreException
	 */
	@Test
	public void testBootstrapCodeGenerator() throws ModelNotFoundException, CoreException {
		Collection<File> generatedFiles = generateCode(
				getOfsProject(), 
				"ds1977/DS-1977.domain", 
				new BootstrapCodeGenerator());

		Assert.assertEquals(1, generatedFiles.size());
		File generatedFile = findGeneratedFile(generatedFiles, "com/odcgroup/mdf/testmodel/DS1977Model.java");
		Assert.assertNotNull(generatedFile);
		
		String generatedFileContents = getJavaContents(generatedFile);
		Assert.assertTrue(generatedFileContents.contains("public final class DS1977Model extends MdfCoreRepository"));
		Assert.assertTrue(generatedFileContents.contains("loadDomain(\"DS1977\""));
		Assert.assertTrue(generatedFileContents.contains("private static final Logger _LOGGER = LoggerFactory.getLogger(MdfCoreRepository.class);"));
	}

	/**
	 * Test the Class Code Generator
	 * @throws ModelNotFoundException
	 * @throws CoreException
	 */
	@Test
	public void testClassCodeGenerator() throws ModelNotFoundException, CoreException {
		Collection<File> generatedFiles = generateCode(
				getOfsProject(), 
				"ds1977/DS-1977.domain", 
				new ClassCodeGenerator());
		
		Assert.assertEquals(2, generatedFiles.size());
		File generatedFile1 = findGeneratedFile(generatedFiles, "com/odcgroup/mdf/testmodel/SomeClass.java");
		Assert.assertNotNull(generatedFile1);
		File generatedFile2 = findGeneratedFile(generatedFiles, "com/odcgroup/mdf/testmodel/SomeLinkedClass.java");
		Assert.assertNotNull(generatedFile2);
		
		String generatedFile1Contents = getJavaContents(generatedFile1);
		for (String test : new String[]{"public interface SomeClass",
				"public com.odcgroup.mdf.testmodel.SomeLinkedClass getAReference()",
				"public void setAReference(com.odcgroup.mdf.testmodel.SomeLinkedClass aReference)",
				"public Integer getAnEnum()",
				"public void setAnEnum(Integer anEnum)",
				"public Long getId1()",
				"public void setId1(Long id1)",
				"public Integer getId2()",
				"public void setId2(Integer id2)",
				"public String getAString()",
				"public void setAString(String aString)"}) {
			Assert.assertTrue("Should contains \"" + test + "\", but it doesn't", generatedFile1Contents.contains(test));
		}
			
		String generatedFile2Contents = getJavaContents(generatedFile2);
		for (String test : new String[]{"public interface SomeLinkedClass",
				"public Long getId()",
				"public void setId(Long id)",
				"public String getLabel()",
				"public void setLabel(String label)",
				"public java.util.Date getSomeMoreAttributes()",
				"public void setSomeMoreAttributes(java.util.Date someMoreAttributes)",
				"public com.odcgroup.mdf.testmodel.SomeLinkedClass getParent()",
				"public void setParent(com.odcgroup.mdf.testmodel.SomeLinkedClass parent)"}) {
			Assert.assertTrue(generatedFile2Contents.contains(test));
		}
	}
	
	/**
	 * Test the Dataset Code Generator
	 * @throws ModelNotFoundException
	 * @throws CoreException
	 */
	@Test
	public void testDatasetCodeGenerator() throws ModelNotFoundException, CoreException {
		Collection<File> generatedFiles = generateCode(
				getOfsProject(), 
				"ds1977/DS-1977.domain", 
				new DatasetCodeGenerator());
		
		Assert.assertEquals(3, generatedFiles.size());
		File generatedFile1 = findGeneratedFile(generatedFiles, "com/odcgroup/mdf/testmodel/SomeDataset.java");
		Assert.assertNotNull(generatedFile1);
		File generatedFile2 = findGeneratedFile(generatedFiles, "com/odcgroup/mdf/testmodel/SomeDataset2.java");
		Assert.assertNotNull(generatedFile2);
		File generatedFile3 = findGeneratedFile(generatedFiles, "com/odcgroup/mdf/testmodel/SomeLinkedDataset.java");
		Assert.assertNotNull(generatedFile3);
		
		String generatedFile1Contents = getJavaContents(generatedFile1);
		for (String test : new String[]{"public interface SomeDataset extends EntityDataset",
				"public String getAString()",
				"public void setAString(String aString)"}) {
			Assert.assertTrue(generatedFile1Contents.contains(test));
		}
		String generatedFile2Contents = getJavaContents(generatedFile2);
		for (String test : new String[]{"public interface SomeDataset2 extends EntityDataset",
				"public com.odcgroup.mdf.testmodel.SomeLinkedDataset getAReference()",
				"public void setAReference(com.odcgroup.mdf.testmodel.SomeLinkedDataset aReference)",
				"public String getAString()",
				"public void setAString(String aString)",
				"public Integer getAnEnum()",
				"public void setAnEnum(Integer anEnum)",
				"public Long getId1()",
				"public void setId1(Long id1)",
				"public Integer getId2()",
				"public void setId2(Integer id2)",
				"public String getCalculatedAttribute()",
				"public void setCalculatedAttribute(String calculatedAttribute)"}) {
			Assert.assertTrue("The generated code should contain " + test, generatedFile2Contents.contains(test));
		}
		String generatedFile3Contents = getJavaContents(generatedFile3);
		for (String test : new String[]{"public interface SomeLinkedDataset extends EntityDataset",
				"public Long getId()",
				"public void setId(Long id)",
				"public String getLabel()",
				"public void setLabel(String label)"}) {
			Assert.assertTrue(generatedFile3Contents.contains(test));
		}
	}
	
	@Test
	public void testDS3862DatasetDoesntContainAtWithMdfNameAfterGeneration() throws Exception {
		Collection<File> generatedFiles = generateCode(
				getOfsProject(), 
				"ds3862/DS3862.domain", 
				new DatasetCodeGenerator());

		final String TODO_IMPORT_COM_ODCGROUP_MDF_DYN_WITH_MDF_NAME = "// TODO import com.odcgroup.mdf.dyn.WithMdfName;";
		final String TODO_WITH_MDF_NAME_DS3862_DS3862_DATASET = "// TODO @WithMdfName(\"DS3862:DS3862Dataset\")";
		
		Assert.assertEquals(1, generatedFiles.size());
		
		File generatedFile = findGeneratedFile(generatedFiles, "ds3862/DS3862Dataset.java");
		Assert.assertNotNull(generatedFile);
		
		String generatedFileContents = getJavaContents(generatedFile);
		Assert.assertFalse("file contains \""+TODO_IMPORT_COM_ODCGROUP_MDF_DYN_WITH_MDF_NAME+"\"", generatedFileContents.contains(TODO_IMPORT_COM_ODCGROUP_MDF_DYN_WITH_MDF_NAME));
		Assert.assertFalse("file contains \""+TODO_WITH_MDF_NAME_DS3862_DS3862_DATASET+"", generatedFileContents.contains(TODO_WITH_MDF_NAME_DS3862_DS3862_DATASET));
		
	}
	
	@Test
	public void testDS3768DatasetContainsFACTORYfieldAfterGeneration() throws Exception {
		Collection<File> generatedFiles = generateCode(
				getOfsProject(), 
				"ds3862/DS3862.domain", 
				new DatasetCodeGenerator());

		final String EXPECTED_FACTORY_DECLARATION = "public static final MdfBeanHelper.DataBeanFactory<DS3862Dataset> FACTORY = MdfBeanHelper.newBeanFactory(DS3862Dataset.class)";
		
		Assert.assertEquals(1, generatedFiles.size());
		
		File generatedFile = findGeneratedFile(generatedFiles, "ds3862/DS3862Dataset.java");
		Assert.assertNotNull(generatedFile);
		
		String generatedFileContents = getJavaContents(generatedFile);
		Assert.assertTrue("file contains \""+EXPECTED_FACTORY_DECLARATION+"\"", generatedFileContents.contains(EXPECTED_FACTORY_DECLARATION));
	}
	
	/**
	 * Test the Enum Code Generator
	 * @throws ModelNotFoundException
	 * @throws CoreException
	 */
	@Test
	public void testEnumCodeGenerator() throws ModelNotFoundException, CoreException {
		Collection<File> generatedFiles = generateCode(
				getOfsProject(), 
				"ds1977/DS-1977.domain", 
				new EnumCodeGenerator());
		
		Assert.assertEquals(1, generatedFiles.size());
		File generatedFile = findGeneratedFile(generatedFiles, "com/odcgroup/mdf/testmodel/SomeEnumeration.java");
		Assert.assertNotNull(generatedFile);
		
		String generatedFileContents = getJavaContents(generatedFile);
		for (String test : new String[]{"public final class SomeEnumeration",
				"public static final int A",
				"public static final int B",
				"public static final int C"}) {
			Assert.assertTrue(generatedFileContents.contains(test));
		}
	}
	
	/**
	 * Testing handling of NPE when generating a model with invalid dataset property.
	 * @throws ModelNotFoundException
	 * @throws CoreException
	 */
	@Test
	public void testCodeGeneratorNPE_WithIncorrectDataSet_DS3986() throws ModelNotFoundException, CoreException {
		generateCode(getOfsProject(), "ds3986/DS3986.domain", new DatasetCodeGenerator());
		Assert.assertEquals("NPE occured.", 1, nonOkStatuses.size());
	}

	/**
	 * Testing handling of NPE when generating an model with invalid enum.
	 * @throws ModelNotFoundException
	 * @throws CoreException
	 */
	@Test
	public void testCodeGeneratorNPE_WithIncorrectEnum_DS3986() throws ModelNotFoundException, CoreException {
		generateCode(getOfsProject(), "ds3986/DS3986_Enum.domain", new EnumCodeGenerator());
		Assert.assertEquals("NPE occured.", 1, nonOkStatuses.size());
	}
	
	@Test
	public void testDS4248TestDatasetInterfaceShouldMdfNameFactory() throws Exception {
		Collection<File> generatedFiles = generateCode(
				getOfsProject(), 
				"ds4248/DS4248.domain", 
				new DatasetCodeGenerator());
		String FACTORY_STRING = "MdfName MDF_NAME = MdfNameFactory.createMdfName(\"DS4248\",\"DS4248DS\");";
		Assert.assertEquals(1, generatedFiles.size());
		
		File generatedFile = findGeneratedFile(generatedFiles, "ds4248/DS4248DS.java");
		Assert.assertNotNull(generatedFile);
		
		String generatedFileContents = getJavaContents(generatedFile);
		Assert.assertTrue(generatedFileContents, generatedFileContents.contains(FACTORY_STRING));
	}
	
	/**
	 * @throws Exception
	 */
	@Test @Ignore
	public void testDS4860DatasetWithAttributeBasedOnEnumMask() throws Exception {
		waitForAutoBuild();
		Collection<File> generatedFiles = generateCode(
				getOfsProject(), 
				"ds4860/DS4860.domain", 
				new DatasetCodeGenerator());
		String FACTORY_STRING = "MdfName MDF_NAME = MdfNameFactory.createMdfName(\"DS4860\", \"DS4860Dataset\");";
		Assert.assertEquals(1, generatedFiles.size());
		
		File generatedFile = findGeneratedFile(generatedFiles, "ds4860/DS4860Dataset.java");
		Assert.assertNotNull(generatedFile);
		
		String generatedFileContents = getJavaContents(generatedFile);
		Assert.assertTrue(generatedFileContents.contains(FACTORY_STRING));
		
		for (String test : new String[]{
				"public java.util.Set<Integer> getAtr();",
				"public void setAtr(java.util.Set<Integer> atr);"}) 
		{
			Assert.assertTrue(generatedFileContents.contains(test));
		}
	}
	
	/**
	 * Execute a code generator for a model in a project
	 * @param ofsProject
	 * @param modelFile
	 * @param generator
	 * @return the list of generated files
	 * @throws ModelNotFoundException
	 * @throws CoreException
	 */
	private Collection<File> generateCode(IOfsProject ofsProject, String modelFile, 
			OAWGenerator generator) throws ModelNotFoundException, CoreException {
		IOfsModelResource res = getModelResource(ofsProject, modelFile);
		assertNotNull(res);
		Set<IOfsModelResource> resources = Collections.singleton(res);
		File rootProject = ofsProject.getProject().getLocation().toFile();
		
		Collection<File> filesBefore = FileUtils.listFiles(rootProject, null, true);
		nonOkStatuses = new ArrayList<IStatus>();
		generator.doRun(getProject(), getProject().getFolder("generated"), resources, nonOkStatuses);
		getProject().refreshLocal(IResource.DEPTH_INFINITE, null);
		
		Collection<File> generatedFiles = FileUtils.listFiles(rootProject, null, true);
		generatedFiles.removeAll(filesBefore);
		return generatedFiles;
	}
	
	/**
	 * Find a generated file in the collection of a generated file
	 * @param generatedFiles 
	 * @param fileSearched
	 * @return the file found or <code>null</code> otherwise
	 */
	private File findGeneratedFile(Collection<File> generatedFiles, String fileSearched) {
		for (File file : generatedFiles) {
			String filepath = file.getAbsolutePath().replace('\\', '/');
			if (filepath.endsWith("generated/" + fileSearched.toString())) {
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
	
	/**
	 * @param javaFile
	 * @return the file content with new lines and tab removed 
	 * (helps for testing)
	 */
	private String getJavaContents(File javaFile) {
		String contents = getFileContents(javaFile);
		contents = contents.replace("\n", "");
		contents = contents.replace("\r", "");
		contents = contents.replace("\t", "");
		return contents;
	}
	
}
