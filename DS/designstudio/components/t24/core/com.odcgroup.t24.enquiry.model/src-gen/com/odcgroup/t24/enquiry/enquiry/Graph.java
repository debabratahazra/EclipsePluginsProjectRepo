/**
 */
package com.odcgroup.t24.enquiry.enquiry;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Graph</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.Graph#getType <em>Type</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.Graph#getLabels <em>Labels</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.Graph#getDimension <em>Dimension</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.Graph#getMargins <em>Margins</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.Graph#getScale <em>Scale</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.Graph#getLegend <em>Legend</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.Graph#getXAxis <em>XAxis</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.Graph#getYAxis <em>YAxis</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.Graph#getZAxis <em>ZAxis</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getGraph()
 * @model
 * @generated
 */
public interface Graph extends EObject
{
  /**
   * Returns the value of the '<em><b>Type</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Type</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Type</em>' attribute.
   * @see #setType(String)
   * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getGraph_Type()
   * @model
   * @generated
   */
  String getType();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.enquiry.enquiry.Graph#getType <em>Type</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Type</em>' attribute.
   * @see #getType()
   * @generated
   */
  void setType(String value);

  /**
   * Returns the value of the '<em><b>Labels</b></em>' containment reference list.
   * The list contents are of type {@link com.odcgroup.t24.enquiry.enquiry.Label}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Labels</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Labels</em>' containment reference list.
   * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getGraph_Labels()
   * @model containment="true"
   * @generated
   */
  EList<Label> getLabels();

  /**
   * Returns the value of the '<em><b>Dimension</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Dimension</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Dimension</em>' containment reference.
   * @see #setDimension(Dimension)
   * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getGraph_Dimension()
   * @model containment="true"
   * @generated
   */
  Dimension getDimension();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.enquiry.enquiry.Graph#getDimension <em>Dimension</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Dimension</em>' containment reference.
   * @see #getDimension()
   * @generated
   */
  void setDimension(Dimension value);

  /**
   * Returns the value of the '<em><b>Margins</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Margins</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Margins</em>' containment reference.
   * @see #setMargins(Margins)
   * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getGraph_Margins()
   * @model containment="true"
   * @generated
   */
  Margins getMargins();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.enquiry.enquiry.Graph#getMargins <em>Margins</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Margins</em>' containment reference.
   * @see #getMargins()
   * @generated
   */
  void setMargins(Margins value);

  /**
   * Returns the value of the '<em><b>Scale</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Scale</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Scale</em>' containment reference.
   * @see #setScale(Scale)
   * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getGraph_Scale()
   * @model containment="true"
   * @generated
   */
  Scale getScale();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.enquiry.enquiry.Graph#getScale <em>Scale</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Scale</em>' containment reference.
   * @see #getScale()
   * @generated
   */
  void setScale(Scale value);

  /**
   * Returns the value of the '<em><b>Legend</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Legend</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Legend</em>' containment reference.
   * @see #setLegend(Legend)
   * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getGraph_Legend()
   * @model containment="true"
   * @generated
   */
  Legend getLegend();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.enquiry.enquiry.Graph#getLegend <em>Legend</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Legend</em>' containment reference.
   * @see #getLegend()
   * @generated
   */
  void setLegend(Legend value);

  /**
   * Returns the value of the '<em><b>XAxis</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>XAxis</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>XAxis</em>' containment reference.
   * @see #setXAxis(Axis)
   * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getGraph_XAxis()
   * @model containment="true"
   * @generated
   */
  Axis getXAxis();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.enquiry.enquiry.Graph#getXAxis <em>XAxis</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>XAxis</em>' containment reference.
   * @see #getXAxis()
   * @generated
   */
  void setXAxis(Axis value);

  /**
   * Returns the value of the '<em><b>YAxis</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>YAxis</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>YAxis</em>' containment reference.
   * @see #setYAxis(Axis)
   * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getGraph_YAxis()
   * @model containment="true"
   * @generated
   */
  Axis getYAxis();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.enquiry.enquiry.Graph#getYAxis <em>YAxis</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>YAxis</em>' containment reference.
   * @see #getYAxis()
   * @generated
   */
  void setYAxis(Axis value);

  /**
   * Returns the value of the '<em><b>ZAxis</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>ZAxis</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>ZAxis</em>' containment reference.
   * @see #setZAxis(Axis)
   * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getGraph_ZAxis()
   * @model containment="true"
   * @generated
   */
  Axis getZAxis();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.enquiry.enquiry.Graph#getZAxis <em>ZAxis</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>ZAxis</em>' containment reference.
   * @see #getZAxis()
   * @generated
   */
  void setZAxis(Axis value);

} // Graph
