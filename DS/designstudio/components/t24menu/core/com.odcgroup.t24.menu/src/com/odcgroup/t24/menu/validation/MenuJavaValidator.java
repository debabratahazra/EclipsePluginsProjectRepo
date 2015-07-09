package com.odcgroup.t24.menu.validation;

import java.util.List;
import java.util.regex.Pattern;

import javax.inject.Inject;

import org.apache.commons.lang.StringUtils;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.xtext.naming.IQualifiedNameProvider;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.IContainer;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.resource.IResourceDescriptions;
import org.eclipse.xtext.resource.impl.ResourceDescriptionsProvider;
import org.eclipse.xtext.validation.Check;
import org.eclipse.xtext.validation.CheckType;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

import com.odcgroup.t24.menu.menu.MenuItem;
import com.odcgroup.t24.menu.menu.MenuPackage;
import com.odcgroup.t24.menu.menu.MenuRoot;
import com.odcgroup.t24.menu.menu.impl.MenuPackageImpl;

public class MenuJavaValidator extends AbstractMenuJavaValidator {
	
    @Inject
    private ResourceDescriptionsProvider resourceDescriptionsProvider;
    
    @Inject
    private IContainer.Manager containerManager;
    
    @Inject
    private IQualifiedNameProvider qualifiedNameProvider;
    
	/**
	 * Calculate the menu level.
	 * @parm menuitems, level.
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
		String regex = "[a-zA-Z0-9_.]*";
		boolean bmatched = Pattern.matches(regex, itemname.trim());
		if (!bmatched) {
			error("Menu name should not contain special characters: " + itemname, 
					MenuPackage.Literals.MENU_ITEM__NAME);
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

    	String pattern = "(CTRL\\+)?(ALT\\+)?(SHIFT\\+)?[^\\s]|[^\\s]|F(1[0-9]?|2[0-4]?|[3-9])";
    	if (!shortcut.matches(pattern)) {
			error("Invalid shortcut", MenuPackage.Literals.MENU_ITEM__SHORTCUT);
    	}
    }
    
	/**
	 * This method checks for duplicate menu item name in the whole model
	 * 
	 * @param MenuItem
	 */
    @Check(CheckType.FAST) 
	public void checkforDuplicateMenuItemName(MenuItem menuItem) {
    	
    	String myName = menuItem.getName();
    	if (StringUtils.isBlank(myName)) {
    		// no name defined, so nothing to compare.
    		return;
    	}
    	
    	// normalize the name
    	myName = myName.trim().toLowerCase();
    	
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
    			String itemName = item.getName();
    			if (StringUtils.isNotBlank(itemName)) {
	    			if (StringUtils.equals(myName, itemName.trim().toLowerCase())) {
	    				count++;
	    				if (count > 1) {
	    					error("Duplicate menu item name, they should be unique within the menu model",
	    							MenuPackage.Literals.MENU_ITEM__NAME);
	    					break;
	    				}
	    			}
    			}
    		}
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
        
        Iterable<IEObjectDescription> objects = resourceDescriptions.getExportedObjects(MenuPackageImpl.Literals.MENU_ROOT, menuQFN, true);
        if (IterableExtensions.size(objects) > 1) {
		 error("A menu with the same name already exists in the project", MenuPackage.Literals.MENU_ITEM__NAME);
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
	 * Check if the physical file name of resource match the "name" in the model
	 * (of the "root" content EObject).
	 * 
	 * @param version
	 */
	@Check(CheckType.FAST)
	public void checkFileNameAndRootModelNameAreSame(MenuRoot menuRoot) {
		String menuName = menuRoot.eResource().getURI().lastSegment();
		String menuFileName = menuName.substring(0, menuName.lastIndexOf("."));

		String menuRootName = menuRoot.getRootItem().getName();

		if (StringUtils.isNotBlank(menuFileName) && StringUtils.isNotBlank(menuRootName)
				&& !menuFileName.equals(menuRootName)) {
			error("Menu file name and root model name are not same.", MenuPackage.Literals.MENU_ROOT__ROOT_ITEM);
		}
	}
}
