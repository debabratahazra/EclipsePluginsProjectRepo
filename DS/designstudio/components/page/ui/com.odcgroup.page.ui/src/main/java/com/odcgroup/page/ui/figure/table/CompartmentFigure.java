package com.odcgroup.page.ui.figure.table;

import java.util.Collections;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Rectangle;

import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.symbols.SymbolsExpander;
import com.odcgroup.page.model.symbols.SymbolsExpanderFactory;
import com.odcgroup.page.model.util.TableHelper;
import com.odcgroup.page.model.widgets.table.ITableGroup;
import com.odcgroup.page.ui.figure.BoxFigure;
import com.odcgroup.page.ui.figure.FigureContext;
import com.odcgroup.page.ui.figure.IWidgetFigure;

public class CompartmentFigure extends BoxFigure  {
 
	/**
	 * @param figureContext
	 */
	public CompartmentFigure(Widget widget, FigureContext figureContext) {
		super(widget, figureContext);
		setBoxType(PropertyTypeConstants.BOX_TYPE_VERTICAL);		
	}
	
	@Override
	protected void drawCaption(Graphics graphics) {
		String value = "Group(s)";	
		graphics.setFont(getFigureConstants().getCaptionFont(false));
		graphics.drawText(value, 3, -2);				
	}
	
	/**
	 * Gets the horizontal alignment.
	 * 
	 * @return String The horizontal alignment
	 */
	public String getHorizontalAlignment() {
		String value = getString(PropertyTypeConstants.HORIZONTAL_ALIGNMENT);
        SymbolsExpander expander = SymbolsExpanderFactory.getSymbolsExpander();
        if (expander != null) {
        	value = expander.substitute(value, getWidget());
        }	
        return value;
	}
	
	@Override
	public int getMinHeight() {
		int size = getChildren().size();
		return size * 16 + 15;
	}
	
	@Override
	public int getMaxHeight() {
		int size = getChildren().size();
		return size * 16 + 15;
	}
	
	
	@Override
	public int getMinWidth() {
		int size = getChildren().size();
		int wspace = getFigureConstants().getWidgetSpacing() * 2;
		int length = 0;
		if(size > 0) {
			for(int i=0; i<size;i++) {
				TableGroupFigure fig = (TableGroupFigure)getChildren().get(i);
				length = Math.max(length, fig.getMinWidth()+wspace);
			}			
		}
		return length + size * 10;
	}
	
	@Override
	public int getMaxWidth() {
		int size = getChildren().size();
		int length = 0;
		if(size > 0) {
			length = ((TableGroupFigure)(getChildren().get(0))).getMinWidth();
		}
		return length + size * 10;
	}
	
	/**
	 * @see org.eclipse.draw2d.Figure#paintChildren(org.eclipse.draw2d.Graphics)
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected void paintChildren(Graphics graphics) {
		IFigure child;
		Hashtable<Integer, IWidgetFigure> table = new Hashtable<Integer, IWidgetFigure>();
		List<IWidgetFigure> groups = getAllChildren();
		for(int i=0;i<groups.size();i++) {
			Widget wid = groups.get(i).getWidget();
			if(wid.getTypeName().equals("TableGroup")) {					
				ITableGroup tg = TableHelper.getTableGroup(wid);
				if(tg.getRank() != i+1) {
					table.put(tg.getRank(), groups.get(i));						
				} else {
					table.put(i+1, groups.get(i));
				}
			}
		}
		Vector v = new Vector(table.keySet());
		Collections.sort(v);  
        
		for(int i=0;i<v.size();i++) {
			child = table.get(v.get(i));
			if (child.isVisible()) {
				Rectangle bounds = child.getBounds();
				int index = i+1;
				bounds.x = index * 10;
				index = index-1;
				bounds.y = index * 16;
				bounds.y += 13;
				graphics.clipRect(bounds);
				child.paint(graphics);
				graphics.restoreState();
			}	
		}
	}
	
	/* (non-Javadoc)
	 * @see com.odcgroup.page.ui.figure.BoxFigure#drawBoxTypeIndicator(org.eclipse.draw2d.Graphics)
	 */
	protected void drawBoxTypeIndicator(Graphics graphics) {		
	}	
}
