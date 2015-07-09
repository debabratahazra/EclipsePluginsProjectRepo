package com.odcgroup.page.model.symbols;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;

import com.odcgroup.page.model.symbols.impl.AbstractSymbolExpander;
import com.odcgroup.page.model.symbols.impl.AbstractSymbolResolver;
import com.odcgroup.page.model.symbols.impl.DefaultSymbolExpander;

/**
 * @author atr
 */
public class SymbolsExpanderFactory {

	private static String SYMBOLS_EXPANDER_EXTENSION_ID = "com.odcgroup.page.model.symbols.expander";

	private static SymbolsExpanderFactory INSTANCE = new SymbolsExpanderFactory();

	private SymbolsExpander expander = null;

	private SymbolsExpanderFactory() {
	}

	private void loadSymbolResolvers(IConfigurationElement[] elements, SymbolsExpander expander) {
		for (int kx=0; kx < elements.length; kx++) {
			IConfigurationElement configElement = elements[kx]; 
			try {
				AbstractSymbolResolver resolver = (AbstractSymbolResolver) configElement.createExecutableExtension("class");
				resolver.setName(configElement.getAttribute("name"));
				resolver.setDescription(configElement.getAttribute("description"));
				expander.addSymbolResolver(resolver);
			} catch (CoreException ex) {
				ex.printStackTrace();
				//getDefault().logError("Could not initialize search participant", e);
			}				
		}
	}

	private void loadSymbolsExpander() {
		IExtensionRegistry registry = Platform.getExtensionRegistry();
		IExtensionPoint point = registry.getExtensionPoint(SYMBOLS_EXPANDER_EXTENSION_ID);
		IExtension[] extensions = point.getExtensions();
		for (int i = 0; i < extensions.length; i++) {
			IConfigurationElement[] elements = extensions[i].getConfigurationElements();
			for (int kx=0; kx < elements.length; kx++) {
				IConfigurationElement configElement = elements[kx]; 
				try {
					AbstractSymbolExpander ase = (AbstractSymbolExpander) configElement.createExecutableExtension("class");
					ase.setName(configElement.getAttribute("name"));
					ase.setDescription(configElement.getAttribute("description"));
					ase.setSymbolPattern(configElement.getAttribute("symbol-pattern"));
					ase.setSymbolPrefix(configElement.getAttribute("symbol-prefix"));
					ase.setSymbolSuffix(configElement.getAttribute("symbol-suffix"));
					loadSymbolResolvers(configElement.getChildren(), ase);
					expander = ase;
				} catch (CoreException ex) {
					ex.printStackTrace();
					//getDefault().logError("Could not initialize search participant", e);
				}				
				
			}
		}
		if (expander == null) {
			expander = new DefaultSymbolExpander();
		}
	}

	private synchronized SymbolsExpander doGetSymbolsExpander() {
		if (expander == null) {
			loadSymbolsExpander();
		}
		return expander;
	}

	public static SymbolsExpander getSymbolsExpander() {
		return INSTANCE.doGetSymbolsExpander();
	}

}
