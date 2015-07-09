/**
 * Odyssey Financial Technologies
 *
 * $Id$
 */
package com.odcgroup.process.model;

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
 * @see com.odcgroup.process.model.ProcessFactory
 * @model kind="package"
 * @generated
 */
public interface ProcessPackage extends EPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String copyright = "Odyssey Financial Technologies";

	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "process";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.odcgroup.com/process";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "process";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	ProcessPackage eINSTANCE = com.odcgroup.process.model.impl.ProcessPackageImpl.init();

	/**
	 * The meta object id for the '{@link com.odcgroup.process.model.impl.ProcessImpl <em>Process</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.odcgroup.process.model.impl.ProcessImpl
	 * @see com.odcgroup.process.model.impl.ProcessPackageImpl#getProcess()
	 * @generated
	 */
	int PROCESS = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROCESS__NAME = 0;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROCESS__COMMENT = 1;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROCESS__DESCRIPTION = 2;

	/**
	 * The feature id for the '<em><b>Display Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROCESS__DISPLAY_NAME = 3;

	/**
	 * The feature id for the '<em><b>Pools</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROCESS__POOLS = 4;

	/**
	 * The feature id for the '<em><b>Transitions</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROCESS__TRANSITIONS = 5;

	/**
	 * The feature id for the '<em><b>Inverted</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROCESS__INVERTED = 6;

	/**
	 * The feature id for the '<em><b>Filename</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROCESS__FILENAME = 7;

	/**
	 * The feature id for the '<em><b>Metamodel Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROCESS__METAMODEL_VERSION = 8;

	/**
	 * The feature id for the '<em><b>Translations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROCESS__TRANSLATIONS = 9;

	/**
	 * The number of structural features of the '<em>Process</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROCESS_FEATURE_COUNT = 10;

	/**
	 * The meta object id for the '{@link com.odcgroup.process.model.impl.NamedElementImpl <em>Named Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.odcgroup.process.model.impl.NamedElementImpl
	 * @see com.odcgroup.process.model.impl.ProcessPackageImpl#getNamedElement()
	 * @generated
	 */
	int NAMED_ELEMENT = 1;

	/**
	 * The feature id for the '<em><b>ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAMED_ELEMENT__ID = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAMED_ELEMENT__NAME = 1;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAMED_ELEMENT__DESCRIPTION = 2;

	/**
	 * The feature id for the '<em><b>TDescription</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAMED_ELEMENT__TDESCRIPTION = 3;

	/**
	 * The number of structural features of the '<em>Named Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAMED_ELEMENT_FEATURE_COUNT = 4;

	/**
	 * The meta object id for the '{@link com.odcgroup.process.model.impl.ProcessItemImpl <em>Item</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.odcgroup.process.model.impl.ProcessItemImpl
	 * @see com.odcgroup.process.model.impl.ProcessPackageImpl#getProcessItem()
	 * @generated
	 */
	int PROCESS_ITEM = 2;

	/**
	 * The feature id for the '<em><b>ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROCESS_ITEM__ID = NAMED_ELEMENT__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROCESS_ITEM__NAME = NAMED_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROCESS_ITEM__DESCRIPTION = NAMED_ELEMENT__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>TDescription</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROCESS_ITEM__TDESCRIPTION = NAMED_ELEMENT__TDESCRIPTION;

	/**
	 * The number of structural features of the '<em>Item</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROCESS_ITEM_FEATURE_COUNT = NAMED_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link com.odcgroup.process.model.impl.PoolImpl <em>Pool</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.odcgroup.process.model.impl.PoolImpl
	 * @see com.odcgroup.process.model.impl.ProcessPackageImpl#getPool()
	 * @generated
	 */
	int POOL = 3;

	/**
	 * The feature id for the '<em><b>ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POOL__ID = NAMED_ELEMENT__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POOL__NAME = NAMED_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POOL__DESCRIPTION = NAMED_ELEMENT__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>TDescription</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POOL__TDESCRIPTION = NAMED_ELEMENT__TDESCRIPTION;

	/**
	 * The feature id for the '<em><b>Tech Desc</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POOL__TECH_DESC = NAMED_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Assignee</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POOL__ASSIGNEE = NAMED_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Assignee By Service</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POOL__ASSIGNEE_BY_SERVICE = NAMED_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Start</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POOL__START = NAMED_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>End</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POOL__END = NAMED_ELEMENT_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Tasks</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POOL__TASKS = NAMED_ELEMENT_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Gateways</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POOL__GATEWAYS = NAMED_ELEMENT_FEATURE_COUNT + 6;

	/**
	 * The number of structural features of the '<em>Pool</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POOL_FEATURE_COUNT = NAMED_ELEMENT_FEATURE_COUNT + 7;

	/**
	 * The meta object id for the '{@link com.odcgroup.process.model.impl.StartEventImpl <em>Start Event</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.odcgroup.process.model.impl.StartEventImpl
	 * @see com.odcgroup.process.model.impl.ProcessPackageImpl#getStartEvent()
	 * @generated
	 */
	int START_EVENT = 4;

	/**
	 * The feature id for the '<em><b>ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int START_EVENT__ID = PROCESS_ITEM__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int START_EVENT__NAME = PROCESS_ITEM__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int START_EVENT__DESCRIPTION = PROCESS_ITEM__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>TDescription</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int START_EVENT__TDESCRIPTION = PROCESS_ITEM__TDESCRIPTION;

	/**
	 * The number of structural features of the '<em>Start Event</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int START_EVENT_FEATURE_COUNT = PROCESS_ITEM_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link com.odcgroup.process.model.impl.EndEventImpl <em>End Event</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.odcgroup.process.model.impl.EndEventImpl
	 * @see com.odcgroup.process.model.impl.ProcessPackageImpl#getEndEvent()
	 * @generated
	 */
	int END_EVENT = 5;

	/**
	 * The feature id for the '<em><b>ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int END_EVENT__ID = PROCESS_ITEM__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int END_EVENT__NAME = PROCESS_ITEM__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int END_EVENT__DESCRIPTION = PROCESS_ITEM__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>TDescription</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int END_EVENT__TDESCRIPTION = PROCESS_ITEM__TDESCRIPTION;

	/**
	 * The number of structural features of the '<em>End Event</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int END_EVENT_FEATURE_COUNT = PROCESS_ITEM_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link com.odcgroup.process.model.impl.TaskImpl <em>Task</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.odcgroup.process.model.impl.TaskImpl
	 * @see com.odcgroup.process.model.impl.ProcessPackageImpl#getTask()
	 * @generated
	 */
	int TASK = 6;

	/**
	 * The feature id for the '<em><b>ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TASK__ID = PROCESS_ITEM__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TASK__NAME = PROCESS_ITEM__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TASK__DESCRIPTION = PROCESS_ITEM__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>TDescription</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TASK__TDESCRIPTION = PROCESS_ITEM__TDESCRIPTION;

	/**
	 * The feature id for the '<em><b>Initial</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TASK__INITIAL = PROCESS_ITEM_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Translations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TASK__TRANSLATIONS = PROCESS_ITEM_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Task</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TASK_FEATURE_COUNT = PROCESS_ITEM_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link com.odcgroup.process.model.impl.UserTaskImpl <em>User Task</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.odcgroup.process.model.impl.UserTaskImpl
	 * @see com.odcgroup.process.model.impl.ProcessPackageImpl#getUserTask()
	 * @generated
	 */
	int USER_TASK = 7;

	/**
	 * The feature id for the '<em><b>ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int USER_TASK__ID = TASK__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int USER_TASK__NAME = TASK__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int USER_TASK__DESCRIPTION = TASK__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>TDescription</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int USER_TASK__TDESCRIPTION = TASK__TDESCRIPTION;

	/**
	 * The feature id for the '<em><b>Initial</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int USER_TASK__INITIAL = TASK__INITIAL;

	/**
	 * The feature id for the '<em><b>Translations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int USER_TASK__TRANSLATIONS = TASK__TRANSLATIONS;

	/**
	 * The feature id for the '<em><b>Pageflow</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int USER_TASK__PAGEFLOW = TASK_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Rule</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int USER_TASK__RULE = TASK_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>User Task</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int USER_TASK_FEATURE_COUNT = TASK_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link com.odcgroup.process.model.impl.ServiceTaskImpl <em>Service Task</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.odcgroup.process.model.impl.ServiceTaskImpl
	 * @see com.odcgroup.process.model.impl.ProcessPackageImpl#getServiceTask()
	 * @generated
	 */
	int SERVICE_TASK = 8;

	/**
	 * The feature id for the '<em><b>ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERVICE_TASK__ID = TASK__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERVICE_TASK__NAME = TASK__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERVICE_TASK__DESCRIPTION = TASK__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>TDescription</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERVICE_TASK__TDESCRIPTION = TASK__TDESCRIPTION;

	/**
	 * The feature id for the '<em><b>Initial</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERVICE_TASK__INITIAL = TASK__INITIAL;

	/**
	 * The feature id for the '<em><b>Translations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERVICE_TASK__TRANSLATIONS = TASK__TRANSLATIONS;

	/**
	 * The feature id for the '<em><b>Service</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERVICE_TASK__SERVICE = TASK_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Service Task</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERVICE_TASK_FEATURE_COUNT = TASK_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link com.odcgroup.process.model.impl.GatewayImpl <em>Gateway</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.odcgroup.process.model.impl.GatewayImpl
	 * @see com.odcgroup.process.model.impl.ProcessPackageImpl#getGateway()
	 * @generated
	 */
	int GATEWAY = 9;

	/**
	 * The feature id for the '<em><b>ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GATEWAY__ID = PROCESS_ITEM__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GATEWAY__NAME = PROCESS_ITEM__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GATEWAY__DESCRIPTION = PROCESS_ITEM__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>TDescription</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GATEWAY__TDESCRIPTION = PROCESS_ITEM__TDESCRIPTION;

	/**
	 * The number of structural features of the '<em>Gateway</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GATEWAY_FEATURE_COUNT = PROCESS_ITEM_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link com.odcgroup.process.model.impl.FlowImpl <em>Flow</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.odcgroup.process.model.impl.FlowImpl
	 * @see com.odcgroup.process.model.impl.ProcessPackageImpl#getFlow()
	 * @generated
	 */
	int FLOW = 10;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FLOW__NAME = 0;

	/**
	 * The feature id for the '<em><b>Source</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FLOW__SOURCE = 1;

	/**
	 * The feature id for the '<em><b>Target</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FLOW__TARGET = 2;

	/**
	 * The number of structural features of the '<em>Flow</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FLOW_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link com.odcgroup.process.model.impl.ComplexGatewayImpl <em>Complex Gateway</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.odcgroup.process.model.impl.ComplexGatewayImpl
	 * @see com.odcgroup.process.model.impl.ProcessPackageImpl#getComplexGateway()
	 * @generated
	 */
	int COMPLEX_GATEWAY = 11;

	/**
	 * The feature id for the '<em><b>ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPLEX_GATEWAY__ID = GATEWAY__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPLEX_GATEWAY__NAME = GATEWAY__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPLEX_GATEWAY__DESCRIPTION = GATEWAY__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>TDescription</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPLEX_GATEWAY__TDESCRIPTION = GATEWAY__TDESCRIPTION;

	/**
	 * The feature id for the '<em><b>Service</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPLEX_GATEWAY__SERVICE = GATEWAY_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Script</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPLEX_GATEWAY__SCRIPT = GATEWAY_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Rule</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPLEX_GATEWAY__RULE = GATEWAY_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Complex Gateway</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPLEX_GATEWAY_FEATURE_COUNT = GATEWAY_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link com.odcgroup.process.model.impl.ExclusiveMergeImpl <em>Exclusive Merge</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.odcgroup.process.model.impl.ExclusiveMergeImpl
	 * @see com.odcgroup.process.model.impl.ProcessPackageImpl#getExclusiveMerge()
	 * @generated
	 */
	int EXCLUSIVE_MERGE = 12;

	/**
	 * The feature id for the '<em><b>ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXCLUSIVE_MERGE__ID = GATEWAY__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXCLUSIVE_MERGE__NAME = GATEWAY__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXCLUSIVE_MERGE__DESCRIPTION = GATEWAY__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>TDescription</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXCLUSIVE_MERGE__TDESCRIPTION = GATEWAY__TDESCRIPTION;

	/**
	 * The number of structural features of the '<em>Exclusive Merge</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXCLUSIVE_MERGE_FEATURE_COUNT = GATEWAY_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link com.odcgroup.process.model.impl.ParallelForkImpl <em>Parallel Fork</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.odcgroup.process.model.impl.ParallelForkImpl
	 * @see com.odcgroup.process.model.impl.ProcessPackageImpl#getParallelFork()
	 * @generated
	 */
	int PARALLEL_FORK = 13;

	/**
	 * The feature id for the '<em><b>ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARALLEL_FORK__ID = GATEWAY__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARALLEL_FORK__NAME = GATEWAY__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARALLEL_FORK__DESCRIPTION = GATEWAY__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>TDescription</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARALLEL_FORK__TDESCRIPTION = GATEWAY__TDESCRIPTION;

	/**
	 * The number of structural features of the '<em>Parallel Fork</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARALLEL_FORK_FEATURE_COUNT = GATEWAY_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link com.odcgroup.process.model.impl.ParallelMergeImpl <em>Parallel Merge</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.odcgroup.process.model.impl.ParallelMergeImpl
	 * @see com.odcgroup.process.model.impl.ProcessPackageImpl#getParallelMerge()
	 * @generated
	 */
	int PARALLEL_MERGE = 14;

	/**
	 * The feature id for the '<em><b>ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARALLEL_MERGE__ID = GATEWAY__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARALLEL_MERGE__NAME = GATEWAY__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARALLEL_MERGE__DESCRIPTION = GATEWAY__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>TDescription</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARALLEL_MERGE__TDESCRIPTION = GATEWAY__TDESCRIPTION;

	/**
	 * The number of structural features of the '<em>Parallel Merge</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARALLEL_MERGE_FEATURE_COUNT = GATEWAY_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link com.odcgroup.process.model.impl.AssigneeImpl <em>Assignee</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.odcgroup.process.model.impl.AssigneeImpl
	 * @see com.odcgroup.process.model.impl.ProcessPackageImpl#getAssignee()
	 * @generated
	 */
	int ASSIGNEE = 15;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASSIGNEE__NAME = 0;

	/**
	 * The number of structural features of the '<em>Assignee</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASSIGNEE_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link com.odcgroup.process.model.impl.PageflowImpl <em>Pageflow</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.odcgroup.process.model.impl.PageflowImpl
	 * @see com.odcgroup.process.model.impl.ProcessPackageImpl#getPageflow()
	 * @generated
	 */
	int PAGEFLOW = 16;

	/**
	 * The feature id for the '<em><b>URI</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAGEFLOW__URI = 0;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAGEFLOW__DESCRIPTION = 1;

	/**
	 * The feature id for the '<em><b>Property</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAGEFLOW__PROPERTY = 2;

	/**
	 * The number of structural features of the '<em>Pageflow</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAGEFLOW_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link com.odcgroup.process.model.impl.ServiceImpl <em>Service</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.odcgroup.process.model.impl.ServiceImpl
	 * @see com.odcgroup.process.model.impl.ProcessPackageImpl#getService()
	 * @generated
	 */
	int SERVICE = 17;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERVICE__NAME = 0;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERVICE__DESCRIPTION = 1;

	/**
	 * The feature id for the '<em><b>Property</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERVICE__PROPERTY = 2;

	/**
	 * The number of structural features of the '<em>Service</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERVICE_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link com.odcgroup.process.model.impl.RuleImpl <em>Rule</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.odcgroup.process.model.impl.RuleImpl
	 * @see com.odcgroup.process.model.impl.ProcessPackageImpl#getRule()
	 * @generated
	 */
	int RULE = 18;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE__NAME = 0;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE__DESCRIPTION = 1;

	/**
	 * The feature id for the '<em><b>Property</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE__PROPERTY = 2;

	/**
	 * The number of structural features of the '<em>Rule</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link com.odcgroup.process.model.impl.ScriptImpl <em>Script</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.odcgroup.process.model.impl.ScriptImpl
	 * @see com.odcgroup.process.model.impl.ProcessPackageImpl#getScript()
	 * @generated
	 */
	int SCRIPT = 19;

	/**
	 * The feature id for the '<em><b>Language</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCRIPT__LANGUAGE = 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCRIPT__VALUE = 1;

	/**
	 * The number of structural features of the '<em>Script</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCRIPT_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link com.odcgroup.process.model.impl.PropertyImpl <em>Property</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.odcgroup.process.model.impl.PropertyImpl
	 * @see com.odcgroup.process.model.impl.ProcessPackageImpl#getProperty()
	 * @generated
	 */
	int PROPERTY = 20;

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
	 * The meta object id for the '{@link com.odcgroup.process.model.impl.TranslationImpl <em>Translation</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.odcgroup.process.model.impl.TranslationImpl
	 * @see com.odcgroup.process.model.impl.ProcessPackageImpl#getTranslation()
	 * @generated
	 */
	int TRANSLATION = 21;

	/**
	 * The feature id for the '<em><b>Kind</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSLATION__KIND = 0;

	/**
	 * The feature id for the '<em><b>Language</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSLATION__LANGUAGE = 1;

	/**
	 * The feature id for the '<em><b>Message</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSLATION__MESSAGE = 2;

	/**
	 * The number of structural features of the '<em>Translation</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSLATION_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link com.odcgroup.process.model.ScriptingLanguage <em>Scripting Language</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.odcgroup.process.model.ScriptingLanguage
	 * @see com.odcgroup.process.model.impl.ProcessPackageImpl#getScriptingLanguage()
	 * @generated
	 */
	int SCRIPTING_LANGUAGE = 22;


	/**
	 * The meta object id for the '{@link com.odcgroup.process.model.TranslationKind <em>Translation Kind</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.odcgroup.process.model.TranslationKind
	 * @see com.odcgroup.process.model.impl.ProcessPackageImpl#getTranslationKind()
	 * @generated
	 */
	int TRANSLATION_KIND = 23;


	/**
	 * Returns the meta object for class '{@link com.odcgroup.process.model.Process <em>Process</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Process</em>'.
	 * @see com.odcgroup.process.model.Process
	 * @generated
	 */
	EClass getProcess();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.process.model.Process#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see com.odcgroup.process.model.Process#getName()
	 * @see #getProcess()
	 * @generated
	 */
	EAttribute getProcess_Name();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.process.model.Process#getComment <em>Comment</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Comment</em>'.
	 * @see com.odcgroup.process.model.Process#getComment()
	 * @see #getProcess()
	 * @generated
	 */
	EAttribute getProcess_Comment();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.process.model.Process#getDescription <em>Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Description</em>'.
	 * @see com.odcgroup.process.model.Process#getDescription()
	 * @see #getProcess()
	 * @generated
	 */
	EAttribute getProcess_Description();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.process.model.Process#getDisplayName <em>Display Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Display Name</em>'.
	 * @see com.odcgroup.process.model.Process#getDisplayName()
	 * @see #getProcess()
	 * @generated
	 */
	EAttribute getProcess_DisplayName();

	/**
	 * Returns the meta object for the containment reference list '{@link com.odcgroup.process.model.Process#getPools <em>Pools</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Pools</em>'.
	 * @see com.odcgroup.process.model.Process#getPools()
	 * @see #getProcess()
	 * @generated
	 */
	EReference getProcess_Pools();

	/**
	 * Returns the meta object for the containment reference list '{@link com.odcgroup.process.model.Process#getTransitions <em>Transitions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Transitions</em>'.
	 * @see com.odcgroup.process.model.Process#getTransitions()
	 * @see #getProcess()
	 * @generated
	 */
	EReference getProcess_Transitions();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.process.model.Process#isInverted <em>Inverted</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Inverted</em>'.
	 * @see com.odcgroup.process.model.Process#isInverted()
	 * @see #getProcess()
	 * @generated
	 */
	EAttribute getProcess_Inverted();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.process.model.Process#getFilename <em>Filename</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Filename</em>'.
	 * @see com.odcgroup.process.model.Process#getFilename()
	 * @see #getProcess()
	 * @generated
	 */
	EAttribute getProcess_Filename();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.process.model.Process#getMetamodelVersion <em>Metamodel Version</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Metamodel Version</em>'.
	 * @see com.odcgroup.process.model.Process#getMetamodelVersion()
	 * @see #getProcess()
	 * @generated
	 */
	EAttribute getProcess_MetamodelVersion();

	/**
	 * Returns the meta object for the containment reference list '{@link com.odcgroup.process.model.Process#getTranslations <em>Translations</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Translations</em>'.
	 * @see com.odcgroup.process.model.Process#getTranslations()
	 * @see #getProcess()
	 * @generated
	 */
	EReference getProcess_Translations();

	/**
	 * Returns the meta object for class '{@link com.odcgroup.process.model.NamedElement <em>Named Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Named Element</em>'.
	 * @see com.odcgroup.process.model.NamedElement
	 * @generated
	 */
	EClass getNamedElement();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.process.model.NamedElement#getID <em>ID</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>ID</em>'.
	 * @see com.odcgroup.process.model.NamedElement#getID()
	 * @see #getNamedElement()
	 * @generated
	 */
	EAttribute getNamedElement_ID();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.process.model.NamedElement#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see com.odcgroup.process.model.NamedElement#getName()
	 * @see #getNamedElement()
	 * @generated
	 */
	EAttribute getNamedElement_Name();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.process.model.NamedElement#getDescription <em>Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Description</em>'.
	 * @see com.odcgroup.process.model.NamedElement#getDescription()
	 * @see #getNamedElement()
	 * @generated
	 */
	EAttribute getNamedElement_Description();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.process.model.NamedElement#getTDescription <em>TDescription</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>TDescription</em>'.
	 * @see com.odcgroup.process.model.NamedElement#getTDescription()
	 * @see #getNamedElement()
	 * @generated
	 */
	EAttribute getNamedElement_TDescription();

	/**
	 * Returns the meta object for class '{@link com.odcgroup.process.model.ProcessItem <em>Item</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Item</em>'.
	 * @see com.odcgroup.process.model.ProcessItem
	 * @generated
	 */
	EClass getProcessItem();

	/**
	 * Returns the meta object for class '{@link com.odcgroup.process.model.Pool <em>Pool</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Pool</em>'.
	 * @see com.odcgroup.process.model.Pool
	 * @generated
	 */
	EClass getPool();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.process.model.Pool#getTechDesc <em>Tech Desc</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Tech Desc</em>'.
	 * @see com.odcgroup.process.model.Pool#getTechDesc()
	 * @see #getPool()
	 * @generated
	 */
	EAttribute getPool_TechDesc();

	/**
	 * Returns the meta object for the containment reference list '{@link com.odcgroup.process.model.Pool#getAssignee <em>Assignee</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Assignee</em>'.
	 * @see com.odcgroup.process.model.Pool#getAssignee()
	 * @see #getPool()
	 * @generated
	 */
	EReference getPool_Assignee();

	/**
	 * Returns the meta object for the containment reference '{@link com.odcgroup.process.model.Pool#getAssigneeByService <em>Assignee By Service</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Assignee By Service</em>'.
	 * @see com.odcgroup.process.model.Pool#getAssigneeByService()
	 * @see #getPool()
	 * @generated
	 */
	EReference getPool_AssigneeByService();

	/**
	 * Returns the meta object for the containment reference '{@link com.odcgroup.process.model.Pool#getStart <em>Start</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Start</em>'.
	 * @see com.odcgroup.process.model.Pool#getStart()
	 * @see #getPool()
	 * @generated
	 */
	EReference getPool_Start();

	/**
	 * Returns the meta object for the containment reference list '{@link com.odcgroup.process.model.Pool#getEnd <em>End</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>End</em>'.
	 * @see com.odcgroup.process.model.Pool#getEnd()
	 * @see #getPool()
	 * @generated
	 */
	EReference getPool_End();

	/**
	 * Returns the meta object for the containment reference list '{@link com.odcgroup.process.model.Pool#getTasks <em>Tasks</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Tasks</em>'.
	 * @see com.odcgroup.process.model.Pool#getTasks()
	 * @see #getPool()
	 * @generated
	 */
	EReference getPool_Tasks();

	/**
	 * Returns the meta object for the containment reference list '{@link com.odcgroup.process.model.Pool#getGateways <em>Gateways</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Gateways</em>'.
	 * @see com.odcgroup.process.model.Pool#getGateways()
	 * @see #getPool()
	 * @generated
	 */
	EReference getPool_Gateways();

	/**
	 * Returns the meta object for class '{@link com.odcgroup.process.model.StartEvent <em>Start Event</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Start Event</em>'.
	 * @see com.odcgroup.process.model.StartEvent
	 * @generated
	 */
	EClass getStartEvent();

	/**
	 * Returns the meta object for class '{@link com.odcgroup.process.model.EndEvent <em>End Event</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>End Event</em>'.
	 * @see com.odcgroup.process.model.EndEvent
	 * @generated
	 */
	EClass getEndEvent();

	/**
	 * Returns the meta object for class '{@link com.odcgroup.process.model.Task <em>Task</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Task</em>'.
	 * @see com.odcgroup.process.model.Task
	 * @generated
	 */
	EClass getTask();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.process.model.Task#isInitial <em>Initial</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Initial</em>'.
	 * @see com.odcgroup.process.model.Task#isInitial()
	 * @see #getTask()
	 * @generated
	 */
	EAttribute getTask_Initial();

	/**
	 * Returns the meta object for the containment reference list '{@link com.odcgroup.process.model.Task#getTranslations <em>Translations</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Translations</em>'.
	 * @see com.odcgroup.process.model.Task#getTranslations()
	 * @see #getTask()
	 * @generated
	 */
	EReference getTask_Translations();

	/**
	 * Returns the meta object for class '{@link com.odcgroup.process.model.UserTask <em>User Task</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>User Task</em>'.
	 * @see com.odcgroup.process.model.UserTask
	 * @generated
	 */
	EClass getUserTask();

	/**
	 * Returns the meta object for the containment reference '{@link com.odcgroup.process.model.UserTask#getPageflow <em>Pageflow</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Pageflow</em>'.
	 * @see com.odcgroup.process.model.UserTask#getPageflow()
	 * @see #getUserTask()
	 * @generated
	 */
	EReference getUserTask_Pageflow();

	/**
	 * Returns the meta object for the containment reference '{@link com.odcgroup.process.model.UserTask#getRule <em>Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Rule</em>'.
	 * @see com.odcgroup.process.model.UserTask#getRule()
	 * @see #getUserTask()
	 * @generated
	 */
	EReference getUserTask_Rule();

	/**
	 * Returns the meta object for class '{@link com.odcgroup.process.model.ServiceTask <em>Service Task</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Service Task</em>'.
	 * @see com.odcgroup.process.model.ServiceTask
	 * @generated
	 */
	EClass getServiceTask();

	/**
	 * Returns the meta object for the containment reference '{@link com.odcgroup.process.model.ServiceTask#getService <em>Service</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Service</em>'.
	 * @see com.odcgroup.process.model.ServiceTask#getService()
	 * @see #getServiceTask()
	 * @generated
	 */
	EReference getServiceTask_Service();

	/**
	 * Returns the meta object for class '{@link com.odcgroup.process.model.Gateway <em>Gateway</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Gateway</em>'.
	 * @see com.odcgroup.process.model.Gateway
	 * @generated
	 */
	EClass getGateway();

	/**
	 * Returns the meta object for class '{@link com.odcgroup.process.model.Flow <em>Flow</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Flow</em>'.
	 * @see com.odcgroup.process.model.Flow
	 * @generated
	 */
	EClass getFlow();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.process.model.Flow#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see com.odcgroup.process.model.Flow#getName()
	 * @see #getFlow()
	 * @generated
	 */
	EAttribute getFlow_Name();

	/**
	 * Returns the meta object for the reference '{@link com.odcgroup.process.model.Flow#getSource <em>Source</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Source</em>'.
	 * @see com.odcgroup.process.model.Flow#getSource()
	 * @see #getFlow()
	 * @generated
	 */
	EReference getFlow_Source();

	/**
	 * Returns the meta object for the reference '{@link com.odcgroup.process.model.Flow#getTarget <em>Target</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Target</em>'.
	 * @see com.odcgroup.process.model.Flow#getTarget()
	 * @see #getFlow()
	 * @generated
	 */
	EReference getFlow_Target();

	/**
	 * Returns the meta object for class '{@link com.odcgroup.process.model.ComplexGateway <em>Complex Gateway</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Complex Gateway</em>'.
	 * @see com.odcgroup.process.model.ComplexGateway
	 * @generated
	 */
	EClass getComplexGateway();

	/**
	 * Returns the meta object for the containment reference '{@link com.odcgroup.process.model.ComplexGateway#getService <em>Service</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Service</em>'.
	 * @see com.odcgroup.process.model.ComplexGateway#getService()
	 * @see #getComplexGateway()
	 * @generated
	 */
	EReference getComplexGateway_Service();

	/**
	 * Returns the meta object for the containment reference '{@link com.odcgroup.process.model.ComplexGateway#getScript <em>Script</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Script</em>'.
	 * @see com.odcgroup.process.model.ComplexGateway#getScript()
	 * @see #getComplexGateway()
	 * @generated
	 */
	EReference getComplexGateway_Script();

	/**
	 * Returns the meta object for the containment reference '{@link com.odcgroup.process.model.ComplexGateway#getRule <em>Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Rule</em>'.
	 * @see com.odcgroup.process.model.ComplexGateway#getRule()
	 * @see #getComplexGateway()
	 * @generated
	 */
	EReference getComplexGateway_Rule();

	/**
	 * Returns the meta object for class '{@link com.odcgroup.process.model.ExclusiveMerge <em>Exclusive Merge</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Exclusive Merge</em>'.
	 * @see com.odcgroup.process.model.ExclusiveMerge
	 * @generated
	 */
	EClass getExclusiveMerge();

	/**
	 * Returns the meta object for class '{@link com.odcgroup.process.model.ParallelFork <em>Parallel Fork</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Parallel Fork</em>'.
	 * @see com.odcgroup.process.model.ParallelFork
	 * @generated
	 */
	EClass getParallelFork();

	/**
	 * Returns the meta object for class '{@link com.odcgroup.process.model.ParallelMerge <em>Parallel Merge</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Parallel Merge</em>'.
	 * @see com.odcgroup.process.model.ParallelMerge
	 * @generated
	 */
	EClass getParallelMerge();

	/**
	 * Returns the meta object for class '{@link com.odcgroup.process.model.Assignee <em>Assignee</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Assignee</em>'.
	 * @see com.odcgroup.process.model.Assignee
	 * @generated
	 */
	EClass getAssignee();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.process.model.Assignee#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see com.odcgroup.process.model.Assignee#getName()
	 * @see #getAssignee()
	 * @generated
	 */
	EAttribute getAssignee_Name();

	/**
	 * Returns the meta object for class '{@link com.odcgroup.process.model.Pageflow <em>Pageflow</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Pageflow</em>'.
	 * @see com.odcgroup.process.model.Pageflow
	 * @generated
	 */
	EClass getPageflow();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.process.model.Pageflow#getURI <em>URI</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>URI</em>'.
	 * @see com.odcgroup.process.model.Pageflow#getURI()
	 * @see #getPageflow()
	 * @generated
	 */
	EAttribute getPageflow_URI();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.process.model.Pageflow#getDescription <em>Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Description</em>'.
	 * @see com.odcgroup.process.model.Pageflow#getDescription()
	 * @see #getPageflow()
	 * @generated
	 */
	EAttribute getPageflow_Description();

	/**
	 * Returns the meta object for the containment reference list '{@link com.odcgroup.process.model.Pageflow#getProperty <em>Property</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Property</em>'.
	 * @see com.odcgroup.process.model.Pageflow#getProperty()
	 * @see #getPageflow()
	 * @generated
	 */
	EReference getPageflow_Property();

	/**
	 * Returns the meta object for class '{@link com.odcgroup.process.model.Service <em>Service</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Service</em>'.
	 * @see com.odcgroup.process.model.Service
	 * @generated
	 */
	EClass getService();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.process.model.Service#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see com.odcgroup.process.model.Service#getName()
	 * @see #getService()
	 * @generated
	 */
	EAttribute getService_Name();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.process.model.Service#getDescription <em>Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Description</em>'.
	 * @see com.odcgroup.process.model.Service#getDescription()
	 * @see #getService()
	 * @generated
	 */
	EAttribute getService_Description();

	/**
	 * Returns the meta object for the containment reference list '{@link com.odcgroup.process.model.Service#getProperty <em>Property</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Property</em>'.
	 * @see com.odcgroup.process.model.Service#getProperty()
	 * @see #getService()
	 * @generated
	 */
	EReference getService_Property();

	/**
	 * Returns the meta object for class '{@link com.odcgroup.process.model.Rule <em>Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Rule</em>'.
	 * @see com.odcgroup.process.model.Rule
	 * @generated
	 */
	EClass getRule();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.process.model.Rule#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see com.odcgroup.process.model.Rule#getName()
	 * @see #getRule()
	 * @generated
	 */
	EAttribute getRule_Name();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.process.model.Rule#getDescription <em>Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Description</em>'.
	 * @see com.odcgroup.process.model.Rule#getDescription()
	 * @see #getRule()
	 * @generated
	 */
	EAttribute getRule_Description();

	/**
	 * Returns the meta object for the containment reference list '{@link com.odcgroup.process.model.Rule#getProperty <em>Property</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Property</em>'.
	 * @see com.odcgroup.process.model.Rule#getProperty()
	 * @see #getRule()
	 * @generated
	 */
	EReference getRule_Property();

	/**
	 * Returns the meta object for class '{@link com.odcgroup.process.model.Script <em>Script</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Script</em>'.
	 * @see com.odcgroup.process.model.Script
	 * @generated
	 */
	EClass getScript();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.process.model.Script#getLanguage <em>Language</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Language</em>'.
	 * @see com.odcgroup.process.model.Script#getLanguage()
	 * @see #getScript()
	 * @generated
	 */
	EAttribute getScript_Language();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.process.model.Script#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see com.odcgroup.process.model.Script#getValue()
	 * @see #getScript()
	 * @generated
	 */
	EAttribute getScript_Value();

	/**
	 * Returns the meta object for class '{@link com.odcgroup.process.model.Property <em>Property</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Property</em>'.
	 * @see com.odcgroup.process.model.Property
	 * @generated
	 */
	EClass getProperty();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.process.model.Property#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see com.odcgroup.process.model.Property#getName()
	 * @see #getProperty()
	 * @generated
	 */
	EAttribute getProperty_Name();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.process.model.Property#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see com.odcgroup.process.model.Property#getValue()
	 * @see #getProperty()
	 * @generated
	 */
	EAttribute getProperty_Value();

	/**
	 * Returns the meta object for class '{@link com.odcgroup.process.model.Translation <em>Translation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Translation</em>'.
	 * @see com.odcgroup.process.model.Translation
	 * @generated
	 */
	EClass getTranslation();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.process.model.Translation#getKind <em>Kind</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Kind</em>'.
	 * @see com.odcgroup.process.model.Translation#getKind()
	 * @see #getTranslation()
	 * @generated
	 */
	EAttribute getTranslation_Kind();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.process.model.Translation#getLanguage <em>Language</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Language</em>'.
	 * @see com.odcgroup.process.model.Translation#getLanguage()
	 * @see #getTranslation()
	 * @generated
	 */
	EAttribute getTranslation_Language();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.process.model.Translation#getMessage <em>Message</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Message</em>'.
	 * @see com.odcgroup.process.model.Translation#getMessage()
	 * @see #getTranslation()
	 * @generated
	 */
	EAttribute getTranslation_Message();

	/**
	 * Returns the meta object for enum '{@link com.odcgroup.process.model.ScriptingLanguage <em>Scripting Language</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Scripting Language</em>'.
	 * @see com.odcgroup.process.model.ScriptingLanguage
	 * @generated
	 */
	EEnum getScriptingLanguage();

	/**
	 * Returns the meta object for enum '{@link com.odcgroup.process.model.TranslationKind <em>Translation Kind</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Translation Kind</em>'.
	 * @see com.odcgroup.process.model.TranslationKind
	 * @generated
	 */
	EEnum getTranslationKind();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	ProcessFactory getProcessFactory();

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
		 * The meta object literal for the '{@link com.odcgroup.process.model.impl.ProcessImpl <em>Process</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.odcgroup.process.model.impl.ProcessImpl
		 * @see com.odcgroup.process.model.impl.ProcessPackageImpl#getProcess()
		 * @generated
		 */
		EClass PROCESS = eINSTANCE.getProcess();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROCESS__NAME = eINSTANCE.getProcess_Name();

		/**
		 * The meta object literal for the '<em><b>Comment</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROCESS__COMMENT = eINSTANCE.getProcess_Comment();

		/**
		 * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROCESS__DESCRIPTION = eINSTANCE.getProcess_Description();

		/**
		 * The meta object literal for the '<em><b>Display Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROCESS__DISPLAY_NAME = eINSTANCE.getProcess_DisplayName();

		/**
		 * The meta object literal for the '<em><b>Pools</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROCESS__POOLS = eINSTANCE.getProcess_Pools();

		/**
		 * The meta object literal for the '<em><b>Transitions</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROCESS__TRANSITIONS = eINSTANCE.getProcess_Transitions();

		/**
		 * The meta object literal for the '<em><b>Inverted</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROCESS__INVERTED = eINSTANCE.getProcess_Inverted();

		/**
		 * The meta object literal for the '<em><b>Filename</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROCESS__FILENAME = eINSTANCE.getProcess_Filename();

		/**
		 * The meta object literal for the '<em><b>Metamodel Version</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROCESS__METAMODEL_VERSION = eINSTANCE.getProcess_MetamodelVersion();

		/**
		 * The meta object literal for the '<em><b>Translations</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROCESS__TRANSLATIONS = eINSTANCE.getProcess_Translations();

		/**
		 * The meta object literal for the '{@link com.odcgroup.process.model.impl.NamedElementImpl <em>Named Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.odcgroup.process.model.impl.NamedElementImpl
		 * @see com.odcgroup.process.model.impl.ProcessPackageImpl#getNamedElement()
		 * @generated
		 */
		EClass NAMED_ELEMENT = eINSTANCE.getNamedElement();

		/**
		 * The meta object literal for the '<em><b>ID</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute NAMED_ELEMENT__ID = eINSTANCE.getNamedElement_ID();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute NAMED_ELEMENT__NAME = eINSTANCE.getNamedElement_Name();

		/**
		 * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute NAMED_ELEMENT__DESCRIPTION = eINSTANCE.getNamedElement_Description();

		/**
		 * The meta object literal for the '<em><b>TDescription</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute NAMED_ELEMENT__TDESCRIPTION = eINSTANCE.getNamedElement_TDescription();

		/**
		 * The meta object literal for the '{@link com.odcgroup.process.model.impl.ProcessItemImpl <em>Item</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.odcgroup.process.model.impl.ProcessItemImpl
		 * @see com.odcgroup.process.model.impl.ProcessPackageImpl#getProcessItem()
		 * @generated
		 */
		EClass PROCESS_ITEM = eINSTANCE.getProcessItem();

		/**
		 * The meta object literal for the '{@link com.odcgroup.process.model.impl.PoolImpl <em>Pool</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.odcgroup.process.model.impl.PoolImpl
		 * @see com.odcgroup.process.model.impl.ProcessPackageImpl#getPool()
		 * @generated
		 */
		EClass POOL = eINSTANCE.getPool();

		/**
		 * The meta object literal for the '<em><b>Tech Desc</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute POOL__TECH_DESC = eINSTANCE.getPool_TechDesc();

		/**
		 * The meta object literal for the '<em><b>Assignee</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference POOL__ASSIGNEE = eINSTANCE.getPool_Assignee();

		/**
		 * The meta object literal for the '<em><b>Assignee By Service</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference POOL__ASSIGNEE_BY_SERVICE = eINSTANCE.getPool_AssigneeByService();

		/**
		 * The meta object literal for the '<em><b>Start</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference POOL__START = eINSTANCE.getPool_Start();

		/**
		 * The meta object literal for the '<em><b>End</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference POOL__END = eINSTANCE.getPool_End();

		/**
		 * The meta object literal for the '<em><b>Tasks</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference POOL__TASKS = eINSTANCE.getPool_Tasks();

		/**
		 * The meta object literal for the '<em><b>Gateways</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference POOL__GATEWAYS = eINSTANCE.getPool_Gateways();

		/**
		 * The meta object literal for the '{@link com.odcgroup.process.model.impl.StartEventImpl <em>Start Event</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.odcgroup.process.model.impl.StartEventImpl
		 * @see com.odcgroup.process.model.impl.ProcessPackageImpl#getStartEvent()
		 * @generated
		 */
		EClass START_EVENT = eINSTANCE.getStartEvent();

		/**
		 * The meta object literal for the '{@link com.odcgroup.process.model.impl.EndEventImpl <em>End Event</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.odcgroup.process.model.impl.EndEventImpl
		 * @see com.odcgroup.process.model.impl.ProcessPackageImpl#getEndEvent()
		 * @generated
		 */
		EClass END_EVENT = eINSTANCE.getEndEvent();

		/**
		 * The meta object literal for the '{@link com.odcgroup.process.model.impl.TaskImpl <em>Task</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.odcgroup.process.model.impl.TaskImpl
		 * @see com.odcgroup.process.model.impl.ProcessPackageImpl#getTask()
		 * @generated
		 */
		EClass TASK = eINSTANCE.getTask();

		/**
		 * The meta object literal for the '<em><b>Initial</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TASK__INITIAL = eINSTANCE.getTask_Initial();

		/**
		 * The meta object literal for the '<em><b>Translations</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TASK__TRANSLATIONS = eINSTANCE.getTask_Translations();

		/**
		 * The meta object literal for the '{@link com.odcgroup.process.model.impl.UserTaskImpl <em>User Task</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.odcgroup.process.model.impl.UserTaskImpl
		 * @see com.odcgroup.process.model.impl.ProcessPackageImpl#getUserTask()
		 * @generated
		 */
		EClass USER_TASK = eINSTANCE.getUserTask();

		/**
		 * The meta object literal for the '<em><b>Pageflow</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference USER_TASK__PAGEFLOW = eINSTANCE.getUserTask_Pageflow();

		/**
		 * The meta object literal for the '<em><b>Rule</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference USER_TASK__RULE = eINSTANCE.getUserTask_Rule();

		/**
		 * The meta object literal for the '{@link com.odcgroup.process.model.impl.ServiceTaskImpl <em>Service Task</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.odcgroup.process.model.impl.ServiceTaskImpl
		 * @see com.odcgroup.process.model.impl.ProcessPackageImpl#getServiceTask()
		 * @generated
		 */
		EClass SERVICE_TASK = eINSTANCE.getServiceTask();

		/**
		 * The meta object literal for the '<em><b>Service</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SERVICE_TASK__SERVICE = eINSTANCE.getServiceTask_Service();

		/**
		 * The meta object literal for the '{@link com.odcgroup.process.model.impl.GatewayImpl <em>Gateway</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.odcgroup.process.model.impl.GatewayImpl
		 * @see com.odcgroup.process.model.impl.ProcessPackageImpl#getGateway()
		 * @generated
		 */
		EClass GATEWAY = eINSTANCE.getGateway();

		/**
		 * The meta object literal for the '{@link com.odcgroup.process.model.impl.FlowImpl <em>Flow</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.odcgroup.process.model.impl.FlowImpl
		 * @see com.odcgroup.process.model.impl.ProcessPackageImpl#getFlow()
		 * @generated
		 */
		EClass FLOW = eINSTANCE.getFlow();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FLOW__NAME = eINSTANCE.getFlow_Name();

		/**
		 * The meta object literal for the '<em><b>Source</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FLOW__SOURCE = eINSTANCE.getFlow_Source();

		/**
		 * The meta object literal for the '<em><b>Target</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FLOW__TARGET = eINSTANCE.getFlow_Target();

		/**
		 * The meta object literal for the '{@link com.odcgroup.process.model.impl.ComplexGatewayImpl <em>Complex Gateway</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.odcgroup.process.model.impl.ComplexGatewayImpl
		 * @see com.odcgroup.process.model.impl.ProcessPackageImpl#getComplexGateway()
		 * @generated
		 */
		EClass COMPLEX_GATEWAY = eINSTANCE.getComplexGateway();

		/**
		 * The meta object literal for the '<em><b>Service</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference COMPLEX_GATEWAY__SERVICE = eINSTANCE.getComplexGateway_Service();

		/**
		 * The meta object literal for the '<em><b>Script</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference COMPLEX_GATEWAY__SCRIPT = eINSTANCE.getComplexGateway_Script();

		/**
		 * The meta object literal for the '<em><b>Rule</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference COMPLEX_GATEWAY__RULE = eINSTANCE.getComplexGateway_Rule();

		/**
		 * The meta object literal for the '{@link com.odcgroup.process.model.impl.ExclusiveMergeImpl <em>Exclusive Merge</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.odcgroup.process.model.impl.ExclusiveMergeImpl
		 * @see com.odcgroup.process.model.impl.ProcessPackageImpl#getExclusiveMerge()
		 * @generated
		 */
		EClass EXCLUSIVE_MERGE = eINSTANCE.getExclusiveMerge();

		/**
		 * The meta object literal for the '{@link com.odcgroup.process.model.impl.ParallelForkImpl <em>Parallel Fork</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.odcgroup.process.model.impl.ParallelForkImpl
		 * @see com.odcgroup.process.model.impl.ProcessPackageImpl#getParallelFork()
		 * @generated
		 */
		EClass PARALLEL_FORK = eINSTANCE.getParallelFork();

		/**
		 * The meta object literal for the '{@link com.odcgroup.process.model.impl.ParallelMergeImpl <em>Parallel Merge</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.odcgroup.process.model.impl.ParallelMergeImpl
		 * @see com.odcgroup.process.model.impl.ProcessPackageImpl#getParallelMerge()
		 * @generated
		 */
		EClass PARALLEL_MERGE = eINSTANCE.getParallelMerge();

		/**
		 * The meta object literal for the '{@link com.odcgroup.process.model.impl.AssigneeImpl <em>Assignee</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.odcgroup.process.model.impl.AssigneeImpl
		 * @see com.odcgroup.process.model.impl.ProcessPackageImpl#getAssignee()
		 * @generated
		 */
		EClass ASSIGNEE = eINSTANCE.getAssignee();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ASSIGNEE__NAME = eINSTANCE.getAssignee_Name();

		/**
		 * The meta object literal for the '{@link com.odcgroup.process.model.impl.PageflowImpl <em>Pageflow</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.odcgroup.process.model.impl.PageflowImpl
		 * @see com.odcgroup.process.model.impl.ProcessPackageImpl#getPageflow()
		 * @generated
		 */
		EClass PAGEFLOW = eINSTANCE.getPageflow();

		/**
		 * The meta object literal for the '<em><b>URI</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PAGEFLOW__URI = eINSTANCE.getPageflow_URI();

		/**
		 * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PAGEFLOW__DESCRIPTION = eINSTANCE.getPageflow_Description();

		/**
		 * The meta object literal for the '<em><b>Property</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PAGEFLOW__PROPERTY = eINSTANCE.getPageflow_Property();

		/**
		 * The meta object literal for the '{@link com.odcgroup.process.model.impl.ServiceImpl <em>Service</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.odcgroup.process.model.impl.ServiceImpl
		 * @see com.odcgroup.process.model.impl.ProcessPackageImpl#getService()
		 * @generated
		 */
		EClass SERVICE = eINSTANCE.getService();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SERVICE__NAME = eINSTANCE.getService_Name();

		/**
		 * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SERVICE__DESCRIPTION = eINSTANCE.getService_Description();

		/**
		 * The meta object literal for the '<em><b>Property</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SERVICE__PROPERTY = eINSTANCE.getService_Property();

		/**
		 * The meta object literal for the '{@link com.odcgroup.process.model.impl.RuleImpl <em>Rule</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.odcgroup.process.model.impl.RuleImpl
		 * @see com.odcgroup.process.model.impl.ProcessPackageImpl#getRule()
		 * @generated
		 */
		EClass RULE = eINSTANCE.getRule();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RULE__NAME = eINSTANCE.getRule_Name();

		/**
		 * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RULE__DESCRIPTION = eINSTANCE.getRule_Description();

		/**
		 * The meta object literal for the '<em><b>Property</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RULE__PROPERTY = eINSTANCE.getRule_Property();

		/**
		 * The meta object literal for the '{@link com.odcgroup.process.model.impl.ScriptImpl <em>Script</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.odcgroup.process.model.impl.ScriptImpl
		 * @see com.odcgroup.process.model.impl.ProcessPackageImpl#getScript()
		 * @generated
		 */
		EClass SCRIPT = eINSTANCE.getScript();

		/**
		 * The meta object literal for the '<em><b>Language</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SCRIPT__LANGUAGE = eINSTANCE.getScript_Language();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SCRIPT__VALUE = eINSTANCE.getScript_Value();

		/**
		 * The meta object literal for the '{@link com.odcgroup.process.model.impl.PropertyImpl <em>Property</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.odcgroup.process.model.impl.PropertyImpl
		 * @see com.odcgroup.process.model.impl.ProcessPackageImpl#getProperty()
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
		 * The meta object literal for the '{@link com.odcgroup.process.model.impl.TranslationImpl <em>Translation</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.odcgroup.process.model.impl.TranslationImpl
		 * @see com.odcgroup.process.model.impl.ProcessPackageImpl#getTranslation()
		 * @generated
		 */
		EClass TRANSLATION = eINSTANCE.getTranslation();

		/**
		 * The meta object literal for the '<em><b>Kind</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TRANSLATION__KIND = eINSTANCE.getTranslation_Kind();

		/**
		 * The meta object literal for the '<em><b>Language</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TRANSLATION__LANGUAGE = eINSTANCE.getTranslation_Language();

		/**
		 * The meta object literal for the '<em><b>Message</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TRANSLATION__MESSAGE = eINSTANCE.getTranslation_Message();

		/**
		 * The meta object literal for the '{@link com.odcgroup.process.model.ScriptingLanguage <em>Scripting Language</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.odcgroup.process.model.ScriptingLanguage
		 * @see com.odcgroup.process.model.impl.ProcessPackageImpl#getScriptingLanguage()
		 * @generated
		 */
		EEnum SCRIPTING_LANGUAGE = eINSTANCE.getScriptingLanguage();

		/**
		 * The meta object literal for the '{@link com.odcgroup.process.model.TranslationKind <em>Translation Kind</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.odcgroup.process.model.TranslationKind
		 * @see com.odcgroup.process.model.impl.ProcessPackageImpl#getTranslationKind()
		 * @generated
		 */
		EEnum TRANSLATION_KIND = eINSTANCE.getTranslationKind();

	}

} //ProcessPackage
