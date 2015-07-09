/**
 */
package com.odcgroup.t24.version.versionDSL;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Display Type</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see com.odcgroup.t24.version.versionDSL.VersionDSLPackage#getDisplayType()
 * @model
 * @generated
 */
public enum DisplayType implements Enumerator
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
   * The '<em><b>No Display</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #NO_DISPLAY_VALUE
   * @generated
   * @ordered
   */
  NO_DISPLAY(1, "NoDisplay", "NoDisplay"),

  /**
   * The '<em><b>Combobox</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #COMBOBOX_VALUE
   * @generated
   * @ordered
   */
  COMBOBOX(2, "Combobox", "Combobox"),

  /**
   * The '<em><b>Vertical Options</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #VERTICAL_OPTIONS_VALUE
   * @generated
   * @ordered
   */
  VERTICAL_OPTIONS(3, "VerticalOptions", "Vertical.Options"),

  /**
   * The '<em><b>Toggle</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #TOGGLE_VALUE
   * @generated
   * @ordered
   */
  TOGGLE(4, "Toggle", "Toggle"),

  /**
   * The '<em><b>Drop Down No Input</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #DROP_DOWN_NO_INPUT_VALUE
   * @generated
   * @ordered
   */
  DROP_DOWN_NO_INPUT(5, "DropDownNoInput", "DropDown.NoInput");

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
   * The '<em><b>No Display</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of '<em><b>No Display</b></em>' literal object isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @see #NO_DISPLAY
   * @model name="NoDisplay"
   * @generated
   * @ordered
   */
  public static final int NO_DISPLAY_VALUE = 1;

  /**
   * The '<em><b>Combobox</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of '<em><b>Combobox</b></em>' literal object isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @see #COMBOBOX
   * @model name="Combobox"
   * @generated
   * @ordered
   */
  public static final int COMBOBOX_VALUE = 2;

  /**
   * The '<em><b>Vertical Options</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of '<em><b>Vertical Options</b></em>' literal object isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @see #VERTICAL_OPTIONS
   * @model name="VerticalOptions" literal="Vertical.Options"
   * @generated
   * @ordered
   */
  public static final int VERTICAL_OPTIONS_VALUE = 3;

  /**
   * The '<em><b>Toggle</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of '<em><b>Toggle</b></em>' literal object isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @see #TOGGLE
   * @model name="Toggle"
   * @generated
   * @ordered
   */
  public static final int TOGGLE_VALUE = 4;

  /**
   * The '<em><b>Drop Down No Input</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of '<em><b>Drop Down No Input</b></em>' literal object isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @see #DROP_DOWN_NO_INPUT
   * @model name="DropDownNoInput" literal="DropDown.NoInput"
   * @generated
   * @ordered
   */
  public static final int DROP_DOWN_NO_INPUT_VALUE = 5;

  /**
   * An array of all the '<em><b>Display Type</b></em>' enumerators.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private static final DisplayType[] VALUES_ARRAY =
    new DisplayType[]
    {
      NONE,
      NO_DISPLAY,
      COMBOBOX,
      VERTICAL_OPTIONS,
      TOGGLE,
      DROP_DOWN_NO_INPUT,
    };

  /**
   * A public read-only list of all the '<em><b>Display Type</b></em>' enumerators.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public static final List<DisplayType> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

  /**
   * Returns the '<em><b>Display Type</b></em>' literal with the specified literal value.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public static DisplayType get(String literal)
  {
    for (int i = 0; i < VALUES_ARRAY.length; ++i)
    {
      DisplayType result = VALUES_ARRAY[i];
      if (result.toString().equals(literal))
      {
        return result;
      }
    }
    return null;
  }

  /**
   * Returns the '<em><b>Display Type</b></em>' literal with the specified name.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public static DisplayType getByName(String name)
  {
    for (int i = 0; i < VALUES_ARRAY.length; ++i)
    {
      DisplayType result = VALUES_ARRAY[i];
      if (result.getName().equals(name))
      {
        return result;
      }
    }
    return null;
  }

  /**
   * Returns the '<em><b>Display Type</b></em>' literal with the specified integer value.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public static DisplayType get(int value)
  {
    switch (value)
    {
      case NONE_VALUE: return NONE;
      case NO_DISPLAY_VALUE: return NO_DISPLAY;
      case COMBOBOX_VALUE: return COMBOBOX;
      case VERTICAL_OPTIONS_VALUE: return VERTICAL_OPTIONS;
      case TOGGLE_VALUE: return TOGGLE;
      case DROP_DOWN_NO_INPUT_VALUE: return DROP_DOWN_NO_INPUT;
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
  private DisplayType(int value, String name, String literal)
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
  
} //DisplayType
