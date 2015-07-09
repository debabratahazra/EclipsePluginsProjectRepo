package com.odcgroup.ocs.support.ui.external.tool;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.widgets.Shell;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.odcgroup.ocs.support.external.tool.ExternalTool;
import com.odcgroup.ocs.support.external.tool.ExternalToolException;

/**
 * Return the external tool 
 * @author yan
 */
public class ExternalToolUI {
	
	protected static final Logger logger = LoggerFactory.getLogger(ExternalToolUI.class);

	private static class ReturnCode {
		int code;
	}

	/**
	 * Execute a Java program externally with a progress bar.
	 * @param shell
	 * @param title
	 * @param javaArgs
	 * @return
	 * @throws ExternalToolException
	 * @throws InterruptedException
	 */
	public static int executeJavaWithProgressBar(final Shell shell, 
			final String title,
			final String javaArgs) throws ExternalToolException, InterruptedException {
		return executeJavaWithProgressBar(shell, title, javaArgs, null);
	}
	
	/**
	 * Execute a Java program externally with a progress bar.
	 * @param shell
	 * @param title
	 * @param javaArgs
	 * @return
	 * @throws ExternalToolException
	 * @throws InterruptedException
	 */
	public static int executeJavaWithProgressBar(final Shell shell, 
			final String title,
			final String javaArgs, 
			final LogKeeper logKeeper) throws ExternalToolException, InterruptedException {
		final ProgressMonitorDialog progressDialog = new ProgressMonitorDialog(shell);
		final ReturnCode returnCode = new ReturnCode();
		IRunnableWithProgress runnable = new IRunnableWithProgress() {
			public void run(final IProgressMonitor progressMonitor) throws InvocationTargetException,InterruptedException {
				progressMonitor.beginTask(title, 100);
				try {
					returnCode.code = ExternalTool.executeJava(javaArgs, new OutputCallbackWithProgressBar(progressDialog, progressMonitor, logKeeper));
				} catch (ExternalToolException e) {
					throw new InvocationTargetException(e);
				}
				progressMonitor.done();
			}
		};
		return execute(shell, progressDialog, returnCode, runnable);
	}

	/**
	 * Execute an external program with a progress bar.
	 * @param shell
	 * @param title
	 * @param javaArgs
	 * @return
	 * @throws ExternalToolException
	 * @throws InterruptedException
	 */
	public static int executeCmdWithProgressBar(final Shell shell, 
			final String title,
			final String javaArgs) throws ExternalToolException, InterruptedException {
		return executeCmdWithProgressBar(shell, title, javaArgs, null);
	}
	
	/**
	 * Execute an external program with a progress bar.
	 * @param shell
	 * @param title
	 * @param javaArgs
	 * @param logKeeper
	 * @return
	 * @throws ExternalToolException
	 * @throws InterruptedException
	 */
	public static int executeCmdWithProgressBar(final Shell shell, 
			final String title,
			final String javaArgs, 
			final LogKeeper logKeeper) throws ExternalToolException, InterruptedException {
		final ProgressMonitorDialog progressDialog = new ProgressMonitorDialog(shell);
		final ReturnCode returnCode = new ReturnCode();
		IRunnableWithProgress runnable = new IRunnableWithProgress() {
			public void run(final IProgressMonitor progressMonitor) throws InvocationTargetException,InterruptedException {
				progressMonitor.beginTask(title, 100);
				try {
					returnCode.code = ExternalTool.executeCmd(javaArgs, new OutputCallbackWithProgressBar(progressDialog, progressMonitor, logKeeper));
				} catch (ExternalToolException e) {
					throw new InvocationTargetException(e);
				}
				progressMonitor.done();
			}
		};
		return execute(shell, progressDialog, returnCode, runnable);
	}

	/**
	 * Execute a runnable with the appropriate error handling.
	 * @param shell
	 * @param progressDialog
	 * @param returnCode
	 * @param runnable
	 * @return
	 * @throws ExternalToolException
	 * @throws InterruptedException 
	 */
	private static int execute(final Shell shell, final ProgressMonitorDialog progressDialog,
			final ReturnCode returnCode, IRunnableWithProgress runnable) throws ExternalToolException, InterruptedException {
		try {
			progressDialog.run(true, false, runnable);
		} catch (InvocationTargetException e) {
			if (e.getCause() instanceof ExternalToolException) {
				throw (ExternalToolException)e.getCause();
			} else {
				throw new ExternalToolException("Unexpected execution problem", e);
			}
		} catch (InterruptedException e) {
			throw e;
		} catch (Exception e) {
			throw new ExternalToolException("Unexpected execution problem", e);
		}

		return returnCode.code;
	}
}
