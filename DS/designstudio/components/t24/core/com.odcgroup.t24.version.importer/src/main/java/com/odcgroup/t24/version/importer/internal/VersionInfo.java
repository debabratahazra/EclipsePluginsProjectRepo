package com.odcgroup.t24.version.importer.internal;

import org.eclipse.core.resources.IFolder;

import com.odcgroup.t24.common.importer.IModelDetail;
import com.odcgroup.t24.common.importer.internal.ModelInfo;
import com.odcgroup.t24.server.external.model.VersionDetail;

/**
 * @author atripod
 */
public class VersionInfo extends ModelInfo<VersionDetail> implements IModelDetail, Comparable<VersionInfo> {

	@Override
	public int compareTo(VersionInfo o) {
		return getModelName().compareTo(o.getModelName());
	}
	
	@Override
	public String getModelType() {
		return "Screen";
	}
	
	@Override
	public String getDescription() {
		StringBuffer buf = new StringBuffer();
		buf.append("["); //$NON-NLS-1$
		buf.append(getDetail().getApplication());
		buf.append(":"); //$NON-NLS-1$
		buf.append(getDetail().getName());
		buf.append("]"); //$NON-NLS-1$
		buf.append(" Module:["); //$NON-NLS-1$
		buf.append(getDetail().getModule());
		buf.append("]"); //$NON-NLS-1$
		buf.append(" Component:["); //$NON-NLS-1$
		buf.append(getDetail().getComponent());
		buf.append("]"); //$NON-NLS-1$
		return buf.toString();
	}

	@Override
	public String getFullModelName(IFolder destinationFolder) {
		StringBuffer buff = new StringBuffer();
		buff.append(destinationFolder.getFullPath().toString());
		buff.append("/"); //$NON-NLS-1$
		
		buff.append(getModelName().replace(".", "_"));
		buff.append(".version"); //$NON-NLS-1$
		return buff.toString(); 
	}

	@Override
	public final String getModelName() {
		String modelName = getDetail().getApplication()+","+getDetail().getName();  //$NON-NLS-1$
		modelName = modelName.replaceAll("[$%/*!#]", "_");
		return modelName; //$NON-NLS-1$
	}

	@Override
	public String getXMLFilename() {
		StringBuffer buf = new StringBuffer(64);
		VersionDetail detail = getDetail();
		buf.append(detail.getApplication());
		buf.append('-');
		buf.append(detail.getComponent());
		buf.append('-');
		buf.append(detail.getModule());
		buf.append('-');
		buf.append(detail.getName());
		return buf.toString();
	}
	
	/**
	 * @param detail
	 */
	public VersionInfo(VersionDetail detail) {
		super(detail);
	}

}
