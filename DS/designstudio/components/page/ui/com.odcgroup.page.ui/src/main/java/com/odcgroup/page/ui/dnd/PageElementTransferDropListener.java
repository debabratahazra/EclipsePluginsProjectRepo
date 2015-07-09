package com.odcgroup.page.ui.dnd;

import java.io.IOException;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.Request;
import org.eclipse.gef.dnd.AbstractTransferDropTargetListener;
import org.eclipse.jface.util.LocalSelectionTransfer;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DropTargetEvent;

import com.odcgroup.page.common.PageConstants;
import com.odcgroup.page.log.Logger;
import com.odcgroup.page.metamodel.IncludeabilityRule;
import com.odcgroup.page.metamodel.WidgetLibraryConstants;
import com.odcgroup.page.metamodel.WidgetType;
import com.odcgroup.page.metamodel.WidgetTypeConstants;
import com.odcgroup.page.metamodel.util.MetaModelRegistry;
import com.odcgroup.page.model.Model;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.util.SearchModuleUtils;
import com.odcgroup.page.model.util.WidgetUtils;
import com.odcgroup.page.ui.PageUIPlugin;
import com.odcgroup.page.ui.request.IncludeRequest;
import com.odcgroup.page.ui.util.EclipseUtils;
import com.odcgroup.workbench.core.IOfsModelResource;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.core.InvalidMetamodelVersionException;
import com.odcgroup.workbench.core.OfsCore;
import com.odcgroup.workbench.core.repository.ModelURIConverter;

/**
 * performs a drop of fragment or module on page designer.
 * 
 * @author <a href="mailto:atripod@odyssey-group.com">Alain Tripod</a>
 */
public class PageElementTransferDropListener extends AbstractTransferDropTargetListener {

	/**
	 * Create a new PageElementTransferDropListener.
	 * 
	 * @param viewer
	 *            The EditPartViewer
	 */
	public PageElementTransferDropListener(EditPartViewer viewer) {
		super(viewer, LocalSelectionTransfer.getTransfer());
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.dnd.AbstractTransferDropTargetListener#isEnabled(org.eclipse.swt.dnd.DropTargetEvent)
	 */
	public boolean isEnabled(DropTargetEvent event) {
		for (int i = 0; i < event.dataTypes.length; i++) {
			if (getTransfer().isSupportedType(event.dataTypes[i])) {
				IncludeRequest request = (IncludeRequest) getTargetRequest();
				if (request == null) {
					return false;
				}
			}
		}
		return super.isEnabled(event);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.dnd.AbstractTransferDropTargetListener#handleDragOver()
	 */
	protected void handleDragOver() {

		getCurrentEvent().detail = DND.DROP_NONE;

		EditPart targetEditPart = getViewer().findObjectAt(getDropLocation());

		IOfsModelResource modelResource = getDroppedModelResource();
		if (modelResource == null) {
			return;
		}

		EObject droppedModel = null;
		try {
			droppedModel = modelResource.getEMFModel().get(0);
		} catch (IOException e) {
			PageUIPlugin.getDefault().logError("Error accessing page model", e);
		} catch (InvalidMetamodelVersionException e) {
			PageUIPlugin.getDefault().logError("Error accessing page model", e);
		}

		IProject project = modelResource.getOfsProject().getProject();
		if (null == project) {
			return;
		}

		if (!OfsCore.isOfsProject(project)) {
			Logger.warning("You cannot drop item not defined in Design Studio project.");
			return;
		}

		IOfsProject ofsActiveProject = EclipseUtils.findCurrentProject();
		if (!ofsActiveProject.ofsModelResourceExists(modelResource.getURI())) {
			// the project p is not referenced by the current project.
			// so the drop is not allowed.
			StringBuilder message = new StringBuilder();
			message.append("You cannot drop this item [");
			message.append(modelResource.getFullPath());
			message.append("], the project ");
			message.append(ofsActiveProject.getProject().getName());
			message.append(" has no dependency with project ");
			message.append(project.getName());
			Logger.warning(message.toString());
			return;
		}

		if (targetEditPart.getModel() instanceof Widget) {

			Widget targetWidget = (Widget) targetEditPart.getModel();
			Widget rootTargetWidget = targetWidget.getRootWidget();

			if (droppedModel instanceof Model) {

				Widget droppedWidget = ((Model) droppedModel).getWidget();
				
				// retrieve the includability rule
				IncludeabilityRule includeRule = (IncludeabilityRule) 
					WidgetUtils.getIncludeability()
							   .findAccountabilityRule(droppedWidget.getType(), rootTargetWidget.getType());

				if (WidgetUtils.canIncludeChild(rootTargetWidget, droppedWidget)) {
					// tablecolumn includese fragment
					if (targetWidget.getTypeName().equals(WidgetTypeConstants.TABLE_COLUMN) 
							&& droppedWidget.getTypeName().equals(WidgetTypeConstants.FRAGMENT)) {
						return;
					}

					// detect cyclic reference
					boolean hasCyclicReference = false;
					Widget temp = targetWidget;
					URI droppedURI = EcoreUtil.getURI(droppedWidget);
					while (temp != null && !hasCyclicReference) {
						URI tempURI = ModelURIConverter.toResourceURI(EcoreUtil.getURI(temp));
						hasCyclicReference = droppedURI.equals(tempURI);
//						hasCyclicReference = droppedWidget.getXmiId().equals(temp.getXmiId());
						temp = temp.getParent();
					}

					if (!hasCyclicReference) {								

						WidgetType wt = MetaModelRegistry.getMetaModel().findWidgetType(WidgetLibraryConstants.XGUI,
								WidgetTypeConstants.INCLUDE);
						
						boolean canContains = wt != null && WidgetUtils.canContainChild(targetWidget, wt, false);
						if (canContains && includeRule.isLimitedAccountability()) {
							canContains &= WidgetUtils.canContainChild(targetWidget, droppedWidget.getType(), true);
						}

						// check special accountability rules
						canContains &= checkSpecialAccountabilityRules(targetWidget, droppedWidget);

						if (canContains) {
							getCurrentEvent().detail = DND.DROP_COPY;
							getViewer().select(targetEditPart);
						}
					}
				
				}

			}

		}
	}

	/**
	 * return the selected IOfsModelResource to be dropped
	 * 
	 * @return IOfsModelResource
	 */
	protected IOfsModelResource getDroppedModelResource() {
		ISelection selection = LocalSelectionTransfer.getTransfer().getSelection();
		if (((IStructuredSelection) selection).getFirstElement() instanceof IOfsModelResource) {
			return (IOfsModelResource) ((IStructuredSelection) selection).getFirstElement();
		} else {
			return null;
		}
	}

	/**
	 * Handles the drop. This tries to open the file using EMF.
	 */
	protected void handleDrop() {
		EditPart targetEditPart = getViewer().findObjectAt(getDropLocation());

		IOfsModelResource modelResource = getDroppedModelResource();
		if (modelResource == null) {
			return;
		}

		EObject droppedModel;
		try {
			droppedModel = modelResource.getEMFModel().get(0);
		} catch (IOException e) {
			PageUIPlugin.getDefault().logError("Error accessing page model", e);
			return;
		} catch (InvalidMetamodelVersionException e) {
			PageUIPlugin.getDefault().logError("Error accessing page model", e);
			return;
		}
		if (droppedModel != null && droppedModel instanceof Model) {

			IncludeRequest request = (IncludeRequest) getTargetRequest();
			if (request != null) {
				
				request.setModel((Model) droppedModel);

				getViewer().setFocus(targetEditPart);
				super.handleDrop();
			}
		}

	}

	/**
	 * The target request is a CreateViewPageRequest.
	 * 
	 * @return Request
	 */
	protected Request createTargetRequest() {
		Request request = null;

		IOfsModelResource resource = getDroppedModelResource();
		if (resource != null) {
			String extension = resource.getURI().fileExtension();
	
			for (String ext : PageConstants.DROPABLE_FILE_EXTENSION) {
				if (extension.equals(ext)) {
					request = new IncludeRequest();
					break;
				}
			}
			
		}

		return request;
	}

	/**
	 * Assumes that the target request is a {@link IncludeRequest}.
	 */
	protected void updateTargetRequest() {
		IncludeRequest request = (IncludeRequest) getTargetRequest();
		if (request != null) {
			request.setLocation(getDropLocation());
		}
	}

	/**
	 * Override the base-class version to set the target EditPart correctly. The
	 * target EditPart is simply the EditPart which contains the figure which
	 * contains the location of the mouse when the user releases the mouse
	 * button.
	 */
	protected void updateTargetEditPart() {
		EditPart targetEditPart = getViewer().findObjectAt(getDropLocation());
		setTargetEditPart(targetEditPart);
	}
	
	/**
	 * Check special D&N rules that cannot yet be expressed using accountability rules.
	 * 
	 * @param targetWidget
	 * @param droppedWidget
	 * 
	 * @return {@code true} if the droppedWidget can be dropped into the target Widget.
	 */
	private boolean checkSpecialAccountabilityRules(Widget targetWidget, Widget droppedWidget) {
		return SearchModuleUtils.canContainChild(targetWidget, droppedWidget);
	}

}