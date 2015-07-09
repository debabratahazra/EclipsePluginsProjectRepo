package com.odcgroup.page.transformmodel.tests.widget.id;

import java.io.IOException;

import org.eclipse.emf.common.util.URI;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.odcgroup.page.transformmodel.tests.AbstractDSPageTransformationUnitTest;
import com.odcgroup.workbench.core.InvalidMetamodelVersionException;
import com.odcgroup.workbench.core.repository.ModelNotFoundException;

public class ActionsIdVerifyFormatTest extends AbstractDSPageTransformationUnitTest {
	String generatedXmlString;

	@Before
	public void setUpBefore() throws ModelNotFoundException, IOException, InvalidMetamodelVersionException {
		createModelsProject();
		copyResourceInModelsProject("module/Default/module/DS4204_ActionIdFormat.module");
		generatedXmlString = createXmlFromUri(URI
					.createURI("resource:///Default/module/DS4204_ActionIdFormat.module"));
	}
	
	@After
	public void tearDown() {
		deleteModelsProjects();
	}
	
	@Test
	public void testButtonHasCorrectFormat() throws Exception {
		String xpath = "/xsp:page[1]/xgui:module[1]/nav:managerequest[1]/xgui:vbox[1]/xgui:button/@id";
		String result = executeXpathOnXML(xpath, generatedXmlString);
		Assert.assertTrue(result.matches("btn_[A-Za-z0-9_]*"));
	}
	
}
