package com.odcgroup.page.model.tests.translation;

import org.junit.Assert;
import org.junit.Test;

import com.odcgroup.page.metamodel.MetaModel;
import com.odcgroup.page.metamodel.WidgetLibrary;
import com.odcgroup.page.metamodel.WidgetType;
import com.odcgroup.page.metamodel.util.MetaModelRegistry;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.util.WidgetFactory;

/**
 * @author atr
 */
public class WidgetTranslationModelTest {
	
	@Test
	public void testWidgetWithTranslationSupport() {

		final String TID = "tid";
		WidgetFactory wFactory = new WidgetFactory();
		
		MetaModel mm = MetaModelRegistry.getMetaModel();
		for (WidgetLibrary library : mm.getWidgetLibraries()) {
			for (WidgetType type : library.getWidgetTypes()) {
				Widget widget = wFactory.create(type);
				Property tid = widget.findProperty(TID);
				if (type.translationSupported()) {
					Assert.assertTrue("Widget ["+type.getName()+"] with translation support must have the property 'tid'", tid != null);
					// check tid can be changed
					widget.setPropertyValue(TID, "123");
					String strTID = widget.getPropertyValue(TID);
					Assert.assertTrue("Widget ["+type.getName()+"], Cannot retrieve the rigth TID value'", "123".equals(strTID));
					long longTID = widget.findProperty(TID).getLongValue();
					Assert.assertTrue("Widget ["+type.getName()+"], Cannot retrieve the rigth TID long value'", 123L == longTID);
				} else {
					Assert.assertTrue("Widget ["+type.getName()+"] with no translation support must not have the property 'tid'", null == tid);
				}
			}
			
		}
	}
	
}
