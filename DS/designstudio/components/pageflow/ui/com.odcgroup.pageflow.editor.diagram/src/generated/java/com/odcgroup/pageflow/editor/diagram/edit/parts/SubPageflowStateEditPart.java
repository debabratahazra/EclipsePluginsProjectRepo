/*
 * Odyssey Financial Technologies
 */
package com.odcgroup.pageflow.editor.diagram.edit.parts;

import java.util.ArrayList;

import org.eclipse.draw2d.GridData;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.ImageFigure;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.RoundedRectangle;
import org.eclipse.draw2d.StackLayout;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.editpolicies.ContainerEditPolicy;
import org.eclipse.gef.editpolicies.LayoutEditPolicy;
import org.eclipse.gef.editpolicies.NonResizableEditPolicy;
import org.eclipse.gef.requests.CreateRequest;
import org.eclipse.gmf.runtime.diagram.ui.commands.ICommandProxy;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
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
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.widgets.Display;

import com.odcgroup.pageflow.editor.diagram.custom.figures.DescCompartmentFigure;
import com.odcgroup.pageflow.editor.diagram.custom.pageintegration.CreateSubPageflowRequest;
import com.odcgroup.pageflow.editor.diagram.custom.parts.PageflowShapeEditPart;
import com.odcgroup.pageflow.editor.diagram.custom.util.StateSize;
import com.odcgroup.pageflow.editor.diagram.edit.policies.SubPageflowStateItemSemanticEditPolicy;
import com.odcgroup.pageflow.editor.diagram.part.Messages;
import com.odcgroup.pageflow.editor.diagram.part.PageflowDiagramEditorPlugin;
import com.odcgroup.pageflow.editor.diagram.part.PageflowVisualIDRegistry;
import com.odcgroup.pageflow.model.Pageflow;
import com.odcgroup.pageflow.model.PageflowPackage;

/**
 * @generated NOT
 */
public class SubPageflowStateEditPart extends PageflowShapeEditPart {

	/**
	 * @generated
	 */
	public static final int VISUAL_ID = 1005;

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
	public SubPageflowStateEditPart(View view) {
		super(view);
	}

	/**
	 * @generated NOT
	 */
	protected void createDefaultEditPolicies() {

		super.createDefaultEditPolicies();
		installEditPolicy(EditPolicyRoles.SEMANTIC_ROLE, new SubPageflowStateItemSemanticEditPolicy());
		installEditPolicy(EditPolicy.LAYOUT_ROLE, createLayoutEditPolicy());
		installEditPolicy(EditPolicy.CONTAINER_ROLE, createContainerEditPolicy());
		// XXX need an SCR to runtime to have another abstract superclass that would let children add reasonable editpolicies
		// removeEditPolicy(org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles.CONNECTION_HANDLES_ROLE);
	}

	/**
	 * @return
	 */
	protected ContainerEditPolicy createContainerEditPolicy() {
		ContainerEditPolicy cep = new ContainerEditPolicy() {

			@Override
			protected Command getCreateCommand(CreateRequest request) {
				if (request instanceof CreateSubPageflowRequest) {
					CreateSubPageflowRequest req = (CreateSubPageflowRequest) request;
					final EObject subpageflow = ((Node) getModel()).getElement();
					EReference ref = PageflowPackage.eINSTANCE.getSubPageflowState_SubPageflow();
					EReference tm = PageflowPackage.eINSTANCE.getSubPageflowState_TransitionMappings();
					Pageflow view = (Pageflow) subpageflow.eGet(ref);
					if (view == null) {
						SetRequest sReq = new SetRequest(getEditingDomain(), subpageflow, ref, req.getSubpageflow());
						return new ICommandProxy(new SetValueCommand(sReq));
					} else {
						String refUri = req.getSubpageflow().eResource().getURI().toString();
						String message = NLS.bind(Messages.SubPageflowStateEditPartContainerPolicyConfirmMsg, refUri);
						boolean okpressed = MessageDialog.openConfirm(Display.getCurrent().getActiveShell(),
								Messages.ViewStateViewDefinitionConfirmDialogTitle, message);
						if (okpressed) {

							CompoundCommand cc = new CompoundCommand("reset subpageflow");
							SetRequest sReq = new SetRequest(getEditingDomain(), subpageflow, ref, req.getSubpageflow());
							// reset transitionmappings if any
							SetRequest tmReq = new SetRequest(getEditingDomain(), subpageflow, tm, new ArrayList());
							cc.add(new ICommandProxy(new SetValueCommand(sReq)));
							cc.add(new ICommandProxy(new SetValueCommand(tmReq)));
							return cc;
						}
					}
				}

				return null;
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
		SubPageflowStateFigure figure = new SubPageflowStateFigure();
		return primaryShape = figure;
	}

	/**
	 * @generated
	 */
	public SubPageflowStateFigure getPrimaryShape() {
		return (SubPageflowStateFigure) primaryShape;
	}

	/**
	 * @generated
	 */
	protected boolean addFixedChild(EditPart childEditPart) {
		if (childEditPart instanceof SubPageflowStateDisplayNameEditPart) {
			((SubPageflowStateDisplayNameEditPart) childEditPart).setLabel(getPrimaryShape()
					.getFigureSubPageflowStateDisplayNameFigure());
			return true;
		}
		if (childEditPart instanceof SubPageflowStateDescEditPart) {
			((SubPageflowStateDescEditPart) childEditPart).setLabel(getPrimaryShape()
					.getFigureSubPageflowStateDescriptionFigure());
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
		DefaultSizeNodeFigure result = new DefaultSizeNodeFigure(getMapMode().DPtoLP(100), getMapMode().DPtoLP(60));
		return result;
	}

	/**
	 * Creates figure for this edit part.
	 * 
	 * Body of this method does not depend on settings in generation model
	 * so you may safely remove <i>generated</i> tag and modify it.
	 * 
	 * @generated NOT
	 */
	protected NodeFigure createMainFigure() {
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
		return getChildBySemanticHint(PageflowVisualIDRegistry.getType(SubPageflowStateDisplayNameEditPart.VISUAL_ID));
	}

	/**
	 * @generated NOT
	 */
	public class SubPageflowStateFigure extends RoundedRectangle {

		/**
		 * @generated
		 */
		private WrapLabel fFigureSubPageflowStateDisplayNameFigure;
		/**
		 * @generated
		 */
		private WrapLabel fFigureSubPageflowStateDescriptionFigure;

		/**
		 * OCS-27175 size preferences included
		 * @generated NOT
		 */
		public SubPageflowStateFigure() {
			this.setCornerDimensions(new Dimension(getMapMode().DPtoLP(25), getMapMode().DPtoLP(25)));
			this.setPreferredSize(StateSize.getPreferredStateSize());
			createContents();
		}

		/**
		 * OFSFMK-271 - Use more distinctable widget for sub-pageflows (sub-task 531)
		 * 
		 * @generated NOT
		 */
		private void createContents() {

			fFigureSubPageflowStateDisplayNameFigure = new WrapLabel();
			fFigureSubPageflowStateDisplayNameFigure.setText("");
			fFigureSubPageflowStateDisplayNameFigure.setAlignment(PositionConstants.CENTER);
			this.add(fFigureSubPageflowStateDisplayNameFigure);

			DescCompartmentFigure compartment = new DescCompartmentFigure();
			this.add(compartment);

			fFigureSubPageflowStateDescriptionFigure = new WrapLabel();
			fFigureSubPageflowStateDescriptionFigure.setText(" ");
			fFigureSubPageflowStateDescriptionFigure.setAlignment(PositionConstants.CENTER);
			fFigureSubPageflowStateDescriptionFigure.setTextWrap(true);
			fFigureSubPageflowStateDescriptionFigure.setTextWrapWidth(this.getBounds().width);
			fFigureSubPageflowStateDescriptionFigure.setFont(FFIGURESUBPAGEFLOWSTATEDESCRIPTIONFIGURE_FONT);
			fFigureSubPageflowStateDescriptionFigure.setTextAlignment(WrapLabel.CENTER);
			compartment.add(fFigureSubPageflowStateDescriptionFigure);

			GridData gridData = new GridData(GridData.FILL_VERTICAL);
			gridData.grabExcessVerticalSpace = true;
			gridData.verticalAlignment = GridData.END;

			ImageFigure img = new ImageFigure();
			String subflowicon = "icons/subflow.GIF";
			img.setImage(PageflowDiagramEditorPlugin.getInstance().getBundledImage(subflowicon));
			img.setAlignment(PositionConstants.SOUTH);
			this.add(img, gridData);

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
		public WrapLabel getFigureSubPageflowStateDisplayNameFigure() {
			return fFigureSubPageflowStateDisplayNameFigure;
		}

		/**
		 * @generated
		 */
		public WrapLabel getFigureSubPageflowStateDescriptionFigure() {
			return fFigureSubPageflowStateDescriptionFigure;
		}

	}

	/**
	 * @generated
	 */
	static final Font FFIGURESUBPAGEFLOWSTATEDESCRIPTIONFIGURE_FONT = new Font(Display.getCurrent(), "Arial", 8,
			SWT.NORMAL);

}
