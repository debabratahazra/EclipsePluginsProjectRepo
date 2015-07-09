package com.odcgroup.pageflow.compare.internal.engine;

import com.odcgroup.workbench.compare.gmf.engine.GMFMatchEngine;

/** This is an implementation of an EMF Compare match engine that is targeted at pageflow model files.
 *  It extends the {@link GMFMatchEngine} in a way that the root pageflow node is always considered
 *  as a match, such that the contents of two different pageflows can still be compared.
 *  (Otherwise you would only see two differences: one pageflow removed, the other one added).
 * 
 * @author Kai Kreuzer
 *
 */
public class PageflowMatchEngine extends GMFMatchEngine {
	
}
