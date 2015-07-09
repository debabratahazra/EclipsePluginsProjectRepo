package com.odcgroup.t24.application.localref.importer;

import org.eclipse.core.resources.IFolder;

import com.odcgroup.t24.common.importer.IModelDetail;
import com.odcgroup.t24.common.importer.internal.ModelInfo;
import com.odcgroup.t24.server.external.model.LocalRefDetail;

/**
 * @author ssreekanth
 */
public class LocalRefInfo extends ModelInfo<LocalRefDetail> implements IModelDetail, Comparable<LocalRefInfo> {

	@Override
	public int compareTo(LocalRefInfo o) {
		return getModelName().compareTo(o.getModelName());
	}
	
	@Override
	public final String getDescription() {
		return getDetail().getDescription();
	}

	@Override
	public String getFullModelName(IFolder destinationFolder) {
		StringBuffer buff = new StringBuffer();
		buff.append(destinationFolder.getFullPath().toString());
		buff.append("/"); //$NON-NLS-1$
		buff.append(getModelName());
		buff.append(".domain"); //$NON-NLS-1$
		return buff.toString(); 
	}

	@Override
	public final String getModelName() {
		String modelName = getDetail().getName();  //$NON-NLS-1$
		return modelName.replaceAll("/","_");
	}
	

	@Override
	public String getModelType() {
		return "LocalRef";
	}

	@Override
	public String getXMLFilename() {
		return getDetail().getName();
	}

	/**
	 * @param detail
	 */
	public LocalRefInfo(LocalRefDetail detail) {
		super(detail);
	}

}
