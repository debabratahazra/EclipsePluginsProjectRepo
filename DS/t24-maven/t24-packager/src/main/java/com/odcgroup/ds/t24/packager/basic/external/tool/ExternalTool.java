package com.odcgroup.ds.t24.packager.basic.external.tool;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.odcgroup.ds.t24.packager.generator.T24Packager;
import com.odcgroup.ds.t24.packager.generator.T24PackagerException;
import com.odcgroup.ds.t24.packager.helper.T24EnvironmentConfiguration;

/**
 * Helper to execute an external command (i.e. java) with gathering of 
 * <ul><li>the standard and error output streams</li>
 * <li>return code</li></ul>
 * <b>The execution of an external tool may lead to OS dependencies</b>
 * @author yan
 */
public class ExternalTool {

	private T24Packager packager;
	
	private static final String INTERNAL_ERROR = "Design Studio Internal Error";

	/**
	 * Inspired from:
	 * http://www.javaworld.com/javaworld/jw-12-2000/jw-1229-traps.html?page=4
	 */
	class StreamGobbler extends Thread {
		
		InputStream is;
		boolean errorStream;
		IOutputCallback callback;

		StreamGobbler(InputStream is, IOutputCallback callback, boolean errorStream) {
			this.is = is;
			this.errorStream = errorStream;
			this.callback = callback;
		}

		public void run() {
			packager.getLogger().debug("Starting StreamGobbler (" + toString() + ") for " + (errorStream?"error stream":"standard stream"));
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
				packager.getLogger().debug("Shutdowning StreamGobbler for (" + toString() + ")");
			}
		}
	}
	
	public ExternalTool() {
	}

	/**
 	 * Execute an external command and wait until it is finished. 
	 * The callback is used to send the output of the execution 
	 * to the UI.
	 * @param cmd command to execute (i.e. dir)
	 * @param envp environment properties
	 * @param callback
	 * @return the command return code
	 * @throws ExternalToolException
	 */
	public int executeCmd(String cmd, String[] envp,File executionDir, IOutputCallback callback) throws ExternalToolException {
		packager.getLogger().debug("Launching : " + cmd);
		packager.getLogger().debug("with the follwing environment properties:");
		if (envp == null) {
			packager.getLogger().debug("No parameters");
		} else {
			for (String prop: envp) {
				packager.getLogger().debug(prop);
			}
		}
		Process proc = null;
		try {
			Runtime rt = Runtime.getRuntime();
			callback.outputLine(cmd, false);
			proc = rt.exec(cmd, envp,executionDir);

			StreamGobbler errorGobbler = new StreamGobbler(proc.getErrorStream(), callback, true);
			StreamGobbler outputGobbler = new StreamGobbler(proc.getInputStream(), callback, false);

			errorGobbler.start();
			outputGobbler.start();

			int exitVal = proc.waitFor();
			String exitValMsg = "Exiting with value: " + exitVal;
			if (exitVal == 0) {
				packager.getLogger().debug(exitValMsg);
			} else {
				packager.getLogger().error(exitValMsg);
				throw new ExternalToolException("Execution failed");
			}
			callback.outputLine(exitValMsg, exitVal != 0);
			return exitVal;
		} catch (Exception e) {
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
	 * @param envp
	 * @param outputCallback
	 * @return the command return code
	 * @throws ExternalToolException
	 */
	public int executeJava(String args, String[] envp, IOutputCallback outputCallback) throws ExternalToolException {
		String javaExec = System.getProperty("java.home") + "/bin/java " + args;
		return executeCmd(javaExec, envp,null, outputCallback);
	}
	
	private void reportInternalError(Exception e, IOutputCallback callback) {
		synchronized(callback) {
			packager.getLogger().error(INTERNAL_ERROR);
			callback.outputLine(INTERNAL_ERROR, true);
			
			packager.getLogger().error(e.toString(), e);
			callback.outputLine(e.toString(), true);
			for (StackTraceElement st : e.getStackTrace()) {
				callback.outputLine(st.toString(), true);
			}
		}
	}
	
	public static String getTafcHome() throws T24PackagerException {
		String home = T24EnvironmentConfiguration.getT24Home();
		if (home == null) {
			throw new T24PackagerException("The HOME is not set. This is required to launch the basic compilation.");
		}
		return home;
	}

	public T24Packager getPackager() {
		return packager;
	}

	public void setPackager(T24Packager packager) {
		this.packager = packager;
	}
}
