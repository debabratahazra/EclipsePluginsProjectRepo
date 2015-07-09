package com.odcgroup.workbench.security;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.util.URI;


/**
 * This interface is the central entry point for all access rights checks in the Design Studio UI.
 * Where ever there is a functionality that might need to be restricted, an AuthorizationManager should be
 * used to determine the right. 
 *
 * @author Kai Kreuzer
 *
 */
public interface IAuthorizationManager {

	public DSAuthorizationResult permissionGranted(String subjectId, DSPermission permission, IUser user);

	public DSAuthorizationResult permissionGranted(IProject project, URI uri, DSPermission permission, IUser user);

	public void setId(String attribute);

	public String getId();

	public void setPriority(String attribute);

	public String getPriority();

}
