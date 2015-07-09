package com.odcgroup.workbench.core.internal.authorization;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.util.URI;

import com.odcgroup.workbench.security.AbstractAuthorizationManager;
import com.odcgroup.workbench.security.DSAuthorizationResult;
import com.odcgroup.workbench.security.DSPermission;
import com.odcgroup.workbench.security.IUser;

/**
 * 
 * The DS core authorization manager simply allows every access; once we have more fine grained permission rules
 * in place, this can be changed. 
 *
 * @author Kai Kreuzer
 *
 */
public class CoreAuthorizationManager extends AbstractAuthorizationManager {

	public CoreAuthorizationManager() {
		super();
	}

	public DSAuthorizationResult permissionGranted(String subjectId, DSPermission permission, IUser user) {
		return DSAuthorizationResult.GRANTED;
	}

	public DSAuthorizationResult permissionGranted(IProject project, URI uri, DSPermission permission, IUser user) {
		return DSAuthorizationResult.GRANTED;
	}

}
