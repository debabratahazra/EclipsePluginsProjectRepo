package com.odcgroup.page.transformmodel;

/**
 * Interface for the definition of the i18n constants.
 * 
 * @author Gary Hayes
 */
public interface I18nConstants {

	/** The I18N namespace. */
	public String I18N_NAMESPACE_URI = "http://apache.org/cocoon/i18n/2.1";

	/** The prefix used for i18n elements. */
	public static final String I18N_NAMESPACE_PREFIX = "i18n";	
	
	/** The name of the element in a <i18n:text>. */
	public String I18N_TEXT_ELEMENT_NAME = "text";	
	
	/** The name of the attribute used for specifying that other attributes are i18n. */
	public String I18N_ATTRIBUTE_NAME = "attr";	
}
