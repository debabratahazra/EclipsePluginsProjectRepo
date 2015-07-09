package com.odcgroup.ocs.packager.launcher;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.debug.core.IStreamListener;
import org.eclipse.debug.core.model.IStreamMonitor;

public class PackagerStreamListener implements IStreamListener {

	static final String BUILD_SUCCESS = "BUILD SUCCESS";
	static final String CUSTO_PACKAGE_GENERATED_PREFIX = "Package generated : ";
	private IProgressMonitor monitor;
	private int nbLogLines;
	private int estimatedNbLogLinesToComplete;
	private int percentageDone;
	/**
	 * @return the percentageDone
	 */
	int getPercentageDone() {
		return percentageDone;
	}

	private boolean buildSuccessful;
	private String zipPackageGenerated;
	
	public PackagerStreamListener(IProgressMonitor monitor, int estimatedNbLogLinesToComplete) {
		this.monitor = monitor!=null?monitor:new NullProgressMonitor();
		this.buildSuccessful = false;
		this.zipPackageGenerated = null;
		this.nbLogLines = 0;
		this.estimatedNbLogLinesToComplete = estimatedNbLogLinesToComplete;
		this.percentageDone = 0;
	}
	
	@Override
	public void streamAppended(String text, IStreamMonitor streamMonitor) {
		String[] lines = StringUtils.split(text, "\n");

		// Update the progressbar
		nbLogLines += lines.length;
		int newPercentageDone = 100 * nbLogLines / estimatedNbLogLinesToComplete;
		int percentageDiff = newPercentageDone - percentageDone;
		percentageDone = newPercentageDone;
		
		if (percentageDiff > 0) {
			monitor.worked(percentageDiff);
		}

		String lastNonEmptyLine = null;
		for (String line: lines) {
			String outputLine = StringUtils.removeStart(line, "[INFO] ").trim();
			
			// Is the package Generated?
			if (outputLine.startsWith(CUSTO_PACKAGE_GENERATED_PREFIX)) {
				zipPackageGenerated = StringUtils.removeStart(outputLine, CUSTO_PACKAGE_GENERATED_PREFIX);
			}
			
			// Is the build successful ?
			if (StringUtils.chomp(outputLine).equals(BUILD_SUCCESS)) {
				buildSuccessful = true;
			}
			
			if (!outputLine.isEmpty()) {
				lastNonEmptyLine = outputLine;
			}
		}
		
		
		// Update progress message
		if (lastNonEmptyLine != null) {
			monitor.subTask(lastNonEmptyLine);
		}
	}
	
	/**
	 * @return the buildSuccessful
	 */
	public boolean isBuildSuccessful() {
		return buildSuccessful;
	}

	/**
	 * @return the zipPackageGenerated
	 */
	public String getZipPackageGenerated() {
		return zipPackageGenerated;
	}

}
