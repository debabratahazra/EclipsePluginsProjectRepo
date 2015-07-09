package org.apache.openjpa.persistence.otfjpa;

import org.apache.openjpa.persistence.NoResultException;

import com.odcgroup.otf.jpa.internal.openjpa.QuerySQLAccessHelper;

/**
 * QuerySQLAccessHelper internal Exception thrown to "return" from DBDictionary,
 * via processHints() above. Again, this is INTERNAL to the QuerySQLAccessHelper
 * and should never by thrown or caught by any other code. Forget it exists!
 * 
 * It extends NoResultException and unfortunately has to be in a sub-package of
 * org.apache.openjpa.persistence because of how OpenJPA's PersistenceExceptions
 * RuntimeExceptionTranslator works.
 * 
 * @see QuerySQLAccessHelper
 * @see com.odcgroup.otf.jpa.test.SQLAccessTest in jpa-test module
 * 
 * @author Michael Vorburger (MVO)
 */
@SuppressWarnings("serial")
public class QueryAbortException extends NoResultException {

	public QueryAbortException() {
		super("See " + QueryAbortException.class.getName() + " JavaDoc", null, null, false);
	}
}