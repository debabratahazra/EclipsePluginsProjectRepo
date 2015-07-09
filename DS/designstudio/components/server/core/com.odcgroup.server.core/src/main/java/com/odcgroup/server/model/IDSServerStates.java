package com.odcgroup.server.model;

/**
 * Server states
 * @author pkk
 */
public interface IDSServerStates {
	
	public int STATE_NOT_CONFIGURED = 0;
	public int STATE_STOPPED  = 1;
	public int STATE_STOPPING = 2;
	public int STATE_STARTED = 3;
	public int STATE_STARTING = 4;
	public int STATE_STARTED_DEBUG = 5;
	public int STATE_STARTING_DEBUG = 6;

	public int STATE_ACTIVE = 7;
	public int STATE_ACTIVE_IN_DEBUG = 8;
	public int STATE_INACTIVE = 9;
}
