package com.odcgroup.page.transformmodel.crossmodel;

import com.odcgroup.workbench.core.IOfsModelResource;

/**
 * basic URI Transformation Util, which does no transformation
 * but just return the string form of URI
 *
 * @author pkk
 *
 */
public class BasicTransformer implements IOfsModelTransformer {

	/**
	 * (non-Javadoc)
	 * @see com.odcgroup.page.transformmodel.crossmodel.IOfsModelTransformer#transform(com.odcgroup.workbench.core.IOfsModelResource)
	 */
	@Override
	public String transform(IOfsModelResource modelResource) {
		if (modelResource == null)
			return "";
		else
			return modelResource.getURI().toString();
	}

	

}
