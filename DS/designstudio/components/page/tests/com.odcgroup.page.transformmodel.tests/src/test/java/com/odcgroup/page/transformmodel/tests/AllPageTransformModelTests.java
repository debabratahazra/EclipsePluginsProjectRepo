package com.odcgroup.page.transformmodel.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.odcgroup.page.transformmodel.SearchFieldWidgetTransformerTest;
import com.odcgroup.page.transformmodel.module.AsynchronousModuleTest;
import com.odcgroup.page.transformmodel.table.NestedGroupAttributeRendererTest;
import com.odcgroup.page.transformmodel.tests.cdm.CdmWidgetTransformerTest;
import com.odcgroup.page.transformmodel.tests.event.AllEventTransformationTests;
import com.odcgroup.page.transformmodel.tests.jira.DS4746EventGenerationTest;
import com.odcgroup.page.transformmodel.tests.jira.DS4925IDTransformationTest;
import com.odcgroup.page.transformmodel.tests.jira.DS4931RadioButtonTransformationTest;
import com.odcgroup.page.transformmodel.tests.jira.DS4941ComboBoxTransformationTest;
import com.odcgroup.page.transformmodel.tests.jira.DS4957EditableTransformationTest;
import com.odcgroup.page.transformmodel.tests.jira.DS5275ModulePrefixIDGenerationTest;
import com.odcgroup.page.transformmodel.tests.jira.DS5304SearchFieldTransformerTest;
import com.odcgroup.page.transformmodel.tests.jira.DS5354CheckBoxGenerationTest;
import com.odcgroup.page.transformmodel.tests.jira.DS5371AutoCompleteSortTransformerTest;
import com.odcgroup.page.transformmodel.tests.jira.DS5377XTooltipTransformationTest;
import com.odcgroup.page.transformmodel.tests.jira.DS5442ELOnConditionsTest;
import com.odcgroup.page.transformmodel.tests.jira.DS6426XSPGenerationTest;
import com.odcgroup.page.transformmodel.tests.jira.EnumerationBasedRadioButtonTransformTest;
import com.odcgroup.page.transformmodel.tests.jira.XmlTransformerPerfTest;
import com.odcgroup.page.transformmodel.tests.pms.models.SafetyNetPmsPageGenerationTest;
import com.odcgroup.page.transformmodel.tests.util.TransformModelRegistryTest;
import com.odcgroup.page.transformmodel.tests.util.TransformUtilsTest;
import com.odcgroup.page.transformmodel.tests.widget.AllWidgetTransformationTests;
import com.odcgroup.page.transformmodel.tests.widget.id.AllWidgetIdFormatValidationTests;
import com.odcgroup.page.transformmodel.tests.widget.tabletree.DS4871CheckboxOnTableGrpingTest;
import com.odcgroup.page.transformmodel.tests.widget.tabletree.TableColumnFormatTest;
import com.odcgroup.page.transformmodel.tests.widget.tabletree.TableTreeOnlineCellModification;
import com.odcgroup.page.transformmodel.tests.widget.tabletree.TableTreeToolbarTransformTest;

/**
 * Tests for the Page TransformModel plugin.
 *
 * @author Gary Hayes
 */
@RunWith(Suite.class)
@SuiteClasses( {
	XmlTransformerPerfTest.class,
	TransformModelRegistryTest.class,
	TransformUtilsTest.class,
	CdmWidgetTransformerTest.class,
	AllWidgetTransformationTests.class,
	AllEventTransformationTests.class,
	AllWidgetIdFormatValidationTests.class,
	NestedGroupAttributeRendererTest.class,
	SearchFieldWidgetTransformerTest.class,
	DS4746EventGenerationTest.class,
	EnumerationBasedRadioButtonTransformTest.class, 
	DS4925IDTransformationTest.class,
	DS5275ModulePrefixIDGenerationTest.class,
	DS4931RadioButtonTransformationTest.class,
	DS4941ComboBoxTransformationTest.class,
	DS4957EditableTransformationTest.class,
	TableTreeOnlineCellModification.class,
	DS5304SearchFieldTransformerTest.class,
	DS5354CheckBoxGenerationTest.class,
	DS5377XTooltipTransformationTest.class,
	DS5371AutoCompleteSortTransformerTest.class,
	SafetyNetPmsPageGenerationTest.class,
	DS5442ELOnConditionsTest.class,
	DS4871CheckboxOnTableGrpingTest.class,
	AsynchronousModuleTest.class,
	TableTreeToolbarTransformTest.class,
	DS6426XSPGenerationTest.class,
	TableColumnFormatTest.class
} )
public class AllPageTransformModelTests {

}
