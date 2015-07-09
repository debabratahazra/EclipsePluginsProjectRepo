package com.odcgroup.page.model.tests.translation;

import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.odcgroup.page.metamodel.MetaModel;
import com.odcgroup.page.metamodel.WidgetLibraryConstants;
import com.odcgroup.page.metamodel.WidgetType;
import com.odcgroup.page.metamodel.util.MetaModelRegistry;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.translation.DomainWidgetTranslation;
import com.odcgroup.page.model.translation.WidgetTranslation;
import com.odcgroup.page.model.util.WidgetFactory;
import com.odcgroup.translation.core.ITranslation;
import com.odcgroup.translation.core.ITranslationKind;
import com.odcgroup.translation.core.ITranslationManager;
import com.odcgroup.translation.core.TranslationCore;
import com.odcgroup.translation.core.TranslationException;
import com.odcgroup.workbench.core.tests.util.AbstractDSUnitTest;

/**
 * @author atr
 */
public class WidgetTranslationTest extends AbstractDSUnitTest {
	
	private static final String DOMAIN_MODEL = "domain/ds3327/DS-3327.domain";

	@Before
	public void setUp() throws Exception {
		createModelsProject();
		copyResourceInModelsProject(DOMAIN_MODEL);
	}

	@After
	public void tearDown() throws Exception  {
		deleteModelsProjects();
	}	
	
	@Test
	public void testWidgetWithoutTranslationSupport() {
		MetaModel mm = MetaModelRegistry.getMetaModel();
		WidgetType wType = mm.findWidgetType(WidgetLibraryConstants.XGUI, "Glue");
		WidgetFactory wFactory = new WidgetFactory();
		Widget widget = wFactory.create(wType);
		
		ITranslationManager manager = TranslationCore.getTranslationManager(getOfsProject().getProject());
		ITranslation translation = manager.getTranslation(widget);
		Assert.assertTrue("Glue should not have translation support", translation == null);
	}
	
	@Test
	public void testWidgetWithTranslationSupport() {
		MetaModel mm = MetaModelRegistry.getMetaModel();
		WidgetType wType = mm.findWidgetType(WidgetLibraryConstants.XGUI, "Label");
		WidgetFactory wFactory = new WidgetFactory();
		Widget widget = wFactory.create(wType);
		
		ITranslationManager manager = TranslationCore.getTranslationManager(getOfsProject().getProject());
		ITranslation translation = manager.getTranslation(widget);
		Assert.assertTrue("Label should have translation support", translation != null);
		Assert.assertTrue("Wrong translation class", translation instanceof WidgetTranslation);

	}

	@Test
	public void testWidgetDomainWithTranslationSupportWithoutDomain() {
		MetaModel mm = MetaModelRegistry.getMetaModel();
		WidgetType wType = mm.findWidgetType(WidgetLibraryConstants.XGUI, "LabelDomain");
		WidgetFactory wFactory = new WidgetFactory();
		Widget widget = wFactory.create(wType);
		
		ITranslationManager manager = TranslationCore.getTranslationManager(getOfsProject().getProject());
		ITranslation translation = manager.getTranslation(widget);
		Assert.assertTrue("LabelDomain should have translation support", translation != null);
		Assert.assertTrue("Wrong translation class for LabelDomain", translation instanceof WidgetTranslation);

	}
	
	@Test
	public void testWidgetDomainWithTranslationSupportWithDomain() {
		MetaModel mm = MetaModelRegistry.getMetaModel();
		WidgetType wType = mm.findWidgetType(WidgetLibraryConstants.XGUI, "LabelDomain");
		WidgetFactory wFactory = new WidgetFactory();
		Widget widget = wFactory.create(wType);
		widget.setPropertyValue("domainAttribute", "attribute");
		
		ITranslationManager manager = TranslationCore.getTranslationManager(getOfsProject().getProject());
		ITranslation translation = manager.getTranslation(widget);
		Assert.assertTrue("LabelDomain should have translation support", translation != null);
		Assert.assertTrue("Wrong translation class for LabelDomain", translation instanceof WidgetTranslation);

	}	
	
	@Test
	public void testWidgetDomainWithTranslationSupportWithDomainInModule() {
		MetaModel mm = MetaModelRegistry.getMetaModel();
		WidgetType wType = mm.findWidgetType(WidgetLibraryConstants.XGUI, "Module");
		WidgetFactory wFactory = new WidgetFactory();
		Widget module = wFactory.create(wType);
		module.setPropertyValue("domainEntity", "dataset");

		mm = MetaModelRegistry.getMetaModel();
		wType = mm.findWidgetType(WidgetLibraryConstants.XGUI, "LabelDomain");
		wFactory = new WidgetFactory();
		Widget widget = wFactory.create(wType);
		widget.setPropertyValue("domainAttribute", "attribute");
		
		module.getContents().add(widget);
		
		ITranslationManager manager = TranslationCore.getTranslationManager(getOfsProject().getProject());
		ITranslation translation = manager.getTranslation(widget);
		Assert.assertTrue("LabelDomain should have translation support", translation != null);
		Assert.assertTrue("Wrong translation class for LabelDomain", translation instanceof DomainWidgetTranslation);
		
	}	
	
	@Test
	public void testWidgetSetTranslationText() {
		MetaModel mm = MetaModelRegistry.getMetaModel();
		WidgetType wType = mm.findWidgetType(WidgetLibraryConstants.XGUI, "Label");
		WidgetFactory wFactory = new WidgetFactory();
		Widget widget = wFactory.create(wType);

		ITranslationManager manager = TranslationCore.getTranslationManager(getOfsProject().getProject());
		ITranslation translation = manager.getTranslation(widget);
		Assert.assertTrue("Label should have translation support", translation != null);
		Assert.assertTrue("Wrong translation class", translation instanceof WidgetTranslation);
		
		try {
			translation.setText(ITranslationKind.NAME, Locale.ENGLISH, "test-label");
		} catch (TranslationException ex) {
			Assert.fail("Unexpected exception when changing translation text"+ex.getMessage());
			ex.printStackTrace();
		}
		
		String text = null;
		try {
			text = translation.getText(ITranslationKind.NAME, Locale.ENGLISH);
		} catch (TranslationException ex) {
			Assert.fail("Unexpected exception when reading translation text"+ex.getMessage());
			ex.printStackTrace();
		}
		Assert.assertTrue("Retrieve wrong translation", "test-label".equals(text));
		
		
		Property tid = widget.findProperty("tid");
		Assert.assertTrue("Cannot find the tid property", tid != null);
		Assert.assertTrue("The tid value must be defined", StringUtils.isNotBlank(tid.getValue()));
	}	
	
	@Test
	public void testDomainWidgetSetTranslationText() {
		
		MetaModel mm = MetaModelRegistry.getMetaModel();
		WidgetType wType = mm.findWidgetType(WidgetLibraryConstants.XGUI, "Module");
		WidgetFactory wFactory = new WidgetFactory();
		Widget module = wFactory.create(wType);
		module.setPropertyValue("domainEntity", "dataset");

		mm = MetaModelRegistry.getMetaModel();
		wType = mm.findWidgetType(WidgetLibraryConstants.XGUI, "LabelDomain");
		wFactory = new WidgetFactory();
		Widget widget = wFactory.create(wType);
		widget.setPropertyValue("domainAttribute", "attribute");
		
		module.getContents().add(widget);

		ITranslationManager manager = TranslationCore.getTranslationManager(getOfsProject().getProject());
		ITranslation translation = manager.getTranslation(widget);
		Assert.assertTrue("Label should have translation support", translation != null);
		Assert.assertTrue("Wrong translation class", translation instanceof DomainWidgetTranslation);
		
		try {
			translation.setText(ITranslationKind.NAME, Locale.ENGLISH, "test-label");
		} catch (TranslationException ex) {
			Assert.fail("Unexpected exception when changing translation text"+ex.getMessage());
			ex.printStackTrace();
		}
		
		String text = null;
		try {
			text = translation.getText(ITranslationKind.NAME, Locale.ENGLISH);
		} catch (TranslationException ex) {
			Assert.fail("Unexpected exception when reading translation text"+ex.getMessage());
			ex.printStackTrace();
		}
		Assert.assertTrue("Retrieve wrong translation", "test-label".equals(text));
		
		Property tid = widget.findProperty("tid");
		Assert.assertTrue("Cannot find the tid property", tid != null);
		Assert.assertTrue("The tid value must be defined", StringUtils.isNotBlank(tid.getValue()));
	}	
	
}
