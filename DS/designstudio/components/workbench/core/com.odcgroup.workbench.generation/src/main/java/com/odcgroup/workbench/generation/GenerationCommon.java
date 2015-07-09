package com.odcgroup.workbench.generation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.eclipse.xtext.util.CancelIndicator;
import org.eclipse.xtext.validation.CheckMode;
import org.eclipse.xtext.validation.IResourceValidator;
import org.eclipse.xtext.validation.Issue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.odcgroup.workbench.core.IOfsModelResource;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.core.OfsCore;
import com.odcgroup.workbench.core.di.StandaloneHeadlessStatus;
import com.odcgroup.workbench.core.helper.FeatureSwitches;
import com.odcgroup.workbench.core.helper.Timer;
import com.odcgroup.workbench.core.logging.MemoryLogUtil;
import com.odcgroup.workbench.core.repository.ModelURIConverter;
import com.odcgroup.workbench.core.xtext.XtextResourceSetProviderDS;
import com.odcgroup.workbench.generation.cartridge.CodeCartridge;
import com.odcgroup.workbench.generation.cartridge.ng.BridgeCodeGenerator;
import com.odcgroup.workbench.generation.cartridge.ng.BridgeCodeGenerator2;
import com.odcgroup.workbench.generation.cartridge.ng.BridgeStatefullCodeGenerator;
import com.odcgroup.workbench.generation.cartridge.ng.CodeGenerator2;
import com.odcgroup.workbench.generation.cartridge.ng.ModelLoader;
import com.odcgroup.workbench.generation.cartridge.ng.ModelLoaderImpl;
import com.odcgroup.workbench.generation.cartridge.ng.StatefullCodeGenerator;
import com.odcgroup.workbench.generation.cartridge.ng.di.GenerationRuntimeModule;
import com.odcgroup.workbench.generation.properties.PropertyHelper;

/**
 * Code common
 * to com.odcgroup.workbench.generation.headless.RunGeneration.executeCodeGeneration(IProject[]) 
 * and com.odcgroup.workbench.generation.ui.action.CodeGenerationAction.run(IAction).
 *
 * @author Michael Vorburger, based on refactoring from Kai's code 
 */
public class GenerationCommon {
	
	public static final Status STATUS_USER_CANCELLATION = new Status(Status.CANCEL, GenerationCore.PLUGIN_ID, "The user canceled the generation.");
	private static final Logger logger = LoggerFactory.getLogger(GenerationCommon.class);
	
	
	public IStatus doGenerateNGC(IProject project, Collection<IResource> resources, Set<IFolder> foldersToRefresh, IProgressMonitor monitor) {
		Timer allGen = new Timer().start();
		
		// get over all selected cartridges, and split them into old/new style lists
		List<CodeCartridge> newCodeGenerators = new ArrayList<CodeCartridge>();
		List<CodeCartridge> oldCodeGenerators = new ArrayList<CodeCartridge>();
        List<IStatus> nonOkStatuses = new ArrayList<IStatus>();
        parseCodeCartridges(project, newCodeGenerators, oldCodeGenerators, nonOkStatuses);        
		
		int newgensize = newCodeGenerators.size(); 
		logger.info("About to run {} new-style code generators", newgensize);
		monitor.beginTask("", newgensize);
		generateNewGenCodeCartridges(newCodeGenerators, resources, project, foldersToRefresh, nonOkStatuses, monitor);
		
		logger.info("Completed all code generation cartridges, in total it took {}", allGen.stopAndText());
		IStatus status = refreshFolders(foldersToRefresh);
		if (!status.isOK()) {
			nonOkStatuses.add(status);
		}
		return buildFinalStatus(nonOkStatuses);
	}
	
	private void parseCodeCartridges(IProject project, List<CodeCartridge> newAge, List<CodeCartridge> oldAge, List<IStatus> nonOkStatuses) {
		final CodeCartridge[] cartridges = PropertyHelper.getSelectedCodeCartridges(project);
		for (final CodeCartridge cartridge : cartridges) { // iterate over all selected cartridges
			if ( ! cartridge.isCodeGeneratorDefined() ) {
				logger.error("No code generator has been defined for the cartridge " +
						"(id:" + cartridge.getId() + ", name:"+cartridge.getName()+")");
				nonOkStatuses.add(new Status(IStatus.ERROR, GenerationCore.PLUGIN_ID, "No code generator has been defined for the cartridge " +
						"(id:" + cartridge.getId() + ", name:"+cartridge.getName()+")"));
				continue;
			}
			if (cartridge.isInstanceOf(CodeGenerator2.class)) {
				newAge.add(cartridge);
			} else {
				oldAge.add(cartridge);
			}
		}
	}

	public IStatus doGeneration(IProject project, Collection<IOfsModelResource> ofsResources, Set<IFolder> foldersToRefresh, IProgressMonitor monitor) {
		Collection<IResource> iResources = transform(ofsResources);
		return doGenerationWithBothOldAndNewStyleResources(project, ofsResources, iResources, foldersToRefresh, monitor);
	}

	public IStatus doGenerationWithIResources(IProject project, Collection<IResource> iResources, Set<IFolder> foldersToRefresh, IProgressMonitor monitor) {
		return doGenerationWithBothOldAndNewStyleResources(project, null, iResources, foldersToRefresh, monitor);
	}

	private IStatus doGenerationWithBothOldAndNewStyleResources(IProject project, Collection<IOfsModelResource> ofsResources, Collection<IResource> iResources, Set<IFolder> foldersToRefresh, IProgressMonitor monitor) {
		Timer allGen = new Timer().start();
		
		// get over all selected cartridges, and split them into old/new style lists
		List<CodeCartridge> newCodeGenerators = new ArrayList<CodeCartridge>();
		List<CodeCartridge> oldCodeGenerators = new ArrayList<CodeCartridge>();
        List<IStatus> nonOkStatuses = new ArrayList<IStatus>();
        parseCodeCartridges(project, newCodeGenerators, oldCodeGenerators, nonOkStatuses);        
		
		int newgensize = newCodeGenerators.size();
		int oldgensize = oldCodeGenerators.size();
		
        logger.info("About to run {} new-style and {} classic style code generators", newgensize, oldgensize);

		monitor.beginTask("", newgensize+oldgensize);

		if (!oldCodeGenerators.isEmpty() && ofsResources == null) {
			logger.info("There ARE some classic (old-style) code generators, and ofsResources == null, so using ModelsHelper.transform() to turn IResource into IOfsModelResource"); 
			ofsResources = ModelsHelper.transform(project, iResources);
		}
		for (CodeCartridge cartridge : oldCodeGenerators) {
			IStatus status = generate(cartridge, project, ofsResources, foldersToRefresh, 
					new SubProgressMonitor(monitor, 1, SubProgressMonitor.PREPEND_MAIN_LABEL_TO_SUBTASK), nonOkStatuses);
			if (badGenerationStatus(status, foldersToRefresh)) {
				if (status == Status.CANCEL_STATUS) {
					return STATUS_USER_CANCELLATION;
				}
				nonOkStatuses.add(status);
			}
		}
		
		clearOfsProjectCaches(project);
		// generation on new gen code cartridges
		generateNewGenCodeCartridges(newCodeGenerators, iResources, project, foldersToRefresh, nonOkStatuses, monitor);
		
        logger.info("Completed all code generation cartridges, in total it took {}", allGen.stopAndText());
		IStatus status = refreshFolders(foldersToRefresh);
		if (!status.isOK()) {
			nonOkStatuses.add(status);
		}
		return buildFinalStatus(nonOkStatuses);
	}
	
	private Collection<IResource> transform(Collection<IOfsModelResource> mResources) {
		Collection<IResource> resources = new CopyOnWriteArraySet<IResource>();
		for(IOfsModelResource res : mResources) {
			resources.add(res.getResource());
		}
		return resources;
	}
	
	
	private IStatus generateNewGenCodeCartridges(List<CodeCartridge> cartridges, Collection<IResource> resources, 
			IProject project, Set<IFolder> foldersToRefresh, List<IStatus> nonOkStatuses, IProgressMonitor monitor) {
		
		if (!cartridges.isEmpty()) {
			XtextResourceSet rs = XtextResourceSetProviderDS.newXtextResourceSet(XtextResourceSet.class, project);
			ModelLoader modelLoader = new ModelLoaderImpl(rs);

			if (StandaloneHeadlessStatus.INSTANCE.isInStandaloneHeadless()) {
				preload(project, rs, resources);
				validate(rs);
			}
			
			for (CodeCartridge cartridge : cartridges) {
				BridgeCodeGenerator generator = null;
				if (cartridge.isInstanceOf(StatefullCodeGenerator.class)) {
					// For the moment, until (if one day..) we have complete DI, we just do this here:
					Injector injector = Guice.createInjector(new GenerationRuntimeModule());
					BridgeStatefullCodeGenerator bridgeStatefullCodeGenerator = injector.getInstance(BridgeStatefullCodeGenerator.class);
					bridgeStatefullCodeGenerator.setModelLoader(modelLoader);
					// DS-8868 StatefullCodeGenerator does not use Guice dependency injection
					// NOTE That this is not and cannot be exactly identical to what happens
					// below and in BridgeCodeGenerator2 for CodeGenerator2, which get
					// re-instantiated and injected for each generation; that's
					// because a StatefullCodeGenerator, by definition, gets
					// created only once for all models, so it cannot get
					// language (URI) specific injections, only globals.
					StatefullCodeGenerator statefulCodeGenerator = (StatefullCodeGenerator)injector.getInstance(cartridge.getCodeGeneratorClass()); // DS-8868 NOT cartridge.getCodeGen();
					bridgeStatefullCodeGenerator.setCodeGenerator(statefulCodeGenerator);
					generator = bridgeStatefullCodeGenerator;
				} else if (cartridge.isInstanceOf(CodeGenerator2.class)) {
					// For the moment, until (if one day..) we have complete DI, we just do this here:
					Injector injector = Guice.createInjector(new GenerationRuntimeModule());
					BridgeCodeGenerator2 bridgeCodeGenerator2 = injector.getInstance(BridgeCodeGenerator2.class);
					bridgeCodeGenerator2.setModelLoader(modelLoader);
					CodeGenerator2 codeGenerator2 = (CodeGenerator2) cartridge.getCodeGen();
					bridgeCodeGenerator2.setCodeGenerator2Class(codeGenerator2.getClass());
					generator = bridgeCodeGenerator2;
				}
				if (generator != null) { 
					IStatus status = generate(cartridge, (BridgeCodeGenerator) generator, project, resources, foldersToRefresh,
							new SubProgressMonitor(monitor, 1, SubProgressMonitor.PREPEND_MAIN_LABEL_TO_SUBTASK), nonOkStatuses);
					if (badGenerationStatus(status, foldersToRefresh)) {
						if (status == Status.CANCEL_STATUS) {
							return STATUS_USER_CANCELLATION;
						}
						nonOkStatuses.add(status);
					}
				} else {
					IStatus status = new Status(IStatus.ERROR, GenerationCore.PLUGIN_ID, 
							"The code generator does not implement the required interface for the cartridge " +
							"(id:" + cartridge.getId() + ", name:"+cartridge.getName()+")");
					nonOkStatuses.add(status);
				}
			}
			rs = null; // rs.getResources().clear(); shouldn't be needed...
			clearOfsProjectCaches(project);
	        logger.info("Completed running all new style code generators, and (should) have freed up up memory used by them");
			MemoryLogUtil.logMemory(logger);
		}
		return null;
	}

	private void clearOfsProjectCaches(IProject project) {
		// At this point we should be done with using the old-style
		// ModelResourceSet infrastructure (used also by the DS
		// Validation), so we can clear it, to make
		// heap space for the new approach.
		OfsCore.getOfsProjectManager().invalidateOfsProject(project);
		// and *NOT* just IOfsProject.clearCache() - that wouldn't be enough..
        logger.info("Completed running all classic style code generators (and DS validations and migrations), and (should) have freed up up memory used by them");
		MemoryLogUtil.logMemory(logger);
	}

	private IStatus buildFinalStatus(List<IStatus> nonOkStatuses) {
		if (nonOkStatuses.size() == 0) {
			return Status.OK_STATUS;
		} else {
			return new MultiStatus(GenerationCore.PLUGIN_ID, Status.ERROR, 
					nonOkStatuses.toArray(new IStatus[nonOkStatuses.size()]), 
					"Code generation failed for some models (" + nonOkStatuses.size() + " errors). \n" +
					"Please make sure the models are valid. \n\n" +
					"The first error is:\n" + 
					nonOkStatuses.get(0).getMessage(), null);
		}
	}

	private boolean badGenerationStatus(IStatus status, Set<IFolder> foldersToRefresh) {
		boolean badGenerationStatus = !status.isOK();
		if (badGenerationStatus) {
			// If we had a generation error, and are about to return, refresh folders anyways..
			// But, in this case, ignore the refreshFolders returned IStatus (as code above will return the, typically more interesting, real generation error)
			refreshFolders(foldersToRefresh);
		}
		return badGenerationStatus;
	}

	private IStatus refreshFolders(Set<IFolder> foldersToRefresh) {
		Timer refreshTimer = new Timer().start();
        logger.info("Completed successfully refreshing {} folders, in total it took {}", foldersToRefresh.size(), refreshTimer.stopAndText());
		return Status.OK_STATUS;
	}
	
	private void preload(IProject project, XtextResourceSet rs, Collection<IResource> resources) {
		Timer preloadTimer = new Timer().start();
		logger.info("About to pre-load {} models...", resources.size());
		for (IResource res : resources) {
			URI uri = URI.createPlatformResourceURI(res.getFullPath().toString(), true);
			try {
				rs.getResource(uri, true);
			} catch (RuntimeException e) {
				logger.error("Could not load model, going to ignore it (this will likely cause problems later for other models referencing anything in it); dsURI = {}: " + e.getMessage(), new Object[] { uri, e });
			}
		}

		Timer indexingTimer = new Timer().start();
		logger.info("About to index models...");
		new StandaloneIndexer().doIndex(rs);
		logger.info("Done indexing {} models in {}", resources.size(), indexingTimer.stopAndText());

		logger.info("Done pre-loading {} models (req. for CodeGenerator2 cross refs) in {} total (incl. indexing)", resources.size(), preloadTimer.stopAndText());
		MemoryLogUtil.logMemory(logger);
	}

	// This is, intentionally, only used if (StandaloneHeadlessStatus.INSTANCE.isInStandaloneHeadless())
	// (because it's called from generateNewGenCodeCartridges() only if running in the Generator edition)
	// In the IDE UI mode, the Xtext Builder will already have this, incrementally, and with proper Error markers instead of logs.
	private void validate(ResourceSet resourceSet) {
		Timer preloadTimer = new Timer().start();
		EList<Resource> resources = resourceSet.getResources();
		int initialSize = resources.size();
		logger.info("About to validate {} models...", initialSize);
		boolean hadError = false;
		
		// A regular Iterator-based for causes a ConcurrentModificationException here, so we do this:
		// (an index-based alternative may not be safe, because the position of content may change if it's not guaranteed to add at the end only)
		List<Resource> fixedResources = new ArrayList<Resource>(resources);
		for (Resource resource : fixedResources) {
			if (!(resource instanceof XtextResource))
				continue;
			
			IResourceValidator validator = ((XtextResource) resource).getResourceServiceProvider().getResourceValidator();
			List<Issue> issues = validator.validate(resource, CheckMode.ALL, CancelIndicator.NullImpl);
			
			for (Issue issue : issues) {
				switch (issue.getSeverity()) {
				case ERROR:
					logger.error(issue.toString());
					hadError = true;
					break;
				case WARNING:
					logger.warn(issue.toString());
					break;
				case INFO:
					logger.info(issue.toString());
					break;
				case IGNORE:
					// huh? Do nothing.
				default:
					break;
				}
			}
		}
		
		logger.info("Done validating {} models in {}", initialSize, preloadTimer.stopAndText());
//		if (initialSize != resources.size())
//			logger.warn("Note that the original number of pre-loaded models to validate in the incoming RS and the number of resources in the RS now differ - this is curious; what else could have been loaded by now, and from where? ({})", resources.size());
		MemoryLogUtil.logMemory(logger);

		if (hadError) {
			if (!FeatureSwitches.generationIgnoreValidationErrors.get()) {
				throw new IllegalArgumentException("Design Studio generation is aborting due to model validation errors - please fix 'em first (generator cannot work on models with broken references etc.)");
			} else {
				logger.error("!!! BEWARE: You have decided to force generation despite model validation errors.  This is unsupported, and a Bad Idea(tm) - it is HIGHLY recommended to fix the model validation errors, instead of using the " + FeatureSwitches.generationIgnoreValidationErrors.toString());  
			}
		}
	}

	/**
	 * MUST pre-load all resources into the RS..
	 * This is NOT done for "perf. optim." but HAS to be done so that
	 * cross ref. resolution works correctly if Xtext in Standalone mode.
	 * It works correctly without this in UI mode, because there the builder
	 * built the Index.  With *StandaloneSetup(), Xtext relies on everything
	 * being in the RS. Also @see https://bugs.eclipse.org/bugs/show_bug.cgi?id=427739
	 * and upcoming new org.eclipse.xtext.builder.standalone infrastructure..
	 * @see http://rd.oams.com/browse/DS-7054 bug we had that raised this.
	 * 
	 * TODO LATER when upgrading Xtext, switch all this to the new org.eclipse.xtext.builder.standalone infra..
	 */
	private void preload(ResourceSet rs, IProject project, Collection<IOfsModelResource> resources) {
		Timer preloadTimer = new Timer().start();
		logger.info("About to pre-load {} models...", resources.size());
		IOfsProject modelsOfsProject = OfsCore.getOfsProjectManager().getOfsProject(project);
		ModelURIConverter modelURIConverter = new ModelURIConverter(modelsOfsProject);
		for (IOfsModelResource iOfsModelResource : resources) {
			URI dsURI = iOfsModelResource.getURI();
			// in-line with com.odcgroup.workbench.generation.cartridge.ng.BridgeCodeGenerator2.convertToStandardURI(IOfsProject, URI)
			URI stdURI = modelURIConverter.toPlatformURI(dsURI);
			try {
				rs.getResource(stdURI, true);
			} catch (RuntimeException e) {
				logger.error("Could not load model, going to ignore it (this will likely cause problems later for other models referencing anything in it); dsURI = {}, stdURI = {}: " + e.getMessage(), new Object[] { dsURI, stdURI, e });
			}
			// TODO monitor how-to?
		}
		logger.info("Done pre-loading {} models (req. for CodeGenerator2 cross refs) in {}", resources.size(), preloadTimer.stopAndText());
		MemoryLogUtil.logMemory(logger);
	}
	
	private IStatus generate(CodeCartridge cartridge, BridgeCodeGenerator generator, IProject project, Collection<IResource> resources, Set<IFolder> foldersToRefresh, IProgressMonitor monitor, List<IStatus> nonOkStatuses) {
		final IFolder outputFolder = GenerationCore.getJavaSourceFolder(project, cartridge.getCategory());

		String cartridgeFullName = cartridge.getCategory().toString() + ": " + cartridge.getName();
		logger.info("Generating code, cartridge {} into {} ...", cartridgeFullName, outputFolder.toString());

		Timer oneCartridgeGen = new Timer().start();

		monitor.beginTask("Executing " + cartridgeFullName + " for ", resources.size());

		try {
			generator.run(project, resources, outputFolder, nonOkStatuses, monitor);
		} finally {
			foldersToRefresh.add(outputFolder);
		}

		logger.info("Completed successfully generating code for cartridge {}, it took {}", cartridgeFullName, oneCartridgeGen.stopAndText());
		MemoryLogUtil.logMemory(logger);
		monitor.done();
		if (monitor.isCanceled())
			return Status.CANCEL_STATUS;
		else if (!nonOkStatuses.isEmpty()) {
			return new Status(IStatus.ERROR, GenerationCore.PLUGIN_ID, "");
		} else {
			return Status.OK_STATUS;
		}
		
	}

	private IStatus generate(CodeCartridge cartridge, IProject project, Collection<IOfsModelResource> resources, Set<IFolder> foldersToRefresh, IProgressMonitor monitor, List<IStatus> nonOkStatuses) {
        final IFolder outputFolder = GenerationCore.getJavaSourceFolder(project, cartridge.getCategory());

        String cartridgeFullName = cartridge.getCategory().toString() + ": " + cartridge.getName();
		logger.info("Generating code, cartridge {} into {} ...", cartridgeFullName, outputFolder.toString());
		
		Timer oneCartridgeGen = new Timer().start();

		monitor.beginTask("Executing " + cartridgeFullName + " for ", resources.size());

		try {
			cartridge.getCodeGen().run(monitor, project, resources, outputFolder, nonOkStatuses);
		} catch ( Exception ex ) {
			nonOkStatuses.add(new Status(IStatus.ERROR, GenerationCore.PLUGIN_ID, "", ex));
		} finally {
			foldersToRefresh.add(outputFolder);
		}
		
        logger.info("Completed successfully generating code for cartridge {}, it took {}", cartridgeFullName, oneCartridgeGen.stopAndText());
		MemoryLogUtil.logMemory(logger);
		monitor.done();
		if(monitor.isCanceled())
			return Status.CANCEL_STATUS;
		else if (!nonOkStatuses.isEmpty()) {
			return new Status(IStatus.ERROR, GenerationCore.PLUGIN_ID, "");
		} else
			return Status.OK_STATUS;
	}
	
}
