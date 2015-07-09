/**
 */
package com.odcgroup.t24.enquiry.enquiry;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Selection Operator</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getSelectionOperator()
 * @model
 * @generated
 */
public enum SelectionOperator implements Enumerator
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
   * The '<em><b>Matches</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #MATCHES_VALUE
   * @generated
   * @ordered
   */
  MATCHES(7, "Matches", "Matches"),

  /**
   * The '<em><b>Not Matches</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #NOT_MATCHES_VALUE
   * @generated
   * @ordered
   */
  NOT_MATCHES(8, "NotMatches", "NotMatches"),

  /**
   * The '<em><b>Between</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #BETWEEN_VALUE
   * @generated
   * @ordered
   */
  BETWEEN(9, "Between", "Between"),

  /**
   * The '<em><b>Not Between</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #NOT_BETWEEN_VALUE
   * @generated
   * @ordered
   */
  NOT_BETWEEN(10, "NotBetween", "NotBetween"),

  /**
   * The '<em><b>Contains</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #CONTAINS_VALUE
   * @generated
   * @ordered
   */
  CONTAINS(11, "Contains", "Contains"),

  /**
   * The '<em><b>Does Not Contain</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #DOES_NOT_CONTAIN_VALUE
   * @generated
   * @ordered
   */
  DOES_NOT_CONTAIN(12, "DoesNotContain", "DoesNotContain"),

  /**
   * The '<em><b>Begins With</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #BEGINS_WITH_VALUE
   * @generated
   * @ordered
   */
  BEGINS_WITH(13, "BeginsWith", "BeginsWith"),

  /**
   * The '<em><b>Does Not Begin With</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #DOES_NOT_BEGIN_WITH_VALUE
   * @generated
   * @ordered
   */
  DOES_NOT_BEGIN_WITH(14, "DoesNotBeginWith", "DoesNotBeginWith"),

  /**
   * The '<em><b>Ends With</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #ENDS_WITH_VALUE
   * @generated
   * @ordered
   */
  ENDS_WITH(15, "EndsWith", "EndsWith"),

  /**
   * The '<em><b>Does Not End With</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #DOES_NOT_END_WITH_VALUE
   * @generated
   * @ordered
   */
  DOES_NOT_END_WITH(16, "DoesNotEndWith", "DoesNotEndWith"),

  /**
   * The '<em><b>Sounds Like</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #SOUNDS_LIKE_VALUE
   * @generated
   * @ordered
   */
  SOUNDS_LIKE(17, "SoundsLike", "SoundsLike");

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
  public static final int MATCHES_VALUE = 7;

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
  public static final int NOT_MATCHES_VALUE = 8;

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
  public static final int BETWEEN_VALUE = 9;

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
  public static final int NOT_BETWEEN_VALUE = 10;

  /**
   * The '<em><b>Contains</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of '<em><b>Contains</b></em>' literal object isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @see #CONTAINS
   * @model name="Contains"
   * @generated
   * @ordered
   */
  public static final int CONTAINS_VALUE = 11;

  /**
   * The '<em><b>Does Not Contain</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of '<em><b>Does Not Contain</b></em>' literal object isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @see #DOES_NOT_CONTAIN
   * @model name="DoesNotContain"
   * @generated
   * @ordered
   */
  public static final int DOES_NOT_CONTAIN_VALUE = 12;

  /**
   * The '<em><b>Begins With</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of '<em><b>Begins With</b></em>' literal object isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @see #BEGINS_WITH
   * @model name="BeginsWith"
   * @generated
   * @ordered
   */
  public static final int BEGINS_WITH_VALUE = 13;

  /**
   * The '<em><b>Does Not Begin With</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of '<em><b>Does Not Begin With</b></em>' literal object isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @see #DOES_NOT_BEGIN_WITH
   * @model name="DoesNotBeginWith"
   * @generated
   * @ordered
   */
  public static final int DOES_NOT_BEGIN_WITH_VALUE = 14;

  /**
   * The '<em><b>Ends With</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of '<em><b>Ends With</b></em>' literal object isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @see #ENDS_WITH
   * @model name="EndsWith"
   * @generated
   * @ordered
   */
  public static final int ENDS_WITH_VALUE = 15;

  /**
   * The '<em><b>Does Not End With</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of '<em><b>Does Not End With</b></em>' literal object isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @see #DOES_NOT_END_WITH
   * @model name="DoesNotEndWith"
   * @generated
   * @ordered
   */
  public static final int DOES_NOT_END_WITH_VALUE = 16;

  /**
   * The '<em><b>Sounds Like</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of '<em><b>Sounds Like</b></em>' literal object isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @see #SOUNDS_LIKE
   * @model name="SoundsLike"
   * @generated
   * @ordered
   */
  public static final int SOUNDS_LIKE_VALUE = 17;

  /**
   * An array of all the '<em><b>Selection Operator</b></em>' enumerators.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private static final SelectionOperator[] VALUES_ARRAY =
    new SelectionOperator[]
    {
      NONE,
      EQUALS,
      NOT_EQUALS,
      GREATER,
      GREATER_OR_EQUALS,
      LESS_THAN,
      LESS_OR_EQUALS,
      MATCHES,
      NOT_MATCHES,
      BETWEEN,
      NOT_BETWEEN,
      CONTAINS,
      DOES_NOT_CONTAIN,
      BEGINS_WITH,
      DOES_NOT_BEGIN_WITH,
      ENDS_WITH,
      DOES_NOT_END_WITH,
      SOUNDS_LIKE,
    };

  /**
   * A public read-only list of all the '<em><b>Selection Operator</b></em>' enumerators.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public static final List<SelectionOperator> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

  /**
   * Returns the '<em><b>Selection Operator</b></em>' literal with the specified literal value.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public static SelectionOperator get(String literal)
  {
    for (int i = 0; i < VALUES_ARRAY.length; ++i)
    {
      SelectionOperator result = VALUES_ARRAY[i];
      if (result.toString().equals(literal))
      {
        return result;
      }
    }
    return null;
  }

  /**
   * Returns the '<em><b>Selection Operator</b></em>' literal with the specified name.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public static SelectionOperator getByName(String name)
  {
    for (int i = 0; i < VALUES_ARRAY.length; ++i)
    {
      SelectionOperator result = VALUES_ARRAY[i];
      if (result.getName().equals(name))
      {
        return result;
      }
    }
    return null;
  }

  /**
   * Returns the '<em><b>Selection Operator</b></em>' literal with the specified integer value.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public static SelectionOperator get(int value)
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
      case MATCHES_VALUE: return MATCHES;
      case NOT_MATCHES_VALUE: return NOT_MATCHES;
      case BETWEEN_VALUE: return BETWEEN;
      case NOT_BETWEEN_VALUE: return NOT_BETWEEN;
      case CONTAINS_VALUE: return CONTAINS;
      case DOES_NOT_CONTAIN_VALUE: return DOES_NOT_CONTAIN;
      case BEGINS_WITH_VALUE: return BEGINS_WITH;
      case DOES_NOT_BEGIN_WITH_VALUE: return DOES_NOT_BEGIN_WITH;
      case ENDS_WITH_VALUE: return ENDS_WITH;
      case DOES_NOT_END_WITH_VALUE: return DOES_NOT_END_WITH;
      case SOUNDS_LIKE_VALUE: return SOUNDS_LIKE;
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
  private SelectionOperator(int value, String name, String literal)
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
  
} //SelectionOperator
