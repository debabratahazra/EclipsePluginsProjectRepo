package com.odcgroup.integrationfwk.ui.model;

public class Application extends ExitPointType {

	private String contractName, exitPoint;

	@Override
	// @XmlElement(name = "ContractName")
	public String getContractName() {
		return contractName;
	}

	@Override
	// @XmlElement(name = "ExitPoint")
	public String getExitPoint() {
		return exitPoint;
	}

	//
	// @XmlElement(name = "SourceType")
	@Override
	public String getSourceType() {
		return SourceType.APPLICATION.toString();
	}

	@Override
	public void setContractName(String name) {
		contractName = name;
	}

	@Override
	public void setExitPoint(String exitPoint) {
		this.exitPoint = exitPoint;
	}
}
