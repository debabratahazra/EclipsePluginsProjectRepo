package com.odcgroup.page.transformmodel.tests.widget.id;

import org.eclipse.emf.common.util.URI;
import org.junit.Assert;
import org.junit.Test;

import com.odcgroup.page.transformmodel.tests.AbstractDSPageTransformationUnitTest;

/**
 * DS-4204
 */
public class EditableIdVerifyFormatTest extends AbstractDSPageTransformationUnitTest {
	String generatedXmlString;

	public EditableIdVerifyFormatTest() {
		setUpBefore(); 
	}

	private void setUpBefore() {
		createModelsProject();
		copyResourceInModelsProject("module/Default/module/DS4204_EditableIdFormat.module");
		try {
			generatedXmlString = createXmlFromUri(URI
					.createURI("resource:///Default/module/DS4204_EditableIdFormat.module"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	@Test
	public void testAutoCompleteIdHasCorrectFormat() throws Exception {
		String xpath = "/xsp:page[1]/xgui:module[1]/nav:managerequest[1]/xgui:vbox[1]/xgui:autocomplete/@id";
		String result = executeXpathOnXML(xpath, generatedXmlString);
		Assert.assertTrue("Autocomplete id does not contain correct id attribute", "DS4204_AutoComplete".equals(result));
	}
	

	
	@Test
	public void testCalDateFieldIdHasCorrectFormat() throws Exception {
		String xpath = "/xsp:page[1]/xgui:module[1]/nav:managerequest[1]/xgui:vbox[1]/xgui:caldatefield/@id";
		String result = executeXpathOnXML(xpath, generatedXmlString);
		Assert.assertTrue("CalDateField id does not contain correct id attribute", "DS4204_CalDate".equals(result));
	}
	
	
	@Test
	public void testFileChooserIdHasCorrectFormat() throws Exception {
		String xpath = "/xsp:page[1]/xgui:module[1]/nav:managerequest[1]/xgui:vbox[1]/xgui:filechooser/@id";
		String result = executeXpathOnXML(xpath, generatedXmlString);
		Assert.assertTrue("Filechooser id does not contain correct id attribute", "DS4204_FileChooser".equals(result));
	}
	
	
	@Test
	public void testPasswordFieldCompleteIdHasCorrectFormat() throws Exception {
		String xpath = "/xsp:page[1]/xgui:module[1]/nav:managerequest[1]/xgui:vbox[1]/xgui:pwdfield/@id";
		String result = executeXpathOnXML(xpath, generatedXmlString);
		Assert.assertTrue("PasswordField id does not contain correct id attribute", "DS4204_PasswordField".equals(result));
	}
	
	
	@Test
	public void testSearchFieldIdHasCorrectFormat() throws Exception {
		String xpath = "/xsp:page[1]/xgui:module[1]/nav:managerequest[1]/xgui:vbox[1]/xgui:searchfield/@id";
		String result = executeXpathOnXML(xpath, generatedXmlString);
		Assert.assertTrue("SearchField id does not contain correct id attribute", "DS4204_SearchArea".equals(result));
	}
	
	
	@Test
	public void testTextAreaIdHasCorrectFormat() throws Exception {
		String xpath = "/xsp:page[1]/xgui:module[1]/nav:managerequest[1]/xgui:vbox[1]/xgui:textarea /@id";
		String result = executeXpathOnXML(xpath, generatedXmlString);
		Assert.assertTrue("TextArea id does not contain correct id attribute", "DS4204_TextArea".equals(result));
	}
	
	
	@Test
	public void testTextFieldIdHasCorrectFormat() throws Exception {
		String xpath = "/xsp:page[1]/xgui:module[1]/nav:managerequest[1]/xgui:vbox[1]/xgui:textfield/@id";
		String result = executeXpathOnXML(xpath, generatedXmlString);
		Assert.assertTrue("TextArea id does not contain correct id attribute", "DS4204_TextField".equals(result));
	}
}
