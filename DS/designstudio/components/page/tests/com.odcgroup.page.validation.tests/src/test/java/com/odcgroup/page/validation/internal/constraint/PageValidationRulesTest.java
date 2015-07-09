package com.odcgroup.page.validation.internal.constraint;

import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
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
import com.odcgroup.page.model.util.TableHelper;
import com.odcgroup.page.model.widgets.table.ITable;
import com.odcgroup.page.model.widgets.table.ITableColumn;
import com.odcgroup.page.model.widgets.table.ITableColumnItem;
import com.odcgroup.page.model.widgets.table.ITableGroup;
import com.odcgroup.page.validation.internal.PageConstraints;
import com.odcgroup.workbench.core.IOfsModelResource;
import com.odcgroup.workbench.core.tests.util.AbstractDSUnitTest;

/**
 * Test of validation rules given concrete models
 * 
 * @author atr
 */
public class PageValidationRulesTest extends AbstractDSUnitTest {

	@Before
	public void setUp() {
		createModelsProject();
	}

	@After
	public void tearDown() {
		deleteModelsProjects();
	}

	@Test
	public void testDS3993() {

		copyResourceInModelsProject("module/Default/module/DS3993.module");
		copyResourceInModelsProject("domain/ds3993/DS3993.domain");

		// fetch the model
		Model model = null;
		URI uri = URI.createURI("resource:///Default/module/DS3993.module");
		try {
			IOfsModelResource ofsResource = getOfsProject()
					.getOfsModelResource(uri);
			model = (Model) ofsResource.getEMFModel().get(0);
		} catch (Exception ex) {
			ex.printStackTrace();
			Assert.fail("Cannot load the model from the resource Default/module/DS3993.module");
		}

		Widget module = model.getWidget();
		Assert.assertTrue("Cannot load the module", module != null);

		Widget box = module.getContents().get(0);
		Widget combo = box.getContents().get(0);

		boolean isValid = PageConstraints
				.checkComboboxDomainHasPermittedValues(combo);
		Assert.assertTrue(
				"validation of the module [Default/module/DS3993.module] failed",
				isValid);

	}

	@Test
	public void testDS4228() {

		copyResourceInModelsProject("module/Default/module/DS4228.module");
		copyResourceInModelsProject("domain/ds4228/DS4228.domain");

		// fetch the model
		Model model = null;
		URI uri = URI.createURI("resource:///Default/module/DS4228.module");
		try {
			IOfsModelResource ofsResource = getOfsProject()
					.getOfsModelResource(uri);
			model = (Model) ofsResource.getEMFModel().get(0);
		} catch (Exception ex) {
			ex.printStackTrace();
			Assert.fail("Cannot load the model from the resource Default/module/DS4228.module");
		}

		Widget module = model.getWidget();
		Assert.assertTrue("Cannot load the module", module != null);

		Widget box = module.getContents().get(0);
		Widget combo = box.getContents().get(0);

		boolean isValid = PageConstraints
				.checkComboboxDomainHasPermittedValues(combo);
		Assert.assertTrue(
				"validation of the module [Default/module/DS4228.module] failed",
				isValid);
	}

	@Test
	public void testDS4059itemInDynamicColumnValidationShouldFailWhenItemColumnIsNotBound() {

		Model model = create40584059ModelProject();

		PageWidgetValidator pwv = new PageWidgetValidator(null);
		Widget tableCol = getTableColumn(model);

		/* Should Validate as we have DS bound */
		Widget validTableColItem = tableCol.getContents().get(0);
		IStatus status = pwv.checkDynamicColumnContainsValueWithColumnDefined(validTableColItem);
		Assert.assertSame(Status.OK_STATUS, status);

		/* Should fail */
		Widget invalidTtableColItem = tableCol.getContents().get(2);
		status = pwv.checkDynamicColumnContainsValueWithColumnDefined(invalidTtableColItem);
		Assert.assertSame(IStatus.ERROR, status.getSeverity()); // Error severity

	}

	@Test
	public void testDS4058itemInDynamicColumnValidationShouldFailSortOrderIsNotSet() {

		Model model = create40584059ModelProject();

		PageWidgetValidator pwv = new PageWidgetValidator(null);
		Widget tableCol = getTableColumn(model);
            
		IStatus status = pwv.checkDynamicColumnSortOrderIsSet(tableCol);
		Assert.assertSame(IStatus.ERROR, status.getSeverity());
	}

	@Test
	public void testDS4406() {

		copyResourceInModelsProject("module/widget/tabletree/DS4406.module");
		copyResourceInModelsProject("domain/ds4228/DS4228.domain");

		// fetch the model
		Model model = null;
		URI uri = URI.createURI("resource:///widget/tabletree/DS4406.module");
		try {
			IOfsModelResource ofsResource = getOfsProject()
					.getOfsModelResource(uri);
			model = (Model) ofsResource.getEMFModel().get(0);
		} catch (Exception ex) {
			ex.printStackTrace();
			Assert.fail("Cannot load the model from the resource widget/tabletree/DS4406.module");
		}

		Widget module = model.getWidget();
		Assert.assertTrue("Cannot load the module", module != null);		
		
		// find the TableColumnItem
		Widget w = module;
		while (w != null && !w.getTypeName().equals("TableColumnItem")) {
			w = w.getContents().get(0);
		}
		
		Assert.assertTrue("Cannot find the widget TableColumnItem in the module", 
				w != null && w.getTypeName().equals("TableColumnItem"));		
		
		ITableColumnItem item = TableHelper.getTableColumnItem(w);
		Assert.assertTrue("For the purpose of this test, the direct parent widget of the TableColumnItem must not be a TableColumn", 
				! item.getWidget().getParent().getTypeName().equals("TableColumn"));		
		
		// find the enclosing TableColumn
		ITableColumn column = item.getTableColumn();
		Assert.assertTrue("Cannot find the column for the TableColumnItem", column != null);

		/* Should Validate as we have DS bound */
		PageWidgetValidator pwv = new PageWidgetValidator(null);
		IStatus status = pwv.checkDynamicColumnContainsValueWithColumnDefined(item.getWidget());
		Assert.assertSame(Status.OK_STATUS, status);
		
	}
	
	@Test
	public void test4438SortedColumnOnTableHasAggregrateDefined() {
		copyResourceInModelsProject("module/Default/module/DS4438.module");
		copyResourceInModelsProject("domain/ds3813/DS3813.domain");
		
		// fetch the model
		Model model = null;
		URI uri = URI.createURI("resource:///Default/module/DS4438.module");
		try {
			IOfsModelResource ofsResource = getOfsProject()
					.getOfsModelResource(uri);
			model = (Model) ofsResource.getEMFModel().get(0);
		} catch (Exception ex) {
			ex.printStackTrace();
			Assert.fail("Cannot load the model from the resource Default/module/DS4438.module");
		}
		
		PageWidgetValidator pwv = new PageWidgetValidator(null);
		IStatus status = pwv.checkSortingColumnGroupHasAggregateDefined(get43504438TableTree(model));
		Assert.assertSame(IStatus.OK, status.getSeverity());
	}

	private Widget get43504438TableTree(Model model) {
		Widget module = model.getWidget();
		Widget box = module.getContents().get(0);
		Widget tableTree = box.getContents().get(0);
		return tableTree;
	}
	
	@Test
	public void test4350ShouldNotHaveSortOnSummaryTable() {
		copyResourceInModelsProject("module/Default/module/DS4438.module");
		copyResourceInModelsProject("domain/ds3813/DS3813.domain");
		
		// fetch the model
		Model model = null;
		URI uri = URI.createURI("resource:///Default/module/DS4438.module");
		try {
			IOfsModelResource ofsResource = getOfsProject()
					.getOfsModelResource(uri);
			model = (Model) ofsResource.getEMFModel().get(0);
		} catch (Exception ex) {
			ex.printStackTrace();
			Assert.fail("Cannot load the model from the resource Default/module/DS4438.module");
		}
		
		PageWidgetValidator pwv = new PageWidgetValidator(null);
		IStatus status = pwv.checkSortIsNotSetOnSummaryTable(get43504438TableTree(model));
		Assert.assertSame(IStatus.ERROR, status.getSeverity());
	}
	

	/**
	 * @return
	 */
	private Model create40584059ModelProject() {
		copyResourceInModelsProject("module/Default/module/DS4059.module");
		copyResourceInModelsProject("domain/ds4059/DS4059.domain");
		// fetch the model
		Model model = null;
		// domain\ds3288\DS3288.domain
		URI uri = URI.createURI("resource:///Default/module/DS4059.module");
		try {
			IOfsModelResource ofsResource = getOfsProject()
					.getOfsModelResource(uri);
			model = (Model) ofsResource.getEMFModel().get(0);
		} catch (Exception ex) {
			ex.printStackTrace();
			Assert.fail("Cannot load the model from the resource Default/module/DS4059.module");
		}
		return model;
	}

	/**
	 * @param model
	 * @return
	 */
	private Widget getTableColumn(Model model) {
		Widget module = model.getWidget();
		Widget box = module.getContents().get(0);
		Widget tableCol = box.getContents().get(0).getContents().get(2);
		return tableCol;
	}
   /*
    * test the Precision-Scale value should be ≥ 0.
    */
	@Test
	public void testcheckDiffOfPrecisionAndScaleValue(){
		copyResourceInModelsProject("domain/ds4558/DS4558PrecisionAndScale.domain");
		copyResourceInModelsProject("module/Default/module/DS4558PrecisionScale.module");
		PageWidgetValidator pwv = new PageWidgetValidator(null);
		URI uri = URI.createURI("resource:///Default/module/DS4558PrecisionScale.module");
		Widget textWidget=null;
		try {
			IOfsModelResource ofsResource = getOfsProject()
					.getOfsModelResource(uri);
		Model model=(Model)ofsResource.getEMFModel().get(0);
		Widget  module=	model.getWidget();
		Widget box=module.getContents().get(0);
		 textWidget=box.getContents().get(1);
		} catch (Exception ex) {
			ex.printStackTrace();
			Assert.fail("Cannot load the model from the resource Default/module/DS4059.module");
		}
		
		IStatus status = pwv.checkDiffOfPrecisionAndScaleValue(textWidget);
		String expectedError="Total Digits must be greater or equals to Fractional digits";
		Assert.assertEquals(expectedError, status.getMessage());
	}
	
	/*
	 * DS-4877
	 */
	@Test
	public void testRadioButtonEnumValueValidation(){
		copyResourceInModelsProject("domain/ds4877/DS4877.domain");
        copyResourceInModelsProject("module/Default/module/DS4877.module");
		Model model = null;
		URI uri = URI.createURI("resource:///Default/module/DS4877.module");
		try {
			IOfsModelResource ofsResource = getOfsProject()
					.getOfsModelResource(uri);
			model = (Model) ofsResource.getEMFModel().get(0);
		} catch (Exception ex) {
			ex.printStackTrace();
			Assert.fail("Cannot load the model from the resource Default/module/DS3993.module");
		}

		Widget module = model.getWidget();
		Assert.assertTrue("Cannot load the module", module != null);

		Widget box = module.getContents().get(0);
		Widget radioButton = box.getContents().get(0);
		PageWidgetValidator validator = new PageWidgetValidator(null);
		IStatus status=validator.checkRadioButtonIsOfValidType(radioButton);
		String expectedError="Enumerated value does not exist in the underlying enumeration";
		Assert.assertEquals(expectedError, status.getMessage());
	}
	
	/*
	 * DS-4976
	 */
	@Test
	public void testCheckboxInConditionalOfTableColumn() {
		copyResourceInModelsProject("domain/ds4405/DS4405.domain");
        copyResourceInModelsProject("module/Default/module/DS4949.module");
        copyResourceInModelsProject("module/Default/module/DS4949ConditionCheckbox.module");
		Model model = null;
		URI uri = URI.createURI("resource:///Default/module/DS4949ConditionCheckbox.module");
		try {
			IOfsModelResource ofsResource = getOfsProject()
					.getOfsModelResource(uri);
			model = (Model) ofsResource.getEMFModel().get(0);
		} catch (Exception ex) {
			ex.printStackTrace();
			Assert.fail("Cannot load the model from the resource Default/module/DS4949ConditionCheckbox.module");
		}

		Widget module = model.getWidget();
		Assert.assertTrue("Cannot load the module", module != null);

		Widget box = module.getContents().get(0);
		Widget wtable = box.getContents().get(0);
		Assert.assertTrue(wtable.getTypeName().equals(WidgetTypeConstants.TABLE_TREE));
		ITable table = TableHelper.getTable(wtable);
		ITableColumn tc = table.getColumn(0);
		Widget wcb = tc.getCheckbox();
		Assert.assertTrue(wcb.getTypeName().equals(WidgetTypeConstants.CHECKBOX));
		Widget wid = wcb.findAncestor(WidgetTypeConstants.TABLE_COLUMN);
		Assert.assertTrue("The parent widget for checkbox is not a TableColumn", wid.getTypeName().equals(WidgetTypeConstants.TABLE_COLUMN));
	}
	
	/*
	 *DS-4947 
	 */
	@Test
	public void test4947checkIncludeFragmentDataset(){
		copyResourceInModelsProject("domain/ds4947/DS4947Domain.domain");
        copyResourceInModelsProject("fragment/ds4947/DS4947DataSetValidationFragment.fragment");
        copyResourceInModelsProject("fragment/ds4947/DS4947RegularFragment.fragment");
        
        Model model = null;
		URI uri = URI.createURI("resource:///ds4947/DS4947RegularFragment.fragment");
		try {
			IOfsModelResource ofsResource = getOfsProject()
					.getOfsModelResource(uri);
			model = (Model) ofsResource.getEMFModel().get(0);
		} catch (Exception ex) {
			ex.printStackTrace();
			Assert.fail("Cannot load the model from the resource:///ds4947/DS4947RegularFragment.fragment");
		}
       
		Widget fragment=model.getWidget();
		Assert.assertTrue("Cannot load the fragment", fragment != null);
		Widget box = fragment.getContents().get(0);
		Widget label = box.getContents().get(0);
		Assert.assertTrue(label.getTypeName().equals(WidgetTypeConstants.LABEL));
		PageWidgetValidator validator = new PageWidgetValidator(null);
		IStatus status=validator.checkIncludeFragmentDataSet(label);
		String expectedError="Dataset bound to the xtooltip fragment and Fragment dataset must be same";
		Assert.assertEquals(expectedError, status.getMessage());
		
	}
	
	/*
	 *	DS-5389 validate that each attribute available in Xtooltip fragment should be configured in table
	 */
	@Test
	public void test5389checkEachAttributeOfXtooltipFragment(){
		copyResourceInModelsProject("domain/ds5389/valrulehistory.domain");
        copyResourceInModelsProject("fragment/ds5389/ValRefXtooltipFragment.fragment");
        copyResourceInModelsProject("module/widget/xtooltip/VlaRuleHistoryList.module");
        
        Model model = null;
		URI uri = URI.createURI("resource:///widget/xtooltip/VlaRuleHistoryList.module");
		try {
			IOfsModelResource ofsResource = getOfsProject()
					.getOfsModelResource(uri);
			model = (Model) ofsResource.getEMFModel().get(0);
		} catch (Exception ex) {
			ex.printStackTrace();
			Assert.fail("Cannot load the model from the resource:///module/widget/xtooltip/VlaRuleHistoryList.module");
		}
		
		Widget fragment=model.getWidget();
		Assert.assertTrue("Cannot load the module", model != null);
		Widget xtooltip = fragment.getContents().get(0);
		Widget label = xtooltip.getContents().get(0);
		Widget tableColmnItem = label.getContents().get(1);
		Widget xtooltipAttr = tableColmnItem.getContents().get(0);
		PageWidgetValidator validator = new PageWidgetValidator(null);
		
		Model model1 = null;
		URI uri1 = URI.createURI("resource:///ds5389/ValRefXtooltipFragment.fragment");
		try {
			IOfsModelResource ofsResource = getOfsProject()
					.getOfsModelResource(uri1);
			model1 = (Model) ofsResource.getEMFModel().get(0);
		} catch (Exception ex) {
			ex.printStackTrace();
			Assert.fail("Cannot load the model from the resource:///fragment/ds5389/ValRefXtooltipFragment.fragment");
		}
		Assert.assertTrue("Cannot load the fragment", model1 != null);
		Property includeProperty=xtooltipAttr.findProperty(PropertyTypeConstants.XTOOLTIP_INCLUDE_FRAGEMENT);
		includeProperty.setModel(model1);
		
		IStatus status=validator.checkEachAttributeOfIncludedXtooltipFragment(xtooltipAttr);
		String expectedError="The attribute(s) [id] found in fragment ValRefXtooltipFragment should be configured in Table";
		Assert.assertEquals(expectedError, status.getMessage());
	}

	  
	/**
	 * DS-5124
	 */
	@Test
	public void testDS5124checkEditableWidgetModuleType() {
		copyResourceInModelsProject("domain/ds4884/DS4884.domain");
		copyResourceInModelsProject("module/Default/module/DS5124_EditableWidgetValidation.module");
		Model model = null;
		URI uri = URI.createURI("resource:///Default/module/DS5124_EditableWidgetValidation.module");
		try {
			IOfsModelResource ofsResource = getOfsProject().getOfsModelResource(uri);
			model = (Model) ofsResource.getEMFModel().get(0);
		} catch (Exception ex) {
			ex.printStackTrace();
			Assert.fail("Cannot load the model from the resource:///Default/module/DS5124_EditableWidgetValidation.module");
		}
       
		Widget module = model.getWidget();
		Assert.assertTrue("Cannot load the module", module != null);
		Widget box = module.getContents().get(0);
		Widget table = box.getContents().get(0);
		Assert.assertTrue(table.getTypeName().equals(WidgetTypeConstants.TABLE_TREE));
		Widget tcol = table.getContents().get(0);
		Assert.assertTrue(tcol.getTypeName().equals(WidgetTypeConstants.TABLE_COLUMN));
		Widget text = tcol.getContents().get(0);
		Assert.assertTrue(text.getTypeName().equals("TableColumnText"));
		PageWidgetValidator validator = new PageWidgetValidator(null);
		IStatus status=validator.checkTableColumnEditableWidgetInRegularModule(text);
		String expectedError="The widget can only be contained in a TableColumn of a Table in a regular Module";
		Assert.assertEquals(expectedError, status.getMessage());
	}
	
	/**
	 * DS-5124
	 */
	@Test
	public void testDS5124TextWidgetSumTreeColEvent() {
		copyResourceInModelsProject("domain/ds4884/DS4884.domain");
		copyResourceInModelsProject("module/Default/module/DS5124_TableColumnTextEvent.module");
		Model model = null;
		URI uri = URI.createURI("resource:///Default/module/DS5124_TableColumnTextEvent.module");
		try {
			IOfsModelResource ofsResource = getOfsProject().getOfsModelResource(uri);
			model = (Model) ofsResource.getEMFModel().get(0);
		} catch (Exception ex) {
			ex.printStackTrace();
			Assert.fail("Cannot load the model from the resource:///Default/module/DS5124_TableColumnTextEvent.module");
		}
       
		Widget module = model.getWidget();
		Assert.assertTrue("Cannot load the module", module != null);
		Widget box = module.getContents().get(0);
		Widget table = box.getContents().get(0);
		Assert.assertTrue(table.getTypeName().equals(WidgetTypeConstants.TABLE_TREE));
		Widget tcol = table.getContents().get(0);
		Assert.assertTrue(tcol.getTypeName().equals(WidgetTypeConstants.TABLE_COLUMN));
		Widget text = tcol.getContents().get(0);
		Assert.assertTrue(text.getTypeName().equals("TableColumnText"));
		PageWidgetValidator validator = new PageWidgetValidator(null);
		IStatus status=validator.checkTableColumnTextSumTreeColEvent(text);
		String expectedError="The Event OnChange/sumTreeCol is only valid with a table having grouping.";
		Assert.assertEquals(expectedError, status.getMessage());
	}
	
      @Test
	public void testDS4992CheckDynamicGroupHasXtooltip(){
	  copyResourceInModelsProject("domain/xtooltip/Valo.domain");
	  copyResourceInModelsProject("domain/xtooltip/valuation.domain");
	  copyResourceInModelsProject("fragment/widgets/xtooltip/ValuationSummaryClient.fragment");
	  copyResourceInModelsProject("module/widget/xtooltip/DynamicColumnByPortfolioD.module");
	  Model model = null;
		URI uri = URI.createURI("resource:///widget/xtooltip/DynamicColumnByPortfolioD.module");
		try {
			IOfsModelResource ofsResource = getOfsProject().getOfsModelResource(uri);
			model = (Model) ofsResource.getEMFModel().get(0);
		} catch (Exception ex) {
			ex.printStackTrace();
			Assert.fail("Cannot load the model from the resource:///widget/xtooltip/DynamicColumnByPortfolioD.module");
		}
 
		Widget module = model.getWidget();
		Assert.assertTrue("Cannot load the module", module != null);
		Widget box = module.getContents().get(0);
		Widget tableWidget = box.getContents().get(0);
		ITable table=TableHelper.getTable(tableWidget);
		List<ITableGroup> groups=table.getGroups();
		PageWidgetValidator validator = new PageWidgetValidator(null);
		IStatus status=validator.checkDynamicGroupHasXtooltip(groups.get(0).getWidget());
		String expectedError="Xtooltip can not be set on Dynamic Group";
		Assert.assertEquals(expectedError, status.getMessage());
      }
      

}
