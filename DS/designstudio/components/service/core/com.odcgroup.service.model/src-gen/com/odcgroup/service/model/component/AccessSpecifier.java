/**
 */
package com.odcgroup.service.model.component;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Access Specifier</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see com.odcgroup.service.model.component.ComponentPackage#getAccessSpecifier()
 * @model
 * @generated
 */
public enum AccessSpecifier implements Enumerator
{
  /**
   * The '<em><b>Module</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #MODULE_VALUE
   * @generated
   * @ordered
   */
  MODULE(0, "module", "module"),

  /**
   * The '<em><b>Private</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #PRIVATE_VALUE
   * @generated
   * @ordered
   */
  PRIVATE(1, "private", "private"),

  /**
   * The '<em><b>Public</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #PUBLIC_VALUE
   * @generated
   * @ordered
   */
  PUBLIC(2, "public", "public"),

  /**
   * The '<em><b>External</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #EXTERNAL_VALUE
   * @generated
   * @ordered
   */
  EXTERNAL(3, "external", "external");

  /**
   * The '<em><b>Module</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of '<em><b>Module</b></em>' literal object isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @see #MODULE
   * @model name="module"
   * @generated
   * @ordered
   */
  public static final int MODULE_VALUE = 0;

  /**
   * The '<em><b>Private</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of '<em><b>Private</b></em>' literal object isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @see #PRIVATE
   * @model name="private"
   * @generated
   * @ordered
   */
  public static final int PRIVATE_VALUE = 1;

  /**
   * The '<em><b>Public</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of '<em><b>Public</b></em>' literal object isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @see #PUBLIC
   * @model name="public"
   * @generated
   * @ordered
   */
  public static final int PUBLIC_VALUE = 2;

  /**
   * The '<em><b>External</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of '<em><b>External</b></em>' literal object isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @see #EXTERNAL
   * @model name="external"
   * @generated
   * @ordered
   */
  public static final int EXTERNAL_VALUE = 3;

  /**
   * An array of all the '<em><b>Access Specifier</b></em>' enumerators.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private static final AccessSpecifier[] VALUES_ARRAY =
    new AccessSpecifier[]
    {
      MODULE,
      PRIVATE,
      PUBLIC,
      EXTERNAL,
    };

  /**
   * A public read-only list of all the '<em><b>Access Specifier</b></em>' enumerators.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public static final List<AccessSpecifier> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

  /**
   * Returns the '<em><b>Access Specifier</b></em>' literal with the specified literal value.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public static AccessSpecifier get(String literal)
  {
    for (int i = 0; i < VALUES_ARRAY.length; ++i)
    {
      AccessSpecifier result = VALUES_ARRAY[i];
      if (result.toString().equals(literal))
      {
        return result;
      }
    }
    return null;
  }

  /**
   * Returns the '<em><b>Access Specifier</b></em>' literal with the specified name.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public static AccessSpecifier getByName(String name)
  {
    for (int i = 0; i < VALUES_ARRAY.length; ++i)
    {
      AccessSpecifier result = VALUES_ARRAY[i];
      if (result.getName().equals(name))
      {
        return result;
      }
    }
    return null;
  }

  /**
   * Returns the '<em><b>Access Specifier</b></em>' literal with the specified integer value.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public static AccessSpecifier get(int value)
  {
    switch (value)
    {
      case MODULE_VALUE: return MODULE;
      case PRIVATE_VALUE: return PRIVATE;
      case PUBLIC_VALUE: return PUBLIC;
      case EXTERNAL_VALUE: return EXTERNAL;
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
  private AccessSpecifier(int value, String name, String literal)
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

} //AccessSpecifier
