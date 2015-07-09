package com.odcgroup.iris.generator.utils;

import java.util.Arrays;
import java.util.List;


/**
 * Constant class to be used for all types of generator contants we will be using
 *
 * @author sjunejo
 *
 */
public class GeneratorConstants {

	// Path where we will be generating artefacts
	public static final String GEN_IRISMETA = "/iris";
	
	// Model extensions
	public static final String DOMAIN_EXT = "domain";
	public static final String VERSION_EXT = "version";
	public static final String ENQUIRY_EXT = "enquiry";
	
	// T24 Constants
	public static final String MVGROUP_SUFFIX = "MvGroup";
	public static final String VALUEPOSITION_PROP_NAME = "valuePosition";
	public static final String SVGROUP_SUFFIX = "SvGroup";
	public static final String SUBVALUEPOSITION_PROP_NAME = "subValuePosition";
	
	public static final String ENQ_NO_FILE = "NOFILE";
	
	public static final String T24_ANNOTATION_NAMESPACE = "http://www.temenos.com/t24";

	public static final String T24_ANNOTATION_NAME = "i";

	public static final String T24_ANNOTATION_T24TYPE = "t24Type";

	public static final String T24_ANNOTATION_AA_PRODUCT_GROUP = "aaProductGroup";

	public static final String T24_ANNOTATION_AA_PROPERTY = "aaProperty";
	
	public static final String T24_ANNOTATION_BUSINESS_TYPE = "businessType";
	
	public static final String DOMAIN_MODULE_ANNOTATION = "module";
	
	// TERM values for data type
	public static final String T24_TYPE_ENUMERATION = "ENUMERATION";
	public static final String T24_TYPE_BOOLEAN = "BOOLEAN";
	public static final String T24_TYPE_NUMBER = "NUMBER";
	public static final String T24_TYPE_STRING = "";
	public static final String T24_TYPE_INTEGER_NUMBER = "INTEGER_NUMBER";
	public static final String T24_TYPE_DATE = "DATE";
	public static final String T24_TYPE_TIMESTAMP = "TIMESTAMP";
	
	// T24 AAA constants
	public static final String AA_MODULE = "AA";
	public static final String AB_MODULE = "AB";
	public static final String AI_MODULE = "AI";
	public static final String AP_MODULE = "AP";
	public static final String AA_PROP_CLASS_PREFIX = "AA.ARR.";
	public static final String AAA_TYPE = "AA.ARRANGEMENT.ACTIVITY";
	public static final List<String> AA_PRODUCT_LINES = 
						Arrays.asList(new String[] {
										"ACCOUNTS", "INTERNET.SERVICES", "LENDING", "OTHER", "BUNDLE", 
										"DEPOSITS", "MOBILE.SERVICES", "PROXY.SERVICES", "RELATIONSHIP.PRICING"} );
}
