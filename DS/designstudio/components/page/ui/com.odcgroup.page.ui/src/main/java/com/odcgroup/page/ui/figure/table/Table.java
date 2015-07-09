package com.odcgroup.page.ui.figure.table;

import static com.odcgroup.page.metamodel.PropertyTypeConstants.GROUP_SORT_RANK;
import static com.odcgroup.page.metamodel.WidgetTypeConstants.COLUMN_HEADER_INDEX;

import org.apache.commons.lang.StringUtils;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.MouseEvent;
import org.eclipse.draw2d.MouseListener;
import org.eclipse.draw2d.geometry.Rectangle;

import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.metamodel.WidgetLibrary;
import com.odcgroup.page.metamodel.WidgetLibraryConstants;
import com.odcgroup.page.metamodel.WidgetTemplate;
import com.odcgroup.page.metamodel.WidgetTypeConstants;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.util.WidgetFactory;
import com.odcgroup.page.ui.figure.AbstractHeaderFigure;
import com.odcgroup.page.ui.figure.Button;
import com.odcgroup.page.ui.figure.FigureContext;
import com.odcgroup.page.ui.figure.Glue;
import com.odcgroup.page.ui.figure.IWidgetFigure;
import com.odcgroup.page.ui.figure.Label;
import com.odcgroup.page.ui.figure.Spacer;
import com.odcgroup.page.ui.figure.TechnicalBoxFigure;

/**
 * The figure for Tables.
 * 
 * A Table consists of a HeaderFigure and a BoxFigure. The BoxFigure contains the Columns, ColumnHeader's and
 * ColumnBody's.
 * 
 * @author Gary Hayes
 */
public class Table extends TechnicalBoxFigure {

	/** The Header figure. */
	private AbstractHeaderFigure header;

	/** The Body figure. */
	private IWidgetFigure body;

	/** This figure contains the paging buttons. */
	private IWidgetFigure pagingButtons;
	
	/**
	 * Creates a new TableFigure.
	 * 
	 * @param widget
	 *            The Widget being displayed by this Figure
	 * @param figureContext
	 *            The context providing information required for displaying the page correctly
	 */
	public Table(Widget widget, FigureContext figureContext) {
		super(widget, figureContext);
		setBoxType(PropertyTypeConstants.BOX_TYPE_VERTICAL);
		setLayoutManager(new TableLayout(figureContext));
		
	}
	
	/**
	 * Gets the minimum width of the figure. The layouts always set the width to be greater than or equal to the minimum
	 * width.
	 * 
	 * @return int The minimum width of the figure
	 */
	public int getMinWidth() {
		return super.getMinWidth();
	}

	/**
	 * Gets the minimum height of the figure. The layouts always set the height to be greater than or equal to the
	 * minimum height.
	 * 
	 * @return int The minimum height of the figure
	 */
	public int getMinHeight() {
		return super.getMinHeight() + TableLayout.SPACING_VALUE ;
	}

	/**
	 * Gets the Preview Size.
	 * 
	 * @return int The Preview Size
	 */
	public int getPreviewSize() {
		return getInt(PropertyTypeConstants.PREVIEW_SIZE);
	}	

	/**
	 * Gets the Page Size.
	 * 
	 * @return int The Page Size
	 */
	public int getPageSize() {
		return getInt(PropertyTypeConstants.PAGE_SIZE);
	}

	/**
	 * Implements the required treatment after the property have been changed.
	 * 
	 * @param name
	 *            The property name
	 */
	public void afterPropertyChange(String name) {
		if (name.equals(PropertyTypeConstants.PAGE_SIZE)) {
			updatePagingButtons();
		}
	}

	/**
	 * Override the base-class version to create the Header. Note this cannot be done in the constructor since the
	 * framework subsequently adds children are added BEFORE the Header.
	 * 
	 * @param graphics
	 *            The graphics context
	 */
	public void paint(Graphics graphics) {
		// Note that since createBody, createHeader and createPagingButtons also call add the header is added BEFORE the
		// body
		createPagingButtons();
		createBody();
		createHeader();		
		super.paint(graphics);
	}
	
	/**
	 * Paint the specific figure
	 * 
	 * @param graphics
	 * 			The graphics context
	 */
	public void paintSpecificFigure(Graphics graphics) {
		drawTableIndicator(graphics);
	}
	/**
	 * Draws an visual indicator for the table.
	 * 
	 * @param graphics 
	 * 			The graphics context
	 */
	private void drawTableIndicator(Graphics graphics) {
		if (getFigureContext().isPreviewMode()) {
			return;
		}

		graphics.setClip(new Rectangle(5, -6, 30, 12));
		graphics.drawText("T", 5, -6);
		// Set the clip to the bounds
		graphics.setClip(new Rectangle(getBounds().x, getBounds().y, getBounds().width, getBounds().height));
	}


	/**
	 * Updates the paging buttons.
	 */
	private void updatePagingButtons() {
		// Force the re-creation of the paging buttons
		remove(pagingButtons);
		pagingButtons = null;
	}

	/**
	 * Creates the body.
	 */
	private void createBody() {
		if (body != null) {
			return;
		}

		WidgetLibrary wl = getMetaModel().findWidgetLibrary(WidgetLibraryConstants.XGUI);
		WidgetTemplate wt = wl.findWidgetTemplate("HorizontalBox");
		Widget dummyBoxWidget = new WidgetFactory().create(wt);

		body = new TableBody(dummyBoxWidget, getFigureContext());

		add(body);
	}
	
	/**
	 * Creates the HeaderFigure.
	 */
	private void createHeader() {
		if (header != null) {
			return;
		}

		if (getFigureContext().isPreviewMode()) {
			// Only create the Header in design-mode
			return;
		}

		header = new TableHeaderFigure(this, getHeaderFigureWidgetType(), body);
		header.addMouseListener(new MouseListener.Stub() {
			public void mousePressed(MouseEvent me) {
				if (header.containsPoint(me.getLocation())) {
					header.changeSelection(me.getLocation());
				}
			}
		});
		add(header);
	}
	
	/**
	 * Gets the name of the WidgetType which is used by the HeaderFigure to 
	 * display itself.
	 * 
	 * @return String 
	 */
	protected String getHeaderFigureWidgetType() {
		return WidgetTypeConstants.COLUMN;
	}

	/**
	 * Adds a figure. Note that in order to draw the Table correctly we artificially insert a HeaderFigure and a
	 * BoxFigure. Therefore we need to override the add and remove methods to take this into account.
	 * 
	 * @param figure
	 *            The figure to add
	 * @param constraint
	 *            A constraint
	 * @param index
	 *            The index
	 */
	public void add(IFigure figure, Object constraint, int index) {
		// Note that since createBody and createHeader also call add the header is added BEFORE the body
		createPagingButtons();
		createBody();
		createHeader();

		int containerSize = 1;
		if (getFigureContext().isDesignMode()) {
			// In design mode we display both the header and the body
			containerSize += 1;
		}

		if (getPageSize() > 0) {
			containerSize += 1;
		}

		if (getChildren().size() < containerSize) {
			super.add(figure, constraint, index);
		} else {
			body.add(figure, constraint, index);
		}
	}

	/**
	 * Removes a figure. Note that in order to draw the Table correctly we artificially insert a HeaderFigure and a
	 * BoxFigure. The Columns are removed from the body (BoxFigure).
	 * 
	 * @param figure
	 *            The figure to add
	 */
	public void remove(IFigure figure) {
		if (figure == null) {
			return;
		}
		if (figure == pagingButtons) {
			super.remove(figure);
		} else {
			body.remove(figure);
		}
	}
	
	/**
	 * Returns true if this Table should be displayed as a Tree. This is true if at least one of the ColumnBodies
	 * grouping properties is true.
	 * 
	 * @return boolean Returns true if this Table should be displayed as a Tree
	 */
	public boolean isGrouped() {
		for (Widget column : getWidget().getContents()) {
			Widget header = column.getContents().get(COLUMN_HEADER_INDEX);
			String rankStr = header.getPropertyValue(GROUP_SORT_RANK);
			if (! StringUtils.isEmpty(rankStr)) {
				int rank = Integer.parseInt(rankStr);
				if (rank > 0) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Creates the paging buttons. This consists of a Horizontal Box containing a glue and then the labels "Results 1 to
	 * 10 followed by the four buttons, First, Previous, Next, Last.
	 */
	private void createPagingButtons() {
		if (getPageSize() == 0) {
			return;
		}

		if (pagingButtons != null) {
			return;
		}

		// Main Box
		WidgetLibrary wl = getMetaModel().findWidgetLibrary(WidgetLibraryConstants.XGUI);
		WidgetTemplate bwt = wl.findWidgetTemplate("HorizontalBox");
		Widget dummyBoxWidget = new WidgetFactory().create(bwt);
		pagingButtons = new TechnicalBoxFigure(dummyBoxWidget, getFigureContext());
		
		// Glue
		WidgetTemplate gwt = wl.findWidgetTemplate("Glue");
		Widget gw = new WidgetFactory().create(gwt);
		IWidgetFigure g = new Glue(gw, getFigureContext());
		pagingButtons.add(g);

		// Note that the Labels and Buttons are not linked to a real EMF eResource.
		// Therefore the methods getI18n value does not work for these artifically
		// created Widgets

		// Results 1 to 2 of 10. Note the *4 is simply to provide more results than the page size
		String ps = String.valueOf(getPageSize());
		String pt = String.valueOf(getPageSize() * 4);
		String label = "general.results" + " 1 " + "general.to" + " " + ps + " "
				+ "general.of" + " " + pt;

		addLabel(wl, pagingButtons, label);

		// First, Previous, Next, Last
		addSpacer(wl, pagingButtons);
		addButton(wl, pagingButtons, "general.nav.first");
		addSpacer(wl, pagingButtons);
		addButton(wl, pagingButtons, "general.nav.prev");
		addSpacer(wl, pagingButtons);
		addButton(wl, pagingButtons, "general.nav.next");
		addSpacer(wl, pagingButtons);
		addButton(wl, pagingButtons, "general.nav.last");
		addSpacer(wl, pagingButtons);

		add(pagingButtons);
	}

	/**
	 * Adds a Label Widget and Figure.
	 * 
	 * @param wl
	 *            The WidgetLibrary
	 * @param parentFigure
	 *            The parent figure to add the Label to
	 * @param caption
	 *            The caption of the Label
	 */
	private void addLabel(WidgetLibrary wl, IWidgetFigure parentFigure, String caption) {

		WidgetTemplate wt = wl.findWidgetTemplate("Label");
		Widget lw = new WidgetFactory().create(wt);
		IWidgetFigure lf = new Label(lw, getFigureContext());
		lf.setText(caption);
		parentFigure.add(lf);
	}

	/**
	 * Adds a Button Widget and Figure.
	 * 
	 * @param wl
	 *            The WidgetLibrary
	 * @param parentFigure
	 *            The parent figure to add the Button to
	 * @param caption
	 *            The caption
	 */
	private void addButton(WidgetLibrary wl, IWidgetFigure parentFigure, String caption) {
		WidgetTemplate wt = wl.findWidgetTemplate("Button");
		Widget bw = new WidgetFactory().create(wt);
		IWidgetFigure bf = new Button(bw, getFigureContext());
		bf.setText(caption);
		parentFigure.add(bf);
	}
	
	/**
	 * Adds a Spacer Widget and Figure.
	 * 
	 * @param wl
	 *            The WidgetLibrary
	 * @param parentFigure
	 *            The parent figure to add the Button to
	 */
	private void addSpacer(WidgetLibrary wl, IWidgetFigure parentFigure) {
		WidgetTemplate swt = wl.findWidgetTemplate("Spacer");
		Widget sw = new WidgetFactory().create(swt);
		sw.setPropertyValue(PropertyTypeConstants.WIDTH, "8");
		IWidgetFigure sf = new Spacer(sw, getFigureContext());
		parentFigure.add(sf);		
	}

	/**
	 * Gets the header figure.
	 * 
	 * @return AbstractHeaderFigure returns the header figure
	 */
	public AbstractHeaderFigure getHeaderFigure() {
		return header;
	}
}