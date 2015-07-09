package com.odcgroup.t24.application.importer.tests;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.eclipse.xtext.resource.impl.ResourceDescriptionsProvider;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Provider;
import com.odcgroup.domain.ui.internal.DomainActivator;
import com.odcgroup.mdf.ecore.MdfClassImpl;
import com.odcgroup.mdf.ecore.MdfDomainImpl;
import com.odcgroup.mdf.ecore.MdfModelElementImpl;
import com.odcgroup.mdf.metamodel.MdfAnnotation;
import com.odcgroup.mdf.metamodel.MdfAnnotationProperty;
import com.odcgroup.mdf.metamodel.MdfAssociation;
import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.mdf.metamodel.MdfContainment;
import com.odcgroup.mdf.metamodel.MdfDomain;
import com.odcgroup.mdf.metamodel.MdfMultiplicity;
import com.odcgroup.mdf.metamodel.MdfProperty;
import com.odcgroup.t24.applicationimport.ApplicationsImport;
import com.odcgroup.t24.applicationimport.T24Aspect;
import com.odcgroup.t24.applicationimport.serializer.MdfDomainSerializer;
import com.odcgroup.workbench.core.tests.util.AbstractDSUnitTest;

/**
 * Tests Application Import of a few select XML local files in the resources/
 * folder (not in resources/applications/), and (for some tests) asserts what
 * was read to check if the mapping in the created domains is correct (contrary
 * to the LocalApplicationImporterTest).
 */
public class ApplicationIntrospectionTest extends AbstractDSUnitTest {
	
	private IProject project = null;
	
	@Inject
	private Provider<XtextResourceSet> xtextResourceSetProvider;
	
	@Inject
	private Provider<ApplicationsImport> applicationsImportProvider;
	
	@Before
	public void setUp() throws Exception {
		project = createModelsProject().getProject();
		
		Injector injector = DomainActivator.getInstance().getInjector(DomainActivator.COM_ODCGROUP_DOMAIN_DOMAIN);
		injector.injectMembers(this);
	}

	private ResourceSet createResourceSet() {
		XtextResourceSet resourceSet = xtextResourceSetProvider.get();
		
		IJavaProject javaProject = JavaCore.create(project);
		if (javaProject == null || !javaProject.exists()) {
			throw new IllegalStateException("Models project must be a Java project: " + project.getName());
		}
		resourceSet.setClasspathURIContext(javaProject);
		resourceSet.getLoadOptions().put(ResourceDescriptionsProvider.LIVE_SCOPE, Boolean.TRUE);
		
		return resourceSet;
	}
	 
	@After
	public void tearDown() throws Exception {
        deleteModelsProjects();
    }
	
	public ApplicationIntrospectionTest() {
		super();
	}

	@Test
	public void testBigFile() throws Exception {
		checkApplicationXMLFile("resources/LD.LOANS.AND.DEPOSITS.xml");
	}

	@Test
	public void testReferenceInSameFile() throws Exception {
		checkApplicationXMLFile("resources/testA.xml");
	}

	@Test
	public void testReferenceInDifferentFile() throws Exception {
		checkApplicationXMLFile("resources/testB.xml");
	}

	@Test
	public void testReferenceToNonExistingDomain() throws Exception {
		checkApplicationXMLFile("resources/testC.xml");
	}
   
	
	@Test
	public void testIfReferenceisNotDefinedInTheSameFile() throws Exception {
		checkApplicationXMLFile("resources/RefNotDefinedInSameFile.xml");
	}
	
	@Test
	public void testSameDomainINDifferentFile() throws Exception {
		Set<MdfDomain> domains = checkApplicationXMLFile("resources/SamedomaininDifferentFiles");
		// if this fails then we created more than one (two) domains - the test is pointless then
		assertEquals(1, domains.size());
	}
	
	@Test
	public void testMultipleValue() throws Exception {
		Set<MdfDomain> domains = checkApplicationXMLFile("resources/SEC_TRADE-ForMultiValuesTest.xml");
		Assert.assertEquals(1, domains.size());
		MdfDomain firstDomain = domains.iterator().next();
		MdfClass mainClass = (MdfClass) firstDomain.getEntity("SEC_TRADE");
		Assert.assertNotNull(mainClass);
		Assert.assertEquals(67, mainClass.getProperties().size());
		
		MdfAssociation associationToMultiValueOnMainClass = (MdfAssociation) mainClass.getProperty("CUSTOMER_NO");
		Assert.assertTrue(associationToMultiValueOnMainClass.getMultiplicity() == MdfMultiplicity.MANY);
		Assert.assertTrue(associationToMultiValueOnMainClass.getContainment() == MdfContainment.BY_VALUE);
		MdfClass multiValueClass = (MdfClass) associationToMultiValueOnMainClass.getType();
		Assert.assertEquals("SEC_TRADE__CUSTOMER_NO", multiValueClass.getName());
		Assert.assertEquals(67, multiValueClass.getProperties().size());

		MdfAssociation associationToSubValueClassOnMultiValueClass = (MdfAssociation) multiValueClass.getProperty("CUST_NO_NOM");
		MdfClass subValueClass = (MdfClass) associationToSubValueClassOnMultiValueClass.getType();
		Assert.assertEquals("SEC_TRADE__CUSTOMER_NO__CUST_NO_NOM", subValueClass.getName());
		Assert.assertEquals(2, subValueClass.getProperties().size());

		MdfProperty custPriceMultiValueSubProperty = subValueClass.getProperty("CUST_PRICE");
		Assert.assertNotNull(custPriceMultiValueSubProperty);
		
		MdfProperty shouldNotExist1 = multiValueClass.getProperty("CUST_PRICE");
		Assert.assertNull(shouldNotExist1);

		MdfProperty shouldNotExist2 = mainClass.getProperty("CUST_PRICE");
		Assert.assertNull(shouldNotExist2);
	}
	
	@Test
	public void testEmptyEnum() throws Exception {
		// This used to fail, because some T24 App has empty valid values - now skipped
		checkApplicationXMLFile("resources/empty-valid-value.xml");
	}
	
	protected Set<MdfDomain> checkApplicationXMLFile(String testResourceName) throws Exception {
		ResourceSet resourceSet = createResourceSet();
		return checkApplicationXMLFile(testResourceName, resourceSet);
	}
	
	protected Set<MdfDomain> checkApplicationXMLFile(String testResourceName, ResourceSet resourceSet) throws Exception {
		URL url = Platform.getBundle("com.odcgroup.t24.application.importer.tests").getEntry(testResourceName);
		File file = new File(FileLocator.toFileURL(url).toURI());
		IProgressMonitor mockMonitor = new NullProgressMonitor();
		Set<MdfDomain> readDomains = new HashSet<MdfDomain>();
		
		IFolder folder = project.getFolder("domain");
		if (file.isDirectory()) {
			applicationsImportProvider.get().importApplications(file, folder, resourceSet, mockMonitor, readDomains);
		} else {
			List<File> xmlFilesToImport = new ArrayList<File>();
			xmlFilesToImport.add(file);
			applicationsImportProvider.get().importApplications(xmlFilesToImport, folder, resourceSet, mockMonitor, readDomains);
		}
		MdfDomainSerializer.serialize(readDomains, project, resourceSet, mockMonitor, folder);
		return readDomains;
	}
	
	@Test
	public void testTranslationTest() throws Exception {
	    Set<MdfDomain> domains= checkApplicationXMLFile("resources/LETTER_OF_CREDIT_Translation.xml");
	    Assert.assertEquals(1, domains.size());
	    MdfDomain domain = domains.iterator().next();
	    MdfClass letterOfCreditClass = (MdfClass) domain.getEntity("LETTER_OF_CREDIT");
	    Assert.assertNotNull(letterOfCreditClass);
	
	    //header1 translation kind value for the MdfCLass
	    MdfAnnotation header1Annotation=letterOfCreditClass.getAnnotation("http://www.odcgroup.com/mdf/translation", "Header1");
	    Assert.assertNotNull(header1Annotation);
	    MdfAnnotationProperty header1Property= header1Annotation.getProperty("en");
	    Assert.assertNotNull(header1Property);
	    String header1Translation=header1Property.getValue();
	    Assert.assertEquals("Test Header1 tag Xml", header1Translation);
	    
	    //header2 translation kind value for the mdfClass
	    MdfAnnotation header2Annotation=letterOfCreditClass.getAnnotation("http://www.odcgroup.com/mdf/translation", "Header2");
	    Assert.assertNotNull(header2Annotation);
	    MdfAnnotationProperty header2Property= header2Annotation.getProperty("en");
	    Assert.assertNotNull(header2Property);
	    String header2Translation=header2Property.getValue();
	    Assert.assertEquals("Test Header2 tag Xml", header2Translation);
	    
	    //get the association from the class LETTER_OF_CREDIT
	    MdfProperty association=letterOfCreditClass.getProperty("ACCOUNT_OFFICER");
	    Assert.assertNotNull(association);
	    MdfAnnotation labelAnnotation= association.getAnnotation("http://www.odcgroup.com/mdf/translation", "Label");
	    MdfAnnotationProperty labelProperty=labelAnnotation.getProperty("en");
	    Assert.assertNotNull(labelProperty);
	    String labelValue=labelProperty.getValue();
	    Assert.assertEquals("Account Officer", labelValue);
	    
	    //get the tooltip 
	    MdfAnnotation tooltipAnnotation= association.getAnnotation("http://www.odcgroup.com/mdf/translation", "Tooltip");
	    MdfAnnotationProperty tooltipProperty=tooltipAnnotation.getProperty("en");
	    Assert.assertNotNull(tooltipProperty);
	    String tooltipValue=tooltipProperty.getValue();
	    Assert.assertEquals("Indicates Account Officer responsible", tooltipValue);
	}
	
	@Test
	public void testlocalRefAllowedTest() throws Exception {
	    Set<MdfDomain> domains= checkApplicationXMLFile("resources/AAA_LOCALREFALLOWED.xml");
	    Assert.assertEquals(1, domains.size());
	    MdfDomain domain = domains.iterator().next();
	    MdfClassImpl detailClass = (MdfClassImpl) domain.getEntity("AA_ACCOUNT_DETAILS_HIST");
	    Assert.assertNotNull(detailClass);
	    boolean localRefAllowed =T24Aspect.isLocalRefAllowed(detailClass);
	    Assert.assertNotNull(localRefAllowed);
	    Assert.assertTrue(localRefAllowed);
	}

	@Test
	public void testDS5541NPEWhileReImport() throws Exception{
	    //read the domain and write it to file system.
	    Set<MdfDomain> domains= checkApplicationXMLFile("resources/AAA_LOCALREFALLOWED.xml");
	    //after read and write. 
	    Assert.assertEquals(1, domains.size());
	    MdfDomainImpl firstImportDomain= (MdfDomainImpl)domains.iterator().next();
	    int sizeofTheDomain =firstImportDomain.eContents().size();
	    Assert.assertNotSame(new Integer(0), new Integer(sizeofTheDomain));
	    //re-import the domain
	    Set<MdfDomain> reimportDomains= checkApplicationXMLFile("resources/AAA_LOCALREFALLOWED.xml");
	    Assert.assertEquals(1, reimportDomains.size());
	    MdfDomainImpl reimportDomain =(MdfDomainImpl) reimportDomains.iterator().next();
	    int sizeofThereimportDomain =reimportDomain.eContents().size();
	    Assert.assertNotSame(new Integer(0), new Integer(sizeofThereimportDomain));
	}
	
	@Test
	public void testOverridingOfExistingClasses() throws Exception {
		//DS-5534 :Importing existing classes must remove before adding to Domain
		//test the total number of classes and enums when these files are imported 
		//they have the same domain
		ResourceSet resourceSet = createResourceSet();
		
		Set<MdfDomain> domains1 = checkApplicationXMLFile("resources/AA.ARR.ACCOUNTING.xml", resourceSet);
		waitForAutoBuild();
		/* Set<MdfDomain> domains2 = */ checkApplicationXMLFile("resources/AA.SIM.ACCOUNTING.xml", resourceSet);
		
		Assert.assertEquals(1, domains1.size());
		
		MdfDomain domain = domains1.iterator().next();
		Assert.assertEquals(52, domain.getClasses().size() + domain.getEnumerations().size());
	}
	
	@Test
	public void testGenOperationAnnotation() throws Exception {
	    //get the MdfDomains form the xml file
	    Set<MdfDomain> domains = checkApplicationXMLFile("resources/AA_AA.ACTIVITY.xml");
	    Assert.assertEquals(1, domains.size());
            //MdfDomain
	    MdfDomain domain = domains.iterator().next();
	    MdfClass activityCalss = domain.getClass("AA_ACTIVITY");
	    Assert.assertNotNull(activityCalss);
	    MdfProperty property = activityCalss.getProperty("PROPERTY_CLASS");
	    String genOperationActualValue = T24Aspect.getGenOperation((MdfModelElementImpl)property);
	    String expectedValue = "PROPERTY>AA.PROPERTY>PROPERTY.CLASS";
	    Assert.assertEquals(expectedValue, genOperationActualValue);
	}
	
	@Test
	public void testDS6388NPEWhileImport() throws Exception {
		 Set<MdfDomain> domains=new HashSet<MdfDomain>();
		 for (int loop = 0; loop < 7; loop++) {
			 domains = checkApplicationXMLFile("resources/FT-FT_Contract-FUNDS.TRANSFER.xml");
		 }
		 Assert.assertTrue(domains.size()==1);
		 Assert.assertNotNull(domains.iterator().next());
	}
	
	@Test
	public void testDS6473EmptyMultiValueFieldsWhileReImport() throws Exception {
		// first domain import
		Set<MdfDomain> firstImportDomains = checkApplicationXMLFile("resources/ST-ST_Customer-CUSTOMER.xml");
		Assert.assertEquals(1, firstImportDomains.size());
		MdfDomain firstImportDomain = firstImportDomains.iterator().next();
		MdfClass firstImportClass = (MdfClass) firstImportDomain.getEntity("CUSTOMER");
		Assert.assertNotNull(firstImportClass);
		
		MdfAssociation firstImportAssocToMultiValOnClass = (MdfAssociation) firstImportClass.getProperty("CUSTOMER_RATING");
		Assert.assertTrue(firstImportAssocToMultiValOnClass.getMultiplicity() == MdfMultiplicity.MANY);
		Assert.assertTrue(firstImportAssocToMultiValOnClass.getContainment() == MdfContainment.BY_VALUE);
		MdfClass multiValueFirstImpClass = (MdfClass) firstImportAssocToMultiValOnClass.getType();
		Assert.assertEquals("CUSTOMER__CUSTOMER_RATING", multiValueFirstImpClass.getName());
		
		MdfAssociation assocToSubValClassOnMultiValClassForFirstImport = (MdfAssociation) multiValueFirstImpClass.getProperty("CUSTOMER_RATING");
		Assert.assertNotNull(assocToSubValClassOnMultiValClassForFirstImport);
		Assert.assertTrue(assocToSubValClassOnMultiValClassForFirstImport.getMultiplicity() == MdfMultiplicity.ONE);
		Assert.assertTrue(assocToSubValClassOnMultiValClassForFirstImport.getContainment() == MdfContainment.BY_REFERENCE);
		MdfClass firstImportSubValClass = (MdfClass) assocToSubValClassOnMultiValClassForFirstImport.getType();
		Assert.assertEquals("EB_RATING", firstImportSubValClass.getName());
		
		// re-import domain
		Set<MdfDomain> reImportDomains = checkApplicationXMLFile("resources/ST-ST_Customer-CUSTOMER.xml");
		Assert.assertEquals(1, reImportDomains.size());
		MdfDomain reImportDomain = reImportDomains.iterator().next();
		MdfClass reimportClass = (MdfClass) reImportDomain.getEntity("CUSTOMER");
		Assert.assertNotNull(reimportClass);
		
		MdfAssociation reimportAssocToMultiValOnClass = (MdfAssociation) reimportClass.getProperty("CUSTOMER_RATING");
		Assert.assertTrue(reimportAssocToMultiValOnClass.getMultiplicity() == MdfMultiplicity.MANY);
		Assert.assertTrue(reimportAssocToMultiValOnClass.getContainment() == MdfContainment.BY_VALUE);
		MdfClass multiValueReimportClass = (MdfClass) reimportAssocToMultiValOnClass.getType();
		Assert.assertEquals("CUSTOMER__CUSTOMER_RATING", multiValueReimportClass.getName());
		
		MdfAssociation assocToSubValClassOnMultiValClassForReimport = (MdfAssociation) multiValueReimportClass.getProperty("CUSTOMER_RATING");
		Assert.assertNotNull(assocToSubValClassOnMultiValClassForReimport);
		Assert.assertTrue(assocToSubValClassOnMultiValClassForReimport.getMultiplicity() == MdfMultiplicity.ONE);
		Assert.assertTrue(assocToSubValClassOnMultiValClassForReimport.getContainment() == MdfContainment.BY_REFERENCE);
		MdfClass reimportSubValClass = (MdfClass) assocToSubValClassOnMultiValClassForReimport.getType();
		Assert.assertEquals("EB_RATING", reimportSubValClass.getName());
	}
	
	@Test
	public void testAllowedFunctionsAndAdditionalInfo() throws Exception {
		Set<MdfDomain> domains= checkApplicationXMLFile("resources/ST-ST_Customer-CUSTOMER.xml");
	    Assert.assertEquals(1, domains.size());
	    MdfDomain domain = domains.iterator().next();
	    MdfClassImpl detailClass = (MdfClassImpl) domain.getEntity("CUSTOMER");
	    Assert.assertNotNull(detailClass);
	    
	    // DS-6835
	    String allowedFunctions =T24Aspect.getAllowedFunctions(detailClass);
	    Assert.assertNotNull(allowedFunctions);
	    Assert.assertEquals("A B C D E H I L P Q R S V", allowedFunctions);
	    
	    //DS-6836
	    String additionalInfo =T24Aspect.getAdditionalInfo(detailClass);
	    Assert.assertNotNull(additionalInfo);
	    Assert.assertEquals("BDA", additionalInfo);	    
	}

	@Test
	public void testDS7424ClassCastException() throws Exception {
		// Note how once we don't use the resource/ prefix here (because
		// copyFromClasspathToModelsProject loads from the classpath), but
		// then we do (because checkApplicationXMLFile loads from the plugin)
		
		copyFromClasspathToModelsProject("domain/AA_ClassicProducts.domain");
		waitForAutoBuild(); // we must do this to be sure that AA_ClassicProducts.domain is in the Xtext index 
		/* Set<MdfDomain> domains = */ checkApplicationXMLFile("resources/domain/AA-AA_ClassicProducts-AA.ARR.AC.GROUP.INTEREST.xml");
	}
}
