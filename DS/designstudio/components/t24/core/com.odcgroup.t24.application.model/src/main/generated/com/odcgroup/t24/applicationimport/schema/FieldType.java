
package com.odcgroup.t24.applicationimport.schema;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for FieldType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="FieldType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="S"/>
 *     &lt;enumeration value="B"/>
 *     &lt;enumeration value="N"/>
 *     &lt;enumeration value="D"/>
 *     &lt;enumeration value="L"/>
 *     &lt;enumeration value="C"/>
 *     &lt;enumeration value="T"/>
 *     &lt;enumeration value="M"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "FieldType")
@XmlEnum
public enum FieldType {

    S,
    B,

    /**
     * AKA Float
     * 
     */
    N,
    D,
    L,
    C,
    T,
    M;

    public String value() {
        return name();
    }

    public static FieldType fromValue(String v) {
        return valueOf(v);
    }

}
