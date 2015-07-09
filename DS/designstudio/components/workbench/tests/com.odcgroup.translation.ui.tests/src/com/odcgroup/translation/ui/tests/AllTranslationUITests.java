package com.odcgroup.translation.ui.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.odcgroup.translation.ui.tests.init.EnableDisableRemoveTransationButtonTest;
import com.odcgroup.translation.ui.tests.init.RichTextToHTMLConverterTest;
import com.odcgroup.translation.ui.tests.init.RichTextToStyledTextConverterTest;
import com.odcgroup.translation.ui.tests.init.StyledTextToRichTextConverterTest;
import com.odcgroup.translation.ui.tests.init.TranslationEditorUITest;

@RunWith(Suite.class)
@SuiteClasses( {
//	TranslationEditorUITest.class,
	EnableDisableRemoveTransationButtonTest.class,
	StyledTextToRichTextConverterTest.class,
	RichTextToHTMLConverterTest.class,
	RichTextToStyledTextConverterTest.class,
} )
public class AllTranslationUITests {

}
