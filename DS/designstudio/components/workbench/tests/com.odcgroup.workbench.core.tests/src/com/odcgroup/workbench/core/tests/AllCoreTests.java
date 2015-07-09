package com.odcgroup.workbench.core.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.odcgroup.template.TemplateCreatorTest;
import com.odcgroup.template.TemplateDescriptorTest;
import com.odcgroup.workbench.core.helper.FeatureSwitchesTest;
import com.odcgroup.workbench.core.init.OfsProjectInitializerTest;
import com.odcgroup.workbench.core.internal.adapter.ResourceAdapterFactoryTest;
import com.odcgroup.workbench.core.repository.MavenDependencyHelperTest;
import com.odcgroup.workbench.core.repository.ModelURIConverterTest;
import com.odcgroup.workbench.core.repository.OfsResourceHelperTest;
import com.odcgroup.workbench.core.resources.OfsModelPackageTest;
import com.odcgroup.workbench.core.resources.OfsProjectTest;

@RunWith(Suite.class)
@SuiteClasses( { 
	TemplateCreatorTest.class,
	TemplateDescriptorTest.class,
	OfsProjectInitializerTest.class,
	ResourceAdapterFactoryTest.class,
	MavenDependencyHelperTest.class,
	ModelURIConverterTest.class,
	OfsResourceHelperTest.class,
	OfsModelPackageTest.class,
	OfsProjectTest.class,
	OfsCoreCreateTest.class,
	OfsCoreReadTest.class,
	StringHelperTest.class,
	UnitTestInfrastructureTest.class,
	ZipFileHelperTest.class,
	FeatureSwitchesTest.class,
	DSLoggingTest.class
})
public class AllCoreTests { }
