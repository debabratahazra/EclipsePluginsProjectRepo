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

public class PanelIdVerifyFormatTest extends AbstractDSPageTransformationUnitTest {

	String generatedXmlString;

	@Before
	public void setUpBefore() throws ModelNotFoundException, IOException, InvalidMetamodelVersionException {
		createModelsProject();
		copyResourceInModelsProject("module/Default/module/DS4204_PanelIdFormat.module");
		generatedXmlString = createXmlFromUri(URI
				.createURI("resource:///Default/module/DS4204_PanelIdFormat.module"));
	}
	
	@After
	public void tearDown() {
		deleteModelsProjects();
	}

	@Test
	public void testTabIdHasCorrectFormat() throws Exception {
		String xpath = "/xsp:page[1]/xgui:module[1]/nav:managerequest[1]/xgui:vbox[1]/tab:tab-view[1]/xgui:tabbed-pane[1]/xgui:tab[1]/@id";
		String result = executeXpathOnXML(xpath, generatedXmlString);
		Assert.assertTrue("Tab id is in an invalid format", result.matches("tab_[A-Za-z0-9_]*"));
	}

	@Test
	public void testTabbedPaneIdHasCorrectFormat() throws Exception {
		String xpath = "/xsp:page[1]/xgui:module[1]/nav:managerequest[1]/xgui:vbox[1]/tab:tab-view[1]/xgui:tabbed-pane[1]/@id";
		String result = executeXpathOnXML(xpath, generatedXmlString);
		Assert.assertTrue("TabbedPane id is in an invalid format", result.matches("[A-Za-z0-9_]*"));
		/* Tab ID has a constraint of being 5 characters long. */
		Assert.assertTrue("TabbedPane has an id longer than 5 characters", result.length()== 5);
	}


}
