package com.odcgroup.t24.application.localrefapplication.importer;

import org.eclipse.core.resources.IFolder;

import com.odcgroup.t24.common.importer.IModelDetail;
import com.odcgroup.t24.common.importer.internal.ModelInfo;
import com.odcgroup.t24.server.external.model.LocalRefApplicationDetail;

/**
 * TODO: Document me!
 *
 * @author hdebabrata
 *
 */
public class LocalRefApplicationInfo extends ModelInfo<LocalRefApplicationDetail> implements IModelDetail, Comparable<LocalRefApplicationInfo> {

	protected LocalRefApplicationInfo(LocalRefApplicationDetail detail) {
		super(detail);
	}

	@Override
	public String getModelType() {
		return "domain";
	}

	@Override
	public String getDescription() {
		return getDetail().getDescription();
	}

	@Override
	public String getModelName() {
		// TODO - Check the Model name properly
		String modelName = getDetail().getName();
		return modelName.replaceAll("/","_");
	}

	@Override
	public String getFullModelName(IFolder destinationFolder) {
		// TODO - check full path
		StringBuffer buff = new StringBuffer();
		buff.append(destinationFolder.getFullPath().toString());
		buff.append("/");
		buff.append(getModelName());
		buff.append(".domain");
		return buff.toString();
	}

	@Override
	public String getXMLFilename() {
		return "X_" + getDetail().getName().replace(".", "_");
	}

	@Override
	public int compareTo(LocalRefApplicationInfo o) {
		return getModelName().compareTo(o.getModelName());
	}
}
