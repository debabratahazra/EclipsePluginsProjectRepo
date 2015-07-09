package com.odcgroup.iris.rim.generation.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.util.URI;
import org.eclipse.xtext.junit4.InjectWith;
import org.eclipse.xtext.junit4.XtextRunner;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import com.google.inject.Inject;
import com.odcgroup.iris.rim.generation.T24ResourceModelsGenerator;
import com.odcgroup.workbench.core.tests.util.MultiplatformTestUtil;
import com.odcgroup.workbench.generation.tests.ng.GeneratorTestHelper;

/**
 * Integration test for T24RimGenerator.
 *
 * @author aphethean
 */
@RunWith(XtextRunner.class)
@InjectWith(IRISInjectorProvider.class)
public class T24ResourceModelsGeneratorTest {
	@Inject T24ResourceModelsGenerator t24ResourceModelsGenerator;
	@Inject GeneratorTestHelper helper;
	
	private static final String DEFAULT_OUTPUTT24 = "DEFAULT_OUTPUTT24/";
	
	private static final String DOMAIN_SAMPLE_MODEL = "BL_Foundation.domain";
	private static final String DOMAIN_SAMPLE_FILENAME = "BL_Foundation.rim";
	
	private static final String CONFIG_DOMAIN_MODEL = "ST_Config.domain";
	private static final String BUSINESS_TYPES_DOMAIN_MODEL = "BusinessTypes.domain";	
	
	// testRimGenerateSimpleEnquiry()
	private final static String CUSTOMER_INFO_ENQUIRY_MODEL = "CUSTOMER.INFO.enquiry";
	private final static String CUSTOMER_DOMAIN_MODEL = "ST_Customer.domain";
	private final static String CUSTOMER_INFO_RIM_FILENAME = "enqCUSTOMER.INFO.rim";

	// testRimGenerateEnquiryWithDrillDownNotFound()
	private final static String CUSTOMER_POSITION_ENQUIRY_MODEL = "CUSTOMER.POSITION.enquiry";
	private final static String CUSTOMER_POSITION_RIM_FILENAME = "enqCUSTOMER.POSITION.rim";

	// testRimGenerateNAUEnquiry()
	private final static String CUSTOMER_NAU_ENQUIRY_MODEL = "CUSTOMER.NAU.enquiry";
	private final static String CUSTOMER_NAU_RIM_FILENAME = "enqCUSTOMER.NAU.rim";

	// testRimGenerateEnquiryDrilldown()
	private final static String CUSTOMER_EDGE_ENQUIRY_MODEL = "CUSTOMER.EDGE.enquiry";
	private final static String ACCOUNT_DOMAIN_MODEL = "ACM_AccountOpening.domain";
	private final static String CUSTOMER_EDGE_RIM_FILENAME = "enqCUSTOMER.EDGE.rim";
	
	// testRimGenerateForDynamicDrilldown()
	private static final String ACCT_BAL_TODAY_ENQUIRY_MODEL = "ACCT.BAL.TODAY.enquiry";
	private static final String ACCT_BAL_TODAY_RIM_FILENAME = "enqACCT.BAL.TODAY.rim";
	
	// testRimGenerationForCurrentVariables()
	private static final String NOSTRO_POSITION_ENQUIRY_MODEL = "NOSTRO.POSITION.enquiry";
	private static final String NOSTRO_POSITION_RIM_FILENAME = "enqNOSTRO.POSITION.rim";
	
	// testRimGenerationForDeliveryPreview()
	private static final String FUNDS_TRANSFER_AC_VERSION_MODEL = "FUNDS_TRANSFER,AC.version";
	private static final String FUNDS_TRANSFER_DOMAIN_MODEL = "FT_Contract.domain";
	private static final String FUNDS_TRANSFER_AC_RIM_FILENAME = "verFUNDS.TRANSFER,AC.rim";
	
	// testRimGenerationForDealSlips()
	private static final String TELLER_CASHINL_VERSION_MODEL = "TELLER,CASHINL.version";
	private static final String TELLER_DOMAIN_MODEL = "TT_Contract.domain";
	private static final String TELLER_CASHINL_RIM_FILENAME = "verTELLER,CASHINL.rim";
	
	// testDynamicApplicationSupport()
	private static final String AC_ACC_OPENING_INPUT_VERSION_MODEL = "AC_ACC_OPENING,INPUT.version";
	private static final String EB_FOUNDATION_DOMAIN_MODEL = "EB_Foundation.domain";
	private static final String AC_ACC_OPENING_INPUT_RIM_FILENAME = "verAC.ACC.OPENING,INPUT.rim";
	
	// testRimGenerationForChangedValues()
	private static final String AA_ARRANGEMENT_ACTIVITY_VERSION_MODEL = "AA_ARRANGEMENT_ACTIVITY,AA_NEW.version";
	private static final String AA_FRAMEWORK_DOMAIN_MODEL = "AA_Framework.domain";
	private static final String AA_ARRANGEMENT_ACTIVITY_RIM_FILENAME = "verAA.ARRANGEMENT.ACTIVITY,AA.NEW.rim";
	
	// testImageResourceSupport()
	private static final String IM_FOUNDATION_DOMAIN_MODEL = "IM_Foundation.domain";
	private static final String CUSTOMER_SIGN_SCV_ENQUIRY_MODEL = "CUSTOMER.SIGN.SCV.enquiry";
	private static final String CUSTOMER_SIGN_SCV_RIM_FILENAME = "enqCUSTOMER.SIGN.SCV.rim";

	//testRimGenerateDefaultListEnquiry()
	private final static String ENQLIST_RIM_FILE_ACCOUNT = "enqlistACCOUNT.rim";	

	private final static String CUSTOMER_CREATE_MODEL = "CUSTOMER,CREATE.version";
	private final static String CUSTOMER_CREATE_RIM_FILENAME = "verCUSTOMER,CREATE.rim";
	private final static String AC_ENRICHMENT_MODEL = "AC_ENRICHMENT,CREATE.version";
	private final static String AC_ENRICHMENT_RIM_FILENAME = "verAC.ENRICHMENT,CREATE.rim";
	private final static String ALTERNATE_ACCOUNT_MODEL = "ALTERNATE_ACCOUNT,CREATE.version";
	private final static String ALTERNATE_ACCOUNT_RIM_FILENAME = "verALTERNATE.ACCOUNT,CREATE.rim";
	
	// Verify Resources
	private final static String EBS_AUTO_FUNCTION_MODEL = "EBS_AUTO_FUNCTION,CUSTOMER.version";
	private final static String EBS_AUTO_FUNCTION_DOMAIN_FILE = "EB_SystemTables.domain";
	private final static String EBS_AUTO_FUNCTION_RIM_FILENAME = "verEBS.AUTO.FUNCTION,CUSTOMER.rim"; 
	
	@Test
	@Ignore // until Domain2ResourceMapper.xtend is implemented?
	public void testGenerateRimFromDomain() throws Exception {
		URI uri = helper.getURI(DOMAIN_SAMPLE_MODEL, getClass());
		helper.loader.getResource(uri);
		// Generate data.
		t24ResourceModelsGenerator.doGenerate(uri, helper.loader, helper.fsa);
		Map<String, CharSequence> files = helper.fsa.getTextFiles();
		assertEquals("Generated resources", 1, getNumberOfFilesGenerated(files));
		assertTrue("RIM File not exist: " + DOMAIN_SAMPLE_FILENAME, files.get(DEFAULT_OUTPUTT24 + DOMAIN_SAMPLE_FILENAME)!=null);
		matchResults(files, DOMAIN_SAMPLE_FILENAME);
	}
	
	private void matchResults(Map<String, CharSequence> files, String filename) throws IOException {
		String actual = files.get(DEFAULT_OUTPUTT24+filename).toString();
		String expected = Resources.toString(Resources.getResource(getClass(), filename),Charsets.UTF_8);
		MultiplatformTestUtil.assertEqualsIgnoringEOL("Assertion Failed, Generated RIM file is not same to sample RIM file.", expected, actual);
	}
	
	private int getNumberOfFilesGenerated(Map<String, CharSequence> files) {
		Set<String> set = files.keySet();
		List<String> list = new ArrayList<String>();
		for (String string : set) {
			String str = string;
			if(str.contains(".rim")){
				list.add(str);
			}
		}
		return list.size();
	}
	
	@Test
	public void testRimGenerateSimpleEnquiry() throws Exception {
		helper.loader.getResource(helper.getURI(CUSTOMER_DOMAIN_MODEL, getClass()));
		URI uri = helper.getURI(CUSTOMER_INFO_ENQUIRY_MODEL, getClass());
 		
		// Generate data.
		t24ResourceModelsGenerator.doGenerate(uri, helper.loader, helper.fsa);
		Map<String, CharSequence> files = helper.fsa.getTextFiles();
		assertEquals("Generated resources", 1, getNumberOfFilesGenerated(files));
		assertTrue("RIM File not exist: " + CUSTOMER_INFO_RIM_FILENAME, files.get(DEFAULT_OUTPUTT24 + CUSTOMER_INFO_RIM_FILENAME)!=null);
		matchResults(files, CUSTOMER_INFO_RIM_FILENAME);
}

	@Test
	public void testRimGenerateEnquiryWithDrillDownNotFound() throws Exception {
		helper.loader.getResource(helper.getURI(CUSTOMER_DOMAIN_MODEL, getClass()));
		URI uri = helper.getURI(CUSTOMER_POSITION_ENQUIRY_MODEL, getClass());
 		
		// Generate data.
		t24ResourceModelsGenerator.doGenerate(uri, helper.loader, helper.fsa);
		Map<String, CharSequence> files = helper.fsa.getTextFiles();
		assertEquals("Generated resources", 1, getNumberOfFilesGenerated(files));
		assertTrue("RIM File not exist: " + CUSTOMER_POSITION_RIM_FILENAME, files.get(DEFAULT_OUTPUTT24 + CUSTOMER_POSITION_RIM_FILENAME)!=null);
		matchResults(files, CUSTOMER_POSITION_RIM_FILENAME);
	}

	@Test
	public void testRimGenerateNAUEnquiry() throws Exception {
		helper.loader.getResource(helper.getURI(CUSTOMER_DOMAIN_MODEL, getClass()));
		URI uri = helper.getURI(CUSTOMER_NAU_ENQUIRY_MODEL, getClass());
 		
		// Generate data.
		t24ResourceModelsGenerator.doGenerate(uri, helper.loader, helper.fsa);
		Map<String, CharSequence> files = helper.fsa.getTextFiles();
		assertEquals("Generated resources", 1, getNumberOfFilesGenerated(files));
		assertTrue("RIM File not exist: " + CUSTOMER_NAU_RIM_FILENAME, files.get(DEFAULT_OUTPUTT24 + CUSTOMER_NAU_RIM_FILENAME)!=null);
		matchResults(files, CUSTOMER_NAU_RIM_FILENAME);
	}

	@Test
	public void testRimGenerateEnquiryDrilldown() throws Exception {
		helper.loader.getResource(helper.getURI(CUSTOMER_DOMAIN_MODEL, getClass()));
		URI uri = helper.getURI(CUSTOMER_EDGE_ENQUIRY_MODEL, getClass());
 		
		// Generate data.
		t24ResourceModelsGenerator.doGenerate(uri, helper.loader, helper.fsa);
		Map<String, CharSequence> files = helper.fsa.getTextFiles();
		assertEquals("Generated resources", 1, getNumberOfFilesGenerated(files));
		assertTrue("RIM File not exist: " + CUSTOMER_EDGE_RIM_FILENAME, files.get(DEFAULT_OUTPUTT24 + CUSTOMER_EDGE_RIM_FILENAME)!=null);
		matchResults(files, CUSTOMER_EDGE_RIM_FILENAME);
	}
	
	@Test
	public void testRimGenerateDefaultListEnquiry() throws Exception {
		// Load the business types domain
		helper.loader.getResource(helper.getURI(BUSINESS_TYPES_DOMAIN_MODEL, getClass()));
		
		helper.loader.getResource(helper.getURI(CONFIG_DOMAIN_MODEL, getClass()));
		helper.loader.getResource(helper.getURI(CUSTOMER_DOMAIN_MODEL, getClass()));
		URI uri = helper.getURI(ACCOUNT_DOMAIN_MODEL, getClass());

		// Generate data.
		t24ResourceModelsGenerator.doGenerate(uri, helper.loader, helper.fsa);
		Map<String, CharSequence> files = helper.fsa.getTextFiles();
		assertEquals("Generated resources", 56, getNumberOfFilesGenerated(files));
		assertTrue("RIM File not exist: " + ENQLIST_RIM_FILE_ACCOUNT, files.get(DEFAULT_OUTPUTT24 + ENQLIST_RIM_FILE_ACCOUNT)!=null);
		matchResults(files, ENQLIST_RIM_FILE_ACCOUNT);
	}	

	@Test
	public void testRimGenerationForVersionOfLiveUnauthAndHist() throws Exception {
		helper.loader.getResource(helper.getURI(CUSTOMER_DOMAIN_MODEL, getClass()));
		URI uri = helper.getURI(CUSTOMER_CREATE_MODEL, getClass());
		// set the rim generation in 'strict' mode
		System.setProperty("rim.strict.mode", "true");
		
		t24ResourceModelsGenerator.doGenerate(uri, helper.loader, helper.fsa);
		Map<String, CharSequence> files = helper.fsa.getTextFiles();
		assertEquals("Generated resources", 1, getNumberOfFilesGenerated(files));
		assertTrue("RIM File not exist: " + CUSTOMER_CREATE_RIM_FILENAME, files.get(DEFAULT_OUTPUTT24 + CUSTOMER_CREATE_RIM_FILENAME)!=null);
		matchResults(files, CUSTOMER_CREATE_RIM_FILENAME);
	}
		
	@Test
	public void testRimGenerationForVersionOfLiveAndUnauth() throws Exception {
		// Load the business types domain
		helper.loader.getResource(helper.getURI(BUSINESS_TYPES_DOMAIN_MODEL, getClass()));
		
		helper.loader.getResource(helper.getURI(CONFIG_DOMAIN_MODEL, getClass()));
		helper.loader.getResource(helper.getURI(CUSTOMER_DOMAIN_MODEL, getClass()));
		helper.loader.getResource(helper.getURI(ACCOUNT_DOMAIN_MODEL, getClass()));
		URI uri = helper.getURI(AC_ENRICHMENT_MODEL, getClass());
		// set the rim generation in 'strict' mode
		System.setProperty("rim.strict.mode", "true");

		t24ResourceModelsGenerator.doGenerate(uri, helper.loader, helper.fsa);
		Map<String, CharSequence> files = helper.fsa.getTextFiles();
		assertEquals("Generated resources", 1, getNumberOfFilesGenerated(files));
		assertTrue("RIM File not exist: " + AC_ENRICHMENT_RIM_FILENAME, files.get(DEFAULT_OUTPUTT24 + AC_ENRICHMENT_RIM_FILENAME)!=null);
		matchResults(files, AC_ENRICHMENT_RIM_FILENAME);
	}	
	
	@Test
	public void testRimGenerationForVersionOfLive() throws Exception {
		// Load the business types domain
		helper.loader.getResource(helper.getURI(BUSINESS_TYPES_DOMAIN_MODEL, getClass()));
		
		helper.loader.getResource(helper.getURI(CONFIG_DOMAIN_MODEL, getClass()));
		helper.loader.getResource(helper.getURI(CUSTOMER_DOMAIN_MODEL, getClass()));
		helper.loader.getResource(helper.getURI(ACCOUNT_DOMAIN_MODEL, getClass()));
		URI uri = helper.getURI(ALTERNATE_ACCOUNT_MODEL, getClass());
		
		t24ResourceModelsGenerator.doGenerate(uri, helper.loader, helper.fsa);
		Map<String, CharSequence> files = helper.fsa.getTextFiles();
		assertEquals("Generated resources", 1, getNumberOfFilesGenerated(files));
		assertTrue("RIM File not exist: " + ALTERNATE_ACCOUNT_RIM_FILENAME, files.get(DEFAULT_OUTPUTT24 + ALTERNATE_ACCOUNT_RIM_FILENAME)!=null);
		matchResults(files, ALTERNATE_ACCOUNT_RIM_FILENAME);
	}	
	
	@Test
	public void testRimGenerationForVersionOfVerify() throws Exception {
		helper.loader.getResource(helper.getURI(EBS_AUTO_FUNCTION_DOMAIN_FILE, getClass()));
		URI uri = helper.getURI(EBS_AUTO_FUNCTION_MODEL, getClass());
		
		t24ResourceModelsGenerator.doGenerate(uri, helper.loader, helper.fsa);
		Map<String, CharSequence> files = helper.fsa.getTextFiles();
		assertEquals("Generated resources", 1, getNumberOfFilesGenerated(files));
		assertTrue("RIM File not exist: " + EBS_AUTO_FUNCTION_RIM_FILENAME, files.get(DEFAULT_OUTPUTT24 + EBS_AUTO_FUNCTION_RIM_FILENAME)!=null);
		matchResults(files, EBS_AUTO_FUNCTION_RIM_FILENAME);
	}	
	
	@Test
	public void testRimGenerateForDynamicDrilldown() throws Exception {
		URI uri = helper.getURI(ACCT_BAL_TODAY_ENQUIRY_MODEL, getClass());
		helper.loader.getResource(uri);
 		
		// Generate data.
		t24ResourceModelsGenerator.doGenerate(uri, helper.loader, helper.fsa);
		Map<String, CharSequence> files = helper.fsa.getTextFiles();
		assertEquals("Generated resources", 1, getNumberOfFilesGenerated(files));
		assertTrue("RIM File not exist: " + ACCT_BAL_TODAY_RIM_FILENAME, files.get(DEFAULT_OUTPUTT24 + ACCT_BAL_TODAY_RIM_FILENAME)!=null);
			matchResults(files, ACCT_BAL_TODAY_RIM_FILENAME);
	}
	
	@Test
	public void testDynamicApplicationSupport() throws Exception {
		// Load the business types domain as it is referenced by the EB foundation domain
		helper.loader.getResource(helper.getURI(BUSINESS_TYPES_DOMAIN_MODEL, getClass()));
		
		// Load the ST customer domain as it is referenced by the EB foundation domain
		helper.loader.getResource(helper.getURI(CUSTOMER_DOMAIN_MODEL, getClass()));
		
		// Load the ST config domain as it is referenced by the EB foundation domain
		helper.loader.getResource(helper.getURI(CONFIG_DOMAIN_MODEL, getClass()));
		
		// Load the ST customer domain as it is referenced by the EB foundation domain
		helper.loader.getResource(helper.getURI("FIN_CurrencyConfig.domain", getClass()));		
		
		// Load the EB foundation domain as it is referenced by the version
		helper.loader.getResource(helper.getURI(EB_FOUNDATION_DOMAIN_MODEL, getClass()));		
		
		// Load the version resource
		URI uri = helper.getURI(AC_ACC_OPENING_INPUT_VERSION_MODEL, getClass());
		helper.loader.getResource(uri);
 		
		// Generate data.
		t24ResourceModelsGenerator.doGenerate(uri, helper.loader, helper.fsa);
		Map<String, CharSequence> files = helper.fsa.getTextFiles();
		assertEquals("Generated resources", 1, getNumberOfFilesGenerated(files));
		assertTrue("RIM File not exist: " + AC_ACC_OPENING_INPUT_RIM_FILENAME, files.get(DEFAULT_OUTPUTT24 + AC_ACC_OPENING_INPUT_RIM_FILENAME)!=null);
				
		matchResults(files, AC_ACC_OPENING_INPUT_RIM_FILENAME);						
	}
	
	@Test
	public void testRimGenerationForCurrentVariables() throws Exception {
		URI uri = helper.getURI(NOSTRO_POSITION_ENQUIRY_MODEL, getClass());
		helper.loader.getResource(uri);
 		
		// Generate data.
		t24ResourceModelsGenerator.doGenerate(uri, helper.loader, helper.fsa);
		Map<String, CharSequence> files = helper.fsa.getTextFiles();
		assertEquals("Generated resources", 1, getNumberOfFilesGenerated(files));
		assertTrue("RIM File not exist: " + NOSTRO_POSITION_RIM_FILENAME, files.get(DEFAULT_OUTPUTT24 + NOSTRO_POSITION_RIM_FILENAME)!=null);
			matchResults(files, NOSTRO_POSITION_RIM_FILENAME);		
	}
	
	@Test
	public void testRimGenerationForDeliveryPreview() throws Exception {
		// Load the business types domain as it is referenced by the teller domain
		helper.loader.getResource(helper.getURI(BUSINESS_TYPES_DOMAIN_MODEL, getClass()));
		
		// Load the teller domain as it is referenced by the version
		helper.loader.getResource(helper.getURI(FUNDS_TRANSFER_DOMAIN_MODEL, getClass()));		
		
		// Load the version resource
		URI uri = helper.getURI(FUNDS_TRANSFER_AC_VERSION_MODEL, getClass());
		helper.loader.getResource(uri);	
		
		// Generate data.
		t24ResourceModelsGenerator.doGenerate(uri, helper.loader, helper.fsa);
		Map<String, CharSequence> files = helper.fsa.getTextFiles();
		assertEquals("Generated resources", 1, getNumberOfFilesGenerated(files));
		assertTrue("RIM File not exist: " + FUNDS_TRANSFER_AC_RIM_FILENAME, files.get(DEFAULT_OUTPUTT24 + FUNDS_TRANSFER_AC_RIM_FILENAME)!=null);
		
		matchResults(files, FUNDS_TRANSFER_AC_RIM_FILENAME);						
	}
	
	@Test
	public void testRimGenerationForDealSlips() throws Exception {
		// Load the business types domain as it is referenced by the teller domain
		helper.loader.getResource(helper.getURI(BUSINESS_TYPES_DOMAIN_MODEL, getClass()));
		
		// Load the teller domain as it is referenced by the version
		helper.loader.getResource(helper.getURI(TELLER_DOMAIN_MODEL, getClass()));		
		
		// Load the version resource
		URI uri = helper.getURI(TELLER_CASHINL_VERSION_MODEL, getClass());
		helper.loader.getResource(uri);
 		
		// Generate data.
		t24ResourceModelsGenerator.doGenerate(uri, helper.loader, helper.fsa);
		Map<String, CharSequence> files = helper.fsa.getTextFiles();
		assertEquals("Generated resources", 1, getNumberOfFilesGenerated(files));
		assertTrue("RIM File not exist: " + TELLER_CASHINL_RIM_FILENAME, files.get(DEFAULT_OUTPUTT24 + TELLER_CASHINL_RIM_FILENAME)!=null);
		
		matchResults(files, TELLER_CASHINL_RIM_FILENAME);				
	}

	@Test
	public void testRimGenerationForChangedValues() throws Exception {
		// Load the business types domain as it is referenced by the teller domain
		helper.loader.getResource(helper.getURI(BUSINESS_TYPES_DOMAIN_MODEL, getClass()));

		// Load the teller domain as it is referenced by the version
		helper.loader.getResource(helper.getURI(TELLER_DOMAIN_MODEL, getClass()));

		// Load the version resource
		URI uri = helper.getURI(TELLER_CASHINL_VERSION_MODEL, getClass());
		helper.loader.getResource(uri);

		// Generate data.
		t24ResourceModelsGenerator.doGenerate(uri, helper.loader, helper.fsa);
		Map<String, CharSequence> files = helper.fsa.getTextFiles();
		assertEquals("Generated resources", 1, getNumberOfFilesGenerated(files));
		assertTrue("RIM File not exist: " + TELLER_CASHINL_RIM_FILENAME, files.get(DEFAULT_OUTPUTT24 + TELLER_CASHINL_RIM_FILENAME)!=null);

		matchResults(files, TELLER_CASHINL_RIM_FILENAME);
	}

	@Test
	public void testRimGenerationForAAChangedValues() throws Exception {
		// Load the business types domain as it is referenced by the AA framework domain
		helper.loader.getResource(helper.getURI(BUSINESS_TYPES_DOMAIN_MODEL, getClass()));

		// Load the domain as it is referenced by the version
		helper.loader.getResource(helper.getURI(AA_FRAMEWORK_DOMAIN_MODEL, getClass()));

		// Load the version resource
		URI uri = helper.getURI(AA_ARRANGEMENT_ACTIVITY_VERSION_MODEL, getClass());
		helper.loader.getResource(uri);

		t24ResourceModelsGenerator.doGenerate(uri, helper.loader, helper.fsa);
		Map<String, CharSequence> files = helper.fsa.getTextFiles();
		assertEquals("Generated resources", 1, getNumberOfFilesGenerated(files));
		assertTrue("RIM File not exist: " + AA_ARRANGEMENT_ACTIVITY_RIM_FILENAME, files.get(DEFAULT_OUTPUTT24 + AA_ARRANGEMENT_ACTIVITY_RIM_FILENAME)!=null);

		matchResults(files, AA_ARRANGEMENT_ACTIVITY_RIM_FILENAME);
	}
	
	@Test
	public void testImageResourceSupport() throws Exception {
		// Load the domain as it is referenced by the enquiry
		helper.loader.getResource(helper.getURI(IM_FOUNDATION_DOMAIN_MODEL, getClass()));
		
		// Load the enquiry resource		
		URI uri = helper.getURI(CUSTOMER_SIGN_SCV_ENQUIRY_MODEL, getClass());
		helper.loader.getResource(uri);

		t24ResourceModelsGenerator.doGenerate(uri, helper.loader, helper.fsa);
		Map<String, CharSequence> files = helper.fsa.getTextFiles();
		assertEquals("Generated resources", 1, getNumberOfFilesGenerated(files));
		assertTrue("RIM File not exist: " + CUSTOMER_SIGN_SCV_RIM_FILENAME, files.get(DEFAULT_OUTPUTT24 + CUSTOMER_SIGN_SCV_RIM_FILENAME)!=null);

		matchResults(files, CUSTOMER_SIGN_SCV_RIM_FILENAME);		
	}
}
