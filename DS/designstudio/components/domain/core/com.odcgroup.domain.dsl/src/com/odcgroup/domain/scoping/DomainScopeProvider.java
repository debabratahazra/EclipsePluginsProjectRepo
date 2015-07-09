package com.odcgroup.domain.scoping;

import static org.eclipse.xtext.scoping.Scopes.scopedElementsFor;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.filter;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.xtext.naming.IQualifiedNameProvider;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.scoping.IScope;
import org.eclipse.xtext.scoping.Scopes;
import org.eclipse.xtext.scoping.impl.AbstractDeclarativeScopeProvider;
import org.eclipse.xtext.scoping.impl.MapBasedScope;

import com.google.inject.Inject;
import com.odcgroup.mdf.ecore.MdfDomainImpl;
import com.odcgroup.mdf.ecore.MdfPropertyImpl;
import com.odcgroup.mdf.ecore.MdfReverseAssociationImpl;
import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.mdf.metamodel.MdfPrimitive;

public class DomainScopeProvider extends AbstractDeclarativeScopeProvider {
	
	@Inject
	private IQualifiedNameProvider qualifiedNameProvider;
	
	public IScope scope_MdfEntity(MdfDomainImpl context, EReference reference) {
		return scopeFor(context.getEntities(), context, reference);
	}
	
	public IScope scope_MdfClass(MdfDomainImpl context, EReference reference) {
		return scopeFor(context.getClasses(), context, reference);
	}
	
	public IScope scope_MdfDataset(MdfDomainImpl context, EReference reference) {
		return scopeFor(context.getDatasets(), context, reference);
	}
	
	public IScope scope_MdfEnumeration(MdfDomainImpl context, EReference reference) {
		return scopeFor(context.getEnumerations(), context, reference);
	}
	
	public IScope scope_MdfBusinessType(MdfDomainImpl context, EReference reference) {
		return scopeFor(context.getBusinessTypes(), context, reference);
	}
	
	public IScope scope_MdfPrimitive(MdfDomainImpl context, EReference reference) {
		return scopeFor(filter(context.getEntities(), MdfPrimitive.class), context, reference);
	}

	private IScope scopeFor(Iterable<?> objects, MdfDomainImpl context, EReference reference) {
		IScope parent = delegateGetScope(context, reference);
		Iterable<EObject> entities = filter(objects, EObject.class);
		Iterable<IEObjectDescription> descriptions = scopedElementsFor(entities, qualifiedNameProvider);
		return MapBasedScope.createScope(parent, descriptions);
	}
	
	public IScope scope_MdfReverseAssociation_reversedAssociation(MdfReverseAssociationImpl context, EReference reference) {
		MdfClass reversedAssociationType = context.getReversedAssociationType();
		Iterable<EObject> properties = collectProperties(reversedAssociationType, new HashMap<String, EObject>());
		return Scopes.scopeFor(properties);
	}

	private Iterable<EObject> collectProperties(MdfClass type, Map<String, EObject> collectedProperties) {
		if (type == null) {
			return collectedProperties.values();
		}
		for (MdfPropertyImpl property : filter(type.getProperties(), MdfPropertyImpl.class)) {
			if (!collectedProperties.containsKey(property.getName())) {
				collectedProperties.put(property.getName(), property);
			}
		}
		return collectProperties(type.getBaseClass(), collectedProperties);
	}

}
