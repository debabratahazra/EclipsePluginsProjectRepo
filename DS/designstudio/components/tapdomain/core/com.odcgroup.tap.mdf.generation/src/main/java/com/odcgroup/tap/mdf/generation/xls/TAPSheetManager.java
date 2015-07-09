
package com.odcgroup.tap.mdf.generation.xls;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import jxl.write.WritableSheet;

import com.odcgroup.mdf.metamodel.MdfDomain;
import com.odcgroup.mdf.metamodel.MdfEntity;

public class TAPSheetManager {
	
	private Map sheets = null;
	private Map columnManagers = null;
	private WritableSheet mainSheet = null;
	private MdfDomain domain;
	
	public TAPSheetManager(WritableSheet main, MdfDomain domain) {
		this.mainSheet = main;
		this.sheets = new HashMap();
		this.columnManagers = new HashMap();
		this.domain = domain;
	}
	
	public MdfDomain getDomain() {
		return domain;
	}
	
	
	public void addSheet(MdfEntity entity, WritableSheet sheet) {
		
		sheets.put(entity.getQualifiedName(), sheet);
		
		
	}
	
	
	public WritableSheet getSheet(MdfEntity entity) {
		return (WritableSheet)sheets.get(entity.getQualifiedName());
	}
	
	public void addColumnManager(MdfEntity entity, ColumnManager m) {
		
		columnManagers.put(entity.getQualifiedName(), m);
		
		
	}
	
	
	public ColumnManager getColumnManager(MdfEntity entity) {
		return (ColumnManager)columnManagers.get(entity.getQualifiedName());
	}
	
	
	public WritableSheet getSheet(MdfDomain domain) {
		return mainSheet;
	}
	
	
	public static class ColumnManager {
		private Map columns = new HashMap();
		
		public Collection getColumns() {
			return columns.values();
		}
		
	
		public void setColumnMaxSize(int column, int size) {
			ColumnInfo info = (ColumnInfo)columns.get(Integer.valueOf(column));
			if (info == null) {
				// first time, set size
				info = new ColumnInfo(column);
				info.setMaxSize(size);
				columns.put(Integer.valueOf(column), info);
			} else {
				if (info.getMaxSize() < size) {
					info.setMaxSize(size);
				}
			}
			
		}
		
	}
	
	public static class ColumnInfo {
		private int columnNumber;
		private int maxSize = 0;
		
		public ColumnInfo(int num) {
			this.columnNumber= num;
		}
		
		public int getColumnNumber() {
			return columnNumber;
		}
		
		public int getMaxSize() {
			return maxSize;
		}
		
		public void setMaxSize(int m) {
			this.maxSize = m;
		}
		
	}
	

}
