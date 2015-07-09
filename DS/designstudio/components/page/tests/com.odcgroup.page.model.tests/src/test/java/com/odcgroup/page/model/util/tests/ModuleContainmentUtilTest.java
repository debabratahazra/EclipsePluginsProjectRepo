package com.odcgroup.page.model.util.tests;

import org.junit.Assert;
import org.junit.Test;

import com.odcgroup.page.metamodel.MetaModel;
import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.metamodel.WidgetType;
import com.odcgroup.page.metamodel.util.MetaModelRegistry;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.util.ModuleContainmentUtil;
import com.odcgroup.page.model.util.WidgetFactory;

/**
 * @author atr
 */
public class ModuleContainmentUtilTest {

	/**
	 * done in the context of JIRA DS-3672
	 */
	@Test
	public void testDS3672_CanContainModules() {
		
		MetaModel metaModel = MetaModelRegistry.getMetaModel();
		WidgetType moduleType = metaModel.findWidgetType("xgui", "Module");
		Assert.assertTrue("The WidgetType [Module] does not exists in library xgui", moduleType != null);
		
		Widget module = new WidgetFactory().create(moduleType);
		Assert.assertTrue("The Widget [Module] cannot be created", module != null);

		boolean result = false;
		
		Property property = module.findProperty(PropertyTypeConstants.MODULE_CONTAINMENT);
		Assert.assertTrue("Module must have the property "+PropertyTypeConstants.MODULE_CONTAINMENT, property != null);

		property.setValue(ModuleContainmentUtil.MODULE_CONTAINMENT_CONTAINER);
		result = ModuleContainmentUtil.canContainModules(module);
		Assert.assertTrue("Container module can contains other modules", result);

		property.setValue(ModuleContainmentUtil.MODULE_CONTAINMENT_STANDALONE);
		result = ModuleContainmentUtil.canContainModules(module);
		Assert.assertFalse("Standalone module cannot contains other modules", result);

		// check the rules behaves well even if the property has no value.
		property.setValue(null);
		result = ModuleContainmentUtil.canContainModules(module);
		Assert.assertFalse("Unknown module cannot contains other modules", result);
	}
	
	/**
	 * done in the context of JIRA DS-3672
	 */
	@Test
	public void testDS3672_IsContainerModule() {
		
		MetaModel metaModel = MetaModelRegistry.getMetaModel();
		WidgetType moduleType = metaModel.findWidgetType("xgui", "Module");
		Assert.assertTrue("The WidgetType [Module] does not exists", moduleType != null);
		
		Widget module = new WidgetFactory().create(moduleType);
		Assert.assertTrue("The Widget [Module] cannot be created", module != null);

		boolean result = false;
		
		Property property = module.findProperty(PropertyTypeConstants.MODULE_CONTAINMENT);
		Assert.assertTrue("Module must have the property "+PropertyTypeConstants.MODULE_CONTAINMENT, property != null);

		property.setValue(ModuleContainmentUtil.MODULE_CONTAINMENT_CONTAINER);
		result = ModuleContainmentUtil.isContainerModule(module);
		Assert.assertTrue("Container module not well recognized", result);

		property.setValue(ModuleContainmentUtil.MODULE_CONTAINMENT_STANDALONE);
		result = ModuleContainmentUtil.isContainerModule(module);
		Assert.assertFalse("Standalone module rgicnized as a Container module", result);

		// check the rules behaves well even if the property has no value.
		property.setValue(null);
		result = ModuleContainmentUtil.isContainerModule(module);
		Assert.assertFalse("Unknown module recognized as a Container Module", result);
	}
}
