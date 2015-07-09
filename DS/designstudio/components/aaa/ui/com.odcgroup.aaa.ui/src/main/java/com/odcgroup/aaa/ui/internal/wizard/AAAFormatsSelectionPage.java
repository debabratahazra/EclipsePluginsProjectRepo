package com.odcgroup.aaa.ui.internal.wizard;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;

import com.odcgroup.aaa.ui.Messages;
import com.odcgroup.aaa.ui.internal.model.AAAFormatCode;
import com.odcgroup.aaa.ui.internal.model.AAAFormatType;
import com.odcgroup.aaa.ui.internal.model.AAAFunction;
import com.odcgroup.aaa.ui.internal.model.AAAImportFacade;
import com.odcgroup.aaa.ui.internal.model.AAAPreferences;
import com.odcgroup.aaa.ui.internal.model.FormatSelectionInfo;
import com.odcgroup.workbench.core.IOfsModelPackage;
import com.odcgroup.workbench.ui.help.IOfsHelpContextIds;

public class AAAFormatsSelectionPage extends AbstractAAAPage {

	private Text formatCode;
	private Combo formatType;
	private Combo function;
	private AAASimpleFormatComposite formatComposite;

//	private Text formatCodePattern;

	private List<AAAFormatType> allFormatTypes = new ArrayList<AAAFormatType>();
	private List<AAAFunction> allFunctions = new ArrayList<AAAFunction>();

	PropertyChangeListener codePatternChangeListener = new PropertyChangeListener(){
		public void propertyChange(PropertyChangeEvent event) {
			AAAImportFacade facade = getImportFacade();
			if (event.getPropertyName().equals("formatCode")) {
				String pattern = facade.getFormatCodePattern();
				if (!formatCode.getText().equals(pattern)) {
					formatCode.setText(pattern);
					formatCode.setSelection(pattern.length());
				}
			}
		}
	};

	public AAAFormatsSelectionPage(IOfsModelPackage ofsPackage,
			AAAImportFacade importFacade) {
		super("", ofsPackage, importFacade);
		setTitle(Messages.getString("aaa.wizard.formats.selection.page.title"));
		setDescription(Messages.getString("aaa.wizard.formats.selection.page.description"));
		
	}

	public void createControl(Composite parent) {
		
		final GridData lblGridData = new GridData();
		lblGridData.widthHint = 70;
		
		Composite topLevel = new Composite(parent, SWT.NULL);
		topLevel.setLayout(new GridLayout(1, true));
		Label label = null;		

		/** 
		 * ###########################################
		 * 1. Format Selection group
		 * ###########################################
		 */

		Group formatGroup = new Group(topLevel, SWT.SHADOW_ETCHED_IN);
		formatGroup.setLayout(new GridLayout(2, false));
		formatGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		formatGroup.setText(Messages.getString("aaa.wizard.format.group"));

		label = new Label(formatGroup, SWT.NULL);
		label.setText(Messages.getString("aaa.wizard.format.code.label"));
		label.setLayoutData(lblGridData);
		formatCode = new Text(formatGroup, SWT.BORDER | SWT.SINGLE);
		formatCode.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		formatCode.addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent e) {
				getImportFacade().setFormatCodePattern(formatCode.getText());
			}
		});
		formatCode.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				onCodePatternChange();
			}
		});      

		label = new Label(formatGroup, SWT.NULL);
		label.setText(Messages.getString("aaa.wizard.format.type.label"));
		label.setLayoutData(lblGridData);
		formatType = new Combo(formatGroup, SWT.BORDER | SWT.SINGLE | SWT.READ_ONLY);
		formatType.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		formatType.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				int index = formatType.getSelectionIndex();
				AAAFormatType type = (index != -1) ? allFormatTypes.get(index) : null;
				getImportFacade().setSelectedFormatType(type);
			}
		});
		
		label = new Label(formatGroup, SWT.NULL);
		label.setText(Messages.getString("aaa.wizard.format.function.label"));
		label.setLayoutData(lblGridData);
		function = new Combo(formatGroup, SWT.BORDER | SWT.SINGLE | SWT.READ_ONLY);
		function.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		function.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				int index = function.getSelectionIndex();
				getImportFacade().setSelectedFunction(allFunctions.get(index));
			}
		});

		/** 
		 * ###########################################
		 * 2. Format
		 * ###########################################
		 */
		Group formatPreviewGroup = new Group(topLevel, SWT.SHADOW_ETCHED_IN);
		formatPreviewGroup.setLayout(new GridLayout(1, false));
		formatPreviewGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL | GridData.FILL_VERTICAL));
		formatPreviewGroup.setText(Messages.getString("aaa.wizard.format.preview.group"));

		getImportFacade().addPropertyChangeListener(codePatternChangeListener);
		formatComposite = new AAASimpleFormatComposite(formatPreviewGroup, getImportFacade());
		
		Composite buttonComposite = new Composite(formatPreviewGroup, SWT.NONE);
		buttonComposite.setLayout(new GridLayout(2, false));
		GridData gridData = new GridData();
		gridData.horizontalAlignment = SWT.END;
		gridData.grabExcessHorizontalSpace = true;
		buttonComposite.setLayoutData(gridData);
		
		Button selectButton = new Button(buttonComposite, SWT.PUSH);
		selectButton.setLayoutData(new GridData(SWT.END));
		selectButton.setText("  Select All  ");
		selectButton.addSelectionListener(new SelectionAdapter() {			
			public void widgetSelected(SelectionEvent e) {	
				formatComposite.getTableViewer().setAllChecked(true);
			}
		});
		
		Button deselectButton = new Button(buttonComposite, SWT.PUSH);
		deselectButton.setLayoutData(new GridData(SWT.END));
		deselectButton.setText("  Unselect All  ");
		deselectButton.addSelectionListener(new SelectionAdapter() {			
			public void widgetSelected(SelectionEvent e) {	
				formatComposite.getTableViewer().setAllChecked(false);
			}
		});
		
		/*
		label = new Label(formatPreviewGroup, SWT.NULL);
		label.setText(Messages.getString("aaa.wizard.format.preview.advice.label"));
		label.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_BEGINNING));
		*/
		
		initialize();
		setControl(topLevel);
		
		// Set help context
		PlatformUI.getWorkbench().getHelpSystem().setHelp(parent.getShell(), IOfsHelpContextIds.IMPORT_TA_FORMAT);
		
	}
	
	/**
	 * @return
	 */
	public FormatSelectionInfo getSelectionInfo() {
		FormatSelectionInfo info = new FormatSelectionInfo();
		info.setCode(formatCode.getText());
		info.setType(allFormatTypes.get(formatType.getSelectionIndex()).getDisplayName());
		info.setFunction(allFunctions.get(function.getSelectionIndex()).getDisplayName());
		TableColumn column = formatComposite.getTableViewer().getTable().getSortColumn();
		TableColumn[] columns = formatComposite.getTableViewer().getTable().getColumns();
		for (int i = 0; i < columns.length; i++) {
			if (columns[i].equals(column)) {
				info.setSortColumn(i);
			}
		}
		info.setSortDirection(formatComposite.getTableViewer().getTable().getSortDirection());
		return info;
	}
	
	private void initialize() {		
		AAAPreferences preferences = getImportFacade().getPreferences();
		if (StringUtils.isEmpty(preferences.getFormatCode())) {
			// TODO: remove ?
			formatCode.setText(getImportFacade().getFormatCodePattern());			
		} else {
			formatCode.setText(preferences.getFormatCode());
		}
		TableColumn[] columns = formatComposite.getTableViewer().getTable().getColumns();
		formatComposite.getTableViewer().getTable().setSortColumn(columns[preferences.getFormatSortColumn()]);
		formatComposite.getTableViewer().getTable().setSortDirection(preferences.getFormatSortDirection());
	}
	

	private void onCodePatternChange() {
		getImportFacade().setFormatCodePattern(formatCode.getText());
	}

	/**
	 * @param formatTypes the formatTypes to set
	 */
	public void initializeFormatTypes(List<AAAFormatType> formatTypes) {
		// Clear the combo
		formatType.removeAll();
		// Clean the list of all formats
		allFormatTypes.clear();
		int index = -1;
		String preferredType = getImportFacade().getPreferences().getFormatType();
		for (AAAFormatType type : formatTypes) {
			if (!StringUtils.isEmpty(preferredType) 
					&& preferredType.equals(type.getDisplayName())) {
				index = formatTypes.indexOf(type);
			}
			formatType.add(type.getDisplayName());
			allFormatTypes.add(type);
		}
		if (formatType.getItemCount() > 0) {
			if (index == -1) {
				formatType.select(0);
			} else {
				formatType.select(index);
			}
			getImportFacade().setSelectedFormatType(formatTypes.get(0));
		}
		// Refresh the display
		formatComposite.refreshTable();
	}

	/**
	 * @param formatCodes
	 */
	public void initializeFormatCodes(List<AAAFormatCode> formatCodes) {
		formatComposite.populateTable(formatCodes);
	}

	/**
	 * @param functions
	 */
	public void initializeFunctions(List<AAAFunction> functions) {
		// Clear the combo
		function.removeAll();
		// Clean the list of all formats
		allFunctions.clear();
		int index = -1;
		String preferredFunc = getImportFacade().getPreferences().getFormatFunction();
		
		for (AAAFunction aFunction : functions) {
			if (!StringUtils.isEmpty(preferredFunc) 
					&& preferredFunc.equals(aFunction.getDisplayName())) {
				index = functions.indexOf(aFunction);
			}
			function.add(aFunction.getDisplayName());
			allFunctions.add(aFunction);
		}
		if (index == -1) {
			function.select(0);
			getImportFacade().setSelectedFunction(allFunctions.get(0));
		} else {
			function.select(index);
			getImportFacade().setSelectedFunction(allFunctions.get(index));
		}

	}


	/* (non-Javadoc)
	 * @see org.eclipse.jface.wizard.WizardPage#getPreviousPage()
	 */
	@Override
	public IWizardPage getPreviousPage() {
		// Reset the error message
		// TODO: uses an event handler instead (ask kkr)
		setErrorMessage(null);
		return super.getPreviousPage();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.DialogPage#setVisible(boolean)
	 */
	@Override
	public void setVisible(boolean visible) {
		super.setVisible(visible);
		if (visible) {
			formatCode.forceFocus();
		}
	}
	
	/**
	 * @return formatcodes
	 */
	public List<AAAFormatCode> getSelectedFormatCodes() {
		Object[] selection = formatComposite.getTableViewer().getCheckedElements();
		List<AAAFormatCode> codes = new ArrayList<AAAFormatCode>();
		for (Object object : selection) {
			if (object instanceof AAAFormatCode) {
				codes.add((AAAFormatCode)object);
			}
		}
		return codes;
	}

}
