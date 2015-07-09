package com.odcgroup.page.ui.figure.tab;

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

/**
 * The TabbedPane represents a TabbedPane.
 * 
 * @author atr
 */
public class TabbedPaneFigure extends TechnicalBoxFigure implements ITabbedPaneFigure {
	
	/** The header figure. */
	private AbstractHeaderFigure header;
	private static final String TABBEDPANE_FIGURE_BORDER_COLOR = "TABBEDPANE_FIGURE_BORDER_COLOR";

	@Override
	public void setBoxType(String newBoxType) {
		setBoxType(new TabbedPaneBoxType(this));
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
     * Paint the specific figure
     * 
     * @param graphics
     *          The graphics context
     */
    public void paintSpecificFigure(Graphics graphics) {
        drawTabbedPaneIndicator(graphics);
    }
    
    /**
     * Draws an visual indicator for the TabbedPane.
     * 
     * @param graphics 
     *          The graphics context
     */
    private void drawTabbedPaneIndicator(Graphics graphics) {
        if (getFigureContext().isPreviewMode()) {
            return;
        }
        
        if (getWidget().getContents().size() != 0) {        	
            return;
        }
        
        graphics.setClip(new Rectangle(5, -6, 70, 12));
        graphics.drawText("Tabbed Pane", 5, -6);
        graphics.setClip(new Rectangle(getBounds().x, getBounds().y, getBounds().width, getBounds().height));
    }	
	
	/**
	 * Creates the HeaderFigure.
	 */
	private void createHeader() {	
		header = new TabbedPaneHeaderFigure(this, WidgetTypeConstants.TAB);
		
		header.addMouseListener(new MouseListener.Stub() { 
			public void mousePressed(MouseEvent me) {
				if (me.getSource() instanceof TabbedPaneHeaderFigure) {
					if (header.containsPoint(me.getLocation())) {
						header.changeSelection(me.getLocation());
					}
				}
			}
		});
		if (getFigureContext().isDesignMode()) {
			ScrollableHeaderFigure scroll = new ScrollableHeaderFigure(getWidget(), getFigureContext());
			scroll.addHeader(header);
			add(scroll);
		} else {
			add(header);
		}
	}

	/**
	 * Adds a figure. Note that the number of figures is not synchronized with the number of Widgets
	 * since we add an extra HeaderFigure as the first element. We need to take this into account
	 * when we add / remove Widgets.
	 * 
	 * @param figure The figure
	 * @param constraint A constraint
	 * @param index The index
	 */
	public void add(IFigure figure, Object constraint, int index) {
		if (header != null) {
			if (figure instanceof TabFigure) { 
				index += 1;
			}
		}
		super.add(figure, constraint, index);
	}

	@Override
	public ITabFigure getTab(int index) {
		// add 1 to index to skip the header
		IFigure fig = getAllChildren().get(index+1);
		if (fig instanceof ITabFigure) {
			return (ITabFigure)fig;
		}
		return null;

	}

	/**
	 * Creates a new TabbedPane.
	 * 
	 * @param widget
	 *            The Widget being displayed by this Figure
	 * @param figureContext
	 *            The context providing information required for displaying the page correctly
	 */
	public TabbedPaneFigure(Widget widget, FigureContext figureContext) {
		super(widget, figureContext);
		initialize();
		setBoxType(PropertyTypeConstants.BOX_TYPE_VERTICAL);		
		setBorderColor(PageUIPlugin.getColor(TABBEDPANE_FIGURE_BORDER_COLOR));
		createHeader();
	}	
	
	/**
	 * initialize method to register color.
	 */
	private void initialize(){
	    PageUIPlugin.setColorInRegistry(TABBEDPANE_FIGURE_BORDER_COLOR, new RGB(139, 69, 19));
	}
}
