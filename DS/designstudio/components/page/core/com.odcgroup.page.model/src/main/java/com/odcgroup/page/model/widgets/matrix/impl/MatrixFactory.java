package com.odcgroup.page.model.widgets.matrix.impl;

import com.odcgroup.page.metamodel.MetaModel;
import com.odcgroup.page.metamodel.WidgetLibrary;
import com.odcgroup.page.metamodel.WidgetTemplate;
import com.odcgroup.page.metamodel.util.MetaModelRegistry;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.util.WidgetFactory;
import com.odcgroup.page.model.widgets.matrix.IMatrix;
import com.odcgroup.page.model.widgets.matrix.IMatrixAxis;
import com.odcgroup.page.model.widgets.matrix.IMatrixCell;
import com.odcgroup.page.model.widgets.matrix.IMatrixCellItem;
import com.odcgroup.page.model.widgets.matrix.IMatrixContentCell;
import com.odcgroup.page.model.widgets.matrix.IMatrixContentCellItem;
import com.odcgroup.page.model.widgets.matrix.IMatrixExtra;
import com.odcgroup.page.model.widgets.matrix.IMatrixExtraColumn;
import com.odcgroup.page.model.widgets.matrix.IMatrixExtraColumnItem;
import com.odcgroup.page.model.widgets.matrix.IMatrixFactory;

/**
 *
 * @author pkk
 *
 */
public class MatrixFactory implements IMatrixFactory {
	
	private static final String WIDGETTYPE_MATRIX = "Matrix";
	private static final String WIDGETTYPE_MATRIXAXIS = "MatrixAxis";
	private static final String WIDGETTYPE_MATRIXCELL = "MatrixCell";
	private static final String WIDGETTYPE_MATRIXCONTENTCELL = "MatrixContentCell";
	private static final String WIDGETTYPE_MATRIXCONTENTCELLITEM = "MatrixContentCellItem";
	private static final String WIDGETTYPE_MATRIXCELLITEM = "MatrixCellItem";
	private static final String WIDGETTYPE_MATRIXEXTRACOLUMN = "MatrixExtraColumn";
	private static final String WIDGETTYPE_MATRIXEXTRACOLUMNITEM = "MatrixExtraColumnItem";
	private static final String MATRIX_EXTRA_TEMPLATE = "MatrixExtra";

	/** name of the xgui library */
	private static final String XGUI_LIBRARY = "xgui";

	/* (non-Javadoc)
	 * @see com.odcgroup.page.model.widgets.matrix.IMatrixFactory#adaptMatrixWidget(com.odcgroup.page.model.Widget)
	 */
	public IMatrix adaptMatrixWidget(Widget widget) {
		if (!WIDGETTYPE_MATRIX.equalsIgnoreCase(widget.getTypeName())) {
			throw new IllegalArgumentException("This is not a Matrix widget");
		}
		return new Matrix(widget);
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.page.model.widgets.matrix.IMatrixFactory#adaptMatrixAxisWidget(com.odcgroup.page.model.Widget)
	 */
	public IMatrixAxis adaptMatrixAxisWidget(Widget widget) {
		if (!WIDGETTYPE_MATRIXAXIS.equalsIgnoreCase(widget.getTypeName())) {
			throw new IllegalArgumentException("This is not a MatrixAxis widget");
		}
		return new MatrixAxis(widget);
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.page.model.widgets.matrix.IMatrixFactory#adaptMatrixCellWidget(com.odcgroup.page.model.Widget)
	 */
	public IMatrixCell adaptMatrixCellWidget(Widget widget) {
		if (!WIDGETTYPE_MATRIXCELL.equalsIgnoreCase(widget.getTypeName())) {
			throw new IllegalArgumentException("This is not a MatrixCell widget");
		}
		return new MatrixCell(widget);
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.page.model.widgets.matrix.IMatrixFactory#adaptMatrixContentCellWidget(com.odcgroup.page.model.Widget)
	 */
	@Override
	public IMatrixContentCell adaptMatrixContentCellWidget(Widget widget) {
		if (!WIDGETTYPE_MATRIXCONTENTCELL.equalsIgnoreCase(widget.getTypeName())) {
			throw new IllegalArgumentException("This is not a MatrixContentCell widget");
		}
		return new MatrixContentCell(widget);
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.page.model.widgets.matrix.IMatrixFactory#adaptMatrixCellItemWidget(com.odcgroup.page.model.Widget)
	 */
	@Override
	public IMatrixCellItem adaptMatrixCellItemWidget(Widget widget) {
		if (!WIDGETTYPE_MATRIXCELLITEM.equalsIgnoreCase(widget.getTypeName())) {
			throw new IllegalArgumentException("This is not a MatrixCellItem widget");
		}
		return new MatrixCellItem(widget);
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.page.model.widgets.matrix.IMatrixFactory#adaptMatrixContentCellItemWidget(com.odcgroup.page.model.Widget)
	 */
	@Override
	public IMatrixContentCellItem adaptMatrixContentCellItemWidget(Widget widget) {
		if (!WIDGETTYPE_MATRIXCONTENTCELLITEM.equalsIgnoreCase(widget.getTypeName())) {
			throw new IllegalArgumentException("This is not a MatrixContentCellItem widget");
		}
		return new MatrixContentCellItem(widget);	
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.page.model.widgets.matrix.IMatrixFactory#adapatMatrixExtraColumnItemWidget(com.odcgroup.page.model.Widget)
	 */
	@Override
	public IMatrixExtraColumnItem adapatMatrixExtraColumnItemWidget(Widget widget) {
		if (!WIDGETTYPE_MATRIXEXTRACOLUMNITEM.equalsIgnoreCase(widget.getTypeName())) {
			throw new IllegalArgumentException("This is not a MatrixExtraColumnItem widget");
		}
		return new MatrixExtraColumnItem(widget);	
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.page.model.widgets.matrix.IMatrixFactory#adapatMatrixExtraColumnWidget(com.odcgroup.page.model.Widget)
	 */
	@Override
	public IMatrixExtraColumn adapatMatrixExtraColumnWidget(Widget widget) {
		if (!WIDGETTYPE_MATRIXEXTRACOLUMN.equalsIgnoreCase(widget.getTypeName())) {
			throw new IllegalArgumentException("This is not a MatrixExtraColumn widget");
		}
		return new MatrixExtraColumn(widget);
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.page.model.widgets.matrix.IMatrixFactory#createTableExtra()
	 */
	@Override
	public IMatrixExtra createTableExtra() {
		Widget widget = createWidget(MATRIX_EXTRA_TEMPLATE);
		return new MatrixExtra(widget);
	}
	
	/**
	 * Create a new Widget instance given its template name
	 * @param TemplateName the name of the widget template
	 * @return Widget
	 */
	protected Widget createWidget(String TemplateName) {
		MetaModel metamodel = MetaModelRegistry.getMetaModel();
		WidgetLibrary library = metamodel.findWidgetLibrary(XGUI_LIBRARY);
		WidgetTemplate template = library.findWidgetTemplate(TemplateName);
		WidgetFactory factory = new WidgetFactory();
		Widget widget = factory.create(template);
		return widget;
	}

}
