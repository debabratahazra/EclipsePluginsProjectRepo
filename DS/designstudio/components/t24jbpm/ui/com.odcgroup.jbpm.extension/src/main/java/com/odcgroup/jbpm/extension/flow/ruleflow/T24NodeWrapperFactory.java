package com.odcgroup.jbpm.extension.flow.ruleflow;

import org.drools.eclipse.flow.ruleflow.core.HumanTaskNodeWrapper;
import org.drools.eclipse.flow.ruleflow.core.NodeWrapperFactory;

/**
 * @author phanikumark
 *
 */
public class T24NodeWrapperFactory extends NodeWrapperFactory {
	

	/* (non-Javadoc)
	 * @see org.drools.eclipse.flow.ruleflow.core.NodeWrapperFactory#getHumanTaskNodeWrapper()
	 */
	public HumanTaskNodeWrapper getHumanTaskNodeWrapper() {
		return new T24HumanTaskNodeWrapper();
	}

}
