package com.odcgroup.page.ui.figure;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.draw2d.FlowLayout;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EValidator;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

import com.odcgroup.page.model.Event;
import com.odcgroup.page.model.Parameter;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.ui.PageUIPlugin;
import com.odcgroup.workbench.core.repository.ModelURIConverter;
import com.odcgroup.workbench.core.repository.OfsResourceHelper;

/**
 * Decorates the figures by adding warning or error icons.
 * NOTE: These are only added in Design Mode.
 * 
 * @author atr
 */
public class ValidationFigureDecorator implements WidgetFigureDecorator {
	
	/** The problem marker type for design studio */
	private final String MARKER_TYPE = "com.odcgroup.workbench.validation.ProblemMarker";
	
	/** */
	private Set<String> uris = new HashSet<String>(50);
	
	/**
	 * Decorates the figure. This is called after paint.
	 * 
	 * @param figure The figure to decorate
	 * @param graphics the Graphics context
	 */
	public void paint(IWidgetFigure figure, Graphics graphics) {
		
		if (! figure.getFigureContext().isDesignMode()) {
			return;
		}		

		Label error = getError(figure);
		if (error == null) {
			figure.setToolTip(error);	
			// Nothing to paint
			return;
		}
		
		
		Rectangle b = figure.getBounds();
		graphics.translate(b.x, b.y);
		figure.setToolTip(error);	
		Image image = null; 
		if (error.getChildren().isEmpty()) {
			image = error.getIcon();
		} else {
			image = PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJS_ERROR_TSK);
		}
		graphics.drawImage(image, 0, 0);
		graphics.translate(-b.x, -b.y);		
		
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
	 * Chooses which image to display. Errors are displayed in priority to warnings and
	 * warnings are displayed in priority to info messages.
	 * 
	 * @param figure The figure to display
	 * @return Image The Image to display or null if no messages exist for the figure
	 */
	private Label getError(IWidgetFigure figure) {
		Widget widget = figure.getWidget();
		
		// query for all the validation markers of the current resource
		int severity = IMarker.SEVERITY_INFO;
		IMarker foundMarker = null;
		Resource eResource = widget.eResource();
		// incase of proxy
		if (eResource == null && widget.eIsProxy()) {
			IFigure parentFig = figure.getParent();
			if (parentFig instanceof IWidgetFigure) {
				IWidgetFigure parent = (IWidgetFigure)parentFig;
				if (parent != null) {
		      		InternalEObject eObj = (InternalEObject) widget;
					EObject model = EcoreUtil.resolve(eObj, parent.getWidget());
					if (model != null) {
						eResource = model.eResource();
					}
				}
			}
		}
		
		if (eResource == null) {
			return null;
		}
		
		IResource resource = OfsResourceHelper.getFile(eResource);
		if (resource == null || !resource.exists()) {
			return null;
		}
		
		uris.clear();
		uris.add(getElementUri(widget));
		
		// TODO move this in the rendering metamodel
		if (widget.getTypeName().equals("TableTree")) {
			for (Widget w: widget.getContents()) {
				if (!w.getTypeName().equals("TableColumn")) {
					uris.add(getElementUri(w));
				}
			}
		}
		
		for (Property property : widget.getProperties()) {
			uris.add(getElementUri(property));
		}
		for (Event event : widget.getEvents()) {
			uris.add(getElementUri(event));
			for (Parameter parameter : event.getParameters()) {
				uris.add(getElementUri(parameter));
			}
		}
		
		IMarker[] markers = null;
		try {
			markers = resource.findMarkers(MARKER_TYPE, true, IResource.DEPTH_INFINITE);
		} catch (CoreException ex) {
			String message = "Validation markers refresh failure";  //$NON-NLS-1$
			IStatus status = new Status(IStatus.ERROR, PageUIPlugin.getDefault().getBundle().getSymbolicName(),
					IStatus.OK, message, ex);
			PageUIPlugin.getDefault().getLog().log(status);
		}
		if (markers == null || markers.length == 0) {
			return null;
		}
		Label toolTip = null;
		for (int i = 0; i < markers.length; i++) {
			IMarker marker = markers[i];
			String uri = marker.getAttribute(EValidator.URI_ATTRIBUTE, ""); //$NON-NLS-1$
			if (uris.contains(uri)) {
				int nextSeverity = marker.getAttribute(IMarker.SEVERITY, IMarker.SEVERITY_INFO);
				Image nextImage = getImage(nextSeverity);
				if (foundMarker == null) {
					foundMarker = marker;
					toolTip = new Label(marker.getAttribute(IMarker.MESSAGE, ""), //$NON-NLS-1$
							nextImage);
				} else {
					if (toolTip.getChildren().isEmpty()) {
						Label compositeLabel = new Label();
						FlowLayout fl = new FlowLayout(false);
						fl.setMinorSpacing(0);
						compositeLabel.setLayoutManager(fl);
						compositeLabel.add(toolTip);
						toolTip = compositeLabel;
					}
					toolTip.add(new Label(marker.getAttribute(IMarker.MESSAGE, ""), //$NON-NLS-1$
							nextImage));
				}
				severity = (nextSeverity > severity) ? nextSeverity : severity;
			}
		}
		if (foundMarker == null) {
			return null;
		}
		
		return toolTip;

	}

	/**
	 * @param severity
	 * @return Image
	 */
	private Image getImage(int severity) {
		String imageName = ISharedImages.IMG_OBJS_ERROR_TSK;
		switch (severity) {
		case IMarker.SEVERITY_ERROR:
			imageName = ISharedImages.IMG_OBJS_ERROR_TSK;
			break;
		case IMarker.SEVERITY_WARNING:
			imageName = ISharedImages.IMG_OBJS_WARN_TSK;
			break;
		default:
			imageName = ISharedImages.IMG_OBJS_INFO_TSK;
		}
		return PlatformUI.getWorkbench().getSharedImages().getImage(imageName);
	}	
	
}
