package com.odcgroup.pageflow.editor.diagram.custom.parts;

import org.eclipse.gef.EditDomain;
import org.eclipse.gef.KeyHandler;
import org.eclipse.gef.Tool;
import org.eclipse.gef.palette.ToolEntry;
import org.eclipse.gef.ui.palette.PaletteViewer;
import org.eclipse.gef.ui.palette.PaletteViewerProvider;
import org.eclipse.gmf.runtime.diagram.ui.internal.parts.PaletteToolTransferDragSourceListener;
import org.eclipse.gmf.runtime.diagram.ui.parts.DiagramEditDomain;
import org.eclipse.gmf.runtime.diagram.ui.parts.IDiagramGraphicalViewer;
import org.eclipse.gmf.runtime.diagram.ui.tools.ConnectionCreationTool;
import org.eclipse.gmf.runtime.diagram.ui.tools.CreationTool;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IFileEditorInput;

import com.odcgroup.pageflow.editor.diagram.custom.palette.ILockableToolEntry;

/**
 * copied from org.eclipse.gmf.runtime.diagram.ui.parts.DiagramEditorWithFlyOutPalette
 *
 */
@SuppressWarnings("restriction")
public class PageflowPaletteViewerProvider extends PaletteViewerProvider {
	
	/** Key Handler for the palette */
	private KeyHandler paletteKeyHandler = null;

	/** Mouse listener for the palette */
	private MouseListener paletteMouseListener = null;
	
	private IDiagramGraphicalViewer diagramGraphicalViewer = null;
	
	private EditDomain graphicalViewerDomain = null;

	/**
	 * @param graphicalViewerDomain
	 * @param diagramGraphicalViewer
	 */
	public PageflowPaletteViewerProvider(EditDomain graphicalViewerDomain, IDiagramGraphicalViewer diagramGraphicalViewer) {
		super(graphicalViewerDomain);
		this.graphicalViewerDomain = graphicalViewerDomain;
		this.diagramGraphicalViewer = diagramGraphicalViewer;
	}
	
	/**
	 * @return
	 */
	private PaletteViewer getPaletteViewer() {
		return getEditDomain().getPaletteViewer();
	}
	
	/**
	 * Override to provide the additional behavior for the tools.
	 * Will intialize with a PaletteEditPartFactory that has a TrackDragger that
	 * understand how to handle the mouseDoubleClick event for shape creation tools.
	 * Also will initialize the palette with a defaultTool that is the SelectToolEx that undestands
	 * how to handle the enter key which will result in the creation of the shape also.
	 */
	protected void configurePaletteViewer(PaletteViewer viewer) {
		super.configurePaletteViewer(viewer);
		// DS-3141 - protect palette for standard models in custom mode
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

		viewer.getKeyHandler().setParent(getPaletteKeyHandler());
		viewer.getControl().addMouseListener(getPaletteMouseListener());

		// Add a transfer drag target listener that is supported on
		// palette template entries whose template is a creation tool.
		// This will enable drag and drop of the palette shape creation
		// tools.
		viewer
			.addDragSourceListener(new PaletteToolTransferDragSourceListener(
				viewer));

	}


	/**
	 * @return Palette Key Handler for the palette
	 */
	private KeyHandler getPaletteKeyHandler() {

		if (paletteKeyHandler == null) {

			paletteKeyHandler = new KeyHandler() {

				/**
				 * Processes a <i>key released </i> event. This method
				 * is called by the Tool whenever a key is released, and
				 * the Tool is in the proper state. Override to support
				 * pressing the enter key to create a shape or connection
				 * (between two selected shapes)
				 *
				 * @param event
				 *            the KeyEvent
				 * @return <code>true</code> if KeyEvent was handled
				 *         in some way
				 */
				public boolean keyReleased(KeyEvent event) {

					if (event.keyCode == SWT.Selection) {

						Tool tool =
							getPaletteViewer()
								.getActiveTool()
								.createTool();

						if (tool instanceof CreationTool
							|| tool instanceof ConnectionCreationTool) {

							tool.keyUp(event, getDiagramGraphicalViewer());

							// 	deactivate current selection
							getPaletteViewer().setActiveTool(null);

							return true;
						}

					}
					return super.keyReleased(event);
				}

			};

		}
		return paletteKeyHandler;
	}

	/**
	 * @return Palette Mouse listener for the palette
	 */
	private MouseListener getPaletteMouseListener() {
		if (paletteMouseListener == null) {
			paletteMouseListener = new MouseListener() {
				/**
				 * Flag to indicate that the current active tool should
				 * be cleared after a mouse double-click event.
				 */
				private boolean clearActiveTool = false;

				/**
				 * Override to support double-clicking a palette tool
				 * entry to create a shape or connection (between two
				 * selected shapes).
				 *
				 * @see org.eclipse.swt.events.MouseListener#mouseDoubleClick(org.eclipse.swt.events.MouseEvent)
				 */
				public void mouseDoubleClick(MouseEvent e) {
					Tool tool = getPaletteViewer().getActiveTool()
						.createTool();

					if (tool instanceof CreationTool
						|| tool instanceof ConnectionCreationTool) {

						tool.setViewer(getDiagramGraphicalViewer());
						tool.setEditDomain(getDiagramGraphicalViewer()
							.getEditDomain());
						tool.mouseDoubleClick(e,
							getDiagramGraphicalViewer());

						// Current active tool should be deactivated,
						// but if it is down here it will get
						// reactivated deep in GEF palette code after
						// receiving mouse up events.
						clearActiveTool = true;
					}
				}

				public void mouseDown(MouseEvent e) {
					// do nothing
				}

				/* (non-Javadoc)
				 * @see org.eclipse.swt.events.MouseListener#mouseUp(org.eclipse.swt.events.MouseEvent)
				 * 
				 * OFSFMK-217 Add the possibility to "lock" palette item
				 * 
				 * set lock to the active tool item holding SHIFT
				 */
				public void mouseUp(MouseEvent e) {
					int modifiers = e.stateMask;
					ToolEntry tool = getPaletteViewer().getActiveTool();
					if ((modifiers & SWT.SHIFT) != 0){	
						// lock the toolentry when selected holding SHIFT
						setToolEntryLock(tool, true);
					} else if (clearActiveTool) {	
						// 	Deactivate current active tool here if a
						// double-click was handled.					
						setToolEntryLock(tool, false);
						getPaletteViewer().setActiveTool(null);
						clearActiveTool = false;
					} else {				
						// reset lock
						setToolEntryLock(tool, false);
					}

				}
			};

		}
		return paletteMouseListener;
	}
	
	/**
	 * @param toolEntry
	 * @param lock
	 */
	private void setToolEntryLock(ToolEntry toolEntry, boolean lock){
		if (toolEntry instanceof ILockableToolEntry) {
			ILockableToolEntry entry = (ILockableToolEntry)toolEntry;
			entry.setLockable(lock);
		}
	}

	/**
	 * @return
	 */
	public IDiagramGraphicalViewer getDiagramGraphicalViewer() {
		return diagramGraphicalViewer;
	}

}
