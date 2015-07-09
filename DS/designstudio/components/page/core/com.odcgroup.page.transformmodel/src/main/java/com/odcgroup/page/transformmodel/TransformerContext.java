package com.odcgroup.page.transformmodel;

import com.odcgroup.page.metamodel.MetaModel;

/**
 * The TransformerContext is used during the transformation process
 * to pass information between the different Transformers.
 * 
 * @author Gary Hayes
 */
public interface TransformerContext {	
	
	/**
	 * Gets the MetaModel used during this transformation process.
	 * 
	 * @return MetaModel The MetaModel
	 */
	public MetaModel getMetaModel();	
	
	/**
	 * Gets the TransformModel used during this transformation process.
	 * 
	 * @return TransformModel The TransformModel
	 */
	public TransformModel getTransformModel();

}
