/**
 */
package com.odcgroup.t24.version.versionDSL;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Deal Slip</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.odcgroup.t24.version.versionDSL.DealSlip#getFormat <em>Format</em>}</li>
 *   <li>{@link com.odcgroup.t24.version.versionDSL.DealSlip#getFunction <em>Function</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.odcgroup.t24.version.versionDSL.VersionDSLPackage#getDealSlip()
 * @model
 * @generated
 */
public interface DealSlip extends EObject
{
  /**
   * Returns the value of the '<em><b>Format</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Format</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Format</em>' attribute.
   * @see #setFormat(String)
   * @see com.odcgroup.t24.version.versionDSL.VersionDSLPackage#getDealSlip_Format()
   * @model
   * @generated
   */
  String getFormat();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.version.versionDSL.DealSlip#getFormat <em>Format</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Format</em>' attribute.
   * @see #getFormat()
   * @generated
   */
  void setFormat(String value);

  /**
   * Returns the value of the '<em><b>Function</b></em>' attribute.
   * The literals are from the enumeration {@link com.odcgroup.t24.version.versionDSL.DealSlipFormatFunction}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Function</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Function</em>' attribute.
   * @see com.odcgroup.t24.version.versionDSL.DealSlipFormatFunction
   * @see #setFunction(DealSlipFormatFunction)
   * @see com.odcgroup.t24.version.versionDSL.VersionDSLPackage#getDealSlip_Function()
   * @model
   * @generated
   */
  DealSlipFormatFunction getFunction();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.version.versionDSL.DealSlip#getFunction <em>Function</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Function</em>' attribute.
   * @see com.odcgroup.t24.version.versionDSL.DealSlipFormatFunction
   * @see #getFunction()
   * @generated
   */
  void setFunction(DealSlipFormatFunction value);

} // DealSlip
