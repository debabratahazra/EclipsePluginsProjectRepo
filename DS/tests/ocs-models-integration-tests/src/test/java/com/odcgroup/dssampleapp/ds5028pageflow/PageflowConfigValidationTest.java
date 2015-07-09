package com.odcgroup.dssampleapp.ds5028pageflow;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import com.odcgroup.otf.utils.tests.OtfTestCase;
import com.odcgroup.otf.utils.tests.OtfTestUtils;
import com.odcgroup.uif.pageflow.engine.config.PageflowConfigService;

public class PageflowConfigValidationTest extends OtfTestCase {

	public PageflowConfigValidationTest(String name) {
		super(name);
	}

	@Test
	public void testLoadAllPageflowsFoundOnClasspath() throws Exception {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:META-INF/spring/wui/applicationContext-wui-pageflow.xml");
		assertNotNull(ctx);
		PageflowConfigService configService = (PageflowConfigService) ctx.getBean(PageflowConfigService.SERVICE_ID);

		String locationPattern = "classpath*:/META-INF/config/*pageflow*.xml";
		ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
		Resource[] resources = resourcePatternResolver.getResources(locationPattern);
		assertTrue(resources.length > 0);
		
		Resource resource1 = resources[0];
		String resource1URL = resource1.getURL().toExternalForm();
		OtfTestUtils.setRelativeConfigFolder(getClass(), getLogger(), resource1URL.substring(resource1URL.indexOf("!") + 1));
		
		// CHECKSTYLE:OFF
		int passed = 0;
		int failed = 0;
		for (Resource resource : resources) {
			if (resource.getFilename().equals("wui-scripted-pageflow-config.xml"))
				// This actually is NOT a pageflow
				continue;
			
			System.out.println("\n\nFound & going to load PF " + resource + "\n");
			try {
				configService.getConfigUnit(resource.getFilename(), false);
				++passed;
			} catch (Exception e) {
				System.out.println("FAILED Pageflow: " + resource + ", because: " + e.toString());
				++failed;
			}
		}
		System.out.println(passed + failed + " Pageflows tested; passed = " + passed + ", failed = " + failed);
		// CHECKSTYLE:ON
		assertTrue("Some Pageflows failed validation", failed == 0);
	}
}
