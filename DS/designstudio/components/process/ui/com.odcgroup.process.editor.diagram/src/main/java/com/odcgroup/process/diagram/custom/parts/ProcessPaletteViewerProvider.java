package com.odcgroup.process.diagram.custom.parts;

import org.eclipse.gef.EditDomain;
import org.eclipse.gef.ui.palette.PaletteViewer;
import org.eclipse.gef.ui.palette.PaletteViewerProvider;
import org.eclipse.gmf.runtime.diagram.ui.parts.DiagramEditDomain;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IFileEditorInput;

/**
 * DS-3141
 * @author pkk
 *
 */
public class ProcessPaletteViewerProvider extends PaletteViewerProvider{
	
	private EditDomain graphicalViewerDomain = null;

	/**
	 * @param graphicalViewerDomain
	 */
	public ProcessPaletteViewerProvider(EditDomain graphicalViewerDomain) {
		super(graphicalViewerDomain);
		this.graphicalViewerDomain = graphicalViewerDomain;
	}
	
	/**
	 * (non-Javadoc)
	 * @see org.eclipse.gef.ui.palette.PaletteViewerProvider#configurePaletteViewer(org.eclipse.gef.ui.palette.PaletteViewer)
	 */
	protected void configurePaletteViewer(PaletteViewer viewer) {
		super.configurePaletteViewer(viewer);
		// disable the palette incase of editorInput not a file based one
		// in simple, read-only mode
		if (graphicalViewerDomain instanceof DiagramEditDomain) {
			DiagramEditDomain editDomain = (DiagramEditDomain) graphicalViewerDomain;
			IEditorInput editInput = editDomain.getEditorPart().getEditorInput();
			if (!(editInput instanceof IFileEditorInput)) {
				viewer.getControl().setEnabled(false);
				return;
			} else {
				viewer.getControl().setEnabled(true);				
			}
		}
	}

}
