package com.odcgroup.aaa.connector.util;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.eclipse.emf.common.EMFPlugin;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.Diagnostician;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.odcgroup.aaa.connector.formats.FormatCriteria;
import com.odcgroup.aaa.connector.internal.metadictreader.MetaDictDAO;
import com.odcgroup.aaa.connector.internal.util.FormatVO;
import com.odcgroup.aaa.connector.internal.util.ImportReportVO;
import com.odcgroup.aaa.connector.internal.util.TAConnectionInfo;
import com.odcgroup.aaa.connector.internal.util.TADatabase;
import com.odcgroup.aaa.connector.internal.util.TADatabaseConnection;
import com.odcgroup.aaa.connector.mdfmml.MetaDictDomains;
import com.odcgroup.aaa.connector.tests.JUnit4TstJPA;
import com.odcgroup.domain.DomainStandaloneSetup;
import com.odcgroup.domain.annotations.AAAAspectDS;
import com.odcgroup.mdf.ecore.MdfDomainImpl;
import com.odcgroup.mdf.ecore.MdfEnumerationImpl;
import com.odcgroup.mdf.ecore.MdfFactory;
import com.odcgroup.mdf.ecore.PrimitivesDomain;
import com.odcgroup.mdf.ext.aaa.AAAAspect;
import com.odcgroup.mdf.ext.sql.SQLAspect;
import com.odcgroup.mdf.ext.tangij.TANGIJTranslationAspect;
import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.mdf.metamodel.MdfDomain;
import com.odcgroup.mdf.metamodel.MdfEntity;
import com.odcgroup.mdf.metamodel.MdfEnumValue;
import com.odcgroup.mdf.metamodel.MdfEnumeration;
import com.odcgroup.mdf.metamodel.MdfModelElement;
import com.odcgroup.mdf.metamodel.MdfProperty;
import com.odcgroup.otf.jpa.utils.BootstrapJPA;


/**
 * Test Case.
 * This also demonstrates how a "client" of this library would use it's functions.
 * 
 * NOTE More internal tests regarding Format importing conversion in Format2DatasetTest.
 * 
 * @author Michael Vorburger (MVO)
 */
public class TADatabaseConnectionTest extends JUnit4TstJPA {

    private static final String DIR = "target";

    private static TADatabase taDB = null;
    private TADatabaseConnection ta;
    private MdfDomain businessTypesDomain;
	private XtextResourceSet resourceSet;

	public TADatabaseConnectionTest() throws Exception {
		setUpBeforeClass();
	}

	/**
     * @throws java.lang.Exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        // This isn't stricly correct / required, you don't need really
        // to use BootstrapJPA.getPropertiesFromClasspathResource (here, only internally later)
        // but I'm too lazy to re-code reading Properties... it doesn't matter, this is just fine:
        Properties nonJPAConnectionProperties = BootstrapJPA.getPropertiesFromClasspathResourceNonJPA("ta-db.properties");
        TAConnectionInfo connectionInfo = new TAConnectionInfo();
        connectionInfo.setHostname(getProperty(nonJPAConnectionProperties, "hostname"));
        connectionInfo.setPort(Integer.parseInt(getProperty(nonJPAConnectionProperties, "port")));
        connectionInfo.setDBName(getProperty(nonJPAConnectionProperties, "db"));
        connectionInfo.setUsername(getProperty(nonJPAConnectionProperties, "uid"));
        connectionInfo.setPassword(getProperty(nonJPAConnectionProperties, "pwd"));
        connectionProperties = nonJPAConnectionProperties;
        persistenceUnitName = "aaa";
        taDB = new TADatabase(connectionInfo);
    }

    /* package-local */ static String getProperty(Properties p, String key) {
        String value = p.getProperty(key);
        if (value == null) {
            throw new IllegalArgumentException("Property '" + key + "' not found");
        }
        return value;
    }

    /**
     * @throws java.lang.Exception
     */
    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    	// necessary ? super.tearDownAfterClass();
    	
        if (taDB != null) {
            taDB.close();
        }
    }

    /**
     * @throws java.lang.Exception
     */
    @Before
	public void setUp() throws Exception {
	super.setUp();
    	// This makes it possible to run this Test as a non-OSGi JUnit
    	if (!EMFPlugin.IS_ECLIPSE_RUNNING) {
    		DomainStandaloneSetup.doSetup();
    	}
    	
        ta = taDB.newTADatabaseConnection();
        resourceSet = new XtextResourceSet();
		businessTypesDomain = BusinessTypesUtil.getBusinessTypesDomain(resourceSet);
    }

    /**
     * @throws java.lang.Exception
     */
    @After
	public void tearDown() throws Exception {
        if (ta != null) {
            ta.close();
        }
        resourceSet.getResources().clear();
        resourceSet = null;
		super.tearDown();
    }

	@Test
    @SuppressWarnings("unchecked")
    public void testYannAndSreekanth() throws IOException {
		MdfDomainImpl enumDomain = (MdfDomainImpl)MdfFactory.eINSTANCE.createMdfDomain();
		enumDomain.setName("AAAEnumerations");
		enumDomain.setNamespace("http://www.odcgroup.com/aaa-enumerations");

		enumDomain.setDocumentation("Some doc");
		
//		JavaAspectDS.setPackage(enumDomain, "com.odcgroup.aaa");


		for (int i=0; i<1000; i++) {
			MdfEnumerationImpl mdfEnum = (MdfEnumerationImpl)MdfFactory.eINSTANCE.createMdfEnumeration();
			mdfEnum.setType(PrimitivesDomain.BOOLEAN);
			mdfEnum.setName("TestName" + i);
//			mdfEnum.setDocumentation("Dummy");
			
//			MdfEnumValueImpl enumValue = (MdfEnumValueImpl)MdfFactory.eINSTANCE.createMdfEnumValue();
//			enumValue.setName("enumName");
//			enumValue.setValue("true");
			
//			// DS-2182
//			AAAAspectDS.setTripleAEntitySQLName(mdfEnum, "someSqlName");
//			// DS-2182
//			AAAAspectDS.setTripleAAttributeSQLName(mdfEnum, "someSqlName");
			
			enumDomain.getEnumerations().add(mdfEnum);
		}

		checkMdfFileWriting(enumDomain);
    }
    
    /**
     * Test method for {@link com.odcgroup.aaa.connector.internal.util.TADatabaseConnection#getMetaDictionaryDomain()}.
     * @throws MdfParsingException 
     */
    @Test
    public void testGetMetaDictionaryDomain() throws Exception {
        MetaDictDomains metaDictDomains = 
        	ta.getMetaDictionaryDomains(false, businessTypesDomain);

        checkMdfFileWriting(metaDictDomains.entitiesDomain, metaDictDomains.businessTypesDomain, metaDictDomains.enumerationsDomain);
        checkAnnotations(metaDictDomains.entitiesDomain);
        
        Diagnostic diagnostic = Diagnostician.INSTANCE.validate(metaDictDomains.enumerationsDomain);
        switch (diagnostic.getSeverity()) {
          case Diagnostic.ERROR:
            System.err.println("Model has errors: " + diagnostic);
            throw new IllegalStateException("Error in model found...");
          case Diagnostic.WARNING:
            System.err.println("Model has warnings: " + diagnostic);
            throw new IllegalStateException("Warning in model found...");
        }

        checkMdfFileWriting(metaDictDomains.enumerationsDomain);        
        checkIfAnyEnums(metaDictDomains.enumerationsDomain);
        checkIfAnyClassesWithEnumAttributes(metaDictDomains.entitiesDomain);
        checkTranslations(metaDictDomains.entitiesDomain);
        checkTranslations(metaDictDomains.enumerationsDomain);
    }

    @SuppressWarnings("unchecked")
    private void checkTranslations(MdfDomain metaDictDomain) {
        List<MdfEntity> entities = metaDictDomain.getEntities();
        for (MdfEntity entity : entities) {
            checkTranslations(entity);
        }
        
    }
    @SuppressWarnings("unchecked")
    private boolean checkTranslations(final MdfEntity type) {
        boolean translationFound = false;
        translationFound = hasTranslations(type);
        if (type instanceof MdfClass) {
            MdfClass klass = (MdfClass) type;
            List<MdfProperty> properties = klass.getProperties(true);
            for (MdfProperty prop : properties) {
                if (hasTranslations(prop)) {
                    translationFound = true;
                }
            }
        }
        if (type instanceof MdfEnumeration) {
            MdfEnumeration enumi = (MdfEnumeration) type;
            List<MdfEnumValue> vals = enumi.getValues();
            for (MdfEnumValue val : vals) {
                if (hasTranslations(val)) {
                    translationFound = true;
                }
            }
        }
        Assert.assertTrue("No translations on entity (or any of it's attributes): " + type.getQualifiedName().toString(), translationFound);
        return translationFound;
    }
    private boolean hasTranslations(MdfModelElement type) {
        boolean translationFound;
        Map<String, String> trans = TANGIJTranslationAspect.getTranslations(type);
        translationFound = !trans.isEmpty();
        return translationFound;
    }

    @SuppressWarnings("unchecked")
	private void checkIfAnyEnums(MdfDomain metaDictDomain) {
		List<MdfEntity> entities = metaDictDomain.getEntities();
		boolean foundOne = false;
		for (MdfEntity mdfEntity : entities) {
			if (mdfEntity instanceof MdfEnumeration) {
				MdfEnumeration mdfEnumeration = (MdfEnumeration) mdfEntity;
				foundOne = true;
				
				Assert.assertTrue("Enumeration has no name?!", !mdfEnumeration.getName().isEmpty());
				Assert.assertTrue("Enumeration has no values?!", !mdfEnumeration.getValues().isEmpty());
			}
		}
		Assert.assertTrue("Not a single enumeration was found?!", foundOne);
	}

    @SuppressWarnings("unchecked")
    private void checkIfAnyClassesWithEnumAttributes(MdfDomain metaDictDomain) {
        List<MdfEntity> entities = metaDictDomain.getEntities();
        boolean foundOne = false;
        for (MdfEntity mdfEntity : entities) {
            if (mdfEntity instanceof MdfClass) {
                MdfClass mdfClass = (MdfClass) mdfEntity;
                List<MdfProperty> properties = mdfClass.getProperties();
                for (MdfProperty mdfProperty : properties) {
                    MdfEntity type = mdfProperty.getType();
                    if (type instanceof MdfEnumeration)
                        foundOne = true;
                }
            }
        }
        Assert.assertTrue("Not a single class with an with an enum attribute was found?!", foundOne);
    }

    @SuppressWarnings("unchecked")
	private void checkAnnotations(MdfDomain metaDictDomain) {
        MdfClass firstClass = null;
	    List<MdfEntity> entities = metaDictDomain.getEntities();
	    for (MdfEntity mdfEntity : entities) {
            if (mdfEntity instanceof MdfClass) {
                firstClass = (MdfClass) mdfEntity;
                break;
            }
        }
	    Assert.assertNotNull("Not one single MDF class found in domain?", firstClass != null);
	    
        Assert.assertTrue(firstClass.getName() + " doesn't have SQL Name annotation?", SQLAspect.getSqlName(firstClass) != null);
        
        MdfProperty firstProperty = (MdfProperty) firstClass.getProperties().get(0);
        Assert.assertTrue(firstProperty.getName() + " doesn't have that AAA type annotation?", AAAAspect.getAAAParamsType(firstProperty) != null);
        Assert.assertTrue(firstProperty.getName() + " doesn't have SQL Name annotation?", SQLAspect.getSqlName(firstProperty) != null);
	}

    /**
     * Test method for {@link com.odcgroup.aaa.connector.internal.util.TADatabaseConnection#getFormatsDomain()}.
     */
    @Test
    public void testGetFormatsDomains() throws Exception {
        MdfDomain domain = ta.getDatasetFormatsDomain();
        Assert.assertNotNull("getDatasetFormatsDomain() == null ?!", domain);
        
        domain = ta.getClassFormatsDomain();
        Assert.assertNotNull("getClassFormatsDomain() == null ?!", domain);
    }

    /**
     * Test method for {@link com.odcgroup.aaa.connector.internal.util.TADatabaseConnection#getAllFormats()}.
     */
    @Test
    public void testGetAllFormatsCodes() throws Exception {
        List<FormatVO> formatCodes = ta.getAllFormats();
        Assert.assertNotNull("getAllFormats() == null ?!", formatCodes);
        Assert.assertFalse("getAllFormats().isEmpty() ?!", formatCodes.isEmpty());
        Assert.assertTrue("Less than 10 Formats found, formatCodes.size() =< 10 ?!", formatCodes.size() > 10);
        
        System.out.println("testGetAllFormats() has returned the following Formats from T'A:");
        for (FormatVO format : formatCodes) {
            System.out.println("\t" + format.getCode() + "\t" + format.getDenom() + "\t" + format.getFunction().getProcNameFunction() + "\t" + format.getFunction().getDashedProcNameFunction() + "\t" + format.getFunction().getCamelCaseProcNameFunction());
        }
    }

    @Test
    public void testGetAllFormats() throws Exception {
    	MetaDictDomains domains = ta.getMetaDictionaryDomains(false, businessTypesDomain);
        MdfDomainImpl metaDictDomain = domains.entitiesDomain;
        MdfDomainImpl enumsDomain = domains.enumerationsDomain;
        MdfDomainImpl btsDomain = domains.businessTypesDomain;
        Map<String, MdfDomainImpl> formatsDomainsMap = new HashMap<String, MdfDomainImpl>();
        
        ImportReportVO classesReportVO = ta.addClassesFromFormats(new FormatCriteria("*"), formatsDomainsMap, metaDictDomain, enumsDomain, businessTypesDomain);
        
        Assert.assertTrue("Should have imported formats", classesReportVO.getImportedFormatsPerDomainFunction().size() > 0);
        
        for (Map.Entry<String, MdfDomainImpl> entry : formatsDomainsMap.entrySet()) {
            checkMdfFileWriting(entry.getValue(), metaDictDomain, btsDomain, enumsDomain);
        }
    }

    private void checkMdfFileWriting(MdfDomainImpl domain, MdfDomainImpl... dependentDomains) throws IOException {
    	for (MdfDomainImpl dependentDomain : dependentDomains) {
    		Resource resource = dependentDomain.eResource();
    		if (resource == null) {
    			URI uri = URI.createURI(dependentDomain.getName() + ".domain");
    			resource = resourceSet.createResource(uri );
    			resource.getContents().add(dependentDomain);
    		}
    		ResourceSet existingRS = resource.getResourceSet();
    		if (existingRS != null && existingRS != resourceSet) {
    			throw new IllegalStateException("Resource already is in a ResourceSet, but not ours: " + resource.getURI());
    		}
			resourceSet.getResources().add(resource);
    	}
    	
    	String fileName = DIR + "/" + domain.getName() + ".domain";
    	URI uri = URI.createFileURI(fileName);
    	Resource resource = resourceSet.createResource(uri);
    	resource.getContents().add(domain);
    	resource.save(null);
    	
        checkFileExistsAndIsNotEmpty(new File(fileName));
        System.out.println("\nPS, FYI: Test has succesefully created a Domain and written it to file: " + fileName);
    }

    /* package-local */
    final static void checkFileExistsAndIsNotEmpty(File f) {
        Assert.assertTrue("File does not exist: " + f.toString(), f.exists());
        Assert.assertTrue("File can not be read: " + f.toString(), f.canRead());
        Assert.assertTrue("File is not a File (but a directory?): " + f.toString(), f.isFile());
        Assert.assertTrue("File length is < 1000 bytes, that's suspicously small: " + f.toString(), f.length() > 1000);
    }
    @Test
    public void testEntieisAndEnumDomainTimeStampAnnotaiton() throws Exception {
	
	//Get the MetaDictDomians 
	MetaDictDomains domains = ta.getMetaDictionaryDomains(false, businessTypesDomain);
	//Entity Domain
        MdfDomain metaDictDomain = domains.entitiesDomain;
        //Enumeration Domain
        MdfDomain enumsDomain = domains.enumerationsDomain;
        //Get the Timestamp by runinng a Query.
        MetaDictDAO metaDictDAO = new MetaDictDAO(em);
        String timeStamp = metaDictDAO.getTimeStamp();
        Assert.assertNotNull(timeStamp);
        Assert.assertTrue(StringUtils.isNotEmpty(timeStamp));
        //Get the timestamp annotation for the entity domain
        String actualValueforEntities = AAAAspectDS.getTripleATimeStamp(metaDictDomain);
        Assert.assertEquals(timeStamp, actualValueforEntities);
      //Get the timestamp annotation for the enumeration domain
        String actualValueforEnumeration = AAAAspectDS.getTripleATimeStamp(enumsDomain);
        Assert.assertEquals(timeStamp, actualValueforEnumeration);
        
    }
}
