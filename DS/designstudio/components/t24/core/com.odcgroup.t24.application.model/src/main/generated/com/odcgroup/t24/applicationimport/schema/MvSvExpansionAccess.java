
package com.odcgroup.t24.applicationimport.schema;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for MvSvExpansionAccess.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="MvSvExpansionAccess">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="NODELETE"/>
 *     &lt;enumeration value="NOEXPAND"/>
 *     &lt;enumeration value="NOMODIFY"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "MvSvExpansionAccess")
@XmlEnum
public enum MvSvExpansionAccess {

    NODELETE,
    NOEXPAND,
    NOMODIFY;

    public String value() {
        return name();
    }

    public static MvSvExpansionAccess fromValue(String v) {
        return valueOf(v);
    }

}
