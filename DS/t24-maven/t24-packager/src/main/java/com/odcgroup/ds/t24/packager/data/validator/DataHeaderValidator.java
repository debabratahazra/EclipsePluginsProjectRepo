package com.odcgroup.ds.t24.packager.data.validator;

import java.util.LinkedHashSet;

import com.odcgroup.ds.t24.packager.data.Cd;
import com.odcgroup.ds.t24.packager.data.DataHeader;
import com.odcgroup.ds.t24.packager.data.Record;

public class DataHeaderValidator {

	public void validate(DataHeader header) throws DataValidationException {
		LinkedHashSet<String> errors = new LinkedHashSet<String>();
		if (header.getCds().size() == 0) {
			errors.add("No CD defined.");
		} else {
			for (Cd cd : header.getCds()) {
				if (cd.getNumber() == null) {
					errors.add("The number field of CD must be defined.");
				}
				for (Record record : cd.getRecords()) {
					if (isEmpty(record.getFilename())) {
						errors.add("Record must have a filename defined.");
					}
					if (isEmpty(record.getName())) {
						errors.add("Record must have a filename defined.");
					}
				}
			}
		}
		if (errors.size() > 1) {
			StringBuffer result = new StringBuffer();
			result.append("There are several validation errors:\n");
			for (String error : errors) {
				result.append(error);
				result.append("\n");
			}
			throw new DataValidationException(result.toString());
		} else if (errors.size() == 1) {
			throw new DataValidationException(errors.iterator().next());
		} 
	}

	private boolean isEmpty(String filename) {
		return filename == null || filename.trim().isEmpty();
	}

}
