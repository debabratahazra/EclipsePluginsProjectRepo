package com.odcgroup.mdf.validation.validator;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EValidator;
import org.eclipse.emf.ecore.resource.Resource;

import com.odcgroup.mdf.validation.MdfValidationCore;
import com.odcgroup.workbench.core.IOfsModelResource;
import com.odcgroup.workbench.core.repository.ModelNotFoundException;
import com.odcgroup.workbench.core.repository.ModelURIConverter;


/**
 * @author <a href="mailto:cvedovini@odyssey-group.com">Claude Vedovini</a>
 * @version 1.0
 */
public class MdfMarkerUtils {

    private static final Logger LOGGER = Logger.getLogger(MdfMarkerUtils.class);

    @Deprecated
    public static final String MDF_NAME = EValidator.URI_ATTRIBUTE;
    public static final String MARKER_ID = "com.odcgroup.mdf.editor.markers";

    public static IMarker createTransientMarker(IResource resource, Throwable t) {
        return createTransientMarker(resource, MdfValidationCore.newStatus(t));
    }

    public static IMarker createTransientMarker(IResource resource,
            IStatus status) {
        IMarker marker = createMarker(resource, status);

        try {
            if ((marker != null) && marker.exists()) {
                marker.setAttribute(IMarker.TRANSIENT, true);
            }
        } catch (CoreException e) {
            LOGGER.error(e, e);
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
            LOGGER.error(e, e);
        }

        return marker;
    }

    public static void createMarkers(EObject eObject, IStatus status) {
        IFile file = getResource(eObject);
        Resource res = eObject.eResource();
        String fragment = res.getURIFragment(eObject);
        URI uri = res.getURI().appendFragment(fragment);

        createMarkers(file, uri, status);
    }

    @SuppressWarnings("unchecked")
	public static IMarker[] findMarkers(EObject eObject) {
        IFile file = getResource(eObject);
        Resource res = eObject.eResource();
        String fragment = res.getURIFragment(eObject);
        String uri = res.getURI().appendFragment(fragment).toString();

        try {
            IMarker[] markers = file.findMarkers(MARKER_ID, false,
                    IResource.DEPTH_ZERO);
            Collection objMarkers = new ArrayList(markers.length);

            for (IMarker marker : markers) {
                Object uriAttr = marker.getAttribute(EValidator.URI_ATTRIBUTE);
                if (uri.equals(uriAttr)) objMarkers.add(marker);
            }

            return (IMarker[]) objMarkers.toArray(new IMarker[objMarkers.size()]);
        } catch (CoreException e) {
            LOGGER.error("Could not find existing problem markers", e);
            return new IMarker[0];
        }
    }

    public static void deleteMarkers(Resource resource) {
        deleteMarkers(getFile(resource));
    }

    public static void deleteMarkers(IFile file) {
        try {
            file.deleteMarkers(MARKER_ID, false, IResource.DEPTH_ZERO);
        } catch (CoreException e) {
            LOGGER.error("Could not delete markers for file "
                    + file.getFullPath(), e);
        }
    }

    public static void updateMarkers(final Resource resource,
            final IProgressMonitor monitor) {
        final IFile file = getFile(resource);
        if(file==null) return;
        
        monitor.beginTask("Updating problem markers", IProgressMonitor.UNKNOWN);

        deleteMarkers(file);
        
        // DS-2253: deactivate legacy validation execution and permanent markers
        
//        final ModelVisitor validator = ValidatorsFactory.newInstance(new ValidationListener() {
//
//            public boolean onValidation(MdfModelElement model, IStatus status) {
//                if (!status.isOK()) {
//                    String fragment = resource.getURIFragment((EObject) model);
//                    URI uri = resource.getURI().appendFragment(fragment);
//                    createMarkers(file, uri, status);
//                }
//
//                return !monitor.isCanceled();
//            }
//        });

//        ModelVisitor visitor = new ModelVisitor() {
//
//            public boolean accept(MdfModelElement model) {
//                if (monitor.isCanceled()) return false;
//
//                monitor.subTask("Validating " + model.getQualifiedName());
//                return validator.accept(model);
//            }
//        };
//
//        ModelWalker walker = new ModelWalker(visitor);
//
//        for (EObject object : resource.getContents()) {
//            if (object instanceof MdfModelElement) {
//                walker.visit((MdfModelElement) object);
//            }
//        }
//
        monitor.done();
    }

    private static void createMarkers(IFile file, URI uri, IStatus status) {
        if (status.isMultiStatus()) {
            for (IStatus child : status.getChildren()) {
                createMarkers(file, uri, child);
            }
        } else {
            IMarker marker = createMarker(file, status);

            try {
                marker.setAttribute(EValidator.URI_ATTRIBUTE, uri.toString());
            } catch (CoreException e) {
                LOGGER.error("Problem creating marker", e);
            }
        }
    }

    public static IFile getResource(EObject object) {
        return getFile(object.eResource());
    }

    /**
     * @param resource
     * @return
     */
    public static IFile getFile(Resource resource) {
        URI uri = resource.getURI();
        IFile file = null;
        ModelURIConverter converter = (ModelURIConverter) resource.getResourceSet().getURIConverter();
		try {
			IOfsModelResource modelResource = converter.getOfsProject().getOfsModelResource(uri);
			if(modelResource==null) return null; // apparently the file is not in a valid ofs project, so ignore it
			IResource res = modelResource.getResource();
			if (res != null && res instanceof IFile) {
				file = (IFile)res;
			}
		} catch (ModelNotFoundException e) {
			LOGGER.error("Problem retrieving model resource for " + uri, e);
		}
		return file;
    }

}
