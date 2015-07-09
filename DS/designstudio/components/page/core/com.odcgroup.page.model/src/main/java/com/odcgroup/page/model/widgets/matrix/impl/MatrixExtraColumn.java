package com.odcgroup.page.model.widgets.matrix.impl;

import java.util.ArrayList;
import java.util.List;

import com.odcgroup.page.metamodel.MetaModel;
import com.odcgroup.page.metamodel.WidgetLibrary;
import com.odcgroup.page.metamodel.WidgetLibraryConstants;
import com.odcgroup.page.metamodel.WidgetTemplate;
import com.odcgroup.page.metamodel.util.MetaModelRegistry;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.util.MatrixHelper;
import com.odcgroup.page.model.util.WidgetFactory;
import com.odcgroup.page.model.widgets.matrix.IMatrixExtraColumnItem;
import com.odcgroup.page.model.widgets.matrix.IMatrixExtraColumn;

/**
 *
 * @author pkk
 *
 */
public class MatrixExtraColumn extends MatrixCell implements IMatrixExtraColumn {

	/**
	 * @param widget
	 */
	protected MatrixExtraColumn(Widget widget) {
		super(widget);
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.page.model.widgets.matrix.IMatrixExtraColumn#addItem()
	 */
	@Override
	public IMatrixExtraColumnItem addItem() {
		IMatrixExtraColumnItem item = createExtraColumnItem();
		addItem(item);
		return item;
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.page.model.widgets.matrix.IMatrixExtraColumn#addItem(com.odcgroup.page.model.widgets.matrix.IExtraColumnItem)
	 */
	@Override
	public void addItem(IMatrixExtraColumnItem item) {
		Widget widget = item.getWidget();
		List<Widget> children = getWidget().getContents();
		int index = children.indexOf(widget);
		if (index == -1) {
			children.add(widget);
		}	
	}
	
	/**
	 * @return ExtraColumnItem
	 */
	private IMatrixExtraColumnItem createExtraColumnItem() {		
		/** Uses a script to create a ExtraColumnItem widget. */
		MetaModel metamodel = MetaModelRegistry.getMetaModel();
		WidgetLibrary library = metamodel.findWidgetLibrary(WidgetLibraryConstants.XGUI);
		WidgetTemplate template = library.findWidgetTemplate("MatrixExtraColumnItem");
		WidgetFactory factory = new WidgetFactory();

		/** Creates the new ExtraColumnItem widget from its template. */
		Widget item = factory.create(template);

		return new MatrixExtraColumnItem(item);
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.page.model.widgets.matrix.IMatrixExtraColumn#getItems()
	 */
	@Override
	public List<IMatrixExtraColumnItem> getItems() {
		List<Widget> contents = getWidget().getContents();
		List<IMatrixExtraColumnItem> items = new ArrayList<IMatrixExtraColumnItem>();
		IMatrixExtraColumnItem item = null;
		for (Widget widget : contents) {
			if ("MatrixExtraColumnItem".equals(widget.getTypeName())) {
				item = MatrixHelper.getMatrixExtraColumnItem(widget);
				items.add(item);
			}
		}
		return items;
	}

}
