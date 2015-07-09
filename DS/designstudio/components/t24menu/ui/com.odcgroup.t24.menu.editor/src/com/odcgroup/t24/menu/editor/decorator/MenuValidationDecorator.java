package com.odcgroup.t24.menu.editor.decorator;

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
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IDecoration;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ILightweightLabelDecorator;
import org.eclipse.jface.viewers.LabelProviderChangedEvent;
import org.eclipse.swt.widgets.Display;
import org.osgi.framework.Bundle;

import com.odcgroup.t24.menu.menu.presentation.MenuEditorPlugin;
import com.odcgroup.workbench.core.repository.ModelURIConverter;
import com.odcgroup.workbench.core.repository.OfsResourceHelper;

/**
 * @author pkk
 */
public class MenuValidationDecorator implements ILightweightLabelDecorator {

	/** The problem marker type for design studio */
	private final static String MARKER_TYPE = "com.odcgroup.workbench.validation.ProblemMarker";

	private static final ImageDescriptor WARNING;
	private static final ImageDescriptor ERROR;
	public static final String ICON_ERROR = "error.gif";
	public static final String ICON_WARNING = "warning.gif";

	private ListenerList listeners;
	private IResourceChangeListener resourceListener;

	static {
		Bundle bundle = MenuEditorPlugin.getPlugin().getBundle();
		ERROR = ImageDescriptor.createFromURL(bundle.getEntry("icons/" + ICON_ERROR));
		WARNING = ImageDescriptor.createFromURL(bundle.getEntry("icons/" + ICON_WARNING));
	}


	/**
	 * @see org.eclipse.jface.viewers.ILightweightLabelDecorator#decorate(java.lang.Object,
	 *      org.eclipse.jface.viewers.IDecoration)
	 */
	public void decorate(Object element, final IDecoration decoration) {
		if ((element instanceof EObject)) {
			EObject eObj = (EObject) element;
			Resource res = eObj.eResource();
			if (res == null)
				return;

			IResource resource = OfsResourceHelper.getFile(res, res.getURI());
			if (resource == null || !resource.exists())
				return;

			IMarker[] markers = null;
			try {
				markers = resource.findMarkers(MARKER_TYPE, true, IResource.DEPTH_INFINITE);
			} catch (CoreException ex) {
				String message = "Error while retrieving validation markers"; //$NON-NLS-1$
				IStatus status = new Status(IStatus.ERROR,
						MenuEditorPlugin.PLUGIN_ID, IStatus.OK, message, ex);
				MenuEditorPlugin.INSTANCE.getPluginLogger().log(status);
			}
			
			if (markers == null || markers.length == 0) {
				return;
			}
			
			for (IMarker marker : markers) {
				int nextSeverity = marker.getAttribute(IMarker.SEVERITY, IMarker.SEVERITY_INFO);
    			String markerURI = marker.getAttribute(EValidator.URI_ATTRIBUTE, "");
    			if(getElementUri(eObj).equals(markerURI)) {
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.viewers.IBaseLabelProvider#addListener(org.eclipse.
	 * jface.viewers.ILabelProviderListener)
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
			ResourcesPlugin.getWorkspace().addResourceChangeListener(
					resourceListener);
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
			LabelProviderChangedEvent event = new LabelProviderChangedEvent(
					this);
			Object[] listenersArray = listeners.getListeners();
			for (int i = 0; i < listenersArray.length; i++) {
				((ILabelProviderListener) listenersArray[i])
						.labelProviderChanged(event);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.viewers.IBaseLabelProvider#dispose()
	 */
	public void dispose() {
		if (resourceListener != null) {
			ResourcesPlugin.getWorkspace().removeResourceChangeListener(
					resourceListener);
			resourceListener = null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.viewers.IBaseLabelProvider#isLabelProperty(java.lang
	 * .Object, java.lang.String)
	 */
	public boolean isLabelProperty(Object element, String property) {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.viewers.IBaseLabelProvider#removeListener(org.eclipse
	 * .jface.viewers.ILabelProviderListener)
	 */
	public void removeListener(ILabelProviderListener listener) {
		if (listeners != null) {
			listeners.remove(listener);
			if (listeners.isEmpty() && resourceListener != null) {
				ResourcesPlugin.getWorkspace().removeResourceChangeListener(
						resourceListener);
				resourceListener = null;
			}
		}
	}

}
