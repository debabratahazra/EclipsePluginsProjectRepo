/**
 */
package com.odcgroup.edge.t24ui.cos.pattern;

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
 * @see com.odcgroup.edge.t24ui.cos.pattern.PatternFactory
 * @model kind="package"
 * @generated
 */
public interface PatternPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "pattern";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.temenos.com/DS/t24ui/cos/pattern";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "pattern";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	PatternPackage eINSTANCE = com.odcgroup.edge.t24ui.cos.pattern.impl.PatternPackageImpl.init();

	/**
	 * The meta object id for the '{@link com.odcgroup.edge.t24ui.cos.pattern.impl.COSPanelImpl <em>COS Panel</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.odcgroup.edge.t24ui.cos.pattern.impl.COSPanelImpl
	 * @see com.odcgroup.edge.t24ui.cos.pattern.impl.PatternPackageImpl#getCOSPanel()
	 * @generated
	 */
	int COS_PANEL = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COS_PANEL__NAME = 0;

	/**
	 * The feature id for the '<em><b>Hostable Content All</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COS_PANEL__HOSTABLE_CONTENT_ALL = 1;

	/**
	 * The feature id for the '<em><b>Hostable Bespoke COS Content All</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COS_PANEL__HOSTABLE_BESPOKE_COS_CONTENT_ALL = 2;

	/**
	 * The feature id for the '<em><b>Hostable Bespoke COS Content</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COS_PANEL__HOSTABLE_BESPOKE_COS_CONTENT = 3;

	/**
	 * The feature id for the '<em><b>Hostable COS Content All</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COS_PANEL__HOSTABLE_COS_CONTENT_ALL = 4;

	/**
	 * The feature id for the '<em><b>Hostable COS Content</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COS_PANEL__HOSTABLE_COS_CONTENT = 5;

	/**
	 * The feature id for the '<em><b>Hostable Enquiry Content All</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COS_PANEL__HOSTABLE_ENQUIRY_CONTENT_ALL = 6;

	/**
	 * The feature id for the '<em><b>Hostable Enquiry Content</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COS_PANEL__HOSTABLE_ENQUIRY_CONTENT = 7;

	/**
	 * The feature id for the '<em><b>Hostable Screen Content All</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COS_PANEL__HOSTABLE_SCREEN_CONTENT_ALL = 8;

	/**
	 * The feature id for the '<em><b>Hostable Screen Content</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COS_PANEL__HOSTABLE_SCREEN_CONTENT = 9;

	/**
	 * The feature id for the '<em><b>Title</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COS_PANEL__TITLE = 10;

	/**
	 * The feature id for the '<em><b>Background Color</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COS_PANEL__BACKGROUND_COLOR = 11;

	/**
	 * The feature id for the '<em><b>Height</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COS_PANEL__HEIGHT = 12;

	/**
	 * The feature id for the '<em><b>Width</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COS_PANEL__WIDTH = 13;

	/**
	 * The feature id for the '<em><b>Horizontal Overflow Option</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COS_PANEL__HORIZONTAL_OVERFLOW_OPTION = 14;

	/**
	 * The feature id for the '<em><b>Vertical Overflow Option</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COS_PANEL__VERTICAL_OVERFLOW_OPTION = 15;

	/**
	 * The number of structural features of the '<em>COS Panel</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COS_PANEL_FEATURE_COUNT = 16;

	/**
	 * The meta object id for the '{@link com.odcgroup.edge.t24ui.cos.pattern.impl.COSPatternContainerImpl <em>COS Pattern Container</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.odcgroup.edge.t24ui.cos.pattern.impl.COSPatternContainerImpl
	 * @see com.odcgroup.edge.t24ui.cos.pattern.impl.PatternPackageImpl#getCOSPatternContainer()
	 * @generated
	 */
	int COS_PATTERN_CONTAINER = 10;

	/**
	 * The meta object id for the '{@link com.odcgroup.edge.t24ui.cos.pattern.impl.AbstractCOSImpl <em>Abstract COS</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.odcgroup.edge.t24ui.cos.pattern.impl.AbstractCOSImpl
	 * @see com.odcgroup.edge.t24ui.cos.pattern.impl.PatternPackageImpl#getAbstractCOS()
	 * @generated
	 */
	int ABSTRACT_COS = 9;

	/**
	 * The meta object id for the '{@link com.odcgroup.edge.t24ui.cos.pattern.impl.SingleComponentPanelImpl <em>Single Component Panel</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.odcgroup.edge.t24ui.cos.pattern.impl.SingleComponentPanelImpl
	 * @see com.odcgroup.edge.t24ui.cos.pattern.impl.PatternPackageImpl#getSingleComponentPanel()
	 * @generated
	 */
	int SINGLE_COMPONENT_PANEL = 1;

	/**
	 * The meta object id for the '{@link com.odcgroup.edge.t24ui.cos.pattern.impl.MultiComponentPanelImpl <em>Multi Component Panel</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.odcgroup.edge.t24ui.cos.pattern.impl.MultiComponentPanelImpl
	 * @see com.odcgroup.edge.t24ui.cos.pattern.impl.PatternPackageImpl#getMultiComponentPanel()
	 * @generated
	 */
	int MULTI_COMPONENT_PANEL = 2;

	/**
	 * The meta object id for the '{@link com.odcgroup.edge.t24ui.cos.pattern.impl.InitialPanelContentSpecImpl <em>Initial Panel Content Spec</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.odcgroup.edge.t24ui.cos.pattern.impl.InitialPanelContentSpecImpl
	 * @see com.odcgroup.edge.t24ui.cos.pattern.impl.PatternPackageImpl#getInitialPanelContentSpec()
	 * @generated
	 */
	int INITIAL_PANEL_CONTENT_SPEC = 3;

	/**
	 * The meta object id for the '{@link com.odcgroup.edge.t24ui.cos.pattern.impl.InlineInitialCOSImpl <em>Inline Initial COS</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.odcgroup.edge.t24ui.cos.pattern.impl.InlineInitialCOSImpl
	 * @see com.odcgroup.edge.t24ui.cos.pattern.impl.PatternPackageImpl#getInlineInitialCOS()
	 * @generated
	 */
	int INLINE_INITIAL_COS = 5;

	/**
	 * The meta object id for the '{@link com.odcgroup.edge.t24ui.cos.pattern.impl.InitialEnquiryImpl <em>Initial Enquiry</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.odcgroup.edge.t24ui.cos.pattern.impl.InitialEnquiryImpl
	 * @see com.odcgroup.edge.t24ui.cos.pattern.impl.PatternPackageImpl#getInitialEnquiry()
	 * @generated
	 */
	int INITIAL_ENQUIRY = 7;

	/**
	 * The meta object id for the '{@link com.odcgroup.edge.t24ui.cos.pattern.impl.InitialScreenImpl <em>Initial Screen</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.odcgroup.edge.t24ui.cos.pattern.impl.InitialScreenImpl
	 * @see com.odcgroup.edge.t24ui.cos.pattern.impl.PatternPackageImpl#getInitialScreen()
	 * @generated
	 */
	int INITIAL_SCREEN = 8;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SINGLE_COMPONENT_PANEL__NAME = COS_PANEL__NAME;

	/**
	 * The feature id for the '<em><b>Hostable Content All</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SINGLE_COMPONENT_PANEL__HOSTABLE_CONTENT_ALL = COS_PANEL__HOSTABLE_CONTENT_ALL;

	/**
	 * The feature id for the '<em><b>Hostable Bespoke COS Content All</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SINGLE_COMPONENT_PANEL__HOSTABLE_BESPOKE_COS_CONTENT_ALL = COS_PANEL__HOSTABLE_BESPOKE_COS_CONTENT_ALL;

	/**
	 * The feature id for the '<em><b>Hostable Bespoke COS Content</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SINGLE_COMPONENT_PANEL__HOSTABLE_BESPOKE_COS_CONTENT = COS_PANEL__HOSTABLE_BESPOKE_COS_CONTENT;

	/**
	 * The feature id for the '<em><b>Hostable COS Content All</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SINGLE_COMPONENT_PANEL__HOSTABLE_COS_CONTENT_ALL = COS_PANEL__HOSTABLE_COS_CONTENT_ALL;

	/**
	 * The feature id for the '<em><b>Hostable COS Content</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SINGLE_COMPONENT_PANEL__HOSTABLE_COS_CONTENT = COS_PANEL__HOSTABLE_COS_CONTENT;

	/**
	 * The feature id for the '<em><b>Hostable Enquiry Content All</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SINGLE_COMPONENT_PANEL__HOSTABLE_ENQUIRY_CONTENT_ALL = COS_PANEL__HOSTABLE_ENQUIRY_CONTENT_ALL;

	/**
	 * The feature id for the '<em><b>Hostable Enquiry Content</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SINGLE_COMPONENT_PANEL__HOSTABLE_ENQUIRY_CONTENT = COS_PANEL__HOSTABLE_ENQUIRY_CONTENT;

	/**
	 * The feature id for the '<em><b>Hostable Screen Content All</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SINGLE_COMPONENT_PANEL__HOSTABLE_SCREEN_CONTENT_ALL = COS_PANEL__HOSTABLE_SCREEN_CONTENT_ALL;

	/**
	 * The feature id for the '<em><b>Hostable Screen Content</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SINGLE_COMPONENT_PANEL__HOSTABLE_SCREEN_CONTENT = COS_PANEL__HOSTABLE_SCREEN_CONTENT;

	/**
	 * The feature id for the '<em><b>Title</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SINGLE_COMPONENT_PANEL__TITLE = COS_PANEL__TITLE;

	/**
	 * The feature id for the '<em><b>Background Color</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SINGLE_COMPONENT_PANEL__BACKGROUND_COLOR = COS_PANEL__BACKGROUND_COLOR;

	/**
	 * The feature id for the '<em><b>Height</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SINGLE_COMPONENT_PANEL__HEIGHT = COS_PANEL__HEIGHT;

	/**
	 * The feature id for the '<em><b>Width</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SINGLE_COMPONENT_PANEL__WIDTH = COS_PANEL__WIDTH;

	/**
	 * The feature id for the '<em><b>Horizontal Overflow Option</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SINGLE_COMPONENT_PANEL__HORIZONTAL_OVERFLOW_OPTION = COS_PANEL__HORIZONTAL_OVERFLOW_OPTION;

	/**
	 * The feature id for the '<em><b>Vertical Overflow Option</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SINGLE_COMPONENT_PANEL__VERTICAL_OVERFLOW_OPTION = COS_PANEL__VERTICAL_OVERFLOW_OPTION;

	/**
	 * The feature id for the '<em><b>Initial Content</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SINGLE_COMPONENT_PANEL__INITIAL_CONTENT = COS_PANEL_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Single Component Panel</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SINGLE_COMPONENT_PANEL_FEATURE_COUNT = COS_PANEL_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link com.odcgroup.edge.t24ui.cos.pattern.impl.ChildResourceSpecImpl <em>Child Resource Spec</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.odcgroup.edge.t24ui.cos.pattern.impl.ChildResourceSpecImpl
	 * @see com.odcgroup.edge.t24ui.cos.pattern.impl.PatternPackageImpl#getChildResourceSpec()
	 * @generated
	 */
	int CHILD_RESOURCE_SPEC = 12;

	/**
	 * The number of structural features of the '<em>Initial Panel Content Spec</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INITIAL_PANEL_CONTENT_SPEC_FEATURE_COUNT = 0;

    /**
	 * The feature id for the '<em><b>Parameters</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHILD_RESOURCE_SPEC__PARAMETERS = INITIAL_PANEL_CONTENT_SPEC_FEATURE_COUNT + 0;

    /**
	 * The number of structural features of the '<em>Child Resource Spec</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHILD_RESOURCE_SPEC_FEATURE_COUNT = INITIAL_PANEL_CONTENT_SPEC_FEATURE_COUNT + 1;

    /**
	 * The feature id for the '<em><b>Parameters</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MULTI_COMPONENT_PANEL__PARAMETERS = CHILD_RESOURCE_SPEC__PARAMETERS;

    /**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MULTI_COMPONENT_PANEL__NAME = CHILD_RESOURCE_SPEC_FEATURE_COUNT + 0;

    /**
	 * The feature id for the '<em><b>Hostable Content All</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MULTI_COMPONENT_PANEL__HOSTABLE_CONTENT_ALL = CHILD_RESOURCE_SPEC_FEATURE_COUNT + 1;

    /**
	 * The feature id for the '<em><b>Hostable Bespoke COS Content All</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MULTI_COMPONENT_PANEL__HOSTABLE_BESPOKE_COS_CONTENT_ALL = CHILD_RESOURCE_SPEC_FEATURE_COUNT + 2;

    /**
	 * The feature id for the '<em><b>Hostable Bespoke COS Content</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MULTI_COMPONENT_PANEL__HOSTABLE_BESPOKE_COS_CONTENT = CHILD_RESOURCE_SPEC_FEATURE_COUNT + 3;

    /**
	 * The feature id for the '<em><b>Hostable COS Content All</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MULTI_COMPONENT_PANEL__HOSTABLE_COS_CONTENT_ALL = CHILD_RESOURCE_SPEC_FEATURE_COUNT + 4;

    /**
	 * The feature id for the '<em><b>Hostable COS Content</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MULTI_COMPONENT_PANEL__HOSTABLE_COS_CONTENT = CHILD_RESOURCE_SPEC_FEATURE_COUNT + 5;

    /**
	 * The feature id for the '<em><b>Hostable Enquiry Content All</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MULTI_COMPONENT_PANEL__HOSTABLE_ENQUIRY_CONTENT_ALL = CHILD_RESOURCE_SPEC_FEATURE_COUNT + 6;

    /**
	 * The feature id for the '<em><b>Hostable Enquiry Content</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MULTI_COMPONENT_PANEL__HOSTABLE_ENQUIRY_CONTENT = CHILD_RESOURCE_SPEC_FEATURE_COUNT + 7;

    /**
	 * The feature id for the '<em><b>Hostable Screen Content All</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MULTI_COMPONENT_PANEL__HOSTABLE_SCREEN_CONTENT_ALL = CHILD_RESOURCE_SPEC_FEATURE_COUNT + 8;

    /**
	 * The feature id for the '<em><b>Hostable Screen Content</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MULTI_COMPONENT_PANEL__HOSTABLE_SCREEN_CONTENT = CHILD_RESOURCE_SPEC_FEATURE_COUNT + 9;

    /**
	 * The feature id for the '<em><b>Title</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MULTI_COMPONENT_PANEL__TITLE = CHILD_RESOURCE_SPEC_FEATURE_COUNT + 10;

    /**
	 * The feature id for the '<em><b>Background Color</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MULTI_COMPONENT_PANEL__BACKGROUND_COLOR = CHILD_RESOURCE_SPEC_FEATURE_COUNT + 11;

    /**
	 * The feature id for the '<em><b>Height</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MULTI_COMPONENT_PANEL__HEIGHT = CHILD_RESOURCE_SPEC_FEATURE_COUNT + 12;

    /**
	 * The feature id for the '<em><b>Width</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MULTI_COMPONENT_PANEL__WIDTH = CHILD_RESOURCE_SPEC_FEATURE_COUNT + 13;

    /**
	 * The feature id for the '<em><b>Horizontal Overflow Option</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MULTI_COMPONENT_PANEL__HORIZONTAL_OVERFLOW_OPTION = CHILD_RESOURCE_SPEC_FEATURE_COUNT + 14;

    /**
	 * The feature id for the '<em><b>Vertical Overflow Option</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MULTI_COMPONENT_PANEL__VERTICAL_OVERFLOW_OPTION = CHILD_RESOURCE_SPEC_FEATURE_COUNT + 15;

    /**
	 * The feature id for the '<em><b>Pattern</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MULTI_COMPONENT_PANEL__PATTERN = CHILD_RESOURCE_SPEC_FEATURE_COUNT + 16;

    /**
	 * The feature id for the '<em><b>Panel Separator Option</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MULTI_COMPONENT_PANEL__PANEL_SEPARATOR_OPTION = CHILD_RESOURCE_SPEC_FEATURE_COUNT + 17;

    /**
	 * The feature id for the '<em><b>Accordion Pattern Multi Expandable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MULTI_COMPONENT_PANEL__ACCORDION_PATTERN_MULTI_EXPANDABLE = CHILD_RESOURCE_SPEC_FEATURE_COUNT + 18;

    /**
	 * The feature id for the '<em><b>Child Panels</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MULTI_COMPONENT_PANEL__CHILD_PANELS = CHILD_RESOURCE_SPEC_FEATURE_COUNT + 19;

    /**
	 * The number of structural features of the '<em>Multi Component Panel</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MULTI_COMPONENT_PANEL_FEATURE_COUNT = CHILD_RESOURCE_SPEC_FEATURE_COUNT + 20;

	/**
	 * The meta object id for the '{@link com.odcgroup.edge.t24ui.cos.pattern.impl.InitialCOSImpl <em>Initial COS</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.odcgroup.edge.t24ui.cos.pattern.impl.InitialCOSImpl
	 * @see com.odcgroup.edge.t24ui.cos.pattern.impl.PatternPackageImpl#getInitialCOS()
	 * @generated
	 */
	int INITIAL_COS = 4;

	/**
	 * The feature id for the '<em><b>Parameters</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INITIAL_COS__PARAMETERS = CHILD_RESOURCE_SPEC__PARAMETERS;

	/**
	 * The feature id for the '<em><b>Composite Screen</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INITIAL_COS__COMPOSITE_SCREEN = CHILD_RESOURCE_SPEC_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Initial COS</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INITIAL_COS_FEATURE_COUNT = CHILD_RESOURCE_SPEC_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Pattern</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COS_PATTERN_CONTAINER__PATTERN = 0;

	/**
	 * The feature id for the '<em><b>Panel Separator Option</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COS_PATTERN_CONTAINER__PANEL_SEPARATOR_OPTION = 1;

	/**
	 * The feature id for the '<em><b>Accordion Pattern Multi Expandable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COS_PATTERN_CONTAINER__ACCORDION_PATTERN_MULTI_EXPANDABLE = 2;

	/**
	 * The number of structural features of the '<em>COS Pattern Container</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COS_PATTERN_CONTAINER_FEATURE_COUNT = 3;

	/**
	 * The feature id for the '<em><b>Pattern</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_COS__PATTERN = COS_PATTERN_CONTAINER__PATTERN;

	/**
	 * The feature id for the '<em><b>Panel Separator Option</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_COS__PANEL_SEPARATOR_OPTION = COS_PATTERN_CONTAINER__PANEL_SEPARATOR_OPTION;

	/**
	 * The feature id for the '<em><b>Accordion Pattern Multi Expandable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_COS__ACCORDION_PATTERN_MULTI_EXPANDABLE = COS_PATTERN_CONTAINER__ACCORDION_PATTERN_MULTI_EXPANDABLE;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_COS__NAME = COS_PATTERN_CONTAINER_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Panels</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_COS__PANELS = COS_PATTERN_CONTAINER_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Title</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_COS__TITLE = COS_PATTERN_CONTAINER_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Abstract COS</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_COS_FEATURE_COUNT = COS_PATTERN_CONTAINER_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Pattern</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INLINE_INITIAL_COS__PATTERN = ABSTRACT_COS__PATTERN;

	/**
	 * The feature id for the '<em><b>Panel Separator Option</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INLINE_INITIAL_COS__PANEL_SEPARATOR_OPTION = ABSTRACT_COS__PANEL_SEPARATOR_OPTION;

	/**
	 * The feature id for the '<em><b>Accordion Pattern Multi Expandable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INLINE_INITIAL_COS__ACCORDION_PATTERN_MULTI_EXPANDABLE = ABSTRACT_COS__ACCORDION_PATTERN_MULTI_EXPANDABLE;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INLINE_INITIAL_COS__NAME = ABSTRACT_COS__NAME;

	/**
	 * The feature id for the '<em><b>Panels</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INLINE_INITIAL_COS__PANELS = ABSTRACT_COS__PANELS;

	/**
	 * The feature id for the '<em><b>Title</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INLINE_INITIAL_COS__TITLE = ABSTRACT_COS__TITLE;

	/**
	 * The feature id for the '<em><b>Parameters</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INLINE_INITIAL_COS__PARAMETERS = ABSTRACT_COS_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Inline Initial COS</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INLINE_INITIAL_COS_FEATURE_COUNT = ABSTRACT_COS_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link com.odcgroup.edge.t24ui.cos.pattern.impl.InitialBespokeCOSImpl <em>Initial Bespoke COS</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.odcgroup.edge.t24ui.cos.pattern.impl.InitialBespokeCOSImpl
	 * @see com.odcgroup.edge.t24ui.cos.pattern.impl.PatternPackageImpl#getInitialBespokeCOS()
	 * @generated
	 */
	int INITIAL_BESPOKE_COS = 6;

	/**
	 * The feature id for the '<em><b>Parameters</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INITIAL_BESPOKE_COS__PARAMETERS = CHILD_RESOURCE_SPEC__PARAMETERS;

	/**
	 * The feature id for the '<em><b>Composite Screen</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INITIAL_BESPOKE_COS__COMPOSITE_SCREEN = CHILD_RESOURCE_SPEC_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Initial Bespoke COS</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INITIAL_BESPOKE_COS_FEATURE_COUNT = CHILD_RESOURCE_SPEC_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Parameters</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INITIAL_ENQUIRY__PARAMETERS = CHILD_RESOURCE_SPEC__PARAMETERS;

	/**
	 * The feature id for the '<em><b>Enquiry</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INITIAL_ENQUIRY__ENQUIRY = CHILD_RESOURCE_SPEC_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Initial Enquiry</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INITIAL_ENQUIRY_FEATURE_COUNT = CHILD_RESOURCE_SPEC_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Parameters</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INITIAL_SCREEN__PARAMETERS = CHILD_RESOURCE_SPEC__PARAMETERS;

	/**
	 * The feature id for the '<em><b>Screen</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INITIAL_SCREEN__SCREEN = CHILD_RESOURCE_SPEC_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Initial Screen</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INITIAL_SCREEN_FEATURE_COUNT = CHILD_RESOURCE_SPEC_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link com.odcgroup.edge.t24ui.cos.pattern.impl.COSPatternImpl <em>COS Pattern</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.odcgroup.edge.t24ui.cos.pattern.impl.COSPatternImpl
	 * @see com.odcgroup.edge.t24ui.cos.pattern.impl.PatternPackageImpl#getCOSPattern()
	 * @generated
	 */
	int COS_PATTERN = 11;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COS_PATTERN__NAME = 0;

	/**
	 * The feature id for the '<em><b>Pattern Specific Attribute Names</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COS_PATTERN__PATTERN_SPECIFIC_ATTRIBUTE_NAMES = 1;

	/**
	 * The number of structural features of the '<em>COS Pattern</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COS_PATTERN_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link com.odcgroup.edge.t24ui.cos.pattern.impl.InitialURLImpl <em>Initial URL</em>}' class.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see com.odcgroup.edge.t24ui.cos.pattern.impl.InitialURLImpl
	 * @see com.odcgroup.edge.t24ui.cos.pattern.impl.PatternPackageImpl#getInitialURL()
	 * @generated
	 */
    int INITIAL_URL = 13;

    /**
	 * The feature id for the '<em><b>Url</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int INITIAL_URL__URL = INITIAL_PANEL_CONTENT_SPEC_FEATURE_COUNT + 0;

    /**
	 * The number of structural features of the '<em>Initial URL</em>' class.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int INITIAL_URL_FEATURE_COUNT = INITIAL_PANEL_CONTENT_SPEC_FEATURE_COUNT + 1;

    /**
	 * The meta object id for the '{@link com.odcgroup.edge.t24ui.cos.pattern.PanelSeparatorOption <em>Panel Separator Option</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.odcgroup.edge.t24ui.cos.pattern.PanelSeparatorOption
	 * @see com.odcgroup.edge.t24ui.cos.pattern.impl.PatternPackageImpl#getPanelSeparatorOption()
	 * @generated
	 */
	int PANEL_SEPARATOR_OPTION = 14;

	/**
	 * The meta object id for the '{@link com.odcgroup.edge.t24ui.cos.pattern.PanelOverflowOption <em>Panel Overflow Option</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.odcgroup.edge.t24ui.cos.pattern.PanelOverflowOption
	 * @see com.odcgroup.edge.t24ui.cos.pattern.impl.PatternPackageImpl#getPanelOverflowOption()
	 * @generated
	 */
	int PANEL_OVERFLOW_OPTION = 15;

	/**
	 * Returns the meta object for class '{@link com.odcgroup.edge.t24ui.cos.pattern.COSPanel <em>COS Panel</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>COS Panel</em>'.
	 * @see com.odcgroup.edge.t24ui.cos.pattern.COSPanel
	 * @generated
	 */
	EClass getCOSPanel();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.edge.t24ui.cos.pattern.COSPanel#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see com.odcgroup.edge.t24ui.cos.pattern.COSPanel#getName()
	 * @see #getCOSPanel()
	 * @generated
	 */
	EAttribute getCOSPanel_Name();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.edge.t24ui.cos.pattern.COSPanel#isHostableContentAll <em>Hostable Content All</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Hostable Content All</em>'.
	 * @see com.odcgroup.edge.t24ui.cos.pattern.COSPanel#isHostableContentAll()
	 * @see #getCOSPanel()
	 * @generated
	 */
	EAttribute getCOSPanel_HostableContentAll();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.edge.t24ui.cos.pattern.COSPanel#isHostableBespokeCOSContentAll <em>Hostable Bespoke COS Content All</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Hostable Bespoke COS Content All</em>'.
	 * @see com.odcgroup.edge.t24ui.cos.pattern.COSPanel#isHostableBespokeCOSContentAll()
	 * @see #getCOSPanel()
	 * @generated
	 */
	EAttribute getCOSPanel_HostableBespokeCOSContentAll();

	/**
	 * Returns the meta object for the reference list '{@link com.odcgroup.edge.t24ui.cos.pattern.COSPanel#getHostableBespokeCOSContent <em>Hostable Bespoke COS Content</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Hostable Bespoke COS Content</em>'.
	 * @see com.odcgroup.edge.t24ui.cos.pattern.COSPanel#getHostableBespokeCOSContent()
	 * @see #getCOSPanel()
	 * @generated
	 */
	EReference getCOSPanel_HostableBespokeCOSContent();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.edge.t24ui.cos.pattern.COSPanel#isHostableCOSContentAll <em>Hostable COS Content All</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Hostable COS Content All</em>'.
	 * @see com.odcgroup.edge.t24ui.cos.pattern.COSPanel#isHostableCOSContentAll()
	 * @see #getCOSPanel()
	 * @generated
	 */
	EAttribute getCOSPanel_HostableCOSContentAll();

	/**
	 * Returns the meta object for the reference list '{@link com.odcgroup.edge.t24ui.cos.pattern.COSPanel#getHostableCOSContent <em>Hostable COS Content</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Hostable COS Content</em>'.
	 * @see com.odcgroup.edge.t24ui.cos.pattern.COSPanel#getHostableCOSContent()
	 * @see #getCOSPanel()
	 * @generated
	 */
	EReference getCOSPanel_HostableCOSContent();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.edge.t24ui.cos.pattern.COSPanel#isHostableEnquiryContentAll <em>Hostable Enquiry Content All</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Hostable Enquiry Content All</em>'.
	 * @see com.odcgroup.edge.t24ui.cos.pattern.COSPanel#isHostableEnquiryContentAll()
	 * @see #getCOSPanel()
	 * @generated
	 */
	EAttribute getCOSPanel_HostableEnquiryContentAll();

	/**
	 * Returns the meta object for the reference list '{@link com.odcgroup.edge.t24ui.cos.pattern.COSPanel#getHostableEnquiryContent <em>Hostable Enquiry Content</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Hostable Enquiry Content</em>'.
	 * @see com.odcgroup.edge.t24ui.cos.pattern.COSPanel#getHostableEnquiryContent()
	 * @see #getCOSPanel()
	 * @generated
	 */
	EReference getCOSPanel_HostableEnquiryContent();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.edge.t24ui.cos.pattern.COSPanel#isHostableScreenContentAll <em>Hostable Screen Content All</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Hostable Screen Content All</em>'.
	 * @see com.odcgroup.edge.t24ui.cos.pattern.COSPanel#isHostableScreenContentAll()
	 * @see #getCOSPanel()
	 * @generated
	 */
	EAttribute getCOSPanel_HostableScreenContentAll();

	/**
	 * Returns the meta object for the reference list '{@link com.odcgroup.edge.t24ui.cos.pattern.COSPanel#getHostableScreenContent <em>Hostable Screen Content</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Hostable Screen Content</em>'.
	 * @see com.odcgroup.edge.t24ui.cos.pattern.COSPanel#getHostableScreenContent()
	 * @see #getCOSPanel()
	 * @generated
	 */
	EReference getCOSPanel_HostableScreenContent();

	/**
	 * Returns the meta object for the containment reference list '{@link com.odcgroup.edge.t24ui.cos.pattern.COSPanel#getTitle <em>Title</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Title</em>'.
	 * @see com.odcgroup.edge.t24ui.cos.pattern.COSPanel#getTitle()
	 * @see #getCOSPanel()
	 * @generated
	 */
	EReference getCOSPanel_Title();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.edge.t24ui.cos.pattern.COSPanel#getBackgroundColor <em>Background Color</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Background Color</em>'.
	 * @see com.odcgroup.edge.t24ui.cos.pattern.COSPanel#getBackgroundColor()
	 * @see #getCOSPanel()
	 * @generated
	 */
	EAttribute getCOSPanel_BackgroundColor();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.edge.t24ui.cos.pattern.COSPanel#getHeight <em>Height</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Height</em>'.
	 * @see com.odcgroup.edge.t24ui.cos.pattern.COSPanel#getHeight()
	 * @see #getCOSPanel()
	 * @generated
	 */
	EAttribute getCOSPanel_Height();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.edge.t24ui.cos.pattern.COSPanel#getWidth <em>Width</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Width</em>'.
	 * @see com.odcgroup.edge.t24ui.cos.pattern.COSPanel#getWidth()
	 * @see #getCOSPanel()
	 * @generated
	 */
	EAttribute getCOSPanel_Width();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.edge.t24ui.cos.pattern.COSPanel#getHorizontalOverflowOption <em>Horizontal Overflow Option</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Horizontal Overflow Option</em>'.
	 * @see com.odcgroup.edge.t24ui.cos.pattern.COSPanel#getHorizontalOverflowOption()
	 * @see #getCOSPanel()
	 * @generated
	 */
	EAttribute getCOSPanel_HorizontalOverflowOption();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.edge.t24ui.cos.pattern.COSPanel#getVerticalOverflowOption <em>Vertical Overflow Option</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Vertical Overflow Option</em>'.
	 * @see com.odcgroup.edge.t24ui.cos.pattern.COSPanel#getVerticalOverflowOption()
	 * @see #getCOSPanel()
	 * @generated
	 */
	EAttribute getCOSPanel_VerticalOverflowOption();

	/**
	 * Returns the meta object for class '{@link com.odcgroup.edge.t24ui.cos.pattern.SingleComponentPanel <em>Single Component Panel</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Single Component Panel</em>'.
	 * @see com.odcgroup.edge.t24ui.cos.pattern.SingleComponentPanel
	 * @generated
	 */
	EClass getSingleComponentPanel();

	/**
	 * Returns the meta object for the containment reference '{@link com.odcgroup.edge.t24ui.cos.pattern.SingleComponentPanel#getInitialContent <em>Initial Content</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Initial Content</em>'.
	 * @see com.odcgroup.edge.t24ui.cos.pattern.SingleComponentPanel#getInitialContent()
	 * @see #getSingleComponentPanel()
	 * @generated
	 */
	EReference getSingleComponentPanel_InitialContent();

	/**
	 * Returns the meta object for class '{@link com.odcgroup.edge.t24ui.cos.pattern.MultiComponentPanel <em>Multi Component Panel</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Multi Component Panel</em>'.
	 * @see com.odcgroup.edge.t24ui.cos.pattern.MultiComponentPanel
	 * @generated
	 */
	EClass getMultiComponentPanel();

	/**
	 * Returns the meta object for the containment reference list '{@link com.odcgroup.edge.t24ui.cos.pattern.MultiComponentPanel#getChildPanels <em>Child Panels</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Child Panels</em>'.
	 * @see com.odcgroup.edge.t24ui.cos.pattern.MultiComponentPanel#getChildPanels()
	 * @see #getMultiComponentPanel()
	 * @generated
	 */
	EReference getMultiComponentPanel_ChildPanels();

	/**
	 * Returns the meta object for class '{@link com.odcgroup.edge.t24ui.cos.pattern.InitialPanelContentSpec <em>Initial Panel Content Spec</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Initial Panel Content Spec</em>'.
	 * @see com.odcgroup.edge.t24ui.cos.pattern.InitialPanelContentSpec
	 * @generated
	 */
	EClass getInitialPanelContentSpec();

	/**
	 * Returns the meta object for class '{@link com.odcgroup.edge.t24ui.cos.pattern.InitialCOS <em>Initial COS</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Initial COS</em>'.
	 * @see com.odcgroup.edge.t24ui.cos.pattern.InitialCOS
	 * @generated
	 */
	EClass getInitialCOS();

	/**
	 * Returns the meta object for the reference '{@link com.odcgroup.edge.t24ui.cos.pattern.InitialCOS#getCompositeScreen <em>Composite Screen</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Composite Screen</em>'.
	 * @see com.odcgroup.edge.t24ui.cos.pattern.InitialCOS#getCompositeScreen()
	 * @see #getInitialCOS()
	 * @generated
	 */
	EReference getInitialCOS_CompositeScreen();

	/**
	 * Returns the meta object for class '{@link com.odcgroup.edge.t24ui.cos.pattern.InlineInitialCOS <em>Inline Initial COS</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Inline Initial COS</em>'.
	 * @see com.odcgroup.edge.t24ui.cos.pattern.InlineInitialCOS
	 * @generated
	 */
	EClass getInlineInitialCOS();

	/**
	 * Returns the meta object for class '{@link com.odcgroup.edge.t24ui.cos.pattern.InitialBespokeCOS <em>Initial Bespoke COS</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Initial Bespoke COS</em>'.
	 * @see com.odcgroup.edge.t24ui.cos.pattern.InitialBespokeCOS
	 * @generated
	 */
	EClass getInitialBespokeCOS();

	/**
	 * Returns the meta object for the reference '{@link com.odcgroup.edge.t24ui.cos.pattern.InitialBespokeCOS#getCompositeScreen <em>Composite Screen</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Composite Screen</em>'.
	 * @see com.odcgroup.edge.t24ui.cos.pattern.InitialBespokeCOS#getCompositeScreen()
	 * @see #getInitialBespokeCOS()
	 * @generated
	 */
	EReference getInitialBespokeCOS_CompositeScreen();

	/**
	 * Returns the meta object for class '{@link com.odcgroup.edge.t24ui.cos.pattern.InitialEnquiry <em>Initial Enquiry</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Initial Enquiry</em>'.
	 * @see com.odcgroup.edge.t24ui.cos.pattern.InitialEnquiry
	 * @generated
	 */
	EClass getInitialEnquiry();

	/**
	 * Returns the meta object for the reference '{@link com.odcgroup.edge.t24ui.cos.pattern.InitialEnquiry#getEnquiry <em>Enquiry</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Enquiry</em>'.
	 * @see com.odcgroup.edge.t24ui.cos.pattern.InitialEnquiry#getEnquiry()
	 * @see #getInitialEnquiry()
	 * @generated
	 */
	EReference getInitialEnquiry_Enquiry();

	/**
	 * Returns the meta object for class '{@link com.odcgroup.edge.t24ui.cos.pattern.InitialScreen <em>Initial Screen</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Initial Screen</em>'.
	 * @see com.odcgroup.edge.t24ui.cos.pattern.InitialScreen
	 * @generated
	 */
	EClass getInitialScreen();

	/**
	 * Returns the meta object for the reference '{@link com.odcgroup.edge.t24ui.cos.pattern.InitialScreen#getScreen <em>Screen</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Screen</em>'.
	 * @see com.odcgroup.edge.t24ui.cos.pattern.InitialScreen#getScreen()
	 * @see #getInitialScreen()
	 * @generated
	 */
	EReference getInitialScreen_Screen();

	/**
	 * Returns the meta object for class '{@link com.odcgroup.edge.t24ui.cos.pattern.COSPattern <em>COS Pattern</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>COS Pattern</em>'.
	 * @see com.odcgroup.edge.t24ui.cos.pattern.COSPattern
	 * @generated
	 */
	EClass getCOSPattern();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.edge.t24ui.cos.pattern.COSPattern#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see com.odcgroup.edge.t24ui.cos.pattern.COSPattern#getName()
	 * @see #getCOSPattern()
	 * @generated
	 */
	EAttribute getCOSPattern_Name();

	/**
	 * Returns the meta object for the attribute list '{@link com.odcgroup.edge.t24ui.cos.pattern.COSPattern#getPatternSpecificAttributeNames <em>Pattern Specific Attribute Names</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Pattern Specific Attribute Names</em>'.
	 * @see com.odcgroup.edge.t24ui.cos.pattern.COSPattern#getPatternSpecificAttributeNames()
	 * @see #getCOSPattern()
	 * @generated
	 */
	EAttribute getCOSPattern_PatternSpecificAttributeNames();

	/**
	 * Returns the meta object for class '{@link com.odcgroup.edge.t24ui.cos.pattern.ChildResourceSpec <em>Child Resource Spec</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Child Resource Spec</em>'.
	 * @see com.odcgroup.edge.t24ui.cos.pattern.ChildResourceSpec
	 * @generated
	 */
	EClass getChildResourceSpec();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.edge.t24ui.cos.pattern.ChildResourceSpec#getParameters <em>Parameters</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Parameters</em>'.
	 * @see com.odcgroup.edge.t24ui.cos.pattern.ChildResourceSpec#getParameters()
	 * @see #getChildResourceSpec()
	 * @generated
	 */
	EAttribute getChildResourceSpec_Parameters();

	/**
	 * Returns the meta object for class '{@link com.odcgroup.edge.t24ui.cos.pattern.InitialURL <em>Initial URL</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Initial URL</em>'.
	 * @see com.odcgroup.edge.t24ui.cos.pattern.InitialURL
	 * @generated
	 */
    EClass getInitialURL();

    /**
	 * Returns the meta object for the attribute '{@link com.odcgroup.edge.t24ui.cos.pattern.InitialURL#getUrl <em>Url</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Url</em>'.
	 * @see com.odcgroup.edge.t24ui.cos.pattern.InitialURL#getUrl()
	 * @see #getInitialURL()
	 * @generated
	 */
    EAttribute getInitialURL_Url();

    /**
	 * Returns the meta object for enum '{@link com.odcgroup.edge.t24ui.cos.pattern.PanelSeparatorOption <em>Panel Separator Option</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Panel Separator Option</em>'.
	 * @see com.odcgroup.edge.t24ui.cos.pattern.PanelSeparatorOption
	 * @generated
	 */
	EEnum getPanelSeparatorOption();

	/**
	 * Returns the meta object for enum '{@link com.odcgroup.edge.t24ui.cos.pattern.PanelOverflowOption <em>Panel Overflow Option</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Panel Overflow Option</em>'.
	 * @see com.odcgroup.edge.t24ui.cos.pattern.PanelOverflowOption
	 * @generated
	 */
	EEnum getPanelOverflowOption();

	/**
	 * Returns the meta object for class '{@link com.odcgroup.edge.t24ui.cos.pattern.AbstractCOS <em>Abstract COS</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Abstract COS</em>'.
	 * @see com.odcgroup.edge.t24ui.cos.pattern.AbstractCOS
	 * @generated
	 */
	EClass getAbstractCOS();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.edge.t24ui.cos.pattern.AbstractCOS#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see com.odcgroup.edge.t24ui.cos.pattern.AbstractCOS#getName()
	 * @see #getAbstractCOS()
	 * @generated
	 */
	EAttribute getAbstractCOS_Name();

	/**
	 * Returns the meta object for the containment reference list '{@link com.odcgroup.edge.t24ui.cos.pattern.AbstractCOS#getPanels <em>Panels</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Panels</em>'.
	 * @see com.odcgroup.edge.t24ui.cos.pattern.AbstractCOS#getPanels()
	 * @see #getAbstractCOS()
	 * @generated
	 */
	EReference getAbstractCOS_Panels();

	/**
	 * Returns the meta object for the containment reference list '{@link com.odcgroup.edge.t24ui.cos.pattern.AbstractCOS#getTitle <em>Title</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Title</em>'.
	 * @see com.odcgroup.edge.t24ui.cos.pattern.AbstractCOS#getTitle()
	 * @see #getAbstractCOS()
	 * @generated
	 */
	EReference getAbstractCOS_Title();

	/**
	 * Returns the meta object for class '{@link com.odcgroup.edge.t24ui.cos.pattern.COSPatternContainer <em>COS Pattern Container</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>COS Pattern Container</em>'.
	 * @see com.odcgroup.edge.t24ui.cos.pattern.COSPatternContainer
	 * @generated
	 */
	EClass getCOSPatternContainer();

	/**
	 * Returns the meta object for the reference '{@link com.odcgroup.edge.t24ui.cos.pattern.COSPatternContainer#getPattern <em>Pattern</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Pattern</em>'.
	 * @see com.odcgroup.edge.t24ui.cos.pattern.COSPatternContainer#getPattern()
	 * @see #getCOSPatternContainer()
	 * @generated
	 */
	EReference getCOSPatternContainer_Pattern();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.edge.t24ui.cos.pattern.COSPatternContainer#getPanelSeparatorOption <em>Panel Separator Option</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Panel Separator Option</em>'.
	 * @see com.odcgroup.edge.t24ui.cos.pattern.COSPatternContainer#getPanelSeparatorOption()
	 * @see #getCOSPatternContainer()
	 * @generated
	 */
	EAttribute getCOSPatternContainer_PanelSeparatorOption();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.edge.t24ui.cos.pattern.COSPatternContainer#isAccordionPatternMultiExpandable <em>Accordion Pattern Multi Expandable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Accordion Pattern Multi Expandable</em>'.
	 * @see com.odcgroup.edge.t24ui.cos.pattern.COSPatternContainer#isAccordionPatternMultiExpandable()
	 * @see #getCOSPatternContainer()
	 * @generated
	 */
	EAttribute getCOSPatternContainer_AccordionPatternMultiExpandable();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	PatternFactory getPatternFactory();

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
		 * The meta object literal for the '{@link com.odcgroup.edge.t24ui.cos.pattern.impl.COSPanelImpl <em>COS Panel</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.odcgroup.edge.t24ui.cos.pattern.impl.COSPanelImpl
		 * @see com.odcgroup.edge.t24ui.cos.pattern.impl.PatternPackageImpl#getCOSPanel()
		 * @generated
		 */
		EClass COS_PANEL = eINSTANCE.getCOSPanel();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COS_PANEL__NAME = eINSTANCE.getCOSPanel_Name();

		/**
		 * The meta object literal for the '<em><b>Hostable Content All</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COS_PANEL__HOSTABLE_CONTENT_ALL = eINSTANCE.getCOSPanel_HostableContentAll();

		/**
		 * The meta object literal for the '<em><b>Hostable Bespoke COS Content All</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COS_PANEL__HOSTABLE_BESPOKE_COS_CONTENT_ALL = eINSTANCE.getCOSPanel_HostableBespokeCOSContentAll();

		/**
		 * The meta object literal for the '<em><b>Hostable Bespoke COS Content</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference COS_PANEL__HOSTABLE_BESPOKE_COS_CONTENT = eINSTANCE.getCOSPanel_HostableBespokeCOSContent();

		/**
		 * The meta object literal for the '<em><b>Hostable COS Content All</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COS_PANEL__HOSTABLE_COS_CONTENT_ALL = eINSTANCE.getCOSPanel_HostableCOSContentAll();

		/**
		 * The meta object literal for the '<em><b>Hostable COS Content</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference COS_PANEL__HOSTABLE_COS_CONTENT = eINSTANCE.getCOSPanel_HostableCOSContent();

		/**
		 * The meta object literal for the '<em><b>Hostable Enquiry Content All</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COS_PANEL__HOSTABLE_ENQUIRY_CONTENT_ALL = eINSTANCE.getCOSPanel_HostableEnquiryContentAll();

		/**
		 * The meta object literal for the '<em><b>Hostable Enquiry Content</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference COS_PANEL__HOSTABLE_ENQUIRY_CONTENT = eINSTANCE.getCOSPanel_HostableEnquiryContent();

		/**
		 * The meta object literal for the '<em><b>Hostable Screen Content All</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COS_PANEL__HOSTABLE_SCREEN_CONTENT_ALL = eINSTANCE.getCOSPanel_HostableScreenContentAll();

		/**
		 * The meta object literal for the '<em><b>Hostable Screen Content</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference COS_PANEL__HOSTABLE_SCREEN_CONTENT = eINSTANCE.getCOSPanel_HostableScreenContent();

		/**
		 * The meta object literal for the '<em><b>Title</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference COS_PANEL__TITLE = eINSTANCE.getCOSPanel_Title();

		/**
		 * The meta object literal for the '<em><b>Background Color</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COS_PANEL__BACKGROUND_COLOR = eINSTANCE.getCOSPanel_BackgroundColor();

		/**
		 * The meta object literal for the '<em><b>Height</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COS_PANEL__HEIGHT = eINSTANCE.getCOSPanel_Height();

		/**
		 * The meta object literal for the '<em><b>Width</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COS_PANEL__WIDTH = eINSTANCE.getCOSPanel_Width();

		/**
		 * The meta object literal for the '<em><b>Horizontal Overflow Option</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COS_PANEL__HORIZONTAL_OVERFLOW_OPTION = eINSTANCE.getCOSPanel_HorizontalOverflowOption();

		/**
		 * The meta object literal for the '<em><b>Vertical Overflow Option</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COS_PANEL__VERTICAL_OVERFLOW_OPTION = eINSTANCE.getCOSPanel_VerticalOverflowOption();

		/**
		 * The meta object literal for the '{@link com.odcgroup.edge.t24ui.cos.pattern.impl.SingleComponentPanelImpl <em>Single Component Panel</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.odcgroup.edge.t24ui.cos.pattern.impl.SingleComponentPanelImpl
		 * @see com.odcgroup.edge.t24ui.cos.pattern.impl.PatternPackageImpl#getSingleComponentPanel()
		 * @generated
		 */
		EClass SINGLE_COMPONENT_PANEL = eINSTANCE.getSingleComponentPanel();

		/**
		 * The meta object literal for the '<em><b>Initial Content</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SINGLE_COMPONENT_PANEL__INITIAL_CONTENT = eINSTANCE.getSingleComponentPanel_InitialContent();

		/**
		 * The meta object literal for the '{@link com.odcgroup.edge.t24ui.cos.pattern.impl.MultiComponentPanelImpl <em>Multi Component Panel</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.odcgroup.edge.t24ui.cos.pattern.impl.MultiComponentPanelImpl
		 * @see com.odcgroup.edge.t24ui.cos.pattern.impl.PatternPackageImpl#getMultiComponentPanel()
		 * @generated
		 */
		EClass MULTI_COMPONENT_PANEL = eINSTANCE.getMultiComponentPanel();

		/**
		 * The meta object literal for the '<em><b>Child Panels</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MULTI_COMPONENT_PANEL__CHILD_PANELS = eINSTANCE.getMultiComponentPanel_ChildPanels();

		/**
		 * The meta object literal for the '{@link com.odcgroup.edge.t24ui.cos.pattern.impl.InitialPanelContentSpecImpl <em>Initial Panel Content Spec</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.odcgroup.edge.t24ui.cos.pattern.impl.InitialPanelContentSpecImpl
		 * @see com.odcgroup.edge.t24ui.cos.pattern.impl.PatternPackageImpl#getInitialPanelContentSpec()
		 * @generated
		 */
		EClass INITIAL_PANEL_CONTENT_SPEC = eINSTANCE.getInitialPanelContentSpec();

		/**
		 * The meta object literal for the '{@link com.odcgroup.edge.t24ui.cos.pattern.impl.InitialCOSImpl <em>Initial COS</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.odcgroup.edge.t24ui.cos.pattern.impl.InitialCOSImpl
		 * @see com.odcgroup.edge.t24ui.cos.pattern.impl.PatternPackageImpl#getInitialCOS()
		 * @generated
		 */
		EClass INITIAL_COS = eINSTANCE.getInitialCOS();

		/**
		 * The meta object literal for the '<em><b>Composite Screen</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference INITIAL_COS__COMPOSITE_SCREEN = eINSTANCE.getInitialCOS_CompositeScreen();

		/**
		 * The meta object literal for the '{@link com.odcgroup.edge.t24ui.cos.pattern.impl.InlineInitialCOSImpl <em>Inline Initial COS</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.odcgroup.edge.t24ui.cos.pattern.impl.InlineInitialCOSImpl
		 * @see com.odcgroup.edge.t24ui.cos.pattern.impl.PatternPackageImpl#getInlineInitialCOS()
		 * @generated
		 */
		EClass INLINE_INITIAL_COS = eINSTANCE.getInlineInitialCOS();

		/**
		 * The meta object literal for the '{@link com.odcgroup.edge.t24ui.cos.pattern.impl.InitialBespokeCOSImpl <em>Initial Bespoke COS</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.odcgroup.edge.t24ui.cos.pattern.impl.InitialBespokeCOSImpl
		 * @see com.odcgroup.edge.t24ui.cos.pattern.impl.PatternPackageImpl#getInitialBespokeCOS()
		 * @generated
		 */
		EClass INITIAL_BESPOKE_COS = eINSTANCE.getInitialBespokeCOS();

		/**
		 * The meta object literal for the '<em><b>Composite Screen</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference INITIAL_BESPOKE_COS__COMPOSITE_SCREEN = eINSTANCE.getInitialBespokeCOS_CompositeScreen();

		/**
		 * The meta object literal for the '{@link com.odcgroup.edge.t24ui.cos.pattern.impl.InitialEnquiryImpl <em>Initial Enquiry</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.odcgroup.edge.t24ui.cos.pattern.impl.InitialEnquiryImpl
		 * @see com.odcgroup.edge.t24ui.cos.pattern.impl.PatternPackageImpl#getInitialEnquiry()
		 * @generated
		 */
		EClass INITIAL_ENQUIRY = eINSTANCE.getInitialEnquiry();

		/**
		 * The meta object literal for the '<em><b>Enquiry</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference INITIAL_ENQUIRY__ENQUIRY = eINSTANCE.getInitialEnquiry_Enquiry();

		/**
		 * The meta object literal for the '{@link com.odcgroup.edge.t24ui.cos.pattern.impl.InitialScreenImpl <em>Initial Screen</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.odcgroup.edge.t24ui.cos.pattern.impl.InitialScreenImpl
		 * @see com.odcgroup.edge.t24ui.cos.pattern.impl.PatternPackageImpl#getInitialScreen()
		 * @generated
		 */
		EClass INITIAL_SCREEN = eINSTANCE.getInitialScreen();

		/**
		 * The meta object literal for the '<em><b>Screen</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference INITIAL_SCREEN__SCREEN = eINSTANCE.getInitialScreen_Screen();

		/**
		 * The meta object literal for the '{@link com.odcgroup.edge.t24ui.cos.pattern.impl.COSPatternImpl <em>COS Pattern</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.odcgroup.edge.t24ui.cos.pattern.impl.COSPatternImpl
		 * @see com.odcgroup.edge.t24ui.cos.pattern.impl.PatternPackageImpl#getCOSPattern()
		 * @generated
		 */
		EClass COS_PATTERN = eINSTANCE.getCOSPattern();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COS_PATTERN__NAME = eINSTANCE.getCOSPattern_Name();

		/**
		 * The meta object literal for the '<em><b>Pattern Specific Attribute Names</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COS_PATTERN__PATTERN_SPECIFIC_ATTRIBUTE_NAMES = eINSTANCE.getCOSPattern_PatternSpecificAttributeNames();

		/**
		 * The meta object literal for the '{@link com.odcgroup.edge.t24ui.cos.pattern.impl.ChildResourceSpecImpl <em>Child Resource Spec</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.odcgroup.edge.t24ui.cos.pattern.impl.ChildResourceSpecImpl
		 * @see com.odcgroup.edge.t24ui.cos.pattern.impl.PatternPackageImpl#getChildResourceSpec()
		 * @generated
		 */
		EClass CHILD_RESOURCE_SPEC = eINSTANCE.getChildResourceSpec();

		/**
		 * The meta object literal for the '<em><b>Parameters</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CHILD_RESOURCE_SPEC__PARAMETERS = eINSTANCE.getChildResourceSpec_Parameters();

		/**
		 * The meta object literal for the '{@link com.odcgroup.edge.t24ui.cos.pattern.impl.InitialURLImpl <em>Initial URL</em>}' class.
		 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
		 * @see com.odcgroup.edge.t24ui.cos.pattern.impl.InitialURLImpl
		 * @see com.odcgroup.edge.t24ui.cos.pattern.impl.PatternPackageImpl#getInitialURL()
		 * @generated
		 */
        EClass INITIAL_URL = eINSTANCE.getInitialURL();

        /**
		 * The meta object literal for the '<em><b>Url</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
		 * @generated
		 */
        EAttribute INITIAL_URL__URL = eINSTANCE.getInitialURL_Url();

        /**
		 * The meta object literal for the '{@link com.odcgroup.edge.t24ui.cos.pattern.PanelSeparatorOption <em>Panel Separator Option</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.odcgroup.edge.t24ui.cos.pattern.PanelSeparatorOption
		 * @see com.odcgroup.edge.t24ui.cos.pattern.impl.PatternPackageImpl#getPanelSeparatorOption()
		 * @generated
		 */
		EEnum PANEL_SEPARATOR_OPTION = eINSTANCE.getPanelSeparatorOption();

		/**
		 * The meta object literal for the '{@link com.odcgroup.edge.t24ui.cos.pattern.PanelOverflowOption <em>Panel Overflow Option</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.odcgroup.edge.t24ui.cos.pattern.PanelOverflowOption
		 * @see com.odcgroup.edge.t24ui.cos.pattern.impl.PatternPackageImpl#getPanelOverflowOption()
		 * @generated
		 */
		EEnum PANEL_OVERFLOW_OPTION = eINSTANCE.getPanelOverflowOption();

		/**
		 * The meta object literal for the '{@link com.odcgroup.edge.t24ui.cos.pattern.impl.AbstractCOSImpl <em>Abstract COS</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.odcgroup.edge.t24ui.cos.pattern.impl.AbstractCOSImpl
		 * @see com.odcgroup.edge.t24ui.cos.pattern.impl.PatternPackageImpl#getAbstractCOS()
		 * @generated
		 */
		EClass ABSTRACT_COS = eINSTANCE.getAbstractCOS();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ABSTRACT_COS__NAME = eINSTANCE.getAbstractCOS_Name();

		/**
		 * The meta object literal for the '<em><b>Panels</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ABSTRACT_COS__PANELS = eINSTANCE.getAbstractCOS_Panels();

		/**
		 * The meta object literal for the '<em><b>Title</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ABSTRACT_COS__TITLE = eINSTANCE.getAbstractCOS_Title();

		/**
		 * The meta object literal for the '{@link com.odcgroup.edge.t24ui.cos.pattern.impl.COSPatternContainerImpl <em>COS Pattern Container</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.odcgroup.edge.t24ui.cos.pattern.impl.COSPatternContainerImpl
		 * @see com.odcgroup.edge.t24ui.cos.pattern.impl.PatternPackageImpl#getCOSPatternContainer()
		 * @generated
		 */
		EClass COS_PATTERN_CONTAINER = eINSTANCE.getCOSPatternContainer();

		/**
		 * The meta object literal for the '<em><b>Pattern</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference COS_PATTERN_CONTAINER__PATTERN = eINSTANCE.getCOSPatternContainer_Pattern();

		/**
		 * The meta object literal for the '<em><b>Panel Separator Option</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COS_PATTERN_CONTAINER__PANEL_SEPARATOR_OPTION = eINSTANCE.getCOSPatternContainer_PanelSeparatorOption();

		/**
		 * The meta object literal for the '<em><b>Accordion Pattern Multi Expandable</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COS_PATTERN_CONTAINER__ACCORDION_PATTERN_MULTI_EXPANDABLE = eINSTANCE.getCOSPatternContainer_AccordionPatternMultiExpandable();

	}

} //PatternPackage
