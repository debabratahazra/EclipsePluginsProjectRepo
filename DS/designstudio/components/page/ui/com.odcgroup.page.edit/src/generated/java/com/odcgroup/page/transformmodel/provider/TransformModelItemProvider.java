/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.odcgroup.page.transformmodel.provider;


import java.util.Collection;
import java.util.List;

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
import org.eclipse.emf.edit.provider.ItemProviderAdapter;
import org.eclipse.emf.edit.provider.ViewerNotification;

import com.odcgroup.page.transformmodel.TransformModel;
import com.odcgroup.page.transformmodel.TransformModelFactory;
import com.odcgroup.page.transformmodel.TransformModelPackage;

/**
 * This is the item provider adapter for a {@link com.odcgroup.page.transformmodel.TransformModel} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class TransformModelItemProvider
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
	public TransformModelItemProvider(AdapterFactory adapterFactory) {
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

			addDefaultNamespacePropertyDescriptor(object);
			addDefaultWidgetLibraryPropertyDescriptor(object);
		}
		return itemPropertyDescriptors;
	}

	/**
	 * This adds a property descriptor for the Default Namespace feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addDefaultNamespacePropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_TransformModel_defaultNamespace_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_TransformModel_defaultNamespace_feature", "_UI_TransformModel_type"),
				 TransformModelPackage.Literals.TRANSFORM_MODEL__DEFAULT_NAMESPACE,
				 true,
				 false,
				 false,
				 null,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Default Widget Library feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addDefaultWidgetLibraryPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_TransformModel_defaultWidgetLibrary_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_TransformModel_defaultWidgetLibrary_feature", "_UI_TransformModel_type"),
				 TransformModelPackage.Literals.TRANSFORM_MODEL__DEFAULT_WIDGET_LIBRARY,
				 true,
				 false,
				 true,
				 null,
				 null,
				 null));
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
			childrenFeatures.add(TransformModelPackage.Literals.TRANSFORM_MODEL__NAMESPACES);
			childrenFeatures.add(TransformModelPackage.Literals.TRANSFORM_MODEL__WIDGET_TRANSFORMERS);
			childrenFeatures.add(TransformModelPackage.Literals.TRANSFORM_MODEL__PROPERTY_TRANSFORMERS);
			childrenFeatures.add(TransformModelPackage.Literals.TRANSFORM_MODEL__EVENT_TRANSFORMATIONS);
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
	 * This returns TransformModel.gif.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object getImage(Object object) {
		return overlayImage(object, getResourceLocator().getImage("full/obj16/TransformModel"));
	}

	/**
	 * This returns the label text for the adapted class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getText(Object object) {
		return getString("_UI_TransformModel_type");
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

		switch (notification.getFeatureID(TransformModel.class)) {
			case TransformModelPackage.TRANSFORM_MODEL__DEFAULT_NAMESPACE:
				fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), false, true));
				return;
			case TransformModelPackage.TRANSFORM_MODEL__NAMESPACES:
			case TransformModelPackage.TRANSFORM_MODEL__WIDGET_TRANSFORMERS:
			case TransformModelPackage.TRANSFORM_MODEL__PROPERTY_TRANSFORMERS:
			case TransformModelPackage.TRANSFORM_MODEL__EVENT_TRANSFORMATIONS:
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
				(TransformModelPackage.Literals.TRANSFORM_MODEL__NAMESPACES,
				 TransformModelFactory.eINSTANCE.createNamespace()));

		newChildDescriptors.add
			(createChildParameter
				(TransformModelPackage.Literals.TRANSFORM_MODEL__WIDGET_TRANSFORMERS,
				 TransformModelFactory.eINSTANCE.createElementNameWidgetTransformer()));

		newChildDescriptors.add
			(createChildParameter
				(TransformModelPackage.Literals.TRANSFORM_MODEL__WIDGET_TRANSFORMERS,
				 TransformModelFactory.eINSTANCE.createExtraParentWidgetTransformer()));

		newChildDescriptors.add
			(createChildParameter
				(TransformModelPackage.Literals.TRANSFORM_MODEL__WIDGET_TRANSFORMERS,
				 TransformModelFactory.eINSTANCE.createGenericWidgetTransformer()));

		newChildDescriptors.add
			(createChildParameter
				(TransformModelPackage.Literals.TRANSFORM_MODEL__WIDGET_TRANSFORMERS,
				 TransformModelFactory.eINSTANCE.createNullWidgetTransformer()));

		newChildDescriptors.add
			(createChildParameter
				(TransformModelPackage.Literals.TRANSFORM_MODEL__WIDGET_TRANSFORMERS,
				 TransformModelFactory.eINSTANCE.createChildrenWidgetTransformer()));

		newChildDescriptors.add
			(createChildParameter
				(TransformModelPackage.Literals.TRANSFORM_MODEL__PROPERTY_TRANSFORMERS,
				 TransformModelFactory.eINSTANCE.createAttributeNamePropertyTransformer()));

		newChildDescriptors.add
			(createChildParameter
				(TransformModelPackage.Literals.TRANSFORM_MODEL__PROPERTY_TRANSFORMERS,
				 TransformModelFactory.eINSTANCE.createElementPropertyTransformer()));

		newChildDescriptors.add
			(createChildParameter
				(TransformModelPackage.Literals.TRANSFORM_MODEL__PROPERTY_TRANSFORMERS,
				 TransformModelFactory.eINSTANCE.createGenericPropertyTransformer()));

		newChildDescriptors.add
			(createChildParameter
				(TransformModelPackage.Literals.TRANSFORM_MODEL__PROPERTY_TRANSFORMERS,
				 TransformModelFactory.eINSTANCE.createNullPropertyTransformer()));

		newChildDescriptors.add
			(createChildParameter
				(TransformModelPackage.Literals.TRANSFORM_MODEL__EVENT_TRANSFORMATIONS,
				 TransformModelFactory.eINSTANCE.createEventTransformations()));
	}

	/**
	 * Return the resource locator for this item provider's resources.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ResourceLocator getResourceLocator() {
		return TransformModelEditPlugin.INSTANCE;
	}

}
