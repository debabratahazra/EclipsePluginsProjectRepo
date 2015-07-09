package com.odcgroup.mdf.editor.ui.providers.decorators;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IMarkerDelta;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.ListenerList;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EValidator;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.URIConverter;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IDecoration;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ILightweightLabelDecorator;
import org.eclipse.jface.viewers.LabelProviderChangedEvent;
import org.eclipse.swt.widgets.Display;

import com.odcgroup.mdf.editor.MdfCore;
import com.odcgroup.mdf.editor.MdfPlugin;
import com.odcgroup.mdf.metamodel.MdfModelElement;
import com.odcgroup.mdf.metamodel.MdfReverseAssociationWrapper;
import com.odcgroup.mdf.model.util.ModelVisitor;
import com.odcgroup.mdf.model.util.ModelWalker;
import com.odcgroup.workbench.core.repository.ModelURIConverter;
import com.odcgroup.workbench.core.repository.OfsResourceHelper;


/**
 * @author <a href="mailto:cvedovini@odyssey-group.com">Claude Vedovini</a>
 * @author pkk
 */
public class ValidationDecorator implements ILightweightLabelDecorator {
	
	/** The problem marker type for design studio */
	private final static String MARKER_TYPE = "com.odcgroup.workbench.validation.ProblemMarker";

    private static final ImageDescriptor WARNING;
    private static final ImageDescriptor ERROR;
    
    private ListenerList listeners;
    private IResourceChangeListener resourceListener;

    static {
        ERROR = MdfPlugin.getImageDescriptor(MdfCore.ICON_ERROR);
        WARNING = MdfPlugin.getImageDescriptor(MdfCore.ICON_WARNING);
    }
    
	/**
	 * @param eObj
	 * @return String
	 */
	private String getElementUri(EObject eObj) {
		URI widgetURI = EcoreUtil.getURI(eObj);
		String elementUri = null;
		if (widgetURI.isPlatform()) {
			elementUri = ModelURIConverter.toResourceURI(widgetURI).toString();
		} else {
			elementUri = widgetURI.toString(); 
		}
		return elementUri;
	}    

    /**
     * @see org.eclipse.jface.viewers.ILightweightLabelDecorator#decorate(java.lang.Object,
     *      org.eclipse.jface.viewers.IDecoration)
     */
    public void decorate(Object element, final IDecoration decoration) {
        if ((element instanceof MdfModelElement)
                && (element instanceof EObject)) {
            MdfModelElement model = (MdfModelElement) element;
            Resource eRes = ((EObject)element).eResource();
            
            if(eRes==null || eRes.getResourceSet()==null || !ModelURIConverter.isModelUri(eRes.getURI())) {
            	return;
            }
            
            URIConverter converter = eRes.getResourceSet().getURIConverter();
        	boolean isOfsProject = converter instanceof ModelURIConverter && ((ModelURIConverter)converter).getOfsProject() != null;
			if(!isOfsProject) {
        		return;
        	}
            if (model instanceof MdfReverseAssociationWrapper) {
            	model = ((MdfReverseAssociationWrapper) model).getInnerReverse();
            }

            // collect children URIs
            final Set<String> uris = new HashSet<String>(50);
            uris.add(getElementUri((EObject)element));
			ModelVisitor mv = new ModelVisitor() {
				public boolean accept(MdfModelElement model) {
					uris.add(getElementUri((EObject)model));
					return true;
				}
			};
            new ModelWalker(mv).visit(model);
            
    		Resource res = ((EObject)element).eResource();
    		if(res==null) return;
    		
    		IResource resource = OfsResourceHelper.getFile(res, res.getURI());
    		if (resource == null || !resource.exists())	return;
    		
    		IMarker[] markers = null;
    		try {
    			markers = resource.findMarkers(MARKER_TYPE, true, IResource.DEPTH_INFINITE);
    		} catch (CoreException ex) {
    			String message = "Error while retrieving validation markers for "+model.getQualifiedName();  //$NON-NLS-1$
    			IStatus status = new Status(IStatus.ERROR, MdfPlugin.getDefault().getBundle().getSymbolicName(),
    					IStatus.OK, message, ex);
    			MdfPlugin.getDefault().getLog().log(status);
    		}
    		if (markers == null || markers.length == 0) {
    			return;
    		}            
            
    		for (int i = 0; i < markers.length; i++) {
    			IMarker marker = markers[i];
    			String markerURI = marker.getAttribute(EValidator.URI_ATTRIBUTE, "");
    			if (uris.contains(markerURI)) {//$NON-NLS-1$
    				int nextSeverity = marker.getAttribute(IMarker.SEVERITY, IMarker.SEVERITY_INFO);
	                switch (nextSeverity) {
	                  case IMarker.SEVERITY_ERROR:
	                      decoration.addOverlay(ERROR, IDecoration.BOTTOM_LEFT);
	                      break;
	                  case IMarker.SEVERITY_WARNING:
	                      decoration.addOverlay(WARNING, IDecoration.BOTTOM_LEFT);
	                      break;
                    }
	                break;
    			}
    		}

        }
    }

    /* (non-Javadoc)
     * @see org.eclipse.jface.viewers.IBaseLabelProvider#addListener(org.eclipse.jface.viewers.ILabelProviderListener)
     */
    public void addListener(ILabelProviderListener listener) {
    	if (listeners == null) {
    		listeners = new ListenerList();
    	}
    	listeners.add(listener);
    	if (resourceListener == null) {
    		resourceListener = new IResourceChangeListener() {
				public void resourceChanged(IResourceChangeEvent event) {
					handleResourceChanged(event);					
				}
			};
			ResourcesPlugin.getWorkspace().addResourceChangeListener(resourceListener);
    	}
    }
    
    /**
     * @param event
     */
    private void handleResourceChanged(IResourceChangeEvent event) {
    	IMarkerDelta[] deltas = event.findMarkerDeltas(MARKER_TYPE, true);
    	if (deltas.length > 0) {
			if (Display.getCurrent() != Display.getDefault()) {
				asyncFireEObjectMarkerChanged();
			} else {
				fireEObjectMarkerChanged();
			}
		}
    }
    
    /**
	 * Send the events asynchronously
	 */
	private void asyncFireEObjectMarkerChanged() {
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				fireEObjectMarkerChanged();
			}
		});
	}

	/**
	 * Send an event to the LabelProviderListeners
	 */
	private void fireEObjectMarkerChanged() {
		if (listeners != null && !listeners.isEmpty()) {
			LabelProviderChangedEvent event = new LabelProviderChangedEvent(this);
			Object[] listenersArray = listeners.getListeners();
			for (int i = 0; i < listenersArray.length; i++) {
				((ILabelProviderListener) listenersArray[i]).labelProviderChanged(event);
			}
		}
	}
    

    /* (non-Javadoc)
     * @see org.eclipse.jface.viewers.IBaseLabelProvider#dispose()
     */
    public void dispose() {
		if (resourceListener != null) {
			ResourcesPlugin.getWorkspace().removeResourceChangeListener(resourceListener);
			resourceListener = null;
		}
    }

    /* (non-Javadoc)
     * @see org.eclipse.jface.viewers.IBaseLabelProvider#isLabelProperty(java.lang.Object, java.lang.String)
     */
    public boolean isLabelProperty(Object element, String property) {
        return false;
    }

    /* (non-Javadoc)
     * @see org.eclipse.jface.viewers.IBaseLabelProvider#removeListener(org.eclipse.jface.viewers.ILabelProviderListener)
     */
    public void removeListener(ILabelProviderListener listener) {
    	if (listeners != null) {
    		listeners.remove(listener);
    		if (listeners.isEmpty() && resourceListener != null) {
    			ResourcesPlugin.getWorkspace().removeResourceChangeListener(resourceListener);
    			resourceListener = null;
    		}
    	}
    }
}
