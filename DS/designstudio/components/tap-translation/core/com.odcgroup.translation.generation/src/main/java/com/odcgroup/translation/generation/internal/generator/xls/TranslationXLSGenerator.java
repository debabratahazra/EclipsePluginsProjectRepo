package com.odcgroup.translation.generation.internal.generator.xls;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;

import com.odcgroup.documentation.generation.DocGenerator;
import com.odcgroup.translation.generation.TranslationGenerationCore;
import com.odcgroup.workbench.core.IOfsModelResource;

/**
 * @author alain
 */
public class TranslationXLSGenerator  implements DocGenerator {
	
	@Override
	public void run(IProject project, Collection<IOfsModelResource> modelResources, IFolder outputFolder,
			IProgressMonitor monitor) throws CoreException, InterruptedException {
		long start = System.currentTimeMillis();		
		
		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put("generator", "com.odcgroup.translaton.xls.generator");
		properties.put("folder", outputFolder);
		TranslationGenerationCore.generateTranslations(project, modelResources, properties);
		
		long stop = System.currentTimeMillis();
		long duration = stop-start;
		TranslationGenerationCore.getDefault().logInfo("Translations generated in " + duration + " ms", null);
		
	}

	@Override
	public boolean isValidForProject(IProject project) {
		return true;
	}

}
