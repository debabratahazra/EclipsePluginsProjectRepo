package com.odcgroup.page.model.filter.test;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.metamodel.WidgetTypeConstants;
import com.odcgroup.page.model.Model;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.filter.ComboBoxPropertyFilter;
import com.odcgroup.page.model.filter.TextfieldPropertyFilter;
import com.odcgroup.workbench.core.IOfsModelResource;
import com.odcgroup.workbench.core.tests.util.AbstractDSUnitTest;
/**
 * test calss for the widget property filter
 * @author snn
 *
 */
public class WidgetPropertyFilterTest extends AbstractDSUnitTest {

	@Before
	public void setUp() throws Exception {
		createModelsProject();
	}

	@After
	public void tearDown() throws Exception {
		deleteModelsProjects();
	}

	/*
	 * filter the modifiable and editable widget properties if the parent is xtooltip.
	 */
	@Test
	public void testds4682ModifiedEditableWidgetPropertyhidden(){
		
		  copyResourceInModelsProject("fragment/ds4682/DS4682XtoolttipFragment.fragment");
		  Model model = null;
			URI uri = URI.createURI("resource:///ds4682/DS4682XtoolttipFragment.fragment");
			try {
				IOfsModelResource ofsResource = getOfsProject()
						.getOfsModelResource(uri);
				model = (Model) ofsResource.getEMFModel().get(0);
			} catch (Exception ex) {
				ex.printStackTrace();
				Assert.fail("Cannot load the model from the  resource:///ds4682/DS4682XtoolttipFragment.fragment");
			}
		
			if(model!=null){
				
				Widget fragment =model.getWidget();
			   Property frgamentType= fragment.findProperty(PropertyTypeConstants.FRAGMENT_TYPE);
			   String type=frgamentType.getValue();
			   Assert.assertEquals("xtooltip", type);	
				
			   Widget xtooltip= fragment.getContents().get(0);
			   Assert.assertNotNull(xtooltip);
			   Widget grid=xtooltip.getContents().get(0);
			   Assert.assertNotNull(grid);
			   Widget cell0=grid.getContents().get(0);
			   Assert.assertNotNull(cell0);
			   //text field widget
			   Widget textFiled=cell0.getContents().get(0);
			   TextfieldPropertyFilter textFilter=new TextfieldPropertyFilter();
			   List<Property> filterList=  textFilter.filter(textFiled);
			   EList<Property> textFieldProperty= textFiled.getProperties();
			   Assert.assertTrue(textFieldProperty.size()>filterList.size());
			   checkHiddenProperties(filterList, textFieldProperty,textFiled);
			   //combo box
			   Widget cell2=grid.getContents().get(2);
			   Assert.assertNotNull(cell2);
			   Widget comboBox=cell2.getContents().get(0);
			   ComboBoxPropertyFilter  comboFilter=new ComboBoxPropertyFilter();
			   List<Property> comboFilterList=  comboFilter.filter(comboBox);
			   EList<Property> comboProperty= comboBox.getProperties();
			   Assert.assertTrue(comboProperty.size()>comboFilterList.size());
			   checkHiddenProperties(comboFilterList, comboProperty,comboBox);
			   
			}
	}
	
	private void checkHiddenProperties(List<Property> filterList, EList<Property> widgetProperties,Widget widget){
		
		for(Property pro:widgetProperties){
		    if(pro.getTypeName().equals(PropertyTypeConstants.PREVIEW_VALUE)){
		    	Assert.assertFalse(filterList.contains(pro));
				Assert.assertTrue(StringUtils.isEmpty(pro.getValue()));
			   }
			   
            if(pro.getTypeName().equals(PropertyTypeConstants.ACCESS_KEY)){
            	Assert.assertFalse(filterList.contains(pro));
				Assert.assertTrue(StringUtils.isEmpty(pro.getValue()));
			   }
            if(pro.getTypeName().equals(PropertyTypeConstants.TAB_INDEX)){
            	Assert.assertFalse(filterList.contains(pro));
				Assert.assertEquals(0,pro.getIntValue());  
			   }
            if(pro.getTypeName().equals(PropertyTypeConstants.EDITABLE)){
            	Assert.assertFalse(filterList.contains(pro));
				Assert.assertFalse(pro.getBooleanValue());  
			   }
            if(pro.getTypeName().equals(PropertyTypeConstants.EDITABLE_IS_BASED_ON)){
            	Assert.assertFalse(filterList.contains(pro));
				Assert.assertTrue(StringUtils.isEmpty(pro.getValue()));  
			   }
            if(pro.getTypeName().equals(PropertyTypeConstants.CHARS)){
            	Assert.assertFalse(filterList.contains(pro));
				Assert.assertEquals(0,pro.getIntValue());  
			   }
            if(pro.getTypeName().equals(PropertyTypeConstants.REQUIRED)){
            	Assert.assertFalse(filterList.contains(pro));
				Assert.assertFalse(pro.getBooleanValue());
			   }
			   
            if(widget.getTypeName().equals(WidgetTypeConstants.TEXTFIELD)||(widget.getTypeName().equals(WidgetTypeConstants.TEXTAREA))){
            	 if(pro.getTypeName().equals(PropertyTypeConstants.PATTERN_TYPE)){
            		 Assert.assertFalse(filterList.contains(pro));
            		 Assert.assertTrue(StringUtils.isEmpty(pro.getValue()));  
  			   }
            }
		   }
		
		
	}
	
	@Test
	public void testds5201PropertiesOfAnAttributeInsideFrgament(){

		copyResourceInModelsProject("fragment/widgets/textField/DS5201QuantityEdit.fragment");
		Model model = null;
		URI uri = URI.createURI("resource:///widgets/textField/DS5201QuantityEdit.fragment");
		try {
			IOfsModelResource ofsResource = getOfsProject()
					.getOfsModelResource(uri);
			model = (Model) ofsResource.getEMFModel().get(0);
		} catch (Exception ex) {
			ex.printStackTrace();
			Assert.fail("Cannot load the model from the  resource:///widgets/textField/DS5201QuantityEdit.fragment");
		}

		if(model!=null){

			Widget fragment =model.getWidget();
			Widget box= fragment.getContents().get(0);
			Assert.assertNotNull(box);
			Widget grid=box.getContents().get(0);
			Assert.assertNotNull(grid);
			Widget cell1=grid.getContents().get(1);
			Assert.assertNotNull(cell1);
			//text field widget
			Widget textFiled=cell1.getContents().get(0);
			TextfieldPropertyFilter textFilter=new TextfieldPropertyFilter();
			List<Property> filterList=  textFilter.filter(textFiled);
			Assert.assertNotNull(filterList);
			Assert.assertTrue(!filterList.isEmpty());

		}
	}
}
