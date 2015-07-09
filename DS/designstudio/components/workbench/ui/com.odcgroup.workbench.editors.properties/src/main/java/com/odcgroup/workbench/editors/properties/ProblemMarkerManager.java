package com.odcgroup.workbench.editors.properties;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EValidator;
import org.eclipse.emf.ecore.util.EcoreUtil;

import com.odcgroup.workbench.core.repository.ModelURIConverter;

/**
 *
 * @author pkk
 *
 */
public class ProblemMarkerManager {
	
	private static final String MARKER_TYPE = "com.odcgroup.workbench.validation.ProblemMarker";
	
	/**
	 * @param element
	 * @return
	 */
	public static IMarker[] getProblemMarkers(EObject element) {		
		if (element != null && element.eResource() != null) {
			List<IMarker> markerList = new ArrayList<IMarker>();
			
			URI puri = element.eResource().getURI();
			String filePath = URI.decode(puri.path());
			IResource resource = ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(filePath));
			if (resource == null) {
				return null;
			}
			IMarker[] markers = null;
			try {
				markers = resource.findMarkers(MARKER_TYPE, true, IResource.DEPTH_INFINITE);
				URI ecoreUri = EcoreUtil.getURI(element);
				if(ModelURIConverter.isModelUri(ecoreUri)) {
				URI elementUri = ModelURIConverter.toResourceURI(ecoreUri);
					for (IMarker marker : markers) {
						String uri = marker.getAttribute(EValidator.URI_ATTRIBUTE, "");
						if (uri.equals(elementUri.toString())) {
							markerList.add(marker);
						}
					}
				}
				return markerList.toArray(new IMarker[0]);
			} catch (CoreException e) {
				//logError("Validation markers refresh failure", e); 
			}			
		}
		return null;
	}

}
