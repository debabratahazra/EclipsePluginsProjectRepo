package com.odcgroup.visualrules.integration.template;

import com.odcgroup.mdf.metamodel.MdfModelElement;

import de.visualrules.integration.model.Parameter;

public class Template {
	
	protected String name;
	protected String type;
	protected String qualifiedTemplateName;
	
	public Template() {}

	public void init(MdfModelElement modelElement) {}

	public void setName(String name) {
		this.name = name;
	}

	public void setQualifiedTemplateName(String qualifiedTemplateName) {
		this.qualifiedTemplateName = qualifiedTemplateName;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Parameter[] getParams() {
		return null;
	}

	public String getDesc() {
		return null;
	}
	
	public String getName() {
		return name;
	}

	public String getQualifiedTemplateName() {
		return qualifiedTemplateName;
	}

	public String getType() {
		return type;
	}
}
