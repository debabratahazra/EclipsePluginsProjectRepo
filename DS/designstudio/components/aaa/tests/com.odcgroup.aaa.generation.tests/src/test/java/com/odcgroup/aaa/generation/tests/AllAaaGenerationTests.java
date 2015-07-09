package com.odcgroup.aaa.generation.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.odcgroup.aaa.generation.gateway.line.ATTTest;
import com.odcgroup.aaa.generation.gateway.line.CMDTest;
import com.odcgroup.aaa.generation.gateway.line.DATTest;
import com.odcgroup.aaa.generation.gateway.validator.GatewayValidatorTest;
import com.odcgroup.aaa.generation.gateway.writer.GeneratorTest;
import com.odcgroup.aaa.generation.udentities.UDEntitiesCodeGeneratorMappingsTest;
@RunWith(Suite.class)
@SuiteClasses( {
	ATTTest.class,
	CMDTest.class,
	DATTest.class,
	GatewayValidatorTest.class,
	GeneratorTest.class,
	UDEntitiesCodeGeneratorMappingsTest.class,
})
public class AllAaaGenerationTests {

}
