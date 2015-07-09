package com.odcgroup.workbench.generation.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.odcgroup.workbench.generation.init.CompilerOptsTest;
import com.odcgroup.workbench.generation.init.CopyStdGenPomTest;
import com.odcgroup.workbench.generation.init.classpath.OutputPathCheckerTest;
import com.odcgroup.workbench.generation.tests.ng.tests.ExampleGeneratorTest;

@RunWith(Suite.class)
@SuiteClasses( { 
	GenerationCoreCreateTest.class,
	GenerationCoreReadTest.class,
	GeneratorsProperlySetupTest.class,
	OutputPathCheckerTest.class,
	CheckForDuplicatePathInitializerTest.class,
	CompilerOptsTest.class,
	CopyStdGenPomTest.class,
	ExampleGeneratorTest.class
})
public class AllGenerationTests { }
