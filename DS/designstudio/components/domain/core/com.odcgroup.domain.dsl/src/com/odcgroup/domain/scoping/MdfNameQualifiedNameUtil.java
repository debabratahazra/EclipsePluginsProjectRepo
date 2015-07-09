package com.odcgroup.domain.scoping;

import org.apache.commons.lang.StringUtils;
import org.eclipse.xtext.naming.QualifiedName;

import com.google.common.base.Preconditions;
import com.odcgroup.mdf.ecore.util.MdfNameURIUtil;
import com.odcgroup.mdf.metamodel.MdfName;
import com.odcgroup.mdf.metamodel.MdfNameFactory;

/**
 * Util to convert MDF MdfName from/to Xtext Qualified Name.
 *
 * @see http://rd.oams.com/browse/DS-6731 re. when/why this was introduced
 * @see MdfNameURIUtil for something a little similar - remember to adapt both if e.g. changing Cache implementation
 *
 * @author Michael Vorburger
 */
public class MdfNameQualifiedNameUtil {

	public static QualifiedName getQualifiedName(MdfName qname) {
		// we do NOT check this.. because a "Domain MdfName", which has only one part (which, strangely, is it's LocalName..) is valid in MDF!
		// Preconditions.checkArgument(!StringUtils.isBlank(qname.getDomain()), "MdfName has a blank domain name part: %s", qname);
		
		Preconditions.checkArgument(!StringUtils.isBlank(qname.getLocalName()), "MdfName has a blank local name part: %s", qname);

		String localName = qname.getLocalName();
		String[] segments = localName.split("#");
		if (segments.length == 2) {
			return QualifiedName.create(qname.getDomain(), segments[0], segments[1]);						
		} else if (!StringUtils.isBlank(qname.getDomain())) {
			return QualifiedName.create(qname.getDomain(), qname.getLocalName());
		} else {
			return QualifiedName.create(qname.getLocalName());
		}
	}

	public static MdfName getMdfName(QualifiedName qname) {
		int segmentCount = qname.getSegmentCount();
		if (segmentCount < 2 || segmentCount > 3)
			throw new IllegalArgumentException("Cannot convert QualifiedName to MdfName, because it should have 2 or 3 segments: " + qname.toString(":"));
		String domainName = qname.getFirstSegment();
		String localName;
		if (segmentCount == 3) {
			localName = qname.getSegment(1)+"#"+qname.getLastSegment();
		} else {
			localName = qname.getLastSegment();
		}
		Preconditions.checkArgument(!StringUtils.isBlank(domainName), "This QualifiedName has a blank domainName : %s", qname);
		Preconditions.checkArgument(!StringUtils.isBlank(localName), "This QualifiedName has a blank localName : %s", qname);
		return MdfNameFactory.createMdfName(domainName, localName);
	}

}