package com.odcgroup.ds.t24.packager.t24project;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.odcgroup.ds.t24.packager.helper.ZipHelper;

/**
 * A T24ProjectArchive represents a zipped version of the T24 project.
 * At this stage there is no distinction between model and public data.
 * The difference between public and private basic remains.
 * @author yandenmatten
 */
public class T24ProjectArchive {

	static final String DATA_FOLDER = "data";
	static final String BASIC_PRIVATE_FOLDER = "basic-private";
	static final String BASIC_PUBLIC_FOLDER = "basic-public";

	private File archive;
	
	public T24ProjectArchive(File archive) {
		this.archive = archive;
	}
	
	/**
	 * Extract all the data to the specified folder. The relocate is used
	 * by the packager to organize the data according the tar structure 
	 * requirements.
	 */
	public void extractDataTo(File outputFolder, final ZipHelper.Relocate relocate) throws IOException {
		ZipHelper.unzipToFolder(
				archive, 
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
	
	public List<String> getDataFileNames() throws IOException {
		return ZipHelper.listZipEntries(archive);
	}

	/**
	 * Extract all the public basic files. The relocate is used by the packager
	 * to organize the file according the expected tar structure.
	 */
	public void extractPublicBasicTo(File outputFolder, final ZipHelper.Relocate relocate) throws IOException {
		ZipHelper.unzipToFolder(archive, outputFolder, new ZipHelper.Filter() {
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
	
	/**
	 * Extract all the private and public basic. This is used to prepare 
	 * basic compilation.
	 */
	public void extractAllBasicTo(File outputFolder) throws IOException {
		ZipHelper.unzipToFolder(archive, outputFolder, new ZipHelper.Filter() {
			public boolean keep(String entryName) {
				return entryName.startsWith(BASIC_PRIVATE_FOLDER) ||
						entryName.startsWith(BASIC_PUBLIC_FOLDER);
			}
		},
		new ZipHelper.Relocate() {
			public String relocate(String entry) {
				if (entry.startsWith(BASIC_PRIVATE_FOLDER)) {
					entry = StringUtils.removeStart(entry, BASIC_PRIVATE_FOLDER + "/");
				} else if (entry.startsWith(BASIC_PUBLIC_FOLDER)) {
					entry = StringUtils.removeStart(entry, BASIC_PUBLIC_FOLDER + "/");
				}
				
				// Remove .b for basic file
				entry = StringUtils.removeEnd(entry, ".b");
				return entry;
			}
		});
	}

	public File getArchive() {
		return archive;
	}
	
}
