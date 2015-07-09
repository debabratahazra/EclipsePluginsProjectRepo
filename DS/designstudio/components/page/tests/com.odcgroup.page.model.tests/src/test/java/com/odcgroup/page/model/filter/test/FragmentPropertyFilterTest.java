package com.odcgroup.page.model.filter.test;

import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.model.Model;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.filter.FragmentPropertyFilter;
import com.odcgroup.workbench.core.IOfsModelResource;
import com.odcgroup.workbench.core.tests.util.AbstractDSUnitTest;

/**
 * test class for the fragment widget property filter
 * @author snn
 *
 */
public class FragmentPropertyFilterTest extends AbstractDSUnitTest{
	
	@Before
	public void setUp() throws Exception {
		createModelsProject();
	}
	
	@After
	public void tearDown() throws Exception {
		deleteModelsProjects();
	}

	/*DS-4682
	 * test to filter the cardinality and default fragment properties if the fragment is xtooltip type
	 */
	@Test
	public void testXtooltipTypeFragmentPropertyFilter() {
		copyResourceInModelsProject("fragment/ds4947/DS4947DataSetValidationFragment.fragment");
		FragmentPropertyFilter fragmentFilter = new FragmentPropertyFilter();
		Model model = null;
		URI uri = URI
				.createURI("resource:///ds4947/DS4947DataSetValidationFragment.fragment");
		try {
			IOfsModelResource ofsResource = getOfsProject()
					.getOfsModelResource(uri);
			model = (Model) ofsResource.getEMFModel().get(0);
		} catch (Exception ex) {
			ex.printStackTrace();
			Assert.fail("Cannot load the model from the resource:///DS4947DataSetValidationFragment.fragmen");
		}

		Widget fragment = model.getWidget();
		List<Property> filterList = fragmentFilter.filter(fragment);
		EList<Property> widgteProperties = fragment.getProperties();
		int filtersize = filterList.size();
		int widgtePropertiessize = widgteProperties.size();
		// size of the properties
		Assert.assertTrue(widgtePropertiessize > filtersize);
		for (Property pro : widgteProperties) {
			if (pro.getTypeName().equals(PropertyTypeConstants.CARDINALITY)) {
				Assert.assertFalse(filterList.contains(pro));
				Assert.assertEquals("one", pro.getValue());
			}
			if (pro.getTypeName()
					.equals(PropertyTypeConstants.DEFAULT_FRAGMENT)) {
				Assert.assertFalse(filterList.contains(pro));
				Assert.assertFalse(pro.getBooleanValue());
			}
		}

	}
}
