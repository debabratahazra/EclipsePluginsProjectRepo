package com.odcgroup.iris.rim.generation.mappers;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.common.util.EList;

import com.temenos.interaction.rimdsl.rim.Ref;
import com.temenos.interaction.rimdsl.rim.RimFactory;
import com.temenos.interaction.rimdsl.rim.Use;

/**
 * Constructs 'use' imported namespaces and add to the list.
 *
 * @author aphethean
 *
 */
public class ImportedNamespaceFactory {
	
	Map<String, Use> useMap = new HashMap<String, Use>();
	EList<Ref> modelReferences;
	
	public ImportedNamespaceFactory(EList<Ref> modelReferences) {
		this.modelReferences = modelReferences;
	}
	
	public Use createUse(String importedNamespace) {
		// a resource command references a command
		if (useMap.get(importedNamespace) == null) {
			Use use = RimFactory.eINSTANCE.createUse();
			use.setImportedNamespace(importedNamespace);
			useMap.put(importedNamespace, use);
			modelReferences.add(use);
		}
		return useMap.get(importedNamespace);
	}

}
