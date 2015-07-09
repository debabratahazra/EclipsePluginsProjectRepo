package com.odcgroup.t24.menu.tests;

import org.eclipse.emf.eson.EFactoryStandaloneSetup;

import com.google.inject.Injector;
import com.odcgroup.t24.enquiry.EnquiryStandaloneSetup;
import com.odcgroup.t24.menu.MenuInjectorProvider;
import com.odcgroup.t24.version.VersionDSLStandaloneSetup;

/**
 * MenuInjectorProvider which (correctly!) initializes dependent languages as
 * well.
 * 
 * @see http://rd.oams.com/browse/DS-7626
 * @see https://bugs.eclipse.org/bugs/show_bug.cgi?id=439451
 * 
 * @author Michael Vorburger
 */
public class MenuWithDependenciesInjectorProvider extends MenuInjectorProvider {

	@Override
	protected Injector internalCreateInjector() {
		VersionDSLStandaloneSetup.doSetup();
		EFactoryStandaloneSetup.doSetup();
		EnquiryStandaloneSetup.doSetup();

		return super.internalCreateInjector();
	}
}
