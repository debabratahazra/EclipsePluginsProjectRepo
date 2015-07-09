package com.odcgroup.t24.version.editor.pattern;

import org.eclipse.emf.common.util.EList;

import com.odcgroup.t24.version.versionDSL.Field;
import com.odcgroup.t24.version.versionDSL.FieldsLayoutPattern;
import com.odcgroup.t24.version.versionDSL.Version;

/**
 *
 */
public class FieldLayoutPatternHelper {
	
	public static void applyFieldLayout(Version version) {
		FieldsLayoutPattern pattern = version.getFieldsLayoutPattern();
		switch (pattern.getValue()) {
		case FieldsLayoutPattern.NONE_VALUE:			
			break;
		case FieldsLayoutPattern.ONE_COLUMN_VALUE:
			applyOneColumnLayout(version);
			break;
		case FieldsLayoutPattern.ONE_ROW_VALUE:
			applyOneRowLayout(version);
			break;
		case FieldsLayoutPattern.TWO_COLUMN_HORIZONTAL_VALUE:
			applyMultiColumnHorizontalLayout(version, 2);
			break;
		case FieldsLayoutPattern.TWO_COLUMN_VERTICAL_VALUE:
			applyMultiColumnVerticalLayout(version, 2);
			break;
		case FieldsLayoutPattern.THREE_COLUMN_HORIZONTAL_VALUE:
			applyMultiColumnHorizontalLayout(version, 3);
			break;
		case FieldsLayoutPattern.THREE_COLUMN_VERTICAL_VALUE:
			applyMultiColumnVerticalLayout(version, 3);
			break;
		default:
			break;
		}		
	}
	
	private static void applyOneColumnLayout(Version version) {
		int row = 1;
		EList<Field> fields = version.getFields();
		for (Field oneField : fields) {
			oneField.setRow(row++);
			oneField.setColumn(1);
		}		
	}
	
	private static void applyOneRowLayout(Version version) {
		int col = 1;
		EList<Field> fields = version.getFields();
		for (Field oneField : fields) {
			oneField.setRow(1);
			oneField.setColumn(col++);
		}
	}
	
	private static void applyMultiColumnHorizontalLayout(Version version, int columnCount) {
		int idx = 0;
		EList<Field> fields = version.getFields();
		for (Field oneField : fields) {
			oneField.setRow((idx / columnCount) + 1);
			oneField.setColumn((idx) % columnCount + 1);
			idx++;
		}		
	}
	
	private static void applyMultiColumnVerticalLayout(Version version, int columnCount) {
		int idx = 0;
		EList<Field> fields = version.getFields();
		int nbRows = fields.size() / columnCount+1;
		for (Field oneField : fields) {
			oneField.setRow((idx % nbRows)+1);
			oneField.setColumn((idx / nbRows) +1);
			idx++;
		}		
	}

}
