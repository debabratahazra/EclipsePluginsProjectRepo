/**
 * Odyssey Financial Technologies
 *
 * $Id$
 */
package com.odcgroup.process.model.provider;


import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ViewerNotification;

import com.odcgroup.process.model.Pool;
import com.odcgroup.process.model.ProcessFactory;
import com.odcgroup.process.model.ProcessPackage;

/**
 * This is the item provider adapter for a {@link com.odcgroup.process.model.Pool} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class PoolItemProvider
	extends NamedElementItemProvider
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
	public static final String copyright = "Odyssey Financial Technologies";

	/**
	 * This constructs an instance from a factory and a notifier.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PoolItemProvider(AdapterFactory adapterFactory) {
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

			addTechDescPropertyDescriptor(object);
		}
		return itemPropertyDescriptors;
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
				 getString("_UI_Pool_techDesc_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_Pool_techDesc_feature", "_UI_Pool_type"),
				 ProcessPackage.Literals.POOL__TECH_DESC,
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
			childrenFeatures.add(ProcessPackage.Literals.POOL__ASSIGNEE);
			childrenFeatures.add(ProcessPackage.Literals.POOL__ASSIGNEE_BY_SERVICE);
			childrenFeatures.add(ProcessPackage.Literals.POOL__START);
			childrenFeatures.add(ProcessPackage.Literals.POOL__END);
			childrenFeatures.add(ProcessPackage.Literals.POOL__TASKS);
			childrenFeatures.add(ProcessPackage.Literals.POOL__GATEWAYS);
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
	 * This returns Pool.gif.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object getImage(Object object) {
		return overlayImage(object, getResourceLocator().getImage("full/obj16/Pool"));
	}

	/**
	 * This returns the label text for the adapted class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getText(Object object) {
		String label = ((Pool)object).getName();
		return label == null || label.length() == 0 ?
			getString("_UI_Pool_type") :
			getString("_UI_Pool_type") + " " + label;
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

		switch (notification.getFeatureID(Pool.class)) {
			case ProcessPackage.POOL__TECH_DESC:
				fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), false, true));
				return;
			case ProcessPackage.POOL__ASSIGNEE:
			case ProcessPackage.POOL__ASSIGNEE_BY_SERVICE:
			case ProcessPackage.POOL__START:
			case ProcessPackage.POOL__END:
			case ProcessPackage.POOL__TASKS:
			case ProcessPackage.POOL__GATEWAYS:
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
				(ProcessPackage.Literals.POOL__ASSIGNEE,
				 ProcessFactory.eINSTANCE.createAssignee()));

		newChildDescriptors.add
			(createChildParameter
				(ProcessPackage.Literals.POOL__ASSIGNEE_BY_SERVICE,
				 ProcessFactory.eINSTANCE.createService()));

		newChildDescriptors.add
			(createChildParameter
				(ProcessPackage.Literals.POOL__START,
				 ProcessFactory.eINSTANCE.createStartEvent()));

		newChildDescriptors.add
			(createChildParameter
				(ProcessPackage.Literals.POOL__END,
				 ProcessFactory.eINSTANCE.createEndEvent()));

		newChildDescriptors.add
			(createChildParameter
				(ProcessPackage.Literals.POOL__TASKS,
				 ProcessFactory.eINSTANCE.createUserTask()));

		newChildDescriptors.add
			(createChildParameter
				(ProcessPackage.Literals.POOL__TASKS,
				 ProcessFactory.eINSTANCE.createServiceTask()));

		newChildDescriptors.add
			(createChildParameter
				(ProcessPackage.Literals.POOL__GATEWAYS,
				 ProcessFactory.eINSTANCE.createComplexGateway()));

		newChildDescriptors.add
			(createChildParameter
				(ProcessPackage.Literals.POOL__GATEWAYS,
				 ProcessFactory.eINSTANCE.createExclusiveMerge()));

		newChildDescriptors.add
			(createChildParameter
				(ProcessPackage.Literals.POOL__GATEWAYS,
				 ProcessFactory.eINSTANCE.createParallelFork()));

		newChildDescriptors.add
			(createChildParameter
				(ProcessPackage.Literals.POOL__GATEWAYS,
				 ProcessFactory.eINSTANCE.createParallelMerge()));
	}

}
