package com.odcgroup.aaa.connector.domainmodel.subs.label;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;

import com.odcgroup.aaa.connector.domainmodel.DictEntity;
import com.odcgroup.aaa.connector.domainmodel.DictLabelEntity;

/**
 * DictLabel of a DictEntiy.
 * 
 * @author Michael Vorburger
 */
@Entity(name = "DictEntityLabel")
@DiscriminatorValue(value="1101")
public class DictEntityLabelEntity extends DictLabelEntity {

    @javax.persistence.ManyToOne(cascade = {CascadeType.PERSIST,
            CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.LAZY, optional = false)
    @javax.persistence.JoinColumn(name = "object_dict_id", nullable = false)
    private DictEntity forEntity;

    public DictEntity getForEntity() {
        return forEntity;
    }
}
