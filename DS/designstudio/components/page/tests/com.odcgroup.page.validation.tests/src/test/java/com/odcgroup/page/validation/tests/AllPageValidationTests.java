package com.odcgroup.page.validation.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.odcgroup.page.validation.internal.PageWidgetEventValidatorTest;
import com.odcgroup.page.validation.internal.PageWidgetTranslationValidatorTest;
import com.odcgroup.page.validation.internal.constraint.PagePropertyValidatorTest;
import com.odcgroup.page.validation.internal.constraint.PageValidationRulesTest;
import com.odcgroup.page.validation.internal.constraint.PageWidgetValidatorTest;
import com.odcgroup.page.validation.resolution.HtmlIdProblemResolutionTest;

@RunWith(Suite.class)
@SuiteClasses( {
	PagePropertyValidatorTest.class,
	HtmlIdProblemResolutionTest.class,
	PageValidationRulesTest.class,
	PageSyntaxValidationTest.class,
	PageWidgetEventValidatorTest.class,
	PageWidgetTranslationValidatorTest.class,
	PageWidgetValidatorTest.class,
} )
public class AllPageValidationTests {

}
