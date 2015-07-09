package com.odcgroup.integrationfwk.ui.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

public class Overrides {

	private List<String> overrides = new ArrayList<String>();

	
	@XmlElement (name ="Override")
	public List<String> getOverrides() {
		return overrides;
	}

	public void setOverrides(List<String> overrides) {
		this.overrides = overrides;
	}
	public boolean addOverride(String override)
	{
		if(overrides.contains(override))
		{
			return false;
		}
		else
		{
			overrides.add(override);
			return true;
		}
	}
	public void removeOverride(String override)
	{
		overrides.remove(override);
	}
	
}
