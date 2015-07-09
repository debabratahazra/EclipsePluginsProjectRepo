/**
 */
package com.odcgroup.t24.version.versionDSL;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Value Or At Routine</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.odcgroup.t24.version.versionDSL.ValueOrAtRoutine#getValue <em>Value</em>}</li>
 *   <li>{@link com.odcgroup.t24.version.versionDSL.ValueOrAtRoutine#getAtRoutine <em>At Routine</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.odcgroup.t24.version.versionDSL.VersionDSLPackage#getValueOrAtRoutine()
 * @model
 * @generated
 */
public interface ValueOrAtRoutine extends EObject
{
  /**
   * Returns the value of the '<em><b>Value</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Value</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Value</em>' attribute.
   * @see #setValue(String)
   * @see com.odcgroup.t24.version.versionDSL.VersionDSLPackage#getValueOrAtRoutine_Value()
   * @model
   * @generated
   */
  String getValue();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.version.versionDSL.ValueOrAtRoutine#getValue <em>Value</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Value</em>' attribute.
   * @see #getValue()
   * @generated
   */
  void setValue(String value);

  /**
   * Returns the value of the '<em><b>At Routine</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>At Routine</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>At Routine</em>' containment reference.
   * @see #setAtRoutine(AtRoutine)
   * @see com.odcgroup.t24.version.versionDSL.VersionDSLPackage#getValueOrAtRoutine_AtRoutine()
   * @model containment="true"
   * @generated
   */
  AtRoutine getAtRoutine();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.version.versionDSL.ValueOrAtRoutine#getAtRoutine <em>At Routine</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>At Routine</em>' containment reference.
   * @see #getAtRoutine()
   * @generated
   */
  void setAtRoutine(AtRoutine value);

} // ValueOrAtRoutine
