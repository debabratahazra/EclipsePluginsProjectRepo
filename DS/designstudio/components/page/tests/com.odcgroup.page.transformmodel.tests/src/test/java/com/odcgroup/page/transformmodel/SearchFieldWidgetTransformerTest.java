package com.odcgroup.page.transformmodel;

import org.junit.Assert;
import org.junit.Test;

public class SearchFieldWidgetTransformerTest {

	@Test
	public void testParamOverride_ignoreIfNotUsingFlowActionAndFlowChange() {
		SearchFieldWidgetTransformer searchFieldWidgetTransformer = new SearchFieldWidgetTransformer(null);
		
		Assert.assertEquals("", searchFieldWidgetTransformer.applyFlowActionOverriddingByFlowChange(""));
		Assert.assertEquals("param=value", searchFieldWidgetTransformer.applyFlowActionOverriddingByFlowChange("param=value"));
	}
	
	@Test
	public void testParamOverride_updateFlowAction() {
		SearchFieldWidgetTransformer searchFieldWidgetTransformer = new SearchFieldWidgetTransformer(null);
		
		Assert.assertEquals("flow-action=someOtherValue", searchFieldWidgetTransformer.applyFlowActionOverriddingByFlowChange("flow-action=someValue;flow-change=someOtherValue"));
		Assert.assertEquals("flow-action=someOtherValue", searchFieldWidgetTransformer.applyFlowActionOverriddingByFlowChange("flow-action = someValue ; flow-change=someOtherValue"));
		Assert.assertEquals(" somethingBefore ;flow-action=someOtherValue; somethingInTheMiddle ; somethingAfter ", searchFieldWidgetTransformer.applyFlowActionOverriddingByFlowChange(" somethingBefore ; flow-action = someValue ; somethingInTheMiddle ; flow-change=someOtherValue ; somethingAfter "));
	}
	
	@Test
	public void testParamOverride_updateFlowActionIfSwapped() {
		SearchFieldWidgetTransformer searchFieldWidgetTransformer = new SearchFieldWidgetTransformer(null);
		
		Assert.assertEquals("flow-action=someOtherValue", searchFieldWidgetTransformer.applyFlowActionOverriddingByFlowChange("flow-change=someOtherValue;flow-action=someValue"));
		Assert.assertEquals("flow-action=someOtherValue", searchFieldWidgetTransformer.applyFlowActionOverriddingByFlowChange("flow-change=someOtherValue ; flow-action = someValue"));
		Assert.assertEquals(" somethingBefore ; somethingInTheMiddle ;flow-action=someOtherValue; somethingAfter ", searchFieldWidgetTransformer.applyFlowActionOverriddingByFlowChange(" somethingBefore ; flow-change = someOtherValue ; somethingInTheMiddle ; flow-action=someValue ; somethingAfter "));
	}
	
	@Test
	public void testRemoveFlowChange() {
		SearchFieldWidgetTransformer searchFieldWidgetTransformer = new SearchFieldWidgetTransformer(null);
		
		Assert.assertEquals("param=value", searchFieldWidgetTransformer.removeFlowChange("param=value"));
		Assert.assertEquals("somethingBefore; flow-action = someValue ;somethingAfter", searchFieldWidgetTransformer.removeFlowChange("somethingBefore; flow-action = someValue ;somethingAfter"));
		Assert.assertEquals(" somethingBefore ; flow-action = someValue ; somethingInTheMiddle ; somethingAfter ", searchFieldWidgetTransformer.removeFlowChange(" somethingBefore ; flow-action = someValue ; somethingInTheMiddle ; flow-change=someOtherValue ; somethingAfter "));
	}
	
	@Test
	public void testDefaultValueOfReload() {
		SearchFieldWidgetTransformer searchFieldWidgetTransformer = new SearchFieldWidgetTransformer(null);
		
		Assert.assertEquals("flow-action=reload", searchFieldWidgetTransformer.applyFlowActionOverriddingByFlowChange("flow-action = someValue"));
		Assert.assertEquals("flow-action = someValue", searchFieldWidgetTransformer.removeFlowChange("flow-action = someValue"));
	}	
}
