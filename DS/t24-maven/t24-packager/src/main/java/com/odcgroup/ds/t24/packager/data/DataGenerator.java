package com.odcgroup.ds.t24.packager.data;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;
import org.apache.commons.io.FileUtils;

import com.odcgroup.ds.t24.packager.data.validator.DataHeaderValidator;
import com.odcgroup.ds.t24.packager.generator.T24Packager;
import com.odcgroup.ds.t24.packager.generator.T24PackagerException;
import com.odcgroup.ds.t24.packager.helper.TarHelper;
import com.odcgroup.ds.t24.packager.writer.DataHeaderWriter;

public class DataGenerator {

	private T24Packager packager;
	private DataHeaderValidator validator;
	
	private List<String> dataFileNames = new ArrayList<String>();
	
	public void addDataFile(String fileName) throws InvalidDataFilenameException, TooMuchDataFilesException {
		checkDataFilename(fileName);
		if (dataFileNames.size() >= 999) {
			throw new TooMuchDataFilesException("The maximum number of records in a package is 999");
		}
		dataFileNames.add(fileName);
	}
	
	public void processDataFiles(TarArchiveOutputStream tarStream) throws IOException, T24PackagerException {
		if (dataFileNames.size() == 0) {
			// Nothing to do if no data files are provided
			return;
		}

		// Generate the header
		byte[] headerContents = processDataFiles();
		
		// Add it to the tar file
		TarHelper.addContents(tarStream, headerContents, packager.getPackageRoot() + "/" + getDataHeaderFilename());
	}
	
	public void processDataFiles(File component) throws IOException, T24PackagerException {
		// Generate the header
		byte[] headerContents = processDataFiles();

		FileUtils.writeByteArrayToFile(new File(component, getDataHeaderFilename()), headerContents);
	}

	public byte[] processDataFiles() throws IOException, T24PackagerException {
		
		// Generate data header
		DataHeader header = new DataHeader("DS", "DS", "");
		List<Cd> cds = new ArrayList<Cd>();
		header.setCds(cds);
		Cd cd = new Cd();
		cd.setNumber(0);
		cds.add(cd);
		for (String dataFileName : dataFileNames) {
			Record record = new Record();
			record.setFilename(DataHelper.getFilename(dataFileName));
			record.setName(DataHelper.getName(dataFileName));
			cd.getRecords().add(record);
		}
		
		// Validate the generated data header
		validator.validate(header);
		
		DataHeaderWriter writer = new DataHeaderWriter(packager.getType());
		byte[] dataHeader = writer.write(header);
		
		// Clear the processed files
		dataFileNames.clear();
		
		return dataHeader;
	}

	private void checkDataFilename(String fileName) throws InvalidDataFilenameException {
		DataHelper.getFilename(fileName);
		DataHelper.getName(fileName);
	}
	
	public String getDataHeaderFilename() {
		return packager.getRelease() + T24Packager.SEPARATOR + packager.getComponentName() + T24Packager.SEPARATOR + packager.getRev();
	}

	public void setPackager(T24Packager packager) {
		this.packager = packager;
	}

	public DataHeaderValidator getValidator() {
		return validator;
	}

	public void setValidator(DataHeaderValidator validator) {
		this.validator = validator;
	}

}
