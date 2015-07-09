package com.odcgroup.t24.version.naming;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.naming.IQualifiedNameProvider;
import org.eclipse.xtext.naming.QualifiedName;

import com.odcgroup.t24.version.versionDSL.Version;

public class VersionNameProvider extends IQualifiedNameProvider.AbstractImpl {

	// HISTORY: This used to be implemented differently, based on usual Xtext
	// approach of Version API to getName() etc. with extra care taken to *NOT*
	// resolve cross-references in order to create EObjectDescriptions!
	// (Else you'll hit a "Cyclic resolution of lazy links" AssertionError.
	// @see https://bugs.eclipse.org/bugs/show_bug.cgi?id=382266)
	//
	// It was rewritten in DS-6841 to be based on filename - not sure why
	// (performance?).
	//
	// If it is ever changed to work as initially designed, ensure DS-7351 is
	// taken into account.

	@Override
	public QualifiedName getFullyQualifiedName(EObject obj) {
		if (obj instanceof Version) {
			if (obj.eResource() != null) {
				String seg = obj.eResource().getURI().lastSegment();
				seg = seg.substring(0, seg.lastIndexOf("."));
				return QualifiedName.create(seg);
			}
		}
		return null;
	}

}
