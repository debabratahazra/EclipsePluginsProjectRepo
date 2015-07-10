package com.tel.autosysframework.actions;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.ui.IEditorPart;

/**
 * @author Eric Bordeau
 */
public class AutosysPasteTemplateAction extends PasteTemplateAction {

/**
 * Constructor for AutosysPasteTemplateAction.
 * @param editor
 */
public AutosysPasteTemplateAction(IEditorPart editor) {
	super(editor);
}

/**
 * @see PasteTemplateAction#getPasteLocation(GraphicalEditPart)
 */
protected Point getPasteLocation(GraphicalEditPart container) {
	Point result = new Point(10, 10);
	IFigure fig = container.getContentPane();
	result.translate(fig.getClientArea(Rectangle.SINGLETON).getLocation());
	fig.translateToAbsolute(result);
	return result;
}

}
