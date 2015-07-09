package com.odcgroup.page.transformmodel.tests.widget;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.URI;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.model.Model;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.transformmodel.tests.AbstractDSPageTransformationUnitTest;
import com.odcgroup.workbench.core.IOfsModelResource;
import com.odcgroup.workbench.core.InvalidMetamodelVersionException;
import com.odcgroup.workbench.core.repository.ModelNotFoundException;

/**
 * @author yan
 */
public class SearchFieldTest extends AbstractDSPageTransformationUnitTest {

	@Before
	public void setUp() {
		createModelsProject();
		copyResourceInModelsProject("domain/ds4467/DS4467.domain"); 
		copyResourceInModelsProject("fragment/widgets/searchField/DS3774.fragment");
		copyResourceInModelsProject("fragment/widgets/searchField/DS3982.fragment");
		copyResourceInModelsProject("fragment/widgets/searchField/DS3982_Container.fragment");
		copyResourceInModelsProject("fragment/widgets/searchField/DS4467.fragment");
		copyResourceInModelsProject("fragment/widgets/searchField/DS6543_SearchOnly.fragment");
		copyResourceInModelsProject("fragment/widgets/searchField/DS6543_AutoComplete.fragment");
	}
	
	@After
	public void tearDown() {
		deleteModelsProjects();
	}
	
	@Test
	public void testDS3374XspGenerationForSearchField() throws ModelNotFoundException, IOException, InvalidMetamodelVersionException, CoreException, SAXException, ParserConfigurationException {
		assertTransformation(
				URI.createURI("resource:///widgets/searchField/DS3774.fragment"), 
				readFileFromClasspath("/com/odcgroup/page/transformmodel/tests/widget/ExpectedSearchField.xml"));
	}

	@Test
	public void testDS3982BeanPrefixOnSearchField() throws ModelNotFoundException, IOException, InvalidMetamodelVersionException, CoreException, SAXException, ParserConfigurationException {
		assertTransformation(
				URI.createURI("resource:///widgets/searchField/DS3982_Container.fragment"), 
				readFileFromClasspath("/com/odcgroup/page/transformmodel/tests/widget/ExpectedSearchField_DS3982.xml"));
	}
	
	@Test
	public void testDS4467SearchFieldEvents() throws ModelNotFoundException, IOException, InvalidMetamodelVersionException, CoreException, SAXException, ParserConfigurationException {
		assertTransformation(
				URI.createURI("resource:///widgets/searchField/DS4467.fragment"), 
				readFileFromClasspath("/com/odcgroup/page/transformmodel/tests/widget/DS4467ExpectedResult2.xml"));
	}
 /*
  * DS-4954 test for the tabindex property in Searchfield widget
  */
	@Test
	public void testDS4954TabIndexPropetyInSearchField(){
		
		URI uri=URI.createURI("resource:///widgets/searchField/DS3774.fragment");
		Model searchmodel = null;
		try {
			IOfsModelResource ofsResource = getOfsProject()
					.getOfsModelResource(uri);
			searchmodel = (Model) ofsResource.getEMFModel().get(0);
		
		} catch (Exception ex) {
			ex.printStackTrace();
			Assert.fail("Cannot load the model from theresource:///widgets/searchField/DS3774.fragment");
		}
		
		Widget fragment=searchmodel.getWidget();
		Assert.assertNotNull(fragment);
		Widget box=fragment.getContents().get(0);
		Widget searchField=box.getContents().get(0);
		Property tabIndex=searchField.findProperty(PropertyTypeConstants.TAB_INDEX);
		Assert.assertNotNull(tabIndex);
		Assert.assertEquals(0, tabIndex.getIntValue());
		
		}
	
	/*
	 * DS-6543 test for the Provide access to history of items property in Searchfield widget
	 */
	@Test
	public void testDS6543AccessHistoryItemsPropetyInSearchField() {

		URI uriSearchOnly = URI
				.createURI("resource:///widgets/searchField/DS6543_SearchOnly.fragment");
		URI uriAutoComplete = URI
				.createURI("resource:///widgets/searchField/DS6543_AutoComplete.fragment");
		Model searchOnlyModel = null;
		Model autoCompleteModel = null;
		try {
			IOfsModelResource ofsResourceSearchOnly = getOfsProject()
					.getOfsModelResource(uriSearchOnly);
			IOfsModelResource ofsResourceAutoComplete = getOfsProject()
					.getOfsModelResource(uriAutoComplete);
			searchOnlyModel = (Model) ofsResourceSearchOnly.getEMFModel()
					.get(0);
			autoCompleteModel = (Model) ofsResourceAutoComplete.getEMFModel()
					.get(0);

		} catch (Exception ex) {
			ex.printStackTrace();
			Assert.fail("Cannot load the model from the resource:///widgets/searchField/DS6543_SearchOnly.fragment");
			Assert.fail("Cannot load the model from the resource:///widgets/searchField/DS6543_AutoComplete.fragment");
		}

		Widget fragment = searchOnlyModel.getWidget();
		Assert.assertNotNull(fragment);
		Widget box = fragment.getContents().get(0);
		Widget searchField = box.getContents().get(0);
		Property searchType = searchField
				.findProperty(PropertyTypeConstants.SEARCH_TYPE);
		Assert.assertNotNull(searchType);
		Assert.assertEquals("searchOnly", searchType.getValue());
		Property accessHistoryItems = searchField
				.findProperty(PropertyTypeConstants.ACCESS_HISTORY_ITEMS);
		Assert.assertNotNull(accessHistoryItems);
		Assert.assertEquals(false, accessHistoryItems.getBooleanValue());

		fragment = autoCompleteModel.getWidget();
		Assert.assertNotNull(fragment);
		box = fragment.getContents().get(0);
		searchField = box.getContents().get(0);
		searchType = searchField
				.findProperty(PropertyTypeConstants.SEARCH_TYPE);
		Assert.assertNotNull(searchType);
		Assert.assertEquals("autoCompleteOnly", searchType.getValue());
		accessHistoryItems = searchField
				.findProperty(PropertyTypeConstants.ACCESS_HISTORY_ITEMS);
		Assert.assertNotNull(accessHistoryItems);
		Assert.assertFalse(accessHistoryItems.isReadonly());
		Assert.assertEquals(true, accessHistoryItems.getBooleanValue());

	}

}
