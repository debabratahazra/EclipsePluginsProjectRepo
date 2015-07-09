package com.odcgroup.t24.server.external.model;

/**
 * @author atripod
 */
public class ObjectDetail implements IExternalObject {
	
	private String name;
	
	public final String getName() {
		return name;
	}
	
	public ObjectDetail(String name) {
		this.name = (name != null) ? name : "";
	}
}
