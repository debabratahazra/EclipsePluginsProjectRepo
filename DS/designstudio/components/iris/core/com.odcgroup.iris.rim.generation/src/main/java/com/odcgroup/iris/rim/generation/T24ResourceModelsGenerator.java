package com.odcgroup.iris.rim.generation;

import java.io.File;
import java.io.StringWriter;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.commons.lang.NotImplementedException;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.xtext.generator.IFileSystemAccess;
import org.eclipse.xtext.generator.IFileSystemAccessExtension2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Injector;
import com.odcgroup.edge.t24ui.CompositeScreen;
import com.odcgroup.edge.t24ui.contextEnquiry.ContextEnquiry;
import com.odcgroup.iris.generation.FileSystemHelper;
import com.odcgroup.iris.generator.Application;
import com.odcgroup.iris.generator.IRISDomainMapper;
import com.odcgroup.iris.generator.Resource.RESOURCE_TYPE;
import com.odcgroup.iris.rim.generation.contextenquiry.ContextEnquiryFile;
import com.odcgroup.iris.rim.generation.contextenquiry.ContextEnquiryLink;
import com.odcgroup.iris.rim.generation.mappers.Domain2ResourceMapper;
import com.odcgroup.iris.rim.generation.mappers.Enquiry2ResourceMapper;
import com.odcgroup.iris.rim.generation.mappers.LightweightPatternCOS2ResourceMapper;
import com.odcgroup.iris.rim.generation.mappers.RIMSerializer;
import com.odcgroup.iris.rim.generation.mappers.Version2ResourceMapper;
import com.odcgroup.iris.rim.generation.streams.mappers.ApplicationVersion2StreamMapper;
import com.odcgroup.iris.rim.generation.streams.mappers.CtxEnquiry2StreamMapper;
import com.odcgroup.iris.rim.generation.streams.mappers.Menu2StreamMapper;
import com.odcgroup.iris.rim.generation.streams.mappers.RimWriter;
import com.odcgroup.iris.rim.generation.streams.mappers.StreamMapper;
import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.mdf.metamodel.MdfDomain;
import com.odcgroup.t24.applicationimport.T24Aspect;
import com.odcgroup.t24.enquiry.enquiry.Enquiry;
import com.odcgroup.t24.enquiry.util.EMUtils;
import com.odcgroup.t24.menu.menu.MenuRoot;
import com.odcgroup.t24.version.versionDSL.Version;
import com.odcgroup.t24.version.versionDSL.YesNo;
import com.odcgroup.t24.version.versionDSL.impl.VersionDSLFactoryImpl;
import com.odcgroup.workbench.core.IOfsModelResource;
import com.odcgroup.workbench.core.di.ILangSpecificProvider;
import com.odcgroup.workbench.core.di.StandaloneHeadlessStatus;
import com.odcgroup.workbench.core.helper.FeatureSwitches;
import com.odcgroup.workbench.core.xtext.XtextProxyUtil;
import com.odcgroup.workbench.generation.cartridge.CodeGenerator;
import com.odcgroup.workbench.generation.cartridge.ng.CodeGenerator2;
import com.odcgroup.workbench.generation.cartridge.ng.ModelLoader;
import com.odcgroup.workbench.generation.cartridge.ng.SimplerEclipseResourceFileSystemAccess2;
import com.odcgroup.workbench.generation.cartridge.ng.SimplerEclipseResourceFileSystemAccess2.IFileCallback;
import com.odcgroup.workbench.generation.cartridge.ng.SimplerEclipseResourceFileSystemNotifier;
import com.temenos.interaction.rimdsl.generator.RIMDslGeneratorSpringPRD;
import com.temenos.interaction.rimdsl.rim.DomainModel;

public class T24ResourceModelsGenerator implements CodeGenerator2, CodeGenerator {
	private static final Logger logger = LoggerFactory.getLogger(T24ResourceModelsGenerator.class);

	private static final String RIM_EXT = ".rim";
	private static final String DOMAIN_EXT = "domain";
	private static final String ENQUIRY_EXT = "enquiry";
	private static final String VERSION_EXT = "version";
	private static final String APP_EXT = "applications";
	private static final String MENU_EXT = "menu";
	private static final String ESON_EXT = "eson";
	
	private final SimplerEclipseResourceFileSystemNotifier changesNotifier = new SimplerEclipseResourceFileSystemNotifier();
	private final FileSystemHelper fileSystemHelper = new FileSystemHelper();
	
	private final boolean skipDomainGenerationForIRIS = FeatureSwitches.skipDomainGenerationForIRIS.get();

	public static RESOURCE_TYPE TYPE_UNKNOWN() {
		return RESOURCE_TYPE.unknown;
	}

	public static RESOURCE_TYPE TYPE_VERSION() {
		return RESOURCE_TYPE.version;
	}

	public static RESOURCE_TYPE TYPE_COMPOSITE() {
		return RESOURCE_TYPE.composite;
	}

	public static RESOURCE_TYPE TYPE_ENQUIRY() {
		return RESOURCE_TYPE.enquiry;
	}

	public static RESOURCE_TYPE TYPE_DYNAMIC() {
		return RESOURCE_TYPE.dynamic;
	}
	
	public static RESOURCE_TYPE TYPE_MENU() {
		return RESOURCE_TYPE.menu;
	}
	
	public static RESOURCE_TYPE TYPE_ENQLIST() {
		return RESOURCE_TYPE.enqlist;
	}

	// NOT private @Inject IRISJavaGenerator irisJavaGenerator;
	// NOR private @Inject ILangSpecificProvider<IRISJavaGenerator>
	// irisJavaGeneratorProvider;
	private @Inject
	ILangSpecificProvider<Injector> irisJavaGeneratorInjectorProvider;

	// private @Inject INodeModelFormatter formatter;

	// like in ResourceGenerator2.. a bit of a hack, may be better supported by
	// DS Foundation later, if more Generators need this kind of "chaining"
	// support
	private @Inject
	SimplerEclipseResourceFileSystemAccess2 irisJavaGeneratorFSA;

	private @Inject
	ILangSpecificProvider<XtextProxyUtil> proxyUtilProvider;

	//
	// Ugly testing support
	//
	// public void setFormatter(INodeModelFormatter formatter) {
	// this.formatter = formatter;
	// }
	
	@Override
	public void doGenerate(URI input, ModelLoader loader, IFileSystemAccess fsa) throws Exception {
		ResourceSet rs = null;
		DomainModel rim = null;
		String rimFoldersAndFileName = null;

		logger.info("Generate RIM [" + input + "]");

		IFileSystemAccess springPRDFsa = determineFsa(fsa, changesNotifier);

		SpringPRDGenerator springPRDGenerator = new SpringPRDGenerator(springPRDFsa);

		try {
			if (!skipDomainGenerationForIRIS && DOMAIN_EXT.equals(input.fileExtension())) {
				MdfDomain domain = loader.getRootEObject(input, MdfDomain.class);
				@SuppressWarnings("unchecked")
				List<MdfClass> classes = domain.getClasses();
				
				boolean runningInEclipse = !StandaloneHeadlessStatus.INSTANCE.isInStandaloneHeadless();
				
				for (MdfClass mdfClass : classes) {
					if (mdfClass.getName().indexOf("__") > 0 || IRISDomainMapper.isAAResource(mdfClass)) {
						continue; // as no rim to generate for field definitions and AA resources
					}
					rim = new Domain2ResourceMapper().mapDomain(mdfClass, loader, fsa);
					Resource resource = loader.getResource(input);
					rs = resource.getResourceSet();
					rimFoldersAndFileName = getRimFoldersAndFileName(null, RESOURCE_TYPE.enqlist, mdfClass.getName());
					if (rim != null && rimFoldersAndFileName != null) {
						saveAndGenerateJava(fsa, rimFoldersAndFileName, rim, rs, springPRDGenerator);
					}
					/*
					 * Only generate the rim if we are not in the build tests. If we are, it seems
					 * that generating 3000 more rims breaks the build do to memory limit.
					 */
					
					if (runningInEclipse){
						/*
						 * and the "default" version (acts like the application
						 */
						Version versionApplication = createDefaultVersionFromApplication(mdfClass);
						if (versionApplication != null){
							logger.info("Generating rim resource for application " + mdfClass.getName());
							buildRimForApplicationVersion(versionApplication, rs, fsa, springPRDGenerator);
						}
					}
				}
				return;
			} else if (ENQUIRY_EXT.equals(input.fileExtension())) {
				Enquiry enquiry = loader.getRootEObject(input, Enquiry.class);
				rs = getRS(enquiry);
				MdfClass mdfClass = EMUtils.getAppByEnquiry(loader, enquiry);
				Application application = new Application(mdfClass);
				rim = new Enquiry2ResourceMapper().mapEnquiry(loader, enquiry, application);
				rimFoldersAndFileName = getRimFoldersAndFileName(null, RESOURCE_TYPE.enquiry, enquiry.getName());
			} else if (VERSION_EXT.equals(input.fileExtension())) {
				Version version = loader.getRootEObject(input, Version.class);
				rs = getRS(version);
				rim = new Version2ResourceMapper().mapVersion(loader, version);
				rimFoldersAndFileName = getRimFoldersAndFileName(null, RESOURCE_TYPE.version, version.getT24Name());
			} else if (MENU_EXT.equals(input.fileExtension())) {
				MenuRoot menuRoot = loader.getRootEObject(input, MenuRoot.class);
				buildMenu(menuRoot, loader, fsa, null, springPRDGenerator);

			} else if (ESON_EXT.equals(input.fileExtension())) {

				EObject rootObject = loader.getRootEObject(input, EObject.class);
				rs = getRS(rootObject);

				if (rootObject instanceof CompositeScreen) {
					final CompositeScreen patternCOS = (CompositeScreen) rootObject;
					rs = getRS(patternCOS);
					final MapperHelper mapperHelper = new MapperHelperImpl(fsa, rs, springPRDGenerator);
					final LightweightPatternCOS2ResourceMapper mapper = new LightweightPatternCOS2ResourceMapper(
							(SimplerEclipseResourceFileSystemAccess2) fsa, mapperHelper, proxyUtilProvider);
					mapper.writePatternCOSRIM(patternCOS, true);
				} else if (rootObject instanceof ContextEnquiry) {

					/*
					 * Since we can have multiple context Enquiries having the
					 * same name (see CUSTOMER / CUSTOMER-KYC / ...) We cannot
					 * use the getName() as a unique thing. So we will use the
					 * file name !
					 */
					String sFileName = input.lastSegment();
					sFileName = sFileName.substring(0, sFileName.length() - 5); // without
																				// .eson
					String rimName = EMUtils.camelCaseName(sFileName);
					/*
					 * Upper Case the first char
					 */
					rimName = "ctx" + rimName.substring(0, 1).toUpperCase() + rimName.substring(1); // eg
																									// Currency
					List<ContextEnquiryLink> ctxList = buildContextEnquiry(fsa, loader, (ContextEnquiry) rootObject, rimName, springPRDGenerator);
					
					/*
					 * Update ContextEnquiry.rim file
					 *
					 * Need the uriConverted to stream directly the ContextEnquiry File.
					 */

					URI ctxEnqRimUri = ContextEnquiryFile.INSTANCE.update(loader, ctxList);
					Resource resource = rs.getResource(ctxEnqRimUri, true);
					if (resource != null){
						/*
						 * OK, This is ugly, but this is the only way I found to force a reload 
						 * when generating the full build.
						 * Still need to generate code on the rim though ....
						 * 
						 */
						resource.unload();
						resource = rs.getResource(ctxEnqRimUri, true);
						springPRDGenerator.generate(resource);
					}
				}
			}

			if (rim != null && rimFoldersAndFileName != null) {
				saveAndGenerateJava(fsa, rimFoldersAndFileName, rim, rs, springPRDGenerator);
			}
		} catch (Exception e) {
			e.printStackTrace();
			
			/*
			 * Catch all exception as if we let these being thrown, it will interrupt the full build (Thierry, May 5th 2014)
			 */
			logger.error("Failed to generate RIM for : '" + input + "'", e);
		}
	}

	/**
	 * Determines the Eclipse file system access abstraction to use for Spring PRD generation
	 */
	private IFileSystemAccess determineFsa(IFileSystemAccess fsa, IFileCallback changesNotifier) {
		IFileSystemAccess springPRDFsa = getConfiguredIRISJavaGeneratorFSA(fsa, changesNotifier);

		springPRDFsa = fileSystemHelper.determineFsa(springPRDFsa);

		return springPRDFsa;
	}

	private List<ContextEnquiryLink> buildContextEnquiry(IFileSystemAccess fsa, ModelLoader loader, ContextEnquiry ctxEnq, String rimName, SpringPRDGenerator springPRDGenerator) throws Exception {
		ResourceSet rs = getRS(ctxEnq);
		String rimFileName = rimName + RIM_EXT; // eg ctxCurrency.rim

		String genFileName = ESON_EXT + "/" + rimFileName;
		CtxEnquiry2StreamMapper mapper = new CtxEnquiry2StreamMapper(proxyUtilProvider);
		List<ContextEnquiryLink> toReturn = map(mapper, ctxEnq, rimName, genFileName , fsa, rs, springPRDGenerator).mapReturn;
		return toReturn;
	}

	private void buildMenu(MenuRoot menuRoot, ModelLoader loader, IFileSystemAccess fsa, CachedStates cache, SpringPRDGenerator springPRDGenerator)
			throws Exception {
		if (cache == null) {
			cache = new CachedStates();
		}
		ResourceSet rs = getRS(menuRoot);

		/*
		 * Figure-out the rime name based on the File.menu name
		 */
		String rimName = menuRoot.getRootItem().getName();

		String rimFileName = "mnu" + rimName + ".rim";

		rimName = EMUtils.camelCaseName(rimName);
		/*
		 * Generate the rim structure
		 */
		
		/*
		 * Get the project name to find the -gen project. Pass it to the mapper as it will generate 
		 * a .properties files for Edge directly in the <project>-gen/src/generated/edge/META-INF/resources/WEB-INF/data/
		 * directory.
		 */
		SimplerEclipseResourceFileSystemAccess2 tmpFsa = (SimplerEclipseResourceFileSystemAccess2) fsa;
		String genProject = tmpFsa.getProject().getName() + "-gen";
		
		Menu2StreamMapper mapper = new Menu2StreamMapper(proxyUtilProvider, genProject);
		MapReturn<Void> generated = map(mapper, menuRoot, rimName, MENU_EXT + "/" + rimFileName, fsa, rs, springPRDGenerator);
		
		if (!InMemMenuTree.INSTANCE.isInitialized()){
			IProject project = ((SimplerEclipseResourceFileSystemAccess2) fsa).getProject();
			String s = ((SimplerEclipseResourceFileSystemAccess2) fsa).getOutputConfigurations().get(IFileSystemAccess.DEFAULT_OUTPUT).getOutputDirectory();
			File f = new File(project.getLocation().toFile(), s);
			File fMenuRims = new File(f, MENU_EXT);
			InMemMenuTree.INSTANCE.Initialize(fMenuRims);
		}
		
		InMemMenuTree.INSTANCE.update(rimFileName, generated.genText);

		IFileSystemAccessExtension2 fsa2 = (IFileSystemAccessExtension2) fsa;
		List<String> toRefresh = InMemMenuTree.INSTANCE.getParents(rimFileName);
		for (String sRim : toRefresh){
			URI uri = fsa2.getURI(MENU_EXT + "/" + sRim);
			Resource resource = rs.getResource(uri, true);
			if (resource != null) {
				if (logger.isDebugEnabled()){
					logger.debug("Menu Regenerate : " + sRim);
				}
				/*
				 * In fact, we want only to do a "touch" on the generated resource for the embedded server to reload them.
				 * TODO : investigate this direction.
				 */
				springPRDGenerator.generate(resource);
			}
		}
		
	}

	private void buildRimForApplicationVersion(Version version, ResourceSet rs, IFileSystemAccess fsa, SpringPRDGenerator springPRDGenerator)
			throws Exception {

		/*
		 * Figure-out the rime name based on the File.menu name
		 */
		String T24Name = version.getT24Name();

		String rimFileName = "ver" + T24Name + ".rim";

		String resourceName = "ver" + EMUtils.camelCaseName(T24Name);
		/*
		 * Generate the rim structure
		 */
		ApplicationVersion2StreamMapper mapper = new ApplicationVersion2StreamMapper();
		map(mapper, version, resourceName,  APP_EXT + "/" + rimFileName, fsa, rs, springPRDGenerator);
	}

	private <T> MapReturn<T> map(StreamMapper<T> mapper, EObject source, String rimName, String genFileName, IFileSystemAccess fsa, ResourceSet rs, SpringPRDGenerator springPRDGenerator) throws Exception {
		StringWriter sw = new StringWriter();
		RimWriter destination = new RimWriter(sw);
		List<T> mapReturn = mapper.map(source, destination, rimName);

		String genText = sw.toString();
		fsa.generateFile(genFileName, genText);

		/*
		 * Generate the java files
		 */
		IFileSystemAccessExtension2 fsa2 = (IFileSystemAccessExtension2) fsa;
		URI uri = fsa2.getURI(genFileName);
		if (!"memory".equals(uri.scheme())) { // memory:/ scheme is used in generator tests (GeneratorTestHelper / InMemoryFileSystemAccess)
			Resource resource = rs.getResource(uri, true);
			if (resource != null) {
				springPRDGenerator.generate(resource);
			}
		}

		return new MapReturn<T>(genText, mapReturn);
	}

	private static class MapReturn<T> {
		String genText;
		List<T> mapReturn;
		public MapReturn(String genText, List<T> mapReturn) {
			this.genText = genText;
			this.mapReturn = mapReturn;
		}
	}

	public void saveAndGenerateJava(IFileSystemAccess fsa, String foldersAndFileName, DomainModel rim, ResourceSet rs, SpringPRDGenerator springPRDGenerator) throws Exception {
		try {
			Resource resource = saveRimFromDomain(fsa, foldersAndFileName, rim, rs);
			springPRDGenerator.generate(resource);
		} catch (Exception e) {
			logger.error("Saving failed, current RS contents is: ", e);
		}
	}

	/**
	 * This (serialization of the RIM) is normally just for (human) debugging
	 * and understanding; or for "manual adoption" use cases.
	 */
	private Resource saveRimFromDomain(IFileSystemAccess fsa, String foldersAndFileName, DomainModel rim, ResourceSet rs)
			throws Exception {
		IFileSystemAccessExtension2 fsa2 = (IFileSystemAccessExtension2) fsa;
		URI uri = fsa2.getURI(foldersAndFileName);
		/*
		 * Create is it does not exist
		 */
		Resource resource = null;
		try {
			resource = rs.createResource(uri);
		} catch (Exception re) {

			/*
			 * When generating code for multiple, this is well possible that the
			 * resource is already loaded. Example : If Generating menu A having
			 * a link on Menu B, then B is loaded.
			 */
			resource = rs.getResource(uri, true);
			/*
			 * Make sure this is cleared.
			 */
			resource.getContents().clear();
		}
		/*
		 * Set the rim.
		 */
		resource.getContents().add(rim);

		// save
		new RIMSerializer().generate(resource, fsa, foldersAndFileName);
		// read and format
		resource.unload();
		resource = rs.getResource(uri, true);

		return resource;
	}

	/**
	 * This generates the *.java in *-models-gen, like it would for manual
	 * *-models/*.rim.
	 */
	class SpringPRDGenerator {
		private IFileSystemAccess fsa;

		public SpringPRDGenerator(IFileSystemAccess fsa) {
			this.fsa = fsa;
		}

		public void generate(Resource rimResource) throws Exception {
			URI rimURI = rimResource.getURI();

			// won't be initialised in test; we won't generate more
			if (irisJavaGeneratorFSA == null)
				return;

			Injector inject = irisJavaGeneratorInjectorProvider.get(rimURI);
			if (inject != null) {
				inject.getInstance(RIMDslGeneratorSpringPRD.class).doGenerate(rimResource, fsa);
			}
		}
	}

	// like in
	// com.odcgroup.workbench.generation.cartridge.ng.ResourceGenerator2.configureFileSystemAccess(IProject,
	// IFolder, IProgressMonitor).. ugly a hack, may be better supported by DS
	// Foundation later, if more Generators need this kind of "chaining" support
	private IFileSystemAccess getConfiguredIRISJavaGeneratorFSA(IFileSystemAccess originalFSA, IFileCallback changesNotifier) {
		if(originalFSA instanceof SimplerEclipseResourceFileSystemAccess2) {
			SimplerEclipseResourceFileSystemAccess2 _originalFSA = (SimplerEclipseResourceFileSystemAccess2) originalFSA;
			// switch from *-models to *-models-gen
			irisJavaGeneratorFSA.setProject(_originalFSA.getProject().getWorkspace().getRoot()
					.getProject(_originalFSA.getProject().getName() + "-gen"));
			irisJavaGeneratorFSA.setOutputPath("src/generated/iris"); // ugly
																			// copy/paste
																			// from
																			// com.odcgroup.workbench.generation.preferences.PreferenceInitializer
			irisJavaGeneratorFSA.getOutputConfigurations().get(IFileSystemAccess.DEFAULT_OUTPUT)
					.setCreateOutputDirectory(true);
			irisJavaGeneratorFSA.setMonitor(new NullProgressMonitor()); // NOTE
																		// EclipseResourceFileSystemAccess2
																		// *HAS* to
																		// have an
																		// IProgressMonitor
																		// (it
																		// doesn't
																		// check for
																		// null)
			/*
			 * This will automatically update the lastChanges file everytimes a file is updated.
			 */
			irisJavaGeneratorFSA.setPostProcessor(changesNotifier);
		}

		return irisJavaGeneratorFSA;
	}

	private ResourceSet getRS(EObject eo) {
		return eo.eResource().getResourceSet();
	}

	@Override
	// remove this method when CodeGenerator2 doesn't have to implement
	// CodeGenerator anymore
	public void run(IProgressMonitor monitor, IProject project, Collection<IOfsModelResource> modelResources,
			IFolder outputFolder, List<IStatus> nonOkStatuses) {
		throw new NotImplementedException();
	}


	public static String getDomain(Application application) {
		if (application != null && application.getModule() != null && application.getComponent() != null) {
			return application.getModule() + "." + application.getComponent();
		} else {
			return "T24";
		}
	}

	public static String getRimFoldersAndFileName(Application application, RESOURCE_TYPE resourceType, String rimName) {
		String rimFoldersAndFileName = resourceType.toString() + rimName + RIM_EXT;
		if (application != null && application.getModule() != null && application.getComponent() != null) {
			rimFoldersAndFileName = application.getModule() + "/" + application.getComponent() + "/"
					+ rimFoldersAndFileName;
		} else {
			rimFoldersAndFileName = "T24/" + rimFoldersAndFileName;
		}
		return rimFoldersAndFileName;
	}
	/**
	 * Return a Map of parameters parameters syntax is name = value | name2 =
	 * {value2} ... Handle the "I something" the same way as id = something
	 * 
	 * @param param
	 * @return
	 */
	public static Map<String, String> getParameterMap(String param) {
		HashMap<String, String> hmRet = new HashMap<String, String>();

		if (param == null) {
			return hmRet;
		}

		if (param.length() > 1 && param.charAt(1) == ' ') {
			param = "id = " + param.substring(2);
		}

		String[] aParam = param.split("\\|");
		for (String oneParam : aParam) {
			int pos = oneParam.indexOf("=");
			if (pos > 0) {
				if (!oneParam.substring(pos + 1).trim().equals("F3")) {
					hmRet.put(oneParam.substring(0, pos).trim(), oneParam.substring(pos + 1).trim());
				}
			}
		}
		return hmRet;
	}

	/**
	 * <code>MapperHelperImpl</code> is our private implementation of the public
	 * {@link MapperHelper} interface.
	 */
	private class MapperHelperImpl implements MapperHelper {
		private final ResourceSet m_resourceSet;
		private final SpringPRDGenerator springPRDGenerator;

		MapperHelperImpl(IFileSystemAccess p_fileSystemAccess, ResourceSet p_resourceSet, SpringPRDGenerator springPRDGenerator) {
			m_resourceSet = p_resourceSet;
			this.springPRDGenerator = springPRDGenerator;
		}

		@Override
        public ResourceSet getResourceSet() {
		    return m_resourceSet;
		}
		
		@Override
        public void generateJava(Resource rimResource) throws Exception {
			springPRDGenerator.generate(rimResource);
		}
	}

	/**
	 * Generate a version object with fieldsPerLine = 1 so this means "all fields"
	 * @param mdfClass
	 * @return
	 */
	private Version createDefaultVersionFromApplication(MdfClass mdfClass ){
		String sT24Name = T24Aspect.getT24Name(mdfClass);
		if (sT24Name == null){
			return null;
		}
		Version version = VersionDSLFactoryImpl.eINSTANCE.createVersion();
		version.setForApplication(mdfClass);
		version.setMetamodelVersion("1.30.6"); // SHould we get it from somewhere ?
		version.setT24Name(sT24Name);
		version.setShortName("");
		version.setNumberOfAuthorisers(1);
		version.setOtherCompanyAccess(YesNo.YES);
		version.setRecordsPerPage("1");
		version.setFieldsPerLine("1");
		return version;
	}
}
