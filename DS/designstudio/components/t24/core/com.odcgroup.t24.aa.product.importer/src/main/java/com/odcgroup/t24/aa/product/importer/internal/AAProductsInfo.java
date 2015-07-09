package com.odcgroup.t24.aa.product.importer.internal;

import org.eclipse.core.resources.IFolder;
import org.eclipse.emf.ecore.EObject;

import com.odcgroup.t24.common.importer.IModelDetail;
import com.odcgroup.t24.common.importer.internal.ModelInfo;
import com.odcgroup.t24.server.external.model.AAProductsDetails;

/**
 * TODO: Document me!
 *
 * @author hdebabrata
 *
 */
public class AAProductsInfo extends ModelInfo<AAProductsDetails> implements IModelDetail, Comparable<AAProductsInfo> {

	/**
	 * @param detail
	 */
	protected AAProductsInfo(AAProductsDetails detail) {
		super(detail);
	}

	@Override
	public EObject getModel() {
		return null;
	}

	@Override
	public String getModelType() {
		return getModelName();
	}

	@Override
	public String getDescription() {
		return getDetail().getDescription();
	}

	@Override
	public String getModelName() {
		String modelName = getDetail().getName();  //$NON-NLS-1$
		return modelName.replaceAll("\\.","_");
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
	public String getXMLFilename() {
		return "";
	}

	@Override
	public int compareTo(AAProductsInfo aaProductsInfo) {
		return getModelName().compareTo(aaProductsInfo.getModelName());
	}
}
