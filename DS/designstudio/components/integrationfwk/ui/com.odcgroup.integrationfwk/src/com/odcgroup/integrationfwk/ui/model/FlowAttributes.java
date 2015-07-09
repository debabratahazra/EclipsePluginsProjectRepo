package com.odcgroup.integrationfwk.ui.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;

/*
 * FlowAttributes class is only to prepare XML entries
 */
@XmlSeeAlso({ FlowAttribute.class })
public class FlowAttributes {

	private List<FlowAttribute> attributes = new ArrayList<FlowAttribute>();

	public boolean addFlowAttribute(FlowAttribute attribute) {
		if (attributes.contains(attribute)) {
			return false;
		} else {
			attributes.add(attribute);
			return true;
		}
	}

	public boolean contains(FlowAttribute attribute) {
		return attributes.contains(attribute);
	}

	@XmlElement(name = "Attribute")
	public List<FlowAttribute> getFlowAttributes() {
		return attributes;
	}

	public List<String> getFlowAttributesAsListOfString() {
		List<String> attributes = new ArrayList<String>();
		for (FlowAttribute attribute : this.attributes) {
			attributes.add(attribute.toString());
		}
		return attributes;
	}

	public void removeFlowAttribute(FlowAttribute attribute) {
		attributes.remove(attribute);
	}

	public void setAttributes(List<FlowAttribute> attributes) {
		this.attributes = attributes;
	}

	@Override
	public String toString() {
		StringBuilder result = new StringBuilder("");
		for (FlowAttribute attribute : attributes) {
			result.append(attribute.toString()).append(",");
		}
		return result.toString();
	}
}
