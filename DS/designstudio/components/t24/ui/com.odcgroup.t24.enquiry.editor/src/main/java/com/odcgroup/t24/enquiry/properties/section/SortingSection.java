package com.odcgroup.t24.enquiry.properties.section;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

import com.odcgroup.t24.enquiry.editor.part.EnquiryDiagramEditPart;
import com.odcgroup.t24.enquiry.editor.part.commands.FixedSortCreationCommand;
import com.odcgroup.t24.enquiry.editor.part.commands.FixedSortDeleteCommand;
import com.odcgroup.t24.enquiry.editor.part.commands.FixedSortMoveDownCommand;
import com.odcgroup.t24.enquiry.editor.part.commands.FixedSortMoveUpCommand;
import com.odcgroup.t24.enquiry.enquiry.Enquiry;
import com.odcgroup.t24.enquiry.enquiry.FixedSort;
import com.odcgroup.t24.enquiry.properties.EnquiryTabbedPropertySheetPage;
import com.odcgroup.t24.enquiry.properties.dialogs.FixedSortDialog;
import com.odcgroup.t24.enquiry.properties.filter.IPropertyFilter;
import com.odcgroup.t24.enquiry.properties.filter.SortingSectionFilter;
import com.odcgroup.t24.enquiry.properties.sources.EnquiryPropertySourceWrapper;

public class SortingSection extends AbstractSection {
    
   private Enquiry enquiry =null;
   private EnquiryTabbedPropertySheetPage enquirytabpage =null;
   private Tree tree =null;
   private Button addButton =null ;
   private Button removeButton =null;
   private Button upButton =null;
   private Button downButton = null;
	@Override
	public IPropertyFilter getPropertyFilter() {
		return new SortingSectionFilter();
	}

	/**
	 * @see org.eclipse.ui.views.properties.tabbed.AdvancedPropertySection#createControls(org.eclipse.swt.widgets.Composite,
	 *      org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage)
	 */
	public void createControls(Composite parent, final TabbedPropertySheetPage aTabbedPropertySheetPage) {
		super.createControls(parent, aTabbedPropertySheetPage);
		tree = (Tree) page.getControl();
		tree.getColumn(0).setText("Field");
		tree.getColumn(1).setText("Sorting Direction");
		tree.getColumn(1).setWidth(500);
		Composite buttonBar = getWidgetFactory().createComposite(parent, SWT.BORDER);
		buttonBar.setLayout(new GridLayout(1, true));
		buttonBar.setSize(30, 100);
		// Add button
		addButton = new Button(buttonBar, SWT.NONE);
		addButton.setText("Add");
		GridData addButtonData = new GridData();
		addButtonData.grabExcessHorizontalSpace = true;
		addButtonData.horizontalIndent = 30;
		addButtonData.verticalIndent = 50;
		addButtonData.widthHint = 80;
		addButton.setLayoutData(addButtonData);
		// Remove button
		removeButton = new Button(buttonBar, SWT.NONE);
		removeButton.setText("Remove");
		GridData removeButtonData = new GridData();
		removeButtonData.grabExcessHorizontalSpace = true;
		removeButtonData.horizontalIndent = 30;
		removeButtonData.widthHint = 80;
		removeButton.setLayoutData(removeButtonData);
		// up button
	    upButton = new Button(buttonBar, SWT.NONE);
		upButton.setText("Up");
		GridData upButtonData = new GridData();
		upButtonData.grabExcessHorizontalSpace = true;
		upButtonData.horizontalIndent = 30;
		upButtonData.widthHint = 80;
		upButton.setLayoutData(upButtonData);
		// Down button
		downButton = new Button(buttonBar, SWT.NONE);
		downButton.setText("Down");
		GridData downButtonData = new GridData();
		downButtonData.grabExcessHorizontalSpace = true;
		downButtonData.horizontalIndent = 30;
		downButtonData.widthHint = 80;
		downButton.setLayoutData(downButtonData);
		enquirytabpage= (EnquiryTabbedPropertySheetPage) aTabbedPropertySheetPage;
		addButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				FixedSortDialog dialog = new FixedSortDialog(aTabbedPropertySheetPage.getControl().getShell(), enquiry);
				FixedSort fsort = dialog.openDilaog();
				if (fsort != null) {
					enquirytabpage = (EnquiryTabbedPropertySheetPage) aTabbedPropertySheetPage;
					enquirytabpage.getCommandStack().execute(new FixedSortCreationCommand(enquiry,fsort));
					refresh();
				}
			}
		});
		
		removeButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
					enquirytabpage = (EnquiryTabbedPropertySheetPage) aTabbedPropertySheetPage;
					TreeItem[] items = tree.getSelection();
					if(items.length !=0){
						String fieldName = items[0].getText();
						enquirytabpage.getCommandStack().execute(new FixedSortDeleteCommand(enquiry, fieldName));
					}
					refresh();
			}
		});
		upButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				TreeItem[] items = tree.getSelection();
				if(items.length !=0){
					String fieldName = items[0].getText();
					enquirytabpage.getCommandStack().execute(new FixedSortMoveUpCommand(enquiry, fieldName));
				}
				refresh();
			}
		});
		downButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				TreeItem[] items = tree.getSelection();
				if(items.length !=0){
					String fieldName = items[0].getText();
					enquirytabpage.getCommandStack().execute(new FixedSortMoveDownCommand(enquiry, fieldName));
				}
				refresh();
			}
		});
	}

	@Override
	public IPropertySource getPropertySource(Object object) {
		IPropertySource source = super.getPropertySource(object);
		if (object instanceof EnquiryDiagramEditPart) {
			enquiry = (Enquiry)((EnquiryDiagramEditPart) object).getModel();
			source = new EnquiryPropertySourceWrapper(source, modelPropertySourceProvider, enquiry);
		}
		return source;
	}
	
	private void updateButtons() {
		if(tree.getItems().length == 0 ){
			removeButton.setEnabled(false);
			upButton.setEnabled(false);
			downButton.setEnabled(false);
		}
		else {
			removeButton.setEnabled(true);
			upButton.setEnabled(true);
			downButton.setEnabled(true);
		}
	}
	   @Override
	public void refresh() {
		super.refresh();
		updateButtons();
	}
}
