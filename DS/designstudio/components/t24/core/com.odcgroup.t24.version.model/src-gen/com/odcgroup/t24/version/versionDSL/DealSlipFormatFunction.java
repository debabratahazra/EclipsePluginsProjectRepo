/**
 */
package com.odcgroup.t24.version.versionDSL;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Deal Slip Format Function</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see com.odcgroup.t24.version.versionDSL.VersionDSLPackage#getDealSlipFormatFunction()
 * @model
 * @generated
 */
public enum DealSlipFormatFunction implements Enumerator
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
   * The '<em><b>I</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #I_VALUE
   * @generated
   * @ordered
   */
  I(1, "I", "I"),

  /**
   * The '<em><b>A</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #A_VALUE
   * @generated
   * @ordered
   */
  A(2, "A", "A"),

  /**
   * The '<em><b>C</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #C_VALUE
   * @generated
   * @ordered
   */
  C(3, "C", "C"),

  /**
   * The '<em><b>R</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #R_VALUE
   * @generated
   * @ordered
   */
  R(4, "R", "R"),

  /**
   * The '<em><b>D</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #D_VALUE
   * @generated
   * @ordered
   */
  D(5, "D", "D"),

  /**
   * The '<em><b>H</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #H_VALUE
   * @generated
   * @ordered
   */
  H(6, "H", "H"),

  /**
   * The '<em><b>Finish</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #FINISH_VALUE
   * @generated
   * @ordered
   */
  FINISH(7, "Finish", "Finish");

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
   * The '<em><b>I</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of '<em><b>I</b></em>' literal object isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @see #I
   * @model
   * @generated
   * @ordered
   */
  public static final int I_VALUE = 1;

  /**
   * The '<em><b>A</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of '<em><b>A</b></em>' literal object isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @see #A
   * @model
   * @generated
   * @ordered
   */
  public static final int A_VALUE = 2;

  /**
   * The '<em><b>C</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of '<em><b>C</b></em>' literal object isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @see #C
   * @model
   * @generated
   * @ordered
   */
  public static final int C_VALUE = 3;

  /**
   * The '<em><b>R</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of '<em><b>R</b></em>' literal object isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @see #R
   * @model
   * @generated
   * @ordered
   */
  public static final int R_VALUE = 4;

  /**
   * The '<em><b>D</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of '<em><b>D</b></em>' literal object isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @see #D
   * @model
   * @generated
   * @ordered
   */
  public static final int D_VALUE = 5;

  /**
   * The '<em><b>H</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of '<em><b>H</b></em>' literal object isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @see #H
   * @model
   * @generated
   * @ordered
   */
  public static final int H_VALUE = 6;

  /**
   * The '<em><b>Finish</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of '<em><b>Finish</b></em>' literal object isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @see #FINISH
   * @model name="Finish"
   * @generated
   * @ordered
   */
  public static final int FINISH_VALUE = 7;

  /**
   * An array of all the '<em><b>Deal Slip Format Function</b></em>' enumerators.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private static final DealSlipFormatFunction[] VALUES_ARRAY =
    new DealSlipFormatFunction[]
    {
      NONE,
      I,
      A,
      C,
      R,
      D,
      H,
      FINISH,
    };

  /**
   * A public read-only list of all the '<em><b>Deal Slip Format Function</b></em>' enumerators.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public static final List<DealSlipFormatFunction> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

  /**
   * Returns the '<em><b>Deal Slip Format Function</b></em>' literal with the specified literal value.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public static DealSlipFormatFunction get(String literal)
  {
    for (int i = 0; i < VALUES_ARRAY.length; ++i)
    {
      DealSlipFormatFunction result = VALUES_ARRAY[i];
      if (result.toString().equals(literal))
      {
        return result;
      }
    }
    return null;
  }

  /**
   * Returns the '<em><b>Deal Slip Format Function</b></em>' literal with the specified name.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public static DealSlipFormatFunction getByName(String name)
  {
    for (int i = 0; i < VALUES_ARRAY.length; ++i)
    {
      DealSlipFormatFunction result = VALUES_ARRAY[i];
      if (result.getName().equals(name))
      {
        return result;
      }
    }
    return null;
  }

  /**
   * Returns the '<em><b>Deal Slip Format Function</b></em>' literal with the specified integer value.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public static DealSlipFormatFunction get(int value)
  {
    switch (value)
    {
      case NONE_VALUE: return NONE;
      case I_VALUE: return I;
      case A_VALUE: return A;
      case C_VALUE: return C;
      case R_VALUE: return R;
      case D_VALUE: return D;
      case H_VALUE: return H;
      case FINISH_VALUE: return FINISH;
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
  private DealSlipFormatFunction(int value, String name, String literal)
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
  
} //DealSlipFormatFunction
