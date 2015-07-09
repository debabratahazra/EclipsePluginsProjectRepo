package com.odcgroup.ocs.server.external.builder.internal.delta;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Holds the differences (modified, created or removed files or folders)
 * An instance of this class is used as an input of the server update.
 * @author yan
 */
public class AnalysedDelta {
	
	private List<String> addedFiles = new LinkedList<String>();
	private List<String> removedFiles = new LinkedList<String>();
	private List<String> changedFiles = new LinkedList<String>();

	private List<String> addedFolders = new LinkedList<String>();
	private List<String> removedFolders = new LinkedList<String>();

	private String projectFolder;
	
	/**
	 * Constructor
	 */
	public AnalysedDelta(String projectFolder) {
		this.projectFolder = projectFolder;
	}

	public void addedFile(String projectRelativeFileName) {
		addedFiles.add(projectRelativeFileName);
	}
	
	public void removedFile(String projectRelativeFileName) {
		removedFiles.add(projectRelativeFileName);
	}
	
	public void changedFile(String projectRelativeFileName) {
		changedFiles.add(projectRelativeFileName);
	}

	public void addedFolder(String projectRelativeFileName) {
		addedFolders.add(projectRelativeFileName);
	}
	
	public void removedFolder(String projectRelativeFileName) {
		removedFolders.add(projectRelativeFileName);
	}

	/**
	 * @return <code>true</code> if any change occurs, <code>false</code> otherwise.
	 */
	public boolean hasChanges() {
		return addedFiles.size() != 0 ||
			removedFiles.size() != 0 ||
			changedFiles.size() != 0 ||
			addedFolders.size() != 0 ||
			removedFolders.size() != 0;
	}

	// Getters
	public String getProjectFolder() {
		return projectFolder;
	}
	
	public List<String> getAddedFiles() {
		return Collections.unmodifiableList(addedFiles);
	}

	public List<String> getRemovedFiles() {
		return Collections.unmodifiableList(removedFiles);
	}

	public List<String> getChangedFiles() {
		return Collections.unmodifiableList(changedFiles);
	}

	public List<String> getAddedFolders() {
		return Collections.unmodifiableList(addedFolders);
	}

	public List<String> getRemovedFolders() {
		return Collections.unmodifiableList(removedFolders);
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("Added files: ");  //$NON-NLS-1$
		buffer.append(addedFiles);  //$NON-NLS-1$
		buffer.append("\n");  //$NON-NLS-1$
		
		buffer.append("Removed files: ");  //$NON-NLS-1$
		buffer.append(removedFiles);  //$NON-NLS-1$
		buffer.append("\n");  //$NON-NLS-1$
		
		buffer.append("Changed files: ");  //$NON-NLS-1$
		buffer.append(changedFiles);  //$NON-NLS-1$
		buffer.append("\n");  //$NON-NLS-1$
		
		buffer.append("Added folders: ");  //$NON-NLS-1$
		buffer.append(addedFolders);  //$NON-NLS-1$
		buffer.append("\n");  //$NON-NLS-1$
		
		buffer.append("Removed folders: ");  //$NON-NLS-1$
		buffer.append(removedFolders);  //$NON-NLS-1$
		buffer.append("\n");  //$NON-NLS-1$
		
		return buffer.toString();
	}
	
}
