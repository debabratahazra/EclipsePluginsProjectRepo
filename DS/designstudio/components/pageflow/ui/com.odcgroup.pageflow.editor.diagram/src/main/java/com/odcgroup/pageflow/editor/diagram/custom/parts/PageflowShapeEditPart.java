package com.odcgroup.pageflow.editor.diagram.custom.parts;

import org.eclipse.core.resources.IResource;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.diagram.ui.editparts.AbstractBorderedShapeEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.OpenEditPolicy;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import com.odcgroup.pageflow.editor.diagram.custom.policies.PageflowSelectionEditPolicy;
import com.odcgroup.pageflow.model.Pageflow;
import com.odcgroup.pageflow.model.SubPageflowState;
import com.odcgroup.pageflow.model.ViewState;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.core.repository.ModelURIConverter;
import com.odcgroup.workbench.core.repository.OfsResourceHelper;
import com.odcgroup.workbench.ui.helper.OfsEditorUtil;

/**
 * @author pkk
 *
 */
public abstract class PageflowShapeEditPart extends AbstractBorderedShapeEditPart {	

	/**
	 * @param view
	 */
	public PageflowShapeEditPart(View view) {
		super(view);
	}

	
	/* (non-Javadoc)
	 * @see org.eclipse.gmf.runtime.diagram.ui.editparts.AbstractBorderedShapeEditPart#createDefaultEditPolicies()
	 */
	protected void createDefaultEditPolicies() {
		super.createDefaultEditPolicies();
		installEditPolicy(EditPolicyRoles.OPEN_ROLE,  createOpenEditPolicy());
	}

	/**
	 * opens the property view on double-click of pageflow items
	 * @return
	 */
	protected OpenEditPolicy createOpenEditPolicy(){
		OpenEditPolicy oep = new OpenEditPolicy() {			
			protected Command getOpenCommand(Request request) {
				IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
				
				Node node = (Node) getHost().getModel();
				EObject eobj = node.getElement();				
				IOfsProject ofsProject = OfsResourceHelper.getOfsProject(eobj.eResource());
				if (ofsProject == null) {
					return null;
				}
				try {
					if (eobj instanceof SubPageflowState){
						openReferencedPageflow(ofsProject, (SubPageflowState)eobj, window);
					} else if (eobj instanceof ViewState){
						openReferencedPage(ofsProject, (ViewState)eobj, window);
					}
					// show the propertysheet view
					window.getActivePage().showView("org.eclipse.ui.views.PropertySheet");
				} catch(Exception ex){
					ex.printStackTrace();
				}
				return null;
			}
			
		};
		return oep;
	}
	
	/**
	 * @param ofsProject
	 * @param subpf
	 * @param window
	 */
	private void openReferencedPageflow(IOfsProject ofsProject, SubPageflowState subpf, IWorkbenchWindow window){
		Pageflow pageflow = subpf.getSubPageflow();		
		if (pageflow == null){
			return;
		}
		URI uri = pageflow.eResource().getURI();
		if (uri.isPlatform()) {
			IResource res = (IResource) OfsResourceHelper.getFile(ofsProject, uri);
			uri = ModelURIConverter.createModelURI(res);
		}
		// open the referenced pageflow
		OfsEditorUtil.openEditor(ofsProject, uri);	
	}
	
	/**
	 * @param ofsProject
	 * @param viewState
	 * @param window
	 */
	private void openReferencedPage(IOfsProject ofsProject, ViewState viewState, IWorkbenchWindow window) {
		// OCS-23280
		// on double-click of viewstate, open page
		com.odcgroup.pageflow.model.View view = viewState.getView();
		if (view != null)  {
			String viewUri = view.getUrl();
			if (viewUri != null && !viewUri.equals("")){
				if ( viewUri.startsWith("resource:") && (viewUri.endsWith(".page"))){				
					URI viewURI = URI.createURI(viewUri);
					// open the referenced page
					OfsEditorUtil.openEditor(ofsProject, viewURI);				
				}
			}
		}
	
	}
	
	
	/* (non-Javadoc)
	 * @see org.eclipse.gmf.runtime.diagram.ui.editparts.ShapeEditPart#getPrimaryDragEditPolicy()
	 */
	public EditPolicy getPrimaryDragEditPolicy() {
		return new PageflowSelectionEditPolicy();
	}
	

}

