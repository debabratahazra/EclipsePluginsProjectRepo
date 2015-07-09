package com.odcgroup.integrationfwk.ui.t24connectivity;

import java.util.List;

public class StudioProjectCache {

	private List<String> applicationList;
	private List<String> versionList;
	private List<String> exitPoint;
	private List<String> overrides;
	/** list of available TSA Services */
	private List<String> tsaServiceList;

	private List<String> applicationVersionList;

	public List<String> getApplicationList() {
		return applicationList;
	}

	public List<String> getApplicationVersionList() {
		return applicationVersionList;
	}

	public List<String> getExitPoint() {
		return exitPoint;
	}

	public List<String> getOverrides() {
		return overrides;
	}

	public List<String> getTsaServiceList() {
		return tsaServiceList;
	}

	public List<String> getVersionList() {
		return versionList;
	}

	public void setApplicationList(List<String> applicationList) {
		this.applicationList = applicationList;
	}

	public void setApplicationVersionList(List<String> applicationVersionList) {
		this.applicationVersionList = applicationVersionList;
	}

	public void setExitPoint(List<String> exitPoint) {
		this.exitPoint = exitPoint;
	}

	public void setOverrides(List<String> overrides) {
		this.overrides = overrides;
	}

	public void setTsaServiceList(List<String> tsaServiceList) {
		this.tsaServiceList = tsaServiceList;
	}

	public void setVersionList(List<String> versionList) {
		this.versionList = versionList;
	}
}
