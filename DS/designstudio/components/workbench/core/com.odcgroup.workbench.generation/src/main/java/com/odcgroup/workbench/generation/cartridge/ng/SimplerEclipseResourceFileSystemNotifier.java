package com.odcgroup.workbench.generation.cartridge.ng;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileLock;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A call back class for the SimplerEclipseResourceFileSystemAccess2.
 * This file is updating a file called lastChange at the root of the project
 * containing the file updated. (used by iris metadata and models generation 
 * to know when something has changed and the embedded server "scanner" to act accordingly.
 *
 * @author taubert
 *
 */
public class SimplerEclipseResourceFileSystemNotifier implements SimplerEclipseResourceFileSystemAccess2.IFileCallback {
	
	private static final Logger logger = LoggerFactory.getLogger(SimplerEclipseResourceFileSystemNotifier.class);
	private static final byte[] bLineSep = System.getProperty("line.separator").getBytes();
	
	@Override
	public void afterFileUpdate(IFile file) {
		try{
			updateModificationTimeStamp(file);
		}catch(Exception e){
			logger.error("Unexpected exception occured", e);
		}
	}

	@Override
	public void afterFileCreation(IFile file) {
		try{
			updateModificationTimeStamp(file);
		}catch(Exception e){
			logger.error("Unexpected exception occured", e);
		}
	}

	@Override
	public boolean beforeFileDeletion(IFile file) {
		return false;
	}
	
	private void updateModificationTimeStamp(IFile f){
		IProject p = f.getProject(); 
		File fLock = p.getFile(".lastChangeLock").getLocation().toFile();
		File fTS = p.getFile("lastChange").getLocation().toFile();
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
				raf.write(f.getLocation().toString().getBytes());
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
}
