package com.odcgroup.page.validation.internal;

import java.util.Locale;

import org.eclipse.core.runtime.IStatus;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.odcgroup.page.metamodel.MetaModel;
import com.odcgroup.page.metamodel.WidgetLibraryConstants;
import com.odcgroup.page.metamodel.WidgetType;
import com.odcgroup.page.metamodel.WidgetTypeConstants;
import com.odcgroup.page.metamodel.util.MetaModelRegistry;
import com.odcgroup.page.model.Event;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.util.WidgetFactory;
import com.odcgroup.page.validation.internal.constraint.PageValidationListener;
import com.odcgroup.translation.core.ITranslation;
import com.odcgroup.translation.core.ITranslationKind;
import com.odcgroup.translation.core.ITranslationManager;
import com.odcgroup.translation.core.TranslationCore;
import com.odcgroup.workbench.core.tests.util.AbstractDSUnitTest;
// DS-4758
public class PageWidgetTranslationValidatorTest extends AbstractDSUnitTest {

	class MyValidationListener implements PageValidationListener {
		private IStatus status = null;

		public void onValidation(IStatus status) {
			this.status = status;
		}

		public void onValidation(IStatus status, Property property) {
			this.status = status;
		}

		public void onValidation(IStatus status, Event event) {
			this.status = status;
		}

		public final IStatus getStatus() {
			return status;
		}
	};

	@Before
	public void setUp() throws Exception {
		createModelsProject();
	}

	@After
	public void tearDown() throws Exception {
		deleteModelsProjects();
	}

	private Widget prepareWidget(String widgetTypeName, String text)
			throws Exception {
		// create the widget
		MetaModel metamodel = MetaModelRegistry.getMetaModel();
		WidgetType widgetType = metamodel.findWidgetType(WidgetLibraryConstants.XGUI, widgetTypeName);
		Widget widget = new WidgetFactory().create(widgetType);
		// set the translation
		ITranslationManager manager = TranslationCore.getTranslationManager(getOfsProject().getProject());
		ITranslation translation = manager.getTranslation(widget);
		translation.setText(ITranslationKind.NAME, Locale.ENGLISH, text);
		return widget;
	}

	private IStatus checkValidTranslation(String widgetTypeName) throws Exception {		
		Widget widget = prepareWidget(widgetTypeName, "<rt>the quick fox <p>jumps</p> over the lazy dog</rt>");
		MyValidationListener listener = new MyValidationListener();
		PageWidgetTranslationValidator validator = new PageWidgetTranslationValidator(listener, widget, getOfsProject());
		validator.checkConstraints();
		return listener.getStatus();
	}

	private IStatus checkInvalidTranslation(String widgetTypeName) throws Exception {
		Widget widget = prepareWidget(widgetTypeName, "<rt>the quick fox <p>jumps<p> over the lazy dog</rt>");
		MyValidationListener listener = new MyValidationListener();
		PageWidgetTranslationValidator validator = new PageWidgetTranslationValidator(listener, widget, getOfsProject());
		validator.checkConstraints();
		return listener.getStatus();
	}

	@Test
	public void testLabelWithValidRichTextTranslationValidation() throws Exception {
		Assert.assertTrue(checkValidTranslation(WidgetTypeConstants.LABEL) == null);
	}

	@Test
	public void testLabelWithInvalidRichTextTranslationValidation() throws Exception {
		Assert.assertTrue(checkInvalidTranslation(WidgetTypeConstants.LABEL) != null);
	}

	@Test
	public void testButtonWithValidRichTextTranslationValidation() throws Exception {
		Assert.assertTrue(checkValidTranslation(WidgetTypeConstants.BUTTON) == null);
	}

	@Test
	public void testButtonWithInvalidRichTextTranslationValidation() throws Exception {
		Assert.assertTrue(checkInvalidTranslation(WidgetTypeConstants.BUTTON) != null);
	}

	@Test
	public void testCheckBoxWithValidRichTextTranslationValidation() throws Exception {
		Assert.assertTrue(checkValidTranslation(WidgetTypeConstants.CHECKBOX) == null);
	}

	@Test
	public void testCheckboxWithInvalidRichTextTranslationValidation() throws Exception {
		Assert.assertTrue(checkInvalidTranslation(WidgetTypeConstants.CHECKBOX) != null);
	}

	@Test
	public void testRadioButtonWithValidRichTextTranslationValidation() throws Exception {
		Assert.assertTrue(checkValidTranslation(WidgetTypeConstants.RADIO_BUTTON) == null);
	}

	@Test
	public void testRadioButtonWithInvalidRichTextTranslationValidation() throws Exception {
		Assert.assertTrue(checkInvalidTranslation(WidgetTypeConstants.RADIO_BUTTON) != null);
	}

}
