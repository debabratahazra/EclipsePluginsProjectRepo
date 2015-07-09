/**
 * 
 */
package com.odcgroup.page.ui.figure;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.runtime.Assert;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;

import com.odcgroup.page.log.Logger;
import com.odcgroup.page.metamodel.MetaModel;
import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.metamodel.util.MetaModelRegistry;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.util.WidgetUtils;
import com.odcgroup.page.ui.PageUIPlugin;
import com.odcgroup.page.ui.util.SWTUtils;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.core.repository.OfsResourceHelper;

/**
 * The base class for all Widgets drawn by the Page Designer.
 * 
 * @author atr
 */
public abstract class AbstractWidgetFigure extends Figure implements IWidgetFigure {

	/**
	 * The context providing information required for displaying the page correctly.
	 */
	private final FigureContext figureContext;

	/** The List of WidgetFigureDecorators. */
	private List<WidgetFigureDecorator> decorators;

	/** The Widget being displayed by this Figure. */
	private /* NOT final */ Widget widget;

	/** The text foreground color */
	private Color textForegroundColor = ColorConstants.black;
	
	/** The figure's text, can be null */
	private String text = "";
	
	private final IOfsProject ofsProject;
	
	@Override
	public void setText(String newText) {
		if (newText == null) {
			newText = "";
		}
		
		if (!StringUtils.equals(newText,this.text)) {
			this.text = newText;
			revalidate();
			repaint();
		}
	}
	
	/**
	 * @return the figure's text
	 */
	protected String getText() {
		return this.text;
	}
	
	@Override
	public final void setTextForegroundColor(Color color) {
		this.textForegroundColor = color;
	}
	
	/**
	 * @return Color
	 */
	protected final Color getTextForegroundColor() {
		return textForegroundColor;
	}

	@Override
	public void setToolTip(String toolTip) {
		if (StringUtils.isEmpty(toolTip)) {
			setToolTip((IFigure)null);
		} else {
			setToolTip(new org.eclipse.draw2d.Label(toolTip));
			revalidate();
			repaint();
		}
	}

	/**
	 * @return must return true if this figure is bound to a business element
	 * Currently only the Attribute figure is bound to business element (mdf)
	 */
	protected boolean isBoundToDomainAttribute() {
		return false;
	}
	
	/**
	 * Called when the edit part is disposed
	 */
	public void dispose() {
	}

	/**
	 * Returns the list of child figures, that are visible.
	 * 
	 * @return List
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List getChildren() {
		List children = new ArrayList();
		if (isVisible() /* && isExpanded() */) {
			for(Object obj : super.getChildren()) {
				IFigure fig = (IFigure) obj;
				if (fig.isVisible()) {
					children.add(fig);
				}
			}
		}
		return java.util.Collections.unmodifiableList(children);
	}

	/**
	 * Returns the list of all child figures
	 * 
	 * @return List
	 */
	public List<IWidgetFigure> getAllChildren() {
		List<?> children = super.getChildren();
		List<IWidgetFigure> widgets = new ArrayList<IWidgetFigure>();
		for (Object object : children) {
			if (object instanceof IWidgetFigure) {
				widgets.add((IWidgetFigure) object);
			}
		}
		return widgets;
	}

	/**
	 * Creates a new AbstractWidgetFigure.
	 * 
	 * @param widget
	 *            The Widget being displayed by this Figure
	 * @param figureContext
	 *            The context providing information required for displaying the page correctly
	 */
	public AbstractWidgetFigure(Widget widget, FigureContext figureContext) {
		super();

		Assert.isNotNull(widget);
		Assert.isNotNull(figureContext);

		this.widget = widget;
		this.figureContext = figureContext;
		setVisible(widget.isVisible());
		this.ofsProject = getOfsProject(widget);
	}
	
	private IOfsProject getOfsProject(Widget widget) {
		Resource res = widget.eResource();
		if (res == null) {
			if(!(getParent() instanceof IWidgetFigure)) return null;
			res = ((IWidgetFigure)getParent()).getWidget().eResource();
		}
		return OfsResourceHelper.getOfsProject(res);
	}
	
	/**
	 * Paints this Figure and its children.
	 * 
	 * @param graphics
	 *            The Graphics context
	 */
	public void paint(Graphics graphics) {
		super.paint(graphics);
		afterPaint(graphics);
	}

	@Override
	public List<WidgetFigureDecorator> getDecorators() {
		if (decorators == null) {
			decorators = new ArrayList<WidgetFigureDecorator>();
		}
		return decorators;
	}

	/**
	 * Gets the Widget.
	 * 
	 * @return Widget
	 */
	public Widget getWidget() {
		if (widget.eIsProxy()) {
			ResourceSet rs = getOfsProject().getModelResourceSet();
			widget =  (Widget) EcoreUtil.resolve(widget, rs);
		}
		return widget;
	}

	/**
	 * Gets the Property of the Widget.
	 * 
	 * @param name
	 *            The name of the Property
	 * @return Property The Property of the Widget
	 */
	protected Property getProperty(String name) {
		return getWidget().findProperty(name);
	}

	/**
	 * Gets the value of the Property of the Widget. If we are not in design mode and the Property is 18n this method
	 * returns the translated value.
	 * 
	 * @param name
	 *            The name of the Property
	 * @return String The value of the Property of the Widget
	 */
	protected String getString(String name) {
		
		String result = "";
		
		Property p = getProperty(name);
		/* when p is null => 
		 *    Unable to find the Property. This can occur,
		 *    for example, since the module also uses the BoxFigure
		 */ 

		if (p != null) {
			result = p.getValue();
		}

		return result;
	}

	/**
	 * Gets the value of the Property as an int.
	 * 
	 * @param name
	 *            The name of the Property
	 * @return int The value of the Property of the Widget
	 */
	protected int getInt(String name) {
		Property p = getProperty(name);
		if (p == null) {
			// Unable to find the Property. This can occur, for example, since the
			// module also uses the BoxFigure
			return 0;
		}

		Object obj = p.getConvertedValue();
		if (!(obj instanceof Integer)) {
			Logger.error(p.getTypeName() + " of Widget " + getWidget().getTypeName() + " is not an Integer");
			return 0;
		}
		return (Integer) obj;
	}

	/**
	 * Gets the value of the Property as a boolean.
	 * 
	 * @param name
	 *            The name of the Property
	 * @return boolean The value of the Property of the Widget
	 */
	protected boolean getBoolean(String name) {
		Property p = getProperty(name);
		if (p == null) {
			// Unable to find the Property. This can occur, for example, since the
			// module also uses the BoxFigure
			return false;
		}

		Object obj = p.getConvertedValue();
		if (!(obj instanceof Boolean)) {
			Logger.error(p.getTypeName() + " of Widget " + getWidget().getTypeName() + " is not a Boolean");
			return false;
		}
		return (Boolean) obj;
	}

	/**
	 * Allows the decorators to decorate the figure.
	 * 
	 * @param graphics
	 *            The Graphics context
	 */
	private void afterPaint(Graphics graphics) {
		for (WidgetFigureDecorator wfd : getDecorators()) {
			wfd.paint(this, graphics);
		}
	}

	/**
	 * Loads the specified image.
	 * 
	 * @param name
	 *            The name of the image to load
	 * @return Image The loaded image
	 */
	protected static Image createImage(String name) {
		return PageUIPlugin.getImageDescriptor(name).createImage();
	}

	/**
	 * Gets the minimum width of the figure. The layouts always set the width to be greater than or equal to the minimum
	 * width.
	 * 
	 * @return int The minimum width of the figure
	 */
	abstract public int getMinWidth();

	/**
	 * Gets the minimum height of the figure. The layouts always set the height to be greater than or equal to the
	 * minimum height.
	 * 
	 * @return int The minimum height of the figure
	 */
	abstract public int getMinHeight();

	/**
	 * Gets the maximum width of the figure. By default this is equal to the minimum width. Returning -1 indicates that
	 * the Figure does not have a maximum width and can be resized.
	 * 
	 * @return int The maximum width of the figure
	 */
	public int getMaxWidth() {
		return getMinWidth();
	}

	/**
	 * Gets the maximum height of the figure. By default this is equal to the minimum height. Returning -1 indicates
	 * that the Figure does not have a maximum height and can be resized.
	 * 
	 * @return int The maximum height of the figure
	 */
	public int getMaxHeight() {
		return getMinHeight();
	}

	/**
	 * Gets the mininum size of the widget used by scroll bar to determine it's position
	 * 
	 * @param wHint
	 *            The width hint
	 * @param hHint
	 *            The height hint
	 * @return Dimension
	 */
	public Dimension getMinimumSize(int wHint, int hHint) {
		return new Dimension(getMinWidth(), getMinHeight());
	}

	/**
	 * Gets the preferred size of the widget. Used by scroll bar to determine it's size.
	 * 
	 * @param wHint
	 *            The width hint
	 * @param hHint
	 *            The height hint
	 * @return Dimension
	 */
	public Dimension getPreferredSize(int wHint, int hHint) {
		return getMinimumSize(wHint, hHint);
	}

	/**
	 * Gets the FigureContext.
	 * 
	 * @return FigureContext The FigureContext
	 */
	public FigureContext getFigureContext() {
		return figureContext;
	}

	/**
	 * Gets the FigureConstants.
	 * 
	 * @return FigureConstants
	 */
	protected FigureConstants getFigureConstants() {
		return getFigureContext().getFigureConstants();
	}
	
	/**
	 * Method called when a Preference is changed in Eclipse. The default version
	 * does nothing.
	 */
	public void preferenceChange() {
		// Do nothing
	}
	
	/**
	 * Method called when a resource is changed in Eclipse. The default version
	 * does nothing.
	 */
	public void resourceChange() {
	}	

	/**
	 * This methods will be called by the edit part to notify the figure whenever the value of a property changes.
	 * 
	 * @param name
	 *            property name
	 * 
	 * IMPROVE GHA Performance. Refactor this concept. Actually every change will notify the figure.
	 */
	public void notifyPropertyChange(String name) {

		afterPropertyChange(name);

		// Presumably the property update changes the display of the Figure.
		// Therefore
		// We invalidate it. However since the size of the figure may change
		// this may
		// cause the parent to need resizing (and grandparent...). For the
		// moment
		// we invalidate the entire canvas.
		IFigure rootFigure = getRootFigure();
		while (rootFigure.getParent() != null) {
			rootFigure = rootFigure.getParent();
		}
		rootFigure.invalidateTree();
		rootFigure.repaint();
	}

	/**
	 * Implements the required treatment after the property have been changed.
	 * 
	 * @param name
	 *            The property name
	 */
	public void afterPropertyChange(String name) {
		// do nothing
	}

	/**
	 * Gets the root Figure. This is the one which has no parent.
	 * 
	 * @return IFiguer The root figure
	 */
	public IFigure getRootFigure() {
		IFigure f = this;
		while ((f.getParent() != null) && ! (f.getParent() instanceof org.eclipse.draw2d.Layer)) {
			f = f.getParent();
		}
		return f;
	}

	/**
	 * @return <em>True</em> if this is the root figure otherwise it return <em>False</em>
	 */
	protected boolean isRootFigure() {
		return this == getRootFigure();
	}

	/**
	 * Draws an outline around the figure. This is only drawn if we are in design mode.
	 * 
	 * @param graphics
	 *            The Graphics context
	 */
	protected void drawOutline(Graphics graphics) {
		if (getFigureContext().isDesignMode() == false) {
			// Nothing to display
			return;
		}

		setGraphicsForOutline(graphics);

		// The -1 ensures that the right and bottom lines are drawn, otherwise
		// they would fall outside the bounds of the TextField
		Rectangle b = getBounds();
		graphics.drawRectangle(new Rectangle(0, 0, b.width - 1, b.height - 1));
	}

	/**
	 * Sets the Graphics context with the values needed to draw the outline which is used for development purposes
	 * (design mode).
	 * 
	 * @param graphics
	 *            The Graphics context
	 */
	protected void setGraphicsForOutline(Graphics graphics) {
		FigureConstants fc = getFigureConstants();

		graphics.setForegroundColor(fc.getOutlineBorderForegroundColor());
		graphics.setLineStyle(fc.getOutlineBorderLineStyle());

		graphics.setLineWidth(fc.getOutlineBorderLineWidth());
	}

	/**
	 * Calculates the size that the String will take using the default font.
	 * 
	 * @param text
	 *            The String to test
	 * @return Rectangle The size of the String
	 */
	protected Rectangle calculateTextSize(String text) {
		return calculateTextSize(text, null);
	}
	
	/**
	 * calculates the size that the string will take using the given font
	 * 
	 * @param text
	 * @param font
	 * @return
	 */
	protected Rectangle calculateTextSize(String text, Font font) {
		Rectangle r = new Rectangle();
		if (font == null) {
			font = getFont();
			if (font == null)
				return r;
		}
		org.eclipse.swt.graphics.Rectangle sr = SWTUtils.calculateSize(getFont(), text);

		r.x = sr.x;
		r.y = sr.y;
		r.width = sr.width;
		r.height = sr.height;

		return r;
	}

	/**
	 * Gets the Preview Value. Returns an Empty string if the value does not exist OR we are in design mode.
	 * 
	 * @return String The preview value
	 */
	protected String getPreviewValue() {
		String s = getWidget().getPropertyValue(PropertyTypeConstants.PREVIEW_VALUE);
		if (s == null) {
			return "";
		}
		return s;
	}

	/**
	 * Returns true if the Widget figure represents a visual element. This returns true by default. It is essentially
	 * used when laying out the figures.
	 * 
	 * @return boolean True if the Widget figure represents a visual element
	 */
	public boolean isVisualElement() {
		return isVisible();
	}

	/**
	 * Gets the meta-model. Note that this should NEVER be used to update the actual model since we MUST always use
	 * Commands to do this.
	 * 
	 * @return MetaModel The MetaModel
	 */
	protected MetaModel getMetaModel() {
		return MetaModelRegistry.getMetaModel();
	}
	
	/**
	 * @return IOfsProject
	 */
	protected IOfsProject getOfsProject() {
		return ofsProject;
	}
	
	/**
	 * Creates the preview values. These are comma separated values taken from the property 'preview'.
	 * 
	 * @return List of preview values
	 */
	protected List<String> createPreviewValues() {
		return WidgetUtils.getPreviewValues(getWidget());
	}
}
