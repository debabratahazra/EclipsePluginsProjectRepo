package com.odcgroup.page.metamodel.util.tests;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.EList;
import org.junit.Assert;
import org.junit.Test;

import com.odcgroup.page.metamodel.DataType;
import com.odcgroup.page.metamodel.DataTypes;
import com.odcgroup.page.metamodel.DataValue;
import com.odcgroup.page.metamodel.FunctionType;
import com.odcgroup.page.metamodel.MetaModel;
import com.odcgroup.page.metamodel.ParameterType;
import com.odcgroup.page.metamodel.PropertyCategory;
import com.odcgroup.page.metamodel.PropertyType;
import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.metamodel.WidgetLibrary;
import com.odcgroup.page.metamodel.WidgetType;
import com.odcgroup.page.metamodel.WidgetTypeConstants;
import com.odcgroup.page.metamodel.util.MetaModelRegistry;

/**
 * Tests the MetaModelRegistry.
 * 
 * @author Gary Hayes & SCN
 */
public class MetaModelRegistryTest {

	/**
	 * Makes sure that there are no errors in the MetaModel.
	 */
	@Test
	public void testMetaModel() {
		MetaModel mm = MetaModelRegistry.getMetaModel();
		Assert.assertTrue(mm.eResource().getErrors().size() == 0);
	}

	/**
	 * Make sure that there are no errors in the Widget Libraries (these are stored in different models).
	 */
	@Test
	public void testWidgetLibraries() {
		MetaModel mm = MetaModelRegistry.getMetaModel();
		for (WidgetLibrary wl : mm.getWidgetLibraries()) {
			Assert.assertTrue(wl.eResource().getErrors().size() == 0);
		}
	}
	
	/**
	 * Make sure that there are no errors in the DataTypes (these are stored in different models).
	 */
	@Test
	public void testDataTypes() {
		MetaModel mm = MetaModelRegistry.getMetaModel();
		DataTypes dts = mm.getDataTypes();
		Assert.assertTrue(dts.eResource().getErrors().size() == 0);
	}	
	
	/**
	 * DS-4019: ensures that mandatory targets are defined for the DataType 'Target'.
	 */
	@Test
	public void testTargetDataType() {
		DataType type = findDataType("Target");
		Assert.assertTrue("the DataType [Target] is missing", type != null);
		Assert.assertTrue("the target [self] is missing in DataType [Target]", valueExists(type,"self"));
		Assert.assertTrue("the target [WUI_main] is missing in DataType [Target]", valueExists(type,"WUI_main"));
	}

	/**
	 * DS-4019: ensures that mandatory targets are defined for the DataType 'SubmitTarget'.
	 */
	@Test
	public void testSubmitTargetDataType() {
		DataType type = findDataType("SubmitTarget");
		Assert.assertTrue("the DataType [SubmitTarget] is missing", type != null);
		Assert.assertTrue("the target [layer] is missing in DataType [SubmitTarget]", valueExists(type,"layer"));
		Assert.assertTrue("the target [layer-not-modal] is missing in DataType [SubmitTarget]", valueExists(type,"layer-not-modal"));
		Assert.assertTrue("the target [module] is missing in DataType [SubmitTarget]", valueExists(type,"module"));
		Assert.assertTrue("the target [new] is missing in DataType [SubmitTarget]", valueExists(type,"new"));
		Assert.assertTrue("the target [popup] is missing in DataType [SubmitTarget]", valueExists(type,"popup"));
		Assert.assertTrue("the target [self] is missing in DataType [SubmitTarget]", valueExists(type,"self"));
		Assert.assertTrue("the target [WUI_main] is missing in DataType [SubmitTarget]", valueExists(type,"WUI_main"));
	}
	
	@Test
	public void testTableColumnVisibilityDataType() {
		DataType type = findDataType("TableColumnVisibility");
		Assert.assertTrue("the DataType [TableColumnVisibility] is missing", type != null);
		
		DataValue dv = getDataValue(type, "visible");
		Assert.assertTrue("the DataValue [visible] is missing", dv != null);
		Assert.assertEquals("Visible (available in column selector)", dv.getDisplayName());
		
		dv = getDataValue(type, "not-visible");
		Assert.assertTrue("the DataValue [not-visible] is missing", dv != null);
		Assert.assertEquals("Not visible by default (available in column selector)", dv.getDisplayName());
	}
	
	/**
	 * DS-3997
	 */
	@Test
	public void testCssClassTabProperty() {
		MetaModel mm = MetaModelRegistry.getMetaModel();
		WidgetLibrary library = mm.findWidgetLibrary("xgui");
		EList<PropertyType> list = library.getPropertyTypes();
		PropertyType pt = getPropertyType(list, PropertyTypeConstants.CSS_CLASS);
		Assert.assertNotNull(pt);
		PropertyCategory category = pt.getCategory();
		Assert.assertNotNull(category);
		Assert.assertTrue("Category of CSS Class property is not Presentation", 
				category.getName().equals("Presentation"));
		
	}
	
	/**
	 * @param list
	 * @param typeName
	 * @return
	 */
	private PropertyType getPropertyType(List<PropertyType> list, String typeName) {
		Iterator<PropertyType> it = list.iterator();
		while (it.hasNext()) {
			PropertyType pt = it.next();
			if (pt.getName().equals(PropertyTypeConstants.CSS_CLASS)) {
				return pt;
			}
		}
		return null;
	}
	
	/**
	 * DS - 4727
	 */
	@Test
	public void testWidgetsForExcludedDocumentationProperty() {
		List<String> filterList = new ArrayList<String>();
		filterList.add(WidgetTypeConstants.ATTRIBUTE);
		filterList.add(WidgetTypeConstants.BOX);
		filterList.add(WidgetTypeConstants.CONDITIONAL);
		filterList.add(WidgetTypeConstants.GLUE);
		filterList.add(WidgetTypeConstants.LABEL);
		filterList.add(WidgetTypeConstants.SPACER);
		filterList.add(WidgetTypeConstants.TABBED_PANE);
		filterList.add(WidgetTypeConstants.MATRIX_CELL);
		filterList.add(WidgetTypeConstants.MATRIX_CONTENTCELL);
		filterList.add(WidgetTypeConstants.MATRIX_EXTRACOLUMN);
		filterList.add(WidgetTypeConstants.TABLE_COLUMN_ITEM);

		MetaModel mm = MetaModelRegistry.getMetaModel();
		WidgetLibrary library = mm.findWidgetLibrary("xgui");
		EList<WidgetType> list = library.getWidgetTypes();
		Iterator<WidgetType> it = list.iterator();
		while (it.hasNext()) {
			WidgetType wt = it.next();
			for (int i = 0; i < filterList.size(); i++) {
				if (wt.getName().equals(filterList.get(i))) {
					PropertyType pt = wt.findPropertyType(PropertyTypeConstants.DOCUMENTATION, false);
					Assert.assertNull("Description property for Widget " + wt.getName() + " should be null.", pt);
				}
			}
		}
	}
	
	/**
	 * DS-4019: ensures that some FunctionTypes have the correct target parameter
	 */
	@Test
	public void testEventFunctionAndTargetParameter() {
		checkTargetParameter("submit", "target", "SubmitTarget");
		checkTargetParameter("clearForm", "target", "Target");
		checkTargetParameter("setFormValue", "target", "Target");
		checkTargetParameter("setWidgetValue", "target", "Target");
		checkTargetParameter("switchWidgetProperty", "target", "Target");
		checkTargetParameter("setWidgetProperty", "target", "Target");
		checkTargetParameter("setFormProperty", "target", "Target");
		checkTargetParameter("focus", "target", "Target");
		checkTargetParameter("setWidgetClass", "target", "Target");
		checkTargetParameter("refreshScreen", "target", "Target");
		checkTargetParameter("winClose", "target", "Target");
	}
	
	/**
	 * 
	 */
	@Test
	public void testPropertySourceAdapters() {
		checkPropertySourceAdapterFor("column-name", 
				"com.odcgroup.page.ui.properties.table.TableColumnAttributePropertySourceAdapter");
	}
	
	private void checkPropertySourceAdapterFor(String propertyName, String adapterclass) {
		PropertyType pType = findPropertyType(propertyName);
		if (pType != null) {
			String sourceAdapter = pType.getSourceAdapter();
			Assert.assertTrue("Property Source Adapter specified is wrong", adapterclass.equals(sourceAdapter));
		}
	}
	
	private void checkTargetParameter(String functionName, String parameterName, String typeName) {
		MetaModel mm = MetaModelRegistry.getMetaModel();
		
		// find the function type
		FunctionType function = null;
		for (FunctionType ft : mm.getEventModel().getFunctions()) {
			if (functionName.equals(ft.getName())) {
				function = ft;
				break;
			}
		}
		Assert.assertTrue("Event Model: the Function Type ["+functionName+"] is missing", 
				function != null);

		// find the parameter type
		ParameterType parameter = null;
		for (ParameterType pt : function.getParameters()) {
			if (parameterName.equals(pt.getName())) {
				parameter = pt;
				break;
			}
		}
		Assert.assertTrue("Event Model: FunctionType ["+functionName+"]:  the ParameterType ["+parameterName+"] is missing", 
				parameter != null);

		// find the data type
		DataType dt = parameter.getType();
		Assert.assertEquals("Event Model: FunctionType ["+functionName+"]: ParameterType ["+parameterName+"] has the wrong DataType",
			typeName, dt.getName());
	}
	
	private DataType findDataType(String name) {
		MetaModel mm = MetaModelRegistry.getMetaModel();
		DataTypes dts = mm.getDataTypes();
		for (DataType dt : dts.getTypes()) {
			if (name.equals(dt.getName())) {
				return dt;
			}
		}
		return null;
	}
	
	/**
	 * @param name
	 * @return
	 */
	private PropertyType findPropertyType(String name) {
		MetaModel mm = MetaModelRegistry.getMetaModel();
		WidgetLibrary library = mm.findWidgetLibrary("xgui");
		List<PropertyType> ptypes = library.getPropertyTypes();
		for (PropertyType ptype : ptypes) {
			if (name.equals(ptype.getName())) {
				return ptype;
			}
		}
		return null;
	}
	
	/**
	 * @param name
	 * @return
	 */
	private WidgetType findWidgetType(String name) {
		MetaModel mm = MetaModelRegistry.getMetaModel();
		WidgetLibrary library = mm.findWidgetLibrary("xgui");
		WidgetType widgetType = library.findWidgetType(name);
		Assert.assertNotNull(widgetType);
		return widgetType;
	}
	
	private boolean valueExists(DataType type, String value) {
		for (DataValue dv : type.getValues()) {
			if (value.equals(dv.getValue())) {
				return true;
			}
		}
		return false;
	}
	
	private DataValue getDataValue(DataType type, String value) {
		for (DataValue dv : type.getValues()) {
			if (value.equals(dv.getValue())) {
				return dv;
			}
		}
		return null;
	}
	
	/**
	 * DS-4635
	 */
	@Test
	public void testKeepFilterWidget1() {
		DataType type = findDataType("KeepFilterOperator");
		Assert.assertNotNull(type);
		EList<DataValue> values = type.getValues();
		Assert.assertEquals(8, values.size());
		Assert.assertNotNull(values);
		Assert.assertNotNull(values.get(7));
		Assert.assertEquals("Not Like", ((DataValue)values.get(7)).getDisplayName());
	}
	
	/**
	 * DS-4636
	 */
	@Test
	public void testKeepFilterWidget2() {
		DataType type = findDataType("KeepFilterOperator");
		Assert.assertNotNull(type);
		EList<DataValue> values = type.getValues();
		Assert.assertEquals(8, values.size());
		Assert.assertNotNull(values);
		Assert.assertEquals("=", ((DataValue)values.get(0)).getDisplayName());
		Assert.assertEquals("<>", ((DataValue)values.get(1)).getDisplayName());
		Assert.assertEquals(">", ((DataValue)values.get(2)).getDisplayName());
		Assert.assertEquals(">=", ((DataValue)values.get(3)).getDisplayName());
		Assert.assertEquals("<", ((DataValue)values.get(4)).getDisplayName());
		Assert.assertEquals("<=", ((DataValue)values.get(5)).getDisplayName());
		Assert.assertEquals("Like", ((DataValue)values.get(6)).getDisplayName());
		Assert.assertEquals("Not Like", ((DataValue)values.get(7)).getDisplayName());
	}
	
	/**
	 * DS-4837
	 */
	@Test
	public void testEnableBasedOn() {
		List<String> filterList = new ArrayList<String>();
		filterList.add(WidgetTypeConstants.CALDATE_FIELD);
		filterList.add(WidgetTypeConstants.CHECKBOX);
		filterList.add(WidgetTypeConstants.FILE_CHOOSER);
		filterList.add(WidgetTypeConstants.PASSWORD_FIELD);
		filterList.add(WidgetTypeConstants.SEARCH_FIELD);
		filterList.add(WidgetTypeConstants.TEXTAREA);
		filterList.add(WidgetTypeConstants.TEXTFIELD);
		filterList.add(WidgetTypeConstants.COMBOBOX);
		filterList.add(WidgetTypeConstants.LIST);
		filterList.add(WidgetTypeConstants.RADIO_BUTTON);
		
		MetaModel mm = MetaModelRegistry.getMetaModel();
		WidgetLibrary library = mm.findWidgetLibrary("xgui");
		List<WidgetType> ptypes = library.getWidgetTypes();
		Iterator<WidgetType> it = ptypes.iterator();
		while (it.hasNext()) {
			WidgetType wt = it.next();
			for (int i = 0; i < filterList.size(); i++) {
				if (wt.getName().equals(filterList.get(i))) {
					PropertyType pt = wt.findPropertyType(PropertyTypeConstants.ENABLED_IS_BASED_ON, false);
					Assert.assertNotNull("EnabledIsBasedOn property for Widget " + wt.getName() + " should not be null.", pt);
					pt = wt.findPropertyType(PropertyTypeConstants.ENABLED_IS_BASED_ON_SIMPLIFIED, false);
					Assert.assertNotNull("EnabledIsBasedOn-Simplified property for Widget " + wt.getName() + " should not be null.", pt);
					pt = wt.findPropertyType(PropertyTypeConstants.ENABLED_IS_BASED_ON_ADVANCED, false);
					Assert.assertNotNull("EnabledIsBasedOn-Advanced property for Widget " + wt.getName() + " should not be null.", pt);
				}
			}
		}
	}
	
	/**
	 * DS-4837
	 */
	@Test
	public void testEditableBasedOn() {
		List<String> filterList = new ArrayList<String>();
		filterList.add(WidgetTypeConstants.CALDATE_FIELD);
		filterList.add(WidgetTypeConstants.FILE_CHOOSER);
		filterList.add(WidgetTypeConstants.PASSWORD_FIELD);
		filterList.add(WidgetTypeConstants.SEARCH_FIELD);
		filterList.add(WidgetTypeConstants.TEXTAREA);
		filterList.add(WidgetTypeConstants.TEXTFIELD);
		filterList.add(WidgetTypeConstants.COMBOBOX);
		filterList.add(WidgetTypeConstants.LIST);
		
		MetaModel mm = MetaModelRegistry.getMetaModel();
		WidgetLibrary library = mm.findWidgetLibrary("xgui");
		List<WidgetType> ptypes = library.getWidgetTypes();
		Iterator<WidgetType> it = ptypes.iterator();
		while (it.hasNext()) {
			WidgetType wt = it.next();
			for (int i = 0; i < filterList.size(); i++) {
				if (wt.getName().equals(filterList.get(i))) {
					PropertyType pt = wt.findPropertyType(PropertyTypeConstants.EDITABLE, false);
					Assert.assertNotNull("Editable property for Widget " + wt.getName() + " should not be null.", pt);
					pt = wt.findPropertyType(PropertyTypeConstants.EDITABLE_IS_BASED_ON, false);
					Assert.assertNotNull("EditableIsBasedOn property for Widget " + wt.getName() + " should not be null.", pt);
					pt = wt.findPropertyType(PropertyTypeConstants.EDITABLE_IS_BASED_ON_SIMPLIFIED, false);
					Assert.assertNotNull("EditableIsBasedOn-Simplified property for Widget" + wt.getName() + " should not be null.", pt);
					pt = wt.findPropertyType(PropertyTypeConstants.EDITABLE_IS_BASED_ON_ADVANCED, false);
					Assert.assertNotNull("EditableIsBasedOn-Advanced property for Widget " + wt.getName() + " should not be null.", pt);
				}
			}
		}
	}
	
	/**
	 * DS-4945
	 */
	@Test
	public void testXtooltipDataType(){		
		//check for the fragment type
		DataType fragmentType=findDataType("FragmentType");
		Assert.assertNotNull(fragmentType);
		List<DataValue> fragmnentType=fragmentType.getValues();
		Assert.assertEquals(2, fragmnentType.size());
	}
	
	/**
	 * DS-4945
	 */
	@Test
	public void testXtooltipProperties(){
		//fragment type property
		PropertyType proprtytype=findPropertyType("fragmentType");
		Assert.assertNotNull(proprtytype);
		String defaultValue=proprtytype.getDefaultValue();
		Assert.assertEquals("regular", defaultValue);
		PropertyCategory category=proprtytype.getCategory();
		Assert.assertNotNull(category);
		String categoryName=category.getName();
		Assert.assertEquals("General", categoryName);
		
		//holdtooltip type property
		
		PropertyType holdWindowProperty=findPropertyType("holdTooltipWindow");
		Assert.assertNotNull(holdWindowProperty);
		String def=holdWindowProperty.getDefaultValue();
		Assert.assertEquals("false", def);
	        category=holdWindowProperty.getCategory();
		Assert.assertNotNull(category);
		categoryName=category.getName();
		Assert.assertEquals("Xtooltip", categoryName);
	}
	
	/**
	 * DS-4945
	 */	
	@Test
	public void testFragmentWidgetForFragmentType(){
		//check for the fragment type property in frgament widget
		MetaModel mm = MetaModelRegistry.getMetaModel();
		WidgetLibrary library = mm.findWidgetLibrary("xgui");
		List<WidgetType> ptypes = library.getWidgetTypes();
		Iterator<WidgetType> it = ptypes.iterator();
		while (it.hasNext()) {
			WidgetType wt = it.next();
			 if (wt.getName().equals("Fragment")) {
				PropertyType pt = wt.findPropertyType(PropertyTypeConstants.FRAGMENT_TYPE, false);
				Assert.assertNotNull(pt);
			}
		}
	}
	
	/**
	 * DS-4991 table/tree xtooltip properties
	 */
	@Test
	public void testTableTreeXtooltipProperties(){
		MetaModel mm = MetaModelRegistry.getMetaModel();
		WidgetLibrary library=mm.findWidgetLibrary("xgui");
		WidgetType tableGroupType=library.findWidgetType("TableGroup");
		checkWigetContainXtooltipProperties(tableGroupType);
		WidgetType tableItem=library.findWidgetType(WidgetTypeConstants.TABLE_COLUMN_ITEM);
		checkWigetContainXtooltipProperties(tableItem);		
	} 
	
	/*
	 * to check the xtooltip properties in a widget
	 */
	private void checkWigetContainXtooltipProperties(WidgetType type){
		PropertyType include=type.findPropertyType(PropertyTypeConstants.XTOOLTIP_INCLUDE_FRAGEMENT, false);
		Assert.assertNotNull(include);
		PropertyType holdWindow=type.findPropertyType(PropertyTypeConstants.XTOOLTIP_HOLDTOOLTIPWINDOW_PROPERTY, false);
		Assert.assertNotNull(holdWindow);
	}
	
	/**
	 * DS-5121 TableColumnEditableItem widget
	 */
	@Test
	public void testTableColumnEditableItemWidget() {
		
		WidgetType editItem = findWidgetType("TableColumnEditableItem");
		
		List<PropertyType> eProps = editItem.getExcludedPropertyTypes();
		Assert.assertTrue(checkContainsProperty(eProps, PropertyTypeConstants.XTOOLTIP_INCLUDE_FRAGEMENT));
		Assert.assertTrue(checkContainsProperty(eProps, PropertyTypeConstants.XTOOLTIP_HOLDTOOLTIPWINDOW_PROPERTY));
		
		List<PropertyType> props = editItem.getPropertyTypes();
		Assert.assertTrue(checkContainsProperty(props, PropertyTypeConstants.TABLE_COLUMN_ITEM_DATASET_ATTRIBUTE));
		Assert.assertTrue(checkContainsProperty(props, PropertyTypeConstants.EDITABLE));
		Assert.assertTrue(checkContainsProperty(props, PropertyTypeConstants.REQUIRED));
		Assert.assertTrue(checkContainsProperty(props, PropertyTypeConstants.TAB_INDEX));
		Assert.assertTrue(checkContainsProperty(props, PropertyTypeConstants.DOCUMENTATION));
		
	}
	
	/**
	 * 
	 */
	@Test
	public void testTableColumnCalendarWidget() {
		WidgetType calendar = findWidgetType("TableColumnCalendar");
		Map<String, PropertyType> maps = calendar.getAllPropertyTypes();
		List<PropertyType> props = new ArrayList<PropertyType>(maps.values());
		Assert.assertTrue(checkContainsProperty(props, PropertyTypeConstants.FREE_DATE_TYPE));
		Assert.assertTrue(checkContainsProperty(props, PropertyTypeConstants.NILLABLE_TYPE));
	}	
	
	/**
	 * 
	 */
	@Test
	public void testTableColumnTextWidget() {
		WidgetType text = findWidgetType("TableColumnText");
		Map<String, PropertyType> maps = text.getAllPropertyTypes();
		List<PropertyType> props = new ArrayList<PropertyType>(maps.values());
		Assert.assertTrue(checkContainsProperty(props, PropertyTypeConstants.CHARS));
		Assert.assertTrue(checkContainsProperty(props, PropertyTypeConstants.COLUMNS));
		Assert.assertTrue(checkContainsProperty(props, PropertyTypeConstants.ALLOW_TYPE));		
		Assert.assertTrue(checkContainsProperty(props, PropertyTypeConstants.SCALE));			
		Assert.assertTrue(checkContainsProperty(props, PropertyTypeConstants.PRECISION));		
	}
	
	/**
	 * 
	 */
	@Test
	public void testTableColumnTextAreaWidget() {
		WidgetType textarea = findWidgetType("TableColumnTextArea");
		Map<String, PropertyType> maps = textarea.getAllPropertyTypes();
		List<PropertyType> props = new ArrayList<PropertyType>(maps.values());
		Assert.assertTrue(checkContainsProperty(props, PropertyTypeConstants.CHARS));
		Assert.assertTrue(checkContainsProperty(props, PropertyTypeConstants.COLUMNS));
		Assert.assertTrue(checkContainsProperty(props, PropertyTypeConstants.RICHTEXT));		
	}
	
	/**
	 * 
	 */
	@Test
	public void testTableColumnTextCheckboxWidget() {
		WidgetType check = findWidgetType("TableColumnCheckbox");
		
		List<PropertyType> props = check.getPropertyTypes();
		Assert.assertTrue(checkContainsProperty(props, PropertyTypeConstants.SELECTED));
		
		List<PropertyType> eProps = check.getExcludedPropertyTypes();
		Assert.assertTrue(checkContainsProperty(eProps, PropertyTypeConstants.PREVIEW_VALUE));
		Assert.assertTrue(checkContainsProperty(eProps, PropertyTypeConstants.TYPE));	
	}
	
	/**
	 * 
	 */
	@Test
	public void testTableColumnComboboxWidget() {
		WidgetType textarea = findWidgetType("TableColumnCombobox");
		List<PropertyType> props = textarea.getPropertyTypes();
		Assert.assertTrue(checkContainsProperty(props, PropertyTypeConstants.SELECTION_TYPE));
		Assert.assertTrue(checkContainsProperty(props, PropertyTypeConstants.SORTBY_TYPE));		
	}
	
	/**
	 * 
	 */
	@Test
	public void testTableColumnSearchWidget() {
		WidgetType search = findWidgetType("TableColumnSearch");
		Map<String, PropertyType> maps = search.getAllPropertyTypes();
		List<PropertyType> props = new ArrayList<PropertyType>(maps.values());
		Assert.assertTrue(checkContainsProperty(props, PropertyTypeConstants.AUTO_DELAY));
		Assert.assertTrue(checkContainsProperty(props, PropertyTypeConstants.OUTPUT_DESIGN));	
		Assert.assertTrue(checkContainsProperty(props, PropertyTypeConstants.NB_CHARS));		
		Assert.assertTrue(checkContainsProperty(props, PropertyTypeConstants.SEARCH_TYPE));		
		Assert.assertTrue(checkContainsProperty(props, PropertyTypeConstants.CHARS));	
		Assert.assertTrue(checkContainsProperty(props, PropertyTypeConstants.COLUMNS));	
		Assert.assertTrue(checkContainsProperty(props, PropertyTypeConstants.ALLOW_TYPE));			
	}
	
	
	
	
	/**
	 * @param list
	 * @param propertyType
	 */
	private boolean checkContainsProperty(List<PropertyType> list, String propertyType) {
		for (PropertyType ptype : list) {
			if (propertyType.equals(ptype.getName()) ) {
				return true;
			}
		}
		return false;
	}
	/**
	 * test if the tabbbed pane widget has name property.
	 */
	@Test
	public void testTabbedPaneNameProperty(){
	    WidgetType tabbedPane = findWidgetType(WidgetTypeConstants.TABBED_PANE);
	    Assert.assertNotNull(tabbedPane);
	    PropertyType name=tabbedPane.findPropertyType("name", false);
	    Assert.assertNotNull(name);
	    
	}
	
	@Test
	public void testDS5062ConditionalLanguageDataTypeName(){
		DataType type = findDataType("ConditionLanguage");
		Assert.assertNotNull(type);
		int i=type.getValues().size();
		Assert.assertEquals(2, i);
		DataValue value=type.getValues().get(1);
		String expecting="Design Studio Script";
		String actual=value.getDisplayName();
		Assert.assertEquals(expecting, actual);
	}
}
