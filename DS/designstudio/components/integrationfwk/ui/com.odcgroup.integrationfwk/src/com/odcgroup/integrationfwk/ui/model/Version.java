package com.odcgroup.integrationfwk.ui.model;

public class Version extends ExitPointType {

	private String contractName, exitPoint;

	public Version() {
		super();
	}

	// @XmlElement(name = "ContractName")
	@Override
	public String getContractName() {
		return contractName;
	}

	@Override
	// @XmlElement(name = "ExitPoint")
	public String getExitPoint() {
		return exitPoint;
	}

	// @Override
	// @XmlElement(name = "SourceType")
	@Override
	public String getSourceType() {
		return SourceType.VERSION.toString();
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
