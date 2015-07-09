package com.odcgroup.workbench.core.template;

import java.io.File;
import java.util.List;

import com.odcgroup.template.ITemplateProvider;

public interface ITemplateProvider2 extends ITemplateProvider {

	public File resolveArtifact(
			String groupId, 
			String artifactId,
			String version, 
			String classifier, 
			String type);

}
