package com.temenos.ds.t24.data.rps.impl.factory;

import java.io.IOException;

import com.temenos.ds.t24.data.rps.RemoteServiceException;
import com.temenos.ds.t24.data.rps.connection.T24JAgentConnectionParameters;
import com.temenos.interaction.commands.jencks.JencksJcaContainer;
import com.temenos.interaction.commands.jencks.TafcConfiguration;
import com.temenos.services.resourceprovider.ResourceProviderServiceProviderAPI;
import com.temenos.services.resourceprovider.ResourceProviderServiceSpringContext;


/**
 * ResourceProviderServiceProviderAPIFactory implementation for TAFC,
 * based on (a static, urgh!) Jencks/Spring Framework etc.
 * This is ugly, not done well in the Component Framework.
 *
 * @author Michael Vorburger
 */
public class JAgentSpringJencksResourceProviderServiceProviderAPIFactory implements ResourceProviderServiceProviderAPIFactory {

	private JencksJcaContainer jcaContainer;
	private ResourceProviderServiceProviderAPI cfService;

	public JAgentSpringJencksResourceProviderServiceProviderAPIFactory(T24JAgentConnectionParameters properties) throws RemoteServiceException {
		TafcConfiguration tafcConfig = new TafcConfiguration();
		tafcConfig.setHosts(properties.jAgentHostname);
		tafcConfig.setPorts(Integer.toString(properties.jAgentPort));
		tafcConfig.setOfsSource(properties.ofsSource);

		tafcConfig.setActionTimeout("30");
		tafcConfig.setAllowInput("false");

		jcaContainer = new JencksJcaContainer(tafcConfig);
		jcaContainer.load(tafcConfig);

		try {
			ResourceProviderServiceSpringContext.loadServiceContext(this.getClass().getClassLoader());
		} catch (Exception e) {
			throw new RemoteServiceException("Unable to loadServiceContext", e);
		}

		try {
			cfService = (ResourceProviderServiceProviderAPI) ResourceProviderServiceSpringContext
					.getContext().getBean(ResourceProviderServiceProviderAPI.class); // better than "ResourceProviderServiceProvider"
		} catch (Exception e) {
			throw new RemoteServiceException("Unable to get ResourceProviderServiceProviderAPI bean", e);
		} 
	}

	@Override
	public ResourceProviderServiceProviderAPI getComponentFrameworkResourceProviderService() {
		if (cfService == null)
			throw new IllegalStateException();
		return cfService;
	}

	@Override
	public void close() throws IOException {
		cfService = null;
		jcaContainer.unload();
		jcaContainer = null;
	}

}
