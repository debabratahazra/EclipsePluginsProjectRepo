package com.odcgroup.otf.jpa.test.openjpa;

import java.util.ArrayList;
import java.util.List;

import org.apache.openjpa.lib.jdbc.AbstractJDBCListener;
import org.apache.openjpa.lib.jdbc.JDBCEvent;


/**
 * An OpenJPA JDBCListener which stores all the executed SQL.
 * 
 * This is infrastructure for unit tests only, it is NOT thread-safe for
 * multiple concurrent EntityManager usage (there is only one global
 * RememberingAllStatementJDBCListener for everything). 
 *
 * @see RememberingAllStatementJDBCListenerHelper
 * 
 * @author Michael Vorburger (initial version, one statement only)
 * @author Thomas Ebert (extension, several previous statements)
 */
public class RememberingAllStatementJDBCListener extends AbstractJDBCListener {

    private List<String> sqlList = new ArrayList<String>();

    @Override
    protected void eventOccurred(JDBCEvent event) {
        if (event.getSQL() != null && event.getType() == JDBCEvent.AFTER_EXECUTE_STATEMENT) {
            this.sqlList.add(event.getSQL());
        }
    }

    /**
	 * Get the last SQL
	 * 
	 * @return The SQL String
	 */
    public String getLastSQL() {
        return this.getSQL(0);
    }
    
    /**
	 * Get the "age" previous SQL
	 * 
	 * @param age
	 *            The number of SQL to go back
	 * @return The SQL String
	 */
    public String getSQL(int age) {
        return this.sqlList.get(this.sqlList.size() -1 - age);
    }
    
    public void reset() {
        this.sqlList.clear();
    }

    /**
     * Return the number of queries produced
     * @return the number of queries produced
     */
    public int nbSQLQueries() {
    	return sqlList.size();
    }
}
