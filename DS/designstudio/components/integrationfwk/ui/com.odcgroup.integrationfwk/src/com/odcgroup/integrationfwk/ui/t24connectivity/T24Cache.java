package com.odcgroup.integrationfwk.ui.t24connectivity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class T24Cache {

	private static T24Cache t24Cache;

	public static T24Cache getT24Cache() {
		if (t24Cache == null) {
			t24Cache = new T24Cache();
			return t24Cache;
		} else {
			return t24Cache;
		}
	}

	private final Map<String, StudioProjectCache> fullT24Cache = new HashMap<String, StudioProjectCache>();

	private T24Cache() {
	}

	public void addApplicationList(List<String> applicationList,
			String projectName) {
		StudioProjectCache studioProjectCache = getT24Cache(projectName);
		if (studioProjectCache != null) {
			studioProjectCache.setApplicationList(applicationList);
		} else {
			studioProjectCache = new StudioProjectCache();
			studioProjectCache.setApplicationList(applicationList);
			addT24Cache(projectName, studioProjectCache);
		}
	}

	public void addApplicationVersion(List<String> applicationVersion,
			String projectName) {
		StudioProjectCache studioProjectCache = getT24Cache(projectName);
		if (studioProjectCache != null) {
			studioProjectCache.setApplicationVersionList(applicationVersion);
		} else {
			studioProjectCache = new StudioProjectCache();
			studioProjectCache.setApplicationVersionList(applicationVersion);
			addT24Cache(projectName, studioProjectCache);
		}
	}

	public void addExitPoint(List<String> exitPoint, String projectName) {
		StudioProjectCache studioProjectCache = getT24Cache(projectName);
		if (studioProjectCache != null) {
			studioProjectCache.setExitPoint(exitPoint);
		} else {
			studioProjectCache = new StudioProjectCache();
			studioProjectCache.setExitPoint(exitPoint);
			addT24Cache(projectName, studioProjectCache);
		}
	}

	public void addOverrides(List<String> overrides, String projectName) {
		StudioProjectCache studioProjectCache = getT24Cache(projectName);
		if (studioProjectCache != null) {
			studioProjectCache.setOverrides(overrides);
		} else {
			studioProjectCache = new StudioProjectCache();
			studioProjectCache.setOverrides(overrides);
			addT24Cache(projectName, studioProjectCache);
		}
	}

	private void addT24Cache(String projectName,
			StudioProjectCache studioProjectCache) {
		fullT24Cache.put(projectName, studioProjectCache);
	}

	/**
	 * Helps to add the given list of tsa services into cache using the given
	 * project name.
	 * 
	 * @param tsaServiceList
	 * @param projectName
	 */
	public void addTSAServiceList(List<String> tsaServiceList,
			String projectName) {
		StudioProjectCache studioProjectCache = getT24Cache(projectName);
		if (studioProjectCache == null) {
			studioProjectCache = new StudioProjectCache();
		}
		studioProjectCache.setTsaServiceList(tsaServiceList);
		addT24Cache(projectName, studioProjectCache);
	}

	public void addVersionList(List<String> versionList, String projectName) {
		StudioProjectCache studioProjectCache = getT24Cache(projectName);
		if (studioProjectCache != null) {
			studioProjectCache.setVersionList(versionList);
		} else {
			studioProjectCache = new StudioProjectCache();
			studioProjectCache.setVersionList(versionList);
			addT24Cache(projectName, studioProjectCache);
		}
	}

	/**
	 * Helps to clear the cache data for respective given project name.
	 * 
	 * @param projectName
	 *            - name of the project.
	 */
	public void clearCache(String projectName) {
		fullT24Cache.remove(projectName);
	}

	public List<String> getApplicationList(String projectName) {
		StudioProjectCache studioProjectCache = getT24Cache(projectName);
		if (studioProjectCache == null) {
			return null;
		}
		if (studioProjectCache.getApplicationList() != null) {
			return studioProjectCache.getApplicationList();
		}
		return null;
	}

	public List<String> getApplicationVersionList(String projectName) {
		StudioProjectCache studioProjectCache = getT24Cache(projectName);
		if (studioProjectCache == null) {
			return null;
		}
		if (studioProjectCache.getApplicationVersionList() != null) {
			return studioProjectCache.getApplicationVersionList();
		}
		return null;
	}

	public List<String> getExitPointList(String projectName) {
		StudioProjectCache studioProjectCache = getT24Cache(projectName);
		if (studioProjectCache == null) {
			return null;
		}
		if (studioProjectCache.getExitPoint() != null) {
			return studioProjectCache.getExitPoint();
		}
		return null;
	}

	public List<String> getOverridesList(String projectName) {
		StudioProjectCache studioProjectCache = getT24Cache(projectName);
		if (studioProjectCache == null) {
			return null;
		}
		if (studioProjectCache.getOverrides() != null) {
			return studioProjectCache.getOverrides();
		}
		return null;
	}

	private StudioProjectCache getT24Cache(String projectName) {
		return fullT24Cache.get(projectName);
	}

	/**
	 * Helps to get the TSA services list associated with given project cache.
	 * 
	 * @param projectName
	 * @return list of TSA Services.
	 */
	public List<String> getTsaServicesList(String projectName) {
		StudioProjectCache studioProjectCache = getT24Cache(projectName);
		if (studioProjectCache == null) {
			return null;
		}
		return studioProjectCache.getTsaServiceList();
	}

	public List<String> getVersionList(String projectName) {
		StudioProjectCache studioProjectCache = getT24Cache(projectName);
		if (studioProjectCache == null) {
			return null;
		}
		if (studioProjectCache.getVersionList() != null) {
			return studioProjectCache.getVersionList();
		}
		return null;
	}
}
