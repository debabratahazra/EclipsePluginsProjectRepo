package com.odcgroup.workbench.tap.validation;

import java.util.Collection;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EValidator;
import org.eclipse.emf.ecore.resource.Resource.Diagnostic;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.validation.marker.IMarkerConfigurator;
import org.eclipse.emf.validation.model.IConstraintStatus;
import org.eclipse.emf.validation.service.IValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.odcgroup.workbench.core.IOfsModelFolder;
import com.odcgroup.workbench.core.IOfsModelResource;
import com.odcgroup.workbench.core.IOfsProject;

/**
 * This class provides convenient methods for managing validation markers.
 *
 * @author Kai Kreuzer
 *
 */
public class ValidationMarkerUtil {
	
	public static final Logger logger = LoggerFactory.getLogger(ValidationMarkerUtil.class);
	
    public static final String MARKER_ID = ValidationCore.PLUGIN_ID + ".ProblemMarker";

    public static IMarker createTransientMarker(IResource resource, Throwable t) {
        return createTransientMarker(resource, new Status(IStatus.ERROR, ValidationCore.PLUGIN_ID, t.getMessage(), t));
    }

    public static IMarker createTransientMarker(IResource resource,
            IStatus status) {
        IMarker marker = createMarker(resource, status);

        try {
            if ((marker != null) && marker.exists()) {
                marker.setAttribute(IMarker.TRANSIENT, true);
            }
        } catch (CoreException e) {
            ValidationCore.getDefault().logError(e.getMessage(), e);
        }

        return marker;
    }

    public static IMarker createMarker(IResource resource, IStatus status) {
        IMarker marker = null;

        try {
            marker = resource.createMarker(MARKER_ID);

            if (marker.exists()) {
                marker.setAttribute(IMarker.MESSAGE, status.getMessage());

                switch (status.getSeverity()) {
                    case IStatus.ERROR:
                        marker.setAttribute(IMarker.SEVERITY,
                                IMarker.SEVERITY_ERROR);
                        break;

                    case IStatus.WARNING:
                        marker.setAttribute(IMarker.SEVERITY,
                                IMarker.SEVERITY_WARNING);
                        break;

                    case IStatus.INFO:
                        marker.setAttribute(IMarker.SEVERITY,
                                IMarker.SEVERITY_INFO);
                }
            }
        } catch (CoreException e) {
        	ValidationCore.getDefault().logError(e.getMessage(), e);
        }

        return marker;
    }

    public static void deleteMarkers(IOfsModelResource modelResource) {
    	if(modelResource.getResource()!=null && modelResource.getResource().exists()) {
	        try {
	            modelResource.getResource().deleteMarkers(MARKER_ID, false, IResource.DEPTH_ZERO);
	        } catch (CoreException e) {
	        	ValidationCore.getDefault().logError("Could not delete markers for file "
	                    + modelResource.getFullPath(), e);
	        }
    	} else {
    		// For "virtual" resources, we put the markers on the model folder. Hence
    		// we have to delete them from there as well, but we have to take care to only
    		// delete those markers of the given ofs resource.
    		IOfsModelFolder modelFolder = modelResource.getOfsProject().getModelFolder(modelResource.getURI().fileExtension());
    		IFolder folder = (IFolder) modelFolder.getResource();
    		try {
				IMarker[] markers = folder.findMarkers(MARKER_ID, false, 0);
				for(IMarker marker : markers) {
					String location = marker.getAttribute(IMarker.LOCATION, "");
					if(location.equals(modelResource.getURI().toString())) {
						marker.delete();
					}
				}
			} catch (CoreException e) {
	        	ValidationCore.getDefault().logError("Could not read markers for folder "
	                    + folder.getFullPath(), e);
			}
    	}
    }

	/**
	 * @param modelResource
	 * @param status
	 * @param monitor
	 */
	public static void updateMarkers(IOfsModelResource modelResource, IStatus status, IProgressMonitor monitor) {
       	deleteMarkers(modelResource);
        
        if (!status.isOK()) {
        	try {
				createMarkers(modelResource, status, MARKER_ID);
			} catch (CoreException e) {
				logger.error("Could not create marker on resource {}", modelResource.getURI(), e);
			}
        }
	}

	public static void updateMarkers(IOfsModelResource modelResource,
			Collection<Diagnostic> result, IProgressMonitor monitor) {
       	deleteMarkers(modelResource);
       	
        if (result.size() > 0) {
        	try {
				createMarkers(modelResource, result, MARKER_ID);
			} catch (CoreException e) {
				logger.error("Could not create marker on resource {}", modelResource.getURI(), e);
			}
        }
	}

	/**
	 * Creates markers with the provided marker type for all resources that had validation
	 * failures or warnings. An options marker configurator is provided in order to populate the
	 * marker with additional information.
	 * @param modelResource TODO
	 * @param validationStatus A status object returned by a validator's validate method.
	 * @param markerType A marker type that is a subtype of the validationProblem marker type.
	 * 
	 * @throws CoreException A core exception is thrown if there were any problems interacting
	 *  with the workspace to attach/delete markers on resources.
	 * @see IValidator#validate(Object)
	 * @see IValidator#validate(java.util.Collection)
	 * @see IMarkerConfigurator
	 * 
	 * @since 1.2
	 */
	public static void createMarkers(final IOfsModelResource modelResource, final IStatus validationStatus, final String markerType) throws CoreException {
		
		if (validationStatus.isOK()) {
			return;
		}
		
		IWorkspaceRunnable runnable = new IWorkspaceRunnable() {
			public void run(IProgressMonitor m)
				throws CoreException {
				
				if (validationStatus.isMultiStatus()) {
					IStatus[] children = validationStatus.getChildren();
					for (IStatus element : children) {
						if (element instanceof IConstraintStatus) {
							createMarker(modelResource, (IConstraintStatus)element, markerType);
						}
					}
				} else if (validationStatus instanceof IConstraintStatus) {
					createMarker(modelResource, (IConstraintStatus)validationStatus, markerType);
				}
			}
		};
		
		ResourcesPlugin.getWorkspace().run(runnable, modelResource.getResource(), IWorkspace.AVOID_UPDATE, null);
	}

	/**
	 * Creates markers for all given diagnostics
	 *
	 * @param markerType A marker type that is a subtype of the validationProblem marker type.
	 * 
	 * @throws CoreException A core exception is thrown if there were any problems interacting
	 *  with the workspace to attach/delete markers on resources.
	 * 
	 */
	public static void createMarkers(final IOfsModelResource modelResource, final Collection<Diagnostic> diagnostics, final String markerType) throws CoreException {
		
		IWorkspaceRunnable runnable = new IWorkspaceRunnable() {
			public void run(IProgressMonitor m)
				throws CoreException {
				
				for(Diagnostic diag : diagnostics) {
					createErrorMarker(modelResource, diag, markerType);
				}
			}
		};
		
		ResourcesPlugin.getWorkspace().run(runnable, modelResource.getResource(), IWorkspace.AVOID_UPDATE, null);
	}

	private static void createMarker(IOfsModelResource modelResource, IConstraintStatus status,
			String markerType) throws CoreException {

		if (modelResource != null) {
			URI uri = modelResource.getURI();
			IOfsProject ofsProject = modelResource.getOfsProject();
	
			if (!status.matches(IStatus.INFO | IStatus.ERROR | IStatus.WARNING
	                | IStatus.CANCEL)) {
	                return;
	        }

			boolean virtualResource = modelResource.getResource()==null||!modelResource.getResource().exists();
			IMarker marker = virtualResource ? 
					ofsProject.getModelFolder(modelResource.getURI().fileExtension()).getResource().createMarker(markerType) :
					modelResource.getResource().createMarker(markerType);

			switch (status.getSeverity()) {
                case IStatus.INFO:
                    marker.setAttribute(IMarker.SEVERITY,
                        IMarker.SEVERITY_INFO);
                    marker.setAttribute(IMarker.PRIORITY,
                        IMarker.PRIORITY_LOW);
                    break;
				case IStatus.WARNING:
					marker.setAttribute(IMarker.SEVERITY,
						IMarker.SEVERITY_WARNING);
					marker.setAttribute(IMarker.PRIORITY,
						IMarker.PRIORITY_NORMAL);
					break;
				case IStatus.ERROR:
				case IStatus.CANCEL:
					marker.setAttribute(IMarker.SEVERITY,
						IMarker.SEVERITY_ERROR);
					marker.setAttribute(IMarker.PRIORITY,
						IMarker.PRIORITY_HIGH);
                    break;
			}

			marker.setAttribute(IMarker.MESSAGE, status.getMessage());
			marker.setAttribute(IMarker.LOCATION, uri.toString());
			URI objectURI = EcoreUtil.getURI(status.getTarget());

			if(objectURI!=null) {
				marker.setAttribute(EValidator.URI_ATTRIBUTE, objectURI.toString());
			}
		}
	}

	private static void createErrorMarker(IOfsModelResource modelResource, Diagnostic diag,	String markerType) throws CoreException {

		if (modelResource != null) {
			IMarker marker = modelResource.getResource().createMarker(markerType);
			marker.setAttribute(IMarker.SEVERITY, IMarker.SEVERITY_ERROR);
			marker.setAttribute(IMarker.MESSAGE, diag.getMessage());
			marker.setAttribute(IMarker.LOCATION, diag.getLocation());
			marker.setAttribute(IMarker.LINE_NUMBER, diag.getLine());
		}
	}

}
