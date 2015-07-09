
package com.odcgroup.t24.applicationimport.schema;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for FieldAlignment.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="FieldAlignment">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="R"/>
 *     &lt;enumeration value="L"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "FieldAlignment")
@XmlEnum
public enum FieldAlignment {

    R,
    L;

    public String value() {
        return name();
    }

    public static FieldAlignment fromValue(String v) {
        return valueOf(v);
    }

}
