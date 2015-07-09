package com.odcgroup.iris.importer;

import org.eclipse.core.resources.IFolder;

import com.odcgroup.t24.common.importer.IModelDetail;
import com.odcgroup.t24.common.importer.internal.ModelInfo;
import com.odcgroup.t24.server.external.model.InteractionDetail;

public class InteractionInfo extends ModelInfo<InteractionDetail> implements IModelDetail, Comparable<InteractionInfo> {

	private InteractionDetail detail;
	
	protected InteractionInfo(InteractionDetail detail) {
		super(detail);
		this.detail = detail;
	}

	@Override
	public int compareTo(InteractionInfo o) {
		return getModelName().compareTo(o.getModelName());
	}

	@Override
	public String getModelType() {
		return "Rim";
	}

	@Override
	public String getDescription() {
		return detail.getName();
	}

	@Override
	public String getModelName() {
		return detail.getName();
	}

	@Override
	public String getFullModelName(IFolder destinationFolder) {
		return "";
	}

	@Override
	public String getXMLFilename() {
		return "";
	}

}
