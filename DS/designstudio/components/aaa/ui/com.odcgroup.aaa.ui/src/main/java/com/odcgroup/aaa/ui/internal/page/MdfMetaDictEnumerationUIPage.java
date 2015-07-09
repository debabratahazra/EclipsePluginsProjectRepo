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
import com.odcgroup.mdf.metamodel.MdfEnumeration;
import com.odcgroup.mdf.metamodel.MdfModelElement;

/**
 * Display Information specific to Triple'A Enumeration
 * 
 * @author atr
 * @since DS 1.40.0
 */
public class MdfMetaDictEnumerationUIPage extends DialogPage {
	
	/**
	 * Triple'A Attribute
	 */
	private MdfEnumeration enumeration;
	
	private Text mdEntitySQLName;
	private Text mdAttributeSQLName;
	
	protected final String getEntitySQLName() {
		String value = AAAAspect.getTripleAEntitySQLName(enumeration);
		return value != null ? value : "";
	}
	
	protected final String getAttributeSQLName() {
		String value = AAAAspect.getTripleAAttributeSQLName(enumeration);
		return value != null ? value : "";
	}

	/**
	 * Initialize the ui controls
	 */
	private void initialize() {
		mdEntitySQLName.setText(getEntitySQLName());
		mdAttributeSQLName.setText(getAttributeSQLName());
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
		
		// Name
		label = new Label(container, SWT.NULL);
		label.setText(Messages.getString("aaa.mdf.metadict.enumeration.entity.sqlname.label"));
		label.setBackground(container.getBackground());
		mdEntitySQLName = factory.createText(container, null);
		mdEntitySQLName.setLayoutData(fieldGridData);
		mdEntitySQLName.setEditable(false);
        
		// Meta Dictionary ID
		label = new Label(container, SWT.NULL);
		label.setText(Messages.getString("aaa.mdf.metadict.enumeration.attribute.sqlname.label"));
		label.setBackground(container.getBackground());
		mdAttributeSQLName = factory.createText(container, null);
		mdAttributeSQLName.setLayoutData(fieldGridData);
		mdAttributeSQLName.setEditable(false);
        
		initialize();
		setControl(container);

	}
	
	/**
	 * @param aaaFormat Triple'A Format
	 */
	public MdfMetaDictEnumerationUIPage(MdfEnumeration enumeration) {
		setTitle(Messages.getString("aaa.mdf.page.title"));
		setDescription(Messages.getString("aaa.mdf.page.enumeration.description"));
		this.enumeration = enumeration;
	}

}
