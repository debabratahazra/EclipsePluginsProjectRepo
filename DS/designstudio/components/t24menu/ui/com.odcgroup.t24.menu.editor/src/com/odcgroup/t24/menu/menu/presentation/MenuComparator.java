package com.odcgroup.t24.menu.menu.presentation;

import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang.StringUtils;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.naming.IQualifiedNameProvider;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.IResourceServiceProvider;

import com.google.common.base.Objects;
import com.odcgroup.edge.t24ui.CompositeScreen;
import com.odcgroup.t24.enquiry.enquiry.Enquiry;
import com.odcgroup.t24.menu.menu.MenuItem;
import com.odcgroup.t24.menu.menu.MenuRoot;
import com.odcgroup.t24.menu.menu.Translation;
import com.odcgroup.t24.version.versionDSL.Version;

/**
 * @author atripod
 */
public class MenuComparator {
	
	@Inject 
	private IResourceServiceProvider.Registry reg;
	
	public MenuComparator() {
	}
	
	// return true if both the menus are the same. 
	public boolean compareMenu(MenuRoot left, MenuRoot right) {
		return compareMenuItem(left.getRootItem(), right.getRootItem());
	}

	protected boolean compareMenuItem(MenuItem left, MenuItem right) {
		if (!StringUtils.equals(left.getName(), right.getName()))
			return false;
		if (!StringUtils.equals(left.getParameters(), right.getParameters()))
			return false;
		if (left.getEnabled() != right.getEnabled())
			return false;
		if (!StringUtils.equals(left.getSecurityRole(), right.getSecurityRole()))
			return false;
		if (!StringUtils.equals(left.getShortcut(), right.getShortcut()))
			return false;
		if (!compareQualifiedName(left.getCompositeScreen(), right.getCompositeScreen()))
			return false;
		if (!compareQualifiedName(left.getEnquiry(), right.getEnquiry()))
			return false;
		if (!compareQualifiedName(left.getVersion(), right.getVersion()))
			return false;
		if (!compareQualifiedName(left.getIncludedMenu(), right.getIncludedMenu()))
			return false;
		if (!compareTranslations(left.getLabels(), right.getLabels()))
			return false;
		if (!compareSubMenus(left.getSubmenus(), right.getSubmenus()))
			return false;
		return true;
	}
	
	protected boolean compareTranslations(List<Translation> left, List<Translation> right) {
		if (left.size() != right.size())
			return false;
		if (right.size() != right.size())
			return false;
		int len = left.size();
		for (int kx = 0; kx < len; kx++) {
			if (!compareTranslation(left.get(kx), right.get(kx))) {
				return false;
			}
		}
		return true;
	}
	
	protected boolean compareTranslation(Translation left, Translation right) {
		if (!StringUtils.equals(left.getLanguage(), right.getLanguage()))
			return false;
		if (!StringUtils.equals(left.getMessage(), right.getMessage()))
			return false;
		return true;
	}

	protected boolean compareSubMenus(List<MenuItem> left, List<MenuItem> right) {
		if (left.size() != right.size())
			return false;
		if (right.size() != right.size())
			return false;
		int len = left.size();
		for (int kx = 0; kx < len; kx++) {
			if (!compareMenuItem(left.get(kx), right.get(kx))) {
				return false;
			}
		}
		return true;
	}

	private boolean compareQualifiedName(EObject left, EObject right) {
		QualifiedName leftQName = getQualifiedName(left);
		QualifiedName rightQName = getQualifiedName(right);
		return Objects.equal(leftQName, rightQName);
	}

	private QualifiedName getQualifiedName(EObject element) {
		if (element != null) {
			IResourceServiceProvider rsp = null;
			if (element instanceof Enquiry) {
				rsp = reg.getResourceServiceProvider(URI.createURI("dummy.enquiry"));
			} else if (element instanceof Version) {
				rsp = reg.getResourceServiceProvider(URI.createURI("dummy.version"));
			} else if (element instanceof CompositeScreen) {
				rsp = reg.getResourceServiceProvider(URI.createURI("dummy.eson"));
			} else if (element instanceof MenuRoot) {
				rsp = reg.getResourceServiceProvider(URI.createURI("dummy.menu"));
			}
			if (rsp != null) {
				// get the qualified for the received element
				IQualifiedNameProvider qualifiedNameProvider = rsp.get(IQualifiedNameProvider.class);
				if (qualifiedNameProvider != null) {
					return qualifiedNameProvider.getFullyQualifiedName(element);
				}
			}
		}
		
		return null;
	}
}
