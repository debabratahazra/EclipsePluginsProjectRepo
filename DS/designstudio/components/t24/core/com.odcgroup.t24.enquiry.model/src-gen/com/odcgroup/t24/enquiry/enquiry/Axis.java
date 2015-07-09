/**
 */
package com.odcgroup.t24.enquiry.enquiry;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Axis</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.Axis#getField <em>Field</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.Axis#isDisplayLegend <em>Display Legend</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.Axis#isShowGrid <em>Show Grid</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getAxis()
 * @model
 * @generated
 */
public interface Axis extends EObject
{
  /**
   * Returns the value of the '<em><b>Field</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Field</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Field</em>' attribute.
   * @see #setField(String)
   * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getAxis_Field()
   * @model
   * @generated
   */
  String getField();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.enquiry.enquiry.Axis#getField <em>Field</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Field</em>' attribute.
   * @see #getField()
   * @generated
   */
  void setField(String value);

  /**
   * Returns the value of the '<em><b>Display Legend</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Display Legend</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Display Legend</em>' attribute.
   * @see #setDisplayLegend(boolean)
   * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getAxis_DisplayLegend()
   * @model
   * @generated
   */
  boolean isDisplayLegend();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.enquiry.enquiry.Axis#isDisplayLegend <em>Display Legend</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Display Legend</em>' attribute.
   * @see #isDisplayLegend()
   * @generated
   */
  void setDisplayLegend(boolean value);

  /**
   * Returns the value of the '<em><b>Show Grid</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Show Grid</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Show Grid</em>' attribute.
   * @see #setShowGrid(boolean)
   * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getAxis_ShowGrid()
   * @model
   * @generated
   */
  boolean isShowGrid();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.enquiry.enquiry.Axis#isShowGrid <em>Show Grid</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Show Grid</em>' attribute.
   * @see #isShowGrid()
   * @generated
   */
  void setShowGrid(boolean value);

} // Axis
