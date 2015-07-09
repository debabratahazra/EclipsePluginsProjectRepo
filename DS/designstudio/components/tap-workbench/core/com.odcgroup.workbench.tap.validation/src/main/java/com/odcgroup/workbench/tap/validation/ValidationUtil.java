package com.odcgroup.workbench.tap.validation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

import org.apache.commons.lang.ArrayUtils;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.Resource.Diagnostic;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.validation.model.EvaluationMode;
import org.eclipse.emf.validation.service.IBatchValidator;
import org.eclipse.emf.validation.service.ModelValidationService;

import com.odcgroup.workbench.core.IMetaModelVersioned;
import com.odcgroup.workbench.core.IOfsModelResource;
import com.odcgroup.workbench.core.InvalidMetamodelVersionException;
import com.odcgroup.workbench.core.OfsCore;
import com.odcgroup.workbench.dsl.resources.AbstractDSLResource;
import com.odcgroup.workbench.tap.validation.internal.strategy.FlatTraversalStrategy;

/**
 * General methods used for validation of model resources
 * 
 * @author Kai Kreuzer
 * 
 */
public class ValidationUtil {
	
	private static IBatchValidator validator = null;
	private static ValidationUtil instance = null;
	
	private ValidationUtil() {}
	
	public static synchronized ValidationUtil getInstance() {
		if (instance == null) {
			instance = new ValidationUtil();
		}
		return instance;
	}
	
	/**
	 * @return
	 */
	public synchronized IBatchValidator getBatchValidator() {
		if (validator == null) {
			validator = createBatchValidator();
		}
		return validator;
	}

	public static IStatus validate(IOfsModelResource modelResource, IProgressMonitor monitor, boolean updateMarkers) {
		MultiStatus status = new MultiStatus(ValidationCore.PLUGIN_ID, IStatus.OK, "OK", null);
		
		// check if the model is outdated, then we do not run the validation on it
		IMetaModelVersioned versionedModelResource = (IMetaModelVersioned) Platform.getAdapterManager().loadAdapter(modelResource, IMetaModelVersioned.class.getName());
		if(versionedModelResource!=null) {
			try {
				if(!versionedModelResource.needsMigration()) {
					boolean wasLoaded = modelResource.isLoaded();

					try {
						IStatus contentStatus = validateContents(modelResource, monitor);
						if(contentStatus.isMultiStatus()) {
							status.addAll(contentStatus);
						} else {
							status.add(contentStatus);
						}
						if(updateMarkers) ValidationMarkerUtil.updateMarkers(modelResource, status, monitor);
					} catch (IOException e) {
						if(modelResource.getResource()!=null && !modelResource.getResource().exists()) {
							// this resource does not exist anymore
							return Status.OK_STATUS;
						} else {
							Collection<Diagnostic> result = validateFileSyntax(modelResource, false, monitor);
							if(result.size() != 0) {
								if(updateMarkers) ValidationMarkerUtil.updateMarkers(modelResource, result, monitor);
								// convert the diagnostics to statuses
								for(Diagnostic diag : result) {
									IStatus diagStatus = new Status(IStatus.ERROR, ValidationCore.PLUGIN_ID, 
											diag.getMessage() + " in line " + diag.getLine());
									status.add(diagStatus);
								}
							} else {
								ValidationCore.getDefault().logError("Could not load model '" + modelResource.getName() + "' for validation", e);
								status.add(new Status(IStatus.ERROR, ValidationCore.PLUGIN_ID, "Could not load model '" + modelResource.getName() + "' for validation"));
							}
						}
					}

					// release memory again
					if(!wasLoaded && ArrayUtils.contains(OfsCore.getEagerlyLoadedModelNames(), modelResource.getURI().fileExtension())) {
						modelResource.unload();
					}
				} else {
					if(updateMarkers) ValidationMarkerUtil.updateMarkers(modelResource, status, monitor);					
				}
			} catch (CoreException e) {
			} catch (InvalidMetamodelVersionException e) {
			}
		}		
		return status;
	}

	public static Collection<Diagnostic> validateFileSyntax(IOfsModelResource modelResource, boolean updateMarkers, IProgressMonitor monitor) {
		Collection<Diagnostic> result = new ArrayList<Diagnostic>();
		try {
			Resource resource = modelResource.getOfsProject().getModelResourceSet().getResource(modelResource.getURI(), false);
			if(resource instanceof AbstractDSLResource) {
				AbstractDSLResource dslResource = (AbstractDSLResource) resource;
				if(!dslResource.isLoaded()) {
					try {
						dslResource.load(null);
					} catch (IOException e) {
						result = new ArrayList<Diagnostic>(((AbstractDSLResource)resource).getErrors());
						dslResource.unload();
					}
				} else {
					result = ((AbstractDSLResource)resource).getErrors();
				}
			}
			if(updateMarkers && !result.isEmpty()) ValidationMarkerUtil.updateMarkers(modelResource, result, monitor);					
		} catch(NoClassDefFoundError e) {
			// the optional plugin com.odcgroup.workbench.dsl does not seem to be present, so don't do any syntax validation
		}
		return new ArrayList<Diagnostic>(result);
	}

	public static Collection<Diagnostic> validateResourceSyntax(AbstractDSLResource dslResource) {
		Collection<Diagnostic> result = new ArrayList<Diagnostic>();

		// we need to make sure that it is unloaded as we have to load it with keeping the parse results
//		dslResource.unload();
//		dslResource.setKeepParserResult(true);
//		// now try to load the resource
//		try {
//			dslResource.load(null);
//		} catch (IOException e) {
//			dslResource.unload();
//		}
		result.addAll(dslResource.getErrors());
		
		// get rid off the parse results again
//		dslResource.setKeepParserResult(false);
//		dslResource.unload();
		
		return result;
	}

	private static IStatus validateContents(
			IOfsModelResource modelResource, IProgressMonitor monitor) throws IOException {
		Collection<EObject> model = null;
		try {
			model = modelResource.getEMFModel();
		} catch (InvalidMetamodelVersionException e) {
			// do not validate models of a wrong metamodel version
		}
		
		Iterator<EObject> iterator = EcoreUtil.<EObject> getAllProperContents(model, false);

		Collection<EObject> noProxyObjects = new HashSet<EObject>();
		try {
			while (iterator.hasNext()) {
				EObject eObj = iterator.next();
				if (!eObj.eIsProxy()) {
					noProxyObjects.add(eObj);
				}
			}
		} catch (NullPointerException e) {
			// sometimes the validation is run when the model has just been unloaded, what results in a NPE.
			// we can simply ignore this 
		}
		return validateFlat(noProxyObjects, monitor);
	}

	private static IStatus validate(IBatchValidator validator, Collection<EObject> eObjects, IProgressMonitor monitor) {
		IStatus status = validator.validate(eObjects, monitor);
		return status;
	}

	private static IStatus validateFlat(Collection<EObject> eObjects, IProgressMonitor monitor) {
		IBatchValidator validator = createBatchValidator();
		return validate(validator, eObjects, monitor);
	}

	public static IBatchValidator createBatchValidator() {
		IBatchValidator validator = (IBatchValidator) ModelValidationService.getInstance().newValidator(
				EvaluationMode.BATCH);
		validator.setIncludeLiveConstraints(true);
		validator.setTraversalStrategy(new FlatTraversalStrategy());
		return validator;
	}

}
