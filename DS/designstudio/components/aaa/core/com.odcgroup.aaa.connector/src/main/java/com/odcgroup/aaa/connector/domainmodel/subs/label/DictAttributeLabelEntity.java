package com.odcgroup.aaa.connector.domainmodel.subs.label;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;

import com.odcgroup.aaa.connector.domainmodel.DictAttribute;
import com.odcgroup.aaa.connector.domainmodel.DictLabelEntity;

/**
 * DictLabel of a DictAttribute.
 * 
 * @author Michael Vorburger
 */
@Entity(name = "DictAttributeLabel")
@DiscriminatorValue(value="1103")
public class DictAttributeLabelEntity extends DictLabelEntity {

    @javax.persistence.ManyToOne(cascade = {CascadeType.PERSIST,
            CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.LAZY, optional = false)
    @javax.persistence.JoinColumn(name = "object_dict_id", nullable = false)
    private DictAttribute forAttribute;

    public DictAttribute getForAttribute() {
        return forAttribute;
    }
}
