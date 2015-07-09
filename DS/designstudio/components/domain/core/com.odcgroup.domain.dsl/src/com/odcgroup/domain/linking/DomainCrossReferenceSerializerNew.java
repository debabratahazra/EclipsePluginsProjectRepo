package com.odcgroup.domain.linking;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.CrossReference;
import org.eclipse.xtext.naming.IQualifiedNameConverter;
import org.eclipse.xtext.naming.IQualifiedNameProvider;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.serializer.diagnostic.ISerializationDiagnostic.Acceptor;
import org.eclipse.xtext.serializer.tokens.CrossReferenceSerializer;

import com.google.inject.Inject;
import com.odcgroup.domain.resource.DomainURIEncoder;
import com.odcgroup.mdf.ecore.util.MdfNameURIUtil;
import com.odcgroup.mdf.metamodel.MdfName;

/**
 * ICrossReferenceSerializer which handles MdfNameURIUtil (mdfname:/ URI
 * scheme), as well as the DomainURIEncoder (domainLink_ URI fragment).
 * 
 * @see http://rd.oams.com/browse/DS-7339
 * @see http://rd.oams.com/browse/DS-7332
 * @author Michael Vorburger
 * 
 * @see http://rd.oams.com/browse/DS-7424
 * @author kosyakov - added DomainURIEncoder
 */
@SuppressWarnings("restriction")
public class DomainCrossReferenceSerializerNew extends CrossReferenceSerializer {
	
	@Inject DomainURIEncoder domainURIEncoder;
	@Inject IQualifiedNameProvider qnp;
	@Inject IQualifiedNameConverter qnc;
	
	@Override
	public String serializeCrossRef(EObject semanticObject, CrossReference crossref, EObject target, INode node, Acceptor errors) {
		if (target.eIsProxy()) {
			InternalEObject interalTarget = (InternalEObject) target;
			URI proxyURI = interalTarget.eProxyURI();
			String fragment = proxyURI.fragment();
			Resource eResource = semanticObject.eResource();
			if (domainURIEncoder.isCrossLinkFragment(eResource, fragment)) {
				return domainURIEncoder.decodeCrossRef(eResource, fragment);
			} else if (domainURIEncoder.isBrokenCrossLinkFragment(eResource, fragment)) {
				return domainURIEncoder.decodeBrokenLink(eResource, fragment);
			}
		}
		MdfName mdfName = MdfNameURIUtil.getMdfNameIfIsProxy(target);
		if (mdfName != null)
			return mdfName.getQualifiedName();
		
		QualifiedName qn = qnp.getFullyQualifiedName(target);
		if (qn != null) {
			String name = qnc.toString(qn);
			if (name != null)
				return name;
		}
		
		return super.serializeCrossRef(semanticObject, crossref, target, node, errors);
	}
	
}
