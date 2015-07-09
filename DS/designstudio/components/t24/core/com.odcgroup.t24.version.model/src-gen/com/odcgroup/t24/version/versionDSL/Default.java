/**
 */
package com.odcgroup.t24.version.versionDSL;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Default</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.odcgroup.t24.version.versionDSL.Default#getMv <em>Mv</em>}</li>
 *   <li>{@link com.odcgroup.t24.version.versionDSL.Default#getSv <em>Sv</em>}</li>
 *   <li>{@link com.odcgroup.t24.version.versionDSL.Default#getDefaultIfOldValueEquals <em>Default If Old Value Equals</em>}</li>
 *   <li>{@link com.odcgroup.t24.version.versionDSL.Default#getDefaultNewValueOrAtRoutine <em>Default New Value Or At Routine</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.odcgroup.t24.version.versionDSL.VersionDSLPackage#getDefault()
 * @model
 * @generated
 */
public interface Default extends EObject
{
  /**
   * Returns the value of the '<em><b>Mv</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Mv</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Mv</em>' attribute.
   * @see #setMv(Integer)
   * @see com.odcgroup.t24.version.versionDSL.VersionDSLPackage#getDefault_Mv()
   * @model
   * @generated
   */
  Integer getMv();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.version.versionDSL.Default#getMv <em>Mv</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Mv</em>' attribute.
   * @see #getMv()
   * @generated
   */
  void setMv(Integer value);

  /**
   * Returns the value of the '<em><b>Sv</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Sv</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Sv</em>' attribute.
   * @see #setSv(Integer)
   * @see com.odcgroup.t24.version.versionDSL.VersionDSLPackage#getDefault_Sv()
   * @model
   * @generated
   */
  Integer getSv();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.version.versionDSL.Default#getSv <em>Sv</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Sv</em>' attribute.
   * @see #getSv()
   * @generated
   */
  void setSv(Integer value);

  /**
   * Returns the value of the '<em><b>Default If Old Value Equals</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Default If Old Value Equals</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Default If Old Value Equals</em>' attribute.
   * @see #setDefaultIfOldValueEquals(String)
   * @see com.odcgroup.t24.version.versionDSL.VersionDSLPackage#getDefault_DefaultIfOldValueEquals()
   * @model
   * @generated
   */
  String getDefaultIfOldValueEquals();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.version.versionDSL.Default#getDefaultIfOldValueEquals <em>Default If Old Value Equals</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Default If Old Value Equals</em>' attribute.
   * @see #getDefaultIfOldValueEquals()
   * @generated
   */
  void setDefaultIfOldValueEquals(String value);

  /**
   * Returns the value of the '<em><b>Default New Value Or At Routine</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Default New Value Or At Routine</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Default New Value Or At Routine</em>' containment reference.
   * @see #setDefaultNewValueOrAtRoutine(ValueOrAtRoutine)
   * @see com.odcgroup.t24.version.versionDSL.VersionDSLPackage#getDefault_DefaultNewValueOrAtRoutine()
   * @model containment="true"
   * @generated
   */
  ValueOrAtRoutine getDefaultNewValueOrAtRoutine();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.version.versionDSL.Default#getDefaultNewValueOrAtRoutine <em>Default New Value Or At Routine</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Default New Value Or At Routine</em>' containment reference.
   * @see #getDefaultNewValueOrAtRoutine()
   * @generated
   */
  void setDefaultNewValueOrAtRoutine(ValueOrAtRoutine value);

} // Default
