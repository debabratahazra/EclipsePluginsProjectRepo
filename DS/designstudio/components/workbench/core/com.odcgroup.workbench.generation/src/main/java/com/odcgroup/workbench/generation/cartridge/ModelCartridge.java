package com.odcgroup.workbench.generation.cartridge;

public class ModelCartridge {
	private String id;
	private String name;
	private String workflow;
	private String pimName;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPimName() {
		return pimName;
	}
	
	public void setPimName(String pimName) {
		this.pimName = pimName;
	}
	
	public String getWorkflow() {
		return workflow;
	}
	
	public void setWorkflow(String workflow) {
		this.workflow = workflow;
	}
}
