package com.odcgroup.t24.version;

import org.eclipse.xtext.conversion.IValueConverterService;
import org.eclipse.xtext.naming.IQualifiedNameConverter;
import org.eclipse.xtext.resource.IDefaultResourceDescriptionStrategy;
import org.eclipse.xtext.resource.IResourceServiceProvider;
import org.eclipse.xtext.scoping.IGlobalScopeProvider;
import org.eclipse.xtext.serializer.tokens.ICrossReferenceSerializer;
import org.eclipse.xtext.service.SingletonBinding;

import com.odcgroup.t24.version.linking.VersionCrossReferenceSerializer;
import com.odcgroup.t24.version.naming.VersionNameConverter;
import com.odcgroup.t24.version.naming.VersionNameProvider;
import com.odcgroup.t24.version.resource.VersionResourceDescriptionStrategy;
import com.odcgroup.t24.version.scoping.VersionGlobalScopeProvider;
import com.odcgroup.t24.version.valueconverter.VersionValueConverterService;
import com.odcgroup.workbench.dsl.resources.CommonDSLResourceServiceProvider;

/**
 * Use this class to register components to be used at runtime / without the
 * Equinox extension registry.
 */
@SuppressWarnings("restriction")
public class VersionDSLRuntimeModule extends AbstractVersionDSLRuntimeModule {

    public Class<? extends IQualifiedNameConverter> bindIQualifiedNameConverter() {
    	return VersionNameConverter.class;
    }

    public Class<? extends org.eclipse.xtext.naming.IQualifiedNameProvider> bindIQualifiedNameProvider() {
    	return VersionNameProvider.class;
    }

    public Class<? extends ICrossReferenceSerializer> bindCrossReferenceSerializer() {
    	return VersionCrossReferenceSerializer.class;		
    }
	
	@Override
	@SingletonBinding
	public Class<? extends IGlobalScopeProvider> bindIGlobalScopeProvider() {
		return VersionGlobalScopeProvider.class;
	}
	
	public Class<? extends IResourceServiceProvider> bindIResourceServiceProvider() {
		return CommonDSLResourceServiceProvider.class;
	}
	
	public Class<? extends IDefaultResourceDescriptionStrategy> bindIDefaultResourceDescriptionStrategy() {
		return VersionResourceDescriptionStrategy.class;
	}
	
	@Override
	public Class<? extends IValueConverterService> bindIValueConverterService() {
		return VersionValueConverterService.class;
	}

}
