/**
 */
package com.odcgroup.edge.t24ui.common.impl;

import com.odcgroup.edge.t24ui.T24UIPackage;

import com.odcgroup.edge.t24ui.common.CommonFactory;
import com.odcgroup.edge.t24ui.common.CommonPackage;
import com.odcgroup.edge.t24ui.common.Language;
import com.odcgroup.edge.t24ui.common.Translation;

import com.odcgroup.edge.t24ui.contextEnquiry.ContextEnquiryPackage;

import com.odcgroup.edge.t24ui.contextEnquiry.impl.ContextEnquiryPackageImpl;

import com.odcgroup.edge.t24ui.cos.bespoke.BespokePackage;

import com.odcgroup.edge.t24ui.cos.bespoke.impl.BespokePackageImpl;

import com.odcgroup.edge.t24ui.cos.pattern.PatternPackage;

import com.odcgroup.edge.t24ui.cos.pattern.impl.PatternPackageImpl;

import com.odcgroup.edge.t24ui.impl.T24UIPackageImpl;

import com.odcgroup.t24.enquiry.enquiry.EnquiryPackage;

import com.odcgroup.t24.version.versionDSL.VersionDSLPackage;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.impl.EPackageImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class CommonPackageImpl extends EPackageImpl implements CommonPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass languageEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass translationEClass = null;

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
	 * @see com.odcgroup.edge.t24ui.common.CommonPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private CommonPackageImpl() {
		super(eNS_URI, CommonFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link CommonPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static CommonPackage init() {
		if (isInited) return (CommonPackage)EPackage.Registry.INSTANCE.getEPackage(CommonPackage.eNS_URI);

		// Obtain or create and register package
		CommonPackageImpl theCommonPackage = (CommonPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof CommonPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new CommonPackageImpl());

		isInited = true;

		// Initialize simple dependencies
		EnquiryPackage.eINSTANCE.eClass();
		VersionDSLPackage.eINSTANCE.eClass();

		// Obtain or create and register interdependencies
		T24UIPackageImpl theT24UIPackage = (T24UIPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(T24UIPackage.eNS_URI) instanceof T24UIPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(T24UIPackage.eNS_URI) : T24UIPackage.eINSTANCE);
		ContextEnquiryPackageImpl theContextEnquiryPackage = (ContextEnquiryPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(ContextEnquiryPackage.eNS_URI) instanceof ContextEnquiryPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(ContextEnquiryPackage.eNS_URI) : ContextEnquiryPackage.eINSTANCE);
		PatternPackageImpl thePatternPackage = (PatternPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(PatternPackage.eNS_URI) instanceof PatternPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(PatternPackage.eNS_URI) : PatternPackage.eINSTANCE);
		BespokePackageImpl theBespokePackage = (BespokePackageImpl)(EPackage.Registry.INSTANCE.getEPackage(BespokePackage.eNS_URI) instanceof BespokePackageImpl ? EPackage.Registry.INSTANCE.getEPackage(BespokePackage.eNS_URI) : BespokePackage.eINSTANCE);

		// Create package meta-data objects
		theCommonPackage.createPackageContents();
		theT24UIPackage.createPackageContents();
		theContextEnquiryPackage.createPackageContents();
		thePatternPackage.createPackageContents();
		theBespokePackage.createPackageContents();

		// Initialize created meta-data
		theCommonPackage.initializePackageContents();
		theT24UIPackage.initializePackageContents();
		theContextEnquiryPackage.initializePackageContents();
		thePatternPackage.initializePackageContents();
		theBespokePackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theCommonPackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(CommonPackage.eNS_URI, theCommonPackage);
		return theCommonPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getLanguage() {
		return languageEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getLanguage_Name() {
		return (EAttribute)languageEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getLanguage_Description() {
		return (EAttribute)languageEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getTranslation() {
		return translationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getTranslation_Value() {
		return (EAttribute)translationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTranslation_Language() {
		return (EReference)translationEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CommonFactory getCommonFactory() {
		return (CommonFactory)getEFactoryInstance();
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
		languageEClass = createEClass(LANGUAGE);
		createEAttribute(languageEClass, LANGUAGE__NAME);
		createEAttribute(languageEClass, LANGUAGE__DESCRIPTION);

		translationEClass = createEClass(TRANSLATION);
		createEAttribute(translationEClass, TRANSLATION__VALUE);
		createEReference(translationEClass, TRANSLATION__LANGUAGE);
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

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes

		// Initialize classes and features; add operations and parameters
		initEClass(languageEClass, Language.class, "Language", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getLanguage_Name(), ecorePackage.getEString(), "name", null, 0, 1, Language.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getLanguage_Description(), ecorePackage.getEString(), "description", null, 0, 1, Language.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(translationEClass, Translation.class, "Translation", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getTranslation_Value(), ecorePackage.getEString(), "value", null, 0, 1, Translation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTranslation_Language(), this.getLanguage(), null, "language", null, 0, 1, Translation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
	}

} //CommonPackageImpl
