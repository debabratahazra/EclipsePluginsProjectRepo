package com.odcgroup.domain.scoping;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.resource.ISelectable;
import org.eclipse.xtext.scoping.IScope;
import org.eclipse.xtext.scoping.impl.DefaultGlobalScopeProvider;
import org.eclipse.xtext.scoping.impl.SelectableBasedScope;

import com.google.common.base.Predicate;
import com.google.inject.Inject;
import com.google.inject.Provider;

public class DomainGlobalScopeProvider extends DefaultGlobalScopeProvider {
	
	@Inject
	private Provider<DomainPrimitivesResourceDescription> domainPrimitivesResourceDescriptionProvider;
	
	@Override
	protected IScope getScope(Resource resource, boolean ignoreCase, EClass type, Predicate<IEObjectDescription> filter) {
		IScope scope = super.getScope(resource, ignoreCase, type, filter);
		ISelectable primitives = domainPrimitivesResourceDescriptionProvider.get();
		return SelectableBasedScope.createScope(scope, primitives, filter, type, ignoreCase);
	}
	
}
