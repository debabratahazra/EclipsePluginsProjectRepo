package com.odcgroup.menu.model.naming;

import org.apache.commons.lang.StringUtils;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.naming.IQualifiedNameConverter;
import org.eclipse.xtext.naming.IQualifiedNameProvider;
import org.eclipse.xtext.naming.QualifiedName;

import com.google.inject.Inject;
import com.odcgroup.menu.model.MenuItem;
import com.odcgroup.menu.model.MenuRoot;

public class MenuNameProvider extends IQualifiedNameProvider.AbstractImpl {    

	@Inject IQualifiedNameConverter qnc; 
	
	public QualifiedName getFullyQualifiedName(EObject obj) {
		MenuItem item = null;
		if (obj instanceof MenuRoot) {
			item = ((MenuRoot)obj).getRootItem();
		}
		if (item != null) {
			// the root item can be null if there is a syntactical error 
			// on a menu reference (see include-menu)
			final String name = item.getName();
			// DS-7351 If MenuItem has no name, or -more likely- eIsProxy() then we MUST return null
			if (!StringUtils.isBlank(name))
				return qnc.toQualifiedName(name);
		}
		return null;
	}
    
} 
