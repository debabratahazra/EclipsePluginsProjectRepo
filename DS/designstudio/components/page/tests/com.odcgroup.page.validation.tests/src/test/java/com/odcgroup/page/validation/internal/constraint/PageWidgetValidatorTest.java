package com.odcgroup.page.validation.internal.constraint;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.common.util.URI;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.metamodel.WidgetTypeConstants;
import com.odcgroup.page.model.Event;
import com.odcgroup.page.model.Model;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Widget;
import com.odcgroup.workbench.core.IOfsModelResource;
import com.odcgroup.workbench.core.InvalidMetamodelVersionException;
import com.odcgroup.workbench.core.repository.ModelNotFoundException;
import com.odcgroup.workbench.core.tests.util.AbstractDSUnitTest;

public class PageWidgetValidatorTest extends AbstractDSUnitTest {

	@Before
	public void setUp() throws Exception {
		createModelsProject();
	}

	@After
	public void tearDown() throws Exception {
		deleteModelsProjects();
	}
	
	class PageValidationListenerTest implements PageValidationListener {
		@Override
		public void onValidation(IStatus status) {
		}
		@Override
		public void onValidation(IStatus status, Property property) {
		}
		@Override
		public void onValidation(IStatus status, Event event) {
		}
	};

	@Test
	public void testDS5553_checkboxValidationWithDataset() throws ModelNotFoundException, IOException, InvalidMetamodelVersionException {
		// Given
		copyResourceInModelsProject("domain/ds5553/DS5553.domain");
		copyResourceInModelsProject("module/Default/module/DS5553ModuleWithDataset.module");
		URI uri = URI.createURI("resource:///Default/module/DS5553ModuleWithDataset.module");
		Widget moduleWithDataset = getModuleWidget(uri);
		List<Widget> checkboxes = new ArrayList<Widget>();
		findRadioButton(moduleWithDataset, checkboxes);
		
		PageValidationListenerTest listener = new PageValidationListenerTest();
		
		// When
		for (Widget checkbox: checkboxes) {
			IStatus status = new PageWidgetValidator(listener).checkRadioButtonIsOfValidType(checkbox);
			
			// Then
			Assert.assertTrue("The validation should be ok for this model, but was: " + status.getMessage(), status.isOK());
		}
	}

	@Test
	public void testDS5553_checkboxValidationWithoutDataset() throws ModelNotFoundException, IOException, InvalidMetamodelVersionException {
		// Given
		copyResourceInModelsProject("domain/ds5553/DS5553.domain");
		copyResourceInModelsProject("module/Default/module/DS5553ModuleWithoutDataset.module");
		URI uri = URI.createURI("resource:///Default/module/DS5553ModuleWithoutDataset.module");
		Widget moduleWithDataset = getModuleWidget(uri);
		List<Widget> checkboxes = new ArrayList<Widget>();
		findRadioButton(moduleWithDataset, checkboxes);
		
		PageValidationListenerTest listener = new PageValidationListenerTest();
		
		// When
		for (Widget checkbox: checkboxes) {
			IStatus status = new PageWidgetValidator(listener).checkRadioButtonIsOfValidType(checkbox);
			
			// Then
			Assert.assertTrue("The validation should be ok for this model", status.isOK());
		}
	}

	private Widget getModuleWidget(URI uri) throws ModelNotFoundException,
			IOException, InvalidMetamodelVersionException {
		IOfsModelResource ofsResource = getOfsProject().getOfsModelResource(uri);
		Model model = (Model) ofsResource.getEMFModel().get(0);
		Widget moduleWithDataset = model.getWidget();
		Assert.assertNotNull("Cannot load the module " + uri, moduleWithDataset);
		return moduleWithDataset;
	}
	
	private void findRadioButton(Widget parent, List<Widget> radiobuttons) {
		for (Widget child: parent.getContents()) {
			if (child.getTypeName().equals(WidgetTypeConstants.RADIO_BUTTON)) {
				radiobuttons.add(child);
			}
			findRadioButton(child, radiobuttons);
		}
	}
	
	private void findIncludeXSP(Widget parent, List<Widget> includeXSP) {
		for (Widget child: parent.getContents()) {
			if (child.getTypeName().equals(WidgetTypeConstants.INCLUDE_XSP)) {
				includeXSP.add(child);
			}
			findIncludeXSP(child, includeXSP);
		}
	}
	@Test
	public void testDS6063_validateModulewithIncludeXSP() throws ModelNotFoundException, IOException, InvalidMetamodelVersionException{
		copyResourceInModelsProject("module/Default/module/DS6063.module");
		URI uri = URI.createURI("resource:///Default/module/DS6063.module");
		Widget moduleWithDataset = getModuleWidget(uri);

		//Ensure that module is of type Regular.
		Assert.assertTrue("none".equals(moduleWithDataset.getPropertyValue(PropertyTypeConstants.SEARCH)));
		
		//Ensure that module containment is not container.
		Assert.assertTrue(!"Container".equals(moduleWithDataset.getPropertyValue(PropertyTypeConstants.MODULE_CONTAINMENT)));

		List<Widget> includeXSPs = new ArrayList<Widget>();
		findIncludeXSP(moduleWithDataset, includeXSPs);
		PageValidationListenerTest listener = new PageValidationListenerTest();
		String errorMessage = "The widget can only be contained in a " +
				"regular Module with container containment";
		// When
		for (Widget includeXSP: includeXSPs) {
			IStatus status = new PageWidgetValidator(listener).checkModuleforIncludeXSP(includeXSP);
			
			Assert.assertFalse("The validation should not be ok for this model, but was: " + status.getMessage(), status.isOK());
			Assert.assertEquals(errorMessage, status.getMessage());
		}
	}
	
}
