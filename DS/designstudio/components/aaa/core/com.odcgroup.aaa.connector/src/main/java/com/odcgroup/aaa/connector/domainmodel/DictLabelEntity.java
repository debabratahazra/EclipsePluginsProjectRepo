package com.odcgroup.aaa.connector.domainmodel;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.InheritanceType;

import com.odcgroup.aaa.connector.domainmodel.ids.DictLabelId;
import com.odcgroup.aaa.connector.internal.nls.Language;
import com.odcgroup.otf.jpa.internal.openjpa.OpenJPAPerformanceHelper;

/**
 * DictLabel.
 * 
 * @author Michael Vorburger
 */
@javax.persistence.Entity(name = "DictLabel")
@IdClass(DictLabelId.class)
@javax.persistence.Table(name = "dict_label_vw")
@javax.persistence.Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="entity_dict_id", discriminatorType=DiscriminatorType.INTEGER)
public abstract class DictLabelEntity {

    // LATER (as needed), create missing subclasses DictCriteriaLabelEntity (1102) and DictFunctionLabelEntity (1107)
    
	//-------------------------------------------------------------------------
	// Fields

	/*
	 * The Name (<em>name</em>) property.
	 */
	@Basic(optional = false)
	@javax.persistence.Column(name = "name", nullable = false)
	private String name;

	/*
	 * The Language (<em>languageDict</em>) property.
	 */
	@Id
	@javax.persistence.ManyToOne(cascade = {CascadeType.PERSIST,
			CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.LAZY, optional = false)
	@javax.persistence.JoinColumn(name = "language_dict_id", nullable = false)
	private DictLanguageEntity language;

    @Id
    @Basic(optional = false)
    @javax.persistence.Column(name = "entity_dict_id", nullable = false)
	private long entityRef;

    @Id
    @Basic(optional = false)
    @javax.persistence.Column(name = "object_dict_id", nullable = false)
    private long objectRef;
    
//    public long getObjectRef()  {
//        return objectRef;
//    }
    
	//-------------------------------------------------------------------------
	// Accessors

	/**
	 * Returns the Name property.
	 * 
	 * @return the <em>name</em> property.
	 */
	public String getName() {
		return this.name;
	}

//	/**
//	 * Sets the Name property.
//	 * @param name the new value of the <em>name</em> property.
//	 */
//	public void setName(String name) {
//		this.name = name;
//	}

	/**
	 * Returns the Language property.
	 * @return the <em>language</em> property.
	 */
	public Language/*DictLanguageEntity*/ getLanguage() {
		return this.language;
	}

	public Long getLanguageDictRef() {
		Long cachedId = (Long) OpenJPAPerformanceHelper.getAssociationRef(this, "languageDict");
		if (cachedId != null) {
			return cachedId;
		} else {
			if (language != null) {
				return language.getDictId();
			} else {
				return null;
			}
		}
	}
	
	public DictLabelId getDictLabelId() {
		DictLabelId id = new DictLabelId();
		id.entityRef = this.entityRef;
		id.language = (this.language == null ) ? null : this.language.getDictId();
		id.objectRef = this.objectRef;
		return id;
	}
}
