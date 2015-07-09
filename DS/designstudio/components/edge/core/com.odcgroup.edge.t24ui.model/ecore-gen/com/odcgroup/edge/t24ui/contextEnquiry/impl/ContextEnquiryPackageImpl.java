/**
 */
package com.odcgroup.edge.t24ui.contextEnquiry.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.EPackageImpl;

import com.odcgroup.edge.t24ui.T24UIPackage;
import com.odcgroup.edge.t24ui.common.CommonPackage;
import com.odcgroup.edge.t24ui.common.impl.CommonPackageImpl;
import com.odcgroup.edge.t24ui.contextEnquiry.ApplicationContextEnquiry;
import com.odcgroup.edge.t24ui.contextEnquiry.AppliedEnquiry;
import com.odcgroup.edge.t24ui.contextEnquiry.AutoLaunch;
import com.odcgroup.edge.t24ui.contextEnquiry.ContextEnquiry;
import com.odcgroup.edge.t24ui.contextEnquiry.ContextEnquiryFactory;
import com.odcgroup.edge.t24ui.contextEnquiry.ContextEnquiryPackage;
import com.odcgroup.edge.t24ui.contextEnquiry.Description;
import com.odcgroup.edge.t24ui.contextEnquiry.Function;
import com.odcgroup.edge.t24ui.contextEnquiry.FunctionAutoLaunch;
import com.odcgroup.edge.t24ui.contextEnquiry.FunctionEnum;
import com.odcgroup.edge.t24ui.contextEnquiry.OnChangeAutoLaunch;
import com.odcgroup.edge.t24ui.contextEnquiry.Operand;
import com.odcgroup.edge.t24ui.contextEnquiry.SelectionCriteria;
import com.odcgroup.edge.t24ui.contextEnquiry.SortOrder;
import com.odcgroup.edge.t24ui.contextEnquiry.VersionContextEnquiry;
import com.odcgroup.edge.t24ui.cos.bespoke.BespokePackage;
import com.odcgroup.edge.t24ui.cos.bespoke.impl.BespokePackageImpl;
import com.odcgroup.edge.t24ui.cos.pattern.PatternPackage;
import com.odcgroup.edge.t24ui.cos.pattern.impl.PatternPackageImpl;
import com.odcgroup.edge.t24ui.impl.T24UIPackageImpl;
import com.odcgroup.mdf.ecore.MdfPackage;
import com.odcgroup.t24.enquiry.enquiry.EnquiryPackage;
import com.odcgroup.t24.version.versionDSL.VersionDSLPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class ContextEnquiryPackageImpl extends EPackageImpl implements ContextEnquiryPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass contextEnquiryEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass descriptionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass selectionCriteriaEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass autoLaunchEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass appliedEnquiryEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass applicationContextEnquiryEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass versionContextEnquiryEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass functionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass functionAutoLaunchEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass onChangeAutoLaunchEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum sortOrderEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum operandEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum functionEnumEEnum = null;

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
	 * @see com.odcgroup.edge.t24ui.contextEnquiry.ContextEnquiryPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private ContextEnquiryPackageImpl() {
		super(eNS_URI, ContextEnquiryFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link ContextEnquiryPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static ContextEnquiryPackage init() {
		if (isInited) return (ContextEnquiryPackage)EPackage.Registry.INSTANCE.getEPackage(ContextEnquiryPackage.eNS_URI);

		// Obtain or create and register package
		ContextEnquiryPackageImpl theContextEnquiryPackage = (ContextEnquiryPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof ContextEnquiryPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new ContextEnquiryPackageImpl());

		isInited = true;

		// Initialize simple dependencies
		EnquiryPackage.eINSTANCE.eClass();
		VersionDSLPackage.eINSTANCE.eClass();

		// Obtain or create and register interdependencies
		T24UIPackageImpl theT24UIPackage = (T24UIPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(T24UIPackage.eNS_URI) instanceof T24UIPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(T24UIPackage.eNS_URI) : T24UIPackage.eINSTANCE);
		CommonPackageImpl theCommonPackage = (CommonPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(CommonPackage.eNS_URI) instanceof CommonPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(CommonPackage.eNS_URI) : CommonPackage.eINSTANCE);
		PatternPackageImpl thePatternPackage = (PatternPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(PatternPackage.eNS_URI) instanceof PatternPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(PatternPackage.eNS_URI) : PatternPackage.eINSTANCE);
		BespokePackageImpl theBespokePackage = (BespokePackageImpl)(EPackage.Registry.INSTANCE.getEPackage(BespokePackage.eNS_URI) instanceof BespokePackageImpl ? EPackage.Registry.INSTANCE.getEPackage(BespokePackage.eNS_URI) : BespokePackage.eINSTANCE);

		// Create package meta-data objects
		theContextEnquiryPackage.createPackageContents();
		theT24UIPackage.createPackageContents();
		theCommonPackage.createPackageContents();
		thePatternPackage.createPackageContents();
		theBespokePackage.createPackageContents();

		// Initialize created meta-data
		theContextEnquiryPackage.initializePackageContents();
		theT24UIPackage.initializePackageContents();
		theCommonPackage.initializePackageContents();
		thePatternPackage.initializePackageContents();
		theBespokePackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theContextEnquiryPackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(ContextEnquiryPackage.eNS_URI, theContextEnquiryPackage);
		return theContextEnquiryPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getContextEnquiry() {
		return contextEnquiryEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getContextEnquiry_Name() {
		return (EAttribute)contextEnquiryEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getContextEnquiry_Descriptions() {
		return (EReference)contextEnquiryEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getContextEnquiry_Enquiries() {
		return (EReference)contextEnquiryEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDescription() {
		return descriptionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDescription_Lang() {
		return (EAttribute)descriptionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDescription_Text() {
		return (EAttribute)descriptionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getSelectionCriteria() {
		return selectionCriteriaEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSelectionCriteria_Name() {
		return (EAttribute)selectionCriteriaEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSelectionCriteria_AppField() {
		return (EAttribute)selectionCriteriaEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSelectionCriteria_Operand() {
		return (EAttribute)selectionCriteriaEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSelectionCriteria_SortOrder() {
		return (EAttribute)selectionCriteriaEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAutoLaunch() {
		return autoLaunchEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAppliedEnquiry() {
		return appliedEnquiryEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAppliedEnquiry_Enquiry() {
		return (EReference)appliedEnquiryEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAppliedEnquiry_SelectionFields() {
		return (EReference)appliedEnquiryEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAppliedEnquiry_Descriptions() {
		return (EReference)appliedEnquiryEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAppliedEnquiry_AutoLaunch() {
		return (EReference)appliedEnquiryEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getApplicationContextEnquiry() {
		return applicationContextEnquiryEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getApplicationContextEnquiry_AppliesToField() {
		return (EAttribute)applicationContextEnquiryEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getApplicationContextEnquiry_AppliesTo() {
		return (EReference)applicationContextEnquiryEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getVersionContextEnquiry() {
		return versionContextEnquiryEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getVersionContextEnquiry_AppliesTo() {
		return (EReference)versionContextEnquiryEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getVersionContextEnquiry_AppliesToField() {
		return (EAttribute)versionContextEnquiryEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getFunction() {
		return functionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getFunction_Function() {
		return (EAttribute)functionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getFunctionAutoLaunch() {
		return functionAutoLaunchEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getFunctionAutoLaunch_Functions() {
		return (EReference)functionAutoLaunchEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getOnChangeAutoLaunch() {
		return onChangeAutoLaunchEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getSortOrder() {
		return sortOrderEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getOperand() {
		return operandEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getFunctionEnum() {
		return functionEnumEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ContextEnquiryFactory getContextEnquiryFactory() {
		return (ContextEnquiryFactory)getEFactoryInstance();
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
		contextEnquiryEClass = createEClass(CONTEXT_ENQUIRY);
		createEAttribute(contextEnquiryEClass, CONTEXT_ENQUIRY__NAME);
		createEReference(contextEnquiryEClass, CONTEXT_ENQUIRY__DESCRIPTIONS);
		createEReference(contextEnquiryEClass, CONTEXT_ENQUIRY__ENQUIRIES);

		descriptionEClass = createEClass(DESCRIPTION);
		createEAttribute(descriptionEClass, DESCRIPTION__LANG);
		createEAttribute(descriptionEClass, DESCRIPTION__TEXT);

		selectionCriteriaEClass = createEClass(SELECTION_CRITERIA);
		createEAttribute(selectionCriteriaEClass, SELECTION_CRITERIA__NAME);
		createEAttribute(selectionCriteriaEClass, SELECTION_CRITERIA__APP_FIELD);
		createEAttribute(selectionCriteriaEClass, SELECTION_CRITERIA__OPERAND);
		createEAttribute(selectionCriteriaEClass, SELECTION_CRITERIA__SORT_ORDER);

		autoLaunchEClass = createEClass(AUTO_LAUNCH);

		appliedEnquiryEClass = createEClass(APPLIED_ENQUIRY);
		createEReference(appliedEnquiryEClass, APPLIED_ENQUIRY__ENQUIRY);
		createEReference(appliedEnquiryEClass, APPLIED_ENQUIRY__SELECTION_FIELDS);
		createEReference(appliedEnquiryEClass, APPLIED_ENQUIRY__DESCRIPTIONS);
		createEReference(appliedEnquiryEClass, APPLIED_ENQUIRY__AUTO_LAUNCH);

		applicationContextEnquiryEClass = createEClass(APPLICATION_CONTEXT_ENQUIRY);
		createEAttribute(applicationContextEnquiryEClass, APPLICATION_CONTEXT_ENQUIRY__APPLIES_TO_FIELD);
		createEReference(applicationContextEnquiryEClass, APPLICATION_CONTEXT_ENQUIRY__APPLIES_TO);

		versionContextEnquiryEClass = createEClass(VERSION_CONTEXT_ENQUIRY);
		createEReference(versionContextEnquiryEClass, VERSION_CONTEXT_ENQUIRY__APPLIES_TO);
		createEAttribute(versionContextEnquiryEClass, VERSION_CONTEXT_ENQUIRY__APPLIES_TO_FIELD);

		functionEClass = createEClass(FUNCTION);
		createEAttribute(functionEClass, FUNCTION__FUNCTION);

		functionAutoLaunchEClass = createEClass(FUNCTION_AUTO_LAUNCH);
		createEReference(functionAutoLaunchEClass, FUNCTION_AUTO_LAUNCH__FUNCTIONS);

		onChangeAutoLaunchEClass = createEClass(ON_CHANGE_AUTO_LAUNCH);

		// Create enums
		sortOrderEEnum = createEEnum(SORT_ORDER);
		operandEEnum = createEEnum(OPERAND);
		functionEnumEEnum = createEEnum(FUNCTION_ENUM);
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
		EnquiryPackage theEnquiryPackage = (EnquiryPackage)EPackage.Registry.INSTANCE.getEPackage(EnquiryPackage.eNS_URI);
		MdfPackage theMdfPackage = (MdfPackage)EPackage.Registry.INSTANCE.getEPackage(MdfPackage.eNS_URI);
		VersionDSLPackage theVersionDSLPackage = (VersionDSLPackage)EPackage.Registry.INSTANCE.getEPackage(VersionDSLPackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		applicationContextEnquiryEClass.getESuperTypes().add(this.getContextEnquiry());
		versionContextEnquiryEClass.getESuperTypes().add(this.getContextEnquiry());
		functionAutoLaunchEClass.getESuperTypes().add(this.getAutoLaunch());
		onChangeAutoLaunchEClass.getESuperTypes().add(this.getAutoLaunch());

		// Initialize classes and features; add operations and parameters
		initEClass(contextEnquiryEClass, ContextEnquiry.class, "ContextEnquiry", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getContextEnquiry_Name(), ecorePackage.getEString(), "name", null, 0, 1, ContextEnquiry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getContextEnquiry_Descriptions(), this.getDescription(), null, "descriptions", null, 0, -1, ContextEnquiry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getContextEnquiry_Enquiries(), this.getAppliedEnquiry(), null, "enquiries", null, 0, -1, ContextEnquiry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(descriptionEClass, Description.class, "Description", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getDescription_Lang(), ecorePackage.getEString(), "lang", null, 0, 1, Description.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDescription_Text(), ecorePackage.getEString(), "text", null, 0, 1, Description.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(selectionCriteriaEClass, SelectionCriteria.class, "SelectionCriteria", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getSelectionCriteria_Name(), ecorePackage.getEString(), "name", null, 0, 1, SelectionCriteria.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSelectionCriteria_AppField(), ecorePackage.getEString(), "appField", null, 0, 1, SelectionCriteria.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSelectionCriteria_Operand(), this.getOperand(), "operand", null, 0, 1, SelectionCriteria.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSelectionCriteria_SortOrder(), this.getSortOrder(), "sortOrder", null, 0, 1, SelectionCriteria.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(autoLaunchEClass, AutoLaunch.class, "AutoLaunch", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(appliedEnquiryEClass, AppliedEnquiry.class, "AppliedEnquiry", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getAppliedEnquiry_Enquiry(), theEnquiryPackage.getEnquiry(), null, "enquiry", null, 0, 1, AppliedEnquiry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAppliedEnquiry_SelectionFields(), this.getSelectionCriteria(), null, "selectionFields", null, 0, -1, AppliedEnquiry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAppliedEnquiry_Descriptions(), this.getDescription(), null, "descriptions", null, 0, -1, AppliedEnquiry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAppliedEnquiry_AutoLaunch(), this.getAutoLaunch(), null, "autoLaunch", null, 0, 1, AppliedEnquiry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(applicationContextEnquiryEClass, ApplicationContextEnquiry.class, "ApplicationContextEnquiry", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getApplicationContextEnquiry_AppliesToField(), ecorePackage.getEString(), "appliesToField", null, 0, 1, ApplicationContextEnquiry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getApplicationContextEnquiry_AppliesTo(), theMdfPackage.getMdfClass(), null, "appliesTo", null, 0, 1, ApplicationContextEnquiry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(versionContextEnquiryEClass, VersionContextEnquiry.class, "VersionContextEnquiry", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getVersionContextEnquiry_AppliesTo(), theVersionDSLPackage.getVersion(), null, "appliesTo", null, 0, 1, VersionContextEnquiry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getVersionContextEnquiry_AppliesToField(), ecorePackage.getEString(), "appliesToField", null, 0, 1, VersionContextEnquiry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(functionEClass, Function.class, "Function", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getFunction_Function(), this.getFunctionEnum(), "function", null, 0, 1, Function.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(functionAutoLaunchEClass, FunctionAutoLaunch.class, "FunctionAutoLaunch", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getFunctionAutoLaunch_Functions(), this.getFunction(), null, "functions", null, 0, -1, FunctionAutoLaunch.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(onChangeAutoLaunchEClass, OnChangeAutoLaunch.class, "OnChangeAutoLaunch", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		// Initialize enums and add enum literals
		initEEnum(sortOrderEEnum, SortOrder.class, "SortOrder");
		addEEnumLiteral(sortOrderEEnum, SortOrder.ASCENDING);
		addEEnumLiteral(sortOrderEEnum, SortOrder.DESCENDING);

		initEEnum(operandEEnum, Operand.class, "Operand");
		addEEnumLiteral(operandEEnum, Operand.NONE);
		addEEnumLiteral(operandEEnum, Operand.EQUALS);
		addEEnumLiteral(operandEEnum, Operand.NOT_EQUALS);
		addEEnumLiteral(operandEEnum, Operand.GREATER);
		addEEnumLiteral(operandEEnum, Operand.GREATER_OR_EQUALS);
		addEEnumLiteral(operandEEnum, Operand.LESS_THAN);
		addEEnumLiteral(operandEEnum, Operand.LESS_OR_EQUALS);
		addEEnumLiteral(operandEEnum, Operand.MATCHES);
		addEEnumLiteral(operandEEnum, Operand.NOT_MATCHES);
		addEEnumLiteral(operandEEnum, Operand.BETWEEN);
		addEEnumLiteral(operandEEnum, Operand.NOT_BETWEEN);
		addEEnumLiteral(operandEEnum, Operand.CONTAINS);
		addEEnumLiteral(operandEEnum, Operand.DOES_NOT_CONTAIN);
		addEEnumLiteral(operandEEnum, Operand.BEGINS_WITH);
		addEEnumLiteral(operandEEnum, Operand.DOES_NOT_BEGIN_WITH);
		addEEnumLiteral(operandEEnum, Operand.ENDS_WITH);
		addEEnumLiteral(operandEEnum, Operand.DOES_NOT_END_WITH);
		addEEnumLiteral(operandEEnum, Operand.SOUNDS_LIKE);

		initEEnum(functionEnumEEnum, FunctionEnum.class, "FunctionEnum");
		addEEnumLiteral(functionEnumEEnum, FunctionEnum.AUTHORISE);
		addEEnumLiteral(functionEnumEEnum, FunctionEnum.SECOND_LEVEL_AUTHORISE);
		addEEnumLiteral(functionEnumEEnum, FunctionEnum.COPY);
		addEEnumLiteral(functionEnumEEnum, FunctionEnum.DELETE);
		addEEnumLiteral(functionEnumEEnum, FunctionEnum.EXCEPTION);
		addEEnumLiteral(functionEnumEEnum, FunctionEnum.HISTORY_RESTORE);
		addEEnumLiteral(functionEnumEEnum, FunctionEnum.INPUT);
		addEEnumLiteral(functionEnumEEnum, FunctionEnum.LIST);
		addEEnumLiteral(functionEnumEEnum, FunctionEnum.PRINT);
		addEEnumLiteral(functionEnumEEnum, FunctionEnum.AUDIT);
		addEEnumLiteral(functionEnumEEnum, FunctionEnum.REVERSE);
		addEEnumLiteral(functionEnumEEnum, FunctionEnum.SEE);
		addEEnumLiteral(functionEnumEEnum, FunctionEnum.VERIFY);
		addEEnumLiteral(functionEnumEEnum, FunctionEnum.ON_HOLD);
	}

} //ContextEnquiryPackageImpl
