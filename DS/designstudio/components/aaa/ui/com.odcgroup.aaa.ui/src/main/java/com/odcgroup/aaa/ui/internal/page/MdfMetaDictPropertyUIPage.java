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
import com.odcgroup.mdf.metamodel.MdfModelElement;
import com.odcgroup.mdf.metamodel.MdfProperty;

/**
 * Display Information specific to Triple'A Attribute / Association
 * 
 * @author atr
 * @since DS 1.40.0
 */
public class MdfMetaDictPropertyUIPage extends DialogPage {
	
	/**
	 * Triple'A Attribute
	 */
	private MdfProperty attribute;
	
	private Text mdSQLName;
	private Text mdName;
	
	protected final String getSQLName() {
		String value = AAAAspect.getTripleAAttributeSQLName(attribute);
		return value != null ? value : "";
	}
	
	protected final String getName() {
		String value = AAAAspect.getTripleAAttributeName(attribute);
		return value != null ? value : "";
	}

	/**
	 * Initialize the ui controls
	 */
	private void initialize() {
		mdSQLName.setText(getSQLName());
		mdName.setText(getName());
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
		label.setText(Messages.getString("aaa.mdf.metadict.attribute.sqlname.label"));
		label.setBackground(container.getBackground());
		mdSQLName = factory.createText(container, null);
		mdSQLName.setLayoutData(fieldGridData);
		mdSQLName.setEditable(false);
        
		// Meta Dictionary ID
		label = new Label(container, SWT.NULL);
		label.setText(Messages.getString("aaa.mdf.metadict.attribute.name.label"));
		label.setBackground(container.getBackground());
		mdName = factory.createText(container, null);
		mdName.setLayoutData(fieldGridData);
		mdName.setEditable(false);
        
		initialize();
		setControl(container);

	}
	
	/**
	 * @param aaaFormat Triple'A Format
	 */
	public MdfMetaDictPropertyUIPage(MdfProperty attribute) {
		setTitle(Messages.getString("aaa.mdf.page.title"));
		setDescription(Messages.getString("aaa.mdf.page.attribute.description"));
		this.attribute = attribute;
	}

}
