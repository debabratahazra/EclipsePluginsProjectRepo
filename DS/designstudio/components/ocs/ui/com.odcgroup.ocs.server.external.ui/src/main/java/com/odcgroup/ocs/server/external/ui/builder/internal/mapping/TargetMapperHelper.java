package com.odcgroup.ocs.server.external.ui.builder.internal.mapping;

import org.eclipse.core.resources.IProject;

import com.odcgroup.ocs.server.external.builder.internal.mapping.TargetMapper;
import com.odcgroup.ocs.server.external.model.IExternalServer;
import com.odcgroup.ocs.server.external.ui.OCSServerUIExternalCore;


public class TargetMapperHelper {

	public static final String GEN_SUFFIX = "-gen";

	public static TargetMapper getTargetMapper(IProject effectiveProject) {
		IExternalServer externalServer = OCSServerUIExternalCore.getDefault().getExternalServer();
		if (effectiveProject.getName().endsWith(GEN_SUFFIX)) {
			return new GenTargetMapper(externalServer, effectiveProject);
		} else {
			return new NonGenTargetMapper(externalServer, effectiveProject);
		}
	}
}

