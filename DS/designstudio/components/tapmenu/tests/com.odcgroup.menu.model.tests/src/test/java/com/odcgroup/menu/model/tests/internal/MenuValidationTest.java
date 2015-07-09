package com.odcgroup.menu.model.tests.internal;

import static com.odcgroup.workbench.core.tests.util.TestTankResourcesTestUtil.loadTestTankResourceAsString;

import java.util.List;

import javax.inject.Inject;

import org.eclipse.xtext.junit4.InjectWith;
import org.eclipse.xtext.junit4.XtextRunner;
import org.eclipse.xtext.junit4.ui.AbstractWorkbenchTest;
import org.eclipse.xtext.junit4.util.ParseHelper;
import org.eclipse.xtext.junit4.validation.ValidationTestHelper;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.odcgroup.menu.model.MenuInjectorProvider;
import com.odcgroup.menu.model.MenuItem;
import com.odcgroup.menu.model.MenuPackage;
import com.odcgroup.menu.model.MenuRoot;
import com.odcgroup.menu.model.validation.MenuJavaValidator;

@SuppressWarnings("restriction")
@RunWith(XtextRunner.class)
@InjectWith(MenuInjectorProvider.class)
public class MenuValidationTest extends AbstractWorkbenchTest {
	
	private static final String MENU_MODEL = "/menu/Default/test/MenuConstraints.menu";
	private static final String SECOND_MENU_MODEL = "/menu/Default/gen/MenuWithDuplicateName.menu";
	
	@Inject
	private ParseHelper<MenuRoot> parseHelper;
	
	@Inject
	private ValidationTestHelper validationHelper;
	
	@Test
	public void testCheckforInvalidShortcutKey() {
		try {
			String content = loadTestTankResourceAsString(this.getClass(), MENU_MODEL);
			MenuRoot root = parseHelper.parse(content);
			MenuItem item = root.getRootItem();
			item.setShortcut("InvalidShortcut");
			validationHelper.assertError(item, 
					MenuPackage.Literals.MENU_ITEM, 
					null,
					"Invalid shortcut");
		} catch (Throwable ex) {
			Exceptions.sneakyThrow(ex);
		}

	}
	
	@Test
	public void testCheckforDuplicateMenuName() {
		try {
			String contents = loadTestTankResourceAsString(this.getClass(), MENU_MODEL);
			MenuRoot root = parseHelper.parse(contents);
			MenuItem rootItem = root.getRootItem();
			List<MenuItem> items = rootItem.getSubmenus();
			Assert.assertTrue(items.size() == 4);
			validationHelper.assertError(rootItem, 
					MenuPackage.Literals.MENU_ITEM, 
					null,
					"Duplicate menu name, should be unique within the menu module");
		} catch (Throwable ex) {
			Exceptions.sneakyThrow(ex);
		}
		
	}
	
	@Test
	public void testCheckforDuplicateMenuNameAcrossModels() {
		try {
			String content1 = loadTestTankResourceAsString(this.getClass(), MENU_MODEL);
			String content2 = loadTestTankResourceAsString(this.getClass(), SECOND_MENU_MODEL);

			MenuRoot root1 = parseHelper.parse(content1);
			
			// Below, the second parameter ensures that the content2 will be in the same
			// resource set as content1, otherwise the parser will put it to another resource set.
			MenuRoot root2 = parseHelper.parse(content2, root1.eResource().getResourceSet());
			
			validationHelper.assertError(root2, 
					MenuPackage.Literals.MENU_ITEM, 
					MenuJavaValidator.DUPLICATE_MENU_NAME,
					"A menu with the same name already exists in the project");

			validationHelper.assertError(root1, 
					MenuPackage.Literals.MENU_ITEM, 
					MenuJavaValidator.DUPLICATE_MENU_NAME,
					"A menu with the same name already exists in the project");
			
		} catch (Throwable ex) {
			Exceptions.sneakyThrow(ex);
		}
	}
	
	@Test
	public void testCheckforValidMenuItemName() { 
		try {
			String content = loadTestTankResourceAsString(this.getClass(), MENU_MODEL);
			MenuRoot root = parseHelper.parse(content);
			MenuItem rootItem = root.getRootItem();
			List<MenuItem> items = rootItem.getSubmenus();
			Assert.assertTrue(items.size() == 4);
			
			validationHelper.assertError(items.get(2), 
					MenuPackage.Literals.MENU_ITEM, 
					null, "Menu name should not contain special characters");

		} catch (Throwable ex) {
			Exceptions.sneakyThrow(ex);
		}
	}			


	/**
	 * test the Display children as Tab property prent has leaf child.
	 */
	@Test
	public void testcheckChildrenAstabs() {
		try {
			String content = loadTestTankResourceAsString(this.getClass(), MENU_MODEL);
			MenuRoot root = parseHelper.parse(content);
			MenuItem rootItem = root.getRootItem();
			List<MenuItem> items = rootItem.getSubmenus();
			MenuItem levl2MenuItem=items.get(1);
			//checking level3 menu item set for the  Display childern as tab property. 
			MenuItem level3MenuItem=levl2MenuItem.getSubmenus().get(0);
			boolean actual=level3MenuItem.isDisplayTabs();
			Assert.assertEquals(true, actual);
		} catch (Throwable ex) {
			Exceptions.sneakyThrow(ex);
		}
	}
	
	@Test
	public void testDS5224PageFlowNameValidation() {
		try {
			String content = loadTestTankResourceAsString(this.getClass(), MENU_MODEL);
			MenuRoot root = parseHelper.parse(content);
		    MenuItem rootItem = root.getRootItem();
		    // ensure we have a leaf node
		    rootItem.getSubmenus().clear(); 

		    rootItem.setPageflow("testpageFlow");
			validationHelper.assertNoErrors(rootItem);
			
			rootItem.setPageflow("");
			validationHelper.assertError(rootItem, 
					MenuPackage.Literals.MENU_ITEM, null, "Menu must define pageflow property");
		    
		} catch (Throwable ex) {
			Exceptions.sneakyThrow(ex);
		}
		
	}

}
