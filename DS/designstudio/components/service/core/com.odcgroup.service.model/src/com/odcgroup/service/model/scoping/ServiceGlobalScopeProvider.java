package com.odcgroup.service.model.scoping;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.EObjectDescription;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.scoping.IScope;
import org.eclipse.xtext.scoping.impl.DefaultGlobalScopeProvider;
import org.eclipse.xtext.scoping.impl.SimpleScope;

import com.google.common.base.Predicate;
import com.odcgroup.mdf.ecore.MdfPrimitiveImpl;
import com.odcgroup.mdf.ecore.PrimitivesDomain;

public class ServiceGlobalScopeProvider extends DefaultGlobalScopeProvider {
	

	@SuppressWarnings("unchecked")
	@Override
	protected IScope getScope(Resource resource, boolean ignoreCase, EClass type, Predicate<IEObjectDescription> filter) {
		Collection<IEObjectDescription> descriptions = new HashSet<IEObjectDescription>();
		IScope scope = super.getScope(resource, ignoreCase, type, filter);
		List<MdfPrimitiveImpl> corePrimitives = PrimitivesDomain.DOMAIN.getPrimitives();
		for (MdfPrimitiveImpl primitive : corePrimitives) {
			descriptions.add(EObjectDescription.create(QualifiedName.create(primitive.getName()), primitive));			
		}
		scope = new SimpleScope(scope, descriptions);
		return scope;
	}
	

}
