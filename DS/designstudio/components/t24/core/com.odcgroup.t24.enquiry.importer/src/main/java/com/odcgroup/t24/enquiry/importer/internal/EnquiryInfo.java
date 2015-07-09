package com.odcgroup.t24.enquiry.importer.internal;

import org.eclipse.core.resources.IFolder;

import com.odcgroup.t24.common.importer.IModelDetail;
import com.odcgroup.t24.common.importer.internal.ModelInfo;
import com.odcgroup.t24.server.external.model.EnquiryDetail;

/**
 * @author atripod
 */
public class EnquiryInfo extends ModelInfo<EnquiryDetail> implements IModelDetail, Comparable<EnquiryInfo> {
	
	@Override
	public int compareTo(EnquiryInfo o) {
		return getModelName().compareTo(o.getModelName());
	}

	@Override
	public String getDescription() {
		StringBuffer buf = new StringBuffer();
		buf.append("["); //$NON-NLS-1$
		/*buf.append(getDetail().getApplication());
		buf.append(":"); //$NON-NLS-1$
*/		buf.append(getDetail().getName());
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
		
		buff.append(getModelName());
		buff.append(".enquiry"); //$NON-NLS-1$
		return buff.toString(); 
	}

	@Override
	public final String getModelName() {
		String modelName = getDetail().getName();  //$NON-NLS-1$
		return modelName.replaceAll("/","_");
	}
	
	@Override
	public String getModelType() {
		return "Enquiry";
	}
	
	@Override
	public String getXMLFilename() {
		StringBuffer buf = new StringBuffer(64);
		EnquiryDetail detail = getDetail();
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
	public EnquiryInfo(EnquiryDetail detail) {
		super(detail);
	}

}
