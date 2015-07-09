/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.odcgroup.menu.edit.provider;


import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ItemProviderAdapter;
import org.eclipse.emf.edit.provider.ViewerNotification;

import com.odcgroup.menu.edit.MenuEditPlugin;
import com.odcgroup.menu.model.MenuFactory;
import com.odcgroup.menu.model.MenuItem;
import com.odcgroup.menu.model.MenuPackage;
import com.odcgroup.menu.model.impl.MenuItemImpl;
import com.odcgroup.translation.core.ITranslation;
import com.odcgroup.translation.core.ITranslationManager;
import com.odcgroup.translation.core.TranslationCore;
import com.odcgroup.translation.ui.views.ITranslationModel;
import com.odcgroup.translation.ui.views.model.TranslationModel;
import com.odcgroup.workbench.core.repository.OfsResourceHelper;

/**
 * This is the item provider adapter for a {@link com.odcgroup.menu.model.MenuItem} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class MenuItemItemProvider
	extends ItemProviderAdapter
	implements
		IEditingDomainItemProvider,
		IStructuredItemContentProvider,
		ITreeItemContentProvider,
		IItemLabelProvider,
		IItemPropertySource {
	/**
	 * This constructs an instance from a factory and a notifier.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MenuItemItemProvider(AdapterFactory adapterFactory) {
		super(adapterFactory);
	}

	/**
	 * This returns the property descriptors for the adapted class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public List<IItemPropertyDescriptor> getPropertyDescriptors(Object object) {
		if (itemPropertyDescriptors == null) {
			super.getPropertyDescriptors(object);

			addNamePropertyDescriptor(object);
			addPageflowPropertyDescriptor(object);
			addEnabledPropertyDescriptor(object);
			addDisplayTabsPropertyDescriptor(object);
			addSecurityRolePropertyDescriptor(object);
		}
		return itemPropertyDescriptors;
	}

	/**
	 * This adds a property descriptor for the Name feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addNamePropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_MenuItem_name_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_MenuItem_name_feature", "_UI_MenuItem_type"),
				 MenuPackage.Literals.MENU_ITEM__NAME,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Pageflow feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addPageflowPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_MenuItem_pageflow_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_MenuItem_pageflow_feature", "_UI_MenuItem_type"),
				 MenuPackage.Literals.MENU_ITEM__PAGEFLOW,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Enabled feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addEnabledPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_MenuItem_enabled_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_MenuItem_enabled_feature", "_UI_MenuItem_type"),
				 MenuPackage.Literals.MENU_ITEM__ENABLED,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Display Tabs feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addDisplayTabsPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_MenuItem_displayTabs_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_MenuItem_displayTabs_feature", "_UI_MenuItem_type"),
				 MenuPackage.Literals.MENU_ITEM__DISPLAY_TABS,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Security Role feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addSecurityRolePropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_MenuItem_securityRole_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_MenuItem_securityRole_feature", "_UI_MenuItem_type"),
				 MenuPackage.Literals.MENU_ITEM__SECURITY_ROLE,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This specifies how to implement {@link #getChildren} and is used to deduce an appropriate feature for an
	 * {@link org.eclipse.emf.edit.command.AddCommand}, {@link org.eclipse.emf.edit.command.RemoveCommand} or
	 * {@link org.eclipse.emf.edit.command.MoveCommand} in {@link #createCommand}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	@Override
	public Collection<? extends EStructuralFeature> getChildrenFeatures(Object object) {
		if (childrenFeatures == null) {
			super.getChildrenFeatures(object);
			//childrenFeatures.add(MenuPackage.Literals.MENU_ITEM__LABELS);
			childrenFeatures.add(MenuPackage.Literals.MENU_ITEM__SUBMENUS);
		}
		return childrenFeatures;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EStructuralFeature getChildFeature(Object object, Object child) {
		// Check the type of the specified child object and return the proper feature to use for
		// adding (see {@link AddCommand}) it as a child.

		return super.getChildFeature(object, child);
	}

	/**
	 * This returns MenuItem.gif.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object getImage(Object object) {
		return overlayImage(object, getResourceLocator().getImage("full/obj16/MenuItem"));
	}

	/**
	 * This returns the label text for the adapted class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	@Override
	public String getText(Object object) {
		MenuItem item = (MenuItem) object;
		String label = getTranslation(item);
		if (StringUtils.isEmpty(label)) {
			label = ((MenuItem)object).getName();
		}
		return label == null || label.length() == 0 ?
			getString("_UI_MenuItem_type") : label;
	}

	/**
	 * This handles model notifications by calling {@link #updateChildren} to update any cached
	 * children and by creating a viewer notification, which it passes to {@link #fireNotifyChanged}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void notifyChanged(Notification notification) {
		updateChildren(notification);

		switch (notification.getFeatureID(MenuItem.class)) {
			case MenuPackage.MENU_ITEM__NAME:
			case MenuPackage.MENU_ITEM__PAGEFLOW:
			case MenuPackage.MENU_ITEM__ENABLED:
			case MenuPackage.MENU_ITEM__DISPLAY_TABS:
			case MenuPackage.MENU_ITEM__SECURITY_ROLE:
				fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), false, true));
				return;
			case MenuPackage.MENU_ITEM__LABELS:
			case MenuPackage.MENU_ITEM__SUBMENUS:
				fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), true, false));
				return;
		}
		super.notifyChanged(notification);
	}

	/**
	 * This adds {@link org.eclipse.emf.edit.command.CommandParameter}s describing the children
	 * that can be created under this object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	@Override
	protected void collectNewChildDescriptors(Collection<Object> newChildDescriptors, Object object) {
		super.collectNewChildDescriptors(newChildDescriptors, object);
		if (object instanceof MenuItem) {
			MenuItemImpl menuItem = (MenuItemImpl) MenuFactory.eINSTANCE.createMenuItem();
			menuItem.setName("NewItem");
			newChildDescriptors.add(createChildParameter(MenuPackage.Literals.MENU_ITEM__SUBMENUS, menuItem));
		}			
	
	}
	
	/**
	 * @param item
	 * @return
	 */
	private String getTranslation(MenuItem item) {
		IProject project = OfsResourceHelper.getProject(item.eResource());
		ITranslationManager manager = TranslationCore.getTranslationManager(project);
		ITranslation translation = manager.getTranslation(item);
		ITranslationModel model = null;
		if (translation != null) {
			model = new TranslationModel(manager.getPreferences(), translation);
		}
		return model.getText();
	}	
	

	/**
	 * Return the resource locator for this item provider's resources.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ResourceLocator getResourceLocator() {
		return MenuEditPlugin.INSTANCE;
	}

}
