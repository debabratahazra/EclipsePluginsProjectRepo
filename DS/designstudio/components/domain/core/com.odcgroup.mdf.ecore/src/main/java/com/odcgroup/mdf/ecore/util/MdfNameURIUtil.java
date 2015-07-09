package com.odcgroup.mdf.ecore.util;

import org.apache.commons.lang.StringUtils;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;

import com.google.common.base.Preconditions;
import com.odcgroup.mdf.metamodel.MdfName;
import com.odcgroup.mdf.metamodel.MdfNameFactory;

/**
 * Util to deal with EMF URIs of mdfname:/ scheme.
 *
 * @see http://rd.oams.com/browse/DS-6436 re. when/why this was introduced
 * 
 * @see com.odcgroup.domain.scoping.MdfNameQualifiedNameUtil for something a little similar - remember to adapt both if e.g. changing Cache implementation
 *
 * @author Michael Vorburger
 */
public class MdfNameURIUtil {

	// please keep this private, not public; it's a better encapsulated design
	private static final String SCHEME_MDFNAME = "mdfname"; // DS-6436 MUST BE LOWER-CASE!

	public static URI createURI(MdfName qname) {
		// we do NOT check this.. because a "Domain MdfName", which has only one part (which, strangely, is it's LocalName..) is valid in MDF!
		// Preconditions.checkArgument(!StringUtils.isBlank(qname.getDomain()), "MdfName has a blank domain name part: %s", qname);
		
		Preconditions.checkArgument(!StringUtils.isBlank(qname.getLocalName()), "MdfName has a blank local name part: %s", qname);

		// Just like DomainCrossReferenceSerializer already also does..
		// and to prevent IllegalArgumentException: invalid segment: Class#association
		// at org.eclipse.emf.common.util.URI.validateURI() 
		String localName = qname.getLocalName().replace('#', ':');

		String[] segments;
		if (!StringUtils.isBlank(qname.getDomain()))
			segments = new String[] { qname.getDomain(), localName };
		else
			segments = new String[] { qname.getLocalName() };
		
		// NOT URI.createGenericURI(MdfNameURIUtil.SCHEME_MDFNAME, qname.toString(), null)
		// because it has some serious performance issues (see DS-6436),
		// http://www.eclipse.org/forums/index.php/m/1091579/
		// https://bugs.eclipse.org/bugs/show_bug.cgi?id=415311
		// instead better like this (and inverse below in getMdfName):
		return URI.createHierarchicalURI(SCHEME_MDFNAME, null, null, segments , null, null);
	}
	
	public static boolean isMdfNameURI(URI uri) {
		return SCHEME_MDFNAME.equals(uri.scheme());
	}
	
	public static MdfName getMdfName(URI uri) {
		if (isMdfNameURI(uri)) {
			Preconditions.checkArgument(!StringUtils.isBlank(uri.segment(0)), "This URI, despite being isMdfNameURI(), has a blank qName/segment(0) for domain: %s", uri);
			if (uri.segmentCount() == 2) {
				Preconditions.checkArgument(!StringUtils.isBlank(uri.segment(1)), "This URI, despite being isMdfNameURI(), has a blank name/segment(1) : %s", uri);
				return MdfNameFactory.createMdfName(uri.segment(0), uri.segment(1));
			} else if (uri.segmentCount() == 1) {
				return MdfNameFactory.createMdfName(uri.segment(0));
			} else {
				throw new IllegalArgumentException("This URI, despite being isMdfNameURI(), does have 1 or 2 segments: " + uri);
			}
		} else if ("name".equals(uri.scheme())) { 
			// TODO com.odcgroup.workbench.dsl.xml.AbstractNameURISwapperImpl.NAME_SCHEME ... ??
			// This will happen because when NameURISwapper uses EcoreUtil, via MdfClassImpl hashCode -> getQualifiedName,
			// we end up in here with a name:/ URI which was not created by this helper class.
			//Probably ok now as we have meanwhile adapted to getQualifiedName() in MDFModelElemetImpl to check if isMdfNameURI(uri).
			Preconditions.checkArgument(!StringUtils.isBlank(uri.segment(0)), "This URI, although not an isMdfNameURI() but a name: scheme, has a blank qName/segment(0) for domain: %s", uri);
			return MdfNameFactory.createMdfName(uri.segment(0));
		} else {
			throw new IllegalArgumentException(uri.toString());
		}
	}
	
	public static MdfName getMdfNameIfIsProxy(EObject eo) {
		if (!eo.eIsProxy())
			return null;
		InternalEObject ieo = (InternalEObject) eo;
		URI uri = ieo.eProxyURI();
		if (!isMdfNameURI(uri)) 
			return null;
		return getMdfName(uri);
	}
}
