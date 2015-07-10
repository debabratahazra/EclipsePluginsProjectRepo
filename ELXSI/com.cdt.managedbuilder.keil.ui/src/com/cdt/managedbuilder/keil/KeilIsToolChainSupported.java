package com.cdt.managedbuilder.keil;

import org.eclipse.cdt.managedbuilder.core.IManagedIsToolChainSupported;
import org.eclipse.cdt.managedbuilder.core.IToolChain;
import org.osgi.framework.Version;

public class KeilIsToolChainSupported implements IManagedIsToolChainSupported {
	
	private final boolean KeilSupported;
	
	public KeilIsToolChainSupported() {
		
		//Keil Supported iff the Keil C51 Compiler is Installed in current system.....
		KeilSupported = KeilEnvironmentVariableSupplier.getBinDir() != null;
	}

	public boolean isSupported(IToolChain toolChain, Version version,
			String instance) {
		return KeilSupported;
	}

	

}
