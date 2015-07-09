package com.odcgroup.otf.jpa.test.openjpa;

import java.util.Properties;

import javax.persistence.EntityManager;

import org.apache.openjpa.conf.OpenJPAConfiguration;
import org.apache.openjpa.jdbc.conf.JDBCConfiguration;
import org.apache.openjpa.lib.jdbc.JDBCListener;
import org.apache.openjpa.persistence.OpenJPAEntityManager;
import org.apache.openjpa.persistence.OpenJPAEntityManagerSPI;
import org.junit.Assert;

import com.odcgroup.otf.jpa.spring.SpringOpenJPAHelper;

/**
 * Helper for tests to verify the SQL which OpenJPA executed.
 * 
 * This is infrastructure for unit tests only, it is NOT thread-safe for
 * multiple concurrent EntityManager usage (there is only one global
 * RememberingAllStatementJDBCListener for everything).  Every test
 * using this should start with <code>RememberingAllStatementJDBCListenerHelper.resetSQLQueries(em);</code> 
 * 
 * @author yan
 * @author Michael Vorburger
 */
public class RememberingAllStatementJDBCListenerHelper {

	/**
	 * Must be called before creating the EntityManager
	 * @param connectionProperties
	 */
	public static void registerListener(Properties connectionProperties) {
		connectionProperties.setProperty("openjpa.jdbc.JDBCListeners", RememberingAllStatementJDBCListener.class.getName());		
	}
	
    /**
	 * Get the latest SQL
	 * 
	 * @return The SQL String
	 */
    public static String getLastSQL(EntityManager em) {
        // We *HAVE* to flush() at this point, otherwise the Listener won't (yet) have seen the Statement(s)
        em.flush();
        return getRememberingAllStatementJDBCListener(em).getLastSQL();
    }

    /**
	 * Get a "age" previous SQL
	 * 
	 * @param age
	 *            The number of SQL to go back
	 * @return The SQl String
	 */
    public static String getSQL(EntityManager em, int age) {
        // We *HAVE* to flush() at this point, otherwise the Listener won't (yet) have seen the Statement(s)
        em.flush();
        return getRememberingAllStatementJDBCListener(em).getSQL(age);
    }
    
    public static int countSQLQueries(EntityManager em) {
        // We *HAVE* to flush() at this point, otherwise the Listener won't (yet) have seen the Statement(s)
        em.flush();
    	return getRememberingAllStatementJDBCListener(em).nbSQLQueries();
    }
    
    public static void resetSQLQueries(EntityManager em) {
    	getRememberingAllStatementJDBCListener(em).reset();
    }
    
	private static RememberingAllStatementJDBCListener getRememberingAllStatementJDBCListener(EntityManager em) {
		RememberingAllStatementJDBCListener rememberingAllStatementJDBCListener = null;
        
        OpenJPAEntityManager oem = SpringOpenJPAHelper.cast(em);
        OpenJPAEntityManagerSPI oems = (OpenJPAEntityManagerSPI) oem;
        OpenJPAConfiguration configuration = oems.getConfiguration();
        JDBCConfiguration jdbcConfiguration = (JDBCConfiguration)configuration;
        JDBCListener[] jdbcListeners = jdbcConfiguration.getJDBCListenerInstances();
        for (JDBCListener listener : jdbcListeners) {
            if (listener instanceof RememberingAllStatementJDBCListener) {
                rememberingAllStatementJDBCListener = (RememberingAllStatementJDBCListener) listener;                
            }
            
        }
        if (rememberingAllStatementJDBCListener == null) {
            // System.err.println("rememberingAllStatementJDBCListener not set up correctly?!");
            Assert.fail("rememberingAllStatementJDBCListener not set up correctly?!");
        }
        
		return rememberingAllStatementJDBCListener;
	}
	
}
