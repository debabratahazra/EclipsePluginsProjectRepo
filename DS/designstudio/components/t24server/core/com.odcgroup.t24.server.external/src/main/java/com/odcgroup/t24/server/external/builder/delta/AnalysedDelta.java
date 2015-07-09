package com.odcgroup.t24.server.external.builder.delta;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Holds the differences (modified or created files)
 * An instance of this class is used as an input of the server update.
 * @author yan
 */
public class AnalysedDelta {
	
	private List<String> addedFiles = new LinkedList<String>();
	private List<String> changedFiles = new LinkedList<String>();

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
	
	public void changedFile(String projectRelativeFileName) {
		changedFiles.add(projectRelativeFileName);
	}

	/**
	 * @return <code>true</code> if any change occurs, <code>false</code> otherwise.
	 */
	public boolean hasChanges() {
		return addedFiles.size() != 0 ||
			changedFiles.size() != 0;
	}

	// Getters
	public String getProjectFolder() {
		return projectFolder;
	}
	
	public List<String> getAddedFiles() {
		return Collections.unmodifiableList(addedFiles);
	}

	public List<String> getChangedFiles() {
		return Collections.unmodifiableList(changedFiles);
	}

	public String getSummary() {
		StringBuffer buffer = new StringBuffer();
		if (addedFiles.size() > 0) {
			buffer.append("Added files: ");
			buffer.append(addedFiles);
			buffer.append("\n");
		}
		if (changedFiles.size() > 0) {
			buffer.append("Changed files: ");
			buffer.append(changedFiles);
			buffer.append("\n");
		}
		
		if (buffer.length() == 0) {
			buffer.append("No added files, no changed files");
		}
		
		return buffer.toString();
	}

}
