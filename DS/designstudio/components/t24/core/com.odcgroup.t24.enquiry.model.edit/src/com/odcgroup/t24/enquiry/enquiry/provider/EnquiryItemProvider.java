/**
 */
package com.odcgroup.t24.enquiry.enquiry.provider;


import com.odcgroup.t24.enquiry.enquiry.Enquiry;
import com.odcgroup.t24.enquiry.enquiry.EnquiryFactory;
import com.odcgroup.t24.enquiry.enquiry.EnquiryPackage;

import com.odcgroup.translation.translationDsl.TranslationDslFactory;

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

/**
 * This is the item provider adapter for a {@link com.odcgroup.t24.enquiry.enquiry.Enquiry} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class EnquiryItemProvider
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
	public EnquiryItemProvider(AdapterFactory adapterFactory) {
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
			addFileNamePropertyDescriptor(object);
			addMetamodelVersionPropertyDescriptor(object);
			addServerModePropertyDescriptor(object);
			addEnquiryModePropertyDescriptor(object);
			addAccountFieldPropertyDescriptor(object);
			addCustomerFieldPropertyDescriptor(object);
			addZeroRecordsDisplayPropertyDescriptor(object);
			addNoSelectionPropertyDescriptor(object);
			addShowAllBooksPropertyDescriptor(object);
			addStartLinePropertyDescriptor(object);
			addEndLinePropertyDescriptor(object);
			addToolbarPropertyDescriptor(object);
			addGenerateIFPPropertyDescriptor(object);
			addAttributesPropertyDescriptor(object);
			addIntrospectionMessagesPropertyDescriptor(object);
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
				 getString("_UI_Enquiry_name_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_Enquiry_name_feature", "_UI_Enquiry_type"),
				 EnquiryPackage.Literals.ENQUIRY__NAME,
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
				 getString("_UI_Enquiry_fileName_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_Enquiry_fileName_feature", "_UI_Enquiry_type"),
				 EnquiryPackage.Literals.ENQUIRY__FILE_NAME,
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
				 getString("_UI_Enquiry_metamodelVersion_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_Enquiry_metamodelVersion_feature", "_UI_Enquiry_type"),
				 EnquiryPackage.Literals.ENQUIRY__METAMODEL_VERSION,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Server Mode feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addServerModePropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_Enquiry_serverMode_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_Enquiry_serverMode_feature", "_UI_Enquiry_type"),
				 EnquiryPackage.Literals.ENQUIRY__SERVER_MODE,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Enquiry Mode feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addEnquiryModePropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_Enquiry_enquiryMode_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_Enquiry_enquiryMode_feature", "_UI_Enquiry_type"),
				 EnquiryPackage.Literals.ENQUIRY__ENQUIRY_MODE,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Account Field feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addAccountFieldPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_Enquiry_accountField_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_Enquiry_accountField_feature", "_UI_Enquiry_type"),
				 EnquiryPackage.Literals.ENQUIRY__ACCOUNT_FIELD,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Customer Field feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addCustomerFieldPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_Enquiry_customerField_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_Enquiry_customerField_feature", "_UI_Enquiry_type"),
				 EnquiryPackage.Literals.ENQUIRY__CUSTOMER_FIELD,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Zero Records Display feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addZeroRecordsDisplayPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_Enquiry_zeroRecordsDisplay_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_Enquiry_zeroRecordsDisplay_feature", "_UI_Enquiry_type"),
				 EnquiryPackage.Literals.ENQUIRY__ZERO_RECORDS_DISPLAY,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the No Selection feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addNoSelectionPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_Enquiry_noSelection_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_Enquiry_noSelection_feature", "_UI_Enquiry_type"),
				 EnquiryPackage.Literals.ENQUIRY__NO_SELECTION,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Show All Books feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addShowAllBooksPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_Enquiry_showAllBooks_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_Enquiry_showAllBooks_feature", "_UI_Enquiry_type"),
				 EnquiryPackage.Literals.ENQUIRY__SHOW_ALL_BOOKS,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Start Line feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addStartLinePropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_Enquiry_startLine_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_Enquiry_startLine_feature", "_UI_Enquiry_type"),
				 EnquiryPackage.Literals.ENQUIRY__START_LINE,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the End Line feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addEndLinePropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_Enquiry_endLine_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_Enquiry_endLine_feature", "_UI_Enquiry_type"),
				 EnquiryPackage.Literals.ENQUIRY__END_LINE,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Toolbar feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addToolbarPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_Enquiry_toolbar_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_Enquiry_toolbar_feature", "_UI_Enquiry_type"),
				 EnquiryPackage.Literals.ENQUIRY__TOOLBAR,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Generate IFP feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addGenerateIFPPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_Enquiry_generateIFP_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_Enquiry_generateIFP_feature", "_UI_Enquiry_type"),
				 EnquiryPackage.Literals.ENQUIRY__GENERATE_IFP,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Attributes feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addAttributesPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_Enquiry_attributes_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_Enquiry_attributes_feature", "_UI_Enquiry_type"),
				 EnquiryPackage.Literals.ENQUIRY__ATTRIBUTES,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Introspection Messages feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addIntrospectionMessagesPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_Enquiry_introspectionMessages_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_Enquiry_introspectionMessages_feature", "_UI_Enquiry_type"),
				 EnquiryPackage.Literals.ENQUIRY__INTROSPECTION_MESSAGES,
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
			childrenFeatures.add(EnquiryPackage.Literals.ENQUIRY__HEADER);
			childrenFeatures.add(EnquiryPackage.Literals.ENQUIRY__DESCRIPTION);
			childrenFeatures.add(EnquiryPackage.Literals.ENQUIRY__COMPANIES);
			childrenFeatures.add(EnquiryPackage.Literals.ENQUIRY__BUILD_ROUTINES);
			childrenFeatures.add(EnquiryPackage.Literals.ENQUIRY__FIXED_SELECTIONS);
			childrenFeatures.add(EnquiryPackage.Literals.ENQUIRY__FIXED_SORTS);
			childrenFeatures.add(EnquiryPackage.Literals.ENQUIRY__CUSTOM_SELECTION);
			childrenFeatures.add(EnquiryPackage.Literals.ENQUIRY__FIELDS);
			childrenFeatures.add(EnquiryPackage.Literals.ENQUIRY__TOOLS);
			childrenFeatures.add(EnquiryPackage.Literals.ENQUIRY__TARGET);
			childrenFeatures.add(EnquiryPackage.Literals.ENQUIRY__DRILL_DOWNS);
			childrenFeatures.add(EnquiryPackage.Literals.ENQUIRY__SECURITY);
			childrenFeatures.add(EnquiryPackage.Literals.ENQUIRY__GRAPH);
			childrenFeatures.add(EnquiryPackage.Literals.ENQUIRY__WEB_SERVICE);
			childrenFeatures.add(EnquiryPackage.Literals.ENQUIRY__FILE_VERSION);
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
	 * This returns Enquiry.gif.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object getImage(Object object) {
		return overlayImage(object, getResourceLocator().getImage("full/obj16/Enquiry"));
	}

	/**
	 * This returns the label text for the adapted class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getText(Object object) {
		String label = ((Enquiry)object).getName();
		return label == null || label.length() == 0 ?
			getString("_UI_Enquiry_type") :
			getString("_UI_Enquiry_type") + " " + label;
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

		switch (notification.getFeatureID(Enquiry.class)) {
			case EnquiryPackage.ENQUIRY__NAME:
			case EnquiryPackage.ENQUIRY__FILE_NAME:
			case EnquiryPackage.ENQUIRY__METAMODEL_VERSION:
			case EnquiryPackage.ENQUIRY__SERVER_MODE:
			case EnquiryPackage.ENQUIRY__ENQUIRY_MODE:
			case EnquiryPackage.ENQUIRY__ACCOUNT_FIELD:
			case EnquiryPackage.ENQUIRY__CUSTOMER_FIELD:
			case EnquiryPackage.ENQUIRY__ZERO_RECORDS_DISPLAY:
			case EnquiryPackage.ENQUIRY__NO_SELECTION:
			case EnquiryPackage.ENQUIRY__SHOW_ALL_BOOKS:
			case EnquiryPackage.ENQUIRY__START_LINE:
			case EnquiryPackage.ENQUIRY__END_LINE:
			case EnquiryPackage.ENQUIRY__TOOLBAR:
			case EnquiryPackage.ENQUIRY__GENERATE_IFP:
			case EnquiryPackage.ENQUIRY__ATTRIBUTES:
			case EnquiryPackage.ENQUIRY__INTROSPECTION_MESSAGES:
				fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), false, true));
				return;
			case EnquiryPackage.ENQUIRY__HEADER:
			case EnquiryPackage.ENQUIRY__DESCRIPTION:
			case EnquiryPackage.ENQUIRY__COMPANIES:
			case EnquiryPackage.ENQUIRY__BUILD_ROUTINES:
			case EnquiryPackage.ENQUIRY__FIXED_SELECTIONS:
			case EnquiryPackage.ENQUIRY__FIXED_SORTS:
			case EnquiryPackage.ENQUIRY__CUSTOM_SELECTION:
			case EnquiryPackage.ENQUIRY__FIELDS:
			case EnquiryPackage.ENQUIRY__TOOLS:
			case EnquiryPackage.ENQUIRY__TARGET:
			case EnquiryPackage.ENQUIRY__DRILL_DOWNS:
			case EnquiryPackage.ENQUIRY__SECURITY:
			case EnquiryPackage.ENQUIRY__GRAPH:
			case EnquiryPackage.ENQUIRY__WEB_SERVICE:
			case EnquiryPackage.ENQUIRY__FILE_VERSION:
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
				(EnquiryPackage.Literals.ENQUIRY__HEADER,
				 EnquiryFactory.eINSTANCE.createEnquiryHeader()));

		newChildDescriptors.add
			(createChildParameter
				(EnquiryPackage.Literals.ENQUIRY__DESCRIPTION,
				 TranslationDslFactory.eINSTANCE.createTranslations()));

		newChildDescriptors.add
			(createChildParameter
				(EnquiryPackage.Literals.ENQUIRY__DESCRIPTION,
				 TranslationDslFactory.eINSTANCE.createLocalTranslations()));

		newChildDescriptors.add
			(createChildParameter
				(EnquiryPackage.Literals.ENQUIRY__COMPANIES,
				 EnquiryFactory.eINSTANCE.createCompanies()));

		newChildDescriptors.add
			(createChildParameter
				(EnquiryPackage.Literals.ENQUIRY__BUILD_ROUTINES,
				 EnquiryFactory.eINSTANCE.createRoutine()));

		newChildDescriptors.add
			(createChildParameter
				(EnquiryPackage.Literals.ENQUIRY__BUILD_ROUTINES,
				 EnquiryFactory.eINSTANCE.createJBCRoutine()));

		newChildDescriptors.add
			(createChildParameter
				(EnquiryPackage.Literals.ENQUIRY__BUILD_ROUTINES,
				 EnquiryFactory.eINSTANCE.createJavaRoutine()));

		newChildDescriptors.add
			(createChildParameter
				(EnquiryPackage.Literals.ENQUIRY__FIXED_SELECTIONS,
				 EnquiryFactory.eINSTANCE.createFixedSelection()));

		newChildDescriptors.add
			(createChildParameter
				(EnquiryPackage.Literals.ENQUIRY__FIXED_SORTS,
				 EnquiryFactory.eINSTANCE.createFixedSort()));

		newChildDescriptors.add
			(createChildParameter
				(EnquiryPackage.Literals.ENQUIRY__CUSTOM_SELECTION,
				 EnquiryFactory.eINSTANCE.createSelectionExpression()));

		newChildDescriptors.add
			(createChildParameter
				(EnquiryPackage.Literals.ENQUIRY__FIELDS,
				 EnquiryFactory.eINSTANCE.createField()));

		newChildDescriptors.add
			(createChildParameter
				(EnquiryPackage.Literals.ENQUIRY__TOOLS,
				 EnquiryFactory.eINSTANCE.createTool()));

		newChildDescriptors.add
			(createChildParameter
				(EnquiryPackage.Literals.ENQUIRY__TARGET,
				 EnquiryFactory.eINSTANCE.createTarget()));

		newChildDescriptors.add
			(createChildParameter
				(EnquiryPackage.Literals.ENQUIRY__DRILL_DOWNS,
				 EnquiryFactory.eINSTANCE.createDrillDown()));

		newChildDescriptors.add
			(createChildParameter
				(EnquiryPackage.Literals.ENQUIRY__SECURITY,
				 EnquiryFactory.eINSTANCE.createSecurity()));

		newChildDescriptors.add
			(createChildParameter
				(EnquiryPackage.Literals.ENQUIRY__GRAPH,
				 EnquiryFactory.eINSTANCE.createGraph()));

		newChildDescriptors.add
			(createChildParameter
				(EnquiryPackage.Literals.ENQUIRY__WEB_SERVICE,
				 EnquiryFactory.eINSTANCE.createWebService()));

		newChildDescriptors.add
			(createChildParameter
				(EnquiryPackage.Literals.ENQUIRY__FILE_VERSION,
				 EnquiryFactory.eINSTANCE.createFileVersion()));
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
