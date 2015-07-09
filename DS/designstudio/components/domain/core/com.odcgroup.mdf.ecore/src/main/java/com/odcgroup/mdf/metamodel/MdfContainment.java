/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.odcgroup.mdf.metamodel;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.AbstractEnumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Containment</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see com.odcgroup.mdf.ecore.MdfPackage#getMdfContainment()
 * @model instanceClass="com.odcgroup.mdf.metamodel.MdfContainment"
 */
public final class MdfContainment extends AbstractEnumerator {
    /**
	 * The '<em><b>BY VALUE</b></em>' literal value.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>BY VALUE</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * @see #BY_VALUE_LITERAL
	 * @model literal="byValue"
	 * @ordered
	 */
    public static final int BY_VALUE = 0;

    /**
	 * The '<em><b>BY REFERENCE</b></em>' literal value.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>BY REFERENCE</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * @see #BY_REFERENCE_LITERAL
	 * @model literal="byReference"
	 * @ordered
	 */
    public static final int BY_REFERENCE = 1;

    /**
	 * The '<em><b>BY VALUE</b></em>' literal object.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #BY_VALUE
	 * @ordered
	 */
    public static final MdfContainment BY_VALUE_LITERAL = new MdfContainment(BY_VALUE, "BY_VALUE", "byValue");

    /**
	 * The '<em><b>BY REFERENCE</b></em>' literal object.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #BY_REFERENCE
	 * @ordered
	 */
    public static final MdfContainment BY_REFERENCE_LITERAL = new MdfContainment(BY_REFERENCE, "BY_REFERENCE", "byReference");

    /**
	 * An array of all the '<em><b>Containment</b></em>' enumerators.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 */
    private static final MdfContainment[] VALUES_ARRAY =
        new MdfContainment[] {
			BY_VALUE_LITERAL,
			BY_REFERENCE_LITERAL,
		};

    /**
	 * A public read-only list of all the '<em><b>Containment</b></em>' enumerators.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 */
    public static final List VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

    /**
	 * Returns the '<em><b>Containment</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 */
    public static MdfContainment get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			MdfContainment result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

    /**
	 * Returns the '<em><b>Containment</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 */
    public static MdfContainment getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			MdfContainment result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

    /**
	 * Returns the '<em><b>Containment</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 */
    public static MdfContainment get(int value) {
		switch (value) {
			case BY_VALUE: return BY_VALUE_LITERAL;
			case BY_REFERENCE: return BY_REFERENCE_LITERAL;
		}
		return null;
	}

    /**
	 * Only this class can construct instances.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 */
    private MdfContainment(int value, String name, String literal) {
		super(value, name, literal);
	}

} //MdfContainment
