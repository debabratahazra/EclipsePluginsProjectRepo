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

import com.odcgroup.t24.enquiry.enquiry.EnquiryFactory;
import com.odcgroup.t24.enquiry.enquiry.EnquiryPackage;
import com.odcgroup.t24.enquiry.enquiry.Field;
import com.odcgroup.translation.translationDsl.TranslationDslFactory;

/**
 * This is the item provider adapter for a {@link com.odcgroup.t24.enquiry.enquiry.Field} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class FieldItemProvider
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
	public FieldItemProvider(AdapterFactory adapterFactory) {
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
			addCommentsPropertyDescriptor(object);
			addDisplayTypePropertyDescriptor(object);
			addLengthPropertyDescriptor(object);
			addAlignmentPropertyDescriptor(object);
			addCommaSeparatorPropertyDescriptor(object);
			addNumberOfDecimalsPropertyDescriptor(object);
			addEscapeSequencePropertyDescriptor(object);
			addFmtMaskPropertyDescriptor(object);
			addDisplaySectionPropertyDescriptor(object);
			addColumnWidthPropertyDescriptor(object);
			addSpoolBreakPropertyDescriptor(object);
			addSingleMultiPropertyDescriptor(object);
			addHiddenPropertyDescriptor(object);
			addNoHeaderPropertyDescriptor(object);
			addNoColumnLabelPropertyDescriptor(object);
			addAttributesPropertyDescriptor(object);
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
				 getString("_UI_Field_name_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_Field_name_feature", "_UI_Field_type"),
				 EnquiryPackage.Literals.FIELD__NAME,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Comments feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addCommentsPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_Field_comments_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_Field_comments_feature", "_UI_Field_type"),
				 EnquiryPackage.Literals.FIELD__COMMENTS,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Display Type feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addDisplayTypePropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_Field_displayType_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_Field_displayType_feature", "_UI_Field_type"),
				 EnquiryPackage.Literals.FIELD__DISPLAY_TYPE,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Length feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addLengthPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_Field_length_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_Field_length_feature", "_UI_Field_type"),
				 EnquiryPackage.Literals.FIELD__LENGTH,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}


	/**
	 * This adds a property descriptor for the Alignment feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addAlignmentPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_Field_alignment_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_Field_alignment_feature", "_UI_Field_type"),
				 EnquiryPackage.Literals.FIELD__ALIGNMENT,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Comma Separator feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addCommaSeparatorPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_Field_commaSeparator_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_Field_commaSeparator_feature", "_UI_Field_type"),
				 EnquiryPackage.Literals.FIELD__COMMA_SEPARATOR,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Number Of Decimals feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addNumberOfDecimalsPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_Field_numberOfDecimals_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_Field_numberOfDecimals_feature", "_UI_Field_type"),
				 EnquiryPackage.Literals.FIELD__NUMBER_OF_DECIMALS,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Escape Sequence feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addEscapeSequencePropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_Field_escapeSequence_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_Field_escapeSequence_feature", "_UI_Field_type"),
				 EnquiryPackage.Literals.FIELD__ESCAPE_SEQUENCE,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Fmt Mask feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addFmtMaskPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_Field_fmtMask_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_Field_fmtMask_feature", "_UI_Field_type"),
				 EnquiryPackage.Literals.FIELD__FMT_MASK,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Display Section feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addDisplaySectionPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_Field_displaySection_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_Field_displaySection_feature", "_UI_Field_type"),
				 EnquiryPackage.Literals.FIELD__DISPLAY_SECTION,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Column Width feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addColumnWidthPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_Field_columnWidth_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_Field_columnWidth_feature", "_UI_Field_type"),
				 EnquiryPackage.Literals.FIELD__COLUMN_WIDTH,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Spool Break feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addSpoolBreakPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_Field_spoolBreak_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_Field_spoolBreak_feature", "_UI_Field_type"),
				 EnquiryPackage.Literals.FIELD__SPOOL_BREAK,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Single Multi feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addSingleMultiPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_Field_singleMulti_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_Field_singleMulti_feature", "_UI_Field_type"),
				 EnquiryPackage.Literals.FIELD__SINGLE_MULTI,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Hidden feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addHiddenPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_Field_hidden_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_Field_hidden_feature", "_UI_Field_type"),
				 EnquiryPackage.Literals.FIELD__HIDDEN,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the No Header feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addNoHeaderPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_Field_noHeader_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_Field_noHeader_feature", "_UI_Field_type"),
				 EnquiryPackage.Literals.FIELD__NO_HEADER,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the No Column Label feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addNoColumnLabelPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_Field_noColumnLabel_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_Field_noColumnLabel_feature", "_UI_Field_type"),
				 EnquiryPackage.Literals.FIELD__NO_COLUMN_LABEL,
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
				 getString("_UI_Field_attributes_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_Field_attributes_feature", "_UI_Field_type"),
				 EnquiryPackage.Literals.FIELD__ATTRIBUTES,
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
			childrenFeatures.add(EnquiryPackage.Literals.FIELD__LABEL);
			childrenFeatures.add(EnquiryPackage.Literals.FIELD__FORMAT);
			childrenFeatures.add(EnquiryPackage.Literals.FIELD__BREAK_CONDITION);
			childrenFeatures.add(EnquiryPackage.Literals.FIELD__POSITION);
			childrenFeatures.add(EnquiryPackage.Literals.FIELD__OPERATION);
			childrenFeatures.add(EnquiryPackage.Literals.FIELD__CONVERSION);
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
	 * This returns Field.gif.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object getImage(Object object) {
		return overlayImage(object, getResourceLocator().getImage("full/obj16/Field"));
	}

	/**
	 * This returns the label text for the adapted class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getText(Object object) {
		String label = ((Field)object).getName();
		return label == null || label.length() == 0 ?
			getString("_UI_Field_type") :
			getString("_UI_Field_type") + " " + label;
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

		switch (notification.getFeatureID(Field.class)) {
			case EnquiryPackage.FIELD__NAME:
			case EnquiryPackage.FIELD__COMMENTS:
			case EnquiryPackage.FIELD__DISPLAY_TYPE:
			case EnquiryPackage.FIELD__LENGTH:
			case EnquiryPackage.FIELD__ALIGNMENT:
			case EnquiryPackage.FIELD__COMMA_SEPARATOR:
			case EnquiryPackage.FIELD__NUMBER_OF_DECIMALS:
			case EnquiryPackage.FIELD__ESCAPE_SEQUENCE:
			case EnquiryPackage.FIELD__FMT_MASK:
			case EnquiryPackage.FIELD__DISPLAY_SECTION:
			case EnquiryPackage.FIELD__COLUMN_WIDTH:
			case EnquiryPackage.FIELD__SPOOL_BREAK:
			case EnquiryPackage.FIELD__SINGLE_MULTI:
			case EnquiryPackage.FIELD__HIDDEN:
			case EnquiryPackage.FIELD__NO_HEADER:
			case EnquiryPackage.FIELD__NO_COLUMN_LABEL:
			case EnquiryPackage.FIELD__ATTRIBUTES:
				fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), false, true));
				return;
			case EnquiryPackage.FIELD__LABEL:
			case EnquiryPackage.FIELD__FORMAT:
			case EnquiryPackage.FIELD__BREAK_CONDITION:
			case EnquiryPackage.FIELD__POSITION:
			case EnquiryPackage.FIELD__OPERATION:
			case EnquiryPackage.FIELD__CONVERSION:
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
				(EnquiryPackage.Literals.FIELD__LABEL,
				 TranslationDslFactory.eINSTANCE.createTranslations()));

		newChildDescriptors.add
			(createChildParameter
				(EnquiryPackage.Literals.FIELD__LABEL,
				 TranslationDslFactory.eINSTANCE.createLocalTranslations()));

		newChildDescriptors.add
			(createChildParameter
				(EnquiryPackage.Literals.FIELD__FORMAT,
				 EnquiryFactory.eINSTANCE.createFormat()));

		newChildDescriptors.add
			(createChildParameter
				(EnquiryPackage.Literals.FIELD__BREAK_CONDITION,
				 EnquiryFactory.eINSTANCE.createBreakCondition()));

		newChildDescriptors.add
			(createChildParameter
				(EnquiryPackage.Literals.FIELD__POSITION,
				 EnquiryFactory.eINSTANCE.createFieldPosition()));

		newChildDescriptors.add
			(createChildParameter
				(EnquiryPackage.Literals.FIELD__OPERATION,
				 EnquiryFactory.eINSTANCE.createOperation()));

		newChildDescriptors.add
			(createChildParameter
				(EnquiryPackage.Literals.FIELD__OPERATION,
				 EnquiryFactory.eINSTANCE.createBreakOperation()));

		newChildDescriptors.add
			(createChildParameter
				(EnquiryPackage.Literals.FIELD__OPERATION,
				 EnquiryFactory.eINSTANCE.createBreakOnChangeOperation()));

		newChildDescriptors.add
			(createChildParameter
				(EnquiryPackage.Literals.FIELD__OPERATION,
				 EnquiryFactory.eINSTANCE.createBreakLineOperation()));

		newChildDescriptors.add
			(createChildParameter
				(EnquiryPackage.Literals.FIELD__OPERATION,
				 EnquiryFactory.eINSTANCE.createCalcOperation()));

		newChildDescriptors.add
			(createChildParameter
				(EnquiryPackage.Literals.FIELD__OPERATION,
				 EnquiryFactory.eINSTANCE.createConstantOperation()));

		newChildDescriptors.add
			(createChildParameter
				(EnquiryPackage.Literals.FIELD__OPERATION,
				 EnquiryFactory.eINSTANCE.createLabelOperation()));

		newChildDescriptors.add
			(createChildParameter
				(EnquiryPackage.Literals.FIELD__OPERATION,
				 EnquiryFactory.eINSTANCE.createDateOperation()));

		newChildDescriptors.add
			(createChildParameter
				(EnquiryPackage.Literals.FIELD__OPERATION,
				 EnquiryFactory.eINSTANCE.createDecisionOperation()));

		newChildDescriptors.add
			(createChildParameter
				(EnquiryPackage.Literals.FIELD__OPERATION,
				 EnquiryFactory.eINSTANCE.createDescriptorOperation()));

		newChildDescriptors.add
			(createChildParameter
				(EnquiryPackage.Literals.FIELD__OPERATION,
				 EnquiryFactory.eINSTANCE.createTodayOperation()));

		newChildDescriptors.add
			(createChildParameter
				(EnquiryPackage.Literals.FIELD__OPERATION,
				 EnquiryFactory.eINSTANCE.createLWDOperation()));

		newChildDescriptors.add
			(createChildParameter
				(EnquiryPackage.Literals.FIELD__OPERATION,
				 EnquiryFactory.eINSTANCE.createNWDOperation()));

		newChildDescriptors.add
			(createChildParameter
				(EnquiryPackage.Literals.FIELD__OPERATION,
				 EnquiryFactory.eINSTANCE.createFieldOperation()));

		newChildDescriptors.add
			(createChildParameter
				(EnquiryPackage.Literals.FIELD__OPERATION,
				 EnquiryFactory.eINSTANCE.createApplicationFieldNameOperation()));

		newChildDescriptors.add
			(createChildParameter
				(EnquiryPackage.Literals.FIELD__OPERATION,
				 EnquiryFactory.eINSTANCE.createFieldNumberOperation()));

		newChildDescriptors.add
			(createChildParameter
				(EnquiryPackage.Literals.FIELD__OPERATION,
				 EnquiryFactory.eINSTANCE.createFieldExtractOperation()));

		newChildDescriptors.add
			(createChildParameter
				(EnquiryPackage.Literals.FIELD__OPERATION,
				 EnquiryFactory.eINSTANCE.createSelectionOperation()));

		newChildDescriptors.add
			(createChildParameter
				(EnquiryPackage.Literals.FIELD__OPERATION,
				 EnquiryFactory.eINSTANCE.createSystemOperation()));

		newChildDescriptors.add
			(createChildParameter
				(EnquiryPackage.Literals.FIELD__OPERATION,
				 EnquiryFactory.eINSTANCE.createUserOperation()));

		newChildDescriptors.add
			(createChildParameter
				(EnquiryPackage.Literals.FIELD__OPERATION,
				 EnquiryFactory.eINSTANCE.createCompanyOperation()));

		newChildDescriptors.add
			(createChildParameter
				(EnquiryPackage.Literals.FIELD__OPERATION,
				 EnquiryFactory.eINSTANCE.createLanguageOperation()));

		newChildDescriptors.add
			(createChildParameter
				(EnquiryPackage.Literals.FIELD__OPERATION,
				 EnquiryFactory.eINSTANCE.createLocalCurrencyOperation()));

		newChildDescriptors.add
			(createChildParameter
				(EnquiryPackage.Literals.FIELD__OPERATION,
				 EnquiryFactory.eINSTANCE.createTotalOperation()));

		newChildDescriptors.add
			(createChildParameter
				(EnquiryPackage.Literals.FIELD__CONVERSION,
				 EnquiryFactory.eINSTANCE.createConversion()));

		newChildDescriptors.add
			(createChildParameter
				(EnquiryPackage.Literals.FIELD__CONVERSION,
				 EnquiryFactory.eINSTANCE.createExtractConversion()));

		newChildDescriptors.add
			(createChildParameter
				(EnquiryPackage.Literals.FIELD__CONVERSION,
				 EnquiryFactory.eINSTANCE.createDecryptConversion()));

		newChildDescriptors.add
			(createChildParameter
				(EnquiryPackage.Literals.FIELD__CONVERSION,
				 EnquiryFactory.eINSTANCE.createReplaceConversion()));

		newChildDescriptors.add
			(createChildParameter
				(EnquiryPackage.Literals.FIELD__CONVERSION,
				 EnquiryFactory.eINSTANCE.createConvertConversion()));

		newChildDescriptors.add
			(createChildParameter
				(EnquiryPackage.Literals.FIELD__CONVERSION,
				 EnquiryFactory.eINSTANCE.createValueConversion()));

		newChildDescriptors.add
			(createChildParameter
				(EnquiryPackage.Literals.FIELD__CONVERSION,
				 EnquiryFactory.eINSTANCE.createJulianConversion()));

		newChildDescriptors.add
			(createChildParameter
				(EnquiryPackage.Literals.FIELD__CONVERSION,
				 EnquiryFactory.eINSTANCE.createBasicConversion()));

		newChildDescriptors.add
			(createChildParameter
				(EnquiryPackage.Literals.FIELD__CONVERSION,
				 EnquiryFactory.eINSTANCE.createBasicOConversion()));

		newChildDescriptors.add
			(createChildParameter
				(EnquiryPackage.Literals.FIELD__CONVERSION,
				 EnquiryFactory.eINSTANCE.createBasicIConversion()));

		newChildDescriptors.add
			(createChildParameter
				(EnquiryPackage.Literals.FIELD__CONVERSION,
				 EnquiryFactory.eINSTANCE.createGetFromConversion()));

		newChildDescriptors.add
			(createChildParameter
				(EnquiryPackage.Literals.FIELD__CONVERSION,
				 EnquiryFactory.eINSTANCE.createRateConversion()));

		newChildDescriptors.add
			(createChildParameter
				(EnquiryPackage.Literals.FIELD__CONVERSION,
				 EnquiryFactory.eINSTANCE.createCalcFixedRateConversion()));

		newChildDescriptors.add
			(createChildParameter
				(EnquiryPackage.Literals.FIELD__CONVERSION,
				 EnquiryFactory.eINSTANCE.createGetFixedRateConversion()));

		newChildDescriptors.add
			(createChildParameter
				(EnquiryPackage.Literals.FIELD__CONVERSION,
				 EnquiryFactory.eINSTANCE.createGetFixedCurrencyConversion()));

		newChildDescriptors.add
			(createChildParameter
				(EnquiryPackage.Literals.FIELD__CONVERSION,
				 EnquiryFactory.eINSTANCE.createAbsConversion()));

		newChildDescriptors.add
			(createChildParameter
				(EnquiryPackage.Literals.FIELD__CONVERSION,
				 EnquiryFactory.eINSTANCE.createMatchField()));

		newChildDescriptors.add
			(createChildParameter
				(EnquiryPackage.Literals.FIELD__CONVERSION,
				 EnquiryFactory.eINSTANCE.createCallRoutine()));

		newChildDescriptors.add
			(createChildParameter
				(EnquiryPackage.Literals.FIELD__CONVERSION,
				 EnquiryFactory.eINSTANCE.createRepeatConversion()));

		newChildDescriptors.add
			(createChildParameter
				(EnquiryPackage.Literals.FIELD__CONVERSION,
				 EnquiryFactory.eINSTANCE.createRepeatOnNullConversion()));

		newChildDescriptors.add
			(createChildParameter
				(EnquiryPackage.Literals.FIELD__CONVERSION,
				 EnquiryFactory.eINSTANCE.createRepeatEveryConversion()));

		newChildDescriptors.add
			(createChildParameter
				(EnquiryPackage.Literals.FIELD__CONVERSION,
				 EnquiryFactory.eINSTANCE.createRepeatSubConversion()));
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
