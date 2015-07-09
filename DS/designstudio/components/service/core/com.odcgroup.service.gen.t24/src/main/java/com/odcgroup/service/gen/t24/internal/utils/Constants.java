package com.odcgroup.service.gen.t24.internal.utils;

public class Constants {
	
	public static final String GENERATED_SRC_DIR = "./src/generated/";
	
	/*********************************************************
	 * source directory structure for T24 service
	 ********************************************************/
	public static final String GENERATED_T24SERVICE_SRC_DIR 
		= GENERATED_SRC_DIR + "t24service/";
	//output dir for JBC API 
	public static final String GENERATED_JBC_API_SRC_DIR 
		= GENERATED_T24SERVICE_SRC_DIR + "JBCAPI/";
	//output directory for JBC Impl
	public static final String GENERATED_JBC_IMPL_SRC_DIR
		= GENERATED_T24SERVICE_SRC_DIR + "JBCImpl/";
	//output direcotry for JBC Insert
	public static final String GENERATED_JBC_INSERT_SRC_DIR
		= GENERATED_T24SERVICE_SRC_DIR + "JBCInsert/";
	//output directory for c++ API
	public static final String GENERATED_CPP_API_SRC_DIR
		= GENERATED_T24SERVICE_SRC_DIR + "cppAPI/";
	//output directory for .NET API	
	public static final String GENERATED_DOTNET_API_SRC_DIR
		= GENERATED_T24SERVICE_SRC_DIR + "dotnetAPI/";
	//output directory for java API
	public static final String GENERATED_JAVA_API_SRC_DIR
		= GENERATED_T24SERVICE_SRC_DIR + "javaAPI/";
	
	/********************************************************
	 * source directory structure for T24 service data
	 *******************************************************/
	public static final String GENERATED_DATA_SRC_DIR
		= GENERATED_SRC_DIR + "data/";
	//output directory for java data and converter
	public static final String GENERATED_JAVA_DATA_SRC_DIR
		= GENERATED_DATA_SRC_DIR + "java/";
	//output directory for c++ data and converter
	public static final String GENERATED_CPP_DATA_SRC_DIR
		= GENERATED_DATA_SRC_DIR + "cpp/";
	//output directory for .net data and marshal
	public static final String GENERATED_DOTNET_DATA_SRC_DIR
		= GENERATED_DATA_SRC_DIR + "dotnet";
	
	/*********************************************************
	 * source directory structure for T24 service proxy adaptor
	 *********************************************************/
	public static final String GENERATED_PROXY_ADAPTOR_SRC_DIR
		= GENERATED_SRC_DIR + "proxyAdaptor/";
	//output directory for c++ proxy adaptor
	public static final String GENERATED_CPP_PROXY_ADAPTOR_SRC_DIR
		= GENERATED_PROXY_ADAPTOR_SRC_DIR + "cpp/";
	//output directory for java proxy adaptor
	public static final String GENERATED_JAVA_PROXY_ADAPTOR_SRC_DIR
		= GENERATED_PROXY_ADAPTOR_SRC_DIR + "java/";
	
	/************************************************************
	 * source directory structure for T24 web service
	 ***********************************************************/
	public static final String GENERATED_WS_SRC_DIR 
		= GENERATED_SRC_DIR + "ws/";
	public static final String GENERATED_JAVA_WS_SRC_DIR
		= GENERATED_WS_SRC_DIR + "java/";
	public static final String GENERATED_DOTNET_WCF_SERVICE_SRC_DIR
	= GENERATED_WS_SRC_DIR + "dotnet/";
	
	/************************************************************
	 * source directory structure for java EJB's
	 ***********************************************************/
	public static final String GENERATED_JAVA_EJB_SRC_DIR 
		= GENERATED_SRC_DIR + "ejb/";
	public static final String GENERATED_JAVA_EJB_RSR_DIR 
		= GENERATED_JAVA_EJB_SRC_DIR + "META-INF/";

	
	/************************************************************
	 * source directory structure for T24 web service
	 ***********************************************************/
	public static final String GENERATED_REST_SRC_DIR 
		= GENERATED_SRC_DIR + "rest/";
	
	public static final String SERVICE_PACKAGE_PREFIX = "com.temenos.services.";
	public static final String T24_SERVICE_PACKAGE_PREFIX = "com.temenos.t24.services.";
	public static final String SERVICE_DATA_PACKAGE_PREFIX = "com.temenos.services.data";
	public static final String WEB_SERVICE_PACKAGE_PREFIX = "com.temenos.webservices.";
	public static final String WEB_SERVICE_DATA_PACKAGE_PREFIX = "com.temenos.webservices.data";
	
	public static final String REST_WEB_SERVICE_PACKAGE = "com.temenos.restservices";
	public static final String REST_WEB_SERVICE_RESOURCE_PACKAGE = "com.temenos.restservices.resources";
	public static final String REST_WEB_SERVICE_REPRESENTATION_PACKAGE = "com.temenos.restservices.representations";
	public static final String REST_WEB_SERVICE_APP_PACKAGE = "com.temenos.restservices.applications";
	public static final String REST_WEB_SERVICE_DATA_PACKAGE = "com.temenos.restservices.data";
	public static final String REST_WEB_SERVICE_ADAPTORS_PACKAGE = "com.temenos.restservices.adaptors";
		
	public static final String CPP_HEADER_FILE_EXT = ".h";
	public static final String CPP_FILE_EXT = ".cpp";
	public static final String JBC_FILE_EXT = ".b";
	public static final String CS_FILE_EXT = ".cs";
	public static final String SVC_FILE_EXT = ".svc";
	public static final String SVCCS_FILE_EXT = SVC_FILE_EXT + CS_FILE_EXT;
	
	
}
