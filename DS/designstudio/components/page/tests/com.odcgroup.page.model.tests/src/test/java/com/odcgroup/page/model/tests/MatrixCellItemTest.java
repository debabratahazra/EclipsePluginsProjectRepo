package com.odcgroup.page.model.tests;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.odcgroup.page.metamodel.MetaModel;
import com.odcgroup.page.metamodel.PropertyTemplate;
import com.odcgroup.page.metamodel.WidgetLibrary;
import com.odcgroup.page.metamodel.WidgetLibraryConstants;
import com.odcgroup.page.metamodel.WidgetTemplate;
import com.odcgroup.page.metamodel.util.MetaModelRegistry;

/**
 * 
 *
 * @author pkk
 *
 */
public class MatrixCellItemTest {
	
	/**
	 * DS-3632
	 */
	@Test
	public void testDs3632AggregationByDefault() {
		// test matrixContentCellItem
		runDefaultAggregationTypeTest("MatrixContentCellItem", 3);
		// test matrixExtraColumnItem
		runDefaultAggregationTypeTest("MatrixExtraColumnItem", 5);
	}
	
	/**
	 * @param widgetTempType
	 * @param numProp
	 */
	private void runDefaultAggregationTypeTest(String widgetTempType, int numProp) {
		MetaModel metamodel = MetaModelRegistry.getMetaModel();
		WidgetLibrary library = metamodel.findWidgetLibrary(WidgetLibraryConstants.XGUI);
		WidgetTemplate template = library.findWidgetTemplate(widgetTempType);
		Assert.assertNotNull("The widget tempate is not found in library xgui", template);
		List<PropertyTemplate> pTemplates = template.getPropertyTemplates();
		Assert.assertTrue("expected property templates is not matching", pTemplates.size() == numProp);
		String reqType = "aggregationType";
		PropertyTemplate aggreTemp = null;
		for (PropertyTemplate temp : pTemplates) {
			if(reqType.equals(temp.getType().getName())) {
				aggreTemp = temp;
				break;
			}
		}
		String defaultAggr = "sum";
		Assert.assertNotNull("AggregateType property template not found", aggreTemp);	
		Assert.assertTrue("Default value is not sum", aggreTemp.getValue().equals(defaultAggr));
	}

}
