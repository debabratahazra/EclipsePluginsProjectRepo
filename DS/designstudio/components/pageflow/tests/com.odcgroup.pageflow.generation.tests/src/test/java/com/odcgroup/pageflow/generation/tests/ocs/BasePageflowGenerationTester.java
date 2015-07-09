/**
 * 
 */
package com.odcgroup.pageflow.generation.tests.ocs;

import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import com.odcgroup.pageflow.generation.PageflowGenerationCore;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.core.InvalidMetamodelVersionException;
import com.odcgroup.workbench.testframework.headless.TestFrameworkHeadlessCore;

/**
 * Unit tests for the pageflow generation. There are three steps involved The
 * first checks the generation, the second validates the generated files against
 * a schema and the third involves checking the content of a generated file
 * against existing templates
 * 
 * Checking the content of generated files are primarily performed by the
 * sub-classes by specifying the element name and the key attribute.
 * 
 * 
 * @author nba
 * 
 */
public abstract class BasePageflowGenerationTester extends BasePageflowGenerationTest {

	IProject project;	

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	@Before
	public void setUp() throws Exception {
    	IOfsProject ofsProject = createModelsProject();
    	copyPageflowResourcesToModelProject(ofsProject);
    	// create output folder for pageflow gen
		project = ofsProject.getProject();
		project.open(null);
		project.getFullPath().append(OCSOUTPUT_DIR);
		project.getFolder(ROOT_OCSOUTPUT_DIR).create(true, true, null);
	}
	
	@After
	public void tearDown() {
		deleteModelsProjects();
	}
	
	/**
	 * @param ofsProject
	 * @throws URISyntaxException
	 * @throws IOException
	 */
	private void copyPageflowResourcesToModelProject(final IOfsProject ofsProject) throws URISyntaxException, IOException {		
		URL url = FileLocator.find(TestFrameworkHeadlessCore.getDefault().getBundle(), new Path(OCSPAGEFLOW_DIR), null);
        final File pfdir = new File(FileLocator.toFileURL(url).toURI());
        Assert.assertTrue(pfdir.exists() && pfdir.isDirectory());
        final FilenameFilter ff = new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return name.endsWith(".pageflow");
			}
		};		
		class MyWorkspaceJob extends WorkspaceJob {
			public MyWorkspaceJob() {
				super("Copy Model Resource Job");
			}
			public IStatus runInWorkspace(IProgressMonitor monitor) throws CoreException {
				for (File sourceResourceFile : pfdir.listFiles(ff)) {					
					String targetFolder = "pageflow/generation-tests/";			
					try {
						mkdirs(ofsProject.getProject().getFolder(targetFolder));
					} catch (CoreException e) {
						throw new RuntimeException("Unable to create the folders", e);
					}			
					IFile targetFile = ofsProject.getProject().getFile(targetFolder + sourceResourceFile.getName());
		            try {
						targetFile.create(new FileInputStream(sourceResourceFile), true, null);
					} catch (Exception e) {
						throw new RuntimeException("Unable to add pageflow models to " + ofsProject + " models project", e);
					}
				}
				return Status.OK_STATUS;
			}
		}
		MyWorkspaceJob job = new MyWorkspaceJob();
		job.setRule(ofsProject.getProject());
		job.schedule();
		try {
			job.join();
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}

	}

	/**
	 * @throws IOException
	 * @throws URISyntaxException
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws InvalidMetamodelVersionException
	 */
	@Test
	public void testPageflowGeneration() throws IOException, URISyntaxException, ParserConfigurationException, SAXException, InvalidMetamodelVersionException {
		// need to create an instance in order to have the notation package
		// registered outside Eclipse
		boolean success = runPageflowGeneration(getOfsProject(), ROOT_OCSOUTPUT_DIR);
		Assert.assertTrue(success);

		// now validate the generated files
		File dir = project.getFolder(OCSOUTPUT_DIR).getLocation().toFile();
		Assert.assertTrue(dir.exists() && dir.isDirectory());

		FilenameFilter ff = new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return name.endsWith(".xml");
			}
		};

		if (dir.listFiles(ff).length == 0) {
			Assert.fail("Could not find any generated pageflow files to validate!");
		}

		for (File file : dir.listFiles(ff)) {
			DocumentBuilder builder;
			builder = getDocumentBuilderFactory().newDocumentBuilder();
			builder.setErrorHandler(new PageflowGeneratorErrorHandler());
			builder.parse(file);
		}

		checkPageflows();
	}

	/**
	 * @throws URISyntaxException
	 * @throws IOException
	 */
	public void checkPageflows() throws URISyntaxException, IOException {
		final File dir = project.getFolder(OCSOUTPUT_DIR).getLocation()
				.toFile();
		Assert.assertTrue(dir.exists() && dir.isDirectory());
		FilenameFilter ff = new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return name.endsWith(".xml");
			}
		};

		if (dir.listFiles(ff).length == 0) {
			Assert.fail("Could not find generated pageflow files to check equivalence!!!!!!!!!");
		}

		for (File file : dir.listFiles(ff)) {
			final URL url = FileLocator.find(PageflowGenerationCore
					.getDefault().getBundle(), new Path(OCSTEMPLATE_DIR + "/"
					+ file.getName()), null);
			final File template = new File(FileLocator.toFileURL(url).toURI());
			assertEquals(
					new InputSource(new FileInputStream(template)),
					new InputSource(new FileInputStream(project
							.getFolder(OCSOUTPUT_DIR).getFile(file.getName())
							.getLocation().toFile())));
		}
	}

	private static class PageflowGeneratorErrorHandler implements ErrorHandler {

		public void error(SAXParseException exception) throws SAXException {
			throw exception;
		}

		public void fatalError(SAXParseException exception) throws SAXException {
			throw exception;
		}

		public void warning(SAXParseException exception) throws SAXException {
			System.out.println("WARNING " + exception.getLocalizedMessage());

		}

	}

	protected abstract void checkDocuments(Document doc1, Document doc2);

	/**
	 * static variables
	 */
	private static final String ROOT_OCSOUTPUT_DIR = "output";

	private static final String OCSOUTPUT_DIR = ROOT_OCSOUTPUT_DIR
			+ "/META-INF/config";

	private static final String OCSTEMPLATE_DIR = "resources/templates/pageflow";

	private static final String OCSPAGEFLOW_DIR = "resources/test-tank-models/pageflow/generation-tests";
}
