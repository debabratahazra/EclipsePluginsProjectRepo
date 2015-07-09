package com.odcgroup.workbench.generation.cartridge.ng.di;

import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;

import com.google.inject.AbstractModule;
import com.google.inject.Injector;
import com.google.inject.Provider;
import com.google.inject.TypeLiteral;
import com.odcgroup.workbench.core.di.ILangSpecificProvider;
import com.odcgroup.workbench.core.di.StandaloneHeadlessStatus;
import com.odcgroup.workbench.core.xtext.XtextProxyUtil;
import com.odcgroup.workbench.generation.cartridge.ng.ResourceGenerator2;

public class GenerationRuntimeModule extends AbstractModule {
	// Maybe later, when I better understand the advantage, switch this to extends Xtext's AbstractGenericModule instead of Guice's AbstractModule 
	
	@Override
	protected void configure() {
		 // use of 'this' as last arg here is :(
		bind(new TypeLiteral<ILangSpecificProvider<ResourceGenerator2>>() {})
			.toInstance(new ILangSpecificProviderImplPlusGeneration<ResourceGenerator2>(ResourceGenerator2.class, this));
		bind(new TypeLiteral<ILangSpecificProvider<Injector>>() {})
			.toInstance(new ILangSpecificProviderImplPlusGeneration<Injector>(Injector.class, this));
		bind(new TypeLiteral<ILangSpecificProvider<XtextProxyUtil>>() {})
			.toInstance(new ILangSpecificProviderImplPlusGeneration<XtextProxyUtil>(XtextProxyUtil.class, this));
		
		if (StandaloneHeadlessStatus.INSTANCE.isInStandaloneHeadless()) {
			bind(IWorkspace.class).toProvider(new Provider<IWorkspace>() {
				public IWorkspace get() {
					return ResourcesPlugin.getWorkspace();
				}
			});
		}
	}

/*  If we ever need to @InjectWith @RunWith(XtextRunner.class) tests with this configuration, this could be useful
 
    public class GenerationInjectorProvider implements IInjectorProvider {
		@Override public Injector getInjector() {
			return Guice.createInjector(new GenerationRuntimeModule());
		}
	}
 */
}