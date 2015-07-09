/**
 */
package com.odcgroup.t24.application.internal.localref;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
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
 * @see com.odcgroup.t24.application.internal.localref.LocalrefFactory
 * @model kind="package"
 * @generated
 */
public interface LocalrefPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "localref";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.odcgroup.com/t24/application/localref/model/LocalRef";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "localref";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	LocalrefPackage eINSTANCE = com.odcgroup.t24.application.internal.localref.impl.LocalrefPackageImpl.init();

	/**
	 * The meta object id for the '{@link com.odcgroup.t24.application.internal.localref.impl.LocalRefImpl <em>Local Ref</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.odcgroup.t24.application.internal.localref.impl.LocalRefImpl
	 * @see com.odcgroup.t24.application.internal.localref.impl.LocalrefPackageImpl#getLocalRef()
	 * @generated
	 */
	int LOCAL_REF = 0;

	/**
	 * The feature id for the '<em><b>Local Ref ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOCAL_REF__LOCAL_REF_ID = 0;

	/**
	 * The feature id for the '<em><b>Maximum Chars</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOCAL_REF__MAXIMUM_CHARS = 1;

	/**
	 * The feature id for the '<em><b>Minimum Chars</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOCAL_REF__MINIMUM_CHARS = 2;

	/**
	 * The feature id for the '<em><b>Mandatory</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOCAL_REF__MANDATORY = 3;

	/**
	 * The feature id for the '<em><b>Char Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOCAL_REF__CHAR_TYPE = 4;

	/**
	 * The feature id for the '<em><b>Override Possible</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOCAL_REF__OVERRIDE_POSSIBLE = 5;

	/**
	 * The feature id for the '<em><b>No Input Change</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOCAL_REF__NO_INPUT_CHANGE = 6;

	/**
	 * The feature id for the '<em><b>Default Possible</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOCAL_REF__DEFAULT_POSSIBLE = 7;

	/**
	 * The feature id for the '<em><b>Application Enrich</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOCAL_REF__APPLICATION_ENRICH = 8;

	/**
	 * The feature id for the '<em><b>Short Name</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOCAL_REF__SHORT_NAME = 9;

	/**
	 * The feature id for the '<em><b>Vetting Table</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOCAL_REF__VETTING_TABLE = 10;

	/**
	 * The feature id for the '<em><b>Virtual Table</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOCAL_REF__VIRTUAL_TABLE = 11;

	/**
	 * The feature id for the '<em><b>Application VET</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOCAL_REF__APPLICATION_VET = 12;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOCAL_REF__DESCRIPTION = 13;

	/**
	 * The number of structural features of the '<em>Local Ref</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOCAL_REF_FEATURE_COUNT = 14;

	/**
	 * The meta object id for the '{@link com.odcgroup.t24.application.internal.localref.impl.VettingTableImpl <em>Vetting Table</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.odcgroup.t24.application.internal.localref.impl.VettingTableImpl
	 * @see com.odcgroup.t24.application.internal.localref.impl.LocalrefPackageImpl#getVettingTable()
	 * @generated
	 */
	int VETTING_TABLE = 1;

	/**
	 * The feature id for the '<em><b>Vetting Table</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VETTING_TABLE__VETTING_TABLE = 0;

	/**
	 * The feature id for the '<em><b>Remarks</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VETTING_TABLE__REMARKS = 1;

	/**
	 * The number of structural features of the '<em>Vetting Table</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VETTING_TABLE_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link com.odcgroup.t24.application.internal.localref.NoInputChange <em>No Input Change</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.odcgroup.t24.application.internal.localref.NoInputChange
	 * @see com.odcgroup.t24.application.internal.localref.impl.LocalrefPackageImpl#getNoInputChange()
	 * @generated
	 */
	int NO_INPUT_CHANGE = 2;


	/**
	 * Returns the meta object for class '{@link com.odcgroup.t24.application.internal.localref.LocalRef <em>Local Ref</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Local Ref</em>'.
	 * @see com.odcgroup.t24.application.internal.localref.LocalRef
	 * @generated
	 */
	EClass getLocalRef();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.t24.application.internal.localref.LocalRef#getLocalRefID <em>Local Ref ID</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Local Ref ID</em>'.
	 * @see com.odcgroup.t24.application.internal.localref.LocalRef#getLocalRefID()
	 * @see #getLocalRef()
	 * @generated
	 */
	EAttribute getLocalRef_LocalRefID();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.t24.application.internal.localref.LocalRef#getMaximumChars <em>Maximum Chars</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Maximum Chars</em>'.
	 * @see com.odcgroup.t24.application.internal.localref.LocalRef#getMaximumChars()
	 * @see #getLocalRef()
	 * @generated
	 */
	EAttribute getLocalRef_MaximumChars();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.t24.application.internal.localref.LocalRef#getMinimumChars <em>Minimum Chars</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Minimum Chars</em>'.
	 * @see com.odcgroup.t24.application.internal.localref.LocalRef#getMinimumChars()
	 * @see #getLocalRef()
	 * @generated
	 */
	EAttribute getLocalRef_MinimumChars();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.t24.application.internal.localref.LocalRef#isMandatory <em>Mandatory</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Mandatory</em>'.
	 * @see com.odcgroup.t24.application.internal.localref.LocalRef#isMandatory()
	 * @see #getLocalRef()
	 * @generated
	 */
	EAttribute getLocalRef_Mandatory();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.t24.application.internal.localref.LocalRef#getCharType <em>Char Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Char Type</em>'.
	 * @see com.odcgroup.t24.application.internal.localref.LocalRef#getCharType()
	 * @see #getLocalRef()
	 * @generated
	 */
	EAttribute getLocalRef_CharType();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.t24.application.internal.localref.LocalRef#isOverridePossible <em>Override Possible</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Override Possible</em>'.
	 * @see com.odcgroup.t24.application.internal.localref.LocalRef#isOverridePossible()
	 * @see #getLocalRef()
	 * @generated
	 */
	EAttribute getLocalRef_OverridePossible();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.t24.application.internal.localref.LocalRef#getNoInputChange <em>No Input Change</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>No Input Change</em>'.
	 * @see com.odcgroup.t24.application.internal.localref.LocalRef#getNoInputChange()
	 * @see #getLocalRef()
	 * @generated
	 */
	EAttribute getLocalRef_NoInputChange();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.t24.application.internal.localref.LocalRef#isDefaultPossible <em>Default Possible</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Default Possible</em>'.
	 * @see com.odcgroup.t24.application.internal.localref.LocalRef#isDefaultPossible()
	 * @see #getLocalRef()
	 * @generated
	 */
	EAttribute getLocalRef_DefaultPossible();

	/**
	 * Returns the meta object for the reference '{@link com.odcgroup.t24.application.internal.localref.LocalRef#getApplicationVET <em>Application VET</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Application VET</em>'.
	 * @see com.odcgroup.t24.application.internal.localref.LocalRef#getApplicationVET()
	 * @see #getLocalRef()
	 * @generated
	 */
	EReference getLocalRef_ApplicationVET();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.t24.application.internal.localref.LocalRef#getApplicationEnrich <em>Application Enrich</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Application Enrich</em>'.
	 * @see com.odcgroup.t24.application.internal.localref.LocalRef#getApplicationEnrich()
	 * @see #getLocalRef()
	 * @generated
	 */
	EAttribute getLocalRef_ApplicationEnrich();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.t24.application.internal.localref.LocalRef#getDescription <em>Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Description</em>'.
	 * @see com.odcgroup.t24.application.internal.localref.LocalRef#getDescription()
	 * @see #getLocalRef()
	 * @generated
	 */
	EAttribute getLocalRef_Description();

	/**
	 * Returns the meta object for the containment reference list '{@link com.odcgroup.t24.application.internal.localref.LocalRef#getVettingTable <em>Vetting Table</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Vetting Table</em>'.
	 * @see com.odcgroup.t24.application.internal.localref.LocalRef#getVettingTable()
	 * @see #getLocalRef()
	 * @generated
	 */
	EReference getLocalRef_VettingTable();

	/**
	 * Returns the meta object for the containment reference list '{@link com.odcgroup.t24.application.internal.localref.LocalRef#getShortName <em>Short Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Short Name</em>'.
	 * @see com.odcgroup.t24.application.internal.localref.LocalRef#getShortName()
	 * @see #getLocalRef()
	 * @generated
	 */
	EReference getLocalRef_ShortName();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.t24.application.internal.localref.LocalRef#getVirtualTable <em>Virtual Table</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Virtual Table</em>'.
	 * @see com.odcgroup.t24.application.internal.localref.LocalRef#getVirtualTable()
	 * @see #getLocalRef()
	 * @generated
	 */
	EAttribute getLocalRef_VirtualTable();

	/**
	 * Returns the meta object for class '{@link com.odcgroup.t24.application.internal.localref.VettingTable <em>Vetting Table</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Vetting Table</em>'.
	 * @see com.odcgroup.t24.application.internal.localref.VettingTable
	 * @generated
	 */
	EClass getVettingTable();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.t24.application.internal.localref.VettingTable#getVettingTable <em>Vetting Table</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Vetting Table</em>'.
	 * @see com.odcgroup.t24.application.internal.localref.VettingTable#getVettingTable()
	 * @see #getVettingTable()
	 * @generated
	 */
	EAttribute getVettingTable_VettingTable();

	/**
	 * Returns the meta object for the containment reference list '{@link com.odcgroup.t24.application.internal.localref.VettingTable#getRemarks <em>Remarks</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Remarks</em>'.
	 * @see com.odcgroup.t24.application.internal.localref.VettingTable#getRemarks()
	 * @see #getVettingTable()
	 * @generated
	 */
	EReference getVettingTable_Remarks();

	/**
	 * Returns the meta object for enum '{@link com.odcgroup.t24.application.internal.localref.NoInputChange <em>No Input Change</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>No Input Change</em>'.
	 * @see com.odcgroup.t24.application.internal.localref.NoInputChange
	 * @generated
	 */
	EEnum getNoInputChange();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	LocalrefFactory getLocalrefFactory();

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
		 * The meta object literal for the '{@link com.odcgroup.t24.application.internal.localref.impl.LocalRefImpl <em>Local Ref</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.odcgroup.t24.application.internal.localref.impl.LocalRefImpl
		 * @see com.odcgroup.t24.application.internal.localref.impl.LocalrefPackageImpl#getLocalRef()
		 * @generated
		 */
		EClass LOCAL_REF = eINSTANCE.getLocalRef();

		/**
		 * The meta object literal for the '<em><b>Local Ref ID</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute LOCAL_REF__LOCAL_REF_ID = eINSTANCE.getLocalRef_LocalRefID();

		/**
		 * The meta object literal for the '<em><b>Maximum Chars</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute LOCAL_REF__MAXIMUM_CHARS = eINSTANCE.getLocalRef_MaximumChars();

		/**
		 * The meta object literal for the '<em><b>Minimum Chars</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute LOCAL_REF__MINIMUM_CHARS = eINSTANCE.getLocalRef_MinimumChars();

		/**
		 * The meta object literal for the '<em><b>Mandatory</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute LOCAL_REF__MANDATORY = eINSTANCE.getLocalRef_Mandatory();

		/**
		 * The meta object literal for the '<em><b>Char Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute LOCAL_REF__CHAR_TYPE = eINSTANCE.getLocalRef_CharType();

		/**
		 * The meta object literal for the '<em><b>Override Possible</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute LOCAL_REF__OVERRIDE_POSSIBLE = eINSTANCE.getLocalRef_OverridePossible();

		/**
		 * The meta object literal for the '<em><b>No Input Change</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute LOCAL_REF__NO_INPUT_CHANGE = eINSTANCE.getLocalRef_NoInputChange();

		/**
		 * The meta object literal for the '<em><b>Default Possible</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute LOCAL_REF__DEFAULT_POSSIBLE = eINSTANCE.getLocalRef_DefaultPossible();

		/**
		 * The meta object literal for the '<em><b>Application VET</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference LOCAL_REF__APPLICATION_VET = eINSTANCE.getLocalRef_ApplicationVET();

		/**
		 * The meta object literal for the '<em><b>Application Enrich</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute LOCAL_REF__APPLICATION_ENRICH = eINSTANCE.getLocalRef_ApplicationEnrich();

		/**
		 * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute LOCAL_REF__DESCRIPTION = eINSTANCE.getLocalRef_Description();

		/**
		 * The meta object literal for the '<em><b>Vetting Table</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference LOCAL_REF__VETTING_TABLE = eINSTANCE.getLocalRef_VettingTable();

		/**
		 * The meta object literal for the '<em><b>Short Name</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference LOCAL_REF__SHORT_NAME = eINSTANCE.getLocalRef_ShortName();

		/**
		 * The meta object literal for the '<em><b>Virtual Table</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute LOCAL_REF__VIRTUAL_TABLE = eINSTANCE.getLocalRef_VirtualTable();

		/**
		 * The meta object literal for the '{@link com.odcgroup.t24.application.internal.localref.impl.VettingTableImpl <em>Vetting Table</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.odcgroup.t24.application.internal.localref.impl.VettingTableImpl
		 * @see com.odcgroup.t24.application.internal.localref.impl.LocalrefPackageImpl#getVettingTable()
		 * @generated
		 */
		EClass VETTING_TABLE = eINSTANCE.getVettingTable();

		/**
		 * The meta object literal for the '<em><b>Vetting Table</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute VETTING_TABLE__VETTING_TABLE = eINSTANCE.getVettingTable_VettingTable();

		/**
		 * The meta object literal for the '<em><b>Remarks</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference VETTING_TABLE__REMARKS = eINSTANCE.getVettingTable_Remarks();

		/**
		 * The meta object literal for the '{@link com.odcgroup.t24.application.internal.localref.NoInputChange <em>No Input Change</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.odcgroup.t24.application.internal.localref.NoInputChange
		 * @see com.odcgroup.t24.application.internal.localref.impl.LocalrefPackageImpl#getNoInputChange()
		 * @generated
		 */
		EEnum NO_INPUT_CHANGE = eINSTANCE.getNoInputChange();

	}

} //LocalrefPackage
