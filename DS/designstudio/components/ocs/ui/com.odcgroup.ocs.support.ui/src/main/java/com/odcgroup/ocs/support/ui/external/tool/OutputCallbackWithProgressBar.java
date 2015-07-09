package com.odcgroup.ocs.support.ui.external.tool;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;

import com.odcgroup.ocs.support.external.tool.IOutputCallback;

/**
 * Create a callback to update the progress bar according the command execution
 * output.
 * @author yan
 */
final class OutputCallbackWithProgressBar implements
		IOutputCallback {
	
	final static String PROGRESS_PREFIX = "PROGRESS: ";

	private final ProgressMonitorDialog progressDialog;
	private final IProgressMonitor progressMonitor;
	
	// Track progress
	private int total = 0;
	private int nbItems = 0;
	private int percentage = 0;
	
	// Keep last entries of the log
	private LogKeeper logKeeper; 

	OutputCallbackWithProgressBar(
			ProgressMonitorDialog progressDialog,
			IProgressMonitor progressMonitor) {
		this(progressDialog, progressMonitor, null);
	}

	OutputCallbackWithProgressBar(
			ProgressMonitorDialog progressDialog,
			IProgressMonitor progressMonitor,
			LogKeeper logKeeper) {
		this.progressDialog = progressDialog;
		this.progressMonitor = progressMonitor;
		this.logKeeper = logKeeper;
	}

	public void outputLine(String line, boolean isError) {
		ExternalToolUI.logger.trace(line);

		// Keep only the most recent lines
		if (logKeeper != null) {
			logKeeper.keepLog(line);
		}
		
		if (total == 0 && line.startsWith(PROGRESS_PREFIX)) {
			Pattern pat = Pattern.compile("\\d+");
			Matcher match = pat.matcher(line);
			if (match.find()) {
				try {
					total = Integer.parseInt(match.group());
				} catch (NumberFormatException e) {
					// Ignore the error as it only impact the user feedback 
				}
			}
		} else if (line.startsWith(PROGRESS_PREFIX)) {
			String progress = StringUtils.removeStart(line, PROGRESS_PREFIX);
			progressMonitor.subTask(progress);
			nbItems++;
			try {
				final int externalToolDone = 100*nbItems / total;
				if (externalToolDone > percentage) {
					progressMonitor.worked(externalToolDone - percentage);

					progressDialog.getShell().getDisplay().asyncExec(new Runnable(){
						public void run() {
							if (progressDialog.getShell() == null)
								return;
							if (progressDialog.getShell().isDisposed())
								return;
							if (progressDialog.getShell().getDisplay().isDisposed())
								return;
							
							progressMonitor.worked(percentage - externalToolDone);
						}
					});

					percentage = externalToolDone;
				}
			} catch (NumberFormatException e) {
				// Ignore error
			}
		}
	}
}