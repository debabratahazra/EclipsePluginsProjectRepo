package com.odcgroup.t24.version.linking;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.CrossReference;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.serializer.diagnostic.ISerializationDiagnostic.Acceptor;
import org.eclipse.xtext.serializer.tokens.CrossReferenceSerializer;

import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.core.repository.ModelURIConverter;
import com.odcgroup.workbench.core.repository.OfsResourceHelper;
import com.odcgroup.workbench.dsl.xml.NameURISwapperImpl;

@SuppressWarnings("restriction")
public class VersionCrossReferenceSerializer extends CrossReferenceSerializer {

	@Override
	public String serializeCrossRef(EObject semanticObject, CrossReference crossref, EObject target, INode node, Acceptor errors) {
		String namedRef = NameURISwapperImpl.getNameFromProxy(target);
		if (namedRef != null) {
			return namedRef;
		} else {
			if (!target.eIsProxy()) {
				Resource resource = target.eResource();
				URI uri = resource.getURI();
				if (ModelURIConverter.isModelUri(uri)) {
					IOfsProject ofsProject = OfsResourceHelper.getOfsProject(resource);
					ModelURIConverter uriConverter = new ModelURIConverter(ofsProject);
					uri = uriConverter.toPlatformURI(uri);
					target.eResource().setURI(uri);
				}
			}
			return super.serializeCrossRef(semanticObject, crossref, target, node, errors);
		}
	}
}
