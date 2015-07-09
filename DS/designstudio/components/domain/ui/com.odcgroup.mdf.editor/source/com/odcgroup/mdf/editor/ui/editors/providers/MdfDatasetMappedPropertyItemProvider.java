/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.odcgroup.mdf.editor.ui.editors.providers;


import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ViewerNotification;

import com.odcgroup.mdf.ecore.MdfPackage;
import com.odcgroup.mdf.metamodel.MdfDatasetMappedProperty;

/**
 * This is the item provider adapter for a {@link com.odcgroup.mdf.metamodel.MdfDatasetMappedProperty} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class MdfDatasetMappedPropertyItemProvider
	extends MdfDatasetPropertyItemProvider
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
	public MdfDatasetMappedPropertyItemProvider(AdapterFactory adapterFactory) {
		super(adapterFactory);
	}

	/**
	 * This returns the property descriptors for the adapted class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public List getPropertyDescriptors(Object object) {
		if (itemPropertyDescriptors == null) {
			super.getPropertyDescriptors(object);

			addPathPropertyDescriptor(object);
			addUniquePropertyDescriptor(object);
			addSingleValuedPropertyDescriptor(object);
			addLinkDatasetPropertyDescriptor(object);
		}
		return itemPropertyDescriptors;
	}

	/**
	 * This adds a property descriptor for the Path feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addPathPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_MdfDatasetMappedProperty_path_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_MdfDatasetMappedProperty_path_feature", "_UI_MdfDatasetMappedProperty_type"),
				 MdfPackage.Literals.MDF_DATASET_MAPPED_PROPERTY__PATH,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Unique feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addUniquePropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_MdfDatasetMappedProperty_unique_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_MdfDatasetMappedProperty_unique_feature", "_UI_MdfDatasetMappedProperty_type"),
				 MdfPackage.Literals.MDF_DATASET_MAPPED_PROPERTY__UNIQUE,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Single Valued feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addSingleValuedPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_MdfDatasetMappedProperty_singleValued_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_MdfDatasetMappedProperty_singleValued_feature", "_UI_MdfDatasetMappedProperty_type"),
				 MdfPackage.Literals.MDF_DATASET_MAPPED_PROPERTY__SINGLE_VALUED,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Link Dataset feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addLinkDatasetPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_MdfDatasetMappedProperty_linkDataset_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_MdfDatasetMappedProperty_linkDataset_feature", "_UI_MdfDatasetMappedProperty_type"),
				 MdfPackage.Literals.MDF_DATASET_MAPPED_PROPERTY__LINK_DATASET,
				 true,
				 false,
				 true,
				 null,
				 null,
				 null));
	}

	/**
	 * This returns MdfDatasetMappedProperty.gif.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 */
	public Object getImage(Object object) {
		return super.getImage(object);
	}

	/**
	 * This handles model notifications by calling {@link #updateChildren} to update any cached
	 * children and by creating a viewer notification, which it passes to {@link #fireNotifyChanged}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void notifyChanged(Notification notification) {
		updateChildren(notification);

		switch (notification.getFeatureID(MdfDatasetMappedProperty.class)) {
			case MdfPackage.MDF_DATASET_MAPPED_PROPERTY__PATH:
			case MdfPackage.MDF_DATASET_MAPPED_PROPERTY__UNIQUE:
			case MdfPackage.MDF_DATASET_MAPPED_PROPERTY__SINGLE_VALUED:
				fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), false, true));
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
	protected void collectNewChildDescriptors(Collection newChildDescriptors, Object object) {
		super.collectNewChildDescriptors(newChildDescriptors, object);
	}
	
    /**
     * This returns the label text for the adapted class.
     */
    public String getText(Object object) {
        String label = ((MdfDatasetMappedProperty)object).getName();
        return label == null || label.length() == 0 ?
            getString("_UI_MdfDatasetMappedProperty_type") : label;
    }

}
