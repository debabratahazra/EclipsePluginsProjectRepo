package com.odcgroup.integrationfwk.ui.model;

/**
 * Holds the business content of TSA Service type of exit point.
 * 
 * @author sbharathraja
 * 
 */
public class TSAService extends ExitPointType {

	private String contractName;

	private String exitPoint;

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
		return SourceType.TSA_SERVICE.toString();
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
