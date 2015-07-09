
package com.odcgroup.t24.applicationimport.schema;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ApplicationEntity complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ApplicationEntity">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="additional-info" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="allowed-functions" type="{http://www.temenos.com/t24-meta}ApplicationFunction" minOccurs="0"/>
 *         &lt;element name="documentation" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="fields" type="{http://www.temenos.com/t24-meta}ApplicationField" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="header1" type="{http://www.temenos.com/t24-meta}Translation" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="header2" type="{http://www.temenos.com/t24-meta}Translation" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="level" type="{http://www.temenos.com/t24-meta}ApplicationLevel"/>
 *         &lt;element name="long-description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="non-stop" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="short-description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="type" type="{http://www.temenos.com/t24-meta}ApplicationType" minOccurs="0"/>
 *         &lt;element name="version-control" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="name-t24" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ApplicationEntity", propOrder = {
    "additionalInfo",
    "allowedFunctions",
    "documentation",
    "fields",
    "header1",
    "header2",
    "level",
    "longDescription",
    "nonStop",
    "shortDescription",
    "type",
    "versionControl"
})
public class ApplicationEntity {

    @XmlElement(name = "additional-info")
    protected String additionalInfo;
    @XmlElement(name = "allowed-functions")
    protected String allowedFunctions;
    protected String documentation;
    protected List<ApplicationField> fields;
    protected List<Translation> header1;
    protected List<Translation> header2;
    @XmlElement(required = true)
    protected ApplicationLevel level;
    @XmlElement(name = "long-description")
    protected String longDescription;
    @XmlElement(name = "non-stop")
    protected boolean nonStop;
    @XmlElement(name = "short-description")
    protected String shortDescription;
    protected ApplicationType type;
    @XmlElement(name = "version-control")
    protected String versionControl;
    @XmlAttribute(name = "name", required = true)
    protected String name;
    @XmlAttribute(name = "name-t24")
    protected String nameT24;

    /**
     * Gets the value of the additionalInfo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAdditionalInfo() {
        return additionalInfo;
    }

    /**
     * Sets the value of the additionalInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAdditionalInfo(String value) {
        this.additionalInfo = value;
    }

    /**
     * Gets the value of the allowedFunctions property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAllowedFunctions() {
        return allowedFunctions;
    }

    /**
     * Sets the value of the allowedFunctions property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAllowedFunctions(String value) {
        this.allowedFunctions = value;
    }

    /**
     * Gets the value of the documentation property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDocumentation() {
        return documentation;
    }

    /**
     * Sets the value of the documentation property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDocumentation(String value) {
        this.documentation = value;
    }

    /**
     * Gets the value of the fields property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the fields property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFields().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ApplicationField }
     * 
     * 
     */
    public List<ApplicationField> getFields() {
        if (fields == null) {
            fields = new ArrayList<ApplicationField>();
        }
        return this.fields;
    }

    /**
     * Gets the value of the header1 property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the header1 property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getHeader1().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Translation }
     * 
     * 
     */
    public List<Translation> getHeader1() {
        if (header1 == null) {
            header1 = new ArrayList<Translation>();
        }
        return this.header1;
    }

    /**
     * Gets the value of the header2 property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the header2 property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getHeader2().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Translation }
     * 
     * 
     */
    public List<Translation> getHeader2() {
        if (header2 == null) {
            header2 = new ArrayList<Translation>();
        }
        return this.header2;
    }

    /**
     * Gets the value of the level property.
     * 
     * @return
     *     possible object is
     *     {@link ApplicationLevel }
     *     
     */
    public ApplicationLevel getLevel() {
        return level;
    }

    /**
     * Sets the value of the level property.
     * 
     * @param value
     *     allowed object is
     *     {@link ApplicationLevel }
     *     
     */
    public void setLevel(ApplicationLevel value) {
        this.level = value;
    }

    /**
     * Gets the value of the longDescription property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLongDescription() {
        return longDescription;
    }

    /**
     * Sets the value of the longDescription property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLongDescription(String value) {
        this.longDescription = value;
    }

    /**
     * Gets the value of the nonStop property.
     * 
     */
    public boolean isNonStop() {
        return nonStop;
    }

    /**
     * Sets the value of the nonStop property.
     * 
     */
    public void setNonStop(boolean value) {
        this.nonStop = value;
    }

    /**
     * Gets the value of the shortDescription property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getShortDescription() {
        return shortDescription;
    }

    /**
     * Sets the value of the shortDescription property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setShortDescription(String value) {
        this.shortDescription = value;
    }

    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link ApplicationType }
     *     
     */
    public ApplicationType getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link ApplicationType }
     *     
     */
    public void setType(ApplicationType value) {
        this.type = value;
    }

    /**
     * Gets the value of the versionControl property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVersionControl() {
        return versionControl;
    }

    /**
     * Sets the value of the versionControl property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVersionControl(String value) {
        this.versionControl = value;
    }

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the nameT24 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNameT24() {
        return nameT24;
    }

    /**
     * Sets the value of the nameT24 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNameT24(String value) {
        this.nameT24 = value;
    }

}
