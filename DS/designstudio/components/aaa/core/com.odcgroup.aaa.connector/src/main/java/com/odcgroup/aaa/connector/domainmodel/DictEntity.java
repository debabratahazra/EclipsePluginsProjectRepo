package com.odcgroup.aaa.connector.domainmodel;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * DictEntity with helper attributes & methods.
 *
 * What is here is beyond JPA data-access only (which could be generated), this could never be generated.
 *
 * @author Michael Vorburger
 */
@Entity
@Table(name = "dict_entity_vw")
public class DictEntity extends DictEntityEntity {

    public DictEntity() {
        super();
    }

    // For Test Cases only (for now)
    public DictEntity(long id) {
        super(id);
    }
}