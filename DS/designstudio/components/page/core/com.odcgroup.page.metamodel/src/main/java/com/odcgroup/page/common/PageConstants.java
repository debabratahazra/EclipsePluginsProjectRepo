package com.odcgroup.page.common;

/**
 * Constants used in the Page Designer.
 * 
 * @author Gary Hayes
 */
public interface PageConstants {

	/** The file extension for pages. */
	public String PAGE_FILE_EXTENSION = "page";
	
	/** The file extension for modules. */
	public String MODULE_FILE_EXTENSION = "module";
	
	/** The file extension for page fragments. */
	public String FRAGMENT_FILE_EXTENSION = "fragment";	
	
	/** The file extension for page layer. */
	public String LAYER_FILE_EXTENSION = FRAGMENT_FILE_EXTENSION;	

	/** All the file extensions associated with the Page Designer. */
	public String[] PAGE_DESIGNER_FILE_EXTENSIONS = new String[]{PAGE_FILE_EXTENSION, MODULE_FILE_EXTENSION, FRAGMENT_FILE_EXTENSION, LAYER_FILE_EXTENSION};
	
	/** All file extensions which can be dropped within the editor */
	public String[] DROPABLE_FILE_EXTENSION = new String[] {MODULE_FILE_EXTENSION, FRAGMENT_FILE_EXTENSION};

	/** The file extension for xsp file. */
	public String XSP_FILE_EXTENSION = "xsp";	

	/** a file extension for the MDF files. */
	public String MML_FILE_EXTENSION = "mml";
	
	/** a file extension for the MDF files. */
	public String DOMAIN_FILE_EXTENSION = "domain";
	
	/** a file extension for the classpath. */
	public String CLASSPATH_FILE_EXTENSION = "classpath";	
}
