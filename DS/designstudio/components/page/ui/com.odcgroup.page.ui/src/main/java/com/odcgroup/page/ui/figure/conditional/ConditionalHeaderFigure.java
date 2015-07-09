package com.odcgroup.page.ui.figure.conditional;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.FigureUtilities;
import org.eclipse.draw2d.IFigure;
import org.eclipse.swt.graphics.Font;

import com.odcgroup.page.model.Widget;
import com.odcgroup.page.ui.figure.AbstractHeaderFigure;
import com.odcgroup.page.ui.figure.IWidgetFigure;

/**
 * HeaderFigure for ConditionalWidget's.
 * 
 * @author atr
 */
public class ConditionalHeaderFigure extends AbstractHeaderFigure {
	
	/** The type of Widget that we should create Headers for. */
	private String bodyType;	
	
	@Override
	protected Font getCaptionFont() {
		return getFigureConstants().getCaptionFont(true);
	}
	
	@Override
	protected int getItemWidth(Widget item, int index, int nbItems, Font itemFont) {
		String text = getItemText(item, index);
		// +4 to add space between the text and the figure
		return FigureUtilities.getTextWidth(" "+text+" ", itemFont) + 14;
	}


	protected String getItemText(Widget widget, int index) {
		//return "?< "+super.getItemText(widget, index)+" >";
 		String text = "";
		IFigure parent = getParent();
		while (parent != null && !(parent instanceof IConditionalFigure)) {
			parent = parent.getParent();
		}
		if (parent != null) {
			IConditionFigure condition = ((IConditionalFigure)parent).getCondition(index);
			text = condition != null ? condition.getName() : "???";
		}
		return " ?< "+text+" > ";
	}	
	

	@Override
	protected List<Widget> getItems() {
		List<Widget> result = new ArrayList<Widget>();
		for (Widget w : getWidget().getContents()) {
			if (w.getTypeName().equals(bodyType)) {
				result.add(w);
			}
		}
		return result;
	}
	
	/**
	 * Creates a new ConditionalHeaderFigure.
	 * 
	 * @param parentFigure
	 *            the parent figure
	 * @param bodyType The type of Widget that we should create Headers for
	 */
	public ConditionalHeaderFigure(IWidgetFigure parentFigure, String bodyType) {
		super(parentFigure);
		this.bodyType = bodyType;
	}

}
