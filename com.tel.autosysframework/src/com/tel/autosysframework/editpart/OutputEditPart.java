package com.tel.autosysframework.editpart;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.eclipse.swt.accessibility.AccessibleEvent;

import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.IFigure;

import org.eclipse.gef.AccessibleAnchorProvider;
import org.eclipse.gef.AccessibleEditPart;

import com.tel.autosysframework.figures.OutputFigure;
import com.tel.autosysframework.model.SimpleOutput;

/**
 * EditPart for Output types in Autosys Example
 */
public class OutputEditPart
	extends AutosysEditPart
{

protected AccessibleEditPart createAccessible() {
	return new AccessibleGraphicalEditPart(){
		public void getName(AccessibleEvent e) {
			e.result = getSimpleOutput().toString();
		}
	};
}

/**
 * Returns a newly created Figure.
 */
protected IFigure createFigure() {
	OutputFigure figure;
	if (getModel() == null)
		return null;
	else
		figure = new OutputFigure();
	return figure;
}

public Object getAdapter(Class key) {
	if (key == AccessibleAnchorProvider.class)
		return new DefaultAccessibleAnchorProvider() { 
			public List getSourceAnchorLocations() {
				List list = new ArrayList();
				Vector sourceAnchors = getNodeFigure().getSourceConnectionAnchors();
				for (int i=0; i<sourceAnchors.size(); i++) {
					ConnectionAnchor anchor = (ConnectionAnchor)sourceAnchors.get(i);
					list.add(anchor.getReferencePoint().getTranslated(0, -3));
				}
				return list;
			}
			public List getTargetAnchorLocations() {
				List list = new ArrayList();
				Vector targetAnchors = getNodeFigure().getTargetConnectionAnchors();
				for (int i=0; i<targetAnchors.size(); i++) {
					ConnectionAnchor anchor = (ConnectionAnchor)targetAnchors.get(i);
					list.add(anchor.getReferencePoint().getTranslated(0, -3));
				}
				return list;
			}

		};
	return super.getAdapter(key);
}

/**
 * Returns the Figure for this as an OutputFigure.
 * @return  Figure of this as a OutputFigure.
 */
protected OutputFigure getOutputFigure() {
	return (OutputFigure)getFigure();
}

/**
 * Returns the model of this as a SimpleOutput.
 * @return  Model of this as a SimpleOutput.
 */
protected SimpleOutput getSimpleOutput() {
	return (SimpleOutput)getModel();
}

}
