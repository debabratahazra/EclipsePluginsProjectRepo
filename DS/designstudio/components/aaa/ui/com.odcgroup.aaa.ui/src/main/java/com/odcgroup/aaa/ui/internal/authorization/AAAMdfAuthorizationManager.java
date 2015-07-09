package com.odcgroup.aaa.ui.internal.authorization;

import org.apache.commons.lang.ArrayUtils;
import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.util.URI;

import com.odcgroup.aaa.core.AAACore;
import com.odcgroup.mdf.editor.security.MdfPermission;
import com.odcgroup.workbench.security.AbstractAuthorizationManager;
import com.odcgroup.workbench.security.DSAuthorizationResult;
import com.odcgroup.workbench.security.DSPermission;
import com.odcgroup.workbench.security.IUser;

public class AAAMdfAuthorizationManager extends AbstractAuthorizationManager {

	public AAAMdfAuthorizationManager() {
		super();
	}

	@Override
	public DSAuthorizationResult permissionGranted(String subjectId, DSPermission permission, IUser user) {
		return DSAuthorizationResult.UNKNOWN;
	}

	@Override
	public DSAuthorizationResult permissionGranted(IProject project, URI uri, DSPermission permission, IUser user) {

		// DS-1773: disable "new" context menu in domain designer on aaa domain models
		if (permission == MdfPermission.CONTEXT_MENU_NEW) {
			// disable on formats
			if (uri != null && uri.path().substring(1).startsWith(AAACore.FORMATS_MODELS_PACKAGE)) {
				return DSAuthorizationResult.REJECTED;
			}
			// disable on entities
			if (uri != null && ArrayUtils.contains(AAACore.METADICT_RESOURCES, uri.path().substring(1).toLowerCase())) {
				return DSAuthorizationResult.REJECTED;
			}
		}

		// DS-1773: disable property sheets in domain designer on format domain models
		if (permission == MdfPermission.PROPERTY_SHEET_EDIT) {
			if (uri != null && uri.path().substring(1).startsWith(AAACore.FORMATS_MODELS_PACKAGE)) {
				return DSAuthorizationResult.REJECTED;
			}
			// disable on entities
			if (uri != null && ArrayUtils.contains(AAACore.METADICT_RESOURCES, uri.path().substring(1).toLowerCase())) {
				return DSAuthorizationResult.REJECTED;
			}
		}

		return DSAuthorizationResult.UNKNOWN;

	}

}
