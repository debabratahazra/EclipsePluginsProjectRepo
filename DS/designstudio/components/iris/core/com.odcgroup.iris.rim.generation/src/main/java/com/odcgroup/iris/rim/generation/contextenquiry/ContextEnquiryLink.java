package com.odcgroup.iris.rim.generation.contextenquiry;

import java.io.IOException;
import java.io.PrintWriter;

import com.odcgroup.t24.enquiry.util.EMUtils;

/**
 * TODO: Document me!
 *
 * @author taubert
 *
 */
public class ContextEnquiryLink {

	private String FullyQualifiedLinkName = null;
	private String Description = null;
	private String Application = null;
	private  boolean bIsVersion = false;
	private String rimName = null;
	private String resourceName = null;




	private String packageName = null;
	
	public ContextEnquiryLink(){}
	
	
	public ContextEnquiryLink(String FullyQualifiedLinkName, String Description, String Application, boolean bIsVersion){
		setFullyQualifiedLinkName(FullyQualifiedLinkName);
		setDescription(Description);
		setApplication(Application);
		this.bIsVersion = bIsVersion;
		resolveRimAndPackage();
	}
	
	/**
	 * @return the fullyQualifiedLinkName
	 */
	public String getFullyQualifiedLinkName() {
		return FullyQualifiedLinkName;
	}
	/**
	 * @param fullyQualifiedLinkName the fullyQualifiedLinkName to set
	 */
	public void setFullyQualifiedLinkName(String fullyQualifiedLinkName) {
		FullyQualifiedLinkName = removeQuotes(fullyQualifiedLinkName);
		resolveRimAndPackage();
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return Description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		Description = removeQuotes(description);
	}
	/**
	 * @return the application
	 */
	public String getApplication() {
		return Application;
	}
	/**
	 * @param application the application to set
	 */
	public void setApplication(String application) {
		Application = removeQuotes(application);
	}

	/**
	 * @return the bIsVersion
	 */
	public boolean isVersion() {
		return bIsVersion;
	}
	
	/**
	 * @param bIsVersion the bIsVersion to set
	 */
	public void setIsVersion(boolean bIsVersion) {
		this.bIsVersion = bIsVersion;
	}

	
	/**
	 * @return the rimName
	 */
	public String getRimName() {
		return rimName;
	}

	/**
	 * @return the resourceName
	 */
	public String getResourceName() {
		return resourceName;
	}

	/**
	 * @return the packageName
	 */
	public String getPackageName() {
		return packageName;
	}

	
	/**
	 * Extract package and rimname from fullyqualifiedName
	 */
	private void resolveRimAndPackage(){
		if (this.FullyQualifiedLinkName != null){
			String[] aName = this.FullyQualifiedLinkName.split("\\.");
			if (aName.length >= 2){
				this.packageName = aName[0];
				this.rimName = aName[1];
				this.resourceName = aName[2];
			}
		}
	}
	
	private String removeQuotes(String s){
		String ret = s.replace("\"", "");
		ret = ret.replace("'", "");
		return ret;
	}
	
	
	protected void writeToStream(PrintWriter writer) throws IOException {
		writer.println("         GET +-> \"" + this.FullyQualifiedLinkName + "\" {");
		writer.println("            title: \"" + this.Description + "\"");

		if (this.bIsVersion) {
			/*
			 * That would be nice to do use the fullyQualifiedName ... :-s
			 */
			writer.println("            condition: OK(" + this.rimName + "_VersionMatch)");
		} else {
			writer.println("            condition: OK(ApplicationJoinsTo" + EMUtils.camelCaseName(this.Application) + ")");
		}

		writer.println("         }");		
	}
	
}
