package com.odcgroup.service.gen.t24.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.odcgroup.service.gen.t24.internal.cartridges.cpp.CppServiceAPIGeneratorTest;
import com.odcgroup.service.gen.t24.internal.cartridges.cpp.CppServiceDataGeneratorTest;
import com.odcgroup.service.gen.t24.internal.cartridges.cpp.CppServiceProxyGeneratorTest;
import com.odcgroup.service.gen.t24.internal.categories.dotnet.api.DotNetServiceAPIGeneratorTest;
import com.odcgroup.service.gen.t24.internal.categories.dotnet.api.DotNetServiceAPIWSGeneratorTest;
import com.odcgroup.service.gen.t24.internal.categories.dotnet.api.DotNetServiceDataGeneratorTest;
import com.odcgroup.service.gen.t24.internal.categories.java.webservice.JavaComponentSprintContextGeneratorTest;
import com.odcgroup.service.gen.t24.internal.categories.java.webservice.JavaWSApplicationContextXMLGeneratorTest;
import com.odcgroup.service.gen.t24.internal.categories.java.webservice.JavaWSComponentSprintInitGeneratorTest;
import com.odcgroup.service.gen.t24.internal.categories.java.webservice.JavaWSServiceXMLGeneratorTest;
import com.odcgroup.service.gen.t24.internal.categories.java.webservice.JavaWebServiceGeneratorTest;
import com.odcgroup.service.gen.t24.internal.categories.java.webservice.JavaWebServiceSecEnabledGeneratorTest;
import com.odcgroup.service.gen.t24.internal.data.AttributeDescriptorTest;
import com.odcgroup.service.gen.t24.internal.data.ClassDefDescriptorTest;
import com.odcgroup.service.gen.t24.internal.data.ParamDescriptorTest;
import com.odcgroup.service.gen.t24.internal.generator.BasicApiGeneratorTest;
import com.odcgroup.service.gen.t24.internal.generator.BasicApiGeneratorWithStereoTypeTest;
import com.odcgroup.service.gen.t24.internal.generator.BasicImplementationGeneratorTest;
import com.odcgroup.service.gen.t24.internal.generator.FrameworkGeneratorTest;
import com.odcgroup.service.gen.t24.internal.generator.JBCGetCustomizedTypeSchemaGeneratorTest;
import com.odcgroup.service.gen.t24.internal.generator.JBCGetCustomizedTypeStructureGeneratorTest;
import com.odcgroup.service.gen.t24.internal.generator.JBCGetMetaDataAPIGeneratorTest;
import com.odcgroup.service.gen.t24.internal.generator.JavaDataClassConverterGeneratorTest;
import com.odcgroup.service.gen.t24.internal.generator.JavaDataClassGeneratorTest;
import com.odcgroup.service.gen.t24.internal.generator.JavaServiceAPIGeneratorTest;
import com.odcgroup.service.gen.t24.internal.generator.JavaServiceEJBGeneratorTest;
import com.odcgroup.service.gen.t24.internal.generator.JavaServiceGeneratorTest;
import com.odcgroup.service.gen.t24.internal.generator.JavaServiceImplGeneratorTest;
import com.odcgroup.service.gen.t24.internal.generator.JavaServiceImplTAFCGeneratorTest;
import com.odcgroup.service.gen.t24.internal.generator.JavaServiceOperationResponseClassGeneratorTest;
import com.odcgroup.service.gen.t24.internal.generator.ProxyAdaptorGeneratorTest;
import com.odcgroup.service.gen.t24.internal.generator.TAFJProxyAdaptorInterfaceTest;
import com.odcgroup.service.gen.t24.internal.generator.TemplateOutsideJarLoaderTest;

@RunWith(Suite.class)
@SuiteClasses( {
	AttributeDescriptorTest.class,
	ClassDefDescriptorTest.class,
	ParamDescriptorTest.class,
	BasicApiGeneratorTest.class,
	BasicApiGeneratorWithStereoTypeTest.class,
	BasicImplementationGeneratorTest.class,
	FrameworkGeneratorTest.class,
	JavaDataClassConverterGeneratorTest.class,
	JavaDataClassGeneratorTest.class,
	JavaServiceAPIGeneratorTest.class,
	JavaServiceEJBGeneratorTest.class,
	JavaServiceGeneratorTest.class,
	JavaServiceImplGeneratorTest.class,
	JavaServiceImplTAFCGeneratorTest.class,
	JavaServiceOperationResponseClassGeneratorTest.class,
	ProxyAdaptorGeneratorTest.class,
	TAFJProxyAdaptorInterfaceTest.class,
	TemplateOutsideJarLoaderTest.class,
	CppServiceAPIGeneratorTest.class,
	CppServiceDataGeneratorTest.class,
	CppServiceProxyGeneratorTest.class,
	DotNetServiceDataGeneratorTest.class,
	DotNetServiceAPIGeneratorTest.class,
	DotNetServiceAPIWSGeneratorTest.class,
	JBCGetMetaDataAPIGeneratorTest.class,
	JBCGetCustomizedTypeSchemaGeneratorTest.class,
	JBCGetCustomizedTypeStructureGeneratorTest.class,
	JavaComponentSprintContextGeneratorTest.class,
	JavaWebServiceGeneratorTest.class,
	JavaWebServiceSecEnabledGeneratorTest.class,
	JavaWSApplicationContextXMLGeneratorTest.class,
	JavaWSComponentSprintInitGeneratorTest.class,
	JavaWSServiceXMLGeneratorTest.class,
} )
public class AllT24ServiceGenerationTests {
}
