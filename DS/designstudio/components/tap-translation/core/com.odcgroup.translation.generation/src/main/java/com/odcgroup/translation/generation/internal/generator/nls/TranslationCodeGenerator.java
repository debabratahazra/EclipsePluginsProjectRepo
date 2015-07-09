package com.odcgroup.translation.generation.internal.generator.nls;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;

import com.odcgroup.translation.generation.TranslationGenerationCore;
import com.odcgroup.workbench.core.IOfsModelResource;
import com.odcgroup.workbench.generation.cartridge.AbstractCodeGenerator;

/**
 * @author alain
 */
public class TranslationCodeGenerator extends AbstractCodeGenerator {
	
	public static final String TRANSLATON_NLS_GENERATOR = "com.odcgroup.translaton.nls.generator";

	@Override
	public void doRun(IProject project, IContainer outputContainer, Collection<IOfsModelResource> modelResources,
			List<IStatus> nonOkStatuses) {
				
		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put("generator", TRANSLATON_NLS_GENERATOR);
		properties.put("folder", outputContainer);
		
		long start = System.currentTimeMillis();		
		try {
			TranslationGenerationCore.generateTranslations(project, modelResources, properties);
		} catch (CoreException e) {
			String errorMsg = "Error while generating translations.";
			newCoreException(e, nonOkStatuses, errorMsg);
		}
		long stop = System.currentTimeMillis();
		long duration = stop - start;
		TranslationGenerationCore.getDefault().logInfo("Translations generated in " + duration + " ms", null);
	}

}
