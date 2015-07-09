
package com.odcgroup.t24.applicationimport.schema;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for InputBehaviour.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="InputBehaviour">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="I"/>
 *     &lt;enumeration value="N"/>
 *     &lt;enumeration value="F"/>
 *     &lt;enumeration value="E"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "InputBehaviour")
@XmlEnum
public enum InputBehaviour {

    I,
    N,
    F,
    E;

    public String value() {
        return name();
    }

    public static InputBehaviour fromValue(String v) {
        return valueOf(v);
    }

}
