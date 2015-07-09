/**
 */
package com.odcgroup.t24.version.versionDSL;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Popup Behaviour</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see com.odcgroup.t24.version.versionDSL.VersionDSLPackage#getPopupBehaviour()
 * @model
 * @generated
 */
public enum PopupBehaviour implements Enumerator
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
   * The '<em><b>Calendar</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #CALENDAR_VALUE
   * @generated
   * @ordered
   */
  CALENDAR(1, "Calendar", "Calendar"),

  /**
   * The '<em><b>Calculator</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #CALCULATOR_VALUE
   * @generated
   * @ordered
   */
  CALCULATOR(2, "Calculator", "Calculator"),

  /**
   * The '<em><b>Rate Control</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #RATE_CONTROL_VALUE
   * @generated
   * @ordered
   */
  RATE_CONTROL(3, "RateControl", "RATE CONTROL"),

  /**
   * The '<em><b>Recurrence</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #RECURRENCE_VALUE
   * @generated
   * @ordered
   */
  RECURRENCE(4, "Recurrence", "Recurrence");

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
   * The '<em><b>Calendar</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of '<em><b>Calendar</b></em>' literal object isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @see #CALENDAR
   * @model name="Calendar"
   * @generated
   * @ordered
   */
  public static final int CALENDAR_VALUE = 1;

  /**
   * The '<em><b>Calculator</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of '<em><b>Calculator</b></em>' literal object isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @see #CALCULATOR
   * @model name="Calculator"
   * @generated
   * @ordered
   */
  public static final int CALCULATOR_VALUE = 2;

  /**
   * The '<em><b>Rate Control</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of '<em><b>Rate Control</b></em>' literal object isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @see #RATE_CONTROL
   * @model name="RateControl" literal="RATE CONTROL"
   * @generated
   * @ordered
   */
  public static final int RATE_CONTROL_VALUE = 3;

  /**
   * The '<em><b>Recurrence</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of '<em><b>Recurrence</b></em>' literal object isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @see #RECURRENCE
   * @model name="Recurrence"
   * @generated
   * @ordered
   */
  public static final int RECURRENCE_VALUE = 4;

  /**
   * An array of all the '<em><b>Popup Behaviour</b></em>' enumerators.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private static final PopupBehaviour[] VALUES_ARRAY =
    new PopupBehaviour[]
    {
      NONE,
      CALENDAR,
      CALCULATOR,
      RATE_CONTROL,
      RECURRENCE,
    };

  /**
   * A public read-only list of all the '<em><b>Popup Behaviour</b></em>' enumerators.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public static final List<PopupBehaviour> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

  /**
   * Returns the '<em><b>Popup Behaviour</b></em>' literal with the specified literal value.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public static PopupBehaviour get(String literal)
  {
    for (int i = 0; i < VALUES_ARRAY.length; ++i)
    {
      PopupBehaviour result = VALUES_ARRAY[i];
      if (result.toString().equals(literal))
      {
        return result;
      }
    }
    return null;
  }

  /**
   * Returns the '<em><b>Popup Behaviour</b></em>' literal with the specified name.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public static PopupBehaviour getByName(String name)
  {
    for (int i = 0; i < VALUES_ARRAY.length; ++i)
    {
      PopupBehaviour result = VALUES_ARRAY[i];
      if (result.getName().equals(name))
      {
        return result;
      }
    }
    return null;
  }

  /**
   * Returns the '<em><b>Popup Behaviour</b></em>' literal with the specified integer value.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public static PopupBehaviour get(int value)
  {
    switch (value)
    {
      case NONE_VALUE: return NONE;
      case CALENDAR_VALUE: return CALENDAR;
      case CALCULATOR_VALUE: return CALCULATOR;
      case RATE_CONTROL_VALUE: return RATE_CONTROL;
      case RECURRENCE_VALUE: return RECURRENCE;
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
  private PopupBehaviour(int value, String name, String literal)
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
  
} //PopupBehaviour
