package com.odcgroup.workbench.editors.properties.util;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author pkk
 *
 */
public class SectionMultiplePropertyRowHelper implements SectionPropertyHelper {
	

	protected String label;
	
	/**
	 * @param label
	 */
	public SectionMultiplePropertyRowHelper(String label){
		this.label = label;
	}
	
	/**
	 * map should contain only SectionAttributePropertyHelper, SectionListReferencePropertyHelper
	 * does not support SectionMultiplePropertyRowHelper
	 */
	protected Map<String, SectionPropertyHelper> featureMap = new LinkedHashMap<String, SectionPropertyHelper>();
	
	/**
	 * @param groupName
	 * @param feature
	 */
	public void addFeature(String groupName, SectionPropertyHelper feature) {
		if (feature instanceof SectionMultiplePropertyRowHelper){
			return;
		}
		featureMap.put(groupName, feature);
	}
	
	/**
	 * @return
	 */
	public Map<String, SectionPropertyHelper> getMappedFeatures(){
		return featureMap;
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.editors.properties.util.SectionPropertyHelper#getLabel()
	 */
	public String getLabel() {
		return label;
	}

}
