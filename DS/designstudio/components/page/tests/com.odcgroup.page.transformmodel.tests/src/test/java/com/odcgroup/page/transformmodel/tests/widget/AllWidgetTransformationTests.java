package com.odcgroup.page.transformmodel.tests.widget;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.odcgroup.page.transformmodel.tests.widget.matrix.MatrixConditionalWidgetTest;
import com.odcgroup.page.transformmodel.tests.widget.tabbedpane.TabbedPaneTest;
import com.odcgroup.page.transformmodel.tests.widget.tabletree.TableTreePaginationTest;
import com.odcgroup.page.transformmodel.tests.widget.tabletree.TableTreeTest;
import com.odcgroup.page.transformmodel.tests.widget.tabletree.TableWidgetTest;
/**
 * Convenient test suite to validate all widgets transformation
 * @author yan
 */

@RunWith(Suite.class)
@SuiteClasses( {
	ButtonTest.class,
	GridTest.class,
	HorizontalBoxTest.class,
	MatrixTest.class,
	MatrixConditionalWidgetTest.class,
	TabbedPaneTest.class,
	TableTreeTest.class,
	TableTreePaginationTest.class,
	TableWidgetTest.class,
	VerticalBoxTest.class,

	LabelTest.class,
	IconTest.class,
	
	AutoCompleteTest.class,
	CalDateFieldTest.class,
	FileChooserTest.class,
	PasswordFieldTest.class,
	SearchFieldTest.class,
	TextAreaTest.class,
	TextFieldTest.class,

	CheckBoxTest.class,
	ComboBoxTest.class,
	ListTest.class,
	RadioButtonTest.class,

	GlueTest.class,
	SpacerTest.class,

	AttributeTest.class,
	CodeTest.class,
	ConditionalTest.class,
	HiddenFieldTest.class,
	IncludeTest.class,
	MessagesTest.class,
	FilterCriteriaTest.class,
	AutoCompleteSearchTest.class,
	//Xtoolttip test suite
	XtooltipWidgetTest.class,
} )
public class AllWidgetTransformationTests {
}
