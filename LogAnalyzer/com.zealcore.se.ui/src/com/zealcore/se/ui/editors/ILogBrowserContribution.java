package com.zealcore.se.ui.editors;

import java.util.List;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;

public interface ILogBrowserContribution {

    void init(ILogsetBrowser view);

    void dispose();

    /**
     * Gets contributed elements valid for the figure. Use the ruler to convert
     * the world coordinates to screen coordinates. This is not done by the
     * rendering pipeline as it does not support long values.
     * 
     * @param figure
     *                the figure
     * @param ruler
     *                the ruler
     * 
     * @return the contributed elements
     */
    List<IFigure> getElements(IRuler ruler, IFigure figure);

    /**
     * Allows the contributor to decorate figures. This pass is done after all
     * elements are contributed. No new figures are allowed.
     * 
     * @param graphics
     *                the graphics onto which to draw decorations
     * @param figure
     *                the figure
     */
    void decorateFigures(Graphics graphics, IFigure figure);
}
