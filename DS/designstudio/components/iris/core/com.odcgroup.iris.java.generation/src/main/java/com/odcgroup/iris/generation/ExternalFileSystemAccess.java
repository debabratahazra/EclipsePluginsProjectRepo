package com.odcgroup.iris.generation;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.io.Writer;
import java.nio.channels.FileLock;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.xtext.generator.IFileSystemAccess;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This IFileSystemAccess implementation supports creating and modifying files that exist outside of the eclipse workspace, including
 * support for the IRIS embedded server runtime file update mechanism.
 */
public class ExternalFileSystemAccess implements IFileSystemAccess {
	private static final Logger logger = LoggerFactory.getLogger(ExternalFileSystemAccess.class);
	private static final byte[] bLineSep = System.getProperty("line.separator").getBytes();
	private File irisConfigDir;

	public ExternalFileSystemAccess(File irisConfigDir) {
		this.irisConfigDir = irisConfigDir;
	}

	@Override
	public void generateFile(String fileName, String outputConfigurationName, CharSequence contents) {
		generateFile(fileName, null, contents);
	}

	@Override
	public void generateFile(String fileName, CharSequence contents) {
		File file = new File(irisConfigDir, new File(fileName).getName());
		Writer writer = null;

		try {
			writer = new PrintWriter(file);
			writer.append(contents);
			writer.flush();

			// Pretend the old Eclipse update mechanism is in place...
			IWorkspace workspace = ResourcesPlugin.getWorkspace();
			IProject project = null;

			for(IProject tmpProject: workspace.getRoot().getProjects()) {
				if(tmpProject.getName().endsWith("-models-gen")) {
					project = tmpProject;
					break;
				}
			}

			if(project != null) {
				File modelsGenDir = project.getLocation().toFile();

				updateModificationTimeStamp(modelsGenDir, file);

				File yuck = new File(modelsGenDir, "src/generated/iris/IRIS-dummy.properties");
				yuck.createNewFile();
			}
		} catch (Exception e) {
			logger.warn("Failed to write file: " + file.getAbsolutePath(), e);
		} finally {
			if(writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					logger.warn("Failed to close writer for file: " + file.getAbsolutePath(), e);
				}
			}
		}
	}

	private void updateModificationTimeStamp(File modelsGenDir, File file){
		File fLock = new File(modelsGenDir, ".lastChangeLock");
		File fTS = new File(modelsGenDir, "lastChange");
		RandomAccessFile raf = null;
		FileLock lock = null;
		RandomAccessFile rafLock = null;
		try{
			if (!fLock.exists()){
				fLock.createNewFile();
			}
			if (!fTS.exists()){
				fTS.createNewFile();
			}
			/*
			 * Get atomic lock
			 */
			rafLock = new RandomAccessFile(fLock, "rw");
			lock = rafLock.getChannel().lock();

			/*
			 * Here we are alone.
			 */
			raf = new RandomAccessFile(fTS, "rws");
			long size = fTS.length();
			if (size > 1024 * 100){  // Max 100K, after that leave the embedded server to figure-out what needs to be refreshed by introspecting the timestamps.
				raf.seek(0);
			    raf.write("RefreshAll".getBytes());
			}else{
				raf.seek(size);
				if (size > 0){
					raf.write(bLineSep);
				}
				raf.write(file.getAbsolutePath().getBytes());
			}
		} catch (IOException e) {
			logger.error("Failed to update the lastChange file.", e);
		} finally {
		    try {
				raf.close();
			} catch (Exception e) { // could be null
				logger.error("Failed to close the lastChange file.", e);
			}
		    try {
				lock.release();
			} catch (Exception e) {  // could be null
				logger.error("Failed to release lock on lastChange file.", e);
			}
		    try {
			rafLock.close();
			} catch (Exception e) { // could be null
				logger.error("Failed to close the lastChangeLock file.", e);
			}

		}
	}

	@Override
	public void deleteFile(String fileName) {
		File file = new File(irisConfigDir, fileName);
		boolean exists = file.exists();

		if(exists) {
			if(!file.delete()) {
				logger.warn("Failed to delete file: " + file.getAbsolutePath());
			}
		}
	}
}
