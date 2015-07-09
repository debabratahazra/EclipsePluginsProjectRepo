package com.odcgroup.page.translation.generation.tests;

import java.util.Locale;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.odcgroup.page.metamodel.MetaModel;
import com.odcgroup.page.metamodel.WidgetLibraryConstants;
import com.odcgroup.page.metamodel.WidgetType;
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
import com.odcgroup.workbench.core.tests.util.AbstractDSUnitTest;

/**
 * @author atr
 */
public class DomainWidgetTranslationKeyTest extends AbstractDSUnitTest {
	
	private static final String DOMAIN_MODEL = "domain/ds3333/DS-3333.domain";

	@Before
	public void setUp() throws Exception {
		createModelsProject();
	}

	@After
	public void tearDown() throws Exception  {
		deleteModelsProjects();
	}	
	
	@Test
	public void testDomainWidgetTranslationKeyWithLocalTranslation() {
		
		// prepare data
		MetaModel mm = MetaModelRegistry.getMetaModel();
		WidgetType wType = mm.findWidgetType(WidgetLibraryConstants.XGUI, "LabelDomain");
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
		ITranslationKey translationKey = provider.getTranslationKey(getProject(), widgetTranslation);
		Assert.assertTrue("Cannot retrieve the translation key.", translationKey != null);

		// check translation kinds
		ITranslationKind kinds[] = translationKey.getTranslationKinds();
		Assert.assertTrue("Wrong number of translation kinds", kinds.length == 2);
		Assert.assertTrue("Wrong translation kind", kinds[0] == ITranslationKind.NAME);
		Assert.assertTrue("Wrong translation kind", kinds[1] == ITranslationKind.TEXT);
		
		// check wrapped translation
		ITranslation translation = translationKey.getTranslation();
		Assert.assertTrue("Missing wrapped event translation", translation != null);
		Assert.assertTrue("Missing wrapped event ", translation.getOwner() != null);
		Assert.assertTrue("Wrong wrapped event instance", widget == translation.getOwner());
		
		ITranslationKind kind = ITranslationKind.NAME;

		String key = translationKey.getKey(kind);
		Assert.assertTrue("Wrong translation key language", "123.text".equals(key));

		// check the English message
		try {
			String message = translationKey.getMessage(kind, Locale.ENGLISH);
			Assert.assertTrue("Wrong English message for the key", "en-test".equals(message));
			
		} catch (TranslationException ex) {
			ex.printStackTrace();
			Assert.fail("Unexpected exception");
		}

		// check the French message
		try {
			String message = translationKey.getMessage(kind, Locale.FRENCH);
			Assert.assertTrue("Wrong French message for the key", "fr-test".equals(message));
			
		} catch (TranslationException ex) {
			ex.printStackTrace();
			Assert.fail("Unexpected exception");
		}
		
		// check the German message
		try {
			String message = translationKey.getMessage(kind, Locale.GERMAN);
			Assert.assertTrue("Wrong German message for the key", null == message);
			
		} catch (TranslationException ex) {
			ex.printStackTrace();
			Assert.fail("Unexpected exception");
		}
	}
	
	/**
	 * 
	 */
	@Test
	public void testDomainWidgetTranslationKeyWithoutLocalTranslation() {
		// prepare data
		
		MetaModel mm = MetaModelRegistry.getMetaModel();
		WidgetType wType = mm.findWidgetType(WidgetLibraryConstants.XGUI, "LabelDomain");
		WidgetFactory wFactory = new WidgetFactory();
		Widget widget = wFactory.create(wType);

		WidgetTranslationProvider transProvider = new WidgetTranslationProvider();
		WidgetTranslation eventTranslation = new WidgetTranslation(transProvider, getProject(), widget); 
		WidgetTranslationKeyProvider provider = new WidgetTranslationKeyProvider();
		
		// retrieve translation key		
		ITranslationKey translationKey = provider.getTranslationKey(getProject(), eventTranslation);
		Assert.assertTrue("The translation key must be null when the widget is not bound to a dataset attribute.", translationKey == null);
		
	}
	
	/**
	 * 
	 */
	@Test
	public void testDomainWidgetTranslationKeyWithDomainAttribute() {

		copyResourceInModelsProject(DOMAIN_MODEL);
		
		MetaModel mm = MetaModelRegistry.getMetaModel();
		WidgetType wType = mm.findWidgetType(WidgetLibraryConstants.XGUI, "Module");
		WidgetFactory wFactory = new WidgetFactory();
		Widget module = wFactory.create(wType);
		module.setPropertyValue("domainEntity", "DS3333:DS3333Dataset");

		wType = mm.findWidgetType(WidgetLibraryConstants.XGUI, "LabelDomain");
		Widget label = wFactory.create(wType);
		label.setPropertyValue("domainAttribute", "attribute");
		
		module.getContents().add(label);

		WidgetTranslationProvider transProvider = new WidgetTranslationProvider();
		WidgetTranslation eventTranslation = new WidgetTranslation(transProvider, getProject(), label); 
		WidgetTranslationKeyProvider provider = new WidgetTranslationKeyProvider();
		
		// retrieve translation key		
		ITranslationKey translationKey = provider.getTranslationKey(getProject(), eventTranslation);
		Assert.assertTrue("The translation key must not be null when the widget is bound to a dataset attribute.", translationKey != null);

		String key = translationKey.getKey(ITranslationKind.NAME);
		Assert.assertTrue("Wrong translation key", "ds3333.ds3333dataset.attribute.text".equals(key));

		// check the English label message
		try {
			String message = translationKey.getMessage(ITranslationKind.NAME, Locale.ENGLISH);
			Assert.assertTrue("Wrong message for the english key", "test-label".equals(message));
			
		} catch (TranslationException ex) {
			ex.printStackTrace();
			Assert.fail("Unexpected exception");
		}

		// check the English tooltip message
		try {
			String message = translationKey.getMessage(ITranslationKind.TEXT, Locale.ENGLISH);
			Assert.assertTrue("Wrong message for the english key", "test-tooltip".equals(message));
			
		} catch (TranslationException ex) {
			ex.printStackTrace();
			Assert.fail("Unexpected exception");
		}
		
		
	}

}
