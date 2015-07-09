package com.odcgroup.t24.common.importer;

import org.eclipse.core.runtime.IProgressMonitor;

public interface IImportingStep<T extends IModelDetail> {
	
	/**
	 * @param model
	 * @param monitor
	 * @param report
	 * @return <tt>true</tt> if this step is executed without error
	 */
	boolean execute(T model, IProgressMonitor monitor);
	
}
