package com.odcgroup.page.linking;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.xtext.CrossReference;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.parsetree.reconstr.impl.CrossReferenceSerializer;

/**
 * This class provides a custom implementation of the Xtext cross reference serializer.
 * It simply returns the URI of a referenced model.
 * 
 * @author Kai Kreuzer
 *
 */
public class PageCrossReferenceSerializer extends CrossReferenceSerializer {

	@Override
	public boolean equalsOrReplacesNode(EObject context,
			CrossReference crossref, EObject target, INode node) {
		return super.equalsOrReplacesNode(context, crossref, target, node);
	}

	@Override
	public String serializeCrossRef(EObject context, CrossReference crossref,
			EObject target, INode node) {
		return EcoreUtil.getURI(target).trimFragment().trimQuery().toString();
//		String ref = super.serializeCrossRef(context, crossref, target, node);
//		if(ref==null && target instanceof Model) {
//			ref = EcoreUtil.getURI(target);
//		}
//		return ref;
	}
}
