package com.odcgroup.t24.enquiry.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

/**
 * This class holds information about an entity
 */
public class EMEntity {
	
	private String name;
	private String t24Name;

	private List<EMProperty> properties = new ArrayList<EMProperty>();
	private List<EMTerm> terms = new ArrayList<EMTerm>();

	public EMEntity(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public List<EMProperty> getProperties() {
		return properties;
	}

	public void addProperty(EMProperty property) {
		properties.add(property);
	}
	
	public void addProperties(List<EMProperty> props) {
		if (props != null && props.size() > 0) { 
			properties.addAll(props);
		}
	}
	
	public List<EMTerm> getVocabularyTerms() {
		return terms;
	}

	public void addVocabularyTerm(EMTerm term) {
		terms.add(term);
	}
	
	public String getT24Name() {
		return t24Name;
	}

	public void setT24Name(String t24Name) {
		if (!StringUtils.isEmpty(t24Name)) {
			this.t24Name = t24Name;
		} else {
			this.t24Name = StringUtils.replace(name, "_", ".");
		}
	}
	
	@Override
	public String toString() {
		return "Name=" + name + ", t24name=" + t24Name + ", properties=" + properties.toString() + ", terms=" + terms.toString();
	}
	
}
