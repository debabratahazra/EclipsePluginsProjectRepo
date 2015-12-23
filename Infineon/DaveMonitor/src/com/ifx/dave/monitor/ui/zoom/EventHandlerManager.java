package com.ifx.dave.monitor.ui.zoom;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.Node;

import java.util.ArrayList;
import java.util.List;

public class EventHandlerManager {
	private final Node target;
	private final List<Registration<? extends Event>> registrations;

	public EventHandlerManager( Node target ) {
		this.target = target;
		registrations = new ArrayList<Registration<? extends Event>>();
	}

	public Node getTarget() {
		return target;
	}

	/**
	 * Adds an event registration, optionally adding it to the target immediately.
	 */
	public <T extends Event> void addEventHandler( boolean addImmediately, EventType<T> type,
	                                               EventHandler<? super T> handler ) {
		Registration<T> reg = new Registration<T>( type, handler );
		registrations.add( reg );
		if ( addImmediately ) {
			target.addEventHandler( type, handler );
			reg.setRegistered( true );
		}
	}

	/**
	 * Adds an event registration, adding it to the target immediately.
	 */
	public <T extends Event> void addEventHandler( EventType<T> type,
	                                               EventHandler<? super T> handler ) {
		addEventHandler( true, type, handler );
	}

	/**
	 * Add all currently unadded handlers (this method will not re-add).
	 */
	@SuppressWarnings( "unchecked" )
	public void addAllHandlers() {
		for ( Registration<?> registration : registrations ) {
			if ( !registration.isRegistered() ) {
				target.addEventHandler( (EventType<Event>) registration.getType(),
				                        (EventHandler<Event>) registration.getHandler() );
				registration.setRegistered( true );
			}
		}
	}

	/**
	 * Remove all currently added handlers.
	 */
	@SuppressWarnings( "unchecked" )
	public void removeAllHandlers() {
		for ( Registration<?> registration : registrations ) {
			if ( registration.isRegistered() ) {
				target.removeEventHandler( (EventType<Event>) registration.getType(),
				                           (EventHandler<Event>) registration.getHandler() );
				registration.setRegistered( false );
			}
		}
	}

	private static class Registration<T extends Event> {
		private final EventType<T> type;
		private final EventHandler<? super T> handler;
		private boolean registered = false;

		public Registration( EventType<T> type, EventHandler<? super T> handler ) {
			if ( type == null )
			  throw new IllegalArgumentException( "type cannot be null" );
			if ( handler == null )
			  throw new IllegalArgumentException( "handler cannot be null" );

			this.type = type;
			this.handler = handler;
		}

		public EventType<T> getType() {
			return type;
		}

		public EventHandler<? super T> getHandler() {
			return handler;
		}

		public boolean isRegistered() {
			return registered;
		}

		public void setRegistered( boolean registered ) {
			this.registered = registered;
		}
	}
}
