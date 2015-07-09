/*
 * Odyssey Financial Technologies
 */
package com.odcgroup.process.diagram.edit.parts;

import org.eclipse.core.resources.IProject;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.editparts.DiagramEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.View;

import com.odcgroup.process.diagram.custom.policies.ProcessDiagramXYLayoutEditPolicy;
import com.odcgroup.process.diagram.edit.policies.ProcessCanonicalEditPolicy;
import com.odcgroup.process.diagram.edit.policies.ProcessItemSemanticEditPolicy;
import com.odcgroup.process.model.Process;
import com.odcgroup.translation.core.ITranslation;
import com.odcgroup.translation.core.ITranslationManager;
import com.odcgroup.translation.core.TranslationCore;
import com.odcgroup.translation.ui.views.ITranslationModel;
import com.odcgroup.translation.ui.views.model.TranslationModel;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.core.repository.OfsResourceHelper;

/**
 * @generated
 */
public class ProcessEditPart extends DiagramEditPart {

	/**
	 * @generated
	 */
	public final static String MODEL_ID = "Process"; //$NON-NLS-1$

	/**
	 * @generated
	 */
	public static final int VISUAL_ID = 79;
	
	/**
	 * @generated NOT
	 */
	private IOfsProject ofsProject;


	/**
	 * @generated
	 */
	public ProcessEditPart(View view) {
		super(view);
	}

	/**
	 * added the custom XYLayoutEditPolicy
	 * @generated NOT
	 */
	protected void createDefaultEditPolicies() {
		super.createDefaultEditPolicies();
		installEditPolicy(EditPolicyRoles.SEMANTIC_ROLE, new ProcessItemSemanticEditPolicy());
		installEditPolicy(EditPolicyRoles.CANONICAL_ROLE, new ProcessCanonicalEditPolicy());
		installEditPolicy(EditPolicy.LAYOUT_ROLE, new ProcessDiagramXYLayoutEditPolicy());
		// removeEditPolicy(org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles.POPUPBAR_ROLE);
	}

	@Override
	protected void addChild(EditPart child, int index) {
		// check for null childs
		if (child != null) {
			super.addChild(child, index);
		}
	}
	
	/** @generated NOT */
	private ITranslation translation = null;

	/**
	 * @return ITranslationModel
	 * @generated NOT
	 */
	public ITranslationModel getTranslationModel() {
		ITranslationManager manager = TranslationCore.getTranslationManager(ofsProject.getProject());
		if (translation == null) {
			translation = manager.getTranslation(getProcess());
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
		Process process = getProcess();
		if (process == null) {
			return;
		}
		
		ofsProject = OfsResourceHelper.getOfsProject(process.eResource());

	}
	
	/**
	 * Gets the model cast as a Process.
	 * 
	 * @return Process The model cast as a Process
	 * @generated NOT
	 */
	public Process getProcess() {
		if (getModel() instanceof Node) {
			Node node = (Node)getModel();
			return (Process)node.getElement();
		} else if (getModel() instanceof Diagram) {
			Diagram diagram = (Diagram)getModel();
			return (Process)diagram.getElement();
		} else {
			throw new IllegalStateException("The model should be a diagram or a node");
		}
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
