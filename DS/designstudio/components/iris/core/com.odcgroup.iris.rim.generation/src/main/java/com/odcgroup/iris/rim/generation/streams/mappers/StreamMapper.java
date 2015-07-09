package com.odcgroup.iris.rim.generation.streams.mappers;

import java.util.List;

import org.eclipse.emf.ecore.EObject;

/**
 * TODO: Document me!
 *
 * @author taubert
 *
 */
public interface StreamMapper<T> {
	public List<T> map(EObject source, RimWriter destination, String rimName);
}
