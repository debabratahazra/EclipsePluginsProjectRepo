package com.odcgroup.t24.enquiry.properties.section;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;
import org.eclipse.xtext.ui.label.GlobalDescriptionLabelProvider;

import com.odcgroup.t24.enquiry.editor.part.FieldEditPart;
import com.odcgroup.t24.enquiry.editor.part.OutputFieldEditPart;
import com.odcgroup.t24.enquiry.editor.part.commands.ConversionCreationCommand;
import com.odcgroup.t24.enquiry.editor.part.commands.ConversionDeletionCommand;
import com.odcgroup.t24.enquiry.editor.part.commands.ConversionEditCommand;
import com.odcgroup.t24.enquiry.enquiry.Conversion;
import com.odcgroup.t24.enquiry.enquiry.Field;
import com.odcgroup.t24.enquiry.properties.EnquiryTabbedPropertySheetPage;
import com.odcgroup.t24.enquiry.properties.dialogs.FieldConversionPropertyTypeDialog;
import com.odcgroup.t24.enquiry.properties.filter.ConversionSectionFilter;
import com.odcgroup.t24.enquiry.properties.filter.IPropertyFilter;
import com.odcgroup.t24.enquiry.properties.sources.FieldPropertySourceWrapper;
import com.odcgroup.t24.enquiry.properties.util.ConversionEnum;
import com.odcgroup.t24.enquiry.properties.util.ConversionPropertiesUtil;
import com.odcgroup.workbench.ui.xtext.search.LanguageXtextEObjectSearch;

/**
 * 
 * @author mumesh
 * 
 */
public class ConversionSection extends AbstractSection {

	@Inject
	protected LanguageXtextEObjectSearch eObjectSearch;
	
	@Inject 
	protected GlobalDescriptionLabelProvider globalDescriptionLabelProvider;	

    private Field field = null;
    private EnquiryTabbedPropertySheetPage enquirytabpage =null;
    private Tree tree =null;
    private Button addButton =null ;
    private Button removeButton =null;
    private Button editButton =null;
    private String[] conversionTypes = ConversionPropertiesUtil.conversionTypes;

	@Override
	public IPropertyFilter getPropertyFilter() {
		return new ConversionSectionFilter();
	}

	/**
	 * @see org.eclipse.ui.views.properties.tabbed.AdvancedPropertySection#createControls(org.eclipse.swt.widgets.Composite,
	 *      org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage)
	 */
	public void createControls(Composite parent, final TabbedPropertySheetPage aTabbedPropertySheetPage) {
		super.createControls(parent, aTabbedPropertySheetPage);
		tree = (Tree) page.getControl();
		tree.getColumn(0).setText("Conversion");
		tree.getColumn(1).setText("Summary");
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

		
		// Edit button
		editButton = new Button(buttonBar, SWT.NONE);
		editButton.setText("Edit");
		GridData editButtonData = new GridData();
		editButtonData.grabExcessHorizontalSpace = true;
		editButtonData.horizontalIndent = 30;
		editButtonData.widthHint = 80;
		editButton.setLayoutData(editButtonData);

		enquirytabpage= (EnquiryTabbedPropertySheetPage) aTabbedPropertySheetPage;
		addButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Arrays.sort(conversionTypes);
				Shell shell = aTabbedPropertySheetPage.getControl().getShell();
				FieldConversionPropertyTypeDialog dialog = new FieldConversionPropertyTypeDialog(shell, 
						field, null, conversionTypes, false, eObjectSearch, globalDescriptionLabelProvider);
				Conversion conversion = dialog.openDilaog();
				if (conversion != null) {
					enquirytabpage = (EnquiryTabbedPropertySheetPage) aTabbedPropertySheetPage;
					enquirytabpage.getCommandStack().execute(new ConversionCreationCommand(field,conversion));
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
						String conversionType = items[0].getText(0);
						String summary = items[0].getText(1);
						enquirytabpage.getCommandStack().execute(new ConversionDeletionCommand(field,conversionType,summary));
					}
					refresh();
			}
		});
		
		editButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				TreeItem[] items = tree.getSelection();
				if(items.length ==0){
					return;
				}
				String conversionType = items[0].getText(0);
				String summary = items[0].getText(1);
				List<Conversion> conversionList = field.getConversion();
				Conversion modifyConversion = null;
				for(Conversion conversion : conversionList) { 
					if(conversionType.equals(ConversionEnum.getConversionEnum(conversion).getDisplay()) 
							&& summary.equals(ConversionPropertiesUtil.getConversionSummary(conversion))){
						modifyConversion = conversion;
						break;
					}
				}
				
				Arrays.sort(conversionTypes);
				Shell shell = aTabbedPropertySheetPage.getControl().getShell();
				FieldConversionPropertyTypeDialog dialog = new FieldConversionPropertyTypeDialog(shell,
						field, modifyConversion, conversionTypes, true, 
						eObjectSearch, globalDescriptionLabelProvider);
				Conversion conversion = dialog.openDilaog();
				if (conversion != null) {
					enquirytabpage = (EnquiryTabbedPropertySheetPage) aTabbedPropertySheetPage;
					enquirytabpage.getCommandStack().execute(new ConversionEditCommand(field, summary, conversion));
					refresh();
				}
			}
		});

	}


	@Override
	public IPropertySource getPropertySource(Object object) {
		IPropertySource source = super.getPropertySource(object);
		if(object instanceof FieldEditPart ){
			field = (Field)((FieldEditPart)object).getModel();
			eObjectSearch.setContext(field.eContainer());
			source= new FieldPropertySourceWrapper(source,modelPropertySourceProvider,field);
		} else if(object instanceof OutputFieldEditPart ){
			field = (Field)((OutputFieldEditPart)object).getModel();
			eObjectSearch.setContext(field.eContainer());
			source= new FieldPropertySourceWrapper(source,modelPropertySourceProvider,field);
		} else {
			eObjectSearch.setContext(null);
		}
		return source;
	}
	
	private void updateButtons() {
		if(tree.getItems().length == 0 ){
			removeButton.setEnabled(false);
		}
		else {
			removeButton.setEnabled(true);
		}
	}
	   @Override
	public void refresh() {
		super.refresh();
		updateButtons();
	}
}