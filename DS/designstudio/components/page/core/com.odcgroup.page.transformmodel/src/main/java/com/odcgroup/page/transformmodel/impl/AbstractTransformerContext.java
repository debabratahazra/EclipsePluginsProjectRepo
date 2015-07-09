package com.odcgroup.page.transformmodel.impl;

import com.odcgroup.page.metamodel.MetaModel;
import com.odcgroup.page.transformmodel.TransformModel;
import com.odcgroup.page.transformmodel.TransformerContext;

/**
 * The TransformerContext is used during the transformation process
 * to pass information between the different Transformers.
 * 
 * @author Gary Hayes
 */
abstract public class AbstractTransformerContext implements TransformerContext {
	
	/** The TransformModel. */
	private TransformModel transformModel;
	
	/** The MetaModel. */
	private MetaModel metaModel;	
	
	/**
	 * Creates a new AbstractTransformerContext.
	 * 
	 * @param metaModel The MetaModel to use
	 * @param transformModel The TransformModel to use
	 */
	public AbstractTransformerContext(MetaModel metaModel, TransformModel transformModel) {
		this.metaModel = metaModel;
		this.transformModel = transformModel;
	}
	
	/**
	 * Gets the MetaModel used during this transformation process.
	 * 
	 * @return MetaModel The MetaModel
	 */
	public MetaModel getMetaModel() {
		return metaModel;
	}
	
	/**
	 * Gets the TransformModel used during this transformation process.
	 * 
	 * @return TransformModel The TransformModel
	 */
	public TransformModel getTransformModel() {
		return transformModel;
	}
}