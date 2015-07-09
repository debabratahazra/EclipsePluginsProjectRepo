/**
 */
package com.odcgroup.edge.t24ui.cos.pattern.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.EPackageImpl;

import com.odcgroup.edge.t24ui.T24UIPackage;
import com.odcgroup.edge.t24ui.common.CommonPackage;
import com.odcgroup.edge.t24ui.common.impl.CommonPackageImpl;
import com.odcgroup.edge.t24ui.contextEnquiry.ContextEnquiryPackage;
import com.odcgroup.edge.t24ui.contextEnquiry.impl.ContextEnquiryPackageImpl;
import com.odcgroup.edge.t24ui.cos.bespoke.BespokePackage;
import com.odcgroup.edge.t24ui.cos.bespoke.impl.BespokePackageImpl;
import com.odcgroup.edge.t24ui.cos.pattern.AbstractCOS;
import com.odcgroup.edge.t24ui.cos.pattern.COSPanel;
import com.odcgroup.edge.t24ui.cos.pattern.COSPattern;
import com.odcgroup.edge.t24ui.cos.pattern.COSPatternContainer;
import com.odcgroup.edge.t24ui.cos.pattern.ChildResourceSpec;
import com.odcgroup.edge.t24ui.cos.pattern.InitialBespokeCOS;
import com.odcgroup.edge.t24ui.cos.pattern.InitialCOS;
import com.odcgroup.edge.t24ui.cos.pattern.InitialEnquiry;
import com.odcgroup.edge.t24ui.cos.pattern.InitialPanelContentSpec;
import com.odcgroup.edge.t24ui.cos.pattern.InitialScreen;
import com.odcgroup.edge.t24ui.cos.pattern.InitialURL;
import com.odcgroup.edge.t24ui.cos.pattern.InlineInitialCOS;
import com.odcgroup.edge.t24ui.cos.pattern.MultiComponentPanel;
import com.odcgroup.edge.t24ui.cos.pattern.PanelOverflowOption;
import com.odcgroup.edge.t24ui.cos.pattern.PanelSeparatorOption;
import com.odcgroup.edge.t24ui.cos.pattern.PatternFactory;
import com.odcgroup.edge.t24ui.cos.pattern.PatternPackage;
import com.odcgroup.edge.t24ui.cos.pattern.SingleComponentPanel;
import com.odcgroup.edge.t24ui.impl.T24UIPackageImpl;
import com.odcgroup.t24.enquiry.enquiry.EnquiryPackage;
import com.odcgroup.t24.version.versionDSL.VersionDSLPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class PatternPackageImpl extends EPackageImpl implements PatternPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass cosPanelEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass singleComponentPanelEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass multiComponentPanelEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass initialPanelContentSpecEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass initialCOSEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass inlineInitialCOSEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass initialBespokeCOSEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass initialEnquiryEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass initialScreenEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass cosPatternEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass childResourceSpecEClass = null;

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    private EClass initialURLEClass = null;

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum panelSeparatorOptionEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum panelOverflowOptionEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass abstractCOSEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass cosPatternContainerEClass = null;

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
	 * @see com.odcgroup.edge.t24ui.cos.pattern.PatternPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private PatternPackageImpl() {
		super(eNS_URI, PatternFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link PatternPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static PatternPackage init() {
		if (isInited) return (PatternPackage)EPackage.Registry.INSTANCE.getEPackage(PatternPackage.eNS_URI);

		// Obtain or create and register package
		PatternPackageImpl thePatternPackage = (PatternPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof PatternPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new PatternPackageImpl());

		isInited = true;

		// Initialize simple dependencies
		EnquiryPackage.eINSTANCE.eClass();
		VersionDSLPackage.eINSTANCE.eClass();

		// Obtain or create and register interdependencies
		T24UIPackageImpl theT24UIPackage = (T24UIPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(T24UIPackage.eNS_URI) instanceof T24UIPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(T24UIPackage.eNS_URI) : T24UIPackage.eINSTANCE);
		CommonPackageImpl theCommonPackage = (CommonPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(CommonPackage.eNS_URI) instanceof CommonPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(CommonPackage.eNS_URI) : CommonPackage.eINSTANCE);
		ContextEnquiryPackageImpl theContextEnquiryPackage = (ContextEnquiryPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(ContextEnquiryPackage.eNS_URI) instanceof ContextEnquiryPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(ContextEnquiryPackage.eNS_URI) : ContextEnquiryPackage.eINSTANCE);
		BespokePackageImpl theBespokePackage = (BespokePackageImpl)(EPackage.Registry.INSTANCE.getEPackage(BespokePackage.eNS_URI) instanceof BespokePackageImpl ? EPackage.Registry.INSTANCE.getEPackage(BespokePackage.eNS_URI) : BespokePackage.eINSTANCE);

		// Create package meta-data objects
		thePatternPackage.createPackageContents();
		theT24UIPackage.createPackageContents();
		theCommonPackage.createPackageContents();
		theContextEnquiryPackage.createPackageContents();
		theBespokePackage.createPackageContents();

		// Initialize created meta-data
		thePatternPackage.initializePackageContents();
		theT24UIPackage.initializePackageContents();
		theCommonPackage.initializePackageContents();
		theContextEnquiryPackage.initializePackageContents();
		theBespokePackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		thePatternPackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(PatternPackage.eNS_URI, thePatternPackage);
		return thePatternPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
    public EClass getCOSPanel() {
		return cosPanelEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
    public EAttribute getCOSPanel_Name() {
		return (EAttribute)cosPanelEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
    public EAttribute getCOSPanel_HostableContentAll() {
		return (EAttribute)cosPanelEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
    public EAttribute getCOSPanel_HostableBespokeCOSContentAll() {
		return (EAttribute)cosPanelEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
    public EReference getCOSPanel_HostableBespokeCOSContent() {
		return (EReference)cosPanelEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
    public EAttribute getCOSPanel_HostableCOSContentAll() {
		return (EAttribute)cosPanelEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
    public EReference getCOSPanel_HostableCOSContent() {
		return (EReference)cosPanelEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
    public EAttribute getCOSPanel_HostableEnquiryContentAll() {
		return (EAttribute)cosPanelEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
    public EReference getCOSPanel_HostableEnquiryContent() {
		return (EReference)cosPanelEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
    public EAttribute getCOSPanel_HostableScreenContentAll() {
		return (EAttribute)cosPanelEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
    public EReference getCOSPanel_HostableScreenContent() {
		return (EReference)cosPanelEClass.getEStructuralFeatures().get(9);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
    public EReference getCOSPanel_Title() {
		return (EReference)cosPanelEClass.getEStructuralFeatures().get(10);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
    public EAttribute getCOSPanel_BackgroundColor() {
		return (EAttribute)cosPanelEClass.getEStructuralFeatures().get(11);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
    public EAttribute getCOSPanel_Height() {
		return (EAttribute)cosPanelEClass.getEStructuralFeatures().get(12);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
    public EAttribute getCOSPanel_Width() {
		return (EAttribute)cosPanelEClass.getEStructuralFeatures().get(13);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
    public EAttribute getCOSPanel_HorizontalOverflowOption() {
		return (EAttribute)cosPanelEClass.getEStructuralFeatures().get(14);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
    public EAttribute getCOSPanel_VerticalOverflowOption() {
		return (EAttribute)cosPanelEClass.getEStructuralFeatures().get(15);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
    public EClass getSingleComponentPanel() {
		return singleComponentPanelEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
    public EReference getSingleComponentPanel_InitialContent() {
		return (EReference)singleComponentPanelEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
    public EClass getMultiComponentPanel() {
		return multiComponentPanelEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
    public EReference getMultiComponentPanel_ChildPanels() {
		return (EReference)multiComponentPanelEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
    public EClass getInitialPanelContentSpec() {
		return initialPanelContentSpecEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
    public EClass getInitialCOS() {
		return initialCOSEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
    public EReference getInitialCOS_CompositeScreen() {
		return (EReference)initialCOSEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
    public EClass getInlineInitialCOS() {
		return inlineInitialCOSEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
    public EClass getInitialBespokeCOS() {
		return initialBespokeCOSEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
    public EReference getInitialBespokeCOS_CompositeScreen() {
		return (EReference)initialBespokeCOSEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
    public EClass getInitialEnquiry() {
		return initialEnquiryEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
    public EReference getInitialEnquiry_Enquiry() {
		return (EReference)initialEnquiryEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
    public EClass getInitialScreen() {
		return initialScreenEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
    public EReference getInitialScreen_Screen() {
		return (EReference)initialScreenEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
    public EClass getCOSPattern() {
		return cosPatternEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
    public EAttribute getCOSPattern_Name() {
		return (EAttribute)cosPatternEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
    public EAttribute getCOSPattern_PatternSpecificAttributeNames() {
		return (EAttribute)cosPatternEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
    public EClass getChildResourceSpec() {
		return childResourceSpecEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
    public EAttribute getChildResourceSpec_Parameters() {
		return (EAttribute)childResourceSpecEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EClass getInitialURL() {
		return initialURLEClass;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EAttribute getInitialURL_Url() {
		return (EAttribute)initialURLEClass.getEStructuralFeatures().get(0);
	}

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
    public EEnum getPanelSeparatorOption() {
		return panelSeparatorOptionEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
    public EEnum getPanelOverflowOption() {
		return panelOverflowOptionEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
    public EClass getAbstractCOS() {
		return abstractCOSEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
    public EAttribute getAbstractCOS_Name() {
		return (EAttribute)abstractCOSEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
    public EReference getAbstractCOS_Panels() {
		return (EReference)abstractCOSEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
    public EReference getAbstractCOS_Title() {
		return (EReference)abstractCOSEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
    public EClass getCOSPatternContainer() {
		return cosPatternContainerEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
    public EReference getCOSPatternContainer_Pattern() {
		return (EReference)cosPatternContainerEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
    public EAttribute getCOSPatternContainer_PanelSeparatorOption() {
		return (EAttribute)cosPatternContainerEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
    public EAttribute getCOSPatternContainer_AccordionPatternMultiExpandable() {
		return (EAttribute)cosPatternContainerEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
    public PatternFactory getPatternFactory() {
		return (PatternFactory)getEFactoryInstance();
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
		cosPanelEClass = createEClass(COS_PANEL);
		createEAttribute(cosPanelEClass, COS_PANEL__NAME);
		createEAttribute(cosPanelEClass, COS_PANEL__HOSTABLE_CONTENT_ALL);
		createEAttribute(cosPanelEClass, COS_PANEL__HOSTABLE_BESPOKE_COS_CONTENT_ALL);
		createEReference(cosPanelEClass, COS_PANEL__HOSTABLE_BESPOKE_COS_CONTENT);
		createEAttribute(cosPanelEClass, COS_PANEL__HOSTABLE_COS_CONTENT_ALL);
		createEReference(cosPanelEClass, COS_PANEL__HOSTABLE_COS_CONTENT);
		createEAttribute(cosPanelEClass, COS_PANEL__HOSTABLE_ENQUIRY_CONTENT_ALL);
		createEReference(cosPanelEClass, COS_PANEL__HOSTABLE_ENQUIRY_CONTENT);
		createEAttribute(cosPanelEClass, COS_PANEL__HOSTABLE_SCREEN_CONTENT_ALL);
		createEReference(cosPanelEClass, COS_PANEL__HOSTABLE_SCREEN_CONTENT);
		createEReference(cosPanelEClass, COS_PANEL__TITLE);
		createEAttribute(cosPanelEClass, COS_PANEL__BACKGROUND_COLOR);
		createEAttribute(cosPanelEClass, COS_PANEL__HEIGHT);
		createEAttribute(cosPanelEClass, COS_PANEL__WIDTH);
		createEAttribute(cosPanelEClass, COS_PANEL__HORIZONTAL_OVERFLOW_OPTION);
		createEAttribute(cosPanelEClass, COS_PANEL__VERTICAL_OVERFLOW_OPTION);

		singleComponentPanelEClass = createEClass(SINGLE_COMPONENT_PANEL);
		createEReference(singleComponentPanelEClass, SINGLE_COMPONENT_PANEL__INITIAL_CONTENT);

		multiComponentPanelEClass = createEClass(MULTI_COMPONENT_PANEL);
		createEReference(multiComponentPanelEClass, MULTI_COMPONENT_PANEL__CHILD_PANELS);

		initialPanelContentSpecEClass = createEClass(INITIAL_PANEL_CONTENT_SPEC);

		initialCOSEClass = createEClass(INITIAL_COS);
		createEReference(initialCOSEClass, INITIAL_COS__COMPOSITE_SCREEN);

		inlineInitialCOSEClass = createEClass(INLINE_INITIAL_COS);

		initialBespokeCOSEClass = createEClass(INITIAL_BESPOKE_COS);
		createEReference(initialBespokeCOSEClass, INITIAL_BESPOKE_COS__COMPOSITE_SCREEN);

		initialEnquiryEClass = createEClass(INITIAL_ENQUIRY);
		createEReference(initialEnquiryEClass, INITIAL_ENQUIRY__ENQUIRY);

		initialScreenEClass = createEClass(INITIAL_SCREEN);
		createEReference(initialScreenEClass, INITIAL_SCREEN__SCREEN);

		abstractCOSEClass = createEClass(ABSTRACT_COS);
		createEAttribute(abstractCOSEClass, ABSTRACT_COS__NAME);
		createEReference(abstractCOSEClass, ABSTRACT_COS__PANELS);
		createEReference(abstractCOSEClass, ABSTRACT_COS__TITLE);

		cosPatternContainerEClass = createEClass(COS_PATTERN_CONTAINER);
		createEReference(cosPatternContainerEClass, COS_PATTERN_CONTAINER__PATTERN);
		createEAttribute(cosPatternContainerEClass, COS_PATTERN_CONTAINER__PANEL_SEPARATOR_OPTION);
		createEAttribute(cosPatternContainerEClass, COS_PATTERN_CONTAINER__ACCORDION_PATTERN_MULTI_EXPANDABLE);

		cosPatternEClass = createEClass(COS_PATTERN);
		createEAttribute(cosPatternEClass, COS_PATTERN__NAME);
		createEAttribute(cosPatternEClass, COS_PATTERN__PATTERN_SPECIFIC_ATTRIBUTE_NAMES);

		childResourceSpecEClass = createEClass(CHILD_RESOURCE_SPEC);
		createEAttribute(childResourceSpecEClass, CHILD_RESOURCE_SPEC__PARAMETERS);

		initialURLEClass = createEClass(INITIAL_URL);
		createEAttribute(initialURLEClass, INITIAL_URL__URL);

		// Create enums
		panelSeparatorOptionEEnum = createEEnum(PANEL_SEPARATOR_OPTION);
		panelOverflowOptionEEnum = createEEnum(PANEL_OVERFLOW_OPTION);
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
		T24UIPackage theT24UIPackage = (T24UIPackage)EPackage.Registry.INSTANCE.getEPackage(T24UIPackage.eNS_URI);
		EnquiryPackage theEnquiryPackage = (EnquiryPackage)EPackage.Registry.INSTANCE.getEPackage(EnquiryPackage.eNS_URI);
		VersionDSLPackage theVersionDSLPackage = (VersionDSLPackage)EPackage.Registry.INSTANCE.getEPackage(VersionDSLPackage.eNS_URI);
		CommonPackage theCommonPackage = (CommonPackage)EPackage.Registry.INSTANCE.getEPackage(CommonPackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		singleComponentPanelEClass.getESuperTypes().add(this.getCOSPanel());
		multiComponentPanelEClass.getESuperTypes().add(this.getChildResourceSpec());
		multiComponentPanelEClass.getESuperTypes().add(this.getCOSPanel());
		multiComponentPanelEClass.getESuperTypes().add(this.getCOSPatternContainer());
		initialCOSEClass.getESuperTypes().add(this.getChildResourceSpec());
		inlineInitialCOSEClass.getESuperTypes().add(this.getAbstractCOS());
		inlineInitialCOSEClass.getESuperTypes().add(this.getChildResourceSpec());
		initialBespokeCOSEClass.getESuperTypes().add(this.getChildResourceSpec());
		initialEnquiryEClass.getESuperTypes().add(this.getChildResourceSpec());
		initialScreenEClass.getESuperTypes().add(this.getChildResourceSpec());
		abstractCOSEClass.getESuperTypes().add(this.getCOSPatternContainer());
		childResourceSpecEClass.getESuperTypes().add(this.getInitialPanelContentSpec());
		initialURLEClass.getESuperTypes().add(this.getInitialPanelContentSpec());

		// Initialize classes and features; add operations and parameters
		initEClass(cosPanelEClass, COSPanel.class, "COSPanel", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getCOSPanel_Name(), ecorePackage.getEString(), "name", null, 1, 1, COSPanel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getCOSPanel_HostableContentAll(), ecorePackage.getEBoolean(), "hostableContentAll", null, 0, 1, COSPanel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getCOSPanel_HostableBespokeCOSContentAll(), ecorePackage.getEBoolean(), "hostableBespokeCOSContentAll", null, 0, 1, COSPanel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getCOSPanel_HostableBespokeCOSContent(), theT24UIPackage.getBespokeCompositeScreen(), null, "hostableBespokeCOSContent", null, 0, -1, COSPanel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getCOSPanel_HostableCOSContentAll(), ecorePackage.getEBoolean(), "hostableCOSContentAll", null, 0, 1, COSPanel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getCOSPanel_HostableCOSContent(), theT24UIPackage.getCompositeScreen(), null, "hostableCOSContent", null, 0, -1, COSPanel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getCOSPanel_HostableEnquiryContentAll(), ecorePackage.getEBoolean(), "hostableEnquiryContentAll", null, 0, 1, COSPanel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getCOSPanel_HostableEnquiryContent(), theEnquiryPackage.getEnquiry(), null, "hostableEnquiryContent", null, 0, -1, COSPanel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getCOSPanel_HostableScreenContentAll(), ecorePackage.getEBoolean(), "hostableScreenContentAll", null, 0, 1, COSPanel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getCOSPanel_HostableScreenContent(), theVersionDSLPackage.getVersion(), null, "hostableScreenContent", null, 0, -1, COSPanel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getCOSPanel_Title(), theCommonPackage.getTranslation(), null, "title", null, 0, -1, COSPanel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getCOSPanel_BackgroundColor(), ecorePackage.getEString(), "backgroundColor", null, 0, 1, COSPanel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getCOSPanel_Height(), ecorePackage.getEString(), "height", "auto", 0, 1, COSPanel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getCOSPanel_Width(), ecorePackage.getEString(), "width", "auto", 0, 1, COSPanel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getCOSPanel_HorizontalOverflowOption(), this.getPanelOverflowOption(), "horizontalOverflowOption", null, 0, 1, COSPanel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getCOSPanel_VerticalOverflowOption(), this.getPanelOverflowOption(), "verticalOverflowOption", null, 0, 1, COSPanel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(singleComponentPanelEClass, SingleComponentPanel.class, "SingleComponentPanel", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getSingleComponentPanel_InitialContent(), this.getInitialPanelContentSpec(), null, "initialContent", null, 0, 1, SingleComponentPanel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(multiComponentPanelEClass, MultiComponentPanel.class, "MultiComponentPanel", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getMultiComponentPanel_ChildPanels(), this.getCOSPanel(), null, "childPanels", null, 1, -1, MultiComponentPanel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(initialPanelContentSpecEClass, InitialPanelContentSpec.class, "InitialPanelContentSpec", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(initialCOSEClass, InitialCOS.class, "InitialCOS", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getInitialCOS_CompositeScreen(), theT24UIPackage.getCompositeScreen(), null, "compositeScreen", null, 0, 1, InitialCOS.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(inlineInitialCOSEClass, InlineInitialCOS.class, "InlineInitialCOS", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(initialBespokeCOSEClass, InitialBespokeCOS.class, "InitialBespokeCOS", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getInitialBespokeCOS_CompositeScreen(), theT24UIPackage.getBespokeCompositeScreen(), null, "compositeScreen", null, 0, 1, InitialBespokeCOS.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(initialEnquiryEClass, InitialEnquiry.class, "InitialEnquiry", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getInitialEnquiry_Enquiry(), theEnquiryPackage.getEnquiry(), null, "enquiry", null, 0, 1, InitialEnquiry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(initialScreenEClass, InitialScreen.class, "InitialScreen", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getInitialScreen_Screen(), theVersionDSLPackage.getVersion(), null, "screen", null, 0, 1, InitialScreen.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(abstractCOSEClass, AbstractCOS.class, "AbstractCOS", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getAbstractCOS_Name(), ecorePackage.getEString(), "name", null, 1, 1, AbstractCOS.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAbstractCOS_Panels(), this.getCOSPanel(), null, "panels", null, 1, -1, AbstractCOS.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAbstractCOS_Title(), theCommonPackage.getTranslation(), null, "title", null, 0, -1, AbstractCOS.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(cosPatternContainerEClass, COSPatternContainer.class, "COSPatternContainer", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getCOSPatternContainer_Pattern(), this.getCOSPattern(), null, "pattern", null, 1, 1, COSPatternContainer.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getCOSPatternContainer_PanelSeparatorOption(), this.getPanelSeparatorOption(), "panelSeparatorOption", null, 0, 1, COSPatternContainer.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getCOSPatternContainer_AccordionPatternMultiExpandable(), ecorePackage.getEBoolean(), "accordionPatternMultiExpandable", null, 0, 1, COSPatternContainer.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(cosPatternEClass, COSPattern.class, "COSPattern", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getCOSPattern_Name(), ecorePackage.getEString(), "name", null, 1, 1, COSPattern.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getCOSPattern_PatternSpecificAttributeNames(), ecorePackage.getEString(), "patternSpecificAttributeNames", null, 0, -1, COSPattern.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(childResourceSpecEClass, ChildResourceSpec.class, "ChildResourceSpec", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getChildResourceSpec_Parameters(), ecorePackage.getEString(), "parameters", null, 0, 1, ChildResourceSpec.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(initialURLEClass, InitialURL.class, "InitialURL", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getInitialURL_Url(), ecorePackage.getEString(), "url", null, 1, 1, InitialURL.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Initialize enums and add enum literals
		initEEnum(panelSeparatorOptionEEnum, PanelSeparatorOption.class, "PanelSeparatorOption");
		addEEnumLiteral(panelSeparatorOptionEEnum, PanelSeparatorOption.NO_SEPARATORS);
		addEEnumLiteral(panelSeparatorOptionEEnum, PanelSeparatorOption.FIXED_POSITION_SEPARATORS);
		addEEnumLiteral(panelSeparatorOptionEEnum, PanelSeparatorOption.PANEL_RESIZE_BAR_SEPARATORS);

		initEEnum(panelOverflowOptionEEnum, PanelOverflowOption.class, "PanelOverflowOption");
		addEEnumLiteral(panelOverflowOptionEEnum, PanelOverflowOption.HIDE_OVERFLOW);
		addEEnumLiteral(panelOverflowOptionEEnum, PanelOverflowOption.SHOW_SCROLLBAR_ALWAYS);
		addEEnumLiteral(panelOverflowOptionEEnum, PanelOverflowOption.SHOW_SCROLLBAR_IF_NEEDED);
	}

} //PatternPackageImpl
