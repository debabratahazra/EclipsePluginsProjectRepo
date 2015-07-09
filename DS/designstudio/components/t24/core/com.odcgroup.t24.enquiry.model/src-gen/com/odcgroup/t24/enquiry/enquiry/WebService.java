/**
 */
package com.odcgroup.t24.enquiry.enquiry;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Web Service</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.WebService#getPublishWebService <em>Publish Web Service</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.WebService#getWebServiceNames <em>Web Service Names</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.WebService#getWebServiceActivity <em>Web Service Activity</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.WebService#getWebServiceDescription <em>Web Service Description</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getWebService()
 * @model
 * @generated
 */
public interface WebService extends EObject
{
  /**
   * Returns the value of the '<em><b>Publish Web Service</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Publish Web Service</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Publish Web Service</em>' attribute.
   * @see #setPublishWebService(Boolean)
   * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getWebService_PublishWebService()
   * @model
   * @generated
   */
  Boolean getPublishWebService();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.enquiry.enquiry.WebService#getPublishWebService <em>Publish Web Service</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Publish Web Service</em>' attribute.
   * @see #getPublishWebService()
   * @generated
   */
  void setPublishWebService(Boolean value);

  /**
   * Returns the value of the '<em><b>Web Service Names</b></em>' attribute list.
   * The list contents are of type {@link java.lang.String}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Web Service Names</em>' attribute list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Web Service Names</em>' attribute list.
   * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getWebService_WebServiceNames()
   * @model unique="false"
   * @generated
   */
  EList<String> getWebServiceNames();

  /**
   * Returns the value of the '<em><b>Web Service Activity</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Web Service Activity</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Web Service Activity</em>' attribute.
   * @see #setWebServiceActivity(String)
   * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getWebService_WebServiceActivity()
   * @model
   * @generated
   */
  String getWebServiceActivity();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.enquiry.enquiry.WebService#getWebServiceActivity <em>Web Service Activity</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Web Service Activity</em>' attribute.
   * @see #getWebServiceActivity()
   * @generated
   */
  void setWebServiceActivity(String value);

  /**
   * Returns the value of the '<em><b>Web Service Description</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Web Service Description</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Web Service Description</em>' attribute.
   * @see #setWebServiceDescription(String)
   * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getWebService_WebServiceDescription()
   * @model
   * @generated
   */
  String getWebServiceDescription();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.enquiry.enquiry.WebService#getWebServiceDescription <em>Web Service Description</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Web Service Description</em>' attribute.
   * @see #getWebServiceDescription()
   * @generated
   */
  void setWebServiceDescription(String value);

} // WebService
