package com.odcgroup.aaa.connector.internal.util;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.apache.commons.lang.StringUtils;

import com.odcgroup.otf.jpa.utils.BootstrapJPA;

/**
 * Representation of a T'A DB.
 * This is a factory for TADatabaseConnection objects.
 * It does NOT hold any open Connection, but will typically have other stuff cached.
 * 
 * While working with one given T'A Database, create and re-use one such object.
 * When switching T'A Database, close() and create another instance of this.
 * When using different T'A Databases, create several instances of this.
 * 
 * Technical Implementation Note: This contains a JPA EntityManagerFactory (EMF).
 * 
 * @author Michael Vorburger (MVO)
 */
public class TADatabase {

    private final EntityManagerFactory emf;
    private final static String PU_NAME = "aaa";
    
    private static String getCharsetString(TAConnectionInfo env) {
    	String charset = env.getCharset();
    	if (StringUtils.isBlank(charset)) {
    		return "";
    	} else {
    		return "&CHARSET="+charset;
    	}
    }
    
    public TADatabase(TAConnectionInfo env) {
        Properties connectionProperties = new Properties();
        connectionProperties.setProperty("openjpa.ConnectionDriverName", "com.sybase.jdbc3.jdbc.SybDriver"); // Hard-coded; OK
        connectionProperties.setProperty("openjpa.ConnectionURL", "jdbc:sybase:Tds:" 
                + env.getHostname() + ":" + env.getPort() + "/" + env.getDBName() 
                + "?BE_AS_JDBC_COMPLIANT_AS_POSSIBLE=true"
                + getCharsetString(env));
        connectionProperties.setProperty("openjpa.ConnectionUserName", env.getUsername());
        connectionProperties.setProperty("openjpa.ConnectionPassword", env.getPassword());
        
        BootstrapJPA.fixProperties(connectionProperties); // This is important - do not remove!
        
        emf = Persistence.createEntityManagerFactory(PU_NAME , connectionProperties);
    }
    
    public TADatabaseConnection newTADatabaseConnection() {
        if ( emf.isOpen() ) {
            return new TADatabaseConnection(emf.createEntityManager());
        } else {
            throw new IllegalStateException("We're closed for business for today!  (I.e. the close() method has already been called.)");
        }
    }
    
    /**
     * Should be called to "destroy" this instance.
     * E.g. at Design Studio shutdown, or when switching / removing a TA DataSource.
     * This object will not be usable anymore after this is called. 
     */
    public void close() {
        emf.close();
    }
}
