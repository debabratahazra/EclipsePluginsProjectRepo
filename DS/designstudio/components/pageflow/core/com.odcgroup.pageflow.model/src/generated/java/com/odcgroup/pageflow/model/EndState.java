/**
 * ODCGROUP
 *
 * $Id$
 */
package com.odcgroup.pageflow.model;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>End State</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.odcgroup.pageflow.model.EndState#getExitUrl <em>Exit Url</em>}</li>
 *   <li>{@link com.odcgroup.pageflow.model.EndState#getId <em>Id</em>}</li>
 *   <li>{@link com.odcgroup.pageflow.model.EndState#getTechDesc <em>Tech Desc</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.odcgroup.pageflow.model.PageflowPackage#getEndState()
 * @model
 * @generated
 */
public interface EndState extends State {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String copyright = "ODCGROUP";

	/**
	 * Returns the value of the '<em><b>Exit Url</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Exit Url</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Exit Url</em>' attribute.
	 * @see #setExitUrl(String)
	 * @see com.odcgroup.pageflow.model.PageflowPackage#getEndState_ExitUrl()
	 * @model unique="false" required="true"
	 * @generated
	 */
	String getExitUrl();

	/**
	 * Sets the value of the '{@link com.odcgroup.pageflow.model.EndState#getExitUrl <em>Exit Url</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Exit Url</em>' attribute.
	 * @see #getExitUrl()
	 * @generated
	 */
	void setExitUrl(String value);

	/**
	 * Returns the value of the '<em><b>Id</b></em>' attribute.
	 * The default value is <code>""</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Id</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Id</em>' attribute.
	 * @see #setId(String)
	 * @see com.odcgroup.pageflow.model.PageflowPackage#getEndState_Id()
	 * @model default="" required="true"
	 * @generated
	 */
	String getId();

	/**
	 * Sets the value of the '{@link com.odcgroup.pageflow.model.EndState#getId <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Id</em>' attribute.
	 * @see #getId()
	 * @generated
	 */
	void setId(String value);

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
	 * @see com.odcgroup.pageflow.model.PageflowPackage#getEndState_TechDesc()
	 * @model unique="false"
	 * @generated
	 */
	String getTechDesc();

	/**
	 * Sets the value of the '{@link com.odcgroup.pageflow.model.EndState#getTechDesc <em>Tech Desc</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Tech Desc</em>' attribute.
	 * @see #getTechDesc()
	 * @generated
	 */
	void setTechDesc(String value);

} // EndState
