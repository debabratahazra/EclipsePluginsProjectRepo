/**
 * ODCGROUP
 *
 * $Id$
 */
package com.odcgroup.pageflow.model;

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
 * @see com.odcgroup.pageflow.model.PageflowFactory
 * @model kind="package"
 * @generated
 */
public interface PageflowPackage extends EPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String copyright = "ODCGROUP";

	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "pageflow";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.odcgroup.com/ofs/pageflow";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "pageflow";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	PageflowPackage eINSTANCE = com.odcgroup.pageflow.model.impl.PageflowPackageImpl.init();

	/**
	 * The meta object id for the '{@link com.odcgroup.pageflow.model.impl.StateImpl <em>State</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.odcgroup.pageflow.model.impl.StateImpl
	 * @see com.odcgroup.pageflow.model.impl.PageflowPackageImpl#getState()
	 * @generated
	 */
	int STATE = 5;

	/**
	 * The feature id for the '<em><b>Desc</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE__DESC = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE__NAME = 1;

	/**
	 * The feature id for the '<em><b>Display Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE__DISPLAY_NAME = 2;

	/**
	 * The number of structural features of the '<em>State</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link com.odcgroup.pageflow.model.impl.EndStateImpl <em>End State</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.odcgroup.pageflow.model.impl.EndStateImpl
	 * @see com.odcgroup.pageflow.model.impl.PageflowPackageImpl#getEndState()
	 * @generated
	 */
	int END_STATE = 0;

	/**
	 * The feature id for the '<em><b>Desc</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int END_STATE__DESC = STATE__DESC;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int END_STATE__NAME = STATE__NAME;

	/**
	 * The feature id for the '<em><b>Display Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int END_STATE__DISPLAY_NAME = STATE__DISPLAY_NAME;

	/**
	 * The feature id for the '<em><b>Exit Url</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int END_STATE__EXIT_URL = STATE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int END_STATE__ID = STATE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Tech Desc</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int END_STATE__TECH_DESC = STATE_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>End State</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int END_STATE_FEATURE_COUNT = STATE_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link com.odcgroup.pageflow.model.impl.PageflowImpl <em>Pageflow</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.odcgroup.pageflow.model.impl.PageflowImpl
	 * @see com.odcgroup.pageflow.model.impl.PageflowPackageImpl#getPageflow()
	 * @generated
	 */
	int PAGEFLOW = 1;

	/**
	 * The feature id for the '<em><b>States</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAGEFLOW__STATES = 0;

	/**
	 * The feature id for the '<em><b>Desc</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAGEFLOW__DESC = 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAGEFLOW__NAME = 2;

	/**
	 * The feature id for the '<em><b>Abort View</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAGEFLOW__ABORT_VIEW = 3;

	/**
	 * The feature id for the '<em><b>Error View</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAGEFLOW__ERROR_VIEW = 4;

	/**
	 * The feature id for the '<em><b>Transitions</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAGEFLOW__TRANSITIONS = 5;

	/**
	 * The feature id for the '<em><b>Property</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAGEFLOW__PROPERTY = 6;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAGEFLOW__ID = 7;

	/**
	 * The feature id for the '<em><b>File Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAGEFLOW__FILE_NAME = 8;

	/**
	 * The feature id for the '<em><b>Tech Desc</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAGEFLOW__TECH_DESC = 9;

	/**
	 * The feature id for the '<em><b>Metamodel Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAGEFLOW__METAMODEL_VERSION = 10;

	/**
	 * The number of structural features of the '<em>Pageflow</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAGEFLOW_FEATURE_COUNT = 11;

	/**
	 * The meta object id for the '{@link com.odcgroup.pageflow.model.impl.InitStateImpl <em>Init State</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.odcgroup.pageflow.model.impl.InitStateImpl
	 * @see com.odcgroup.pageflow.model.impl.PageflowPackageImpl#getInitState()
	 * @generated
	 */
	int INIT_STATE = 2;

	/**
	 * The feature id for the '<em><b>Desc</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INIT_STATE__DESC = STATE__DESC;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INIT_STATE__NAME = STATE__NAME;

	/**
	 * The feature id for the '<em><b>Display Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INIT_STATE__DISPLAY_NAME = STATE__DISPLAY_NAME;

	/**
	 * The number of structural features of the '<em>Init State</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INIT_STATE_FEATURE_COUNT = STATE_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link com.odcgroup.pageflow.model.impl.ActionImpl <em>Action</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.odcgroup.pageflow.model.impl.ActionImpl
	 * @see com.odcgroup.pageflow.model.impl.PageflowPackageImpl#getAction()
	 * @generated
	 */
	int ACTION = 3;

	/**
	 * The feature id for the '<em><b>Property</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTION__PROPERTY = 0;

	/**
	 * The feature id for the '<em><b>Uri</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTION__URI = 1;

	/**
	 * The feature id for the '<em><b>Desc</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTION__DESC = 2;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTION__NAME = 3;

	/**
	 * The feature id for the '<em><b>Stop On Failure</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTION__STOP_ON_FAILURE = 4;

	/**
	 * The number of structural features of the '<em>Action</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTION_FEATURE_COUNT = 5;

	/**
	 * The meta object id for the '{@link com.odcgroup.pageflow.model.impl.PropertyImpl <em>Property</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.odcgroup.pageflow.model.impl.PropertyImpl
	 * @see com.odcgroup.pageflow.model.impl.PageflowPackageImpl#getProperty()
	 * @generated
	 */
	int PROPERTY = 4;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY__NAME = 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY__VALUE = 1;

	/**
	 * The number of structural features of the '<em>Property</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link com.odcgroup.pageflow.model.impl.TransitionImpl <em>Transition</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.odcgroup.pageflow.model.impl.TransitionImpl
	 * @see com.odcgroup.pageflow.model.impl.PageflowPackageImpl#getTransition()
	 * @generated
	 */
	int TRANSITION = 6;

	/**
	 * The feature id for the '<em><b>Actions</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSITION__ACTIONS = 0;

	/**
	 * The feature id for the '<em><b>Desc</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSITION__DESC = 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSITION__NAME = 2;

	/**
	 * The feature id for the '<em><b>From State</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSITION__FROM_STATE = 3;

	/**
	 * The feature id for the '<em><b>To State</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSITION__TO_STATE = 4;

	/**
	 * The feature id for the '<em><b>Display Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSITION__DISPLAY_NAME = 5;

	/**
	 * The feature id for the '<em><b>Tech Desc</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSITION__TECH_DESC = 6;

	/**
	 * The feature id for the '<em><b>Is Idempotent</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSITION__IS_IDEMPOTENT = 7;

	/**
	 * The number of structural features of the '<em>Transition</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSITION_FEATURE_COUNT = 8;

	/**
	 * The meta object id for the '{@link com.odcgroup.pageflow.model.impl.ViewStateImpl <em>View State</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.odcgroup.pageflow.model.impl.ViewStateImpl
	 * @see com.odcgroup.pageflow.model.impl.PageflowPackageImpl#getViewState()
	 * @generated
	 */
	int VIEW_STATE = 7;

	/**
	 * The feature id for the '<em><b>Desc</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIEW_STATE__DESC = STATE__DESC;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIEW_STATE__NAME = STATE__NAME;

	/**
	 * The feature id for the '<em><b>Display Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIEW_STATE__DISPLAY_NAME = STATE__DISPLAY_NAME;

	/**
	 * The feature id for the '<em><b>View</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIEW_STATE__VIEW = STATE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Tech Desc</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIEW_STATE__TECH_DESC = STATE_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>View State</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIEW_STATE_FEATURE_COUNT = STATE_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link com.odcgroup.pageflow.model.impl.ViewImpl <em>View</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.odcgroup.pageflow.model.impl.ViewImpl
	 * @see com.odcgroup.pageflow.model.impl.PageflowPackageImpl#getView()
	 * @generated
	 */
	int VIEW = 8;

	/**
	 * The feature id for the '<em><b>Url</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIEW__URL = 0;

	/**
	 * The number of structural features of the '<em>View</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIEW_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link com.odcgroup.pageflow.model.impl.DecisionStateImpl <em>Decision State</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.odcgroup.pageflow.model.impl.DecisionStateImpl
	 * @see com.odcgroup.pageflow.model.impl.PageflowPackageImpl#getDecisionState()
	 * @generated
	 */
	int DECISION_STATE = 9;

	/**
	 * The feature id for the '<em><b>Desc</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DECISION_STATE__DESC = STATE__DESC;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DECISION_STATE__NAME = STATE__NAME;

	/**
	 * The feature id for the '<em><b>Display Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DECISION_STATE__DISPLAY_NAME = STATE__DISPLAY_NAME;

	/**
	 * The feature id for the '<em><b>Action</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DECISION_STATE__ACTION = STATE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Tech Desc</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DECISION_STATE__TECH_DESC = STATE_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Decision State</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DECISION_STATE_FEATURE_COUNT = STATE_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link com.odcgroup.pageflow.model.impl.TransitionActionImpl <em>Transition Action</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.odcgroup.pageflow.model.impl.TransitionActionImpl
	 * @see com.odcgroup.pageflow.model.impl.PageflowPackageImpl#getTransitionAction()
	 * @generated
	 */
	int TRANSITION_ACTION = 10;

	/**
	 * The feature id for the '<em><b>Property</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSITION_ACTION__PROPERTY = ACTION__PROPERTY;

	/**
	 * The feature id for the '<em><b>Uri</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSITION_ACTION__URI = ACTION__URI;

	/**
	 * The feature id for the '<em><b>Desc</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSITION_ACTION__DESC = ACTION__DESC;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSITION_ACTION__NAME = ACTION__NAME;

	/**
	 * The feature id for the '<em><b>Stop On Failure</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSITION_ACTION__STOP_ON_FAILURE = ACTION__STOP_ON_FAILURE;

	/**
	 * The feature id for the '<em><b>Problem Management</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSITION_ACTION__PROBLEM_MANAGEMENT = ACTION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Transition Action</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSITION_ACTION_FEATURE_COUNT = ACTION_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link com.odcgroup.pageflow.model.impl.DecisionActionImpl <em>Decision Action</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.odcgroup.pageflow.model.impl.DecisionActionImpl
	 * @see com.odcgroup.pageflow.model.impl.PageflowPackageImpl#getDecisionAction()
	 * @generated
	 */
	int DECISION_ACTION = 11;

	/**
	 * The feature id for the '<em><b>Property</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DECISION_ACTION__PROPERTY = ACTION__PROPERTY;

	/**
	 * The feature id for the '<em><b>Uri</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DECISION_ACTION__URI = ACTION__URI;

	/**
	 * The feature id for the '<em><b>Desc</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DECISION_ACTION__DESC = ACTION__DESC;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DECISION_ACTION__NAME = ACTION__NAME;

	/**
	 * The feature id for the '<em><b>Stop On Failure</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DECISION_ACTION__STOP_ON_FAILURE = ACTION__STOP_ON_FAILURE;

	/**
	 * The number of structural features of the '<em>Decision Action</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DECISION_ACTION_FEATURE_COUNT = ACTION_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link com.odcgroup.pageflow.model.impl.SubPageflowStateImpl <em>Sub Pageflow State</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.odcgroup.pageflow.model.impl.SubPageflowStateImpl
	 * @see com.odcgroup.pageflow.model.impl.PageflowPackageImpl#getSubPageflowState()
	 * @generated
	 */
	int SUB_PAGEFLOW_STATE = 12;

	/**
	 * The feature id for the '<em><b>Desc</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUB_PAGEFLOW_STATE__DESC = STATE__DESC;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUB_PAGEFLOW_STATE__NAME = STATE__NAME;

	/**
	 * The feature id for the '<em><b>Display Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUB_PAGEFLOW_STATE__DISPLAY_NAME = STATE__DISPLAY_NAME;

	/**
	 * The feature id for the '<em><b>Sub Pageflow</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUB_PAGEFLOW_STATE__SUB_PAGEFLOW = STATE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Transition Mappings</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUB_PAGEFLOW_STATE__TRANSITION_MAPPINGS = STATE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Tech Desc</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUB_PAGEFLOW_STATE__TECH_DESC = STATE_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Sub Pageflow State</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUB_PAGEFLOW_STATE_FEATURE_COUNT = STATE_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link com.odcgroup.pageflow.model.impl.TransitionMappingImpl <em>Transition Mapping</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.odcgroup.pageflow.model.impl.TransitionMappingImpl
	 * @see com.odcgroup.pageflow.model.impl.PageflowPackageImpl#getTransitionMapping()
	 * @generated
	 */
	int TRANSITION_MAPPING = 13;

	/**
	 * The feature id for the '<em><b>End State</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSITION_MAPPING__END_STATE = 0;

	/**
	 * The feature id for the '<em><b>Transition</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSITION_MAPPING__TRANSITION = 1;

	/**
	 * The number of structural features of the '<em>Transition Mapping</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSITION_MAPPING_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link com.odcgroup.pageflow.model.ProblemManagement <em>Problem Management</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.odcgroup.pageflow.model.ProblemManagement
	 * @see com.odcgroup.pageflow.model.impl.PageflowPackageImpl#getProblemManagement()
	 * @generated
	 */
	int PROBLEM_MANAGEMENT = 14;


	/**
	 * Returns the meta object for class '{@link com.odcgroup.pageflow.model.EndState <em>End State</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>End State</em>'.
	 * @see com.odcgroup.pageflow.model.EndState
	 * @generated
	 */
	EClass getEndState();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.pageflow.model.EndState#getExitUrl <em>Exit Url</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Exit Url</em>'.
	 * @see com.odcgroup.pageflow.model.EndState#getExitUrl()
	 * @see #getEndState()
	 * @generated
	 */
	EAttribute getEndState_ExitUrl();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.pageflow.model.EndState#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see com.odcgroup.pageflow.model.EndState#getId()
	 * @see #getEndState()
	 * @generated
	 */
	EAttribute getEndState_Id();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.pageflow.model.EndState#getTechDesc <em>Tech Desc</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Tech Desc</em>'.
	 * @see com.odcgroup.pageflow.model.EndState#getTechDesc()
	 * @see #getEndState()
	 * @generated
	 */
	EAttribute getEndState_TechDesc();

	/**
	 * Returns the meta object for class '{@link com.odcgroup.pageflow.model.Pageflow <em>Pageflow</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Pageflow</em>'.
	 * @see com.odcgroup.pageflow.model.Pageflow
	 * @generated
	 */
	EClass getPageflow();

	/**
	 * Returns the meta object for the containment reference list '{@link com.odcgroup.pageflow.model.Pageflow#getStates <em>States</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>States</em>'.
	 * @see com.odcgroup.pageflow.model.Pageflow#getStates()
	 * @see #getPageflow()
	 * @generated
	 */
	EReference getPageflow_States();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.pageflow.model.Pageflow#getDesc <em>Desc</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Desc</em>'.
	 * @see com.odcgroup.pageflow.model.Pageflow#getDesc()
	 * @see #getPageflow()
	 * @generated
	 */
	EAttribute getPageflow_Desc();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.pageflow.model.Pageflow#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see com.odcgroup.pageflow.model.Pageflow#getName()
	 * @see #getPageflow()
	 * @generated
	 */
	EAttribute getPageflow_Name();

	/**
	 * Returns the meta object for the containment reference '{@link com.odcgroup.pageflow.model.Pageflow#getAbortView <em>Abort View</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Abort View</em>'.
	 * @see com.odcgroup.pageflow.model.Pageflow#getAbortView()
	 * @see #getPageflow()
	 * @generated
	 */
	EReference getPageflow_AbortView();

	/**
	 * Returns the meta object for the containment reference '{@link com.odcgroup.pageflow.model.Pageflow#getErrorView <em>Error View</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Error View</em>'.
	 * @see com.odcgroup.pageflow.model.Pageflow#getErrorView()
	 * @see #getPageflow()
	 * @generated
	 */
	EReference getPageflow_ErrorView();

	/**
	 * Returns the meta object for the containment reference list '{@link com.odcgroup.pageflow.model.Pageflow#getTransitions <em>Transitions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Transitions</em>'.
	 * @see com.odcgroup.pageflow.model.Pageflow#getTransitions()
	 * @see #getPageflow()
	 * @generated
	 */
	EReference getPageflow_Transitions();

	/**
	 * Returns the meta object for the containment reference list '{@link com.odcgroup.pageflow.model.Pageflow#getProperty <em>Property</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Property</em>'.
	 * @see com.odcgroup.pageflow.model.Pageflow#getProperty()
	 * @see #getPageflow()
	 * @generated
	 */
	EReference getPageflow_Property();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.pageflow.model.Pageflow#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see com.odcgroup.pageflow.model.Pageflow#getId()
	 * @see #getPageflow()
	 * @generated
	 */
	EAttribute getPageflow_Id();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.pageflow.model.Pageflow#getFileName <em>File Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>File Name</em>'.
	 * @see com.odcgroup.pageflow.model.Pageflow#getFileName()
	 * @see #getPageflow()
	 * @generated
	 */
	EAttribute getPageflow_FileName();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.pageflow.model.Pageflow#getTechDesc <em>Tech Desc</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Tech Desc</em>'.
	 * @see com.odcgroup.pageflow.model.Pageflow#getTechDesc()
	 * @see #getPageflow()
	 * @generated
	 */
	EAttribute getPageflow_TechDesc();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.pageflow.model.Pageflow#getMetamodelVersion <em>Metamodel Version</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Metamodel Version</em>'.
	 * @see com.odcgroup.pageflow.model.Pageflow#getMetamodelVersion()
	 * @see #getPageflow()
	 * @generated
	 */
	EAttribute getPageflow_MetamodelVersion();

	/**
	 * Returns the meta object for class '{@link com.odcgroup.pageflow.model.InitState <em>Init State</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Init State</em>'.
	 * @see com.odcgroup.pageflow.model.InitState
	 * @generated
	 */
	EClass getInitState();

	/**
	 * Returns the meta object for class '{@link com.odcgroup.pageflow.model.Action <em>Action</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Action</em>'.
	 * @see com.odcgroup.pageflow.model.Action
	 * @generated
	 */
	EClass getAction();

	/**
	 * Returns the meta object for the containment reference list '{@link com.odcgroup.pageflow.model.Action#getProperty <em>Property</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Property</em>'.
	 * @see com.odcgroup.pageflow.model.Action#getProperty()
	 * @see #getAction()
	 * @generated
	 */
	EReference getAction_Property();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.pageflow.model.Action#getUri <em>Uri</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Uri</em>'.
	 * @see com.odcgroup.pageflow.model.Action#getUri()
	 * @see #getAction()
	 * @generated
	 */
	EAttribute getAction_Uri();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.pageflow.model.Action#getDesc <em>Desc</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Desc</em>'.
	 * @see com.odcgroup.pageflow.model.Action#getDesc()
	 * @see #getAction()
	 * @generated
	 */
	EAttribute getAction_Desc();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.pageflow.model.Action#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see com.odcgroup.pageflow.model.Action#getName()
	 * @see #getAction()
	 * @generated
	 */
	EAttribute getAction_Name();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.pageflow.model.Action#isStopOnFailure <em>Stop On Failure</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Stop On Failure</em>'.
	 * @see com.odcgroup.pageflow.model.Action#isStopOnFailure()
	 * @see #getAction()
	 * @generated
	 */
	EAttribute getAction_StopOnFailure();

	/**
	 * Returns the meta object for class '{@link com.odcgroup.pageflow.model.Property <em>Property</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Property</em>'.
	 * @see com.odcgroup.pageflow.model.Property
	 * @generated
	 */
	EClass getProperty();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.pageflow.model.Property#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see com.odcgroup.pageflow.model.Property#getName()
	 * @see #getProperty()
	 * @generated
	 */
	EAttribute getProperty_Name();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.pageflow.model.Property#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see com.odcgroup.pageflow.model.Property#getValue()
	 * @see #getProperty()
	 * @generated
	 */
	EAttribute getProperty_Value();

	/**
	 * Returns the meta object for class '{@link com.odcgroup.pageflow.model.State <em>State</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>State</em>'.
	 * @see com.odcgroup.pageflow.model.State
	 * @generated
	 */
	EClass getState();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.pageflow.model.State#getDesc <em>Desc</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Desc</em>'.
	 * @see com.odcgroup.pageflow.model.State#getDesc()
	 * @see #getState()
	 * @generated
	 */
	EAttribute getState_Desc();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.pageflow.model.State#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see com.odcgroup.pageflow.model.State#getName()
	 * @see #getState()
	 * @generated
	 */
	EAttribute getState_Name();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.pageflow.model.State#getDisplayName <em>Display Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Display Name</em>'.
	 * @see com.odcgroup.pageflow.model.State#getDisplayName()
	 * @see #getState()
	 * @generated
	 */
	EAttribute getState_DisplayName();

	/**
	 * Returns the meta object for class '{@link com.odcgroup.pageflow.model.Transition <em>Transition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Transition</em>'.
	 * @see com.odcgroup.pageflow.model.Transition
	 * @generated
	 */
	EClass getTransition();

	/**
	 * Returns the meta object for the containment reference list '{@link com.odcgroup.pageflow.model.Transition#getActions <em>Actions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Actions</em>'.
	 * @see com.odcgroup.pageflow.model.Transition#getActions()
	 * @see #getTransition()
	 * @generated
	 */
	EReference getTransition_Actions();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.pageflow.model.Transition#getDesc <em>Desc</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Desc</em>'.
	 * @see com.odcgroup.pageflow.model.Transition#getDesc()
	 * @see #getTransition()
	 * @generated
	 */
	EAttribute getTransition_Desc();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.pageflow.model.Transition#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see com.odcgroup.pageflow.model.Transition#getName()
	 * @see #getTransition()
	 * @generated
	 */
	EAttribute getTransition_Name();

	/**
	 * Returns the meta object for the reference '{@link com.odcgroup.pageflow.model.Transition#getFromState <em>From State</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>From State</em>'.
	 * @see com.odcgroup.pageflow.model.Transition#getFromState()
	 * @see #getTransition()
	 * @generated
	 */
	EReference getTransition_FromState();

	/**
	 * Returns the meta object for the reference '{@link com.odcgroup.pageflow.model.Transition#getToState <em>To State</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>To State</em>'.
	 * @see com.odcgroup.pageflow.model.Transition#getToState()
	 * @see #getTransition()
	 * @generated
	 */
	EReference getTransition_ToState();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.pageflow.model.Transition#getDisplayName <em>Display Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Display Name</em>'.
	 * @see com.odcgroup.pageflow.model.Transition#getDisplayName()
	 * @see #getTransition()
	 * @generated
	 */
	EAttribute getTransition_DisplayName();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.pageflow.model.Transition#getTechDesc <em>Tech Desc</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Tech Desc</em>'.
	 * @see com.odcgroup.pageflow.model.Transition#getTechDesc()
	 * @see #getTransition()
	 * @generated
	 */
	EAttribute getTransition_TechDesc();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.pageflow.model.Transition#isIsIdempotent <em>Is Idempotent</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Is Idempotent</em>'.
	 * @see com.odcgroup.pageflow.model.Transition#isIsIdempotent()
	 * @see #getTransition()
	 * @generated
	 */
	EAttribute getTransition_IsIdempotent();

	/**
	 * Returns the meta object for class '{@link com.odcgroup.pageflow.model.ViewState <em>View State</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>View State</em>'.
	 * @see com.odcgroup.pageflow.model.ViewState
	 * @generated
	 */
	EClass getViewState();

	/**
	 * Returns the meta object for the containment reference '{@link com.odcgroup.pageflow.model.ViewState#getView <em>View</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>View</em>'.
	 * @see com.odcgroup.pageflow.model.ViewState#getView()
	 * @see #getViewState()
	 * @generated
	 */
	EReference getViewState_View();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.pageflow.model.ViewState#getTechDesc <em>Tech Desc</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Tech Desc</em>'.
	 * @see com.odcgroup.pageflow.model.ViewState#getTechDesc()
	 * @see #getViewState()
	 * @generated
	 */
	EAttribute getViewState_TechDesc();

	/**
	 * Returns the meta object for class '{@link com.odcgroup.pageflow.model.View <em>View</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>View</em>'.
	 * @see com.odcgroup.pageflow.model.View
	 * @generated
	 */
	EClass getView();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.pageflow.model.View#getUrl <em>Url</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Url</em>'.
	 * @see com.odcgroup.pageflow.model.View#getUrl()
	 * @see #getView()
	 * @generated
	 */
	EAttribute getView_Url();

	/**
	 * Returns the meta object for class '{@link com.odcgroup.pageflow.model.DecisionState <em>Decision State</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Decision State</em>'.
	 * @see com.odcgroup.pageflow.model.DecisionState
	 * @generated
	 */
	EClass getDecisionState();

	/**
	 * Returns the meta object for the containment reference '{@link com.odcgroup.pageflow.model.DecisionState#getAction <em>Action</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Action</em>'.
	 * @see com.odcgroup.pageflow.model.DecisionState#getAction()
	 * @see #getDecisionState()
	 * @generated
	 */
	EReference getDecisionState_Action();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.pageflow.model.DecisionState#getTechDesc <em>Tech Desc</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Tech Desc</em>'.
	 * @see com.odcgroup.pageflow.model.DecisionState#getTechDesc()
	 * @see #getDecisionState()
	 * @generated
	 */
	EAttribute getDecisionState_TechDesc();

	/**
	 * Returns the meta object for class '{@link com.odcgroup.pageflow.model.TransitionAction <em>Transition Action</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Transition Action</em>'.
	 * @see com.odcgroup.pageflow.model.TransitionAction
	 * @generated
	 */
	EClass getTransitionAction();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.pageflow.model.TransitionAction#getProblemManagement <em>Problem Management</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Problem Management</em>'.
	 * @see com.odcgroup.pageflow.model.TransitionAction#getProblemManagement()
	 * @see #getTransitionAction()
	 * @generated
	 */
	EAttribute getTransitionAction_ProblemManagement();

	/**
	 * Returns the meta object for class '{@link com.odcgroup.pageflow.model.DecisionAction <em>Decision Action</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Decision Action</em>'.
	 * @see com.odcgroup.pageflow.model.DecisionAction
	 * @generated
	 */
	EClass getDecisionAction();

	/**
	 * Returns the meta object for class '{@link com.odcgroup.pageflow.model.SubPageflowState <em>Sub Pageflow State</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Sub Pageflow State</em>'.
	 * @see com.odcgroup.pageflow.model.SubPageflowState
	 * @generated
	 */
	EClass getSubPageflowState();

	/**
	 * Returns the meta object for the reference '{@link com.odcgroup.pageflow.model.SubPageflowState#getSubPageflow <em>Sub Pageflow</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Sub Pageflow</em>'.
	 * @see com.odcgroup.pageflow.model.SubPageflowState#getSubPageflow()
	 * @see #getSubPageflowState()
	 * @generated
	 */
	EReference getSubPageflowState_SubPageflow();

	/**
	 * Returns the meta object for the containment reference list '{@link com.odcgroup.pageflow.model.SubPageflowState#getTransitionMappings <em>Transition Mappings</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Transition Mappings</em>'.
	 * @see com.odcgroup.pageflow.model.SubPageflowState#getTransitionMappings()
	 * @see #getSubPageflowState()
	 * @generated
	 */
	EReference getSubPageflowState_TransitionMappings();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.pageflow.model.SubPageflowState#getTechDesc <em>Tech Desc</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Tech Desc</em>'.
	 * @see com.odcgroup.pageflow.model.SubPageflowState#getTechDesc()
	 * @see #getSubPageflowState()
	 * @generated
	 */
	EAttribute getSubPageflowState_TechDesc();

	/**
	 * Returns the meta object for class '{@link com.odcgroup.pageflow.model.TransitionMapping <em>Transition Mapping</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Transition Mapping</em>'.
	 * @see com.odcgroup.pageflow.model.TransitionMapping
	 * @generated
	 */
	EClass getTransitionMapping();

	/**
	 * Returns the meta object for the reference '{@link com.odcgroup.pageflow.model.TransitionMapping#getEndState <em>End State</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>End State</em>'.
	 * @see com.odcgroup.pageflow.model.TransitionMapping#getEndState()
	 * @see #getTransitionMapping()
	 * @generated
	 */
	EReference getTransitionMapping_EndState();

	/**
	 * Returns the meta object for the reference '{@link com.odcgroup.pageflow.model.TransitionMapping#getTransition <em>Transition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Transition</em>'.
	 * @see com.odcgroup.pageflow.model.TransitionMapping#getTransition()
	 * @see #getTransitionMapping()
	 * @generated
	 */
	EReference getTransitionMapping_Transition();

	/**
	 * Returns the meta object for enum '{@link com.odcgroup.pageflow.model.ProblemManagement <em>Problem Management</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Problem Management</em>'.
	 * @see com.odcgroup.pageflow.model.ProblemManagement
	 * @generated
	 */
	EEnum getProblemManagement();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	PageflowFactory getPageflowFactory();

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
		 * The meta object literal for the '{@link com.odcgroup.pageflow.model.impl.EndStateImpl <em>End State</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.odcgroup.pageflow.model.impl.EndStateImpl
		 * @see com.odcgroup.pageflow.model.impl.PageflowPackageImpl#getEndState()
		 * @generated
		 */
		EClass END_STATE = eINSTANCE.getEndState();

		/**
		 * The meta object literal for the '<em><b>Exit Url</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute END_STATE__EXIT_URL = eINSTANCE.getEndState_ExitUrl();

		/**
		 * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute END_STATE__ID = eINSTANCE.getEndState_Id();

		/**
		 * The meta object literal for the '<em><b>Tech Desc</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute END_STATE__TECH_DESC = eINSTANCE.getEndState_TechDesc();

		/**
		 * The meta object literal for the '{@link com.odcgroup.pageflow.model.impl.PageflowImpl <em>Pageflow</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.odcgroup.pageflow.model.impl.PageflowImpl
		 * @see com.odcgroup.pageflow.model.impl.PageflowPackageImpl#getPageflow()
		 * @generated
		 */
		EClass PAGEFLOW = eINSTANCE.getPageflow();

		/**
		 * The meta object literal for the '<em><b>States</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PAGEFLOW__STATES = eINSTANCE.getPageflow_States();

		/**
		 * The meta object literal for the '<em><b>Desc</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PAGEFLOW__DESC = eINSTANCE.getPageflow_Desc();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PAGEFLOW__NAME = eINSTANCE.getPageflow_Name();

		/**
		 * The meta object literal for the '<em><b>Abort View</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PAGEFLOW__ABORT_VIEW = eINSTANCE.getPageflow_AbortView();

		/**
		 * The meta object literal for the '<em><b>Error View</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PAGEFLOW__ERROR_VIEW = eINSTANCE.getPageflow_ErrorView();

		/**
		 * The meta object literal for the '<em><b>Transitions</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PAGEFLOW__TRANSITIONS = eINSTANCE.getPageflow_Transitions();

		/**
		 * The meta object literal for the '<em><b>Property</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PAGEFLOW__PROPERTY = eINSTANCE.getPageflow_Property();

		/**
		 * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PAGEFLOW__ID = eINSTANCE.getPageflow_Id();

		/**
		 * The meta object literal for the '<em><b>File Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PAGEFLOW__FILE_NAME = eINSTANCE.getPageflow_FileName();

		/**
		 * The meta object literal for the '<em><b>Tech Desc</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PAGEFLOW__TECH_DESC = eINSTANCE.getPageflow_TechDesc();

		/**
		 * The meta object literal for the '<em><b>Metamodel Version</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PAGEFLOW__METAMODEL_VERSION = eINSTANCE.getPageflow_MetamodelVersion();

		/**
		 * The meta object literal for the '{@link com.odcgroup.pageflow.model.impl.InitStateImpl <em>Init State</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.odcgroup.pageflow.model.impl.InitStateImpl
		 * @see com.odcgroup.pageflow.model.impl.PageflowPackageImpl#getInitState()
		 * @generated
		 */
		EClass INIT_STATE = eINSTANCE.getInitState();

		/**
		 * The meta object literal for the '{@link com.odcgroup.pageflow.model.impl.ActionImpl <em>Action</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.odcgroup.pageflow.model.impl.ActionImpl
		 * @see com.odcgroup.pageflow.model.impl.PageflowPackageImpl#getAction()
		 * @generated
		 */
		EClass ACTION = eINSTANCE.getAction();

		/**
		 * The meta object literal for the '<em><b>Property</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ACTION__PROPERTY = eINSTANCE.getAction_Property();

		/**
		 * The meta object literal for the '<em><b>Uri</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ACTION__URI = eINSTANCE.getAction_Uri();

		/**
		 * The meta object literal for the '<em><b>Desc</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ACTION__DESC = eINSTANCE.getAction_Desc();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ACTION__NAME = eINSTANCE.getAction_Name();

		/**
		 * The meta object literal for the '<em><b>Stop On Failure</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ACTION__STOP_ON_FAILURE = eINSTANCE.getAction_StopOnFailure();

		/**
		 * The meta object literal for the '{@link com.odcgroup.pageflow.model.impl.PropertyImpl <em>Property</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.odcgroup.pageflow.model.impl.PropertyImpl
		 * @see com.odcgroup.pageflow.model.impl.PageflowPackageImpl#getProperty()
		 * @generated
		 */
		EClass PROPERTY = eINSTANCE.getProperty();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROPERTY__NAME = eINSTANCE.getProperty_Name();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROPERTY__VALUE = eINSTANCE.getProperty_Value();

		/**
		 * The meta object literal for the '{@link com.odcgroup.pageflow.model.impl.StateImpl <em>State</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.odcgroup.pageflow.model.impl.StateImpl
		 * @see com.odcgroup.pageflow.model.impl.PageflowPackageImpl#getState()
		 * @generated
		 */
		EClass STATE = eINSTANCE.getState();

		/**
		 * The meta object literal for the '<em><b>Desc</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute STATE__DESC = eINSTANCE.getState_Desc();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute STATE__NAME = eINSTANCE.getState_Name();

		/**
		 * The meta object literal for the '<em><b>Display Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute STATE__DISPLAY_NAME = eINSTANCE.getState_DisplayName();

		/**
		 * The meta object literal for the '{@link com.odcgroup.pageflow.model.impl.TransitionImpl <em>Transition</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.odcgroup.pageflow.model.impl.TransitionImpl
		 * @see com.odcgroup.pageflow.model.impl.PageflowPackageImpl#getTransition()
		 * @generated
		 */
		EClass TRANSITION = eINSTANCE.getTransition();

		/**
		 * The meta object literal for the '<em><b>Actions</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TRANSITION__ACTIONS = eINSTANCE.getTransition_Actions();

		/**
		 * The meta object literal for the '<em><b>Desc</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TRANSITION__DESC = eINSTANCE.getTransition_Desc();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TRANSITION__NAME = eINSTANCE.getTransition_Name();

		/**
		 * The meta object literal for the '<em><b>From State</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TRANSITION__FROM_STATE = eINSTANCE.getTransition_FromState();

		/**
		 * The meta object literal for the '<em><b>To State</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TRANSITION__TO_STATE = eINSTANCE.getTransition_ToState();

		/**
		 * The meta object literal for the '<em><b>Display Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TRANSITION__DISPLAY_NAME = eINSTANCE.getTransition_DisplayName();

		/**
		 * The meta object literal for the '<em><b>Tech Desc</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TRANSITION__TECH_DESC = eINSTANCE.getTransition_TechDesc();

		/**
		 * The meta object literal for the '<em><b>Is Idempotent</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TRANSITION__IS_IDEMPOTENT = eINSTANCE.getTransition_IsIdempotent();

		/**
		 * The meta object literal for the '{@link com.odcgroup.pageflow.model.impl.ViewStateImpl <em>View State</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.odcgroup.pageflow.model.impl.ViewStateImpl
		 * @see com.odcgroup.pageflow.model.impl.PageflowPackageImpl#getViewState()
		 * @generated
		 */
		EClass VIEW_STATE = eINSTANCE.getViewState();

		/**
		 * The meta object literal for the '<em><b>View</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference VIEW_STATE__VIEW = eINSTANCE.getViewState_View();

		/**
		 * The meta object literal for the '<em><b>Tech Desc</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute VIEW_STATE__TECH_DESC = eINSTANCE.getViewState_TechDesc();

		/**
		 * The meta object literal for the '{@link com.odcgroup.pageflow.model.impl.ViewImpl <em>View</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.odcgroup.pageflow.model.impl.ViewImpl
		 * @see com.odcgroup.pageflow.model.impl.PageflowPackageImpl#getView()
		 * @generated
		 */
		EClass VIEW = eINSTANCE.getView();

		/**
		 * The meta object literal for the '<em><b>Url</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute VIEW__URL = eINSTANCE.getView_Url();

		/**
		 * The meta object literal for the '{@link com.odcgroup.pageflow.model.impl.DecisionStateImpl <em>Decision State</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.odcgroup.pageflow.model.impl.DecisionStateImpl
		 * @see com.odcgroup.pageflow.model.impl.PageflowPackageImpl#getDecisionState()
		 * @generated
		 */
		EClass DECISION_STATE = eINSTANCE.getDecisionState();

		/**
		 * The meta object literal for the '<em><b>Action</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DECISION_STATE__ACTION = eINSTANCE.getDecisionState_Action();

		/**
		 * The meta object literal for the '<em><b>Tech Desc</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DECISION_STATE__TECH_DESC = eINSTANCE.getDecisionState_TechDesc();

		/**
		 * The meta object literal for the '{@link com.odcgroup.pageflow.model.impl.TransitionActionImpl <em>Transition Action</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.odcgroup.pageflow.model.impl.TransitionActionImpl
		 * @see com.odcgroup.pageflow.model.impl.PageflowPackageImpl#getTransitionAction()
		 * @generated
		 */
		EClass TRANSITION_ACTION = eINSTANCE.getTransitionAction();

		/**
		 * The meta object literal for the '<em><b>Problem Management</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TRANSITION_ACTION__PROBLEM_MANAGEMENT = eINSTANCE.getTransitionAction_ProblemManagement();

		/**
		 * The meta object literal for the '{@link com.odcgroup.pageflow.model.impl.DecisionActionImpl <em>Decision Action</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.odcgroup.pageflow.model.impl.DecisionActionImpl
		 * @see com.odcgroup.pageflow.model.impl.PageflowPackageImpl#getDecisionAction()
		 * @generated
		 */
		EClass DECISION_ACTION = eINSTANCE.getDecisionAction();

		/**
		 * The meta object literal for the '{@link com.odcgroup.pageflow.model.impl.SubPageflowStateImpl <em>Sub Pageflow State</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.odcgroup.pageflow.model.impl.SubPageflowStateImpl
		 * @see com.odcgroup.pageflow.model.impl.PageflowPackageImpl#getSubPageflowState()
		 * @generated
		 */
		EClass SUB_PAGEFLOW_STATE = eINSTANCE.getSubPageflowState();

		/**
		 * The meta object literal for the '<em><b>Sub Pageflow</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SUB_PAGEFLOW_STATE__SUB_PAGEFLOW = eINSTANCE.getSubPageflowState_SubPageflow();

		/**
		 * The meta object literal for the '<em><b>Transition Mappings</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SUB_PAGEFLOW_STATE__TRANSITION_MAPPINGS = eINSTANCE.getSubPageflowState_TransitionMappings();

		/**
		 * The meta object literal for the '<em><b>Tech Desc</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SUB_PAGEFLOW_STATE__TECH_DESC = eINSTANCE.getSubPageflowState_TechDesc();

		/**
		 * The meta object literal for the '{@link com.odcgroup.pageflow.model.impl.TransitionMappingImpl <em>Transition Mapping</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.odcgroup.pageflow.model.impl.TransitionMappingImpl
		 * @see com.odcgroup.pageflow.model.impl.PageflowPackageImpl#getTransitionMapping()
		 * @generated
		 */
		EClass TRANSITION_MAPPING = eINSTANCE.getTransitionMapping();

		/**
		 * The meta object literal for the '<em><b>End State</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TRANSITION_MAPPING__END_STATE = eINSTANCE.getTransitionMapping_EndState();

		/**
		 * The meta object literal for the '<em><b>Transition</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TRANSITION_MAPPING__TRANSITION = eINSTANCE.getTransitionMapping_Transition();

		/**
		 * The meta object literal for the '{@link com.odcgroup.pageflow.model.ProblemManagement <em>Problem Management</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.odcgroup.pageflow.model.ProblemManagement
		 * @see com.odcgroup.pageflow.model.impl.PageflowPackageImpl#getProblemManagement()
		 * @generated
		 */
		EEnum PROBLEM_MANAGEMENT = eINSTANCE.getProblemManagement();

	}

} //PageflowPackage
