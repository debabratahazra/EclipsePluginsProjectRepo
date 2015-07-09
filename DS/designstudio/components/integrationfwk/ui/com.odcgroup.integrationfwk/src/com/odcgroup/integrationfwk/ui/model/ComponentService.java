package com.odcgroup.integrationfwk.ui.model;

public class ComponentService extends ExitPointType {

	private String contractName, exitPoint;

	@Override
	public String getContractName() {
		return contractName;
	}

	@Override
	public String getExitPoint() {
		return exitPoint;
	}

	@Override
	public String getSourceType() {
		return SourceType.COMPONENT_SERVICE.toString();
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
