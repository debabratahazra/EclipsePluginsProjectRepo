package com.odcgroup.integrationfwk.ui.model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

@XmlRootElement(name = "Flow")
@XmlSeeAlso({ ApplicationVersionField.class, ComponentServiceField.class,
		Field.class })
public class Flow {

	private String flowName;
	private Fields fields;
	private String baseEvent;
	private FlowAttributes attributes = new FlowAttributes();
	/** type of the exit point */
	private SourceType exitPointType;

	private boolean isComponentService;

	public boolean addAttribute(FlowAttribute attribute) {
		return attributes.addFlowAttribute(attribute);
	}

	@XmlElement(name = "Attributes")
	public FlowAttributes getAttributes() {
		return attributes;
	}

	public List<String> getAttributesAsListOfString() {
		return attributes.getFlowAttributesAsListOfString();
	}

	@XmlElement(name = "BaseEvent")
	public String getBaseEvent() {
		if (baseEvent != null) {
			return baseEvent;
		} else {
			return "";
		}
	}

	@XmlElement(name = "ExitPointType")
	public SourceType getExitPointType() {
		return exitPointType;
	}

	@XmlElement(name = "InputFields")
	public Fields getFields() {
		return fields;
	}

	public String getFlowName() {
		return flowName;
	}

	// Just for backward compatibility
	@XmlElement(name = "IsComponentService")
	public boolean isComponentService() {
		return isComponentService;
	}

	public void removeAttribute(FlowAttribute attribute) {
		attributes.removeFlowAttribute(attribute);
	}

	public void setAttributes(FlowAttributes attributes) {
		this.attributes = attributes;
	}

	public void setBaseEvent(String baseEvent) {
		this.baseEvent = baseEvent;
	}

	public void setComponentService(boolean isComponentService) {
		this.isComponentService = isComponentService;
	}

	public void setExitPointType(SourceType exitPointType) {
		this.exitPointType = exitPointType;
	}

	public void setFields(Fields fields) {
		this.fields = fields;
	}

	public void setFlowName(String flowName) {
		this.flowName = flowName;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(flowName).append(" [Attributes:");
		sb.append(attributes.toString()).append("] ").append(fields.toString());
		return sb.toString();
	}
}
