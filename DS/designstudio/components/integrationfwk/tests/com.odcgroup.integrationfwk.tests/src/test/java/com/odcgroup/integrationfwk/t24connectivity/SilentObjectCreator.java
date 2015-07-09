package com.odcgroup.integrationfwk.t24connectivity;


/**
 * class used to create the object without constructor. please check
 * <a>http://www.javaspecialists.eu/archive/Issue175.html</a> for further
 * details.
 * 
 * @author sbharathraja
 * 
 */
public class SilentObjectCreator {

	public static <T> T create(Class<T> clazz) {
		try {
			return clazz.newInstance();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			// Uncomment and replace with appropriate logger
			// LOGGER.error(e, e);
			throw new IllegalStateException("Cannot create object", e);
		}
	}

}