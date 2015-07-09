package com.odcgroup.page.ui.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.odcgroup.page.ui.command.table.TableDeleteColumnCommandTest;
import com.odcgroup.page.ui.properties.sections.tests.EventSectionTest;
import com.odcgroup.page.ui.util.tests.ActivityModuleCreationTest;
import com.odcgroup.page.ui.util.tests.AutoCompleteSearchFieldTest;
import com.odcgroup.page.ui.util.tests.CheckBoxWidgetTransformerTest;
import com.odcgroup.page.ui.util.tests.CodeWidgetPropertySaveTest;
import com.odcgroup.page.ui.util.tests.DomainAssociationIncludeConstraintTest;
import com.odcgroup.page.ui.util.tests.DynamicColumnAggregatesTest;
import com.odcgroup.page.ui.util.tests.DynamicColumnComputationTabTest;
import com.odcgroup.page.ui.util.tests.DynamicColumnTranslationTest;
import com.odcgroup.page.ui.util.tests.DynamicTabCustomEventTest;
import com.odcgroup.page.ui.util.tests.DynamicTabKeepFilterTest;
import com.odcgroup.page.ui.util.tests.FilterSetEnumerationTest;
import com.odcgroup.page.ui.util.tests.FragmentDefaultDSTest;
import com.odcgroup.page.ui.util.tests.MatrixAggregatesCssTransformTest;
import com.odcgroup.page.ui.util.tests.MatrixAggregationGenTest;
import com.odcgroup.page.ui.util.tests.MatrixAxisEventGenTest;
import com.odcgroup.page.ui.util.tests.MatrixAxisMaxGroupingTest;
import com.odcgroup.page.ui.util.tests.MatrixAxisSortTest;
import com.odcgroup.page.ui.util.tests.MatrixCellDuplicateContentsTest;
import com.odcgroup.page.ui.util.tests.MatrixCellItemPropertiesTest;
import com.odcgroup.page.ui.util.tests.MatrixCellItemTooltipTest;
import com.odcgroup.page.ui.util.tests.MatrixComputationItemTest;
import com.odcgroup.page.ui.util.tests.MatrixContentCellConditionalTest;
import com.odcgroup.page.ui.util.tests.MatrixContentCellItemFormatTest;
import com.odcgroup.page.ui.util.tests.MatrixDuplicateAggregatesTest;
import com.odcgroup.page.ui.util.tests.MatrixEnabledPropertiesTest;
import com.odcgroup.page.ui.util.tests.MatrixExtraColumnAlignmentTransformTest;
import com.odcgroup.page.ui.util.tests.MatrixExtraColumnCssTransformTest;
import com.odcgroup.page.ui.util.tests.MatrixExtraColumnDisplayFormatTest;
import com.odcgroup.page.ui.util.tests.MatrixExtraColumnTest;
import com.odcgroup.page.ui.util.tests.MatrixItemDisplayFormatTest;
import com.odcgroup.page.ui.util.tests.MatrixProtectColumnTypeTest;
import com.odcgroup.page.ui.util.tests.MatrixRelativePercentComputationTest;
import com.odcgroup.page.ui.util.tests.MatrixSimplifiedEnabledConditionTest;
import com.odcgroup.page.ui.util.tests.MatrixStickyPropertyTest;
import com.odcgroup.page.ui.util.tests.MatrixTransformIssuesTests;
import com.odcgroup.page.ui.util.tests.MatrixTransformTests;
import com.odcgroup.page.ui.util.tests.MatrixWeightedMeanGenTest;
import com.odcgroup.page.ui.util.tests.MultipleDynamicColumnTest;
import com.odcgroup.page.ui.util.tests.PageDesignerEditorTest;
import com.odcgroup.page.ui.util.tests.PageDesignerMatrixWidgetTest;
import com.odcgroup.page.ui.util.tests.PageModelWidgetDomainAttributeTest;
import com.odcgroup.page.ui.util.tests.PasteWidgetActionTest;
import com.odcgroup.page.ui.util.tests.TableColumnBoxesTest;
import com.odcgroup.page.ui.util.tests.TableColumnCheckboxTest;
import com.odcgroup.page.ui.util.tests.TableColumnHAlignmentTest;
import com.odcgroup.page.ui.util.tests.TableComputedColumnSortTest;
import com.odcgroup.page.ui.util.tests.TableFilterSubmitMethodTest;
import com.odcgroup.page.ui.util.tests.TableGroupTest;
import com.odcgroup.page.ui.util.tests.TableTreeMultipleCheckboxesTest;
import com.odcgroup.page.ui.util.tests.TestDatasetCache;
import com.odcgroup.page.ui.util.tests.TestSaveImageUtils;
import com.odcgroup.page.ui.util.tests.TranslationModelTest;

/**
 * Tests for the Page UIModel plugin.
 * 
 * @author Gary Hayes and SCN
 */
@RunWith(Suite.class)
@SuiteClasses( {
	TestSaveImageUtils.class,
	PageDesignerMatrixWidgetTest.class,
	PageDesignerEditorTest.class,
	TranslationModelTest.class,
	MatrixExtraColumnTest.class,
	MatrixComputationItemTest.class,
	MatrixStickyPropertyTest.class,
	MatrixTransformTests.class,
	MatrixAxisMaxGroupingTest.class,
	MatrixRelativePercentComputationTest.class,
	MatrixTransformIssuesTests.class,
	MatrixEnabledPropertiesTest.class,
	MatrixCellDuplicateContentsTest.class,
	MatrixDuplicateAggregatesTest.class,
	MatrixCellItemTooltipTest.class,
	MatrixProtectColumnTypeTest.class,
	MatrixExtraColumnAlignmentTransformTest.class,
	MatrixAxisEventGenTest.class,
	MatrixExtraColumnDisplayFormatTest.class,
	MatrixSimplifiedEnabledConditionTest.class,
	MatrixAggregationGenTest.class,
	MatrixContentCellItemFormatTest.class,
	MatrixItemDisplayFormatTest.class,
	MatrixAxisSortTest.class,
	FilterSetEnumerationTest.class,
	CheckBoxWidgetTransformerTest.class,
	TableColumnHAlignmentTest.class,
	TableFilterSubmitMethodTest.class,
	MatrixCellItemPropertiesTest.class,
	FragmentDefaultDSTest.class,
	DynamicTabKeepFilterTest.class,
	DynamicColumnTranslationTest.class,
	DynamicColumnAggregatesTest.class,
	MultipleDynamicColumnTest.class,
	MatrixExtraColumnCssTransformTest.class, 
//	MatrixAggregatesCssTransformTest.class,
	DynamicColumnComputationTabTest.class,
	PageModelWidgetDomainAttributeTest.class,
	MatrixContentCellConditionalTest.class,
	TableComputedColumnSortTest.class,
	TableDeleteColumnCommandTest.class,
	AutoCompleteSearchFieldTest.class,
	MatrixWeightedMeanGenTest.class,
	TableTreeMultipleCheckboxesTest.class,
	TableColumnCheckboxTest.class,
	TableColumnBoxesTest.class,
	DomainAssociationIncludeConstraintTest.class,
	TestDatasetCache.class,
	EventSectionTest.class,
	PasteWidgetActionTest.class,
	TableGroupTest.class,
	CodeWidgetPropertySaveTest.class,
	DynamicTabCustomEventTest.class,
	ActivityModuleCreationTest.class
} )
public class AllPageUITests {

}
