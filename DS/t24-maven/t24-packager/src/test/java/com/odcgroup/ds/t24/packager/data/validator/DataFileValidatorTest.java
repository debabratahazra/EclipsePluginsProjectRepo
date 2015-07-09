package com.odcgroup.ds.t24.packager.data.validator;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;

import org.junit.Test;

import com.odcgroup.ds.t24.packager.generator.PackageTypeEnum;

public class DataFileValidatorTest {
	
	private static final String TAFC_DATA_FILE = "F.CONVERSION.DETAILS!CONV.TEC.SERVICE.R13.d";
	private static final String TAFJ_DATA_FILE = "FBNK.CURRENCY!USD.d";
	private static final String NEUTRAL_DATA_FILE = "F.PGM.FILE!T24MONITOR.DISPATCHER.TEST.d";
	
	@Test
	public void testValidateNonDataFileMustFail() {
		DataFileValidator dataFileValidatorTAFC = new DataFileValidator(PackageTypeEnum.TAFC);
		DataFileValidator dataFileValidatorTAFJ = new DataFileValidator(PackageTypeEnum.TAFJ);
		
		File[] dataFiles = new File[] {
				new File("comp/abc.def"),
				new File("comp/abc"),
				new File("comp/abc.def.d"),
				new File("comp/abc.d"),
				new File("abc.def"),
				new File("abc"),
				new File("abc.def.d"),
				new File("abc.d"),
				new File(""),
		};
		for (File dataFile : dataFiles) {
			try {
				dataFileValidatorTAFC.validate(dataFile);
				throw new RuntimeException("The validation should fails as the data file (" + dataFile.getAbsolutePath() + ") name is invalid.");
			} catch (DataValidationException e) {
				// This is expected
			}
			try {
				dataFileValidatorTAFJ.validate(dataFile);
				throw new RuntimeException("The validation should fails as the data file (" + dataFile.getAbsolutePath() + ") name is invalid.");
			} catch (DataValidationException e) {
				// This is expected
			}
		}
	}

	@Test
	public void testValidateValidTAFCDataFile() throws DataValidationException {
		DataFileValidator dataFileValidatorTAFC = new DataFileValidator(PackageTypeEnum.TAFC);
		File tafcDataFile = getDataFile(TAFC_DATA_FILE);
		dataFileValidatorTAFC.validate(tafcDataFile);
	}

	@Test
	public void testValidateValidTAFJDataFile() throws DataValidationException {
		DataFileValidator dataFileValidatorTAFJ = new DataFileValidator(PackageTypeEnum.TAFJ);
		File tafjDataFile = getDataFile(TAFJ_DATA_FILE);
		dataFileValidatorTAFJ.validate(tafjDataFile);
	}

	@Test(expected=DataValidationException.class)
	public void testValidateInvalidTAFJDataFile() throws DataValidationException {
		DataFileValidator dataFileValidatorTAFJ = new DataFileValidator(PackageTypeEnum.TAFJ);
		File tafcDataFile = getDataFile(TAFC_DATA_FILE);
		dataFileValidatorTAFJ.validate(tafcDataFile);
	}

	@Test(expected=DataValidationException.class)
	public void testValidateInvalidTAFCDataFile() throws DataValidationException {
		DataFileValidator dataFileValidatorTAFC = new DataFileValidator(PackageTypeEnum.TAFC);
		File tafjDataFile = getDataFile(TAFJ_DATA_FILE);
		dataFileValidatorTAFC.validate(tafjDataFile);
	}
	
	@Test
	public void testValidateNeutralDataFileForTAFC() throws DataValidationException {
		File neutralDataFile = getDataFile(NEUTRAL_DATA_FILE);
		DataFileValidator dataFileValidatorTAFC = new DataFileValidator(PackageTypeEnum.TAFC);
		dataFileValidatorTAFC.validate(neutralDataFile);
	}
	
	@Test
	public void testValidateNeutralDataFileForTAFJ() throws DataValidationException {
		File neutralDataFile = getDataFile(NEUTRAL_DATA_FILE);
		DataFileValidator dataFileValidatorTAFJ = new DataFileValidator(PackageTypeEnum.TAFJ);
		dataFileValidatorTAFJ.validate(neutralDataFile);
	}
	
	private File getDataFile(String filename) {
		URL resource = getClass().getResource("/" + filename);
		try {
			return new File(resource.toURI());
		} catch (URISyntaxException e) {
			throw new RuntimeException(e);
		}
	}

}
