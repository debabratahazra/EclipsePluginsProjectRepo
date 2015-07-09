package com.odcgroup.otf.jpa.internal.openjpa;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.apache.openjpa.jdbc.kernel.JDBCFetchConfiguration;
import org.apache.openjpa.jdbc.sql.SQLBuffer;
import org.apache.openjpa.persistence.otfjpa.QueryAbortException;

import com.odcgroup.otf.jpa.exception.OtfJPAInitializationException;

/**
 * Helper to get the SQL which a JPA Query operation would have generated,
 * without actually executing it.
 * 
 * <p>
 * Currently this is only built and tested for
 * <code>query.getResultList()</code>. If later needed, this helper could very
 * easily be extended to support e.g. <code>query.getSingleResult()</code>, and
 * also <code>entityManager.find(klass, id)</code>, now that the required
 * infrastructure is available.
 * </p>
 * 
 * @see com.odcgroup.otf.jpa.test.SQLAccessTest in jpa-test module
 * 
 * @author Michael Vorburger (MVO)
 * @since 11.12.2009
 */
public final class QuerySQLAccessHelper {
	private QuerySQLAccessHelper() {
	}

	/**
	 * Build your javax.persistence.Query normally, but then instead of invoking
	 * <code>List results = query.getResultList()</code>, use
	 * <code>String sql = QuerySQLAccessHelper.getQuerySQL(query)</code>.
	 * 
	 * Returned list often will be only one String, but could several statements
	 * if parallel eager fetching is used, see chapter "Eager Fetching" in
	 * OpenJPA documentation. TODO: Actually support this internally, for now
	 * just the API signature is there (which is important), the implementation
	 * for more than one returned SQL is incomplete and untested.)
	 * 
	 * @param query JPA Query
	 * @return SQL SELECT as Strings, with Parameters
	 */
	public static List<String> getResultListQuerySQL(Query query) {
		QuerySQLAccessHintObject ho = new QuerySQLAccessHintObject();
		ho.keepSQL = true;
		ho.abortQuery = true;
		query.setHint(QuerySQLAccessHintObject.HINT_NAME, ho);

		try {
			query.getResultList();
			throw new OtfJPAInitializationException(
					"QuerySQLAccessHelper internal problem; why did we return from query.getResultList() ?  Our QueryAbortException thrown inside the DBDictionary should have prevented this!");

		} catch (QueryAbortException qae) {
			// OK, as expected
		}

		query.setHint(QuerySQLAccessHintObject.HINT_NAME, null);

		List<String> result = new ArrayList<String>(1);
		result.add(ho.sql);
		return result;
	}

	/*
	 * package-local, only called from within OdysseyCustom*Dictionary, NOT user
	 * code
	 */
	static void processHints(SQLBuffer r, JDBCFetchConfiguration fetch) {
		if (fetch == null)
			return;
		
		Object hintObject = fetch.getHint(QuerySQLAccessHintObject.HINT_NAME);
		QuerySQLAccessHintObject saqho = (QuerySQLAccessHintObject) hintObject;

		if (saqho == null)
			return;
		
		if (saqho.keepSQL)
			saqho.sql = r.getSQL(true);
		if (saqho.abortQuery)
			throw new QueryAbortException();
	}

	/**
	 * A "User Object" associated with an OpenJPA Query to "pass-through"
	 * information from user code (set via the QuerySQLAccessHelper), into a
	 * DBDictionary (read via the QuerySQLAccessHelper again).
	 */
	private static class QuerySQLAccessHintObject {

		// NOTE: MUST start with "openjpa.hint.". else
		// org.apache.openjpa.persistence.QueryImpl#setHint(String key, Object
		// value) ignores or rejects it!
		private static final String HINT_PREFIX = "openjpa.hint.";

		private static final String HINT_NAME = HINT_PREFIX + QuerySQLAccessHintObject.class.getName();

		private boolean keepSQL;
		private boolean abortQuery;

		private String sql;
	}

}
