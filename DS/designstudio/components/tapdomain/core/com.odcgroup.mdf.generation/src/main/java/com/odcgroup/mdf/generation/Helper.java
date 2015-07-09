package com.odcgroup.mdf.generation;

import java.util.ArrayList;
import java.util.List;

import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.mdf.metamodel.MdfDataset;
import com.odcgroup.mdf.metamodel.MdfDomain;
import com.odcgroup.mdf.metamodel.MdfEntity;
import com.odcgroup.mdf.metamodel.MdfProperty;

public class Helper {

	public final static String ODC_BASE_PACKAGE = "com.odcgroup";

	// For oAW extensions, cannot access constants directly...
	public static String getODCBasePackage() {
		return ODC_BASE_PACKAGE;
	}

	public static String toPath(String str) {
		return str.replace('.', '/');
	}

	public static String toPlural(String name) {
		if (name.endsWith("y")) {
			return name.substring(name.length() - 1).concat("ies");
		} else {
			return name.concat("s");
		}
	}

	public static MdfDomain getParentDomain(MdfEntity entity) {
		return entity.getParentDomain();
	}

	public static List getProperties(MdfClass model) {
		List props = new ArrayList();

		MdfClass baseClass = model.getBaseClass();
		if (baseClass != null) {
			props.addAll(getProperties(baseClass));
		}

		props.addAll(model.getProperties());

		return props;
	}

	public static MdfProperty getPrimaryKey(MdfClass model) {
		List pks = model.getPrimaryKeys(true);
		return pks.isEmpty() ? null : (MdfProperty) pks.get(0);
	}

	public static MdfProperty getPrimaryKey(MdfDataset model) {
		return getPrimaryKey(model.getBaseClass());
	}
}