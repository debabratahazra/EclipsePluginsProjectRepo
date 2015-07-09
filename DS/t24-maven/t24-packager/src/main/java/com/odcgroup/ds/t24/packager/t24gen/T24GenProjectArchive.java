package com.odcgroup.ds.t24.packager.t24gen;

import java.io.File;
import java.io.IOException;

import com.odcgroup.ds.t24.packager.helper.ZipHelper;

public class T24GenProjectArchive {

	private File archive;
	
	public T24GenProjectArchive(File archive) {
		this.archive = archive;
	}
	
	public void extractTo(File outputFolder) throws IOException {
		ZipHelper.unzipToFolder(archive, outputFolder);
	}

	public File getArchive() {
		return archive;
	}
}
