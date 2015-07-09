package com.odcgroup.aaa.ui.internal.page;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.odcgroup.aaa.ui.Messages;
import com.odcgroup.mdf.editor.ui.WidgetFactory;
import com.odcgroup.mdf.editor.ui.dialogs.DialogPage;
import com.odcgroup.mdf.ext.aaa.AAAAspect;
import com.odcgroup.mdf.metamodel.MdfEntity;
import com.odcgroup.mdf.metamodel.MdfModelElement;

/**
 * Display Information specific to Triple'A Format
 * 
 * @author atr
 * @since DS 1.40.0
 */
public class MdfFormatUIPage extends DialogPage {
	
	/**
	 * Triple'A Format
	 */
	private MdfEntity format;
	
	private Text fmtName;
	private Text fmtFinFunction;
	private Text fmtEntitySQLName;
	private Text fmtColumnFilter;
	
	protected final String getName() {
		String value = AAAAspect.getTripleAFormatName(format);
		return value != null ? value : "";
	}
	
	protected final String getFinancialFunction() {
		String value = AAAAspect.getTripleAFormatFunction(format);
		return value != null ? value : "";
	}
	
	protected final String getEntitySQLName() {
		String value = AAAAspect.getTripleAEntitySQLName(format);
		return value != null ? value : "";
	}
	
	protected final String getColumnFilter() {
		String value = AAAAspect.getTripleAColumnFilter(format);
		return value != null ? value : "";
	}

	/**
	 * Initialize the ui controls
	 */
	private void initialize() {
		fmtName.setText(getName());
		fmtFinFunction.setText(getFinancialFunction());
		fmtEntitySQLName.setText(getEntitySQLName());
		fmtColumnFilter.setText(getColumnFilter());
	}

	/* 
	 * @see com.odcgroup.mdf.editor.ui.dialogs.DialogPage#doSave(com.odcgroup.mdf.metamodel.MdfModelElement)
	 */
	public void doSave(MdfModelElement element) {
		// nothing to do
	}

	/*
	 * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
	 */
	public void createControl(Composite parent) {
		
		WidgetFactory factory = getWidgetFactory();		
		
		final int gridDataStyle = GridData.FILL_HORIZONTAL | GridData.GRAB_HORIZONTAL;
		final GridData fieldGridData = new GridData(gridDataStyle);
		
		Composite container = new Composite(parent, SWT.NULL);
		container.setBackground(parent.getBackground());
		container.setForeground(parent.getForeground());
		container.setLayout(new GridLayout(2, false));
		container.setLayoutData(fieldGridData);
		
		Label label = null;
		
		// Format code
		label = new Label(container, SWT.NULL);
		label.setText(Messages.getString("aaa.mdf.format.name.label"));
		label.setBackground(container.getBackground());
		fmtName = factory.createText(container, null);
		fmtName.setLayoutData(fieldGridData);
		fmtName.setEditable(false);
        
		// Format Finantial Function
		label = new Label(container, SWT.NULL);
		label.setText(Messages.getString("aaa.mdf.format.financialfunction.label"));
		label.setBackground(container.getBackground());
		fmtFinFunction = factory.createText(container, null);
		fmtFinFunction.setLayoutData(fieldGridData);
		fmtFinFunction.setEditable(false);
        
		// Format AAA Entity name
		label = new Label(container, SWT.NULL);
		label.setText(Messages.getString("aaa.mdf.format.entitysqlname.label"));
		label.setBackground(container.getBackground());
		fmtEntitySQLName = factory.createText(container, null);
		fmtEntitySQLName.setLayoutData(fieldGridData);
		fmtEntitySQLName.setEditable(false);

		// Format Column filter
		label = new Label(container, SWT.NULL);
		label.setText(Messages.getString("aaa.mdf.format.columnfilter.label"));
		label.setBackground(container.getBackground());
		fmtColumnFilter = factory.createText(container, null);
		fmtColumnFilter.setLayoutData(fieldGridData);
		fmtColumnFilter.setEditable(false);

		initialize();
		setControl(container);

	}
	
	/**
	 * @param aaaFormat Triple'A Format
	 */
	public MdfFormatUIPage(MdfEntity format) {
		setTitle(Messages.getString("aaa.mdf.page.title"));
		setDescription(Messages.getString("aaa.mdf.page.format.description"));
		this.format = format;
	}

}
