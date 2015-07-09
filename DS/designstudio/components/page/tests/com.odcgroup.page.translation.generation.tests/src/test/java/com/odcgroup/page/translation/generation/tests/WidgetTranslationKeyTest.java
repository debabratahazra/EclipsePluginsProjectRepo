package com.odcgroup.page.translation.generation.tests;

import java.util.Locale;

import org.junit.Assert;
import org.junit.Test;

import com.odcgroup.page.metamodel.MetaModel;
import com.odcgroup.page.metamodel.WidgetLibraryConstants;
import com.odcgroup.page.metamodel.WidgetType;
import com.odcgroup.page.metamodel.WidgetTypeConstants;
import com.odcgroup.page.metamodel.util.MetaModelRegistry;
import com.odcgroup.page.model.ModelFactory;
import com.odcgroup.page.model.Translation;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.translation.WidgetTranslation;
import com.odcgroup.page.model.translation.WidgetTranslationProvider;
import com.odcgroup.page.model.util.WidgetFactory;
import com.odcgroup.page.translation.generation.WidgetTranslationKeyProvider;
import com.odcgroup.translation.core.ITranslation;
import com.odcgroup.translation.core.ITranslationKind;
import com.odcgroup.translation.core.TranslationException;
import com.odcgroup.translation.generation.ITranslationKey;

/**
 * @author atr
 */
public class WidgetTranslationKeyTest extends TranslationKeyTest {
	
	/**
	 * 
	 */
	@Test
	public void testWidgetTranslationKey() {
		
		// prepare data
		MetaModel mm = MetaModelRegistry.getMetaModel();
		WidgetType wType = mm.findWidgetType(WidgetLibraryConstants.XGUI, WidgetTypeConstants.MODULE);
		WidgetFactory wFactory = new WidgetFactory();
		Widget widget = wFactory.create(wType);
		
		WidgetTranslationProvider transProvider = new WidgetTranslationProvider();
		Translation enTrans = ModelFactory.eINSTANCE.createTranslation();
		enTrans.setLanguage("en");
		enTrans.setMessage("en-test");
		widget.getLabels().add(enTrans);
		Translation frTrans = ModelFactory.eINSTANCE.createTranslation();
		frTrans.setLanguage("fr");
		frTrans.setMessage("fr-test");
		widget.getLabels().add(frTrans);
		widget.setTranslationId(123);
		WidgetTranslation widgetTranslation = new WidgetTranslation(transProvider, getProject(), widget); 
		WidgetTranslationKeyProvider provider = new WidgetTranslationKeyProvider();
		
		// retrieve translation key		
		ITranslationKey translationKey = provider.getTranslationKey(project, widgetTranslation);
		Assert.assertTrue("Cannot retrieve the translation key.", translationKey != null);

		// check translation kinds
		ITranslationKind kinds[] = translationKey.getTranslationKinds();
		Assert.assertTrue("Wrong number of translation kinds", kinds.length == 1);
		Assert.assertTrue("Wrong translation kind", kinds[0] == ITranslationKind.NAME);
		
		// check wrapped translation
		ITranslation translation = translationKey.getTranslation();
		Assert.assertTrue("Missing wrapped event translation", translation != null);
		Assert.assertTrue("Missing wrapped event ", translation.getOwner() != null);
		Assert.assertTrue("Wrong wrapped event instance", widget == translation.getOwner());
		
		ITranslationKind kind = ITranslationKind.NAME;
		String key = translationKey.getKey(kind);

		// check the English message
		try {
			String message = translationKey.getMessage(kind, Locale.ENGLISH);
			Assert.assertTrue("Wrong message for the english key", "en-test".equals(message));
			
		} catch (TranslationException ex) {
			ex.printStackTrace();
			Assert.fail("Unexpected exception");
		}

		// check the French message
		Assert.assertTrue("Wrong translation key for french language", "123.text".equals(key));
		try {
			String message = translationKey.getMessage(kind, Locale.FRENCH);
			Assert.assertTrue("Wrong message for the french key", "fr-test".equals(message));
			
		} catch (TranslationException ex) {
			ex.printStackTrace();
			Assert.fail("Unexpected exception");
		}
		
		// check the German message
		Assert.assertTrue("Wrong translation key for german language", "123.text".equals(key));
		try {
			String message = translationKey.getMessage(kind, Locale.GERMAN);
			Assert.assertTrue("Wrong message for the german key", null == message);
			
		} catch (TranslationException ex) {
			ex.printStackTrace();
			Assert.fail("Unexpected exception");
		}
	}
	
	/**
	 * 
	 */
	@Test
	public void testWidgetTranslationKeyWhenNoTranslation() {
		// prepare data
		
		MetaModel mm = MetaModelRegistry.getMetaModel();
		WidgetType wType = mm.findWidgetType(WidgetLibraryConstants.XGUI, WidgetTypeConstants.MODULE);
		WidgetFactory wFactory = new WidgetFactory();
		Widget widget = wFactory.create(wType);

		WidgetTranslationProvider transProvider = new WidgetTranslationProvider();
		WidgetTranslation eventTranslation = new WidgetTranslation(transProvider, getProject(), widget); 
		WidgetTranslationKeyProvider provider = new WidgetTranslationKeyProvider();
		
		// retrieve translation key		
		ITranslationKey translationKey = provider.getTranslationKey(project, eventTranslation);
		Assert.assertTrue("Cannot retrieve the translation key.", translationKey != null);
		
		// check translation kinds
		ITranslationKind kinds[] = translationKey.getTranslationKinds();
		Assert.assertTrue("Wrong number of translation kinds", kinds.length == 1);
		Assert.assertTrue("Wrong translation kind", kinds[0] == ITranslationKind.NAME);
		
		// check wrapped translation
		ITranslation translation = translationKey.getTranslation();
		Assert.assertTrue("Missing wrapped event translation", translation != null);
		Assert.assertTrue("Missing wrapped event ", translation.getOwner() != null);
		Assert.assertTrue("Wrong wrapped event instance", widget == translation.getOwner());
		
		ITranslationKind kind = ITranslationKind.NAME;
		String key = translationKey.getKey(kind);

		// check english key & message
		Assert.assertTrue("The message key must be null if no translation has been defined", null == key);
		
		try {
			String message = translationKey.getMessage(kind, Locale.ENGLISH);
			Assert.assertTrue("The message must be null if no translation has been defined", null == message);
			
		} catch (TranslationException ex) {
			ex.printStackTrace();
			Assert.fail("Unexpected exception");
		}
		
	}

}
