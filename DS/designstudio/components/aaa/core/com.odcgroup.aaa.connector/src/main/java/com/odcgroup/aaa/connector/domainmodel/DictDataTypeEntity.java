package com.odcgroup.aaa.connector.domainmodel;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * A T'A DictDataType as JPA Entity.
 * 
 * @author Camille Weber
 */
@MappedSuperclass
public class DictDataTypeEntity {

	@Id
	@Column(insertable=false, updatable = false, nullable=false)
	private long dict_id;
	
	@Column(insertable=false, updatable = false, nullable=false)
	private String name;
		
	@Column(insertable=false, updatable = false, nullable=false)
	private String sqlname_c;
	
	@Column(insertable=false, updatable = false, nullable=false)
	private String equiv_type_c;
	
	
	public String getName() {
		return name;
	}

	public DictDataTypeEntity setName(String name) {
		this.name = name;
		return this;
	}

	public long getDictID() {
		return dict_id;
	}	
	
	public String getSqlname() {
		return sqlname_c;
	}

	public DictDataTypeEntity setSqlname(String sqlname_c) {
		this.sqlname_c = sqlname_c;
		return this;
	}

	public String getEquivType() {
		return equiv_type_c;
	}

	public DictDataTypeEntity setEquivType(String equiv_type_c) {
		this.equiv_type_c = equiv_type_c;
		return this;
	}
	
   public String toString()
    {
        // @see http://commons.apache.org/lang/api/org/apache/commons/lang/builder/ToStringBuilder.html
        return ToStringBuilder.reflectionToString(this);
    }
}
