package com.odcgroup.documentation.generation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.InvalidRegistryObjectException;
import org.eclipse.core.runtime.Platform;
import org.osgi.framework.BundleContext;

import com.odcgroup.documentstion.generation.cartridge.CategoryNotFoundException;
import com.odcgroup.documentstion.generation.cartridge.DocGenCategory;
import com.odcgroup.workbench.core.AbstractActivator;

public class DocumentationCore extends AbstractActivator {	

	// The plug-in ID
	public static final String PLUGIN_ID = "com.odcgroup.documentation.generation"; 
	
	// documentation generation extension ID
	public static final String DOCGEN_CARTRIDGE_EXTENSION_ID = "com.odcgroup.documentation.generation.docGenCartridge";
	private static DocGenCartridge[] docGenCartridges = null;
	
	public static DocumentationCore getDefault() {
		return (DocumentationCore) getDefault(DocumentationCore.class);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext
	 * )
	 */
	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext
	 * )
	 */
	@Override
	public void stop(BundleContext context) throws Exception {
		super.stop(context);
	}

	
	/**
	 * @return
	 */
	public static DocGenCartridge[] getRegisteredDocGenCartridges() {
		if (docGenCartridges == null) {
		ArrayList<DocGenCartridge> cartridges = new ArrayList<DocGenCartridge>();
		IExtensionRegistry registry = Platform.getExtensionRegistry();
		IExtensionPoint point = registry.getExtensionPoint(DOCGEN_CARTRIDGE_EXTENSION_ID);
		if (point == null) return new DocGenCartridge[0];
		IConfigurationElement[] configElements = point.getConfigurationElements();	
		
		// this set is used to avoid to register the same generator more than one.
		// this can occur when a test plugin defines the same generator in a fragment.
		Set<String> uniqueIDs = new HashSet<String>();
    	
		// iterate over all defined documentation generation cartridges
		for(int j=0; j<configElements.length; j++) {
			DocGenCartridge cartridge = new DocGenCartridge();
			String idString = configElements[j].getAttribute("id");
			cartridge.setId(idString);
			cartridge.setName(configElements[j].getAttribute("name"));
			cartridge.setEnabledByDefault(!"false".equals(configElements[j].getAttribute("enabledByDefault")));
			cartridge.setTerminal(Boolean.valueOf(configElements[j].getAttribute("terminal")));
            try {
            	String categoryString = configElements[j].getAttribute("category");
            	DocGenCategory category = DocGenCategory.fromString(categoryString);
				cartridge.setCategory(category);
				
				String uniqueID = categoryString + "_" + idString;
				if (!uniqueIDs.contains(uniqueID)) {
	    			DocGenerator docGen = (DocGenerator) configElements[j].createExecutableExtension("class");
	                cartridge.setDocGen(docGen);
	        		cartridges.add(cartridge);
	        		uniqueIDs.add(uniqueID);
				}
            } catch (CoreException e) {
            	DocumentationCore.getDefault().getLog().log(e.getStatus());
	        } catch (InvalidRegistryObjectException e) {
				e.printStackTrace();
			} catch (CategoryNotFoundException e) {
				e.printStackTrace();
			}
		}
		docGenCartridges = cartridges.toArray(new DocGenCartridge[0]);
		}
		return docGenCartridges.clone();
	}

}
