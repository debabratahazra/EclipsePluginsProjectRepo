/**
 */
package com.odcgroup.t24.version.versionDSL;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Associated Versions Presentation Pattern</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see com.odcgroup.t24.version.versionDSL.VersionDSLPackage#getAssociatedVersionsPresentationPattern()
 * @model
 * @generated
 */
public enum AssociatedVersionsPresentationPattern implements Enumerator
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
   * The '<em><b>Tabs</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #TABS_VALUE
   * @generated
   * @ordered
   */
  TABS(1, "Tabs", "Tabs"),

  /**
   * The '<em><b>Vertical</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #VERTICAL_VALUE
   * @generated
   * @ordered
   */
  VERTICAL(2, "Vertical", "Vertical"),

  /**
   * The '<em><b>Accordions</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #ACCORDIONS_VALUE
   * @generated
   * @ordered
   */
  ACCORDIONS(3, "Accordions", "Accordions");

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
   * The '<em><b>Tabs</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of '<em><b>Tabs</b></em>' literal object isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @see #TABS
   * @model name="Tabs"
   * @generated
   * @ordered
   */
  public static final int TABS_VALUE = 1;

  /**
   * The '<em><b>Vertical</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of '<em><b>Vertical</b></em>' literal object isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @see #VERTICAL
   * @model name="Vertical"
   * @generated
   * @ordered
   */
  public static final int VERTICAL_VALUE = 2;

  /**
   * The '<em><b>Accordions</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of '<em><b>Accordions</b></em>' literal object isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @see #ACCORDIONS
   * @model name="Accordions"
   * @generated
   * @ordered
   */
  public static final int ACCORDIONS_VALUE = 3;

  /**
   * An array of all the '<em><b>Associated Versions Presentation Pattern</b></em>' enumerators.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private static final AssociatedVersionsPresentationPattern[] VALUES_ARRAY =
    new AssociatedVersionsPresentationPattern[]
    {
      NONE,
      TABS,
      VERTICAL,
      ACCORDIONS,
    };

  /**
   * A public read-only list of all the '<em><b>Associated Versions Presentation Pattern</b></em>' enumerators.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public static final List<AssociatedVersionsPresentationPattern> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

  /**
   * Returns the '<em><b>Associated Versions Presentation Pattern</b></em>' literal with the specified literal value.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public static AssociatedVersionsPresentationPattern get(String literal)
  {
    for (int i = 0; i < VALUES_ARRAY.length; ++i)
    {
      AssociatedVersionsPresentationPattern result = VALUES_ARRAY[i];
      if (result.toString().equals(literal))
      {
        return result;
      }
    }
    return null;
  }

  /**
   * Returns the '<em><b>Associated Versions Presentation Pattern</b></em>' literal with the specified name.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public static AssociatedVersionsPresentationPattern getByName(String name)
  {
    for (int i = 0; i < VALUES_ARRAY.length; ++i)
    {
      AssociatedVersionsPresentationPattern result = VALUES_ARRAY[i];
      if (result.getName().equals(name))
      {
        return result;
      }
    }
    return null;
  }

  /**
   * Returns the '<em><b>Associated Versions Presentation Pattern</b></em>' literal with the specified integer value.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public static AssociatedVersionsPresentationPattern get(int value)
  {
    switch (value)
    {
      case NONE_VALUE: return NONE;
      case TABS_VALUE: return TABS;
      case VERTICAL_VALUE: return VERTICAL;
      case ACCORDIONS_VALUE: return ACCORDIONS;
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
  private AssociatedVersionsPresentationPattern(int value, String name, String literal)
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
  
} //AssociatedVersionsPresentationPattern
