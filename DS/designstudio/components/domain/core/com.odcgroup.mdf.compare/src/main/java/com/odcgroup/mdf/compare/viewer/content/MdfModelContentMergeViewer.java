package com.odcgroup.mdf.compare.viewer.content;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.compare.CompareConfiguration;
import org.eclipse.emf.compare.diff.metamodel.DiffElement;
import org.eclipse.emf.compare.diff.metamodel.DiffGroup;
import org.eclipse.emf.compare.diff.metamodel.ModelElementChangeLeftTarget;
import org.eclipse.emf.compare.diff.metamodel.ModelElementChangeRightTarget;
import org.eclipse.emf.compare.ui.ModelCompareInput;
import org.eclipse.emf.compare.ui.util.EMFCompareConstants;
import org.eclipse.emf.compare.ui.viewer.content.ModelContentMergeViewer;
import org.eclipse.emf.compare.ui.viewer.content.part.AbstractCenterPart;
import org.eclipse.emf.compare.ui.viewer.content.part.ModelContentMergeTabItem;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

import com.odcgroup.mdf.compare.viewer.content.part.MdfModelContentMergeTabFolder;
import com.odcgroup.workbench.compare.viewer.content.BaseModelContentMergeViewer;

/**
 *
 * @author pkk
 */
public class MdfModelContentMergeViewer extends BaseModelContentMergeViewer {
	
	/**
	 * this is the "center" part of the content merge viewer where we handle all the drawing operations.
	 */
	private AbstractCenterPart canvas;


	/**
	 * @param parent
	 * @param config
	 */
	public MdfModelContentMergeViewer(Composite parent, CompareConfiguration config) {
		super(parent, config);
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.compare.viewer.content.BaseModelContentMergeViewer#createCompareViewerParts(org.eclipse.swt.widgets.Composite)
	 */	
	protected void createCompareViewerParts(Composite composite) {
		leftPart = new MdfModelContentMergeTabFolder(this, composite, EMFCompareConstants.LEFT);
		rightPart = new MdfModelContentMergeTabFolder(this, composite, EMFCompareConstants.RIGHT);
		ancestorPart = new MdfModelContentMergeTabFolder(this, composite, EMFCompareConstants.ANCESTOR);		
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.emf.compare.ui.viewer.content.ModelContentMergeViewer#copy(boolean)
	 */
	protected void copy(boolean leftToRight) {
		if (((ModelCompareInput)getInput()).getDiffAsList().size() > 0) {
			List<DiffElement> diffs = new ArrayList<DiffElement>();
			diffs.addAll(((ModelCompareInput)getInput()).getDiffAsList());
			for (DiffElement diffElement : ((ModelCompareInput)getInput()).getDiffAsList()) {
				if (!leftToRight && (diffElement instanceof ModelElementChangeLeftTarget)) {
					diffs.remove(diffElement);
				}
				if (leftToRight && (diffElement instanceof ModelElementChangeRightTarget)) {
					diffs.remove(diffElement);					
				}
			}
			copy(diffs, leftToRight);
		}
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.emf.compare.ui.viewer.content.ModelContentMergeViewer#getCenterPart()
	 */
	public Canvas getCenterPart() {
		if (canvas == null && !getControl().isDisposed()) {
			canvas = new AbstractCenterPart((Composite)getControl()) {
				@Override
				public void doPaint(GC gc) {
					if (!ModelContentMergeViewer.shouldDrawDiffMarkers() || getInput() == null)
						return;
					if (getInput() instanceof ModelCompareInput) {						
						// incase of diff more than 100, dont draw the lines
						// to avoid too long drawing times
						ModelCompareInput cInput = (ModelCompareInput) getInput();
						int diffSize = cInput.getDiffAsList().size();
						if (diffSize > 100) {
							return;
						}
					}
					final List<ModelContentMergeTabItem> leftVisible = leftPart.getVisibleElements();
					final List<ModelContentMergeTabItem> rightVisible = rightPart.getVisibleElements();
					final List<DiffElement> visibleDiffs = retainVisibleDiffs(leftVisible, rightVisible);
					// we don't clear selection when the last diff is merged so this could happen
					if (currentSelection.size() > 0 && currentSelection.get(0).eContainer() != null) {
						visibleDiffs.addAll(currentSelection);
					}
					for (final DiffElement diff : visibleDiffs) {
						if (!(diff instanceof DiffGroup)) {
							final ModelContentMergeTabItem leftUIItem = leftPart.getUIItem(diff);
							final ModelContentMergeTabItem rightUIItem = rightPart.getUIItem(diff);
							if (leftUIItem != null && rightUIItem != null) {
								drawLine(gc, leftUIItem, rightUIItem);
							}
						}
					}
				}
			};
		}
		if (canvas != null) {
			canvas.moveAbove(null);
		}
		return canvas;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.emf.compare.ui.viewer.content.ModelContentMergeViewer#handleDispose(org.eclipse.swt.events.DisposeEvent)
	 */
	protected void handleDispose(DisposeEvent event) {
		try {
			super.handleDispose(event);
		} catch(Exception e) {
			// suppress any runtime exceptions here
		}
		canvas.dispose();
		canvas = null;
	}

}
