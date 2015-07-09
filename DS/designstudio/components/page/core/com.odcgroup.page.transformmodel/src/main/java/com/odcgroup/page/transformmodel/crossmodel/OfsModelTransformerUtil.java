package com.odcgroup.page.transformmodel.crossmodel;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.util.URI;

import com.odcgroup.page.common.PageConstants;
import com.odcgroup.page.log.Logger;
import com.odcgroup.workbench.core.IOfsModelResource;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.core.OfsCore;
import com.odcgroup.workbench.core.repository.ModelNotFoundException;
import com.odcgroup.workbench.core.repository.ModelURIConverter;

/**
 * Cross ofsModel Transformer Util
 *
 * @author pkk
 *
 */
public class OfsModelTransformerUtil {
	
	/**
	 * @param value 
	 * @return boolean
	 */
	public static boolean isCrossModelResourceUri(String value) {
		if (!StringUtils.isEmpty(value)) {
			URI uri = URI.createURI(value);
			String extn = uri.fileExtension();
			String scheme = uri.scheme();
			if (scheme != null && scheme.equals(ModelURIConverter.SCHEME) 
					&& !extn.equals(PageConstants.MODULE_FILE_EXTENSION)
					&& !extn.equals(PageConstants.PAGE_FILE_EXTENSION)
					&& ! extn.equals(PageConstants.FRAGMENT_FILE_EXTENSION)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * @param value
	 * @param project
	 * @return string
	 */
	public static String transform(String value, IProject project) {
		if (isCrossModelResourceUri(value)) {
			URI uri = URI.createURI(value);
			IOfsModelResource modelResource;
			try {
				IOfsProject ofsProject = OfsCore.getOfsProjectManager().getOfsProject(project);
				modelResource = ofsProject.getOfsModelResource(uri);
				IOfsModelTransformer transformer = OfsModelTransformerFactory.getInstance().getTransformer(modelResource);
				value = transformer.transform(modelResource);
			} catch (ModelNotFoundException e) {
				Logger.error("Model not found for URI \'"+uri+"\'", e);
			}
		} 
		return value;
	}

}
