package com.odcgroup.page.model.widgets.matrix.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.odcgroup.page.metamodel.MetaModel;
import com.odcgroup.page.metamodel.WidgetLibrary;
import com.odcgroup.page.metamodel.WidgetLibraryConstants;
import com.odcgroup.page.metamodel.WidgetTemplate;
import com.odcgroup.page.metamodel.WidgetTypeConstants;
import com.odcgroup.page.metamodel.util.MetaModelRegistry;
import com.odcgroup.page.model.Snippet;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.snippets.SnippetUtil;
import com.odcgroup.page.model.util.MatrixHelper;
import com.odcgroup.page.model.util.WidgetFactory;
import com.odcgroup.page.model.widgets.matrix.ICssClass;
import com.odcgroup.page.model.widgets.matrix.IMatrix;
import com.odcgroup.page.model.widgets.matrix.IMatrixCell;
import com.odcgroup.page.model.widgets.matrix.IMatrixCellItem;
import com.odcgroup.page.model.widgets.matrix.IMatrixContentCell;
import com.odcgroup.page.model.widgets.matrix.IMatrixContentCellItem;

/**
 *
 * @author pkk
 *
 */
public class MatrixContentCell extends MatrixCell implements IMatrixContentCell {

	/**
	 * @param widget
	 */
	protected MatrixContentCell(Widget widget) {
		super(widget);
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.page.model.widgets.matrix.IMatrixContentCell#addItem()
	 */
	public IMatrixContentCellItem addItem(boolean computed) {
		IMatrixContentCellItem item = createContentCellItem();
		if (computed) {
			item.getType().setValue(IMatrixContentCellItem.ITEMTYPE_COMPUTED);
			item.setColumnName("comp_"+item.getID());
		} else {
			item.getType().setValue(IMatrixContentCellItem.ITEMTYPE_DOMAIN);
		}
		addItem(item);
		return item;
	}
	
	/* (non-Javadoc)
	 * @see com.odcgroup.page.model.widgets.matrix.IMatrixContentCell#addToBox(com.odcgroup.page.model.Widget)
	 */
	public IMatrixContentCellItem addToBox(Widget box, boolean computed) {
		IMatrixContentCellItem item = createContentCellItem();
		if (computed) {
			item.getType().setValue(IMatrixContentCellItem.ITEMTYPE_COMPUTED);
			item.setColumnName("comp_"+item.getID());
		} else {
			item.getType().setValue(IMatrixContentCellItem.ITEMTYPE_DOMAIN);
		}
		addItem(item, box);
		return item;		
	}
	
	/* (non-Javadoc)
	 * @see com.odcgroup.page.model.widgets.matrix.impl.MatrixCell#removeItem(com.odcgroup.page.model.widgets.matrix.IMatrixCellItem)
	 */
	public void removeItem(IMatrixCellItem cellItem) {
		if (cellItem instanceof IMatrixContentCellItem) {	
			List<String> items = ((IMatrixContentCellItem)cellItem).getAggregationItems();
			Widget parent = cellItem.getWidget().getParent();
			parent.getContents().remove(cellItem.getWidget());
			IMatrix matrix = getParent();
			IMatrixCell lastCol = matrix.getLastColumn();
			if (lastCol != null) {
				IMatrixCellItem item = getCellItem(lastCol, items);
				if (item != null) {
					lastCol.removeItem(item);
				}
			}
			IMatrixCell lastRow = matrix.getLastRow();
			if (lastRow != null) {
				IMatrixCellItem item = getCellItem(lastRow, items);
				if (item != null) {
					lastRow.removeItem(item);
				}
			}
			IMatrixCell lastCell = matrix.getLastCell();
			if (lastCell != null) {
				IMatrixCellItem item = getCellItem(lastCell, items);
				if (item != null) {
					lastCell.removeItem(item);
				}
			}
		}
	}
	
	/**
	 * @param cell
	 * @param ids
	 * @return
	 */
	private IMatrixCellItem getCellItem(IMatrixCell cell, List<String> ids) {
		List<IMatrixCellItem> items = cell.getCellItems();
		for (IMatrixCellItem item : items) {
			if (ids.contains(item.getID())) {
				return item;
			}
		}
		return null;
	}
	
	/**
	 * @param item
	 */
	public void addItem(IMatrixContentCellItem item) {
		Widget widget = item.getWidget();
		List<IMatrixContentCellItem> children = getItems();
		boolean found = false;
		for (IMatrixContentCellItem cellItem : children) {
			if(widget.equals(cellItem.getWidget())) {
				found = true;
				break;
			}
		}
		if (!found) {
			getWidget().getContents().add(widget);
		}	
		//now add items to all last cells
		addItemstoLastCells(item, item.isComputed());
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.page.model.widgets.matrix.IMatrixContentCell#addItem(com.odcgroup.page.model.widgets.matrix.IMatrixContentCellItem, com.odcgroup.page.model.Widget)
	 */
	@Override
	public void addItem(IMatrixContentCellItem item, Widget box) {
		Widget widget = item.getWidget();
		List<IMatrixContentCellItem> children = getItems();
		boolean found = false;
		for (IMatrixContentCellItem cellItem : children) {
			if(widget.equals(cellItem.getWidget())) {
				found = true;
				break;
			}
		}
		if (!found) {
			box.getContents().add(widget);
		}	
		//now add items to all last cells
		String wt = box.getTypeName();
		if (!WidgetTypeConstants.CONDITIONAL_BODY.equals(wt) ) {
			if (!isBoxContainedInCondition(box)) {
				addItemstoLastCells(item, item.isComputed());
			}
		}
		
	}
	
	/**
	 * @param widget
	 * @return
	 */
	private boolean isBoxContainedInCondition(Widget widget) {
		String type = widget.getTypeName();
		if (WidgetTypeConstants.BOX.equals(type)) {
			Widget cond = getParentConditionBody(widget);
			if (cond != null) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * @param parent
	 * @return
	 */
	private  Widget getParentConditionBody(Widget parent) {
		String type = parent.getTypeName();
		if (type.equals(WidgetTypeConstants.CONDITIONAL_BODY)) {
			return parent;
		} else {
			if (parent.getParent() == null) {
				return null;
			}
			return getParentConditionBody(parent.getParent());
		}
	}
	
	/**
	 * @param item
	 */
	private void addItemstoLastCells(IMatrixContentCellItem item, boolean computed) {		
		List<IMatrixCellItem> items = new ArrayList<IMatrixCellItem>();		
		IMatrix matrix = getParent();
		//last column
		IMatrixCellItem cellItem = createCellItem();
		IMatrixCell cell = matrix.getLastColumn();
		cell.getWidget().getContents().add(cellItem.getWidget());
		items.add(cellItem);
		
		//last row
		IMatrixCellItem cellItem2 = createCellItem();
		IMatrixCell cell2 = matrix.getLastRow();
		cell2.getWidget().getContents().add(cellItem2.getWidget());
		items.add(cellItem2);			
		
		//last cell
		IMatrixCellItem cellItem3 = createCellItem();
		IMatrixCell cell3 = matrix.getLastCell();
		cell3.getWidget().getContents().add(cellItem3.getWidget());	
		items.add(cellItem3);	
		
		String type = null;
		if (computed) {
			type = IMatrixCellItem.ITEMTYPE_COMPUTED;
		} else {
			type = IMatrixCellItem.ITEMTYPE_DOMAIN;
		}
		StringBuilder sb = new StringBuilder();
		String attr = item.getDomainAttribute().getValue();
		for (IMatrixCellItem mItem : items) {
			mItem.getType().setValue(type);
			if (computed) {
				mItem.setColumnName("comp_"+mItem.getID());
			}
			sb.append(mItem.getID()+",");
			if (!computed) {
				mItem.getDomainAttribute().setValue(attr);
			}
		}
		String aggItems = sb.toString();
		if (!StringUtils.isEmpty(aggItems)) {
			aggItems = aggItems.substring(0, aggItems.lastIndexOf(","));
			item.getAggregationItemsProperty().setValue(aggItems);
		}
	}
	
	/**
	 * @return tableColumnItem
	 */
	private IMatrixContentCellItem createContentCellItem() {		
		/** Uses a script to create a matrixContentCellItem widget. */
		MetaModel metamodel = MetaModelRegistry.getMetaModel();
		WidgetLibrary library = metamodel.findWidgetLibrary(WidgetLibraryConstants.XGUI);
		WidgetTemplate template = library.findWidgetTemplate("MatrixContentCellItem");
		WidgetFactory factory = new WidgetFactory();

		/** Creates the new contentCellItem widget from its template. */
		Widget item = factory.create(template);

		return new MatrixContentCellItem(item);
	}
	
	/**
	 * @return
	 */
	private IMatrixCellItem createCellItem() {		
		/** Uses a script to create a table-column-widget. */
		MetaModel metamodel = MetaModelRegistry.getMetaModel();
		WidgetLibrary library = metamodel.findWidgetLibrary(WidgetLibraryConstants.XGUI);
		WidgetTemplate template = library.findWidgetTemplate("MatrixCellItem");
		WidgetFactory factory = new WidgetFactory();

		/** Creates the new column widget from its template. */
		Widget item = factory.create(template);

		return new MatrixCellItem(item);		
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.page.model.widgets.matrix.IStyleProvider#getCssClass()
	 */
	@Override
	public ICssClass getCssClass() {
		List<Snippet> snippets = getWidget().getSnippets();
		if (!snippets.isEmpty()) {
			return SnippetUtil.getSnippetFactory().adaptCssClass(snippets.get(0));
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.page.model.widgets.matrix.IMatrixContentCell#getItems()
	 */
	@Override
	public List<IMatrixContentCellItem> getItems() {
		List<Widget> contents = getWidget().getContents();
		List<IMatrixContentCellItem> items = new ArrayList<IMatrixContentCellItem>();
		String type;
		for (Widget widget : contents) {
			type = widget.getTypeName();
			if (WidgetTypeConstants.MATRIX_CONTENTCELLITEM.equals(type)) {
				items.add(MatrixHelper.getMatrixContentCellItem(widget));
			} else if (WidgetTypeConstants.BOX.equals(type) 
					|| WidgetTypeConstants.CONDITIONAL.equals(type)
					|| WidgetTypeConstants.CONDITIONAL_BODY.equals(type)) {
				collectContentCellItems(widget, items);
			}
		}
		return items;
	}
	
	/**
	 * @param widget
	 */
	private void collectContentCellItems(Widget widget, List<IMatrixContentCellItem> items) {
		String type;
		List<Widget> contents = widget.getContents();
		for (Widget content : contents) {
			type = content.getTypeName();
			if (WidgetTypeConstants.MATRIX_CONTENTCELLITEM.equals(type)) {
				items.add(MatrixHelper.getMatrixContentCellItem(content));
			} else if (WidgetTypeConstants.BOX.equals(type) 
					|| WidgetTypeConstants.CONDITIONAL.equals(type)
					|| WidgetTypeConstants.CONDITIONAL_BODY.equals(type)) {
				collectContentCellItems(content, items);
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.page.model.widgets.matrix.IMatrixContentCell#getComputedItems()
	 */
	@Override
	public List<IMatrixContentCellItem> getComputedItems() {
		List<IMatrixContentCellItem> items = getItems();
		List<IMatrixContentCellItem> comps = new ArrayList<IMatrixContentCellItem>();
		for (IMatrixContentCellItem item : items) {
			if (item.isComputed()) {
				comps.add(item);
			}
		}
		return comps;
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.page.model.widgets.matrix.IMatrixContentCell#getDomainItems()
	 */
	@Override
	public List<IMatrixContentCellItem> getDomainItems() {
		List<IMatrixContentCellItem> items = getItems();
		List<IMatrixContentCellItem> doms = new ArrayList<IMatrixContentCellItem>();
		for (IMatrixContentCellItem item : items) {
			if (item.isDomainBound()) {
				doms.add(item);
			}
		}
		return doms;
	}

}
