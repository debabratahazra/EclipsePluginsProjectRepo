package com.odcgroup.page.transformmodel;

/**
 * Constants for Reporter tags.
 * Ref. DS-2756
 * @author yan
 * @since 2.4
 */
public interface ReporterContants {

	/** Xml namespace for the reporter tags. */
	String REPORTER_URI = "http://www.odcgroup.com/uif/inputcontrol/0.1";
	
	/** Xml prefix for the reporter tags. */
	String REPORTER_PREFIX = "ic";

	/** The define element name */
	String REPORTER_ELEMENT = "reporter";
	
	/** The reporter suffix */
    String REPORT_KEY_SUFFIX = ".reporter";  

    /** The add scope report element name */
    String ADD_SCOPE_REPORTER_ELEMENT = "add-scope-reporter";

    /** Message rendering element name */
    String MSG_RENDERING_ELEMENT = "msg-rendering";
    
    /** Form name attribute name */
    String FORM_NAME_ATTRIBUTE = "form-name";
    
    /** Report key attribute */
	String REPORT_KEY_ATTRIBUTE = "report-key";
    
}
