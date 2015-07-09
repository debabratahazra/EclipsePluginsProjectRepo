/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.odcgroup.page.metamodel.provider;


import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ViewerNotification;

import com.odcgroup.page.metamodel.MetaModelFactory;
import com.odcgroup.page.metamodel.MetaModelPackage;
import com.odcgroup.page.metamodel.WidgetType;

/**
 * This is the item provider adapter for a {@link com.odcgroup.page.metamodel.WidgetType} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class WidgetTypeItemProvider
	extends NamedTypeItemProvider
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
	public WidgetTypeItemProvider(AdapterFactory adapterFactory) {
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

			addParentPropertyDescriptor(object);
			addPropertyTypesPropertyDescriptor(object);
			addExcludedPropertyTypesPropertyDescriptor(object);
			addDisplayNamePropertyDescriptor(object);
			addPropertyFilterClassPropertyDescriptor(object);
			addDerivablePropertyDescriptor(object);
			addStrictAccountabilityPropertyDescriptor(object);
			addDomainWidgetPropertyDescriptor(object);
			addCanDropDomainElementPropertyDescriptor(object);
			addRichtextPropertyDescriptor(object);
		}
		return itemPropertyDescriptors;
	}

	/**
	 * This adds a property descriptor for the Parent feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addParentPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_WidgetType_parent_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_WidgetType_parent_feature", "_UI_WidgetType_type"),
				 MetaModelPackage.Literals.WIDGET_TYPE__PARENT,
				 true,
				 false,
				 true,
				 null,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Property Types feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addPropertyTypesPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_WidgetType_propertyTypes_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_WidgetType_propertyTypes_feature", "_UI_WidgetType_type"),
				 MetaModelPackage.Literals.WIDGET_TYPE__PROPERTY_TYPES,
				 true,
				 false,
				 true,
				 null,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Excluded Property Types feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addExcludedPropertyTypesPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_WidgetType_excludedPropertyTypes_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_WidgetType_excludedPropertyTypes_feature", "_UI_WidgetType_type"),
				 MetaModelPackage.Literals.WIDGET_TYPE__EXCLUDED_PROPERTY_TYPES,
				 true,
				 false,
				 true,
				 null,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Display Name feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addDisplayNamePropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_WidgetType_displayName_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_WidgetType_displayName_feature", "_UI_WidgetType_type"),
				 MetaModelPackage.Literals.WIDGET_TYPE__DISPLAY_NAME,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Property Filter Class feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addPropertyFilterClassPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_WidgetType_propertyFilterClass_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_WidgetType_propertyFilterClass_feature", "_UI_WidgetType_type"),
				 MetaModelPackage.Literals.WIDGET_TYPE__PROPERTY_FILTER_CLASS,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Derivable feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addDerivablePropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_WidgetType_derivable_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_WidgetType_derivable_feature", "_UI_WidgetType_type"),
				 MetaModelPackage.Literals.WIDGET_TYPE__DERIVABLE,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Strict Accountability feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addStrictAccountabilityPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_WidgetType_strictAccountability_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_WidgetType_strictAccountability_feature", "_UI_WidgetType_type"),
				 MetaModelPackage.Literals.WIDGET_TYPE__STRICT_ACCOUNTABILITY,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Domain Widget feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addDomainWidgetPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_WidgetType_domainWidget_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_WidgetType_domainWidget_feature", "_UI_WidgetType_type"),
				 MetaModelPackage.Literals.WIDGET_TYPE__DOMAIN_WIDGET,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Can Drop Domain Element feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addCanDropDomainElementPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_WidgetType_canDropDomainElement_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_WidgetType_canDropDomainElement_feature", "_UI_WidgetType_type"),
				 MetaModelPackage.Literals.WIDGET_TYPE__CAN_DROP_DOMAIN_ELEMENT,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Richtext feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addRichtextPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_WidgetType_richtext_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_WidgetType_richtext_feature", "_UI_WidgetType_type"),
				 MetaModelPackage.Literals.WIDGET_TYPE__RICHTEXT,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This returns WidgetType.gif.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object getImage(Object object) {
		return overlayImage(object, getResourceLocator().getImage("full/obj16/WidgetType"));
	}

	/**
	 * This returns the label text for the adapted class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getText(Object object) {
		String label = ((WidgetType)object).getName();
		return label == null || label.length() == 0 ?
			getString("_UI_WidgetType_type") :
			getString("_UI_WidgetType_type") + " " + label;
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

		switch (notification.getFeatureID(WidgetType.class)) {
			case MetaModelPackage.WIDGET_TYPE__DISPLAY_NAME:
			case MetaModelPackage.WIDGET_TYPE__PROPERTY_FILTER_CLASS:
			case MetaModelPackage.WIDGET_TYPE__DERIVABLE:
			case MetaModelPackage.WIDGET_TYPE__STRICT_ACCOUNTABILITY:
			case MetaModelPackage.WIDGET_TYPE__DOMAIN_WIDGET:
			case MetaModelPackage.WIDGET_TYPE__CAN_DROP_DOMAIN_ELEMENT:
			case MetaModelPackage.WIDGET_TYPE__RICHTEXT:
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
	@Override
	protected void collectNewChildDescriptors(Collection<Object> newChildDescriptors, Object object) {
		super.collectNewChildDescriptors(newChildDescriptors, object);
	}

}
