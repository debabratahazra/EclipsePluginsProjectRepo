package com.odcgroup.t24.application.importer.tests;

import java.io.File;
import java.io.FilenameFilter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.xml.bind.JAXBException;
import javax.xml.bind.UnmarshalException;

import org.junit.Assert;
import org.junit.Test;
import org.xml.sax.SAXException;

import com.odcgroup.t24.applicationimport.reader.Reader;
import com.odcgroup.t24.applicationimport.schema.ApplicationEntity;
import com.odcgroup.t24.applicationimport.schema.Module;

/**
 * Test for Reader.
 *
 * @author Michael Vorburger
 */
public class ReaderTest {

	@Test
	public void testApplicationsReader() throws Exception {
		URL xml = getClass().getResource("/LD.LOANS.AND.DEPOSITS.xml");
		Assert.assertNotNull(xml);
		List<Module> modules = new Reader().unmarshalApplications(xml);
		
		ApplicationEntity app = modules.get(0).getComponents().get(0).getApplications().get(0);
		Assert.assertEquals("LD_LOANS_AND_DEPOSITS", app.getName());
		Assert.assertEquals("LD.LOANS.AND.DEPOSITS", app.getNameT24());
	}
	
	public static void main(String[] args) throws Exception {
		mainApplications();
	}

	private static void mainApplications() throws JAXBException, SAXException, MalformedURLException {
		Reader reader = new Reader();
		File dir = new File("W:\\T24.Applications\\F.DS.INTROSPECTION");
		File[] files = dir.listFiles(new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return name.endsWith(".xml");
			}
		});
		
		long start = System.currentTimeMillis();
		for (File file : files) {
			try {
				reader.unmarshalApplications(file.toURI().toURL());
			} catch (UnmarshalException e) {
				throw new IllegalArgumentException("Could not unmarshal this XML: " + file, e);
			} catch (SAXException e) {
				throw new IllegalArgumentException("Could not unmarshal this XML: " + file, e);
			} catch (JAXBException e) {
				throw new IllegalArgumentException("Could not unmarshal this XML: " + file, e);
			}
		}
		long duration = System.currentTimeMillis() - start;
		System.out.println("Reading " + files.length + " took " + duration + "ms");
	}
}
