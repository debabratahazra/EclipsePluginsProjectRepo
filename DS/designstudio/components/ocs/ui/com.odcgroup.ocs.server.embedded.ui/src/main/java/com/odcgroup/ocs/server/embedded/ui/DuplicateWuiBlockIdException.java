package com.odcgroup.ocs.server.embedded.ui;

public class DuplicateWuiBlockIdException extends Exception {

	private static final long serialVersionUID = 2878623694297792169L;

	public DuplicateWuiBlockIdException(String id, String location1, String location2) {
		super("The block id \"" + id + "\" is duplicated (location1=" + location1 + ", " +
				"location2=" + location2 + ")");
	}
}
