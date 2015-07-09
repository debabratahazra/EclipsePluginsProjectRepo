package com.odcgroup.ds.t24.packager.t24project;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import com.odcgroup.ds.t24.packager.helper.ZipHelper;

/**
 * This class archives T24project in zip.
 * @author yandenmatten
 */
public class T24ProjectArchiver {

	public T24ProjectArchive archive(T24Project project) throws T24ProjectException {
		try {
			return new T24ProjectArchive(createZipArchive(project));
		} catch (IOException e) {
			throw new T24ProjectException("Unable to create the archive", e);
		}
	}

	protected File createZipArchive(T24Project project) throws IOException {
		File archive = new File(project.getOutputFolder(), project.getName() + "-" + project.getVersion() + ".zip");
		FileUtils.forceMkdir(archive.getParentFile());
		ZipHelper.createZip(archive,
				new ZipHelper.MappedFolder(project.getSourcePublicFolder(), T24ProjectArchive.BASIC_PUBLIC_FOLDER),
				new ZipHelper.MappedFolder(project.getSourcePrivateFolder(), T24ProjectArchive.BASIC_PRIVATE_FOLDER),
				new ZipHelper.MappedFolder(project.getDataPublicFolder(), T24ProjectArchive.DATA_FOLDER),
				new ZipHelper.MappedFolder(project.getDataModelFolder(), T24ProjectArchive.DATA_FOLDER));

		return archive;
	}
	
}
