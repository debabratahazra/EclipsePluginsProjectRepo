package com.odcgroup.ds.t24.packager.data.validator;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import com.odcgroup.ds.t24.packager.data.DataHelper;
import com.odcgroup.ds.t24.packager.generator.PackageTypeEnum;

public class DataFileValidator {
	
	private final PackageTypeEnum packageType;

	public DataFileValidator(PackageTypeEnum packageType) {
		this.packageType = packageType;
	}
	
	public void validate(File dataFile) throws DataValidationException {
		validateFileName(dataFile);
		validateFileEncoding(dataFile);
	}
	
	private void validateFileName(File dataFile) throws DataValidationException {
		if (!DataHelper.isDataFile(dataFile)) {
			throw new DataValidationException("The file " + dataFile.getAbsolutePath() + " has an invalid filename.");
		}
	}

	private void validateFileEncoding(File dataFile) throws DataValidationException {
		String dataFileContents;
		try {
			dataFileContents = FileUtils.readFileToString(dataFile, packageType.getEncoding());
		} catch (IOException e) {
			throw new DataValidationException("Unable to read the file " + dataFile.getAbsolutePath() + ".", e);
		} 
		
		if (packageType == PackageTypeEnum.TAFC) {
			boolean tafjSeparatorUsed = dataFileContents.contains(PackageTypeEnum.TAFJ.getFieldSeparatorInAnotherEncoding(PackageTypeEnum.TAFC.getEncoding()));
			if (tafjSeparatorUsed) {
				throw new DataValidationException("The data file " + dataFile.getAbsolutePath() + " uses unicode separator, but is packaged in a TAFC package.");
			}
		} else if (packageType == PackageTypeEnum.TAFJ) {
			boolean tafcSeparatorUsed = dataFileContents.contains(PackageTypeEnum.TAFC.getFieldSeparatorInAnotherEncoding(PackageTypeEnum.TAFJ.getEncoding()));
			if (tafcSeparatorUsed) {
				throw new DataValidationException("The data file " + dataFile.getAbsolutePath() + " uses TAFC compatible separator, but is packaged in a TAFJ package.");
			}
		} else {
			throw new IllegalStateException("The package type " + packageType + " is not supported.");
		}
	}
	
}
