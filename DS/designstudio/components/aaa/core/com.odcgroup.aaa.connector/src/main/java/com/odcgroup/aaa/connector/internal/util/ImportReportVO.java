package com.odcgroup.aaa.connector.internal.util;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author yan
 */
public class ImportReportVO {

	private Map<String, String> errorMessagePerFormat = new LinkedHashMap<String, String>();
	
	private Map<String, List<String>> importedFormatsPerDomainFunction = new LinkedHashMap<String, List<String>>();
	
	private Map<String, List<String>> nonImportedFormatPerDomainFunction = new LinkedHashMap<String, List<String>>();

	public void copyFrom(ImportReportVO from) {
		this.errorMessagePerFormat.clear();
		this.importedFormatsPerDomainFunction.clear();
		this.nonImportedFormatPerDomainFunction.clear();
		
		this.errorMessagePerFormat.putAll(from.getErrorMessagePerFormat());
		this.importedFormatsPerDomainFunction.putAll(from.getImportedFormatsPerDomainFunction());
		this.nonImportedFormatPerDomainFunction.putAll(from.getNonImportedFormatPerDomainFunction());
	}
	
	/**
	 * @return the errorMessagePerFormat
	 */
	public Map<String, String> getErrorMessagePerFormat() {
		return errorMessagePerFormat;
	}
	
	public void addErrorMessage(String formatCode, String message) {
		errorMessagePerFormat.put(formatCode, message);
	}

	/**
	 * @return the importedFormatPerDomainFunction
	 */
	public Map<String, List<String>> getImportedFormatsPerDomainFunction() {
		return importedFormatsPerDomainFunction;
	}
	
	public void addImportedFormat(String domainFunction, String formatCode) {
		if (!importedFormatsPerDomainFunction.containsKey(domainFunction)) {
			importedFormatsPerDomainFunction.put(domainFunction, new LinkedList<String>());
		}
		importedFormatsPerDomainFunction.get(domainFunction).add(formatCode);
	}

	/**
	 * @return the nonImportedFormatPerDomainFunction
	 */
	public Map<String, List<String>> getNonImportedFormatPerDomainFunction() {
		return nonImportedFormatPerDomainFunction;
	}
	
	public void addNonImportedFormatPerDomainFunction(String domainFunction, String formatCode) {
		if (!nonImportedFormatPerDomainFunction.containsKey(domainFunction)) {
			nonImportedFormatPerDomainFunction.put(domainFunction, new LinkedList<String>());
		}
		nonImportedFormatPerDomainFunction.get(domainFunction).add(formatCode);
	}
	
	public int getNbSuccessfullyImportedFormats() {
		int result = 0;
		for (Map.Entry<String, List<String>> entry : importedFormatsPerDomainFunction.entrySet()) {
			result += entry.getValue().size();
		}
		return result;
	}

	public int getNbUnsuccessfullyImportedFormats() {
		return errorMessagePerFormat.size();
	}

}
