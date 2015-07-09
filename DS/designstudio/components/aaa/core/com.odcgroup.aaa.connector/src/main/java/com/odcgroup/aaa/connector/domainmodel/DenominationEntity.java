package com.odcgroup.aaa.connector.domainmodel;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.odcgroup.aaa.connector.domainmodel.ids.DenominationEntityId;



/**
 * Denomination.
 * 
 * @author Michael Vorburger
 */
@Entity(name = "Denomination")
@Table(name = "denomination_vw")
@IdClass(DenominationEntityId.class)
public class DenominationEntity
{
    @Id
    @Basic(optional = false)
    @Column(name = "entity_dict_id", nullable=false)  // NEVER NULL
    private long entity;

    @Id
    @Basic(optional = false)
    @Column(name = "object_id", nullable=false)       // NEVER NULL
    private long object;
    
    @Id
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "language_dict_id", nullable = false)
    private DictLanguageEntity language;

    @Basic(optional = false)
    @Column(name="denom", nullable=false)  // Never NULL
    private String denom;
    
    /**
     * @return the denom
     */
    public String getDenom() {
        return denom;
    }

    /**
     * @return the language
     */
    public DictLanguageEntity getLanguage() {
        return language;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return language.getName() + ":" + denom;
    }
    
//    /**
//     * @return the language
//     */
//    public DictLanguageEntity getLanguage() {
//        return language;
//    }

    // NOTE: No getters (and even less setters) for attribute_dict_id & object_id should normally be needed; that's internal only.
}
