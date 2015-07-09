/**
 */
package com.odcgroup.service.model.component;

import com.odcgroup.mdf.metamodel.MdfEntity;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Argument</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.odcgroup.service.model.component.Argument#getDocumentation <em>Documentation</em>}</li>
 *   <li>{@link com.odcgroup.service.model.component.Argument#getInout <em>Inout</em>}</li>
 *   <li>{@link com.odcgroup.service.model.component.Argument#getName <em>Name</em>}</li>
 *   <li>{@link com.odcgroup.service.model.component.Argument#getType <em>Type</em>}</li>
 *   <li>{@link com.odcgroup.service.model.component.Argument#getMultiplicity <em>Multiplicity</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.odcgroup.service.model.component.ComponentPackage#getArgument()
 * @model
 * @generated
 */
public interface Argument extends EObject
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
   * @see com.odcgroup.service.model.component.ComponentPackage#getArgument_Documentation()
   * @model
   * @generated
   */
  String getDocumentation();

  /**
   * Sets the value of the '{@link com.odcgroup.service.model.component.Argument#getDocumentation <em>Documentation</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Documentation</em>' attribute.
   * @see #getDocumentation()
   * @generated
   */
  void setDocumentation(String value);

  /**
   * Returns the value of the '<em><b>Inout</b></em>' attribute.
   * The literals are from the enumeration {@link com.odcgroup.service.model.component.InOutType}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Inout</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Inout</em>' attribute.
   * @see com.odcgroup.service.model.component.InOutType
   * @see #setInout(InOutType)
   * @see com.odcgroup.service.model.component.ComponentPackage#getArgument_Inout()
   * @model
   * @generated
   */
  InOutType getInout();

  /**
   * Sets the value of the '{@link com.odcgroup.service.model.component.Argument#getInout <em>Inout</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Inout</em>' attribute.
   * @see com.odcgroup.service.model.component.InOutType
   * @see #getInout()
   * @generated
   */
  void setInout(InOutType value);

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
   * @see com.odcgroup.service.model.component.ComponentPackage#getArgument_Name()
   * @model
   * @generated
   */
  String getName();

  /**
   * Sets the value of the '{@link com.odcgroup.service.model.component.Argument#getName <em>Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Name</em>' attribute.
   * @see #getName()
   * @generated
   */
  void setName(String value);

  /**
   * Returns the value of the '<em><b>Type</b></em>' reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Type</em>' reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Type</em>' reference.
   * @see #setType(MdfEntity)
   * @see com.odcgroup.service.model.component.ComponentPackage#getArgument_Type()
   * @model
   * @generated
   */
  MdfEntity getType();

  /**
   * Sets the value of the '{@link com.odcgroup.service.model.component.Argument#getType <em>Type</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Type</em>' reference.
   * @see #getType()
   * @generated
   */
  void setType(MdfEntity value);

  /**
   * Returns the value of the '<em><b>Multiplicity</b></em>' attribute.
   * The literals are from the enumeration {@link com.odcgroup.service.model.component.Multiplicity}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Multiplicity</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Multiplicity</em>' attribute.
   * @see com.odcgroup.service.model.component.Multiplicity
   * @see #setMultiplicity(Multiplicity)
   * @see com.odcgroup.service.model.component.ComponentPackage#getArgument_Multiplicity()
   * @model
   * @generated
   */
  Multiplicity getMultiplicity();

  /**
   * Sets the value of the '{@link com.odcgroup.service.model.component.Argument#getMultiplicity <em>Multiplicity</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Multiplicity</em>' attribute.
   * @see com.odcgroup.service.model.component.Multiplicity
   * @see #getMultiplicity()
   * @generated
   */
  void setMultiplicity(Multiplicity value);

} // Argument
