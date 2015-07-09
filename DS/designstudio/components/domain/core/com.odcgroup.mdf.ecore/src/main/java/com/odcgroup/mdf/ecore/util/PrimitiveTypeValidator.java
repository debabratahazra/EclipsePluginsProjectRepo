package com.odcgroup.mdf.ecore.util;

import java.text.ParseException;

import com.odcgroup.mdf.metamodel.MdfPrimitive;


/**
 * A class that enables easy validation of primitive values.
 *
 * @author <a href="mailto:cvedovini@odyssey-group.com">Claude Vedovini</a>
 * @version 1.0
 */
public class PrimitiveTypeValidator {

    /**
     * Tells whether a given string is valid value for a given primitive type. A
     * <code>null</code> value will never be valid.
     *
     * @param baseType The primitive type.
     * @param value The value to test.
     *
     * @return <code>true</code> if the value is compatible,
     *         <code>false</code> otherwise.
     */
    public static boolean isValidValue(MdfPrimitive type, String value) {
        Object parsed = null;

        try {
            parsed = PrimitiveValueParser.parse(type, value);
        } catch (ParseException e) {
            // If it cannot be parsed it is obviously not valid
            return false;
        }

        return (parsed != null);
    }
}
