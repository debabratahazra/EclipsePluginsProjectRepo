package com.odcgroup.page.metamodel.util.tests;

import org.junit.Assert;
import org.junit.Test;

import com.odcgroup.page.metamodel.Accountability;
import com.odcgroup.page.metamodel.AccountabilityRule;
import com.odcgroup.page.metamodel.MetaModel;
import com.odcgroup.page.metamodel.WidgetType;
import com.odcgroup.page.metamodel.util.MetaModelRegistry;

/**
 * @author atr
 */
public class AccountabilityRuleTest {

	@Test
	public void testDs3650AccountabilityRule() {
		
		MetaModel mm = MetaModelRegistry.getMetaModel();
		WidgetType wt1 = mm.findWidgetType("xgui", "ConditionalBody");
		Assert.assertTrue("Cannot find widget type [ConditionalBody] in library [xgui]", wt1 != null);
		WidgetType wt2 = mm.findWidgetType("xgui", "TableColumnItem");
		Assert.assertTrue("Cannot find widget type [TableColumnItem] in library [xgui]", wt2 != null);
		
		Accountability ac = MetaModelRegistry.getMetaModel().getContainability();
		AccountabilityRule rule = ac.findAccountabilityRule(wt2, wt1);
		Assert.assertTrue("Accountability rule for [ConditionalBody/TableColumnItem] is not found", rule != null);
		Assert.assertTrue("Accountability rule for [ConditionalBody/TableColumnItem] min occurences is wrong", rule.getMinOccurs() == 0);
		Assert.assertTrue("Accountability rule for [ConditionalBody/TableColumnItem] max occurences is wrong", rule.getMaxOccurs() == 1);
		
		

	}
	
	//check for the accountability rule for xtooltip widget
	@Test
	public void testDS4945AccountabilityRule(){
		MetaModel mm = MetaModelRegistry.getMetaModel();
		WidgetType parent = mm.findWidgetType("xgui", "Xtooltip"); 
		Assert.assertTrue("Cannot find widget type [Xtooltip] in library [xgui]", parent != null);
		WidgetType child = mm.findWidgetType("xgui", "Grid");
		Assert.assertTrue("Cannot find widget type [Grid] in library [xgui]", child != null);
		Accountability ac = MetaModelRegistry.getMetaModel().getContainability();
		AccountabilityRule rule = ac.findAccountabilityRule(child,parent);
		Assert.assertTrue("Accountability rule for [xtooltip/Grid] is not found", rule != null);
		Assert.assertTrue("Accountability rule for [xtooltip/Grid] min occurences is wrong", rule.getMinOccurs() == 0);
		Assert.assertTrue("Accountability rule for [xtooltip/Grid] max occurences is wrong", rule.getMaxOccurs() == -1);
		
	}
}
