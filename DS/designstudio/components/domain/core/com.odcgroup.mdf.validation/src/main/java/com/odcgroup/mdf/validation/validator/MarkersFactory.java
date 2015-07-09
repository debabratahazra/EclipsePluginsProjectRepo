package com.odcgroup.mdf.validation.validator;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;

import com.odcgroup.mdf.metamodel.MdfModelElement;


/**
 * TODO: DOCUMENT ME!
 *
 * @author <a href="mailto:cvedovini@odyssey-group.com">Claude Vedovini</a>
 * @version 1.0
 */
public class MarkersFactory implements ValidationListener {
    private static final Logger LOGGER =
        Logger.getLogger(MarkersFactory.class);
    private final IResource resource;

    /**
     * Constructor for MarkersFactory
     *
     * @param resource
     *
     * @throws CoreException
     */
    public MarkersFactory(IResource resource) throws CoreException {
        super();
        this.resource = resource;

        resource.deleteMarkers(MdfMarkerUtils.MARKER_ID, true,
            IResource.DEPTH_INFINITE);
    }

    /**
     * @see com.odcgroup.mdf.editor.model.ValidationListener#accept(com.odcgroup.mdf.metamodel.MdfModelElement,
     *      org.eclipse.core.runtime.IStatus)
     */
    public boolean onValidation(MdfModelElement model, IStatus status) {
        if (!status.isOK()) {
            createMarker(model, status);
        }

        return true;
    }

    @SuppressWarnings("deprecation")
	private void createMarker(MdfModelElement model, IStatus status) {
        if (status.isMultiStatus()) {
            IStatus[] children = status.getChildren();

            for (int i = 0; i < children.length; i++) {
                createMarker(model, children[i]);
            }
        } else {
            try {
                IMarker marker =
                    MdfMarkerUtils.createMarker(resource, status);

                if ((marker != null) && marker.exists()) {
                    marker.setAttribute(MdfMarkerUtils.MDF_NAME,
                        model.getQualifiedName().toString());
                }
            } catch (CoreException e) {
                LOGGER.error(e, e);
            }
        }
    }
}
