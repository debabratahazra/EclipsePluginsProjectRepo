package com.odcgroup.template;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import com.odcgroup.workbench.core.OfsCore;

/**
 * @author yan
 */
public class TemplateCreatorTest {
	
	/**
	 * Test that template descriptors are correctly read from jars
	 */
	@Test
	public void testGetAvailableTemplatesInJars() {
		TemplateCreator.instance().setTemplateProvider(new ITemplateProvider() {
			public List<File> getAvailableTemplates() {
				try {
					URL url = FileLocator.find(OfsCore.getDefault().getBundle(),
			                new Path("resources/templates/set1"), null);
					File set1 = new File(FileLocator.toFileURL(url).getPath());
					return Arrays.asList(set1.listFiles());
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
		});
		
		List<TemplateDescriptor> descriptors = TemplateCreator.instance().getAvailableTemplates();
		Assert.assertEquals("Wrong number of descriptor found (two have errors)", 3, descriptors.size());
		Assert.assertEquals("Wrong template name", "template-001", descriptors.get(0).getName());
		Assert.assertEquals("Wrong template name", "template-002", descriptors.get(1).getName());
		Assert.assertEquals("Wrong template name", "template-003", descriptors.get(2).getName());
	}

	/**
	 * Test the filtering on the content
	 */
	@Test
	public void testFilter() {
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("param1", "value1");
		parameters.put("param2", "value2");
		
		Assert.assertEquals("value1", TextFilter.instance().filter("${param1}", parameters));
		Assert.assertEquals("value2", TextFilter.instance().filter("${param2}", parameters));
		Assert.assertEquals("value1value2", TextFilter.instance().filter("${param1}${param2}", parameters));
		Assert.assertEquals(" value1 value2 ", TextFilter.instance().filter(" ${param1} ${param2} ", parameters));
		Assert.assertEquals("value1\nvalue2\n", TextFilter.instance().filter("${param1}\n${param2}\n", parameters));
		Assert.assertEquals("nothing to filter", TextFilter.instance().filter("nothing to filter", parameters));
		Assert.assertEquals("", TextFilter.instance().filter("", parameters));
	}

	/**
	 * Test filtering with function call
	 */
	@Test
	public void testFilterWithFunctionCall() {
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("param1", "valueName");
		parameters.put("param2", "v");
		parameters.put("param3", "");
		
		Assert.assertEquals("VALUENAME", TextFilter.instance().filter("${param1#uppercase}", parameters));
		Assert.assertEquals("valuename", TextFilter.instance().filter("${param1#lowercase}", parameters));
		Assert.assertEquals("ValueName", TextFilter.instance().filter("${param1#capitalize}", parameters));
		Assert.assertEquals("V", TextFilter.instance().filter("${param2#capitalize}", parameters));
		Assert.assertEquals("", TextFilter.instance().filter("${param3#capitalize}", parameters));
	}

	/**
	 * Test template creating (without file and folder filtering)
	 * @throws IOException
	 */
	@Test
	public void testCreateTemplates() throws IOException {
		TemplateCreator.instance().setTemplateProvider(new ITemplateProvider() {
			public List<File> getAvailableTemplates() {
				try {
					URL url = FileLocator.find(OfsCore.getDefault().getBundle(),
			                new Path("resources/templates/set2"), null);
					File set2 = new File(FileLocator.toFileURL(url).getPath());
					return Arrays.asList(set2.listFiles());
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
		});
		
		List<TemplateDescriptor> descriptors = TemplateCreator.instance().getAvailableTemplates();
		Map<String, String> params = new HashMap<String, String>();
		params.put("version", "123.4");
		params.put("custo-name", "SomeCustoName");
		
		File tempDir = createTempDirectory();
		
		TemplateCreator.instance().createTemplate(descriptors.get(0), params, tempDir);
		
		Assert.assertTrue("file missing", new File(tempDir, "abc").exists());
		Assert.assertTrue("file missing", new File(tempDir, "abc/pom.xml").exists());
		Assert.assertTrue("file missing", new File(tempDir, "abc/.project").exists());
		Assert.assertTrue("file missing", new File(tempDir, "abc/.classpath").exists());
		Assert.assertTrue("file missing", new File(tempDir, "abc/.settings").exists());
		Assert.assertTrue("file missing", new File(tempDir, "abc/.settings/org.eclipse.jdt.core.prefs").exists());
		Assert.assertTrue("file missing", new File(tempDir, "abc/src").exists());
		Assert.assertFalse("descriptor should be skipped", new File(tempDir, "template-descriptor.properties").exists());
		
		Reader reader = null;
		String pomXml = null;
		try {
			reader = new FileReader(new File(tempDir, "abc/pom.xml"));
			pomXml = IOUtils.toString(reader);
		} finally {
			IOUtils.closeQuietly(reader);
		}
		
		Assert.assertEquals("contents not filtered properly", 2, StringUtils.countMatches(pomXml,"<version>123.4</version>"));
		Assert.assertTrue("contents not filtered properly", pomXml.contains("<groupId>com.odcgroup.ocs.custo.somecustoname</groupId>"));
		Assert.assertTrue("contents not filtered properly", pomXml.contains("<name>SomeCustoName</name>"));

		String project = null;
		try {
			reader = new FileReader(new File(tempDir, "abc/.project"));
			project = IOUtils.toString(reader);
		} finally {
			IOUtils.closeQuietly(reader);
		}
		
		String expectedProject = null;
		try {
			URL url = FileLocator.find(OfsCore.getDefault().getBundle(),
	                new Path("resources/templates/set2/expected.project"), null);
			reader = new FileReader(new File(FileLocator.toFileURL(url).getPath()));
			expectedProject = IOUtils.toString(reader);
		} finally {
			IOUtils.closeQuietly(reader);
		}
		
		Assert.assertEquals("Non filtered content differs", expectedProject, project);
	}

	/**
	 * Test template creating (with file and folder filtering)
	 * @throws IOException
	 */
	@Test
	public void testCreateTemplateWithFilteredFilenameAndDir() throws IOException {
		TemplateCreator.instance().setTemplateProvider(new ITemplateProvider() {
			public List<File> getAvailableTemplates() {
				try {
					URL url = FileLocator.find(OfsCore.getDefault().getBundle(),
			                new Path("resources/templates/set2"), null);
					File set2 = new File(FileLocator.toFileURL(url).getPath());
					return Arrays.asList(set2.listFiles());
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
		});
		
		List<TemplateDescriptor> descriptors = TemplateCreator.instance().getAvailableTemplates();
		Map<String, String> params = new HashMap<String, String>();
		params.put("version", "123.4");
		params.put("custo-name", "SomeCustoName");
		params.put("package", "com.odcgroup.ocs.custo");
		
		File tempDir = createTempDirectory();
		
		TemplateCreator.instance().createTemplate(descriptors.get(1), params, tempDir);
		
		Assert.assertTrue("file missing", new File(tempDir, "somecustoname").exists());
		Assert.assertTrue("file missing", new File(tempDir, "somecustoname/src/com/odcgroup/ocs/custo/sub/TestSomeCustoName.java").exists());
		Assert.assertFalse("descriptor should be skipped", new File(tempDir, "template-descriptor.properties").exists());
		
		Reader reader = null;
		String javaFileContents = null;
		try {
			reader = new FileReader(new File(tempDir, "somecustoname/src/com/odcgroup/ocs/custo/sub/TestSomeCustoName.java"));
			javaFileContents = IOUtils.toString(reader);
		} finally {
			IOUtils.closeQuietly(reader);
		}
		
		String expectedJavaFileContents = null;
		try {
			URL url = FileLocator.find(OfsCore.getDefault().getBundle(),
	                new Path("resources/templates/set2/expected.TestSomeCustoName.java"), null);
			reader = new FileReader(new File(FileLocator.toFileURL(url).getPath()));
			expectedJavaFileContents = IOUtils.toString(reader);
		} finally {
			reader.close();
		}
		
		Assert.assertEquals("Filtered content differs", expectedJavaFileContents, javaFileContents);
	}

//	DS-6507 ignored till its green
	@Ignore
	public void testCreateTemplateWithExclusions() throws IOException {
		TemplateCreator.instance().setTemplateProvider(new ITemplateProvider() {
			public List<File> getAvailableTemplates() {
				try {
					URL url = FileLocator.find(OfsCore.getDefault().getBundle(),
			                new Path("resources/templates/set2"), null);
					File set2 = new File(FileLocator.toFileURL(url).getPath());
					return Arrays.asList(set2.listFiles());
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
		});
		
		List<TemplateDescriptor> descriptors = TemplateCreator.instance().getAvailableTemplates();
		Map<String, String> params = new HashMap<String, String>();
		params.put("custo-name", "SomeCustoName");
		
		File tempDir = createTempDirectory();
		
		TemplateCreator.instance().createTemplate(descriptors.get(2), params, tempDir);
		
		Reader reader = null;
		String[] filteredFiles = new String[]{
				"abc/pom.xml",
				"abc/.project",
				"abc/.classpath",
				"abc/src/filteredFolder/filtered.txt"
		};
		String[] notFilteredFiles = new String[]{
				"abc/src/filteredFolder/notFilteredByExtention.notFiltered",
				"abc/src/filteredFolder/notFilteredByName.txt",
				"abc/src/filteredFolder/notFilteredByWildCard-1.txt",
				"abc/src/filteredFolder/notFilteredByWildCard-2.txt",
				"abc/src/filteredFolder/notFilteredByWildCard-abc.txt",
				"abc/src/notFilteredFolder/abc.txt",
				"notFilteredProject/pom.xml",
		};
		String contents;
		try {
			for (String file : filteredFiles) {
				reader = new FileReader(new File(tempDir, file));
				contents = IOUtils.toString(reader);
				Assert.assertFalse("The file " + file + " should be filtered, therefore it shouldn't contain ${custo-name}", contents.contains("${custo-name}"));
			}
			
			for (String file : notFilteredFiles) {
				reader = new FileReader(new File(tempDir, file));
				contents = IOUtils.toString(reader);
				Assert.assertTrue("The file " + file + " shouldn't be filtered, therefore it should contain ${custo-name}", contents.contains("${custo-name}"));
			}
		} finally {
			IOUtils.closeQuietly(reader);
		}
	}
	
	/**
	 * Create a temporary directory that will be deleted when the
	 * JVM exits
	 * @return the temporary directory
	 * @throws IOException
	 */
	private File createTempDirectory() throws IOException {
		File file = File.createTempFile("temp", ""+System.currentTimeMillis());
		if (!file.delete()) {
			throw new IOException("Couldn't delete temporary file: " + file.getAbsolutePath());
		}
		if (!file.mkdirs()) {
			throw new IOException("Couldn't create temporary folder: " + file.getAbsolutePath());
		}
		
		file.deleteOnExit();
		return file;
	}

}
