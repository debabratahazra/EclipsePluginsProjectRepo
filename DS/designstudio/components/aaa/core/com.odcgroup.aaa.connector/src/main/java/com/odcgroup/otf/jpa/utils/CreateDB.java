package com.odcgroup.otf.jpa.utils;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.apache.openjpa.jdbc.conf.JDBCConfiguration;
import org.apache.openjpa.jdbc.conf.JDBCConfigurationImpl;
import org.apache.openjpa.jdbc.meta.MappingTool;
import org.apache.openjpa.lib.util.Options;

/**
 * Helpers to set-up connections, for Tests.
 * 
 * @see CreateTestDerbyDB
 * 
 * @author Michael Vorburger (MVO)
 */
// TODO This may work now, with Maven changes? - "This is a Test Util; and I'd like it to be in src/test/java.  However, dependant projects can then not find it, as it won't be in the JAR.  How to resolve this?"
// TODO Rename this... this may be more appropriately named JPATestsHelper, or something like that?
public final class CreateDB {
	private static final Logger logger = Logger.getLogger(CreateDB.class);

	public static final String CONNECTION_URL = "openjpa.ConnectionURL";
	public static final String CONNECTION_DRIVER_NAME = "openjpa.ConnectionDriverName";
	public static final String CONNECTION_USER_NAME = "openjpa.ConnectionUserName";
	public static final String CONNECTION_PASSWORD = "openjpa.ConnectionPassword";
	
	/**
	 * Database Update Tool. Runs DDL for 
	 * persistent Entities found on the classpath (described by a
	 * META-INF/persistence.xml on the classpath) on Database.
	 * 
	 * <p>
	 * The current OpenJPA implementation of course simply calls the mappingtool
	 * with the right options.
	 * </p>
	 * 
	 * You might want/need to run the {@link #dropAllForeignKeyConstraintsFromSybase(EntityManager)} 
	 * before calling this?  If you do so from a Constructor of a Test Case (which is typical),
	 * you need to put a setUp(); & tearDown() around it to get an EM.  E.g. like this:
	 * <code>
	 *     setUp();
	 *     CreateDB.dropAllForeignKeyConstraintsFromSybase(em);
	 *     tearDown();
	 *     CreateDB.synchronizeDatabase(connectionProperties);
	 * </code>
	 */
	// TODO Do we need this?!  Unit tests use getTemporaryDerbyDatabaseProperties, which uses SynchronizeMappings.
	// But what about Unit Tests running on Sybase (of which I have some).  I don't want to hard-code SynchronizeMappings. 
	public static boolean synchronizeDatabase(final Properties dbConnectionProperties) throws IOException, SQLException {
		JDBCConfiguration jdbcConf = new JDBCConfigurationImpl();		
		
		Options opts = new Options();
		opts.putAll(dbConnectionProperties);
		fixPropertiesForStupidOpenJPAMappingTool(opts);
		
		final String[] args2 = opts.setFromCmdLine(new String[] {
				// "-schemaAction", "dropDB,build", "-openjpaTables true", "-foreignKeys true" });
				"-schemaAction", "dropDB,add", "-openjpaTables true", "-foreignKeys true", "-sql target/create.ddl" });

		try {
			return MappingTool.run(jdbcConf, args2, opts);
		} finally {
			jdbcConf.close();
		}
	}

	/**
	 * The MappingTool seems to expect no 'openjpa.' prefix, 
	 * but the Persistence.createEntityManagerFactory seems to it,
	 * so this removes the 'openjpa.' prefix.
	 *  
	 * @param openJPAProperties
	 */
	/* package-local */
	@SuppressWarnings("unchecked")
	static void fixPropertiesForStupidOpenJPAMappingTool(final Properties openJPAProperties) {
		Properties additionalProperties = new Properties();
		Iterator it = openJPAProperties.entrySet().iterator();
		while (it.hasNext()) {
			// TODO Fix this Generics warning here...
			Entry<String, String> entry = (Entry<String, String>) it.next();
			if (entry.getKey().startsWith("openjpa.")) {
				additionalProperties.put(entry.getKey().substring("openjpa.".length()), entry.getValue());
			}
		}
		openJPAProperties.putAll(additionalProperties);
	}
	
	/**
	 * Drop all constraints from all tables in schema (Sybase specific).
	 * 
	 * @see #deleteTemporaryDerbyDatabase() for an alternative when running on Derby for tests
	 * @see #synchronizeDatabase(Properties) for some usage hints.
	 * 
	 * @author Bruno Venditti (SQL), Michael Vorburger (Java method)
	 */
	@SuppressWarnings("unchecked")
	public static void dropAllForeignKeyConstraintsFromSybase(EntityManager em) {
		// Could do this in pure JDBC as well, nothing JPA here really, but hey, while we're at JPA-ing, let's do it like this:
		Query statementsQuery = em.createNativeQuery("select 'alter table '+object_name(tableid)+' drop constraint '+object_name(constrid) from sysconstraints", String.class);
		List<String> statementsList = statementsQuery.getResultList();
		for (String statement : statementsList) {
			logger.info("dropAllForeignKeyConstraintsFromSybase(EntityManager) - " + statement);
			Query alterTableDropConstraintStatementQuery = em.createNativeQuery(statement);
			alterTableDropConstraintStatementQuery.executeUpdate();
		}
	}
	
	/**
	 * Drop all constraints from all tables in schema (Sybase specific).
	 * 
	 * @author Bruno Venditti (SQL), Michael Vorburger (Java method)
	 */
	@SuppressWarnings("unchecked")
	public static void dropAllViewFromSybase(EntityManager em) {
		//and not(t0.name like 'sys%') to avoid drop of system view like sysquerymetrics 
		Query statementsQuery = em.createNativeQuery("select 'drop view '+t0.name FROM sysobjects t0 where t0.type='V' and not(t0.name like 'sys%')", String.class);
		List<String> statementsList = statementsQuery.getResultList();
		for (String statement : statementsList) {
			logger.info("dropAllViewFromSybase(EntityManager) - " + statement);
			Query dropViewStatementQuery = em.createNativeQuery(statement);
			dropViewStatementQuery.executeUpdate();
		}
	}
	
	/**
	 * @deprecated Use {@link BootstrapJPA#getPropertiesFromClasspathResource(String)} instead now please.
	 */
	// TODO Remove this @deprecated method?
	public static Properties getPropertiesFromClasspathResource(String propertiesResourceName) throws IOException {
		return BootstrapJPA.getPropertiesFromClasspathResource(propertiesResourceName);
	}
	
	// Logging for OpenJPA should now go through Log4j for both JUnit tests as
	// well as App Server, Tests & Production.
	// @see BootstrapJPA#forceLog4jLogging.
	// TODO Can/should these helpers here actuallly be removed?
	
	public static void addFullTraceLoggingProperties(Properties p) {
		p.put("openjpa.Log", "DefaultLevel=TRACE");
		p.put("openjpa.ConnectionFactoryProperties", "PrettyPrint=true, PrettyPrintLineLength=100");
	}

	public static void addNormalLoggingProperties(Properties p) {
		p.put("openjpa.Log", "DefaultLevel=INFO, Runtime=INFO, Tool=INFO, JDBC=INFO, SQL=TRACE, Query=TRACE, Schema=TRACE, DataCache=TRACE");
		p.put("openjpa.ConnectionFactoryProperties", "PrettyPrint=true, PrettyPrintLineLength=100");
	}
	
	public static void addNoTraceLoggingProperties(Properties p) {
		p.put("openjpa.Log", "DefaultLevel=INFO, Runtime=INFO, Tool=INFO, JDBC=INFO, SQL=INFO, Query=INFO, Schema=INFO, DataCache=INFO");
		p.put("openjpa.ConnectionFactoryProperties", "PrettyPrint=true, PrettyPrintLineLength=100");
	}

	private CreateDB() {
	}
	
//	/**
//	 * @param args
//	 * @throws SQLException
//	 * @throws IOException
//	 */
//	public static void main(String[] args) throws Exception {
//		// MappingTool.main(new String[] {
//		// "-schemaAction", "dropDB,build",
//		// "-openjpaTables true", "-foreignKeys true"});
//
//		// MappingTool.main(new String[] {"-properties", "derby.properties",
//		// "-schemaAction", "dropDB,build",
//		// "-openjpaTables true", "-foreignKeys true"});
//		
//		synchronizeDatabase(getTemporaryDerbyDatabaseProperties());
//	}
}
