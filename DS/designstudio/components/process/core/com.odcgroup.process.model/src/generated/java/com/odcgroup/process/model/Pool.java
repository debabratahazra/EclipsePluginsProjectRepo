/**
 * Odyssey Financial Technologies
 *
 * $Id$
 */
package com.odcgroup.process.model;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Pool</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.odcgroup.process.model.Pool#getTechDesc <em>Tech Desc</em>}</li>
 *   <li>{@link com.odcgroup.process.model.Pool#getAssignee <em>Assignee</em>}</li>
 *   <li>{@link com.odcgroup.process.model.Pool#getAssigneeByService <em>Assignee By Service</em>}</li>
 *   <li>{@link com.odcgroup.process.model.Pool#getStart <em>Start</em>}</li>
 *   <li>{@link com.odcgroup.process.model.Pool#getEnd <em>End</em>}</li>
 *   <li>{@link com.odcgroup.process.model.Pool#getTasks <em>Tasks</em>}</li>
 *   <li>{@link com.odcgroup.process.model.Pool#getGateways <em>Gateways</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.odcgroup.process.model.ProcessPackage#getPool()
 * @model
 * @generated
 */
public interface Pool extends NamedElement {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String copyright = "Odyssey Financial Technologies";

	/**
	 * Returns the value of the '<em><b>Tech Desc</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Tech Desc</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Tech Desc</em>' attribute.
	 * @see #setTechDesc(String)
	 * @see com.odcgroup.process.model.ProcessPackage#getPool_TechDesc()
	 * @model unique="false"
	 * @generated
	 */
	String getTechDesc();

	/**
	 * Sets the value of the '{@link com.odcgroup.process.model.Pool#getTechDesc <em>Tech Desc</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Tech Desc</em>' attribute.
	 * @see #getTechDesc()
	 * @generated
	 */
	void setTechDesc(String value);

	/**
	 * Returns the value of the '<em><b>Assignee</b></em>' containment reference list.
	 * The list contents are of type {@link com.odcgroup.process.model.Assignee}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Assignee</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Assignee</em>' containment reference list.
	 * @see com.odcgroup.process.model.ProcessPackage#getPool_Assignee()
	 * @model type="com.odcgroup.process.model.Assignee" containment="true"
	 * @generated
	 */
	EList getAssignee();

	/**
	 * Returns the value of the '<em><b>Assignee By Service</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Assignee By Service</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Assignee By Service</em>' containment reference.
	 * @see #setAssigneeByService(Service)
	 * @see com.odcgroup.process.model.ProcessPackage#getPool_AssigneeByService()
	 * @model containment="true"
	 * @generated
	 */
	Service getAssigneeByService();

	/**
	 * Sets the value of the '{@link com.odcgroup.process.model.Pool#getAssigneeByService <em>Assignee By Service</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Assignee By Service</em>' containment reference.
	 * @see #getAssigneeByService()
	 * @generated
	 */
	void setAssigneeByService(Service value);

	/**
	 * Returns the value of the '<em><b>Start</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Start</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Start</em>' containment reference.
	 * @see #setStart(StartEvent)
	 * @see com.odcgroup.process.model.ProcessPackage#getPool_Start()
	 * @model containment="true"
	 * @generated
	 */
	StartEvent getStart();

	/**
	 * Sets the value of the '{@link com.odcgroup.process.model.Pool#getStart <em>Start</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Start</em>' containment reference.
	 * @see #getStart()
	 * @generated
	 */
	void setStart(StartEvent value);

	/**
	 * Returns the value of the '<em><b>End</b></em>' containment reference list.
	 * The list contents are of type {@link com.odcgroup.process.model.EndEvent}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>End</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>End</em>' containment reference list.
	 * @see com.odcgroup.process.model.ProcessPackage#getPool_End()
	 * @model type="com.odcgroup.process.model.EndEvent" containment="true"
	 * @generated
	 */
	EList getEnd();

	/**
	 * Returns the value of the '<em><b>Tasks</b></em>' containment reference list.
	 * The list contents are of type {@link com.odcgroup.process.model.Task}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Tasks</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Tasks</em>' containment reference list.
	 * @see com.odcgroup.process.model.ProcessPackage#getPool_Tasks()
	 * @model type="com.odcgroup.process.model.Task" containment="true" required="true"
	 * @generated
	 */
	EList getTasks();

	/**
	 * Returns the value of the '<em><b>Gateways</b></em>' containment reference list.
	 * The list contents are of type {@link com.odcgroup.process.model.Gateway}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Gateways</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Gateways</em>' containment reference list.
	 * @see com.odcgroup.process.model.ProcessPackage#getPool_Gateways()
	 * @model type="com.odcgroup.process.model.Gateway" containment="true"
	 * @generated
	 */
	EList getGateways();

} // Pool
