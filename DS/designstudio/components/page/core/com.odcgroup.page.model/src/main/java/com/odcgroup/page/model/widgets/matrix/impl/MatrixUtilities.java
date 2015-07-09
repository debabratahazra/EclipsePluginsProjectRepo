package com.odcgroup.page.model.widgets.matrix.impl;

import com.odcgroup.page.metamodel.DataType;
import com.odcgroup.page.metamodel.MetaModel;
import com.odcgroup.page.metamodel.util.MetaModelRegistry;
import com.odcgroup.page.model.widgets.matrix.IMatrixFactory;
import com.odcgroup.page.model.widgets.matrix.IMatrixUtilities;

/**
 *
 * @author pkk
 *
 */
public class MatrixUtilities implements IMatrixUtilities {
	
	private static final IMatrixFactory factory = new MatrixFactory();

	/* (non-Javadoc)
	 * @see com.odcgroup.page.model.widgets.matrix.IMatrixUtilities#getFactory()
	 */
	@Override
	public IMatrixFactory getFactory() {
		return factory;
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.page.model.widgets.matrix.IMatrixUtilities#getCssClassType()
	 */
	@Override
	public DataType getCssClassType() {
		return findDataType("CssClassType");
	}
	
	/**
	 * @return
	 */
	public DataType getAggregationType() {
		return findDataType("MatrixAggregationType");
	}

	/**
	 * Find the DataType given its  name
	 * @param name
	 * @return DataType
	 */
	private DataType findDataType(String name) {
		MetaModel metamodel = MetaModelRegistry.getMetaModel();
		for (DataType dataType : metamodel.getDataTypes().getTypes()) {
			if (name.equals(dataType.getName())) {
				return dataType;
			}
		}
		return null;
	}

}
