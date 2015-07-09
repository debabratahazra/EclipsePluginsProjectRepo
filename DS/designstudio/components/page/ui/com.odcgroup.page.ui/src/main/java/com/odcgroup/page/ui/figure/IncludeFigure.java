package com.odcgroup.page.ui.figure;

import java.util.Stack;

import org.apache.commons.lang.StringUtils;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Pattern;

import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.model.Model;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Widget;


/**
 * The IncludeFigure represents the inclusion of a Widget from another file. It correctly calculates its size according
 * to the size of the included figure. It does NOT paint the source figure. This is because the user shouldn't be able
 * to modify anything in the included figure.
 * 
 * @author Gary Hayes
 */
public class IncludeFigure extends AbstractTranslatedWidgetFigure implements Alignable {

    /** The background image. */
    private static final Image BACKGROUND_IMAGE = createImage("/icons/obj16/background.png");
    
    /** The background pattern. */
    private static final Pattern BACKGROUND_PATTERN = new Pattern(null, BACKGROUND_IMAGE);

    /** The source Widget Figure. */
    private IWidgetFigure sourceWidgetFigure;
    /** */
    private String uri = "";

    /**
     * Flag indicating that the source widget cannot be found. This is reset every time the incldueSrc property is
     * changed.
     */
    private boolean sourceWidgetError;

    /**
     * Creates a new IncludeFigure.
     * 
     * @param widget The Widget being displayed by this Figure
     * @param figureContext The context providing information required for displaying the page correctly
     */
    public IncludeFigure(Widget widget, FigureContext figureContext) {
        super(widget, figureContext);
    }

    /**
     * Gets the minimum width of the figure. The layouts always set the width to be greater than or equal to the minimum
     * width.
     * 
     * @return int The minimum width of the figure
     */
    public int getMinWidth() {
    	IWidgetFigure source = getSourceWidgetFigure();
        if (source != null) {
            return source.getMinWidth();
        }
        return getFigureConstants().getInvisibleWidgetSize()+8;
    }

    /**
     * Gets the minimum height of the figure. The layouts always set the height to be greater than or equal to the
     * minimum height.
     * 
     * @return int The minimum height of the figure
     */
    public int getMinHeight() {
    	IWidgetFigure source = getSourceWidgetFigure();
        if (source != null) {
            return source.getMinHeight();
        }
        return getFigureConstants().getInvisibleWidgetSize()+8;
    }

    /**
     * Gets the maximum width of the figure. By default this is equal to the minimum width.
     * 
     * @return int The maximum width of the figure
     */
    public int getMaxWidth() {
    	IWidgetFigure source = getSourceWidgetFigure();
        if (source != null) {
            return source.getMaxWidth();
        }
        return getFigureConstants().getInvisibleWidgetSize();
    }

    /**
     * Gets the maximum height of the figure. By default this is equal to the minimum height.
     * 
     * @return int The maximum height of the figure
     */
    public int getMaxHeight() {
    	IWidgetFigure source = getSourceWidgetFigure();
        if (source != null) {
            return source.getMaxHeight();
        }
        return getFigureConstants().getInvisibleWidgetSize();
    }

    /**
     * Gets the horizontal alignment.
     * 
     * @return String The horizontal alignment
     */
    public String getHorizontalAlignment() {
    	IWidgetFigure awf = getSourceWidgetFigure();
        if (awf != null && awf instanceof Alignable) {
            Alignable a = (Alignable) awf;
            String s = a.getHorizontalAlignment();
            if (StringUtils.isNotEmpty(s)) {
                return s;
            }
        }

        // Default value
        return Alignable.LEAD;
    }

    /**
     * Gets the vertical alignment.
     * 
     * @return String The vertical alignment
     */
    public String getVerticalAlignment() {
        IWidgetFigure awf = getSourceWidgetFigure();
        if (awf != null && awf instanceof Alignable) {
            Alignable a = (Alignable) awf;
            String s = a.getVerticalAlignment();
            if (StringUtils.isNotEmpty(s)) {
                return s;
            }
        }

        // Default value
        return Alignable.CENTER;
    }

    /**
     * Paints the specific figure. The Graphics context has already been translated to the origin of the figure.
     * Subclasses need to override this method.
     * 
     * @param graphics The Graphics context
     */
    protected void paintSpecificFigure(Graphics graphics) {
    	IWidgetFigure figure = getSourceWidgetFigure();
        if (figure != null) {
        	
            Rectangle b = getBounds();
            Rectangle sb = new Rectangle(0, 0, b.width, b.height);

            // Since the source widget is not a child of the IncludeFigure it does
            // not get laid out. Therefore we need to ensure that it is laid out correctly
            figure.setBounds(sb);
            layout(figure);

            if (getFigureContext().isDesignMode()) {
                graphics.setBackgroundPattern(BACKGROUND_PATTERN);
            } else {
            	graphics.setBackgroundColor(ColorConstants.white);
            }
            figure.paint(graphics);
        } else {
            // We are unable to determine the size of the source figure
            FigureConstants fc = getFigureConstants();
            graphics.setForegroundColor(fc.getHiddenFeaturesColor());
            // 2, -3 is used to centre the text
            graphics.drawText("[I]", 0, 0);//, new Point(2, -3));
        }
    }

    /**
     * Lays out the figure and all the child figures using their LayoutManager.
     * 
     * @param figure The figure to layout
     */
    private void layout(IFigure figure) {
        // Layout this figure
        if (figure.getLayoutManager() != null) {
            figure.getLayoutManager().layout(figure);
        }
        // Layout the child figures
        for (int i = 0; i < figure.getChildren().size(); ++i) {
            IFigure f = (IFigure) figure.getChildren().get(i);
            layout(f);
        }
    }

    /**
     * Gets the inclusion type.
     * 
     * @return String The inclusion type
     */
    public String getIncludeType() {
        return getString(PropertyTypeConstants.INCLUDE_TYPE);
    }

    /**
     * Gets the <code>IWidgetFigure</code> associated with the Widget contained within the source file.
     * 
     * @return The <code>IWidgetFigure</code> associated with the Widget contained within the source
     *         file.
     */
    private IWidgetFigure getSourceWidgetFigure() {
        if (sourceWidgetError == true) {
            return null;
        }

        if (sourceWidgetFigure == null) {
            try {
                // Now find the associated Figure
          		//EcoreUtil.resolveAll(getWidget());
            	Property include = getWidget().findProperty(PropertyTypeConstants.INCLUDE_SOURCE);
            	if (include != null) {
	              	Model model = include.getModel();
	              	if (model != null && model.getWidget() != null) {
              			createSourceWidgetFigure(model.getWidget());
	                    sourceWidgetError = false;
	              	}
            	} else {
            		sourceWidgetError = false;
            	}
            } catch (Exception e) {
                // Ignore since this may happen a lot
                sourceWidgetError = true;
            }
        }
        return sourceWidgetFigure;
    }
    
   public void dispose() {
	   if (sourceWidgetFigure != null) {
		   // dispose subtree of figures. Ensure all GDI resources are disposed.
		   Stack<IWidgetFigure> figures = new Stack<IWidgetFigure>();
		   figures.push(sourceWidgetFigure);
		   while (!figures.isEmpty()) {
			   IWidgetFigure fig = figures.pop();
			   for (IWidgetFigure child : fig.getAllChildren()) {
				   figures.push(child);
			   }
			   fig.dispose();
		   }
	   }
	   super.dispose();
   }

    /**
     * Creates the source widget figure.
     * 
     * @param sourceWidget The source widget to create the figure from
     */
    private void createSourceWidgetFigure(Widget sourceWidget) {

    	
        // Now find the associated Figure
        FigureContext fc = getFigureContext();
        FigureFactory ff = new FigureFactory(fc.isDesignMode(), fc.getProject());
        
        sourceWidgetFigure = (IWidgetFigure) ff.createTree(sourceWidget);

        // Don't display the Fragment
        if (sourceWidgetFigure instanceof Fragment) {
            if (sourceWidgetFigure.getChildren().size() > 0) {
                sourceWidgetFigure = (IWidgetFigure) sourceWidgetFigure.getChildren().get(0);
            }
        }

        // It should be possible to add the sourceWidgetFigure to this Figure.
        // In this case the sourceWidgetFigure would by set up correctly.
        // Unfortunately at the moment this does not work (an empty Figure is displayed).
        // I therefore set the Font manually
        sourceWidgetFigure.setFont(getFont());
    }

    /**
     * Called when a property is changed.
     * 
     * @param name The name of the property
     */
    public void afterPropertyChange(String name) {
        if (name.equals(PropertyTypeConstants.INCLUDE_SOURCE)) {
        	Property prop = getWidget().findProperty(PropertyTypeConstants.INCLUDE_SOURCE);
        	URI mUri = prop.getModelURI();
        	if (mUri != null) {
	        	String modelURI = mUri.toString();
	        	if (!uri.equals(modelURI)) {
	        		// the URI has changed, so we remove the underlying figure to recreate it
	        		uri = modelURI;
	                removeSourceFigure();
	        	}
        	}
        }
    }

    /**
     * Method called when a resource is changed in Eclipse. The default version does nothing.
     */
    public void resourceChange() {
        removeSourceFigure();
        getRootFigure().invalidateTree();
    }

    /**
     * Sets the information related to the source figure to null.
     */
    private void removeSourceFigure() {
        sourceWidgetFigure = null;
        sourceWidgetError = false;
    }
}
