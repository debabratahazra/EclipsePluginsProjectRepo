package com.odcgroup.pageflow.editor.diagram.custom.figures;

import java.net.URL;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.gmf.runtime.draw2d.ui.render.factory.RenderedImageFactory;
import org.eclipse.gmf.runtime.draw2d.ui.render.figures.ScalableImageFigure;

import com.odcgroup.pageflow.editor.diagram.part.PageflowDiagramEditorPlugin;

public class EndStateSVGFigure extends ScalableImageFigure {
	
	private static final String decisionSvgPath = "icons/svg/endState.svg";
	private static final URL url = FileLocator.find(PageflowDiagramEditorPlugin.getInstance().getBundle(), new Path(decisionSvgPath), null);

	/**
	 * 
	 */
	public EndStateSVGFigure() {
		super(RenderedImageFactory.getInstance(url), true, true, true);
	}

}
