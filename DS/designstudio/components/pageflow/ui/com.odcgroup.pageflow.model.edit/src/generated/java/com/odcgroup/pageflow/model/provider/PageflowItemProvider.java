/**
 * ODCGROUP
 *
 * $Id$
 */
package com.odcgroup.pageflow.model.provider;


import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ItemProviderAdapter;
import org.eclipse.emf.edit.provider.ViewerNotification;

import com.odcgroup.pageflow.model.Pageflow;
import com.odcgroup.pageflow.model.PageflowFactory;
import com.odcgroup.pageflow.model.PageflowPackage;

/**
 * This is the item provider adapter for a {@link com.odcgroup.pageflow.model.Pageflow} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class PageflowItemProvider
	extends ItemProviderAdapter
	implements	
		IEditingDomainItemProvider,	
		IStructuredItemContentProvider,	
		ITreeItemContentProvider,	
		IItemLabelProvider,	
		IItemPropertySource {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String copyright = "ODCGROUP";

	/**
	 * This constructs an instance from a factory and a notifier.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PageflowItemProvider(AdapterFactory adapterFactory) {
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

			addDescPropertyDescriptor(object);
			addNamePropertyDescriptor(object);
			addPropertyPropertyDescriptor(object);
			addIdPropertyDescriptor(object);
			addFileNamePropertyDescriptor(object);
			addTechDescPropertyDescriptor(object);
			addMetamodelVersionPropertyDescriptor(object);
		}
		return itemPropertyDescriptors;
	}

	/**
	 * This adds a property descriptor for the Desc feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addDescPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_Pageflow_desc_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_Pageflow_desc_feature", "_UI_Pageflow_type"),
				 PageflowPackage.Literals.PAGEFLOW__DESC,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
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
				 getString("_UI_Pageflow_name_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_Pageflow_name_feature", "_UI_Pageflow_type"),
				 PageflowPackage.Literals.PAGEFLOW__NAME,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Property feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addPropertyPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_Pageflow_property_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_Pageflow_property_feature", "_UI_Pageflow_type"),
				 PageflowPackage.Literals.PAGEFLOW__PROPERTY,
				 true,
				 false,
				 true,
				 null,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Id feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addIdPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_Pageflow_id_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_Pageflow_id_feature", "_UI_Pageflow_type"),
				 PageflowPackage.Literals.PAGEFLOW__ID,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the File Name feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addFileNamePropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_Pageflow_fileName_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_Pageflow_fileName_feature", "_UI_Pageflow_type"),
				 PageflowPackage.Literals.PAGEFLOW__FILE_NAME,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Tech Desc feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addTechDescPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_Pageflow_techDesc_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_Pageflow_techDesc_feature", "_UI_Pageflow_type"),
				 PageflowPackage.Literals.PAGEFLOW__TECH_DESC,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Metamodel Version feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addMetamodelVersionPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_Pageflow_metamodelVersion_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_Pageflow_metamodelVersion_feature", "_UI_Pageflow_type"),
				 PageflowPackage.Literals.PAGEFLOW__METAMODEL_VERSION,
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
	 * @generated
	 */
	public Collection getChildrenFeatures(Object object) {
		if (childrenFeatures == null) {
			super.getChildrenFeatures(object);
			childrenFeatures.add(PageflowPackage.Literals.PAGEFLOW__STATES);
			childrenFeatures.add(PageflowPackage.Literals.PAGEFLOW__ABORT_VIEW);
			childrenFeatures.add(PageflowPackage.Literals.PAGEFLOW__ERROR_VIEW);
			childrenFeatures.add(PageflowPackage.Literals.PAGEFLOW__TRANSITIONS);
		}
		return childrenFeatures;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EStructuralFeature getChildFeature(Object object, Object child) {
		// Check the type of the specified child object and return the proper feature to use for
		// adding (see {@link AddCommand}) it as a child.

		return super.getChildFeature(object, child);
	}

	/**
	 * This returns Pageflow.gif.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object getImage(Object object) {
		return overlayImage(object, getResourceLocator().getImage("full/obj16/Pageflow"));
	}

	/**
	 * This returns the label text for the adapted class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated not
	 */
	public String getText(Object object) {
		String label = ((Pageflow)object).getName();
		return label == null || label.length() == 0 ?
			getString("_UI_Pageflow_type") :
			label;
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

		switch (notification.getFeatureID(Pageflow.class)) {
			case PageflowPackage.PAGEFLOW__DESC:
			case PageflowPackage.PAGEFLOW__NAME:
			case PageflowPackage.PAGEFLOW__ID:
			case PageflowPackage.PAGEFLOW__FILE_NAME:
			case PageflowPackage.PAGEFLOW__TECH_DESC:
			case PageflowPackage.PAGEFLOW__METAMODEL_VERSION:
				fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), false, true));
				return;
			case PageflowPackage.PAGEFLOW__STATES:
			case PageflowPackage.PAGEFLOW__ABORT_VIEW:
			case PageflowPackage.PAGEFLOW__ERROR_VIEW:
			case PageflowPackage.PAGEFLOW__TRANSITIONS:
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
	protected void collectNewChildDescriptors(Collection newChildDescriptors, Object object) {
		super.collectNewChildDescriptors(newChildDescriptors, object);

		newChildDescriptors.add
			(createChildParameter
				(PageflowPackage.Literals.PAGEFLOW__STATES,
				 PageflowFactory.eINSTANCE.createEndState()));

		newChildDescriptors.add
			(createChildParameter
				(PageflowPackage.Literals.PAGEFLOW__STATES,
				 PageflowFactory.eINSTANCE.createInitState()));

		newChildDescriptors.add
			(createChildParameter
				(PageflowPackage.Literals.PAGEFLOW__STATES,
				 PageflowFactory.eINSTANCE.createViewState()));

		newChildDescriptors.add
			(createChildParameter
				(PageflowPackage.Literals.PAGEFLOW__STATES,
				 PageflowFactory.eINSTANCE.createDecisionState()));

		newChildDescriptors.add
			(createChildParameter
				(PageflowPackage.Literals.PAGEFLOW__STATES,
				 PageflowFactory.eINSTANCE.createSubPageflowState()));

		newChildDescriptors.add
			(createChildParameter
				(PageflowPackage.Literals.PAGEFLOW__ABORT_VIEW,
				 PageflowFactory.eINSTANCE.createView()));

		newChildDescriptors.add
			(createChildParameter
				(PageflowPackage.Literals.PAGEFLOW__ERROR_VIEW,
				 PageflowFactory.eINSTANCE.createView()));

		newChildDescriptors.add
			(createChildParameter
				(PageflowPackage.Literals.PAGEFLOW__TRANSITIONS,
				 PageflowFactory.eINSTANCE.createTransition()));
	}

	/**
	 * This returns the label text for {@link org.eclipse.emf.edit.command.CreateChildCommand}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getCreateChildText(Object owner, Object feature, Object child, Collection selection) {
		Object childFeature = feature;
		Object childObject = child;

		boolean qualify =
			childFeature == PageflowPackage.Literals.PAGEFLOW__ABORT_VIEW ||
			childFeature == PageflowPackage.Literals.PAGEFLOW__ERROR_VIEW;

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
	public ResourceLocator getResourceLocator() {
		return PageflowEditPlugin.INSTANCE;
	}

}
