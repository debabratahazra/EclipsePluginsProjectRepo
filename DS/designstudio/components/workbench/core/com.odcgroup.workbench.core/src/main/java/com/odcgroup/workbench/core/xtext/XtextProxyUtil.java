package com.odcgroup.workbench.core.xtext;

import javax.inject.Inject;

import org.eclipse.emf.ecore.EObject;

/**
 * Utility for Xtext related to EMF Proxies.
 * 
 * Tested by VersionXtextProxyUtilTest in com.odcgroup.t24.version.model.tests,
 * and DS7367ESONReferenceXtextProxyUtilTest in com.odcgroup.edge.t24ui.model.tests.
 * 
 * @see org.eclipse.emf.eson.util.XtextProxyUtil
 * 
 * @author Michael Vorburger
 */
public class XtextProxyUtil {

	@Inject
	private org.eclipse.emf.eson.util.XtextProxyUtil esonXtextProxyUtil;
	
	/**
	 * @see org.eclipse.emf.eson.util.XtextProxyUtil for full documentation
	 */
	public String getProxyCrossRefAsString(EObject context, EObject proxy) {
		return esonXtextProxyUtil.getProxyCrossRefAsString(context, proxy);
	}
	
}
