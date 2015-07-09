package com.odcgroup.translation.generation.internal.generator.nls;

import java.util.Map;

import org.eclipse.core.resources.IProject;

import com.odcgroup.translation.generation.ITranslationGenerator;
import com.odcgroup.translation.generation.provider.BaseTranslationGeneratorProvider;

/**
 * @author atr
 */
public class NLSGeneratorProvider extends BaseTranslationGeneratorProvider {

	@SuppressWarnings("unchecked")
	@Override
	public ITranslationGenerator getTranslationGenerator(IProject project, Map properties) {
		return new NLSGenerator(project, properties);
	}

}
