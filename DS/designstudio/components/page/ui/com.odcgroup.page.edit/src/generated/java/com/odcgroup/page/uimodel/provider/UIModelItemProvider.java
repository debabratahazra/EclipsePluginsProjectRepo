/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.odcgroup.page.uimodel.provider;


import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;
import org.eclipse.emf.edit.provider.ItemProviderAdapter;
import org.eclipse.emf.edit.provider.ViewerNotification;

import com.odcgroup.page.uimodel.UIModel;
import com.odcgroup.page.uimodel.UIModelFactory;
import com.odcgroup.page.uimodel.UIModelPackage;

/**
 * This is the item provider adapter for a {@link com.odcgroup.page.uimodel.UIModel} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class UIModelItemProvider
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
	public UIModelItemProvider(AdapterFactory adapterFactory) {
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

		}
		return itemPropertyDescriptors;
	}

	/**
	 * This specifies how to implement {@link #getChildren} and is used to deduce an appropriate feature for an
	 * {@link org.eclipse.emf.edit.command.AddCommand}, {@link org.eclipse.emf.edit.command.RemoveCommand} or
	 * {@link org.eclipse.emf.edit.command.MoveCommand} in {@link #createCommand}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Collection<? extends EStructuralFeature> getChildrenFeatures(Object object) {
		if (childrenFeatures == null) {
			super.getChildrenFeatures(object);
			childrenFeatures.add(UIModelPackage.Literals.UI_MODEL__PALETTE);
			childrenFeatures.add(UIModelPackage.Literals.UI_MODEL__RENDERERS);
			childrenFeatures.add(UIModelPackage.Literals.UI_MODEL__PALETTE_GROUP_ITEMS);
			childrenFeatures.add(UIModelPackage.Literals.UI_MODEL__DEFAULT_PALETTE);
			childrenFeatures.add(UIModelPackage.Literals.UI_MODEL__MENUS);
			childrenFeatures.add(UIModelPackage.Literals.UI_MODEL__ACTION_GROUPS);
			childrenFeatures.add(UIModelPackage.Literals.UI_MODEL__ACTIONS);
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
	 * This returns UIModel.gif.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object getImage(Object object) {
		return overlayImage(object, getResourceLocator().getImage("full/obj16/UIModel"));
	}

	/**
	 * This returns the label text for the adapted class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getText(Object object) {
		return getString("_UI_UIModel_type");
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

		switch (notification.getFeatureID(UIModel.class)) {
			case UIModelPackage.UI_MODEL__PALETTE:
			case UIModelPackage.UI_MODEL__RENDERERS:
			case UIModelPackage.UI_MODEL__PALETTE_GROUP_ITEMS:
			case UIModelPackage.UI_MODEL__DEFAULT_PALETTE:
			case UIModelPackage.UI_MODEL__MENUS:
			case UIModelPackage.UI_MODEL__ACTION_GROUPS:
			case UIModelPackage.UI_MODEL__ACTIONS:
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
	 * @generated
	 */
	@Override
	protected void collectNewChildDescriptors(Collection<Object> newChildDescriptors, Object object) {
		super.collectNewChildDescriptors(newChildDescriptors, object);

		newChildDescriptors.add
			(createChildParameter
				(UIModelPackage.Literals.UI_MODEL__PALETTE,
				 UIModelFactory.eINSTANCE.createPalette()));

		newChildDescriptors.add
			(createChildParameter
				(UIModelPackage.Literals.UI_MODEL__RENDERERS,
				 UIModelFactory.eINSTANCE.createRenderers()));

		newChildDescriptors.add
			(createChildParameter
				(UIModelPackage.Literals.UI_MODEL__PALETTE_GROUP_ITEMS,
				 UIModelFactory.eINSTANCE.createPaletteGroupItem()));

		newChildDescriptors.add
			(createChildParameter
				(UIModelPackage.Literals.UI_MODEL__DEFAULT_PALETTE,
				 UIModelFactory.eINSTANCE.createPalette()));

		newChildDescriptors.add
			(createChildParameter
				(UIModelPackage.Literals.UI_MODEL__MENUS,
				 UIModelFactory.eINSTANCE.createMenus()));

		newChildDescriptors.add
			(createChildParameter
				(UIModelPackage.Literals.UI_MODEL__ACTION_GROUPS,
				 UIModelFactory.eINSTANCE.createActionGroups()));

		newChildDescriptors.add
			(createChildParameter
				(UIModelPackage.Literals.UI_MODEL__ACTIONS,
				 UIModelFactory.eINSTANCE.createActions()));
	}

	/**
	 * This returns the label text for {@link org.eclipse.emf.edit.command.CreateChildCommand}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getCreateChildText(Object owner, Object feature, Object child, Collection<?> selection) {
		Object childFeature = feature;
		Object childObject = child;

		boolean qualify =
			childFeature == UIModelPackage.Literals.UI_MODEL__PALETTE ||
			childFeature == UIModelPackage.Literals.UI_MODEL__DEFAULT_PALETTE;

		if (qualify) {
			return getString
				("_UI_CreateChild_text2",
				 new Object[] { getTypeText(childObject), getFeatureText(childFeature), getTypeText(owner) });
		}
		return super.getCreateChildText(owner, feature, child, selection);
	}

	/**
	 * Return the resource locator for this item provider's resources.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ResourceLocator getResourceLocator() {
		return PageUIModelEditPlugin.INSTANCE;
	}

}
