package com.odcgroup.aaa.connector.domainmodel;

import javax.persistence.Basic;
import javax.persistence.Id;

import com.odcgroup.aaa.connector.internal.nls.Language;

/**
 * Language.
 * 
 * @author Michael Vorburger
 */
@javax.persistence.Entity(name = "DictLanguage")
@javax.persistence.Table(name = "dict_language_vw")
public class DictLanguageEntity /*extends TranslateableAdapter*/ implements Language {

	//-------------------------------------------------------------------------
	// Fields

    /*
     * The <em>dict_id</em> property.
     * <p>Identifier</p>
     */
    @Id
    @javax.persistence.Column(name = "dict_id", nullable = false)
    private long dict_id;

    /*
	 * The <em>denom</em> property.
	 * <p>Denomination</p>
	 */
	@javax.persistence.Column(name = "denom")
	private String denom;


    /*
     * The <em>dateFormatC</em> property.
     * <p>Date Format</p>
     */
    @javax.persistence.Column(name = "date_format_c")
    private String dateFormatC;

    /*
     * The <em>thousSeparatorC</em> property.
     * <p>Thousand Separator</p>
     */
    @javax.persistence.Column(name = "thous_separator_c")
    private String thousSeparatorC;

    /*
     * The <em>decimSeparatorC</em> property.
     * <p>Decimal Separator</p>
     */
    @javax.persistence.Column(name = "decim_separator_c")
    private String decimSeparatorC;

    /*
     * The <em>name</em> property.
     * <p>Name</p>
     */
    @javax.persistence.Column(name = "name")
    private String name;

    /*
     * The <em>sqlnameC</em> property.
     * <p>SQL Name</p>
     */
    @javax.persistence.Column(name = "sqlname_c")
    private String sqlnameC;

    /*
     * The <em>code</em> property.
     * <p>Code</p>
     */
    @Basic(optional = false)
    @javax.persistence.Column(name = "code", nullable = false)
    private String code;

//	/*
//	 * The <em>synonym</em> property.
//	 * <p>Synonym</p>
//	 */
//	@javax.persistence.OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY, mappedBy = "synonymTechInv")
//	private java.util.Set<com.odcgroup.tangij.domain.SynonymEntity> synonym;

//  /*
//  * The <em>denomination</em> property.
//  * <p>Denomination</p>
//  */
// @javax.persistence.OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY, mappedBy = "denominationTechInv")
// private java.util.Set<com.odcgroup.tangij.domain.DenominationEntity> denomination;

    
	//-------------------------------------------------------------------------
	// Accessors

    /**
     * Returns the <em>dict</em> property.
     * Identifier
     * @return the <em>dict</em> property.
     * @primaryKey
     */
    public Long getDictId() {
        return this.dict_id;
    }

	/**
	 * Returns the <em>denom</em> property.
	 * Denomination
	 * @return the <em>denom</em> property.
	 */
	public String getDenom() {
		return this.denom;
	}

	/**
	 * Sets the <em>denom</em> property.
	 * Denomination
	 * @param denom the new value of the <em>denom</em> property.
	 */
	public void setDenom(String denom) {
		this.denom = denom;
	}

	/**
	 * Returns the <em>dateFormatC</em> property.
	 * Date Format
	 * @return the <em>dateFormatC</em> property.
	 */
	public String getDateFormatC() {
		return this.dateFormatC;
	}

	/**
	 * Sets the <em>dateFormatC</em> property.
	 * Date Format
	 * @param dateFormatC the new value of the <em>dateFormatC</em> property.
	 */
	public void setDateFormatC(String dateFormatC) {
		this.dateFormatC = dateFormatC;
	}

	/**
	 * Returns the <em>thousSeparatorC</em> property.
	 * Thousand Separator
	 * @return the <em>thousSeparatorC</em> property.
	 */
	public String getThousSeparatorC() {
		return this.thousSeparatorC;
	}

	/**
	 * Sets the <em>thousSeparatorC</em> property.
	 * Thousand Separator
	 * @param thousSeparatorC the new value of the <em>thousSeparatorC</em> property.
	 */
	public void setThousSeparatorC(String thousSeparatorC) {
		this.thousSeparatorC = thousSeparatorC;
	}

	/**
	 * Returns the <em>decimSeparatorC</em> property.
	 * Decimal Separator
	 * @return the <em>decimSeparatorC</em> property.
	 */
	public String getDecimSeparatorC() {
		return this.decimSeparatorC;
	}

	/**
	 * Sets the <em>decimSeparatorC</em> property.
	 * Decimal Separator
	 * @param decimSeparatorC the new value of the <em>decimSeparatorC</em> property.
	 */
	public void setDecimSeparatorC(String decimSeparatorC) {
		this.decimSeparatorC = decimSeparatorC;
	}

	/**
	 * Returns the <em>name</em> property.
	 * Name
	 * @return the <em>name</em> property.
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Sets the <em>name</em> property.
	 * Name
	 * @param name the new value of the <em>name</em> property.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Returns the <em>sqlnameC</em> property.
	 * SQL Name
	 * @return the <em>sqlnameC</em> property.
	 */
	public String getSqlnameC() {
		return this.sqlnameC;
	}

	/**
	 * Sets the <em>sqlnameC</em> property.
	 * SQL Name
	 * @param sqlnameC the new value of the <em>sqlnameC</em> property.
	 */
	public void setSqlnameC(String sqlnameC) {
		this.sqlnameC = sqlnameC;
	}

	/**
	 * Returns the <em>code</em> property.
	 * Code
	 * @return the <em>code</em> property.
	 */
	public String getCode() {
		return this.code;
	}

	/**
	 * Sets the <em>code</em> property.
	 * Code
	 * @param code the new value of the <em>code</em> property.
	 */
	public void setCode(String code) {
		this.code = code;
	}

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return code;
    }

// /**
//  * Returns the <em>synonym</em> property.
//  * Synonym
//  * @return the <em>synonym</em> property.
//  */
// public java.util.Set<com.odcgroup.tangij.domain.SynonymEntity> getSynonym() {
//     return this.synonym;
// }

// /**
// * Returns the <em>scriptControl</em> property.
// * Control
// * @return the <em>scriptControl</em> property.
// */
//public java.util.Set<com.odcgroup.tangij.domain.ScriptDefinitionEntity> getScriptControl() {
//    return this.scriptControl;
//}
   
// /**
// * Returns the <em>denomination</em> property.
// * Denomination
// * @return the <em>denomination</em> property.
// */
//public java.util.Set<com.odcgroup.tangij.domain.DenominationEntity> getDenomination() {
//    return this.denomination;
//}
}
