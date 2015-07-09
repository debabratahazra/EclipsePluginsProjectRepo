package com.odcgroup.mdf.metamodel;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.AbstractEnumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Multiplicity</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see com.odcgroup.mdf.ecore.MdfPackage#getMdfMultiplicity()
 * @model instanceClass="com.odcgroup.mdf.metamodel.MdfMultiplicity"
 */
public final class MdfMultiplicity extends AbstractEnumerator {
    /**
	 * The '<em><b>ONE</b></em>' literal value.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>ONE</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * @see #ONE_LITERAL
	 * @model literal="one"
	 * @ordered
	 */
    public static final int ONE = 0;

    /**
	 * The '<em><b>MANY</b></em>' literal value.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>MANY</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * @see #MANY_LITERAL
	 * @model literal="many"
	 * @ordered
	 */
    public static final int MANY = 1;

    /**
	 * The '<em><b>ONE</b></em>' literal object.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #ONE
	 * @ordered
	 */
    public static final MdfMultiplicity ONE_LITERAL = new MdfMultiplicity(ONE, "ONE", "one");

    /**
	 * The '<em><b>MANY</b></em>' literal object.
	 * @see #MANY
	 * @ordered
	 */
    public static final MdfMultiplicity MANY_LITERAL = new MdfMultiplicity(MANY, "MANY", "many");

    /**
	 * An array of all the '<em><b>Multiplicity</b></em>' enumerators.
	 */
    private static final MdfMultiplicity[] VALUES_ARRAY =
        new MdfMultiplicity[] {
			ONE_LITERAL,
			MANY_LITERAL,
		};

    /**
	 * A public read-only list of all the '<em><b>Multiplicity</b></em>' enumerators.
	 */
    public static final List VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

    /**
	 * Returns the '<em><b>Multiplicity</b></em>' literal with the specified literal value.
	 */
    public static MdfMultiplicity get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			MdfMultiplicity result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

    /**
	 * Returns the '<em><b>Multiplicity</b></em>' literal with the specified name.
	 */
    public static MdfMultiplicity getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			MdfMultiplicity result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

    /**
	 * Returns the '<em><b>Multiplicity</b></em>' literal with the specified integer value.
	 */
    public static MdfMultiplicity get(int value) {
		switch (value) {
			case ONE: return ONE_LITERAL;
			case MANY: return MANY_LITERAL;
		}
		return null;
	}

    /**
	 * Only this class can construct instances.
	 */
    private MdfMultiplicity(int value, String name, String literal) {
		super(value, name, literal);
	}

} //MdfMultiplicity
