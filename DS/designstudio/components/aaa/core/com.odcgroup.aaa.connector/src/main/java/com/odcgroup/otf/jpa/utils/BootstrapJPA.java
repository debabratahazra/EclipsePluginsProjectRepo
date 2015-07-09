package com.odcgroup.otf.jpa.utils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.Persistence;
import javax.rmi.PortableRemoteObject;
import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.apache.openjpa.conf.OpenJPAVersion;
import org.apache.openjpa.jdbc.sql.OracleDictionary;

import com.odcgroup.otf.jpa.exception.OtfJPAInitializationException;
import com.odcgroup.otf.jpa.internal.openjpa.OdysseyCustomDerbyDictionary;
import com.odcgroup.otf.jpa.internal.openjpa.OdysseyCustomSybaseDictionary;

/**
 * Helpers to "bootstrap" JPA.
 * 
 * @author Michael Vorburger (MVO)
 */
public final class BootstrapJPA {

	private static final Logger LOGGER = Logger.getLogger(BootstrapJPA.class);

	private static final String DB_DICT_PROPERTYNAME1 = "openjpa.jdbc.DBDictionary";
	private static final String DB_DICT_PROPERTYNAME2 = "DBDictionary";

	// @see http://rd.oams.com/browse/OCS-32118 (it's not really a "snapshot", it's an IBM supported drop)
	private static final String OPENJPA_EXPECTED_VERSION = "1.2.2-SNAPSHOT";
	private static final Object OPENJPA_EXPECTED_REVISION = "422266:888513M";

	// Private constructor
	private BootstrapJPA() {}

	/**
	 * Helper to load a Property file from the classpath.
	 * 
	 * This conveniently also does the {@link #fixProperties(Properties)} before returning the Properties.
	 * Because fixProperties has some code that looks at passed properties (e.g. to determine database type),
	 * this can only be used to load properties files with OpenJPA specific properties. 
	 * 
	 * @param propertiesResourceName Name of the .properties file on the classpath
	 * @return Properties read, with JPA properties "fixed".
	 * @throws IOException If problems reading
	 */
	public static Properties getPropertiesFromClasspathResource(String propertiesResourceName) throws IOException {
		Properties p = getPropertiesFromClasspathResourceNonJPA(propertiesResourceName);
		// addFullLoggingProperties(p);
		fixProperties(p);
		// NOT needed here after all, I hope? - fixPropertiesForStupidOpenJPA(p);
		return p;
	}

	/**
	 * Helper to load a Property file from the classpath.
	 * This is a generic helper with nothing specific about JPA. 
	 * 
	 * @param propertiesResourceName Name of the .properties file on the classpath
	 * @return java.util.Properties read from the file
	 * @throws IOException If problems reading
	 */
	// TODO Something like this probably already exists in OCS (it certainly does in Spring)... use that instead? Or keep outside of this class in a more generic non-JPA helper class?
	public static Properties getPropertiesFromClasspathResourceNonJPA(String propertiesResourceName) throws IOException {
		Properties p = new Properties();
		ClassLoader cl = Thread.currentThread().getContextClassLoader(); 
		InputStream is = cl.getResourceAsStream(propertiesResourceName);
		if (is==null) {
			throw new IOException("getResourceAsStream(\"" + propertiesResourceName + "\") returned null");
		}
		try {
			p.load(is);
		} finally {
			is.close();
		}
		return p;
	}	
	
	/**
	 * Helper to adapt properties programmatically, so that they do not need to be re-specified each time, and we can extend it easily.
	 * Currently, this does the following:<ul>
	 * <li>forces the use of our OdysseyCustomSybaseDictionary (if appropriate),</li>
	 * <li>enables statement batching (hard-coded to 100, should be flexible?)</li>
	 * <li>and forces RuntimeUnenhancedClasses to unsupported.</li></ul>
	 * 
	 * The {@link #getPropertiesFromClasspathResource(String)} as well as the
	 * {@link CreateDB#getTemporaryDerbyDatabaseProperties()} conveniently already do this,
	 * but you need to call this if you obtain JPA Properties yourself, before passing them
	 * to {@link Persistence#createEntityManagerFactory(String, java.util.Map)}.
	 * 
	 * @param p Properties
	 */
	public static void fixProperties(Properties p) {
		openJPAVersionCheck();
		fixDBDictionaryProperty(p);
		fixPropertiesCOMMON(p);
	}
	public static void fixProperties(Properties p, DatabaseType db) {
		openJPAVersionCheck();
		fixDBDictionaryProperty(p, db);
		fixPropertiesCOMMON(p);
	}

	private static void fixPropertiesCOMMON(Properties p) {
		forceRuntimeUnenhancedClasses(p);
		forceExceptionWhenAccessUnloadedDataOfDetachedObject(p);
		
		// The setBatchLimit has to be done AFTER the fixDBDictionaryProperty!
		setDefaultBatchLimit(p);
		
		forceLog4jLogging(p);
	}
	
    /**
	 * @see http://openjpa.apache.org/builds/1.1.0/apache-openjpa-1.1.0/docs/manual/ref_guide_dbsetup_stmtbatch.html
	 */
	private static void setDefaultBatchLimit(Properties p) {
		final int defaultBatchLimit = 300;
		
		// Just to be sure, use both:
		setDefaultBatchLimit(p, DB_DICT_PROPERTYNAME1, defaultBatchLimit);
		setDefaultBatchLimit(p, DB_DICT_PROPERTYNAME2, defaultBatchLimit);
	}
	
	private static void setDefaultBatchLimit(Properties p, String dbDictPropertyName, int batchLimit) {
		String dbDictProperty = p.getProperty(dbDictPropertyName);
		if (dbDictProperty != null) {
			// If there are already other properties specified, this won't work... bah.
			p.setProperty(dbDictPropertyName, dbDictProperty + "(batchLimit=" + batchLimit + ")");
		} else {
			throw new UnsupportedOperationException("The db dictionary should be set before calling setDefaultBatchLimit");
		}		
	}
/* 	
	private static void setBatchLimit(EntityManagerFactory emf, int limit) {
		OpenJPAEntityManagerFactorySPI openJpaEmf = (OpenJPAEntityManagerFactorySPI) emf; 
		JDBCConfiguration configuration = (JDBCConfiguration) openJpaEmf.getConfiguration();
		DBDictionary activeDBDictionary = configuration.getDBDictionaryInstance();
		activeDBDictionary.setBatchLimit(limit);
	}
*/

	/**
	 * openjpa.RuntimeUnenhancedClasses = unsupported.
	 * 
	 * We never want to run under OpenJPA's stupid (non-working)
	 * "fall-back mode" which creates subclasses. We want to either use the
	 * OpenJPA JVM Agent (for small test-cases); or use build-time enhancement,
	 * which is preferred/recommended/necessary for all "real" Entites such as
	 * those gen. from aaa.mmml which we'll end up in production, mainly because
	 * of the high start-up cost (time, several tens of seconds, with the 244
	 * T'A Entities) of OpenJPA run-time enhancement.
	 * 
	 * @see http://openjpa.apache.org/builds/1.2.1/apache-openjpa-1.2.1/docs/manual/ref_guide_conf_openjpa.html#openjpa.RuntimeUnenhancedClasses
	 * @see https://issues.apache.org/jira/browse/OPENJPA-650
	 * @see https://issues.apache.org/jira/browse/OPENJPA-651
	 * @see http://rd.oams.com/browse/ARCH-160
	 */
	private static void forceRuntimeUnenhancedClasses(Properties p) {
	    p.setProperty("openjpa.RuntimeUnenhancedClasses", "unsupported");
	}

	/**
	 * If the ConnectionURL or ConnectionDriverName contain "sybase",
	 * then force the DBDictionary to a OdysseyCustomSybaseDictionary.
	 * 
	 * @see OdysseyCustomSybaseDictionary
	 * @param p Properties to fix
	 */
	private static void fixDBDictionaryProperty(Properties p) {
		DatabaseType dbType = getDatabaseType(p);
		fixDBDictionaryProperty(p, dbType);
	}
	private static void fixDBDictionaryProperty(Properties p, DatabaseType dbType) {
		if (dbType == DatabaseType.Sybase) {
			fixDictionary(p, OdysseyCustomSybaseDictionary.class.getName());
		} else if (dbType == DatabaseType.Oracle) {
			fixDictionary(p, OracleDictionary.class.getName());
		} else if (dbType == DatabaseType.Derby) {
			fixDictionary(p, OdysseyCustomDerbyDictionary.class.getName());		
		}
	}

	private static void fixDictionary(Properties p, String dbDictionaryClassName) {
		// It's again not very clear which property name to use... set both, don't change this unless you verify it still works and are really sure: 
		p.setProperty(DB_DICT_PROPERTYNAME1, dbDictionaryClassName);
		p.setProperty(DB_DICT_PROPERTYNAME2, dbDictionaryClassName);
	}
	
	/**
	 * This is used to use different techniques to pass the data security id (textsize in Sybase) 
	 * @param p
	 * @return The database type used
	 */
	// TODO why public, could and should be private
	public static DatabaseType getDatabaseType(Properties p) {
		// Connection string analysis
		for (DatabaseType d : DatabaseType.values()) {
			if ( doesConnectionPropertiesContains(p, d.getDatabaseConnectionDiscriminator())) {
				return d;
			}			
		}

		// Datasource analysis
		String datasourceJNDI = p.getProperty("openjpa.ConnectionFactoryName");
		if (datasourceJNDI == null) {
			throw new OtfJPAInitializationException("No OpenJPA connection properties and no datasource property found - don't know how to determine the type of database");
		}
		
		Connection connection = null;
		try {
			DataSource datasource = getDatasource(datasourceJNDI);
			connection = datasource.getConnection();
			
			for (DatabaseType d : DatabaseType.values()) {
				if (querySucceed(connection, DatabaseType.Sybase.getTestQuery())) {
					return d;
				}
			}
			
			throw new OtfJPAInitializationException("Unsupported database type");
		} catch (NamingException e) {
			throw new OtfJPAInitializationException("Due to NamingException, uUnable to look up datasource " + datasourceJNDI, e);
		} catch (SQLException e) {
			throw new OtfJPAInitializationException("Due to SQLException, unable to get datasource " + datasourceJNDI, e);
		} finally {
	    	if (connection != null) {
	    		try {
	    			connection.close();
	    		} catch (SQLException e) {
	    			LOGGER.error("Unable to close connection", e);
	    		}
	    	}
		}
	}

	/**
	 * Execute the test query. Return true if the query succeed, false if the query failed
	 * @param connection connection to use to execute the test query
	 * @param testQuery test query (depends on the database).
	 * @return <code>true</code> if the query succeed, <code>false</code> if the query failed
	 */
	private static boolean querySucceed(Connection connection, String testQuery) {
		Statement stmt = null;
    	ResultSet result = null; 
		try {
        	stmt = connection.createStatement();
        	result = stmt.executeQuery(testQuery);
			LOGGER.debug("Test query " + testQuery + " succeed");
        	return true;
		} catch (Exception e) {
			LOGGER.debug("Test query " + testQuery + " failed");
			return false;
		} finally {
	    	if (result!=null) {
	    		try {
	    			result.close();
	    		} catch (SQLException e) {
	    			LOGGER.error("Unable to close resultset", e);
	    		}
	    	}
	    	if (stmt != null) {
	    		try {
	    			stmt.close();
	    		} catch (SQLException e) {
	    			LOGGER.error("Unable to close statement", e);
	    		}
	    	}
		}
	}

	private static DataSource getDatasource(String datasourceJNDI) throws NamingException {
		// TODO : use OtfServiceLocator when integrated to OCS
		// DataSource ds = (DataSource) OtfServiceLocator.getInstance().lookup(datasourceJNDI, DataSource.class);
		InitialContext ic = new InitialContext();
		Object service = ic.lookup(datasourceJNDI);
		DataSource datasource = (DataSource)PortableRemoteObject.narrow(service, DataSource.class);
		return datasource;
	}

	/**
	 * As the common denominator logging framework between OpenJPA,
	 * other frameworks (notably Spring) and existing OCS code is log4j,
	 * OpenJPA has to use Log4j and not it's own built-in simple logging.
	 * 
	 * @see http://openjpa.apache.org/builds/latest/docs/manual/ref_guide_logging.html
	 *   
	 * @param p Properties
	 */
	private static void forceLog4jLogging(Properties p) {
		p.put("openjpa.Log", "log4j");
		// This is nice/handy if openjpa.jdbc.SQL are enabled, and should
		// (hopefully) NOT have any performance impact IFF the Log4j Logger for
		// OpenJPA is not active.
		p.put("openjpa.ConnectionFactoryProperties", "PrettyPrint=true, PrettyPrintLineLength=100");
	}

	/**
	 * Make OpenJPA throw an IllegalStateException (instead of returning null)
	 * when accessing an unloaded field from a detached instanced (instead of
	 * lazy loading if it was still attached).
	 * 
	 * According the JPA documentation: loaded: Detach all fields and relations
	 * that are already loaded, but don't include unloaded fields in the
	 * detached graph. This is the default. AccessUnloaded: Whether to allow
	 * access to unloaded fields of detached objects. Defaults to true. Set to
	 * false to throw an exception whenever an unloaded field is accessed. This
	 * option is only available when you use detached state managers, as
	 * determined by the settings above.
	 * 
	 * @param p
	 */
	private static void forceExceptionWhenAccessUnloadedDataOfDetachedObject(Properties p) {
		p.put("openjpa.DetachState", "loaded(AccessUnloaded=false)");
		
		p.put("openjpa.AutoDetach", "false");
	}


	private static boolean doesConnectionPropertiesContains(Properties p, String contained) {
		return ( doesPropertyContainString(p,"ConnectionURL", contained) 
				  || doesPropertyContainString(p, "openjpa.ConnectionURL", contained) 
				  || doesPropertyContainString(p, "ConnectionDriverName", contained)
				  || doesPropertyContainString(p, "openjpa.ConnectionDriverName", contained));
	}
	
	private static boolean doesPropertyContainString(Properties p, String propertyName, String contained) {
		String propertyValue = p.getProperty(propertyName);
		return (propertyValue != null) && (propertyValue.toLowerCase().contains(contained));
	}

	/*
	 * Validate the version number of the underlying OpenJPA implementation
	 */
	private static void openJPAVersionCheck() {
		if (!OpenJPAVersion.VERSION_NUMBER.equals(OPENJPA_EXPECTED_VERSION)) {
			throw new OtfJPAInitializationException("Unsupported OpenJPA version, expected:" + 
					OPENJPA_EXPECTED_VERSION + " but was:" + OpenJPAVersion.VERSION_NUMBER);
		}
		if (!OpenJPAVersion.REVISION_NUMBER.equals(OPENJPA_EXPECTED_REVISION)) {
			throw new OtfJPAInitializationException("Unsupported OpenJPA revision (but version is correct: "
					+ OpenJPAVersion.VERSION_NUMBER + "), expected:" + OPENJPA_EXPECTED_REVISION + " but was:"
					+ OpenJPAVersion.REVISION_NUMBER);
		}
	}
	
//	/**
//	 * Workaround to fix OpenJPA meta-data loading bug (https://issues.apache.org/jira/browse/OPENJPA-1141)
//	 * @param openJpaEm OpenJPAEntityManager
//	 * @throws OtfJPAInitializationException
//	 */
//	// TODO why public, could and should be private
//	public static void fixOpenJPAMappingProblem(OpenJPAEntityManager openJpaEm) {
//		try {
//			fixOpenJPAMappingProblemInternal(openJpaEm);
//		} catch (RuntimeException e) {
//			// Do nothing, the exception is throws to rollback the transaction
//		}
//	}
//	
//	@SuppressWarnings("unchecked")
//	@Transactional(rollbackFor=RuntimeException.class)
//	private static void fixOpenJPAMappingProblemInternal(OpenJPAEntityManager openJpaEm) {
//		List<Class> entitiesToBeInitilised = new ArrayList<Class>(openJpaEm.getPersistedClasses());
//		int nbTries = 0;
//		do {
//			List<Class> remainingEntities = new ArrayList<Class>(entitiesToBeInitilised);
//			for (Class entityClass : remainingEntities) {
//				try {
//					// add a dummy entity                                           
//					Object entity = entityClass.newInstance();
//					openJpaEm.persist(entity);
//				} catch (Exception e) {
//					if (++nbTries > 10) {
//						// Too much tries, abandoning...
//						throw new OtfJPAInitializationException("Unable to correctly initialize OpenJPA metainformation", e);
//					}
//					continue;
//				}
//				entitiesToBeInitilised.remove(entityClass);
//			}
//		} while (entitiesToBeInitilised.size() != 0);
//	}

	
}
