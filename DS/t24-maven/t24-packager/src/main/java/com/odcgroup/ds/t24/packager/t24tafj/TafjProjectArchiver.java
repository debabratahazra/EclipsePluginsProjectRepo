package com.odcgroup.ds.t24.packager.t24tafj;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import com.odcgroup.ds.t24.packager.helper.ZipHelper;
import com.odcgroup.ds.t24.packager.t24project.T24ProjectException;

public class TafjProjectArchiver {

	public TafjProjectArchive archive(TafjProject project) throws T24ProjectException {
		try {
			return new TafjProjectArchive(createZipArchive(project));
		} catch (IOException e) {
			throw new T24ProjectException("Unable to create the archive", e);
		}
	}

	protected File createZipArchive(TafjProject project) throws IOException {
		File archive = new File(project.getOutputFolder(), project.getName() + "-" + project.getVersion() + "-" + TafjProjectArchive.DATA_PUBLIC_CODE_CLASSIFIER + ".zip");
		FileUtils.forceMkdir(archive.getParentFile());
		ZipHelper.createZip(archive,
				new ZipHelper.MappedFolder(project.getSourcePublicFolder(), TafjProjectArchive.BASIC_PUBLIC_FOLDER),
				new ZipHelper.MappedFolder(project.getDataPublicFolder(), TafjProjectArchive.DATA_FOLDER),
				new ZipHelper.MappedFolder(project.getDataModelFolder(), TafjProjectArchive.DATA_FOLDER));

		return archive;
	}
	

}
