/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.odcgroup.page.metamodel;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Property Category</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see com.odcgroup.page.metamodel.MetaModelPackage#getPropertyCategory()
 * @model
 * @generated
 */
public enum PropertyCategory implements Enumerator
{
	/**
	 * The '<em><b>None</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #NONE
	 * @generated
	 * @ordered
	 */
	NONE_LITERAL(0, "None", "None"),
	/**
	 * The '<em><b>General</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #GENERAL
	 * @generated
	 * @ordered
	 */
	GENERAL_LITERAL(1, "General", "General"),
	/**
	 * The '<em><b>Presentation</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #PRESENTATION
	 * @generated
	 * @ordered
	 */
	PRESENTATION_LITERAL(3, "Presentation", "Presentation"), /**
	 * The '<em><b>Event</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #EVENT
	 * @generated
	 * @ordered
	 */
	EVENT_LITERAL(6, "Event", "Event"), /**
	 * The '<em><b>Description</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #DESCRIPTION
	 * @generated
	 * @ordered
	 */
	DESCRIPTION_LITERAL(2, "Description", "Description"), /**
	 * The '<em><b>Limitation</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #LIMITATION
	 * @generated
	 * @ordered
	 */
	LIMITATION_LITERAL(4, "Limitation", "Limitation"), /**
	 * The '<em><b>Technical</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #TECHNICAL
	 * @generated
	 * @ordered
	 */
	TECHNICAL_LITERAL(5, "Technical", "Technical"), /**
	 * The '<em><b>Localization</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #LOCALIZATION
	 * @generated
	 * @ordered
	 */
	LOCALIZATION_LITERAL(7, "Localization", "Localization"), /**
	 * The '<em><b>Autocomplete</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #AUTOCOMPLETE
	 * @generated
	 * @ordered
	 */
	AUTOCOMPLETE_LITERAL(8, "Autocomplete", "Autocomplete"), /**
	 * The '<em><b>Xtooltip</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #XTOOLTIP
	 * @generated
	 * @ordered
	 */
	XTOOLTIP_LITERAL(9, "Xtooltip", "Xtooltip");
	/**
	 * The '<em><b>None</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>None</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #NONE_LITERAL
	 * @model name="None"
	 * @generated
	 * @ordered
	 */
	public static final int NONE = 0;

	/**
	 * The '<em><b>General</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>General</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #GENERAL_LITERAL
	 * @model name="General"
	 * @generated
	 * @ordered
	 */
	public static final int GENERAL = 1;

	/**
	 * The '<em><b>Presentation</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Presentation</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #PRESENTATION_LITERAL
	 * @model name="Presentation"
	 * @generated
	 * @ordered
	 */
	public static final int PRESENTATION = 3;

	/**
	 * The '<em><b>Event</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Event</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #EVENT_LITERAL
	 * @model name="Event"
	 * @generated
	 * @ordered
	 */
	public static final int EVENT = 6;

	/**
	 * The '<em><b>Description</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Description</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #DESCRIPTION_LITERAL
	 * @model name="Description"
	 * @generated
	 * @ordered
	 */
	public static final int DESCRIPTION = 2;

	/**
	 * The '<em><b>Limitation</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Limitation</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #LIMITATION_LITERAL
	 * @model name="Limitation"
	 * @generated
	 * @ordered
	 */
	public static final int LIMITATION = 4;

	/**
	 * The '<em><b>Technical</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Technical</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #TECHNICAL_LITERAL
	 * @model name="Technical"
	 * @generated
	 * @ordered
	 */
	public static final int TECHNICAL = 5;

	/**
	 * The '<em><b>Localization</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Localization</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #LOCALIZATION_LITERAL
	 * @model name="Localization"
	 * @generated
	 * @ordered
	 */
	public static final int LOCALIZATION = 7;

	/**
	 * The '<em><b>Autocomplete</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Autocomplete</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #AUTOCOMPLETE_LITERAL
	 * @model name="Autocomplete"
	 * @generated
	 * @ordered
	 */
	public static final int AUTOCOMPLETE = 8;

	/**
	 * The '<em><b>Xtooltip</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Xtooltip</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #XTOOLTIP_LITERAL
	 * @model name="Xtooltip"
	 * @generated
	 * @ordered
	 */
	public static final int XTOOLTIP = 9;

	/**
	 * An array of all the '<em><b>Property Category</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final PropertyCategory[] VALUES_ARRAY =
		new PropertyCategory[] {
			NONE_LITERAL,
			GENERAL_LITERAL,
			PRESENTATION_LITERAL,
			EVENT_LITERAL,
			DESCRIPTION_LITERAL,
			LIMITATION_LITERAL,
			TECHNICAL_LITERAL,
			LOCALIZATION_LITERAL,
			AUTOCOMPLETE_LITERAL,
			XTOOLTIP_LITERAL,
		};

	/**
	 * A public read-only list of all the '<em><b>Property Category</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<PropertyCategory> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Property Category</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * @param literal
	 * @return PropertyCategory
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static PropertyCategory get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			PropertyCategory result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Property Category</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * @param name
	 * @return PropertyCategory
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static PropertyCategory getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			PropertyCategory result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Property Category</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * @param value
	 * @return PropertyCategory
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static PropertyCategory get(int value) {
		switch (value) {
			case NONE: return NONE_LITERAL;
			case GENERAL: return GENERAL_LITERAL;
			case PRESENTATION: return PRESENTATION_LITERAL;
			case EVENT: return EVENT_LITERAL;
			case DESCRIPTION: return DESCRIPTION_LITERAL;
			case LIMITATION: return LIMITATION_LITERAL;
			case TECHNICAL: return TECHNICAL_LITERAL;
			case LOCALIZATION: return LOCALIZATION_LITERAL;
			case AUTOCOMPLETE: return AUTOCOMPLETE_LITERAL;
			case XTOOLTIP: return XTOOLTIP_LITERAL;
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
	 * @param value
	 * @param name
	 * @param literal
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private PropertyCategory(int value, String name, String literal) {
		this.value = value;
		this.name = name;
		this.literal = literal;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return int
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getValue() {
	  return value;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return String
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
	  return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return String
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getLiteral() {
	  return literal;
	}

	/**
	 * Returns the literal value of the enumerator, which is its string representation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		return literal;
	}
}
