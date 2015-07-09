package com.odcgroup.page.model.widgets.matrix.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.commons.lang.StringUtils;

import com.odcgroup.mdf.metamodel.MdfDatasetProperty;
import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.metamodel.WidgetTypeConstants;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.util.MatrixHelper;
import com.odcgroup.page.model.widgets.matrix.IMatrix;
import com.odcgroup.page.model.widgets.matrix.IMatrixContentCell;
import com.odcgroup.page.model.widgets.matrix.IMatrixContentCellItem;

/**
 *
 * @author pkk
 *
 */
public class MatrixContentCellItem extends MatrixCellItem implements IMatrixContentCellItem {
	
	private static final String PROPTYPE_AGGRITEMS = "aggregationItems";
	private static final String PROPTYPE_COMPPARAMS = "column-computation-parameters";
	private static final String PROPTYPE_ENABLEDBASED = "enabledIsBasedOn";
	private static final String COLUMN_DISPLAY_FORMAT_PROPERTY = "format";	
	private static final String PROPTYPE_MEANWEIGHT = "matrixMeanWeight";

	/**
	 * @param widget
	 */
	protected MatrixContentCellItem(Widget widget) {
		super(widget);
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.page.model.widgets.matrix.IMatrixContentCellItem#getHorizontalAlignment()
	 */
	@Override
	public Property getHorizontalAlignment() {
		return getProperty(PropertyTypeConstants.HORIZONTAL_ALIGNMENT);
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.page.model.widgets.matrix.IMatrixContentCellItem#getVerticalAlignment()
	 */
	@Override
	public Property getVerticalAlignment() {
		return getProperty(PropertyTypeConstants.VERTICAL_ALIGNMENT);
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.page.model.widgets.matrix.IMatrixContentCellItem#getColumnComputation()
	 */
	@Override
	public Property getAggregation() {
		return getProperty("aggregationType");
	}
	
	/* (non-Javadoc)
	 * @see com.odcgroup.page.model.widgets.matrix.IMatrixContentCellItem#getComputation()
	 */
	public Property getComputation() {
		return getProperty("column-computation");
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.page.model.widgets.matrix.IMatrixCellItem#getID()
	 */
	@Override
	public String getID() {
		return getWidget().getID();
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.page.model.widgets.matrix.IMatrixContentCellItem#getAggregationItems()
	 */
	@Override
	public List<String> getAggregationItems() {
		String  value = getAggregationItemsProperty().getValue();
		List<String> items = new ArrayList<String>();
		if (!StringUtils.isEmpty(value)) {
			String[]  ids = value.split(",");
			for (String id : ids) {
				items.add(id);
			}
		}
		return items;
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.page.model.widgets.matrix.IMatrixContentCellItem#removeAggregationItem(java.lang.String)
	 */
	@Override
	public void removeAggregationItem(String id) {
		List<String> items = getAggregationItems();
		boolean removed = items.remove(id);
		if (removed) {
			String values = StringUtils.join(items.toArray(new String[]{}),',');
			getAggregationItemsProperty().setValue(values);
		}
		
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.page.model.widgets.matrix.IMatrixContentCellItem#getAggregationItemsProperty()
	 */
	@Override
	public Property getAggregationItemsProperty() {
		return getProperty(PROPTYPE_AGGRITEMS);
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.page.model.widgets.matrix.IMatrixContentCellItem#getComputationParameters()
	 */
	@Override
	public List<String> getComputationParameters() {
		List<String> parameters = new ArrayList<String>();
		String values = getPropertyValue(PROPTYPE_COMPPARAMS);
		StringTokenizer tokenizer = new StringTokenizer(values, ",");
		while (tokenizer.hasMoreTokens()) {
			parameters.add(tokenizer.nextToken());
		}
		return parameters;	
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.page.model.widgets.matrix.IMatrixContentCellItem#setComputationParameters(java.util.List)
	 */
	@Override
	public void setComputationParameters(List<String> parameters) {
		String values = StringUtils.join(parameters.toArray(new String[]{}),',');
		setPropertyValue(PROPTYPE_COMPPARAMS, values);		
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.page.model.widgets.matrix.IMatrixContentCellItem#getMatrix()
	 */
	@Override
	public IMatrix getMatrix() {
		IMatrixContentCell cell = getMatrixContentCell(getWidget().getParent());
		return cell.getParent();
	}
	
	/**
	 * @param widget
	 * @return
	 */
	private IMatrixContentCell getMatrixContentCell(Widget widget) {
		if (widget.getTypeName().equals(WidgetTypeConstants.MATRIX_CONTENTCELL)) {
			return MatrixHelper.getMatrixContentCell(widget);
		} else {
			return getMatrixContentCell(widget.getParent());
		}
	}	
	
	/* (non-Javadoc)
	 * @see com.odcgroup.page.model.widgets.matrix.IMatrixContentCellItem#getDatasetProperty(java.lang.String)
	 */
	public MdfDatasetProperty getDatasetProperty(String name) {
		return (MdfDatasetProperty)getWidget().findDomainObject(getDomainRepository(), name);
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.page.model.widgets.matrix.IMatrixAxis#getEnabled()
	 */
	@Override
	public Property getEnabled() {
		return getProperty(PropertyTypeConstants.ENABLED);
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.page.model.widgets.matrix.IMatrixAxis#getEnabledIsBasedOn()
	 */
	@Override
	public Property getEnabledIsBasedOn() {
		return getProperty(PROPTYPE_ENABLEDBASED);
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.page.model.widgets.matrix.IMatrixContentCellItem#getDisplayFormat()
	 */
	@Override
	public Property getDisplayFormat() {
		return getProperty(COLUMN_DISPLAY_FORMAT_PROPERTY);
	}

	@Override
	public boolean isWeightedMeanAggregation() {
		if(getAggregationType().getValue().equals("weighted-mean")) {
			return true;
		}
		return false;
	}

	@Override
	public Property getMeanWeight() {
		if (isWeightedMeanAggregation()) {
			return getProperty(PROPTYPE_MEANWEIGHT);
		}
		return null;
	}

	@Override
	public void setAggregationItems(List<String> items) {
		StringBuffer aggItemValue = new StringBuffer();
		String value = new String();
		for (String item : items) {
			aggItemValue.append(item + ",");	
		}
		value = aggItemValue.toString();
		if(value.length() > 0) {
			value = value.substring(0, aggItemValue.lastIndexOf(","));
		}
		getAggregationItemsProperty().setValue(value);
	}

}
