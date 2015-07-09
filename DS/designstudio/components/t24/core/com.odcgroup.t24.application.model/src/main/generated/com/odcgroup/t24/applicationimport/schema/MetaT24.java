
package com.odcgroup.t24.applicationimport.schema;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice>
 *         &lt;sequence maxOccurs="unbounded" minOccurs="0">
 *           &lt;element name="domain-service-response" type="{http://www.temenos.com/t24-meta}DomainServiceResponse" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;/sequence>
 *       &lt;/choice>
 *       &lt;attribute name="mode" default="publish">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;enumeration value="publish"/>
 *             &lt;enumeration value="remove"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "domainServiceResponse"
})
@XmlRootElement(name = "meta-t24")
public class MetaT24 {

    @XmlElement(name = "domain-service-response")
    protected List<DomainServiceResponse> domainServiceResponse;
    @XmlAttribute(name = "mode")
    protected String mode;

    /**
     * Gets the value of the domainServiceResponse property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the domainServiceResponse property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDomainServiceResponse().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DomainServiceResponse }
     * 
     * 
     */
    public List<DomainServiceResponse> getDomainServiceResponse() {
        if (domainServiceResponse == null) {
            domainServiceResponse = new ArrayList<DomainServiceResponse>();
        }
        return this.domainServiceResponse;
    }

    /**
     * Gets the value of the mode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMode() {
        if (mode == null) {
            return "publish";
        } else {
            return mode;
        }
    }

    /**
     * Sets the value of the mode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMode(String value) {
        this.mode = value;
    }

}
