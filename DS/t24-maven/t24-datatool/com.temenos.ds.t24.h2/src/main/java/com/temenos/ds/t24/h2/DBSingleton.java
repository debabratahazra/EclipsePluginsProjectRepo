package com.temenos.ds.t24.h2;

import java.sql.SQLException;

/**
 * Singleton with static for DB.
 *
 * @author Michael Vorburger
 */
public class DBSingleton {

	private static DB db;

	public static void startIfNotAlreadyRunning(String directory) throws SQLException {
		if (db == null) {
			// TODO auto port selection 0 needs further thought - since I intro. that this entire class doesn't really make sense anymore, does it?
			db = new DB(directory, 0).start();
		} else {
			if (!db.getDirectory().getPath().equals(directory))
				throw new IllegalStateException("There is already a DB running, but with a different path!");
		}
	}

}
