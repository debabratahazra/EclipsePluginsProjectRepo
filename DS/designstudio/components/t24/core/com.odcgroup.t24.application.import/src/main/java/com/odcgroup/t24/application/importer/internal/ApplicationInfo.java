package com.odcgroup.t24.application.importer.internal;

import org.eclipse.core.resources.IFolder;

import com.odcgroup.t24.common.importer.IModelDetail;
import com.odcgroup.t24.common.importer.internal.ModelInfo;
import com.odcgroup.t24.server.external.model.ApplicationDetail;

/**
 * @author atripod
 */
public class ApplicationInfo extends ModelInfo<ApplicationDetail> implements IModelDetail, Comparable<ApplicationInfo> {

	@Override
	public int compareTo(ApplicationInfo o) {
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
		String modelName = getDetail().getProduct()+"_"+getDetail().getName();  //$NON-NLS-1$
		return modelName.replaceAll("/","_");
	}
	

	@Override
	public String getModelType() {
		return "Application";
	}

	@Override
	public String getXMLFilename() {
		StringBuffer buf = new StringBuffer(64);
		ApplicationDetail detail = getDetail();
		buf.append(detail.getProduct());
		buf.append('-');
		buf.append(detail.getComponent());
		buf.append('-');
		buf.append(detail.getName());
		return buf.toString();
	}

	/**
	 * @param detail
	 */
	public ApplicationInfo(ApplicationDetail detail) {
		super(detail);
	}

	
	public final String getApplicationDescription() {
		return getDetail().getApplicaitonDescription();
	}
}
