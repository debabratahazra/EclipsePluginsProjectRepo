package com.odcgroup.t24.enquiry.figure.tab;

import org.eclipse.draw2d.ActionEvent;
import org.eclipse.draw2d.ActionListener;
import org.eclipse.draw2d.ButtonModel;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.FigureUtilities;
import org.eclipse.draw2d.GridData;
import org.eclipse.draw2d.GridLayout;
import org.eclipse.draw2d.Panel;
import org.eclipse.jface.resource.JFaceResources;

/**
 *
 * @author phanikumark
 *
 */
public class TabItemFigure {
	
	private static final int WIDTH_DIFF = 40; 
	
	private TabFolderFigure tabFolder;
	private TabFigure item;
	private Panel contentArea;  
	private GridLayout gridLayout; 
	private boolean onTop;  
	
	
	public TabItemFigure(TabFolderFigure tabFolder, String name) {
		this.tabFolder = tabFolder;
		item = new TabFigure(name);
		item.setPreferredSize(FigureUtilities.getTextWidth(name, JFaceResources.getDefaultFont())+WIDTH_DIFF, TabFolderFigure.TAB_ITEM_HEIGHT);
		item.setBackgroundColor(ColorConstants.button);
		contentArea = new Panel();
		gridLayout = new GridLayout();
		gridLayout.marginHeight = 0;
		gridLayout.marginWidth = 0;
		contentArea.setLayoutManager(gridLayout);
		onTop = false;
		init();
	}
	
	private void init() {
		ButtonModel model = new ButtonModel();
		model.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent event) {				
				tabFolder.select(TabItemFigure.this);
			}
		});
		item.setModel(model);
	}
	
	public TabFigure getItem() {
		return item;
	}
	
	public Panel getContentArea() {
		return contentArea;
	}
	
	protected void refreshState() {
		if (onTop) {
			getItem().setBackgroundColor(ColorConstants.white);
			getItem().setForegroundColor(ColorConstants.black);
			getContentArea().setVisible(true);
		} else {
			getItem().setBackgroundColor(ColorConstants.button);
			getItem().setForegroundColor(ColorConstants.lightGray);
			getContentArea().setVisible(false);
		}
	}
	
	public void setContent(Figure figure) {
		contentArea.add(figure);
		GridData gd = new GridData(GridData.FILL_BOTH);
		gridLayout.setConstraint(figure, gd);
	}
	
	public void setOnTop(boolean onTop) {
		this.onTop = onTop;
		refreshState();
	}

}
