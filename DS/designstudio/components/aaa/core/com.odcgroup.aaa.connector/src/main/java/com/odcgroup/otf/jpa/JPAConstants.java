package com.odcgroup.otf.jpa;

/**
 * Some constants.
 * 
 * @author Michael Vorburger (MVO)
 */
public interface JPAConstants {

	/**
	 * Persistence Unit Name of the JPA Entity generated from MML.
	 * In the current architecture, there is one and only one
	 * Persistence Unit, which includes all JPA Entites from MML.
	 *  
	 * (Should there ever by any need to generate more than one JPA
	 * Persistence Unit from MML, this would have to be reviewed.)
	 */
	String MDF_GENERATED_PERSISTENCE_UNIT_NAME = "mdfmml";
}
