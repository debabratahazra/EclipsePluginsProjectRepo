package com.odcgroup.integrationfwk.ui.model;

import java.util.Arrays;
import java.util.List;

/**
 * Define the flow attributes
 * 
 * @author sjunejo
 * 
 */
public enum FlowAttribute {

	/**
	 * INCLUDE_BEFORE_IMAGE Attribute
	 */
	INCLUDE_BEFORE_IMAGE(
			"Include Before Image",
			Arrays.asList(new SourceType[] { SourceType.APPLICATION,
					SourceType.TSA_SERVICE, SourceType.VERSION }),
			"Selecting this option would cause IF runtime to include before image of the existing record in the event xml for current flow"),

	/**
	 * PROCESS_ONLY_UPDATES Attribute
	 */
	PROCESS_ONLY_UPDATES(
			"Process Updates Only",
			Arrays.asList(new SourceType[] { SourceType.APPLICATION,
					SourceType.TSA_SERVICE, SourceType.VERSION }),
			"Selecting this option would cause IF runtime to include only effected data of the transaction in event xml for current flow"),

	/**
	 * INCLUDE DIGITAL SIGNATURE Attribute
	 */
	INCLUDE_DIGITAL_SIGNATURE(
			"Include Digital Signature",
			Arrays.asList(new SourceType[] { SourceType.APPLICATION,
					SourceType.TSA_SERVICE, SourceType.VERSION,
					SourceType.COMPONENT_SERVICE }),
			"Selecting this option would cause IF runtime to digitally sign the events generated for this flow");

	private String label;
	private List<SourceType> allowedSources;
	private String toolTip;

	private FlowAttribute(String label, List<SourceType> allowedSources,
			String toolTip) {
		this.label = label;
		this.allowedSources = allowedSources;
		this.toolTip = toolTip;
	}

	public List<SourceType> getAllowedSources() {
		return allowedSources;
	}

	public String getLabel() {
		return label;
	}

	public String getToolTip() {
		return toolTip;
	}

	public boolean isExitPointAllowed(Flow flow) {
		SourceType exitPointType = flow.getExitPointType();
		// If Null then check for couple if things
		if (exitPointType == null) {
			if (flow.isComponentService()) {
				return false;
			} else {
				return true;
			}
		}
		return getAllowedSources().contains(exitPointType);
	}

}
