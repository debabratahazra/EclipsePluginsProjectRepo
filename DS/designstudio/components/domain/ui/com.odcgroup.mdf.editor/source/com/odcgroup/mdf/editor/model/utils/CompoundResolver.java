package com.odcgroup.mdf.editor.model.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;


/**
 * TODO: DOCUMENT ME!
 * 
 * @author <a href="mailto:cvedovini@odyssey-group.com">Claude Vedovini</a>
 * @version 1.0
 */
public class CompoundResolver implements EntityResolver {
	private final List resolvers = new ArrayList();

	/**
	 * Constructor for CompoundResolver
	 */
	public CompoundResolver() {
		super();
	}
	
	public void addResolver(EntityResolver resolver) {
		resolvers.add(resolver);
	}

	/**
	 * @see org.xml.sax.EntityResolver#resolveEntity(java.lang.String, java.lang.String)
	 */
	public InputSource resolveEntity(String publicId, String systemId)
		throws SAXException, IOException {
		Iterator it = resolvers.iterator();
		while (it.hasNext()) {
			EntityResolver resolver = (EntityResolver) it.next();
			InputSource is = resolver.resolveEntity(publicId, systemId);
			if (is != null) {
				return is;
			}
		}
		
		return null;
	}

}