package com.odcgroup.workbench.core.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Helper to centralize feature switches, configurable via -D.
 * 
 * We only want to use this for in-Dev kind of switches.. this is not intended
 * for e.g. different kinds of real final end-users to turn things on or off!
 * For example, this is useful for us to try out different settings, and be able
 * to change them in-IDE as well as (notably) in the DS (Maven) Generator
 * Edition.
 * 
 * The term "Feature" in this context has nothing to do with an Eclipse Feature
 * (as in Bundles and Features).
 * 
 * @author Michael Vorburger
 */
public class FeatureSwitches {
	private static final Logger logger = LoggerFactory.getLogger(FeatureSwitches.class);

	/**
	 * Size of Cache<URI, Resource> in ModelResourceSet. Value = -1 unlimited,
	 * which is how it used to work for DS TAP for years (as weakValues() never
	 * actually worked...); and is actually OK for usual pms-models (but a
	 * problem for a fully loaded T24 models project). Value -2 = don't use
	 * Cache at all, so everything is always in underlying ResourceSet; unless
	 * unload() by other code. Value = 0 will continuously unload() - not
	 * recommended!
	 * 
	 * @see com.odcgroup.workbench.core.internal.repository.ModelResourceSetImpl
	 */
	public static final Feature<Integer> modelResourceSetResourceCacheSize = new Feature("ds.resource.cache.maxsize", -1);
// TODO is it possible to find a good default value here?? 300 caused DS-6979 (before DomainRepository fix..)
//	 * The default has been chosen based on some manual tests. It's not strictly
//	 * scientific, because it depends a lot on size of the models cached. Until
//	 * DS-6707, there was no max size, so for pms-models most likely everything
//	 * was always cached. Because pms-models has >10k+ model files, we've set
//	 * this to... 10000?
	
	/**
	 * Enables/disables the Cache<MdfName, MdfDataset>
	 * in com.odcgroup.mdf.ecore.util.DomainRepository.
	 */
	public static final Feature<Boolean> domainRepositoryDatasetCacheEnabled = new Feature("ds.dataset.cache.enabled", true);
	
	/**
	 * Stop generation (throw exception) on error during T24 XML Generation.
	 */
	public static final Feature<Boolean> t24XMLGenerationStopOnFailureEnabled = new Feature("ds.t24.gen.xml.failonerror", true);

	/**
	 * Check license 
	 */
	public static final Feature<Boolean> dsSubroutineEnabled = new Feature("ds.t24.jbc.dssubroutine.enabled", true);

	
	/**
	 * Replace field primitive type by Business Type in application disabled for IRIS cartridges
	 */
	public static final Feature<Boolean> primitiveToBTReplaceForIrisDisabled = new Feature("ds.t24.bt.iris.disabled", false);
	
	/**
	 * Xbase JvmType inferred from *.domain by DomainJvmModelInferrer are
	 * currently needed by ELang/DS EL, so DS TAP, only. So for DS T24 we may
	 * want to disable them.. for perf/mem. (This is not tested/proven to be a
	 * problem yet - just leaving a door open for the future.)
	 */
	public static final Feature<Boolean> domainInferJvmTypes = new Feature("ds.domain.jvmtypes", true);
	
	/**
	 * CodeGenerationAction can refresh all output folders. This appears to be a
	 * very expensive operation in the case of just one/few models having been
	 * generated into a project with a lot of generated files. For DS T24, where
	 * all cartridges (normally...) should go through IFileSystemAccess, this
	 * isn't actually needed. For DS TAP, where we have old code which directly
	 * uses java.io.File, we (probably, not checked) still need this. Therefore
	 * this feature switch is available, and -D set in T24 INIs.
	 */
	public static final Feature<Boolean> generationRefreshOutputFolders = new Feature("ds.gen.refreshOutputFolders", true);
	
	/**
	 * The "headless" (Maven) Generator usually 'stops' a build when there are
	 * errors (by returning non 0 from the process, which the Maven plug-in
	 * interprets and results in failure). In exceptional situations, you may
	 * want to force a build to continue nevertheless ("use at your own risk")
	 * by setting this to false. Note that it does NOT stop on the first error,
	 * but always continues to collect all; this options just determines what
	 * happens at the end.
	 */
	public static final Feature<Boolean> generationHeadlessStopOnErrors = new Feature("ds.gen.headless.stop", true);
	
	
	/**
	 * Whether to clear memory (by emptying the ResourceSet and
	 * CachingCodeGenerator) when we almost run OOM. This is true by default,
	 * and only a FeatureSwitch so that we can disable it if we run into
	 * unexpected problems (but then may have OOM if Xmx is too low). Note that
	 * this behaves differently in the UI (where it clears the ResourceSet and
	 * CachingCodeGenerator) and the command line Maven Generator Edition (where
	 * it clears only the CachingCodeGenerator, but cannot empty the
	 * ResourceSet).
	 */
	public static final Feature<Boolean> generationFullMemoryCleaner  = new Feature("ds.gen.memory.autoclean", true);
	public static final Feature<Integer> generationFullMemoryStrategy = new Feature("ds.gen.memory.autoclean.strategy", 2);

	
	/**
	 * Allows "fake" generation - make the headless Generator return
	 * immediately. Not intended for real end-users, but used only internally
	 * in the build of the t24-binaries to speed up collecting JARs from the IRIS
	 * template, which has to run several times with different profiles (DS-8106).
	 */
	public static final Feature<Boolean> generationHeadlessFakeIt = new Feature("ds.gen.fake", false);

	/**
	 * Whether Headless Code Generation (in pre-load mode, only) should ignore or abort if there are validation errors.
	 * Note that it's 'ds.ignoreValidationErrors' instead of ds.gen because an earlier property like this already existed before (DS-3842) the new (DS-7127) validation code. 
	 */
	public static final Feature<Boolean> generationIgnoreValidationErrors = new Feature("ds.ignoreValidationErrors", false); 

	/**
	 * Skip generation of metadata-ResourecName(*).xml and T24-ResourecName(*).properties for domains and enqlist. 
	 * By default generate everything
	 */
	public static final Feature<Boolean> skipDomainGenerationForIRIS = new Feature("ds.skipDomainGenerationForIRIS", false);

	/**
	 * DynamicResourceClusteringPolicy instead DisabledClusteringPolicy IResourceClusteringPolicy.
	 * @see SharedModule2
	 */
	public static final Feature<Boolean> xtextBuilderFreeMemory = new Feature("ds.xtext.builder.free", true);
	
	/**
	 * ParallelResourceLoader IResourceLoader  etc.
	 * @see SharedModule2
	 */
	public static final Feature<Boolean> xtextBuilderParallel = new Feature("ds.xtext.builder.parallel", true);

	/**
	 * DS-8394 Check an infinite call in our EclipseLogListener
	 * The check is done only when the switch is set to true
	 * @see EclipseLogListener
	 */
	public static final Feature<Boolean> checkInfiniteCallInLogger = new Feature("ds.checkInfiniteCallInLogger", false);

	/**
	 * DS-8394 an infinite call  in our EclipseLogListener
	 * Whenever a possible infinite loop is detected, and when this switch is set to true an exception is thrown
	 * with <code>Exceptions.sneakyThrow(new Throwable(message, exception))</code>. otherwise the error message and the
	 * stack trace is printed on the error console (System.err)
	 * and
	 * @see EclipseLogListener
	 */
	public static final Feature<Boolean> checkInfiniteCallInLoggerSneakyThrow = new Feature("ds.checkInfiniteCallInLoggerSneakyThrow", false);
	

	/**
	 * DS-8809 Make EDS unaware of generated IRIS runtime artifacts - IRIS artifacts will be generated in the directory
	 * specified, this is expected to be a location that is not under the current project's Eclipse file system.
	 */
	public static final Feature<String> genIrisOutputDir = new Feature("ds.t24.gen.iris.outdir", null);

	public static class Feature<T> {
		private final String systemPropertyName;
		private final T value;

		// intentionally package private; new Feature should not be defined anywhere else than in this centralized class (for better overview/tracking)
		// not completely private so that the FeatureEnablementTest can use this as well
		Feature(String sysPropName, int defaultValue) {
			this.systemPropertyName = check(sysPropName);
			this.value = (T) Integer.getInteger(sysPropName, defaultValue);
			logger.info(toString());
		}
		
		Feature(String sysPropName, boolean defaultValue) {
			this.systemPropertyName = check(sysPropName);
			// NOT this.value = (T) Boolean.valueOf(System.getProperty(sysPropName, Boolean.toString(defaultValue)));
			String sysPropValue = System.getProperty(sysPropName, Boolean.toString(defaultValue));
			if (Boolean.TRUE.toString().equalsIgnoreCase(sysPropValue))
				this.value = (T) Boolean.TRUE;
			else if (Boolean.FALSE.toString().equalsIgnoreCase(sysPropValue))
				this.value = (T) Boolean.FALSE;
			else
				this.value = (T) Boolean.valueOf(defaultValue);
			logger.info(toString());
		}

		Feature(String sysPropName, String defaultValue) {
			this.systemPropertyName = check(sysPropName);

			String tmpPropValue = System.getProperty(sysPropName);

			if(tmpPropValue == null || "".equals(tmpPropValue.trim())) {
				// System property override either not set or invalid value so use default value
				this.value = (T)defaultValue;
			} else {
				this.value = (T)tmpPropValue;
			}

			logger.info(toString());
		}

		public T get() {
			return value;
		}
		
		@Override
		public String toString() {
			return "DS Feature Switch: " + systemPropertyName + "=" + value;
		}
		
		private String check(String sysPropName) {
			if (!sysPropName.startsWith("ds."))
				throw new IllegalArgumentException("DS Feature Switch System Property name doesn't start with ds.* (but it must, so that's it's easily configurable even for the Generator, which forwards only ds.* prefixed system properties from the Maven plug-in process to the DS Generator process) : " + sysPropName);
			return sysPropName;
		}

	}

}
