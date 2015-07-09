package com.odcgroup.page.model.util.tests;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.odcgroup.page.metamodel.MetaModel;
import com.odcgroup.page.metamodel.WidgetType;
import com.odcgroup.page.metamodel.util.MetaModelRegistry;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.util.WidgetFactory;

public class WidgetIdPropertyTest {

	/**
	 * DS-4204 - Too many ids generated spoil performance
	 */
	private final static String ID_PROPERTY = "id";
	private MetaModel metaModel;
	private WidgetFactory widgetFactory;
	
	@Before
	public void setUp() throws Exception {
		metaModel = MetaModelRegistry.getMetaModel();
		widgetFactory = new WidgetFactory();
	}
	
	@Test
	public void testBoxWidgetContainAnIdProperty() throws Exception {
		WidgetType wType = metaModel.findWidgetType("xgui", "Box");
		Widget w = widgetFactory.create(wType);
		String idString = w.getPropertyValue(ID_PROPERTY);
		Assert.assertSame("", idString);
	}
	
	@Test
	public void testGridWidgetDoesNotContainAnIdProperty() throws Exception {
		WidgetType wType = metaModel.findWidgetType("xgui", "Grid");
		Widget w = widgetFactory.create(wType);
		String idString = w.getPropertyValue(ID_PROPERTY);
		Assert.assertNull(idString);
	}
	
	@Test
	public void testGridCellWidgetDoesNotContainAnIdProperty() throws Exception {
		WidgetType wType = metaModel.findWidgetType("xgui", "GridCell");
		Widget w = widgetFactory.create(wType);
		String idString = w.getPropertyValue(ID_PROPERTY);
		Assert.assertNull(idString);
	}
	
	@Test
	public void testTableAggregateWidgetDoesNotContainAnIdProperty() throws Exception {
		WidgetType wType = metaModel.findWidgetType("xgui", "TableAggregate");
		Widget w = widgetFactory.create(wType);
		String idString = w.getPropertyValue(ID_PROPERTY);
		Assert.assertNull(idString);
	}
	
	@Test
	public void testTableExtraWidgetDoesNotContainAnIdProperty() throws Exception {
		WidgetType wType = metaModel.findWidgetType("xgui", "TableExtra");
		Widget w = widgetFactory.create(wType);
		String idString = w.getPropertyValue(ID_PROPERTY);
		Assert.assertNull(idString);
	}
	
	@Test
	public void testTableGroupWidgetDoesNotContainAnIdProperty() throws Exception {
		WidgetType wType = metaModel.findWidgetType("xgui", "TableGroup");
		Widget w = widgetFactory.create(wType);
		String idString = w.getPropertyValue(ID_PROPERTY);
		Assert.assertNull(idString);
	}
	
	@Test
	public void testTableKeepFilterWidgetDoesNotContainAnIdProperty() throws Exception {
		WidgetType wType = metaModel.findWidgetType("xgui", "TableKeepFilter");
		Widget w = widgetFactory.create(wType);
		String idString = w.getPropertyValue(ID_PROPERTY);
		Assert.assertNull(idString);
	}
	
	@Test
	public void testTableSortWidgetDoesNotContainAnIdProperty() throws Exception {
		WidgetType wType = metaModel.findWidgetType("xgui", "TableSort");
		Widget w = widgetFactory.create(wType);
		String idString = w.getPropertyValue(ID_PROPERTY);
		Assert.assertNull(idString);
	}
	
	@Test
	public void testLabelWidgetContainAnIdProperty() throws Exception {
		WidgetType wType = metaModel.findWidgetType("xgui", "Label");
		Widget w = widgetFactory.create(wType);
		String idString = w.getPropertyValue(ID_PROPERTY);
		Assert.assertSame("", idString);
	}

	@Test
	public void testLabelDomainWidgetContainAnIdProperty() throws Exception {
		WidgetType wType = metaModel.findWidgetType("xgui", "LabelDomain");
		Widget w = widgetFactory.create(wType);
		String idString = w.getPropertyValue(ID_PROPERTY);
		Assert.assertSame("", idString);
	}
}
