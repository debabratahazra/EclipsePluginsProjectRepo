package com.odcgroup.ds.t24.packager.data;

import java.util.Date;
import java.util.List;

public class DataHeader {

	private String gbDescription = "";
	private List<Cd> cds;
	private String savedVersion = "";
	private String savedFrom = "";
	private String savedRelease = "";
	private Date savedDate;
	private String workset = "";
	private String product = "";
	private String useDimensions = "";
	private String currNo = "";
	private String inputtingUser = "";
	private String authorizingUser = "";
	private Date creationModificationDate;
	private String branch = "";
	private String userDepartmentCode = "";
	
	public DataHeader(String inputtingUser, String authorizingUser, String branch) {
		Date now = new Date();
		this.inputtingUser = inputtingUser;
		this.authorizingUser = authorizingUser;
		this.branch = branch;
		this.savedDate = now;
		this.creationModificationDate = now;
	}
	
	
	public String getGbDescription() {
		return gbDescription;
	}
	public void setGbDescription(String gbDescription) {
		this.gbDescription = gbDescription;
	}
	public List<Cd> getCds() {
		return cds;
	}
	public void setCds(List<Cd> cds) {
		this.cds = cds;
	}
	public String getSavedVersion() {
		return savedVersion;
	}
	public void setSavedVersion(String savedVersion) {
		this.savedVersion = savedVersion;
	}
	public String getSavedFrom() {
		return savedFrom;
	}
	public void setSavedFrom(String savedFrom) {
		this.savedFrom = savedFrom;
	}
	public String getSavedRelease() {
		return savedRelease;
	}
	public void setSavedRelease(String savedRelease) {
		this.savedRelease = savedRelease;
	}
	public Date getSavedDate() {
		return savedDate;
	}
	public void setSavedDate(Date savedDate) {
		this.savedDate = savedDate;
	}
	public String getWorkset() {
		return workset;
	}
	public void setWorkset(String workset) {
		this.workset = workset;
	}
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	public String getUseDimensions() {
		return useDimensions;
	}
	public void setUseDimensions(String useDimensions) {
		this.useDimensions = useDimensions;
	}
	public String getCurrNo() {
		return currNo;
	}
	public void setCurrNo(String currNo) {
		this.currNo = currNo;
	}
	public String getInputtingUser() {
		return inputtingUser;
	}
	public void setInputtingUser(String user) {
		this.inputtingUser = user;
	}
	public String getAuthorizingUser() {
		return authorizingUser;
	}


	public void setAuthorizingUser(String authorizedUser) {
		this.authorizingUser = authorizedUser;
	}


	public Date getCreationModificationDate() {
		return creationModificationDate;
	}
	public void setCreationModificationDate(Date creationModificationDate) {
		this.creationModificationDate = creationModificationDate;
	}
	public String getBranch() {
		return branch;
	}
	public void setBranch(String branch) {
		this.branch = branch;
	}
	public String getUserDepartmentCode() {
		return userDepartmentCode;
	}
	public void setUserDepartmentCode(String line49) {
		this.userDepartmentCode = line49;
	}
}
