package com.odcgroup.t24.external.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.odcgroup.t24.external.model.internal.TestExternalT24Server;
import com.odcgroup.t24.external.model.internal.TestExternalT24ServerPasswordEncoderDecoder;

@RunWith(Suite.class)
@SuiteClasses( {
	TestExternalT24Server.class,
	TestExternalT24ServerPasswordEncoderDecoder.class
} )public class AllT24ExternalTests {
}
