package com.odcgroup.page.model.widgets.matrix.impl;

import java.util.List;

import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Snippet;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.snippets.SnippetUtil;
import com.odcgroup.page.model.widgets.matrix.ICssClass;
import com.odcgroup.page.model.widgets.matrix.IMatrixCellItem;
import com.odcgroup.page.model.widgets.table.impl.WidgetAdapter;

/**
 *
 * @author pkk
 *
 */
public class MatrixCellItem extends WidgetAdapter implements IMatrixCellItem {
	
	private static final String PROPERTY_AGGREGATIONTYPE = "aggregationType";
	private static final String PROPERTY_ITEMTYPE = "matrixCellItemType";

	/**
	 * @param widget
	 */
	protected MatrixCellItem(Widget widget) {
		super(widget);
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.page.model.widgets.matrix.IMatrixCellItem#getAggregationType()
	 */
	@Override
	public Property getAggregationType() {
		return getProperty(PROPERTY_AGGREGATIONTYPE);
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.page.model.widgets.matrix.IMatrixCellItem#getDomainAttribute()
	 */
	@Override
	public Property getDomainAttribute() {
		return getProperty(PropertyTypeConstants.DOMAIN_ATTRIBUTE);
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.page.model.widgets.matrix.IMatrixCellItem#getID()
	 */
	@Override
	public String getID() {
		return getWidget().getID();
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
	 * @see com.odcgroup.page.model.widgets.matrix.IMatrixCellItem#getType()
	 */
	@Override
	public Property getType() {
		return getProperty(PROPERTY_ITEMTYPE);
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.page.model.widgets.matrix.IMatrixCellItem#isComputed()
	 */
	@Override
	public boolean isComputed() {
		return getType()==null? false : ITEMTYPE_COMPUTED.equals(getType().getValue());
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.page.model.widgets.matrix.IMatrixCellItem#isDomainBound()
	 */
	@Override
	public boolean isDomainBound() {
		return getType()==null? false : ITEMTYPE_DOMAIN.equals(getType().getValue());
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.page.model.widgets.matrix.IMatrixCellItem#getColumnName()
	 */
	@Override
	public String getColumnName() {
		if (isComputed()) {
			return getPropertyValue("column-name");
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.page.model.widgets.matrix.IMatrixCellItem#setColumnName(java.lang.String)
	 */
	@Override
	public void setColumnName(String columnName) {
		if (isComputed()) {
			getProperty("column-name").setValue(columnName);
		}		
	}

}
