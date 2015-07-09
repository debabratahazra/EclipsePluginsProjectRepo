package com.odcgroup.t24.enquiry.scoping;

import javax.inject.Inject;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.naming.IQualifiedNameConverter;
import org.eclipse.xtext.naming.IQualifiedNameProvider;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.util.Strings;

import com.odcgroup.t24.enquiry.enquiry.Enquiry;

public class EnquiryQualifiedNameProvider extends IQualifiedNameProvider.AbstractImpl {
	// NOT DefaultDeclarativeQualifiedNameProvider @see DS-7308

	@Inject IQualifiedNameConverter qnc;

	@Override
	public QualifiedName getFullyQualifiedName(final EObject obj) {
		if(obj instanceof Enquiry) {
			Enquiry enquiry = (Enquiry) obj;
			final String name = enquiry.getName();
			// DS-7351 If the Enquiry has no name, or -more likely- eIsProxy() then we MUST return null
			if (!Strings.isEmpty(name))
				// DS-7351 NOT return QualifiedName.create(name); else it will create 1 segment, instead of 3 (for each . in Enquiry name)
				return qnc.toQualifiedName(name);
		}
		return null;
	}
}
