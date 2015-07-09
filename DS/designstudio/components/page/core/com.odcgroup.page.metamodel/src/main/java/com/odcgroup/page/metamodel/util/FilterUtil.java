package com.odcgroup.page.metamodel.util;

import com.odcgroup.mdf.ecore.PrimitivesDomain;
import com.odcgroup.mdf.metamodel.MdfBusinessType;
import com.odcgroup.mdf.metamodel.MdfEntity;
import com.odcgroup.mdf.metamodel.MdfEnumeration;
import com.odcgroup.page.metamodel.util.OperatorTypeRegistry.Type;

/**
 *
 * @author pkk
 *
 */
public class FilterUtil {	

	
	/**
	 * @param entity
	 * @return operator type
	 */
	public static Type getOperatorKeyType(MdfEntity entity) {
		if (entity instanceof MdfBusinessType) {
			return getOperatorKeyType(((MdfBusinessType) entity).getType());
		}
		if (entity != null && entity.isPrimitiveType()) {
			if (entity.getQualifiedName().equals(PrimitivesDomain.STRING.getQualifiedName())) {
				return Type.STRING;
			} else if(entity.getQualifiedName().equals(PrimitivesDomain.DATE.getQualifiedName()) 
					|| entity.getQualifiedName().equals(PrimitivesDomain.DATE_TIME.getQualifiedName())) {
				return Type.DATE;
			} else if (entity.getQualifiedName().equals(PrimitivesDomain.BOOLEAN.getQualifiedName()) 
					|| entity.getQualifiedName().equals(PrimitivesDomain.BOOLEAN_OBJ.getQualifiedName())) {
				return Type.FLAG;
			} else if (entity.getQualifiedName().equals(PrimitivesDomain.INTEGER.getQualifiedName()) 
					|| entity.getQualifiedName().equals(PrimitivesDomain.INTEGER_OBJ.getQualifiedName())
					|| entity.getQualifiedName().equals(PrimitivesDomain.DOUBLE.getQualifiedName()) 
					|| entity.getQualifiedName().equals(PrimitivesDomain.DOUBLE_OBJ.getQualifiedName())
					|| entity.getQualifiedName().equals(PrimitivesDomain.SHORT.getQualifiedName()) 
					|| entity.getQualifiedName().equals(PrimitivesDomain.SHORT_OBJ.getQualifiedName())
					|| entity.getQualifiedName().equals(PrimitivesDomain.LONG.getQualifiedName()) 
					|| entity.getQualifiedName().equals(PrimitivesDomain.LONG_OBJ.getQualifiedName())
					|| entity.getQualifiedName().equals(PrimitivesDomain.FLOAT.getQualifiedName()) 
					|| entity.getQualifiedName().equals(PrimitivesDomain.FLOAT_OBJ.getQualifiedName())
					|| entity.getQualifiedName().equals(PrimitivesDomain.DECIMAL.getQualifiedName()) ) {
				return Type.NUMBER;
			} else if (entity instanceof MdfEnumeration) {
				return Type.ENUMERATION;
			}
		}
		return Type.DEFAULT;
	}
	

}
