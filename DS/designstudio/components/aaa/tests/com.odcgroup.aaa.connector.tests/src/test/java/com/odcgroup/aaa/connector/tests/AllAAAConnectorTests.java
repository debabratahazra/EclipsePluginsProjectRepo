package com.odcgroup.aaa.connector.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.odcgroup.aaa.connector.domainmodel.DictEntityTest;
import com.odcgroup.aaa.connector.formats.FormatDAOTest;
import com.odcgroup.aaa.connector.mdfmml.AAAWidgetRulesCheckerTest;
import com.odcgroup.aaa.connector.mdfmml.Format2DatasetTest;
import com.odcgroup.aaa.connector.mdfmml.MetaDict2MmlNoDBTest;
import com.odcgroup.aaa.connector.mdfmml.MetaDict2MmlTest;
import com.odcgroup.aaa.connector.mdfmml.NamingHelperTest;
import com.odcgroup.aaa.connector.metadictreader.MetaDictDAOTest;
import com.odcgroup.aaa.connector.nls.LabelsDAOTest;
import com.odcgroup.aaa.connector.util.ProcNameFunctionFormatterTest;
import com.odcgroup.aaa.connector.util.TADatabaseConnectionTest;


/**
 * All aaa-connector tests agregated in a test suite
 * @author yan
 */
@RunWith(Suite.class)
@SuiteClasses( {
	DictEntityTest.class,
	FormatDAOTest.class,
	Format2DatasetTest.class,
	MetaDict2MmlTest.class,
	MetaDict2MmlNoDBTest.class,
	NamingHelperTest.class,
	MetaDictDAOTest.class,
	LabelsDAOTest.class,
	TADatabaseConnectionTest.class,
	AAAWidgetRulesCheckerTest.class,
	ProcNameFunctionFormatterTest.class
})
public class AllAAAConnectorTests {
}
