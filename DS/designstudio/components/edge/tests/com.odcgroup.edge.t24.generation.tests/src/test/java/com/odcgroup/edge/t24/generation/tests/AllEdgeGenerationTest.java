package com.odcgroup.edge.t24.generation.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.odcgroup.edge.t24.generation.EdgeEnquiryMapperTest;
import com.odcgroup.edge.t24.generation.FundsTransferEdgeVersionMapperTest;
import com.odcgroup.edge.t24.generation.ResourcesUtilTest;
import com.odcgroup.edge.t24.generation.TellerLcyCashinDenomFieldTest;
import com.odcgroup.edge.t24.generation.TranslationTest;


/**
 * @author phanikumark
 */
@RunWith(Suite.class)
@SuiteClasses( {
	ResourcesUtilTest.class,
	EdgeEnquiryMapperTest.class,
	FundsTransferEdgeVersionMapperTest.class,
	TellerLcyCashinDenomFieldTest.class,
	T24EdgeGeneratorTest.class,
	TranslationTest.class
} )
public class AllEdgeGenerationTest {
}
