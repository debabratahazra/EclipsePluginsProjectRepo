/**
 */
package com.odcgroup.service.model.component;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>T24 Method Stereotype</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see com.odcgroup.service.model.component.ComponentPackage#getT24MethodStereotype()
 * @model
 * @generated
 */
public enum T24MethodStereotype implements Enumerator
{
  /**
   * The '<em><b>Integration Flow Supportable</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #INTEGRATION_FLOW_SUPPORTABLE_VALUE
   * @generated
   * @ordered
   */
  INTEGRATION_FLOW_SUPPORTABLE(0, "integrationFlowSupportable", "integrationFlowSupportable"),

  /**
   * The '<em><b>Unsafe</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #UNSAFE_VALUE
   * @generated
   * @ordered
   */
  UNSAFE(1, "unsafe", "unsafe"),

  /**
   * The '<em><b>Idempotent</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #IDEMPOTENT_VALUE
   * @generated
   * @ordered
   */
  IDEMPOTENT(2, "idempotent", "idempotent");

  /**
   * The '<em><b>Integration Flow Supportable</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of '<em><b>Integration Flow Supportable</b></em>' literal object isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @see #INTEGRATION_FLOW_SUPPORTABLE
   * @model name="integrationFlowSupportable"
   * @generated
   * @ordered
   */
  public static final int INTEGRATION_FLOW_SUPPORTABLE_VALUE = 0;

  /**
   * The '<em><b>Unsafe</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of '<em><b>Unsafe</b></em>' literal object isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @see #UNSAFE
   * @model name="unsafe"
   * @generated
   * @ordered
   */
  public static final int UNSAFE_VALUE = 1;

  /**
   * The '<em><b>Idempotent</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of '<em><b>Idempotent</b></em>' literal object isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @see #IDEMPOTENT
   * @model name="idempotent"
   * @generated
   * @ordered
   */
  public static final int IDEMPOTENT_VALUE = 2;

  /**
   * An array of all the '<em><b>T24 Method Stereotype</b></em>' enumerators.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private static final T24MethodStereotype[] VALUES_ARRAY =
    new T24MethodStereotype[]
    {
      INTEGRATION_FLOW_SUPPORTABLE,
      UNSAFE,
      IDEMPOTENT,
    };

  /**
   * A public read-only list of all the '<em><b>T24 Method Stereotype</b></em>' enumerators.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public static final List<T24MethodStereotype> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

  /**
   * Returns the '<em><b>T24 Method Stereotype</b></em>' literal with the specified literal value.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public static T24MethodStereotype get(String literal)
  {
    for (int i = 0; i < VALUES_ARRAY.length; ++i)
    {
      T24MethodStereotype result = VALUES_ARRAY[i];
      if (result.toString().equals(literal))
      {
        return result;
      }
    }
    return null;
  }

  /**
   * Returns the '<em><b>T24 Method Stereotype</b></em>' literal with the specified name.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public static T24MethodStereotype getByName(String name)
  {
    for (int i = 0; i < VALUES_ARRAY.length; ++i)
    {
      T24MethodStereotype result = VALUES_ARRAY[i];
      if (result.getName().equals(name))
      {
        return result;
      }
    }
    return null;
  }

  /**
   * Returns the '<em><b>T24 Method Stereotype</b></em>' literal with the specified integer value.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public static T24MethodStereotype get(int value)
  {
    switch (value)
    {
      case INTEGRATION_FLOW_SUPPORTABLE_VALUE: return INTEGRATION_FLOW_SUPPORTABLE;
      case UNSAFE_VALUE: return UNSAFE;
      case IDEMPOTENT_VALUE: return IDEMPOTENT;
    }
    return null;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private final int value;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private final String name;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private final String literal;

  /**
   * Only this class can construct instances.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private T24MethodStereotype(int value, String name, String literal)
  {
    this.value = value;
    this.name = name;
    this.literal = literal;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public int getValue()
  {
    return value;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getName()
  {
    return name;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getLiteral()
  {
    return literal;
  }

  /**
   * Returns the literal value of the enumerator, which is its string representation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String toString()
  {
    return literal;
  }

} //T24MethodStereotype
