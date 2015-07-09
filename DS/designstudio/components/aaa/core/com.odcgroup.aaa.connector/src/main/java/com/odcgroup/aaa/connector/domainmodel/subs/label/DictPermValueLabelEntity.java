package com.odcgroup.aaa.connector.domainmodel.subs.label;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;

import com.odcgroup.aaa.connector.domainmodel.DictLabelEntity;
import com.odcgroup.aaa.connector.domainmodel.DictPermValue;

/**
 * DictLabel of a DictPermValue.
 * 
 * @author Michael Vorburger
 */
@Entity(name = "DictPermValueLabel")
@DiscriminatorValue(value="1105")
public class DictPermValueLabelEntity extends DictLabelEntity {

	//-------------------------------------------------------------------------
	// Fields

    /*
     * The DictPermValue (<em>object_dict_id</em>) property.
     */
    @javax.persistence.ManyToOne(cascade = {CascadeType.PERSIST,
            CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.LAZY, optional = false)
    @javax.persistence.JoinColumn(name = "object_dict_id", nullable = false)
    private DictPermValue forPermValue;

    /**
     * @return the languageDict
     */
    public DictPermValue getForPermValue() {
        return forPermValue;
    }

}
