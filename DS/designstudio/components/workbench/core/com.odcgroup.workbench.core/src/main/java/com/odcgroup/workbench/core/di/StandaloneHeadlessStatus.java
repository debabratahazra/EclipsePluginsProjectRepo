package com.odcgroup.workbench.core.di;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utility which knows about whether we are currently running in the headless
 * Generator Edition, with Xtext *StandaloneSetup() active (as opposed to the
 * normal DS IDE with UI, which shall never use StandaloneSetup, only regular
 * Xtext .ui bindings). 
 * 
 * Yeah, I know, this isn't very nice... :-( Call it a hack if you must.
 * We could do this via an extension point, or via a -D sys prop. (e.g.
 * one not available in the Generator, but set by the *.ini for the DS UIs).
 * But this works! ;-)
 * 
 * @see http://rd.oams.com/browse/DS-7054
 * @see com.odcgroup.t24.headlessgenstartup.T24HeadlessGenStartupCore
 * @see com.odcgroup.workbench.generation.cartridge.ng.di.GenerationRuntimeModule
 * @see com.odcgroup.workbench.core.xtext.ZappingResourceSet
 * @see com.odcgroup.workbench.generation.GenerationCommon
 * 
 * @author Michael Vorburger
 */
public class StandaloneHeadlessStatus {
	private static final Logger logger = LoggerFactory.getLogger(StandaloneHeadlessStatus.class);

	private boolean isInStandaloneHeadless = false;
	
	public static StandaloneHeadlessStatus INSTANCE = new StandaloneHeadlessStatus();
	
	private StandaloneHeadlessStatus() {
	}
	
	public boolean isInStandaloneHeadless() {
		return isInStandaloneHeadless;
	}
	
	public void setInStandaloneHeadless() {
		if (isInStandaloneHeadless) {
			logger.error("StandaloneHeadlessStatus.setInStandaloneHeadless() called even tough we're already isInStandaloneHeadless() - how come?");
		}
		isInStandaloneHeadless = true;
	}
	
}
