package com.odcgroup.t24.enquiry.util;

import java.util.ArrayList;
import java.util.List;

public class EMEntityModel {

	private String name;
	private List<EMEntity> entities = new ArrayList<EMEntity>();

	public EMEntityModel(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public List<EMEntity> getEntities() {
		return entities;
	}

	public void addEntity(EMEntity entity) {
		entities.add(entity);
	}

}
