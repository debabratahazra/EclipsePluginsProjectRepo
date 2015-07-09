package com.odcgroup.page.model.tests;

import org.apache.commons.lang.StringUtils;
import org.junit.Assert;
import org.junit.Test;

import com.odcgroup.page.metamodel.MetaModel;
import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.metamodel.WidgetType;
import com.odcgroup.page.metamodel.WidgetTypeConstants;
import com.odcgroup.page.metamodel.util.MetaModelRegistry;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.util.UniqueIdGenerator;
import com.odcgroup.page.model.util.WidgetFactory;

/**
 * @author atr
 */
public class UniqueIdGeneratorTest {

	@Test
	public void testWidgetIdGeneration() {
		
		UniqueIdGenerator.generateIdForWidgets.remove(WidgetTypeConstants.MODULE);
		UniqueIdGenerator.generateIdForWidgets.remove(WidgetTypeConstants.CDM_TABLE);
		
		MetaModel mm = MetaModelRegistry.getMetaModel();

		for (String typeName : UniqueIdGenerator.generateIdForWidgets) {
		
			WidgetType wt = mm.findWidgetType("xgui", typeName);
			Assert.assertTrue("The widget type "+typeName+ " is not found in library xgui", wt != null);
			
			WidgetFactory wf = new WidgetFactory();
			Widget widget = wf.create(wt);

			Property idProp = widget.findProperty(PropertyTypeConstants.ID);
			Assert.assertTrue("The widget type "+typeName+ "must have the ID property", idProp != null);
			
			String id = widget.getID();
			if (typeName.equals(WidgetTypeConstants.BOX) || typeName.equals(WidgetTypeConstants.LABEL) || typeName.equals(WidgetTypeConstants.LABEL_DOMAIN)) {
				// for those widget, id id exist but must be initialized with the empty string.
				// reason: this id is never written in the XSP
				Assert.assertTrue("The HTML ID for the widget "+typeName + "must be the empty string", id.length() == 0);
			} else {
				Assert.assertTrue("The HTML ID for the widget "+wt.getName() + "is not generated", StringUtils.isNotBlank(id));
				Assert.assertEquals("The HTML ID should be the same", id,idProp.getValue());
			}
		}		
		
	}
	
}
