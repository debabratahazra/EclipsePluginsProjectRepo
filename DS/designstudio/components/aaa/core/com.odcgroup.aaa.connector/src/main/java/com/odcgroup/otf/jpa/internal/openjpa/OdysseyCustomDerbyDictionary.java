package com.odcgroup.otf.jpa.internal.openjpa;

import org.apache.openjpa.jdbc.kernel.JDBCFetchConfiguration;
import org.apache.openjpa.jdbc.sql.DerbyDictionary;
import org.apache.openjpa.jdbc.sql.SQLBuffer;
import org.apache.openjpa.jdbc.sql.Select;

import com.odcgroup.otf.jpa.utils.CreateDB;

/**
 * An OpenJPA SybaseDictionary (a DBDictionary) with some extensions.
 * 
 * Currently only difference between this one and the standard DerbyDictionary is "SQL trapping".
 * 
 * @see CreateDB#fixDBDictionary
 * 
 * @author Michael Vorburger (MVO)
 */
public class OdysseyCustomDerbyDictionary extends DerbyDictionary {

	//---------------------------------------------------------------
	// SQL Trapping
	// Not sure if all of the following slightly different toSelect()
	// methods need this, but better be safe than sorry... Only one
	// of them is called for one given query (verified).
	
	@Override
	public SQLBuffer toSelect(SQLBuffer selects, JDBCFetchConfiguration fetch, SQLBuffer from, SQLBuffer where,
			SQLBuffer group, SQLBuffer having, SQLBuffer order, boolean distinct, boolean forUpdate, long start,
			long end, boolean subselect, boolean checkTableForUpdate) {
		
		SQLBuffer sql = super.toSelect(selects, fetch, from, where, group, having, order, distinct, forUpdate, start, end, subselect, checkTableForUpdate);
		QuerySQLAccessHelper.processHints(sql, fetch);
		return sql;
	}

	@Override
	public SQLBuffer toSelect(SQLBuffer selects, JDBCFetchConfiguration fetch, SQLBuffer from, SQLBuffer where,
			SQLBuffer group, SQLBuffer having, SQLBuffer order, boolean distinct, boolean forUpdate, long start,
			long end, boolean subselect) {
		
		SQLBuffer sql = super.toSelect(selects, fetch, from, where, group, having, order, distinct, forUpdate, start, end, subselect);
		QuerySQLAccessHelper.processHints(sql, fetch);
		return sql;
	}

	@Override
	public SQLBuffer toSelect(SQLBuffer selects, JDBCFetchConfiguration fetch, SQLBuffer from, SQLBuffer where,
			SQLBuffer group, SQLBuffer having, SQLBuffer order, boolean distinct, boolean forUpdate, long start,
			long end, Select sel) {
		
		SQLBuffer sql = super.toSelect(selects, fetch, from, where, group, having, order, distinct, forUpdate, start, end, sel);
		QuerySQLAccessHelper.processHints(sql, fetch);
		return sql;
	}

	@Override
	public SQLBuffer toSelect(SQLBuffer selects, JDBCFetchConfiguration fetch, SQLBuffer from, SQLBuffer where,
			SQLBuffer group, SQLBuffer having, SQLBuffer order, boolean distinct, boolean forUpdate, long start,
			long end) {

		SQLBuffer sql = super.toSelect(selects, fetch, from, where, group, having, order, distinct, forUpdate, start, end);
		QuerySQLAccessHelper.processHints(sql, fetch);
		return sql;
	}
}
