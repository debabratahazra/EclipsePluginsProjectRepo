package com.odcgroup.t24.version;

import com.google.inject.Injector;
import com.odcgroup.t24.version.versionDSL.impl.VersionDSLPackageImpl;
import com.odcgroup.workbench.core.di.StandaloneSetupChecker;
/**
 * Initialization support for running Xtext languages without equinox extension registry
 */
public class VersionDSLStandaloneSetup extends VersionDSLStandaloneSetupGenerated {

	static private Injector injector = null;
	
	static public Injector getInjector() {
		return injector;
	}
	
    public static void doSetup() {
    	VersionDSLPackageImpl.init();
        new VersionDSLStandaloneSetup().createInjectorAndDoEMFRegistration();
    }

	@Override
	public Injector createInjector() {
		StandaloneSetupChecker.abortIfAlreadyRegistered("version");
		injector = super.createInjector();
		return injector;
	}

}
