package com.odcgroup.visualrules.integration.builder;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openarchitectureware.workflow.ConfigurationException;
import org.openarchitectureware.workflow.util.ResourceLoaderFactory;

import com.odcgroup.mdf.ecore.util.MMLResourceFactory;
import com.odcgroup.mdf.metamodel.MdfDomain;
import com.odcgroup.visualrules.integration.datasync.DataTypeBuilder;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import de.visualrules.integration.model.RulePackage;


public class DataTypeBuilderTest extends DataTypeBuilder {

	private static final String DOMAINFILES_DIR = "com/odcgroup/visualrules/integration/builder";

	private ResourceSet resourceSet;
	private File dir;
	
	@Before
	public void setUp() throws Exception {
        final URL url = ResourceLoaderFactory.createResourceLoader().getResource(DOMAINFILES_DIR);
        dir = new File(URI.decode(url.getFile()));

        assertTrue(dir.exists() && dir.isDirectory());

    	resourceSet = new ResourceSetImpl();

    	// Register XMI resource factory
    	resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("domain", new MMLResourceFactory());
	}

	@After
	public void tearDown() throws Exception {
		resourceSet = null;
		dir = null;
	}
	
	@Test
	public void testClassMapping() throws FileNotFoundException {
    	File file = new File(dir, "class.domain");
		URI resourceURI = URI.createFileURI(file.getAbsolutePath());
        final Resource domain = resourceSet.getResource(resourceURI, true);	       
        try {
            domain.load(null);
        } catch (final IOException e) {
            throw new ConfigurationException(e);
        }

		assertNotNull(domain);
		assertTrue(domain.getContents().size()==1);
		MdfDomain domainModel = (MdfDomain) domain.getContents().get(0);
		
    	RulePackage rulePackage = transformToVRDataTypes(domainModel);
		assertNotNull(rulePackage);
    	assertEquals("ClassTest", rulePackage.getName());
    	
		XStream xs = new XStream(new DomDriver());
    	File infile = new File(dir, "class.xml");
		RulePackage origRulePackage = (RulePackage) xs.fromXML(new FileInputStream(infile));

		assertTrue(EcoreUtil.equals(rulePackage, origRulePackage));
	}

	@Test
	public void testDatasetMapping() {
    	File file = new File(dir, "dataset.domain");
		URI resourceURI = URI.createFileURI(file.getAbsolutePath());
        final Resource domain = resourceSet.getResource(resourceURI, true);	       
        try {
            domain.load(null);
        } catch (final IOException e) {
            throw new ConfigurationException(e);
        }

		assertNotNull(domain);
		assertTrue(domain.getContents().size()==1);
		MdfDomain domainModel = (MdfDomain) domain.getContents().get(0);
		
    	RulePackage rulePackage = transformToVRDataTypes(domainModel);
		assertNotNull(rulePackage);

		XStream xs = new XStream();
    	File outfile = new File(dir, "dataset.xml");
		try {
			xs.toXML(rulePackage, new FileOutputStream(outfile));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    	assertEquals("DatasetTest", rulePackage.getName());
    	
    	EList types = rulePackage.getTypes();
    	assertEquals(2, types.size());
	}
	
	@Test
	public void testEnumerationMapping() {
    	File file = new File(dir, "enumeration.domain");
		URI resourceURI = URI.createFileURI(file.getAbsolutePath());
        final Resource domain = resourceSet.getResource(resourceURI, true);	       
        try {
            domain.load(null);
        } catch (final IOException e) {
            throw new ConfigurationException(e);
        }

		assertNotNull(domain);
		assertTrue(domain.getContents().size()==1);
		MdfDomain domainModel = (MdfDomain) domain.getContents().get(0);
		
    	RulePackage rulePackage = transformToVRDataTypes(domainModel);
		assertNotNull(rulePackage);

		XStream xs = new XStream();
    	File outfile = new File(dir, "enumeration.xml");
		try {
			xs.toXML(rulePackage, new FileOutputStream(outfile));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    	assertEquals("EnumerationTest", rulePackage.getName());
    	
    	EList types = rulePackage.getTypes();
    	assertEquals(2, types.size());
	}

	@Test
	public void testBusinessTypeMapping() {
    	File file = new File(dir, "businesstype.domain");
		URI resourceURI = URI.createFileURI(file.getAbsolutePath());
        final Resource domain = resourceSet.getResource(resourceURI, true);	       
        try {
            domain.load(null);
        } catch (final IOException e) {
            throw new ConfigurationException(e);
        }

		assertNotNull(domain);
		assertTrue(domain.getContents().size()==1);
		MdfDomain domainModel = (MdfDomain) domain.getContents().get(0);
		
    	RulePackage rulePackage = transformToVRDataTypes(domainModel);
		assertNotNull(rulePackage);

		XStream xs = new XStream();
    	File outfile = new File(dir, "businesstype.xml");
		try {
			xs.toXML(rulePackage, new FileOutputStream(outfile));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    	assertEquals("BusinessTypeTest", rulePackage.getName());
    	
    	EList types = rulePackage.getTypes();
    	assertEquals(1, types.size());
	}
	
	@Test
	public void testPropertyMapping() {
    	File file = new File(dir, "property.domain");
		URI resourceURI = URI.createFileURI(file.getAbsolutePath());
        final Resource domain = resourceSet.getResource(resourceURI, true);	       
        try {
            domain.load(null);
        } catch (final IOException e) {
            throw new ConfigurationException(e);
        }

		assertNotNull(domain);
		assertTrue(domain.getContents().size()==1);
		MdfDomain domainModel = (MdfDomain) domain.getContents().get(0);
		
    	RulePackage rulePackage = transformToVRDataTypes(domainModel);
		assertNotNull(rulePackage);
    	
		XStream xs = new XStream();
    	File outfile = new File(dir, "property.xml");
		try {
			xs.toXML(rulePackage, new FileOutputStream(outfile));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    	assertEquals("PropertyTest", rulePackage.getName());
    	
    	EList types = rulePackage.getTypes();
    	assertEquals(3, types.size());
	}
}
