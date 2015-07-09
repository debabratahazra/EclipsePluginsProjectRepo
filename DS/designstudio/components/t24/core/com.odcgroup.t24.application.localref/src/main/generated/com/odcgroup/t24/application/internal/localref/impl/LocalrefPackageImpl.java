/**
 */
package com.odcgroup.t24.application.internal.localref.impl;

import com.odcgroup.mdf.ecore.MdfPackage;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.EPackageImpl;

import com.odcgroup.t24.application.internal.localref.LocalRef;
import com.odcgroup.t24.application.internal.localref.LocalrefFactory;
import com.odcgroup.t24.application.internal.localref.LocalrefPackage;
import com.odcgroup.t24.application.internal.localref.NoInputChange;
import com.odcgroup.t24.application.internal.localref.VettingTable;
import com.odcgroup.translation.translationDsl.TranslationDslPackage;


/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class LocalrefPackageImpl extends EPackageImpl implements LocalrefPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass localRefEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass vettingTableEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum noInputChangeEEnum = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
	 * package URI value.
	 * <p>Note: the correct way to create the package is via the static
	 * factory method {@link #init init()}, which also performs
	 * initialization of the package, or returns the registered package,
	 * if one already exists.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see com.odcgroup.t24.application.internal.localref.LocalrefPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private LocalrefPackageImpl() {
		super(eNS_URI, LocalrefFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
	 * 
	 * <p>This method is used to initialize {@link LocalrefPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static LocalrefPackage init() {
		if (isInited) return (LocalrefPackage)EPackage.Registry.INSTANCE.getEPackage(LocalrefPackage.eNS_URI);

		// Obtain or create and register package
		LocalrefPackageImpl theLocalrefPackage = (LocalrefPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof LocalrefPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new LocalrefPackageImpl());

		isInited = true;

		// Initialize simple dependencies
		MdfPackage.eINSTANCE.eClass();
		TranslationDslPackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theLocalrefPackage.createPackageContents();

		// Initialize created meta-data
		theLocalrefPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theLocalrefPackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(LocalrefPackage.eNS_URI, theLocalrefPackage);
		return theLocalrefPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getLocalRef() {
		return localRefEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getLocalRef_LocalRefID() {
		return (EAttribute)localRefEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getLocalRef_MaximumChars() {
		return (EAttribute)localRefEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getLocalRef_MinimumChars() {
		return (EAttribute)localRefEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getLocalRef_Mandatory() {
		return (EAttribute)localRefEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getLocalRef_CharType() {
		return (EAttribute)localRefEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getLocalRef_OverridePossible() {
		return (EAttribute)localRefEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getLocalRef_NoInputChange() {
		return (EAttribute)localRefEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getLocalRef_DefaultPossible() {
		return (EAttribute)localRefEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getLocalRef_ApplicationVET() {
		return (EReference)localRefEClass.getEStructuralFeatures().get(12);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getLocalRef_ApplicationEnrich() {
		return (EAttribute)localRefEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getLocalRef_Description() {
		return (EAttribute)localRefEClass.getEStructuralFeatures().get(13);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getLocalRef_VettingTable() {
		return (EReference)localRefEClass.getEStructuralFeatures().get(10);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getLocalRef_ShortName() {
		return (EReference)localRefEClass.getEStructuralFeatures().get(9);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getLocalRef_VirtualTable() {
		return (EAttribute)localRefEClass.getEStructuralFeatures().get(11);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getVettingTable() {
		return vettingTableEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getVettingTable_VettingTable() {
		return (EAttribute)vettingTableEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getVettingTable_Remarks() {
		return (EReference)vettingTableEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getNoInputChange() {
		return noInputChangeEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LocalrefFactory getLocalrefFactory() {
		return (LocalrefFactory)getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package.  This method is
	 * guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void createPackageContents() {
		if (isCreated) return;
		isCreated = true;

		// Create classes and their features
		localRefEClass = createEClass(LOCAL_REF);
		createEAttribute(localRefEClass, LOCAL_REF__LOCAL_REF_ID);
		createEAttribute(localRefEClass, LOCAL_REF__MAXIMUM_CHARS);
		createEAttribute(localRefEClass, LOCAL_REF__MINIMUM_CHARS);
		createEAttribute(localRefEClass, LOCAL_REF__MANDATORY);
		createEAttribute(localRefEClass, LOCAL_REF__CHAR_TYPE);
		createEAttribute(localRefEClass, LOCAL_REF__OVERRIDE_POSSIBLE);
		createEAttribute(localRefEClass, LOCAL_REF__NO_INPUT_CHANGE);
		createEAttribute(localRefEClass, LOCAL_REF__DEFAULT_POSSIBLE);
		createEAttribute(localRefEClass, LOCAL_REF__APPLICATION_ENRICH);
		createEReference(localRefEClass, LOCAL_REF__SHORT_NAME);
		createEReference(localRefEClass, LOCAL_REF__VETTING_TABLE);
		createEAttribute(localRefEClass, LOCAL_REF__VIRTUAL_TABLE);
		createEReference(localRefEClass, LOCAL_REF__APPLICATION_VET);
		createEAttribute(localRefEClass, LOCAL_REF__DESCRIPTION);

		vettingTableEClass = createEClass(VETTING_TABLE);
		createEAttribute(vettingTableEClass, VETTING_TABLE__VETTING_TABLE);
		createEReference(vettingTableEClass, VETTING_TABLE__REMARKS);

		// Create enums
		noInputChangeEEnum = createEEnum(NO_INPUT_CHANGE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model.  This
	 * method is guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void initializePackageContents() {
		if (isInitialized) return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Obtain other dependent packages
		TranslationDslPackage theTranslationDslPackage = (TranslationDslPackage)EPackage.Registry.INSTANCE.getEPackage(TranslationDslPackage.eNS_URI);
		MdfPackage theMdfPackage = (MdfPackage)EPackage.Registry.INSTANCE.getEPackage(MdfPackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes

		// Initialize classes and features; add operations and parameters
		initEClass(localRefEClass, LocalRef.class, "LocalRef", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getLocalRef_LocalRefID(), ecorePackage.getEString(), "localRefID", null, 1, 1, LocalRef.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getLocalRef_MaximumChars(), ecorePackage.getEIntegerObject(), "maximumChars", null, 1, 1, LocalRef.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getLocalRef_MinimumChars(), ecorePackage.getEIntegerObject(), "minimumChars", null, 1, 1, LocalRef.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getLocalRef_Mandatory(), ecorePackage.getEBoolean(), "mandatory", "false", 0, 1, LocalRef.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getLocalRef_CharType(), ecorePackage.getEString(), "charType", null, 0, 1, LocalRef.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getLocalRef_OverridePossible(), ecorePackage.getEBoolean(), "overridePossible", null, 0, 1, LocalRef.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getLocalRef_NoInputChange(), this.getNoInputChange(), "noInputChange", null, 0, 1, LocalRef.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getLocalRef_DefaultPossible(), ecorePackage.getEBoolean(), "defaultPossible", "true", 0, 1, LocalRef.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getLocalRef_ApplicationEnrich(), ecorePackage.getEString(), "applicationEnrich", null, 0, 1, LocalRef.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getLocalRef_ShortName(), theTranslationDslPackage.getTranslations(), null, "shortName", null, 1, -1, LocalRef.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getLocalRef_VettingTable(), this.getVettingTable(), null, "vettingTable", null, 0, -1, LocalRef.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getLocalRef_VirtualTable(), ecorePackage.getEString(), "virtualTable", null, 0, 1, LocalRef.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getLocalRef_ApplicationVET(), theMdfPackage.getMdfClass(), null, "applicationVET", null, 0, 1, LocalRef.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getLocalRef_Description(), ecorePackage.getEString(), "description", null, 0, 1, LocalRef.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(vettingTableEClass, VettingTable.class, "VettingTable", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getVettingTable_VettingTable(), ecorePackage.getEString(), "vettingTable", null, 1, 1, VettingTable.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getVettingTable_Remarks(), theTranslationDslPackage.getTranslations(), null, "remarks", null, 0, -1, VettingTable.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Initialize enums and add enum literals
		initEEnum(noInputChangeEEnum, NoInputChange.class, "NoInputChange");
		addEEnumLiteral(noInputChangeEEnum, NoInputChange.NONE);
		addEEnumLiteral(noInputChangeEEnum, NoInputChange.NOINPUT);
		addEEnumLiteral(noInputChangeEEnum, NoInputChange.NOCHANGE);

		// Create resource
		createResource(eNS_URI);
	}

} //LocalrefPackageImpl
