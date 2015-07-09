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

/**
 * DS-4204
 *
 */
public class ModifiableIdVerifyFormatTest extends AbstractDSPageTransformationUnitTest {
		String generatedXmlString;

	@Before
	public void setUpBefore() throws ModelNotFoundException, IOException, InvalidMetamodelVersionException {
		createModelsProject();
		copyResourceInModelsProject("module/Default/module/DS4204_ModifiableIdFormat.module");
		generatedXmlString = createXmlFromUri(URI
				.createURI("resource:///Default/module/DS4204_ModifiableIdFormat.module"));
	}
	
	@After
	public void tearDown() {
		deleteModelsProjects();
	}
	
	@Test
	public void testComboBoxHasCorrectFormat() throws Exception {
		String xpath = "/xsp:page[1]/xgui:module[1]/nav:managerequest[1]/xgui:vbox[1]/xgui:combobox/@id";
		String result = executeXpathOnXML(xpath, generatedXmlString);
		Assert.assertTrue("ComboBox id does not contain correct id attribute", "DS4204_ComboBox".equals(result));
	}
		
	@Test
	public void testListIdHasCorrectFormat() throws Exception {
		String xpath = "/xsp:page[1]/xgui:module[1]/nav:managerequest[1]/xgui:vbox[1]/xgui:list[1]/@id";
		String result = executeXpathOnXML(xpath, generatedXmlString);
		Assert.assertTrue("List id does not contain correct id attribute", "DS4204_List".equals(result));
	}
		
	@Test
	public void testCheckBoxIdHasCorrectFormat() throws Exception {
		String xpath = "/xsp:page[1]/xgui:module[1]/nav:managerequest[1]/xgui:vbox[1]/xgui:checkbox[1]/@id";
		String result = executeXpathOnXML(xpath, generatedXmlString);
		Assert.assertTrue("Checkbox id does not contain correct id attribute", "DS4204_Checkbox".equals(result));
	}
		
		
}
