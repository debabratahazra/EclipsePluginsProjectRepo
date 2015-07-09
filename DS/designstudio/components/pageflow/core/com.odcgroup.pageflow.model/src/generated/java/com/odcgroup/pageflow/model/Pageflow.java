/**
 * ODCGROUP
 *
 * $Id$
 */
package com.odcgroup.pageflow.model;

import java.util.Date;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Pageflow</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.odcgroup.pageflow.model.Pageflow#getStates <em>States</em>}</li>
 *   <li>{@link com.odcgroup.pageflow.model.Pageflow#getDesc <em>Desc</em>}</li>
 *   <li>{@link com.odcgroup.pageflow.model.Pageflow#getName <em>Name</em>}</li>
 *   <li>{@link com.odcgroup.pageflow.model.Pageflow#getAbortView <em>Abort View</em>}</li>
 *   <li>{@link com.odcgroup.pageflow.model.Pageflow#getErrorView <em>Error View</em>}</li>
 *   <li>{@link com.odcgroup.pageflow.model.Pageflow#getTransitions <em>Transitions</em>}</li>
 *   <li>{@link com.odcgroup.pageflow.model.Pageflow#getProperty <em>Property</em>}</li>
 *   <li>{@link com.odcgroup.pageflow.model.Pageflow#getId <em>Id</em>}</li>
 *   <li>{@link com.odcgroup.pageflow.model.Pageflow#getFileName <em>File Name</em>}</li>
 *   <li>{@link com.odcgroup.pageflow.model.Pageflow#getTechDesc <em>Tech Desc</em>}</li>
 *   <li>{@link com.odcgroup.pageflow.model.Pageflow#getMetamodelVersion <em>Metamodel Version</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.odcgroup.pageflow.model.PageflowPackage#getPageflow()
 * @model
 * @generated
 */
public interface Pageflow extends EObject {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String copyright = "ODCGROUP";

	/**
	 * Returns the value of the '<em><b>States</b></em>' containment reference list.
	 * The list contents are of type {@link com.odcgroup.pageflow.model.State}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>States</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>States</em>' containment reference list.
	 * @see com.odcgroup.pageflow.model.PageflowPackage#getPageflow_States()
	 * @model type="com.odcgroup.pageflow.model.State" containment="true" lower="2"
	 * @generated
	 */
	EList getStates();

	/**
	 * Returns the value of the '<em><b>Desc</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Desc</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Desc</em>' attribute.
	 * @see #setDesc(String)
	 * @see com.odcgroup.pageflow.model.PageflowPackage#getPageflow_Desc()
	 * @model unique="false"
	 * @generated
	 */
	String getDesc();

	/**
	 * Sets the value of the '{@link com.odcgroup.pageflow.model.Pageflow#getDesc <em>Desc</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Desc</em>' attribute.
	 * @see #getDesc()
	 * @generated
	 */
	void setDesc(String value);

	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see com.odcgroup.pageflow.model.PageflowPackage#getPageflow_Name()
	 * @model required="true"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link com.odcgroup.pageflow.model.Pageflow#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Abort View</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Abort View</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Abort View</em>' containment reference.
	 * @see #setAbortView(View)
	 * @see com.odcgroup.pageflow.model.PageflowPackage#getPageflow_AbortView()
	 * @model containment="true" required="true"
	 * @generated
	 */
	View getAbortView();

	/**
	 * Sets the value of the '{@link com.odcgroup.pageflow.model.Pageflow#getAbortView <em>Abort View</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Abort View</em>' containment reference.
	 * @see #getAbortView()
	 * @generated
	 */
	void setAbortView(View value);

	/**
	 * Returns the value of the '<em><b>Error View</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Error View</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Error View</em>' containment reference.
	 * @see #setErrorView(View)
	 * @see com.odcgroup.pageflow.model.PageflowPackage#getPageflow_ErrorView()
	 * @model containment="true" required="true"
	 * @generated
	 */
	View getErrorView();

	/**
	 * Sets the value of the '{@link com.odcgroup.pageflow.model.Pageflow#getErrorView <em>Error View</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Error View</em>' containment reference.
	 * @see #getErrorView()
	 * @generated
	 */
	void setErrorView(View value);

	/**
	 * Returns the value of the '<em><b>Transitions</b></em>' containment reference list.
	 * The list contents are of type {@link com.odcgroup.pageflow.model.Transition}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Transitions</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Transitions</em>' containment reference list.
	 * @see com.odcgroup.pageflow.model.PageflowPackage#getPageflow_Transitions()
	 * @model type="com.odcgroup.pageflow.model.Transition" containment="true"
	 * @generated
	 */
	EList getTransitions();

	/**
	 * Returns the value of the '<em><b>Property</b></em>' containment reference list.
	 * The list contents are of type {@link com.odcgroup.pageflow.model.Property}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Property</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Property</em>' containment reference list.
	 * @see com.odcgroup.pageflow.model.PageflowPackage#getPageflow_Property()
	 * @model type="com.odcgroup.pageflow.model.Property" containment="true"
	 * @generated
	 */
	EList getProperty();

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
	 * @see com.odcgroup.pageflow.model.PageflowPackage#getPageflow_Id()
	 * @model default="" required="true"
	 * @generated
	 */
	String getId();

	/**
	 * Sets the value of the '{@link com.odcgroup.pageflow.model.Pageflow#getId <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Id</em>' attribute.
	 * @see #getId()
	 * @generated
	 */
	void setId(String value);

	/**
	 * Returns the value of the '<em><b>File Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>File Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>File Name</em>' attribute.
	 * @see #setFileName(String)
	 * @see com.odcgroup.pageflow.model.PageflowPackage#getPageflow_FileName()
	 * @model unique="false" required="true"
	 * @generated
	 */
	String getFileName();

	/**
	 * Sets the value of the '{@link com.odcgroup.pageflow.model.Pageflow#getFileName <em>File Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>File Name</em>' attribute.
	 * @see #getFileName()
	 * @generated
	 */
	void setFileName(String value);

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
	 * @see com.odcgroup.pageflow.model.PageflowPackage#getPageflow_TechDesc()
	 * @model unique="false"
	 * @generated
	 */
	String getTechDesc();

	/**
	 * Sets the value of the '{@link com.odcgroup.pageflow.model.Pageflow#getTechDesc <em>Tech Desc</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Tech Desc</em>' attribute.
	 * @see #getTechDesc()
	 * @generated
	 */
	void setTechDesc(String value);

	/**
	 * Returns the value of the '<em><b>Metamodel Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Metamodel Version</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Metamodel Version</em>' attribute.
	 * @see #setMetamodelVersion(String)
	 * @see com.odcgroup.pageflow.model.PageflowPackage#getPageflow_MetamodelVersion()
	 * @model unique="false"
	 * @generated
	 */
	String getMetamodelVersion();

	/**
	 * Sets the value of the '{@link com.odcgroup.pageflow.model.Pageflow#getMetamodelVersion <em>Metamodel Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Metamodel Version</em>' attribute.
	 * @see #getMetamodelVersion()
	 * @generated
	 */
	void setMetamodelVersion(String value);

} // Pageflow
