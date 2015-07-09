package com.odcgroup.page.model.tests.translation;

import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.resources.IProject;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.odcgroup.page.metamodel.MetaModel;
import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.metamodel.WidgetLibraryConstants;
import com.odcgroup.page.metamodel.WidgetType;
import com.odcgroup.page.metamodel.WidgetTypeConstants;
import com.odcgroup.page.metamodel.util.MetaModelRegistry;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.translation.DomainWidgetTranslation;
import com.odcgroup.page.model.translation.WidgetTranslation;
import com.odcgroup.page.model.translation.WidgetTranslationProvider;
import com.odcgroup.page.model.util.WidgetFactory;
import com.odcgroup.translation.core.ITranslation;
import com.odcgroup.translation.core.ITranslationKind;
import com.odcgroup.translation.core.ITranslationProvider;
import com.odcgroup.translation.core.TranslationException;
import com.odcgroup.workbench.core.tests.util.AbstractDSUnitTest;

/**
 * @author atr
 */
public class WidgetDomainTranslationTest extends AbstractDSUnitTest {
	
	/** the name of the property that stores the type of a table column */
	private static final String COLUMN_TYPE_PROPERTY = "column-type";	

	@Before
	public void setUp() throws Exception {
		createModelsProject();
		waitForJavaProjectNature(getProject());
		copyResourceInModelsProject("domain/ds3674/DS3674.domain");
	}

	@After
	public void tearDown() throws Exception {
		deleteModelsProjects();
	}

	@Test
	public void testDS3674() {

		MetaModel mm = MetaModelRegistry.getMetaModel();

		// make a module and bind it to a dataset
		WidgetType wType = mm.findWidgetType(WidgetLibraryConstants.XGUI, WidgetTypeConstants.MODULE);
		WidgetFactory wFactory = new WidgetFactory();
		Widget module = wFactory.create(wType);
		module.setPropertyValue(PropertyTypeConstants.DOMAIN_ENTITY, "DS3674:DS1");

		// make an attribute without binding it to a dataset's property
		wType = mm.findWidgetType(WidgetLibraryConstants.XGUI, WidgetTypeConstants.ATTRIBUTE);
		Widget attribute = wFactory.create(wType);
		attribute.setPropertyValue(PropertyTypeConstants.DOMAIN_ATTRIBUTE, "");

		module.getContents().add(attribute);

		WidgetTranslationProvider provider = new WidgetTranslationProvider();
		ITranslation translation = provider.getTranslation(getProject(), attribute);

		Assert.assertTrue("Invalid Translation Wrapper", translation instanceof DomainWidgetTranslation);

		// retrieve the english text
		try {
			String text = translation.getText(ITranslationKind.NAME, Locale.ENGLISH);
			Assert.assertTrue("Translation must not be defined", StringUtils.isEmpty(text));
		} catch (TranslationException ex) {
			// ignore, the exception is normal as the widget
			// cannot find the underlying dataset's property.
		}

	}
	
	@Test
	public void testDs3691_DomainTableColumn() {

		MetaModel mm = MetaModelRegistry.getMetaModel();
		
		// make a module and bind it to a dataset
		WidgetType wType = mm.findWidgetType(WidgetLibraryConstants.XGUI, WidgetTypeConstants.MODULE);
		WidgetFactory wFactory = new WidgetFactory();
		Widget module = wFactory.create(wType);
		module.setPropertyValue(PropertyTypeConstants.DOMAIN_ENTITY, "DS3674:DS1");

		// make a computed table-column & bind it to a dataset's property
		wType = mm.findWidgetType(WidgetLibraryConstants.XGUI, WidgetTypeConstants.TABLE_COLUMN);
		Widget tableColumn = new WidgetFactory().create(wType);
		tableColumn.setPropertyValue(COLUMN_TYPE_PROPERTY, "domain");
		tableColumn.setPropertyValue(PropertyTypeConstants.DOMAIN_ATTRIBUTE, "attr1");

		module.getContents().add(tableColumn);
		
		WidgetTranslationProvider provider = new WidgetTranslationProvider();
		ITranslation translation = provider.getTranslation(getProject(), tableColumn);

		Assert.assertTrue("Invalid Translation Wrapper for domain table-column " + WidgetTypeConstants.TABLE_COLUMN,
				translation instanceof DomainWidgetTranslation);

	}	

	@Test
	public void testDs3691_ComputedTableColumn() {

		MetaModel mm = MetaModelRegistry.getMetaModel();
		
		// make a module and bind it to a dataset
		WidgetType wType = mm.findWidgetType(WidgetLibraryConstants.XGUI, WidgetTypeConstants.MODULE);
		WidgetFactory wFactory = new WidgetFactory();
		Widget module = wFactory.create(wType);
		module.setPropertyValue(PropertyTypeConstants.DOMAIN_ENTITY, "DS3674:DS1");

		// make a computed table-column without binding it to a dataset's property
		wType = mm.findWidgetType(WidgetLibraryConstants.XGUI, WidgetTypeConstants.TABLE_COLUMN);
		Widget tableColumn = new WidgetFactory().create(wType);
		tableColumn.setPropertyValue(COLUMN_TYPE_PROPERTY, "computed");
		tableColumn.setPropertyValue(PropertyTypeConstants.DOMAIN_ATTRIBUTE, "");

		module.getContents().add(tableColumn);
		
		WidgetTranslationProvider provider = new WidgetTranslationProvider();
		ITranslation translation = provider.getTranslation(getProject(), tableColumn);

		Assert.assertTrue("Invalid Translation Wrapper for computed table-column ",
				translation instanceof WidgetTranslation);

	}
	
	@Test
	public void testDs3691_PlaceholderTableColumn() {

		MetaModel mm = MetaModelRegistry.getMetaModel();

		// make a module and bind it to a dataset
		WidgetType wType = mm.findWidgetType(WidgetLibraryConstants.XGUI, WidgetTypeConstants.MODULE);
		WidgetFactory wFactory = new WidgetFactory();
		Widget module = wFactory.create(wType);
		module.setPropertyValue(PropertyTypeConstants.DOMAIN_ENTITY, "DS3674:DS1");

		// make an attribute without binding it to a dataset's property
		wType = mm.findWidgetType(WidgetLibraryConstants.XGUI, WidgetTypeConstants.TABLE_COLUMN);
		Widget tableColumn = wFactory.create(wType);
		tableColumn.setPropertyValue(COLUMN_TYPE_PROPERTY, "placeholder");
		tableColumn.setPropertyValue(PropertyTypeConstants.DOMAIN_ATTRIBUTE, "");

		module.getContents().add(tableColumn);

		WidgetTranslationProvider provider = new WidgetTranslationProvider();
		ITranslation translation = provider.getTranslation(getProject(), tableColumn);

		Assert.assertTrue("Invalid Translation Wrapper for Placeholder tabele-column",
				translation instanceof WidgetTranslation);

	}	

	static boolean isDomainWidgetTranslationProtectedCalled = false;

	@Test
	public void testDS4024_isProtectedCallWhenReadOnlyIsCalled_for_DomainWidgetTranslation() throws TranslationException {
		
		class DomainWidgetTranslation2 extends DomainWidgetTranslation {
			public DomainWidgetTranslation2(ITranslationProvider provider, IProject project, Widget widget, Property dataset,
					Property datasetProperty) {
				super(provider, project, widget, dataset, datasetProperty);
			}
			public boolean isProtected() throws TranslationException {
				isDomainWidgetTranslationProtectedCalled = true;
				return super.isProtected();
			}			
		}
		
		MetaModel mm = MetaModelRegistry.getMetaModel();

		// make a module and bind it to a dataset
		WidgetType wType = mm.findWidgetType(WidgetLibraryConstants.XGUI, WidgetTypeConstants.MODULE);
		WidgetFactory wFactory = new WidgetFactory();
		Widget module = wFactory.create(wType);
		module.setPropertyValue(PropertyTypeConstants.DOMAIN_ENTITY, "DS4024:DS_DomainWidgetTranslation");

		// make an attribute without binding it to a dataset's property
		wType = mm.findWidgetType(WidgetLibraryConstants.XGUI, WidgetTypeConstants.ATTRIBUTE);
		Widget attribute = wFactory.create(wType);
		attribute.setPropertyValue(PropertyTypeConstants.DOMAIN_ATTRIBUTE, "");

		module.getContents().add(attribute);

		WidgetTranslationProvider provider = new WidgetTranslationProvider() {
			protected DomainWidgetTranslation newDomainWidgetTranslation(IProject project, Widget widget,
					Property datasetProperty, Property dataset) {
				return new DomainWidgetTranslation2(this, project, widget, dataset, dataset) {
					public boolean isProtected() throws TranslationException {
						isDomainWidgetTranslationProtectedCalled = true;
						return super.isProtected();
					}			
				};
			}
		};
		
		ITranslation translation = provider.getTranslation(getProject(), attribute);
		Assert.assertTrue("Invalid Translation Wrapper", translation instanceof DomainWidgetTranslation);

		translation.isReadOnly();

		Assert.assertTrue("isProtected should be called when isReadOnly is called", isDomainWidgetTranslationProtectedCalled);
	}

	static boolean isWidgetTranslationProtectedCalled = false;

	@Test
	public void testDS4024_isProtectedCallWhenReadOnlyIsCalled_for_WidgetTranslation() throws TranslationException {

		class WidgetTranslation2 extends WidgetTranslation {
			public WidgetTranslation2(ITranslationProvider provider, IProject project, Widget widget) {
				super(provider, project, widget);
			}
			public boolean isProtected() throws TranslationException {
				isWidgetTranslationProtectedCalled = true;
				return super.isProtected();
			}			
		}

		MetaModel mm = MetaModelRegistry.getMetaModel();
		
		// make a module and bind it to a dataset
		WidgetType wType = mm.findWidgetType(WidgetLibraryConstants.XGUI, WidgetTypeConstants.MODULE);
		WidgetFactory wFactory = new WidgetFactory();
		Widget module = wFactory.create(wType);
		module.setPropertyValue(PropertyTypeConstants.DOMAIN_ENTITY, "DS4024:DS_WidgetTranslation");

		// make a computed table-column without binding it to a dataset's property
		wType = mm.findWidgetType(WidgetLibraryConstants.XGUI, WidgetTypeConstants.TABLE_COLUMN);
		Widget tableColumn = new WidgetFactory().create(wType);
		tableColumn.setPropertyValue(COLUMN_TYPE_PROPERTY, "computed");
		tableColumn.setPropertyValue(PropertyTypeConstants.DOMAIN_ATTRIBUTE, "");

		module.getContents().add(tableColumn);
		
		WidgetTranslationProvider provider = new WidgetTranslationProvider() {
			protected WidgetTranslation newWidgetTranslation(IProject project, Widget widget) {
				return new WidgetTranslation2(this, project, widget) {
					public boolean isProtected() throws TranslationException {
						isDomainWidgetTranslationProtectedCalled = true;
						return super.isProtected();
					}			
				};
			}
		};
		ITranslation translation = provider.getTranslation(getProject(), tableColumn);
		
		translation.isReadOnly();

		Assert.assertTrue("isProtected should be called when isReadOnly is called", isWidgetTranslationProtectedCalled);
	}
	
}
