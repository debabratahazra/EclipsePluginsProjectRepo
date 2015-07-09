
package com.odcgroup.tap.mdf.generation.xls;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableHyperlink;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import com.odcgroup.mdf.metamodel.MdfBusinessType;
import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.mdf.metamodel.MdfDataset;
import com.odcgroup.mdf.metamodel.MdfDomain;
import com.odcgroup.mdf.metamodel.MdfEntity;
import com.odcgroup.mdf.metamodel.MdfEnumeration;


public class TAPExcelDomainGenerator {

	private MdfDomain domain;
	private Set<String> givenSheetName = new HashSet<String>();
	
	public TAPExcelDomainGenerator(MdfDomain domain) {
		
		this.domain = domain;
	}
	
	
	
	
	public WritableWorkbook createWorkbook(OutputStream os) throws Exception {
		
		WritableWorkbook wb = Workbook.createWorkbook(os);
		int sheetNumber = 0;
		
		WritableSheet entityListSheet = wb.createSheet(domain.getName(), sheetNumber);
		sheetNumber++;
		TAPSheetManager sheetManager = new TAPSheetManager(entityListSheet, domain);
		entityListSheet.addCell(new Label(0,0,"Type", TAPExcelCore.getTitleCellFormat()));
		entityListSheet.addCell(new Label(1,0,"Link", TAPExcelCore.getTitleCellFormat()));
			
		List list = new ArrayList();
		list.addAll(domain.getEntities());
		list.addAll(domain.getPrimitives());
		// for each entity, we have a Excel sheet 
		for (Iterator it = list.iterator();it.hasNext();) {
			MdfEntity entity = (MdfEntity)it.next();
			WritableSheet sheet = wb.createSheet(checkEntityName(entity.getName()), sheetNumber);
			
			sheetManager.addSheet(entity, sheet);
			sheetManager.addColumnManager(entity, new TAPSheetManager.ColumnManager());
			
			Label label = new Label(0, sheetNumber, entityType(entity));
			entityListSheet.addCell(label);
			
			label = new Label(1, sheetNumber, entity.getName());
			entityListSheet.addCell(label);
			WritableHyperlink hl = new WritableHyperlink(1, sheetNumber, entity.getQualifiedName().toString(), sheet, 0,0);
			entityListSheet.addHyperlink(hl);
			sheetNumber++;
			
		}
		TAPExcelSheetGenerator sheetHandler = new TAPExcelSheetGenerator(sheetManager);
		
		for (Iterator it = list.iterator();it.hasNext();) {
			MdfEntity entity = (MdfEntity)it.next();
			if (entity instanceof MdfClass) {
				sheetHandler.fillSheet(wb, (MdfClass)entity);
				// now set the columns sizes
				sheetHandler.updateColumnsSize(entity);
				
			} else if (entity instanceof MdfEnumeration) {
				sheetHandler.fillSheet(wb, (MdfEnumeration)entity);
				// now set the columns sizes
				sheetHandler.updateColumnsSize(entity);
			}
			
			
		}
		
		
		
		return wb;
	}
	
	private String entityType(MdfEntity entity) {
		if (entity instanceof MdfClass) {
			return "Class";
		} else if (entity instanceof MdfDataset) {
			return "Dataset";
		} else if (entity instanceof MdfEnumeration) {
			return "Enum";
		} else if (entity instanceof MdfBusinessType) {
			return "BusinessType";
		} else {
			return "";
		}
	}


	private String checkEntityName(String name) {
		String shortenName = StringUtils.abbreviate(name, 31);
		
		if (!givenSheetName.contains(shortenName)) {
			givenSheetName.add(shortenName);
			return shortenName;
		} else {
			for (int i=0; i<1000; i++) {
				String suffix = "("+i+")";
				String newTry = StringUtils.abbreviate(name, 31-suffix.length()) + suffix;
				if (!givenSheetName.contains(newTry)) {
					givenSheetName.add(newTry);
					return newTry;
				}
			}
		}
		throw new IllegalStateException("Unable to find a valid sheet name for " + name);
	}
	
	
}
