package com.odcgroup.jbpm.extension.flow.ruleflow;

import org.drools.eclipse.flow.ruleflow.core.HumanTaskNodeWrapper;
import org.drools.eclipse.flow.ruleflow.core.WorkItemWrapper;
import org.eclipse.ui.views.properties.IPropertyDescriptor;

import com.odcgroup.jbpm.extension.flow.ruleflow.properties.HumanTaskCommentPropertyDescriptor;
import com.odcgroup.jbpm.extension.flow.ruleflow.properties.HumanTaskResultMappingPropertyDescriptor;

/**
 * @author phanikumark
 *
 */
public class T24HumanTaskNodeWrapper extends HumanTaskNodeWrapper {

	private static final long serialVersionUID = -4854338846776625111L;
	
	public static final String COMMENT = "Comment";
	public static final String RESULT_MAPPING_DISPLAY_NAME = "Result Mapping";

	@Override
	protected void initDescriptors() {
		super.initDescriptors();
		IPropertyDescriptor desc = new HumanTaskCommentPropertyDescriptor(COMMENT, COMMENT,this);
		replacePropertyDescriptor(COMMENT, desc);
		desc = new HumanTaskResultMappingPropertyDescriptor(WorkItemWrapper.RESULT_MAPPING, RESULT_MAPPING_DISPLAY_NAME, this);
		replacePropertyDescriptor(RESULT_MAPPING_DISPLAY_NAME, desc);
	}
	
	/**
	 * @param name
	 * @param descriptor
	 */
	private void replacePropertyDescriptor(String name, IPropertyDescriptor descriptor) {
		int index = getPropertyDescriptorIndexByName(name);
		if (index >= 0) {
			for (int t = index; t < descriptors.length - 1; t++) {
				descriptors[t] = descriptors[t + 1];
			}
			descriptors[descriptors.length-1] = descriptor;
		}
	}

	/**
	 * @param name
	 * @return
	 */
	private int getPropertyDescriptorIndexByName(String name) {
		for(int i=0;i<descriptors.length;i++) {
			if (name.equals(descriptors[i].getDisplayName())) {
				return i;
			}
		}
		return -1;
	}

}
