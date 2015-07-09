/**
 * Odyssey Financial Technologies
 *
 * $Id$
 */
package com.odcgroup.process.model;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Complex Gateway</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.odcgroup.process.model.ComplexGateway#getService <em>Service</em>}</li>
 *   <li>{@link com.odcgroup.process.model.ComplexGateway#getScript <em>Script</em>}</li>
 *   <li>{@link com.odcgroup.process.model.ComplexGateway#getRule <em>Rule</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.odcgroup.process.model.ProcessPackage#getComplexGateway()
 * @model
 * @generated
 */
public interface ComplexGateway extends Gateway {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String copyright = "Odyssey Financial Technologies";

	/**
	 * Returns the value of the '<em><b>Service</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Service</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Service</em>' containment reference.
	 * @see #setService(Service)
	 * @see com.odcgroup.process.model.ProcessPackage#getComplexGateway_Service()
	 * @model containment="true"
	 * @generated
	 */
	Service getService();

	/**
	 * Sets the value of the '{@link com.odcgroup.process.model.ComplexGateway#getService <em>Service</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Service</em>' containment reference.
	 * @see #getService()
	 * @generated
	 */
	void setService(Service value);

	/**
	 * Returns the value of the '<em><b>Script</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Script</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Script</em>' containment reference.
	 * @see #setScript(Script)
	 * @see com.odcgroup.process.model.ProcessPackage#getComplexGateway_Script()
	 * @model containment="true"
	 * @generated
	 */
	Script getScript();

	/**
	 * Sets the value of the '{@link com.odcgroup.process.model.ComplexGateway#getScript <em>Script</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Script</em>' containment reference.
	 * @see #getScript()
	 * @generated
	 */
	void setScript(Script value);

	/**
	 * Returns the value of the '<em><b>Rule</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Rule</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Rule</em>' containment reference.
	 * @see #setRule(Rule)
	 * @see com.odcgroup.process.model.ProcessPackage#getComplexGateway_Rule()
	 * @model containment="true"
	 * @generated
	 */
	Rule getRule();

	/**
	 * Sets the value of the '{@link com.odcgroup.process.model.ComplexGateway#getRule <em>Rule</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Rule</em>' containment reference.
	 * @see #getRule()
	 * @generated
	 */
	void setRule(Rule value);

} // ComplexGateway
