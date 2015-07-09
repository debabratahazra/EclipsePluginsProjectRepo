package com.odcgroup.t24.enquiry.editor.policy;

import java.util.Iterator;
import java.util.List;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.LayoutManager;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.editpolicies.XYLayoutEditPolicy;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gef.requests.CreateRequest;

import com.odcgroup.t24.enquiry.editor.part.EnquiryDiagramEditPart;
import com.odcgroup.t24.enquiry.editor.part.FieldEditPart;
import com.odcgroup.t24.enquiry.editor.part.OutputFieldEditPart;
import com.odcgroup.t24.enquiry.editor.part.actions.AbstractMoveAction;
import com.odcgroup.t24.enquiry.editor.part.commands.ColumnFieldMoveCommand;
import com.odcgroup.t24.enquiry.editor.part.commands.CreateCustomSelectionCommand;
import com.odcgroup.t24.enquiry.editor.part.commands.CreateFixedSelectionCommand;
import com.odcgroup.t24.enquiry.editor.part.commands.DrillDownCreationCommand;
import com.odcgroup.t24.enquiry.editor.part.commands.FieldColumnPositionSortCommand;
import com.odcgroup.t24.enquiry.editor.part.commands.FieldCreationCommand;
import com.odcgroup.t24.enquiry.editor.part.commands.FieldSelectionCreationCommand;
import com.odcgroup.t24.enquiry.editor.part.commands.HeaderCreateCommand;
import com.odcgroup.t24.enquiry.editor.part.commands.ManageFieldsCommand;
import com.odcgroup.t24.enquiry.editor.part.commands.RoutineCreationCommand;
import com.odcgroup.t24.enquiry.editor.part.commands.RoutineMoveCommand;
import com.odcgroup.t24.enquiry.editor.part.commands.SelectionCreationCommand;
import com.odcgroup.t24.enquiry.editor.part.commands.SelectionMoveCommand;
import com.odcgroup.t24.enquiry.editor.part.commands.TargetCreationCommand;
import com.odcgroup.t24.enquiry.editor.part.commands.TargetMappingMoveCommand;
import com.odcgroup.t24.enquiry.editor.part.commands.ToolCreationCommand;
import com.odcgroup.t24.enquiry.editor.part.commands.ToolMoveCommand;
import com.odcgroup.t24.enquiry.editor.part.commands.WebServiceCreationCommand;
import com.odcgroup.t24.enquiry.editor.request.CreateCustomSelectionRequest;
import com.odcgroup.t24.enquiry.editor.request.CreateFixedSelectionRequest;
import com.odcgroup.t24.enquiry.editor.request.ManageFieldsRequest;
import com.odcgroup.t24.enquiry.editor.request.MultiCreateRequest;
import com.odcgroup.t24.enquiry.enquiry.DrillDown;
import com.odcgroup.t24.enquiry.enquiry.Enquiry;
import com.odcgroup.t24.enquiry.enquiry.EnquiryHeader;
import com.odcgroup.t24.enquiry.enquiry.Field;
import com.odcgroup.t24.enquiry.enquiry.FieldPosition;
import com.odcgroup.t24.enquiry.enquiry.FixedSelection;
import com.odcgroup.t24.enquiry.enquiry.Routine;
import com.odcgroup.t24.enquiry.enquiry.Selection;
import com.odcgroup.t24.enquiry.enquiry.SelectionCriteria;
import com.odcgroup.t24.enquiry.enquiry.SelectionExpression;
import com.odcgroup.t24.enquiry.enquiry.Target;
import com.odcgroup.t24.enquiry.enquiry.TargetMapping;
import com.odcgroup.t24.enquiry.enquiry.Tool;
import com.odcgroup.t24.enquiry.enquiry.WebService;
import com.odcgroup.t24.enquiry.figure.CustomLayout;
import com.odcgroup.t24.enquiry.figure.EnquiryFigure;
import com.odcgroup.t24.enquiry.properties.util.EnquiryUiUtil;
import com.odcgroup.t24.enquiry.util.EnquiryUtil;

/**
 *
 * @author phanikumark
 *
 */
public class EnquiryDiagramXYLayoutPolicy extends XYLayoutEditPolicy {

	@Override
	protected Command getCreateCommand(CreateRequest request) {
		Object child = request.getNewObject();
		if (child instanceof FixedSelection) {
			return new FieldSelectionCreationCommand((FixedSelection) child, (Enquiry) getHost().getModel());
		} else if (child instanceof Selection) {
			if(getHost().getModel() instanceof Enquiry){
				Enquiry enquiry = (Enquiry) getHost().getModel();
				return new SelectionCreationCommand(enquiry, (Selection) child);
			} else {
				SelectionExpression sep = (SelectionExpression) getHost().getModel();
				return new SelectionCreationCommand((Enquiry) sep.eContainer(), (Selection) child);			
			}
		} else if(child instanceof Field){
			return new FieldCreationCommand((Field)child, (Enquiry) getHost().getModel());
		} else if(child instanceof Tool){
			return new ToolCreationCommand((Tool)child,(Enquiry)getHost().getModel());
		} else if(child instanceof Routine){
			return new RoutineCreationCommand((Routine)child,(Enquiry)getHost().getModel());
		} else if(child instanceof TargetMapping){
			if(getHost().getModel() instanceof Enquiry){
				Enquiry model = (Enquiry) getHost().getModel();
				return new TargetCreationCommand(model,model.getTarget(), (TargetMapping) child);
			}
			else{
				return new TargetCreationCommand((Target) getHost().getModel(), (TargetMapping) child);
			}
		} else if(child instanceof WebService){
			return new WebServiceCreationCommand((WebService)child,(Enquiry)getHost().getModel());
		} else if(child instanceof EnquiryHeader){
			return new HeaderCreateCommand((EnquiryHeader)child, (Enquiry)getHost().getModel());
		} else if(child instanceof DrillDown){
			return new DrillDownCreationCommand((DrillDown)child, (Enquiry)getHost().getModel());
		} else if (child instanceof SelectionCriteria) {
			return new SelectionCreationCommand((DrillDown)getHost().getModel(), (SelectionCriteria) child);
		}
		return null;
	}
	
	@Override
	protected Command getMoveChildrenCommand(Request request) {
		
		Object parent = getHost().getModel();
		if (parent instanceof SelectionExpression && !( request instanceof ChangeBoundsRequest)) {
			boolean isIcrement = (Boolean) request.getExtendedData().get(AbstractMoveAction.INCREMENT_FLAG);
			Selection child = (Selection) request.getExtendedData().get(AbstractMoveAction.SELECTED_CHILD);
			return new SelectionMoveCommand(child, (SelectionExpression) parent, isIcrement);
		} else if (parent instanceof Enquiry) {
			Object child = request.getExtendedData().get(AbstractMoveAction.SELECTED_CHILD);
			if (child == null && request instanceof ChangeBoundsRequest ) {
				ChangeBoundsRequest changeBoundRequest = (ChangeBoundsRequest) request;
				EditPart childPart = (EditPart) changeBoundRequest.getEditParts().get(0);
				if (childPart instanceof OutputFieldEditPart) {
					child = childPart.getModel();
					Point location = EnquiryUiUtil.getOffsetLocation((EnquiryDiagramEditPart) getHost(),
							changeBoundRequest.getLocation());
					int newIndex = calculateIndex(location, childPart);
					if( newIndex > 0 && newIndex <= ((Enquiry)parent).getFields().size()){
						return new ColumnFieldMoveCommand(newIndex - 1, (Field) child, (EnquiryDiagramEditPart) getHost());
					}					
				} else if (childPart instanceof FieldEditPart) {
					child = childPart.getModel();
					Point location = EnquiryUiUtil.getOffsetLocation((EnquiryDiagramEditPart) getHost(),
							changeBoundRequest.getLocation());
					int newIndex = calculateIndex(location, childPart);
					Enquiry enquiry = (Enquiry) parent;
					boolean allowrelative = allowRelative((Field)child, enquiry, newIndex);
					if( newIndex >= 0 && newIndex <= EnquiryUtil.fetchDisplayFieldList(enquiry).size()){
						if (allowrelative) {
							return new FieldColumnPositionSortCommand(newIndex, (Field) child, (EnquiryDiagramEditPart) getHost());
						}
					}
				}
				return null;
			} else {
				if (child instanceof Field) {
					boolean isIcrement = (Boolean) request.getExtendedData().get(AbstractMoveAction.INCREMENT_FLAG);
					EditPart ep = (EditPart) request.getExtendedData().get(AbstractMoveAction.EDIT_PART);
					if (ep instanceof OutputFieldEditPart) {
						return new ColumnFieldMoveCommand(isIcrement, (Field) child, (EnquiryDiagramEditPart) getHost());
					} else if (ep instanceof FieldEditPart) {
						return new FieldColumnPositionSortCommand(isIcrement, (Field) child, (EnquiryDiagramEditPart) getHost());
					}
				}
				if (child instanceof Tool) {
					return new ToolMoveCommand((Boolean) request.getExtendedData().get(
							AbstractMoveAction.INCREMENT_FLAG), (Tool) child, (Enquiry) parent);
				}
				if (child instanceof Routine) {
					return new RoutineMoveCommand((Boolean) request.getExtendedData().get(
							AbstractMoveAction.INCREMENT_FLAG), (Routine) child, (Enquiry) parent);
				}

			}

		} else if (parent instanceof Target && !( request instanceof ChangeBoundsRequest)) {
			boolean isIcrement = (Boolean) request.getExtendedData().get(AbstractMoveAction.INCREMENT_FLAG);
			TargetMapping child = (TargetMapping) request.getExtendedData().get(AbstractMoveAction.SELECTED_CHILD);
			return new TargetMappingMoveCommand(child, (Target) parent, isIcrement);
		}
		
		return null;
	}
	
	private boolean allowRelative(Field field, Enquiry enquiry, int newIndex) {
		boolean relative = false;
		boolean allowrelative = false;
		if (EnquiryUtil.isFieldRelative(field)) {
			relative = true;
			int col = field.getPosition().getColumn();
			List<Field> list = EnquiryUtil.fetchDisplayFieldList(enquiry);
			Field fld = list.get(newIndex);
			FieldPosition pos = fld.getPosition();
			if (!field.equals(fld) && pos != null 
					&& pos.getColumn() == col 
					&& pos.getLine() != null) {
				allowrelative = true;
			}
		}
		return (relative == allowrelative);
	}

	/**
	 * Gets the Container (host) figure.
	 * @param childPart 
	 * 
	 * @return IFigure The Container (host) figure
	 */
	private IFigure getFieldContainerFigure(EditPart childPart) {
		EnquiryFigure figure = (EnquiryFigure) ((EnquiryDiagramEditPart) getHost()).getFigure();
		if (childPart instanceof OutputFieldEditPart) {
			return figure.getDataOutputFieldsContainer();
		} else {
			return figure.getColumnsContainer();
		}
	}	

	/**
	 * This method calculates the index of the IFigure in the parent IFigure collection
	 * which was clicked. If the user clicked between Widgets then the index
	 * after the previous IFigure is returned.
	 * 
	 * @param location The location
	 * @param childPart 
	 * @return index The index
	 */
	private int calculateIndex(Point location, EditPart childPart) {
		IFigure container = getFieldContainerFigure(childPart);		
		LayoutManager lm = container.getLayoutManager();
		if (lm instanceof CustomLayout) {
			CustomLayout cl = (CustomLayout) lm;
			return cl.calculateIndex(container, location);
		}
		
		 return 0;
	}

	
	@Override
	public Command getCommand(Request request) {
		if(request.getType().equals(MultiCreateRequest.MULTIPLE_CREATE)){
			return getCreateCommand((MultiCreateRequest)request);
		} else if (request.getType().equals(ManageFieldsRequest.MANAGE_FIELDS)) {
			return getManageFieldsCommand((ManageFieldsRequest) request);
		} else if (request.getType().equals(CreateFixedSelectionRequest.CREATE_FIXEDSELECTION)) {
			return getCreateFixedSelectionCommand((CreateFixedSelectionRequest)request);
		} else if (request.getType().equals(CreateCustomSelectionRequest.CREATE_CUSTOMSELECTION)) {
			return getCreateCustomSelectionCommand((CreateCustomSelectionRequest)request);
		}
		return super.getCommand(request);
	}
	
	private Command getManageFieldsCommand(ManageFieldsRequest request) {
		return new ManageFieldsCommand(request.getEnquiry());
	}
	
	private Command getCreateFixedSelectionCommand(CreateFixedSelectionRequest request) {
		return new CreateFixedSelectionCommand(request.getEnquiry());
	}
	
	private Command getCreateCustomSelectionCommand(CreateCustomSelectionRequest request) {
		return new CreateCustomSelectionCommand(request.getEnquiry());
	}

	/**
	 * @param request
	 */
	private Command getCreateCommand(MultiCreateRequest request) {

		CompoundCommand cc = new CompoundCommand();
		List children = request.getNewObjects();

		for (Iterator it = children.iterator(); it.hasNext();) {
			final Object child = it.next();
			CreateRequest createRequest = new CreateRequest() {
				public Object getNewObject() {
					return child;
				};
			};
			Command createcommand = getCreateCommand(createRequest);
			cc.add(createcommand);

		}
		return cc;
	}

}
