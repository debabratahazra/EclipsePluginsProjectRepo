package com.odcgroup.integrationfwk.ui.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


/**
 * Model provider class, which holds all the event info (models).  
 * @author eswaranmuthu
 *
 */
@XmlRootElement(name = "EventFlow")
public class EventFlow {

	
	private Event event;
	
	@XmlElement (name ="Event")
	public Event getEvent() {
		return event;
	}
	public void setEvent(Event event) {
		this.event = event;
	}
	@XmlElement (name ="Flow")
	public Flow getFlow() {
		return flow;
	}
	public void setFlow(Flow flow) {
		this.flow = flow;
	}

	private Flow  flow;
}
