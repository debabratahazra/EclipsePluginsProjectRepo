
package com.odcgroup.tap.mdf.generation.xls;

import jxl.write.Label;
import jxl.write.WritableHyperlink;
import jxl.write.WritableSheet;

import org.apache.commons.lang.StringUtils;

import com.odcgroup.mdf.metamodel.MdfAssociation;
import com.odcgroup.mdf.metamodel.MdfAttribute;
import com.odcgroup.mdf.metamodel.MdfConstants;
import com.odcgroup.mdf.metamodel.MdfDomain;
import com.odcgroup.mdf.metamodel.MdfEntity;
import com.odcgroup.mdf.metamodel.MdfEnumValue;
import com.odcgroup.mdf.metamodel.MdfEnumeration;
import com.odcgroup.mdf.metamodel.MdfProperty;


public class TAPExcelRowGenerator {
	
	private TAPSheetManager manager = null;
	private TAPSheetManager.ColumnManager columnManager = null;
	
	public TAPExcelRowGenerator(TAPSheetManager m, TAPSheetManager.ColumnManager cm) {
		this.manager = m;
		this.columnManager = cm;
	}
	
	public void createRow(WritableSheet sheet, MdfProperty property, int rowNumber) throws Exception {

		
		Label label = new Label(0, rowNumber, property.getName());
		columnManager.setColumnMaxSize(0, property.getName().length());
		sheet.addCell(label);
		
		if ((property instanceof MdfAttribute) && (!(property.getType() instanceof MdfEnumeration)))  {
		
			label = new Label(1, rowNumber, property.getType().getName());
			columnManager.setColumnMaxSize(1, property.getType().getName().length());
			sheet.addCell(label);
		} else {
			// an association to another class. If same domain, add hyperlink
			// if we put all the domains in the same excel file, we will add
			// the hyperlink also
			MdfEntity type = property.getType();
			MdfDomain parentDomain = type.getParentDomain();
			if (parentDomain != null && parentDomain.getQualifiedName().equals(manager.getDomain().getQualifiedName())) {
				// same domain
				WritableHyperlink hl = new WritableHyperlink(1, rowNumber, property.getType().getName(), manager.getSheet(type), 0, 0);

				columnManager.setColumnMaxSize(1, property.getType().getName().length());
				sheet.addHyperlink(hl);
			} else {
				label = new Label(1, rowNumber, property.getType().getQualifiedName().toString());

				columnManager.setColumnMaxSize(1, property.getType().getQualifiedName().toString().length());
				sheet.addCell(label);
			}
		}
		
		// here, we should use a SheetManager that handles the sheets for each MDF entity
		// from this info, we can put a hyperlink to it
		
		
		String multiplicity = "one";
		if (property instanceof MdfAssociation) {
			if (((MdfAssociation)property).getMultiplicity() == MdfConstants.MULTIPLICITY_MANY) {
				multiplicity = "many";
			}
		}
		label = new Label(2, rowNumber, multiplicity);
		columnManager.setColumnMaxSize(2, multiplicity.length());
		sheet.addCell(label);
		
		String documentation = StringUtils.trimToEmpty(property.getDocumentation());
		label = new Label(3, rowNumber, documentation);
		columnManager.setColumnMaxSize(3, documentation.length());
		sheet.addCell(label);
	}
	
	
	public void createRow(WritableSheet sheet, MdfEnumValue value, int rowNumber) throws Exception {
		
		Label label = new Label(0, rowNumber, value.getName());
		columnManager.setColumnMaxSize(0, value.getName().length());
		sheet.addCell(label);
		
		label = new Label(1, rowNumber, value.getValue());
		columnManager.setColumnMaxSize(1, value.getValue().length());
		sheet.addCell(label);
		
	}
	
	
	
}
