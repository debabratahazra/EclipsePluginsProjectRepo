/**
 * Odyssey Financial Technologies
 *
 * $Id$
 */
package com.odcgroup.process.model;

import org.eclipse.emf.common.util.EList;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Task</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.odcgroup.process.model.Task#isInitial <em>Initial</em>}</li>
 *   <li>{@link com.odcgroup.process.model.Task#getTranslations <em>Translations</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.odcgroup.process.model.ProcessPackage#getTask()
 * @model abstract="true"
 * @generated
 */
public interface Task extends ProcessItem {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String copyright = "Odyssey Financial Technologies";

	/**
	 * Returns the value of the '<em><b>Initial</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Initial</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Initial</em>' attribute.
	 * @see #isSetInitial()
	 * @see #unsetInitial()
	 * @see #setInitial(boolean)
	 * @see com.odcgroup.process.model.ProcessPackage#getTask_Initial()
	 * @model unique="false" unsettable="true"
	 * @generated
	 */
	boolean isInitial();

	/**
	 * Sets the value of the '{@link com.odcgroup.process.model.Task#isInitial <em>Initial</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Initial</em>' attribute.
	 * @see #isSetInitial()
	 * @see #unsetInitial()
	 * @see #isInitial()
	 * @generated
	 */
	void setInitial(boolean value);

	/**
	 * Unsets the value of the '{@link com.odcgroup.process.model.Task#isInitial <em>Initial</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetInitial()
	 * @see #isInitial()
	 * @see #setInitial(boolean)
	 * @generated
	 */
	void unsetInitial();

	/**
	 * Returns whether the value of the '{@link com.odcgroup.process.model.Task#isInitial <em>Initial</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Initial</em>' attribute is set.
	 * @see #unsetInitial()
	 * @see #isInitial()
	 * @see #setInitial(boolean)
	 * @generated
	 */
	boolean isSetInitial();

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
	 * @see com.odcgroup.process.model.ProcessPackage#getTask_Translations()
	 * @model type="com.odcgroup.process.model.Translation" containment="true"
	 * @generated
	 */
	EList getTranslations();

} // Task
