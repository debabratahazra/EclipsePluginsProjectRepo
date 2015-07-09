/**
 */
package com.odcgroup.t24.localReferenceApplication.impl;

import com.odcgroup.mdf.ecore.MdfPackage;

import com.odcgroup.t24.localReferenceApplication.LocalField;
import com.odcgroup.t24.localReferenceApplication.LocalReferenceApplication;
import com.odcgroup.t24.localReferenceApplication.LocalReferenceApplicationFactory;
import com.odcgroup.t24.localReferenceApplication.LocalReferenceApplicationPackage;

import com.odcgroup.translation.translationDsl.TranslationDslPackage;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcorePackage;

import org.eclipse.emf.ecore.impl.EPackageImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class LocalReferenceApplicationPackageImpl extends EPackageImpl implements LocalReferenceApplicationPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass localReferenceApplicationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass localFieldEClass = null;

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
	 * @see com.odcgroup.t24.localReferenceApplication.LocalReferenceApplicationPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private LocalReferenceApplicationPackageImpl() {
		super(eNS_URI, LocalReferenceApplicationFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link LocalReferenceApplicationPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static LocalReferenceApplicationPackage init() {
		if (isInited) return (LocalReferenceApplicationPackage)EPackage.Registry.INSTANCE.getEPackage(LocalReferenceApplicationPackage.eNS_URI);

		// Obtain or create and register package
		LocalReferenceApplicationPackageImpl theLocalReferenceApplicationPackage = (LocalReferenceApplicationPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof LocalReferenceApplicationPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new LocalReferenceApplicationPackageImpl());

		isInited = true;

		// Initialize simple dependencies
		MdfPackage.eINSTANCE.eClass();
		EcorePackage.eINSTANCE.eClass();
		TranslationDslPackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theLocalReferenceApplicationPackage.createPackageContents();

		// Initialize created meta-data
		theLocalReferenceApplicationPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theLocalReferenceApplicationPackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(LocalReferenceApplicationPackage.eNS_URI, theLocalReferenceApplicationPackage);
		return theLocalReferenceApplicationPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getLocalReferenceApplication() {
		return localReferenceApplicationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getLocalReferenceApplication_LocalField() {
		return (EReference)localReferenceApplicationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getLocalReferenceApplication_ForApplication() {
		return (EReference)localReferenceApplicationEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getLocalField() {
		return localFieldEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getLocalField_GroupName() {
		return (EAttribute)localFieldEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getLocalField_LocalRefID() {
		return (EAttribute)localFieldEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getLocalField_Label() {
		return (EReference)localFieldEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getLocalField_ToolTip() {
		return (EReference)localFieldEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LocalReferenceApplicationFactory getLocalReferenceApplicationFactory() {
		return (LocalReferenceApplicationFactory)getEFactoryInstance();
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
		localReferenceApplicationEClass = createEClass(LOCAL_REFERENCE_APPLICATION);
		createEReference(localReferenceApplicationEClass, LOCAL_REFERENCE_APPLICATION__LOCAL_FIELD);
		createEReference(localReferenceApplicationEClass, LOCAL_REFERENCE_APPLICATION__FOR_APPLICATION);

		localFieldEClass = createEClass(LOCAL_FIELD);
		createEAttribute(localFieldEClass, LOCAL_FIELD__GROUP_NAME);
		createEAttribute(localFieldEClass, LOCAL_FIELD__LOCAL_REF_ID);
		createEReference(localFieldEClass, LOCAL_FIELD__LABEL);
		createEReference(localFieldEClass, LOCAL_FIELD__TOOL_TIP);
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
		MdfPackage theMdfPackage = (MdfPackage)EPackage.Registry.INSTANCE.getEPackage(MdfPackage.eNS_URI);
		EcorePackage theEcorePackage = (EcorePackage)EPackage.Registry.INSTANCE.getEPackage(EcorePackage.eNS_URI);
		TranslationDslPackage theTranslationDslPackage = (TranslationDslPackage)EPackage.Registry.INSTANCE.getEPackage(TranslationDslPackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes

		// Initialize classes and features; add operations and parameters
		initEClass(localReferenceApplicationEClass, LocalReferenceApplication.class, "LocalReferenceApplication", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getLocalReferenceApplication_LocalField(), this.getLocalField(), null, "localField", null, 1, -1, LocalReferenceApplication.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getLocalReferenceApplication_ForApplication(), theMdfPackage.getMdfClass(), null, "forApplication", null, 0, 1, LocalReferenceApplication.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(localFieldEClass, LocalField.class, "LocalField", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getLocalField_GroupName(), theEcorePackage.getEString(), "groupName", null, 0, 1, LocalField.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getLocalField_LocalRefID(), theEcorePackage.getEString(), "localRefID", "", 1, 1, LocalField.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getLocalField_Label(), theTranslationDslPackage.getTranslations(), null, "label", null, 0, 1, LocalField.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getLocalField_ToolTip(), theTranslationDslPackage.getTranslations(), null, "toolTip", null, 0, 1, LocalField.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Create resource
		createResource(eNS_URI);

		// Create annotations
		// http:///org/eclipse/emf/ecore/util/ExtendedMetaData
		createExtendedMetaDataAnnotations();
	}

	/**
	 * Initializes the annotations for <b>http:///org/eclipse/emf/ecore/util/ExtendedMetaData</b>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void createExtendedMetaDataAnnotations() {
		String source = "http:///org/eclipse/emf/ecore/util/ExtendedMetaData";		
		addAnnotation
		  (getLocalField_GroupName(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "groupName"
		   });		
		addAnnotation
		  (getLocalField_LocalRefID(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "localRefID"
		   });
	}

} //LocalReferenceApplicationPackageImpl
