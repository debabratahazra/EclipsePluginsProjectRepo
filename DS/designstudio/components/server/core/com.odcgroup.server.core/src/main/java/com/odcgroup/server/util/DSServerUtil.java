package com.odcgroup.server.util;


import com.odcgroup.server.model.IDSServerStates;

/**
 *
 * @author pkk
 *
 */
public class DSServerUtil {
	
	/**
	 * @param state
	 * @return
	 */
	public static String getServerState(int state) {
		switch(state) {
			case IDSServerStates.STATE_NOT_CONFIGURED : return "Not properly configured";
			case IDSServerStates.STATE_STOPPED : return "Stopped";
			case IDSServerStates.STATE_STOPPING : return "Stopping ...";
			case IDSServerStates.STATE_STARTED : return "Started";
			case IDSServerStates.STATE_STARTING : return "Starting ...";
			case IDSServerStates.STATE_STARTED_DEBUG : return "Started in debug";
			case IDSServerStates.STATE_STARTING_DEBUG : return "Starting in debug ...";
			case IDSServerStates.STATE_ACTIVE : return "Active";
			case IDSServerStates.STATE_ACTIVE_IN_DEBUG : return "Active in debug";
			case IDSServerStates.STATE_INACTIVE : return "Inactive";
			default : return "Invalid state";
		}
	}

}
