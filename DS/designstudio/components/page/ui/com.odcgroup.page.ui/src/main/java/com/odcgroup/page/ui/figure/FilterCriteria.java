package com.odcgroup.page.ui.figure;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.ImageFigure;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;

import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.ui.edit.PaintUtils;

/**
 * FilterCriteria widget figure
 *
 * @author pkk
 *
 */
public class FilterCriteria extends TechnicalBoxFigure {
	
	/** */
	private static Image ADD = createImage("/icons/obj16/add.png"); //$NON-NLS-1$	
	/** */
	private static Image BIN = createImage("/icons/obj16/bin_empty.png"); //$NON-NLS-1$

	/**
	 * @param widget
	 * @param figureContext
	 */
	public FilterCriteria(Widget widget, FigureContext figureContext) {
		super(widget, figureContext);
		createContents();
	}
	
	/**
	 * 
	 */
	private void createContents() {
		
		for (int i = 0; i < 3; i++) {
			add(new ImageFigure(BIN));		
			add(new NumberFigure(getWidget(), getFigureContext(), i+1));		
			ComboBox combo = new ComboBox(getWidget(), getFigureContext()) {
				public int getMinWidth() {
					return 300;
				}
				
			};
			add(combo);
			ComboBox combo2 = new ComboBox(getWidget(), getFigureContext()) {
				public int getMinWidth() {
					return 100;
				}
				
			};
			add(combo2);	
			
			TextField text = new TextField(getWidget(), getFigureContext()) {
				public int getColumns() {
					return 20;
				}				
			};
			add(text);
			if (i == 2) {
				add(new ImageFigure(ADD));	
			} else {
				add(new Figure());
			}
		}		
	}
	
	/**
	 * @see com.odcgroup.page.ui.figure.BoxFigure#setBoxType(java.lang.String)
	 */
	public void setBoxType(String newBoxType) {
		setBoxType(new FilterCriteriaBoxType(this));
	}
	
	/**
	 * @author pkk
	 *
	 */
	class NumberFigure extends TechnicalBoxFigure {
		/** */
		private int num;

		/**
		 * @param widget
		 * @param figureContext
		 * @param num 
		 */
		public NumberFigure(Widget widget, FigureContext figureContext, int num) {
			super(widget, figureContext);
			setBoxType(PropertyTypeConstants.BOX_TYPE_HORIZONTAL);
			this.num = num;
		}

		/**
		 * @see com.odcgroup.page.ui.figure.BoxFigure#paintSpecificFigure(org.eclipse.draw2d.Graphics)
		 */
		@Override
		protected void paintSpecificFigure(Graphics graphics) {
		    if(PaintUtils.isWidgethAlignLead(getWidget())){
			PaintUtils.paintIcons(getWidget(), graphics,getMinWidth() - PaintUtils.getWidth(getWidget()));
		    }else {
			PaintUtils.paintIcons(getWidget(), graphics,0);
		    }
			Font font = new Font(getFont().getDevice(), "Tahoma", 10, SWT.BOLD);
			graphics.setFont(font);
            graphics.drawText(num+"", new Point(5, 0));
            font.dispose();
		}
		
		/**
		 * @see com.odcgroup.page.ui.figure.TechnicalBoxFigure#drawBorder(org.eclipse.draw2d.Graphics)
		 */
		protected void drawBorder(Graphics graphics) {
			// no border
		}
		
		
	}
 
}
