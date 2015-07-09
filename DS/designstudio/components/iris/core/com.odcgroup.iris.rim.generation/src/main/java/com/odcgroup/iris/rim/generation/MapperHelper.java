package com.odcgroup.iris.rim.generation;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;

import com.odcgroup.iris.rim.generation.mappers.LightweightPatternCOS2ResourceMapper;

/**
 * The <code>MapperHelper</code> interface defines the subset of methods that {@link T24ResourceModelsGenerator} makes available as "helpers" for "mapper" classes such as
 * {@link LightweightPatternCOS2ResourceMapper}.
 *
 * @author Simon Hayes
 */
public interface MapperHelper {
    public ResourceSet getResourceSet();
    
	public void generateJava(Resource rimResource) throws Exception;
}
