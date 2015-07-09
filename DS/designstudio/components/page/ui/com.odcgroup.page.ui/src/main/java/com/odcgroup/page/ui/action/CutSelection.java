package com.odcgroup.page.ui.action;

import java.util.List;

/**
 * @author atr
 */
public class CutSelection {
	
	private int count = 0;
	private List selection;
	
	public int getCount() {
		return count;
	}
	
	public void incrCount() {
		count++;
	}
	
	public List getSelection () {
		return selection;
	}

	public CutSelection(List selection) {
		this.selection = selection;
	}

}
