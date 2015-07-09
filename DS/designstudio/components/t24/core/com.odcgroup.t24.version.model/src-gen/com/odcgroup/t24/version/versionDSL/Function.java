/**
 */
package com.odcgroup.t24.version.versionDSL;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Function</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see com.odcgroup.t24.version.versionDSL.VersionDSLPackage#getFunction()
 * @model
 * @generated
 */
public enum Function implements Enumerator
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
   * The '<em><b>Input</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #INPUT_VALUE
   * @generated
   * @ordered
   */
  INPUT(1, "Input", "Input"),

  /**
   * The '<em><b>Authorise</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #AUTHORISE_VALUE
   * @generated
   * @ordered
   */
  AUTHORISE(2, "Authorise", "Authorise"),

  /**
   * The '<em><b>Reverse</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #REVERSE_VALUE
   * @generated
   * @ordered
   */
  REVERSE(3, "Reverse", "Reverse"),

  /**
   * The '<em><b>See</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #SEE_VALUE
   * @generated
   * @ordered
   */
  SEE(4, "See", "See"),

  /**
   * The '<em><b>Copy</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #COPY_VALUE
   * @generated
   * @ordered
   */
  COPY(5, "Copy", "Copy"),

  /**
   * The '<em><b>Delete</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #DELETE_VALUE
   * @generated
   * @ordered
   */
  DELETE(6, "Delete", "Delete"),

  /**
   * The '<em><b>History Restore</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #HISTORY_RESTORE_VALUE
   * @generated
   * @ordered
   */
  HISTORY_RESTORE(7, "HistoryRestore", "HistoryRestore"),

  /**
   * The '<em><b>Verify</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #VERIFY_VALUE
   * @generated
   * @ordered
   */
  VERIFY(8, "Verify", "Verify"),

  /**
   * The '<em><b>Auditor Review</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #AUDITOR_REVIEW_VALUE
   * @generated
   * @ordered
   */
  AUDITOR_REVIEW(9, "AuditorReview", "AuditorReview");

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
   * The '<em><b>Input</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of '<em><b>Input</b></em>' literal object isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @see #INPUT
   * @model name="Input"
   * @generated
   * @ordered
   */
  public static final int INPUT_VALUE = 1;

  /**
   * The '<em><b>Authorise</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of '<em><b>Authorise</b></em>' literal object isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @see #AUTHORISE
   * @model name="Authorise"
   * @generated
   * @ordered
   */
  public static final int AUTHORISE_VALUE = 2;

  /**
   * The '<em><b>Reverse</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of '<em><b>Reverse</b></em>' literal object isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @see #REVERSE
   * @model name="Reverse"
   * @generated
   * @ordered
   */
  public static final int REVERSE_VALUE = 3;

  /**
   * The '<em><b>See</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of '<em><b>See</b></em>' literal object isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @see #SEE
   * @model name="See"
   * @generated
   * @ordered
   */
  public static final int SEE_VALUE = 4;

  /**
   * The '<em><b>Copy</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of '<em><b>Copy</b></em>' literal object isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @see #COPY
   * @model name="Copy"
   * @generated
   * @ordered
   */
  public static final int COPY_VALUE = 5;

  /**
   * The '<em><b>Delete</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of '<em><b>Delete</b></em>' literal object isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @see #DELETE
   * @model name="Delete"
   * @generated
   * @ordered
   */
  public static final int DELETE_VALUE = 6;

  /**
   * The '<em><b>History Restore</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of '<em><b>History Restore</b></em>' literal object isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @see #HISTORY_RESTORE
   * @model name="HistoryRestore"
   * @generated
   * @ordered
   */
  public static final int HISTORY_RESTORE_VALUE = 7;

  /**
   * The '<em><b>Verify</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of '<em><b>Verify</b></em>' literal object isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @see #VERIFY
   * @model name="Verify"
   * @generated
   * @ordered
   */
  public static final int VERIFY_VALUE = 8;

  /**
   * The '<em><b>Auditor Review</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of '<em><b>Auditor Review</b></em>' literal object isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @see #AUDITOR_REVIEW
   * @model name="AuditorReview"
   * @generated
   * @ordered
   */
  public static final int AUDITOR_REVIEW_VALUE = 9;

  /**
   * An array of all the '<em><b>Function</b></em>' enumerators.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private static final Function[] VALUES_ARRAY =
    new Function[]
    {
      NONE,
      INPUT,
      AUTHORISE,
      REVERSE,
      SEE,
      COPY,
      DELETE,
      HISTORY_RESTORE,
      VERIFY,
      AUDITOR_REVIEW,
    };

  /**
   * A public read-only list of all the '<em><b>Function</b></em>' enumerators.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public static final List<Function> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

  /**
   * Returns the '<em><b>Function</b></em>' literal with the specified literal value.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public static Function get(String literal)
  {
    for (int i = 0; i < VALUES_ARRAY.length; ++i)
    {
      Function result = VALUES_ARRAY[i];
      if (result.toString().equals(literal))
      {
        return result;
      }
    }
    return null;
  }

  /**
   * Returns the '<em><b>Function</b></em>' literal with the specified name.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public static Function getByName(String name)
  {
    for (int i = 0; i < VALUES_ARRAY.length; ++i)
    {
      Function result = VALUES_ARRAY[i];
      if (result.getName().equals(name))
      {
        return result;
      }
    }
    return null;
  }

  /**
   * Returns the '<em><b>Function</b></em>' literal with the specified integer value.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public static Function get(int value)
  {
    switch (value)
    {
      case NONE_VALUE: return NONE;
      case INPUT_VALUE: return INPUT;
      case AUTHORISE_VALUE: return AUTHORISE;
      case REVERSE_VALUE: return REVERSE;
      case SEE_VALUE: return SEE;
      case COPY_VALUE: return COPY;
      case DELETE_VALUE: return DELETE;
      case HISTORY_RESTORE_VALUE: return HISTORY_RESTORE;
      case VERIFY_VALUE: return VERIFY;
      case AUDITOR_REVIEW_VALUE: return AUDITOR_REVIEW;
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
  private Function(int value, String name, String literal)
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
  
} //Function
