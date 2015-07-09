package com.odcgroup.page.ui.util.tests;

import java.util.Locale;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.odcgroup.page.metamodel.MetaModel;
import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.metamodel.WidgetLibraryConstants;
import com.odcgroup.page.metamodel.WidgetType;
import com.odcgroup.page.metamodel.util.MetaModelRegistry;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.translation.DomainWidgetTranslation;
import com.odcgroup.page.model.util.WidgetFactory;
import com.odcgroup.translation.core.ITranslation;
import com.odcgroup.translation.core.ITranslationKind;
import com.odcgroup.translation.core.ITranslationManager;
import com.odcgroup.translation.core.TranslationCore;
import com.odcgroup.translation.core.TranslationException;
import com.odcgroup.translation.ui.views.model.TranslationModel;
import com.odcgroup.workbench.core.repository.ModelNotFoundException;
import com.odcgroup.workbench.core.tests.util.AbstractDSUnitTest;

/**
 * @author atr
 */
public class TranslationModelTest extends AbstractDSUnitTest {
	
    @Before
	public void setUp() throws Exception {
    	createModelsProject();
        copyResourceInModelsProject("domain/ds2360/DS-2360.domain");
    }

	@After
	public void tearDown() throws Exception {
        deleteModelsProjects();
    }
	
	@Test
	public void testDS2360WidgetLocalTranslation() throws TranslationException {
		
		MetaModel mm = MetaModelRegistry.getMetaModel();
		WidgetType wType = null;
		WidgetFactory wFactory = null;
		
		// prepare data
		wType = mm.findWidgetType(WidgetLibraryConstants.XGUI, "Module");
		wFactory = new WidgetFactory();
		Widget module = wFactory.create(wType);
		module.setPropertyValue(PropertyTypeConstants.DOMAIN_ENTITY, "DS2360:Dataset");
		

		wType = mm.findWidgetType(WidgetLibraryConstants.XGUI, "LabelDomain");
		wFactory = new WidgetFactory();
		Widget label = wFactory.create(wType);
		label.setPropertyValue(PropertyTypeConstants.DOMAIN_ATTRIBUTE, "a1");
		
		module.getContents().add(label);
		
		ITranslationManager manager = TranslationCore.getTranslationManager(getProject());
		ITranslation translation = manager.getTranslation(label); 
		Assert.assertTrue("Cannot retrieve a translation for the widget LabelDomain", translation != null);
		
		// change the english translation of the widget
		translation.setText(ITranslationKind.NAME,Locale.ENGLISH,"xyz");
		
		TranslationModel tm = new TranslationModel(manager.getPreferences(), translation);
		String origin = tm.getOrigin(Locale.ENGLISH);
		Assert.assertTrue("The origin must be [Local] and not ["+origin+"]", "Local".equalsIgnoreCase(origin));	

	}

	@Test
	public void testDS2360WidgetInheritedTranslation() throws ModelNotFoundException {
		
		MetaModel mm = MetaModelRegistry.getMetaModel();
		WidgetType wType = null;
		
		/*
		 * Prepare data
		 */
		wType = mm.findWidgetType(WidgetLibraryConstants.XGUI, "Module");
		Widget module = new WidgetFactory().create(wType);
		module.setPropertyValue(PropertyTypeConstants.DOMAIN_ENTITY, "DS2360:Dataset");
		wType = mm.findWidgetType(WidgetLibraryConstants.XGUI, "LabelDomain");
		Widget label = new WidgetFactory().create(wType);
		label.setPropertyValue(PropertyTypeConstants.DOMAIN_ATTRIBUTE, "a1");
		module.getContents().add(label);
		
		/*
		 * Retrieve a translation for the label 
		 */
		ITranslationManager manager = TranslationCore.getTranslationManager(getOfsProject().getProject());
		ITranslation translation = manager.getTranslation(label); 
		Assert.assertTrue("Cannot retrieve a translation for the widget LabelDomain", translation != null);
		Assert.assertTrue("Invalid Translation's implementation class for the widget LabelDomain", translation instanceof DomainWidgetTranslation);

		/*
		 * Check the origin of the translation. It must be inherited 
		 */
		TranslationModel tm = new TranslationModel(manager.getPreferences(), translation);
		String origin = tm.getOrigin(Locale.ENGLISH);
		Assert.assertTrue("The origin must be [Inherited] and not ["+origin+"]", "Inherited".equalsIgnoreCase(origin));
	}

}
