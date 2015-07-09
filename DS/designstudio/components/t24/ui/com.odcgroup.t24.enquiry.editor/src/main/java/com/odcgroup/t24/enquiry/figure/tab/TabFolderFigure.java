package com.odcgroup.t24.enquiry.figure.tab;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.FlowLayout;
import org.eclipse.draw2d.GridData;
import org.eclipse.draw2d.GridLayout;
import org.eclipse.draw2d.Panel;
import org.eclipse.draw2d.StackLayout;

/**
 *
 * @author phanikumark
 *
 */
public class TabFolderFigure extends Panel {
	
	public static final int TAB_ITEM_HEIGHT = 25;
	private List<TabItemFigure> items;
	private StackLayout stackLayout;
	private Panel contentPanel;
	private Figure itemPanel;
	private TabItemFigure selectedTabItem;
	
	public List<TabItemFigure> getItems() {
		return items;
	}
	
	public TabFolderFigure() {
		super();
		init();
	}
	
	public TabItemFigure getSelectedTabItem(){
		return selectedTabItem;
	}
	
	private void init() {
		items = new ArrayList<TabItemFigure>();
		
		GridLayout gridLayout = new GridLayout();
		gridLayout.verticalSpacing = 0;
		stackLayout = new StackLayout();
		contentPanel = new Panel();
		contentPanel.setBackgroundColor(ColorConstants.white);
		
		itemPanel = new Figure();
		this.setLayoutManager(gridLayout);
		this.add(itemPanel);
		this.add(contentPanel);
		
		GridData itemgd = new GridData(GridData.FILL_HORIZONTAL);
		itemgd.heightHint = TAB_ITEM_HEIGHT;
		gridLayout.setConstraint(itemPanel, itemgd);
		
		FlowLayout flowlayout = new FlowLayout();
		flowlayout.setMajorSpacing(0);
		flowlayout.setMinorSpacing(0);
		itemPanel.setLayoutManager(flowlayout);
		
		GridData contentgd = new GridData(GridData.FILL_BOTH);
		gridLayout.setConstraint(contentPanel, contentgd);
		contentPanel.setLayoutManager(stackLayout);
	}
	
	public void addItem(TabItemFigure item) {
		itemPanel.add(item.getItem());
		contentPanel.add(item.getContentArea());
		item.getContentArea().setVisible(true);
		items.add(item);
	}
	
	public void select(TabItemFigure item) {
		for (TabItemFigure tif : items) {
			if(tif.equals(item)) {
				tif.setOnTop(true);
				selectedTabItem = tif;
			} else {
				tif.setOnTop(false);
			}
		}
	}

}
