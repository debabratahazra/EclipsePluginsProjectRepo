package com.odcgroup.iris.rim.generation.mappers;

import org.eclipse.emf.ecore.EObject;

import com.odcgroup.workbench.generation.cartridge.ng.ModelLoader;
import com.temenos.interaction.rimdsl.rim.Event;
import com.temenos.interaction.rimdsl.rim.RimFactory;
import com.temenos.interaction.rimdsl.rim.RimPackage;

/**
 * Constructs events.
 *
 * @author aphethean
 */
public class EventFactory {
	
	private Event getEvent;
	private Event putEvent;
	private Event postEvent;
	private Event deleteEvent;
	
	private ModelLoader modelLoader;
	private EObject context;
	
	// package local (for now)
	EventFactory(ModelLoader loader, EObject context) {
		this.modelLoader = loader;
		this.context = context;
	}
	
	public Event createGET() {
		if (getEvent == null) {
			getEvent = lookupOrCreateNewEvent("GET");
		}
		return getEvent;
	}

	public Event createPUT() {
		if (putEvent == null) {
			putEvent = lookupOrCreateNewEvent("PUT");
		}
		return putEvent;
	}

	public Event createPOST() {
		if (postEvent == null) {
			postEvent = lookupOrCreateNewEvent("POST");
		}
		return postEvent;
	}

	public Event createDELETE() {
		if (deleteEvent == null) {
			deleteEvent = lookupOrCreateNewEvent("DELETE");
		}
		return deleteEvent;
	}
	
	protected Event lookupOrCreateNewEvent(String name) {
		// lookup the reference to the common event definition
		Event event = (Event) modelLoader.getNamedEObjectOrProxy(context, "common.HTTPEvents." + name, RimPackage.Literals.EVENT, false, false); 
		if (event == null) {
			event = RimFactory.eINSTANCE.createEvent();
			event.setHttpMethod(name);
			event.setName(name);
		}
		return event;
	}

}
