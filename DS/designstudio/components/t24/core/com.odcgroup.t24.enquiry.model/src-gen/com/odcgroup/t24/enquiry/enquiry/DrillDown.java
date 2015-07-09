/**
 */
package com.odcgroup.t24.enquiry.enquiry;

import com.odcgroup.translation.translationDsl.Translations;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Drill Down</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.DrillDown#getDrill_name <em>Drill name</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.DrillDown#getDescription <em>Description</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.DrillDown#getLabelField <em>Label Field</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.DrillDown#getImage <em>Image</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.DrillDown#getInfo <em>Info</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.DrillDown#getCriteria <em>Criteria</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.DrillDown#getParameters <em>Parameters</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.DrillDown#getType <em>Type</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getDrillDown()
 * @model
 * @generated
 */
public interface DrillDown extends EObject
{
  /**
   * Returns the value of the '<em><b>Drill name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Drill name</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Drill name</em>' attribute.
   * @see #setDrill_name(String)
   * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getDrillDown_Drill_name()
   * @model
   * @generated
   */
  String getDrill_name();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.enquiry.enquiry.DrillDown#getDrill_name <em>Drill name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Drill name</em>' attribute.
   * @see #getDrill_name()
   * @generated
   */
  void setDrill_name(String value);

  /**
   * Returns the value of the '<em><b>Description</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Description</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Description</em>' containment reference.
   * @see #setDescription(Translations)
   * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getDrillDown_Description()
   * @model containment="true"
   * @generated
   */
  Translations getDescription();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.enquiry.enquiry.DrillDown#getDescription <em>Description</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Description</em>' containment reference.
   * @see #getDescription()
   * @generated
   */
  void setDescription(Translations value);

  /**
   * Returns the value of the '<em><b>Label Field</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Label Field</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Label Field</em>' attribute.
   * @see #setLabelField(String)
   * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getDrillDown_LabelField()
   * @model
   * @generated
   */
  String getLabelField();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.enquiry.enquiry.DrillDown#getLabelField <em>Label Field</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Label Field</em>' attribute.
   * @see #getLabelField()
   * @generated
   */
  void setLabelField(String value);

  /**
   * Returns the value of the '<em><b>Image</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Image</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Image</em>' attribute.
   * @see #setImage(String)
   * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getDrillDown_Image()
   * @model
   * @generated
   */
  String getImage();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.enquiry.enquiry.DrillDown#getImage <em>Image</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Image</em>' attribute.
   * @see #getImage()
   * @generated
   */
  void setImage(String value);

  /**
   * Returns the value of the '<em><b>Info</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Info</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Info</em>' attribute.
   * @see #setInfo(String)
   * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getDrillDown_Info()
   * @model
   * @generated
   */
  String getInfo();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.enquiry.enquiry.DrillDown#getInfo <em>Info</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Info</em>' attribute.
   * @see #getInfo()
   * @generated
   */
  void setInfo(String value);

  /**
   * Returns the value of the '<em><b>Criteria</b></em>' containment reference list.
   * The list contents are of type {@link com.odcgroup.t24.enquiry.enquiry.SelectionCriteria}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Criteria</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Criteria</em>' containment reference list.
   * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getDrillDown_Criteria()
   * @model containment="true"
   * @generated
   */
  EList<SelectionCriteria> getCriteria();

  /**
   * Returns the value of the '<em><b>Parameters</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Parameters</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Parameters</em>' containment reference.
   * @see #setParameters(Parameters)
   * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getDrillDown_Parameters()
   * @model containment="true"
   * @generated
   */
  Parameters getParameters();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.enquiry.enquiry.DrillDown#getParameters <em>Parameters</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Parameters</em>' containment reference.
   * @see #getParameters()
   * @generated
   */
  void setParameters(Parameters value);

  /**
   * Returns the value of the '<em><b>Type</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Type</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Type</em>' containment reference.
   * @see #setType(DrillDownType)
   * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getDrillDown_Type()
   * @model containment="true"
   * @generated
   */
  DrillDownType getType();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.enquiry.enquiry.DrillDown#getType <em>Type</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Type</em>' containment reference.
   * @see #getType()
   * @generated
   */
  void setType(DrillDownType value);

} // DrillDown
