/**
 */
package com.odcgroup.edge.t24ui.cos.bespoke.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.EPackageImpl;

import com.odcgroup.edge.t24ui.T24UIPackage;
import com.odcgroup.edge.t24ui.common.CommonPackage;
import com.odcgroup.edge.t24ui.common.impl.CommonPackageImpl;
import com.odcgroup.edge.t24ui.contextEnquiry.ContextEnquiryPackage;
import com.odcgroup.edge.t24ui.contextEnquiry.impl.ContextEnquiryPackageImpl;
import com.odcgroup.edge.t24ui.cos.bespoke.ApplicationPanel;
import com.odcgroup.edge.t24ui.cos.bespoke.BespokeFactory;
import com.odcgroup.edge.t24ui.cos.bespoke.BespokePackage;
import com.odcgroup.edge.t24ui.cos.bespoke.EnquiryPanel;
import com.odcgroup.edge.t24ui.cos.bespoke.Panel;
import com.odcgroup.edge.t24ui.cos.bespoke.Section;
import com.odcgroup.edge.t24ui.cos.bespoke.VersionPanel;
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
public class BespokePackageImpl extends EPackageImpl implements BespokePackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass sectionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass panelEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass versionPanelEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass enquiryPanelEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass applicationPanelEClass = null;

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
	 * @see com.odcgroup.edge.t24ui.cos.bespoke.BespokePackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private BespokePackageImpl() {
		super(eNS_URI, BespokeFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link BespokePackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static BespokePackage init() {
		if (isInited) return (BespokePackage)EPackage.Registry.INSTANCE.getEPackage(BespokePackage.eNS_URI);

		// Obtain or create and register package
		BespokePackageImpl theBespokePackage = (BespokePackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof BespokePackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new BespokePackageImpl());

		isInited = true;

		// Initialize simple dependencies
		EnquiryPackage.eINSTANCE.eClass();
		VersionDSLPackage.eINSTANCE.eClass();

		// Obtain or create and register interdependencies
		T24UIPackageImpl theT24UIPackage = (T24UIPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(T24UIPackage.eNS_URI) instanceof T24UIPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(T24UIPackage.eNS_URI) : T24UIPackage.eINSTANCE);
		CommonPackageImpl theCommonPackage = (CommonPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(CommonPackage.eNS_URI) instanceof CommonPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(CommonPackage.eNS_URI) : CommonPackage.eINSTANCE);
		ContextEnquiryPackageImpl theContextEnquiryPackage = (ContextEnquiryPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(ContextEnquiryPackage.eNS_URI) instanceof ContextEnquiryPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(ContextEnquiryPackage.eNS_URI) : ContextEnquiryPackage.eINSTANCE);
		PatternPackageImpl thePatternPackage = (PatternPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(PatternPackage.eNS_URI) instanceof PatternPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(PatternPackage.eNS_URI) : PatternPackage.eINSTANCE);

		// Create package meta-data objects
		theBespokePackage.createPackageContents();
		theT24UIPackage.createPackageContents();
		theCommonPackage.createPackageContents();
		theContextEnquiryPackage.createPackageContents();
		thePatternPackage.createPackageContents();

		// Initialize created meta-data
		theBespokePackage.initializePackageContents();
		theT24UIPackage.initializePackageContents();
		theCommonPackage.initializePackageContents();
		theContextEnquiryPackage.initializePackageContents();
		thePatternPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theBespokePackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(BespokePackage.eNS_URI, theBespokePackage);
		return theBespokePackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getSection() {
		return sectionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSection_Name() {
		return (EAttribute)sectionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getSection_Resources() {
		return (EReference)sectionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getPanel() {
		return panelEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getVersionPanel() {
		return versionPanelEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getVersionPanel_Version() {
		return (EReference)versionPanelEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getEnquiryPanel() {
		return enquiryPanelEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getEnquiryPanel_Enquiry() {
		return (EReference)enquiryPanelEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getApplicationPanel() {
		return applicationPanelEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getApplicationPanel_Application() {
		return (EReference)applicationPanelEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BespokeFactory getBespokeFactory() {
		return (BespokeFactory)getEFactoryInstance();
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
		sectionEClass = createEClass(SECTION);
		createEAttribute(sectionEClass, SECTION__NAME);
		createEReference(sectionEClass, SECTION__RESOURCES);

		panelEClass = createEClass(PANEL);

		versionPanelEClass = createEClass(VERSION_PANEL);
		createEReference(versionPanelEClass, VERSION_PANEL__VERSION);

		enquiryPanelEClass = createEClass(ENQUIRY_PANEL);
		createEReference(enquiryPanelEClass, ENQUIRY_PANEL__ENQUIRY);

		applicationPanelEClass = createEClass(APPLICATION_PANEL);
		createEReference(applicationPanelEClass, APPLICATION_PANEL__APPLICATION);
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
		VersionDSLPackage theVersionDSLPackage = (VersionDSLPackage)EPackage.Registry.INSTANCE.getEPackage(VersionDSLPackage.eNS_URI);
		EnquiryPackage theEnquiryPackage = (EnquiryPackage)EPackage.Registry.INSTANCE.getEPackage(EnquiryPackage.eNS_URI);
		MdfPackage theMdfPackage = (MdfPackage)EPackage.Registry.INSTANCE.getEPackage(MdfPackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		versionPanelEClass.getESuperTypes().add(this.getPanel());
		enquiryPanelEClass.getESuperTypes().add(this.getPanel());
		applicationPanelEClass.getESuperTypes().add(this.getPanel());

		// Initialize classes and features; add operations and parameters
		initEClass(sectionEClass, Section.class, "Section", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getSection_Name(), ecorePackage.getEString(), "name", null, 0, 1, Section.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getSection_Resources(), this.getPanel(), null, "resources", null, 0, -1, Section.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(panelEClass, Panel.class, "Panel", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(versionPanelEClass, VersionPanel.class, "VersionPanel", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getVersionPanel_Version(), theVersionDSLPackage.getVersion(), null, "version", null, 0, 1, VersionPanel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(enquiryPanelEClass, EnquiryPanel.class, "EnquiryPanel", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getEnquiryPanel_Enquiry(), theEnquiryPackage.getEnquiry(), null, "enquiry", null, 0, 1, EnquiryPanel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(applicationPanelEClass, ApplicationPanel.class, "ApplicationPanel", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getApplicationPanel_Application(), theMdfPackage.getMdfClass(), null, "application", null, 0, 1, ApplicationPanel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
	}

} //BespokePackageImpl
