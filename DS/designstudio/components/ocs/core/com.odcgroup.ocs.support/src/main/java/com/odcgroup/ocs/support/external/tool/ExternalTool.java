package com.odcgroup.ocs.support.external.tool;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Helper to execute an external command (i.e. java) with gathering of 
 * <ul><li>the standard and error output streams</li>
 * <li>return code</li></ul>
 * <b>The execution of an external tool may lead to OS dependencies</b>
 * @author yan
 */
public class ExternalTool {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = LoggerFactory.getLogger(ExternalTool.class);

	private static final String INTERNAL_ERROR = "Design Studio Internal Error";

	/**
	 * Inspired from:
	 * http://www.javaworld.com/javaworld/jw-12-2000/jw-1229-traps.html?page=4
	 */
	static class StreamGobbler extends Thread {
		/**
		 * Logger for this class
		 */
		private static final Logger logger = LoggerFactory.getLogger(StreamGobbler.class);

		InputStream is;
		boolean errorStream;
		IOutputCallback callback;

		StreamGobbler(InputStream is, IOutputCallback callback, boolean errorStream) {
			this.is = is;
			this.errorStream = errorStream;
			this.callback = callback;
		}

		public void run() {
			logger.trace("Starting StreamGobbler (" + toString() + ") for " + (errorStream?"error stream":"standard stream"));
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			try {
				String line = null;
				while ((line = br.readLine()) != null) {
					synchronized(callback) {
						callback.outputLine(line, errorStream);
					}
				}
			} catch (Exception e) {
				reportInternalError(e, callback);
			} finally {
				try {
					br.close();
				} catch (Exception e) {
					reportInternalError(e, callback);
				}
				logger.trace("Shutdowning StreamGobbler for (" + toString() + ")");
			}
		}
	}

	/**
 	 * Execute an external command and wait until it is finished. 
	 * The callback is used to send the output of the execution 
	 * to the UI.
	 * @param cmd command to execute (i.e. dir)
	 * @param callback
	 * @return the command return code
	 * @throws ExternalToolException
	 */
	public static int executeCmd(String cmd, IOutputCallback callback) throws ExternalToolException {
		Process proc = null;
		try {
			Runtime rt = Runtime.getRuntime();
			logger.trace("Executing " + cmd);
			callback.outputLine("Executing " + cmd, false);
			proc = rt.exec(cmd);
			
			StreamGobbler errorGobbler = new StreamGobbler(proc.getErrorStream(), callback, true);
			StreamGobbler outputGobbler = new StreamGobbler(proc.getInputStream(), callback, false);

			errorGobbler.start();
			outputGobbler.start();

			int exitVal = proc.waitFor();
			String exitValMsg = "Exiting with value: " + exitVal;
			if (exitVal == 0) {
				logger.trace(exitValMsg);
			} else {
				logger.error(exitValMsg);
			}
			callback.outputLine(exitValMsg, exitVal != 0);
			return exitVal;
		} catch (Exception e) {
			reportInternalError(e, callback);
			throw new ExternalToolException("Unable to execute " + cmd + " properly", e);
		} finally {
			if (proc != null) {
				proc.destroy();
			}
		}
	}
	
	/**
	 * Execute a java command and wait until it is finished. 
	 * The callback is used to send the output of the execution 
	 * to the UI.
	 * @param args
	 * @param outputCallback
	 * @return the command return code
	 * @throws ExternalToolException
	 */
	public static int executeJava(String args, IOutputCallback outputCallback) throws ExternalToolException {
		String javaExec = System.getProperty("java.home") + "/bin/java " + args;
		return executeCmd(javaExec, outputCallback);
	}
	
	private static void reportInternalError(Exception e, IOutputCallback callback) {
		synchronized(callback) {
			logger.error(INTERNAL_ERROR);
			callback.outputLine(INTERNAL_ERROR, true);
			
			logger.error(e.toString(), e);
			callback.outputLine(e.toString(), true);
			for (StackTraceElement st : e.getStackTrace()) {
				callback.outputLine(st.toString(), true);
			}
		}
	}
}
