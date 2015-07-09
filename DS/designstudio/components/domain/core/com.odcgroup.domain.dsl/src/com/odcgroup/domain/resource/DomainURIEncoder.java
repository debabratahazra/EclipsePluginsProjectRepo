/*******************************************************************************
 * Copyright (c) 2008 itemis AG (http://www.itemis.eu) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package com.odcgroup.domain.resource;

import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.util.Strings;
import org.eclipse.xtext.util.Triple;
import org.eclipse.xtext.util.Tuples;

import com.google.inject.Singleton;

/**
 * Used to create a URI fragment for EMF proxies installed during T24
 * Application XML to Domain transformation in T24MetaModel2MdfMapper.
 * 
 * @see http://rd.oams.com/browse/DS-7424
 * @see https://bugs.eclipse.org/bugs/show_bug.cgi?id=437669
 * 
 * @author kosyakov
 */
@Singleton
public class DomainURIEncoder {
	
	public static final String DOMAIN_LINK = "domainLink_";
	public static final String DOMAIN_BROKEN_LINK = "domainBrokenLink_";
	public static final String SEP = "::";

	public String encodeBrokenLink(String crossRefString) {
		StringBuilder fragment = new StringBuilder(20).append(DOMAIN_BROKEN_LINK).append(SEP);
		fragment.append(crossRefString);
		return fragment.toString();
	}

	public String decodeBrokenLink(Resource res, String uriFragment) {
		List<String> split = Strings.split(uriFragment, SEP);
		return split.get(1);
	}

	public String encode(EObject obj, EReference ref, String crossRefString) {
		StringBuilder fragment = new StringBuilder(20).append(DOMAIN_LINK).append(SEP);
		appendShortFragment(obj, fragment);
		fragment.append(SEP);
		fragment.append(toShortExternalForm(obj.eClass(), ref)).append(SEP);
		fragment.append(crossRefString);
		return fragment.toString();
	}
	
	public void appendShortFragment(EObject obj, StringBuilder target) {
		EReference containmentFeature = obj.eContainmentFeature();
		if (containmentFeature == null) {
			target.append(obj.eResource().getContents().indexOf(obj));
		} else {
			EObject container = obj.eContainer();
			appendShortFragment(container, target);
			target.append('.').append(container.eClass().getFeatureID(containmentFeature));
			if (containmentFeature.isMany()) {
				List<?> list = (List<?>) container.eGet(containmentFeature);
				target.append('.').append(list.indexOf(obj));
			}
		}
	}
	
	public String toShortExternalForm(EClass clazz, EReference ref) {
		return Integer.toString(clazz.getFeatureID(ref));
	}

	public String decodeCrossRef(Resource eResource, String uriFragment) {
		List<String> split = Strings.split(uriFragment, SEP);
		return split.get(3);
	}

	public Triple<EObject, EReference, String> decode(Resource res, String uriFragment) {
		List<String> split = Strings.split(uriFragment, SEP);
		EObject source = resolveShortFragment(res, split.get(1));
		EReference ref = fromShortExternalForm(source.eClass(), split.get(2));
		String crossRefString = split.get(3);
		return Tuples.create(source, ref, crossRefString);
	}
	
	public EObject resolveShortFragment(Resource res, String shortFragment) {
		List<String> split = Strings.split(shortFragment, '.');
		int contentsIdx = Integer.parseInt(split.get(0));
		EObject result = res.getContents().get(contentsIdx);
		int splitIdx = 1;
		while(splitIdx < split.size()) {
			int featureId = Integer.parseInt(split.get(splitIdx++));
			EReference reference = (EReference) result.eClass().getEStructuralFeature(featureId);
			if (reference.isMany()) {
				List<?> list = (List<?>) result.eGet(reference);
				int listIdx = Integer.parseInt(split.get(splitIdx++));
				result = (EObject) list.get(listIdx);
			} else {
				result = (EObject) result.eGet(reference);
			}
		}
		return result;
	}
	
	public EReference fromShortExternalForm(EClass clazz, String shortForm) {
		int featureId = Integer.parseInt(shortForm);
		return (EReference) clazz.getEStructuralFeature(featureId);
	}

	public boolean isCrossLinkFragment(Resource res, String s) {
		return s != null && s.startsWith(DOMAIN_LINK);
	}

	public boolean isBrokenCrossLinkFragment(Resource eResource, String s) {
		return s != null && s.startsWith(DOMAIN_BROKEN_LINK);
	}
}
