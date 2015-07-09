package com.odcgroup.pageflow.editor.diagram.custom.figures;

import java.net.URL;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.gmf.runtime.draw2d.ui.render.factory.RenderedImageFactory;
import org.eclipse.gmf.runtime.draw2d.ui.render.figures.ScalableImageFigure;

import com.odcgroup.pageflow.editor.diagram.part.PageflowDiagramEditorPlugin;

public class DecisionStateSVGFigure extends ScalableImageFigure {
	
	private static final String decisionSvgPath = "icons/svg/decision.svg";
	private static final URL url = FileLocator.find(PageflowDiagramEditorPlugin.getInstance().getBundle(), new Path(decisionSvgPath), null);

	/**
	 * 
	 */
	public DecisionStateSVGFigure() {
		super(RenderedImageFactory.getInstance(url), true, true, true);
	}

}
