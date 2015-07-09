package com.odcgroup.domain.linking;

import com.odcgroup.mdf.metamodel.MdfName;
import com.odcgroup.mdf.metamodel.MdfNameFactory;

/**
 * Util to handle "legacy" MDF Name using '.' instead of ':' which we had at some point in *.domain.
 *
 * @see DS-6777 problem with old test *-models projects which start failing after we've started the Xtext Index infrastructure for *.domain
 * 
 * @see LegacyMdfNameFactoryUtilTest for exact intended semantics
 *
 * @author Michael Vorburger
 */
public class LegacyMdfNameFactoryUtil {

	public static MdfName createMdfName(String crossRefString) {
		if (MdfName.isQualified(crossRefString)) {
			return MdfNameFactory.createMdfName(crossRefString.replace('#', ':'));
		}
		
		int firstDot = crossRefString.indexOf('.');
		if (firstDot == -1)
			return MdfNameFactory.createMdfName(crossRefString);
		
		String domain = crossRefString.substring(0, firstDot);
		String localName = crossRefString.substring(firstDot + 1);

		// Do NOT use replace('.', '#') here, because that will cause
		// IllegalArgumentException: invalid segment: Class#association
		// at org.eclipse.emf.common.util.URI.validateURI() when this
		// is used together with MdfNameURIUtil, as tested by the
		// LegacyMdfNameFactoryUtilTest
		localName = localName.replace('.', ':');

		// Just like DomainCrossReferenceSerializer already also does..
		// and to prevent IllegalArgumentException: invalid segment: Class#association
		// at org.eclipse.emf.common.util.URI.validateURI() 
		localName = localName.replace('#', ':');
		
		return MdfNameFactory.createMdfName(domain, localName);
	}

}
