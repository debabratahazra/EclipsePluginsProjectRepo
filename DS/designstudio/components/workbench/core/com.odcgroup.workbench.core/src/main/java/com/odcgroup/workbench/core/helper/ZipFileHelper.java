package com.odcgroup.workbench.core.helper;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;

/**
 * Some utility methods for handling zip file contents.
 *
 * @author Kai Kreuzer
 *
 */
public class ZipFileHelper {

	public static Set<String> getSubDirectories(Enumeration<? extends ZipEntry> zipEntries, IPath path) {
		Set<String> subDirs = new HashSet<String>();
		while(zipEntries.hasMoreElements()) {
			ZipEntry entry = zipEntries.nextElement();
			IPath entryPath = new Path(entry.getName());
			if(entry.isDirectory() && path.isPrefixOf(entryPath)) {
				IPath relativePath = new Path(entry.getName().substring(path.toString().length()));
				if(relativePath.segmentCount()==1) 
					subDirs.add(relativePath.segment(0));
			}
		}
		return subDirs;
	}

	public static Set<IPath> getFiles(Enumeration<? extends ZipEntry> zipEntries, IPath path) {
		Set<IPath> files = new HashSet<IPath>();
		while(zipEntries.hasMoreElements()) {
			ZipEntry entry = zipEntries.nextElement();
			IPath entryPath = new Path(entry.getName());
			if(!entry.isDirectory() && path.isPrefixOf(entryPath)) {
				IPath relativePath = new Path(entry.getName().substring(path.toString().length()));
				if(relativePath.segmentCount()==1) 
					files.add(entryPath);
			}
		}
		return files;
	}

	public static void copyToFolder(ZipFile zipFile, IPath path, IFolder targetFolder) throws CoreException, IOException {
		Enumeration<? extends ZipEntry> zipEntries = zipFile.entries();
		while(zipEntries.hasMoreElements()) {
			ZipEntry entry = zipEntries.nextElement();
			IPath entryPath = new Path(entry.getName());
			if(path.isPrefixOf(entryPath)) {
				IPath relativePath = new Path(entry.getName().substring(path.toString().length()));
				if(relativePath.segmentCount()>0) {
					if(entry.isDirectory()) {
						IFolder folder = targetFolder.getFolder(relativePath);
						folder.create(false, true, null);
					} else {
						IFile file = targetFolder.getFile(relativePath);
						file.create(zipFile.getInputStream(entry), false, null);
					}
				}
			}
		}
	}
	
	public static InputStream getInputStream(ZipFile zipFile, IPath path) throws IOException {
		ZipEntry entry = zipFile.getEntry(path.toString());
		if(entry!=null) return zipFile.getInputStream(entry);
		return null;
	}
	
	public static boolean exists(ZipFile zipFile, IPath path) {
		return zipFile.getEntry(path.toString())!=null;
	}
}
