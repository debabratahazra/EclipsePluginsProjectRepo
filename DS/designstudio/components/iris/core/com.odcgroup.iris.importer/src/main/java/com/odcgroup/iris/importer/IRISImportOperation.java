package com.odcgroup.iris.importer;

import java.util.ArrayList;
import java.util.List;

import com.odcgroup.t24.common.importer.IImportModelReport;
import com.odcgroup.t24.common.importer.IImportOperation;
import com.odcgroup.t24.common.importer.IImportingStep;
import com.odcgroup.t24.common.importer.internal.T24ModelImportOperation;
import com.odcgroup.t24.server.external.model.IExternalLoader;
import com.odcgroup.t24.server.external.model.InteractionDetail;
import com.odcgroup.t24.server.external.model.internal.IInteractionLoader;

public class IRISImportOperation extends T24ModelImportOperation<InteractionInfo, InteractionDetail> implements IImportOperation {
	
	private IInteractionLoader loader;
	
	private String modelName;
	
	protected IImportingStep<InteractionInfo> getFinalStep(IImportModelReport report) {
		return new SaveInteractionInfo<InteractionInfo>(report, this.loader, getContainer(), this.modelName);
	}
	
	@Override
	protected List<IImportingStep<InteractionInfo>> getImportingSteps(IImportModelReport report) {
		List<IImportingStep<InteractionInfo>> steps = new ArrayList<IImportingStep<InteractionInfo>>();
		steps.add(new LoadInteractionInfo(report, this.loader));
		return steps;
	}
	
	@Override
	protected InteractionInfo makeModelInfoFromDetail(InteractionDetail detail) {
		return new InteractionInfo(detail);
	}

	@Override
	protected String getImportMessage() {
		return "Processing T24 Resources Import";
	}
	
	public final void setExternalLoader(IExternalLoader loader) {
		this.loader = null;
		if (loader instanceof IInteractionLoader) {
			this.loader = (IInteractionLoader)loader;
		} else {
			throw new IllegalArgumentException("The loader must implement the interface "+IInteractionLoader.class.getCanonicalName());
		}
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}
	
	public String getModelName() {
		return this.modelName;
	}

}
