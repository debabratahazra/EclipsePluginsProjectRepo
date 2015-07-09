/**
 */
package com.odcgroup.t24.localReferenceApplication;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see com.odcgroup.t24.localReferenceApplication.LocalReferenceApplicationFactory
 * @model kind="package"
 * @generated
 */
public interface LocalReferenceApplicationPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "localReferenceApplication";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.odcgroup.com/t24/application/localRefApplication/model/LocalReferenceApplication";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "localReferenceApplication";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	LocalReferenceApplicationPackage eINSTANCE = com.odcgroup.t24.localReferenceApplication.impl.LocalReferenceApplicationPackageImpl.init();

	/**
	 * The meta object id for the '{@link com.odcgroup.t24.localReferenceApplication.impl.LocalReferenceApplicationImpl <em>Local Reference Application</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.odcgroup.t24.localReferenceApplication.impl.LocalReferenceApplicationImpl
	 * @see com.odcgroup.t24.localReferenceApplication.impl.LocalReferenceApplicationPackageImpl#getLocalReferenceApplication()
	 * @generated
	 */
	int LOCAL_REFERENCE_APPLICATION = 0;

	/**
	 * The feature id for the '<em><b>Local Field</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOCAL_REFERENCE_APPLICATION__LOCAL_FIELD = 0;

	/**
	 * The feature id for the '<em><b>For Application</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOCAL_REFERENCE_APPLICATION__FOR_APPLICATION = 1;

	/**
	 * The number of structural features of the '<em>Local Reference Application</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOCAL_REFERENCE_APPLICATION_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link com.odcgroup.t24.localReferenceApplication.impl.LocalFieldImpl <em>Local Field</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.odcgroup.t24.localReferenceApplication.impl.LocalFieldImpl
	 * @see com.odcgroup.t24.localReferenceApplication.impl.LocalReferenceApplicationPackageImpl#getLocalField()
	 * @generated
	 */
	int LOCAL_FIELD = 1;

	/**
	 * The feature id for the '<em><b>Group Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOCAL_FIELD__GROUP_NAME = 0;

	/**
	 * The feature id for the '<em><b>Local Ref ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOCAL_FIELD__LOCAL_REF_ID = 1;

	/**
	 * The feature id for the '<em><b>Label</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOCAL_FIELD__LABEL = 2;

	/**
	 * The feature id for the '<em><b>Tool Tip</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOCAL_FIELD__TOOL_TIP = 3;

	/**
	 * The number of structural features of the '<em>Local Field</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOCAL_FIELD_FEATURE_COUNT = 4;


	/**
	 * Returns the meta object for class '{@link com.odcgroup.t24.localReferenceApplication.LocalReferenceApplication <em>Local Reference Application</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Local Reference Application</em>'.
	 * @see com.odcgroup.t24.localReferenceApplication.LocalReferenceApplication
	 * @generated
	 */
	EClass getLocalReferenceApplication();

	/**
	 * Returns the meta object for the containment reference list '{@link com.odcgroup.t24.localReferenceApplication.LocalReferenceApplication#getLocalField <em>Local Field</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Local Field</em>'.
	 * @see com.odcgroup.t24.localReferenceApplication.LocalReferenceApplication#getLocalField()
	 * @see #getLocalReferenceApplication()
	 * @generated
	 */
	EReference getLocalReferenceApplication_LocalField();

	/**
	 * Returns the meta object for the reference '{@link com.odcgroup.t24.localReferenceApplication.LocalReferenceApplication#getForApplication <em>For Application</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>For Application</em>'.
	 * @see com.odcgroup.t24.localReferenceApplication.LocalReferenceApplication#getForApplication()
	 * @see #getLocalReferenceApplication()
	 * @generated
	 */
	EReference getLocalReferenceApplication_ForApplication();

	/**
	 * Returns the meta object for class '{@link com.odcgroup.t24.localReferenceApplication.LocalField <em>Local Field</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Local Field</em>'.
	 * @see com.odcgroup.t24.localReferenceApplication.LocalField
	 * @generated
	 */
	EClass getLocalField();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.t24.localReferenceApplication.LocalField#getGroupName <em>Group Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Group Name</em>'.
	 * @see com.odcgroup.t24.localReferenceApplication.LocalField#getGroupName()
	 * @see #getLocalField()
	 * @generated
	 */
	EAttribute getLocalField_GroupName();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.t24.localReferenceApplication.LocalField#getLocalRefID <em>Local Ref ID</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Local Ref ID</em>'.
	 * @see com.odcgroup.t24.localReferenceApplication.LocalField#getLocalRefID()
	 * @see #getLocalField()
	 * @generated
	 */
	EAttribute getLocalField_LocalRefID();

	/**
	 * Returns the meta object for the containment reference '{@link com.odcgroup.t24.localReferenceApplication.LocalField#getLabel <em>Label</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Label</em>'.
	 * @see com.odcgroup.t24.localReferenceApplication.LocalField#getLabel()
	 * @see #getLocalField()
	 * @generated
	 */
	EReference getLocalField_Label();

	/**
	 * Returns the meta object for the containment reference '{@link com.odcgroup.t24.localReferenceApplication.LocalField#getToolTip <em>Tool Tip</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Tool Tip</em>'.
	 * @see com.odcgroup.t24.localReferenceApplication.LocalField#getToolTip()
	 * @see #getLocalField()
	 * @generated
	 */
	EReference getLocalField_ToolTip();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	LocalReferenceApplicationFactory getLocalReferenceApplicationFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link com.odcgroup.t24.localReferenceApplication.impl.LocalReferenceApplicationImpl <em>Local Reference Application</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.odcgroup.t24.localReferenceApplication.impl.LocalReferenceApplicationImpl
		 * @see com.odcgroup.t24.localReferenceApplication.impl.LocalReferenceApplicationPackageImpl#getLocalReferenceApplication()
		 * @generated
		 */
		EClass LOCAL_REFERENCE_APPLICATION = eINSTANCE.getLocalReferenceApplication();

		/**
		 * The meta object literal for the '<em><b>Local Field</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference LOCAL_REFERENCE_APPLICATION__LOCAL_FIELD = eINSTANCE.getLocalReferenceApplication_LocalField();

		/**
		 * The meta object literal for the '<em><b>For Application</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference LOCAL_REFERENCE_APPLICATION__FOR_APPLICATION = eINSTANCE.getLocalReferenceApplication_ForApplication();

		/**
		 * The meta object literal for the '{@link com.odcgroup.t24.localReferenceApplication.impl.LocalFieldImpl <em>Local Field</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.odcgroup.t24.localReferenceApplication.impl.LocalFieldImpl
		 * @see com.odcgroup.t24.localReferenceApplication.impl.LocalReferenceApplicationPackageImpl#getLocalField()
		 * @generated
		 */
		EClass LOCAL_FIELD = eINSTANCE.getLocalField();

		/**
		 * The meta object literal for the '<em><b>Group Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute LOCAL_FIELD__GROUP_NAME = eINSTANCE.getLocalField_GroupName();

		/**
		 * The meta object literal for the '<em><b>Local Ref ID</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute LOCAL_FIELD__LOCAL_REF_ID = eINSTANCE.getLocalField_LocalRefID();

		/**
		 * The meta object literal for the '<em><b>Label</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference LOCAL_FIELD__LABEL = eINSTANCE.getLocalField_Label();

		/**
		 * The meta object literal for the '<em><b>Tool Tip</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference LOCAL_FIELD__TOOL_TIP = eINSTANCE.getLocalField_ToolTip();

	}

} //LocalReferenceApplicationPackage
