package com.odcgroup.workbench.core.template;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.odcgroup.template.TemplateDescriptor;

public class StandardModelsTemplateDescriptor extends TemplateDescriptor {
	
	private String modelsProjectName;
	private String genProjectName;
	private File standardModelsPom;
	private File standardGenPom;

	public StandardModelsTemplateDescriptor(String modelsProjectName, String genProjectName, File standardModelsJar, File standardModelsPom, File standardGenPom,
			 String name, String description) {
		super(standardModelsJar, name, description, null, null, Arrays.asList(modelsProjectName, genProjectName));
		this.modelsProjectName = modelsProjectName;
		this.genProjectName = genProjectName;
		this.standardModelsPom = standardModelsPom;
		this.standardGenPom = standardGenPom;
	}

	public File getStandardModelsJar() {
		return getTemplateJar();
	}
	
	public File getStandardModelsPom() {
		return standardModelsPom;
	}

	public String getStandardModelsName() {
		return modelsProjectName;
	}
	
	public String getStandardGenName() {
		return genProjectName;
	}

	public File getStandardGenPom() {
		return standardGenPom;
	}
	
	public List<String> getProjectsName() {
		return Arrays.asList(
				getStandardModelsJar().getName(),
				getStandardGenName());
	}
}
