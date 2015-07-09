package com.odcgroup.aaa.generation.udentities;

import java.util.ArrayList;
import java.util.List;

import com.odcgroup.aaa.generation.gateway.line.CMD;

public class CMDsByTypeList {

	CMD xd_entity;
	CMD xd_attribute;
	CMD xd_perm_value;
	CMD xd_attribute_parent;
	CMD xd_entity_parent_entity;
	CMD xd_label_for_xd_entity;
	CMD xd_label_for_xd_attribute;
	CMD xs_label_for_xd_perm_values;
	CMD xd_entity_security;
	CMD xd_attribute_security;
	CMD xd_attribute_security_rights;

	public boolean isEmpty() {
		return xd_entity==null && 
			xd_attribute==null && 
			xd_perm_value==null &&  
			xd_attribute_parent==null && 
			xd_entity_parent_entity==null && 
			xd_label_for_xd_entity==null && 
			xd_label_for_xd_attribute==null && 
			xs_label_for_xd_perm_values==null&& 
			xd_entity_security==null &&  
			xd_attribute_security==null &&  
			xd_attribute_security_rights==null;
	}
	
	public List<CMD> getAllCMDs() {
		List<CMD> result = new ArrayList<CMD>();
		if (xd_entity!=null)
			result.add(xd_entity);
		if (xd_attribute!=null)
			result.add(xd_attribute);
		if (xd_perm_value!=null)
			result.add(xd_perm_value);
		if (xd_attribute_parent!=null)
			result.add(xd_attribute_parent);
		if (xd_entity_parent_entity!=null)
			result.add(xd_entity_parent_entity);
		if (xd_label_for_xd_entity!=null)
			result.add(xd_label_for_xd_entity);
		if (xd_label_for_xd_attribute!=null)
			result.add(xd_label_for_xd_attribute);
		if (xs_label_for_xd_perm_values!=null)
			result.add(xs_label_for_xd_perm_values);
		if (xd_entity_security!=null)
			result.add(xd_entity_security);
		if (xd_attribute_security!=null)
			result.add(xd_attribute_security);
		if (xd_attribute_security_rights!=null)
			result.add(xd_attribute_security_rights);
		return result;
	}
}
