package com.odcgroup.page.model.widgets.matrix.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.collections.Transformer;

import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.util.MatrixHelper;
import com.odcgroup.page.model.widgets.matrix.IMatrix;
import com.odcgroup.page.model.widgets.matrix.IMatrixCell;
import com.odcgroup.page.model.widgets.matrix.IMatrixCellItem;
import com.odcgroup.page.model.widgets.table.impl.WidgetAdapter;

/**
 *
 * @author pkk
 *
 */
public class MatrixCell extends WidgetAdapter implements IMatrixCell {
	
	private static final String WIDGETTYPE_MATRIXCELLITEM = "MatrixCellItem";
	private static final String PROPTYPE_MATRIXCELLTYPE = "matrixCellType";

	/**
	 * @param widget
	 */
	protected MatrixCell(Widget widget) {
		super(widget);
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.page.model.widgets.matrix.IMatrixCell#getCellItems()
	 */
	@SuppressWarnings("unchecked")
	public List<IMatrixCellItem> getCellItems() {
		return (List<IMatrixCellItem>) CollectionUtils.collect(CollectionUtils.select(getWidget().getContents(),
				new Predicate() {
					public boolean evaluate(Object object) {
						return ((Widget) object).getTypeName().equals(WIDGETTYPE_MATRIXCELLITEM);
					}
				}), new Transformer() {
			public Object transform(Object object) {
				return new MatrixCellItem((Widget) object);
			}
		});	
	}
	
	/**
	 * @param id
	 * @return
	 */
	public IMatrixCellItem getCellItem(String id) {
		for(IMatrixCellItem item : getCellItems()) {
			if (id.equals(item.getID())) {
				return item;
			}
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.page.model.widgets.matrix.IMatrixCell#removeItem(java.lang.Object)
	 */
	public void removeItem(IMatrixCellItem cellItem) {
		List<IMatrixCellItem> items = getCellItems();
		Widget widget = null;
		for (IMatrixCellItem item : items) {
			widget = item.getWidget();
			if (cellItem.getWidget().equals(widget)) {
				int index = getWidget().getContents().indexOf(widget);
				getWidget().getContents().remove(index);
			}
		}		
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.page.model.widgets.matrix.IMatrixCell#getMatrixCellType()
	 */
	public String getMatrixCellType() {
		return getPropertyValue(PROPTYPE_MATRIXCELLTYPE);
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.page.model.widgets.matrix.IMatrixCell#isContentCell()
	 */
	public boolean isContentCell() {
		return getMatrixCellType().equals(CELLTYPE_CONTENT);
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.page.model.widgets.matrix.IMatrixCell#isExtraColumn()
	 */
	public boolean isExtraColumn() {
		return getMatrixCellType().equals(CELLTYPE_EXTRACOLUMN);
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.page.model.widgets.matrix.IMatrixCell#isLastCell()
	 */
	public boolean isLastCell() {
		return getMatrixCellType().equals(CELLTYPE_LASTCELL);
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.page.model.widgets.matrix.IMatrixCell#isLastColumn()
	 */
	public boolean isLastColumn() {
		return getMatrixCellType().equals(CELLTYPE_LASTCOLUMN);
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.page.model.widgets.matrix.IMatrixCell#isLastRow()
	 */
	public boolean isLastRow() {
		return getMatrixCellType().equals(CELLTYPE_LASTROW);
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.page.model.widgets.matrix.IMatrixCell#getParent()
	 */
	@Override
	public IMatrix getParent() {
		return MatrixHelper.getMatrix(getWidget().getParent());
	}

}
