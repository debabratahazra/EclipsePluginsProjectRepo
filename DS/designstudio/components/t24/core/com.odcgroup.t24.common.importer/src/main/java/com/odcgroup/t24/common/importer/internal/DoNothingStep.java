package com.odcgroup.t24.common.importer.internal;

import org.eclipse.core.runtime.IProgressMonitor;

import com.odcgroup.t24.common.importer.IImportingStep;
import com.odcgroup.t24.common.importer.IModelDetail;

public class DoNothingStep<T extends IModelDetail> implements IImportingStep<T> {

	@Override
	public boolean execute(T model, IProgressMonitor monitor) {
		return true;
	}

}
