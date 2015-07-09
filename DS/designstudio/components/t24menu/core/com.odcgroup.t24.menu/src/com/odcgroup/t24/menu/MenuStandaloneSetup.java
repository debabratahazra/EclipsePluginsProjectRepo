package com.odcgroup.t24.menu;

import com.google.inject.Injector;
import com.odcgroup.edge.t24ui.impl.T24UIPackageImpl;

public class MenuStandaloneSetup extends MenuStandaloneSetupGenerated {

	// @Generated
	public static void doSetup() {
		new MenuStandaloneSetup().createInjectorAndDoEMFRegistration();
	}

	@Override
	public Injector createInjectorAndDoEMFRegistration() {
		
		// as well as any other ecore model packages needed:
		// in our case typically e.g. this one just to safe:
		T24UIPackageImpl.init();
		// but the ones from ecore packages from the DSL langs (such as
		// MenuPackage, EnquiryPackage, ...) normally don't have to be repeated
		// because as part of the setup above e.g. the *JavaValidator already
		// does that.
		
		return super.createInjectorAndDoEMFRegistration();
	}
	
}

