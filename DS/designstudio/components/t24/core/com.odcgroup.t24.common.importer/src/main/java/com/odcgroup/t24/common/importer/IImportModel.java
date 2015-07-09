package com.odcgroup.t24.common.importer;

import org.eclipse.core.runtime.IProgressMonitor;

import com.odcgroup.t24.server.external.model.internal.T24ServerException;

/**
 * @author atripod
 */
public interface IImportModel {
	
	/**
	 * @return
	 */
	public String getModelName();

	/**
	 * @param monitor
	 * @return
	 * @throws T24ServerException 
	 */
	void importModels(IProgressMonitor monitor) throws T24ServerException;
	
	/**
	 * @return
	 */
	IImportModelReport getImportReport();

}
