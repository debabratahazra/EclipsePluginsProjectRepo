package com.temenos.ds.t24.data.rps.impl.factory;

import java.io.Closeable;

import com.temenos.services.resourceprovider.ResourceProviderServiceProviderAPI;

/**
 * Factory for ResourceProviderServiceProviderAPI.
 *
 * @author Michael Vorburger
 */
public interface ResourceProviderServiceProviderAPIFactory extends Closeable {

	ResourceProviderServiceProviderAPI getComponentFrameworkResourceProviderService();

}
