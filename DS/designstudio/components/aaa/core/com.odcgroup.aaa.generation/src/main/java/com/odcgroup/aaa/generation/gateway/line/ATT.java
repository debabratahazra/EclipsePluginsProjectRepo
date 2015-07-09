package com.odcgroup.aaa.generation.gateway.line;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.StringUtils;

public class ATT {

	private List<String> columnNames = new ArrayList<String>();
	private List<DAT> dats = new ArrayList<DAT>();
	
	public ATT(String... columnNames) {
		this(Arrays.asList(columnNames));
	}
	
	public ATT(List<String> columnNames) {
		this.columnNames.addAll(columnNames);
	}
	
	public List<String> getColumnNames() {
		return Collections.unmodifiableList(columnNames);
	}
	
	public void setColumnNames(List<String> newColumnNames) {
		List<String> newValue = new ArrayList<String>();
		newValue.addAll(newColumnNames);
		this.columnNames = newValue;
	}
	
	public void addDAT(DAT dat) {
		dats.add(dat);
	}
	
	public void clearDAT() {
		dats.clear();
	}
	
	public List<DAT> getDATs() {
		return Collections.unmodifiableList(dats);
	}
	
	public void setDATs(List<DAT> newDATs) {
		List<DAT> newValue = new ArrayList<DAT>();
		newValue.addAll(newDATs);
		this.dats = newValue;
	}
	
	public String toString() {
		StringBuffer result = new StringBuffer();
		for (String columnName:columnNames) {
			result.append(columnName);
			result.append(" ");
		}
		return "ATT " + StringUtils.removeEnd(result.toString(), " ");
	}
	
}
