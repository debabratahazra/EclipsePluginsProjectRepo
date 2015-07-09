package com.odcgroup.aaa.ui.internal.model;

/**
 * @author pkk
 *
 */
public class FormatSelectionInfo {
	
	private String code;
	private String function;
	private String type;
	private int sortColumn;
	private int sortDirection;
	
	
	/**
	 * @return
	 */
	public String getCode() {
		return code;
	}
	
	/**
	 * @param code
	 */
	public void setCode(String code) {
		this.code = code;
	}
	
	/**
	 * @return
	 */
	public String getFunction() {
		return function;
	}
	
	/**
	 * @param function
	 */
	public void setFunction(String function) {
		this.function = function;
	}
	
	/**
	 * @return
	 */
	public String getType() {
		return type;
	}
	
	/**
	 * @param denom
	 */
	public void setType(String type) {
		this.type = type;
	}
	
	/**
	 * @return
	 */
	public int getSortColumn() {
		return sortColumn;
	}
	
	/**
	 * @param sortColumn
	 */
	public void setSortColumn(int sortColumn) {
		this.sortColumn = sortColumn;
	}
	
	/**
	 * @return
	 */
	public int getSortDirection() {
		return sortDirection;
	}
	
	/**
	 * @param sortDirection
	 */
	public void setSortDirection(int sortDirection) {
		this.sortDirection = sortDirection;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return this.code.hashCode()  
			 + this.function.hashCode()
		     + this.type.hashCode();
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object other) {
		if (other == this) {
			return true;
		}
		if (other instanceof FormatSelectionInfo) {
			FormatSelectionInfo info = (FormatSelectionInfo)other;
			return this.code.equals(info.code) 
				&& this.function.equals(info.function)
				&& this.type.equals(info.type)
				&& this.sortColumn == info.sortColumn
				&& this.sortDirection == info.sortDirection;
		}
		return false;
	}
	
	

}
