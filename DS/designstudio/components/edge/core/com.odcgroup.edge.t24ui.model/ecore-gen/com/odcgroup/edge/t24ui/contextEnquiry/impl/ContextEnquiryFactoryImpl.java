/**
 */
package com.odcgroup.edge.t24ui.contextEnquiry.impl;

import com.odcgroup.edge.t24ui.contextEnquiry.*;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class ContextEnquiryFactoryImpl extends EFactoryImpl implements ContextEnquiryFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static ContextEnquiryFactory init() {
		try {
			ContextEnquiryFactory theContextEnquiryFactory = (ContextEnquiryFactory)EPackage.Registry.INSTANCE.getEFactory(ContextEnquiryPackage.eNS_URI);
			if (theContextEnquiryFactory != null) {
				return theContextEnquiryFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new ContextEnquiryFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ContextEnquiryFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case ContextEnquiryPackage.DESCRIPTION: return createDescription();
			case ContextEnquiryPackage.SELECTION_CRITERIA: return createSelectionCriteria();
			case ContextEnquiryPackage.APPLIED_ENQUIRY: return createAppliedEnquiry();
			case ContextEnquiryPackage.APPLICATION_CONTEXT_ENQUIRY: return createApplicationContextEnquiry();
			case ContextEnquiryPackage.VERSION_CONTEXT_ENQUIRY: return createVersionContextEnquiry();
			case ContextEnquiryPackage.FUNCTION: return createFunction();
			case ContextEnquiryPackage.FUNCTION_AUTO_LAUNCH: return createFunctionAutoLaunch();
			case ContextEnquiryPackage.ON_CHANGE_AUTO_LAUNCH: return createOnChangeAutoLaunch();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object createFromString(EDataType eDataType, String initialValue) {
		switch (eDataType.getClassifierID()) {
			case ContextEnquiryPackage.SORT_ORDER:
				return createSortOrderFromString(eDataType, initialValue);
			case ContextEnquiryPackage.OPERAND:
				return createOperandFromString(eDataType, initialValue);
			case ContextEnquiryPackage.FUNCTION_ENUM:
				return createFunctionEnumFromString(eDataType, initialValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String convertToString(EDataType eDataType, Object instanceValue) {
		switch (eDataType.getClassifierID()) {
			case ContextEnquiryPackage.SORT_ORDER:
				return convertSortOrderToString(eDataType, instanceValue);
			case ContextEnquiryPackage.OPERAND:
				return convertOperandToString(eDataType, instanceValue);
			case ContextEnquiryPackage.FUNCTION_ENUM:
				return convertFunctionEnumToString(eDataType, instanceValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Description createDescription() {
		DescriptionImpl description = new DescriptionImpl();
		return description;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SelectionCriteria createSelectionCriteria() {
		SelectionCriteriaImpl selectionCriteria = new SelectionCriteriaImpl();
		return selectionCriteria;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AppliedEnquiry createAppliedEnquiry() {
		AppliedEnquiryImpl appliedEnquiry = new AppliedEnquiryImpl();
		return appliedEnquiry;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ApplicationContextEnquiry createApplicationContextEnquiry() {
		ApplicationContextEnquiryImpl applicationContextEnquiry = new ApplicationContextEnquiryImpl();
		return applicationContextEnquiry;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public VersionContextEnquiry createVersionContextEnquiry() {
		VersionContextEnquiryImpl versionContextEnquiry = new VersionContextEnquiryImpl();
		return versionContextEnquiry;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Function createFunction() {
		FunctionImpl function = new FunctionImpl();
		return function;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FunctionAutoLaunch createFunctionAutoLaunch() {
		FunctionAutoLaunchImpl functionAutoLaunch = new FunctionAutoLaunchImpl();
		return functionAutoLaunch;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OnChangeAutoLaunch createOnChangeAutoLaunch() {
		OnChangeAutoLaunchImpl onChangeAutoLaunch = new OnChangeAutoLaunchImpl();
		return onChangeAutoLaunch;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SortOrder createSortOrderFromString(EDataType eDataType, String initialValue) {
		SortOrder result = SortOrder.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertSortOrderToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Operand createOperandFromString(EDataType eDataType, String initialValue) {
		Operand result = Operand.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertOperandToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FunctionEnum createFunctionEnumFromString(EDataType eDataType, String initialValue) {
		FunctionEnum result = FunctionEnum.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertFunctionEnumToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ContextEnquiryPackage getContextEnquiryPackage() {
		return (ContextEnquiryPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static ContextEnquiryPackage getPackage() {
		return ContextEnquiryPackage.eINSTANCE;
	}

} //ContextEnquiryFactoryImpl
