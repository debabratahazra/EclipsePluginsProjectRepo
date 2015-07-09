package com.odcgroup.service.gen.t24.internal.data;

import java.util.HashSet;
import java.util.Set;

import com.odcgroup.service.gen.t24.internal.utils.ComponentNameHelper;
import com.odcgroup.service.gen.t24.internal.utils.StringUtils;
import com.odcgroup.service.model.component.T24MethodStereotype;

public class ElementDescriptor {
	private String name;
	private String id;
	private Set<T24MethodStereotype> stereotypes;
	private String moduleName;
	
	public ElementDescriptor(String name) {
		if(name == null)
			this.name = "";
		else
			this.name = ComponentNameHelper.getComponentName(name);
		
		this.moduleName = ComponentNameHelper.getModuleName(name);
		stereotypes = new HashSet<T24MethodStereotype>();
	}
	
	public String getModuleName() {
		return moduleName;
	}

	public String getName() {
		return name;
	}	
	
	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void addStereotype(T24MethodStereotype stereotype) {
		stereotypes.add(stereotype);
	}
	
	public boolean stereotypeIsApplied(T24MethodStereotype stereotype) {
		return stereotypes.contains(stereotype);
	}
	
	public boolean stereotypeIsApplied(String stereotype) {
		if (stereotype != null && !stereotype.equals("")) {
			return stereotypes.contains(T24MethodStereotype.getByName(stereotype));
		}
		return false;
	}

	public Set<T24MethodStereotype> getStereotypes() {
		return stereotypes;
	}
	
	public String getLowerCaseName() {
		return name.toLowerCase();
	}
	public String getLowerCamelCaseName() {
		return StringUtils.lowerInitialCharacter(getName());
	}
	public String getUpperCamelCaseName() {
    	return StringUtils.upperInitialCharacter(getName());
	}
	public String getNameOnlyInLowerCase() {
		return getLowerCaseName().replace("service", "");
	}
	public String getNameOnlyInUpperCamelCase() {
		return getUpperCamelCaseName().replace("Service", "");
	}
}
