package com.odcgroup.page.model.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.odcgroup.page.model.filter.test.FragmentPropertyFilterTest;
import com.odcgroup.page.model.filter.test.WidgetPropertyFilterTest;
import com.odcgroup.page.model.tests.translation.EventTranslationTest;
import com.odcgroup.page.model.tests.translation.WidgetDomainTranslationTest;
import com.odcgroup.page.model.tests.translation.WidgetTranslationModelTest;
import com.odcgroup.page.model.tests.translation.WidgetTranslationTest;
import com.odcgroup.page.model.translation.DomainPropertyListenerTest;
import com.odcgroup.page.model.util.tests.ActionIdverifyFormatTest;
import com.odcgroup.page.model.util.tests.DSExpressionWithContextGeneratorTest;
import com.odcgroup.page.model.util.tests.EditableIdVerifyFormat;
import com.odcgroup.page.model.util.tests.ModifiableIdVerifyFormat;
import com.odcgroup.page.model.util.tests.ModuleContainmentUtilTest;
import com.odcgroup.page.model.util.tests.PanelIdVerifyFormatTest;
import com.odcgroup.page.model.util.tests.WidgetFactoryTest;
import com.odcgroup.page.model.util.tests.WidgetIdPropertyTest;

/**
 * Tests for the Page MetaModel plugin.
 * @author atr
 */
@RunWith(Suite.class)
@SuiteClasses( {
	WidgetFactoryTest.class,
	ModuleContainmentUtilTest.class,
	WidgetTranslationModelTest.class,
	WidgetTranslationTest.class,
	WidgetDomainTranslationTest.class,
	EventTranslationTest.class,
	UniqueIdGeneratorTest.class,
	WidgetCopierTest.class,
	MatrixCellItemTest.class,
	WidgetIdPropertyTest.class,
	PanelIdVerifyFormatTest.class,
	EditableIdVerifyFormat.class,
	ModifiableIdVerifyFormat.class,
	ActionIdverifyFormatTest.class,
	DomainPropertyListenerTest.class,
	//property filter on xtooltip
	FragmentPropertyFilterTest.class,
	WidgetPropertyFilterTest.class,
	DSExpressionWithContextGeneratorTest.class
})
public class AllPageModelTests {
}
