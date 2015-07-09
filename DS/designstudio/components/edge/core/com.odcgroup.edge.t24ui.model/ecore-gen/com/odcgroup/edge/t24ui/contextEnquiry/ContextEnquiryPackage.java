/**
 */
package com.odcgroup.edge.t24ui.contextEnquiry;

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
 * @see com.odcgroup.edge.t24ui.contextEnquiry.ContextEnquiryFactory
 * @model kind="package"
 * @generated
 */
public interface ContextEnquiryPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "contextEnquiry";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.temenos.com/DS/t24ui/contextEnquiry";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "contextEnquiry";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	ContextEnquiryPackage eINSTANCE = com.odcgroup.edge.t24ui.contextEnquiry.impl.ContextEnquiryPackageImpl.init();

	/**
	 * The meta object id for the '{@link com.odcgroup.edge.t24ui.contextEnquiry.impl.ContextEnquiryImpl <em>Context Enquiry</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.odcgroup.edge.t24ui.contextEnquiry.impl.ContextEnquiryImpl
	 * @see com.odcgroup.edge.t24ui.contextEnquiry.impl.ContextEnquiryPackageImpl#getContextEnquiry()
	 * @generated
	 */
	int CONTEXT_ENQUIRY = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTEXT_ENQUIRY__NAME = 0;

	/**
	 * The feature id for the '<em><b>Descriptions</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTEXT_ENQUIRY__DESCRIPTIONS = 1;

	/**
	 * The feature id for the '<em><b>Enquiries</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTEXT_ENQUIRY__ENQUIRIES = 2;

	/**
	 * The number of structural features of the '<em>Context Enquiry</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTEXT_ENQUIRY_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link com.odcgroup.edge.t24ui.contextEnquiry.impl.DescriptionImpl <em>Description</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.odcgroup.edge.t24ui.contextEnquiry.impl.DescriptionImpl
	 * @see com.odcgroup.edge.t24ui.contextEnquiry.impl.ContextEnquiryPackageImpl#getDescription()
	 * @generated
	 */
	int DESCRIPTION = 1;

	/**
	 * The feature id for the '<em><b>Lang</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DESCRIPTION__LANG = 0;

	/**
	 * The feature id for the '<em><b>Text</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DESCRIPTION__TEXT = 1;

	/**
	 * The number of structural features of the '<em>Description</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DESCRIPTION_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link com.odcgroup.edge.t24ui.contextEnquiry.impl.SelectionCriteriaImpl <em>Selection Criteria</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.odcgroup.edge.t24ui.contextEnquiry.impl.SelectionCriteriaImpl
	 * @see com.odcgroup.edge.t24ui.contextEnquiry.impl.ContextEnquiryPackageImpl#getSelectionCriteria()
	 * @generated
	 */
	int SELECTION_CRITERIA = 2;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SELECTION_CRITERIA__NAME = 0;

	/**
	 * The feature id for the '<em><b>App Field</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SELECTION_CRITERIA__APP_FIELD = 1;

	/**
	 * The feature id for the '<em><b>Operand</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SELECTION_CRITERIA__OPERAND = 2;

	/**
	 * The feature id for the '<em><b>Sort Order</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SELECTION_CRITERIA__SORT_ORDER = 3;

	/**
	 * The number of structural features of the '<em>Selection Criteria</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SELECTION_CRITERIA_FEATURE_COUNT = 4;

	/**
	 * The meta object id for the '{@link com.odcgroup.edge.t24ui.contextEnquiry.impl.AutoLaunchImpl <em>Auto Launch</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.odcgroup.edge.t24ui.contextEnquiry.impl.AutoLaunchImpl
	 * @see com.odcgroup.edge.t24ui.contextEnquiry.impl.ContextEnquiryPackageImpl#getAutoLaunch()
	 * @generated
	 */
	int AUTO_LAUNCH = 3;

	/**
	 * The number of structural features of the '<em>Auto Launch</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUTO_LAUNCH_FEATURE_COUNT = 0;

	/**
	 * The meta object id for the '{@link com.odcgroup.edge.t24ui.contextEnquiry.impl.AppliedEnquiryImpl <em>Applied Enquiry</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.odcgroup.edge.t24ui.contextEnquiry.impl.AppliedEnquiryImpl
	 * @see com.odcgroup.edge.t24ui.contextEnquiry.impl.ContextEnquiryPackageImpl#getAppliedEnquiry()
	 * @generated
	 */
	int APPLIED_ENQUIRY = 4;

	/**
	 * The feature id for the '<em><b>Enquiry</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int APPLIED_ENQUIRY__ENQUIRY = 0;

	/**
	 * The feature id for the '<em><b>Selection Fields</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int APPLIED_ENQUIRY__SELECTION_FIELDS = 1;

	/**
	 * The feature id for the '<em><b>Descriptions</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int APPLIED_ENQUIRY__DESCRIPTIONS = 2;

	/**
	 * The feature id for the '<em><b>Auto Launch</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int APPLIED_ENQUIRY__AUTO_LAUNCH = 3;

	/**
	 * The number of structural features of the '<em>Applied Enquiry</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int APPLIED_ENQUIRY_FEATURE_COUNT = 4;

	/**
	 * The meta object id for the '{@link com.odcgroup.edge.t24ui.contextEnquiry.impl.ApplicationContextEnquiryImpl <em>Application Context Enquiry</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.odcgroup.edge.t24ui.contextEnquiry.impl.ApplicationContextEnquiryImpl
	 * @see com.odcgroup.edge.t24ui.contextEnquiry.impl.ContextEnquiryPackageImpl#getApplicationContextEnquiry()
	 * @generated
	 */
	int APPLICATION_CONTEXT_ENQUIRY = 5;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int APPLICATION_CONTEXT_ENQUIRY__NAME = CONTEXT_ENQUIRY__NAME;

	/**
	 * The feature id for the '<em><b>Descriptions</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int APPLICATION_CONTEXT_ENQUIRY__DESCRIPTIONS = CONTEXT_ENQUIRY__DESCRIPTIONS;

	/**
	 * The feature id for the '<em><b>Enquiries</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int APPLICATION_CONTEXT_ENQUIRY__ENQUIRIES = CONTEXT_ENQUIRY__ENQUIRIES;

	/**
	 * The feature id for the '<em><b>Applies To Field</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int APPLICATION_CONTEXT_ENQUIRY__APPLIES_TO_FIELD = CONTEXT_ENQUIRY_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Applies To</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int APPLICATION_CONTEXT_ENQUIRY__APPLIES_TO = CONTEXT_ENQUIRY_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Application Context Enquiry</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int APPLICATION_CONTEXT_ENQUIRY_FEATURE_COUNT = CONTEXT_ENQUIRY_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link com.odcgroup.edge.t24ui.contextEnquiry.impl.VersionContextEnquiryImpl <em>Version Context Enquiry</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.odcgroup.edge.t24ui.contextEnquiry.impl.VersionContextEnquiryImpl
	 * @see com.odcgroup.edge.t24ui.contextEnquiry.impl.ContextEnquiryPackageImpl#getVersionContextEnquiry()
	 * @generated
	 */
	int VERSION_CONTEXT_ENQUIRY = 6;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSION_CONTEXT_ENQUIRY__NAME = CONTEXT_ENQUIRY__NAME;

	/**
	 * The feature id for the '<em><b>Descriptions</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSION_CONTEXT_ENQUIRY__DESCRIPTIONS = CONTEXT_ENQUIRY__DESCRIPTIONS;

	/**
	 * The feature id for the '<em><b>Enquiries</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSION_CONTEXT_ENQUIRY__ENQUIRIES = CONTEXT_ENQUIRY__ENQUIRIES;

	/**
	 * The feature id for the '<em><b>Applies To</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSION_CONTEXT_ENQUIRY__APPLIES_TO = CONTEXT_ENQUIRY_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Applies To Field</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSION_CONTEXT_ENQUIRY__APPLIES_TO_FIELD = CONTEXT_ENQUIRY_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Version Context Enquiry</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSION_CONTEXT_ENQUIRY_FEATURE_COUNT = CONTEXT_ENQUIRY_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link com.odcgroup.edge.t24ui.contextEnquiry.impl.FunctionImpl <em>Function</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.odcgroup.edge.t24ui.contextEnquiry.impl.FunctionImpl
	 * @see com.odcgroup.edge.t24ui.contextEnquiry.impl.ContextEnquiryPackageImpl#getFunction()
	 * @generated
	 */
	int FUNCTION = 7;

	/**
	 * The feature id for the '<em><b>Function</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION__FUNCTION = 0;

	/**
	 * The number of structural features of the '<em>Function</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link com.odcgroup.edge.t24ui.contextEnquiry.impl.FunctionAutoLaunchImpl <em>Function Auto Launch</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.odcgroup.edge.t24ui.contextEnquiry.impl.FunctionAutoLaunchImpl
	 * @see com.odcgroup.edge.t24ui.contextEnquiry.impl.ContextEnquiryPackageImpl#getFunctionAutoLaunch()
	 * @generated
	 */
	int FUNCTION_AUTO_LAUNCH = 8;

	/**
	 * The feature id for the '<em><b>Functions</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_AUTO_LAUNCH__FUNCTIONS = AUTO_LAUNCH_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Function Auto Launch</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_AUTO_LAUNCH_FEATURE_COUNT = AUTO_LAUNCH_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link com.odcgroup.edge.t24ui.contextEnquiry.impl.OnChangeAutoLaunchImpl <em>On Change Auto Launch</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.odcgroup.edge.t24ui.contextEnquiry.impl.OnChangeAutoLaunchImpl
	 * @see com.odcgroup.edge.t24ui.contextEnquiry.impl.ContextEnquiryPackageImpl#getOnChangeAutoLaunch()
	 * @generated
	 */
	int ON_CHANGE_AUTO_LAUNCH = 9;

	/**
	 * The number of structural features of the '<em>On Change Auto Launch</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ON_CHANGE_AUTO_LAUNCH_FEATURE_COUNT = AUTO_LAUNCH_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link com.odcgroup.edge.t24ui.contextEnquiry.SortOrder <em>Sort Order</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.odcgroup.edge.t24ui.contextEnquiry.SortOrder
	 * @see com.odcgroup.edge.t24ui.contextEnquiry.impl.ContextEnquiryPackageImpl#getSortOrder()
	 * @generated
	 */
	int SORT_ORDER = 10;

	/**
	 * The meta object id for the '{@link com.odcgroup.edge.t24ui.contextEnquiry.Operand <em>Operand</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.odcgroup.edge.t24ui.contextEnquiry.Operand
	 * @see com.odcgroup.edge.t24ui.contextEnquiry.impl.ContextEnquiryPackageImpl#getOperand()
	 * @generated
	 */
	int OPERAND = 11;

	/**
	 * The meta object id for the '{@link com.odcgroup.edge.t24ui.contextEnquiry.FunctionEnum <em>Function Enum</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.odcgroup.edge.t24ui.contextEnquiry.FunctionEnum
	 * @see com.odcgroup.edge.t24ui.contextEnquiry.impl.ContextEnquiryPackageImpl#getFunctionEnum()
	 * @generated
	 */
	int FUNCTION_ENUM = 12;


	/**
	 * Returns the meta object for class '{@link com.odcgroup.edge.t24ui.contextEnquiry.ContextEnquiry <em>Context Enquiry</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Context Enquiry</em>'.
	 * @see com.odcgroup.edge.t24ui.contextEnquiry.ContextEnquiry
	 * @generated
	 */
	EClass getContextEnquiry();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.edge.t24ui.contextEnquiry.ContextEnquiry#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see com.odcgroup.edge.t24ui.contextEnquiry.ContextEnquiry#getName()
	 * @see #getContextEnquiry()
	 * @generated
	 */
	EAttribute getContextEnquiry_Name();

	/**
	 * Returns the meta object for the containment reference list '{@link com.odcgroup.edge.t24ui.contextEnquiry.ContextEnquiry#getDescriptions <em>Descriptions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Descriptions</em>'.
	 * @see com.odcgroup.edge.t24ui.contextEnquiry.ContextEnquiry#getDescriptions()
	 * @see #getContextEnquiry()
	 * @generated
	 */
	EReference getContextEnquiry_Descriptions();

	/**
	 * Returns the meta object for the containment reference list '{@link com.odcgroup.edge.t24ui.contextEnquiry.ContextEnquiry#getEnquiries <em>Enquiries</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Enquiries</em>'.
	 * @see com.odcgroup.edge.t24ui.contextEnquiry.ContextEnquiry#getEnquiries()
	 * @see #getContextEnquiry()
	 * @generated
	 */
	EReference getContextEnquiry_Enquiries();

	/**
	 * Returns the meta object for class '{@link com.odcgroup.edge.t24ui.contextEnquiry.Description <em>Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Description</em>'.
	 * @see com.odcgroup.edge.t24ui.contextEnquiry.Description
	 * @generated
	 */
	EClass getDescription();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.edge.t24ui.contextEnquiry.Description#getLang <em>Lang</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Lang</em>'.
	 * @see com.odcgroup.edge.t24ui.contextEnquiry.Description#getLang()
	 * @see #getDescription()
	 * @generated
	 */
	EAttribute getDescription_Lang();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.edge.t24ui.contextEnquiry.Description#getText <em>Text</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Text</em>'.
	 * @see com.odcgroup.edge.t24ui.contextEnquiry.Description#getText()
	 * @see #getDescription()
	 * @generated
	 */
	EAttribute getDescription_Text();

	/**
	 * Returns the meta object for class '{@link com.odcgroup.edge.t24ui.contextEnquiry.SelectionCriteria <em>Selection Criteria</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Selection Criteria</em>'.
	 * @see com.odcgroup.edge.t24ui.contextEnquiry.SelectionCriteria
	 * @generated
	 */
	EClass getSelectionCriteria();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.edge.t24ui.contextEnquiry.SelectionCriteria#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see com.odcgroup.edge.t24ui.contextEnquiry.SelectionCriteria#getName()
	 * @see #getSelectionCriteria()
	 * @generated
	 */
	EAttribute getSelectionCriteria_Name();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.edge.t24ui.contextEnquiry.SelectionCriteria#getAppField <em>App Field</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>App Field</em>'.
	 * @see com.odcgroup.edge.t24ui.contextEnquiry.SelectionCriteria#getAppField()
	 * @see #getSelectionCriteria()
	 * @generated
	 */
	EAttribute getSelectionCriteria_AppField();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.edge.t24ui.contextEnquiry.SelectionCriteria#getOperand <em>Operand</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Operand</em>'.
	 * @see com.odcgroup.edge.t24ui.contextEnquiry.SelectionCriteria#getOperand()
	 * @see #getSelectionCriteria()
	 * @generated
	 */
	EAttribute getSelectionCriteria_Operand();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.edge.t24ui.contextEnquiry.SelectionCriteria#getSortOrder <em>Sort Order</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Sort Order</em>'.
	 * @see com.odcgroup.edge.t24ui.contextEnquiry.SelectionCriteria#getSortOrder()
	 * @see #getSelectionCriteria()
	 * @generated
	 */
	EAttribute getSelectionCriteria_SortOrder();

	/**
	 * Returns the meta object for class '{@link com.odcgroup.edge.t24ui.contextEnquiry.AutoLaunch <em>Auto Launch</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Auto Launch</em>'.
	 * @see com.odcgroup.edge.t24ui.contextEnquiry.AutoLaunch
	 * @generated
	 */
	EClass getAutoLaunch();

	/**
	 * Returns the meta object for class '{@link com.odcgroup.edge.t24ui.contextEnquiry.AppliedEnquiry <em>Applied Enquiry</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Applied Enquiry</em>'.
	 * @see com.odcgroup.edge.t24ui.contextEnquiry.AppliedEnquiry
	 * @generated
	 */
	EClass getAppliedEnquiry();

	/**
	 * Returns the meta object for the reference '{@link com.odcgroup.edge.t24ui.contextEnquiry.AppliedEnquiry#getEnquiry <em>Enquiry</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Enquiry</em>'.
	 * @see com.odcgroup.edge.t24ui.contextEnquiry.AppliedEnquiry#getEnquiry()
	 * @see #getAppliedEnquiry()
	 * @generated
	 */
	EReference getAppliedEnquiry_Enquiry();

	/**
	 * Returns the meta object for the containment reference list '{@link com.odcgroup.edge.t24ui.contextEnquiry.AppliedEnquiry#getSelectionFields <em>Selection Fields</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Selection Fields</em>'.
	 * @see com.odcgroup.edge.t24ui.contextEnquiry.AppliedEnquiry#getSelectionFields()
	 * @see #getAppliedEnquiry()
	 * @generated
	 */
	EReference getAppliedEnquiry_SelectionFields();

	/**
	 * Returns the meta object for the containment reference list '{@link com.odcgroup.edge.t24ui.contextEnquiry.AppliedEnquiry#getDescriptions <em>Descriptions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Descriptions</em>'.
	 * @see com.odcgroup.edge.t24ui.contextEnquiry.AppliedEnquiry#getDescriptions()
	 * @see #getAppliedEnquiry()
	 * @generated
	 */
	EReference getAppliedEnquiry_Descriptions();

	/**
	 * Returns the meta object for the containment reference '{@link com.odcgroup.edge.t24ui.contextEnquiry.AppliedEnquiry#getAutoLaunch <em>Auto Launch</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Auto Launch</em>'.
	 * @see com.odcgroup.edge.t24ui.contextEnquiry.AppliedEnquiry#getAutoLaunch()
	 * @see #getAppliedEnquiry()
	 * @generated
	 */
	EReference getAppliedEnquiry_AutoLaunch();

	/**
	 * Returns the meta object for class '{@link com.odcgroup.edge.t24ui.contextEnquiry.ApplicationContextEnquiry <em>Application Context Enquiry</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Application Context Enquiry</em>'.
	 * @see com.odcgroup.edge.t24ui.contextEnquiry.ApplicationContextEnquiry
	 * @generated
	 */
	EClass getApplicationContextEnquiry();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.edge.t24ui.contextEnquiry.ApplicationContextEnquiry#getAppliesToField <em>Applies To Field</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Applies To Field</em>'.
	 * @see com.odcgroup.edge.t24ui.contextEnquiry.ApplicationContextEnquiry#getAppliesToField()
	 * @see #getApplicationContextEnquiry()
	 * @generated
	 */
	EAttribute getApplicationContextEnquiry_AppliesToField();

	/**
	 * Returns the meta object for the reference '{@link com.odcgroup.edge.t24ui.contextEnquiry.ApplicationContextEnquiry#getAppliesTo <em>Applies To</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Applies To</em>'.
	 * @see com.odcgroup.edge.t24ui.contextEnquiry.ApplicationContextEnquiry#getAppliesTo()
	 * @see #getApplicationContextEnquiry()
	 * @generated
	 */
	EReference getApplicationContextEnquiry_AppliesTo();

	/**
	 * Returns the meta object for class '{@link com.odcgroup.edge.t24ui.contextEnquiry.VersionContextEnquiry <em>Version Context Enquiry</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Version Context Enquiry</em>'.
	 * @see com.odcgroup.edge.t24ui.contextEnquiry.VersionContextEnquiry
	 * @generated
	 */
	EClass getVersionContextEnquiry();

	/**
	 * Returns the meta object for the reference '{@link com.odcgroup.edge.t24ui.contextEnquiry.VersionContextEnquiry#getAppliesTo <em>Applies To</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Applies To</em>'.
	 * @see com.odcgroup.edge.t24ui.contextEnquiry.VersionContextEnquiry#getAppliesTo()
	 * @see #getVersionContextEnquiry()
	 * @generated
	 */
	EReference getVersionContextEnquiry_AppliesTo();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.edge.t24ui.contextEnquiry.VersionContextEnquiry#getAppliesToField <em>Applies To Field</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Applies To Field</em>'.
	 * @see com.odcgroup.edge.t24ui.contextEnquiry.VersionContextEnquiry#getAppliesToField()
	 * @see #getVersionContextEnquiry()
	 * @generated
	 */
	EAttribute getVersionContextEnquiry_AppliesToField();

	/**
	 * Returns the meta object for class '{@link com.odcgroup.edge.t24ui.contextEnquiry.Function <em>Function</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Function</em>'.
	 * @see com.odcgroup.edge.t24ui.contextEnquiry.Function
	 * @generated
	 */
	EClass getFunction();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.edge.t24ui.contextEnquiry.Function#getFunction <em>Function</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Function</em>'.
	 * @see com.odcgroup.edge.t24ui.contextEnquiry.Function#getFunction()
	 * @see #getFunction()
	 * @generated
	 */
	EAttribute getFunction_Function();

	/**
	 * Returns the meta object for class '{@link com.odcgroup.edge.t24ui.contextEnquiry.FunctionAutoLaunch <em>Function Auto Launch</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Function Auto Launch</em>'.
	 * @see com.odcgroup.edge.t24ui.contextEnquiry.FunctionAutoLaunch
	 * @generated
	 */
	EClass getFunctionAutoLaunch();

	/**
	 * Returns the meta object for the containment reference list '{@link com.odcgroup.edge.t24ui.contextEnquiry.FunctionAutoLaunch#getFunctions <em>Functions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Functions</em>'.
	 * @see com.odcgroup.edge.t24ui.contextEnquiry.FunctionAutoLaunch#getFunctions()
	 * @see #getFunctionAutoLaunch()
	 * @generated
	 */
	EReference getFunctionAutoLaunch_Functions();

	/**
	 * Returns the meta object for class '{@link com.odcgroup.edge.t24ui.contextEnquiry.OnChangeAutoLaunch <em>On Change Auto Launch</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>On Change Auto Launch</em>'.
	 * @see com.odcgroup.edge.t24ui.contextEnquiry.OnChangeAutoLaunch
	 * @generated
	 */
	EClass getOnChangeAutoLaunch();

	/**
	 * Returns the meta object for enum '{@link com.odcgroup.edge.t24ui.contextEnquiry.SortOrder <em>Sort Order</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Sort Order</em>'.
	 * @see com.odcgroup.edge.t24ui.contextEnquiry.SortOrder
	 * @generated
	 */
	EEnum getSortOrder();

	/**
	 * Returns the meta object for enum '{@link com.odcgroup.edge.t24ui.contextEnquiry.Operand <em>Operand</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Operand</em>'.
	 * @see com.odcgroup.edge.t24ui.contextEnquiry.Operand
	 * @generated
	 */
	EEnum getOperand();

	/**
	 * Returns the meta object for enum '{@link com.odcgroup.edge.t24ui.contextEnquiry.FunctionEnum <em>Function Enum</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Function Enum</em>'.
	 * @see com.odcgroup.edge.t24ui.contextEnquiry.FunctionEnum
	 * @generated
	 */
	EEnum getFunctionEnum();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	ContextEnquiryFactory getContextEnquiryFactory();

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
		 * The meta object literal for the '{@link com.odcgroup.edge.t24ui.contextEnquiry.impl.ContextEnquiryImpl <em>Context Enquiry</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.odcgroup.edge.t24ui.contextEnquiry.impl.ContextEnquiryImpl
		 * @see com.odcgroup.edge.t24ui.contextEnquiry.impl.ContextEnquiryPackageImpl#getContextEnquiry()
		 * @generated
		 */
		EClass CONTEXT_ENQUIRY = eINSTANCE.getContextEnquiry();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONTEXT_ENQUIRY__NAME = eINSTANCE.getContextEnquiry_Name();

		/**
		 * The meta object literal for the '<em><b>Descriptions</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONTEXT_ENQUIRY__DESCRIPTIONS = eINSTANCE.getContextEnquiry_Descriptions();

		/**
		 * The meta object literal for the '<em><b>Enquiries</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONTEXT_ENQUIRY__ENQUIRIES = eINSTANCE.getContextEnquiry_Enquiries();

		/**
		 * The meta object literal for the '{@link com.odcgroup.edge.t24ui.contextEnquiry.impl.DescriptionImpl <em>Description</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.odcgroup.edge.t24ui.contextEnquiry.impl.DescriptionImpl
		 * @see com.odcgroup.edge.t24ui.contextEnquiry.impl.ContextEnquiryPackageImpl#getDescription()
		 * @generated
		 */
		EClass DESCRIPTION = eINSTANCE.getDescription();

		/**
		 * The meta object literal for the '<em><b>Lang</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DESCRIPTION__LANG = eINSTANCE.getDescription_Lang();

		/**
		 * The meta object literal for the '<em><b>Text</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DESCRIPTION__TEXT = eINSTANCE.getDescription_Text();

		/**
		 * The meta object literal for the '{@link com.odcgroup.edge.t24ui.contextEnquiry.impl.SelectionCriteriaImpl <em>Selection Criteria</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.odcgroup.edge.t24ui.contextEnquiry.impl.SelectionCriteriaImpl
		 * @see com.odcgroup.edge.t24ui.contextEnquiry.impl.ContextEnquiryPackageImpl#getSelectionCriteria()
		 * @generated
		 */
		EClass SELECTION_CRITERIA = eINSTANCE.getSelectionCriteria();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SELECTION_CRITERIA__NAME = eINSTANCE.getSelectionCriteria_Name();

		/**
		 * The meta object literal for the '<em><b>App Field</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SELECTION_CRITERIA__APP_FIELD = eINSTANCE.getSelectionCriteria_AppField();

		/**
		 * The meta object literal for the '<em><b>Operand</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SELECTION_CRITERIA__OPERAND = eINSTANCE.getSelectionCriteria_Operand();

		/**
		 * The meta object literal for the '<em><b>Sort Order</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SELECTION_CRITERIA__SORT_ORDER = eINSTANCE.getSelectionCriteria_SortOrder();

		/**
		 * The meta object literal for the '{@link com.odcgroup.edge.t24ui.contextEnquiry.impl.AutoLaunchImpl <em>Auto Launch</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.odcgroup.edge.t24ui.contextEnquiry.impl.AutoLaunchImpl
		 * @see com.odcgroup.edge.t24ui.contextEnquiry.impl.ContextEnquiryPackageImpl#getAutoLaunch()
		 * @generated
		 */
		EClass AUTO_LAUNCH = eINSTANCE.getAutoLaunch();

		/**
		 * The meta object literal for the '{@link com.odcgroup.edge.t24ui.contextEnquiry.impl.AppliedEnquiryImpl <em>Applied Enquiry</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.odcgroup.edge.t24ui.contextEnquiry.impl.AppliedEnquiryImpl
		 * @see com.odcgroup.edge.t24ui.contextEnquiry.impl.ContextEnquiryPackageImpl#getAppliedEnquiry()
		 * @generated
		 */
		EClass APPLIED_ENQUIRY = eINSTANCE.getAppliedEnquiry();

		/**
		 * The meta object literal for the '<em><b>Enquiry</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference APPLIED_ENQUIRY__ENQUIRY = eINSTANCE.getAppliedEnquiry_Enquiry();

		/**
		 * The meta object literal for the '<em><b>Selection Fields</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference APPLIED_ENQUIRY__SELECTION_FIELDS = eINSTANCE.getAppliedEnquiry_SelectionFields();

		/**
		 * The meta object literal for the '<em><b>Descriptions</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference APPLIED_ENQUIRY__DESCRIPTIONS = eINSTANCE.getAppliedEnquiry_Descriptions();

		/**
		 * The meta object literal for the '<em><b>Auto Launch</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference APPLIED_ENQUIRY__AUTO_LAUNCH = eINSTANCE.getAppliedEnquiry_AutoLaunch();

		/**
		 * The meta object literal for the '{@link com.odcgroup.edge.t24ui.contextEnquiry.impl.ApplicationContextEnquiryImpl <em>Application Context Enquiry</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.odcgroup.edge.t24ui.contextEnquiry.impl.ApplicationContextEnquiryImpl
		 * @see com.odcgroup.edge.t24ui.contextEnquiry.impl.ContextEnquiryPackageImpl#getApplicationContextEnquiry()
		 * @generated
		 */
		EClass APPLICATION_CONTEXT_ENQUIRY = eINSTANCE.getApplicationContextEnquiry();

		/**
		 * The meta object literal for the '<em><b>Applies To Field</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute APPLICATION_CONTEXT_ENQUIRY__APPLIES_TO_FIELD = eINSTANCE.getApplicationContextEnquiry_AppliesToField();

		/**
		 * The meta object literal for the '<em><b>Applies To</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference APPLICATION_CONTEXT_ENQUIRY__APPLIES_TO = eINSTANCE.getApplicationContextEnquiry_AppliesTo();

		/**
		 * The meta object literal for the '{@link com.odcgroup.edge.t24ui.contextEnquiry.impl.VersionContextEnquiryImpl <em>Version Context Enquiry</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.odcgroup.edge.t24ui.contextEnquiry.impl.VersionContextEnquiryImpl
		 * @see com.odcgroup.edge.t24ui.contextEnquiry.impl.ContextEnquiryPackageImpl#getVersionContextEnquiry()
		 * @generated
		 */
		EClass VERSION_CONTEXT_ENQUIRY = eINSTANCE.getVersionContextEnquiry();

		/**
		 * The meta object literal for the '<em><b>Applies To</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference VERSION_CONTEXT_ENQUIRY__APPLIES_TO = eINSTANCE.getVersionContextEnquiry_AppliesTo();

		/**
		 * The meta object literal for the '<em><b>Applies To Field</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute VERSION_CONTEXT_ENQUIRY__APPLIES_TO_FIELD = eINSTANCE.getVersionContextEnquiry_AppliesToField();

		/**
		 * The meta object literal for the '{@link com.odcgroup.edge.t24ui.contextEnquiry.impl.FunctionImpl <em>Function</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.odcgroup.edge.t24ui.contextEnquiry.impl.FunctionImpl
		 * @see com.odcgroup.edge.t24ui.contextEnquiry.impl.ContextEnquiryPackageImpl#getFunction()
		 * @generated
		 */
		EClass FUNCTION = eINSTANCE.getFunction();

		/**
		 * The meta object literal for the '<em><b>Function</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FUNCTION__FUNCTION = eINSTANCE.getFunction_Function();

		/**
		 * The meta object literal for the '{@link com.odcgroup.edge.t24ui.contextEnquiry.impl.FunctionAutoLaunchImpl <em>Function Auto Launch</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.odcgroup.edge.t24ui.contextEnquiry.impl.FunctionAutoLaunchImpl
		 * @see com.odcgroup.edge.t24ui.contextEnquiry.impl.ContextEnquiryPackageImpl#getFunctionAutoLaunch()
		 * @generated
		 */
		EClass FUNCTION_AUTO_LAUNCH = eINSTANCE.getFunctionAutoLaunch();

		/**
		 * The meta object literal for the '<em><b>Functions</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FUNCTION_AUTO_LAUNCH__FUNCTIONS = eINSTANCE.getFunctionAutoLaunch_Functions();

		/**
		 * The meta object literal for the '{@link com.odcgroup.edge.t24ui.contextEnquiry.impl.OnChangeAutoLaunchImpl <em>On Change Auto Launch</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.odcgroup.edge.t24ui.contextEnquiry.impl.OnChangeAutoLaunchImpl
		 * @see com.odcgroup.edge.t24ui.contextEnquiry.impl.ContextEnquiryPackageImpl#getOnChangeAutoLaunch()
		 * @generated
		 */
		EClass ON_CHANGE_AUTO_LAUNCH = eINSTANCE.getOnChangeAutoLaunch();

		/**
		 * The meta object literal for the '{@link com.odcgroup.edge.t24ui.contextEnquiry.SortOrder <em>Sort Order</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.odcgroup.edge.t24ui.contextEnquiry.SortOrder
		 * @see com.odcgroup.edge.t24ui.contextEnquiry.impl.ContextEnquiryPackageImpl#getSortOrder()
		 * @generated
		 */
		EEnum SORT_ORDER = eINSTANCE.getSortOrder();

		/**
		 * The meta object literal for the '{@link com.odcgroup.edge.t24ui.contextEnquiry.Operand <em>Operand</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.odcgroup.edge.t24ui.contextEnquiry.Operand
		 * @see com.odcgroup.edge.t24ui.contextEnquiry.impl.ContextEnquiryPackageImpl#getOperand()
		 * @generated
		 */
		EEnum OPERAND = eINSTANCE.getOperand();

		/**
		 * The meta object literal for the '{@link com.odcgroup.edge.t24ui.contextEnquiry.FunctionEnum <em>Function Enum</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.odcgroup.edge.t24ui.contextEnquiry.FunctionEnum
		 * @see com.odcgroup.edge.t24ui.contextEnquiry.impl.ContextEnquiryPackageImpl#getFunctionEnum()
		 * @generated
		 */
		EEnum FUNCTION_ENUM = eINSTANCE.getFunctionEnum();

	}

} //ContextEnquiryPackage
