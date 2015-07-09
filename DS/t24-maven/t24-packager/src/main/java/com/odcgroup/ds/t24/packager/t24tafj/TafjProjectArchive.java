package com.odcgroup.ds.t24.packager.t24tafj;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;

import com.odcgroup.ds.t24.packager.helper.ZipHelper;

public class TafjProjectArchive {
	
	static final String DATA_PUBLIC_CODE_CLASSIFIER = "dataPublicCode";
	static final String DATA_FOLDER = "data";
	static final String BASIC_PUBLIC_FOLDER = "basic-public";
	
	private File archive;
	private File dataPublicCodeArchive;
	
	public TafjProjectArchive(File archive) {
		this.archive = archive;
		this.dataPublicCodeArchive = dataPublicCodeArchive(archive);
	}
	
	private File dataPublicCodeArchive(File archive) {
		String filename = archive.getName();
		String extension = FilenameUtils.getExtension(filename);
		if (!extension.isEmpty()) {
			extension = "." + extension; 
		}
		filename = StringUtils.remove(filename, extension) + "-" + DATA_PUBLIC_CODE_CLASSIFIER + ".zip";
		
		return new File(archive.getParentFile(), filename);
	}

	public void copyJarTo(File tafjFolder) throws IOException {
		FileUtils.copyFile(archive, new File(tafjFolder, stripVersion(archive.getName())));
	}

	protected String stripVersion(String filename) {
		String extension = FilenameUtils.getExtension(filename);
		if (!extension.isEmpty()) {
			extension = "." + extension; 
		}
		
		String result = StringUtils.removeEnd(filename, extension);
		result = StringUtils.removeEnd(result, "-SNAPSHOT");
		result = StringUtils.substringBeforeLast(result, "-");
		
		if (result.isEmpty()) {
			throw new IllegalArgumentException("Invalid file name : " + filename);
		}
		
		return  result + extension;
	}

	/**
	 * Extract all the data to the specified folder. The relocate is used
	 * by the packager to organize the data according the tar structure 
	 * requirements.
	 */
	public void extractDataTo(File outputFolder, final ZipHelper.Relocate relocate) throws IOException {
		if (!dataPublicCodeArchive.exists()){
			return;
		}
		
		ZipHelper.unzipToFolder(
				dataPublicCodeArchive, 
				outputFolder, 
				new ZipHelper.Filter() {
					public boolean keep(String entryName) {
						return entryName.startsWith(DATA_FOLDER) && entryName.endsWith(".d");
					}
				},
				new ZipHelper.Relocate() {
					public String relocate(String entry) {
						return relocate.relocate(StringUtils.remove(entry, DATA_FOLDER + "/"));
					}
				});
	}

	/**
	 * Extract all the public basic files. The relocate is used by the packager
	 * to organize the file according the expected tar structure.
	 */
	public void extractPublicBasicTo(File outputFolder, final ZipHelper.Relocate relocate) throws IOException {
		if (!dataPublicCodeArchive.exists()){
			return;
		}
		
		ZipHelper.unzipToFolder(dataPublicCodeArchive, outputFolder, new ZipHelper.Filter() {
			public boolean keep(String entryName) {
				return entryName.startsWith(BASIC_PUBLIC_FOLDER);
			}
		},
		new ZipHelper.Relocate() {
			public String relocate(String entry) {
				return relocate.relocate(StringUtils.remove(entry, BASIC_PUBLIC_FOLDER + "/"));
			}
		});
	}

	public File getArchive() {
		return archive;
	}

	public File getDataPublicCodeArchive() {
		return dataPublicCodeArchive;
	}

	public List<String> getDataFileNames() throws IOException {
		return ZipHelper.listZipEntries(archive);
	}

}
