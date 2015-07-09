package com.odcgroup.aaa.connector.domainmodel;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * DictDataType with helper attributes & methods.
 * 
 * What is here is beyond JPA data-access only (which could be generated), this could never be generated.
 * 
 * @author Michael Vorburger
 */
@Entity
@Table(name = "dict_datatype_vw")
public class DictDataType extends DictDataTypeEntity {

    public boolean isSkipped() {
    	String sqlName = getSqlname();
    	return sqlName.equals("binary_t") 
    		/* DS-1648 remove the restriction on timestamp 
               || sqlName.equals("timestamp")
            */;
    }
}