package com.odcgroup.workbench.generation.cartridge.ng;

import org.eclipse.emf.ecore.resource.ResourceSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.odcgroup.workbench.core.di.StandaloneHeadlessStatus;
import com.odcgroup.workbench.core.helper.FeatureSwitches;

/**
 * Utility which checks if we are near OOM, and which can correctly clear an EMF ResourceSet if we are.
 * 
 * Give me some sunshine
 * Give me some rain
 * Give me another chance i wanna grow up once again
 * 
 * @see https://www.youtube.com/watch?v=t-SVBlZRbWk&list=RDt533PJnDMKg
 *
 * @author Michael Vorburger
 */
public class MemoryGuy {
	private static Logger logger = LoggerFactory.getLogger(MemoryGuy.class);

	private static final DynamicResourceClusteringPolicy policy = new DynamicResourceClusteringPolicy();
	
	public static boolean giveMeSomeSunshineIfNearOOM(ResourceSet resourceSet, CodeGenerator2 generator) {
		if (!FeatureSwitches.generationFullMemoryCleaner.get())
			return false;
		if (!isAlmostOutOfMemory())
			return false;
		
		boolean done = false;
		if (generator instanceof CachingCodeGenerator) {
			CachingCodeGenerator cachingGenerator = (CachingCodeGenerator) generator;
			cachingGenerator.giveMeSomeSunshineAsWeAreNearOOM();
			done = true;
		}

		// We CANNOT clear the ResourceSet in the Generator Edition.
		// @see doc in GenerationCommon.preload() for background why.  
		// The Generator, currently/as-is, HAS to load all DS models
		// into memory.  The medium term plan is to switch to the 3rd
		// gen. generators, and use standard Xtext standalone builder
		// which handles the index correctly.
		if (!StandaloneHeadlessStatus.INSTANCE.isInStandaloneHeadless()) {
			// code stolen from org.eclipse.xtext.builder.standalone.StandaloneBuilder#clearResourceSet() :
			// "Clears the content of the resource set without sending notifications. This avoids unnecessary, explicit unloads."
			boolean wasDeliver = resourceSet.eDeliver();
			try {
				resourceSet.eSetDeliver(false);
				resourceSet.getResources().clear();
			} finally {
				resourceSet.eSetDeliver(wasDeliver);
			}
			done = true;
		}

		if (!done) {
			logger.warn("isAlmostOutOfMemory, but no CachingCodeGenerator, and isInStandaloneHeadless, so cannot free anything - OOM about to happen? Increase Xmx..");
			return false;
		}
		
		StringBuilder sb = new StringBuilder("Forced in-flight clear() of the ");
		if (generator instanceof CachingCodeGenerator) {
			sb.append("CachingCodeGenerator " + generator.getClass().getName());
		}
		if (generator instanceof CachingCodeGenerator && !StandaloneHeadlessStatus.INSTANCE.isInStandaloneHeadless()) {
			sb.append(" and the ");
		}
		if (!StandaloneHeadlessStatus.INSTANCE.isInStandaloneHeadless()) {
			sb.append("ResourceSet");
		}
		sb.append(" because your CURRENT total heap size (Xmx) is too small to generate this project efficiently."
				+ "  While this trick works, if you can please do increase the maximum heap for your running JVM to gain faster generation performance!");

		logger.warn(sb.toString());
		
		return true;
	}

	private static boolean isAlmostOutOfMemory() {
		Integer strategy = FeatureSwitches.generationFullMemoryStrategy.get();
		if (strategy == 1)
			// Thierry's strategy (10% - was 20% in T24EdgeGenerator)
			return (double)Runtime.getRuntime().freeMemory() / (double)Runtime.getRuntime().totalMemory() < 0.1;
		else if (strategy == 2)
			// Standard Xtext's approach
			return !policy.continueProcessing();
		else
			return false;
	}
}
