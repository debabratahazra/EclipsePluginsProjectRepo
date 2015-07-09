/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.odcgroup.mdf.editor.ui.editors.providers;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.ComposedImage;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ViewerNotification;

import com.odcgroup.mdf.ecore.MdfPackage;
import com.odcgroup.mdf.metamodel.MdfDatasetDerivedProperty;

/**
 * This is the item provider adapter for a {@link com.odcgroup.mdf.metamodel.MdfDatasetDerivedProperty} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class MdfDatasetDerivedPropertyItemProvider
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
	public MdfDatasetDerivedPropertyItemProvider(AdapterFactory adapterFactory) {
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

			addDefaultPropertyDescriptor(object);
			addMultiplicityPropertyDescriptor(object);
			addTypePropertyDescriptor(object);
		}
		return itemPropertyDescriptors;
	}

	/**
	 * This adds a property descriptor for the Default feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addDefaultPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_MdfDatasetDerivedProperty_default_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_MdfDatasetDerivedProperty_default_feature", "_UI_MdfDatasetDerivedProperty_type"),
				 MdfPackage.Literals.MDF_DATASET_DERIVED_PROPERTY__DEFAULT,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Multiplicity feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addMultiplicityPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_MdfDatasetDerivedProperty_multiplicity_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_MdfDatasetDerivedProperty_multiplicity_feature", "_UI_MdfDatasetDerivedProperty_type"),
				 MdfPackage.Literals.MDF_DATASET_DERIVED_PROPERTY__MULTIPLICITY,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.INTEGRAL_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Type feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addTypePropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_MdfDatasetDerivedProperty_type_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_MdfDatasetDerivedProperty_type_feature", "_UI_MdfDatasetDerivedProperty_type"),
				 MdfPackage.Literals.MDF_DATASET_DERIVED_PROPERTY__TYPE,
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
		List<Object> images = new ArrayList<Object>(2);
	    images.add(super.getImage(object));
	    images.add(getResourceLocator().getImage("sum"));
	    return new ComposedImage(images);		
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

		switch (notification.getFeatureID(MdfDatasetDerivedProperty.class)) {
			case MdfPackage.MDF_DATASET_DERIVED_PROPERTY__DEFAULT:
			case MdfPackage.MDF_DATASET_DERIVED_PROPERTY__MULTIPLICITY:
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
        String label = ((MdfDatasetDerivedProperty)object).getName();
        return label == null || label.length() == 0 ?
            getString("_UI_MdfDatasetDerivedProperty_type") : label;
    }

}
