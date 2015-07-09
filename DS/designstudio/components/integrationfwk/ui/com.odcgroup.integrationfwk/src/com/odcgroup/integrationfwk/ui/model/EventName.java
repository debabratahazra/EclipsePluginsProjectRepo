package com.odcgroup.integrationfwk.ui.model;

import javax.xml.bind.annotation.XmlElement;

public class EventName {
	private String eventName;

	@XmlElement (name = "EventName")
	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}
	
}
