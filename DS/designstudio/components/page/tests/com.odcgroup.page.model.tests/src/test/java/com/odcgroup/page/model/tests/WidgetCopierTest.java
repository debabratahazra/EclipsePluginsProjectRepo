package com.odcgroup.page.model.tests;

import org.junit.Assert;
import org.junit.Test;

import com.odcgroup.page.metamodel.MetaModel;
import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.metamodel.WidgetLibrary;
import com.odcgroup.page.metamodel.WidgetLibraryConstants;
import com.odcgroup.page.metamodel.WidgetType;
import com.odcgroup.page.metamodel.WidgetTypeConstants;
import com.odcgroup.page.metamodel.util.MetaModelRegistry;
import com.odcgroup.page.model.Model;
import com.odcgroup.page.model.ModelFactory;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Translation;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.util.UniqueIdGenerator;
import com.odcgroup.page.model.util.WidgetCopier;
import com.odcgroup.page.model.util.WidgetFactory;

/**
 * DS-3624, DS-3623, DS-3622
 * @author atr
 */
public class WidgetCopierTest {
	
	
	/**
	 * Check the unique-id is regenerated when a widget is copied 
	 * @see JIRA DS-3624,DS-3622
	 */
	@Test
	public void testWidgetCopyAndUniqueId() {

		MetaModel mm = MetaModelRegistry.getMetaModel();
		
		for (WidgetLibrary wl : mm.getWidgetLibraries()) {
			
			for (WidgetType wt : wl.getWidgetTypes()) {
				
				WidgetFactory wf = new WidgetFactory();
				Widget widget = wf.create(wt);
				
				// do the test only if the widget have an ID property
				Property idProp = widget.findProperty(PropertyTypeConstants.ID);
				if (idProp != null) {
					
					// generate a unique id
					String id = UniqueIdGenerator.generateId(widget);
					idProp.setValue(id);
					//ds-4068
					if (widget.getTypeName().equals(WidgetTypeConstants.TEXTFIELD)) {
						widget.setPropertyValue(PropertyTypeConstants.DOMAIN_ATTRIBUTE, "dd");
					}
					// copy the widget
					Widget copy = WidgetCopier.copy(widget);
					
					// check the id has been regenerated if it must be regenerated.

					if (UniqueIdGenerator.generateIdForWidgets.contains(wt.getName())) {
						Property copyIdProp = copy.findProperty(PropertyTypeConstants.ID);
						Assert.assertTrue("The ID property is lost during the copy", copyIdProp != null);		
						String copyId = copyIdProp.getValue();
						String typeName = widget.getTypeName();
						if (widget.isDomainWidget()) {				
							Assert.assertTrue("The ID property has been regenerated during the copy", id.equals(copyId));
						} else if (typeName.equals(WidgetTypeConstants.BOX) || typeName.equals(WidgetTypeConstants.LABEL) || typeName.equals(WidgetTypeConstants.LABEL_DOMAIN)) {
								// for those widget, id id exist but must be initialized with the empty string.
								// reason: this id is never written in the XSP
							Assert.assertEquals("The ID values should be the same", copy.getID(), "");
						} else {
							Assert.assertFalse("The ID property has not been regenerated during the copy", id.equals(copyId));
							Assert.assertEquals("The ID values should be the same", copy.getID(), copyId);
						}
					}
				}

			}
		}
		
	}
	
	/**
	 * Check the unique-id is regenerated when a widget is copied 
	 * @see JIRA DS-3624,DS-3622
	 */
	@Test
	public void testWidgetCopyWithoutRegenerateWidgetID() {

		MetaModel mm = MetaModelRegistry.getMetaModel();
		
		for (WidgetLibrary wl : mm.getWidgetLibraries()) {
			
			for (WidgetType wt : wl.getWidgetTypes()) {
				
				WidgetFactory wf = new WidgetFactory();
				Widget widget = wf.create(wt);
				
				// do the test inly if the widget have an ID property
				Property idProp = widget.findProperty(PropertyTypeConstants.ID);
				if (idProp != null) {
					
					// get the expected unique id
					String expectedID = widget.getPropertyValue(PropertyTypeConstants.ID);
					idProp.setValue(expectedID);
				
					// copy the widget
					Widget copy = WidgetCopier.copy(widget, false);
					
					// check the id has been regenerated if it must be regenerated.
					if (UniqueIdGenerator.generateIdForWidgets.contains(wt.getName())) {
						Property copyIdProp = copy.findProperty(PropertyTypeConstants.ID);
						Assert.assertTrue("The ID property is lost during the copy", copyIdProp != null);
						
						String copyId = copyIdProp.getValue();
						Assert.assertTrue("The ID property has not been regenerated during the copy", expectedID.equals(copyId));
						Assert.assertEquals("The ID values should be the same", copy.getID(), copyId);
					}
				}

			}
		}
		
	}	

	/**
	 * Check translations are not lost when a widget is copied. This test concern
	 * the low level API.
	 * 
	 * @see JIRA DS-3622
	 */
	@Test
	public void testWidgetCopyAndTranslation() {
		
		MetaModel mm = MetaModelRegistry.getMetaModel();
		for (WidgetLibrary wl : mm.getWidgetLibraries()) {
			for (WidgetType wt : wl.getWidgetTypes()) {
				if (wt.translationSupported()) {
					
					WidgetFactory wf = new WidgetFactory();
					Widget widget = wf.create(wt);
					
					// add a label to the widget
					Translation label = ModelFactory.eINSTANCE.createTranslation();
					label.setLanguage("en");
					label.setMessage("label");
					widget.getLabels().add(label);
					
					// add a toolTip to the widget
					Translation toolTip = ModelFactory.eINSTANCE.createTranslation();
					toolTip.setLanguage("en");
					toolTip.setMessage("tooltip");
					widget.getToolTips().add(toolTip);
					
					// set a translation id
					long tid = 1234;
					widget.setTranslationId(tid);
					Assert.assertTrue("Original translation id has not been memorize", widget.getTranslationId() == tid);
					
					// copy the widget
					Widget copyWidget = WidgetCopier.copy(widget);

					// check tid copy
					long copyTID = copyWidget.getTranslationId();
					Assert.assertTrue("The translation id has not been regenerated during the copy", copyTID != tid);
					Assert.assertTrue("The translation id has not been set to zero during the copy", copyTID > 0);
					
					// check copied label
					Assert.assertTrue("The number of copied labels is wrong", copyWidget.getLabels().size() == 1);
					Translation copyLabel = copyWidget.getLabels().get(0);
					Assert.assertTrue("The language of the copied label is wrong", "en".equals(copyLabel.getLanguage()));
					Assert.assertTrue("The message of the copied label is wrong", "label".equals(copyLabel.getMessage()));
					
					// check copied toolTip
					Assert.assertTrue("The number of copied tooltips is wrong", copyWidget.getToolTips().size() == 1);
					Translation copyToolTtip = copyWidget.getToolTips().get(0);
					Assert.assertTrue("The language of the copied label is wrong", "en".equals(copyToolTtip.getLanguage()));
					Assert.assertTrue("The message of the copied label is wrong", "tooltip".equals(copyToolTtip.getMessage()));
				}				
			}
		}
	}

	/**
	 * Check the model reference is not lost when it is copied
	 * @see JIRA DS-3623
	 */
	@Test
	public void testWidgetCopyAndModelReference() {

		// build a include widget and copy it.
		MetaModel mm = MetaModelRegistry.getMetaModel();
		
		// make a module and bind it to a dataset
		WidgetType wType = mm.findWidgetType(WidgetLibraryConstants.XGUI, WidgetTypeConstants.INCLUDE);
		WidgetFactory wFactory = new WidgetFactory();
		Widget include = wFactory.create(wType);
		
		Property p = include.findProperty(PropertyTypeConstants.INCLUDE_SOURCE);
		Assert.assertTrue("Property IncludeSrc is missing", p != null);
		
		Model model = ModelFactory.eINSTANCE.createModel();
		p.setModel(model);
		
		Widget copyInclude = WidgetCopier.copy(include);
		Property src = copyInclude.findProperty(PropertyTypeConstants.INCLUDE_SOURCE);
		Assert.assertTrue("Property IncludeSrc is not copied", src != null);
		
		Model copyModel = src.getModel();
		Assert.assertTrue("Model reference is not copied", copyModel != null);
		
	}

	/**
	 * Check widgets are copied across different/same Module
	 * @see JIRA DS-6024
	 */
	@Test
	public void testCopyWidgetAcrossModule() {
		
		MetaModel metaModel = MetaModelRegistry.getMetaModel();
		WidgetFactory wFactory = new WidgetFactory();
		WidgetType moduleType = metaModel.findWidgetType("xgui", "Module");
		Assert.assertTrue("The WidgetType [Module] does not exists in library xgui", moduleType != null);
		
		Widget moduleSrc = wFactory.create(moduleType);
		Assert.assertTrue("The Widget [Module] cannot be created", moduleSrc != null);	

		// prepare data
		WidgetType wType = metaModel.findWidgetType(WidgetLibraryConstants.XGUI, "SearchField");
		Widget searchWidget = wFactory.create(wType);
		moduleSrc.getContents().add(searchWidget);
		Assert.assertTrue("The Widget [Module] has no child", !moduleSrc.getContents().isEmpty());	
				
		Widget moduleDest = wFactory.create(moduleType);
		Assert.assertTrue("The Source Widget [Module] cannot be created", moduleDest != null);	
		
		Widget copySearchWidget = WidgetCopier.copy(searchWidget);
		moduleDest.getContents().add(copySearchWidget);
		Assert.assertTrue("The Target Widget [Module] has no child", !moduleDest.getContents().isEmpty());	
	}

}
