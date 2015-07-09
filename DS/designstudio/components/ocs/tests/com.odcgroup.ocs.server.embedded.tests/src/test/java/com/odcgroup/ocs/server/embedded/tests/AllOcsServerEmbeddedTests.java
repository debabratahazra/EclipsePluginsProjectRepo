package com.odcgroup.ocs.server.embedded.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.odcgroup.ocs.server.embedded.model.internal.EmbeddedServerTest;
import com.odcgroup.ocs.server.embedded.nature.EmbeddedServerNatureTest;
import com.odcgroup.ocs.server.embedded.util.EmbeddedServerManagerTest;

@RunWith(Suite.class)
@SuiteClasses( {
	EmbeddedServerTest.class,
	EmbeddedServerNatureTest.class,
	EmbeddedServerManagerTest.class,
} )
public class AllOcsServerEmbeddedTests {

}
