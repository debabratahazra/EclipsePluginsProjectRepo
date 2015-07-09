
package com.odcgroup.t24.applicationimport.schema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DomainServiceRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DomainServiceRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="application-name-filter" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="component-name-filter" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="module-name-filter" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DomainServiceRequest", propOrder = {
    "applicationNameFilter",
    "componentNameFilter",
    "moduleNameFilter"
})
public class DomainServiceRequest {

    @XmlElement(name = "application-name-filter")
    protected String applicationNameFilter;
    @XmlElement(name = "component-name-filter")
    protected String componentNameFilter;
    @XmlElement(name = "module-name-filter")
    protected String moduleNameFilter;

    /**
     * Gets the value of the applicationNameFilter property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getApplicationNameFilter() {
        return applicationNameFilter;
    }

    /**
     * Sets the value of the applicationNameFilter property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setApplicationNameFilter(String value) {
        this.applicationNameFilter = value;
    }

    /**
     * Gets the value of the componentNameFilter property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getComponentNameFilter() {
        return componentNameFilter;
    }

    /**
     * Sets the value of the componentNameFilter property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setComponentNameFilter(String value) {
        this.componentNameFilter = value;
    }

    /**
     * Gets the value of the moduleNameFilter property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getModuleNameFilter() {
        return moduleNameFilter;
    }

    /**
     * Sets the value of the moduleNameFilter property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setModuleNameFilter(String value) {
        this.moduleNameFilter = value;
    }

}
