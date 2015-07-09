package com.odcgroup.process.compare.internal.engine;

import com.odcgroup.workbench.compare.gmf.engine.GMFMatchEngine;


/** This is an implementation of an EMF Compare match engine that is targeted at workflow model files.
 *  It extends the {@link GMFMatchEngine} in a way that the root workflow node is always considered
 *  as a match, such that the contents of two different workflows can still be compared.
 *  (Otherwise you would only see two differences: one workflow removed, the other one added).
 * 
 * @author Kai Kreuzer
 *
 */
public class ProcessMatchEngine extends GMFMatchEngine {
	
}
