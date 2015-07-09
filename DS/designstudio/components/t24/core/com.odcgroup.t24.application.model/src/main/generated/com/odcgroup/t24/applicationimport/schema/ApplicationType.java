
package com.odcgroup.t24.applicationimport.schema;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ApplicationType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ApplicationType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="H"/>
 *     &lt;enumeration value="U"/>
 *     &lt;enumeration value="L"/>
 *     &lt;enumeration value="D"/>
 *     &lt;enumeration value="W"/>
 *     &lt;enumeration value="T"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "ApplicationType")
@XmlEnum
public enum ApplicationType {

    H,
    U,
    L,
    D,
    W,
    T;

    public String value() {
        return name();
    }

    public static ApplicationType fromValue(String v) {
        return valueOf(v);
    }

}
