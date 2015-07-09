package com.odcgroup.edge.t24ui.model;

import com.odcgroup.edge.t24ui.common.impl.CommonPackageImpl;
import com.odcgroup.edge.t24ui.contextEnquiry.impl.ContextEnquiryPackageImpl;
import com.odcgroup.edge.t24ui.cos.bespoke.impl.BespokePackageImpl;
import com.odcgroup.edge.t24ui.cos.pattern.impl.PatternPackageImpl;
import com.odcgroup.edge.t24ui.impl.T24UIPackageImpl;

/**
 * StandaloneSetup like (not real) for com.odcgroup.edge.t24ui.model.
 * It just makes sure that all Ecore packages are initialized.
 * Used in T24UIInjectorProvider, and should also be used in
 * Generator later. 
 *
 * @author Michael Vorburger
 */
public class T24UIStandaloneSetup {

	public static void doSetup() {
		CommonPackageImpl.init();
		ContextEnquiryPackageImpl.init();
		BespokePackageImpl.init();
		PatternPackageImpl.init();
		T24UIPackageImpl.init();
	}
}
