package com.odcgroup.workbench.core.di;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Checks that the Xtext *StandaloneSetup utils are NEVER, EVER, used from
 * within a full Eclipse (UI). *StandaloneSetup is only allowed from the
 * Generator Edition, or for non-OSGi Java SE simple unit tests.
 * 
 * @see https://bugs.eclipse.org/bugs/show_bug.cgi?id=427034
 * @see http://rd.oams.com/browse/DS-7055
 * 
 * @author Michael Vorburger
 */
public class StandaloneSetupChecker {
	private static final Logger logger = LoggerFactory.getLogger(StandaloneSetupChecker.class);

	// If this happens to you during development, you probably
	// have e.g. the com.odcgroup.t24.headlessgenstartup plug-in
	// open in your workspace?  Close it!
	private static final String MSG = "Thou shalt never, ever, call a *StandaloneSetup when running within the Eclipse IDE (UI)"; 

	private static final String SWT_CLASS = "org.eclipse.swt.SWTError";
	
	/**
	 * You must add call to this helper as the first line
	 * of createInjector() in the *StandaloneSetup of every Xtext language plug-in.
	 * 
	 * @throws Error if usage of *StandaloneSetup is not allowed in this environment.
	 */
//	public static void abortIfStandaloneNotAllowed() throws Error {
//		 if (isInEclipseWithUI()) {
	public static void abortIfAlreadyRegistered(String extension) throws Error {
		// we do NOT want to use EMFPlugin.IS_ECLIPSE_RUNNING here, because
		// this has to reliable work (not complain) in a scenario where *StandaloneSetup
		// is used in a headless Eclipse product - which IS_ECLIPSE_RUNNING
		// of course (but doesn't have UI, so no XtextBuilder).
/*
 * DEACTIVATED ON TRUNK - WILL BE RETRIED ON FEATURE BRANCH
 * 		
		if (Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().get(extension) != null
		 || org.eclipse.xtext.resource.IResourceServiceProvider.Registry.INSTANCE.getExtensionToFactoryMap().get(extension) != null)
		{
			// Just to be sure it's really noticed.. ;-)
			for (int i = 0; i < 17; i++) {
				logger.error(MSG);
				System.err.println(MSG);
				System.out.println(MSG);
			}
			throw new Error(MSG);
		}
*/		
	}

//	/** @see also http://aniszczyk.org/2007/07/24/am-i-headless/ ... 
//	 *  but to be able to run in non-OSGi Java SE simple unit tests,
//	 *  or say from a Maven plug-in, this seemed like a better idea...
//	 *  but doesn't actually work, in OSGi - because SWT won't be the CP of a the non.ui plug-in.. :( */
//	private static boolean isInEclipseWithUI() {
//		try {
//			Class.forName(SWT_CLASS);
//			return true;
//		} catch (Exception e) {
//			// Good!
//			return false;
//		}
//	}

}
