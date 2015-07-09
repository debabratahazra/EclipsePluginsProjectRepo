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
import com.odcgroup.mdf.metamodel.MdfEnumValue;
import com.odcgroup.mdf.metamodel.MdfModelElement;

/**
 * Display Information specific to Triple'A Enumeration
 * 
 * @author atr
 * @since DS 1.40.0
 */
public class MdfMetaDictEnumerationValueUIPage extends DialogPage {
	
	/**
	 * Triple'A enumeration value
	 */
	private MdfEnumValue enumValue;
	
	private Text mdValueName;
	private Text mdValueRank;
	
	protected final String getPermittedValueName() {
		String value = AAAAspect.getTripleAPermittedValueName(enumValue);
		return value != null ? value : "";
	}
	
	protected final String getPermittedValueRank() {
		String value = AAAAspect.getTripleAPermittedValueRank(enumValue);
		return value != null ? value : "";
	}

	/**
	 * Initialize the ui controls
	 */
	private void initialize() {
		mdValueName.setText(getPermittedValueName());
		mdValueRank.setText(getPermittedValueRank());
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
		label.setText(Messages.getString("aaa.mdf.metadict.enumerationvalue.name.label"));
		label.setBackground(container.getBackground());
		mdValueName = factory.createText(container, null);
		mdValueName.setLayoutData(fieldGridData);
		mdValueName.setEditable(false);
        
		// Meta Dictionary ID
		label = new Label(container, SWT.NULL);
		label.setText(Messages.getString("aaa.mdf.metadict.enumerationvalue.rank.label"));
		label.setBackground(container.getBackground());
		mdValueRank = factory.createText(container, null);
		mdValueRank.setLayoutData(fieldGridData);
		mdValueRank.setEditable(false);
        
		initialize();
		setControl(container);

	}
	
	/**
	 * @param aaaFormat Triple'A Format
	 */
	public MdfMetaDictEnumerationValueUIPage(MdfEnumValue enumValue) {
		setTitle(Messages.getString("aaa.mdf.page.title"));
		setDescription(Messages.getString("aaa.mdf.page.enumerationvalue.description"));
		this.enumValue = enumValue;
	}

}
