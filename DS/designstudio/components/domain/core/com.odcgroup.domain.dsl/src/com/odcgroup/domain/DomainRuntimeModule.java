package com.odcgroup.domain;

import org.eclipse.xtext.common.types.access.ClasspathTypeProviderFactory;
import org.eclipse.xtext.conversion.IValueConverterService;
import org.eclipse.xtext.naming.IQualifiedNameConverter;
import org.eclipse.xtext.naming.IQualifiedNameProvider;
import org.eclipse.xtext.parser.IEncodingProvider;
import org.eclipse.xtext.resource.DerivedStateAwareResourceDescriptionManager;
import org.eclipse.xtext.resource.IDefaultResourceDescriptionStrategy;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.IResourceServiceProvider;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.scoping.IGlobalScopeProvider;
import org.eclipse.xtext.service.SingletonBinding;
import org.eclipse.xtext.validation.IResourceValidator;
import org.eclipse.xtext.xbase.jvmmodel.IJvmModelInferrer;

import com.google.inject.Provider;
import com.odcgroup.domain.derived.DomainJvmModelInferrer;
import com.odcgroup.domain.linking.DomainCrossReferenceSerializerNew;
import com.odcgroup.domain.resource.DomainDSLResource;
import com.odcgroup.domain.resource.DomainResourceDescriptionStrategy;
import com.odcgroup.domain.scoping.DomainGlobalScopeProvider;
import com.odcgroup.domain.scoping.DomainQualifiedNameConverter;
import com.odcgroup.domain.scoping.DomainQualifiedNameProvider;
import com.odcgroup.domain.values.DomainValueConverterService;
import com.odcgroup.workbench.dsl.resources.CommonDSLResourceServiceProvider;
import com.odcgroup.workbench.dsl.resources.DSLEncodingProviderProvider;

/**
 * Use this class to register components to be used at runtime / without the
 * Equinox extension registry.
 * 
 * DS-3929: We add "@SingletonBinding" to some bindings to reduce memory consumption
 */
@SuppressWarnings("restriction")
public class DomainRuntimeModule extends com.odcgroup.domain.AbstractDomainRuntimeModule {

	@Override
	@SingletonBinding
	public Class<? extends IGlobalScopeProvider> bindIGlobalScopeProvider() {
		return DomainGlobalScopeProvider.class;
	}

	@Override
	@SingletonBinding
	public Class<? extends IValueConverterService> bindIValueConverterService() {
		return DomainValueConverterService.class;
	}

	@Override
	@SingletonBinding
	public Class<? extends IQualifiedNameProvider> bindIQualifiedNameProvider() {
		return DomainQualifiedNameProvider.class;
	}
	
	@SingletonBinding
	public Class<? extends IQualifiedNameConverter> bindIQualifiedNameConverter() {
		return DomainQualifiedNameConverter.class;
	}

	@Override
	public Class<? extends XtextResource> bindXtextResource() {
		return DomainDSLResource.class;
	}
	
	@Override
	public Class<? extends Provider<IEncodingProvider>> provideIEncodingProvider() {
	    return DSLEncodingProviderProvider.class;
	}
	
	public Class<? extends org.eclipse.xtext.serializer.tokens.ICrossReferenceSerializer> bindNewICrossReferenceSerializer() {
	    return DomainCrossReferenceSerializerNew.class;
	}
	
	public Class<? extends IDefaultResourceDescriptionStrategy> bindIDefaultResourceDescriptionStrategy() {
		return DomainResourceDescriptionStrategy.class;
	}
	
	public Class<? extends IResourceServiceProvider> bindIResourceServiceProvider() {
		return CommonDSLResourceServiceProvider.class;
	}
	
	public Class<? extends IResourceDescription.Manager> bindIResourceDescription$Manager() {
		return DerivedStateAwareResourceDescriptionManager.class;
	}
	
	public Class<? extends IResourceValidator> bindIResourceValidator() {
		return org.eclipse.xtext.xbase.annotations.validation.DerivedStateAwareResourceValidator.class;
	}

	public Class<? extends IJvmModelInferrer> bindIJvmModelInferrer() {
		return DomainJvmModelInferrer.class;
	}
	
	public Class<? extends org.eclipse.xtext.resource.IDerivedStateComputer> bindIDerivedStateComputer() {
		return org.eclipse.xtext.xbase.jvmmodel.JvmModelAssociator.class;
	}
	
	public org.eclipse.xtext.common.types.TypesFactory bindTypesFactoryToInstance() {
		return org.eclipse.xtext.common.types.TypesFactory.eINSTANCE;
	}
	
	public Class<? extends org.eclipse.xtext.common.types.access.IJvmTypeProvider.Factory> bindIJvmTypeProvider$Factory() {
		return ClasspathTypeProviderFactory.class;
	}
	
	public org.eclipse.xtext.xtype.XtypeFactory bindXtypeFactoryToInstance() {
		return org.eclipse.xtext.xtype.XtypeFactory.eINSTANCE;
	}

}
