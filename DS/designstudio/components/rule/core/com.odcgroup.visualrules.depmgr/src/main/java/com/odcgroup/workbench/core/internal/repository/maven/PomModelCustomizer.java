package com.odcgroup.workbench.core.internal.repository.maven;

import java.io.File;

import org.apache.maven.model.Dependency;
import org.apache.maven.model.Model;

import de.visualrules.artifact.maven.util.IModelCustomizer;


public class PomModelCustomizer implements IModelCustomizer {
	@Override
	public void customize(File file, Model model) {
		transformPomModel(model);
	}

	static public void transformPomModel(Model model) {

		model.setBuild(null);
		// model remove "build" section // build information is useless here
		extractParentInformation(model); // get version and groupId from parent
		model.setParent(null); // remove the parent node
		model.setPackaging(null); // remove the "ds-models" packaging
		removeTypeFromDependencies(model); // remove the type "ds-models" from all declared dependencies
	}

	static private void extractParentInformation(Model model) {
		if(model.getParent()!=null) {
			if(model.getVersion()==null && model.getParent().getVersion()!=null){
				model.setVersion(model.getParent().getVersion());
			}
			if(model.getGroupId()==null && model.getParent().getGroupId()!=null){
				model.setGroupId(model.getParent().getGroupId());
			}
		}
	}

	private static void removeTypeFromDependencies(Model model) {
		for(Dependency dependency : model.getDependencies()) {
			dependency.setType(null);
		}
	}

}