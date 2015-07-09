/**
 */
package com.odcgroup.t24.enquiry.enquiry;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Field Format</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getFieldFormat()
 * @model
 * @generated
 */
public enum FieldFormat implements Enumerator
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
   * The '<em><b>Date</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #DATE_VALUE
   * @generated
   * @ordered
   */
  DATE(2, "Date", "Date"),

  /**
   * The '<em><b>User</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #USER_VALUE
   * @generated
   * @ordered
   */
  USER(3, "User", "User"),

  /**
   * The '<em><b>Translate</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #TRANSLATE_VALUE
   * @generated
   * @ordered
   */
  TRANSLATE(4, "Translate", "Translate"),

  /**
   * The '<em><b>Rate</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #RATE_VALUE
   * @generated
   * @ordered
   */
  RATE(5, "Rate", "Rate"),

  /**
   * The '<em><b>Currency</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #CURRENCY_VALUE
   * @generated
   * @ordered
   */
  CURRENCY(6, "Currency", "Currency");

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
   * The '<em><b>Date</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of '<em><b>Date</b></em>' literal object isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @see #DATE
   * @model name="Date"
   * @generated
   * @ordered
   */
  public static final int DATE_VALUE = 2;

  /**
   * The '<em><b>User</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of '<em><b>User</b></em>' literal object isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @see #USER
   * @model name="User"
   * @generated
   * @ordered
   */
  public static final int USER_VALUE = 3;

  /**
   * The '<em><b>Translate</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of '<em><b>Translate</b></em>' literal object isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @see #TRANSLATE
   * @model name="Translate"
   * @generated
   * @ordered
   */
  public static final int TRANSLATE_VALUE = 4;

  /**
   * The '<em><b>Rate</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of '<em><b>Rate</b></em>' literal object isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @see #RATE
   * @model name="Rate"
   * @generated
   * @ordered
   */
  public static final int RATE_VALUE = 5;

  /**
   * The '<em><b>Currency</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of '<em><b>Currency</b></em>' literal object isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @see #CURRENCY
   * @model name="Currency"
   * @generated
   * @ordered
   */
  public static final int CURRENCY_VALUE = 6;

  /**
   * An array of all the '<em><b>Field Format</b></em>' enumerators.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private static final FieldFormat[] VALUES_ARRAY =
    new FieldFormat[]
    {
      UNSPECIFIED,
      NONE,
      DATE,
      USER,
      TRANSLATE,
      RATE,
      CURRENCY,
    };

  /**
   * A public read-only list of all the '<em><b>Field Format</b></em>' enumerators.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public static final List<FieldFormat> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

  /**
   * Returns the '<em><b>Field Format</b></em>' literal with the specified literal value.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public static FieldFormat get(String literal)
  {
    for (int i = 0; i < VALUES_ARRAY.length; ++i)
    {
      FieldFormat result = VALUES_ARRAY[i];
      if (result.toString().equals(literal))
      {
        return result;
      }
    }
    return null;
  }

  /**
   * Returns the '<em><b>Field Format</b></em>' literal with the specified name.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public static FieldFormat getByName(String name)
  {
    for (int i = 0; i < VALUES_ARRAY.length; ++i)
    {
      FieldFormat result = VALUES_ARRAY[i];
      if (result.getName().equals(name))
      {
        return result;
      }
    }
    return null;
  }

  /**
   * Returns the '<em><b>Field Format</b></em>' literal with the specified integer value.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public static FieldFormat get(int value)
  {
    switch (value)
    {
      case UNSPECIFIED_VALUE: return UNSPECIFIED;
      case NONE_VALUE: return NONE;
      case DATE_VALUE: return DATE;
      case USER_VALUE: return USER;
      case TRANSLATE_VALUE: return TRANSLATE;
      case RATE_VALUE: return RATE;
      case CURRENCY_VALUE: return CURRENCY;
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
  private FieldFormat(int value, String name, String literal)
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
  
} //FieldFormat
