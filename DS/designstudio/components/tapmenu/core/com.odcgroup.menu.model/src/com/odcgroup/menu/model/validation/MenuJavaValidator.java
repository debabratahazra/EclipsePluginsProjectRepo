package com.odcgroup.menu.model.validation;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import javax.inject.Inject;

import org.apache.commons.lang.StringUtils;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.xtext.naming.IQualifiedNameProvider;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.IContainer;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.IResourceDescriptions;
import org.eclipse.xtext.resource.impl.ResourceDescriptionsProvider;
import org.eclipse.xtext.validation.Check;
import org.eclipse.xtext.validation.CheckType;

import com.odcgroup.menu.model.MenuItem;
import com.odcgroup.menu.model.MenuPackage;
import com.odcgroup.menu.model.MenuRoot;
 

public class MenuJavaValidator extends AbstractMenuJavaValidator {
	
	public static String DUPLICATE_MENU_NAME = "com.odcgroup.menu.model.DuplicateMenuName";

    @Inject
    private ResourceDescriptionsProvider resourceDescriptionsProvider;
    
    @Inject
    private IContainer.Manager containerManager;
    
    @Inject
    private IQualifiedNameProvider qualifiedNameProvider;
	/**
	 * Calculate the menu level.
	 * @param menuitems, level.
	 */
	private int calculateMenuLevel(MenuItem item,int level){
		level++;
		Object parent = item.eContainer();
		if(parent instanceof MenuRoot){
			return level;
		}
		level = calculateMenuLevel((MenuItem)parent, level);
		return level;
	}
	
	/**
	 * This method checks any special characters in the Menu name.
	 * 
	 * @param MenuItem
	 */
    @Check(CheckType.FAST) 
	public void checkforValidMenuName(MenuItem menuitem) {
		String itemname = menuitem.getName();
		String regex = "[a-zA-Z0-9.]*";
		boolean bmatched = Pattern.matches(regex, itemname.trim());
		if (!bmatched) {
			error("Menu name should not contain special characters: " + itemname, 
					MenuPackage.Literals.MENU_ITEM__NAME);
		}
	}
    
	/**
	 * Check the Parent has Leaf node if the parent has Display Children As Tab
	 * checked.
	 * 
	 * @param item
	 */
    @Check(CheckType.FAST) 
	public void checkChildrenAstabs(MenuItem item) {
		if (item.isDisplayTabs()) {
			List<MenuItem> subMenusList = item.getSubmenus();
			for (MenuItem subMenu : subMenusList) {
				if (!subMenu.getSubmenus().isEmpty()) {
					error("Display Childern As Tabs property is checked, Child Menu items must be leafs.", 
							MenuPackage.Literals.MENU_ITEM__DISPLAY_TABS);
				}
			}
		}
	}
    
	/**
	 * Validates the key board shortcut for menu item.
	 * 
	 * @param menuItem
	 */
    @Check(CheckType.FAST)
	public void checkKeyboardShortcut(MenuItem menuItem) {
    	
    	String shortcut = menuItem.getShortcut();
    	if (StringUtils.isBlank(shortcut)) {
    		// no shortcut defined, so nothing to compare.
    		return;
    	}
    	
    	shortcut = shortcut.trim().toUpperCase();

    	String pattern = "(CTRL\\+)?(ALT\\+)?(SHIFT\\+)?([^\\s]|F(1[0-9]?|2[0-4]?|[3-9]))"; // OK
    	if (!shortcut.matches(pattern)) {
			error("Invalid shortcut", MenuPackage.Literals.MENU_ITEM__SHORTCUT);
    	}
    }
    
	/**
	 * Validate the menu level if the menu level is more than 6 shows a error
	 * message.
	 * 
	 * @param item
	 */
	@Check(CheckType.FAST) 
	public void checkForMenuItemLevel(MenuItem item) {
		 int level=0;
		 level=calculateMenuLevel(item, level);
		 if (level > 6) {
			 error("Menu tree cannot be deeper than 6 levels", 
					 MenuPackage.Literals.MENU_ITEM__SUBMENUS);
		 }
    }
	
	/**
	 * This method checks for duplicate shortcuts in the whole menu.
	 * 
	 * @param MenuItem
	 */
    @Check(CheckType.FAST) 
	public void checkforDuplicateMenuShortcut(MenuItem menuItem) {
    	
    	String myShortcut = menuItem.getShortcut();
    	if (StringUtils.isBlank(myShortcut)) {
    		// no shortcut defined, so nothing to compare.
    		return;
    	}
    	
    	// normalize shortcut
    	myShortcut = myShortcut.trim().toLowerCase();
    	
    	// find the menu root.
    	MenuRoot root = null;
    	EObject parent = menuItem.eContainer(); 
    	while (!(parent instanceof MenuRoot)) {
    		parent = parent.eContainer();
    	}
    	root = (MenuRoot)parent;
    	
    	// walk through all child item to find a duplicate
    	int count = 0;
    	TreeIterator<EObject> contents = EcoreUtil.getAllContents(root, false);
    	while (contents.hasNext()) {
    		EObject eObject = contents.next();
    		if (eObject instanceof MenuItem) {
    			MenuItem item = (MenuItem)eObject;
    			String itemShortcut = item.getShortcut();
    			if (StringUtils.isNotBlank(itemShortcut)) {
	    			if (StringUtils.equals(myShortcut, itemShortcut.trim().toLowerCase())) {
	    				count++;
	    				if (count > 1) {
	    					error("The shortcut key is already in use, try different ",
	    							MenuPackage.Literals.MENU_ITEM__SHORTCUT);
	    					break;
	    				}
	    			}
    			}
    		}
    	}
	}
    
    /**
	 * This method checks only one value for shortcut.
	 * 
	 * @param MenuItem
	 */
    @Check(CheckType.NORMAL) 
    public void checkRepeatedKeyInMenuShortcut(MenuItem menuitem) {
		String itemShortcut = menuitem.getShortcut();
		if (itemShortcut != null) {
			if (itemShortcut.contains("+")) {
				if(MenuItemValidatorHelper.isKeyRepeated(menuitem)) {
					error("The repeated keys for shortcut are not allowed", MenuPackage.Literals.MENU_ITEM__SHORTCUT);
				}
			}
		}
	}
    
    /**
	 * This method checks that the pageflow property is defined
	 * 
	 * @param MenuItem
	 */
    @Check(CheckType.FAST) 
	public void checkPageflowProperty(MenuItem menuItem) {
		List<MenuItem> submenuList = menuItem.getSubmenus();
		if (submenuList.isEmpty()) {
			String pageFlow = menuItem.getPageflow();
			if (StringUtils.isBlank(pageFlow)) {
				error("Menu must define pageflow property", MenuPackage.Literals.MENU_ITEM__PAGEFLOW);
			}
		}
	}
    
    @Check(CheckType.NORMAL) 
    public void checkforDuplicateMenuName(MenuItem menuItem) {		
    	// retrieve menu root
    	EObject obj = menuItem;
    	while ( obj.eContainer() != null) {
    		obj = obj.eContainer();
    	}
    	
    	if (!(obj instanceof MenuRoot)) {
    		// invalid hierarchy
    		return;
    	}

    	// find duplicate menu item names 
    	Iterator<EObject> it = obj.eAllContents();
    	while (it.hasNext()) {
    		EObject eObj = it.next();
    		if (eObj instanceof MenuItem) {
    			MenuItem item = (MenuItem)eObj;
    			if (item != menuItem && menuItem.getName().equals(item.getName())) {
    				error("Duplicate menu name, should be unique within the menu module", MenuPackage.Literals.MENU_ITEM__NAME);
    				break;
    			}
    		}
    	}
	}
    
	/**
	 * Check for duplicate menu name across models
	 * 
	 * @param menuItem
	 */
    @Check(CheckType.NORMAL) 
    public void checkMenuDuplicateAccrossModels(MenuItem menuItem) {
    	
    	MenuRoot menuRoot = null;
    	EObject parent = menuItem.eContainer();
    	if (!(parent instanceof MenuRoot)) {
    		// this is not the root menu item
    		return;
    	}
    	
    	menuRoot = (MenuRoot)parent;
    	
    	QualifiedName menuQFN = qualifiedNameProvider.getFullyQualifiedName(menuRoot);  
        IResourceDescriptions resourceDescriptions = resourceDescriptionsProvider.getResourceDescriptions(menuRoot.eResource());
        
        URI eURI = menuRoot.eResource().getURI();
        
        IResourceDescription resourceDescription = resourceDescriptions.getResourceDescription(eURI);
        if (resourceDescription != null) {
	        Set<QualifiedName> names = new HashSet<QualifiedName>();
	        for (IContainer c : containerManager.getVisibleContainers(resourceDescription, resourceDescriptions)) {
	             for (IEObjectDescription od : c.getExportedObjectsByType(MenuPackage.Literals.MENU_ROOT)) {
	            	 QualifiedName qfn = od.getQualifiedName();
	            	 if (menuQFN.equals(qfn) && !names.add(qfn)) { 
	            		 error("A menu with the same name already exists in the project",
	            				 MenuPackage.Literals.MENU_ITEM__NAME, 
	            				 DUPLICATE_MENU_NAME
	            				 );
	            	 }
	             }
	        }
        }
    }

}
