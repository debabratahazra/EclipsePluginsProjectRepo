package com.odcgroup.page.metamodel.util;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.odcgroup.page.metamodel.MetaModel;
import com.odcgroup.page.metamodel.PropertyType;
import com.odcgroup.page.metamodel.SnippetModel;
import com.odcgroup.page.metamodel.SnippetType;

/**
 *
 * @author pkk
 *
 */
public final class SnippetRegistry {
	
	/** The Map of SnippetTypes. */
	private Map<String, SnippetType> snippetTypes = new HashMap<String, SnippetType>();
	
	/** The Map of PropertyTypes for each snippet type */
	private Map<String, Map<String, PropertyType> > snippetPropertyTypes = new HashMap<String, Map<String,PropertyType>>();

	/**
	 * Initializes this registry.
	 * 
	 * @param metamodel
	 *            The metamodel
	 */
	SnippetRegistry(MetaModel metamodel) {
		SnippetModel sm = metamodel.getSnippetModel();
		if (sm != null) {
			for (SnippetType st : sm.getSnippets()) {
				String stName = st.getName();
				snippetTypes.put(stName, st);
				Map<String,PropertyType> map = new HashMap<String, PropertyType>();
				snippetPropertyTypes.put(stName, map);
				for (PropertyType pt : st.getPropertyTypes()) {
					map.put(pt.getName(), pt);
				}
			}
		}
	}
	
	/**
	 * Gets the EventType given its name.
	 * 
	 * @param eventName The name of the EventType
	 * @return EventType The EventType
	 */
	SnippetType findSnippetType(String eventName) {
	    return snippetTypes.get(eventName);
	}

	/**
	 * Gets the propertyTypes supported by the designated snippet type
	 * @param snippetTypeName the name of a snippet
	 * @return the property types map
	 */
	public Map<String, PropertyType> findSnippetPropertyTypes(String snippetTypeName) {
		Map<String,PropertyType> map = snippetPropertyTypes.get(snippetTypeName);
		if (map == null) {
			map = new HashMap<String, PropertyType>();
		}
		return Collections.unmodifiableMap(map);
	}

}
