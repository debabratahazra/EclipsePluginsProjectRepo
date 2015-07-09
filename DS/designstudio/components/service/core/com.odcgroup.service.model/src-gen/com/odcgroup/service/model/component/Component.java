/**
 */
package com.odcgroup.service.model.component;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Component</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.odcgroup.service.model.component.Component#getDocumentation <em>Documentation</em>}</li>
 *   <li>{@link com.odcgroup.service.model.component.Component#getName <em>Name</em>}</li>
 *   <li>{@link com.odcgroup.service.model.component.Component#getMetamodelVersion <em>Metamodel Version</em>}</li>
 *   <li>{@link com.odcgroup.service.model.component.Component#getContent <em>Content</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.odcgroup.service.model.component.ComponentPackage#getComponent()
 * @model
 * @generated
 */
public interface Component extends EObject
{
  /**
   * Returns the value of the '<em><b>Documentation</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Documentation</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Documentation</em>' attribute.
   * @see #setDocumentation(String)
   * @see com.odcgroup.service.model.component.ComponentPackage#getComponent_Documentation()
   * @model
   * @generated
   */
  String getDocumentation();

  /**
   * Sets the value of the '{@link com.odcgroup.service.model.component.Component#getDocumentation <em>Documentation</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Documentation</em>' attribute.
   * @see #getDocumentation()
   * @generated
   */
  void setDocumentation(String value);

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
   * @see com.odcgroup.service.model.component.ComponentPackage#getComponent_Name()
   * @model
   * @generated
   */
  String getName();

  /**
   * Sets the value of the '{@link com.odcgroup.service.model.component.Component#getName <em>Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Name</em>' attribute.
   * @see #getName()
   * @generated
   */
  void setName(String value);

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
   * @see com.odcgroup.service.model.component.ComponentPackage#getComponent_MetamodelVersion()
   * @model
   * @generated
   */
  String getMetamodelVersion();

  /**
   * Sets the value of the '{@link com.odcgroup.service.model.component.Component#getMetamodelVersion <em>Metamodel Version</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Metamodel Version</em>' attribute.
   * @see #getMetamodelVersion()
   * @generated
   */
  void setMetamodelVersion(String value);

  /**
   * Returns the value of the '<em><b>Content</b></em>' containment reference list.
   * The list contents are of type {@link com.odcgroup.service.model.component.Content}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Content</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Content</em>' containment reference list.
   * @see com.odcgroup.service.model.component.ComponentPackage#getComponent_Content()
   * @model containment="true"
   * @generated
   */
  EList<Content> getContent();

} // Component
