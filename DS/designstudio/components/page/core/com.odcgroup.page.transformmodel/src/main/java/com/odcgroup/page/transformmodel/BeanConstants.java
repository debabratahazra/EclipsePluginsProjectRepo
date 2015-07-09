package com.odcgroup.page.transformmodel;

/**
 * Constants for Bean tags.
 * 
 * @author Gary Hayes
 */
public interface BeanConstants {
	
	/** Xml namespace for the bean tags. */
	public String BEAN_URI = "http://www.odcgroup.com/uif/bean/0.1";	

	/** Xml prefix for the bean tags. */
	public String BEAN_PREFIX = "bean";
	
	/** The define element name. */
	public String BEAN_DEFINE_ELEMENT = "define";
	
	/** The get-property element name. */
	public String BEAN_GET_PROPERTY_ELEMENT = "get-property";
	
	/** The is-null-or-empty element name. */
	public String BEAN_IS_NULL_OR_EMPTY = "is-null-or-empty";
	
	/** The contains element name. */
	public String BEAN_CONTAINS = "contains";
	
	/** The iterate element name. */
	public String BEAN_ITERATE = "iterate";
	
	/** The name attribute. */
	public String BEAN_NAME_ATTRIBUTE = "name";
	
    /** The key attribute. */
    public String BEAN_KEY_ATTRIBUTE = "key";	
	
	/** The bean attribute. */
	public String BEAN_BEAN_ATTRIBUTE = "bean";
	
	/** The property attribute. */
	public String BEAN_PROPERTY_ATTRIBUTE = "property";
	
	/** The property attribute. */
	public String BEAN_OBJECT_ATTRIBUTE = "object";
	
	/** The property attribute. */
	public String BEAN_STRING_ATTRIBUTE = "string";
	
	/** The prefix-keyword attribute.
	 *  @see http://rd.oams.com/browse/OCS-42357 */
	public String BEAN_PREFIX_KEYWORD_ATTRIBUTE = "prefix-keyword";
}
