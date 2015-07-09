/**
 */
package com.odcgroup.t24.enquiry.enquiry;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Processing Mode</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getProcessingMode()
 * @model
 * @generated
 */
public enum ProcessingMode implements Enumerator
{
  /**
   * The '<em><b>Unspecified</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #UNSPECIFIED_VALUE
   * @generated
   * @ordered
   */
  UNSPECIFIED(0, "Unspecified", "Null"),

  /**
   * The '<em><b>None</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #NONE_VALUE
   * @generated
   * @ordered
   */
  NONE(1, "None", "None"),

  /**
   * The '<em><b>Single</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #SINGLE_VALUE
   * @generated
   * @ordered
   */
  SINGLE(2, "Single", "Single"),

  /**
   * The '<em><b>Multi</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #MULTI_VALUE
   * @generated
   * @ordered
   */
  MULTI(3, "Multi", "Multi");

  /**
   * The '<em><b>Unspecified</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of '<em><b>Unspecified</b></em>' literal object isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @see #UNSPECIFIED
   * @model name="Unspecified" literal="Null"
   * @generated
   * @ordered
   */
  public static final int UNSPECIFIED_VALUE = 0;

  /**
   * The '<em><b>None</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of '<em><b>None</b></em>' literal object isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @see #NONE
   * @model name="None"
   * @generated
   * @ordered
   */
  public static final int NONE_VALUE = 1;

  /**
   * The '<em><b>Single</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of '<em><b>Single</b></em>' literal object isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @see #SINGLE
   * @model name="Single"
   * @generated
   * @ordered
   */
  public static final int SINGLE_VALUE = 2;

  /**
   * The '<em><b>Multi</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of '<em><b>Multi</b></em>' literal object isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @see #MULTI
   * @model name="Multi"
   * @generated
   * @ordered
   */
  public static final int MULTI_VALUE = 3;

  /**
   * An array of all the '<em><b>Processing Mode</b></em>' enumerators.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private static final ProcessingMode[] VALUES_ARRAY =
    new ProcessingMode[]
    {
      UNSPECIFIED,
      NONE,
      SINGLE,
      MULTI,
    };

  /**
   * A public read-only list of all the '<em><b>Processing Mode</b></em>' enumerators.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public static final List<ProcessingMode> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

  /**
   * Returns the '<em><b>Processing Mode</b></em>' literal with the specified literal value.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public static ProcessingMode get(String literal)
  {
    for (int i = 0; i < VALUES_ARRAY.length; ++i)
    {
      ProcessingMode result = VALUES_ARRAY[i];
      if (result.toString().equals(literal))
      {
        return result;
      }
    }
    return null;
  }

  /**
   * Returns the '<em><b>Processing Mode</b></em>' literal with the specified name.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public static ProcessingMode getByName(String name)
  {
    for (int i = 0; i < VALUES_ARRAY.length; ++i)
    {
      ProcessingMode result = VALUES_ARRAY[i];
      if (result.getName().equals(name))
      {
        return result;
      }
    }
    return null;
  }

  /**
   * Returns the '<em><b>Processing Mode</b></em>' literal with the specified integer value.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public static ProcessingMode get(int value)
  {
    switch (value)
    {
      case UNSPECIFIED_VALUE: return UNSPECIFIED;
      case NONE_VALUE: return NONE;
      case SINGLE_VALUE: return SINGLE;
      case MULTI_VALUE: return MULTI;
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
  private ProcessingMode(int value, String name, String literal)
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
  
} //ProcessingMode
