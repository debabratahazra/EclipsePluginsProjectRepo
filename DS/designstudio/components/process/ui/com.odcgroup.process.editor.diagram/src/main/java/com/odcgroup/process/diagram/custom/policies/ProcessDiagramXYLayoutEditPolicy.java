package com.odcgroup.process.diagram.custom.policies;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gef.requests.CreateRequest;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.XYLayoutEditPolicy;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;

import com.odcgroup.process.diagram.edit.parts.PoolEditPart;
import com.odcgroup.process.diagram.providers.ProcessElementTypes;

public class ProcessDiagramXYLayoutEditPolicy extends XYLayoutEditPolicy {

	public static final int DEFAULT_POOL_X_COORD = 16;

	/**
	 * 
	 */
	public ProcessDiagramXYLayoutEditPolicy() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editpolicies.ConstrainedLayoutEditPolicy#getResizeChildrenCommand(org.eclipse.gef.requests.ChangeBoundsRequest)
	 */
	protected Command getResizeChildrenCommand(ChangeBoundsRequest request) {
		int maxWidth = 250;
		Map<Rectangle, IGraphicalEditPart> toSortBounds = new HashMap<Rectangle, IGraphicalEditPart>();
		IGraphicalEditPart childPart;
		Rectangle rect;
		for (Iterator<?> iterator = getHost().getChildren().iterator(); iterator
				.hasNext(); toSortBounds.put(rect, childPart)) {
			Object child = iterator.next();
			childPart = (IGraphicalEditPart) child;
			rect = childPart.getFigure().getBounds().getCopy();
			if (request.getEditParts().contains(child)) {
				if (request.getSizeDelta() != null) {
					rect.width += request.getSizeDelta().width;
					rect.height += request.getSizeDelta().height;
				}
				if (request.getMoveDelta() != null) {
					rect.y += request.getMoveDelta().y;
					rect.x += request.getMoveDelta().x;
				}
			}
			maxWidth = maxWidth >= rect.width ? maxWidth : rect.width;
		}

		List<Rectangle> keys = new LinkedList<Rectangle>(
				(Collection<Rectangle>) toSortBounds.keySet());
		Collections.sort(keys, new Comparator<Object>() {

			public int compare(Rectangle o1, Rectangle o2) {
				return o1.y > o2.y ? 1 : -1;
			}

			public int compare(Object arg0, Object arg1) {
				return compare((Rectangle) arg0, (Rectangle) arg1);
			}

		});
		int y = 0;
		CompoundCommand command = new CompoundCommand("Resizing pools");
		for (Iterator<Rectangle> iterator1 = keys.iterator(); iterator1
				.hasNext();) {
			Rectangle key = iterator1.next();
			IGraphicalEditPart part = toSortBounds.get(key);
			y = key.y >= y + 16 ? key.y : y + 16;
			key.y = y;
			if (part instanceof PoolEditPart) {
				key.x = 16;
				key.width = maxWidth;
			}
			command.add(createChangeConstraintCommand(part, key));
			y += key.height;
		}

		return command;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gmf.runtime.diagram.ui.editpolicies.XYLayoutEditPolicy#getConstraintFor(org.eclipse.gef.requests.CreateRequest)
	 */
	protected Object getConstraintFor(CreateRequest request) {
		Object constraint = super.getConstraintFor(request);
		if (request == null
				|| request.getNewObject() == null
				|| ((List) request.getNewObject()).isEmpty()
				|| ((List) request.getNewObject()).get(0) == null
				|| ((org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequest.ViewDescriptor) ((List) request
						.getNewObject()).get(0)).getElementAdapter() == null
				|| ((List) request.getNewObject()).get(0) == null)
			return constraint;
		IElementType type = (IElementType) ((org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequest.ViewDescriptor) ((List) request
				.getNewObject()).get(0)).getElementAdapter().getAdapter(
				IElementType.class);
		int maxWidth = 200;
		if (((com.odcgroup.process.model.Process) ((IGraphicalEditPart) getHost())
				.resolveSemanticElement()).getPools().isEmpty())
			maxWidth = 900;
		for (Iterator iterator = getHost().getChildren().iterator(); iterator
				.hasNext();) {
			Object child = iterator.next();
			IGraphicalEditPart childPart = (IGraphicalEditPart) child;
			Rectangle rect = childPart.getFigure().getBounds().getCopy();
			maxWidth = maxWidth >= rect.width ? maxWidth : rect.width;
		}

		if (type == ProcessElementTypes.Pool_1001) {
			((Rectangle) constraint).x = 16;
			((Rectangle) constraint).width = maxWidth;
			((Rectangle) constraint).height = 250;
			return constraint;
		} else {
			return constraint;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gmf.runtime.diagram.ui.editpolicies.XYLayoutEditPolicy#getCreateCommand(org.eclipse.gef.requests.CreateRequest)
	 */
	protected Command getCreateCommand(CreateRequest request) {
		CompoundCommand co = new CompoundCommand();
		co.add(super.getCreateCommand(request));
		ChangeBoundsRequest req = new ChangeBoundsRequest();
		Rectangle addedThing = (Rectangle) getConstraintFor(request);
		List children = ((IGraphicalEditPart) getHost()).getChildren();
		List<IGraphicalEditPart> parts = new LinkedList<IGraphicalEditPart>();
		Point location = request.getLocation().getCopy();
		getHostFigure().translateToRelative(location);
		for (Iterator iterator = children.iterator(); iterator.hasNext();) {
			Object child = iterator.next();
			Rectangle rect = ((IGraphicalEditPart) child).getFigure()
					.getBounds();
			if (rect.y > location.y)
				parts.add((IGraphicalEditPart) child);
		}

		if (!parts.isEmpty()) {
			req.setEditParts(parts);
			req.setConstrainedMove(true);
			req.setMoveDelta(new Point(addedThing.x,
					((Rectangle) getConstraintFor(request)).height + 16));
			co.add(getResizeChildrenCommand(req));
		}
		return co;
	}
}
