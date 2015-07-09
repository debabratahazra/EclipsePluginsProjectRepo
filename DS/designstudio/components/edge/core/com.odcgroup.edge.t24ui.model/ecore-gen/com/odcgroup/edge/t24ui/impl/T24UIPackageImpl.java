/**
 */
package com.odcgroup.edge.t24ui.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.EPackageImpl;

import com.odcgroup.edge.t24ui.AvailableCOSPatterns;
import com.odcgroup.edge.t24ui.AvailableTranslationLanguages;
import com.odcgroup.edge.t24ui.BespokeCompositeScreen;
import com.odcgroup.edge.t24ui.CompositeScreen;
import com.odcgroup.edge.t24ui.T24UIFactory;
import com.odcgroup.edge.t24ui.T24UIPackage;
import com.odcgroup.edge.t24ui.common.CommonPackage;
import com.odcgroup.edge.t24ui.common.impl.CommonPackageImpl;
import com.odcgroup.edge.t24ui.contextEnquiry.ContextEnquiryPackage;
import com.odcgroup.edge.t24ui.contextEnquiry.impl.ContextEnquiryPackageImpl;
import com.odcgroup.edge.t24ui.cos.bespoke.BespokePackage;
import com.odcgroup.edge.t24ui.cos.bespoke.impl.BespokePackageImpl;
import com.odcgroup.edge.t24ui.cos.pattern.PatternPackage;
import com.odcgroup.edge.t24ui.cos.pattern.impl.PatternPackageImpl;
import com.odcgroup.t24.enquiry.enquiry.EnquiryPackage;
import com.odcgroup.t24.version.versionDSL.VersionDSLPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class T24UIPackageImpl extends EPackageImpl implements T24UIPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass availableCOSPatternsEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass compositeScreenEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass bespokeCompositeScreenEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass availableTranslationLanguagesEClass = null;

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
	 * @see com.odcgroup.edge.t24ui.T24UIPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private T24UIPackageImpl() {
		super(eNS_URI, T24UIFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link T24UIPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static T24UIPackage init() {
		if (isInited) return (T24UIPackage)EPackage.Registry.INSTANCE.getEPackage(T24UIPackage.eNS_URI);

		// Obtain or create and register package
		T24UIPackageImpl theT24UIPackage = (T24UIPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof T24UIPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new T24UIPackageImpl());

		isInited = true;

		// Initialize simple dependencies
		EnquiryPackage.eINSTANCE.eClass();
		VersionDSLPackage.eINSTANCE.eClass();

		// Obtain or create and register interdependencies
		CommonPackageImpl theCommonPackage = (CommonPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(CommonPackage.eNS_URI) instanceof CommonPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(CommonPackage.eNS_URI) : CommonPackage.eINSTANCE);
		ContextEnquiryPackageImpl theContextEnquiryPackage = (ContextEnquiryPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(ContextEnquiryPackage.eNS_URI) instanceof ContextEnquiryPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(ContextEnquiryPackage.eNS_URI) : ContextEnquiryPackage.eINSTANCE);
		PatternPackageImpl thePatternPackage = (PatternPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(PatternPackage.eNS_URI) instanceof PatternPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(PatternPackage.eNS_URI) : PatternPackage.eINSTANCE);
		BespokePackageImpl theBespokePackage = (BespokePackageImpl)(EPackage.Registry.INSTANCE.getEPackage(BespokePackage.eNS_URI) instanceof BespokePackageImpl ? EPackage.Registry.INSTANCE.getEPackage(BespokePackage.eNS_URI) : BespokePackage.eINSTANCE);

		// Create package meta-data objects
		theT24UIPackage.createPackageContents();
		theCommonPackage.createPackageContents();
		theContextEnquiryPackage.createPackageContents();
		thePatternPackage.createPackageContents();
		theBespokePackage.createPackageContents();

		// Initialize created meta-data
		theT24UIPackage.initializePackageContents();
		theCommonPackage.initializePackageContents();
		theContextEnquiryPackage.initializePackageContents();
		thePatternPackage.initializePackageContents();
		theBespokePackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theT24UIPackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(T24UIPackage.eNS_URI, theT24UIPackage);
		return theT24UIPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAvailableCOSPatterns() {
		return availableCOSPatternsEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAvailableCOSPatterns_Patterns() {
		return (EReference)availableCOSPatternsEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getCompositeScreen() {
		return compositeScreenEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getCompositeScreen_Domain() {
		return (EAttribute)compositeScreenEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getCompositeScreen_MenuDslName() {
		return (EAttribute)compositeScreenEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EAttribute getCompositeScreen_TopLevelCOS() {
		return (EAttribute)compositeScreenEClass.getEStructuralFeatures().get(2);
	}

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getBespokeCompositeScreen() {
		return bespokeCompositeScreenEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getBespokeCompositeScreen_Name() {
		return (EAttribute)bespokeCompositeScreenEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getBespokeCompositeScreen_Sections() {
		return (EReference)bespokeCompositeScreenEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAvailableTranslationLanguages() {
		return availableTranslationLanguagesEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAvailableTranslationLanguages_Languages() {
		return (EReference)availableTranslationLanguagesEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public T24UIFactory getT24UIFactory() {
		return (T24UIFactory)getEFactoryInstance();
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
		availableCOSPatternsEClass = createEClass(AVAILABLE_COS_PATTERNS);
		createEReference(availableCOSPatternsEClass, AVAILABLE_COS_PATTERNS__PATTERNS);

		compositeScreenEClass = createEClass(COMPOSITE_SCREEN);
		createEAttribute(compositeScreenEClass, COMPOSITE_SCREEN__DOMAIN);
		createEAttribute(compositeScreenEClass, COMPOSITE_SCREEN__MENU_DSL_NAME);
		createEAttribute(compositeScreenEClass, COMPOSITE_SCREEN__TOP_LEVEL_COS);

		bespokeCompositeScreenEClass = createEClass(BESPOKE_COMPOSITE_SCREEN);
		createEAttribute(bespokeCompositeScreenEClass, BESPOKE_COMPOSITE_SCREEN__NAME);
		createEReference(bespokeCompositeScreenEClass, BESPOKE_COMPOSITE_SCREEN__SECTIONS);

		availableTranslationLanguagesEClass = createEClass(AVAILABLE_TRANSLATION_LANGUAGES);
		createEReference(availableTranslationLanguagesEClass, AVAILABLE_TRANSLATION_LANGUAGES__LANGUAGES);
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
		CommonPackage theCommonPackage = (CommonPackage)EPackage.Registry.INSTANCE.getEPackage(CommonPackage.eNS_URI);
		ContextEnquiryPackage theContextEnquiryPackage = (ContextEnquiryPackage)EPackage.Registry.INSTANCE.getEPackage(ContextEnquiryPackage.eNS_URI);
		PatternPackage thePatternPackage = (PatternPackage)EPackage.Registry.INSTANCE.getEPackage(PatternPackage.eNS_URI);
		BespokePackage theBespokePackage = (BespokePackage)EPackage.Registry.INSTANCE.getEPackage(BespokePackage.eNS_URI);

		// Add subpackages
		getESubpackages().add(theCommonPackage);
		getESubpackages().add(theContextEnquiryPackage);
		getESubpackages().add(thePatternPackage);
		getESubpackages().add(theBespokePackage);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		compositeScreenEClass.getESuperTypes().add(thePatternPackage.getAbstractCOS());

		// Initialize classes and features; add operations and parameters
		initEClass(availableCOSPatternsEClass, AvailableCOSPatterns.class, "AvailableCOSPatterns", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getAvailableCOSPatterns_Patterns(), thePatternPackage.getCOSPattern(), null, "patterns", null, 0, -1, AvailableCOSPatterns.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(compositeScreenEClass, CompositeScreen.class, "CompositeScreen", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getCompositeScreen_Domain(), ecorePackage.getEString(), "domain", null, 1, 1, CompositeScreen.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getCompositeScreen_MenuDslName(), ecorePackage.getEString(), "menuDslName", null, 0, 1, CompositeScreen.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getCompositeScreen_TopLevelCOS(), ecorePackage.getEBoolean(), "topLevelCOS", null, 0, 1, CompositeScreen.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(bespokeCompositeScreenEClass, BespokeCompositeScreen.class, "BespokeCompositeScreen", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getBespokeCompositeScreen_Name(), ecorePackage.getEString(), "name", null, 0, 1, BespokeCompositeScreen.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getBespokeCompositeScreen_Sections(), theBespokePackage.getSection(), null, "sections", null, 0, -1, BespokeCompositeScreen.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(availableTranslationLanguagesEClass, AvailableTranslationLanguages.class, "AvailableTranslationLanguages", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getAvailableTranslationLanguages_Languages(), theCommonPackage.getLanguage(), null, "languages", null, 0, -1, AvailableTranslationLanguages.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Create resource
		createResource(eNS_URI);
	}

} //T24UIPackageImpl
