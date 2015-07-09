package com.odcgroup.page.transformmodel.namespaces.internal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.odcgroup.page.transformmodel.Namespace;
import com.odcgroup.page.transformmodel.TransformModel;
import com.odcgroup.page.transformmodel.TransformModelFactory;
import com.odcgroup.page.transformmodel.namespaces.NamespaceFacilityConstants;
import com.odcgroup.workbench.core.preferences.ProjectPreferences;

/**
 * An implementation of the NamespaceFacility which uses the 
 * Eclipse Preferences mechanism.
 * 
 * @author atr
 */
class NamespaceFacilityImpl extends AbstractNamespaceFacility {
	
	/**
	 * @see com.odcgroup.page.transformmodel.namespaces.internal.AbstractNamespaceFacility#dispose()
	 */
	/*package*/ void dispose() {
	}	
	
	/**
	 * @see com.odcgroup.page.transformmodel.namespaces.internal.AbstractNamespaceFacility#setDefaultValues()
	 */
	protected void setDefaultValues() {
		getPropertyStore().clear();
	}
	
	/**
	 * @see com.odcgroup.page.transformmodel.namespaces.NamespaceFacility#getUserDefinedNamespaces()
	 */
	public List<Namespace> getUserDefinedNamespaces() {
		
		List<Namespace> list = new ArrayList<Namespace>();
		
		String value = getPropertyStore().get(NamespaceFacilityConstants.PROPERTY_USER_DEFINED_NAMESPACE, null);
		if (StringUtils.isNotEmpty(value)) {
			String [] images = value.split(",\\s?");
			for (String image : images) {
				if (image.charAt(0) == '{')  {
					image = image.substring(1);
	            }
	            if (image.charAt(image.length() - 1) == '}') {
	            	image = image.substring(0, image.length() - 1);
	            }
	            String[]words = image.split("=");
	            if (words.length > 1) {
		            Namespace ns = TransformModelFactory.eINSTANCE.createNamespace();
		            ns.setOrigin(Namespace.USER_DEFINED_NAMESPACES);
		            ns.setPrefix(words[0]);
		            ns.setUri(words[1]);
		            list.add(ns);
	            }
			}
		}
        return list;
	}

	/**
	 * @see com.odcgroup.page.transformmodel.namespaces.NamespaceFacility#setUserDefinedNamespaces(java.util.List)
	 */
	public void setUserDefinedNamespaces(List<Namespace> namespaces) {
		HashMap<String, String> map = new HashMap<String, String>();
		for (Namespace ns : namespaces) {
			if (Namespace.USER_DEFINED_NAMESPACES.equals(ns.getOrigin())) {
				map.put(ns.getPrefix(), ns.getUri());
			}
		}
		String value = map.toString();
		getPropertyStore().put(NamespaceFacilityConstants.PROPERTY_USER_DEFINED_NAMESPACE, value);
		getPropertyStore().flush();
	}
	
	/**
	 * @param model
	 */
	public void removeUserDefinedNamespaces(TransformModel model) {
		List<Namespace> list = model.getNamespaces();
		for (int i = 0; i < list.size(); i++) {
			Namespace ns = (Namespace) list.get(i);
			if (ns.getOrigin().equals(Namespace.USER_DEFINED_NAMESPACES)) {
				model.getNamespaces().remove(ns);
			}
		}
	}	

	/**
	 * @param model
	 */
	public void addUserDefinedNamespaces(TransformModel model) {
		removeUserDefinedNamespaces(model);
		for (Namespace namespace : getUserDefinedNamespaces()) {
			model.getNamespaces().add(namespace);
		}
	}	
	
	/**
	 * @param preferences
	 */
	public NamespaceFacilityImpl(ProjectPreferences preferences) {
		super(preferences);
	}
}