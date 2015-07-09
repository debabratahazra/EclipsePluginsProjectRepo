/**
 */
package com.odcgroup.t24.enquiry.enquiry;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Decision Operand</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getDecisionOperand()
 * @model
 * @generated
 */
public enum DecisionOperand implements Enumerator
{
  /**
   * The '<em><b>None</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #NONE_VALUE
   * @generated
   * @ordered
   */
  NONE(0, "None", "None"),

  /**
   * The '<em><b>Equals</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #EQUALS_VALUE
   * @generated
   * @ordered
   */
  EQUALS(1, "Equals", "Equals"),

  /**
   * The '<em><b>Not Equals</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #NOT_EQUALS_VALUE
   * @generated
   * @ordered
   */
  NOT_EQUALS(2, "NotEquals", "NotEquals"),

  /**
   * The '<em><b>Greater</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #GREATER_VALUE
   * @generated
   * @ordered
   */
  GREATER(3, "Greater", "Greater"),

  /**
   * The '<em><b>Greater Or Equals</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #GREATER_OR_EQUALS_VALUE
   * @generated
   * @ordered
   */
  GREATER_OR_EQUALS(4, "GreaterOrEquals", "GreaterOrEquals"),

  /**
   * The '<em><b>Less Than</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #LESS_THAN_VALUE
   * @generated
   * @ordered
   */
  LESS_THAN(5, "LessThan", "LessThan"),

  /**
   * The '<em><b>Less Or Equals</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #LESS_OR_EQUALS_VALUE
   * @generated
   * @ordered
   */
  LESS_OR_EQUALS(6, "LessOrEquals", "LessOrEquals"),

  /**
   * The '<em><b>Between</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #BETWEEN_VALUE
   * @generated
   * @ordered
   */
  BETWEEN(7, "Between", "Between"),

  /**
   * The '<em><b>Not Between</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #NOT_BETWEEN_VALUE
   * @generated
   * @ordered
   */
  NOT_BETWEEN(8, "NotBetween", "NotBetween"),

  /**
   * The '<em><b>Matches</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #MATCHES_VALUE
   * @generated
   * @ordered
   */
  MATCHES(9, "Matches", "Matches"),

  /**
   * The '<em><b>Not Matches</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #NOT_MATCHES_VALUE
   * @generated
   * @ordered
   */
  NOT_MATCHES(10, "NotMatches", "NotMatches");

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
  public static final int NONE_VALUE = 0;

  /**
   * The '<em><b>Equals</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of '<em><b>Equals</b></em>' literal object isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @see #EQUALS
   * @model name="Equals"
   * @generated
   * @ordered
   */
  public static final int EQUALS_VALUE = 1;

  /**
   * The '<em><b>Not Equals</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of '<em><b>Not Equals</b></em>' literal object isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @see #NOT_EQUALS
   * @model name="NotEquals"
   * @generated
   * @ordered
   */
  public static final int NOT_EQUALS_VALUE = 2;

  /**
   * The '<em><b>Greater</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of '<em><b>Greater</b></em>' literal object isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @see #GREATER
   * @model name="Greater"
   * @generated
   * @ordered
   */
  public static final int GREATER_VALUE = 3;

  /**
   * The '<em><b>Greater Or Equals</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of '<em><b>Greater Or Equals</b></em>' literal object isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @see #GREATER_OR_EQUALS
   * @model name="GreaterOrEquals"
   * @generated
   * @ordered
   */
  public static final int GREATER_OR_EQUALS_VALUE = 4;

  /**
   * The '<em><b>Less Than</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of '<em><b>Less Than</b></em>' literal object isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @see #LESS_THAN
   * @model name="LessThan"
   * @generated
   * @ordered
   */
  public static final int LESS_THAN_VALUE = 5;

  /**
   * The '<em><b>Less Or Equals</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of '<em><b>Less Or Equals</b></em>' literal object isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @see #LESS_OR_EQUALS
   * @model name="LessOrEquals"
   * @generated
   * @ordered
   */
  public static final int LESS_OR_EQUALS_VALUE = 6;

  /**
   * The '<em><b>Between</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of '<em><b>Between</b></em>' literal object isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @see #BETWEEN
   * @model name="Between"
   * @generated
   * @ordered
   */
  public static final int BETWEEN_VALUE = 7;

  /**
   * The '<em><b>Not Between</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of '<em><b>Not Between</b></em>' literal object isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @see #NOT_BETWEEN
   * @model name="NotBetween"
   * @generated
   * @ordered
   */
  public static final int NOT_BETWEEN_VALUE = 8;

  /**
   * The '<em><b>Matches</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of '<em><b>Matches</b></em>' literal object isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @see #MATCHES
   * @model name="Matches"
   * @generated
   * @ordered
   */
  public static final int MATCHES_VALUE = 9;

  /**
   * The '<em><b>Not Matches</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of '<em><b>Not Matches</b></em>' literal object isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @see #NOT_MATCHES
   * @model name="NotMatches"
   * @generated
   * @ordered
   */
  public static final int NOT_MATCHES_VALUE = 10;

  /**
   * An array of all the '<em><b>Decision Operand</b></em>' enumerators.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private static final DecisionOperand[] VALUES_ARRAY =
    new DecisionOperand[]
    {
      NONE,
      EQUALS,
      NOT_EQUALS,
      GREATER,
      GREATER_OR_EQUALS,
      LESS_THAN,
      LESS_OR_EQUALS,
      BETWEEN,
      NOT_BETWEEN,
      MATCHES,
      NOT_MATCHES,
    };

  /**
   * A public read-only list of all the '<em><b>Decision Operand</b></em>' enumerators.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public static final List<DecisionOperand> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

  /**
   * Returns the '<em><b>Decision Operand</b></em>' literal with the specified literal value.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public static DecisionOperand get(String literal)
  {
    for (int i = 0; i < VALUES_ARRAY.length; ++i)
    {
      DecisionOperand result = VALUES_ARRAY[i];
      if (result.toString().equals(literal))
      {
        return result;
      }
    }
    return null;
  }

  /**
   * Returns the '<em><b>Decision Operand</b></em>' literal with the specified name.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public static DecisionOperand getByName(String name)
  {
    for (int i = 0; i < VALUES_ARRAY.length; ++i)
    {
      DecisionOperand result = VALUES_ARRAY[i];
      if (result.getName().equals(name))
      {
        return result;
      }
    }
    return null;
  }

  /**
   * Returns the '<em><b>Decision Operand</b></em>' literal with the specified integer value.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public static DecisionOperand get(int value)
  {
    switch (value)
    {
      case NONE_VALUE: return NONE;
      case EQUALS_VALUE: return EQUALS;
      case NOT_EQUALS_VALUE: return NOT_EQUALS;
      case GREATER_VALUE: return GREATER;
      case GREATER_OR_EQUALS_VALUE: return GREATER_OR_EQUALS;
      case LESS_THAN_VALUE: return LESS_THAN;
      case LESS_OR_EQUALS_VALUE: return LESS_OR_EQUALS;
      case BETWEEN_VALUE: return BETWEEN;
      case NOT_BETWEEN_VALUE: return NOT_BETWEEN;
      case MATCHES_VALUE: return MATCHES;
      case NOT_MATCHES_VALUE: return NOT_MATCHES;
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
  private DecisionOperand(int value, String name, String literal)
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
  
} //DecisionOperand
