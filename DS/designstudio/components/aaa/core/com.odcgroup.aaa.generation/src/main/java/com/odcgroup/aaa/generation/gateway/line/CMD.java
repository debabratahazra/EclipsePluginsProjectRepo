package com.odcgroup.aaa.generation.gateway.line;


public class CMD {
	
	private CMDType type;
	private String entityName;
	private String objectEntityName;
	private String comment;

	private ATT att;
	
	public CMD(CMDType type, String entityName) {
		this.type = type;
		this.entityName = entityName;
	}

	public CMD(CMDType type, String entityName, String objectEntityName) {
		this.type = type;
		this.entityName = entityName;
		this.objectEntityName = objectEntityName;
	}

	/**
	 * @return the type
	 */
	public CMDType getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(CMDType type) {
		this.type = type;
	}
	
	public String getEntityName() {
		return entityName;
	}
	
	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}
	
	public ATT getATT() {
		return att;
	}
	
	public void setATT(ATT att) {
		this.att = att;
	}

	public String getObjectEntityName() {
		return objectEntityName;
	}

	public void setObjectEntityName(String objectEntityName) {
		this.objectEntityName = objectEntityName;
	}

	public String toString() {
		return "CMD " + type + " " + entityName + (objectEntityName!=null?" " + objectEntityName:"");
	}
	
	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

}
