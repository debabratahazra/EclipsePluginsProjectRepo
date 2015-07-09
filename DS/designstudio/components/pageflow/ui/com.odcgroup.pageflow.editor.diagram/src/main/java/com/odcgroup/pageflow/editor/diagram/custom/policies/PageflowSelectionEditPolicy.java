package com.odcgroup.pageflow.editor.diagram.custom.policies;


import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.LineBorder;
import org.eclipse.gef.LayerConstants;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.ResizableShapeEditPolicy;

public class PageflowSelectionEditPolicy extends ResizableShapeEditPolicy {
	
	/* (non-Javadoc)
	 * @see org.eclipse.gmf.runtime.diagram.ui.editpolicies.ResizableEditPolicyEx#addSelectionHandles()
	 */
	protected void addSelectionHandles() {
		removeSelectionHandles();
		IFigure layer = getLayer(LayerConstants.HANDLE_LAYER);
		handles = createSelectionHandles();
		for (int i = 0; i < handles.size(); i++) {
			IFigure figure = (IFigure)handles.get(i);
			figure.setBorder(new LineBorder(2));
			layer.add(figure);
		}
	}

}
