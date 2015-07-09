package com.odcgroup.aaa.connector.domainmodel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * A T'A DictFunction as JPA Entity.
 *
 * @author yan
 */
@Entity(name = "DictFunction")
@Table(name = "dict_function_vw")
public class DictFunctionEntity {

	@Id
	@Column(name = "dict_id")
	private long id;

	@Column(name = "name")
	private String name;
	
	@Column(name = "proc_name")
	private String procName;

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the procName
	 */
	public String getProcName() {
		return procName;
	}

	/**
	 * @param procName the procName to set
	 */
	public void setProcName(String procName) {
		this.procName = procName;
	}
}
