package com.odcgroup.page.validation.internal.constraint;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.odcgroup.page.metamodel.EventType;
import com.odcgroup.page.metamodel.MetaModel;
import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.metamodel.WidgetLibrary;
import com.odcgroup.page.metamodel.WidgetType;
import com.odcgroup.page.metamodel.WidgetTypeConstants;
import com.odcgroup.page.metamodel.util.MetaModelRegistry;
import com.odcgroup.page.model.Event;
import com.odcgroup.page.model.Model;
import com.odcgroup.page.model.ModelFactory;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Snippet;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.util.UniqueIdGenerator;
import com.odcgroup.page.model.util.WidgetFactory;
import com.odcgroup.page.validation.Activator;

public class PagePropertyValidatorTest {
	
	private PageValidationListener listener;
	private PagePropertyValidator validator;
	
	// this stores all statuses that are sent to the listener
	private MultiStatus multiStatus;

	@Before
	public void setUp() throws Exception {
		listener = new PageValidationListener() {
			public void onValidation(IStatus status, Event event) {}
			
			public void onValidation(IStatus status, Property property) {}
			
			public void onValidation(IStatus status) {
				multiStatus.add(status);
			}
		};
		validator = new PagePropertyValidator(listener);
	}
	
	@Test
	public void testAccept() {
		
		// start with an empty status list
		multiStatus = new MultiStatus(Activator.PLUGIN_ID, IStatus.OK, null, null);
		
		// DS-3583: Test validation error for missing ids
		for(WidgetLibrary lib : MetaModelRegistry.getMetaModel().getWidgetLibraries()) {
			for(WidgetType widgetType : lib.getWidgetTypes()) {

				// start with an empty status list
				multiStatus = new MultiStatus(Activator.PLUGIN_ID, IStatus.OK, null, null);
				
				// Create a widget instance. Its ID if it exists must be set.
				Widget widget = new WidgetFactory().create(widgetType);
			
				// retrieve the property ID
				Property idProp = widget.findProperty(PropertyTypeConstants.ID);
				if (idProp != null) {
					// this widget has an ID
					
					if (UniqueIdGenerator.generateIdForWidgets.contains(widget.getTypeName())) {
						// For these widgets the factory must generate an ID

						// check the ID is not blank (initialized)
						String typeName = widgetType.getName();
						if (typeName.equals(WidgetTypeConstants.BOX) || typeName.equals(WidgetTypeConstants.LABEL) || typeName.equals(WidgetTypeConstants.LABEL_DOMAIN)) {
							// for those widget, id id exist but must be initialized with the empty string.
							// reason: this id is never written in the XSP
							Assert.assertTrue("The ID of the widget ["+typeName+"] must be initialized with the empty string", 
									   widget.getID().length() == 0);
						} else {
							Assert.assertTrue("The ID of the widget ["+typeName+"] is not initialized", 
								   StringUtils.isNotBlank(widget.getID()));
						}
						
						// validate the id
						validator.accept(idProp);
						if ( ! multiStatus.isOK()) {
						
							// the property value, is not valid
							for(IStatus status : multiStatus.getChildren()) {
								if(status.getSeverity()==IStatus.ERROR && status.getMessage().equals(PagePropertyValidator.MESSAGE_PROPERTY_ID)) {
									Assert.assertFalse("The ID of the widget ["+widgetType.getName()+"] is not valid", true);
								}
							}
						}
						
					} else {
 
						/*
						 * For these widgets the factory do not generate an ID
						 * The ID value depends of some properties of the widget container.
						 * So by default the ID must set to the empty value by the factory.
						 */
						Assert.assertTrue("The ID of the widget ["+widgetType.getName()+"] must not be initialized", 
								StringUtils.length(widget.getID()) == 0);
						
					}
					
				} else {
					// this widget doesn't support ID
					
					// check the ID is not initialized
					Assert.assertTrue("The ID of the widget ["+widgetType.getName()+"] must be empty", 
							   StringUtils.length(widget.getID()) == 0);
				}
				
			}
		}
	
	}
	
	/**
	 * This test ensure that a property of an event does not generated an NPE. 
	 */
	@Test
	public void testDS3618() {
		
		// create events with properties set to their default values
		MetaModel metamodel = MetaModelRegistry.getMetaModel();
		for (EventType et : metamodel.getEventModel().getEventTypes()) {
			Event event = ModelFactory.eINSTANCE.createEvent(et.getName());
			for (Property property : event.getProperties()) {
				try {
					// set the default value of the property.
					property.resetValue();
					multiStatus = new MultiStatus(Activator.PLUGIN_ID, IStatus.OK, null, null);
					// try to validate it.
					validator.accept(property);
					Assert.assertTrue("Event property ["+property.getTypeName()+"] has a wrong value", multiStatus.isOK());
				} catch (Exception ex) {
					ex.printStackTrace();
					Assert.assertFalse("validation of the event property ["+property.getTypeName()+"]" + ex.getMessage(), true);
				}
			}
			
		}
	}

	@Test
	public void testcheckSelectedTab_DS4384() {
		// create snippet with properties
		Model searchModel = ModelFactory.eINSTANCE.createModel();
		Snippet searchSnippet = ModelFactory.eINSTANCE.createSnippet();
		Snippet tabDisplaySnippet = ModelFactory.eINSTANCE.createSnippet();
		
		Property selectedTabProperty = ModelFactory.eINSTANCE.createProperty();
		selectedTabProperty.setTypeName(PropertyTypeConstants.SELECTED_TAB);
		selectedTabProperty.setValue("nonExistentTab");
		
		tabDisplaySnippet.getProperties().add(selectedTabProperty);
		
		Property outputModule = ModelFactory.eINSTANCE.createProperty();
		outputModule.setTypeName(PropertyTypeConstants.QUERY_OUTPUTMODULE);
		outputModule.setModel(searchModel);
				
		searchSnippet.getContents().add(tabDisplaySnippet);
		searchSnippet.getProperties().add(outputModule);
		
		multiStatus = new MultiStatus(Activator.PLUGIN_ID, IStatus.OK, null, null);
		// try to validate it.
		validator.accept(selectedTabProperty);
		Assert.assertFalse(multiStatus.isOK());
		Assert.assertEquals("The tab nonExistentTab cannot be found in the search module .", multiStatus.getChildren()[10].getMessage());
	}
}
