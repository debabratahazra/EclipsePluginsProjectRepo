package com.odcgroup.domain.scoping;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.EObjectDescription;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.resource.IReferenceDescription;
import org.eclipse.xtext.resource.impl.AbstractResourceDescription;

import com.google.inject.Inject;
import com.odcgroup.mdf.ecore.MdfPrimitiveImpl;
import com.odcgroup.mdf.ecore.PrimitivesDomain;
import com.odcgroup.mdf.metamodel.MdfPrimitive;

public class DomainPrimitivesResourceDescription extends AbstractResourceDescription {

	@Inject
	private DomainQualifiedNameProvider domainQualifiedNameProvider;

	@Override
	public Iterable<QualifiedName> getImportedNames() {
		return Collections.emptySet();
	}

	@Override
	public Iterable<IReferenceDescription> getReferenceDescriptions() {
		return Collections.emptySet();
	}

	@Override
	public URI getURI() {
		return PrimitivesDomain.DOMAIN.eResource().getURI(); // DS-7404
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected List<IEObjectDescription> computeExportedObjects() {
		List<IEObjectDescription> descs = new ArrayList<IEObjectDescription>();
		
		List<MdfPrimitiveImpl> corePrimitives = (List<MdfPrimitiveImpl>)(PrimitivesDomain.DOMAIN.getPrimitives());
		
		for(MdfPrimitive primitive : corePrimitives) {
			EObject element = (EObject) primitive;
			descs.add(EObjectDescription.create(domainQualifiedNameProvider.getFullyQualifiedName(element), element));
			descs.add(EObjectDescription.create(QualifiedName.create(primitive.getName()), element));
		}
		return descs;
	}

}
