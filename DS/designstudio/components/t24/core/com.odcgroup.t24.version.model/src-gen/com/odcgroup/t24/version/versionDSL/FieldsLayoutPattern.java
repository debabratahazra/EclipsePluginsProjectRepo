/**
 */
package com.odcgroup.t24.version.versionDSL;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Fields Layout Pattern</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see com.odcgroup.t24.version.versionDSL.VersionDSLPackage#getFieldsLayoutPattern()
 * @model
 * @generated
 */
public enum FieldsLayoutPattern implements Enumerator
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
   * The '<em><b>One Row</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #ONE_ROW_VALUE
   * @generated
   * @ordered
   */
  ONE_ROW(1, "OneRow", "OneRow"),

  /**
   * The '<em><b>One Column</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #ONE_COLUMN_VALUE
   * @generated
   * @ordered
   */
  ONE_COLUMN(2, "OneColumn", "OneColumn"),

  /**
   * The '<em><b>Two Column Horizontal</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #TWO_COLUMN_HORIZONTAL_VALUE
   * @generated
   * @ordered
   */
  TWO_COLUMN_HORIZONTAL(3, "TwoColumnHorizontal", "TwoColumnHorizontal"),

  /**
   * The '<em><b>Two Column Vertical</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #TWO_COLUMN_VERTICAL_VALUE
   * @generated
   * @ordered
   */
  TWO_COLUMN_VERTICAL(4, "TwoColumnVertical", "TwoColumnVertical"),

  /**
   * The '<em><b>Three Column Horizontal</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #THREE_COLUMN_HORIZONTAL_VALUE
   * @generated
   * @ordered
   */
  THREE_COLUMN_HORIZONTAL(5, "ThreeColumnHorizontal", "ThreeColumnHorizontal"),

  /**
   * The '<em><b>Three Column Vertical</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #THREE_COLUMN_VERTICAL_VALUE
   * @generated
   * @ordered
   */
  THREE_COLUMN_VERTICAL(6, "ThreeColumnVertical", "ThreeColumnVertical");

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
   * The '<em><b>One Row</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of '<em><b>One Row</b></em>' literal object isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @see #ONE_ROW
   * @model name="OneRow"
   * @generated
   * @ordered
   */
  public static final int ONE_ROW_VALUE = 1;

  /**
   * The '<em><b>One Column</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of '<em><b>One Column</b></em>' literal object isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @see #ONE_COLUMN
   * @model name="OneColumn"
   * @generated
   * @ordered
   */
  public static final int ONE_COLUMN_VALUE = 2;

  /**
   * The '<em><b>Two Column Horizontal</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of '<em><b>Two Column Horizontal</b></em>' literal object isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @see #TWO_COLUMN_HORIZONTAL
   * @model name="TwoColumnHorizontal"
   * @generated
   * @ordered
   */
  public static final int TWO_COLUMN_HORIZONTAL_VALUE = 3;

  /**
   * The '<em><b>Two Column Vertical</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of '<em><b>Two Column Vertical</b></em>' literal object isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @see #TWO_COLUMN_VERTICAL
   * @model name="TwoColumnVertical"
   * @generated
   * @ordered
   */
  public static final int TWO_COLUMN_VERTICAL_VALUE = 4;

  /**
   * The '<em><b>Three Column Horizontal</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of '<em><b>Three Column Horizontal</b></em>' literal object isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @see #THREE_COLUMN_HORIZONTAL
   * @model name="ThreeColumnHorizontal"
   * @generated
   * @ordered
   */
  public static final int THREE_COLUMN_HORIZONTAL_VALUE = 5;

  /**
   * The '<em><b>Three Column Vertical</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of '<em><b>Three Column Vertical</b></em>' literal object isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @see #THREE_COLUMN_VERTICAL
   * @model name="ThreeColumnVertical"
   * @generated
   * @ordered
   */
  public static final int THREE_COLUMN_VERTICAL_VALUE = 6;

  /**
   * An array of all the '<em><b>Fields Layout Pattern</b></em>' enumerators.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private static final FieldsLayoutPattern[] VALUES_ARRAY =
    new FieldsLayoutPattern[]
    {
      NONE,
      ONE_ROW,
      ONE_COLUMN,
      TWO_COLUMN_HORIZONTAL,
      TWO_COLUMN_VERTICAL,
      THREE_COLUMN_HORIZONTAL,
      THREE_COLUMN_VERTICAL,
    };

  /**
   * A public read-only list of all the '<em><b>Fields Layout Pattern</b></em>' enumerators.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public static final List<FieldsLayoutPattern> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

  /**
   * Returns the '<em><b>Fields Layout Pattern</b></em>' literal with the specified literal value.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public static FieldsLayoutPattern get(String literal)
  {
    for (int i = 0; i < VALUES_ARRAY.length; ++i)
    {
      FieldsLayoutPattern result = VALUES_ARRAY[i];
      if (result.toString().equals(literal))
      {
        return result;
      }
    }
    return null;
  }

  /**
   * Returns the '<em><b>Fields Layout Pattern</b></em>' literal with the specified name.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public static FieldsLayoutPattern getByName(String name)
  {
    for (int i = 0; i < VALUES_ARRAY.length; ++i)
    {
      FieldsLayoutPattern result = VALUES_ARRAY[i];
      if (result.getName().equals(name))
      {
        return result;
      }
    }
    return null;
  }

  /**
   * Returns the '<em><b>Fields Layout Pattern</b></em>' literal with the specified integer value.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public static FieldsLayoutPattern get(int value)
  {
    switch (value)
    {
      case NONE_VALUE: return NONE;
      case ONE_ROW_VALUE: return ONE_ROW;
      case ONE_COLUMN_VALUE: return ONE_COLUMN;
      case TWO_COLUMN_HORIZONTAL_VALUE: return TWO_COLUMN_HORIZONTAL;
      case TWO_COLUMN_VERTICAL_VALUE: return TWO_COLUMN_VERTICAL;
      case THREE_COLUMN_HORIZONTAL_VALUE: return THREE_COLUMN_HORIZONTAL;
      case THREE_COLUMN_VERTICAL_VALUE: return THREE_COLUMN_VERTICAL;
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
  private FieldsLayoutPattern(int value, String name, String literal)
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
  
} //FieldsLayoutPattern
