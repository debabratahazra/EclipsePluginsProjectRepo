package com.odcgroup.page.transformmodel.tests.jira;

import java.io.IOException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.URI;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

import com.odcgroup.page.metamodel.WidgetTypeConstants;
import com.odcgroup.page.model.Event;
import com.odcgroup.page.model.Model;
import com.odcgroup.page.model.Parameter;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.transformmodel.tests.AbstractDSPageTransformationUnitTest;
import com.odcgroup.page.transformmodel.util.TransformUtils;
import com.odcgroup.workbench.core.IOfsModelResource;
import com.odcgroup.workbench.core.InvalidMetamodelVersionException;
import com.odcgroup.workbench.core.repository.ModelNotFoundException;

public class DS4746EventGenerationTest extends
		AbstractDSPageTransformationUnitTest {

	private static final String DOMAIN_MODEL = "domain/ds4405/DS4405.domain";
	private static final String FRAG_MODEL = "fragment/ds4746/Ds4746EventGen.fragment";
	private static final String MODULE_MODEL = "module/Default/module/DS4467Auto.module";

	@Before
	public void setUp() {
		createModelsProject();
		copyResourceInModelsProject(DOMAIN_MODEL, MODULE_MODEL, FRAG_MODEL);
	}

	@After
	public void tearDown() {
		deleteModelsProjects();
	}
	
	/**
	 * @throws ModelNotFoundException
	 * @throws IOException
	 * @throws InvalidMetamodelVersionException
	 * @throws CoreException
	 * @throws SAXException
	 * @throws ParserConfigurationException
	 */
	@Test
	public void testDS4924AutoCompleteSearchEventTransformation() 
			throws ModelNotFoundException, IOException,
			InvalidMetamodelVersionException, CoreException, SAXException,
			ParserConfigurationException {

		assertTransformation(
				URI.createURI("resource:///ds4746/Ds4746EventGen.fragment"),
				readFileFromClasspath("/com/odcgroup/page/transformmodel/tests/widget/DS4924ExpectedResult.xml"));
	}

	/**
	 * @throws ModelNotFoundException
	 * @throws IOException
	 * @throws InvalidMetamodelVersionException
	 * @throws CoreException
	 * @throws SAXException
	 * @throws ParserConfigurationException
	 */
	@Test
	public void testDS3561EnumerationBasedRatioButtonTransformation()
			throws ModelNotFoundException, IOException,
			InvalidMetamodelVersionException, CoreException, SAXException,
			ParserConfigurationException {
		
		URI modelUri = URI.createURI("resource:///ds4746/Ds4746EventGen.fragment");
		String EXPECTED_PARAM_VAL = "flow-action=search;flow-change=contextReload";
		
		IOfsModelResource resource = getOfsProject().getOfsModelResource(modelUri);

		Model model = (Model)resource.getEMFModel().iterator().next();
		Widget root = model.getWidget();
		List<Widget> children = root.getContents();
		Assert.assertTrue(children.size() == 1);
		Widget box = children.get(0);
		Assert.assertTrue(WidgetTypeConstants.BOX.equals(box.getTypeName()));
		children = box.getContents();
		Assert.assertTrue(children.size() == 1);
		Widget search =  children.get(0);
		Assert.assertTrue(WidgetTypeConstants.SEARCH_FIELD.equals(search.getTypeName()));
		List<Event> events = search.getEvents();
		Assert.assertTrue(events.size() == 5);
		Event event = events.get(4);
		Assert.assertTrue("Search".equals(event.getEventName()));
		Parameter param = event.findParameter("param");
		Assert.assertNotNull(param);
		Assert.assertTrue(EXPECTED_PARAM_VAL.equals(param.getValue()));	

		// do transform
		TransformUtils.transformWidgetToXmlString(getProject(), root);
		
		// Assert.assert again
		Assert.assertTrue(EXPECTED_PARAM_VAL.equals(param.getValue()));
		
		
	}

}
