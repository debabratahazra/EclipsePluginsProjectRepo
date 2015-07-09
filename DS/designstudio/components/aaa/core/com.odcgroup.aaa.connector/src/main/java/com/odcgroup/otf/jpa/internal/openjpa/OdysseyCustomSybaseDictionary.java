package com.odcgroup.otf.jpa.internal.openjpa;

import java.sql.Connection;
import java.util.Arrays;

import org.apache.openjpa.jdbc.kernel.JDBCFetchConfiguration;
import org.apache.openjpa.jdbc.schema.ForeignKey;
import org.apache.openjpa.jdbc.schema.Table;
import org.apache.openjpa.jdbc.sql.SQLBuffer;
import org.apache.openjpa.jdbc.sql.Select;
import org.apache.openjpa.jdbc.sql.SybaseDictionary;

import com.odcgroup.otf.jpa.utils.CreateDB;

/**
 * An OpenJPA SybaseDictionary (a DBDictionary) with some patches.
 * 
 * @see CreateDB#fixDBDictionary
 * 
 * @author Michael Vorburger (MVO)
 */
public class OdysseyCustomSybaseDictionary extends SybaseDictionary {

	/**
	 * Sets createIdentityColumn = false.
	 * We won't do pessimistic locking, so this extra Sybase specific column
	 * that OpenJPA generates for Sybase by default (see JavaDoc of  SybaseDictionary)
	 * is not needed.
	 * 
	 * @see SybaseDictionary
	 */
	@SuppressWarnings("unchecked")
	public OdysseyCustomSybaseDictionary() {
		super();
		createIdentityColumn = false;
		
		storeCharsAsNumbers = false;
		fixedSizeTypeNameSet.addAll(Arrays.asList(new String[]{ "CHAR" }));
		
		// TODO This maxTableNameLength needs to be set back to 30 again! Once SQL name override with @PersistentCollection(elementEmbedded = true) is solved, or model is manually written so that all are <30
		maxTableNameLength = 50;
	    maxColumnNameLength = 30;
	    maxIndexNameLength = 30;
	}

    @Override
    public String getValidColumnName(String name, Table table) {
        return super.getValidColumnName(name, table).toLowerCase();
    }

    /**
     * Use e.g. <code>query.setHint("openjpa.FetchPlan.Isolation", IsolationLevel.READ_UNCOMMITTED)</code>
     * to have OpenJPA append "AT ISOLATION READ UNCOMMITTED" to SELECT statements from queries.
     * 
     * Inspired by {@link org.apache.openjpa.jdbc.sql.DB2Dictionary#getForUpdateClause}.
     * @see org.apache.openjpa.jdbc.sql.DBDictionary#getForUpdateClause(org.apache.openjpa.jdbc.kernel.JDBCFetchConfiguration, boolean, org.apache.openjpa.jdbc.sql.Select) 
     */
	@Override
	protected String getForUpdateClause(JDBCFetchConfiguration fetch, boolean isForUpdate, Select sel) {
		// TODO Not sure about this... in DB2Dictionary & DBDictionary... 
		// this only works with a pessimistic lock manager with
		// q.setHint("openjpa.FetchPlan.ReadLockMode", LockModeType.WRITE)
		// @see org.apache.openjpa.jdbc.sql.SelectImpl#isForUpdate(JDBCStore store, int lockLevel)
		//
		// But for now I'm deliberately ALWAYS checking and appending this, 
		// if on Sybase we ALWAYS want '...at isolation...', remove this later?
		// if (!isForUpdate) {
		//		return null;
		// }
		
		/*
		 * TODO a) The DB2 OpenJPA code, and some bla bla I found online, seem to
		 * indicate that (on some databases) this may not work with ORDER BY
		 * (supportsLockingWithOrderClause)? Should I ignore that - will work as
		 * you want it on Sybase? b) Similarly, if I understand the existing
		 * code in OpenJPA about this which I looked at right, I think on DB2
		 * even if it's configured to pessimistic, IFF the Query (SELECT) has an
		 * Aggregate or Grouping, it wouldn't do FOR UPDDATE WITH UR/CS/RS/RR
		 * (SelectImpl.isForUpdate). Quid about Sybase? c) Lastly, still in the
		 * DB2, there is something about a "OPTIMIZE FOR" (which sounds like the
		 * DB2 equivalent of 'TOP' ?) which is usually at the end but with a
		 * 'FOR UPDATE' it has to be before. On Sybase, will we still do a TOP
		 * with OpenJPA, or do Cursors replace this? If there has to be a TOP,
		 * would AT ISOLATION come before or after?
		 */

		
		int isolationLevel;
		if (fetch != null && fetch.getIsolation() != -1)
            isolationLevel = fetch.getIsolation();
        else
            isolationLevel = conf.getTransactionIsolationConstant();

		switch(isolationLevel) {
		case -1:
			return null;
		case Connection.TRANSACTION_NONE:
			return null;
		case Connection.TRANSACTION_READ_UNCOMMITTED:
			return "AT ISOLATION READ UNCOMMITTED";
		case Connection.TRANSACTION_READ_COMMITTED:
			return "AT ISOLATION READ COMMITTED";
		case Connection.TRANSACTION_REPEATABLE_READ:
			// TODO Not supported on Sybase, or "AT ISOLATION REPEATABLE READ" ?
			return super.getForUpdateClause(fetch, isForUpdate, sel);
		case Connection.TRANSACTION_SERIALIZABLE:
			return "AT ISOLATION SERIALIZABLE";
		default:
			return null;
		}
	}

	
	// Statement batching IS enabled but not here in the Dictionary, but in the BootstrapJPA class

	// TODO Review below; OpenJPA v1.2.0 fixes this?  See https://issues.apache.org/jira/browse/OPENJPA-642
//	
	/**
	 * Do NOT override this method - the generic version in the DBDictionary parent DOES seem to work on Sybase!
	 * What is not supported on Sybase is CASCADE stuff, so don't do <property name="openjpa.jdbc.MappingDefaults"
	 * value="ForeignKeyDeleteAction=cascade,JoinForeignKeyDeleteAction=cascade" />, but just basic 
	 * value="ForeignKeyDeleteAction=restrict,JoinForeignKeyDeleteAction=restrict" /> appears fine!
	 */ 
	@Override
	public String[] getAddForeignKeySQL(ForeignKey fk) {
		// return super.super.getAddForeignKeySQL(fk);
		// return DBDictionary.getAddForeignKeySQL(fk);

		// Copy/pasted from DBDictionary:
		String fkSQL = getForeignKeyConstraintSQL(fk);
		if (fkSQL == null)
			return new String[0];
		return new String[] { "ALTER TABLE "
				+ getFullName(fk.getTable(), false) + " ADD " + fkSQL };
	}
	
	
	//---------------------------------------------------------------
	// SQL Trapping
	// See the OdysseyCustomDerbyDictionary
	
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
