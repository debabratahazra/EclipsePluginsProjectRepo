package com.odcgroup.page.model.widgets.matrix.impl;

import java.util.Collection;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.collections.Transformer;
import org.apache.commons.lang.StringUtils;

import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.util.MatrixHelper;
import com.odcgroup.page.model.widgets.matrix.IMatrix;
import com.odcgroup.page.model.widgets.matrix.IMatrixAxis;
import com.odcgroup.page.model.widgets.matrix.IMatrixCell;
import com.odcgroup.page.model.widgets.matrix.IMatrixContentCell;
import com.odcgroup.page.model.widgets.matrix.IMatrixExtra;
import com.odcgroup.page.model.widgets.matrix.IMatrixExtraColumn;
import com.odcgroup.page.model.widgets.table.ITableKeepFilter;
import com.odcgroup.page.model.widgets.table.impl.TableKeepFilter;
import com.odcgroup.page.model.widgets.table.impl.WidgetAdapter;

/**
 *
 * @author pkk
 *
 */
public class Matrix extends WidgetAdapter implements IMatrix {
	
	private static final String DISPLAY_LAST_CELL = "displayLastCell";	
	private static final String DISPLAY_LAST_COLUMN = "displayLastColumn";
	private static final String DISPLAY_LAST_ROW = "displayLastRow";
	private static final String TABLE_KEEP_FILTER_WIDGET_TYPE = "TableKeepFilter";
	private static final String TABLE_EXTRA_WIDGET_TYPE = "MatrixExtra";

	private static final String MATRIX_AXIS_WIDGET = "MatrixAxis";
	private static final String MATRIX_AXIS_TYPE = "matrixAxis";
	private static final String MATRIX_Y_AXIS = "y-axis";
	private static final String MATRIX_X_AXIS = "x-axis";
	
	private static final String MATRIX_CELL_WIDGET = "MatrixCell";
	private static final String MATRIX_CONTENTCELL_WIDGET = "MatrixContentCell";
	private static final String MATRIX_EXTRACOL_WIDGET = "MatrixExtraColumn";
	private static final String MATRIX_CELL_TYPE = "matrixCellType";
	private static final String LAST_CELL = "last-cell";
	private static final String LAST_ROW = "last-row";
	private static final String LAST_COLUMN = "last-column";
	private static final String EXTRA_COLUMN = "extra-column";
	private static final String TABLE_KEEP_FILTER_LOGIC_PROPERTY = "table-keep-filter-logic";
	private static final String TABLE_MODEL_REFERENCE_PROPERTY = "matrix-model-reference";
	private static final String TABLE_DUMP_MODEL_PROPERTY = "dumpModel";
	
	/**
	 * @param widget
	 */
	public Matrix(Widget widget) {
		super(widget);
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.page.model.widgets.matrix.IMatrix#add(com.odcgroup.page.model.widgets.table.ITableKeepFilter)
	 */
	@Override
	public void add(ITableKeepFilter keepFilter) {
		add(keepFilter.getWidget());
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.page.model.widgets.matrix.IMatrix#add(com.odcgroup.page.model.widgets.table.ITableExtra)
	 */
	@Override
	public void add(IMatrixExtra extra) {
		add(extra.getWidget());
	}
	
	/**
	 * Adds new child widget (column) of the table widget
	 * 
	 * @param widget
	 *            the column widget to be added
	 */
	private void add(Widget widget) {
		List<Widget> children = getWidget().getContents();
		int index = children.indexOf(widget);
		if (index == -1) {
			children.add(widget);
		}
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.page.model.widgets.matrix.IMatrix#displayLastCell()
	 */
	@Override
	public boolean displayLastCell() {
		Property property = getProperty(DISPLAY_LAST_CELL);
		return property.getBooleanValue();
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.page.model.widgets.matrix.IMatrix#displayLastColumn()
	 */
	@Override
	public boolean displayLastColumn() {
		Property property = getProperty(DISPLAY_LAST_COLUMN);
		return property.getBooleanValue();
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.page.model.widgets.matrix.IMatrix#displayLastRow()
	 */
	@Override
	public boolean displayLastRow() {
		Property property = getProperty(DISPLAY_LAST_ROW);
		return property.getBooleanValue();
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.page.model.widgets.matrix.IMatrix#getCssClass()
	 */
	@Override
	public String getCssClass() {
		Property property = getProperty(PropertyTypeConstants.CSS_CLASS);
		return property.getValue();
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.page.model.widgets.matrix.IMatrix#getDescription()
	 */
	@Override
	public String getDescription() {
		Property property = getProperty(PropertyTypeConstants.DOCUMENTATION);
		return property.getValue();
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.page.model.widgets.matrix.IMatrix#getExtraColumn()
	 */
	@Override
	public IMatrixExtraColumn getExtraColumn() {
		return MatrixHelper.getMatrixExtraColumn(getContentWidget(MATRIX_EXTRACOL_WIDGET, MATRIX_CELL_TYPE, EXTRA_COLUMN));
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.page.model.widgets.matrix.IMatrix#getExtras()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<IMatrixExtra> getExtras() {
		return (List<IMatrixExtra>) CollectionUtils.collect(CollectionUtils.select(getWidget().getContents(),
				new Predicate() {
					public boolean evaluate(Object object) {
						return ((Widget) object).getTypeName().equals(TABLE_EXTRA_WIDGET_TYPE);
					}
				}), new Transformer() {
			public Object transform(Object object) {
				return new MatrixExtra((Widget) object);
			}
		});
	
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.page.model.widgets.matrix.IMatrix#getXAxis()
	 */
	@Override
	public IMatrixAxis getXAxis() {
		return MatrixHelper.getMatrixAxis(getContentWidget(MATRIX_AXIS_WIDGET, MATRIX_AXIS_TYPE, MATRIX_X_AXIS));
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.page.model.widgets.matrix.IMatrix#getYAxis()
	 */
	@Override
	public IMatrixAxis getYAxis() {
		return MatrixHelper.getMatrixAxis(getContentWidget(MATRIX_AXIS_WIDGET, MATRIX_AXIS_TYPE, MATRIX_Y_AXIS));
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.page.model.widgets.matrix.IMatrix#getID()
	 */
	@Override
	public String getID() {
		return getWidget().getID();
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.page.model.widgets.matrix.IMatrix#getKeepFilters()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ITableKeepFilter> getKeepFilters() {
		return (List<ITableKeepFilter>) CollectionUtils.collect(CollectionUtils.select(getWidget().getContents(),
				new Predicate() {
					public boolean evaluate(Object object) {
						return ((Widget) object).getTypeName().equals(TABLE_KEEP_FILTER_WIDGET_TYPE);
					}
				}), new Transformer() {
			public Object transform(Object object) {
				return new TableKeepFilter((Widget) object);
			}
		});
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.page.model.widgets.matrix.IMatrix#getLastCell()
	 */
	@Override
	public IMatrixCell getLastCell() {
		return MatrixHelper.getMatrixCell(getContentWidget(MATRIX_CELL_WIDGET, MATRIX_CELL_TYPE, LAST_CELL));
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.page.model.widgets.matrix.IMatrix#getLastColumn()
	 */
	@Override
	public IMatrixCell getLastColumn() {
		return MatrixHelper.getMatrixCell(getContentWidget(MATRIX_CELL_WIDGET, MATRIX_CELL_TYPE, LAST_COLUMN));
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.page.model.widgets.matrix.IMatrix#getLastRow()
	 */
	@Override
	public IMatrixCell getLastRow() {
		return MatrixHelper.getMatrixCell(getContentWidget(MATRIX_CELL_WIDGET, MATRIX_CELL_TYPE, LAST_ROW));
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.page.model.widgets.matrix.IMatrix#getMatrixCell()
	 */
	@Override
	public IMatrixContentCell getMatrixCell() {
		return MatrixHelper.getMatrixContentCell(getContentWidget(MATRIX_CONTENTCELL_WIDGET, MATRIX_CELL_TYPE, "content-cell"));
	}
	
	/**
	 * @return the name of the dataset [<Domain>.<Dataset>]  
	 */
	private String getDataset() {
		String dataset = getWidget().findPropertyValueInParent(PropertyTypeConstants.DOMAIN_ENTITY);
		return null != dataset ? dataset.replaceAll(":", ".") : "";
	}		

	/* (non-Javadoc)
	 * @see com.odcgroup.page.model.widgets.matrix.IMatrix#getModelReference()
	 */
	@Override
	public String getModelReference() {
		Property property = getProperty(TABLE_MODEL_REFERENCE_PROPERTY);
		String value = property.getValue();
		if (StringUtils.isBlank(value)) {
			value = "DSTableModel." + getDataset();
		}
		return null != value ? value : "";
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.page.model.widgets.matrix.IMatrix#isDumpModelEnabled()
	 */
	@Override
	public boolean isDumpModelEnabled() {
		return getProperty(TABLE_DUMP_MODEL_PROPERTY).getBooleanValue();
	}
	
	/**
	 * @param type
	 * @return
	 */
	private Widget getContentWidget(final String widgetType, final String propertyType, final String value) {
		List<Widget> contents = getWidget().getContents();
		Collection<?> collection = CollectionUtils.select(contents, new Predicate() {
			public boolean evaluate(Object object) {
				Widget widget = (Widget) object;
				if (widget.getTypeName().equals(widgetType) 
						&& widget.getPropertyValue(propertyType).equals(value)) {
					return true;
				}
				return false;
			
			}
		});
		if (!collection.isEmpty()) {
			return (Widget) collection.iterator().next();
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.page.model.widgets.matrix.IMatrix#getKeepFilterLogic()
	 */
	@Override
	public Property getKeepFilterLogic() {
		return getWidget().findProperty(TABLE_KEEP_FILTER_LOGIC_PROPERTY);
	}
	
	/* (non-Javadoc)
	 * @see com.odcgroup.page.model.widgets.matrix.IMatrix#remove(com.odcgroup.page.model.widgets.table.ITableExtra)
	 */
	public void remove(IMatrixExtra extra) {
		remove(extra.getWidget());
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.page.model.widgets.matrix.IMatrix#remove(com.odcgroup.page.model.widgets.table.ITableKeepFilter)
	 */
	public void remove(ITableKeepFilter keepFilter) {
		remove(keepFilter.getWidget());
	}

	/**
	 * Remove a widget from the collection of child widget
	 * 
	 * @param widget
	 *            the widget to be removed
	 */
	private void remove(Widget widget) {
		List<Widget> children = getWidget().getContents();
		int index = children.indexOf(widget);
		if (index != -1) {
			children.remove(index);
		}
	}

}
