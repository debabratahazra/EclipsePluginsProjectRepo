package com.odcgroup.workbench.editors.properties.choice;

import java.util.LinkedList;
import java.util.List;

public class ChoiceGroup {
	
	private String label;
	private List<ChoiceItem> choiceItems = new LinkedList<ChoiceItem>();
	private ChoiceItem defaultItem = null;
	
	public ChoiceGroup(String label){
		this.label = label;
	}
	
	/**
	 * @param item
	 */
	public void addChoiceItem(ChoiceItem item){
		choiceItems.add(item);
	}
	
	/**
	 * @param item
	 * @param defaultItem
	 */
	public void addChoiceItem(ChoiceItem item, boolean defaultItem){
		addChoiceItem(item);
		if (defaultItem){
			this.defaultItem = item;
		}
	}

	/**
	 * @return
	 */
	public String getLabel() {
		return label;
	}
	
	/**
	 * @param item
	 * @return
	 */
	public boolean isDefaultItem(ChoiceItem item){
		if (defaultItem != null && defaultItem.equals(item)){
			return true;
		} else {
			return false;
		}
	}

	/**
	 * @return
	 */
	public List<ChoiceItem> getChoiceItems() {
		return choiceItems;
	}

}
