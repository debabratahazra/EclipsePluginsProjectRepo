package com.odcgroup.aaa.connector.tests;

import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;

import com.odcgroup.otf.jpa.JPAConstants;
import com.odcgroup.otf.jpa.test.openjpa.RememberingAllStatementJDBCListenerHelper;

/**
 * TODO: Document me!
 *
 * @author yan
 *
 */
public abstract class JUnit4TstJPA {

	protected static Properties connectionProperties;

	/**
	 * Default persistenceUnitName is "mdfmml", then one used by the MML code generator
	 * This is important because the otf-jpa module has it's own persistence.xml for tests,
	 * and test subclasses of this helper shouldn't use that by mistake.
	 * Can of course be set to another PU by subclass, if required.
	 */
	protected static String persistenceUnitName = JPAConstants.MDF_GENERATED_PERSISTENCE_UNIT_NAME;
	
	private static EntityManagerFactory emf;
	protected EntityManager em;
	
	public JUnit4TstJPA() {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		if (emf != null) {
			emf.close();
			emf = null;
		}
	}
	
	@Before
	public void setUp() throws Exception {
		em = getEMF().createEntityManager();
		em.getTransaction().begin();
		RememberingAllStatementJDBCListenerHelper.resetSQLQueries(em);
	}

	protected static EntityManagerFactory getEMF() throws Error, Exception {
        if (emf == null) {
			if (connectionProperties == null) {
				throw new Error("Subclass needs to set connectionProperties in constructor");
			}
			if (persistenceUnitName == null) {
				throw new Error("Subclass needs to set the persistenceUnitName in constructor (should never be null; safer)");
			}
			RememberingAllStatementJDBCListenerHelper.registerListener(connectionProperties);
	        
	        // TODO Remove this again later... @see http://rd.oams.com/browse/OCS-28741
	        connectionProperties.setProperty("openjpa.jdbc.QuerySQLCache", "false");
	        
			emf = Persistence.createEntityManagerFactory(persistenceUnitName, connectionProperties);
			
			// emf could still be null here, e.g. if the persistence-unit name (null for now) was not found
			if (emf == null) {
				throw new Exception("Persistence.createEntityManagerFactory() returned null, maybe the persistenceUnitName could not be found? persistenceUnitName=" + persistenceUnitName);
			}
		}
        return emf;
    }

	@After
	public void tearDown() throws Exception {
		if (em != null) {
			// TODO This sort of stuff should later be done by a Spring thingie, not by me here..
			EntityTransaction tx = em.getTransaction();
			if (tx.isActive()) {
				if (!tx.getRollbackOnly()) {
					tx.commit();
				} else {
					tx.rollback();
				}
			}
			em.close();
		}
		else {
			System.err.println("TestJPA.tearDown() found em == null, but I'm not throwing an exception so that the original cause exception is not getting lost");
		}
	}

}
