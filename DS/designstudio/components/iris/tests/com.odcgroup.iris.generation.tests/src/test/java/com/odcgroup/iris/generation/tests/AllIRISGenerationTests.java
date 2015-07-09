package com.odcgroup.iris.generation.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.odcgroup.iris.generator.IRISAAMetadataGenerationTests;
import com.odcgroup.iris.metadata.generation.tests.IRISJavaGenerationTests;
import com.odcgroup.iris.metadata.generation.tests.IRISMetadataGenerator2Tests;
import com.odcgroup.iris.metadata.generation.tests.RODBIRISMetadataGeneratorTests;
import com.odcgroup.iris.metadata.generation.tests.RimReferencesTest;

/**
 * @author phanikumark
 */
@RunWith(Suite.class)
@SuiteClasses( {
	IRISMetadataGenerator2Tests.class,
	IRISAAMetadataGenerationTests.class,
	IRISJavaGenerationTests.class,
	RimReferencesTest.class,
	RODBIRISMetadataGeneratorTests.class
} )
public class AllIRISGenerationTests {

}
