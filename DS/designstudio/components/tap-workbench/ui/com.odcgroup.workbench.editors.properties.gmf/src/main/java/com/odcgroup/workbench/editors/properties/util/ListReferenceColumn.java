package com.odcgroup.workbench.editors.properties.util;

import javax.swing.table.TableColumn;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.swt.SWT;

public class ListReferenceColumn {
	
	private String label;
	private String name;
	private boolean booleanType;
	private int width;
	private int weight;
	private int columnAlignment = SWT.LEFT;
	private TableColumn tabColumn = null;
	private EAttribute attribute = null;
	
	public ListReferenceColumn(String label, String name){
		this.label = label;
		this.name = name;
	}
	
	public ListReferenceColumn(String label, String name, EAttribute attribute){
		this(label, name);
		this.attribute = attribute;
	}
	
	public ListReferenceColumn(String label, String name, int width, int weight){
		this(label, name);
		setWidth(width);	
		setWeight(weight);
	}
	
	public ListReferenceColumn(String label, String name, int width, int weight, int columnAlignment){
		this(label, name, width, weight);
		setColumnAlignment(columnAlignment);
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isBooleanType() {
		return booleanType;
	}

	public void setBooleanType(boolean booleanType) {
		this.booleanType = booleanType;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public int getColumnAlignment() {
		return columnAlignment;
	}

	public void setColumnAlignment(int columnAlignment) {
		this.columnAlignment = columnAlignment;
	}

	public TableColumn getTabColumn() {
		return tabColumn;
	}

	public void setTabColumn(TableColumn tabColumn) {
		this.tabColumn = tabColumn;
	}

	public EAttribute getAttribute() {
		return attribute;
	}

}
