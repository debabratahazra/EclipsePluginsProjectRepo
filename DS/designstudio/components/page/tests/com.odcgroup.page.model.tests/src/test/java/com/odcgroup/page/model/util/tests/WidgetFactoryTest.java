package com.odcgroup.page.model.util.tests;

import java.util.Map;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.junit.Assert;
import org.junit.Test;

import com.odcgroup.page.metamodel.EventType;
import com.odcgroup.page.metamodel.FunctionType;
import com.odcgroup.page.metamodel.MetaModel;
import com.odcgroup.page.metamodel.ParameterType;
import com.odcgroup.page.metamodel.PropertyType;
import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.metamodel.SnippetType;
import com.odcgroup.page.metamodel.WidgetLibrary;
import com.odcgroup.page.metamodel.WidgetLibraryConstants;
import com.odcgroup.page.metamodel.WidgetTemplate;
import com.odcgroup.page.metamodel.WidgetType;
import com.odcgroup.page.metamodel.util.MetaModelRegistry;
import com.odcgroup.page.model.Event;
import com.odcgroup.page.model.ModelFactory;
import com.odcgroup.page.model.Parameter;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Snippet;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.util.WidgetFactory;

/**
 * Tests the WidgetFactory.
 * 
 * @author Gary Hayes
 */
public class WidgetFactoryTest {

	/**
	 * Creates the Widget from the WidgetTemplate for each template.
	 */
	@Test
	public void testCreateWidgetFromTemplate() {
		MetaModel mm = MetaModelRegistry.getMetaModel();
		for (WidgetLibrary wl : mm.getWidgetLibraries()) {
			for (WidgetTemplate wt : wl.getWidgetTemplates()) {
				WidgetFactory wf = new WidgetFactory();
				Widget w = wf.create(wt);
				Assert.assertTrue(w != null);
			}
		}
	}
	
	/**
	 * Creates the Widget from the WidgetType for each type.
	 */
	@Test
	public void testCreateWidgetFromType() {
		MetaModel mm = MetaModelRegistry.getMetaModel();
		for (WidgetLibrary wl : mm.getWidgetLibraries()) {
			for (WidgetType wt : wl.getWidgetTypes()) {
				WidgetFactory wf = new WidgetFactory();
				Widget w = wf.create(wt);
				Assert.assertTrue(w != null);	
				Assert.assertTrue(w.getProperties().size() == wt.getAllPropertyTypes().size());
			}
		}
	}	
	
	
	// check that all mandatory properties are added to the widget and assigned
	// with the default values.
	@Test
	public void testUpdateOfNewCreatedWidget() {
		
		MetaModel mm = MetaModelRegistry.getMetaModel();
		
		for (WidgetLibrary wl : mm.getWidgetLibraries()) {

			String libName = wl.getName();
			
			for (WidgetType wt : wl.getWidgetTypes()) {
				
				String widgetName = wt.getName();
				
				Widget widget = ModelFactory.eINSTANCE.createWidget();
				widget.setTypeName(widgetName);
				widget.setLibraryName(wt.getLibrary().getName());
				WidgetFactory wf = new WidgetFactory();
				wf.update(widget);
				
				// check number of properties
				Assert.assertTrue("New created widget ["+libName+"#"+wt.getName()+"] lacks some mandatory properties",
						widget.getProperties().size() == wt.getAllPropertyTypes().size());
				
				// check default values
				for (Property property : widget.getProperties()) {
					String propName = property.getTypeName();
					PropertyType pt = wt.findPropertyType(propName, true);
					Assert.assertTrue("The property ["+libName+"#"+wt.getName()+"#"+property.getTypeName()+"] has the wrong default value", 
							property.getValue().equals(pt.getDefaultValue())); 
				}
				
				// check other associations
				Assert.assertTrue("New created widget ["+libName+"#"+wt.getName()+"] shouldn't have events", 
						widget.getEvents().size() == 0);

				// check other associations
				Assert.assertTrue("New created widget ["+libName+"#"+wt.getName()+"] shouldn't have snippets", 
						widget.getSnippets().size() == 0);
				
				// check other associations
				Assert.assertTrue("New created widget ["+libName+"#"+wt.getName()+"] shouldn't have children", 
						widget.getContents().size() == 0);

			}
		}
	}
	
	
	// check that all mandatory properties are added to the event and assigned
	// with the default value.
	@Test
	public void testUpdateOfNewCreatedWidgetSnippet() {
		
		MetaModel mm = MetaModelRegistry.getMetaModel();
		
		for (SnippetType st : mm.getSnippetModel().getSnippets()) {
			
			String snippetName = st.getName();
			Snippet snippet = ModelFactory.eINSTANCE.createSnippet();
			snippet.setTypeName(snippetName);

			WidgetFactory wf = new WidgetFactory();
			WidgetType wType = mm.findWidgetType("xgui", "Box");
			Widget widget = ModelFactory.eINSTANCE.createWidget();
			widget.setLibraryName(wType.getLibrary().getName());
			widget.setTypeName(wType.getName());
			widget.getSnippets().add(snippet);
			wf.update(widget);
			
			// check number of properties
			Assert.assertTrue("New created snippet ["+snippetName+"] lacks some mandatory properties",
					snippet.getProperties().size() == st.getPropertyTypes().size());

			// check default values
			for (Property property : snippet.getProperties()) {
				String propName = property.getTypeName();
				for (PropertyType pt : st.getPropertyTypes()) {
					if (pt.getName().equals(propName)) {
						Assert.assertTrue("The snippet property ["+snippetName+"#"+propName+"] has the wrong default value", 
								property.getValue().equals(pt.getDefaultValue()));
					}
				}
			}

		}
	}
	
	// check that all mandatory properties are added to the snippet and assigned
	// with the default value.
	@Test
	public void testUpdateOfNewCreatedWidgetEvent() {
		
		MetaModel mm = MetaModelRegistry.getMetaModel();
		
		for (EventType et : mm.getEventModel().getEventTypes()) {
			
			String eventName = et.getName();
			Event event = ModelFactory.eINSTANCE.createEvent(eventName);
			event.setEventName(eventName);
			
			for (FunctionType ft : MetaModelRegistry.getFunctionsFor(et)) {
				
				event.setFunctionName(ft.getName());

				WidgetFactory wf = new WidgetFactory();
				WidgetType wType = mm.findWidgetType("xgui", "Box");
				Widget widget = ModelFactory.eINSTANCE.createWidget();
				widget.setLibraryName(wType.getLibrary().getName());
				widget.setTypeName(wType.getName());
				widget.getEvents().add(event);
				wf.update(widget);
				
				// check number of properties
				Assert.assertTrue("New created event ["+eventName+"] lacks some mandatory properties",
						event.getProperties().size() == et.getPropertyTypes().size());
		
				// check number of parameters
				Map<String, ParameterType> parameterTypes = MetaModelRegistry.findFunctionParameterTypes(event.getFunctionName());
				Assert.assertEquals("New created event ["+eventName+"] lacks some mandatory parameters",
						parameterTypes.values().size(), event.getParameters().size());
	
				// check default properties values
				for (Property property : event.getProperties()) {
					String propName = property.getTypeName();
					for (PropertyType pt : et.getPropertyTypes()) {
						if (pt.getName().equals(propName)) {
							Assert.assertEquals("The event property ["+eventName+"#"+propName+"] has the wrong default value", 
									pt.getDefaultValue(), property.getValue());
						}
					}
				}
				
				// check default parameters values
				for (Parameter param : event.getParameters()) {
					String paramName = param.getName();
					ParameterType pt = parameterTypes.get(paramName);
					// DS-5231 Unstable unit test stating "The event parameter [OnClick#delay] has the wrong default value expected:<[]> but was:<[0]>"
					if ("delay".equals(paramName)) { 
						continue;
					}
					Assert.assertEquals("The event parameter ["+eventName+"#"+paramName+"] has the wrong default value", 
									pt.getDefaultValue(), param.getValue());
				}

			}
		}
		
		
	}

	/**
	 * Ensure the widget instance is up to date with its type.
	 */
	@Test
	public void testUpdateWidgetFromType() {
		
		// prepare data
		MetaModel mm = MetaModelRegistry.getMetaModel();
		WidgetType wType = mm.findWidgetType(WidgetLibraryConstants.XGUI, "SearchField");
		WidgetFactory wFactory = new WidgetFactory();
		Widget widget = wFactory.create(wType);
		
		Property p = widget.findProperty(PropertyTypeConstants.REQUIRED);
		Assert.assertTrue("Property ["+PropertyTypeConstants.REQUIRED+"] is missing", p!= null);
		
		// duplicate the property and update the widget;
		Property duplicate = (Property)EcoreUtil.copy(p);
		widget.getProperties().add(duplicate);
		
		wFactory.update(widget);
		
		int count = 0;
		for (Property prop : widget.getProperties()) {
			if (prop.getTypeName().equals(PropertyTypeConstants.REQUIRED)) {
				count++;
			}
		}
		Assert.assertTrue("The duplicated property has not been removed", count == 1);
		
		
	}
	
	@Test
	public void testNillablePropertyCanBeSet_DS3256() throws Exception {
		// prepare data
		MetaModel mm = MetaModelRegistry.getMetaModel();
		WidgetType wType = mm.findWidgetType(WidgetLibraryConstants.XGUI, "CaldateField");
		WidgetFactory wFactory = new WidgetFactory();
		Widget widget = wFactory.create(wType);
		
		Property p = widget.findProperty("nillable");
		
		Assert.assertNotNull("Property [nillable] is missing", p);
		
		String expectedValue = p.getType().getDefaultValue();
		Assert.assertEquals("Default value was not false",p.getValue(), expectedValue);
		
		p.setValue(true);
		expectedValue = "true";
		Assert.assertEquals("The value was not true", p.getValue(), expectedValue);
	}
	
}