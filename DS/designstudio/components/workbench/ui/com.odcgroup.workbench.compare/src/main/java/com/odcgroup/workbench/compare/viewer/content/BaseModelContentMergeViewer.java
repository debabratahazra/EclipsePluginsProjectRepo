package com.odcgroup.workbench.compare.viewer.content;

import org.eclipse.compare.CompareConfiguration;
import org.eclipse.emf.compare.ui.EMFCompareUIMessages;
import org.eclipse.emf.compare.ui.ICompareEditorPartListener;
import org.eclipse.emf.compare.ui.util.EMFCompareConstants;
import org.eclipse.emf.compare.ui.viewer.content.ModelContentMergeViewer;
import org.eclipse.emf.compare.ui.viewer.content.part.IModelContentMergeViewerTab;
import org.eclipse.emf.compare.ui.viewer.content.part.ModelContentMergeTabFolder;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.ScrollBar;
import org.eclipse.swt.widgets.Scrollable;


/**
 *
 * @author pkk
 */
public abstract class BaseModelContentMergeViewer extends ModelContentMergeViewer {
	
	/** This listener will be registered for notifications against all tab folders. */
	private EditorPartListener partListener;

	/**
	 * @param parent
	 * @param config
	 */
	public BaseModelContentMergeViewer(Composite parent, CompareConfiguration config) {
		super(parent, config);
	}	
	
	/* (non-Javadoc)
	 * @see org.eclipse.emf.compare.ui.viewer.content.ModelContentMergeViewer#createControls(org.eclipse.swt.widgets.Composite)
	 */
	protected void createControls(Composite composite) {
		
		createCompareViewerParts(composite);
		
		if (leftPart == null)
			leftPart = new ModelContentMergeTabFolder(this, composite, EMFCompareConstants.LEFT);
		if (rightPart == null)
			rightPart = new ModelContentMergeTabFolder(this, composite, EMFCompareConstants.RIGHT);
		if (ancestorPart == null)
			ancestorPart = new ModelContentMergeTabFolder(this, composite, EMFCompareConstants.ANCESTOR);

		partListener = new EditorPartListener(leftPart, rightPart, ancestorPart);
		leftPart.addCompareEditorPartListener(partListener);
		rightPart.addCompareEditorPartListener(partListener);
		ancestorPart.addCompareEditorPartListener(partListener);

		createPropertiesSyncHandlers(leftPart, rightPart, ancestorPart);
		createTreeSyncHandlers(leftPart, rightPart, ancestorPart);
	}
	
	/**
	 * @param composite
	 */
	protected abstract void createCompareViewerParts(Composite composite);	
	
	/**
	 * Takes care of the creation of the synchronization handlers for the properties tab of our viewer parts.
	 * 
	 * @param parts
	 *            The other parts to synchronize with.
	 */
	private void createPropertiesSyncHandlers(ModelContentMergeTabFolder... parts) {
		if (parts.length < 2) {
			throw new IllegalArgumentException(EMFCompareUIMessages
					.getString("ModelContentMergeViewer.illegalSync")); //$NON-NLS-1$
		}

		// horizontal synchronization
		handleHSync(leftPart.getPropertyPart(), rightPart.getPropertyPart(), ancestorPart.getPropertyPart());
		handleHSync(ancestorPart.getPropertyPart(), rightPart.getPropertyPart(), leftPart.getPropertyPart());
		handleHSync(rightPart.getPropertyPart(), leftPart.getPropertyPart(), ancestorPart.getPropertyPart());
		// Vertical synchronization
		handleVSync(leftPart.getPropertyPart(), rightPart.getPropertyPart(), ancestorPart.getPropertyPart());
		handleVSync(rightPart.getPropertyPart(), leftPart.getPropertyPart(), ancestorPart.getPropertyPart());
		handleVSync(ancestorPart.getPropertyPart(), rightPart.getPropertyPart(), leftPart.getPropertyPart());
	}

	/**
	 * Takes care of the creation of the synchronization handlers for the tree tab of our viewer parts.
	 * 
	 * @param parts
	 *            The other parts to synchronize with.
	 */
	private void createTreeSyncHandlers(ModelContentMergeTabFolder... parts) {
		if (parts.length < 2) {
			throw new IllegalArgumentException(EMFCompareUIMessages
					.getString("ModelContentMergeViewer.illegalSync")); //$NON-NLS-1$
		}

		handleHSync(leftPart.getTreePart(), rightPart.getTreePart(), ancestorPart.getTreePart());
		handleHSync(rightPart.getTreePart(), leftPart.getTreePart(), ancestorPart.getTreePart());
		handleHSync(ancestorPart.getTreePart(), rightPart.getTreePart(), leftPart.getTreePart());
	}
	
	/**
	 * Allows synchronization of the properties viewports horizontal scrolling.
	 * 
	 * @param parts
	 *            The other parts to synchronize with.
	 */
	private void handleHSync(IModelContentMergeViewerTab... parts) {
		// inspired from TreeMergeViewer#hsynchViewport
		final Scrollable scroll1 = (Scrollable)parts[0].getControl();
		final Scrollable scroll2 = (Scrollable)parts[1].getControl();
		final Scrollable scroll3;
		if (parts.length > 2) {
			scroll3 = (Scrollable)parts[2].getControl();
		} else {
			scroll3 = null;
		}
		final ScrollBar scrollBar1 = scroll1.getHorizontalBar();

		scrollBar1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				final int max = scrollBar1.getMaximum() - scrollBar1.getThumb();
				double v = 0.0;
				if (max > 0) {
					v = (double)scrollBar1.getSelection() / (double)max;
				}
				if (scroll2.isVisible()) {
					final ScrollBar scrollBar2 = scroll2.getHorizontalBar();
					scrollBar2.setSelection((int)((scrollBar2.getMaximum() - scrollBar2.getThumb()) * v));
				}
				if (scroll3 != null && scroll3.isVisible()) {
					final ScrollBar scrollBar3 = scroll3.getHorizontalBar();
					scrollBar3.setSelection((int)((scrollBar3.getMaximum() - scrollBar3.getThumb()) * v));
				}
				if ("carbon".equals(SWT.getPlatform()) && getControl() != null //$NON-NLS-1$
						&& !getControl().isDisposed()) {
					getControl().getDisplay().update();
				}
			}
		});
	}

	/**
	 * Allows synchronization of the viewports vertical scrolling.
	 * 
	 * @param parts
	 *            The other parts to synchronize with.
	 */
	private void handleVSync(IModelContentMergeViewerTab... parts) {
		// inspired from TreeMergeViewer#hsynchViewport
		final Scrollable table1 = (Scrollable)parts[0].getControl();
		final Scrollable table2 = (Scrollable)parts[1].getControl();
		final Scrollable table3;
		if (parts.length > 2) {
			table3 = (Scrollable)parts[2].getControl();
		} else {
			table3 = null;
		}
		final ScrollBar scrollBar1 = table1.getVerticalBar();

		scrollBar1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				final int max = scrollBar1.getMaximum() - scrollBar1.getThumb();
				double v = 0.0;
				if (max > 0) {
					v = (double)scrollBar1.getSelection() / (double)max;
				}
				if (table2.isVisible()) {
					final ScrollBar scrollBar2 = table2.getVerticalBar();
					scrollBar2.setSelection((int)((scrollBar2.getMaximum() - scrollBar2.getThumb()) * v));
				}
				if (table3 != null && table3.isVisible()) {
					final ScrollBar scrollBar3 = table3.getVerticalBar();
					scrollBar3.setSelection((int)((scrollBar3.getMaximum() - scrollBar3.getThumb()) * v));
				}
				if ("carbon".equals(SWT.getPlatform()) && getControl() != null //$NON-NLS-1$
						&& !getControl().isDisposed()) {
					getControl().getDisplay().update();
				}
			}
		});
	}
	
	/**
	 * Basic implementation of an {@link ICompareEditorPartListener}.
	 */
	private class EditorPartListener implements ICompareEditorPartListener {
		/** Viewer parts this listener is registered for. */
		private final ModelContentMergeTabFolder[] viewerParts;

		/**
		 * Instantiate this {@link EditorPartListener} given the left, right and ancestor viewer parts.
		 * 
		 * @param parts
		 *            The viewer parts.
		 */
		public EditorPartListener(ModelContentMergeTabFolder... parts) {
			viewerParts = parts;
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see ICompareEditorPartListener#selectedTabChanged()
		 */
		public void selectedTabChanged(int newIndex) {
			for (int i = 0; i < viewerParts.length; i++) {
				viewerParts[i].setSelectedTab(newIndex);
			}
			updateCenter();
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see ICompareEditorPartListener#selectionChanged()
		 */
		public void selectionChanged(SelectionChangedEvent event) {
			fireSelectionChanged(event);
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see ICompareEditorPartListener#updateCenter()
		 */
		public void updateCenter() {
			BaseModelContentMergeViewer.this.updateCenter();
		}
	}

}
