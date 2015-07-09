
package com.odcgroup.t24.applicationimport.schema;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ApplicationLevel.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ApplicationLevel">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="I"/>
 *     &lt;enumeration value="F"/>
 *     &lt;enumeration value="C"/>
 *     &lt;enumeration value="B"/>
 *     &lt;enumeration value="Y"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "ApplicationLevel")
@XmlEnum
public enum ApplicationLevel {

    I,
    F,
    C,
    B,
    Y;

    public String value() {
        return name();
    }

    public static ApplicationLevel fromValue(String v) {
        return valueOf(v);
    }

}
