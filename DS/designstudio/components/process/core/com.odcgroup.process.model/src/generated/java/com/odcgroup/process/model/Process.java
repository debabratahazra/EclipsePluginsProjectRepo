/**
 * Odyssey Financial Technologies
 *
 * $Id$
 */
package com.odcgroup.process.model;

import java.util.Date;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Process</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.odcgroup.process.model.Process#getName <em>Name</em>}</li>
 *   <li>{@link com.odcgroup.process.model.Process#getComment <em>Comment</em>}</li>
 *   <li>{@link com.odcgroup.process.model.Process#getDescription <em>Description</em>}</li>
 *   <li>{@link com.odcgroup.process.model.Process#getDisplayName <em>Display Name</em>}</li>
 *   <li>{@link com.odcgroup.process.model.Process#getPools <em>Pools</em>}</li>
 *   <li>{@link com.odcgroup.process.model.Process#getTransitions <em>Transitions</em>}</li>
 *   <li>{@link com.odcgroup.process.model.Process#isInverted <em>Inverted</em>}</li>
 *   <li>{@link com.odcgroup.process.model.Process#getFilename <em>Filename</em>}</li>
 *   <li>{@link com.odcgroup.process.model.Process#getMetamodelVersion <em>Metamodel Version</em>}</li>
 *   <li>{@link com.odcgroup.process.model.Process#getTranslations <em>Translations</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.odcgroup.process.model.ProcessPackage#getProcess()
 * @model
 * @generated
 */
public interface Process extends EObject {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String copyright = "Odyssey Financial Technologies";

	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * The default value is <code>""</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see com.odcgroup.process.model.ProcessPackage#getProcess_Name()
	 * @model default="" unique="false" id="true" required="true"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link com.odcgroup.process.model.Process#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Comment</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Comment</em>' attribute.
	 * @see #setComment(String)
	 * @see com.odcgroup.process.model.ProcessPackage#getProcess_Comment()
	 * @model unique="false"
	 * @generated
	 */
	String getComment();

	/**
	 * Sets the value of the '{@link com.odcgroup.process.model.Process#getComment <em>Comment</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Comment</em>' attribute.
	 * @see #getComment()
	 * @generated
	 */
	void setComment(String value);

	/**
	 * Returns the value of the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Description</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Description</em>' attribute.
	 * @see #setDescription(String)
	 * @see com.odcgroup.process.model.ProcessPackage#getProcess_Description()
	 * @model unique="false"
	 * @generated
	 */
	String getDescription();

	/**
	 * Sets the value of the '{@link com.odcgroup.process.model.Process#getDescription <em>Description</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Description</em>' attribute.
	 * @see #getDescription()
	 * @generated
	 */
	void setDescription(String value);

	/**
	 * Returns the value of the '<em><b>Display Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Display Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Display Name</em>' attribute.
	 * @see #setDisplayName(String)
	 * @see com.odcgroup.process.model.ProcessPackage#getProcess_DisplayName()
	 * @model unique="false" required="true"
	 * @generated
	 */
	String getDisplayName();

	/**
	 * Sets the value of the '{@link com.odcgroup.process.model.Process#getDisplayName <em>Display Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Display Name</em>' attribute.
	 * @see #getDisplayName()
	 * @generated
	 */
	void setDisplayName(String value);

	/**
	 * Returns the value of the '<em><b>Pools</b></em>' containment reference list.
	 * The list contents are of type {@link com.odcgroup.process.model.Pool}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Pools</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Pools</em>' containment reference list.
	 * @see com.odcgroup.process.model.ProcessPackage#getProcess_Pools()
	 * @model type="com.odcgroup.process.model.Pool" containment="true" required="true"
	 * @generated
	 */
	EList getPools();

	/**
	 * Returns the value of the '<em><b>Transitions</b></em>' containment reference list.
	 * The list contents are of type {@link com.odcgroup.process.model.Flow}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Transitions</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Transitions</em>' containment reference list.
	 * @see com.odcgroup.process.model.ProcessPackage#getProcess_Transitions()
	 * @model type="com.odcgroup.process.model.Flow" containment="true"
	 * @generated
	 */
	EList getTransitions();

	/**
	 * Returns the value of the '<em><b>Inverted</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Inverted</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Inverted</em>' attribute.
	 * @see #setInverted(boolean)
	 * @see com.odcgroup.process.model.ProcessPackage#getProcess_Inverted()
	 * @model
	 * @generated
	 */
	boolean isInverted();

	/**
	 * Sets the value of the '{@link com.odcgroup.process.model.Process#isInverted <em>Inverted</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Inverted</em>' attribute.
	 * @see #isInverted()
	 * @generated
	 */
	void setInverted(boolean value);

	/**
	 * Returns the value of the '<em><b>Filename</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Filename</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Filename</em>' attribute.
	 * @see #setFilename(String)
	 * @see com.odcgroup.process.model.ProcessPackage#getProcess_Filename()
	 * @model required="true"
	 * @generated
	 */
	String getFilename();

	/**
	 * Sets the value of the '{@link com.odcgroup.process.model.Process#getFilename <em>Filename</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Filename</em>' attribute.
	 * @see #getFilename()
	 * @generated
	 */
	void setFilename(String value);

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
	 * @see com.odcgroup.process.model.ProcessPackage#getProcess_MetamodelVersion()
	 * @model unique="false"
	 * @generated
	 */
	String getMetamodelVersion();

	/**
	 * Sets the value of the '{@link com.odcgroup.process.model.Process#getMetamodelVersion <em>Metamodel Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Metamodel Version</em>' attribute.
	 * @see #getMetamodelVersion()
	 * @generated
	 */
	void setMetamodelVersion(String value);

	/**
	 * Returns the value of the '<em><b>Translations</b></em>' containment reference list.
	 * The list contents are of type {@link com.odcgroup.process.model.Translation}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Translations</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Translations</em>' containment reference list.
	 * @see com.odcgroup.process.model.ProcessPackage#getProcess_Translations()
	 * @model type="com.odcgroup.process.model.Translation" containment="true"
	 * @generated
	 */
	EList getTranslations();

} // Process
