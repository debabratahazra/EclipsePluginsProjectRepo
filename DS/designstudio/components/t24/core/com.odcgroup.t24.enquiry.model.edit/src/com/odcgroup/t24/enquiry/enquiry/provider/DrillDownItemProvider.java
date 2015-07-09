/**
 */
package com.odcgroup.t24.enquiry.enquiry.provider;


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
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ItemProviderAdapter;
import org.eclipse.emf.edit.provider.ViewerNotification;

import com.odcgroup.t24.enquiry.enquiry.DrillDown;
import com.odcgroup.t24.enquiry.enquiry.EnquiryFactory;
import com.odcgroup.t24.enquiry.enquiry.EnquiryPackage;
import com.odcgroup.translation.translationDsl.TranslationDslFactory;

/**
 * This is the item provider adapter for a {@link com.odcgroup.t24.enquiry.enquiry.DrillDown} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class DrillDownItemProvider
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
	public DrillDownItemProvider(AdapterFactory adapterFactory) {
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

			addDrill_namePropertyDescriptor(object);
			addLabelFieldPropertyDescriptor(object);
			addImagePropertyDescriptor(object);
			addInfoPropertyDescriptor(object);
		}
		return itemPropertyDescriptors;
	}

	/**
	 * This adds a property descriptor for the Drill name feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addDrill_namePropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_DrillDown_drill_name_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_DrillDown_drill_name_feature", "_UI_DrillDown_type"),
				 EnquiryPackage.Literals.DRILL_DOWN__DRILL_NAME,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Label Field feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addLabelFieldPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_DrillDown_labelField_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_DrillDown_labelField_feature", "_UI_DrillDown_type"),
				 EnquiryPackage.Literals.DRILL_DOWN__LABEL_FIELD,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Image feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addImagePropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_DrillDown_image_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_DrillDown_image_feature", "_UI_DrillDown_type"),
				 EnquiryPackage.Literals.DRILL_DOWN__IMAGE,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Info feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addInfoPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_DrillDown_info_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_DrillDown_info_feature", "_UI_DrillDown_type"),
				 EnquiryPackage.Literals.DRILL_DOWN__INFO,
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
	@Override
	public Collection<? extends EStructuralFeature> getChildrenFeatures(Object object) {
		if (childrenFeatures == null) {
			super.getChildrenFeatures(object);
			childrenFeatures.add(EnquiryPackage.Literals.DRILL_DOWN__DESCRIPTION);
			childrenFeatures.add(EnquiryPackage.Literals.DRILL_DOWN__CRITERIA);
			childrenFeatures.add(EnquiryPackage.Literals.DRILL_DOWN__PARAMETERS);
			childrenFeatures.add(EnquiryPackage.Literals.DRILL_DOWN__TYPE);
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
	 * This returns DrillDown.gif.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object getImage(Object object) {
		return overlayImage(object, getResourceLocator().getImage("full/obj16/DrillDown"));
	}

	/**
	 * This returns the label text for the adapted class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getText(Object object) {
		String label = ((DrillDown)object).getDrill_name();
		return label == null || label.length() == 0 ?
			getString("_UI_DrillDown_type") :
			getString("_UI_DrillDown_type") + " " + label;
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

		switch (notification.getFeatureID(DrillDown.class)) {
			case EnquiryPackage.DRILL_DOWN__DRILL_NAME:
			case EnquiryPackage.DRILL_DOWN__LABEL_FIELD:
			case EnquiryPackage.DRILL_DOWN__IMAGE:
			case EnquiryPackage.DRILL_DOWN__INFO:
				fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), false, true));
				return;
			case EnquiryPackage.DRILL_DOWN__DESCRIPTION:
			case EnquiryPackage.DRILL_DOWN__CRITERIA:
			case EnquiryPackage.DRILL_DOWN__PARAMETERS:
			case EnquiryPackage.DRILL_DOWN__TYPE:
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
				(EnquiryPackage.Literals.DRILL_DOWN__DESCRIPTION,
				 TranslationDslFactory.eINSTANCE.createTranslations()));

		newChildDescriptors.add
			(createChildParameter
				(EnquiryPackage.Literals.DRILL_DOWN__DESCRIPTION,
				 TranslationDslFactory.eINSTANCE.createLocalTranslations()));

		newChildDescriptors.add
			(createChildParameter
				(EnquiryPackage.Literals.DRILL_DOWN__CRITERIA,
				 EnquiryFactory.eINSTANCE.createSelectionCriteria()));

		newChildDescriptors.add
			(createChildParameter
				(EnquiryPackage.Literals.DRILL_DOWN__PARAMETERS,
				 EnquiryFactory.eINSTANCE.createParameters()));

		newChildDescriptors.add
			(createChildParameter
				(EnquiryPackage.Literals.DRILL_DOWN__TYPE,
				 EnquiryFactory.eINSTANCE.createDrillDownType()));

		newChildDescriptors.add
			(createChildParameter
				(EnquiryPackage.Literals.DRILL_DOWN__TYPE,
				 EnquiryFactory.eINSTANCE.createDrillDownStringType()));

		newChildDescriptors.add
			(createChildParameter
				(EnquiryPackage.Literals.DRILL_DOWN__TYPE,
				 EnquiryFactory.eINSTANCE.createApplicationType()));

		newChildDescriptors.add
			(createChildParameter
				(EnquiryPackage.Literals.DRILL_DOWN__TYPE,
				 EnquiryFactory.eINSTANCE.createScreenType()));

		newChildDescriptors.add
			(createChildParameter
				(EnquiryPackage.Literals.DRILL_DOWN__TYPE,
				 EnquiryFactory.eINSTANCE.createEnquiryType()));

		newChildDescriptors.add
			(createChildParameter
				(EnquiryPackage.Literals.DRILL_DOWN__TYPE,
				 EnquiryFactory.eINSTANCE.createFromFieldType()));

		newChildDescriptors.add
			(createChildParameter
				(EnquiryPackage.Literals.DRILL_DOWN__TYPE,
				 EnquiryFactory.eINSTANCE.createCompositeScreenType()));

		newChildDescriptors.add
			(createChildParameter
				(EnquiryPackage.Literals.DRILL_DOWN__TYPE,
				 EnquiryFactory.eINSTANCE.createTabbedScreenType()));

		newChildDescriptors.add
			(createChildParameter
				(EnquiryPackage.Literals.DRILL_DOWN__TYPE,
				 EnquiryFactory.eINSTANCE.createViewType()));

		newChildDescriptors.add
			(createChildParameter
				(EnquiryPackage.Literals.DRILL_DOWN__TYPE,
				 EnquiryFactory.eINSTANCE.createQuitSEEType()));

		newChildDescriptors.add
			(createChildParameter
				(EnquiryPackage.Literals.DRILL_DOWN__TYPE,
				 EnquiryFactory.eINSTANCE.createBlankType()));

		newChildDescriptors.add
			(createChildParameter
				(EnquiryPackage.Literals.DRILL_DOWN__TYPE,
				 EnquiryFactory.eINSTANCE.createPWProcessType()));

		newChildDescriptors.add
			(createChildParameter
				(EnquiryPackage.Literals.DRILL_DOWN__TYPE,
				 EnquiryFactory.eINSTANCE.createDownloadType()));

		newChildDescriptors.add
			(createChildParameter
				(EnquiryPackage.Literals.DRILL_DOWN__TYPE,
				 EnquiryFactory.eINSTANCE.createRunType()));

		newChildDescriptors.add
			(createChildParameter
				(EnquiryPackage.Literals.DRILL_DOWN__TYPE,
				 EnquiryFactory.eINSTANCE.createUtilType()));

		newChildDescriptors.add
			(createChildParameter
				(EnquiryPackage.Literals.DRILL_DOWN__TYPE,
				 EnquiryFactory.eINSTANCE.createJavaScriptType()));

		newChildDescriptors.add
			(createChildParameter
				(EnquiryPackage.Literals.DRILL_DOWN__TYPE,
				 EnquiryFactory.eINSTANCE.createShouldBeChangedType()));
	}

	/**
	 * Return the resource locator for this item provider's resources.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ResourceLocator getResourceLocator() {
		return EnquiryEditPlugin.INSTANCE;
	}

}
