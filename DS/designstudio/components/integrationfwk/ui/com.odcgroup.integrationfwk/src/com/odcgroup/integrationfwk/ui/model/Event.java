package com.odcgroup.integrationfwk.ui.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

@XmlRootElement(name = "Event")
@XmlSeeAlso({ Version.class, Application.class, ComponentService.class,
		ExitPointType.class, TSAService.class })
public class Event {

	private ExitPointType exitPointType;
	private Overrides overrides;
	private String eventName;
	private String flowName;

	public String getEventName() {
		return eventName;
	}

	@XmlElement(name = "ExitPointType")
	public ExitPointType getExitPointType() {
		return exitPointType;
	}

	@XmlElement(name = "FlowName")
	public String getFlowName() {
		return flowName;
	}

	@XmlElement(name = "Overrides")
	public Overrides getOverrides() {
		return overrides;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public void setExitPointType(ExitPointType exitPointType) {
		this.exitPointType = exitPointType;
	}

	public void setFlowName(String flowName) {
		this.flowName = flowName;
	}

	public void setOverrides(Overrides overrides) {
		this.overrides = overrides;
	}

}
