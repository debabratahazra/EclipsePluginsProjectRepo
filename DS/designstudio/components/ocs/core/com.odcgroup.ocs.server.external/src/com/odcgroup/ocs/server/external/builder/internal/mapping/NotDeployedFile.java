package com.odcgroup.ocs.server.external.builder.internal.mapping;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

/**
 * Singleton that represents a non mappable file
 * @author yan
 */
public class NotDeployedFile extends File {

	private static final long serialVersionUID = 1L;
	
	public static final NotDeployedFile INSTANCE = new NotDeployedFile();
	
	private NotDeployedFile() {
		super("");
	}

	// All operation on this object will causes UnsupportedOperationException

	/* (non-Javadoc)
	 * @see java.io.File#canExecute()
	 */
	@Override
	public boolean canExecute() {
		throw new UnsupportedOperationException();
	}

	/* (non-Javadoc)
	 * @see java.io.File#canRead()
	 */
	@Override
	public boolean canRead() {
		throw new UnsupportedOperationException();
	}

	/* (non-Javadoc)
	 * @see java.io.File#canWrite()
	 */
	@Override
	public boolean canWrite() {
		throw new UnsupportedOperationException();
	}

	/* (non-Javadoc)
	 * @see java.io.File#compareTo(java.io.File)
	 */
	@Override
	public int compareTo(File pathname) {
		throw new UnsupportedOperationException();
	}

	/* (non-Javadoc)
	 * @see java.io.File#createNewFile()
	 */
	@Override
	public boolean createNewFile() throws IOException {
		throw new UnsupportedOperationException();
	}

	/* (non-Javadoc)
	 * @see java.io.File#delete()
	 */
	@Override
	public boolean delete() {
		throw new UnsupportedOperationException();
	}

	/* (non-Javadoc)
	 * @see java.io.File#deleteOnExit()
	 */
	@Override
	public void deleteOnExit() {
		throw new UnsupportedOperationException();
	}

	/* (non-Javadoc)
	 * @see java.io.File#exists()
	 */
	@Override
	public boolean exists() {
		throw new UnsupportedOperationException();
	}

	/* (non-Javadoc)
	 * @see java.io.File#getAbsoluteFile()
	 */
	@Override
	public File getAbsoluteFile() {
		throw new UnsupportedOperationException();
	}

	/* (non-Javadoc)
	 * @see java.io.File#getAbsolutePath()
	 */
	@Override
	public String getAbsolutePath() {
		throw new UnsupportedOperationException();
	}

	/* (non-Javadoc)
	 * @see java.io.File#getCanonicalFile()
	 */
	@Override
	public File getCanonicalFile() throws IOException {
		throw new UnsupportedOperationException();
	}

	/* (non-Javadoc)
	 * @see java.io.File#getCanonicalPath()
	 */
	@Override
	public String getCanonicalPath() throws IOException {
		throw new UnsupportedOperationException();
	}

	/* (non-Javadoc)
	 * @see java.io.File#getFreeSpace()
	 */
	@Override
	public long getFreeSpace() {
		throw new UnsupportedOperationException();
	}

	/* (non-Javadoc)
	 * @see java.io.File#getName()
	 */
	@Override
	public String getName() {
		throw new UnsupportedOperationException();
	}

	/* (non-Javadoc)
	 * @see java.io.File#getParent()
	 */
	@Override
	public String getParent() {
		throw new UnsupportedOperationException();
	}

	/* (non-Javadoc)
	 * @see java.io.File#getParentFile()
	 */
	@Override
	public File getParentFile() {
		throw new UnsupportedOperationException();
	}

	/* (non-Javadoc)
	 * @see java.io.File#getPath()
	 */
	@Override
	public String getPath() {
		throw new UnsupportedOperationException();
	}

	/* (non-Javadoc)
	 * @see java.io.File#getTotalSpace()
	 */
	@Override
	public long getTotalSpace() {
		throw new UnsupportedOperationException();
	}

	/* (non-Javadoc)
	 * @see java.io.File#getUsableSpace()
	 */
	@Override
	public long getUsableSpace() {
		throw new UnsupportedOperationException();
	}

	/* (non-Javadoc)
	 * @see java.io.File#isAbsolute()
	 */
	@Override
	public boolean isAbsolute() {
		throw new UnsupportedOperationException();
	}

	/* (non-Javadoc)
	 * @see java.io.File#isDirectory()
	 */
	@Override
	public boolean isDirectory() {
		throw new UnsupportedOperationException();
	}

	/* (non-Javadoc)
	 * @see java.io.File#isFile()
	 */
	@Override
	public boolean isFile() {
		throw new UnsupportedOperationException();
	}

	/* (non-Javadoc)
	 * @see java.io.File#isHidden()
	 */
	@Override
	public boolean isHidden() {
		throw new UnsupportedOperationException();
	}

	/* (non-Javadoc)
	 * @see java.io.File#lastModified()
	 */
	@Override
	public long lastModified() {
		throw new UnsupportedOperationException();
	}

	/* (non-Javadoc)
	 * @see java.io.File#length()
	 */
	@Override
	public long length() {
		throw new UnsupportedOperationException();
	}

	/* (non-Javadoc)
	 * @see java.io.File#list()
	 */
	@Override
	public String[] list() {
		throw new UnsupportedOperationException();
	}

	/* (non-Javadoc)
	 * @see java.io.File#list(java.io.FilenameFilter)
	 */
	@Override
	public String[] list(FilenameFilter filter) {
		throw new UnsupportedOperationException();
	}

	/* (non-Javadoc)
	 * @see java.io.File#listFiles()
	 */
	@Override
	public File[] listFiles() {
		throw new UnsupportedOperationException();
	}

	/* (non-Javadoc)
	 * @see java.io.File#listFiles(java.io.FileFilter)
	 */
	@Override
	public File[] listFiles(FileFilter filter) {
		throw new UnsupportedOperationException();
	}

	/* (non-Javadoc)
	 * @see java.io.File#listFiles(java.io.FilenameFilter)
	 */
	@Override
	public File[] listFiles(FilenameFilter filter) {
		throw new UnsupportedOperationException();
	}

	/* (non-Javadoc)
	 * @see java.io.File#mkdir()
	 */
	@Override
	public boolean mkdir() {
		throw new UnsupportedOperationException();
	}

	/* (non-Javadoc)
	 * @see java.io.File#mkdirs()
	 */
	@Override
	public boolean mkdirs() {
		throw new UnsupportedOperationException();
	}

	/* (non-Javadoc)
	 * @see java.io.File#renameTo(java.io.File)
	 */
	@Override
	public boolean renameTo(File dest) {
		throw new UnsupportedOperationException();
	}

	/* (non-Javadoc)
	 * @see java.io.File#setExecutable(boolean, boolean)
	 */
	@Override
	public boolean setExecutable(boolean executable, boolean ownerOnly) {
		throw new UnsupportedOperationException();
	}

	/* (non-Javadoc)
	 * @see java.io.File#setExecutable(boolean)
	 */
	@Override
	public boolean setExecutable(boolean executable) {
		throw new UnsupportedOperationException();
	}

	/* (non-Javadoc)
	 * @see java.io.File#setLastModified(long)
	 */
	@Override
	public boolean setLastModified(long time) {
		throw new UnsupportedOperationException();
	}

	/* (non-Javadoc)
	 * @see java.io.File#setReadable(boolean, boolean)
	 */
	@Override
	public boolean setReadable(boolean readable, boolean ownerOnly) {
		throw new UnsupportedOperationException();
	}

	/* (non-Javadoc)
	 * @see java.io.File#setReadable(boolean)
	 */
	@Override
	public boolean setReadable(boolean readable) {
		throw new UnsupportedOperationException();
	}

	/* (non-Javadoc)
	 * @see java.io.File#setReadOnly()
	 */
	@Override
	public boolean setReadOnly() {
		throw new UnsupportedOperationException();
	}

	/* (non-Javadoc)
	 * @see java.io.File#setWritable(boolean, boolean)
	 */
	@Override
	public boolean setWritable(boolean writable, boolean ownerOnly) {
		throw new UnsupportedOperationException();
	}

	/* (non-Javadoc)
	 * @see java.io.File#setWritable(boolean)
	 */
	@Override
	public boolean setWritable(boolean writable) {
		throw new UnsupportedOperationException();
	}

	/* (non-Javadoc)
	 * @see java.io.File#toURI()
	 */
	@Override
	public URI toURI() {
		throw new UnsupportedOperationException();
	}

	/* (non-Javadoc)
	 * @see java.io.File#toURL()
	 */
	@Override
	public URL toURL() throws MalformedURLException {
		throw new UnsupportedOperationException();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	@Override
	protected Object clone() throws CloneNotSupportedException {
		throw new UnsupportedOperationException();
	}

}
