package com.odcgroup.page.model.widgets.matrix.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.model.Event;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.symbols.SymbolsExpander;
import com.odcgroup.page.model.symbols.SymbolsExpanderFactory;
import com.odcgroup.page.model.util.MatrixHelper;
import com.odcgroup.page.model.widgets.matrix.IMatrix;
import com.odcgroup.page.model.widgets.matrix.IMatrixAxis;
import com.odcgroup.page.model.widgets.table.impl.WidgetAdapter;

/**
 *
 * @author pkk
 *
 */
public class MatrixAxis extends WidgetAdapter implements IMatrixAxis {
	
	private static final String PROP_MATRIXAXIS = "matrixAxis";
	private static final String PROPVAL_XAXIS = "x-axis";
	private static final String PROPVAL_YAXIS = "y-axis";
	private static final String PROPTYPE_SORTCOLUMN = "group-sorting-column-name";
	private static final String PROPTYPE_SORTDIR = "group-sorting-direction";
	private static final String HORIZONAL_ALIGNMENT = "axis-halign";

	/**
	 * @param widget
	 */
	protected MatrixAxis(Widget widget) {
		super(widget);
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.page.model.widgets.matrix.IMatrixAxis#getDisplayFormat()
	 */
	@Override
	public Property getDisplayFormat() {
		return getProperty(PropertyTypeConstants.FORMAT);
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.page.model.widgets.matrix.IMatrixAxis#getDomainAttribute()
	 */
	@Override
	public Property getDomainAttribute() {
		return getProperty(PropertyTypeConstants.DOMAIN_ATTRIBUTE);
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.page.model.widgets.matrix.IMatrixAxis#getEvents()
	 */
	@Override
	public List<Event> getEvents() {
		return getWidget().getEvents();
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.page.model.widgets.matrix.IMatrixAxis#isXAxis()
	 */
	@Override
	public boolean isXAxis() {
		String value = getPropertyValue(PROP_MATRIXAXIS);
		return (value.equals(PROPVAL_XAXIS));
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.page.model.widgets.matrix.IMatrixAxis#isYAxis()
	 */
	@Override
	public boolean isYAxis() {
		String value = getPropertyValue(PROP_MATRIXAXIS);
		return (value.equals(PROPVAL_YAXIS));
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.page.model.widgets.matrix.IMatrixAxis#getMaxGrouping()
	 */
	@Override
	public int getMaxGrouping() {
		String val = "";
		int retVal = -1;
		if (isXAxis()) {
			val =  getProperty("maxColumns").getValue();
		} else {
			val = getProperty("maxRows").getValue();
		}
		if (!StringUtils.isEmpty(val)) {
			try {
				retVal = (new Integer(val)).intValue();
			} catch (NumberFormatException e) {
				retVal = -1;
			}
		}
		return retVal;
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.page.model.widgets.matrix.IMatrixAxis#getParent()
	 */
	@Override
	public IMatrix getParent() {
		return MatrixHelper.getMatrix(getWidget().getParent());		
	}

	/**
	 * @see com.odcgroup.page.model.widgets.table.ITableGroup#getSortingColumnName()
	 */
	public String getSortingColumnName() {
		return getPropertyValue(PROPTYPE_SORTCOLUMN);
	}

	/**
	 * @see com.odcgroup.page.model.widgets.table.ITableGroup#getSortingDirection()
	 */
	public String getSortingDirection() {
		return getPropertyValue(PROPTYPE_SORTDIR);
	}

	/**
	 * @see com.odcgroup.page.model.widgets.table.ITableGroup#isAscending()
	 */
	public boolean isAscending() {
		return "ascending".equals(getSortingDirection());
	}

	/**
	 * @see com.odcgroup.page.model.widgets.table.ITableGroup#isDescending()
	 */
	public boolean isDescending() {
		return "descending".equals(getSortingDirection());
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.page.model.widgets.matrix.IMatrixAxis#setSortingColumnName(java.lang.String)
	 */
	@Override
	public void setSortingColumnName(String columnName) {
		setPropertyValue(PROPTYPE_SORTCOLUMN, columnName);		
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.page.model.widgets.matrix.IMatrixAxis#setSortingDirection(java.lang.String)
	 */
	@Override
	public void setSortingDirection(String direction) {
		setPropertyValue(PROPTYPE_SORTDIR, direction);			
	}

	@Override
	public String getHorizontalAlignment() {
		String value = getPropertyValue(HORIZONAL_ALIGNMENT);
		if (StringUtils.isNotEmpty(value)) {
	        SymbolsExpander expander = SymbolsExpanderFactory.getSymbolsExpander();
	        if (expander != null) {
	        	value = expander.substitute(value, getWidget());
	        }	
		}
		return value;
	}	
	
	/* (non-Javadoc)
	 * @see com.odcgroup.page.model.widgets.matrix.IMatrixAxis#isDefaultHorizontalAlignment()
	 */
	public boolean isDefaultHorizontalAlignment() {
		Property prop = getWidget().findProperty(HORIZONAL_ALIGNMENT);
		return getHorizontalAlignment().equals(prop.getType().getDefaultValue());
	}

}
