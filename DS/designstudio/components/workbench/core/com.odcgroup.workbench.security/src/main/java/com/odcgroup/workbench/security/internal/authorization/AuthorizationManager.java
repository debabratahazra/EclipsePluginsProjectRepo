package com.odcgroup.workbench.security.internal.authorization;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.util.URI;

import com.odcgroup.workbench.security.DSAuthorizationResult;
import com.odcgroup.workbench.security.DSPermission;
import com.odcgroup.workbench.security.IAuthorizationManager;
import com.odcgroup.workbench.security.IUser;
import com.odcgroup.workbench.security.SecurityCore;

/**
 * This is the main implementation of an AuthorizationManager. It delegates
 * authorization requests to the registered extensions.
 * 
 * @author Kai Kreuzer
 * 
 */
public class AuthorizationManager implements IAuthorizationManager {

	public static final String AUTH_MANAGER_EXTENSION_ID = "com.odcgroup.workbench.security.authorization";

	static private IAuthorizationManager instance;

	protected IAuthorizationManager[] authManagers = new IAuthorizationManager[0];

	protected String id;
	protected String priority;

	static Map<String, Integer> priorityMap = new HashMap<String, Integer>();
	
	static {
		priorityMap.put("lowest", -3);
		priorityMap.put("lower", -2);
		priorityMap.put("low", -1);
		priorityMap.put("normal", 0);
		priorityMap.put("high", 1);
		priorityMap.put("higher", 2);
		priorityMap.put("highest", 3);
	}

	/**
	 * @return the priority
	 */
	public String getPriority() {
		return priority;
	}

	/**
	 * @param priority
	 *            the priority to set
	 */
	public void setPriority(String priority) {
		this.priority = priority;
	}

	public AuthorizationManager() {
	}

	private AuthorizationManager(String id) {
		this.id = id;
		this.authManagers = getRegisteredAuthorizationManagers();
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.odcgroup.workbench.security.IAuthorizationManager#permissionGranted
	 * (java.lang.String, com.odcgroup.workbench.security.DSPermission,
	 * com.odcgroup.workbench.security.IUser)
	 */
	public DSAuthorizationResult permissionGranted(String subjectId, DSPermission permission, IUser user) {
		for (IAuthorizationManager authManager : authManagers) {
			DSAuthorizationResult result = authManager.permissionGranted(subjectId, permission, user);
			if (result != DSAuthorizationResult.UNKNOWN)
				return result;
		}
		// in case of doubt, always reject
		return DSAuthorizationResult.REJECTED;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.odcgroup.workbench.security.IAuthorizationManager#permissionGranted
	 * (org.eclipse.core.resources.IResource,
	 * com.odcgroup.workbench.security.DSPermission,
	 * com.odcgroup.workbench.security.IUser)
	 */

	public DSAuthorizationResult permissionGranted(IProject project, URI uri, DSPermission permission, IUser user) {
		for (IAuthorizationManager authManager : authManagers) {
			DSAuthorizationResult result = authManager.permissionGranted(project, uri, permission, user);
			if (result != DSAuthorizationResult.UNKNOWN)
				return result;
		}
		// in case of doubt, always reject
		return DSAuthorizationResult.REJECTED;
	}

	/**
	 * returns an instance of the AuthorizationManager
	 * 
	 * @return an instance of the AuthorizationManager
	 */
	public static synchronized IAuthorizationManager getInstance() {
		if (instance == null) {
			instance = new AuthorizationManager("com.odcgroup.workbench.security.authorization.main");
		}
		return instance;
	}

	private IAuthorizationManager[] getRegisteredAuthorizationManagers() {
		ArrayList<IAuthorizationManager> authManagers = new ArrayList<IAuthorizationManager>();
		IExtensionRegistry registry = Platform.getExtensionRegistry();
		IExtensionPoint point = registry.getExtensionPoint(AUTH_MANAGER_EXTENSION_ID);
		if (point == null)
			return new IAuthorizationManager[0];
		IConfigurationElement[] configElements = point.getConfigurationElements();

		// iterate over all defined authorization managers
		for (int j = 0; j < configElements.length; j++) {
			try {
				IAuthorizationManager authManager = (IAuthorizationManager) configElements[j]
						.createExecutableExtension("class");
				authManager.setId(configElements[j].getAttribute("id"));
				authManager.setPriority(configElements[j].getAttribute("priority"));
				authManagers.add(authManager);
			} catch (CoreException e) {
				SecurityCore.getDefault().getLog().log(e.getStatus());
			}
		}
		
		Comparator<IAuthorizationManager> comparator = new Comparator<IAuthorizationManager>() {
			public int compare(IAuthorizationManager m1, IAuthorizationManager m2) {				
				return priorityMap.get(m2.getPriority()).compareTo(priorityMap.get(m1.getPriority()));
			}
		};
		
		Collections.sort(authManagers, comparator);
		
		return authManagers.toArray(new IAuthorizationManager[0]);
	}

}
