package com.odcgroup.page.model.util.tests;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.odcgroup.page.metamodel.MetaModel;
import com.odcgroup.page.metamodel.WidgetType;
import com.odcgroup.page.metamodel.util.MetaModelRegistry;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.util.WidgetFactory;

public class ModifiableIdVerifyFormat {
	
	private final static String ID_PROPERTY = "id";
	private MetaModel metaModel;
	private WidgetFactory widgetFactory;
	
	@Before
	public void setUp() throws Exception {
		metaModel = MetaModelRegistry.getMetaModel();
		widgetFactory = new WidgetFactory();
	}
	
	@Test
	public void testCheckBoxShouldHaveIdCorrectFormat() throws Exception {
		WidgetType wType = metaModel.findWidgetType("xgui", "CheckBox");
		Widget w = widgetFactory.create(wType);
		String idString = w.getPropertyValue(ID_PROPERTY);
		Assert.assertTrue(idString.matches("btn_[A-Za-z0-9_]*"));
		
	}
	
	@Test
	public void testComboBoxShouldHaveIdCorrectFormat() throws Exception {
		WidgetType wType = metaModel.findWidgetType("xgui", "ComboBox");
		Widget w = widgetFactory.create(wType);
		String idString = w.getPropertyValue(ID_PROPERTY);
		Assert.assertTrue(idString.matches("[A-Za-z0-9_]*"));
		Assert.assertTrue(idString.length()== 5);
	}
	
	@Test
	public void testListShouldHaveIdCorrectFormat() throws Exception {
		WidgetType wType = metaModel.findWidgetType("xgui", "List");
		Widget w = widgetFactory.create(wType);
		String idString = w.getPropertyValue(ID_PROPERTY);
		Assert.assertTrue(idString.matches("[A-Za-z0-9_]*"));
		Assert.assertTrue(idString.length()== 5);
	}
	
	@Test
	public void testRadioButtonShouldHaveIdCorrectFormat() throws Exception {
		WidgetType wType = metaModel.findWidgetType("xgui", "RadioButton");
		Widget w = widgetFactory.create(wType);
		String idString = w.getPropertyValue(ID_PROPERTY);
		Assert.assertTrue(idString.matches("btn_[A-Za-z0-9_]*"));
	}
}
