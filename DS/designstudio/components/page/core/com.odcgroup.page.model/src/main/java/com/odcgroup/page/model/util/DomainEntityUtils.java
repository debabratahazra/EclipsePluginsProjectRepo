package com.odcgroup.page.model.util;

import com.odcgroup.mdf.ecore.PrimitivesDomain;
import com.odcgroup.mdf.metamodel.MdfBusinessType;
import com.odcgroup.mdf.metamodel.MdfEntity;
import com.odcgroup.mdf.metamodel.MdfEnumeration;

public class DomainEntityUtils {
	
	/**
	 * @param entity
	 * @return operator type
	 */
	public static DataType getOperatorKeyType(MdfEntity entity) {
		if (entity instanceof MdfBusinessType) {
			return getOperatorKeyType(((MdfBusinessType) entity).getType());
		}
		if (entity != null && entity.isPrimitiveType()) {
			if (entity.equals(PrimitivesDomain.STRING)) {
				return DataType.STRING;
			} else if(entity.equals(PrimitivesDomain.DATE) || entity.equals(PrimitivesDomain.DATE_TIME)) {
				return DataType.DATE;
			} else if (entity.equals(PrimitivesDomain.BOOLEAN) || entity.equals(PrimitivesDomain.BOOLEAN_OBJ)) {
				return DataType.FLAG;
			} else if (entity.equals(PrimitivesDomain.INTEGER) || entity.equals(PrimitivesDomain.INTEGER_OBJ)
						|| entity.equals(PrimitivesDomain.SHORT) || entity.equals(PrimitivesDomain.SHORT_OBJ)
						|| entity.equals(PrimitivesDomain.LONG) || entity.equals(PrimitivesDomain.LONG_OBJ)) {
				return DataType.NUMBER;
			} else if (entity instanceof MdfEnumeration) {
				return DataType.ENUMERATION;
			} else if ( entity.equals(PrimitivesDomain.DOUBLE) || entity.equals(PrimitivesDomain.DOUBLE_OBJ)
					|| entity.equals(PrimitivesDomain.FLOAT) || entity.equals(PrimitivesDomain.FLOAT_OBJ)
					|| entity.equals(PrimitivesDomain.DECIMAL)	) {
				return DataType.AMOUNT;
			}
		}
		return DataType.DEFAULT;
	}
	

	/**
	 *
	 */
	public enum DataType {
		ENUMERATION, FLAG, STRING, NUMBER, AMOUNT, DATE, DEFAULT
	}

}
