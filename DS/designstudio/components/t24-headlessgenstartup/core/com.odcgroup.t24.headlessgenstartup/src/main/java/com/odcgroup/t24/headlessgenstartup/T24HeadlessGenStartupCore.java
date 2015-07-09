package com.odcgroup.t24.headlessgenstartup;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import org.eclipse.emf.eson.EFactoryStandaloneSetup;
import com.odcgroup.domain.DomainStandaloneSetup;
import com.odcgroup.service.model.ComponentStandaloneSetup;
import com.odcgroup.t24.enquiry.EnquiryStandaloneSetup;
import com.odcgroup.t24.menu.MenuStandaloneSetup;
import com.odcgroup.t24.version.VersionDSLStandaloneSetup;
import com.odcgroup.workbench.core.di.StandaloneHeadlessStatus;
import com.temenos.interaction.rimdsl.RIMDslStandaloneSetup;

public class T24HeadlessGenStartupCore implements BundleActivator {

	private static BundleContext context;

	public static BundleContext getContext() {
		return context;
	}

	public void start(BundleContext bundleContext) throws Exception {
		StandaloneHeadlessStatus.INSTANCE.setInStandaloneHeadless();
		
		T24HeadlessGenStartupCore.context = bundleContext;
		RIMDslStandaloneSetup.doSetup();
		DomainStandaloneSetup.doSetup();
		ComponentStandaloneSetup.doSetup();
		VersionDSLStandaloneSetup.doSetup();
		EnquiryStandaloneSetup.doSetup();
		EFactoryStandaloneSetup.doSetup();
		MenuStandaloneSetup.doSetup();
	}

	public void stop(BundleContext bundleContext) throws Exception {
		T24HeadlessGenStartupCore.context = null;
	}

}
