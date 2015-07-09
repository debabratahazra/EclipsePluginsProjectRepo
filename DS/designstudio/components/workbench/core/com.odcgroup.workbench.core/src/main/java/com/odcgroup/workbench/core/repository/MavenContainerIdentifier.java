package com.odcgroup.workbench.core.repository;

import com.odcgroup.workbench.core.IContainerIdentifier;

/**
 * This is an implementation of a container identifier, which is based on Maven
 * coordinates groupId, artifactId and version.
 *
 * @author Kai Kreuzer
 *
 */
public class MavenContainerIdentifier implements IContainerIdentifier {

	private String groupId;
	private String artifactId;
	private String version;
	
	/**
	 * @param groupId
	 * @param artifactId
	 * @param version
	 */
	public MavenContainerIdentifier(String groupId, String artifactId, String version) {
		this.groupId = groupId;
		this.artifactId = artifactId;
		this.version = version;
	}
	
	public String getGroupId() {
		return this.groupId;
	}
	
	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.core.IContainerIdentifier#getName()
	 */
	public String getName() {
		return this.artifactId;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "(groupId=" + getGroupId() + ", artifactId=" + getName() + ", version=" + getVersion() +  ")";
	}
	
	public static MavenContainerIdentifier fromString(String serializedString) {
		if(serializedString==null) return null;
		
		String groupId = null;
		String artifactId = null;
		String version = null;
		
		for(String part : serializedString.split(",")) {
			if(part.startsWith("(groupId=")) groupId = part.substring("(groupId=".length());
			if(part.startsWith(" artifactId=")) artifactId = part.substring(" artifactId=".length());
			if(part.startsWith(" version=")) version = part.substring(" version=".length(), part.length()-1);
		}
		if(groupId!=null && artifactId!=null && version!=null) {
			return new MavenContainerIdentifier(groupId, artifactId, version);
		}
		return null;
	}

	@Override
	public String getVersion() {
		return version;
	}

}
