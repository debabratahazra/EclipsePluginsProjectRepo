package com.odcgroup.page.event.transformation;

import org.eclipse.emf.common.util.URI;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.odcgroup.workbench.core.IOfsModelResource;
import com.odcgroup.workbench.core.repository.ModelNotFoundException;
import com.odcgroup.workbench.core.tests.util.AbstractDSUnitTest;

/**
 * @author yan
 */
public class PageflowTransformerTest extends AbstractDSUnitTest {

	private static final String PAGEFLOW = "ds3742/Ds3742.pageflow";
	
	@Before
	public void setUp() {
		createModelsProject();
		copyResourceInModelsProject("pageflow/" + PAGEFLOW);
	}
	
	@After
	public void tearDown() {
		deleteModelsProjects();
	}
	
	@Test
	public void testDS3742newUrlForPageFlow() throws ModelNotFoundException {
		PageflowTransformer transformer = new PageflowTransformer();
		IOfsModelResource resource = getOfsProject().getOfsModelResource(URI.createURI("resource:///" + PAGEFLOW));
		String pageflowUrl = transformer.transform(resource);
		Assert.assertEquals("The pageflow url is wrong", "/wui/activity/aaa/pageflow/ds3742-pageflow-Ds3742/Ds3742", pageflowUrl);
	}

}
