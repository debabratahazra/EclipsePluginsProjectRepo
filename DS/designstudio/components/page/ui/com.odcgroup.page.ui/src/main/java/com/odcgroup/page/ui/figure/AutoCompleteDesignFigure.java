package com.odcgroup.page.ui.figure;

import org.apache.commons.lang.StringUtils;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.GridLayout;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.RGB;

import com.odcgroup.mdf.ecore.util.DomainRepository;
import com.odcgroup.mdf.metamodel.MdfModelElement;
import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.snippets.ILineItemSnippet;
import com.odcgroup.page.model.snippets.ILineSnippet;
import com.odcgroup.page.model.util.WidgetHelper;
import com.odcgroup.page.model.widgets.IAutoCompleteDesign;
import com.odcgroup.page.ui.PageUIPlugin;
import com.odcgroup.page.ui.figure.table.TableGridLayout;
import com.odcgroup.translation.core.ITranslation;
import com.odcgroup.translation.core.ITranslationKind;
import com.odcgroup.translation.core.ITranslationManager;
import com.odcgroup.translation.core.TranslationCore;
import com.odcgroup.translation.core.TranslationException;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.core.repository.OfsResourceHelper;

/**
 * @author pkk
 *
 */
public class AutoCompleteDesignFigure extends TechnicalBoxFigure {
	
	/** The border Color. */
	private Color borderColor; 
        private static final String AUTOCOMPLETE_DESIGN_FIGURE_BORDER_COLOR = "AUTOCOMPLETE_DESIGN_FIGURE_BORDER_COLOR";
	/**
	 * @param widget
	 * @param figureContext
	 */
	public AutoCompleteDesignFigure(Widget widget, FigureContext figureContext) {
		super(widget, figureContext);
		createContents();
		GridLayout gd = new TableGridLayout(1, false);
		gd.verticalSpacing = 0;
		setLayoutManager(gd);
		initialize();
	}
	
	/**
	 * 
	 */
	private void createContents() {
		add(new SearchFigure(getWidget(), getFigureContext()));
		add(new AutoCompleteTitleFigure(getWidget(), getFigureContext()));
		add(new AutoCompleteDesignLineFigure(getWidget(), getFigureContext()));
	}		
	
	/**
	 * Gets the border color.
	 * 
	 * @return Color The border color
	 */
	public Color getBorderColor() {
		if (borderColor == null) {
			borderColor = PageUIPlugin.getColor(AUTOCOMPLETE_DESIGN_FIGURE_BORDER_COLOR);	
		}
		return borderColor;
	}
	
	/**
	 * Sets the border color.
	 * 
	 * @param newBorderColor The border color to set
	 */
	protected void setBorderColor(Color newBorderColor) {
		this.borderColor = newBorderColor;	
	}
	
	@Override
	public BoxType getBoxType() {
		return new AutoCompleteDesignBoxType(this);
	}

	/**
	 *
	 */
	static class AutoCompleteDesignLineFigure extends TechnicalBoxFigure {

		/**
		 * @param widget
		 * @param figureContext
		 */
		public AutoCompleteDesignLineFigure(Widget widget,
				FigureContext figureContext) {
			super(widget, figureContext);
			createContents();
			GridLayout gd = new TableGridLayout(4, false);
			gd.marginHeight = 0;
			setLayoutManager(gd);
		}
		
		/**
		 * 
		 */
		private void createContents() {	
			for (int i = 0; i < 3; i++) {	
				add(new LabelFigure(getWidget(), getFigureContext(), "Line "+(i+1)));				
				LineItemFigure text1 = new LineItemFigure(getWidget(), getFigureContext(), 0, i) ;				
				add(text1);
				LineItemFigure text2 = new LineItemFigure(getWidget(), getFigureContext(), 1, i) ;
				add(text2);					
				LineItemFigure text3 = new LineItemFigure(getWidget(), getFigureContext(), 2, i) ;
				add(text3);
			}		
		}
		
		@Override
		public int getMinWidth() {
			return 580;
		}
		
		/**
		 * @see com.odcgroup.page.ui.figure.BoxType#getMinHeight()
		 */
		public int getMinHeight() {
			return 70;	
		}
		
		protected void drawBorder(Graphics graphics) {
		}
		
		protected void drawAggregationBorder(Graphics graphics) {			
		}		
		
	}
	
	/**
	 *
	 */
	static class SearchFigure extends TechnicalBoxFigure {

		public SearchFigure(Widget widget, FigureContext figureContext) {
			super(widget, figureContext);
			createContents();
			GridLayout gd = new TableGridLayout(2, false);
			gd.verticalSpacing = 0;
			setLayoutManager(gd);
		}
		
		private void createContents() {	
			add(new LabelFigure(getWidget(), getFigureContext(), ""));	
			SearchField text1 = new SearchField(getWidget(), getFigureContext()) {
				public int getMinWidth() {
					return 450;
				}				
			};
			add(text1);
		}
		
		@Override
		public int getMinWidth() {
			return 580;
		}
		@Override		
		protected void drawBorder(Graphics graphics) {
		}
		@Override		
		protected void drawAggregationBorder(Graphics graphics) {			
		}	
		
	}
	
	/**
	 *
	 */
	static class AutoCompleteTitleFigure extends TechnicalBoxFigure {

		public AutoCompleteTitleFigure(Widget widget, FigureContext figureContext) {
			super(widget, figureContext);
			createContents();
			GridLayout gd = new TableGridLayout(2, false);
			gd.verticalSpacing = 0;
			setLayoutManager(gd);
		}
		
		private void createContents() {	
			add(new LabelFigure(getWidget(), getFigureContext(), "Title"));	
			TextField text1 = new TextField(getWidget(), getFigureContext()) {
				public int getColumns() {
					return 92;
				}

				@Override
				protected void paintSpecificFigure(Graphics graphics) {
					IAutoCompleteDesign autoDesign = WidgetHelper.getAutoCompleteDesign(getWidget());
					super.paintSpecificFigure(graphics);
					Font font = new Font(getFont().getDevice(), "Tahoma", 10, SWT.BOLD);
					graphics.setFont(font);
		            graphics.drawText(autoDesign.getTitleAttribute(), new Point(5, 0));
		            font.dispose();
				}				
			};
			add(text1);
		}
		@Override
		public int getMinWidth() {
			return 580;
		}
		@Override		
		protected void drawBorder(Graphics graphics) {
		}
		@Override		
		protected void drawAggregationBorder(Graphics graphics) {			
		}		
		
	}
	
	/**
	 * @author pkk
	 *
	 */
	static class LabelFigure extends TechnicalBoxFigure {
		/** */
		private String value;

		/**
		 * @param widget
		 * @param figureContext
		 * @param num 
		 */
		public LabelFigure(Widget widget, FigureContext figureContext, String val) {
			super(widget, figureContext);
			setBoxType(PropertyTypeConstants.BOX_TYPE_HORIZONTAL);
			this.value = val;
		}

		/**
		 * @see com.odcgroup.page.ui.figure.BoxFigure#paintSpecificFigure(org.eclipse.draw2d.Graphics)
		 */
		@Override
		protected void paintSpecificFigure(Graphics graphics) {
			Font font = new Font(getFont().getDevice(), "Tahoma", 10, SWT.BOLD);
			graphics.setFont(font);
            graphics.drawText(value, new Point(5, 0));
            font.dispose();
		}		

		@Override
		public int getMinWidth() {
			return 80;
		}
		
		protected void drawBorder(Graphics graphics) {
		}
		
		protected void drawAggregationBorder(Graphics graphics) {			
		}		
		
	}
	
	/**
	 *
	 */
	static class LineItemFigure extends AbstractAlignableWidgetFigure {
		
		private int index;
		private int line;


		/**
		 * Creates a new TextField.
		 * 
		 * @param widget The Widget being displayed by this Figure
		 * @param figureContext The context providing information required for displaying the page correctly
		 */
		public LineItemFigure(Widget widget, FigureContext figureContext, int index, int line) {
			super(widget, figureContext);
			this.index = index;
			this.line = line;
		}
		
		/**
		 * Gets the minimum width of the figure. 
		 * 
		 * @return int The minimum width of the figure
		 */
		public int getMinWidth() {
			return 150;
		}
		
		/**
		 * Gets the minimum height of the figure.
		 * 
		 * @return int The minimum height of the figure
		 */
		public int getMinHeight() {
			return getFigureConstants().getSimpleWidgetDefaultHeight();
		}	
		
		/**
		 * Paints the TextField.
		 * 
		 * @param graphics The Graphics context
		 */
		protected void paintSpecificFigure(Graphics graphics) {
			FigureConstants fc = getFigureConstants();
			graphics.setForegroundColor(fc.getFieldColor());
			graphics.setLineStyle(fc.getFieldLineStyle());
			
			// The -1 ensures that the right and bottom lines are drawn, otherwise they wiould fall outside the bounds of the TextField
			Rectangle b = getBounds();
			graphics.drawRectangle(new Rectangle(0, 0, b.width - 1, b.height - 1));
			
			// The 1 serves to center the text in the rectangle
			IAutoCompleteDesign design = WidgetHelper.getAutoCompleteDesign(getWidget());
			ILineSnippet linesnip = null;
			if (line == 0) {
				linesnip = design.getFirstLine();
			} else if (line == 1) {
				linesnip = design.getSecondLine();
			} else {
				linesnip = design.getThirdLine();
			}
			if (linesnip != null && linesnip.getLineItems().size()>index ) {
				ILineItemSnippet lineItem = linesnip.getLineItems().get(index);
				if (lineItem != null) {
					String pv = "";
					if (lineItem.isTranslation()) {
						pv = getTranslation(lineItem);
						
					} 
					if (StringUtils.isEmpty(pv)) {
						pv = lineItem.getAttributeName();
					}
					pv = pv+": value("+lineItem.getCSSClass()+")";
					if (! StringUtils.isEmpty(pv)) {
						graphics.drawText(pv, 5, 1);
					}
				}
			}
		}
		
		/**
		 * @param ofsProject
		 * @return
		 */
		private String getTranslation(ILineItemSnippet lineItem) {
			
			Widget widget = lineItem.getParent().getParentWidget().getWidget();
			if (widget.eResource() == null) {
				return "";
			}
			IOfsProject ofsProject = OfsResourceHelper.getOfsProject(widget.eResource());
			DomainRepository repository = DomainRepository.getInstance(ofsProject);
			Widget root = widget.getRootWidget();
			String datasetName = root.getPropertyValue(PropertyTypeConstants.DOMAIN_ENTITY);
			MdfModelElement element = widget.findDatasetProperty(repository, datasetName, lineItem.getAttributeName());
			if (element == null) {
				return "";
			}
			ITranslationManager manager = TranslationCore.getTranslationManager(ofsProject.getProject());
			ITranslation translation = manager.getTranslation(element);
			if (translation != null) {
				try {
					return translation.getText(ITranslationKind.NAME, 
							manager.getPreferences().getDefaultLocale());
				} catch (TranslationException e) {
					return "";
				}
			}
			return "";
		}
	}
 
	/**
	 * initialize method to register color.
	 */
	private void initialize(){
	    PageUIPlugin.setColorInRegistry(AUTOCOMPLETE_DESIGN_FIGURE_BORDER_COLOR, new RGB(128, 0, 0));
	}

}
