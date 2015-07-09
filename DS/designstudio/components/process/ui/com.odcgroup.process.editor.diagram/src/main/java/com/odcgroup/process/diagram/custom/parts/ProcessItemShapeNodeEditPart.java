package com.odcgroup.process.diagram.custom.parts;

import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ShapeNodeEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.OpenEditPolicy;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.gmf.runtime.notation.impl.NodeImpl;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import com.odcgroup.process.diagram.custom.policies.ProcessItemSelectionEditPolicy;
import com.odcgroup.process.model.Flow;
import com.odcgroup.process.model.ProcessItem;
import com.odcgroup.process.model.Task;
import com.odcgroup.process.model.UserTask;
import com.odcgroup.translation.core.ITranslation;
import com.odcgroup.translation.core.ITranslationManager;
import com.odcgroup.translation.core.TranslationCore;
import com.odcgroup.translation.ui.views.ITranslationModel;
import com.odcgroup.translation.ui.views.model.TranslationModel;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.core.repository.ModelURIConverter;
import com.odcgroup.workbench.core.repository.OfsResourceHelper;
import com.odcgroup.workbench.ui.helper.OfsEditorUtil;

/**
 * @author pkk
 *
 */
public abstract class ProcessItemShapeNodeEditPart extends ShapeNodeEditPart {
	
	private IOfsProject ofsProject;

	/**
	 * @param view
	 */
	public ProcessItemShapeNodeEditPart(View view) {
		super(view);
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.gmf.runtime.diagram.ui.editparts.ShapeNodeEditPart#createDefaultEditPolicies()
	 */
	protected void createDefaultEditPolicies() {
		super.createDefaultEditPolicies();
		installEditPolicy(EditPolicy.SELECTION_FEEDBACK_ROLE, new ProcessItemSelectionEditPolicy());
		installEditPolicy(EditPolicyRoles.OPEN_ROLE,  createOpenEditPolicy());
	}
	
	/**
	 * @return
	 */
	protected OpenEditPolicy createOpenEditPolicy(){
		OpenEditPolicy oep = new OpenEditPolicy() {			
			protected Command getOpenCommand(Request request) {
				IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
				
				Node node = (Node) getHost().getModel();
				EObject eobj = node.getElement();
				ResourceSet rSet = eobj.eResource().getResourceSet();
				ModelURIConverter mConverter = (ModelURIConverter) rSet.getURIConverter();
				IOfsProject project = mConverter.getOfsProject();
				if (project == null) {
					return null;
				}
				try {
					// if double-clicked edit-part model is usertask, 
					// open the pageflow in different editor
					if (eobj instanceof UserTask && ((UserTask)eobj).getPageflow() != null){
						openReferencedPageflow(project, (UserTask)eobj, window);						
					} 
					// show the propertysheet view
					window.getActivePage().showView("org.eclipse.ui.views.PropertySheet"); 
				} catch (Exception e) {
					
				}
				return null;
			}
			
		};
		return oep;
	}
	
	/**
	 * @param ofsProject
	 * @param userTask
	 * @param window
	 */
	private void openReferencedPageflow(IOfsProject ofsProject, UserTask userTask, IWorkbenchWindow window) {
		String uri = userTask.getPageflow().getURI();
		if (uri == null || uri.trim().length()==0) {
			return;
		}
		// open the referenced pageflow
		OfsEditorUtil.openEditor(ofsProject, URI.createURI(uri));
	}

	/* (non-Javadoc)
	 * @see org.eclipse.gmf.runtime.diagram.ui.editparts.ShapeEditPart#refreshBounds()
	 */
	protected void refreshBounds(){	
		super.refreshBounds();
		
		int width = ((Integer) getStructuralFeatureValue(NotationPackage.eINSTANCE.getSize_Width())).intValue();
		int height = ((Integer) getStructuralFeatureValue(NotationPackage.eINSTANCE.getSize_Height())).intValue();
		Dimension size = new Dimension(width, height);
		int x = ((Integer) getStructuralFeatureValue(NotationPackage.eINSTANCE.getLocation_X())).intValue();
		int y = ((Integer) getStructuralFeatureValue(NotationPackage.eINSTANCE.getLocation_Y())).intValue();
		List<?> siblings = getParent().getChildren();
		int index = siblings.indexOf(this);
		index = index+1;
		boolean linked = false;
		if (x == (index * 10)){	
			for (Object object : siblings) {
				ShapeNodeEditPart ep = (ShapeNodeEditPart)object;
				NodeImpl node = (NodeImpl)ep.getModel();
				ProcessItem processItem = (ProcessItem)node.getElement();
				List<?> transitions = ((com.odcgroup.process.model.Process)processItem.eContainer().eContainer()).getTransitions();
				for (Object flow : transitions) {
					Flow transition = (Flow)flow;
					if (transition.getTarget().equals(((NodeImpl)this.getModel()).getElement())){
						x = ((Integer) ep.getStructuralFeatureValue(NotationPackage.eINSTANCE.getLocation_X())).intValue()+siblings.indexOf(ep) * 125+30;
						y = y/index+125;
						linked = true;
					} 
				}			
			}
			if (!linked){			
				x = x+ (index * 125);
				if (index != 0)
					y = y/index;
			}
		}
		if (y < 15)
			y = 15;
		Point loc = new Point(x, y);
		((GraphicalEditPart) getParent()).setLayoutConstraint(this,	getFigure(), new Rectangle(loc, size));	
		
		
		if (getParent().getParent() instanceof PoolShapeNodeEditPart){
			PoolShapeNodeEditPart pep = (PoolShapeNodeEditPart)getParent().getParent();
			pep.refresh();	
		}
	}		

	/**
	 * @return ITranslationModel
	 * @generated NOT
	 */
	private ITranslation translation = null;
	public ITranslationModel getTranslationModel() {
		ITranslationManager manager = TranslationCore.getTranslationManager(ofsProject.getProject());
		if (translation == null) {
			translation = manager.getTranslation(getTask());
		} 
		ITranslationModel model = null;
		if (translation != null) {
			model = new TranslationModel(manager.getPreferences(), translation);
		}
		return model;
	}

	/**
	 * @see org.eclipse.gef.editparts.AbstractEditPart#setModel(java.lang.Object)
	 * @generated NOT
	 */
	@Override
	public void setModel(Object model) {
		super.setModel(model);
		Task task = getTask();
		if (task == null) {
			return;
		}
		
		ofsProject = OfsResourceHelper.getOfsProject(task.eResource());		

	}
	
	/**
	 * Gets the model cast as a Widget.
	 * 
	 * @return Widget The model cast as a Widget
	 * @generated NOT
	 */
	public Task getTask() {
		Node node = (Node)getModel();
		return (Task)node.getElement();
	}

	/**
	 * @see org.eclipse.core.runtime.IAdaptable#getAdapter(java.lang.Class) override the default behavior defined in
	 *      AbstractEditPart which would expect the model to be a property sourced. instead the editpart can provide a
	 *      property source
	 * @generated NOT
	 */
	public Object getAdapter(Class key) {
		if (IProject.class == key) {
			if (ofsProject != null) {
				return ofsProject.getProject();
			} else {
				return null;
			}
		}
		return super.getAdapter(key);
	}

}
