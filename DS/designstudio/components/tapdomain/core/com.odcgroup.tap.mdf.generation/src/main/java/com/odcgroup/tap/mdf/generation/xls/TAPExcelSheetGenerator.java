package com.odcgroup.tap.mdf.generation.xls;

import java.util.Iterator;

import jxl.write.Label;
import jxl.write.WritableHyperlink;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.mdf.metamodel.MdfEntity;
import com.odcgroup.mdf.metamodel.MdfEnumValue;
import com.odcgroup.mdf.metamodel.MdfEnumeration;
import com.odcgroup.mdf.metamodel.MdfProperty;

public class TAPExcelSheetGenerator {

	private TAPSheetManager sheetManager;
	
	public TAPExcelSheetGenerator(TAPSheetManager manager) {
		this.sheetManager = manager;
	}
	
	
	
	public void updateColumnsSize(MdfEntity entity) {
		WritableSheet sheet = sheetManager.getSheet(entity);
		
		TAPSheetManager.ColumnManager cm = sheetManager.getColumnManager(entity);
		
		for (Iterator it = cm.getColumns().iterator();it.hasNext();) {
			TAPSheetManager.ColumnInfo ch = (TAPSheetManager.ColumnInfo)it.next();
			sheet.setColumnView(ch.getColumnNumber(), ch.getMaxSize());
		}
		
	}
	
	
	public  void fillSheet(WritableWorkbook wb, MdfClass klass) throws Exception {

		WritableSheet sheet = sheetManager.getSheet(klass);

		TAPSheetManager.ColumnManager manager = sheetManager.getColumnManager(klass);
		TAPExcelRowGenerator gen = new TAPExcelRowGenerator(sheetManager, manager);
		int row = addHeader(sheet, klass);
		for (Iterator it = klass.getProperties(true).iterator(); it.hasNext();) {
			MdfProperty property = (MdfProperty) it.next();
			gen.createRow(sheet, property, row);
			row++;
		}
	}

	public  void fillSheet(WritableWorkbook wb, MdfEnumeration e) throws Exception {
		
		WritableSheet sheet = sheetManager.getSheet(e);
		TAPSheetManager.ColumnManager manager = sheetManager.getColumnManager(e);
		TAPExcelRowGenerator gen = new TAPExcelRowGenerator(sheetManager, manager);

		int row = addHeader(sheet, e);
		for (Iterator it = e.getValues().iterator(); it.hasNext();) {
			MdfEnumValue value = (MdfEnumValue) it.next();
			gen.createRow(sheet, value, row);
			row++;
		}

	}
	

	
//	 populate the column names
	// Property name, type, containment, description
	private  int addHeader(WritableSheet sheet, MdfClass klass) throws Exception {
		int rowN = 0;
		
		TAPSheetManager.ColumnManager manager = sheetManager.getColumnManager(klass);
		 
		 // the hyperlink to go back to domain list
		 WritableHyperlink hl = new WritableHyperlink(0, rowN, "Back to domain", sheetManager.getSheet(klass.getParentDomain()), 0,0);
			Label label = new Label(0, rowN, klass.getParentDomain().getName());
			sheet.addCell(label);
			sheet.addHyperlink(hl);
			manager.setColumnMaxSize(0, klass.getParentDomain().getName().length());
		 rowN++;
		 rowN++;
		 
		 // Create the label, specifying content and format 
		 label = new Label(0, rowN, "Class",  TAPExcelCore.getTitleCellFormat()); 
		 sheet.addCell(label); 
		 manager.setColumnMaxSize(0, "Class".length());
		 label = new Label(1, rowN, klass.getQualifiedName().toString(), TAPExcelCore.getDefaultCellFormat());
		 sheet.addCell(label);
			manager.setColumnMaxSize(1, klass.getQualifiedName().toString().length());
		rowN++;
		
		
		if (klass.getBaseClass() != null) {
			label = new Label(0, rowN, "Extends",  TAPExcelCore.getTitleCellFormat()); 
			 manager.setColumnMaxSize(0, "Extends".length());
			 sheet.addCell(label); 
			 label = new Label(1, rowN, klass.getBaseClass().getQualifiedName()
					.toString(), TAPExcelCore.getDefaultCellFormat());
				manager.setColumnMaxSize(1, klass.getBaseClass().getQualifiedName()
						.toString().length());
			 sheet.addCell(label);
			rowN++;
		}
		label = new Label(0, rowN, "Visibility",  TAPExcelCore.getTitleCellFormat()); 
		 manager.setColumnMaxSize(0, "Visibility".length());
		 sheet.addCell(label); 
		 label = new Label(1, rowN, "public", TAPExcelCore.getDefaultCellFormat());

		 manager.setColumnMaxSize(1, "public".length());
		 sheet.addCell(label);
		rowN++;

		rowN++;

		// the columns for the fields
		label = new Label(0, rowN, "Property Name",  TAPExcelCore.getTitleCellFormat()); 
		 manager.setColumnMaxSize(0, "Property Name".length()); 
		 sheet.addCell(label); 
		 label = new Label(1, rowN, "Type", TAPExcelCore.getTitleCellFormat());
		 manager.setColumnMaxSize(1, "Type".length()); 
		 sheet.addCell(label);
		 label = new Label(2, rowN, "Containment", TAPExcelCore.getTitleCellFormat());
		 manager.setColumnMaxSize(2, "Containment".length()); 
		 sheet.addCell(label);
		 label = new Label(3, rowN, "Description", TAPExcelCore.getTitleCellFormat());
		 manager.setColumnMaxSize(3, "Description".length()); 
		 sheet.addCell(label);
		

		rowN++;
		return rowN;
	}

	//	 populate the column names
	// Property name, type, containment, description
	private int addHeader(WritableSheet sheet, MdfEnumeration e) throws Exception {

		TAPSheetManager.ColumnManager manager = sheetManager.getColumnManager(e);
		int rowN = 0;
//		 the hyperlink to go back to domain list
		 WritableHyperlink hl = new WritableHyperlink(0, rowN, "Back to domain", sheetManager.getSheet(e.getParentDomain()), 0,0);

		 manager.setColumnMaxSize(0, "Back to domain".length()); 
			sheet.addHyperlink(hl);
		 rowN++;
		 rowN++;
		 
		 // Create the label, specifying content and format 
		 Label label = new Label(0,rowN, "name",  TAPExcelCore.getTitleCellFormat());

		 manager.setColumnMaxSize(0, "name".length()); 
		 sheet.addCell(label); 
		 label = new Label(1, rowN, "value", TAPExcelCore.getTitleCellFormat());
		 manager.setColumnMaxSize(1, "value".length()); 
		 sheet.addCell(label);
		 label = new Label(2, rowN, "description", TAPExcelCore.getTitleCellFormat());

		 manager.setColumnMaxSize(2, "description".length()); 
		 sheet.addCell(label);
		
		 rowN++;
		 rowN++;
		
		
		return rowN;
	}

}