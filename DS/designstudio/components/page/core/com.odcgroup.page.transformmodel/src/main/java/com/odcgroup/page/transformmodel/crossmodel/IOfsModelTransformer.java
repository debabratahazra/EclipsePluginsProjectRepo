package com.odcgroup.page.transformmodel.crossmodel;

import com.odcgroup.workbench.core.IOfsModelResource;

/**
 * Cross Model Transformer - A Transformer that transforms cross-model 
 * references which require more insight into the model referenced. 
 *
 * @author pkk
 *
 */
public interface IOfsModelTransformer {
	
	
	/**
	 * transform the specified ofsModelResource for generation needs
	 * @param modelResource
	 * @return String
	 */
	public String transform(IOfsModelResource modelResource);

}
