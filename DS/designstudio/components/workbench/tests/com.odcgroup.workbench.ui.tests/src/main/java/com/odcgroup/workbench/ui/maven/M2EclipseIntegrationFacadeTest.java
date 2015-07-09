package com.odcgroup.workbench.ui.maven;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;

import org.apache.commons.io.IOUtils;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.Namespace;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


/**
 * Test m2eclipse integration
 * @author yan
 */
public class M2EclipseIntegrationFacadeTest {

	private String errorMessage = null;
	private String settingsXml = null;
	
	@Before
	public void setUp() {
		M2EclipseIntegrationFacade.INSTANCE = new M2EclipseIntegrationFacade() {
			@Override
			protected void displayError(String message, Exception e) {
				errorMessage = message;
			}
			public File getSettingsXmlFile() {
				return new File(settingsXml);
			}
		};
		errorMessage = null;
	}
	
	@Test
	public void testUpdateSettingsXmlWithInvalidFile() {
		settingsXml = ""; // invalid file name
		M2EclipseIntegrationFacade.instance().updateSettingsXml("C:/dummy", true);
		Assert.assertNotNull("The file shouldn't be found and an error should be raised", 
				errorMessage);
	}
	
	@Test
	public void testUpdateSettingsXml() throws JDOMException, IOException {
		final String SETTINGS_XML_CONTENTS = 
			"<settings xmlns=\"http://maven.apache.org/POM/4.0.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/settings-1.0.0.xsd\">\n" +
			"<localRepository>dummy</localRepository>\n"+
			"</settings>\n";

		File settingsXmlFile = File.createTempFile("settings", "xml");
		settingsXmlFile.deleteOnExit();
		settingsXml = settingsXmlFile.getAbsolutePath();

		FileWriter fileWriter = null;
		try {
			Document document = new SAXBuilder().build(new StringReader(SETTINGS_XML_CONTENTS));
			fileWriter = new FileWriter(settingsXml);
			new XMLOutputter().output(document, fileWriter);
			fileWriter.flush();
		} finally {
			if (fileWriter != null) {
				IOUtils.closeQuietly(fileWriter);
			}
		}
		
		boolean modified = M2EclipseIntegrationFacade.instance().updateSettingsXml("C:/dummy", true);
		Assert.assertNull("No error should be raised", 
				errorMessage);
		Assert.assertTrue("The updateSettingsXml should say it modified the settings", modified);

		Document result = new SAXBuilder().build(new File(settingsXml));
		Assert.assertNotNull("localRepository tag should exist", result.getRootElement().getChild("localRepository", result.getRootElement().getNamespace()));
	}
	
	@Test
	public void testUpdateSettingsXmlDoesNothing() throws JDOMException, IOException {
		final String SETTINGS_XML_CONTENTS = 
			"<settings xmlns=\"http://maven.apache.org/POM/4.0.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/settings-1.0.0.xsd\">\n" +
			"<localRepository>C:/dummy</localRepository>\n"+
			"</settings>\n";

		File settingsXmlFile = File.createTempFile("settings", "xml");
		settingsXmlFile.deleteOnExit();
		settingsXml = settingsXmlFile.getAbsolutePath();

		FileWriter fileWriter = null;
		try {
			Document document = new SAXBuilder().build(new StringReader(SETTINGS_XML_CONTENTS));
			fileWriter = new FileWriter(settingsXml);
			new XMLOutputter().output(document, fileWriter);
			fileWriter.flush();
		} finally {
			if (fileWriter != null) {
				IOUtils.closeQuietly(fileWriter);
			}
		}
		
		boolean modified = M2EclipseIntegrationFacade.instance().updateSettingsXml("C:/dummy", true);
		Assert.assertNull("No error should be raised", errorMessage);
		Assert.assertTrue("The updateSettingsXml should say it didn't modify the settings", modified);

		Document result = new SAXBuilder().build(new File(settingsXml));
		Assert.assertNotNull("localRepository tag should exist", result.getRootElement().getChild("localRepository", result.getRootElement().getNamespace()));
	}
	
	@Test
	public void testUpdateLocalRepositoryUpdateExisting() throws JDOMException, IOException {
		final String SETTINGS_XML_CONTENTS = 
			"<settings xmlns=\"http://maven.apache.org/POM/4.0.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/settings-1.0.0.xsd\">\n" +
			"<localRepository>dummy</localRepository>\n"+
			"</settings>\n";
		
		SAXBuilder builder = new SAXBuilder();
		Document document = builder.build(new StringReader(SETTINGS_XML_CONTENTS));
		
		boolean modified = M2EclipseIntegrationFacade.instance().updateLocalRepository(document, "C:\test-repo");
		Assert.assertTrue("The updateLocalRepository should say it modified the settings", modified);

		Element localRepo = document.getRootElement().getChild("localRepository", document.getRootElement().getNamespace());
		Assert.assertNotNull("localRepository tag should exist", localRepo);
		Assert.assertEquals("local repo location not properly set", 
				new File("C:\test-repo"), 
				new File(localRepo.getText()));
	}
	
	@Test
	public void testUpdateLocalRepositoryUpdateDoesNothing() throws JDOMException, IOException {
		final String SETTINGS_XML_CONTENTS = 
			"<settings xmlns=\"http://maven.apache.org/POM/4.0.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/settings-1.0.0.xsd\">\n" +
			"<localRepository>C:\test-repo</localRepository>\n"+
			"</settings>\n";
		
		SAXBuilder builder = new SAXBuilder();
		Document document = builder.build(new StringReader(SETTINGS_XML_CONTENTS));
		
		boolean modified = M2EclipseIntegrationFacade.instance().updateLocalRepository(document, "C:\test-repo");
		Assert.assertFalse("The updateLocalRepository should say it didn't modified the settings", modified);

		Element localRepo = document.getRootElement().getChild("localRepository", document.getRootElement().getNamespace());
		Assert.assertNotNull("localRepository tag should exist", localRepo);
		Assert.assertEquals("local repo location not properly set", 
				new File("C:\test-repo"), 
				new File(localRepo.getText()));
	}
	
	@Test
	public void testUpdateLocalRepositoryNew() throws JDOMException, IOException {
		String settingXml = 
			"<settings xmlns=\"http://maven.apache.org/POM/4.0.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/settings-1.0.0.xsd\">\n" +
			"</settings>\n";
		
		SAXBuilder builder = new SAXBuilder();
		Document document = builder.build(new StringReader(settingXml));
		
		boolean modified = M2EclipseIntegrationFacade.instance().updateLocalRepository(document, "C:\test-repo");
		Assert.assertTrue("The updateLocalRepository should say it has modified the settings", modified);

		Element localRepo = document.getRootElement().getChild("localRepository", document.getRootElement().getNamespace());
		Assert.assertNotNull("localRepository tag should exist", localRepo);
		Assert.assertEquals("local repo location not properly set", 
				new File("C:\test-repo"), 
				new File(localRepo.getText()));
	}
	
	@Test 
	public void testUpdateMirrorUrl() throws JDOMException, IOException {
		String settingXml = 
				"<settings xmlns=\"http://maven.apache.org/POM/4.0.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/settings-1.0.0.xsd\">\n" +
				"<mirrors/>"+"</settings>\n";
			
			SAXBuilder builder = new SAXBuilder();
			Document document = builder.build(new StringReader(settingXml));
			Namespace ns = document.getRootElement().getNamespace();
			Element mirrors = document.getRootElement().getChild("mirrors", ns);
			Assert.assertNotNull("mirrors Element not null",mirrors);
			Element beforeUpdateUrlmirrorElement = mirrors.getChild("mirror");
			Assert.assertNull("mirror element null before updating the url" , beforeUpdateUrlmirrorElement);
			boolean modified = M2EclipseIntegrationFacade.instance().updateMirrorUrl(document, "C:\\DSREPOURL");
			Assert.assertTrue("Update the URL", modified);
			Element mirrorsAfterUrlUpdate = document.getRootElement().getChild("mirrors", ns);
			Element mirrorElementAfterUrlUpdate = mirrorsAfterUrlUpdate.getChild("mirror",  ns);
			Assert.assertNotNull("mirror element not null" , mirrorElementAfterUrlUpdate);
			Element idElement = mirrorElementAfterUrlUpdate.getChild("id",ns);
			Assert.assertNotNull("id element of the mirror child element not null" , idElement);
			String idElementDummyText = idElement.getText();
			Assert.assertEquals("Dummy",idElementDummyText);
			Element urlElement = mirrorElementAfterUrlUpdate.getChild("url", ns);
			Assert.assertNotNull("url element of the mirror child element not null" , urlElement);
			String urlElementValue = urlElement.getText();
			Assert.assertEquals("C:\\DSREPOURL",urlElementValue);
			
		
	}
	
	@Test 
	public void testUpdateMirrorUrlWithDefaultUrl() throws JDOMException, IOException {
		String settingXml = 
				"<settings xmlns=\"http://maven.apache.org/POM/4.0.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/settings-1.0.0.xsd\">\n" +
				"<mirrors/>"+"</settings>\n";
			
			SAXBuilder builder = new SAXBuilder();
			Document document = builder.build(new StringReader(settingXml));
			Namespace ns = document.getRootElement().getNamespace();
			Element mirrors = document.getRootElement().getChild("mirrors", ns);
			Assert.assertNotNull("mirrors Element not null",mirrors);
			Element beforeUpdateUrlmirrorElement = mirrors.getChild("mirror");
			Assert.assertNull("mirror element null before updating the url" , beforeUpdateUrlmirrorElement);
			boolean modified = M2EclipseIntegrationFacade.instance().updateMirrorUrl(document, null);
			Assert.assertTrue("Update the URL", modified);
			Element mirrorsAfterUrlUpdate = document.getRootElement().getChild("mirrors", ns);
			Element mirrorElementAfterUrlUpdate = mirrorsAfterUrlUpdate.getChild("mirror",  ns);
			Assert.assertNotNull("mirror element not null" , mirrorElementAfterUrlUpdate);
			Element idElement = mirrorElementAfterUrlUpdate.getChild("id",ns);
			Assert.assertNotNull("id element of the mirror child element not null" , idElement);
			String idElementDummyText = idElement.getText();
			Assert.assertEquals("Dummy",idElementDummyText);
			Element urlElement = mirrorElementAfterUrlUpdate.getChild("url", ns);
			Assert.assertNotNull("url element of the mirror child element not null" , urlElement);
			String urlElementValue = urlElement.getText();
			Assert.assertEquals(M2EclipseIntegrationFacade.DEFAULT_DS_MAVEN_MIRROR_PROPERTY_VALUE ,urlElementValue);
	}
	
}
