package com.odcgroup.page.compare.viewer;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.compare.diff.metamodel.AttributeChange;
import org.eclipse.emf.compare.diff.metamodel.DiffElement;
import org.eclipse.emf.compare.diff.metamodel.ReferenceChange;
import org.eclipse.emf.compare.match.metamodel.UnmatchElement;
import org.eclipse.emf.compare.ui.viewer.content.part.IModelContentMergeViewerTab;
import org.eclipse.emf.compare.ui.viewer.content.part.ModelContentMergeTabItem;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.FontMetrics;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Item;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Widget;

import com.odcgroup.page.model.provider.PageModelItemProviderAdapterFactory;

/**
 * The Property part for the Page-Compare functionality.
 * 
 * @author Gary Hayes
 */
public class PageContentMergePropertyPart extends TableViewer implements IModelContentMergeViewerTab {
	
	/** <code>int</code> representing this viewer part side. */
	protected final int partSide;

	/**
	 * Creates a table viewer on a newly-created table control under the given parent. The table control is
	 * created using the given style bits.
	 * 
	 * @param parent
	 *            the parent control.
	 * @param style
	 *            SWT style bits.
	 * @param side
	 *            Side of this viewer part.
	 */
	public PageContentMergePropertyPart(Composite parent, int style, int side) {
		super(parent, style);
		partSide = side;

		setLabelProvider(new AdapterFactoryLabelProvider(new PageModelItemProviderAdapterFactory()));

		setUseHashlookup(true);
		getTable().setLinesVisible(true);
		getTable().setHeaderVisible(true);

		final GC gc = new GC(getTable());
		gc.setFont(getTable().getFont());
		final FontMetrics metrics = gc.getFontMetrics();
		gc.dispose();

		final TableColumn nameColumn = new TableColumn(getTable(), SWT.LEFT);
		nameColumn.setText("Attribute Name");
		nameColumn.setWidth(Dialog.convertWidthInCharsToPixels(metrics, nameColumn.getText().length() * 3));
		final TableColumn weightsColumn = new TableColumn(getTable(), SWT.LEFT);
		weightsColumn.setText("Value");
		weightsColumn.setWidth(Dialog.convertWidthInCharsToPixels(metrics,
				weightsColumn.getText().length() * 10));
	}

	/**
	 * Returns the widget representing the given {@link DiffElement} in the table.
	 * 
	 * @param inputEObject
	 *            Object which propertiesPart are currently displayed.
	 * @param diff
	 *            {@link DiffElement} to seek in the table.
	 * @return The widget representing the given {@link DiffElement}.
	 * @see org.eclipse.jface.viewers.StructuredViewer#findItem(Object)
	 */
	public TableItem find(DiffElement diff) {
		final EObject inputEObject = ((PageCompareStructuredContentProvider)getContentProvider()).getInputEObject();
		TableItem item = null;
		if (diff instanceof AttributeChange) {
			final AttributeChange theDiff = (AttributeChange)diff;
			if (theDiff.getLeftElement() == inputEObject || theDiff.getRightElement() == inputEObject)
				item = (TableItem)findItem(theDiff.getAttribute());
		} else if (diff instanceof ReferenceChange) {
			final ReferenceChange theDiff = (ReferenceChange)diff;
			if (theDiff.getLeftElement() == inputEObject || theDiff.getRightElement() == inputEObject)
				item = (TableItem)findItem(theDiff.getReference());
		}
		return item;
	
	}

	/**
	 * Returns the side of this viewer part.
	 * 
	 * @return The side of this viewer part.
	 */
	public int getSide() {
		return partSide;
	}


	/* (non-Javadoc)
	 * @see org.eclipse.emf.compare.ui.viewer.content.part.IModelContentMergeViewerTab#dispose()
	 */
	public void dispose() {		
	}

	/* (non-Javadoc)
	 * @see org.eclipse.emf.compare.ui.viewer.content.part.IModelContentMergeViewerTab#getSelectedElements()
	 */
	public List<? extends Item> getSelectedElements() {
		final List<TableItem> result = new ArrayList<TableItem>();
		final Control control = getControl();
		if (control != null && !control.isDisposed()) {
			final List<?> list = getSelectionFromWidget();
			for (Object o : list) {
				final Widget w = findItem(o);
				if (w instanceof TableItem)
					result.add((TableItem)w);
			}
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.emf.compare.ui.viewer.content.part.IModelContentMergeViewerTab#getUIItem(org.eclipse.emf.ecore.EObject)
	 */
	public ModelContentMergeTabItem getUIItem(EObject data) {
		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.emf.compare.ui.viewer.content.part.IModelContentMergeViewerTab#getVisibleElements()
	 */
	public List<ModelContentMergeTabItem> getVisibleElements() {
		return new ArrayList<ModelContentMergeTabItem>();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.emf.compare.ui.viewer.content.part.IModelContentMergeViewerTab#redraw()
	 */
	public void redraw() {
		
	}

	/* (non-Javadoc)
	 * @see org.eclipse.emf.compare.ui.viewer.content.part.IModelContentMergeViewerTab#setReflectiveInput(java.lang.Object)
	 */
	public void setReflectiveInput(Object input) {
		if (input != null && input instanceof UnmatchElement) {
			UnmatchElement element = (UnmatchElement) input;
			setInput(element);
		}		
	}

	/* (non-Javadoc)
	 * @see org.eclipse.emf.compare.ui.viewer.content.part.IModelContentMergeViewerTab#showItems(java.util.List)
	 */
	public void showItems(List<DiffElement> items) {
		if (items.size() > 0)
			showItem(items.get(0));		
	}
	
	/**
	 * @param diff
	 */
	public void showItem(DiffElement diff) {
		final TableItem elementItem = find(diff);
		if (elementItem != null) {
			getTable().setSelection(elementItem);
		}
	}
}
