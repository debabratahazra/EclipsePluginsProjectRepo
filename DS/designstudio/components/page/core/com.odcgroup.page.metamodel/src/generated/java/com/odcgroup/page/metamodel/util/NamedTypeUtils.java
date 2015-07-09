package com.odcgroup.page.metamodel.util;

import java.util.Collection;
import java.util.Iterator;

import org.apache.commons.lang.StringUtils;

import com.odcgroup.page.metamodel.NamedType;

/**
 * Static utility methods for NamedTypes.
 * 
 * @author Gary Hayes
 */
public class NamedTypeUtils {

	/**
	 * Finds the NamedType by name.
	 * 
	 * @param coll The Collection containing the NamedType
	 * @param name The name of the type
	 * @return NamedType The NamedType or null if no type of this name can be found
	 */
	public static NamedType findByName(Collection coll, String name) {
		return findByName(coll, name, false);
	}
	
	/**
	 * Finds the NamedType by name.
	 * 
	 * @param coll The Collection containing the NamedType
	 * @param name The name of the type
	 * @param ignoreCase True if we should ignore the case
	 * @return NamedType The NamedType or null if no type of this name can be found
	 */
	@SuppressWarnings("unchecked")
	public static NamedType findByName(Collection coll, String name, boolean ignoreCase) {
		if (StringUtils.isEmpty(name)) {
			// No name supplied
			return null;
		}

		String n1 = name;
		if (ignoreCase) {
			n1 = n1.toLowerCase();
			String n2 = null;
			NamedType nt = null;
			for (Iterator it = coll.iterator(); it.hasNext();) {
				nt = (NamedType) it.next();
				n2 = nt.getName();
				if (n2 != null) {
					if (n1.equals(n2.toLowerCase())) {
						return nt;
					}
				}
			}
		} else {
			NamedType nt = null;
			for (Iterator it = coll.iterator(); it.hasNext();) {
				nt = (NamedType) it.next();
				if (n1.equals(nt.getName())) {
					return nt;
				}
			}
		}
		
		
		// Not found
		return null;
	}	
}
