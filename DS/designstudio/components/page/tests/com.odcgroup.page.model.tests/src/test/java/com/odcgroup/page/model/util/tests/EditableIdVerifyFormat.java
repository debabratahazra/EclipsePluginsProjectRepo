package com.odcgroup.page.model.util.tests;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.odcgroup.page.metamodel.MetaModel;
import com.odcgroup.page.metamodel.WidgetType;
import com.odcgroup.page.metamodel.util.MetaModelRegistry;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.util.WidgetFactory;

public class EditableIdVerifyFormat {
	
	private final static String ID_PROPERTY = "id";
	private MetaModel metaModel;
	private WidgetFactory widgetFactory;
	
	@Before
	public void setUp() throws Exception {
		metaModel = MetaModelRegistry.getMetaModel();
		widgetFactory = new WidgetFactory();
	}
	
	@Test
	public void testAutoCompleteShouldHaveIdCorrectFormat() throws Exception {
		WidgetType wType = metaModel.findWidgetType("xgui", "AutoComplete");
		Widget w = widgetFactory.create(wType);
		String idString = w.getPropertyValue(ID_PROPERTY);
		Assert.assertTrue(idString.matches("[A-Za-z0-9_]*"));
		Assert.assertTrue(idString.length()== 5);
	}
	
	@Test
	public void testCalDateFieldShouldHaveIdCorrectFormat() throws Exception {
		WidgetType wType = metaModel.findWidgetType("xgui", "CaldateField");
		Widget w = widgetFactory.create(wType);
		String idString = w.getPropertyValue(ID_PROPERTY);
		Assert.assertTrue(idString.matches("[A-Za-z0-9_]*"));
		Assert.assertTrue(idString.length()== 5);
	}
	
	@Test
	public void testFileChooserFieldShouldHaveIdCorrectFormat() throws Exception {
		WidgetType wType = metaModel.findWidgetType("xgui", "FileChooser");
		Widget w = widgetFactory.create(wType);
		String idString = w.getPropertyValue(ID_PROPERTY);
		Assert.assertTrue(idString.matches("[A-Za-z0-9_]*"));
		Assert.assertTrue(idString.length()== 5);
	}
	
	@Test
	public void testPasswordFieldShouldHaveIdCorrectFormat() throws Exception {
		WidgetType wType = metaModel.findWidgetType("xgui", "PasswordField");
		Widget w = widgetFactory.create(wType);
		String idString = w.getPropertyValue(ID_PROPERTY);
		Assert.assertTrue(idString.matches("[A-Za-z0-9_]*"));
		Assert.assertTrue(idString.length()== 5);
	}
	
	@Test
	public void testSearchFieldShouldHaveIdCorrectFormat() throws Exception {
		WidgetType wType = metaModel.findWidgetType("xgui", "SearchField");
		Widget w = widgetFactory.create(wType);
		String idString = w.getPropertyValue(ID_PROPERTY);
		Assert.assertTrue(idString.matches("[A-Za-z0-9_]*"));
		Assert.assertTrue(idString.length()== 5);
	}
	
	
	@Test
	public void testTextAreaShouldHaveIdCorrectFormat() throws Exception {
		WidgetType wType = metaModel.findWidgetType("xgui", "TextArea");
		Widget w = widgetFactory.create(wType);
		String idString = w.getPropertyValue(ID_PROPERTY);
		Assert.assertTrue(idString.matches("[A-Za-z0-9_]*"));
		Assert.assertTrue(idString.length()== 5);
	}
	
	@Test
	public void testTextFieldShouldHaveIdCorrectFormat() throws Exception {
		WidgetType wType = metaModel.findWidgetType("xgui", "TextField");
		Widget w = widgetFactory.create(wType);
		String idString = w.getPropertyValue(ID_PROPERTY);
		Assert.assertTrue(idString.matches("[A-Za-z0-9_]*"));
		Assert.assertTrue(idString.length()== 5);
	}

}
