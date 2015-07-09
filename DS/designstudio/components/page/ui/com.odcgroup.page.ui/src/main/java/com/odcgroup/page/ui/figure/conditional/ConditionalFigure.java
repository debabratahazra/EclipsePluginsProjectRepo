package com.odcgroup.page.ui.figure.conditional;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.MouseEvent;
import org.eclipse.draw2d.MouseListener;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.graphics.RGB;

import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.metamodel.WidgetTypeConstants;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.ui.PageUIPlugin;
import com.odcgroup.page.ui.figure.AbstractHeaderFigure;
import com.odcgroup.page.ui.figure.FigureContext;
import com.odcgroup.page.ui.figure.TechnicalBoxFigure;
import com.odcgroup.page.ui.figure.tab.ScrollableHeaderFigure;
import com.odcgroup.page.ui.figure.tab.TabBoxType;

/**
 * The ConditionalWidget represents a Condition (if-else...).
 * 
 * @author atr
 */
public class ConditionalFigure extends TechnicalBoxFigure implements IConditionalFigure {
	
	/** The header figure. */
	private AbstractHeaderFigure header;
	
	private static String COLOR_CONDITIONALFIGURE_BORDER = "COLOR_CONDITIONALFIGURE_BORDER";
	static {
	    PageUIPlugin.setColorInRegistry(COLOR_CONDITIONALFIGURE_BORDER , new RGB(139, 69, 19));
	}
	/**
	 * @see com.odcgroup.page.ui.figure.BoxFigure#setBoxType(java.lang.String)
	 */
	public void setBoxType(String newBoxType) {
		setBoxType(new TabBoxType(this));
	}
	
    /**
     * Paint the specific figure
     * 
     * @param graphics
     *          The graphics context
     */
    public void paintSpecificFigure(Graphics graphics) {
        drawConditionalIndicator(graphics);
       
    }	
	
    /**
     * Draws an visual indicator for the Conditional.
     * 
     * @param graphics 
     *          The graphics context
     */
    private void drawConditionalIndicator(Graphics graphics) {
        if (getFigureContext().isPreviewMode()) {
            return;
        }

        if (getWidget().getContents().size() != 0) {
            return;
        }
        
        graphics.setClip(new Rectangle(5, -6, 70, 12));
        graphics.drawText("Conditional", 5, -6);
        // Set the clip to the bounds
        graphics.setClip(new Rectangle(getBounds().x, getBounds().y, getBounds().width, getBounds().height));
    }	
	
    /**
     * Gets the HeaderFigure.
     * 
     * @return AbstractHeaderFigure
     */
    public AbstractHeaderFigure getHeaderFigure() {
        return header;
    }	
	
	/**
	 * Creates the HeaderFigure.
	 */
	private void createHeader() {
		if (getFigureContext().isPreviewMode()) {
			// Only create the Header in design-mode
			return;
		}
			
		header = new ConditionalHeaderFigure(this, WidgetTypeConstants.CONDITIONAL_BODY);
		if (getFigureContext().isDesignMode()) {
			ScrollableHeaderFigure scroll = new ScrollableHeaderFigure(getWidget(), getFigureContext());
			scroll.addHeader(header);
			add(scroll, 0);
		} else {
			add(header, 0);
		}
		
		header.addMouseListener(new MouseListener.Stub() { 
			public void mousePressed(MouseEvent me) {
				if (header.containsPoint(me.getLocation())) {
					header.changeSelection(me.getLocation());
				}
			}
		});
	}

	/**
	 * Adds a figure. Note that the number of figures is not synchronized with the number of Widgets
	 * since we add an extra HeaderFigure as the first element. We need to take this into account
	 * when we add / remove Widgets.
	 */
	@Override
	public void add(IFigure figure, Object constraint, int index) {
		if (header != null) {
			if (figure instanceof IConditionFigure) { 
				index += 1;
			}
		}
		super.add(figure, constraint, index);
	}

	@Override
	public IConditionFigure getCondition(int index) {
		// add 1 to index to skip the header
		IFigure fig = getAllChildren().get(index+1);
		if (fig instanceof IConditionFigure) {
			return (IConditionFigure)fig;
		}
		return null;
	}

	/**
	 * Creates a new ConditionalFigure.
	 * 
	 * @param widget
	 *            The Widget being displayed by this Figure
	 * @param figureContext
	 *            The context providing information required for displaying the page correctly
	 */
	public ConditionalFigure(Widget widget, FigureContext figureContext) {
		super(widget, figureContext);		
		setBoxType(PropertyTypeConstants.BOX_TYPE_VERTICAL);
		setBorderColor(PageUIPlugin.getColor(COLOR_CONDITIONALFIGURE_BORDER));
		createHeader();
	}
	
}