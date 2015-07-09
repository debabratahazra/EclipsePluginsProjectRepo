package com.odcgroup.page.model.tests.translation;

import java.util.Locale;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.odcgroup.page.model.ModelFactory;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.translation.WidgetTranslation;
import com.odcgroup.page.model.translation.WidgetTranslationProvider;
import com.odcgroup.translation.core.ITranslation;
import com.odcgroup.translation.core.ITranslationKind;
import com.odcgroup.translation.core.TranslationException;
import com.odcgroup.workbench.core.tests.util.AbstractDSUnitTest;

/**
 * @author yan
 */
public class WidgetTranslationProviderTest extends AbstractDSUnitTest {
	
	private Widget widget;
	
	@Before
	public void setUp() throws Exception {
		
		// create a dummy project with the OfsNature.
		createModelsProject();

		widget = ModelFactory.eINSTANCE.createWidget();
		widget.setTypeName("Box");
	}
	
	@After
	public void tearDown() throws Exception {
		widget = null;
		deleteModelsProjects();
	}

	@Test
	public void testProcessTranslationProvider () throws TranslationException {
		ITranslation translationForWidget = 
			new WidgetTranslationProvider()
				.getTranslation(getProject(), widget);
	
		Assert.assertTrue("Wrong translation object returned", 
				translationForWidget instanceof WidgetTranslation);

		translationForWidget.setText(ITranslationKind.NAME, Locale.FRENCH, "test1");
		Assert.assertEquals("The translation object is not connected to the class", 
				"test1",
				translationForWidget.getText(ITranslationKind.NAME, Locale.FRENCH));
	}

}
