package com.odcgroup.t24.common.importer;

public interface IImportModelReport {
	
	/**
	 * @return
	 */
	int modelCount();
	
	/**
	 * @return
	 */
	String getSummaryMessage();

	/**
	 * @return
	 */
	String getErrors();
	
	/**
	 * @return
	 */
	String getSuccess();

	/**
	 * @return the number of errors
	 */
	boolean hasErrors();
	
	/**
	 * @param ex
	 */
	void error(Exception ex);

	/**
	 * @param text
	 */
	void error(String text);

	/**
	 * @param model
	 * @param ex
	 */
	void error(IModelDetail model, Exception ex);
	
	/**
	 * @param model
	 * @param text
	 */
	void error(IModelDetail model, String text);
	
	 /**
	 * @param model
	 */
	void add(IModelDetail model);
	
}
