/*******************************************************************************
 * Copyright (c) 2006 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package com.odcgroup.page.ui.properties.sections;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.ScrollBar;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.views.properties.PropertySheetPage;
import org.eclipse.ui.views.properties.PropertySheetSorter;
import org.eclipse.ui.views.properties.tabbed.AbstractPropertySection;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

import com.odcgroup.page.metamodel.PropertyCategory;
import com.odcgroup.page.ui.properties.IWidgetPropertySource;
import com.odcgroup.page.util.AdaptableUtils;

/**
 * This is an abstract class for section in a tab in the properties view.
 * 
 * @author Alain Tripod 
 */
public abstract class AbstractSection extends AbstractPropertySection {

	/**
	 * The Property Sheet Page.
	 */
	protected PropertySheetPage page;
	protected Composite parent;
	
	/**
	 * The category for filtering widget properties that will be
	 * used by the WidgetEditPart to construct property source for
	 * the current selected widget/edit part
	 */
	private PropertyCategory category;
	
	/**
	 * @see org.eclipse.ui.views.properties.tabbed.AdvancedPropertySection#createControls(org.eclipse.swt.widgets.Composite, org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage)
	 */
	public void createControls(Composite parent, final TabbedPropertySheetPage aTabbedPropertySheetPage) {
		super.createControls(parent, aTabbedPropertySheetPage);
		final Composite composite = getWidgetFactory()
			.createFlatFormComposite(parent);
		//create a section property sheet page.
		page = new SectionPropertySheetPage();
        page.createControl(composite);
		FormData data = new FormData();
		data.left = new FormAttachment(0, 0);
		data.right = new FormAttachment(100, 0);
		data.top = new FormAttachment(0, 0);
		data.bottom = new FormAttachment(100, 0);
		page.getControl().setLayoutData(data);

		final Tree tree = (Tree) page.getControl();
		page.getControl().addControlListener(new ControlAdapter() {
			public void controlResized(ControlEvent e) {
				aTabbedPropertySheetPage.resizeScrolledComposite();
				Rectangle area = composite.getClientArea();
				Point size = tree.computeSize(SWT.DEFAULT, SWT.DEFAULT);
				ScrollBar vBar = tree.getVerticalBar();
				int width = area.width - tree.computeTrim(0,0,0,0).width - vBar.getSize().x;
				if (size.y > area.height + tree.getHeaderHeight()) {
					Point vBarSize = vBar.getSize();
					width -= vBarSize.x;
				}
				Point oldSize = tree.getSize();
				if (oldSize.x > area.width) {
					TreeColumn[] columns = tree.getColumns();
					for (TreeColumn column : columns) {
						column.setWidth(width/columns.length);
					}
				 	tree.setSize(area.width, area.height);
				 } else {
				 	tree.setSize(area.width, area.height);
				 	TreeColumn[] columns = tree.getColumns();
					for (TreeColumn column : columns) {
						column.setWidth(width/columns.length);
					}
				}
			}
		});
		this.parent = parent;
		
	}

	/**
	 * Notifies the section that the workbench selection has changed. 
	 * 
	 * @param part The active workench part.
	 * @param selection The active selection in the workbench part.
	 * 
	 * Implementation note: transmit the category to the selection only
	 * if it's an instance of a WidgetPropertySource.
	 */
	public void setInput(IWorkbenchPart part, ISelection selection) {
		if (selection instanceof IStructuredSelection) {
			Object input = ((IStructuredSelection) selection).getFirstElement();
			IWidgetPropertySource wps = (IWidgetPropertySource) AdaptableUtils.getAdapter(input, IWidgetPropertySource.class);
			
			// notify the property source the current category of widget properties
			if (wps != null) {
				wps.setCurrentPropertyCategory(category);
			}

		}
		if (part instanceof IEditorPart) {
			IEditorInput editInput = ((IEditorPart) part).getEditorInput();
			if (editInput instanceof IFileEditorInput) {
				parent.setEnabled(true);
			} else {
				parent.setEnabled(false);
			}
		}
		
		super.setInput(part, selection);
		page.selectionChanged(part, selection);
	}
	
	/**
	 * @see org.eclipse.ui.views.properties.tabbed.ISection#dispose()
	 */
	public void dispose() {
		super.dispose();

		if (page != null) {
			page.dispose();
			page = null;
		}

	}

	/**
	 * @see org.eclipse.ui.views.properties.tabbed.ISection#refresh()
	 */
	public void refresh() {
		page.refresh();
	}

	/**
	 * @see org.eclipse.ui.views.properties.tabbed.ISection#shouldUseExtraSpace()
	 */
	public boolean shouldUseExtraSpace() {
		return true;
	}
	
	/**
	 * Constructor
	 * @param category
	 */
	protected AbstractSection(PropertyCategory category) {
		this.category = category;
	}
	
	/**
	 * a custom property sheet page to set the sorter.
	 * @author snn
	 *
	 */
	class SectionPropertySheetPage extends PropertySheetPage{
		/**
		 * setSorter to set the sorter to the propertysheet page.
		 */
		public void setSorter(PropertySheetSorter sorter) {
			super.setSorter(sorter);
		}
	}
	
	/**
	 * set the sorter for this propertysheet page
	 * @param sorter.
	 */
	protected void setSorter(PropertySheetSorter sorter){
		((SectionPropertySheetPage)page).setSorter(sorter);
	}
	
}