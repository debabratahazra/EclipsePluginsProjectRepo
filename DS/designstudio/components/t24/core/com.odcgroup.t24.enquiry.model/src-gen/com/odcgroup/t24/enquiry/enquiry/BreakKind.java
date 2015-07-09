/**
 */
package com.odcgroup.t24.enquiry.enquiry;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Break Kind</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getBreakKind()
 * @model
 * @generated
 */
public enum BreakKind implements Enumerator
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
   * The '<em><b>Once</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #ONCE_VALUE
   * @generated
   * @ordered
   */
  ONCE(2, "Once", "Once"),

  /**
   * The '<em><b>End</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #END_VALUE
   * @generated
   * @ordered
   */
  END(3, "End", "End"),

  /**
   * The '<em><b>New Page</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #NEW_PAGE_VALUE
   * @generated
   * @ordered
   */
  NEW_PAGE(4, "NewPage", "NewPage"),

  /**
   * The '<em><b>Page</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #PAGE_VALUE
   * @generated
   * @ordered
   */
  PAGE(5, "Page", "Page");

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
   * The '<em><b>Once</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of '<em><b>Once</b></em>' literal object isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @see #ONCE
   * @model name="Once"
   * @generated
   * @ordered
   */
  public static final int ONCE_VALUE = 2;

  /**
   * The '<em><b>End</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of '<em><b>End</b></em>' literal object isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @see #END
   * @model name="End"
   * @generated
   * @ordered
   */
  public static final int END_VALUE = 3;

  /**
   * The '<em><b>New Page</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of '<em><b>New Page</b></em>' literal object isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @see #NEW_PAGE
   * @model name="NewPage"
   * @generated
   * @ordered
   */
  public static final int NEW_PAGE_VALUE = 4;

  /**
   * The '<em><b>Page</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of '<em><b>Page</b></em>' literal object isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @see #PAGE
   * @model name="Page"
   * @generated
   * @ordered
   */
  public static final int PAGE_VALUE = 5;

  /**
   * An array of all the '<em><b>Break Kind</b></em>' enumerators.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private static final BreakKind[] VALUES_ARRAY =
    new BreakKind[]
    {
      UNSPECIFIED,
      NONE,
      ONCE,
      END,
      NEW_PAGE,
      PAGE,
    };

  /**
   * A public read-only list of all the '<em><b>Break Kind</b></em>' enumerators.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public static final List<BreakKind> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

  /**
   * Returns the '<em><b>Break Kind</b></em>' literal with the specified literal value.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public static BreakKind get(String literal)
  {
    for (int i = 0; i < VALUES_ARRAY.length; ++i)
    {
      BreakKind result = VALUES_ARRAY[i];
      if (result.toString().equals(literal))
      {
        return result;
      }
    }
    return null;
  }

  /**
   * Returns the '<em><b>Break Kind</b></em>' literal with the specified name.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public static BreakKind getByName(String name)
  {
    for (int i = 0; i < VALUES_ARRAY.length; ++i)
    {
      BreakKind result = VALUES_ARRAY[i];
      if (result.getName().equals(name))
      {
        return result;
      }
    }
    return null;
  }

  /**
   * Returns the '<em><b>Break Kind</b></em>' literal with the specified integer value.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public static BreakKind get(int value)
  {
    switch (value)
    {
      case UNSPECIFIED_VALUE: return UNSPECIFIED;
      case NONE_VALUE: return NONE;
      case ONCE_VALUE: return ONCE;
      case END_VALUE: return END;
      case NEW_PAGE_VALUE: return NEW_PAGE;
      case PAGE_VALUE: return PAGE;
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
  private BreakKind(int value, String name, String literal)
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
  
} //BreakKind
