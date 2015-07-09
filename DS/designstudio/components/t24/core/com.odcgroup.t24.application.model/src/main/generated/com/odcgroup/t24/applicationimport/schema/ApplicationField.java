
package com.odcgroup.t24.applicationimport.schema;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ApplicationField complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ApplicationField">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="alignment" type="{http://www.temenos.com/t24-meta}FieldAlignment" minOccurs="0"/>
 *         &lt;element name="business-type" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="concat-file" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="core" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="gen-operation" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="documentation" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="input-behaviour" type="{http://www.temenos.com/t24-meta}InputBehaviour"/>
 *         &lt;element name="is-image" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="is-textarea" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="onchange-behaviour" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="label" type="{http://www.temenos.com/t24-meta}Translation" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="mandatory" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="mask" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="max-length" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *         &lt;element name="multi-language" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="multi-value-group-name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="mv-sv-expansion-access" type="{http://www.temenos.com/t24-meta}MvSvExpansionAccess" minOccurs="0"/>
 *         &lt;element name="name-t24" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="primary-key" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="range" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ref-application" type="{http://www.temenos.com/t24-meta}ApplicationReference" minOccurs="0"/>
 *         &lt;element name="sub-value-group-name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="sys-number" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="tooltip" type="{http://www.temenos.com/t24-meta}Translation" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="type" type="{http://www.temenos.com/t24-meta}FieldType" minOccurs="0"/>
 *         &lt;element name="type-modifiers" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="valid-value" type="{http://www.temenos.com/t24-meta}FieldValidValue" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ApplicationField", propOrder = {
    "alignment",
    "businessType",
    "concatFile",
    "core",
    "genOperation",
    "documentation",
    "inputBehaviour",
    "isImage",
    "isTextarea",
    "onchangeBehaviour",
    "label",
    "mandatory",
    "mask",
    "maxLength",
    "multiLanguage",
    "multiValueGroupName",
    "mvSvExpansionAccess",
    "nameT24",
    "primaryKey",
    "range",
    "refApplication",
    "subValueGroupName",
    "sysNumber",
    "tooltip",
    "type",
    "typeModifiers",
    "validValue"
})
public class ApplicationField {

    protected FieldAlignment alignment;
    @XmlElement(name = "business-type")
    protected String businessType;
    @XmlElement(name = "concat-file")
    protected String concatFile;
    protected boolean core;
    @XmlElement(name = "gen-operation")
    protected String genOperation;
    protected String documentation;
    @XmlElement(name = "input-behaviour", required = true)
    protected InputBehaviour inputBehaviour;
    @XmlElement(name = "is-image")
    protected Boolean isImage;
    @XmlElement(name = "is-textarea")
    protected Boolean isTextarea;
    @XmlElement(name = "onchange-behaviour")
    protected String onchangeBehaviour;
    protected List<Translation> label;
    protected Boolean mandatory;
    protected String mask;
    @XmlElement(name = "max-length")
    protected BigInteger maxLength;
    @XmlElement(name = "multi-language")
    protected Boolean multiLanguage;
    @XmlElement(name = "multi-value-group-name")
    protected String multiValueGroupName;
    @XmlElement(name = "mv-sv-expansion-access")
    protected MvSvExpansionAccess mvSvExpansionAccess;
    @XmlElement(name = "name-t24")
    protected String nameT24;
    @XmlElement(name = "primary-key")
    protected Boolean primaryKey;
    protected String range;
    @XmlElement(name = "ref-application")
    protected ApplicationReference refApplication;
    @XmlElement(name = "sub-value-group-name")
    protected String subValueGroupName;
    @XmlElement(name = "sys-number")
    protected Double sysNumber;
    protected List<Translation> tooltip;
    protected FieldType type;
    @XmlElement(name = "type-modifiers")
    protected String typeModifiers;
    @XmlElement(name = "valid-value")
    protected List<FieldValidValue> validValue;
    @XmlAttribute(name = "name", required = true)
    protected String name;

    /**
     * Gets the value of the alignment property.
     * 
     * @return
     *     possible object is
     *     {@link FieldAlignment }
     *     
     */
    public FieldAlignment getAlignment() {
        return alignment;
    }

    /**
     * Sets the value of the alignment property.
     * 
     * @param value
     *     allowed object is
     *     {@link FieldAlignment }
     *     
     */
    public void setAlignment(FieldAlignment value) {
        this.alignment = value;
    }

    /**
     * Gets the value of the businessType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBusinessType() {
        return businessType;
    }

    /**
     * Sets the value of the businessType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBusinessType(String value) {
        this.businessType = value;
    }

    /**
     * Gets the value of the concatFile property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getConcatFile() {
        return concatFile;
    }

    /**
     * Sets the value of the concatFile property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setConcatFile(String value) {
        this.concatFile = value;
    }

    /**
     * Gets the value of the core property.
     * 
     */
    public boolean isCore() {
        return core;
    }

    /**
     * Sets the value of the core property.
     * 
     */
    public void setCore(boolean value) {
        this.core = value;
    }

    /**
     * Gets the value of the genOperation property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGenOperation() {
        return genOperation;
    }

    /**
     * Sets the value of the genOperation property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGenOperation(String value) {
        this.genOperation = value;
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
     * Gets the value of the inputBehaviour property.
     * 
     * @return
     *     possible object is
     *     {@link InputBehaviour }
     *     
     */
    public InputBehaviour getInputBehaviour() {
        return inputBehaviour;
    }

    /**
     * Sets the value of the inputBehaviour property.
     * 
     * @param value
     *     allowed object is
     *     {@link InputBehaviour }
     *     
     */
    public void setInputBehaviour(InputBehaviour value) {
        this.inputBehaviour = value;
    }

    /**
     * Gets the value of the isImage property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsImage() {
        return isImage;
    }

    /**
     * Sets the value of the isImage property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsImage(Boolean value) {
        this.isImage = value;
    }

    /**
     * Gets the value of the isTextarea property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsTextarea() {
        return isTextarea;
    }

    /**
     * Sets the value of the isTextarea property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsTextarea(Boolean value) {
        this.isTextarea = value;
    }

    /**
     * Gets the value of the onchangeBehaviour property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOnchangeBehaviour() {
        return onchangeBehaviour;
    }

    /**
     * Sets the value of the onchangeBehaviour property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOnchangeBehaviour(String value) {
        this.onchangeBehaviour = value;
    }

    /**
     * Gets the value of the label property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the label property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getLabel().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Translation }
     * 
     * 
     */
    public List<Translation> getLabel() {
        if (label == null) {
            label = new ArrayList<Translation>();
        }
        return this.label;
    }

    /**
     * Gets the value of the mandatory property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isMandatory() {
        return mandatory;
    }

    /**
     * Sets the value of the mandatory property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setMandatory(Boolean value) {
        this.mandatory = value;
    }

    /**
     * Gets the value of the mask property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMask() {
        return mask;
    }

    /**
     * Sets the value of the mask property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMask(String value) {
        this.mask = value;
    }

    /**
     * Gets the value of the maxLength property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getMaxLength() {
        return maxLength;
    }

    /**
     * Sets the value of the maxLength property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setMaxLength(BigInteger value) {
        this.maxLength = value;
    }

    /**
     * Gets the value of the multiLanguage property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isMultiLanguage() {
        return multiLanguage;
    }

    /**
     * Sets the value of the multiLanguage property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setMultiLanguage(Boolean value) {
        this.multiLanguage = value;
    }

    /**
     * Gets the value of the multiValueGroupName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMultiValueGroupName() {
        return multiValueGroupName;
    }

    /**
     * Sets the value of the multiValueGroupName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMultiValueGroupName(String value) {
        this.multiValueGroupName = value;
    }

    /**
     * Gets the value of the mvSvExpansionAccess property.
     * 
     * @return
     *     possible object is
     *     {@link MvSvExpansionAccess }
     *     
     */
    public MvSvExpansionAccess getMvSvExpansionAccess() {
        return mvSvExpansionAccess;
    }

    /**
     * Sets the value of the mvSvExpansionAccess property.
     * 
     * @param value
     *     allowed object is
     *     {@link MvSvExpansionAccess }
     *     
     */
    public void setMvSvExpansionAccess(MvSvExpansionAccess value) {
        this.mvSvExpansionAccess = value;
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

    /**
     * Gets the value of the primaryKey property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isPrimaryKey() {
        return primaryKey;
    }

    /**
     * Sets the value of the primaryKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setPrimaryKey(Boolean value) {
        this.primaryKey = value;
    }

    /**
     * Gets the value of the range property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRange() {
        return range;
    }

    /**
     * Sets the value of the range property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRange(String value) {
        this.range = value;
    }

    /**
     * Gets the value of the refApplication property.
     * 
     * @return
     *     possible object is
     *     {@link ApplicationReference }
     *     
     */
    public ApplicationReference getRefApplication() {
        return refApplication;
    }

    /**
     * Sets the value of the refApplication property.
     * 
     * @param value
     *     allowed object is
     *     {@link ApplicationReference }
     *     
     */
    public void setRefApplication(ApplicationReference value) {
        this.refApplication = value;
    }

    /**
     * Gets the value of the subValueGroupName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSubValueGroupName() {
        return subValueGroupName;
    }

    /**
     * Sets the value of the subValueGroupName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSubValueGroupName(String value) {
        this.subValueGroupName = value;
    }

    /**
     * Gets the value of the sysNumber property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getSysNumber() {
        return sysNumber;
    }

    /**
     * Sets the value of the sysNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setSysNumber(Double value) {
        this.sysNumber = value;
    }

    /**
     * Gets the value of the tooltip property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the tooltip property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTooltip().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Translation }
     * 
     * 
     */
    public List<Translation> getTooltip() {
        if (tooltip == null) {
            tooltip = new ArrayList<Translation>();
        }
        return this.tooltip;
    }

    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link FieldType }
     *     
     */
    public FieldType getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link FieldType }
     *     
     */
    public void setType(FieldType value) {
        this.type = value;
    }

    /**
     * Gets the value of the typeModifiers property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTypeModifiers() {
        return typeModifiers;
    }

    /**
     * Sets the value of the typeModifiers property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTypeModifiers(String value) {
        this.typeModifiers = value;
    }

    /**
     * Gets the value of the validValue property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the validValue property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getValidValue().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link FieldValidValue }
     * 
     * 
     */
    public List<FieldValidValue> getValidValue() {
        if (validValue == null) {
            validValue = new ArrayList<FieldValidValue>();
        }
        return this.validValue;
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

}
