package com.odcgroup.workbench.core.internal.repository.maven;

import com.odcgroup.workbench.core.IContainerIdentifier;

import de.visualrules.artifact.model.util.Identifier;

/**
 * This is an implementation of a container identifier, which is specific to the VisualRules Maven API.
 * The main properties are typically Maven: groupId, artifactId and version.
 *
 * @author Kai Kreuzer
 *
 */
public class VRMavenContainerIdentifier extends Identifier implements IContainerIdentifier {

	private static final long serialVersionUID = 5972405200866952689L;

	/**
	 * @param groupId
	 * @param artifactId
	 * @param version
	 */
	public VRMavenContainerIdentifier(String groupId, String artifactId, String version) {
		super(groupId, artifactId, version);
	}
	
	public String getGroupId() {
		return super.getGroupId();
	}
	
	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.core.IContainerIdentifier#getName()
	 */
	public String getName() {
		return getArtifactId();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "(groupId=" + getGroupId() + ", artifactId=" + getArtifactId() + ", version=" + getVersion()	+  ")";
	}
	
	public static VRMavenContainerIdentifier fromString(String serializedString) {
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
			return new VRMavenContainerIdentifier(groupId, artifactId, version);
		}
		return null;
	}

}
