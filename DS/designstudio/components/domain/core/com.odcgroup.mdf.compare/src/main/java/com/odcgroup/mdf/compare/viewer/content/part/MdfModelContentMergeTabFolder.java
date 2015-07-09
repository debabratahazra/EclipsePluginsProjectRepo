package com.odcgroup.mdf.compare.viewer.content.part;

import org.eclipse.emf.compare.ui.util.EMFCompareConstants;
import org.eclipse.emf.compare.ui.viewer.content.ModelContentMergeViewer;
import org.eclipse.emf.compare.ui.viewer.content.part.ModelContentMergeTabFolder;
import org.eclipse.emf.compare.ui.viewer.content.part.ModelContentMergeTabItem;
import org.eclipse.emf.compare.ui.viewer.content.part.diff.ModelContentMergeDiffTab;
import org.eclipse.emf.compare.util.AdapterUtils;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.TreeItem;

import com.odcgroup.mdf.editor.ui.editors.MdfAdapterFactoryContentProvider;
import com.odcgroup.mdf.editor.ui.sorters.MdfElementSorter;

/**
 *
 * @author pkk
 *
 */
public class MdfModelContentMergeTabFolder extends ModelContentMergeTabFolder {
	
	private ModelContentMergeDiffTab viewer = null;

	/**
	 * @param viewer
	 * @param composite
	 * @param side
	 */
	public MdfModelContentMergeTabFolder(ModelContentMergeViewer viewer, Composite composite, int side) {
		super(viewer, composite, side);
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.emf.compare.ui.viewer.content.part.ModelContentMergeTabFolder#createContents(org.eclipse.swt.widgets.Composite)
	 */
	protected void createContents(Composite composite) {
		super.createContents(composite);
		if (tree instanceof ModelContentMergeDiffTab) {
			viewer = (ModelContentMergeDiffTab) tree;
			reconfigureListeners();
			viewer.setContentProvider(new MdfAdapterFactoryContentProvider(AdapterUtils.getAdapterFactory()));
			/*
            ILabelProvider labelProvider = new AdapterFactoryLabelProvider(AdapterUtils.getAdapterFactory());
            ILabelDecorator decorator = MdfPlugin.getDefault().getWorkbench().getDecoratorManager().getLabelDecorator();
            viewer.setLabelProvider(new MdfDecoratingLabelProvider(labelProvider, decorator));
            */
            viewer.setSorter(new MdfElementSorter());
		}
	}
	
	/**
	 * @param viewer
	 */
	private void reconfigureListeners() {
		if (viewer != null) {
			Listener[] listeners = viewer.getTree().getListeners(SWT.Paint);
			for (Listener listener : listeners) {
				viewer.getTree().removeListener(SWT.Paint, listener);
			}
			viewer.getTree().addPaintListener(new TreePaintListener());	
			viewer.getTree().addListener(SWT.EraseItem, new Listener() {
				public void handleEvent(Event event) {
					if (getDiffAsList().size()>100) {
				        event.detail &= ~SWT.HOT;
				        if ((event.detail & SWT.SELECTED) == 0) return; /// item not selected
	
				        TreeItem item =(TreeItem)event.item;
				        Rectangle bounds = item.getBounds();
	
				        GC gc = event.gc;                               
				        Color oldForeground = gc.getForeground();
				        Color oldBackground = gc.getBackground();
				        
				    	Color gray = Display.getCurrent().getSystemColor(SWT.COLOR_DARK_GRAY);
				    	Color white = Display.getCurrent().getSystemColor(SWT.COLOR_WHITE);
				    	
				        gc.setBackground(gray);
				        gc.setForeground(white); 
				        
				        gc.fillRoundRectangle(bounds.x, bounds.y, bounds.width, bounds.height, 5,5);
	
				        gc.setForeground(oldForeground);
				        gc.setBackground(oldBackground);
				        event.detail &= ~SWT.SELECTED;
					}
				}

			});
		}
	}
	
	/**
	 *
	 */
	class TreePaintListener implements PaintListener {
		
		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.swt.events.PaintListener#paintControl(org.eclipse.swt.events.PaintEvent)
		 */
		public void paintControl(PaintEvent event) {
			if (getDiffAsList().size()>100) {
				return;
			}
			if (ModelContentMergeViewer.shouldDrawDiffMarkers()) {
				for (final ModelContentMergeTabItem item : getVisibleElements()) {
					drawRectangle(event.gc, item);
				}
			}
		}

		/**
		 * Handles the drawing itself.
		 * 
		 * @param gc
		 *            {@link GC} on which paint operations will take place.
		 * @param item
		 *            Item that need to be circled and connected to the center part.
		 */
		private void drawRectangle(GC gc, ModelContentMergeTabItem item) {
			if (viewer == null) return;
			final Rectangle treeBounds = viewer.getTree().getClientArea();
			final Rectangle treeItemBounds = ((TreeItem)item.getVisibleItem()).getBounds();

			// Defines the circling Color
			final RGB color = ModelContentMergeViewer.getColor(item.getCurveColor());

			// We add a margin before the rectangle to circle the "+" as well as
			// the tree line.
			final int margin = 60;

			// Defines all variables needed for drawing the rectangle.
			final int rectangleX = treeItemBounds.x - margin;
			final int rectangleY = treeItemBounds.y;
			final int rectangleWidth = treeItemBounds.width + margin;
			final int rectangleHeight = treeItemBounds.height - 1;
			final int rectangleArcWidth = 5;
			final int rectangleArcHeight = 5;

			// Performs the actual drawing
			gc.setLineWidth(item.getCurveSize());
			gc.setForeground(new Color(viewer.getTree().getDisplay(), color));
			if (partSide == EMFCompareConstants.LEFT) {
				if (item.getCurveY() != treeItemBounds.y + treeItemBounds.height / 2) {
					gc.setLineStyle(SWT.LINE_SOLID);
					gc.drawLine(rectangleX, item.getCurveY(), treeBounds.width + treeBounds.x, item
							.getCurveY());
				} else {
					gc.setLineStyle(SWT.LINE_DASHDOT);
					gc.drawRoundRectangle(rectangleX, rectangleY, rectangleWidth, rectangleHeight,
							rectangleArcWidth, rectangleArcHeight);
					gc.setLineStyle(SWT.LINE_SOLID);
					gc.drawLine(rectangleX + rectangleWidth, item.getCurveY(), treeBounds.width
							+ treeBounds.x, item.getCurveY());
				}
			} else if (partSide == EMFCompareConstants.RIGHT) {
				if (item.getCurveY() != treeItemBounds.y + treeItemBounds.height / 2) {
					gc.setLineStyle(SWT.LINE_SOLID);
					gc
							.drawLine(rectangleX + rectangleWidth, item.getCurveY(), treeBounds.x, item
									.getCurveY());
				} else {
					gc.setLineStyle(SWT.LINE_DASHDOT);
					gc.drawRoundRectangle(rectangleX, rectangleY, rectangleWidth, rectangleHeight,
							rectangleArcWidth, rectangleArcHeight);
					gc.setLineStyle(SWT.LINE_SOLID);
					gc.drawLine(rectangleX, item.getCurveY(), treeBounds.x, item.getCurveY());
				}
			} else {
				if (item.getCurveY() != treeItemBounds.y + treeItemBounds.height / 2) {
					gc.setLineStyle(SWT.LINE_SOLID);
					gc.drawLine(rectangleX + rectangleWidth, item.getCurveY(), rectangleX, item.getCurveY());
				} else {
					gc.setLineStyle(SWT.LINE_DASHDOT);
					gc.drawRoundRectangle(rectangleX, rectangleY, rectangleWidth, rectangleHeight,
							rectangleArcWidth, rectangleArcHeight);
				}
			}
		}
	}

}
