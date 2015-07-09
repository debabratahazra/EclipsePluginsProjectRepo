
package com.odcgroup.t24.applicationimport.schema;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DomainServiceResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DomainServiceResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="modules" type="{http://www.temenos.com/t24-meta}Module" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="t24-release" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DomainServiceResponse", propOrder = {
    "modules",
    "t24Release"
})
public class DomainServiceResponse {

    protected List<Module> modules;
    @XmlElement(name = "t24-release", required = true)
    protected String t24Release;

    /**
     * Gets the value of the modules property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the modules property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getModules().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Module }
     * 
     * 
     */
    public List<Module> getModules() {
        if (modules == null) {
            modules = new ArrayList<Module>();
        }
        return this.modules;
    }

    /**
     * Gets the value of the t24Release property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getT24Release() {
        return t24Release;
    }

    /**
     * Sets the value of the t24Release property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setT24Release(String value) {
        this.t24Release = value;
    }

}
