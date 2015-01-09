package design_pattern.mainclass;

import java.sql.Connection;

import design_pattern.internal.JDBCConnectionPool;

public class MainObjectPool {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		// Create the ConnectionPool:
	    JDBCConnectionPool pool = new JDBCConnectionPool(
	      "org.hsqldb.jdbcDriver", "jdbc:hsqldb://localhost/mydb",
	      "sa", "secret");

	    // Get a connection:
	    Connection con = pool.checkOut();

	    // Use the connection
	   /*
	    * Do some work
	    */

	    // Return the connection:
	    pool.checkIn(con);
	}

}
