/*
 * Odyssey Financial Technologies
 */
package com.odcgroup.process.diagram.edit.parts;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.StackLayout;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.LayoutEditPolicy;
import org.eclipse.gef.editpolicies.NonResizableEditPolicy;
import org.eclipse.gef.editpolicies.ResizableEditPolicy;
import org.eclipse.gef.requests.CreateRequest;
import org.eclipse.gmf.runtime.diagram.ui.commands.ICommandProxy;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.CreationEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles;
import org.eclipse.gmf.runtime.draw2d.ui.figures.ConstrainedToolbarLayout;
import org.eclipse.gmf.runtime.draw2d.ui.figures.WrapLabel;
import org.eclipse.gmf.runtime.emf.type.core.commands.SetValueCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.SetRequest;
import org.eclipse.gmf.runtime.gef.ui.figures.DefaultSizeNodeFigure;
import org.eclipse.gmf.runtime.gef.ui.figures.NodeFigure;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.widgets.Display;

import com.odcgroup.process.diagram.custom.dnd.CreatePageflowRequest;
import com.odcgroup.process.diagram.custom.parts.ProcessItemShapeNodeEditPart;
import com.odcgroup.process.diagram.edit.policies.UserTaskItemSemanticEditPolicy;
import com.odcgroup.process.diagram.part.Messages;
import com.odcgroup.process.diagram.part.ProcessVisualIDRegistry;
import com.odcgroup.process.model.Pageflow;
import com.odcgroup.process.model.ProcessFactory;
import com.odcgroup.process.model.ProcessPackage;

/**
 * @generated NOT
 */
public class UserTaskEditPart extends ProcessItemShapeNodeEditPart {

	/**
	 * @generated
	 */
	public static final int VISUAL_ID = 2001;

	/**
	 * @generated
	 */
	protected IFigure contentPane;

	/**
	 * @generated
	 */
	protected IFigure primaryShape;

	/**
	 * @generated
	 */
	public UserTaskEditPart(View view) {
		super(view);
	}

	/**
	 * @generated
	 */
	protected void createDefaultEditPolicies() {

		super.createDefaultEditPolicies();
		installEditPolicy(EditPolicyRoles.SEMANTIC_ROLE, new UserTaskItemSemanticEditPolicy());
		installEditPolicy(EditPolicyRoles.CREATION_ROLE, createContainerEditPolicy());
		installEditPolicy(EditPolicy.LAYOUT_ROLE, createLayoutEditPolicy());
		// XXX need an SCR to runtime to have another abstract superclass that would let children add reasonable editpolicies
		// removeEditPolicy(org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles.CONNECTION_HANDLES_ROLE);
	}

	/**
	 * @return
	 */
	protected CreationEditPolicy createContainerEditPolicy() {
		CreationEditPolicy cep = new CreationEditPolicy() {

			@Override
			public Command getCommand(Request request) {
				if (request instanceof CreatePageflowRequest) {
					CreatePageflowRequest req = (CreatePageflowRequest) request;
					final EObject userTask = ((Node) getModel()).getElement();
					EReference ref = ProcessPackage.eINSTANCE.getUserTask_Pageflow();
					Pageflow pageflow = (Pageflow) userTask.eGet(ref);
					if (pageflow == null) {
						pageflow = ProcessFactory.eINSTANCE.createPageflow();
						pageflow.setURI(req.getPageflowURI());
						SetRequest sReq = new SetRequest(getEditingDomain(), userTask, ref, pageflow);
						return new ICommandProxy(new SetValueCommand(sReq));
					} else {
						String message = NLS.bind(Messages.UserTaskEditPartContainerPolicyConfirmMsg, req
								.getPageflowURI());
						boolean okpressed = MessageDialog.openConfirm(Display.getCurrent().getActiveShell(),
								Messages.UserTaskPageflowDefinitionConfirmDialogTitle, message);
						if (okpressed) {
							pageflow = ProcessFactory.eINSTANCE.createPageflow();
							pageflow.setURI(req.getPageflowURI());
							SetRequest sReq = new SetRequest(getEditingDomain(), userTask, ref, pageflow);
							return new ICommandProxy(new SetValueCommand(sReq));
						}
					}
				}

				return super.getCommand(request);
			}

		};
		return cep;
	}

	/**
	 * @generated
	 */
	protected LayoutEditPolicy createLayoutEditPolicy() {
		LayoutEditPolicy lep = new LayoutEditPolicy() {

			protected EditPolicy createChildEditPolicy(EditPart child) {
				EditPolicy result = child.getEditPolicy(EditPolicy.PRIMARY_DRAG_ROLE);
				if (result == null) {
					result = new NonResizableEditPolicy();
				}
				return result;
			}

			protected Command getMoveChildrenCommand(Request request) {
				return null;
			}

			protected Command getCreateCommand(CreateRequest request) {
				return null;
			}
		};
		return lep;
	}

	/**
	 * @generated
	 */
	protected IFigure createNodeShape() {
		TaskFigure figure = new TaskFigure();
		return primaryShape = figure;
	}

	/**
	 * @generated
	 */
	public TaskFigure getPrimaryShape() {
		return (TaskFigure) primaryShape;
	}

	/**
	 * @generated
	 */
	protected boolean addFixedChild(EditPart childEditPart) {
		if (childEditPart instanceof UserTaskNameEditPart) {
			((UserTaskNameEditPart) childEditPart).setLabel(getPrimaryShape().getFigureTaskNameFigure());
			return true;
		}
		return false;
	}

	/**
	 * @generated
	 */
	protected boolean removeFixedChild(EditPart childEditPart) {

		return false;
	}

	/**
	 * @generated
	 */
	protected void addChildVisual(EditPart childEditPart, int index) {
		if (addFixedChild(childEditPart)) {
			return;
		}
		super.addChildVisual(childEditPart, -1);
	}

	/**
	 * @generated
	 */
	protected void removeChildVisual(EditPart childEditPart) {
		if (removeFixedChild(childEditPart)) {
			return;
		}
		super.removeChildVisual(childEditPart);
	}

	/**
	 * @generated
	 */
	protected IFigure getContentPaneFor(IGraphicalEditPart editPart) {

		return super.getContentPaneFor(editPart);
	}

	/**
	 * @generated
	 */
	protected NodeFigure createNodePlate() {
		DefaultSizeNodeFigure result = new DefaultSizeNodeFigure(getMapMode().DPtoLP(40), getMapMode().DPtoLP(40));
		return result;
	}

	/**
	 * OCS-24509 (resizing following text length not possible)
	 * @generated NOT
	 */
	public EditPolicy getPrimaryDragEditPolicy() {
		EditPolicy result = super.getPrimaryDragEditPolicy();
		if (result instanceof ResizableEditPolicy) {
			ResizableEditPolicy ep = (ResizableEditPolicy) result;
			ep.setResizeDirections(PositionConstants.NSEW);
		}
		return result;
	}

	/**
	 * Creates figure for this edit part.
	 * 
	 * Body of this method does not depend on settings in generation model
	 * so you may safely remove <i>generated</i> tag and modify it.
	 * 
	 * @generated
	 */
	protected NodeFigure createNodeFigure() {
		NodeFigure figure = createNodePlate();
		figure.setLayoutManager(new StackLayout());
		IFigure shape = createNodeShape();
		figure.add(shape);
		contentPane = setupContentPane(shape);
		return figure;
	}

	/**
	 * Default implementation treats passed figure as content pane.
	 * Respects layout one may have set for generated figure.
	 * @param nodeShape instance of generated figure class
	 * @generated
	 */
	protected IFigure setupContentPane(IFigure nodeShape) {
		if (nodeShape.getLayoutManager() == null) {
			ConstrainedToolbarLayout layout = new ConstrainedToolbarLayout();
			layout.setSpacing(getMapMode().DPtoLP(5));
			nodeShape.setLayoutManager(layout);
		}
		return nodeShape; // use nodeShape itself as contentPane
	}

	/**
	 * @generated
	 */
	public IFigure getContentPane() {
		if (contentPane != null) {
			return contentPane;
		}
		return super.getContentPane();
	}

	/**
	 * @generated
	 */
	public EditPart getPrimaryChildEditPart() {
		return getChildBySemanticHint(ProcessVisualIDRegistry.getType(UserTaskNameEditPart.VISUAL_ID));
	}

	/**
	 * @generated
	 */
	public class TaskFigure extends com.odcgroup.process.diagram.custom.figures.TaskFigure {

		/**
		 * @generated
		 */
		private WrapLabel fFigureTaskNameFigure;

		/**
		 * @generated
		 */
		public TaskFigure() {
			super("icons/UserTask.gif");
			createContents();
		}

		/**
		 * @generated
		 */
		private void createContents() {

			fFigureTaskNameFigure = new WrapLabel();
			fFigureTaskNameFigure.setText("");
			fFigureTaskNameFigure.setAlignment(PositionConstants.CENTER);
			this.add(fFigureTaskNameFigure);

		}

		/**
		 * @generated
		 */
		private boolean myUseLocalCoordinates = false;

		/**
		 * @generated
		 */
		protected boolean useLocalCoordinates() {
			return myUseLocalCoordinates;
		}

		/**
		 * @generated
		 */
		protected void setUseLocalCoordinates(boolean useLocalCoordinates) {
			myUseLocalCoordinates = useLocalCoordinates;
		}

		/**
		 * @generated
		 */
		public WrapLabel getFigureTaskNameFigure() {
			return fFigureTaskNameFigure;
		}

	}

}
