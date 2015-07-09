package com.odcgroup.aaa.core.internal.authorization;

import org.apache.commons.lang.ArrayUtils;
import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.util.URI;

import com.odcgroup.aaa.core.AAACore;
import com.odcgroup.workbench.security.AbstractAuthorizationManager;
import com.odcgroup.workbench.security.DSAuthorizationResult;
import com.odcgroup.workbench.security.DSPermission;
import com.odcgroup.workbench.security.IUser;

public class AAAAuthorizationManager extends AbstractAuthorizationManager {

	public AAAAuthorizationManager() {
		super();
	}

	public DSAuthorizationResult permissionGranted(String subjectId, DSPermission permission, IUser user) {
		return DSAuthorizationResult.UNKNOWN;
	}

	public DSAuthorizationResult permissionGranted(IProject project, URI uri, DSPermission permission, IUser user) {

		// DS-1773: disable edit mode on meta dict domain models
		if (permission == DSPermission.EDIT_MODEL) {
			if (uri != null && ArrayUtils.contains(AAACore.METADICT_RESOURCES, uri.path().substring(1).toLowerCase())) {
				return DSAuthorizationResult.REJECTED;
			}
		}

		return DSAuthorizationResult.UNKNOWN;
	}

}
