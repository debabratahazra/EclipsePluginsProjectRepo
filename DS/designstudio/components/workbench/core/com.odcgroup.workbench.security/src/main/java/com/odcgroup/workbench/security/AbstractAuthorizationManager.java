package com.odcgroup.workbench.security;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.util.URI;

/**
 * This abstract class should be used as a base class for specific implementations that register
 * via the extension point "com.odcgroup.workbench.security.authorization".
 * 
 * @author Kai Kreuzer
 *
 */
public abstract class AbstractAuthorizationManager implements IAuthorizationManager {

	private String id;
	private String priority;

	public String getId() {
		return id;
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.security.IAuthorizationManager#getPriority()
	 */
	public String getPriority() {
		return priority;
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.security.IAuthorizationManager#permissionGranted(java.lang.String, com.odcgroup.workbench.security.DSPermission, com.odcgroup.workbench.security.IUser)
	 */
	abstract public DSAuthorizationResult permissionGranted(String subjectId, DSPermission permission, IUser user);

	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.security.IAuthorizationManager#permissionGranted(org.eclipse.core.resources.IResource, com.odcgroup.workbench.security.DSPermission, com.odcgroup.workbench.security.IUser)
	 */

	abstract public DSAuthorizationResult permissionGranted(IProject project, URI uri, DSPermission permission, IUser user);
	
	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.security.IAuthorizationManager#setId(java.lang.String)
	 */

	public void setId(String id) {
		this.id = id;
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.security.IAuthorizationManager#setPriority(java.lang.String)
	 */
	public void setPriority(String priority) {
		this.priority = priority;
	}

}
