package com.odcgroup.translation.generation.internal;

import org.osgi.framework.BundleContext;

import com.odcgroup.translation.generation.ITranslationGenerationRuntime;

/**
 * @author atr
 */
public final class TranslationGenerationRuntimeProvider {

	/** cannot instantiate */
	private TranslationGenerationRuntimeProvider() {
	}

	/**
	 * @param context
	 * @return
	 */
	public static synchronized ITranslationGenerationRuntime getRuntime(BundleContext context) {
		return new TranslationGenerationRuntime(context);
	}

}
